package com.coolerpromc.productiveslimes.config.fluid;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogParameters;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidType;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.awt.*;

public class ModBaseFluidType extends FluidType {
    public final Vector3f FOG_COLOR;
    public final float fogStart;
    public final float fogEnd;
    public final ModClientFluidType clientFluidType;


    public ModBaseFluidType(Properties properties, FunkyFluidInfo info, int color) {
        super(properties);
        ResourceLocation WATER_STILL_RL = ResourceLocation.parse("block/water_still");
        ResourceLocation WATER_FLOWING_RL = ResourceLocation.parse("block/water_flow");
        ResourceLocation WATER_OVERLAY_RL = ResourceLocation.parse("block/water_overlay");

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
        public Vector4f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector4f fluidFogColor) {
            return new Vector4f(FOG_COLOR.x, FOG_COLOR.y, FOG_COLOR.z, 1.0F);
        }

        @Override
        public FogParameters modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, FogParameters fogParameters) {
            float r = FOG_COLOR.x;
            float g = FOG_COLOR.y;
            float b = FOG_COLOR.z;
            float a = 1.0F;
            return new FogParameters(1f, 6f, FogShape.SPHERE, r, g, b, a);
        }
    }
}