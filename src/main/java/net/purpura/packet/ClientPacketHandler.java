package net.purpura.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import net.purpura.blocks.DayChangerTileEntity;

import java.util.function.Supplier;

/**
 * Copyright (c) 09.09.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class ClientPacketHandler {
    public static void handle(PacketStartDayChangerAnimation packet, Supplier<NetworkEvent.Context> ctx) {
        PlayerEntity player = Minecraft.getInstance().player;
        World world = player.level;

        if(!packet.error) {
            TileEntity tileEntity = world.getBlockEntity(packet.pos);

            if(tileEntity != null) {
                DayChangerTileEntity dayChangerTileEntity = (DayChangerTileEntity) tileEntity;

                dayChangerTileEntity.animationStage = 1;
                dayChangerTileEntity.progressClient = 0;
            }
        }
    }
}
