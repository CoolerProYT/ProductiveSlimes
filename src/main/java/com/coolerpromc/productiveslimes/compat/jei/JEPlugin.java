package com.coolerpromc.productiveslimes.compat.jei;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.recipe.*;
import com.coolerpromc.productiveslimes.screen.*;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

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

        registration.addRecipes(meltingRecipes, MeltingCategory.UID);
        registration.addRecipes(solidingRecipes, SolidingCategory.UID);
        registration.addRecipes(dnaExtractingRecipes, DnaExtractingCategory.UID);
        registration.addRecipes(dnaSynthesizingRecipes, DnaSynthesizingCategory.UID);
        registration.addRecipes(squeezingRecipes, SqueezingCategory.UID);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(MeltingStationScreen.class, 77, 38, 26, 8, MeltingCategory.UID);
        registration.addRecipeClickArea(SolidingStationScreen.class, 77, 38, 26, 8, SolidingCategory.UID);
        registration.addRecipeClickArea(DnaExtractorScreen.class, 77, 38, 26, 8, DnaExtractingCategory.UID);
        registration.addRecipeClickArea(DnaSynthesizerScreen.class, 77, 38, 26, 8, DnaSynthesizingCategory.UID);
        registration.addRecipeClickArea(SlimeSqueezerScreen.class, 77, 38, 26, 8, SqueezingCategory.UID);
    }
}