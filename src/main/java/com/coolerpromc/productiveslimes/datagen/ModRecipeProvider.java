package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.datagen.builder.MeltingRecipeBuilder;
import com.coolerpromc.productiveslimes.datagen.builder.SolidingRecipeBuilder;
import com.coolerpromc.productiveslimes.fluid.ModFluids;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
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

        //Slime Ball Recipe
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

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.OAK_SLIME_BLOCK, ModItems.OAK_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.OAK_SLIME_BALL, ModBlocks.OAK_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.COAL_SLIME_BLOCK, ModItems.COAL_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.COAL_SLIME_BALL, ModBlocks.COAL_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.GRAVEL_SLIME_BLOCK, ModItems.GRAVEL_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.GRAVEL_SLIME_BALL, ModBlocks.GRAVEL_SLIME_BLOCK);

        //Melting Recipe
        meltingRecipe(recipeOutput, ModBlocks.DIRT_SLIME_BLOCK, ModFluids.MOLTEN_DIRT_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.DIRT_SLIME_BALL, ModFluids.MOLTEN_DIRT_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.STONE_SLIME_BLOCK, ModFluids.MOLTEN_STONE_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.STONE_SLIME_BALL, ModFluids.MOLTEN_STONE_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.COPPER_SLIME_BLOCK, ModFluids.MOLTEN_COPPER_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.COPPER_SLIME_BALL, ModFluids.MOLTEN_COPPER_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.IRON_SLIME_BLOCK, ModFluids.MOLTEN_IRON_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.IRON_SLIME_BALL, ModFluids.MOLTEN_IRON_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.GOLD_SLIME_BLOCK, ModFluids.MOLTEN_GOLD_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.GOLD_SLIME_BALL, ModFluids.MOLTEN_GOLD_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.DIAMOND_SLIME_BLOCK, ModFluids.MOLTEN_DIAMOND_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.DIAMOND_SLIME_BALL, ModFluids.MOLTEN_DIAMOND_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.NETHERITE_SLIME_BLOCK, ModFluids.MOLTEN_NETHERITE_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.NETHERITE_SLIME_BALL, ModFluids.MOLTEN_NETHERITE_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.LAPIS_SLIME_BLOCK, ModFluids.MOLTEN_LAPIS_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.LAPIS_SLIME_BALL, ModFluids.MOLTEN_LAPIS_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.REDSTONE_SLIME_BLOCK, ModFluids.MOLTEN_REDSTONE_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.REDSTONE_SLIME_BALL, ModFluids.MOLTEN_REDSTONE_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.OAK_SLIME_BLOCK, ModFluids.MOLTEN_OAK_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.OAK_SLIME_BALL, ModFluids.MOLTEN_OAK_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.COAL_SLIME_BLOCK, ModFluids.MOLTEN_COAL_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.COAL_SLIME_BALL, ModFluids.MOLTEN_COAL_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.GRAVEL_SLIME_BLOCK, ModFluids.MOLTEN_GRAVEL_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.GRAVEL_SLIME_BALL, ModFluids.MOLTEN_GRAVEL_BUCKET, 4, 1);

        //Soliding Recipe
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_DIRT_BUCKET, Items.DIRT, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_STONE_BUCKET, Items.STONE, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_COPPER_BUCKET, Items.COPPER_INGOT, 1, 1);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_IRON_BUCKET, Items.IRON_INGOT, 1, 1);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_GOLD_BUCKET, Items.GOLD_INGOT, 1, 1);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_DIAMOND_BUCKET, Items.DIAMOND, 1, 1);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_NETHERITE_BUCKET, Items.NETHERITE_INGOT, 1, 1);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_LAPIS_BUCKET, Items.LAPIS_LAZULI, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_REDSTONE_BUCKET, Items.REDSTONE, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_OAK_BUCKET, Items.OAK_PLANKS, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_COAL_BUCKET, Items.COAL, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_GRAVEL_BUCKET, Items.GRAVEL, 1, 2);
    }

    protected static void meltingRecipe(RecipeOutput pRecipeOutput, ItemLike pIngredient, ItemLike pResult, int pInputCount, int outputCount) {
        MeltingRecipeBuilder.meltingRecipe()
                .addIngredient(Ingredient.of(pIngredient))
                .setInputCount(pInputCount)
                .addOutput(new ItemStack(pResult, outputCount))
                .unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "melting/" + getItemName(pIngredient) + "_melting"));
    }

    protected static void solidingRecipe(RecipeOutput pRecipeOutput, ItemLike pIngredient, ItemLike pResult, int pInputCount, int outputCount) {
        SolidingRecipeBuilder.solidingRecipe()
                .addIngredient(Ingredient.of(pIngredient))
                .setInputCount(pInputCount)
                .addOutput(new ItemStack(pResult, outputCount))
                .addOutput(new ItemStack(Items.BUCKET, pInputCount))
                .unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "soliding/" + getItemName(pIngredient) + "_soliding"));
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
}
