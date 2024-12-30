package com.coolerpromc.productiveslimes.config.fluid;

import com.mojang.math.Vector3f;

import java.awt.*;

public class ModBaseFluidType {
    public final Vector3f FOG_COLOR;
    public final float fogStart;
    public final float fogEnd;


    public ModBaseFluidType(FunkyFluidInfo info, int color) {
        Color colorObject = new Color(info.color);
        FOG_COLOR = new Vector3f(colorObject.getRed()/255F, colorObject.getGreen()/255F, colorObject.getBlue()/255F);
        fogStart = info.fogStart;
        fogEnd = info.fogEnd;
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
}