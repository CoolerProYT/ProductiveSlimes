package com.coolerpromc.productiveslimes.recipe;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import java.util.ArrayList;
import java.util.List;

public class SqueezingRecipe implements Recipe<SingleRecipeInput>{
    private final NonNullList<Ingredient> inputItems;
    private final List<ItemStack> output;
    private final int energy;

    public SqueezingRecipe(List<Ingredient> inputItems, List<ItemStack> output, int energy) {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.addAll(inputItems);
        this.inputItems = ingredients;
        this.output = output;
        this.energy = energy;
    }

    @Override
    public boolean matches(SingleRecipeInput pInput, Level pLevel) {
        if (pLevel.isClientSide()){
            return false;
        }
        return inputItems.getFirst().test(pInput.getItem(0));
    }

    @Override
    public ItemStack assemble(SingleRecipeInput pInput, HolderLookup.Provider pRegistries) {
        return output.isEmpty() ? ItemStack.EMPTY : output.get(0).copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<SingleRecipeInput>> getSerializer() {
        return (RecipeSerializer<? extends Recipe<SingleRecipeInput>>) ModRecipes.SQUEEZING_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<SingleRecipeInput>> getType() {
        return ModRecipes.SQUEEZING_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.create(inputItems);
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return null;
    }

    public NonNullList<Ingredient> getInputItems() {
        return inputItems;
    }

    public List<ItemStack> getOutputs() {
        return output;
    }
    public int getEnergy() {
        return energy;
    }
    public static class Serializer implements RecipeSerializer<SqueezingRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "squeezing");
        private final MapCodec<SqueezingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(recipe -> recipe.inputItems),
                ItemStack.CODEC.listOf().fieldOf("output").forGetter(recipe -> recipe.output),
                Codec.INT.fieldOf("energy").forGetter(recipe -> recipe.energy)
        ).apply(instance, SqueezingRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, SqueezingRecipe> STREAM_CODEC = StreamCodec.of(
                SqueezingRecipe.Serializer::toNetwork, SqueezingRecipe.Serializer::fromNetwork
        );
        private static SqueezingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            int ingredientCount = buffer.readVarInt();
            List<Ingredient> inputItems = new ArrayList<>(ingredientCount);
            for (int i = 0; i < ingredientCount; i++) {
                inputItems.add(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
            }
            int outputCount = buffer.readVarInt();
            List<ItemStack> result = new ArrayList<>(outputCount);
            for (int i = 0; i < outputCount; i++) {
                result.add(ItemStack.STREAM_CODEC.decode(buffer));
            }
            int energy = buffer.readInt();
            return new SqueezingRecipe(inputItems, result, energy);
        }
        private static void toNetwork(RegistryFriendlyByteBuf buffer, SqueezingRecipe recipe) {
            buffer.writeVarInt(recipe.inputItems.size());
            for (Ingredient ingredient : recipe.inputItems) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
            }
            buffer.writeVarInt(recipe.output.size());
            for (ItemStack itemStack : recipe.output) {
                ItemStack.STREAM_CODEC.encode(buffer, itemStack);
            }
            buffer.writeInt(recipe.energy);
        }
        @Override
        public MapCodec<SqueezingRecipe> codec() {
            return CODEC;
        }
        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SqueezingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}