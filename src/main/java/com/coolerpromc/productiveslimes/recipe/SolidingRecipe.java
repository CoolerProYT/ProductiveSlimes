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

public class SolidingRecipe implements Recipe<SingleRecipeInput>{
    private final NonNullList<Ingredient> inputItems;
    private final List<ItemStack> output;
    private final int inputCount;

    public SolidingRecipe(List<Ingredient> inputItems, List<ItemStack> output, int inputCount) {
        this.inputItems = NonNullList.of(Ingredient.EMPTY, inputItems.toArray(new Ingredient[0]));
        this.output = output;
        this.inputCount = inputCount;
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
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return output.isEmpty() ? ItemStack.EMPTY : output.get(0).copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.SOLIDING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.SOLIDING_TYPE.get();
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

    public static class Serializer implements RecipeSerializer<SolidingRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "soliding");
        private final MapCodec<SolidingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(recipe -> recipe.inputItems),
                ItemStack.CODEC.listOf().fieldOf("output").forGetter(recipe -> recipe.output),
                Codec.INT.fieldOf("inputCount").forGetter(recipe -> recipe.inputCount)
        ).apply(instance, SolidingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, SolidingRecipe> STREAM_CODEC = StreamCodec.of(
                SolidingRecipe.Serializer::toNetwork, SolidingRecipe.Serializer::fromNetwork
        );

        private static SolidingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
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

            int inputCount = buffer.readInt();

            return new SolidingRecipe(inputItems, result, inputCount);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, SolidingRecipe recipe) {
            buffer.writeVarInt(recipe.inputItems.size());
            for (Ingredient ingredient : recipe.inputItems) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
            }

            buffer.writeVarInt(recipe.output.size());
            for (ItemStack itemStack : recipe.output) {
                ItemStack.STREAM_CODEC.encode(buffer, itemStack);
            }

            buffer.writeInt(recipe.inputCount);
        }

        @Override
        public MapCodec<SolidingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SolidingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
