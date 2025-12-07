package com.coolerpromc.productiveslimes.fluid;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.Optional;

public class FluidRegistry {
    public static FluidStack getFluidStackByName(String fluidName, int amount) {
        Identifier fluidLocation;
        try {
            fluidLocation = Identifier.parse(fluidName);
        } catch (Exception e) {
            return FluidStack.EMPTY;
        }

        Optional<Holder.Reference<Fluid>> fluid = BuiltInRegistries.FLUID.get(fluidLocation);
        if (fluid.isPresent()) {
            return new FluidStack(fluid.get(), amount);
        }

        return FluidStack.EMPTY;
    }
}