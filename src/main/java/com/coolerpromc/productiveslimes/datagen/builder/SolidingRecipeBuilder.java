package com.coolerpromc.productiveslimes.datagen.builder;

import com.coolerpromc.productiveslimes.recipe.SolidingRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SolidingRecipeBuilder implements RecipeBuilder {
    private final List<Ingredient> ingredients = new ArrayList<>();
    private int inputCount;
    private int energy;
    private final List<ItemStack> outputs = new ArrayList<>();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;

    public static SolidingRecipeBuilder solidingRecipe() {
        return new SolidingRecipeBuilder();
    }

    private SolidingRecipeBuilder() {
        // Private constructor to enforce the use of the static factory method
    }

    public SolidingRecipeBuilder addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }

    public SolidingRecipeBuilder setInputCount(int count) {
        this.inputCount = count;
        return this;
    }

    public SolidingRecipeBuilder addOutput(ItemStack output) {
        this.outputs.add(output);
        return this;
    }

    public SolidingRecipeBuilder setEnergy(int energy) {
        this.energy = energy;
        return this;
    }

    @Override
    public SolidingRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public SolidingRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getResult() {
        // Return the first output item as the representative result
        return this.outputs.isEmpty() ? Items.AIR : this.outputs.get(0).getItem();
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        // Build the advancement
        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);

        // Create the recipe instance
        SolidingRecipe recipe = new SolidingRecipe(
                this.ingredients,
                this.outputs,
                this.inputCount,
                this.energy
        );

        // Pass the recipe and advancement to the output
        output.accept(id, recipe, advancement.build(id.withPrefix("recipes/")));
    }
}
