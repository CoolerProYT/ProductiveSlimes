package com.coolerpromc.productiveslimes.compat.rei.Soliding;

import com.coolerpromc.productiveslimes.compat.rei.Melting.MeltingCategory;
import com.coolerpromc.productiveslimes.recipe.MeltingRecipe;
import com.coolerpromc.productiveslimes.recipe.SolidingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

public class SolidingRecipeDisplay extends BasicDisplay {
    private final int energy;
    private final ItemStack inputItem;
    private final int outputCount;

    public SolidingRecipeDisplay(RecipeHolder<SolidingRecipe> recipe) {
        super(
            List.of(EntryIngredients.ofIngredient(recipe.value().getIngredients().getFirst())),
            List.of(
                EntryIngredient.of(EntryStacks.of(recipe.value().getOutputs().get(0))),
                EntryIngredient.of(EntryStacks.of(recipe.value().getOutputs().get(1)))
            )
        );

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

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return SolidingCategory.SOLIDING;
    }
}