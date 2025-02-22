package com.coolerpromc.productiveslimes.compat.rei.Melting;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.recipe.MeltingRecipe;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record MeltingRecipeDisplay(RecipeHolder<MeltingRecipe> recipe) implements Display {
    public static final CategoryIdentifier<? extends MeltingRecipeDisplay> CATEGORY = CategoryIdentifier.of(ProductiveSlimes.MODID, "melting");

    public static final DisplaySerializer<MeltingRecipeDisplay> SERIALIZER = DisplaySerializer.of(
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    ResourceLocation.CODEC.fieldOf("recipeId").forGetter(display -> display.recipe.id().location()),
                    MeltingRecipe.Serializer.CODEC.fieldOf("ingredients").forGetter(display -> display.recipe.value())
            ).apply(instance, (ResourceLocation recipeId, MeltingRecipe recipe) -> new MeltingRecipeDisplay(new RecipeHolder<>(ResourceKey.create(Registries.RECIPE, recipeId), recipe)))),
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC,
                    display -> display.recipe.id().location(),
                    MeltingRecipe.Serializer.STREAM_CODEC,
                    display -> display.recipe.value(),
                    (recipeId, recipe) -> new MeltingRecipeDisplay(new RecipeHolder<>(ResourceKey.create(Registries.RECIPE, recipeId), recipe))
            )
    );

    @Override
    public List<EntryIngredient> getInputEntries() {
        List<EntryIngredient> entryIngredients = new ArrayList<>();
        entryIngredients.add(EntryIngredients.of(new ItemStack(recipe.value().inputItems().ingredient().getValues().get(0), recipe.value().inputItems().count())));
        entryIngredients.add(EntryIngredients.of(new ItemStack(Items.BUCKET, recipe.value().output().getFirst().getCount())));
        return entryIngredients;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return List.of(EntryIngredients.ofItemStacks(recipe.value().output()));
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
