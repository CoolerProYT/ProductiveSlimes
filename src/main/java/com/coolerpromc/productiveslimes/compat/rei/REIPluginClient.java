package com.coolerpromc.productiveslimes.compat.rei;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.compat.rei.DnaExtracting.DnaExtractingCategory;
import com.coolerpromc.productiveslimes.compat.rei.DnaSynthesizing.DnaSynthesizingCategory;
import com.coolerpromc.productiveslimes.compat.rei.Melting.MeltingCategory;
import com.coolerpromc.productiveslimes.compat.rei.Soliding.SolidingCategory;
import com.coolerpromc.productiveslimes.screen.DnaExtractorScreen;
import com.coolerpromc.productiveslimes.screen.DnaSynthesizerScreen;
import com.coolerpromc.productiveslimes.screen.MeltingStationScreen;
import com.coolerpromc.productiveslimes.screen.SolidingStationScreen;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
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
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerClickArea(screen -> new Rectangle(((screen.width - 176) / 2) + 77, ((screen.height - 166) / 2) + 38, 26, 8), DnaExtractorScreen.class, DnaExtractingCategory.DNA_EXTRACTING);
        registry.registerClickArea(screen -> new Rectangle(((screen.width - 176) / 2) + 77, ((screen.height - 166) / 2) + 38, 26, 8), DnaSynthesizerScreen.class, DnaSynthesizingCategory.DNA_SYNTHESIZING);
        registry.registerClickArea(screen -> new Rectangle(((screen.width - 176) / 2) + 77, ((screen.height - 166) / 2) + 38, 26, 8), MeltingStationScreen.class, MeltingCategory.MELTING);
        registry.registerClickArea(screen -> new Rectangle(((screen.width - 176) / 2) + 77, ((screen.height - 166) / 2) + 38, 26, 8), SolidingStationScreen.class, SolidingCategory.SOLIDING);
    }
}
