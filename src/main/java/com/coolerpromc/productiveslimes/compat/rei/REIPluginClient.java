package com.coolerpromc.productiveslimes.compat.rei;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.compat.rei.DnaExtracting.DnaExtractingCategory;
import com.coolerpromc.productiveslimes.compat.rei.DnaExtracting.DnaExtractingRecipeDisplay;
import com.coolerpromc.productiveslimes.compat.rei.DnaSynthesizing.DnaSynthesizingCategory;
import com.coolerpromc.productiveslimes.compat.rei.DnaSynthesizing.DnaSynthesizingRecipeDisplay;
import com.coolerpromc.productiveslimes.compat.rei.Melting.MeltingCategory;
import com.coolerpromc.productiveslimes.compat.rei.Melting.MeltingRecipeDisplay;
import com.coolerpromc.productiveslimes.compat.rei.Soliding.SolidingCategory;
import com.coolerpromc.productiveslimes.compat.rei.Soliding.SolidingRecipeDisplay;
import com.coolerpromc.productiveslimes.compat.rei.Squeezing.SqueezingCategory;
import com.coolerpromc.productiveslimes.compat.rei.Squeezing.SqueezingRecipeDisplay;
import com.coolerpromc.productiveslimes.recipe.*;
import com.coolerpromc.productiveslimes.screen.*;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;

@me.shedaniel.rei.forge.REIPluginClient
public class REIPluginClient implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new DnaExtractingCategory(), configuration -> configuration.addWorkstations(EntryStacks.of(ModBlocks.DNA_EXTRACTOR.get())));
        registry.add(new DnaSynthesizingCategory(), configuration -> configuration.addWorkstations(EntryStacks.of(ModBlocks.DNA_SYNTHESIZER.get())));
        registry.add(new MeltingCategory(), configuration -> configuration.addWorkstations(EntryStacks.of(ModBlocks.MELTING_STATION.get())));
        registry.add(new SolidingCategory(), configuration -> configuration.addWorkstations(EntryStacks.of(ModBlocks.LIQUID_SOLIDING_STATION.get())));
        registry.add(new SqueezingCategory(), configuration -> configuration.addWorkstations(EntryStacks.of(ModBlocks.SLIME_SQUEEZER.get())));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(DnaExtractingRecipe.class, DnaExtractingRecipe.Type.INSTANCE, DnaExtractingRecipeDisplay::new);
        registry.registerRecipeFiller(DnaSynthesizingRecipe.class, DnaSynthesizingRecipe.Type.INSTANCE, DnaSynthesizingRecipeDisplay::new);
        registry.registerRecipeFiller(MeltingRecipe.class, MeltingRecipe.Type.INSTANCE, MeltingRecipeDisplay::new);
        registry.registerRecipeFiller(SolidingRecipe.class, SolidingRecipe.Type.INSTANCE, SolidingRecipeDisplay::new);
        registry.registerRecipeFiller(SqueezingRecipe.class, SqueezingRecipe.Type.INSTANCE, SqueezingRecipeDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerClickArea(screen -> new Rectangle(((screen.width - 176) / 2) + 77, ((screen.height - 166) / 2) + 38, 26, 8), DnaExtractorScreen.class, DnaExtractingCategory.DNA_EXTRACTING);
        registry.registerClickArea(screen -> new Rectangle(((screen.width - 176) / 2) + 77, ((screen.height - 166) / 2) + 38, 26, 8), DnaSynthesizerScreen.class, DnaSynthesizingCategory.DNA_SYNTHESIZING);
        registry.registerClickArea(screen -> new Rectangle(((screen.width - 176) / 2) + 77, ((screen.height - 166) / 2) + 38, 26, 8), MeltingStationScreen.class, MeltingCategory.MELTING);
        registry.registerClickArea(screen -> new Rectangle(((screen.width - 176) / 2) + 77, ((screen.height - 166) / 2) + 38, 26, 8), SolidingStationScreen.class, SolidingCategory.SOLIDING);
        registry.registerClickArea(screen -> new Rectangle(((screen.width - 176) / 2) + 77, ((screen.height - 166) / 2) + 38, 26, 8), SlimeSqueezerScreen.class, SqueezingCategory.SQUEEZING);
    }
}
