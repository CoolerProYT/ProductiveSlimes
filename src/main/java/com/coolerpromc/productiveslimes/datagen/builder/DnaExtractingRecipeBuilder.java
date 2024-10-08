package com.coolerpromc.productiveslimes.datagen.builder;

import com.coolerpromc.productiveslimes.recipe.DnaExtractingRecipe;
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

public class DnaExtractingRecipeBuilder implements RecipeBuilder {
    private final List<Ingredient> ingredients = new ArrayList<>();
    private int inputCount;
    private int energy;
    private float outputChance;
    private final List<ItemStack> outputs = new ArrayList<>();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;

    public static DnaExtractingRecipeBuilder dnaExtractingRecipe() {
        return new DnaExtractingRecipeBuilder();
    }

    private DnaExtractingRecipeBuilder() {
        // Private constructor to enforce the use of the static factory method
    }

    public DnaExtractingRecipeBuilder addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }

    public DnaExtractingRecipeBuilder setInputCount(int count) {
        this.inputCount = count;
        return this;
    }

    public DnaExtractingRecipeBuilder addOutput(ItemStack output) {
        this.outputs.add(output);
        return this;
    }

    public DnaExtractingRecipeBuilder setEnergy(int energy) {
        this.energy = energy;
        return this;
    }

    public DnaExtractingRecipeBuilder setOutputChance(float outputChance) {
        this.outputChance = outputChance;
        return this;
    }

    @Override
    public DnaExtractingRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public DnaExtractingRecipeBuilder group(@Nullable String group) {
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
        DnaExtractingRecipe recipe = new DnaExtractingRecipe(
                this.ingredients,
                this.outputs,
                this.inputCount,
                this.energy,
                this.outputChance
        );

        // Pass the recipe and advancement to the output
        output.accept(id, recipe, advancement.build(id.withPrefix("recipes/")));
    }
}
