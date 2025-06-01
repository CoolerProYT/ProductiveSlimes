package com.coolerpromc.productiveslimes.compat.rei.DnaExtracting;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.recipe.DnaExtractingRecipe;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DnaExtractingRecipeDisplay extends BasicDisplay {
    public static final CategoryIdentifier<? extends DnaExtractingRecipeDisplay> CATEGORY = CategoryIdentifier.of(ProductiveSlimes.MODID, "dna_extracting");
    private final int energy;
    private final float outputChance;
    private final int inputCount;

    public static final DisplaySerializer<DnaExtractingRecipeDisplay> SERIALIZER = DisplaySerializer.of(
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    EntryIngredient.codec().listOf().fieldOf("ingredients").forGetter(DnaExtractingRecipeDisplay::getInputEntries),
                    EntryIngredient.codec().listOf().fieldOf("output").forGetter(DnaExtractingRecipeDisplay::getOutputEntries),
                    Codec.INT.fieldOf("inputCount").forGetter(DnaExtractingRecipeDisplay::getInputCount),
                    Codec.INT.fieldOf("energy").forGetter(DnaExtractingRecipeDisplay::getEnergy),
                    Codec.FLOAT.fieldOf("output_chance").forGetter(DnaExtractingRecipeDisplay::getOutputChance)
            ).apply(instance, DnaExtractingRecipeDisplay::new)),
            StreamCodec.composite(
                    EntryIngredient.streamCodec().apply(ByteBufCodecs.list()),
                    DnaExtractingRecipeDisplay::getInputEntries,
                    EntryIngredient.streamCodec().apply(ByteBufCodecs.list()),
                    DnaExtractingRecipeDisplay::getOutputEntries,
                    ByteBufCodecs.INT,
                    DnaExtractingRecipeDisplay::getInputCount,
                    ByteBufCodecs.INT,
                    DnaExtractingRecipeDisplay::getEnergy,
                    ByteBufCodecs.FLOAT,
                    DnaExtractingRecipeDisplay::getOutputChance,
                    DnaExtractingRecipeDisplay::new
            )
    );

    public DnaExtractingRecipeDisplay(RecipeHolder<DnaExtractingRecipe> recipe) {
        super(List.of(EntryIngredients.ofIngredient(recipe.value().getInputItems().getFirst())),
                List.of(EntryIngredient.of(EntryStacks.of(recipe.value().getOutputs().get(0))),
                        EntryIngredient.of(EntryStacks.of(recipe.value().getOutputs().size() > 1 ? recipe.value().getOutputs().get(1) : ItemStack.EMPTY))));
        energy = recipe.value().getEnergy();
        outputChance = recipe.value().getOutputChance();
        inputCount = recipe.value().getInputCount();
    }

    public DnaExtractingRecipeDisplay(List<EntryIngredient> input, List<EntryIngredient> output, int inputCount, int energy, float outputChance) {
        super(input, output);
        this.energy = energy;
        this.outputChance = outputChance;
        this.inputCount = inputCount;
    }

    public int getEnergy() {
        return energy;
    }

    public float getOutputChance() {
        return outputChance;
    }

    public int getInputCount() {
        return inputCount;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CATEGORY;
    }

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return SERIALIZER;
    }
}
