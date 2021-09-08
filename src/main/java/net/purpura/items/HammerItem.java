package net.purpura.items;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.world.BlockEvent;
import net.purpura.Purpura;
import net.purpura.armor.PurpuraItemTier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Copyright (c) 07.09.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class HammerItem extends ToolItem {

    private static final Set<Block> DIGGABLES = ImmutableSet.of(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.POWERED_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.NETHER_GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.BLUE_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.GRANITE, Blocks.POLISHED_GRANITE, Blocks.DIORITE, Blocks.POLISHED_DIORITE, Blocks.ANDESITE, Blocks.POLISHED_ANDESITE, Blocks.STONE_SLAB, Blocks.SMOOTH_STONE_SLAB, Blocks.SANDSTONE_SLAB, Blocks.PETRIFIED_OAK_SLAB, Blocks.COBBLESTONE_SLAB, Blocks.BRICK_SLAB, Blocks.STONE_BRICK_SLAB, Blocks.NETHER_BRICK_SLAB, Blocks.QUARTZ_SLAB, Blocks.RED_SANDSTONE_SLAB, Blocks.PURPUR_SLAB, Blocks.SMOOTH_QUARTZ, Blocks.SMOOTH_RED_SANDSTONE, Blocks.SMOOTH_SANDSTONE, Blocks.SMOOTH_STONE, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE, Blocks.POLISHED_GRANITE_SLAB, Blocks.SMOOTH_RED_SANDSTONE_SLAB, Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.POLISHED_DIORITE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.END_STONE_BRICK_SLAB, Blocks.SMOOTH_SANDSTONE_SLAB, Blocks.SMOOTH_QUARTZ_SLAB, Blocks.GRANITE_SLAB, Blocks.ANDESITE_SLAB, Blocks.RED_NETHER_BRICK_SLAB, Blocks.POLISHED_ANDESITE_SLAB, Blocks.DIORITE_SLAB, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.PISTON, Blocks.STICKY_PISTON, Blocks.PISTON_HEAD);


    public HammerItem() {
        super(2,3f, PurpuraItemTier.TETRAEDIT_KUNZIT, DIGGABLES, new Properties().tab(Purpura.PURPURA_ITEMS));
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, PlayerEntity player) {
        List<BlockPos> toBreak = new ArrayList<>();

        if((player.getDirection() == Direction.NORTH | player.getDirection() == Direction.SOUTH) && (player.getLookAngle().y >= -0.6 && player.getLookAngle().y <= 0.6)) {
            toBreak.addAll(Arrays.asList(pos.east(),pos.west(),pos.above(),pos.above().east(),pos.above().west(),pos.below(),pos.below().east(),pos.below().west()));
        } else if((player.getDirection() == Direction.WEST | player.getDirection() == Direction.EAST) && (player.getLookAngle().y >= -0.6 && player.getLookAngle().y <= 0.6)) {
            toBreak.addAll(Arrays.asList(pos.north(),pos.south(),pos.above(),pos.above().north(),pos.above().south(),pos.below(),pos.below().north(),pos.below().south()));
        } else if(player.getLookAngle().y < -0.6 | player.getLookAngle().y > 0.6) {
            toBreak.addAll(Arrays.asList(pos.east(),pos.west(),pos.north().east(),pos.north().west(),pos.north(),pos.south().west(),pos.south().east(),pos.south()));
        }

        for(BlockPos position : toBreak) {
            BlockState state = player.level.getBlockState(position);
            player.level.removeBlock(position,true);
            if(!player.isCreative()) {
                Block.dropResources(state,player.level,position);
            }
        }
        return super.onBlockStartBreak(itemstack, pos, player);
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState p_150897_1_) {
        int i = this.getTier().getLevel();
        if (p_150897_1_.getHarvestTool() == net.minecraftforge.common.ToolType.PICKAXE) {
            return i >= p_150897_1_.getHarvestLevel();
        }
        Material material = p_150897_1_.getMaterial();
        return material == Material.STONE || material == Material.METAL || material == Material.HEAVY_METAL;
    }
}
