package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.datagen.builder.*;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import com.coolerpromc.productiveslimes.util.ModTags;
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

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
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

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, ModItems.SLIMEBALL_FRAGMENT.get(), 4)
                .requires(Items.SLIME_BALL)
                .unlockedBy(getHasName(Items.SLIME_BALL), has(Items.SLIME_BALL))
                .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, Items.SLIME_BALL, 1)
                .pattern("AA ")
                .pattern("AA ")
                .pattern("   ")
                .define('A', ModItems.SLIMEBALL_FRAGMENT)
                .unlockedBy(getHasName(ModItems.SLIMEBALL_FRAGMENT), has(ModItems.SLIMEBALL_FRAGMENT))
                .save(output, "slimeball_from_fragment");

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SQUEEZER, 1)
                .pattern(" A ")
                .pattern(" A ")
                .pattern("AAA")
                .define('A', ModBlocks.SLIMY_PLANKS)
                .unlockedBy(getHasName(ModBlocks.SLIMY_PLANKS), has(ModBlocks.SLIMY_PLANKS))
                .save(output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLIME_SQUEEZER, 1)
                .pattern("BAB")
                .pattern("C  ")
                .pattern("BBB")
                .define('A', ModBlocks.SQUEEZER)
                .define('B', ModBlocks.SLIMY_STONE)
                .define('C', ModItems.ENERGY_SLIME_BALL)
                .unlockedBy(getHasName(ModBlocks.SQUEEZER), has(ModBlocks.SQUEEZER))
                .save(output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLIME_NEST, 1)
                .pattern("BBB")
                .pattern("BCB")
                .pattern("AAA")
                .define('A', ModBlocks.SLIMY_GRASS_BLOCK)
                .define('B', Items.GLASS_PANE)
                .define('C', Tags.Items.SLIME_BALLS)
                .unlockedBy(getHasName(ModBlocks.SLIMY_GRASS_BLOCK), has(Items.GLASS_PANE))
                .save(output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLIMEBALL_COLLECTOR, 1)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .define('A', Items.IRON_INGOT)
                .define('B', Items.HOPPER)
                .define('C', Tags.Items.CHESTS)
                .unlockedBy(getHasName(Items.HOPPER), has(Tags.Items.CHESTS))
                .save(output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.SLIME_NEST_SPEED_UPGRADE_1, 1)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .define('A', Items.REDSTONE_BLOCK)
                .define('B', ModTierLists.getBlockByName(Tier.IRON.getTierName()))
                .define('C', Tags.Items.INGOTS_IRON)
                .unlockedBy(getHasName(Items.REDSTONE_BLOCK), has(ModTierLists.getBlockByName(Tier.IRON.getTierName())))
                .save(output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, ModItems.SLIME_NEST_SPEED_UPGRADE_2, 1)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .define('A', ModItems.SLIME_NEST_SPEED_UPGRADE_1)
                .define('B', ModTierLists.getBlockByName(Tier.GOLD.getTierName()))
                .define('C', Tags.Items.INGOTS_GOLD)
                .unlockedBy(getHasName(ModItems.SLIME_NEST_SPEED_UPGRADE_1), has(ModTierLists.getBlockByName(Tier.GOLD.getTierName())))
                .save(output);

        planksFromLogs(ModBlocks.SLIMY_PLANKS.get(), ModTags.Items.SLIMY_LOG, 4);
        woodFromLogs(ModBlocks.SLIMY_WOOD.get(), ModBlocks.SLIMY_LOG.get());
        woodFromLogs(ModBlocks.STRIPPED_SLIMY_WOOD.get(), ModBlocks.STRIPPED_SLIMY_LOG.get());
        stairBuilder(ModBlocks.SLIMY_STAIRS.get(), Ingredient.of(ModBlocks.SLIMY_PLANKS)).group("slimy")
                .unlockedBy("has_slimy", has(ModBlocks.SLIMY_PLANKS)).save(output);
        slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLIMY_SLAB.get(), ModBlocks.SLIMY_PLANKS.get());
        buttonBuilder(ModBlocks.SLIMY_BUTTON.get(), Ingredient.of(ModBlocks.SLIMY_PLANKS.get())).group("slimy")
                .unlockedBy("has_slimy", has(ModBlocks.SLIMY_PLANKS.get())).save(output);
        pressurePlate(ModBlocks.SLIMY_PRESSURE_PLATE.get(), ModBlocks.SLIMY_PLANKS.get());
        fenceBuilder(ModBlocks.SLIMY_FENCE.get(), Ingredient.of(ModBlocks.SLIMY_PLANKS.get())).group("slimy")
                .unlockedBy("has_slimy", has(ModBlocks.SLIMY_PLANKS.get())).save(output);
        fenceGateBuilder(ModBlocks.SLIMY_FENCE_GATE.get(), Ingredient.of(ModBlocks.SLIMY_PLANKS.get())).group("slimy")
                .unlockedBy("has_slimy", has(ModBlocks.SLIMY_PLANKS.get())).save(output);
        doorBuilder(ModBlocks.SLIMY_DOOR.get(), Ingredient.of(ModBlocks.SLIMY_PLANKS.get())).group("slimy")
                .unlockedBy("has_slimy", has(ModBlocks.SLIMY_PLANKS.get())).save(output);
        trapdoorBuilder(ModBlocks.SLIMY_TRAPDOOR.get(), Ingredient.of(ModBlocks.SLIMY_PLANKS.get())).group("slimy")
                .unlockedBy("has_slimy", has(ModBlocks.SLIMY_PLANKS.get())).save(output);
        stairBuilder(ModBlocks.SLIMY_STONE_STAIRS.get(), Ingredient.of(ModBlocks.SLIMY_STONE)).group("slimy_stone")
                .unlockedBy("has_slimy_stone", has(ModBlocks.SLIMY_STONE)).save(output);
        slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLIMY_STONE_SLAB.get(), ModBlocks.SLIMY_STONE.get());
        buttonBuilder(ModBlocks.SLIMY_STONE_BUTTON.get(), Ingredient.of(ModBlocks.SLIMY_STONE.get())).group("slimy_stone")
                .unlockedBy("has_slimy_stone", has(ModBlocks.SLIMY_STONE.get())).save(output);
        pressurePlate(ModBlocks.SLIMY_STONE_PRESSURE_PLATE.get(), ModBlocks.SLIMY_STONE.get());
        stairBuilder(ModBlocks.SLIMY_COBBLESTONE_STAIRS.get(), Ingredient.of(ModBlocks.SLIMY_COBBLESTONE)).group("slimy_cobblestone")
                .unlockedBy("has_slimy_cobblestone", has(ModBlocks.SLIMY_COBBLESTONE)).save(output);
        slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLIMY_COBBLESTONE_SLAB.get(), ModBlocks.SLIMY_COBBLESTONE.get());
        wall(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLIMY_COBBLESTONE_WALL.get(), ModBlocks.SLIMY_COBBLESTONE.get());
        stairBuilder(ModBlocks.SLIMY_COBBLED_DEEPSLATE_STAIRS.get(), Ingredient.of(ModBlocks.SLIMY_COBBLED_DEEPSLATE)).group("slimy_cobbled_deepslate")
                .unlockedBy("has_slimy_cobbled_deepslate", has(ModBlocks.SLIMY_COBBLED_DEEPSLATE)).save(output);
        slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLIMY_COBBLED_DEEPSLATE_SLAB.get(), ModBlocks.SLIMY_COBBLED_DEEPSLATE.get());
        wall(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLIMY_COBBLED_DEEPSLATE_WALL.get(), ModBlocks.SLIMY_COBBLED_DEEPSLATE.get());

        smeltingRecipe(output, ModBlocks.SLIMY_COBBLESTONE.get(), ModBlocks.SLIMY_STONE.get(), 0.1f, 200);
        smeltingRecipe(output, ModBlocks.SLIMY_COBBLED_DEEPSLATE.get(), ModBlocks.SLIMY_DEEPSLATE.get(), 0.1f, 200);

        //Slime Ball Recipe
        slimeBlockToSlimeBall(output, ModBlocks.ENERGY_SLIME_BLOCK, ModItems.ENERGY_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.ENERGY_SLIME_BALL, ModBlocks.ENERGY_SLIME_BLOCK);

        for (Tier tier : Tier.values()){
            ModTiers tiers = ModTierLists.getTierByName(tier);
            slimeBlockToSlimeBall(output, ModTierLists.getBlockByName(tiers.name()), ModTierLists.getSlimeballItemByName(tiers.name()));
            slimeBallToSlimeBlock(output, ModTierLists.getSlimeballItemByName(tiers.name()), ModTierLists.getBlockByName(tiers.name()));

            meltingRecipe(output, ModTierLists.getBlockByName(tiers.name()), ModTierLists.getBucketItemByName(tiers.name()), 2, 5);
            meltingRecipe(output, ModTierLists.getSlimeballItemByName(tiers.name()), ModTierLists.getBucketItemByName(tiers.name()), 4, 1);

            solidingRecipe(output, ModTierLists.getBucketItemByName(tiers.name()), ModTierLists.getItemByKey(tiers.solidingOutputKey()), 1, tiers.solidingOutputAmount());

            dnaExtractingRecipe(output, ModTierLists.getSlimeballItemByName(tiers.name()), ModTierLists.getDnaItemByName(tiers.name()), 1, tiers.dnaOutputChance());

            dnaSynthesizingSelfRecipe(output, ModTierLists.getSpawnEggItemByName(tiers.name()), 2, ModTierLists.getDnaItemByName(tiers.name()), ModTierLists.getDnaItemByName(tiers.name()), ModTierLists.getItemByKey(tiers.synthesizingInputItemKey()));

            dnaSynthesizingRecipe(output, ModTierLists.getSpawnEggItemByName(tiers.name()), 4, ModTierLists.getItemByKey(tiers.synthesizingInputDnaKey1()), ModTierLists.getItemByKey(tiers.synthesizingInputDnaKey2()), ModTierLists.getItemByKey(tiers.synthesizingInputItemKey()));
        }

        squeezingRecipe(output, ModBlocks.SLIMY_DIRT, new ItemStack(Items.DIRT, 1), new ItemStack(ModItems.SLIMEBALL_FRAGMENT.get(), 1));
        squeezingRecipe(output, ModBlocks.SLIMY_GRASS_BLOCK, new ItemStack(Items.GRASS_BLOCK, 1), new ItemStack(ModItems.SLIMEBALL_FRAGMENT.get(), 1));
        squeezingRecipe(output, ModBlocks.SLIMY_STONE, new ItemStack(Items.STONE, 1), new ItemStack(ModItems.SLIMEBALL_FRAGMENT.get(), 1));
        squeezingRecipe(output, ModBlocks.SLIMY_DEEPSLATE, new ItemStack(Items.DEEPSLATE, 1), new ItemStack(ModItems.SLIMEBALL_FRAGMENT.get(), 1));
        squeezingRecipe(output, ModBlocks.SLIMY_COBBLESTONE, new ItemStack(Items.COBBLESTONE, 1), new ItemStack(ModItems.SLIMEBALL_FRAGMENT.get(), 1));
        squeezingRecipe(output, ModBlocks.SLIMY_COBBLED_DEEPSLATE, new ItemStack(Items.COBBLED_DEEPSLATE, 1), new ItemStack(ModItems.SLIMEBALL_FRAGMENT.get(), 1));
        squeezingRecipe(output, ModBlocks.SLIMY_LOG, new ItemStack(Items.OAK_LOG, 1), new ItemStack(ModItems.SLIMEBALL_FRAGMENT.get(), 1));
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

    protected void squeezingRecipe(RecipeOutput pRecipeOutput, ItemLike pIngredient, ItemStack pResult1, ItemStack pResult2) {
        SqueezingRecipeBuilder.squeezingRecipe()
                .addIngredient(Ingredient.of(pIngredient))
                .addOutput(pResult1)
                .addOutput(pResult2)
                .setEnergy(300)
                .unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "squeezing/" + getItemName(pIngredient) + "_squeezing").toString());
    }

    private void smeltingRecipe(RecipeOutput pRecipeOutput, ItemLike pIngredient, ItemLike pResult, float pExperience, int pCookingTime) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(pIngredient), RecipeCategory.BUILDING_BLOCKS, pResult, pExperience, pCookingTime).unlockedBy(getHasName(pIngredient), has(pIngredient)).save(pRecipeOutput);
    }
}
