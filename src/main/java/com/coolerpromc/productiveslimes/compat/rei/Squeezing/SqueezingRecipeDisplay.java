package com.coolerpromc.productiveslimes.compat.rei.Squeezing;

import com.coolerpromc.productiveslimes.recipe.SqueezingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class SqueezingRecipeDisplay extends BasicDisplay {
    private final int energy;
    private final ItemStack inputItem;
    private final int outputCount;
    public SqueezingRecipeDisplay(SqueezingRecipe recipe) {
        super(
                List.of(EntryIngredients.ofIngredient(recipe.getIngredients().get(0))),
                List.of(
                        EntryIngredient.of(EntryStacks.of(recipe.getOutputs().get(0))),
                        EntryIngredient.of(EntryStacks.of(recipe.getOutputs().get(1)))
                )
        );
        energy = recipe.getEnergy();
        inputItem = new ItemStack(recipe.getIngredients().get(0).getItems()[0].getItem());
        outputCount = recipe.getOutputs().get(0).getCount();
    }
    public int getEnergy() {
        return energy;
    }
    public ItemStack getInputItem() {
        return inputItem;
    }
    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return SqueezingCategory.SQUEEZING;
    }
}