package com.coolerpromc.productiveslimes.compat.rei.DnaExtracting;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.recipe.DnaExtractingRecipe;
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

public record DnaExtractingRecipeDisplay(RecipeHolder<DnaExtractingRecipe> recipe) implements Display {
    public static final CategoryIdentifier<? extends DnaExtractingRecipeDisplay> CATEGORY = CategoryIdentifier.of(ProductiveSlimes.MODID, "dna_extracting");
    public static final DisplaySerializer<DnaExtractingRecipeDisplay> SERIALIZER = DisplaySerializer.of(
            RecordCodecBuilder.mapCodec(instance ->
                    instance.group(
                            ResourceLocation.CODEC.fieldOf("recipeId").forGetter(display ->
                                    display.recipe.id().location()
                            ),
                            DnaExtractingRecipe.Serializer.CODEC.fieldOf("ingredients").forGetter(display ->
                                    display.recipe.value()
                            )
                    ).apply(instance, (recipeId, recipe) ->
                            new DnaExtractingRecipeDisplay(new RecipeHolder<>(
                                    ResourceKey.create(Registries.RECIPE, recipeId), recipe
                            ))
                    )
            ),
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC,
                    display -> display.recipe.id().location(),
                    DnaExtractingRecipe.Serializer.STREAM_CODEC,
                    display -> display.recipe.value(),
                    (recipeId, recipe) ->
                            new DnaExtractingRecipeDisplay(new RecipeHolder<>(
                                    ResourceKey.create(Registries.RECIPE, recipeId), recipe
                            ))
            )
    );

    @Override
    public List<EntryIngredient> getInputEntries() {
        return List.of(EntryIngredients.ofIngredient(recipe().value().inputItems()));
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        List<ItemStack> output = recipe().value().output();
        List<EntryIngredient> entries = new ArrayList<>();
        for (int i = 0; i < output.size(); i++) {
            entries.add(EntryIngredients.of(output.get(i)));
        }
        return entries;
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
