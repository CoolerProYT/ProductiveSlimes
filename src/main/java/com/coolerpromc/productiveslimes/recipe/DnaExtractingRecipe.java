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

public class DnaExtractingRecipe implements Recipe<SingleRecipeInput>{
    private final NonNullList<Ingredient> inputItems;
    private final List<ItemStack> output;
    private final int inputCount;
    private final int energy;
    private final float outputChance;

    public DnaExtractingRecipe(List<Ingredient> inputItems, List<ItemStack> output, int inputCount, int energy, float outputChance) {
        this.inputItems = NonNullList.of(Ingredient.EMPTY, inputItems.toArray(new Ingredient[0]));
        this.output = output;
        this.inputCount = inputCount;
        this.energy = energy;
        this.outputChance = outputChance;
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
        return ModRecipes.DNA_EXTRACTING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.DNA_EXTRACTING_TYPE.get();
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

    public float getOutputChance() {
        return outputChance;
    }

    public static class Serializer implements RecipeSerializer<DnaExtractingRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "dna_extracting");
        private final MapCodec<DnaExtractingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(recipe -> recipe.inputItems),
                ItemStack.CODEC.listOf().fieldOf("output").forGetter(recipe -> recipe.output),
                Codec.INT.fieldOf("inputCount").forGetter(recipe -> recipe.inputCount),
                Codec.INT.fieldOf("energy").forGetter(recipe -> recipe.energy),
                Codec.FLOAT.fieldOf("outputChance").forGetter(recipe -> recipe.outputChance)
        ).apply(instance, DnaExtractingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, DnaExtractingRecipe> STREAM_CODEC = StreamCodec.of(
                DnaExtractingRecipe.Serializer::toNetwork, DnaExtractingRecipe.Serializer::fromNetwork
        );

        private static DnaExtractingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
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

            int energy = buffer.readInt();

            float outputChance = buffer.readFloat();

            return new DnaExtractingRecipe(inputItems, result, inputCount, energy, outputChance);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, DnaExtractingRecipe recipe) {
            buffer.writeVarInt(recipe.inputItems.size());
            for (Ingredient ingredient : recipe.inputItems) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
            }

            buffer.writeVarInt(recipe.output.size());
            for (ItemStack itemStack : recipe.output) {
                ItemStack.STREAM_CODEC.encode(buffer, itemStack);
            }

            buffer.writeInt(recipe.inputCount);
            buffer.writeInt(recipe.energy);
            buffer.writeFloat(recipe.outputChance);
        }

        @Override
        public MapCodec<DnaExtractingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, DnaExtractingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
