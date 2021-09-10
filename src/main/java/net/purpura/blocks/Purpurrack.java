package net.purpura.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.ToolType;
import net.purpura.Purpura;

import java.util.List;
import java.util.Random;

/**
 * Copyright (c) 10.09.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class Purpurrack extends Block {
    public Purpurrack() {
        super(AbstractBlock.Properties.of(Material.STONE).strength(2, 6).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE));
    }
}
