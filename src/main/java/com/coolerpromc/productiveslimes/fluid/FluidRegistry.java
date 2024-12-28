package com.coolerpromc.productiveslimes.fluid;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidRegistry {
    public static FluidStack getFluidStackByName(String fluidName, int amount) {
        ResourceLocation fluidLocation;
        try {
            fluidLocation = new ResourceLocation(fluidName);
        } catch (Exception e) {
            return FluidStack.EMPTY;
        }

        Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidLocation);
        if (fluid != null) {
            return new FluidStack(fluid, amount);
        }

        return FluidStack.EMPTY;
    }
}
