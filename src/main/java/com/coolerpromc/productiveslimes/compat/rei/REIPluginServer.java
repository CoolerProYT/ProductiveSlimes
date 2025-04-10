package com.coolerpromc.productiveslimes.compat.rei;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.compat.rei.DnaExtracting.DnaExtractingRecipeDisplay;
import com.coolerpromc.productiveslimes.compat.rei.DnaSynthesizing.DnaSynthesizingRecipeDisplay;
import com.coolerpromc.productiveslimes.compat.rei.Melting.MeltingRecipeDisplay;
import com.coolerpromc.productiveslimes.compat.rei.Soliding.SolidingRecipeDisplay;
import com.coolerpromc.productiveslimes.compat.rei.Squeezing.SqueezingRecipeDisplay;
import com.coolerpromc.productiveslimes.recipe.*;
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
        registry.register(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "dna_synthesizing"), DnaSynthesizingRecipeDisplay.SERIALIZER);
        registry.register(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "melting"), MeltingRecipeDisplay.SERIALIZER);
        registry.register(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "soliding"), SolidingRecipeDisplay.SERIALIZER);
        registry.register(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "squeezing"), SqueezingRecipeDisplay.SERIALIZER);
    }

    @Override
    public void registerDisplays(ServerDisplayRegistry registry) {
        registry.beginRecipeFiller(DnaExtractingRecipe.class).filterType(ModRecipes.DNA_EXTRACTING_TYPE.get()).fill(DnaExtractingRecipeDisplay::new);
        registry.beginRecipeFiller(DnaSynthesizingRecipe.class).filterType(ModRecipes.DNA_SYNTHESIZING_TYPE.get()).fill(DnaSynthesizingRecipeDisplay::new);
        registry.beginRecipeFiller(MeltingRecipe.class).filterType(ModRecipes.MELTING_TYPE.get()).fill(MeltingRecipeDisplay::new);
        registry.beginRecipeFiller(SolidingRecipe.class).filterType(ModRecipes.SOLIDING_TYPE.get()).fill(SolidingRecipeDisplay::new);
        registry.beginRecipeFiller(SqueezingRecipe.class).filterType(ModRecipes.SQUEEZING_TYPE.get()).fill(SqueezingRecipeDisplay::new);
    }
}
