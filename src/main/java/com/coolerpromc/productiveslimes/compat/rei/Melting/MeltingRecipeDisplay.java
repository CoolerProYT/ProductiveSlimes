package com.coolerpromc.productiveslimes.compat.rei.Melting;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.compat.rei.DnaSynthesizing.DnaSynthesizingRecipeDisplay;
import com.coolerpromc.productiveslimes.recipe.MeltingRecipe;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MeltingRecipeDisplay extends BasicDisplay {
    public static final CategoryIdentifier<? extends MeltingRecipeDisplay> CATEGORY = CategoryIdentifier.of(ProductiveSlimes.MODID, "melting");
    private final int energy;
    private final int inputCount;

    public static final DisplaySerializer<MeltingRecipeDisplay> SERIALIZER = DisplaySerializer.of(
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    EntryIngredient.codec().listOf().fieldOf("ingredients").forGetter(MeltingRecipeDisplay::getInputEntries),
                    EntryIngredient.codec().listOf().fieldOf("output").forGetter(MeltingRecipeDisplay::getOutputEntries),
                    Codec.INT.fieldOf("inputCount").forGetter(MeltingRecipeDisplay::getInputCount),
                    Codec.INT.fieldOf("energy").forGetter(MeltingRecipeDisplay::getEnergy)
            ).apply(instance, MeltingRecipeDisplay::new)),
            StreamCodec.composite(
                    EntryIngredient.streamCodec().apply(ByteBufCodecs.list()),
                    MeltingRecipeDisplay::getInputEntries,
                    EntryIngredient.streamCodec().apply(ByteBufCodecs.list()),
                    MeltingRecipeDisplay::getOutputEntries,
                    ByteBufCodecs.INT,
                    MeltingRecipeDisplay::getInputCount,
                    ByteBufCodecs.INT,
                    MeltingRecipeDisplay::getEnergy,
                    MeltingRecipeDisplay::new
            )
    );

    public MeltingRecipeDisplay(RecipeHolder<MeltingRecipe> recipe) {
        super(List.of(EntryIngredients.of(new ItemStack(recipe.value().getInputItems().getFirst().items().getFirst(), recipe.value().getInputCount())),
                        EntryIngredients.of(new ItemStack(Items.BUCKET, recipe.value().getOutputs().getFirst().getCount()))),
                List.of(EntryIngredient.of(EntryStacks.of(recipe.value().getOutputs().getFirst()))));

        energy = recipe.value().getEnergy();
        inputCount = recipe.value().getInputCount();
    }

    public MeltingRecipeDisplay(List<EntryIngredient> input, List<EntryIngredient> output, int inputCount, int energy) {
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
        return CATEGORY;
    }

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return SERIALIZER;
    }
}
