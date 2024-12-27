package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.datagen.builder.*;
import com.coolerpromc.productiveslimes.fluid.ModFluids;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import com.coolerpromc.productiveslimes.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

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

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ENERGY_SLIME_SPAWN_EGG.get(),1)
                .pattern("CAC")
                .pattern("ABA")
                .pattern("CAC")
                .define('A', Items.SLIME_BALL)
                .define('B', Items.EGG)
                .define('C', Items.REDSTONE)
                .unlockedBy(getHasName(Items.SLIME_BALL), has(Items.REDSTONE))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ENERGY_GENERATOR.get(),1)
                .pattern("CAC")
                .pattern("ABA")
                .pattern("CAC")
                .define('A', ModItems.ENERGY_SLIME_BALL)
                .define('B', Items.COPPER_BLOCK)
                .define('C', Items.REDSTONE)
                .unlockedBy(getHasName(Items.SLIME_BALL), has(Items.REDSTONE))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CABLE.get(),8)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', Items.REDSTONE)
                .define('B', Items.COPPER_INGOT)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.REDSTONE))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DNA_EXTRACTOR.get(),1)
                .pattern("AAA")
                .pattern("ACA")
                .pattern("ABA")
                .define('A', Items.IRON_INGOT)
                .define('B', ModItems.ENERGY_SLIME_BALL)
                .define('C', Items.GLASS)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.GLASS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DNA_SYNTHESIZER.get(),1)
                .pattern("AAA")
                .pattern("CCC")
                .pattern("ABA")
                .define('A', Items.IRON_INGOT)
                .define('B', ModItems.ENERGY_SLIME_BALL)
                .define('C', Items.GLASS)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.GLASS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ENERGY_MULTIPLIER_UPGRADE,1)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .define('A', Items.IRON_INGOT)
                .define('B', ModItems.ENERGY_SLIME_BALL)
                .define('C', Items.BLUE_WOOL)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.REDSTONE))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.FLUID_TANK,1)
                .pattern("AAA")
                .pattern("BCB")
                .pattern("AAA")
                .define('A', Items.IRON_INGOT)
                .define('B', Items.GLASS)
                .define('C', Items.BUCKET)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.GLASS))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GUIDEBOOK.get(), 1)
                .requires(Items.BOOK)
                .requires(Tags.Items.SLIME_BALLS)
                .unlockedBy(getHasName(Items.BOOK), has(Items.SLIME_BALL))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SLIMEBALL_FRAGMENT.get(), 4)
                .requires(Items.SLIME_BALL)
                .unlockedBy(getHasName(Items.SLIME_BALL), has(Items.SLIME_BALL))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Items.SLIME_BALL, 1)
                .pattern("AA ")
                .pattern("AA ")
                .pattern("   ")
                .define('A', ModItems.SLIMEBALL_FRAGMENT)
                .unlockedBy(getHasName(ModItems.SLIMEBALL_FRAGMENT), has(ModItems.SLIMEBALL_FRAGMENT))
                .save(recipeOutput, "slimeball_from_fragment");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SQUEEZER, 1)
                .pattern(" A ")
                .pattern(" A ")
                .pattern("AAA")
                .define('A', ModBlocks.SLIMY_PLANKS)
                .unlockedBy(getHasName(ModBlocks.SLIMY_PLANKS), has(ModBlocks.SLIMY_PLANKS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLIME_SQUEEZER, 1)
                .pattern("BAB")
                .pattern("C  ")
                .pattern("BBB")
                .define('A', ModBlocks.SQUEEZER)
                .define('B', ModBlocks.SLIMY_STONE)
                .define('C', ModItems.ENERGY_SLIME_BALL)
                .unlockedBy(getHasName(ModBlocks.SQUEEZER), has(ModBlocks.SQUEEZER))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLIME_NEST, 1)
                .pattern("BBB")
                .pattern("BCB")
                .pattern("AAA")
                .define('A', ModBlocks.SLIMY_GRASS_BLOCK)
                .define('B', Items.GLASS_PANE)
                .define('C', Tags.Items.SLIME_BALLS)
                .unlockedBy(getHasName(ModBlocks.SLIMY_GRASS_BLOCK), has(Items.GLASS_PANE))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLIMEBALL_COLLECTOR, 1)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .define('A', Items.IRON_INGOT)
                .define('B', Items.HOPPER)
                .define('C', Tags.Items.CHESTS)
                .unlockedBy(getHasName(Items.HOPPER), has(Tags.Items.CHESTS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.SLIME_NEST_SPEED_UPGRADE_1, 1)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .define('A', Items.REDSTONE_BLOCK)
                .define('B', ModTierLists.getBlockByName(Tier.IRON.getTierName()))
                .define('C', Tags.Items.INGOTS_IRON)
                .unlockedBy(getHasName(Items.REDSTONE_BLOCK), has(ModTierLists.getBlockByName(Tier.IRON.getTierName())))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.SLIME_NEST_SPEED_UPGRADE_2, 1)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .define('A', ModItems.SLIME_NEST_SPEED_UPGRADE_1)
                .define('B', ModTierLists.getBlockByName(Tier.GOLD.getTierName()))
                .define('C', Tags.Items.INGOTS_GOLD)
                .unlockedBy(getHasName(ModItems.SLIME_NEST_SPEED_UPGRADE_1), has(ModTierLists.getBlockByName(Tier.GOLD.getTierName())))
                .save(recipeOutput);

        planksFromLogs(recipeOutput, ModBlocks.SLIMY_PLANKS.get(), ModTags.Items.SLIMY_LOG, 4);

        woodFromLogs(recipeOutput, ModBlocks.SLIMY_WOOD.get(), ModBlocks.SLIMY_LOG.get());

        woodFromLogs(recipeOutput, ModBlocks.STRIPPED_SLIMY_WOOD.get(), ModBlocks.STRIPPED_SLIMY_LOG.get());

        stairBuilder(ModBlocks.SLIMY_STAIRS.get(), Ingredient.of(ModBlocks.SLIMY_PLANKS)).group("slimy")
                .unlockedBy("has_slimy", has(ModBlocks.SLIMY_PLANKS)).save(recipeOutput);

        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLIMY_SLAB.get(), ModBlocks.SLIMY_PLANKS.get());

        buttonBuilder(ModBlocks.SLIMY_BUTTON.get(), Ingredient.of(ModBlocks.SLIMY_PLANKS.get())).group("slimy")
                .unlockedBy("has_slimy", has(ModBlocks.SLIMY_PLANKS.get())).save(recipeOutput);

        pressurePlate(recipeOutput, ModBlocks.SLIMY_PRESSURE_PLATE.get(), ModBlocks.SLIMY_PLANKS.get());

        fenceBuilder(ModBlocks.SLIMY_FENCE.get(), Ingredient.of(ModBlocks.SLIMY_PLANKS.get())).group("slimy")
                .unlockedBy("has_slimy", has(ModBlocks.SLIMY_PLANKS.get())).save(recipeOutput);

        fenceGateBuilder(ModBlocks.SLIMY_FENCE_GATE.get(), Ingredient.of(ModBlocks.SLIMY_PLANKS.get())).group("slimy")
                .unlockedBy("has_slimy", has(ModBlocks.SLIMY_PLANKS.get())).save(recipeOutput);

        doorBuilder(ModBlocks.SLIMY_DOOR.get(), Ingredient.of(ModBlocks.SLIMY_PLANKS.get())).group("slimy")
                .unlockedBy("has_slimy", has(ModBlocks.SLIMY_PLANKS.get())).save(recipeOutput);

        trapdoorBuilder(ModBlocks.SLIMY_TRAPDOOR.get(), Ingredient.of(ModBlocks.SLIMY_PLANKS.get())).group("slimy")
                .unlockedBy("has_slimy", has(ModBlocks.SLIMY_PLANKS.get())).save(recipeOutput);

        stairBuilder(ModBlocks.SLIMY_STONE_STAIRS.get(), Ingredient.of(ModBlocks.SLIMY_STONE)).group("slimy_stone")
                .unlockedBy("has_slimy_stone", has(ModBlocks.SLIMY_STONE)).save(recipeOutput);

        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLIMY_STONE_SLAB.get(), ModBlocks.SLIMY_STONE.get());

        buttonBuilder(ModBlocks.SLIMY_STONE_BUTTON.get(), Ingredient.of(ModBlocks.SLIMY_STONE.get())).group("slimy_stone")
                .unlockedBy("has_slimy_stone", has(ModBlocks.SLIMY_STONE.get())).save(recipeOutput);

        pressurePlate(recipeOutput, ModBlocks.SLIMY_STONE_PRESSURE_PLATE.get(), ModBlocks.SLIMY_STONE.get());

        stairBuilder(ModBlocks.SLIMY_COBBLESTONE_STAIRS.get(), Ingredient.of(ModBlocks.SLIMY_COBBLESTONE)).group("slimy_cobblestone")
                .unlockedBy("has_slimy_cobblestone", has(ModBlocks.SLIMY_COBBLESTONE)).save(recipeOutput);

        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLIMY_COBBLESTONE_SLAB.get(), ModBlocks.SLIMY_COBBLESTONE.get());

        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLIMY_COBBLESTONE_WALL.get(), ModBlocks.SLIMY_COBBLESTONE.get());

        stairBuilder(ModBlocks.SLIMY_COBBLED_DEEPSLATE_STAIRS.get(), Ingredient.of(ModBlocks.SLIMY_COBBLED_DEEPSLATE)).group("slimy_cobbled_deepslate")
                .unlockedBy("has_slimy_cobbled_deepslate", has(ModBlocks.SLIMY_COBBLED_DEEPSLATE)).save(recipeOutput);

        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLIMY_COBBLED_DEEPSLATE_SLAB.get(), ModBlocks.SLIMY_COBBLED_DEEPSLATE.get());

        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLIMY_COBBLED_DEEPSLATE_WALL.get(), ModBlocks.SLIMY_COBBLED_DEEPSLATE.get());

        //Slime Ball Recipe
        slimeBlockToSlimeBall(recipeOutput, ModBlocks.ENERGY_SLIME_BLOCK, ModItems.ENERGY_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.ENERGY_SLIME_BALL, ModBlocks.ENERGY_SLIME_BLOCK);

        //Dna Extracting Recipe
        dnaExtractingRecipe(recipeOutput, Items.SLIME_BALL, ModItems.SLIME_DNA, 1, 0.9f);

        for(Tier tier : Tier.values()){
            ModTiers modTiers = ModTierLists.getTierByName(tier);
            String name = modTiers.name();

            //Slime Ball Recipe
            slimeBlockToSlimeBall(recipeOutput, ModTierLists.getBlockByName(name), ModTierLists.getSlimeballItemByName(name));
            slimeBallToSlimeBlock(recipeOutput, ModTierLists.getSlimeballItemByName(name), ModTierLists.getBlockByName(name));

            //Melting Recipe
            meltingRecipe(recipeOutput, ModTierLists.getBlockByName(name), ModTierLists.getBucketItemByName(name), 2, 5);
            meltingRecipe(recipeOutput, ModTierLists.getSlimeballItemByName(name), ModTierLists.getBucketItemByName(name), 4, 1);

            //Soliding Recipe
            solidingRecipe(recipeOutput, ModTierLists.getBucketItemByName(name), ModTierLists.getItemByKey(modTiers.growthItemKey()), 1, modTiers.solidingOutputAmount());

            //Dna Extracting Recipe
            dnaExtractingRecipe(recipeOutput, ModTierLists.getSlimeballItemByName(name), ModTierLists.getDnaItemByName(name), 1, modTiers.dnaOutputChance());

            //Dna Synthesizing Self Recipe
            dnaSynthesizingSelfRecipe(recipeOutput, ModTierLists.getSpawnEggItemByName(name), 2, ModTierLists.getDnaItemByName(name), ModTierLists.getDnaItemByName(name), ModTierLists.getItemByKey(modTiers.synthesizingInputItemKey()));

            //Dna Synthesizing Recipe
            dnaSynthesizingRecipe(recipeOutput, ModTierLists.getSpawnEggItemByName(name), 4, ModTierLists.getItemByKey(modTiers.synthesizingInputDnaKey1()), ModTierLists.getItemByKey(modTiers.synthesizingInputDnaKey2()), ModTierLists.getItemByKey(modTiers.synthesizingInputItemKey()));
        }

        squeezingRecipe(recipeOutput, ModBlocks.SLIMY_DIRT, new ItemStack(Items.DIRT, 1), new ItemStack(ModItems.SLIMEBALL_FRAGMENT.get(), 1));
        squeezingRecipe(recipeOutput, ModBlocks.SLIMY_GRASS_BLOCK, new ItemStack(Items.GRASS_BLOCK, 1), new ItemStack(ModItems.SLIMEBALL_FRAGMENT.get(), 1));
        squeezingRecipe(recipeOutput, ModBlocks.SLIMY_STONE, new ItemStack(Items.STONE, 1), new ItemStack(ModItems.SLIMEBALL_FRAGMENT.get(), 1));
        squeezingRecipe(recipeOutput, ModBlocks.SLIMY_DEEPSLATE, new ItemStack(Items.DEEPSLATE, 1), new ItemStack(ModItems.SLIMEBALL_FRAGMENT.get(), 1));
        squeezingRecipe(recipeOutput, ModBlocks.SLIMY_COBBLESTONE, new ItemStack(Items.COBBLESTONE, 1), new ItemStack(ModItems.SLIMEBALL_FRAGMENT.get(), 1));
        squeezingRecipe(recipeOutput, ModBlocks.SLIMY_COBBLED_DEEPSLATE, new ItemStack(Items.COBBLED_DEEPSLATE, 1), new ItemStack(ModItems.SLIMEBALL_FRAGMENT.get(), 1));
        squeezingRecipe(recipeOutput, ModBlocks.SLIMY_LOG, new ItemStack(Items.OAK_LOG, 1), new ItemStack(ModItems.SLIMEBALL_FRAGMENT.get(), 1));
    }

    protected static void meltingRecipe(RecipeOutput pRecipeOutput, ItemLike pIngredient, ItemLike pResult, int pInputCount, int outputCount) {
        MeltingRecipeBuilder.meltingRecipe()
                .addIngredient(Ingredient.of(pIngredient))
                .setInputCount(pInputCount)
                .addOutput(new ItemStack(pResult, outputCount))
                .setEnergy(200)
                .unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "melting/" + getItemName(pIngredient) + "_melting"));
    }

    protected static void solidingRecipe(RecipeOutput pRecipeOutput, ItemLike pIngredient, ItemLike pResult, int pInputCount, int outputCount) {
        SolidingRecipeBuilder.solidingRecipe()
                .addIngredient(Ingredient.of(pIngredient))
                .setInputCount(pInputCount)
                .addOutput(new ItemStack(pResult, outputCount))
                .addOutput(new ItemStack(Items.BUCKET, pInputCount))
                .setEnergy(200)
                .unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "soliding/" + getItemName(pIngredient) + "_soliding"));
    }

    protected static void dnaExtractingRecipe(RecipeOutput pRecipeOutput, ItemLike pIngredient, ItemLike pResult, int outputCount, float outputChance) {
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
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "dna_extracting/" + getItemName(pIngredient) + "_dna_extracting"));

    }

    protected static void dnaSynthesizingSelfRecipe(RecipeOutput pRecipeOutput, ItemLike pResult, int inputCount, ItemLike... pIngredient) {
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
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "dna_synthesizing/" + getItemName(pResult) + "_dna_synthesizing_self"));

    }

    protected static void dnaSynthesizingRecipe(RecipeOutput pRecipeOutput, ItemLike pResult, int inputCount, ItemLike... pIngredient) {
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
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "dna_synthesizing/" + getItemName(pResult) + "_dna_synthesizing"));

    }

    protected static void squeezingRecipe(RecipeOutput pRecipeOutput, ItemLike pIngredient, ItemStack pResult1, ItemStack pResult2) {
        SqueezingRecipeBuilder.squeezingRecipe()
                .addIngredient(Ingredient.of(pIngredient))
                .addOutput(pResult1)
                .addOutput(pResult2)
                .setEnergy(300)
                .unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "squeezing/" + getItemName(pIngredient) + "_squeezing"));
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
