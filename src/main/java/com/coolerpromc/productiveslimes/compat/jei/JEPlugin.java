package com.coolerpromc.productiveslimes.compat.jei;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.event.ModRecipeReceived;
import com.coolerpromc.productiveslimes.recipe.*;
import com.coolerpromc.productiveslimes.screen.*;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Collection;

@JeiPlugin
public class JEPlugin implements IModPlugin {

    @Override
    public Identifier getPluginUid() {
        return Identifier.fromNamespaceAndPath(ProductiveSlimes.MODID,"jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        registration.addRecipeCategories(
                new MeltingCategory(guiHelper),
                new SolidingCategory(guiHelper),
                new DnaExtractingCategory(guiHelper),
                new DnaSynthesizingCategory(guiHelper),
                new SqueezingCategory(guiHelper)
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        Collection<RecipeHolder<MeltingRecipe>> meltingRecipes = ModRecipeReceived.recipeMap.byType(ModRecipes.MELTING_TYPE.get());
        Collection<RecipeHolder<SolidingRecipe>> solidingRecipes = ModRecipeReceived.recipeMap.byType(ModRecipes.SOLIDING_TYPE.get());
        Collection<RecipeHolder<DnaExtractingRecipe>> dnaExtractingRecipes = ModRecipeReceived.recipeMap.byType(ModRecipes.DNA_EXTRACTING_TYPE.get());
        Collection<RecipeHolder<DnaSynthesizingRecipe>> dnaSynthesizingRecipes = ModRecipeReceived.recipeMap.byType(ModRecipes.DNA_SYNTHESIZING_TYPE.get());
        Collection<RecipeHolder<SqueezingRecipe>> squeezingRecipes = ModRecipeReceived.recipeMap.byType(ModRecipes.SQUEEZING_TYPE.get());

        registration.addRecipes(MeltingCategory.MELTING_TYPE, meltingRecipes.stream().toList());
        registration.addRecipes(SolidingCategory.SOLIDING_TYPE, solidingRecipes.stream().toList());
        registration.addRecipes(DnaExtractingCategory.DNA_EXTRACTING_TYPE, dnaExtractingRecipes.stream().toList());
        registration.addRecipes(DnaSynthesizingCategory.DNA_SYNTHESIZING_TYPE, dnaSynthesizingRecipes.stream().toList());
        registration.addRecipes(SqueezingCategory.SQUEEZING_TYPE, squeezingRecipes.stream().toList());

    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(MeltingStationScreen.class, 77, 38, 26, 8, MeltingCategory.MELTING_TYPE);
        registration.addRecipeClickArea(SolidingStationScreen.class, 77, 38, 26, 8, SolidingCategory.SOLIDING_TYPE);
        registration.addRecipeClickArea(DnaExtractorScreen.class, 77, 38, 26, 8, DnaExtractingCategory.DNA_EXTRACTING_TYPE);
        registration.addRecipeClickArea(DnaSynthesizerScreen.class, 77, 38, 26, 8, DnaSynthesizingCategory.DNA_SYNTHESIZING_TYPE);
        registration.addRecipeClickArea(SlimeSqueezerScreen.class, 77, 38, 26, 8, SqueezingCategory.SQUEEZING_TYPE);
    }
}
