package net.purpura.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.material.Material;
import net.minecraft.command.impl.WeatherCommand;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

/**
 * Copyright (c) 08.09.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class DayChangerBlock extends Block {

    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    public DayChangerBlock() {
        super(Properties.of(Material.METAL));
    }

    @Override
    public ActionResultType use(BlockState p_225533_1_, World world, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand hand, BlockRayTraceResult p_225533_6_) {
        if(hand == Hand.MAIN_HAND) {
            if(!world.isClientSide()) {
                ServerWorld serverWorld = (ServerWorld) world;

                if(serverWorld.getDayTime() >= 13000) {
                    serverWorld.setDayTime(1000);
                } else {
                    serverWorld.setDayTime(13000);
                }
            }
        }
        return super.use(p_225533_1_, world, p_225533_3_, p_225533_4_, hand, p_225533_6_);
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
}
