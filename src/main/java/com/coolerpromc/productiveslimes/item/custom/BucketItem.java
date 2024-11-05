package com.coolerpromc.productiveslimes.item.custom;

import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

public class BucketItem extends net.minecraft.world.item.BucketItem {
    public final int color;
    private final Fluid fluid;
    private final int amount = 1000;

    public BucketItem(Fluid pContent, Properties pProperties, int color) {
        super(pContent, pProperties);
        this.color = color;
        this.fluid = pContent;
    }

    public int getColor() {
        return color;
    }

    public FluidStack getFluidStack() {
        return new FluidStack(fluid, amount);
    }

    public int getAmount() {
        return amount;
    }
}
