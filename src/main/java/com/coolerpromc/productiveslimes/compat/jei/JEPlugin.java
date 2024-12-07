package com.coolerpromc.productiveslimes.compat.jei;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.recipe.*;
import com.coolerpromc.productiveslimes.screen.DnaExtractorScreen;
import com.coolerpromc.productiveslimes.screen.DnaSynthesizerScreen;
import com.coolerpromc.productiveslimes.screen.MeltingStationScreen;
import com.coolerpromc.productiveslimes.screen.SolidingStationScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
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
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<MeltingRecipe> meltingRecipes = recipeManager.getAllRecipesFor(MeltingRecipe.Type.INSTANCE);
        List<SolidingRecipe> solidingRecipes = recipeManager.getAllRecipesFor(SolidingRecipe.Type.INSTANCE);
        List<DnaExtractingRecipe> dnaExtractingRecipes = recipeManager.getAllRecipesFor(DnaExtractingRecipe.Type.INSTANCE);
        List<DnaSynthesizingRecipe> dnaSynthesizingRecipes = recipeManager.getAllRecipesFor(DnaSynthesizingRecipe.Type.INSTANCE);

        registration.addRecipes(MeltingCategory.MELTING_TYPE, meltingRecipes);
        registration.addRecipes(SolidingCategory.SOLIDING_TYPE, solidingRecipes);
        registration.addRecipes(DnaExtractingCategory.DNA_EXTRACTING_TYPE, dnaExtractingRecipes);
        registration.addRecipes(DnaSynthesizingCategory.DNA_SYNTHESIZING_TYPE, dnaSynthesizingRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(MeltingStationScreen.class, 77, 38, 26, 8, MeltingCategory.MELTING_TYPE);
        registration.addRecipeClickArea(SolidingStationScreen.class, 77, 38, 26, 8, SolidingCategory.SOLIDING_TYPE);
        registration.addRecipeClickArea(DnaExtractorScreen.class, 77, 38, 26, 8, DnaExtractingCategory.DNA_EXTRACTING_TYPE);
        registration.addRecipeClickArea(DnaSynthesizerScreen.class, 77, 38, 26, 8, DnaSynthesizingCategory.DNA_SYNTHESIZING_TYPE);
    }
}