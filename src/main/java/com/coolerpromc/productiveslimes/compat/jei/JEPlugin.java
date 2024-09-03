package com.coolerpromc.productiveslimes.compat.jei;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.recipe.MeltingRecipe;
import com.coolerpromc.productiveslimes.recipe.ModRecipes;
import com.coolerpromc.productiveslimes.screen.MeltingStationScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.stream.Collectors;

@JeiPlugin
public class JEPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID,"jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new MeltingCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<RecipeHolder<MeltingRecipe>> fluidSeparatingRecipes = recipeManager.getAllRecipesFor(ModRecipes.MELTING_TYPE.get());
        List<MeltingRecipe> fluidSeparatingRecipeList = fluidSeparatingRecipes.stream().map(RecipeHolder::value).collect(Collectors.toList());

        registration.addRecipes(MeltingCategory.MELTING_TYPE, fluidSeparatingRecipeList);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(MeltingStationScreen.class, 77, 38, 26, 8, MeltingCategory.MELTING_TYPE);
    }
}