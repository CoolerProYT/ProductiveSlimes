package com.coolerpromc.productiveslimes.compat.rei.Melting;

import com.coolerpromc.productiveslimes.recipe.MeltingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

public class MeltingRecipeDisplay extends BasicDisplay {
    private final int energy;
    private final ItemStack inputItem;
    private final int outputCount;

    public MeltingRecipeDisplay(RecipeHolder<MeltingRecipe> recipe) {
        super(List.of(EntryIngredients.ofIngredient(recipe.value().getIngredients().getFirst())),
                List.of(EntryIngredient.of(EntryStacks.of(recipe.value().getOutputs().getFirst()))));

        energy = recipe.value().getEnergy();
        inputItem = new ItemStack(recipe.value().getIngredients().getFirst().getItems()[0].getItem(), recipe.value().getInputCount());
        outputCount = recipe.value().getOutputs().getFirst().getCount();
    }

    public int getEnergy() {
        return energy;
    }

    public ItemStack getInputItem() {
        return inputItem;
    }

    public int getOutputCount() {
        return outputCount;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return MeltingCategory.MELTING;
    }
}