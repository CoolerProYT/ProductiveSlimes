package com.coolerpromc.productiveslimes.util;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.item.custom.BucketItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.fluids.FluidStack;

public class ModBlockEntityWithoutLevelRenderer extends ItemStackTileEntityRenderer {
    private boolean isRendering = false;

    public ModBlockEntityWithoutLevelRenderer() {
        super();
    }

    @Override
    public void renderByItem(ItemStack pStack, ItemCameraTransforms.TransformType pTransformType, MatrixStack pPoseStack, IRenderTypeBuffer pBuffer, int pPackedLight, int pPackedOverlay) {
        if (isRendering) {
            return;
        }

        isRendering = true;
        try {
            // First, render the tank using the solid buffer
            pPoseStack.pushPose();
            BlockState state = ModBlocks.FLUID_TANK.get().defaultBlockState();
            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
            pPoseStack.popPose();

            FluidStack fluidStack = FluidStack.EMPTY;

            if (pStack.hasTag() && pStack.getTag().contains("fluid")) {
                CompoundNBT fluidTag = pStack.getTag().getCompound("fluid");
                fluidStack = FluidStack.loadFluidStackFromNBT(fluidTag);
            }

            if (!fluidStack.isEmpty()) {
                float height = ((float) fluidStack.getAmount() / 50000) * 0.95f;

                ResourceLocation stillTexture = new ResourceLocation("block/water_still");

                TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(PlayerContainer.BLOCK_ATLAS).apply(stillTexture);
                int fluidColor = 0xFFFFFFFF;

                if (fluidStack.getFluid().getBucket() instanceof BucketItem) {
                    BucketItem bucketItem = (BucketItem) fluidStack.getFluid().getBucket();
                    fluidColor = bucketItem.getColor();
                }
                else{
                    fluidColor = fluidStack.getFluid().getAttributes().getColor();
                }

                IVertexBuilder builder = pBuffer.getBuffer(RenderTypeLookup.getRenderLayer(fluidStack.getFluid().defaultFluidState()));

                drawQuad(builder, pPoseStack, 0.15f, height, 0.15f, 0.85f, height, 0.85f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, fluidColor);

                drawQuad(builder, pPoseStack, 0.15f, 0, 0.15f, 0.85f, height, 0.15f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, fluidColor);
                pPoseStack.pushPose();
                pPoseStack.mulPose(Vector3f.YP.rotationDegrees(180));
                pPoseStack.translate(-1f, 0, -1.6f);
                drawQuad(builder, pPoseStack, 0.15f, 0, 0.75f, 0.85f, height, 0.75f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, fluidColor);
                pPoseStack.popPose();
                pPoseStack.pushPose();
                pPoseStack.mulPose(Vector3f.YP.rotationDegrees(90));
                pPoseStack.translate(-1f, 0, 0);
                drawQuad(builder, pPoseStack, 0.15f, 0, 0.15f, 0.85f, height, 0.15f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, fluidColor);
                pPoseStack.popPose();
                pPoseStack.pushPose();
                pPoseStack.mulPose(Vector3f.YN.rotationDegrees(90));
                pPoseStack.translate(0, 0, -1f);
                drawQuad(builder, pPoseStack, 0.15f, 0, 0.15f, 0.85f, height, 0.15f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, fluidColor);
                pPoseStack.popPose();
            }
        } finally {
            isRendering = false;
        }
    }

    private static void drawVertex(IVertexBuilder builder, MatrixStack poseStack, float x, float y, float z, float u, float v, int packedLight, int color) {
        int alpha = (color >> 24) & 0xFF;
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        builder.vertex(poseStack.last().pose(), x, y, z)
                .color(red, green, blue, alpha)
                .uv(u, v)
                .uv2(packedLight)
                .normal(1, 0, 0)
                .endVertex();
    }
    private static void drawQuad(IVertexBuilder builder, MatrixStack poseStack, float x0, float y0, float z0, float x1, float y1, float z1, float u0, float v0, float u1, float v1, int packedLight, int color) {
        drawVertex(builder, poseStack, x0, y0, z0, u0, v0, packedLight, color);
        drawVertex(builder, poseStack, x0, y1, z1, u0, v1, packedLight, color);
        drawVertex(builder, poseStack, x1, y1, z1, u1, v1, packedLight, color);
        drawVertex(builder, poseStack, x1, y0, z0, u1, v0, packedLight, color);
    }
}
