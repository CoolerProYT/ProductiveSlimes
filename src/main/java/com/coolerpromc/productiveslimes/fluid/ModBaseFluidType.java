package com.coolerpromc.productiveslimes.fluid;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.Identifier;
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
        Identifier WATER_STILL_RL = Identifier.parse("block/water_still");
        Identifier WATER_FLOWING_RL = Identifier.parse("block/water_flow");
        Identifier WATER_OVERLAY_RL = Identifier.parse("block/water_overlay");

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
        public final Identifier TEXTURE_STILL;
        public final Identifier TEXTURE_FLOW;
        public final Identifier TEXTURE_OVERLAY;
        public final Vector3f FOG_COLOR;
        public final float fogStart;
        public final float fogEnd;
        public int color;

        public ModClientFluidType(Identifier textureStill, Identifier textureFlow, Identifier textureOverlay, Vector3f fogColor, float fogStart, float fogEnd, int color) {
            TEXTURE_STILL = textureStill;
            TEXTURE_FLOW = textureFlow;
            TEXTURE_OVERLAY = textureOverlay;
            FOG_COLOR = fogColor;
            this.fogStart = fogStart;
            this.fogEnd = fogEnd;
            this.color = color;
        }


        @Override
        public Identifier getStillTexture() {
            return TEXTURE_STILL;
        }

        @Override
        public Identifier getFlowingTexture() {
            return TEXTURE_FLOW;
        }

        @Override
        public Identifier getOverlayTexture() {
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
    }
}