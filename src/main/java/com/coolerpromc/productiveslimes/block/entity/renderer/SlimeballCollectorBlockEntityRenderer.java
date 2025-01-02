package com.coolerpromc.productiveslimes.block.entity.renderer;

import com.coolerpromc.productiveslimes.block.entity.SlimeballCollectorBlockEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class SlimeballCollectorBlockEntityRenderer implements BlockEntityRenderer<SlimeballCollectorBlockEntity> {
    public SlimeballCollectorBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {
    }

    @Override
    public void render(SlimeballCollectorBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.getLevel() == null) return;
        if (blockEntity.getData().get(0) == 0) return;

        int rangeXZ = 8;
        int rangeY = 256;
        AABB collectionArea = new AABB(
                blockEntity.getBlockPos().getX() - rangeXZ, -64, blockEntity.getBlockPos().getZ() - rangeXZ,
                blockEntity.getBlockPos().getX() + rangeXZ + 1, rangeY, blockEntity.getBlockPos().getZ() + rangeXZ + 1
        );

        poseStack.pushPose();
        poseStack.translate(-blockEntity.getBlockPos().getX(), -blockEntity.getBlockPos().getY(), -blockEntity.getBlockPos().getZ());

        renderOutline(poseStack, bufferSource, collectionArea);
        poseStack.popPose();
    }

    private void renderOutline(PoseStack poseStack, MultiBufferSource bufferSource, AABB aabb) {
        var buffer = bufferSource.getBuffer(RenderType.lines());

        RenderSystem.enableDepthTest();
        RenderSystem.lineWidth(3.0f);
        RenderSystem.disableCull();

        drawBox(poseStack, buffer, aabb, 1.0f, 0.2f, 0.2f, 1.0f);

        RenderSystem.lineWidth(1.0f);
        RenderSystem.enableCull();
    }


    private void renderGrid(PoseStack poseStack, VertexConsumer buffer, AABB box, float red, float green, float blue, float alpha) {
        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix = pose.pose();

        int chunkSize = 16;

        for (double x = Math.ceil(box.minX / chunkSize) * chunkSize; x < box.maxX; x += chunkSize) {
            drawLine(matrix, buffer, x, box.minY, box.minZ, x, box.maxY, box.minZ, red, green, blue, alpha); // Vertical lines.
        }
        for (double z = Math.ceil(box.minZ / chunkSize) * chunkSize; z < box.maxZ; z += chunkSize) {
            drawLine(matrix, buffer, box.minX, box.minY, z, box.minX, box.maxY, z, red, green, blue, alpha); // Horizontal lines.
        }
    }

    private void drawBox(PoseStack poseStack, VertexConsumer buffer, AABB box, float red, float green, float blue, float alpha) {
        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix = pose.pose();
        Matrix3f normal = pose.normal();
        float x1 = (float) box.minX;
        float y1 = (float) box.minY;
        float z1 = (float) box.minZ;
        float x2 = (float) box.maxX;
        float y2 = (float) box.maxY;
        float z2 = (float) box.maxZ;

        drawLine(matrix, buffer, x1, y1, z1, x2, y1, z1, red, green, blue, alpha);
        drawLine(matrix, buffer, x1, y1, z1, x1, y2, z1, red, green, blue, alpha);
        drawLine(matrix, buffer, x1, y1, z1, x1, y1, z2, red, green, blue, alpha);
        drawLine(matrix, buffer, x2, y2, z2, x1, y2, z2, red, green, blue, alpha);
        drawLine(matrix, buffer, x2, y2, z2, x2, y1, z2, red, green, blue, alpha);
        drawLine(matrix, buffer, x2, y2, z2, x2, y2, z1, red, green, blue, alpha);
        drawLine(matrix, buffer, x1, y2, z2, x1, y1, z2, red, green, blue, alpha);
        drawLine(matrix, buffer, x1, y2, z2, x2, y2, z2, red, green, blue, alpha);
        drawLine(matrix, buffer, x2, y1, z1, x2, y2, z1, red, green, blue, alpha);
        drawLine(matrix, buffer, x2, y1, z1, x1, y1, z1, red, green, blue, alpha);
        drawLine(matrix, buffer, x2, y1, z1, x2, y1, z2, red, green, blue, alpha);
        drawLine(matrix, buffer, x1, y2, z1, x2, y2, z1, red, green, blue, alpha);
        drawLine(matrix, buffer, x1, y2, z1, x1, y2, z2, red, green, blue, alpha);
    }

    private void drawLine(Matrix4f matrix, VertexConsumer buffer, double x1, double y1, double z1, double x2, double y2, double z2, float red, float green, float blue, float alpha) {
        buffer.vertex(matrix, (float) x1, (float) y1, (float) z1).color(red, green, blue, alpha).normal(1.0f, 0.0f, 0.0f).endVertex();
        buffer.vertex(matrix, (float) x2, (float) y2, (float) z2).color(red, green, blue, alpha).normal(1.0f, 0.0f, 0.0f).endVertex();
    }
}