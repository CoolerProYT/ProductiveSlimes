package com.coolerpromc.productiveslimes.recipe.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.FluidStack;

public record SingleFluidRecipeInput(FluidStack fluidStack) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        throw new UnsupportedOperationException("Fluid recipe input does not have an item.");
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return fluidStack.isEmpty();
    }
}
