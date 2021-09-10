package net.purpura.events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.purpura.Purpura;
import net.purpura.blocks.PurpuraLeavesBlockColor;
import net.purpura.blocks.PurpuraLeavesItemColor;

/**
 * Copyright (c) 10.09.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
@Mod.EventBusSubscriber(modid = "purpura", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PurpuraForgeEvents {
    @SubscribeEvent
    public static void colorHandleEventBlock(ColorHandlerEvent.Block e) {
        e.getBlockColors().register(Purpura.PURPURA_LEAVES_COLOR, Purpura.PURPURA_LEAVES.get());
    }
    @SubscribeEvent
    public static void colorHandleEventItem(ColorHandlerEvent.Item e) {
        e.getItemColors().register(new PurpuraLeavesItemColor(), Purpura.PURPURA_LEAVES_ITEM.get());
    }
}
