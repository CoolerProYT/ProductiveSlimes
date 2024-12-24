package com.coolerpromc.productiveslimes.block.entity.renderer;

import com.coolerpromc.productiveslimes.block.entity.SlimeballCollectorBlockEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.OptionalDouble;

public class SlimeballCollectorBlockEntityRenderer implements BlockEntityRenderer<SlimeballCollectorBlockEntity> {
    public SlimeballCollectorBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {
    }

    @Override
    public void render(SlimeballCollectorBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
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
        renderOutline(poseStack, bufferSource, collectionArea);

        poseStack.popPose();
    }

    private void renderOutline(PoseStack poseStack, MultiBufferSource bufferSource, AABB aabb) {
        // Buffer for lines.
        var buffer = bufferSource.getBuffer(RenderType.create("glow_lines", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.LINES, 256,
                RenderType.CompositeState.builder()
                        .setShaderState(RenderStateShard.RENDERTYPE_LINES_SHADER)
                        .setLineState(new RenderStateShard.LineStateShard(OptionalDouble.of(2.0))) // Line width
                        .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                        .setOutputState(RenderStateShard.ITEM_ENTITY_TARGET)
                        .setCullState(RenderStateShard.NO_CULL) // Disable culling
                        .createCompositeState(false)
        ));

        RenderSystem.lineWidth(2.0f);
        RenderSystem.disableCull();

        // Render the outer box.
        drawBox(poseStack, buffer, aabb, 1.0f, 0.0f, 0.0f, 1.0f); // Red color.

        RenderSystem.lineWidth(1.0f);
        RenderSystem.enableCull();
    }

    private void renderGrid(PoseStack poseStack, VertexConsumer buffer, AABB box, float red, float green, float blue, float alpha) {
        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix = pose.pose();

        // Chunk grid size (16 blocks).
        int chunkSize = 16;

        // Loop through the X and Z axes to draw the grid.
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
        buffer.addVertex(matrix, (float) x1, (float) y1, (float) z1).setColor(red, green, blue, alpha).setNormal(1.0f, 0.0f, 0.0f);
        buffer.addVertex(matrix, (float) x2, (float) y2, (float) z2).setColor(red, green, blue, alpha).setNormal(1.0f, 0.0f, 0.0f);
    }

}
