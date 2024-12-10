package com.coolerpromc.productiveslimes.compat.rei.Squeezing;

import com.coolerpromc.productiveslimes.recipe.SqueezingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

public class SqueezingRecipeDisplay extends BasicDisplay {
    private final int energy;
    private final ItemStack inputItem;
    private final int outputCount;

    public SqueezingRecipeDisplay(RecipeHolder<SqueezingRecipe> recipe) {
        super(
            List.of(EntryIngredients.ofIngredient(recipe.value().getIngredients().getFirst())),
            List.of(
                EntryIngredient.of(EntryStacks.of(recipe.value().getOutputs().get(0))),
                EntryIngredient.of(EntryStacks.of(recipe.value().getOutputs().get(1)))
            )
        );

        energy = recipe.value().getEnergy();
        inputItem = new ItemStack(recipe.value().getIngredients().getFirst().getItems()[0].getItem());
        outputCount = recipe.value().getOutputs().getFirst().getCount();
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