package com.coolerpromc.productiveslimes.block.entity.renderer;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.block.custom.SlimeSqueezerBlock;
import com.coolerpromc.productiveslimes.block.entity.SlimeSqueezerBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class SlimeSqueezerBlockEntityRenderer implements BlockEntityRenderer<SlimeSqueezerBlockEntity> {
    public SlimeSqueezerBlockEntityRenderer(BlockEntityRendererProvider.Context context){
    }
    @Override
    public void render(SlimeSqueezerBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
        var squeezer = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getBlockModel(ModBlocks.SQUEEZER.get().defaultBlockState());
        float progressRatio = (float) blockEntity.getData().get(0) / (float) blockEntity.getData().get(1);
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
        itemRenderer.renderStatic(inputItem, ItemTransforms.TransformType.FIXED, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, buffer, 1);
        poseStack.popPose();
        switch (facing){
            case SOUTH:
                x1 = 0.0625f; x2 = 0.9375f; y1 = 0.175f; y2 = 0.175f; z1 = 0.5f; z2 = 0.5f;
                break;
            case NORTH:
                x1 = 0.9375f; x2 = 0.0625f; y1 = 0.175f; y2 = 0.175f; z1 = 0.5f; z2 = 0.5f;
                break;
            case EAST:
                x1 = 0.5f; x2 = 0.5f; y1 = 0.175f; y2 = 0.175f; z1 = 0.9375f; z2 = 0.0625f;
                break;
            case WEST:
                x1 = 0.5f; x2 = 0.5f; y1 = 0.175f; y2 = 0.175f; z1 = 0.0625f; z2 = 0.9375f;
                break;
        }
        // Render the output item 1
        poseStack.pushPose();
        poseStack.translate(x1, y1, z1);
        poseStack.scale(0.15f, 0.15f, 0.15f);
        poseStack.mulPose(Vector3f.XP.rotationDegrees(270));
        itemRenderer.renderStatic(outputItem1, ItemTransforms.TransformType.FIXED, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, buffer, 1);
        poseStack.popPose();
        // Render the output item 2
        poseStack.pushPose();
        poseStack.translate(x2, y2, z2);
        poseStack.scale(0.15f, 0.15f, 0.15f);
        poseStack.mulPose(Vector3f.XP.rotationDegrees(270));
        itemRenderer.renderStatic(outputItem2, ItemTransforms.TransformType.FIXED, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, buffer, 1);
        poseStack.popPose();
    }
    private void renderModel(BakedModel model, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
        RandomSource rand = RandomSource.create();
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
    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}