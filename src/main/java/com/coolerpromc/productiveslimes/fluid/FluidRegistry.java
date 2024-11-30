package com.coolerpromc.productiveslimes.fluid;

import com.coolerpromc.productiveslimes.item.custom.BucketItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Supplier;

public class FluidRegistry {
    public static FluidStack getFluidStackByName(String fluidName, int amount) {
        ResourceLocation fluidLocation;
        try {
            fluidLocation = ResourceLocation.parse(fluidName);
        } catch (Exception e) {
            return FluidStack.EMPTY;
        }

        Fluid fluid = BuiltInRegistries.FLUID.get(fluidLocation);
        if (fluid != null) {
            return new FluidStack(fluid, amount);
        }

        return FluidStack.EMPTY;
    }
}
