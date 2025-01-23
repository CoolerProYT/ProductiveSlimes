package com.coolerpromc.productiveslimes.item.custom;

import net.minecraft.fluid.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.Supplier;

public class BucketItem extends net.minecraft.item.BucketItem {
    public final int color;
    private final Supplier<? extends Fluid> fluid;
    private final int amount = 1000;

    public BucketItem(Supplier<? extends Fluid> pContent, Properties pProperties, int color) {
        super(pContent, pProperties);
        this.color = color;
        this.fluid = pContent;
    }

    public int getColor() {
        return color;
    }

    public FluidStack getFluidStack() {
        return new FluidStack(fluid.get(), amount);
    }

    public int getAmount() {
        return amount;
    }
}
