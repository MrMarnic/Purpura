package net.purpura.blocks;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.*;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.registries.ForgeRegistries;
import net.purpura.Purpura;
import net.purpura.dimension.PurpuraTeleporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;

/**
 * Copyright (c) 09.09.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class PurpuraPortalBlock extends Block{
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    protected static final VoxelShape X_AXIS_AABB = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    protected static final VoxelShape Z_AXIS_AABB = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

    public static HashMap<UUID,Integer> PORTAL_COOLDOWN = new HashMap<>();

    public PurpuraPortalBlock(AbstractBlock.Properties p_i48352_1_) {
        super(p_i48352_1_);
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.X));
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        switch((Direction.Axis)p_220053_1_.getValue(AXIS)) {
            case Z:
                return Z_AXIS_AABB;
            case X:
            default:
                return X_AXIS_AABB;
        }
    }

    /*
    @Override
    public void randomTick(BlockState p_225542_1_, ServerWorld p_225542_2_, BlockPos p_225542_3_, Random p_225542_4_) {
        if (p_225542_2_.dimensionType().natural() && p_225542_2_.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING) && p_225542_4_.nextInt(2000) < p_225542_2_.getDifficulty().getId()) {
            while(p_225542_2_.getBlockState(p_225542_3_).is(this)) {
                p_225542_3_ = p_225542_3_.below();
            }

            if (p_225542_2_.getBlockState(p_225542_3_).isValidSpawn(p_225542_2_, p_225542_3_, EntityType.ZOMBIFIED_PIGLIN)) {
                Entity entity = EntityType.ZOMBIFIED_PIGLIN.spawn(p_225542_2_, (CompoundNBT)null, (ITextComponent)null, (PlayerEntity)null, p_225542_3_.above(), SpawnReason.STRUCTURE, false, false);
                if (entity != null) {
                    entity.setPortalCooldown();
                }
            }
        }

    }
     */

    @Override
    public BlockState updateShape(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, IWorld p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
        Direction.Axis direction$axis = p_196271_2_.getAxis();
        Direction.Axis direction$axis1 = p_196271_1_.getValue(AXIS);
        boolean flag = direction$axis1 != direction$axis && direction$axis.isHorizontal();
        return !flag && !p_196271_3_.is(this) && !(new PortalSize(p_196271_4_, p_196271_5_, direction$axis1)).isComplete() ? Blocks.AIR.defaultBlockState() : super.updateShape(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
    }
    @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
        if(world instanceof ServerWorld && entity instanceof ServerPlayerEntity) {
            if (!entity.isPassenger() && !entity.isVehicle() && entity.canChangeDimensions() && !PORTAL_COOLDOWN.containsKey(entity.getUUID())) {
                PORTAL_COOLDOWN.put(entity.getUUID(),80);

                ServerPlayerEntity playerEntity = (ServerPlayerEntity) entity;

                ServerWorld serverWorld = null;

                if(playerEntity.getLevel().dimension() == World.OVERWORLD) {
                    serverWorld = playerEntity.getServer().getLevel(Purpura.PURPURA_DIMENSION);
                } else {
                    serverWorld = playerEntity.getServer().getLevel(World.OVERWORLD);
                }

                if(serverWorld == null) {
                    return;
                }


                playerEntity.changeDimension(serverWorld, new ITeleporter() {
                    @Override
                    public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
                        return ITeleporter.super.placeEntity(entity, currentWorld, destWorld, yaw, repositionEntity);
                    }
                });


                //playerEntity.changeDimension(serverWorld, new PurpuraTeleporter(serverWorld));

                playerEntity.setRespawnPosition(Purpura.PURPURA_DIMENSION,playerEntity.blockPosition(),playerEntity.yRot,true,false);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
        if (p_180655_4_.nextInt(100) == 0) {
            p_180655_2_.playLocalSound((double)p_180655_3_.getX() + 0.5D, (double)p_180655_3_.getY() + 0.5D, (double)p_180655_3_.getZ() + 0.5D, SoundEvents.PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, p_180655_4_.nextFloat() * 0.4F + 0.8F, false);
        }

        for(int i = 0; i < 4; ++i) {
            double d0 = (double)p_180655_3_.getX() + p_180655_4_.nextDouble();
            double d1 = (double)p_180655_3_.getY() + p_180655_4_.nextDouble();
            double d2 = (double)p_180655_3_.getZ() + p_180655_4_.nextDouble();
            double d3 = ((double)p_180655_4_.nextFloat() - 0.5D) * 0.5D;
            double d4 = ((double)p_180655_4_.nextFloat() - 0.5D) * 0.5D;
            double d5 = ((double)p_180655_4_.nextFloat() - 0.5D) * 0.5D;
            int j = p_180655_4_.nextInt(2) * 2 - 1;
            if (!p_180655_2_.getBlockState(p_180655_3_.west()).is(this) && !p_180655_2_.getBlockState(p_180655_3_.east()).is(this)) {
                d0 = (double)p_180655_3_.getX() + 0.5D + 0.25D * (double)j;
                d3 = (double)(p_180655_4_.nextFloat() * 2.0F * (float)j);
            } else {
                d2 = (double)p_180655_3_.getZ() + 0.5D + 0.25D * (double)j;
                d5 = (double)(p_180655_4_.nextFloat() * 2.0F * (float)j);
            }

            p_180655_2_.addParticle(ParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
        }

    }
    @Override
    public ItemStack getCloneItemStack(IBlockReader p_185473_1_, BlockPos p_185473_2_, BlockState p_185473_3_) {
        return ItemStack.EMPTY;
    }
    @Override
    public BlockState rotate(BlockState p_185499_1_, Rotation p_185499_2_) {
        switch(p_185499_2_) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch((Direction.Axis)p_185499_1_.getValue(AXIS)) {
                    case Z:
                        return p_185499_1_.setValue(AXIS, Direction.Axis.X);
                    case X:
                        return p_185499_1_.setValue(AXIS, Direction.Axis.Z);
                    default:
                        return p_185499_1_;
                }
            default:
                return p_185499_1_;
        }
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(AXIS);
    }
}
