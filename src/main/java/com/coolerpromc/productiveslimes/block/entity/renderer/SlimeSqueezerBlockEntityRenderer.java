package com.coolerpromc.productiveslimes.block.entity.renderer;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.block.custom.SlimeSqueezerBlock;
import com.coolerpromc.productiveslimes.block.entity.SlimeSqueezerBlockEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.Random;

public class SlimeSqueezerBlockEntityRenderer extends TileEntityRenderer<SlimeSqueezerBlockEntity> {
    public SlimeSqueezerBlockEntityRenderer(TileEntityRendererDispatcher context) {
        super(context);
    }

    @Override
    public void render(SlimeSqueezerBlockEntity blockEntity, float partialTick, MatrixStack poseStack, IRenderTypeBuffer buffer, int light, int overlay) {
        IBakedModel squeezer = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getBlockModel(ModBlocks.SQUEEZER.get().defaultBlockState());
        float progressRatio = (float) blockEntity.getData().get(0) / (float) blockEntity.getData().get(1);
        if (progressRatio == 0.0 && blockEntity.getData().get(0) != 0) return;
        float startPoint = 0.8f;
        float endPoint = 0.15f;
        float squeezerPosition = startPoint - ((startPoint - endPoint) * progressRatio);
        float x1 = 0, x2 = 0, y1 = 0, y2 = 0, z1 = 0, z2 = 0;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack inputItem = blockEntity.getInputStack();
        ItemStack outputItem1 = blockEntity.getOutputStack(0);
        ItemStack outputItem2 = blockEntity.getOutputStack(1);
        Direction facing = blockEntity.getBlockState().getValue(SlimeSqueezerBlock.FACING);
        // Render the squeezer
        poseStack.pushPose();
        poseStack.translate(0, squeezerPosition, 0);
        renderModel(squeezer, poseStack, buffer, light, overlay);
        poseStack.popPose();
        // Render the input item
        poseStack.pushPose();
        poseStack.translate(0.5f, 0.09, 0.5f);
        poseStack.scale(0.35f, 0.35f, 0.35f);
        poseStack.mulPose(Vector3f.XP.rotationDegrees(270));
        itemRenderer.renderStatic(inputItem, ItemCameraTransforms.TransformType.FIXED, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, buffer);
        poseStack.popPose();
        switch (facing) {
            case SOUTH:
                x1 = 0.0625f;
                x2 = 0.9375f;
                y1 = 0.175f;
                y2 = 0.175f;
                z1 = 0.5f;
                z2 = 0.5f;
                break;
            case NORTH:
                x1 = 0.9375f;
                x2 = 0.0625f;
                y1 = 0.175f;
                y2 = 0.175f;
                z1 = 0.5f;
                z2 = 0.5f;
                break;
            case EAST:
                x1 = 0.5f;
                x2 = 0.5f;
                y1 = 0.175f;
                y2 = 0.175f;
                z1 = 0.9375f;
                z2 = 0.0625f;
                break;
            case WEST:
                x1 = 0.5f;
                x2 = 0.5f;
                y1 = 0.175f;
                y2 = 0.175f;
                z1 = 0.0625f;
                z2 = 0.9375f;
                break;
        }
        // Render the output item 1
        poseStack.pushPose();
        poseStack.translate(x1, y1, z1);
        poseStack.scale(0.15f, 0.15f, 0.15f);
        poseStack.mulPose(Vector3f.XP.rotationDegrees(270));
        itemRenderer.renderStatic(outputItem1, ItemCameraTransforms.TransformType.FIXED, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, buffer);
        poseStack.popPose();
        // Render the output item 2
        poseStack.pushPose();
        poseStack.translate(x2, y2, z2);
        poseStack.scale(0.15f, 0.15f, 0.15f);
        poseStack.mulPose(Vector3f.XP.rotationDegrees(270));
        itemRenderer.renderStatic(outputItem2, ItemCameraTransforms.TransformType.FIXED, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, buffer);
        poseStack.popPose();
    }

    private void renderModel(IBakedModel model, MatrixStack poseStack, IRenderTypeBuffer buffer, int light, int overlay) {
        Random rand = new Random();
        for (Direction direction : Direction.values()) {
            rand.setSeed(42L);
            Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(
                    poseStack.last(),
                    buffer.getBuffer(RenderType.cutout()),
                    null,
                    model,
                    1.0F, 1.0F, 1.0F,
                    light,
                    overlay
            );
        }
    }

    private int getLightLevel(World level, BlockPos pos) {
        int bLight = level.getBrightness(LightType.BLOCK, pos);
        int sLight = level.getBrightness(LightType.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}