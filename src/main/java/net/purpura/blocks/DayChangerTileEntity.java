package net.purpura.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.ChunkEvent;
import net.purpura.Purpura;

import javax.annotation.Nullable;

/**
 * Copyright (c) 08.09.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class DayChangerTileEntity extends TileEntity implements ITickableTileEntity {

    public float[] height;
    public float[] rot;
    public float[] rY;
    public float[] mov;
    public float[] standardRot;
    public int animationStage = 0;
    public float rotSpeed = 2.0f;

    public int progress;
    public int progressClient;
    public int changingProgress;
    public boolean changeTime;
    public boolean night;
    public boolean changeTimeReal;
    public float timeDiff;
    public float realDivisor;
    public boolean isUse;

    public boolean[] solarium;

    public DayChangerTileEntity() {
        super(Purpura.DAY_CHANGER_TILE_ENTITY.get());
        this.height = new float[] {0f,0f,0f,0f};
        this.rot = new float[] {0f,0f,0f,0f};
        this.rY = new float[] {0f,0f,0f,0f};
        this.mov = new float[] {225f,45f,135f,315f};
        this.standardRot = new float[] {225f,45f,135f,315f};
        this.solarium = new boolean[] {false,false,false,false};
    }

    @Override
    public void tick() {
        if(!this.level.isClientSide()) {
            if(this.changeTime) {
                this.isUse = true;
                Purpura.DAY_CHANGER_USED.put(level.dimensionType(),true);
                progress +=1;

                if((progress/20)==2 && !changeTimeReal) {
                    ServerWorld serverWorld = (ServerWorld) level;

                    changeTimeReal = true;
                    if(night) {
                        this.timeDiff = serverWorld.getDayTime() - 1000;
                    } else {
                        this.timeDiff = 13000 - serverWorld.getDayTime();
                    }
                    this.realDivisor = this.timeDiff / 8f;
                }

                if(changeTimeReal) {
                    changingProgress++;

                    ServerWorld serverWorld = (ServerWorld) level;

                    if(changingProgress%10==0) {
                        if(night) {
                            serverWorld.setDayTime((long) (serverWorld.getDayTime() - realDivisor));
                        } else {
                            serverWorld.setDayTime((long) (serverWorld.getDayTime() + realDivisor));
                        }
                        timeDiff -= realDivisor;
                    }

                    if((changingProgress/20)==4) {
                        this.changeTime = false;
                        this.changeTimeReal = false;
                        this.timeDiff = 0;
                        this.progress = 0;
                        this.realDivisor = 0;
                        this.changingProgress = 0;
                        this.isUse = false;
                        Purpura.DAY_CHANGER_USED.remove(level.dimensionType());
                        this.solarium = new boolean[] {false,false,false,false};
                        serverWorld.sendBlockUpdated(getBlockPos(),getBlockState(),getBlockState(),2);
                    }
                }
            }
        } else {
            progressClient +=1;
            if((progressClient/20)==6) {
                this.animationStage = 0;
                this.height = new float[] {0f,0f,0f,0f};
                this.rot = new float[] {0f,0f,0f,0f};
                this.rY = new float[] {0f,0f,0f,0f};
                this.mov = new float[] {225f,45f,135f,315f};
                this.standardRot = new float[] {225f,45f,135f,315f};
                this.rotSpeed = 2.0f;
            }
        }
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT tag) {
        this.solarium[0] = tag.getBoolean("solarium0");
        this.solarium[1] = tag.getBoolean("solarium1");
        this.solarium[2] = tag.getBoolean("solarium2");
        this.solarium[3] = tag.getBoolean("solarium3");
        super.load(p_230337_1_, tag);

    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        tag.putBoolean("solarium0",solarium[0]);
        tag.putBoolean("solarium1",solarium[1]);
        tag.putBoolean("solarium2",solarium[2]);
        tag.putBoolean("solarium3",solarium[3]);
        return super.save(tag);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT tag = super.getUpdateTag();
        tag.putBoolean("solarium0",solarium[0]);
        tag.putBoolean("solarium1",solarium[1]);
        tag.putBoolean("solarium2",solarium[2]);
        tag.putBoolean("solarium3",solarium[3]);
        return tag;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.solarium[0] = pkt.getTag().getBoolean("solarium0");
        this.solarium[1] = pkt.getTag().getBoolean("solarium1");
        this.solarium[2] = pkt.getTag().getBoolean("solarium2");
        this.solarium[3] = pkt.getTag().getBoolean("solarium3");
        super.onDataPacket(net, pkt);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbtTag = new CompoundNBT();
        nbtTag.putBoolean("solarium0",solarium[0]);
        nbtTag.putBoolean("solarium1",solarium[1]);
        nbtTag.putBoolean("solarium2",solarium[2]);
        nbtTag.putBoolean("solarium3",solarium[3]);
        return new SUpdateTileEntityPacket(getBlockPos(), -1, nbtTag);
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        this.solarium[0] = tag.getBoolean("solarium0");
        this.solarium[1] = tag.getBoolean("solarium1");
        this.solarium[2] = tag.getBoolean("solarium2");
        this.solarium[3] = tag.getBoolean("solarium3");
    }

    public boolean hasAllSolarium() {
        return solarium[0] && solarium[1] && solarium[2] && solarium[3];
    }

    public boolean hasSomeSolarium() {
        return solarium[0] || solarium[1] || solarium[2] || solarium[3];
    }

    public void removeSolarium() {
        if(solarium[0]) {
            solarium[0] = false;
        } else
        if(solarium[1]) {
            solarium[1] = false;
        } else
        if(solarium[2]) {
            solarium[2] = false;
        } else
        if(solarium[3]) {
            solarium[3] = false;
        }
    }

    public void addSolarium() {
        if(!solarium[0]) {
            solarium[0] = true;
        } else
        if(!solarium[1]) {
            solarium[1] = true;
        } else
        if(!solarium[2]) {
            solarium[2] = true;
        } else
        if(!solarium[3]) {
            solarium[3] = true;
        }
    }
}
