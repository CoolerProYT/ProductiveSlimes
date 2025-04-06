package com.coolerpromc.productiveslimes.compat.jei;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.recipe.*;
import com.coolerpromc.productiveslimes.screen.*;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ProductiveSlimes.MODID,"jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new MeltingCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new SolidingCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new DnaExtractingCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new DnaSynthesizingCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new SqueezingCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<MeltingRecipe> meltingRecipes = recipeManager.getAllRecipesFor(MeltingRecipe.Type.INSTANCE);
        List<SolidingRecipe> solidingRecipes = recipeManager.getAllRecipesFor(SolidingRecipe.Type.INSTANCE);
        List<DnaExtractingRecipe> dnaExtractingRecipes = recipeManager.getAllRecipesFor(DnaExtractingRecipe.Type.INSTANCE);
        List<DnaSynthesizingRecipe> dnaSynthesizingRecipes = recipeManager.getAllRecipesFor(DnaSynthesizingRecipe.Type.INSTANCE);
        List<SqueezingRecipe> squeezingRecipes = recipeManager.getAllRecipesFor(SqueezingRecipe.Type.INSTANCE);

        registration.addRecipes(MeltingCategory.MELTING_TYPE, meltingRecipes);
        registration.addRecipes(SolidingCategory.SOLIDING_TYPE, solidingRecipes);
        registration.addRecipes(DnaExtractingCategory.DNA_EXTRACTING_TYPE, dnaExtractingRecipes);
        registration.addRecipes(DnaSynthesizingCategory.DNA_SYNTHESIZING_TYPE, dnaSynthesizingRecipes);
        registration.addRecipes(SqueezingCategory.SQUEEZING_TYPE, squeezingRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(MeltingStationScreen.class, 77, 38, 26, 8, MeltingCategory.MELTING_TYPE);
        registration.addRecipeClickArea(SolidingStationScreen.class, 77, 38, 26, 8, SolidingCategory.SOLIDING_TYPE);
        registration.addRecipeClickArea(DnaExtractorScreen.class, 77, 38, 26, 8, DnaExtractingCategory.DNA_EXTRACTING_TYPE);
        registration.addRecipeClickArea(DnaSynthesizerScreen.class, 77, 38, 26, 8, DnaSynthesizingCategory.DNA_SYNTHESIZING_TYPE);
        registration.addRecipeClickArea(SlimeSqueezerScreen.class, 77, 38, 26, 8, SqueezingCategory.SQUEEZING_TYPE);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.MELTING_STATION.get()), MeltingCategory.MELTING_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.LIQUID_SOLIDING_STATION.get()), SolidingCategory.SOLIDING_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.DNA_EXTRACTOR.get()), DnaExtractingCategory.DNA_EXTRACTING_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.DNA_SYNTHESIZER.get()), DnaSynthesizingCategory.DNA_SYNTHESIZING_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.SLIME_SQUEEZER.get()), SqueezingCategory.SQUEEZING_TYPE);
    }
}