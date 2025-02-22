package com.coolerpromc.productiveslimes.recipe;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public record DnaExtractingRecipe(Ingredient inputItems, List<ItemStack> output, int energy, float outputChance) implements Recipe<SingleRecipeInput> {
    @Override
    public boolean matches(SingleRecipeInput pInput, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }

        return inputItems.test(pInput.getItem(0));
    }

    @Override
    public ItemStack assemble(SingleRecipeInput pInput, HolderLookup.Provider pRegistries) {
        return output.isEmpty() ? ItemStack.EMPTY : output.get(0).copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<SingleRecipeInput>> getSerializer() {
        return ModRecipes.DNA_EXTRACTING_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<SingleRecipeInput>> getType() {
        return ModRecipes.DNA_EXTRACTING_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipes.DNA_EXTRACTING_CATEGORY.get();
    }


    public static class Serializer implements RecipeSerializer<DnaExtractingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "dna_extracting");
        public static final MapCodec<DnaExtractingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC.fieldOf("ingredients").forGetter(recipe -> recipe.inputItems),
                ItemStack.CODEC.listOf().fieldOf("output").forGetter(recipe -> recipe.output),
                Codec.INT.fieldOf("energy").forGetter(recipe -> recipe.energy),
                Codec.FLOAT.fieldOf("outputChance").forGetter(recipe -> recipe.outputChance)
        ).apply(instance, DnaExtractingRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, DnaExtractingRecipe> STREAM_CODEC = StreamCodec.of(
                DnaExtractingRecipe.Serializer::toNetwork, DnaExtractingRecipe.Serializer::fromNetwork
        );

        private static DnaExtractingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            Ingredient inputItems = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);

            int outputCount = buffer.readVarInt();
            List<ItemStack> result = new ArrayList<>(outputCount);
            for (int i = 0; i < outputCount; i++) {
                result.add(ItemStack.STREAM_CODEC.decode(buffer));
            }

            int energy = buffer.readInt();

            float outputChance = buffer.readFloat();

            return new DnaExtractingRecipe(inputItems, result, energy, outputChance);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, DnaExtractingRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.inputItems);


            buffer.writeVarInt(recipe.output.size());
            for (ItemStack itemStack : recipe.output) {
                ItemStack.STREAM_CODEC.encode(buffer, itemStack);
            }

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
