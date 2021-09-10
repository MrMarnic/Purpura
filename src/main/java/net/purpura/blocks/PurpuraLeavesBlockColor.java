package net.purpura.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.biome.BiomeColors;

import javax.annotation.Nullable;

/**
 * Copyright (c) 10.09.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class PurpuraLeavesBlockColor implements IBlockColor {
    @Override
    public int getColor(BlockState p_getColor_1_, @Nullable IBlockDisplayReader displayReader, @Nullable BlockPos blockPos, int p_getColor_4_) {
        return 13123530;
    }
}
