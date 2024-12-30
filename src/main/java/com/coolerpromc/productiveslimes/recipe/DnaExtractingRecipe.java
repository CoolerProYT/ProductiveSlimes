package com.coolerpromc.productiveslimes.recipe;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.istack.internal.Nullable;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class DnaExtractingRecipe implements IRecipe<IInventory> {
    private final NonNullList<Ingredient> inputItems;
    private final List<ItemStack> output;
    private final int inputCount;
    private final int energy;
    private final float outputChance;
    private final ResourceLocation id;

    public DnaExtractingRecipe(List<Ingredient> inputItems, List<ItemStack> output, int inputCount, int energy, float outputChance, ResourceLocation id) {
        this.inputItems = NonNullList.of(Ingredient.EMPTY, inputItems.toArray(new Ingredient[0]));
        this.output = output;
        this.inputCount = inputCount;
        this.energy = energy;
        this.outputChance = outputChance;
        this.id = id;
    }

    @Override
    public boolean matches(IInventory iInventory, World world) {
        if (world.isClientSide()){
            return false;
        }

        return inputItems.get(0).test(iInventory.getItem(0));
    }

    @Override
    public ItemStack assemble(IInventory iInventory) {
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
        return ModRecipes.DNA_EXTRACTING_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputItems;
    }

    @Override
    public ResourceLocation getId() {
        return id;
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

    public float getOutputChance() {
        return outputChance;
    }

    public static class Type implements IRecipeType<DnaExtractingRecipe> {
        public static final DnaExtractingRecipe.Type INSTANCE = new DnaExtractingRecipe.Type();
        public static final String ID = "dna_extracting";
    }

    public static class Serializer implements IRecipeSerializer<DnaExtractingRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(ProductiveSlimes.MODID, "dna_extracting");

        @Override
        public DnaExtractingRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
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
            float outputChance = JSONUtils.getAsFloat(jsonObject, "outputChance");

            return new DnaExtractingRecipe(inputItems, output, inputCount, energy, outputChance, resourceLocation);
        }

        @Override
        public @Nullable DnaExtractingRecipe fromNetwork(ResourceLocation resourceLocation, PacketBuffer buffer) {
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

            float outputChance = buffer.readFloat();

            return new DnaExtractingRecipe(inputItems, output, inputCount, energy, outputChance, resourceLocation);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, DnaExtractingRecipe recipe) {
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

            buffer.writeFloat(recipe.outputChance);
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
