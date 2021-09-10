package net.purpura.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.biome.BiomeColors;
import net.purpura.Purpura;

import javax.annotation.Nullable;

/**
 * Copyright (c) 10.09.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class PurpuraLeavesItemColor implements IItemColor {

    @Override
    public int getColor(ItemStack stack, int p_getColor_2_) {
        BlockState blockstate = ((BlockItem)stack.getItem()).getBlock().defaultBlockState();
        return Purpura.PURPURA_LEAVES_COLOR.getColor(blockstate, (IBlockDisplayReader)null, (BlockPos)null, p_getColor_2_);
    }
}
