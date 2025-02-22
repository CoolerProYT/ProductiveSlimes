package com.coolerpromc.productiveslimes.compat.rei.Squeezing;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.recipe.SqueezingRecipe;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record SqueezingRecipeDisplay(RecipeHolder<SqueezingRecipe> recipe) implements Display {
    public static final CategoryIdentifier<? extends SqueezingRecipeDisplay> CATEGORY = CategoryIdentifier.of(ProductiveSlimes.MODID, "squeezing");

    public static final DisplaySerializer<SqueezingRecipeDisplay> SERIALIZER = DisplaySerializer.of(
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    ResourceLocation.CODEC.fieldOf("recipeId").forGetter(display -> display.recipe.id().location()),
                    SqueezingRecipe.Serializer.CODEC.fieldOf("ingredients").forGetter(display -> display.recipe.value())
            ).apply(instance, (recipeId, ingredients) -> new SqueezingRecipeDisplay(new RecipeHolder<>(ResourceKey.create(Registries.RECIPE, recipeId), ingredients)))),
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC,
                    display -> display.recipe.id().location(),
                    SqueezingRecipe.Serializer.STREAM_CODEC,
                    display -> display.recipe.value(),
                    (resourceLocation, squeezingRecipe) -> new SqueezingRecipeDisplay(new RecipeHolder<>(ResourceKey.create(Registries.RECIPE, resourceLocation), squeezingRecipe))
            )
    );

    @Override
    public List<EntryIngredient> getInputEntries() {
        return EntryIngredients.ofIngredients(recipe.value().inputItems());
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        List<ItemStack> outputs = recipe.value().output();
        List<EntryIngredient> entryIngredients = new ArrayList<>();
        for (ItemStack output : outputs) {
            entryIngredients.add(EntryIngredients.of(output));
        }
        return entryIngredients;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CATEGORY;
    }

    @Override
    public Optional<ResourceLocation> getDisplayLocation() {
        return Optional.of(recipe.id().location());
    }

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return SERIALIZER;
    }
}