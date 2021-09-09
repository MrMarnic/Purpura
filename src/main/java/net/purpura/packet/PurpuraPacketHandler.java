package net.purpura.packet;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.purpura.Purpura;

/**
 * Copyright (c) 09.09.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class PurpuraPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Purpura.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void registerPackets() {
        INSTANCE.registerMessage(0,PacketStartDayChangerAnimation.class,PacketStartDayChangerAnimation::encode,PacketStartDayChangerAnimation::decode,PacketStartDayChangerAnimation::handle);
    }
}
