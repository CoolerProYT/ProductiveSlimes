package com.coolerpromc.productiveslimes.recipe;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.recipe.custom.MultipleRecipeInput;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jline.utils.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DnaSynthesizingRecipe implements Recipe<MultipleRecipeInput> {
    private final List<Ingredient> inputItems;
    private final List<ItemStack> output;
    private final int energy;
    private final int inputCount;

    public DnaSynthesizingRecipe(List<Ingredient> inputItems, List<ItemStack> output, int energy, int inputCount) {
        this.inputItems = inputItems;
        this.output = output;
        this.energy = energy;
        this.inputCount = inputCount;
    }

    @Override
    public boolean matches(MultipleRecipeInput pInput, Level pLevel) {
        List<ItemStack> inputItems = pInput.inputItems();
        if (inputItems.size() != inputItems.size()) {
            return false;
        }

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
    public ItemStack assemble(MultipleRecipeInput pInput, HolderLookup.Provider pRegistries) {
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
        return ModRecipes.DNA_SYNTHESIZING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.DNA_SYNTHESIZING_TYPE.get();
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

    public static class Serializer implements RecipeSerializer<DnaSynthesizingRecipe>{
        public static final DnaSynthesizingRecipe.Serializer INSTANCE = new DnaSynthesizingRecipe.Serializer();
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "dna_synthesizing");
        private final MapCodec<DnaSynthesizingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(recipe -> recipe.inputItems),
                ItemStack.CODEC.listOf().fieldOf("output").forGetter(recipe -> recipe.output),
                Codec.INT.fieldOf("energy").forGetter(recipe -> recipe.energy),
                Codec.INT.fieldOf("inputCount").forGetter(recipe -> recipe.inputCount)
        ).apply(instance, DnaSynthesizingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, DnaSynthesizingRecipe> STREAM_CODEC = StreamCodec.of(
                DnaSynthesizingRecipe.Serializer::toNetwork, DnaSynthesizingRecipe.Serializer::fromNetwork
        );

        private static DnaSynthesizingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
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

            int inputCount = buffer.readInt();

            return new DnaSynthesizingRecipe(inputItems, result, energy, inputCount);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, DnaSynthesizingRecipe recipe) {
            buffer.writeVarInt(recipe.inputItems.size());
            for (Ingredient ingredient : recipe.inputItems) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
            }

            buffer.writeVarInt(recipe.output.size());
            for (ItemStack itemStack : recipe.output) {
                ItemStack.STREAM_CODEC.encode(buffer, itemStack);
            }

            buffer.writeInt(recipe.energy);
            buffer.writeInt(recipe.inputCount);
        }

        @Override
        public MapCodec<DnaSynthesizingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, DnaSynthesizingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
