package com.coolerpromc.productiveslimes.fluid;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;

import java.awt.*;

public class ModBaseFluidType extends FluidType {
    public final Vector3f FOG_COLOR;
    public final float fogStart;
    public final float fogEnd;
    public final IClientFluidTypeExtensions clientFluidType;
    public ModBaseFluidType(FluidType.Properties properties, FunkyFluidInfo info, int color) {
        super(properties);
        ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
        ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
        ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");
        Color colorObject = new Color(info.color);
        FOG_COLOR = new Vector3f(colorObject.getRed()/255F, colorObject.getGreen()/255F, colorObject.getBlue()/255F);
        fogStart = info.fogStart;
        fogEnd = info.fogEnd;
        clientFluidType = new ModClientFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, FOG_COLOR, fogStart, fogEnd, color);
    }

    public static class FunkyFluidInfo {
        public String name;
        public int color;
        public float fogStart;
        public float fogEnd;
        public boolean isTranslucent;
        public FunkyFluidInfo(String name, int color, float fogStart, float fogEnd,boolean isTranslucent) {
            this.name = name;
            this.color = color;
            this.fogStart = fogStart;
            this.fogEnd = fogEnd;
            this.isTranslucent  = isTranslucent;
        }
    }

    public IClientFluidTypeExtensions getClientExtensions() {
        return clientFluidType;
    }

    private static class ModClientFluidType implements IClientFluidTypeExtensions {
        public final ResourceLocation TEXTURE_STILL;
        public final ResourceLocation TEXTURE_FLOW;
        public final ResourceLocation TEXTURE_OVERLAY;
        public final Vector3f FOG_COLOR;
        public final float fogStart;
        public final float fogEnd;
        public int color;
        public ModClientFluidType(ResourceLocation textureStill, ResourceLocation textureFlow, ResourceLocation textureOverlay, Vector3f fogColor, float fogStart, float fogEnd, int color) {
            TEXTURE_STILL = textureStill;
            TEXTURE_FLOW = textureFlow;
            TEXTURE_OVERLAY = textureOverlay;
            FOG_COLOR = fogColor;
            this.fogStart = fogStart;
            this.fogEnd = fogEnd;
            this.color = color;
        }
        @Override
        public ResourceLocation getStillTexture() {
            return TEXTURE_STILL;
        }
        @Override
        public ResourceLocation getFlowingTexture() {
            return TEXTURE_FLOW;
        }
        @Override
        public ResourceLocation getOverlayTexture() {
            return TEXTURE_OVERLAY;
        }
        @Override
        public int getTintColor() {
            return color;
        }
        @Override
        public Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
            return FOG_COLOR;
        }
        @Override
        public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
            RenderSystem.setShaderFogStart(fogStart);
            RenderSystem.setShaderFogEnd(fogEnd);
        }
    }
}