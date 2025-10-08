package com.coolerpromc.productiveslimes.block.entity.renderer;

import com.coolerpromc.productiveslimes.block.entity.SolidingStationBlockEntity;
import com.coolerpromc.productiveslimes.block.entity.renderstate.SolidingStationBlockEntityRenderState;
import com.coolerpromc.productiveslimes.item.custom.BucketItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

public class SolidingStationBlockEntityRenderer implements BlockEntityRenderer<SolidingStationBlockEntity, SolidingStationBlockEntityRenderState> {
    public SolidingStationBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {

    }

    @Override
    public void extractRenderState(SolidingStationBlockEntity blockEntity, SolidingStationBlockEntityRenderState renderState, float p_446851_, Vec3 p_445788_, @Nullable ModelFeatureRenderer.CrumblingOverlay p_446944_) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, p_446851_, p_445788_, p_446944_);
        renderState.blockEntity = blockEntity;
    }

    @Override
    public void submit(SolidingStationBlockEntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState p_451022_) {
        SolidingStationBlockEntity blockEntity = renderState.blockEntity;
        ItemStack itemStack = blockEntity.getRenderStack();
        FluidStack fluidStack;
        int color = 0xFFFFFFFF;

        if (itemStack.getItem() instanceof BucketItem bucketItem) {
            fluidStack = bucketItem.getFluidStack();
            color = bucketItem.getColor();
        }
        else {
            fluidStack = FluidStack.EMPTY;
        }

        if (fluidStack.isEmpty()) return;

        Level level = blockEntity.getLevel();
        if (level == null) return;

        IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
        ResourceLocation stillTexture = fluidTypeExtensions.getStillTexture(fluidStack);
        if (stillTexture == null) return;

        FluidState state = fluidStack.getFluid().defaultFluidState();

        TextureAtlasSprite sprite = Minecraft.getInstance().getAtlasManager().get(new Material(TextureAtlas.LOCATION_BLOCKS, stillTexture));
        int tintColor = color;

        float height = 0.8f;

        nodeCollector.submitCustomGeometry(poseStack, RenderType.entityTranslucent(sprite.atlasLocation()), (pose, builder) -> {
            drawQuad(builder, pose, 0.2f, height, 0.2f, 0.80f, height, 0.2f, 0.80f, height, 0.80f, 0.2f, height, 0.80f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), renderState.lightCoords, tintColor, 0, -1, 0);

            drawQuad(builder, pose, 0.2f, 0.05f, 0.2f, 0.80f, height, 0.2f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), renderState.lightCoords, tintColor, 0, 0, -1);
            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees(180));
            poseStack.translate(-1f, 0, -1.6f);
            drawQuad(builder, pose, 0.8f, 0.05f, 0.80f, 0.2f, height, 0.80f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), renderState.lightCoords, tintColor, 0, 0, 1);
            poseStack.popPose();
            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees(90));
            poseStack.translate(-1f, 0, 0);
            drawQuad(builder, pose, 0.2f, 0.05f, 0.2f, 0.20f, height, 0.8f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), renderState.lightCoords, tintColor, -1, 0, 0);
            poseStack.popPose();
            poseStack.pushPose();
            poseStack.mulPose(Axis.YN.rotationDegrees(90));
            poseStack.translate(0, 0, -1f);
            drawQuad(builder, pose, 0.8f, 0.05f, 0.2f, 0.80f, height, 0.8f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), renderState.lightCoords, tintColor, 1, 0, 0);
            poseStack.popPose();
        });
    }

    private static void drawVertex(VertexConsumer builder, PoseStack.Pose pose,
                                   float x, float y, float z,
                                   float u, float v,
                                   int packedLight, int color,
                                   float nx, float ny, float nz) {
        builder.addVertex(pose.pose(), x, y, z)
                .setColor(color)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, nx, ny, nz);
    }

    private static void drawQuad(VertexConsumer builder, PoseStack.Pose pose,
                                 float x0, float y0, float z0,
                                 float x1, float y1, float z1,
                                 float u0, float v0, float u1, float v1,
                                 int packedLight, int color,
                                 float nx, float ny, float nz) {
        drawVertex(builder, pose, x0, y0, z0, u0, v0, packedLight, color, nx, ny, nz);
        drawVertex(builder, pose, x0, y1, z0, u0, v1, packedLight, color, nx, ny, nz);
        drawVertex(builder, pose, x1, y1, z1, u1, v1, packedLight, color, nx, ny, nz);
        drawVertex(builder, pose, x1, y0, z1, u1, v0, packedLight, color, nx, ny, nz);
    }

    private static void drawQuad(VertexConsumer builder, PoseStack.Pose pose,
                                 float x0, float y0, float z0,
                                 float x1, float y1, float z1,
                                 float x2, float y2, float z2,
                                 float x3, float y3, float z3,
                                 float u0, float v0, float u1, float v1,
                                 int packedLight, int color,
                                 float nx, float ny, float nz) {
        drawVertex(builder, pose, x0, y0, z0, u0, v0, packedLight, color, nx, ny, nz);
        drawVertex(builder, pose, x1, y1, z1, u0, v1, packedLight, color, nx, ny, nz);
        drawVertex(builder, pose, x2, y2, z2, u1, v1, packedLight, color, nx, ny, nz);
        drawVertex(builder, pose, x3, y3, z3, u1, v0, packedLight, color, nx, ny, nz);
    }

    @Override
    public SolidingStationBlockEntityRenderState createRenderState() {
        return new SolidingStationBlockEntityRenderState();
    }
}
