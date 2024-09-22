package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.datagen.builder.DnaExtractingRecipeBuilder;
import com.coolerpromc.productiveslimes.datagen.builder.DnaSynthesizingRecipeBuilder;
import com.coolerpromc.productiveslimes.datagen.builder.MeltingRecipeBuilder;
import com.coolerpromc.productiveslimes.datagen.builder.SolidingRecipeBuilder;
import com.coolerpromc.productiveslimes.fluid.ModFluids;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.recipe.DnaSynthesizingRecipe;
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
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.REDSTONE))
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

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.SAND_SLIME_BLOCK, ModItems.SAND_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.SAND_SLIME_BALL, ModBlocks.SAND_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.ANDESITE_SLIME_BLOCK, ModItems.ANDESITE_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.ANDESITE_SLIME_BALL, ModBlocks.ANDESITE_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.SNOW_SLIME_BLOCK, ModItems.SNOW_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.SNOW_SLIME_BALL, ModBlocks.SNOW_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.ICE_SLIME_BLOCK, ModItems.ICE_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.ICE_SLIME_BALL, ModBlocks.ICE_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.MUD_SLIME_BLOCK, ModItems.MUD_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.MUD_SLIME_BALL, ModBlocks.MUD_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.CLAY_SLIME_BLOCK, ModItems.CLAY_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.CLAY_SLIME_BALL, ModBlocks.CLAY_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.RED_SAND_SLIME_BLOCK, ModItems.RED_SAND_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.RED_SAND_SLIME_BALL, ModBlocks.RED_SAND_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.MOSS_SLIME_BLOCK, ModItems.MOSS_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.MOSS_SLIME_BALL, ModBlocks.MOSS_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.DEEPSLATE_SLIME_BLOCK, ModItems.DEEPSLATE_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.DEEPSLATE_SLIME_BALL, ModBlocks.DEEPSLATE_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.GRANITE_SLIME_BLOCK, ModItems.GRANITE_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.GRANITE_SLIME_BALL, ModBlocks.GRANITE_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.DIORITE_SLIME_BLOCK, ModItems.DIORITE_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.DIORITE_SLIME_BALL, ModBlocks.DIORITE_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.CALCITE_SLIME_BLOCK, ModItems.CALCITE_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.CALCITE_SLIME_BALL, ModBlocks.CALCITE_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.TUFF_SLIME_BLOCK, ModItems.TUFF_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.TUFF_SLIME_BALL, ModBlocks.TUFF_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.DRIPSTONE_SLIME_BLOCK, ModItems.DRIPSTONE_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.DRIPSTONE_SLIME_BALL, ModBlocks.DRIPSTONE_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.PRISMARINE_SLIME_BLOCK, ModItems.PRISMARINE_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.PRISMARINE_SLIME_BALL, ModBlocks.PRISMARINE_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.MAGMA_SLIME_BLOCK, ModItems.MAGMA_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.MAGMA_SLIME_BALL, ModBlocks.MAGMA_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.OBSIDIAN_SLIME_BLOCK, ModItems.OBSIDIAN_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.OBSIDIAN_SLIME_BALL, ModBlocks.OBSIDIAN_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.NETHERRACK_SLIME_BLOCK, ModItems.NETHERRACK_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.NETHERRACK_SLIME_BALL, ModBlocks.NETHERRACK_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.SOUL_SAND_SLIME_BLOCK, ModItems.SOUL_SAND_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.SOUL_SAND_SLIME_BALL, ModBlocks.SOUL_SAND_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.SOUL_SOIL_SLIME_BLOCK, ModItems.SOUL_SOIL_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.SOUL_SOIL_SLIME_BALL, ModBlocks.SOUL_SOIL_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.BLACKSTONE_SLIME_BLOCK, ModItems.BLACKSTONE_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.BLACKSTONE_SLIME_BALL, ModBlocks.BLACKSTONE_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.BASALT_SLIME_BLOCK, ModItems.BASALT_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.BASALT_SLIME_BALL, ModBlocks.BASALT_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.ENDSTONE_SLIME_BLOCK, ModItems.ENDSTONE_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.ENDSTONE_SLIME_BALL, ModBlocks.ENDSTONE_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.QUARTZ_SLIME_BLOCK, ModItems.QUARTZ_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.QUARTZ_SLIME_BALL, ModBlocks.QUARTZ_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.GLOWSTONE_SLIME_BLOCK, ModItems.GLOWSTONE_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.GLOWSTONE_SLIME_BALL, ModBlocks.GLOWSTONE_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.AMETHYST_SLIME_BLOCK, ModItems.AMETHYST_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.AMETHYST_SLIME_BALL, ModBlocks.AMETHYST_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.BROWN_MUSHROOM_SLIME_BLOCK, ModItems.BROWN_MUSHROOM_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.BROWN_MUSHROOM_SLIME_BALL, ModBlocks.BROWN_MUSHROOM_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.RED_MUSHROOM_SLIME_BLOCK, ModItems.RED_MUSHROOM_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.RED_MUSHROOM_SLIME_BALL, ModBlocks.RED_MUSHROOM_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.CACTUS_SLIME_BLOCK, ModItems.CACTUS_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.CACTUS_SLIME_BALL, ModBlocks.CACTUS_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.COAL_SLIME_BLOCK, ModItems.COAL_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.COAL_SLIME_BALL, ModBlocks.COAL_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.GRAVEL_SLIME_BLOCK, ModItems.GRAVEL_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.GRAVEL_SLIME_BALL, ModBlocks.GRAVEL_SLIME_BLOCK);

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.ENERGY_SLIME_BLOCK, ModItems.ENERGY_SLIME_BALL);
        slimeBallToSlimeBlock(recipeOutput, ModItems.ENERGY_SLIME_BALL, ModBlocks.ENERGY_SLIME_BLOCK);

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

        meltingRecipe(recipeOutput, ModBlocks.SAND_SLIME_BLOCK, ModFluids.MOLTEN_SAND_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.SAND_SLIME_BALL, ModFluids.MOLTEN_SAND_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.ANDESITE_SLIME_BLOCK, ModFluids.MOLTEN_ANDESITE_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.ANDESITE_SLIME_BALL, ModFluids.MOLTEN_ANDESITE_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.SNOW_SLIME_BLOCK, ModFluids.MOLTEN_SNOW_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.SNOW_SLIME_BALL, ModFluids.MOLTEN_SNOW_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.ICE_SLIME_BLOCK, ModFluids.MOLTEN_ICE_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.ICE_SLIME_BALL, ModFluids.MOLTEN_ICE_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.MUD_SLIME_BLOCK, ModFluids.MOLTEN_MUD_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.MUD_SLIME_BALL, ModFluids.MOLTEN_MUD_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.CLAY_SLIME_BLOCK, ModFluids.MOLTEN_CLAY_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.CLAY_SLIME_BALL, ModFluids.MOLTEN_CLAY_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.RED_SAND_SLIME_BLOCK, ModFluids.MOLTEN_RED_SAND_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.RED_SAND_SLIME_BALL, ModFluids.MOLTEN_RED_SAND_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.MOSS_SLIME_BLOCK, ModFluids.MOLTEN_MOSS_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.MOSS_SLIME_BALL, ModFluids.MOLTEN_MOSS_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.DEEPSLATE_SLIME_BLOCK, ModFluids.MOLTEN_DEEPSLATE_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.DEEPSLATE_SLIME_BALL, ModFluids.MOLTEN_DEEPSLATE_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.GRANITE_SLIME_BLOCK, ModFluids.MOLTEN_GRANITE_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.GRANITE_SLIME_BALL, ModFluids.MOLTEN_GRANITE_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.DIORITE_SLIME_BLOCK, ModFluids.MOLTEN_DIORITE_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.DIORITE_SLIME_BALL, ModFluids.MOLTEN_DIORITE_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.CALCITE_SLIME_BLOCK, ModFluids.MOLTEN_CALCITE_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.CALCITE_SLIME_BALL, ModFluids.MOLTEN_CALCITE_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.TUFF_SLIME_BLOCK, ModFluids.MOLTEN_TUFF_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.TUFF_SLIME_BALL, ModFluids.MOLTEN_TUFF_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.DRIPSTONE_SLIME_BLOCK, ModFluids.MOLTEN_DRIPSTONE_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.DRIPSTONE_SLIME_BALL, ModFluids.MOLTEN_DRIPSTONE_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.PRISMARINE_SLIME_BLOCK, ModFluids.MOLTEN_PRISMARINE_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.PRISMARINE_SLIME_BALL, ModFluids.MOLTEN_PRISMARINE_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.MAGMA_SLIME_BLOCK, ModFluids.MOLTEN_MAGMA_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.MAGMA_SLIME_BALL, ModFluids.MOLTEN_MAGMA_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.OBSIDIAN_SLIME_BLOCK, ModFluids.MOLTEN_OBSIDIAN_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.OBSIDIAN_SLIME_BALL, ModFluids.MOLTEN_OBSIDIAN_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.NETHERRACK_SLIME_BLOCK, ModFluids.MOLTEN_NETHERRACK_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.NETHERRACK_SLIME_BALL, ModFluids.MOLTEN_NETHERRACK_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.SOUL_SAND_SLIME_BLOCK, ModFluids.MOLTEN_SOUL_SAND_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.SOUL_SAND_SLIME_BALL, ModFluids.MOLTEN_SOUL_SAND_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.SOUL_SOIL_SLIME_BLOCK, ModFluids.MOLTEN_SOUL_SOIL_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.SOUL_SOIL_SLIME_BALL, ModFluids.MOLTEN_SOUL_SOIL_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.BLACKSTONE_SLIME_BLOCK, ModFluids.MOLTEN_BLACKSTONE_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.BLACKSTONE_SLIME_BALL, ModFluids.MOLTEN_BLACKSTONE_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.BASALT_SLIME_BLOCK, ModFluids.MOLTEN_BASALT_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.BASALT_SLIME_BALL, ModFluids.MOLTEN_BASALT_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.ENDSTONE_SLIME_BLOCK, ModFluids.MOLTEN_ENDSTONE_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.ENDSTONE_SLIME_BALL, ModFluids.MOLTEN_ENDSTONE_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.QUARTZ_SLIME_BLOCK, ModFluids.MOLTEN_QUARTZ_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.QUARTZ_SLIME_BALL, ModFluids.MOLTEN_QUARTZ_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.GLOWSTONE_SLIME_BLOCK, ModFluids.MOLTEN_GLOWSTONE_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.GLOWSTONE_SLIME_BALL, ModFluids.MOLTEN_GLOWSTONE_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.AMETHYST_SLIME_BLOCK, ModFluids.MOLTEN_AMETHYST_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.AMETHYST_SLIME_BALL, ModFluids.MOLTEN_AMETHYST_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.BROWN_MUSHROOM_SLIME_BLOCK, ModFluids.MOLTEN_BROWN_MUSHROOM_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.BROWN_MUSHROOM_SLIME_BALL, ModFluids.MOLTEN_BROWN_MUSHROOM_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.RED_MUSHROOM_SLIME_BLOCK, ModFluids.MOLTEN_RED_MUSHROOM_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.RED_MUSHROOM_SLIME_BALL, ModFluids.MOLTEN_RED_MUSHROOM_BUCKET, 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.CACTUS_SLIME_BLOCK, ModFluids.MOLTEN_CACTUS_BUCKET, 2, 5);
        meltingRecipe(recipeOutput, ModItems.CACTUS_SLIME_BALL, ModFluids.MOLTEN_CACTUS_BUCKET, 4, 1);

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
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_SAND_BUCKET, Items.SAND, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_ANDESITE_BUCKET, Items.ANDESITE, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_SNOW_BUCKET, Items.SNOW, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_ICE_BUCKET, Items.ICE, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_MUD_BUCKET, Items.MUD, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_CLAY_BUCKET, Items.CLAY, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_RED_SAND_BUCKET, Items.RED_SAND, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_MOSS_BUCKET, Items.MOSS_BLOCK, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_DEEPSLATE_BUCKET, Items.DEEPSLATE, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_GRANITE_BUCKET, Items.GRANITE, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_DIORITE_BUCKET, Items.DIORITE, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_CALCITE_BUCKET, Items.CALCITE, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_TUFF_BUCKET, Items.TUFF, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_DRIPSTONE_BUCKET, Items.POINTED_DRIPSTONE, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_PRISMARINE_BUCKET, Items.PRISMARINE_SHARD, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_MAGMA_BUCKET, Items.MAGMA_BLOCK, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_OBSIDIAN_BUCKET, Items.OBSIDIAN, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_NETHERRACK_BUCKET, Items.NETHERRACK, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_SOUL_SAND_BUCKET, Items.SOUL_SAND, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_SOUL_SOIL_BUCKET, Items.SOUL_SOIL, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_BLACKSTONE_BUCKET, Items.BLACKSTONE, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_BASALT_BUCKET, Items.BASALT, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_ENDSTONE_BUCKET, Items.END_STONE, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_QUARTZ_BUCKET, Items.QUARTZ, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_GLOWSTONE_BUCKET, Items.GLOWSTONE_DUST, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_AMETHYST_BUCKET, Items.AMETHYST_SHARD, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_BROWN_MUSHROOM_BUCKET, Items.BROWN_MUSHROOM, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_RED_MUSHROOM_BUCKET, Items.RED_MUSHROOM, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_CACTUS_BUCKET, Items.CACTUS, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_COAL_BUCKET, Items.COAL, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_GRAVEL_BUCKET, Items.GRAVEL, 1, 2);

        dnaExtractingRecipe(recipeOutput, Items.SLIME_BALL, ModItems.SLIME_DNA, 1, 0.9f);
        dnaExtractingRecipe(recipeOutput, ModItems.DIRT_SLIME_BALL, ModItems.DIRT_SLIME_DNA, 1, 0.75f);
        dnaExtractingRecipe(recipeOutput, ModItems.STONE_SLIME_BALL, ModItems.STONE_SLIME_DNA, 1, 0.70f);
        dnaExtractingRecipe(recipeOutput, ModItems.COPPER_SLIME_BALL, ModItems.COPPER_SLIME_DNA, 1, 0.6f);
        dnaExtractingRecipe(recipeOutput, ModItems.IRON_SLIME_BALL, ModItems.IRON_SLIME_DNA, 1, 0.6f);
        dnaExtractingRecipe(recipeOutput, ModItems.GOLD_SLIME_BALL, ModItems.GOLD_SLIME_DNA, 1, 0.5f);
        dnaExtractingRecipe(recipeOutput, ModItems.DIAMOND_SLIME_BALL, ModItems.DIAMOND_SLIME_DNA, 1, 0.4f);
        dnaExtractingRecipe(recipeOutput, ModItems.NETHERITE_SLIME_BALL, ModItems.NETHERITE_SLIME_DNA, 1, 0.3f);
        dnaExtractingRecipe(recipeOutput, ModItems.LAPIS_SLIME_BALL, ModItems.LAPIS_SLIME_DNA, 1, 0.6f);
        dnaExtractingRecipe(recipeOutput, ModItems.REDSTONE_SLIME_BALL, ModItems.REDSTONE_SLIME_DNA, 1, 0.6f);
        dnaExtractingRecipe(recipeOutput, ModItems.OAK_SLIME_BALL, ModItems.OAK_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.SAND_SLIME_BALL, ModItems.SAND_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.ANDESITE_SLIME_BALL, ModItems.ANDESITE_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.SNOW_SLIME_BALL, ModItems.SNOW_SLIME_DNA, 1, 0.65f);
        dnaExtractingRecipe(recipeOutput, ModItems.ICE_SLIME_BALL, ModItems.ICE_SLIME_DNA, 1, 0.6f);
        dnaExtractingRecipe(recipeOutput, ModItems.MUD_SLIME_BALL, ModItems.MUD_SLIME_DNA, 1, 0.8f);
        dnaExtractingRecipe(recipeOutput, ModItems.CLAY_SLIME_BALL, ModItems.CLAY_SLIME_DNA, 1, 0.75f);
        dnaExtractingRecipe(recipeOutput, ModItems.RED_SAND_SLIME_BALL, ModItems.RED_SAND_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.MOSS_SLIME_BALL, ModItems.MOSS_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.DEEPSLATE_SLIME_BALL, ModItems.DEEPSLATE_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.GRANITE_SLIME_BALL, ModItems.GRANITE_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.DIORITE_SLIME_BALL, ModItems.DIORITE_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.CALCITE_SLIME_BALL, ModItems.CALCITE_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.TUFF_SLIME_BALL, ModItems.TUFF_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.DRIPSTONE_SLIME_BALL, ModItems.DRIPSTONE_SLIME_DNA, 1, 0.6f);
        dnaExtractingRecipe(recipeOutput, ModItems.PRISMARINE_SLIME_BALL, ModItems.PRISMARINE_SLIME_DNA, 1, 0.5f);
        dnaExtractingRecipe(recipeOutput, ModItems.MAGMA_SLIME_BALL, ModItems.MAGMA_SLIME_DNA, 1, 0.5f);
        dnaExtractingRecipe(recipeOutput, ModItems.OBSIDIAN_SLIME_BALL, ModItems.OBSIDIAN_SLIME_DNA, 1, 0.45f);
        dnaExtractingRecipe(recipeOutput, ModItems.NETHERRACK_SLIME_BALL, ModItems.NETHERRACK_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.SOUL_SAND_SLIME_BALL, ModItems.SOUL_SAND_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.SOUL_SOIL_SLIME_BALL, ModItems.SOUL_SOIL_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.BLACKSTONE_SLIME_BALL, ModItems.BLACKSTONE_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.BASALT_SLIME_BALL, ModItems.BASALT_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.ENDSTONE_SLIME_BALL, ModItems.ENDSTONE_SLIME_DNA, 1, 0.6f);
        dnaExtractingRecipe(recipeOutput, ModItems.QUARTZ_SLIME_BALL, ModItems.QUARTZ_SLIME_DNA, 1, 0.55f);
        dnaExtractingRecipe(recipeOutput, ModItems.GLOWSTONE_SLIME_BALL, ModItems.GLOWSTONE_SLIME_DNA, 1, 0.5f);
        dnaExtractingRecipe(recipeOutput, ModItems.AMETHYST_SLIME_BALL, ModItems.AMETHYST_SLIME_DNA, 1, 0.4f);
        dnaExtractingRecipe(recipeOutput, ModItems.BROWN_MUSHROOM_SLIME_BALL, ModItems.BROWN_MUSHROOM_SLIME_DNA, 1, 0.3f);
        dnaExtractingRecipe(recipeOutput, ModItems.RED_MUSHROOM_SLIME_BALL, ModItems.RED_MUSHROOM_SLIME_DNA, 1, 0.3f);
        dnaExtractingRecipe(recipeOutput, ModItems.CACTUS_SLIME_BALL, ModItems.CACTUS_SLIME_DNA, 1, 0.6f);
        dnaExtractingRecipe(recipeOutput, ModItems.COAL_SLIME_BALL, ModItems.COAL_SLIME_DNA, 1, 0.65f);
        dnaExtractingRecipe(recipeOutput, ModItems.GRAVEL_SLIME_BALL, ModItems.GRAVEL_SLIME_DNA, 1, 0.6f);

        //DNA Synthesizing Recipe For Getting Self
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.DIRT_SLIME_SPAWN_EGG, 2, ModItems.DIRT_SLIME_DNA, ModItems.DIRT_SLIME_DNA, Items.DIRT);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.STONE_SLIME_SPAWN_EGG, 2, ModItems.STONE_SLIME_DNA, ModItems.STONE_SLIME_DNA, Items.STONE);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.COPPER_SLIME_SPAWN_EGG, 2, ModItems.COPPER_SLIME_DNA, ModItems.COPPER_SLIME_DNA, Items.COPPER_BLOCK);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.IRON_SLIME_SPAWN_EGG, 2, ModItems.IRON_SLIME_DNA, ModItems.IRON_SLIME_DNA, Items.IRON_BLOCK);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.GOLD_SLIME_SPAWN_EGG, 2, ModItems.GOLD_SLIME_DNA, ModItems.GOLD_SLIME_DNA, Items.GOLD_BLOCK);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.DIAMOND_SLIME_SPAWN_EGG, 2, ModItems.DIAMOND_SLIME_DNA, ModItems.DIAMOND_SLIME_DNA, Items.DIAMOND_BLOCK);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.NETHERITE_SLIME_SPAWN_EGG, 2, ModItems.NETHERITE_SLIME_DNA, ModItems.NETHERITE_SLIME_DNA, Items.NETHERITE_BLOCK);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.LAPIS_SLIME_SPAWN_EGG, 2, ModItems.LAPIS_SLIME_DNA, ModItems.LAPIS_SLIME_DNA, Items.LAPIS_BLOCK);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.REDSTONE_SLIME_SPAWN_EGG, 2, ModItems.REDSTONE_SLIME_DNA, ModItems.REDSTONE_SLIME_DNA, Items.REDSTONE_BLOCK);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.OAK_SLIME_SPAWN_EGG, 2, ModItems.OAK_SLIME_DNA, ModItems.OAK_SLIME_DNA, Items.OAK_PLANKS);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.SAND_SLIME_SPAWN_EGG, 2, ModItems.SAND_SLIME_DNA, ModItems.SAND_SLIME_DNA, Items.SAND);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.ANDESITE_SLIME_SPAWN_EGG, 2, ModItems.ANDESITE_SLIME_DNA, ModItems.ANDESITE_SLIME_DNA, Items.ANDESITE);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.SNOW_SLIME_SPAWN_EGG, 2, ModItems.SNOW_SLIME_DNA, ModItems.SNOW_SLIME_DNA, Items.SNOW);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.ICE_SLIME_SPAWN_EGG, 2, ModItems.ICE_SLIME_DNA, ModItems.ICE_SLIME_DNA, Items.ICE);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.MUD_SLIME_SPAWN_EGG, 2, ModItems.MUD_SLIME_DNA, ModItems.MUD_SLIME_DNA, Items.MUD);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.CLAY_SLIME_SPAWN_EGG, 2, ModItems.CLAY_SLIME_DNA, ModItems.CLAY_SLIME_DNA, Items.CLAY);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.RED_SAND_SLIME_SPAWN_EGG, 2, ModItems.RED_SAND_SLIME_DNA, ModItems.RED_SAND_SLIME_DNA, Items.RED_SAND);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.MOSS_SLIME_SPAWN_EGG, 2, ModItems.MOSS_SLIME_DNA, ModItems.MOSS_SLIME_DNA, Items.MOSS_BLOCK);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.DEEPSLATE_SLIME_SPAWN_EGG, 2, ModItems.DEEPSLATE_SLIME_DNA, ModItems.DEEPSLATE_SLIME_DNA, Items.DEEPSLATE);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.GRANITE_SLIME_SPAWN_EGG, 2, ModItems.GRANITE_SLIME_DNA, ModItems.GRANITE_SLIME_DNA, Items.GRANITE);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.DIORITE_SLIME_SPAWN_EGG, 2, ModItems.DIORITE_SLIME_DNA, ModItems.DIORITE_SLIME_DNA, Items.DIORITE);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.CALCITE_SLIME_SPAWN_EGG, 2, ModItems.CALCITE_SLIME_DNA, ModItems.CALCITE_SLIME_DNA, Items.CALCITE);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.TUFF_SLIME_SPAWN_EGG, 2, ModItems.TUFF_SLIME_DNA, ModItems.TUFF_SLIME_DNA, Items.TUFF);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.DRIPSTONE_SLIME_SPAWN_EGG, 2, ModItems.DRIPSTONE_SLIME_DNA, ModItems.DRIPSTONE_SLIME_DNA, Items.DRIPSTONE_BLOCK);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.PRISMARINE_SLIME_SPAWN_EGG, 2, ModItems.PRISMARINE_SLIME_DNA, ModItems.PRISMARINE_SLIME_DNA, Items.PRISMARINE_SHARD);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.MAGMA_SLIME_SPAWN_EGG, 2, ModItems.MAGMA_SLIME_DNA, ModItems.MAGMA_SLIME_DNA, Items.MAGMA_BLOCK);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.OBSIDIAN_SLIME_SPAWN_EGG, 2, ModItems.OBSIDIAN_SLIME_DNA, ModItems.OBSIDIAN_SLIME_DNA, Items.OBSIDIAN);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.NETHERRACK_SLIME_SPAWN_EGG, 2, ModItems.NETHERRACK_SLIME_DNA, ModItems.NETHERRACK_SLIME_DNA, Items.NETHERRACK);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.SOUL_SAND_SLIME_SPAWN_EGG, 2, ModItems.SOUL_SAND_SLIME_DNA, ModItems.SOUL_SAND_SLIME_DNA, Items.SOUL_SAND);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.SOUL_SOIL_SLIME_SPAWN_EGG, 2, ModItems.SOUL_SOIL_SLIME_DNA, ModItems.SOUL_SOIL_SLIME_DNA, Items.SOUL_SOIL);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.BLACKSTONE_SLIME_SPAWN_EGG, 2, ModItems.BLACKSTONE_SLIME_DNA, ModItems.BLACKSTONE_SLIME_DNA, Items.BLACKSTONE);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.BASALT_SLIME_SPAWN_EGG, 2, ModItems.BASALT_SLIME_DNA, ModItems.BASALT_SLIME_DNA, Items.BASALT);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.ENDSTONE_SLIME_SPAWN_EGG, 2, ModItems.ENDSTONE_SLIME_DNA, ModItems.ENDSTONE_SLIME_DNA, Items.END_STONE);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.QUARTZ_SLIME_SPAWN_EGG, 2, ModItems.QUARTZ_SLIME_DNA, ModItems.QUARTZ_SLIME_DNA, Items.QUARTZ);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.GLOWSTONE_SLIME_SPAWN_EGG, 2, ModItems.GLOWSTONE_SLIME_DNA, ModItems.GLOWSTONE_SLIME_DNA, Items.GLOWSTONE_DUST);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.AMETHYST_SLIME_SPAWN_EGG, 2, ModItems.AMETHYST_SLIME_DNA, ModItems.AMETHYST_SLIME_DNA, Items.AMETHYST_SHARD);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.BROWN_MUSHROOM_SLIME_SPAWN_EGG, 2, ModItems.BROWN_MUSHROOM_SLIME_DNA, ModItems.BROWN_MUSHROOM_SLIME_DNA, Items.BROWN_MUSHROOM);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.RED_MUSHROOM_SLIME_SPAWN_EGG, 2, ModItems.RED_MUSHROOM_SLIME_DNA, ModItems.RED_MUSHROOM_SLIME_DNA, Items.RED_MUSHROOM);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.CACTUS_SLIME_SPAWN_EGG, 2, ModItems.CACTUS_SLIME_DNA, ModItems.CACTUS_SLIME_DNA, Items.CACTUS);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.COAL_SLIME_SPAWN_EGG, 2, ModItems.COAL_SLIME_DNA, ModItems.COAL_SLIME_DNA, Items.COAL_BLOCK);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.GRAVEL_SLIME_SPAWN_EGG, 2, ModItems.GRAVEL_SLIME_DNA, ModItems.GRAVEL_SLIME_DNA, Items.GRAVEL);

        //DNA Synthesizing Recipe For Getting New Egg
        dnaSynthesizingRecipe(recipeOutput, ModItems.DIRT_SLIME_SPAWN_EGG, 4,ModItems.SLIME_DNA, ModItems.SLIME_DNA, Items.DIRT);
        dnaSynthesizingRecipe(recipeOutput, ModItems.STONE_SLIME_SPAWN_EGG, 4,ModItems.DIRT_SLIME_DNA, ModItems.DIRT_SLIME_DNA, Items.STONE);
        dnaSynthesizingRecipe(recipeOutput, ModItems.COAL_SLIME_SPAWN_EGG, 4,ModItems.STONE_SLIME_DNA, ModItems.STONE_SLIME_DNA, Items.COAL_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.COPPER_SLIME_SPAWN_EGG, 4,ModItems.COAL_SLIME_DNA, ModItems.COAL_SLIME_DNA, Items.COPPER_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.IRON_SLIME_SPAWN_EGG, 4,ModItems.COPPER_SLIME_DNA, ModItems.COPPER_SLIME_DNA, Items.IRON_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.GOLD_SLIME_SPAWN_EGG, 4,ModItems.IRON_SLIME_DNA, ModItems.IRON_SLIME_DNA, Items.GOLD_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.DIAMOND_SLIME_SPAWN_EGG, 4,ModItems.GOLD_SLIME_DNA, ModItems.GOLD_SLIME_DNA, Items.DIAMOND_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.NETHERITE_SLIME_SPAWN_EGG, 4,ModItems.DIAMOND_SLIME_DNA, ModItems.DIAMOND_SLIME_DNA, Items.NETHERITE_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.LAPIS_SLIME_SPAWN_EGG, 4,ModItems.IRON_SLIME_DNA, ModItems.IRON_SLIME_DNA, Items.LAPIS_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.REDSTONE_SLIME_SPAWN_EGG, 4,ModItems.GOLD_SLIME_DNA, ModItems.GOLD_SLIME_DNA, Items.REDSTONE_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.OAK_SLIME_SPAWN_EGG, 4,ModItems.DIRT_SLIME_DNA, ModItems.DIRT_SLIME_DNA, Items.OAK_PLANKS);
        dnaSynthesizingRecipe(recipeOutput, ModItems.SAND_SLIME_SPAWN_EGG, 4,ModItems.DIRT_SLIME_DNA, ModItems.DIRT_SLIME_DNA, Items.SAND);
        dnaSynthesizingRecipe(recipeOutput, ModItems.GRAVEL_SLIME_SPAWN_EGG, 4,ModItems.SAND_SLIME_DNA, ModItems.SAND_SLIME_DNA, Items.GRAVEL);
        dnaSynthesizingRecipe(recipeOutput, ModItems.ANDESITE_SLIME_SPAWN_EGG, 4,ModItems.STONE_SLIME_DNA, ModItems.STONE_SLIME_DNA, Items.ANDESITE);
        dnaSynthesizingRecipe(recipeOutput, ModItems.SNOW_SLIME_SPAWN_EGG, 4,ModItems.SLIME_DNA, ModItems.SLIME_DNA, Items.SNOW);
        dnaSynthesizingRecipe(recipeOutput, ModItems.ICE_SLIME_SPAWN_EGG, 4,ModItems.SNOW_SLIME_DNA, ModItems.SNOW_SLIME_DNA, Items.ICE);
        dnaSynthesizingRecipe(recipeOutput, ModItems.MUD_SLIME_SPAWN_EGG, 4,ModItems.DIRT_SLIME_DNA, ModItems.DIRT_SLIME_DNA, Items.MUD);
        dnaSynthesizingRecipe(recipeOutput, ModItems.CLAY_SLIME_SPAWN_EGG, 4,ModItems.MUD_SLIME_DNA, ModItems.MUD_SLIME_DNA, Items.CLAY);
        dnaSynthesizingRecipe(recipeOutput, ModItems.RED_SAND_SLIME_SPAWN_EGG, 4,ModItems.SAND_SLIME_DNA, ModItems.SAND_SLIME_DNA, Items.RED_SAND);
        dnaSynthesizingRecipe(recipeOutput, ModItems.MOSS_SLIME_SPAWN_EGG, 4,ModItems.DIRT_SLIME_DNA, ModItems.DIRT_SLIME_DNA, Items.MOSS_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.DEEPSLATE_SLIME_SPAWN_EGG, 4,ModItems.STONE_SLIME_DNA, ModItems.STONE_SLIME_DNA, Items.DEEPSLATE);
        dnaSynthesizingRecipe(recipeOutput, ModItems.GRANITE_SLIME_SPAWN_EGG, 4,ModItems.STONE_SLIME_DNA, ModItems.STONE_SLIME_DNA, Items.GRANITE);
        dnaSynthesizingRecipe(recipeOutput, ModItems.DIORITE_SLIME_SPAWN_EGG, 4,ModItems.STONE_SLIME_DNA, ModItems.STONE_SLIME_DNA, Items.DIORITE);
        dnaSynthesizingRecipe(recipeOutput, ModItems.CALCITE_SLIME_SPAWN_EGG, 4,ModItems.STONE_SLIME_DNA, ModItems.STONE_SLIME_DNA, Items.CALCITE);
        dnaSynthesizingRecipe(recipeOutput, ModItems.TUFF_SLIME_SPAWN_EGG, 4,ModItems.STONE_SLIME_DNA, ModItems.STONE_SLIME_DNA, Items.TUFF);
        dnaSynthesizingRecipe(recipeOutput, ModItems.DRIPSTONE_SLIME_SPAWN_EGG, 4,ModItems.STONE_SLIME_DNA, ModItems.STONE_SLIME_DNA, Items.DRIPSTONE_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.PRISMARINE_SLIME_SPAWN_EGG, 4,ModItems.SAND_SLIME_DNA, ModItems.SAND_SLIME_DNA, Items.PRISMARINE_SHARD);
        dnaSynthesizingRecipe(recipeOutput, ModItems.MAGMA_SLIME_SPAWN_EGG, 4,ModItems.NETHERITE_SLIME_DNA, ModItems.NETHERITE_SLIME_DNA, Items.MAGMA_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.OBSIDIAN_SLIME_SPAWN_EGG, 4,ModItems.DEEPSLATE_SLIME_DNA, ModItems.DEEPSLATE_SLIME_DNA, Items.OBSIDIAN);
        dnaSynthesizingRecipe(recipeOutput, ModItems.NETHERRACK_SLIME_SPAWN_EGG, 4,ModItems.STONE_SLIME_DNA, ModItems.STONE_SLIME_DNA, Items.NETHERRACK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.SOUL_SAND_SLIME_SPAWN_EGG, 4,ModItems.SAND_SLIME_DNA, ModItems.NETHERRACK_SLIME_DNA, Items.SOUL_SAND);
        dnaSynthesizingRecipe(recipeOutput, ModItems.SOUL_SOIL_SLIME_SPAWN_EGG, 4,ModItems.DIRT_SLIME_DNA, ModItems.NETHERRACK_SLIME_DNA, Items.SOUL_SOIL);
        dnaSynthesizingRecipe(recipeOutput, ModItems.BLACKSTONE_SLIME_SPAWN_EGG, 4,ModItems.STONE_SLIME_DNA, ModItems.NETHERRACK_SLIME_DNA, Items.BLACKSTONE);
        dnaSynthesizingRecipe(recipeOutput, ModItems.BASALT_SLIME_SPAWN_EGG, 4,ModItems.STONE_SLIME_DNA, ModItems.NETHERRACK_SLIME_DNA, Items.BASALT);
        dnaSynthesizingRecipe(recipeOutput, ModItems.ENDSTONE_SLIME_SPAWN_EGG, 4,ModItems.DEEPSLATE_SLIME_DNA, ModItems.NETHERRACK_SLIME_DNA, Items.END_STONE);
        dnaSynthesizingRecipe(recipeOutput, ModItems.QUARTZ_SLIME_SPAWN_EGG, 4,ModItems.IRON_SLIME_DNA, ModItems.NETHERRACK_SLIME_DNA, Items.QUARTZ_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.GLOWSTONE_SLIME_SPAWN_EGG, 4,ModItems.GOLD_SLIME_DNA, ModItems.NETHERRACK_SLIME_DNA, Items.GLOWSTONE);
        dnaSynthesizingRecipe(recipeOutput, ModItems.AMETHYST_SLIME_SPAWN_EGG, 4,ModItems.CALCITE_SLIME_DNA, ModItems.GLOWSTONE_SLIME_DNA, Items.AMETHYST_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.BROWN_MUSHROOM_SLIME_SPAWN_EGG, 4,ModItems.MUD_SLIME_DNA, ModItems.CACTUS_SLIME_DNA, Items.BROWN_MUSHROOM_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.RED_MUSHROOM_SLIME_SPAWN_EGG, 4,ModItems.MUD_SLIME_DNA, ModItems.CACTUS_SLIME_DNA, Items.RED_MUSHROOM_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.CACTUS_SLIME_SPAWN_EGG, 4,ModItems.SAND_SLIME_DNA, ModItems.SLIME_DNA, Items.CACTUS);
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
