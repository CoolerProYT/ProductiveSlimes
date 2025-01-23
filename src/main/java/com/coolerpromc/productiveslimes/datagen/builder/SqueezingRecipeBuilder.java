package com.coolerpromc.productiveslimes.datagen.builder;

import com.coolerpromc.productiveslimes.recipe.ModRecipes;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SqueezingRecipeBuilder {
    private final List<Ingredient> ingredients = new ArrayList<>();
    private int energy;
    private final List<ItemStack> outputs = new ArrayList<>();
    private final List<JsonObject> outputJson = new ArrayList<>();
    private final Map<String, ICriterionInstance> criteria = new LinkedHashMap<>();

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
        JsonObject outputJson = new JsonObject();
        outputJson.addProperty("item", output.getDescriptionId().substring(output.getDescriptionId().indexOf(".") + 1).replace('.', ':'));
        outputJson.addProperty("count", output.getCount());
        this.outputs.add(output);
        this.outputJson.add(outputJson);
        return this;
    }

    public SqueezingRecipeBuilder setEnergy(int energy) {
        this.energy = energy;
        return this;
    }

    public SqueezingRecipeBuilder unlockedBy(String name, ICriterionInstance criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    public SqueezingRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    public Item getResult() {
        return this.outputs.isEmpty() ? Items.AIR : this.outputs.get(0).getItem();
    }

    public void save(Consumer<IFinishedRecipe> consumer, ResourceLocation resourceLocation) {
        consumer.accept(new Result(resourceLocation, ingredients, outputJson, energy));
    }

    public static class Result implements IFinishedRecipe {
        private final ResourceLocation id;
        private final List<Ingredient> ingredients;
        private final List<JsonObject> outputs;
        private final int energy;

        public Result(ResourceLocation id, List<Ingredient> ingredients, List<JsonObject> outputs, int energy) {
            this.id = id;
            this.ingredients = ingredients;
            this.outputs = outputs;
            this.energy = energy;
        }

        @Override
        public void serializeRecipeData(JsonObject jsonObject) {
            jsonObject.addProperty("type", "productiveslimes:squeezing");
            jsonObject.addProperty("energy", energy);

            JsonArray ingredientArray = new JsonArray();
            for (Ingredient ingredient : ingredients) {
                ingredientArray.add(ingredient.toJson());
            }
            jsonObject.add("ingredients", ingredientArray);

            JsonArray outputArray = new JsonArray();
            for (JsonObject output : outputs) {
                outputArray.add(output);
            }
            jsonObject.add("output", outputArray);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public IRecipeSerializer<?> getType() {
            return ModRecipes.SQUEEZING_SERIALIZER.get();
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