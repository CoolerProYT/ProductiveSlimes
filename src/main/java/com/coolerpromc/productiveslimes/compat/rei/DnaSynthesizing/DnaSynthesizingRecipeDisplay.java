/*
package com.coolerpromc.productiveslimes.compat.rei.DnaSynthesizing;

import com.coolerpromc.productiveslimes.recipe.DnaSynthesizingRecipe;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.core.Holder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DnaSynthesizingRecipeDisplay extends BasicDisplay {
    private final int energy;
    private final int inputCount;

    public static final DisplaySerializer<DnaSynthesizingRecipeDisplay> SERIALIZER = DisplaySerializer.of(
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    EntryIngredient.codec().listOf().fieldOf("ingredients").forGetter(DnaSynthesizingRecipeDisplay::getInputEntries),
                    EntryIngredient.codec().listOf().fieldOf("output").forGetter(DnaSynthesizingRecipeDisplay::getOutputEntries),
                    Codec.INT.fieldOf("inputCount").forGetter(DnaSynthesizingRecipeDisplay::getInputCount),
                    Codec.INT.fieldOf("energy").forGetter(DnaSynthesizingRecipeDisplay::getEnergy)
            ).apply(instance, DnaSynthesizingRecipeDisplay::new)),
            StreamCodec.composite(
                    EntryIngredient.streamCodec().apply(ByteBufCodecs.list()),
                    DnaSynthesizingRecipeDisplay::getInputEntries,
                    EntryIngredient.streamCodec().apply(ByteBufCodecs.list()),
                    DnaSynthesizingRecipeDisplay::getOutputEntries,
                    ByteBufCodecs.INT,
                    DnaSynthesizingRecipeDisplay::getInputCount,
                    ByteBufCodecs.INT,
                    DnaSynthesizingRecipeDisplay::getEnergy,
                    DnaSynthesizingRecipeDisplay::new
            )
    );

    public DnaSynthesizingRecipeDisplay(RecipeHolder<DnaSynthesizingRecipe> recipe) {
        super(
            List.of(
                EntryIngredients.ofIngredient(recipe.value().getInputItems().get(0)),
                EntryIngredients.ofIngredient(recipe.value().getInputItems().get(1)),
                EntryIngredients.of(new ItemStack(recipe.value().getInputItems().get(2).items().get(0), recipe.value().getInputCount()))
            ),
            List.of(EntryIngredient.of(EntryStacks.of(recipe.value().getOutput().getFirst())))
        );

        energy = recipe.value().getEnergy();
        inputCount = recipe.value().getInputCount();
    }

    public DnaSynthesizingRecipeDisplay(List<EntryIngredient> input, List<EntryIngredient> output, int inputCount, int energy) {
        super(input, output);
        this.energy = energy;
        this.inputCount = inputCount;
    }

    public int getEnergy() {
        return energy;
    }

    public int getInputCount() {
        return inputCount;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return DnaSynthesizingCategory.DNA_SYNTHESIZING;
    }

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return SERIALIZER;
    }
}*/
