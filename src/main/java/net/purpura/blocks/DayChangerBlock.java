package net.purpura.blocks;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.material.Material;
import net.minecraft.command.impl.WeatherCommand;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.PacketDistributor;
import net.purpura.Purpura;
import net.purpura.packet.PacketStartDayChangerAnimation;
import net.purpura.packet.PurpuraPacketHandler;

import javax.annotation.Nullable;

/**
 * Copyright (c) 08.09.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class DayChangerBlock extends Block {

    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    public DayChangerBlock() {
        super(Properties.of(Material.METAL).strength(5, 6).requiresCorrectToolForDrops());
    }

    @Override
    public ActionResultType use(BlockState p_225533_1_, World world, BlockPos p_225533_3_, PlayerEntity player, Hand hand, BlockRayTraceResult p_225533_6_) {
        if(hand == Hand.MAIN_HAND) {
            if(!world.isClientSide()) {

                if(!player.isShiftKeyDown()) {
                    if(player.inventory.hasAnyOf(Sets.newHashSet(Purpura.SOLARIUM.get()))) {
                        DayChangerTileEntity tileEntity = (DayChangerTileEntity) world.getBlockEntity(p_225533_3_);

                        if(!tileEntity.hasAllSolarium()) {

                            int slot = findSlotMatchingItem(Purpura.SOLARIUM.get(),player);

                            if(slot != -1) {
                                ItemStack stack = player.inventory.getItem(slot);
                                stack.setCount(stack.getCount()-1);

                                player.inventory.setItem(slot,stack);
                            }

                            tileEntity.addSolarium();
                            ((ServerWorld)world).sendBlockUpdated(p_225533_3_,p_225533_1_,p_225533_1_,2);
                        } else if(!Purpura.DAY_CHANGER_USED.containsKey(world.dimensionType())){
                            tileEntity.changeTime = true;

                            if(world.getDayTime() >= 13000) {
                                tileEntity.night = true;
                            } else {
                                tileEntity.night = false;
                            }

                            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;

                            PurpuraPacketHandler.INSTANCE.sendTo(new PacketStartDayChangerAnimation(false,p_225533_3_),serverPlayerEntity.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
                        } else {
                            player.sendMessage(new TranslationTextComponent("text.purpura.day_changer_used"), Util.NIL_UUID);
                        }
                    } else {
                        player.sendMessage(new TranslationTextComponent("text.purpura.day_changer_no_solarium"), Util.NIL_UUID);
                    }
                } else{
                    DayChangerTileEntity tileEntity = (DayChangerTileEntity) world.getBlockEntity(p_225533_3_);

                    if(tileEntity.hasSomeSolarium()) {
                        tileEntity.removeSolarium();
                        player.inventory.add(new ItemStack(Purpura.SOLARIUM.get()));
                        ((ServerWorld)world).sendBlockUpdated(p_225533_3_,p_225533_1_,p_225533_1_,2);
                    }
                }
            }
        }
        return super.use(p_225533_1_, world, p_225533_3_, player, hand, p_225533_6_);
    }

    public int findSlotMatchingItem(Item item, PlayerEntity entity) {
        for(int i = 0; i < entity.inventory.items.size(); ++i) {
            if (entity.inventory.items.get(i).getItem() == item) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new DayChangerTileEntity();
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState p_220074_1_) {
        return true;
    }

    @Override
    public void onRemove(BlockState p_196243_1_, World world, BlockPos pos, BlockState p_196243_4_, boolean p_196243_5_) {
        if(!world.isClientSide()) {
            DayChangerTileEntity tileEntity = (DayChangerTileEntity) world.getBlockEntity(pos);

            if(tileEntity.isUse) {
                Purpura.DAY_CHANGER_USED.remove(world.dimensionType());
            } else {
                if(tileEntity.solarium[0]) {
                    Block.popResource(world,pos,new ItemStack(Purpura.SOLARIUM.get()));
                }
                if(tileEntity.solarium[1]) {
                    Block.popResource(world,pos,new ItemStack(Purpura.SOLARIUM.get()));
                }
                if(tileEntity.solarium[2]) {
                    Block.popResource(world,pos,new ItemStack(Purpura.SOLARIUM.get()));
                }
                if(tileEntity.solarium[3]) {
                    Block.popResource(world,pos,new ItemStack(Purpura.SOLARIUM.get()));
                }
            }
        }
        super.onRemove(p_196243_1_, world, pos, p_196243_4_, p_196243_5_);
    }
}
