package net.purpura.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.trees.Tree;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BoneMealItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;
import net.purpura.Purpura;
import net.purpura.tree.PurpuraTree;

import java.util.Random;

/**
 * Copyright (c) 10.09.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class PurpuraSapling extends SaplingBlock {
    public PurpuraSapling() {
        super(new PurpuraTree(), AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS));
    }

    @Override
    protected boolean mayPlaceOn(BlockState p_200014_1_, IBlockReader p_200014_2_, BlockPos p_200014_3_) {
        return super.mayPlaceOn(p_200014_1_,p_200014_2_,p_200014_3_) || p_200014_1_.getBlock() == Purpura.PURPURRACK_GRASS.get();
    }

}
