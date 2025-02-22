package com.coolerpromc.productiveslimes.compat.rei.DnaSynthesizing;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.recipe.DnaSynthesizingRecipe;
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
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record DnaSynthesizingRecipeDisplay(RecipeHolder<DnaSynthesizingRecipe> recipe) implements Display {
    public static final CategoryIdentifier<? extends DnaSynthesizingRecipeDisplay> CATEGORY = CategoryIdentifier.of(ProductiveSlimes.MODID, "dna_synthesizing");

    public static final DisplaySerializer<DnaSynthesizingRecipeDisplay> SERIALIZER = DisplaySerializer.of(
            RecordCodecBuilder.mapCodec(instance ->
                    instance.group(
                            ResourceLocation.CODEC.fieldOf("recipeId").forGetter(display ->
                                    display.recipe.id().location()
                            ),
                            DnaSynthesizingRecipe.Serializer.CODEC.fieldOf("ingredients").forGetter(display ->
                                    display.recipe.value()
                            )
                    ).apply(instance, (ResourceLocation recipeId, DnaSynthesizingRecipe recipe) ->
                            new DnaSynthesizingRecipeDisplay(new RecipeHolder<>(
                                    ResourceKey.create(Registries.RECIPE, recipeId), recipe
                            ))
                    )
            ),
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC,
                    display -> display.recipe.id().location(),
                    DnaSynthesizingRecipe.Serializer.STREAM_CODEC,
                    display -> display.recipe.value(),
                    (recipeId, recipe) ->
                            new DnaSynthesizingRecipeDisplay(new RecipeHolder<>(
                                    ResourceKey.create(Registries.RECIPE, recipeId), recipe
                            ))
            )
    );

    @Override
    public List<EntryIngredient> getInputEntries() {
        List<SizedIngredient> inputs = recipe.value().inputItems();
        List<EntryIngredient> entryIngredients = new ArrayList<>();
        for (SizedIngredient input : inputs) {
            entryIngredients.add(EntryIngredients.of(input.ingredient().getValues().get(0).value(), input.count()));
        }
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
