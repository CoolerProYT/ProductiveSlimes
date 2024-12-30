package com.coolerpromc.productiveslimes.recipe;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SqueezingRecipe implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final List<ItemStack> output;
    private final int energy;
    private final ResourceLocation id;

    public SqueezingRecipe(List<Ingredient> inputItems, List<ItemStack> output, int energy, ResourceLocation id) {
        this.inputItems = NonNullList.of(Ingredient.EMPTY, inputItems.toArray(new Ingredient[0]));
        this.output = output;
        this.energy = energy;
        this.id = id;
    }

    @Override
    public boolean matches(SimpleContainer pInput, Level pLevel) {
        if (pLevel.isClientSide()){
            return false;
        }
        return inputItems.get(0).test(pInput.getItem(0));
    }

    @Override
    public ItemStack assemble(SimpleContainer simpleContainer) {
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
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.SQUEEZING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputItems;
    }

    public List<ItemStack> getOutputs() {
        return output;
    }

    public int getEnergy() {
        return energy;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public static class Type implements RecipeType<SqueezingRecipe> {
        public static final SqueezingRecipe.Type INSTANCE = new SqueezingRecipe.Type();
        public static final String ID = "squeezing";
    }

    public static class Serializer implements RecipeSerializer<SqueezingRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(ProductiveSlimes.MODID, "squeezing");

        @Override
        public SqueezingRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
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

            int energy = GsonHelper.getAsInt(jsonObject, "energy");

            return new SqueezingRecipe(inputItems, output, energy, resourceLocation);
        }

        @Override
        public @Nullable SqueezingRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf buffer) {
            NonNullList<Ingredient> inputItems = NonNullList.withSize(buffer.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputItems.size(); i++) {
                inputItems.set(i, Ingredient.fromNetwork(buffer));
            }

            int outputCount = buffer.readInt();
            List<ItemStack> result = new ArrayList<>();

            for (int i = 0; i < outputCount; i++) {
                result.add(buffer.readItem());
            }

            int energy = buffer.readInt();

            return new SqueezingRecipe(inputItems, result, energy, resourceLocation);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, SqueezingRecipe recipe) {
            buffer.writeInt(recipe.inputItems.size());

            for (Ingredient ingredient : recipe.inputItems) {
                ingredient.toNetwork(buffer);
            }

            buffer.writeInt(recipe.output.size());

            for (ItemStack itemStack : recipe.output) {
                buffer.writeItem(itemStack);
            }
            buffer.writeInt(recipe.energy);
        }

        @Override
        public RecipeSerializer<?> setRegistryName(ResourceLocation resourceLocation) {
            return INSTANCE;
        }

        @Nullable
        @Override
        public ResourceLocation getRegistryName() {
            return ID;
        }

        @Override
        public Class<RecipeSerializer<?>> getRegistryType() {
            return Serializer.castClass(RecipeSerializer.class);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }
    }
}
