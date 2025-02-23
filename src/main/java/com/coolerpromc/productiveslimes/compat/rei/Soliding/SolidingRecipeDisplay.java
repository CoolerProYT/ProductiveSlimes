package com.coolerpromc.productiveslimes.compat.rei.Soliding;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.recipe.SolidingRecipe;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record SolidingRecipeDisplay(RecipeHolder<SolidingRecipe> recipe) implements Display {
    public static final CategoryIdentifier<? extends SolidingRecipeDisplay> CATEGORY = CategoryIdentifier.of(ProductiveSlimes.MODID, "soliding");

    public static final DisplaySerializer<SolidingRecipeDisplay> SERIALIZER = DisplaySerializer.of(
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    ResourceLocation.CODEC.fieldOf("recipeId").forGetter(display -> display.recipe.id().location()),
                    SolidingRecipe.Serializer.CODEC.fieldOf("ingredients").forGetter(display -> display.recipe.value())
            ).apply(instance, (resourceLocation, solidingRecipe) -> new SolidingRecipeDisplay(new RecipeHolder<>(ResourceKey.create(Registries.RECIPE, resourceLocation), solidingRecipe)))),
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC,
                    display -> display.recipe.id().location(),
                    SolidingRecipe.Serializer.STREAM_CODEC,
                    display -> display.recipe.value(),
                    (recipeId, recipe) -> new SolidingRecipeDisplay(new RecipeHolder<>(ResourceKey.create(Registries.RECIPE, recipeId), recipe))
            )
    );

    @Override
    public List<EntryIngredient> getInputEntries() {
        return List.of(EntryIngredients.of(recipe.value().fluidStack().getFluid(), recipe.value().fluidStack().getAmount()));
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
