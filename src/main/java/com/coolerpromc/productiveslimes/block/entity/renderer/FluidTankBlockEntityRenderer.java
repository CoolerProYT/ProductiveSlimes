package com.coolerpromc.productiveslimes.block.entity.renderer;

import com.coolerpromc.productiveslimes.block.entity.FluidTankBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;

public class FluidTankBlockEntityRenderer implements BlockEntityRenderer<FluidTankBlockEntity> {
    public FluidTankBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {

    }

    @Override
    public void render(FluidTankBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay, Vec3 p_401186_) {
        FluidStack fluidStack = pBlockEntity.getFluidStack();
        renderFluid(pPoseStack, pBufferSource, pPackedLight, pPackedOverlay, fluidStack);
    }

    public static void renderFluid(PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int packedOverlay, FluidStack fluidStack){
        if (fluidStack.isEmpty()) return;

        IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
        ResourceLocation stillTexture = fluidTypeExtensions.getStillTexture(fluidStack);

        FluidState state = fluidStack.getFluid().defaultFluidState();

        int color = fluidTypeExtensions.getTintColor();

        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(stillTexture);

        float height = ((float) fluidStack.getAmount() / 50000) * 0.90f;
        if (fluidStack.getAmount() > 1000) {
            height += 0.05f;
        }

        VertexConsumer builder = pBufferSource.getBuffer(ItemBlockRenderTypes.getRenderLayer(state));

        drawQuad(builder, pPoseStack, 0.2f, height, 0.2f, 0.80f, height, 0.80f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, color);

        drawQuad(builder, pPoseStack, 0.2f, 0.05f, 0.2f, 0.80f, height, 0.2f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, color);
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YP.rotationDegrees(180));
        pPoseStack.translate(-1f, 0, -1.6f);
        drawQuad(builder, pPoseStack, 0.2f, 0.05f, 0.80f, 0.80f, height, 0.80f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, color);
        pPoseStack.popPose();
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
        pPoseStack.translate(-1f, 0, 0);
        drawQuad(builder, pPoseStack, 0.2f, 0.05f, 0.2f, 0.80f, height, 0.2f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, color);
        pPoseStack.popPose();
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YN.rotationDegrees(90));
        pPoseStack.translate(0, 0, -1f);
        drawQuad(builder, pPoseStack, 0.2f, 0.05f, 0.2f, 0.80f, height, 0.2f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, color);
        pPoseStack.popPose();
    }

    private static void drawVertex(VertexConsumer builder, PoseStack poseStack, float x, float y, float z, float u, float v, int packedLight, int color) {
        builder.addVertex(poseStack.last().pose(), x, y, z)
                .setColor(color)
                .setUv(u, v)
                .setLight(packedLight)
                .setNormal(1, 0, 0);
    }
    private static void drawQuad(VertexConsumer builder, PoseStack poseStack, float x0, float y0, float z0, float x1, float y1, float z1, float u0, float v0, float u1, float v1, int packedLight, int color) {
        drawVertex(builder, poseStack, x0, y0, z0, u0, v0, packedLight, color);
        drawVertex(builder, poseStack, x0, y1, z1, u0, v1, packedLight, color);
        drawVertex(builder, poseStack, x1, y1, z1, u1, v1, packedLight, color);
        drawVertex(builder, poseStack, x1, y0, z0, u1, v0, packedLight, color);
    }
}