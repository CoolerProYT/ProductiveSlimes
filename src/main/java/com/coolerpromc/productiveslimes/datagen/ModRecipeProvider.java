package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
//import com.coolerpromc.productiveslimes.datagen.builder.DnaExtractingRecipeBuilder;
import com.coolerpromc.productiveslimes.datagen.builder.DnaExtractingRecipeBuilder;
import com.coolerpromc.productiveslimes.datagen.builder.DnaSynthesizingRecipeBuilder;
//import com.coolerpromc.productiveslimes.datagen.builder.MeltingRecipeBuilder;
//import com.coolerpromc.productiveslimes.datagen.builder.SolidingRecipeBuilder;
import com.coolerpromc.productiveslimes.datagen.builder.MeltingRecipeBuilder;
import com.coolerpromc.productiveslimes.datagen.builder.SolidingRecipeBuilder;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private final HolderGetter<Item> items;

    public ModRecipeProvider(HolderLookup.Provider lookupProvider, RecipeOutput output) {
        super(lookupProvider, output);
        this.items = lookupProvider.lookupOrThrow(Registries.ITEM);
    }

    @Override
    protected void buildRecipes() {
        //Override vanilla recipes
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, Items.STICKY_PISTON, 1)
                .requires(Tags.Items.SLIME_BALLS)
                .requires(Items.PISTON)
                .unlockedBy(getHasName(Items.SLIME_BALL), has(Items.PISTON))
                .save(output);

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, Items.MAGMA_CREAM, 1)
                .requires(Tags.Items.SLIME_BALLS)
                .requires(Items.BLAZE_POWDER)
                .unlockedBy(getHasName(Items.SLIME_BALL), has(Items.BLAZE_POWDER))
                .save(output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, Items.LEAD,2)
                .pattern("AA ")
                .pattern("AB ")
                .pattern("  A")
                .define('A', Items.STRING)
                .define('B', Tags.Items.SLIME_BALLS)
                .unlockedBy(getHasName(Items.DEEPSLATE), has(Items.LAVA_BUCKET))
                .save(output);

        //Mod Recipe
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, ModBlocks.MELTING_STATION.get(),1)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', Items.DEEPSLATE)
                .define('B', Items.LAVA_BUCKET)
                .unlockedBy(getHasName(Items.DEEPSLATE), has(Items.LAVA_BUCKET))
                .save(output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, ModBlocks.LIQUID_SOLIDING_STATION.get(),1)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', Items.DEEPSLATE)
                .define('B', Items.WATER_BUCKET)
                .unlockedBy(getHasName(Items.DEEPSLATE), has(Items.WATER_BUCKET))
                .save(output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.ENERGY_SLIME_SPAWN_EGG.get(),1)
                .pattern("CAC")
                .pattern("ABA")
                .pattern("CAC")
                .define('A', Items.SLIME_BALL)
                .define('B', Items.EGG)
                .define('C', Items.REDSTONE)
                .unlockedBy(getHasName(Items.SLIME_BALL), has(Items.REDSTONE))
                .save(output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ENERGY_GENERATOR.get(),1)
                .pattern("CAC")
                .pattern("ABA")
                .pattern("CAC")
                .define('A', ModItems.ENERGY_SLIME_BALL)
                .define('B', Items.COPPER_BLOCK)
                .define('C', Items.REDSTONE)
                .unlockedBy(getHasName(Items.SLIME_BALL), has(Items.REDSTONE))
                .save(output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CABLE.get(),8)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', Items.REDSTONE)
                .define('B', Items.COPPER_INGOT)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.REDSTONE))
                .save(output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, ModBlocks.DNA_EXTRACTOR.get(),1)
                .pattern("AAA")
                .pattern("ACA")
                .pattern("ABA")
                .define('A', Items.IRON_INGOT)
                .define('B', ModItems.ENERGY_SLIME_BALL)
                .define('C', Items.GLASS)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.GLASS))
                .save(output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, ModBlocks.DNA_SYNTHESIZER.get(),1)
                .pattern("AAA")
                .pattern("CCC")
                .pattern("ABA")
                .define('A', Items.IRON_INGOT)
                .define('B', ModItems.ENERGY_SLIME_BALL)
                .define('C', Items.GLASS)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.GLASS))
                .save(output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.ENERGY_MULTIPLIER_UPGRADE,1)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .define('A', Items.IRON_INGOT)
                .define('B', ModItems.ENERGY_SLIME_BALL)
                .define('C', Items.BLUE_WOOL)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.REDSTONE))
                .save(output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, ModBlocks.FLUID_TANK,1)
                .pattern("AAA")
                .pattern("BCB")
                .pattern("AAA")
                .define('A', Items.IRON_INGOT)
                .define('B', Items.GLASS)
                .define('C', Items.BUCKET)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.GLASS))
                .save(output);

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, ModItems.GUIDEBOOK.get(), 1)
                .requires(Items.BOOK)
                .requires(Tags.Items.SLIME_BALLS)
                .unlockedBy(getHasName(Items.BOOK), has(Items.SLIME_BALL))
                .save(output);

        //Slime Ball Recipe
        slimeBlockToSlimeBall(output, ModBlocks.ENERGY_SLIME_BLOCK, ModItems.ENERGY_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.ENERGY_SLIME_BALL, ModBlocks.ENERGY_SLIME_BLOCK);

        for (String name : ModTierLists.TIER_NAMES){
            slimeBlockToSlimeBall(output, ModTierLists.getBlockByName(name), ModTierLists.getSlimeballItemByName(name));
            slimeBallToSlimeBlock(output, ModTierLists.getSlimeballItemByName(name), ModTierLists.getBlockByName(name));
        }

        //Melting Recipe
        for (String name : ModTierLists.TIER_NAMES){
            meltingRecipe(output, ModTierLists.getBlockByName(name), ModTierLists.getBucketItemByName(name), 2, 5);
            meltingRecipe(output, ModTierLists.getSlimeballItemByName(name), ModTierLists.getBucketItemByName(name), 4, 1);
        }

        //Soliding Recipe
        for (String name : ModTierLists.TIER_NAMES){
            ModTiers tiers = ModTierLists.getTierByName(name);
            solidingRecipe(output, ModTierLists.getBucketItemByName(name), ModTierLists.getItemByKey(tiers.getGrowthItemKey()), 1, tiers.getSolidingOutputAmount());
        }

        //DNA Extracting Recipe
        dnaExtractingRecipe(output, Items.SLIME_BALL, ModItems.SLIME_DNA, 1, 0.9f);
        for (String name : ModTierLists.TIER_NAMES){
            ModTiers tiers = ModTierLists.getTierByName(name);
            dnaExtractingRecipe(output, ModTierLists.getSlimeballItemByName(name), ModTierLists.getDnaItemByName(name), 1, tiers.getDnaOutputChance());
        }

        //DNA Synthesizing Recipe For Getting Self
        for (String name : ModTierLists.TIER_NAMES){
            ModTiers tiers = ModTierLists.getTierByName(name);
            dnaSynthesizingSelfRecipe(output, ModTierLists.getSpawnEggItemByName(name), 2, ModTierLists.getDnaItemByName(name), ModTierLists.getDnaItemByName(name), ModTierLists.getItemByKey(tiers.getSynthesizingInputItemKey()));
        }

        //DNA Synthesizing Recipe For Getting New Egg
        for (String name : ModTierLists.TIER_NAMES){
            ModTiers tiers = ModTierLists.getTierByName(name);
            dnaSynthesizingRecipe(output, ModTierLists.getSpawnEggItemByName(name), 4, ModTierLists.getItemByKey(tiers.getSynthesizingInputDnaKey1()), ModTierLists.getItemByKey(tiers.getSynthesizingInputDnaKey2()), ModTierLists.getItemByKey(tiers.getSynthesizingInputItemKey()));
        }
    }

    public static final class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider lookupProvider, RecipeOutput output) {
            return new ModRecipeProvider(lookupProvider, output);
        }

        @Override
        public String getName() {
            return "ProductiveSlimes recipes";
        }
    }

    protected void meltingRecipe(RecipeOutput pRecipeOutput, ItemLike pIngredient, ItemLike pResult, int pInputCount, int outputCount) {
        MeltingRecipeBuilder.meltingRecipe()
                .addIngredient(Ingredient.of(pIngredient))
                .setInputCount(pInputCount)
                .addOutput(new ItemStack(pResult, outputCount))
                .setEnergy(200)
                .unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "melting/" + getItemName(pIngredient) + "_melting").toString());
    }

    protected void solidingRecipe(RecipeOutput pRecipeOutput, ItemLike pIngredient, ItemLike pResult, int pInputCount, int outputCount) {
        SolidingRecipeBuilder.solidingRecipe()
                .addIngredient(Ingredient.of(pIngredient))
                .setInputCount(pInputCount)
                .addOutput(new ItemStack(pResult, outputCount))
                .addOutput(new ItemStack(Items.BUCKET, pInputCount))
                .setEnergy(200)
                .unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "soliding/" + getItemName(pIngredient) + "_soliding").toString());
    }

    protected void dnaExtractingRecipe(RecipeOutput pRecipeOutput, ItemLike pIngredient, ItemLike pResult, int outputCount, float outputChance) {
        var recipeBuilder = DnaExtractingRecipeBuilder.dnaExtractingRecipe()
                .addIngredient(Ingredient.of(pIngredient))
                .setInputCount(1)
                .addOutput(new ItemStack(pResult, outputCount));

        if (pIngredient != Items.SLIME_BALL) {
            recipeBuilder.addOutput(new ItemStack(Items.SLIME_BALL, 1));
        }

        recipeBuilder.setEnergy(400)
                .setOutputChance(outputChance)
                .unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "dna_extracting/" + getItemName(pIngredient) + "_dna_extracting").toString());

    }

    protected void dnaSynthesizingSelfRecipe(RecipeOutput pRecipeOutput, ItemLike pResult, int inputCount, ItemLike... pIngredient) {
        var recipeBuilder = DnaSynthesizingRecipeBuilder.dnaSynthesizingRecipe();

        if (pIngredient.length != 3) {
            throw new IllegalArgumentException("Only accepts 3 ingredients.");
        }

        for (var ingredient : pIngredient) {
            recipeBuilder.addIngredient(Ingredient.of(ingredient));
        }

        recipeBuilder
                .addOutput(new ItemStack(pResult, 1))
                .setInputCount(inputCount)
                .setEnergy(600)
                .unlockedBy(getHasName(Items.EGG), has(Items.EGG))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "dna_synthesizing/" + getItemName(pResult) + "_dna_synthesizing_self").toString());

    }

    protected void dnaSynthesizingRecipe(RecipeOutput pRecipeOutput, ItemLike pResult, int inputCount, ItemLike... pIngredient) {
        var recipeBuilder = DnaSynthesizingRecipeBuilder.dnaSynthesizingRecipe();

        if (pIngredient.length != 3) {
            throw new IllegalArgumentException("Only accepts 3 ingredients.");
        }

        for (var ingredient : pIngredient) {
            recipeBuilder.addIngredient(Ingredient.of(ingredient));
        }

        recipeBuilder
                .addOutput(new ItemStack(pResult, 1))
                .setInputCount(inputCount)
                .setEnergy(600)
                .unlockedBy(getHasName(Items.EGG), has(Items.EGG))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "dna_synthesizing/" + getItemName(pResult) + "_dna_synthesizing").toString());

    }

    protected void slimeBlockToSlimeBall(RecipeOutput pRecipeOutput, ItemLike pSlimeBlock, ItemLike pSlimeBall) {
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, pSlimeBall, 9)
                .requires(pSlimeBlock)
                .unlockedBy(getHasName(pSlimeBlock), has(pSlimeBlock))
                .save(pRecipeOutput, getItemName(pSlimeBall) + "_from_" + getItemName(pSlimeBlock));
    }

    protected void slimeBallToSlimeBlock(RecipeOutput pRecipeOutput, ItemLike pSlimeBall, ItemLike pSlimeBlock) {
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, pSlimeBlock, 1)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', pSlimeBall)
                .unlockedBy(getHasName(pSlimeBall), has(pSlimeBall))
                .save(pRecipeOutput, getItemName(pSlimeBlock) + "_from_" + getItemName(pSlimeBall));
    }
}
