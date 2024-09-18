package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput p_248933_, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(p_248933_,pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        //Override vanilla recipes
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.STICKY_PISTON, 1)
                .requires(Tags.Items.SLIME_BALLS)
                .requires(Items.PISTON)
                .unlockedBy(getHasName(Items.SLIME_BALL), has(Items.PISTON))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.MAGMA_CREAM, 1)
                .requires(Tags.Items.SLIME_BALLS)
                .requires(Items.BLAZE_POWDER)
                .unlockedBy(getHasName(Items.SLIME_BALL), has(Items.BLAZE_POWDER))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.LEAD,2)
                .pattern("AA ")
                .pattern("AB ")
                .pattern("  A")
                .define('A', Items.STRING)
                .define('B', Tags.Items.SLIME_BALLS)
                .unlockedBy(getHasName(Items.DEEPSLATE), has(Items.LAVA_BUCKET))
                .save(recipeOutput);

        //Mod Recipe

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.MELTING_STATION.get(),1)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', Items.DEEPSLATE)
                .define('B', Items.LAVA_BUCKET)
                .unlockedBy(getHasName(Items.DEEPSLATE), has(Items.LAVA_BUCKET))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.LIQUID_SOLIDING_STATION.get(),1)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', Items.DEEPSLATE)
                .define('B', Items.WATER_BUCKET)
                .unlockedBy(getHasName(Items.DEEPSLATE), has(Items.WATER_BUCKET))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GUIDEBOOK.get(), 1)
                .requires(Items.BOOK)
                .requires(Tags.Items.SLIME_BALLS)
                .unlockedBy(getHasName(Items.BOOK), has(Items.SLIME_BALL))
                .save(recipeOutput);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.DIRT_SLIME_BLOCK, ModItems.DIRT_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.DIRT_SLIME_BALL, ModBlocks.DIRT_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.STONE_SLIME_BLOCK, ModItems.STONE_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.STONE_SLIME_BALL, ModBlocks.STONE_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.COPPER_SLIME_BLOCK, ModItems.COPPER_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.COPPER_SLIME_BALL, ModBlocks.COPPER_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.IRON_SLIME_BLOCK, ModItems.IRON_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.IRON_SLIME_BALL, ModBlocks.IRON_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.GOLD_SLIME_BLOCK, ModItems.GOLD_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.GOLD_SLIME_BALL, ModBlocks.GOLD_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.DIAMOND_SLIME_BLOCK, ModItems.DIAMOND_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.DIAMOND_SLIME_BALL, ModBlocks.DIAMOND_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.NETHERITE_SLIME_BLOCK, ModItems.NETHERITE_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.NETHERITE_SLIME_BALL, ModBlocks.NETHERITE_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.LAPIS_SLIME_BLOCK, ModItems.LAPIS_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.LAPIS_SLIME_BALL, ModBlocks.LAPIS_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.REDSTONE_SLIME_BLOCK, ModItems.REDSTONE_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.REDSTONE_SLIME_BALL, ModBlocks.REDSTONE_SLIME_BLOCK);
    }

    protected static void slimeBlockToSlimeBall(RecipeOutput pRecipeOutput, ItemLike pSlimeBlock, ItemLike pSlimeBall) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, pSlimeBall, 9)
                .requires(pSlimeBlock)
                .unlockedBy(getHasName(pSlimeBlock), has(pSlimeBlock))
                .save(pRecipeOutput, getItemName(pSlimeBall) + "_from_" + getItemName(pSlimeBlock));
    }

    protected static void slimeBallToSlimeBlock(RecipeOutput pRecipeOutput, ItemLike pSlimeBall, ItemLike pSlimeBlock) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, pSlimeBlock, 1)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', pSlimeBall)
                .unlockedBy(getHasName(pSlimeBall), has(pSlimeBall))
                .save(pRecipeOutput, getItemName(pSlimeBlock) + "_from_" + getItemName(pSlimeBall));
    }

    protected static void oreSmelting(RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pRecipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pRecipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput pRecipeOutput, RecipeSerializer<T> pSerializer, AbstractCookingRecipe.Factory<T> pRecipeFactory, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pSuffix) {
        Iterator var10 = pIngredients.iterator();

        while(var10.hasNext()) {
            ItemLike itemlike = (ItemLike)var10.next();
            SimpleCookingRecipeBuilder.generic(Ingredient.of(new ItemLike[]{itemlike}), pCategory, pResult, pExperience, pCookingTime, pSerializer, pRecipeFactory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike)).save(pRecipeOutput, getItemName(pResult) + pSuffix + "_" + getItemName(itemlike));
        }
    }
}
