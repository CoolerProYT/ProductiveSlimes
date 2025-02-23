package com.coolerpromc.productiveslimes.recipe;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.recipe.custom.SingleFluidRecipeInput;
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
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public record SolidingRecipe(FluidStack fluidStack, List<ItemStack> output, int energy) implements Recipe<SingleFluidRecipeInput>{
    @Override
    public boolean matches(SingleFluidRecipeInput pInput, Level pLevel) {
        FluidStack inputStack = pInput.fluidStack();
        return inputStack.getFluid().isSame(fluidStack.getFluid()) && inputStack.getAmount() >= fluidStack.getAmount();
    }

    @Override
    public ItemStack assemble(SingleFluidRecipeInput pInput, HolderLookup.Provider pRegistries) {
        return output.isEmpty() ? ItemStack.EMPTY : output.get(0).copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<SingleFluidRecipeInput>> getSerializer() {
        return ModRecipes.SOLIDING_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<SingleFluidRecipeInput>> getType() {
        return ModRecipes.SOLIDING_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipes.SOLIDING_CATEGORY.get();
    }

    public static class Serializer implements RecipeSerializer<SolidingRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "soliding");
        public static final MapCodec<SolidingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                FluidStack.CODEC.fieldOf("ingredients").forGetter(recipe -> recipe.fluidStack),
                ItemStack.CODEC.listOf().fieldOf("output").forGetter(recipe -> recipe.output),
                Codec.INT.fieldOf("energy").forGetter(recipe -> recipe.energy)
        ).apply(instance, SolidingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, SolidingRecipe> STREAM_CODEC = StreamCodec.of(
                SolidingRecipe.Serializer::toNetwork, SolidingRecipe.Serializer::fromNetwork
        );

        private static SolidingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            FluidStack inputItems = FluidStack.STREAM_CODEC.decode(buffer);

            int outputCount = buffer.readVarInt();
            List<ItemStack> result = new ArrayList<>(outputCount);
            for (int i = 0; i < outputCount; i++) {
                result.add(ItemStack.STREAM_CODEC.decode(buffer));
            }

            int energy = buffer.readInt();

            return new SolidingRecipe(inputItems, result, energy);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, SolidingRecipe recipe) {
            FluidStack.STREAM_CODEC.encode(buffer, recipe.fluidStack);

            buffer.writeVarInt(recipe.output.size());
            for (ItemStack itemStack : recipe.output) {
                ItemStack.STREAM_CODEC.encode(buffer, itemStack);
            }

            buffer.writeInt(recipe.energy);
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
