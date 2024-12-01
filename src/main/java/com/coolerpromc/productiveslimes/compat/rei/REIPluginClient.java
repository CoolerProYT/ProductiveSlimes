package com.coolerpromc.productiveslimes.compat.rei;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.compat.rei.DnaExtracting.DnaExtractingCategory;
import com.coolerpromc.productiveslimes.compat.rei.DnaExtracting.DnaExtractingRecipeDisplay;
import com.coolerpromc.productiveslimes.recipe.*;
import com.coolerpromc.productiveslimes.screen.DnaExtractorScreen;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeMap;

import java.util.Collection;
import java.util.Objects;

@me.shedaniel.rei.forge.REIPluginClient
public class REIPluginClient implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new DnaExtractingCategory(), configuration -> configuration.addWorkstations(EntryStacks.of(ModBlocks.DNA_EXTRACTOR.get())));
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerClickArea(screen -> new Rectangle(((screen.width - 176) / 2) + 77, ((screen.height - 166) / 2) + 38, 26, 8), DnaExtractorScreen.class, DnaExtractingCategory.DNA_EXTRACTING);
    }
}
