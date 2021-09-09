package net.purpura.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.purpura.Purpura;

/**
 * Copyright (c) 08.09.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class DayChangerTileEntity extends TileEntity {

    public float[] height;
    public float[] rot;
    public float[] rY;
    public float[] mov;
    public int animationStage = 0;

    public DayChangerTileEntity() {
        super(Purpura.DAY_CHANGER_TILE_ENTITY.get());
        this.height = new float[] {0f,0f,0f,0f};
        this.rot = new float[] {0f,0f,0f,0f};
        this.rY = new float[] {0f,0f,0f,0f};
        this.mov = new float[] {0f,0f,0f,0f};
    }
}
