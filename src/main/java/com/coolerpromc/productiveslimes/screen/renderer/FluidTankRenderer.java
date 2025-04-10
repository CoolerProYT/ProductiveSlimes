package com.coolerpromc.productiveslimes.screen.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Matrix4f;

@SuppressWarnings("deprecation")
public class FluidTankRenderer {
    public static void renderFluidStack(GuiGraphics guiGraphics, FluidStack fluidStack, int tankCapacity, int w, int h, int x, int y) {
        if(fluidStack.isEmpty())
            return;

        Fluid fluid = fluidStack.getFluid();
        IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluid);
        ResourceLocation stillFluidImageId = fluidTypeExtensions.getStillTexture(fluidStack);
        TextureAtlasSprite stillFluidSprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(stillFluidImageId);

        int fluidColorTint = fluidTypeExtensions.getTintColor(fluidStack);

        int fluidMeterPos = tankCapacity == -1 || (fluidStack.getAmount() > 0 && fluidStack.getAmount() == tankCapacity) ? 0:(h - ((fluidStack.getAmount() <= 0 || tankCapacity == 0)?0: (Math.min(fluidStack.getAmount(), tankCapacity - 1) * h / tankCapacity + 1)));

        Matrix4f mat = guiGraphics.pose().last().pose();

        for (int yOffset = h; yOffset > fluidMeterPos; yOffset -= 16) {
            for (int xOffset = 0; xOffset < w; xOffset += 16) {
                int finalXOffset = x + xOffset;
                int finalYOffset = y + yOffset;

                int finalXOffset1 = xOffset;
                guiGraphics.drawSpecial(vertexConsumers -> {
                    int width = Math.min(w - finalXOffset1, 16);
                    int height = Math.min(finalYOffset - (y + fluidMeterPos), 16);

                    float u0 = stillFluidSprite.getU0();
                    float u1 = stillFluidSprite.getU1();
                    float v0 = stillFluidSprite.getV0();
                    float v1 = stillFluidSprite.getV1();
                    u1 = u1 - ((16 - width) / 16.f * (u1 - u0));
                    v0 = v0 - ((16 - height) / 16.f * (v0 - v1));

                    VertexConsumer bufferBuilder = vertexConsumers.getBuffer(RenderType.guiTextured(stillFluidSprite.atlasLocation()));
                    bufferBuilder.addVertex(mat, finalXOffset, finalYOffset, 0).setColor(fluidColorTint).setUv(u0, v1);
                    bufferBuilder.addVertex(mat, finalXOffset + width, finalYOffset, 0).setColor(fluidColorTint).setUv(u1, v1);
                    bufferBuilder.addVertex(mat, finalXOffset + width, finalYOffset - height, 0).setColor(fluidColorTint).setUv(u1, v0);
                    bufferBuilder.addVertex(mat, finalXOffset, finalYOffset - height, 0).setColor(fluidColorTint).setUv(u0, v0);
                });
            }
        }
    }
}
