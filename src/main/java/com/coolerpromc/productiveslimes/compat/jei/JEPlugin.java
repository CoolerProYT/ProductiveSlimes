package com.coolerpromc.productiveslimes.compat.jei;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.recipe.DnaExtractingRecipe;
import com.coolerpromc.productiveslimes.recipe.MeltingRecipe;
import com.coolerpromc.productiveslimes.recipe.ModRecipes;
import com.coolerpromc.productiveslimes.recipe.SolidingRecipe;
import com.coolerpromc.productiveslimes.screen.DnaExtractorScreen;
import com.coolerpromc.productiveslimes.screen.MeltingStationScreen;
import com.coolerpromc.productiveslimes.screen.SolidingStationScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
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
        registration.addRecipeCategories(new SolidingCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new DnaExtractingCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<RecipeHolder<MeltingRecipe>> meltingRecipes = recipeManager.getAllRecipesFor(ModRecipes.MELTING_TYPE.get());
        List<MeltingRecipe> meltingRecipeList = meltingRecipes.stream().map(RecipeHolder::value).collect(Collectors.toList());

        List<RecipeHolder<SolidingRecipe>> solidingRecipes = recipeManager.getAllRecipesFor(ModRecipes.SOLIDING_TYPE.get());
        List<SolidingRecipe> solidingRecipeList = solidingRecipes.stream().map(RecipeHolder::value).collect(Collectors.toList());

        List<RecipeHolder<DnaExtractingRecipe>> dnaExtractingRecipes = recipeManager.getAllRecipesFor(ModRecipes.DNA_EXTRACTING_TYPE.get());
        List<DnaExtractingRecipe> dnaExtractingRecipeList = dnaExtractingRecipes.stream().map(RecipeHolder::value).collect(Collectors.toList());

        registration.addRecipes(MeltingCategory.MELTING_TYPE, meltingRecipeList);
        registration.addRecipes(SolidingCategory.SOLIDING_TYPE, solidingRecipeList);
        registration.addRecipes(DnaExtractingCategory.DNA_EXTRACTING_TYPE, dnaExtractingRecipeList);

        /*registration.addItemStackInfo(
                new ItemStack(ModItems.DIRT_SLIME_BALL.get()),
                Component.literal("""
                        §nDirt Slimeball§r

                        This slimeball is dropped from Dirt Slime.

                        Dirt Slime is obtainable by using dirt on a slime.""")
        );*/
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(MeltingStationScreen.class, 77, 38, 26, 8, MeltingCategory.MELTING_TYPE);
        registration.addRecipeClickArea(SolidingStationScreen.class, 77, 38, 26, 8, SolidingCategory.SOLIDING_TYPE);
        registration.addRecipeClickArea(DnaExtractorScreen.class, 77, 38, 26, 8, DnaExtractingCategory.DNA_EXTRACTING_TYPE);
    }
}