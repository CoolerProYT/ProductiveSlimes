package com.coolerpromc.productiveslimes.compat.rei.DnaExtracting;

import com.coolerpromc.productiveslimes.recipe.DnaExtractingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class DnaExtractingRecipeDisplay extends BasicDisplay {
    private int energy;
    private float outputChance;

    public DnaExtractingRecipeDisplay(DnaExtractingRecipe recipe) {
        super(List.of(EntryIngredients.ofIngredient(recipe.getIngredients().get(0))),
                List.of(EntryIngredient.of(EntryStacks.of(recipe.getOutputs().get(0))),
                        EntryIngredient.of(EntryStacks.of(recipe.getOutputs().size() > 1 ? recipe.getOutputs().get(1) : ItemStack.EMPTY))));

        energy = recipe.getEnergy();
        outputChance = recipe.getOutputChance();
    }

    public int getEnergy() {
        return energy;
    }

    public float getOutputChance() {
        return outputChance;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return DnaExtractingCategory.DNA_EXTRACTING;
    }
}