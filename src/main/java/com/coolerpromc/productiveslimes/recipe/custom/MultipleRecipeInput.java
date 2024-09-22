package com.coolerpromc.productiveslimes.recipe.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;

public record MultipleRecipeInput(List<ItemStack> inputItems) implements RecipeInput {
    public MultipleRecipeInput(List<ItemStack> inputItems) {
        this.inputItems = inputItems;
    }

    @Override
    public ItemStack getItem(int pIndex) {
        return inputItems.get(pIndex);
    }

    @Override
    public int size() {
        return 3;
    }
}
