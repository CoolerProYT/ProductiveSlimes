package com.coolerpromc.productiveslimes.recipe;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DnaSynthesizingRecipe implements IRecipe<Inventory> {
    private final List<Ingredient> inputItems;
    private final List<ItemStack> output;
    private final int energy;
    private final int inputCount;
    private final ResourceLocation id;

    public DnaSynthesizingRecipe(List<Ingredient> inputItems, List<ItemStack> output, int inputCount, int energy, ResourceLocation id) {
        this.inputItems = inputItems;
        this.output = output;
        this.energy = energy;
        this.inputCount = inputCount;
        this.id = id;
    }

    @Override
    public boolean matches(Inventory pInput, World pLevel) {
        List<ItemStack> inputItems = Arrays.asList(pInput.getItem(0), pInput.getItem(1), pInput.getItem(2));

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
    public ItemStack assemble(Inventory simpleContainer) {
        return output.isEmpty() ? ItemStack.EMPTY : output.get(0).copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.isEmpty() ? ItemStack.EMPTY : output.get(0).copy();
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.DNA_SYNTHESIZING_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
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
        return id;
    }

    public static class Type implements IRecipeType<DnaSynthesizingRecipe> {
        public static final DnaSynthesizingRecipe.Type INSTANCE = new DnaSynthesizingRecipe.Type();
        public static final String ID = "dna_synthesizing";
    }

    public static class Serializer implements IRecipeSerializer<DnaSynthesizingRecipe> {
        public static final DnaSynthesizingRecipe.Serializer INSTANCE = new DnaSynthesizingRecipe.Serializer();
        public static final ResourceLocation ID = new ResourceLocation(ProductiveSlimes.MODID, "dna_synthesizing");
        @Override
        public DnaSynthesizingRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            JsonArray ingredients = JSONUtils.getAsJsonArray(jsonObject, "ingredients");
            NonNullList<Ingredient> inputItems = NonNullList.withSize(ingredients.size(), Ingredient.EMPTY);

            for (int i = 0; i < ingredients.size(); i++) {
                inputItems.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            JsonArray outputs = JSONUtils.getAsJsonArray(jsonObject, "output");
            List<ItemStack> output = new ArrayList<>();

            for(JsonElement element : outputs) {
                output.add(ShapedRecipe.itemFromJson(element.getAsJsonObject()));
            }

            int inputCount = JSONUtils.getAsInt(jsonObject, "inputCount");
            int energy = JSONUtils.getAsInt(jsonObject, "energy");

            return new DnaSynthesizingRecipe(inputItems, output, inputCount, energy, resourceLocation);
        }

        @Override
        public @Nullable DnaSynthesizingRecipe fromNetwork(ResourceLocation resourceLocation, PacketBuffer buffer) {
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

            return new DnaSynthesizingRecipe(inputItems, output, inputCount, energy, resourceLocation);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, DnaSynthesizingRecipe recipe) {
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

        @Override
        public IRecipeSerializer<?> setRegistryName(ResourceLocation resourceLocation) {
            return INSTANCE;
        }

        @Nullable
        @Override
        public ResourceLocation getRegistryName() {
            return ID;
        }

        @Override
        public Class<IRecipeSerializer<?>> getRegistryType() {
            return Serializer.castClass(IRecipeSerializer.class);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }
    }
}
