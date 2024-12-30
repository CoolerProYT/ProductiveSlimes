package com.coolerpromc.productiveslimes.datagen.builder;

import com.coolerpromc.productiveslimes.recipe.ModRecipes;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class DnaExtractingRecipeBuilder implements RecipeBuilder {
    private final List<Ingredient> ingredients = new ArrayList<>();
    private int inputCount;
    private int energy;
    private float outputChance;
    private final List<ItemStack> outputs = new ArrayList<>();
    private final List<JsonObject> outputJson = new ArrayList<>();
    private final Map<String, CriterionTriggerInstance> criteria = new LinkedHashMap<>();

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
        JsonObject outputJson = new JsonObject();
        outputJson.addProperty("item", output.getDescriptionId().substring(output.getDescriptionId().indexOf(".") + 1).replace('.', ':'));
        outputJson.addProperty("count", output.getCount());
        this.outputs.add(output);
        this.outputJson.add(outputJson);
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
    public DnaExtractingRecipeBuilder unlockedBy(String name, CriterionTriggerInstance criterion) {
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
    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation resourceLocation) {
        consumer.accept(new Result(resourceLocation, ingredients, outputJson, inputCount, energy, outputChance));
    }

    public static class Result implements FinishedRecipe{
        private final ResourceLocation id;
        private final List<Ingredient> ingredients;
        private final List<JsonObject> outputs;
        private final int inputCount;
        private final int energy;
        private final float outputChance;

        public Result(ResourceLocation id, List<Ingredient> ingredients, List<JsonObject> outputs, int inputCount, int energy, float outputChance) {
            this.id = id;
            this.ingredients = ingredients;
            this.outputs = outputs;
            this.inputCount = inputCount;
            this.energy = energy;
            this.outputChance = outputChance;
        }

        @Override
        public void serializeRecipeData(JsonObject jsonObject) {
            jsonObject.addProperty("type", "productiveslimes:dna_extracting");
            jsonObject.addProperty("energy", energy);

            JsonArray ingredientArray = new JsonArray();
            for (Ingredient ingredient : ingredients) {
                ingredientArray.add(ingredient.toJson());
            }
            jsonObject.add("ingredients", ingredientArray);
            jsonObject.addProperty("inputCount", inputCount);

            JsonArray outputArray = new JsonArray();
            for (JsonObject output : outputs) {
                outputArray.add(output);
            }
            jsonObject.add("output", outputArray);

            jsonObject.addProperty("outputChance", outputChance);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipes.DNA_EXTRACTING_SERIALIZER.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
