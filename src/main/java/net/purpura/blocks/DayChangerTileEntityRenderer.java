package net.purpura.blocks;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.purpura.Purpura;

/**
 * Copyright (c) 08.09.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class DayChangerTileEntityRenderer extends TileEntityRenderer<DayChangerTileEntity> {

    private ItemStack watch;
    private ItemStack solarium;

    public DayChangerTileEntityRenderer(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
        this.watch = new ItemStack(Items.CLOCK);
        this.solarium = new ItemStack(Purpura.SOLARIUM.get());
    }

    @Override
    public void render(DayChangerTileEntity tileEntity, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int combinedLightIn, int p_225616_6_) {
        World level = tileEntity.getLevel();

        stack.pushPose();
        IBakedModel model = Minecraft.getInstance().getItemRenderer().getModel(this.watch,level,Minecraft.getInstance().player);
        stack.translate(0.5,0.77,0.5);
        stack.scale(0.75f,0.75f,0.75f);
        stack.mulPose(Vector3f.XP.rotationDegrees(90.0f));
        Minecraft.getInstance().getItemRenderer().render(watch, ItemCameraTransforms.TransformType.FIXED,true,stack,buffer,combinedLightIn,p_225616_6_,model);
        stack.popPose();

        if(tileEntity.solarium[0]) {
            renderSolarium(tileEntity,0.1f,0.1f,level,stack,buffer,combinedLightIn,p_225616_6_,-1.0f,0);
        }
        if(tileEntity.solarium[1]) {
            renderSolarium(tileEntity,0.9f,0.9f,level,stack,buffer,combinedLightIn,p_225616_6_,-1.0f,1);
        }
        if(tileEntity.solarium[2]) {
            renderSolarium(tileEntity,0.9f,0.1f,level,stack,buffer,combinedLightIn,p_225616_6_,1.0f,2);
        }
        if(tileEntity.solarium[3]) {
            renderSolarium(tileEntity,0.1f,0.9f,level,stack,buffer,combinedLightIn,p_225616_6_,1.0f,3);
        }
    }

    private void renderSolarium(DayChangerTileEntity tileEntity,float posX, float posZ, World level,MatrixStack stack, IRenderTypeBuffer buffer, int combinedLightIn, int p_225616_6_, float inverseRot,int index) {
        if(tileEntity.animationStage == 0) {
            stack.pushPose();
            IBakedModel model2 = Minecraft.getInstance().getItemRenderer().getModel(this.solarium,level,Minecraft.getInstance().player);
            stack.translate(posX,0.77,posZ);
            stack.scale(0.35f,0.35f,0.35f);
            stack.mulPose(Vector3f.XP.rotationDegrees(90.0f));
            Minecraft.getInstance().getItemRenderer().render(solarium, ItemCameraTransforms.TransformType.FIXED,true,stack,buffer,combinedLightIn,p_225616_6_,model2);
            stack.popPose();
        } else if(tileEntity.animationStage == 1) {

            int finished = 0;

            if (tileEntity.height[index] <= 0.4) {
                tileEntity.height[index] += Minecraft.getInstance().getDeltaFrameTime() * 0.01;
            } else {
                tileEntity.height[index] = 0.4f;
                finished++;
            }

            if(tileEntity.rot[index] < 90.0) {
                tileEntity.rot[index] += Minecraft.getInstance().getDeltaFrameTime() * 2.2;
            } else {
                tileEntity.rot[index] = 90.0f;
                finished++;
            }

            if(tileEntity.rY[index] < 45.0f) {
                tileEntity.rY[index] += Minecraft.getInstance().getDeltaFrameTime() * 2.0;
            } else {
                tileEntity.rY[index] = 45.0f;
                finished++;
            }

            if(finished==3) {
                tileEntity.animationStage = 2;
            }

            stack.pushPose();
            IBakedModel model2 = Minecraft.getInstance().getItemRenderer().getModel(this.solarium,level,Minecraft.getInstance().player);
            stack.translate(posX,0.77 + tileEntity.height[index],posZ);
            stack.scale(0.35f,0.35f,0.35f);
            stack.mulPose(Vector3f.XP.rotationDegrees(90.0f - tileEntity.rot[index]));
            stack.mulPose(Vector3f.YP.rotationDegrees(-tileEntity.rY[index] * inverseRot));
            Minecraft.getInstance().getItemRenderer().render(solarium, ItemCameraTransforms.TransformType.FIXED,true,stack,buffer,combinedLightIn,p_225616_6_,model2);
            stack.popPose();
        } else if(tileEntity.animationStage==2) {
            stack.pushPose();
            IBakedModel model2 = Minecraft.getInstance().getItemRenderer().getModel(this.solarium,level,Minecraft.getInstance().player);
            stack.translate(0.5 + Math.sin(Math.toRadians(tileEntity.mov[index])) * 0.5656,0.77 + tileEntity.height[index],0.5 + Math.cos(Math.toRadians(tileEntity.mov[index])) * 0.5656);
            stack.scale(0.35f,0.35f,0.35f);
            stack.mulPose(Vector3f.XP.rotationDegrees(90.0f - tileEntity.rot[index]));
            stack.mulPose(Vector3f.YP.rotationDegrees(tileEntity.mov[index] - tileEntity.standardRot[index] -tileEntity.rY[index] * inverseRot));

            Minecraft.getInstance().getItemRenderer().render(solarium, ItemCameraTransforms.TransformType.FIXED,true,stack,buffer,combinedLightIn,p_225616_6_,model2);
            stack.popPose();

            if(tileEntity.mov[index] <= 360.0) {
                tileEntity.mov[index] += Minecraft.getInstance().getDeltaFrameTime() * tileEntity.rotSpeed;
            } else {
                tileEntity.mov[index] = 0.0f;
                tileEntity.mov[index] += Minecraft.getInstance().getDeltaFrameTime() * tileEntity.rotSpeed;
            }

            tileEntity.rotSpeed += Minecraft.getInstance().getDeltaFrameTime() * 0.1;
        }
    }
}
