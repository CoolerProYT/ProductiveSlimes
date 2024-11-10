package com.coolerpromc.productiveslimes.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

public class FakeBucketItem extends Item {
    public final int color;

    public FakeBucketItem(Properties pProperties, int color) {
        super(pProperties);
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
