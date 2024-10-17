package com.coolerpromc.productiveslimes.util;

import com.coolerpromc.productiveslimes.block.entity.FluidTankBlockEntity;
import com.coolerpromc.productiveslimes.datacomponent.ModDataComponents;
import com.coolerpromc.productiveslimes.handler.ImmutableFluidStack;
import com.coolerpromc.productiveslimes.item.custom.BucketItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Matrix4f;

public class ModBlockEntityWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer {
    private boolean isRendering = false;

    public ModBlockEntityWithoutLevelRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack pStack, ItemDisplayContext pDisplayContext, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        if (isRendering) {
            return;
        }

        isRendering = true;
        try {
            // Ensure we're rendering only in the GUI context
            if (pDisplayContext != ItemDisplayContext.GUI) {
                return;
            }

            ImmutableFluidStack immutableFluidStack = pStack.get(ModDataComponents.FLUID_STACK.get());
            FluidStack fluidStack = (immutableFluidStack != null) ? immutableFluidStack.fluidStack() : FluidStack.EMPTY;

            // Force the rendering of the tank model
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            BakedModel model = itemRenderer.getModel(pStack, null, null, 0);

            // Begin rendering the tank base
            pPoseStack.pushPose(); // Push the tank pose to ensure transformations don't interfere
            itemRenderer.render(pStack, ItemDisplayContext.GUI, false, pPoseStack, pBuffer, pPackedLight, pPackedOverlay, model);
            pPoseStack.popPose();  // Ensure that the tank pose is reverted

            // Now, render the fluid if it exists
            if (!fluidStack.isEmpty()) {
                pPoseStack.pushPose();
                pPoseStack.translate(0.525, 0.525, 0.525); // Positioning of fluid
                pPoseStack.scale(0.875f, 0.875f, 0.875f); // Scaling fluid to fit within the tank

                TextureAtlasSprite fluidSprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(ResourceLocation.parse("block/water_still"));
                int fluidColor = 0xFFFFFFFF; // Default color

                if (fluidStack.getFluid().getBucket() instanceof BucketItem bucketItem) {
                    fluidColor = bucketItem.getColor();
                }

                VertexConsumer builder = pBuffer.getBuffer(RenderType.translucent());
                float height = (float) fluidStack.getAmount() / FluidTankBlockEntity.CAPACITY;
                renderCube(builder, pPoseStack, fluidSprite, fluidColor, height, pPackedLight);

                pPoseStack.popPose(); // Pop fluid pose after rendering
            }
        } finally {
            isRendering = false;
        }
    }


    private void renderCube(VertexConsumer builder, PoseStack matrixStack, TextureAtlasSprite sprite, int color, float height, int light) {
        // Implement cube rendering with tinting here
        // This is a simplified version; you may need to adjust UV coordinates and normals
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >> 8) & 0xFF) / 255f;
        float b = (color & 0xFF) / 255f;
        float a = ((color >> 24) & 0xFF) / 255f;

        Matrix4f matrix = matrixStack.last().pose();

        // Bottom face
        builder.addVertex(matrix, -0.5f, -0.5f, -0.5f).setColor(r, g, b, a).setUv(sprite.getU0(), sprite.getV0()).setLight(light).setNormal(0, -1, 0);
        builder.addVertex(matrix, 0.5f, -0.5f, -0.5f).setColor(r, g, b, a).setUv(sprite.getU1(), sprite.getV0()).setLight(light).setNormal(0, -1, 0);
        builder.addVertex(matrix, 0.5f, -0.5f, 0.5f).setColor(r, g, b, a).setUv(sprite.getU1(), sprite.getV1()).setLight(light).setNormal(0, -1, 0);
        builder.addVertex(matrix, -0.5f, -0.5f, 0.5f).setColor(r, g, b, a).setUv(sprite.getU0(), sprite.getV1()).setLight(light).setNormal(0, -1, 0);

        // Top face
        float topY = -0.5f + height;
        builder.addVertex(matrix, -0.5f, topY, -0.5f).setColor(r, g, b, a).setUv(sprite.getU0(), sprite.getV0()).setLight(light).setNormal(0, 1, 0);
        builder.addVertex(matrix, -0.5f, topY, 0.5f).setColor(r, g, b, a).setUv(sprite.getU0(), sprite.getV1()).setLight(light).setNormal(0, 1, 0);
        builder.addVertex(matrix, 0.5f, topY, 0.5f).setColor(r, g, b, a).setUv(sprite.getU1(), sprite.getV1()).setLight(light).setNormal(0, 1, 0);
        builder.addVertex(matrix, 0.5f, topY, -0.5f).setColor(r, g, b, a).setUv(sprite.getU1(), sprite.getV0()).setLight(light).setNormal(0, 1, 0);

        // Side faces
        builder.addVertex(matrix, -0.5f, -0.5f, -0.5f).setColor(r, g, b, a).setUv(sprite.getU0(), sprite.getV0()).setLight(light).setNormal(-1, 0, 0);
        builder.addVertex(matrix, -0.5f, -0.5f, 0.5f).setColor(r, g, b, a).setUv(sprite.getU1(), sprite.getV0()).setLight(light).setNormal(-1, 0, 0);
        builder.addVertex(matrix, -0.5f, topY, 0.5f).setColor(r, g, b, a).setUv(sprite.getU1(), sprite.getV1()).setLight(light).setNormal(-1, 0, 0);
        builder.addVertex(matrix, -0.5f, topY, -0.5f).setColor(r, g, b, a).setUv(sprite.getU0(), sprite.getV1()).setLight(light).setNormal(-1, 0, 0);

        builder.addVertex(matrix, 0.5f, -0.5f, -0.5f).setColor(r, g, b, a).setUv(sprite.getU0(), sprite.getV0()).setLight(light).setNormal(1, 0, 0);
        builder.addVertex(matrix, 0.5f, topY, -0.5f).setColor(r, g, b, a).setUv(sprite.getU0(), sprite.getV1()).setLight(light).setNormal(1, 0, 0);
        builder.addVertex(matrix, 0.5f, topY, 0.5f).setColor(r, g, b, a).setUv(sprite.getU1(), sprite.getV1()).setLight(light).setNormal(1, 0, 0);
        builder.addVertex(matrix, 0.5f, -0.5f, 0.5f).setColor(r, g, b, a).setUv(sprite.getU1(), sprite.getV0()).setLight(light).setNormal(1, 0, 0);

        builder.addVertex(matrix, -0.5f, -0.5f, -0.5f).setColor(r, g, b, a).setUv(sprite.getU0(), sprite.getV0()).setLight(light).setNormal(0, 0, -1);
        builder.addVertex(matrix, -0.5f, topY, -0.5f).setColor(r, g, b, a).setUv(sprite.getU0(), sprite.getV1()).setLight(light).setNormal(0, 0, -1);
        builder.addVertex(matrix, 0.5f, topY, -0.5f).setColor(r, g, b, a).setUv(sprite.getU1(), sprite.getV1()).setLight(light).setNormal(0, 0, -1);
        builder.addVertex(matrix, 0.5f, -0.5f, -0.5f).setColor(r, g, b, a).setUv(sprite.getU1(), sprite.getV0()).setLight(light).setNormal(0, 0, -1);

        builder.addVertex(matrix, -0.5f, -0.5f, 0.5f).setColor(r, g, b, a).setUv(sprite.getU0(), sprite.getV0()).setLight(light).setNormal(0, 0, 1);
        builder.addVertex(matrix, 0.5f, -0.5f, 0.5f).setColor(r, g, b, a).setUv(sprite.getU1(), sprite.getV0()).setLight(light).setNormal(0, 0, 1);
        builder.addVertex(matrix, 0.5f, topY, 0.5f).setColor(r, g, b, a).setUv(sprite.getU1(), sprite.getV1()).setLight(light).setNormal(0, 0, 1);
        builder.addVertex(matrix, -0.5f, topY, 0.5f).setColor(r, g, b, a).setUv(sprite.getU0(), sprite.getV1()).setLight(light).setNormal(0, 0, 1);
    }
}
