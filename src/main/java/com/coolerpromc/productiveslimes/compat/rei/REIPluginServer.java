package com.coolerpromc.productiveslimes.compat.rei;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.compat.rei.DnaExtracting.DnaExtractingRecipeDisplay;
import com.coolerpromc.productiveslimes.recipe.DnaExtractingRecipe;
import com.coolerpromc.productiveslimes.recipe.ModRecipes;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;
import me.shedaniel.rei.forge.REIPluginCommon;
import net.minecraft.resources.ResourceLocation;

@REIPluginCommon
public class REIPluginServer implements REICommonPlugin {
    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "dna_extracting"), DnaExtractingRecipeDisplay.SERIALIZER);
    }

    @Override
    public void registerDisplays(ServerDisplayRegistry registry) {
        registry.beginRecipeFiller(DnaExtractingRecipe.class).filterType(ModRecipes.DNA_EXTRACTING_TYPE.get()).fill(DnaExtractingRecipeDisplay::new);
    }
}
