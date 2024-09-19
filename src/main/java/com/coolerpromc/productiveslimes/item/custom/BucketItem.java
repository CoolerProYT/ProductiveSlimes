package com.coolerpromc.productiveslimes.item.custom;

import net.minecraft.world.level.material.Fluid;

public class BucketItem extends net.minecraft.world.item.BucketItem {
    public final int color;

    public BucketItem(Fluid pContent, Properties pProperties, int color) {
        super(pContent, pProperties);
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
