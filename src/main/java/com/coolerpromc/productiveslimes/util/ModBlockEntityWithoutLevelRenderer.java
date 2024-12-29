package com.coolerpromc.productiveslimes.util;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.item.custom.BucketItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;

public class ModBlockEntityWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer {
    private boolean isRendering = false;

    public ModBlockEntityWithoutLevelRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack pStack, ItemTransforms.TransformType pTransformType, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
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
                CompoundTag fluidTag = pStack.getTag().getCompound("fluid");
                fluidStack = FluidStack.loadFluidStackFromNBT(fluidTag);
            }

            if (!fluidStack.isEmpty()) {
                float height = ((float) fluidStack.getAmount() / 50000) * 0.95f;

                ResourceLocation stillTexture = new ResourceLocation("block/water_still");

                TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);
                int fluidColor = 0xFFFFFFFF;

                if (fluidStack.getFluid().getBucket() instanceof BucketItem bucketItem) {
                    fluidColor = bucketItem.getColor();
                }
                else{
                    fluidColor = fluidStack.getFluid().getAttributes().getColor();
                }

                VertexConsumer builder = pBuffer.getBuffer(ItemBlockRenderTypes.getRenderLayer(fluidStack.getFluid().defaultFluidState()));

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

    private static void drawVertex(VertexConsumer builder, PoseStack poseStack, float x, float y, float z, float u, float v, int packedLight, int color) {
        builder.vertex(poseStack.last().pose(), x, y, z)
                .color(color)
                .uv(u, v)
                .uv2(packedLight)
                .normal(1, 0, 0)
                .endVertex();
    }
    private static void drawQuad(VertexConsumer builder, PoseStack poseStack, float x0, float y0, float z0, float x1, float y1, float z1, float u0, float v0, float u1, float v1, int packedLight, int color) {
        drawVertex(builder, poseStack, x0, y0, z0, u0, v0, packedLight, color);
        drawVertex(builder, poseStack, x0, y1, z1, u0, v1, packedLight, color);
        drawVertex(builder, poseStack, x1, y1, z1, u1, v1, packedLight, color);
        drawVertex(builder, poseStack, x1, y0, z0, u1, v0, packedLight, color);
    }
}
