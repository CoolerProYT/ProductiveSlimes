package com.coolerpromc.productiveslimes.datagen.builder;

import com.coolerpromc.productiveslimes.recipe.MeltingRecipe;
import com.google.gson.JsonObject;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MeltingRecipeBuilder implements RecipeBuilder {
    private SizedIngredient ingredients;
    private int energy;
    private FluidStack outputs;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;

    public static MeltingRecipeBuilder meltingRecipe() {
        return new MeltingRecipeBuilder();
    }

    private MeltingRecipeBuilder() {
        // Private constructor to enforce the use of the static method
    }

    public MeltingRecipeBuilder addIngredient(SizedIngredient ingredient) {
        this.ingredients = ingredient;
        return this;
    }

    public MeltingRecipeBuilder addOutput(FluidStack output) {
        this.outputs = output;
        return this;
    }

    public MeltingRecipeBuilder setEnergy(int energy) {
        this.energy = energy;
        return this;
    }

    @Override
    public MeltingRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public MeltingRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getResult() {
        return this.outputs.isEmpty() ? Items.AIR : this.outputs.getFluid().getBucket();
    }

    @Override
    public void save(RecipeOutput pRecipeOutput, ResourceKey<Recipe<?>> p_379998_) {
        Advancement.Builder advancement = pRecipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(p_379998_))
                .rewards(AdvancementRewards.Builder.recipe(p_379998_))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);

        MeltingRecipe recipe = new MeltingRecipe(
                this.ingredients,
                this.outputs,
                this.energy
        );

        pRecipeOutput.accept(p_379998_, recipe, advancement.build(p_379998_.location().withPrefix("recipes/")));
    }
}
