package com.coolerpromc.productiveslimes.datagen.builder;

import com.coolerpromc.productiveslimes.recipe.SqueezingRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqueezingRecipeBuilder implements RecipeBuilder {
    private final List<Ingredient> ingredients = new ArrayList<>();
    private int energy;
    private final List<ItemStack> outputs = new ArrayList<>();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;
    public static SqueezingRecipeBuilder squeezingRecipe() {
        return new SqueezingRecipeBuilder();
    }
    private SqueezingRecipeBuilder() {
        // Private constructor to enforce the use of the static method
    }
    public SqueezingRecipeBuilder addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }
    public SqueezingRecipeBuilder addOutput(ItemStack output) {
        this.outputs.add(output);
        return this;
    }
    public SqueezingRecipeBuilder setEnergy(int energy) {
        this.energy = energy;
        return this;
    }
    @Override
    public SqueezingRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }
    @Override
    public SqueezingRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }
    @Override
    public Item getResult() {
        return this.outputs.isEmpty() ? Items.AIR : this.outputs.get(0).getItem();
    }
    @Override
    public void save(RecipeOutput pRecipeOutput, ResourceKey<Recipe<?>> pId) {
        Advancement.Builder advancement = pRecipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId))
                .rewards(AdvancementRewards.Builder.recipe(pId))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);
        // Create the recipe instance
        SqueezingRecipe recipe = new SqueezingRecipe(
                this.ingredients,
                this.outputs,
                this.energy
        );
        // Pass the recipe and advancement to the output
        pRecipeOutput.accept(pId, recipe, advancement.build(Identifier.fromNamespaceAndPath(pId.identifier().getNamespace(), "recipes/" + pId.identifier().getPath())));
    }
}