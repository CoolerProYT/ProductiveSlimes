package com.coolerpromc.productiveslimes.block.entity.renderer;

import com.coolerpromc.productiveslimes.block.entity.SlimeballCollectorBlockEntity;
import com.coolerpromc.productiveslimes.block.entity.renderstate.SlimeballCollectorBlockEntityRenderState;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class SlimeballCollectorBlockEntityRenderer implements BlockEntityRenderer<SlimeballCollectorBlockEntity, SlimeballCollectorBlockEntityRenderState> {
    public SlimeballCollectorBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {
    }

    @Override
    public void extractRenderState(SlimeballCollectorBlockEntity blockEntity, SlimeballCollectorBlockEntityRenderState renderState, float p_446851_, Vec3 p_445788_, @Nullable ModelFeatureRenderer.CrumblingOverlay p_446944_) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, p_446851_, p_445788_, p_446944_);
        renderState.blockEntity = blockEntity;
    }

    @Override
    public void submit(SlimeballCollectorBlockEntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState p_451022_) {
        SlimeballCollectorBlockEntity blockEntity = renderState.blockEntity;
        if (blockEntity.getLevel() == null) return;
        if (blockEntity.getData().get(0) == 0) return;
        // Define the collection area AABB (match this with your logic).
        int rangeXZ = 8; // Half of 16 blocks for X and Z.
        int rangeY = 256; // Full height.
        AABB collectionArea = new AABB(
                blockEntity.getBlockPos().getX() - rangeXZ, -64, blockEntity.getBlockPos().getZ() - rangeXZ,
                blockEntity.getBlockPos().getX() + rangeXZ + 1, rangeY, blockEntity.getBlockPos().getZ() + rangeXZ + 1
        );
        // Shift to world coordinates.
        poseStack.pushPose();
        poseStack.translate(-blockEntity.getBlockPos().getX(), -blockEntity.getBlockPos().getY(), -blockEntity.getBlockPos().getZ());
        // Render the outline box.
        renderOutline(poseStack, nodeCollector, collectionArea);
        poseStack.popPose();
    }

    private void renderOutline(PoseStack poseStack, SubmitNodeCollector nodeCollector, AABB aabb) {
        // Buffer for lines.
        RenderSystem.lineWidth(2.0f);
        // Render the outer box.
        nodeCollector.submitCustomGeometry(poseStack, RenderType.lines(), (pose, vertexConsumer) -> {
            drawBox(pose, vertexConsumer, aabb, 1.0f, 0.0f, 0.0f, 1.0f);
        });
        RenderSystem.lineWidth(1.0f);
    }

    private void drawBox(PoseStack.Pose pose, VertexConsumer buffer, AABB box, float red, float green, float blue, float alpha) {
        Matrix4f matrix = pose.pose();
        Matrix3f normal = pose.normal();
        float x1 = (float) box.minX;
        float y1 = (float) box.minY;
        float z1 = (float) box.minZ;
        float x2 = (float) box.maxX;
        float y2 = (float) box.maxY;
        float z2 = (float) box.maxZ;
        // Draw lines for the box.
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

    private void drawLine(Matrix4f matrix, VertexConsumer buffer,
                          double x1, double y1, double z1, double x2, double y2, double z2,
                          float red, float green, float blue, float alpha) {
        buffer.addVertex(matrix, (float) x1, (float) y1, (float) z1).setColor(red, green, blue, alpha).setUv(0, 0).setLight(0x00F000F0).setOverlay(OverlayTexture.NO_OVERLAY).setNormal(1, 0, 0);
        buffer.addVertex(matrix, (float) x2, (float) y2, (float) z2).setColor(red, green, blue, alpha).setUv(0, 0).setLight(0x00F000F0).setOverlay(OverlayTexture.NO_OVERLAY).setNormal(1, 0, 0);
    }

    @Override
    public SlimeballCollectorBlockEntityRenderState createRenderState() {
        return new SlimeballCollectorBlockEntityRenderState();
    }
}