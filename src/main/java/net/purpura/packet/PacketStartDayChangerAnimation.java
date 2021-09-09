package net.purpura.packet;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Copyright (c) 09.09.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class PacketStartDayChangerAnimation{
    public boolean error;
    public BlockPos pos;

    public PacketStartDayChangerAnimation(boolean error,BlockPos pos) {
        this.error = error;
        this.pos = pos;
    }

    public static void encode(PacketStartDayChangerAnimation packet, PacketBuffer buffer) {
        buffer.writeBoolean(packet.error);
        buffer.writeBlockPos(packet.pos);
    }

    public static PacketStartDayChangerAnimation decode(PacketBuffer buffer) {
        boolean error = buffer.readBoolean();
        BlockPos pos = buffer.readBlockPos();
        return new PacketStartDayChangerAnimation(error,pos);
    }

    public static void handle(PacketStartDayChangerAnimation packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handle(packet, ctx));
        });
        ctx.get().setPacketHandled(true);
    }
}
