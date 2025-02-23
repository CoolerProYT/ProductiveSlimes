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
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.FluidStack;

public record MeltingRecipe(SizedIngredient inputItems, FluidStack output, int energy) implements Recipe<SingleRecipeInput>{
    @Override
    public boolean matches(SingleRecipeInput pInput, Level pLevel) {
        if (pLevel.isClientSide()){
            return false;
        }

        return inputItems.test(pInput.getItem(0));
    }

    @Override
    public ItemStack assemble(SingleRecipeInput pInput, HolderLookup.Provider pRegistries) {
        return output.isEmpty() ? ItemStack.EMPTY : output.getFluid().getBucket().getDefaultInstance();
    }

    @Override
    public RecipeSerializer<? extends Recipe<SingleRecipeInput>> getSerializer() {
        return ModRecipes.MELTING_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<SingleRecipeInput>> getType() {
        return ModRecipes.MELTING_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipes.MELTING_CATEGORY.get();
    }


    public static class Serializer implements RecipeSerializer<MeltingRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "melting");
        public static final MapCodec<MeltingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                SizedIngredient.NESTED_CODEC.fieldOf("ingredients").forGetter(recipe -> recipe.inputItems),
                FluidStack.CODEC.fieldOf("output").forGetter(recipe -> recipe.output),
                Codec.INT.fieldOf("energy").forGetter(recipe -> recipe.energy)
        ).apply(instance, MeltingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, MeltingRecipe> STREAM_CODEC = StreamCodec.of(
                MeltingRecipe.Serializer::toNetwork, MeltingRecipe.Serializer::fromNetwork
        );

        private static MeltingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            SizedIngredient inputItems = SizedIngredient.STREAM_CODEC.decode(buffer);
            FluidStack result = FluidStack.STREAM_CODEC.decode(buffer);
            int energy = buffer.readInt();

            return new MeltingRecipe(inputItems, result, energy);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, MeltingRecipe recipe) {
            SizedIngredient.STREAM_CODEC.encode(buffer, recipe.inputItems);
            FluidStack.STREAM_CODEC.encode(buffer, recipe.output);
            buffer.writeInt(recipe.energy);
        }

        @Override
        public MapCodec<MeltingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MeltingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
