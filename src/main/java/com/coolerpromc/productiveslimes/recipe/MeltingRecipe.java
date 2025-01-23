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
import java.util.List;

public class MeltingRecipe implements IRecipe<Inventory> {
    private final NonNullList<Ingredient> inputItems;
    private final List<ItemStack> output;
    private final int inputCount;
    private final int energy;
    private final ResourceLocation id;

    public MeltingRecipe(NonNullList<Ingredient> inputItems, List<ItemStack> output, int inputCount, int energy, ResourceLocation id) {
        this.inputItems = inputItems;
        this.output = output;
        this.inputCount = inputCount;
        this.energy = energy;
        this.id = id;
    }

    @Override
    public boolean matches(Inventory pInput, World pLevel) {
        if (pLevel.isClientSide()){
            return false;
        }

        return inputItems.get(0).test(pInput.getItem(0));
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
        return ModRecipes.MELTING_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputItems;
    }

    public List<ItemStack> getOutputs() {
        return output;
    }

    public int getInputCount() {
        return inputCount;
    }

    public int getEnergy() {
        return energy;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public static class Type implements IRecipeType<MeltingRecipe> {
        public static final MeltingRecipe.Type INSTANCE = new MeltingRecipe.Type();
        public static final String ID = "melting";
    }

    public static class Serializer implements IRecipeSerializer<MeltingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(ProductiveSlimes.MODID, "melting");
        @Override
        public MeltingRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
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

            return new MeltingRecipe(inputItems, output, inputCount, energy, resourceLocation);
        }

        @Override
        public @Nullable MeltingRecipe fromNetwork(ResourceLocation resourceLocation, PacketBuffer buffer) {
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

            return new MeltingRecipe(inputItems, output, inputCount, energy, resourceLocation);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, MeltingRecipe recipe) {
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
