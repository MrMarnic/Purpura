package net.purpura.blocks;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Block;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

/**
 * Copyright (c) 08.09.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class DayChangerTileEntityRenderer extends TileEntityRenderer<DayChangerTileEntity> {

    private ItemStack watch;

    public DayChangerTileEntityRenderer(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
        this.watch = new ItemStack(Items.CLOCK);
    }

    @Override
    public void render(DayChangerTileEntity tileEntity, float p_225616_2_, MatrixStack stack, IRenderTypeBuffer buffer, int combinedLightIn, int p_225616_6_) {
        stack.pushPose();
        World level = tileEntity.getLevel();
        IBakedModel model = Minecraft.getInstance().getItemRenderer().getModel(this.watch,level,Minecraft.getInstance().player);
        stack.scale(0.8f,0.8f,0.8f);
        stack.translate(0.5,0.9,0.5);
        stack.mulPose(Vector3f.XP.rotationDegrees(90.0f));
        Minecraft.getInstance().getItemRenderer().render(watch, ItemCameraTransforms.TransformType.FIXED,true,stack,buffer,combinedLightIn,p_225616_6_,model);
        stack.popPose();
    }
}
