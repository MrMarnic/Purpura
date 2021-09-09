package net.purpura.events;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.PortalSize;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemTier;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.purpura.Purpura;
import net.purpura.blocks.PurpuraPortalBlock;

import java.util.Optional;

/**
 * Copyright (c) 08.09.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */

@Mod.EventBusSubscriber
public class PurpuraEvents {

    @SubscribeEvent
    public static void playerTickEvent(TickEvent.PlayerTickEvent e) {
        if(e.side == LogicalSide.SERVER) {
            if(PurpuraPortalBlock.PORTAL_COOLDOWN.containsKey(e.player.getUUID())) {
                int ticks = PurpuraPortalBlock.PORTAL_COOLDOWN.get(e.player.getUUID());
                PurpuraPortalBlock.PORTAL_COOLDOWN.put(e.player.getUUID(),ticks-1);
                if(ticks <= 0) {
                    PurpuraPortalBlock.PORTAL_COOLDOWN.remove(e.player.getUUID());
                }
            }
        }
    }

    @SubscribeEvent
    public static void blockPlacedEvent(BlockEvent.EntityPlaceEvent e) {
        if(e.getEntity() instanceof ServerPlayerEntity) {
            if(e.getPlacedBlock().getBlock() instanceof AbstractFireBlock) {
                if(e.getEntity().level.getBlockState(e.getPos().below()).getBlock() == Purpura.PURPURIUM_BLOCK.get()) {
                    Optional<PortalSize> optional = PortalSize.findEmptyPortalShape(e.getWorld(), e.getPos(), Direction.Axis.X);
                    System.out.println(optional.isPresent());
                }
            }
        }
    }
}
