package com.coolerpromc.productiveslimes.item.custom;

import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

public class BucketItem extends net.minecraft.world.item.BucketItem {
    public final int color;
    private final Fluid fluid;

    public BucketItem(Fluid pContent, Properties pProperties, int color) {
        super(pContent, pProperties);
        this.color = color;
        this.fluid = pContent;
    }

    public int getColor() {
        return color;
    }

    public FluidStack getFluidStack() {
        return new FluidStack(fluid, 1000);
    }
}
