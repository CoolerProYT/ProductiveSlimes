package com.coolerpromc.productiveslimes.compat.rei.DnaSynthesizing;

import com.coolerpromc.productiveslimes.recipe.DnaSynthesizingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

public class DnaSynthesizingRecipeDisplay extends BasicDisplay {
    private int energy;
    private int inputCount;
    private ItemStack inputItem;

    public DnaSynthesizingRecipeDisplay(RecipeHolder<DnaSynthesizingRecipe> recipe) {
        super(
            List.of(
                EntryIngredients.ofIngredient(recipe.value().getInputItems().getFirst()),
                EntryIngredients.ofIngredient(recipe.value().getInputItems().get(1)),
                EntryIngredients.ofIngredient(recipe.value().getInputItems().get(2))
            ),
            List.of(EntryIngredient.of(EntryStacks.of(recipe.value().getOutput().getFirst())))
        );

        energy = recipe.value().getEnergy();
        inputCount = recipe.value().getInputCount();
        inputItem = new ItemStack(recipe.value().getInputItems().get(2).getItems()[0].getItem(), recipe.value().getInputCount());
    }

    public int getEnergy() {
        return energy;
    }

    public int getInputCount() {
        return inputCount;
    }

    public ItemStack getInputItem() {
        return inputItem;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return DnaSynthesizingCategory.DNA_SYNTHESIZING;
    }
}