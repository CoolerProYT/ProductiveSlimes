package com.coolerpromc.productiveslimes.recipe;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DnaSynthesizingRecipe implements Recipe<SimpleContainer> {
    private final List<Ingredient> inputItems;
    private final List<ItemStack> output;
    private final int energy;
    private final int inputCount;

    public DnaSynthesizingRecipe(List<Ingredient> inputItems, List<ItemStack> output, int inputCount, int energy) {
        this.inputItems = inputItems;
        this.output = output;
        this.energy = energy;
        this.inputCount = inputCount;
    }

    @Override
    public boolean matches(SimpleContainer pInput, Level pLevel) {
        List<ItemStack> inputItems = List.of(pInput.getItem(0), pInput.getItem(1), pInput.getItem(2));

        List<Ingredient> remainingIngredients = new ArrayList<>(this.inputItems);

        for (ItemStack itemStack : inputItems) {
            if (itemStack.isEmpty()) {
                continue;
            }

            boolean ingredientFound = false;
            Iterator<Ingredient> iterator = remainingIngredients.iterator();

            while (iterator.hasNext()) {
                Ingredient ingredient = iterator.next();
                if (ingredient.test(itemStack)) {
                    iterator.remove();
                    ingredientFound = true;
                    break;
                }
            }

            if (!ingredientFound) {
                return false;
            }
        }

        return remainingIngredients.isEmpty();
    }

    @Override
    public ItemStack assemble(SimpleContainer simpleContainer, RegistryAccess registryAccess) {
        return output.isEmpty() ? ItemStack.EMPTY : output.get(0).copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return output.isEmpty() ? ItemStack.EMPTY : output.get(0).copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.DNA_SYNTHESIZING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public int getEnergy() {
        return energy;
    }

    public List<ItemStack> getOutput() {
        return output;
    }

    public List<Ingredient> getInputItems() {
        return inputItems;
    }

    public int getInputCount() {
        return inputCount;
    }

    @Override
    public ResourceLocation getId() {
        return Serializer.ID;
    }

    public static class Type implements RecipeType<DnaSynthesizingRecipe> {
        public static final DnaSynthesizingRecipe.Type INSTANCE = new DnaSynthesizingRecipe.Type();
        public static final String ID = "dna_synthesizing";
    }

    public static class Serializer implements RecipeSerializer<DnaSynthesizingRecipe>{
        public static final DnaSynthesizingRecipe.Serializer INSTANCE = new DnaSynthesizingRecipe.Serializer();
        public static final ResourceLocation ID = new ResourceLocation(ProductiveSlimes.MODID, "dna_synthesizing");
        @Override
        public DnaSynthesizingRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            JsonArray ingredients = GsonHelper.getAsJsonArray(jsonObject, "ingredients");
            NonNullList<Ingredient> inputItems = NonNullList.withSize(ingredients.size(), Ingredient.EMPTY);

            for (int i = 0; i < ingredients.size(); i++) {
                inputItems.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            JsonArray outputs = GsonHelper.getAsJsonArray(jsonObject, "output");
            List<ItemStack> output = new ArrayList<>();

            for(JsonElement element : outputs) {
                output.add(ShapedRecipe.itemFromJson(element.getAsJsonObject()).getDefaultInstance());
            }

            int inputCount = GsonHelper.getAsInt(jsonObject, "inputCount");
            int energy = GsonHelper.getAsInt(jsonObject, "energy");

            return new DnaSynthesizingRecipe(inputItems, output, inputCount, energy);
        }

        @Override
        public @Nullable DnaSynthesizingRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf buffer) {
            NonNullList<Ingredient> inputItems = NonNullList.withSize(buffer.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputItems.size(); i++) {
                inputItems.set(i, Ingredient.fromNetwork(buffer));
            }

            int outputSize = buffer.readInt();
            List<ItemStack> output = new ArrayList<>();

            for (int i = 0; i < outputSize; i++) {
                output.add(buffer.readItem());
            }

            int inputCount = buffer.readInt();

            int energy = buffer.readInt();

            return new DnaSynthesizingRecipe(inputItems, output, inputCount, energy);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, DnaSynthesizingRecipe recipe) {
            buffer.writeInt(recipe.inputItems.size());

            for (Ingredient ingredient : recipe.inputItems) {
                ingredient.toNetwork(buffer);
            }

            List<ItemStack> output = recipe.output;
            buffer.writeInt(recipe.output.size());

            for (ItemStack stack : output) {
                buffer.writeItem(stack);
            }

            buffer.writeInt(recipe.inputCount);

            buffer.writeInt(recipe.energy);
        }
    }
}
