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
