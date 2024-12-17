package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.datagen.builder.DnaExtractingRecipeBuilder;
import com.coolerpromc.productiveslimes.datagen.builder.DnaSynthesizingRecipeBuilder;
import com.coolerpromc.productiveslimes.datagen.builder.MeltingRecipeBuilder;
import com.coolerpromc.productiveslimes.datagen.builder.SolidingRecipeBuilder;
import com.coolerpromc.productiveslimes.fluid.ModFluids;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput p_248933_, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(p_248933_);
    }
    
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> recipeOutput) {
        //Override vanilla recipes
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.STICKY_PISTON, 1)
                .requires(Tags.Items.SLIMEBALLS)
                .requires(Items.PISTON)
                .unlockedBy(getHasName(Items.SLIME_BALL), has(Items.PISTON))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.MAGMA_CREAM, 1)
                .requires(Tags.Items.SLIMEBALLS)
                .requires(Items.BLAZE_POWDER)
                .unlockedBy(getHasName(Items.SLIME_BALL), has(Items.BLAZE_POWDER))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.LEAD,2)
                .pattern("AA ")
                .pattern("AB ")
                .pattern("  A")
                .define('A', Items.STRING)
                .define('B', Tags.Items.SLIMEBALLS)
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
                .define('A', ModItems.ENERGY_SLIME_BALL.get())
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
                .define('B', ModItems.ENERGY_SLIME_BALL.get())
                .define('C', Items.GLASS)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.GLASS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DNA_SYNTHESIZER.get(),1)
                .pattern("AAA")
                .pattern("CCC")
                .pattern("ABA")
                .define('A', Items.IRON_INGOT)
                .define('B', ModItems.ENERGY_SLIME_BALL.get())
                .define('C', Items.GLASS)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.GLASS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ENERGY_MULTIPLIER_UPGRADE.get(),1)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .define('A', Items.IRON_INGOT)
                .define('B', ModItems.ENERGY_SLIME_BALL.get())
                .define('C', Items.BLUE_WOOL)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.REDSTONE))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.FLUID_TANK.get(),1)
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
                .requires(Tags.Items.SLIMEBALLS)
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
                .define('A', ModItems.SLIMEBALL_FRAGMENT.get())
                .unlockedBy(getHasName(ModItems.SLIMEBALL_FRAGMENT.get()), has(ModItems.SLIMEBALL_FRAGMENT.get()))
                .save(recipeOutput, "slimeball_from_fragment");

        planksFromLogs(recipeOutput, ModBlocks.SLIMY_PLANKS.get(), ModTags.Items.SLIMY_LOG, 4);

        woodFromLogs(recipeOutput, ModBlocks.SLIMY_WOOD.get(), ModBlocks.SLIMY_LOG.get());

        woodFromLogs(recipeOutput, ModBlocks.STRIPPED_SLIMY_WOOD.get(), ModBlocks.STRIPPED_SLIMY_LOG.get());

        stairBuilder(ModBlocks.SLIMY_STAIRS.get(), Ingredient.of(ModBlocks.SLIMY_PLANKS.get())).group("slimy")
                .unlockedBy("has_slimy", has(ModBlocks.SLIMY_PLANKS.get())).save(recipeOutput);

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

        stairBuilder(ModBlocks.SLIMY_STONE_STAIRS.get(), Ingredient.of(ModBlocks.SLIMY_STONE.get())).group("slimy_stone")
                .unlockedBy("has_slimy_stone", has(ModBlocks.SLIMY_STONE.get())).save(recipeOutput);

        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLIMY_STONE_SLAB.get(), ModBlocks.SLIMY_STONE.get());

        buttonBuilder(ModBlocks.SLIMY_STONE_BUTTON.get(), Ingredient.of(ModBlocks.SLIMY_STONE.get())).group("slimy_stone")
                .unlockedBy("has_slimy_stone", has(ModBlocks.SLIMY_STONE.get())).save(recipeOutput);

        pressurePlate(recipeOutput, ModBlocks.SLIMY_STONE_PRESSURE_PLATE.get(), ModBlocks.SLIMY_STONE.get());

        stairBuilder(ModBlocks.SLIMY_COBBLESTONE_STAIRS.get(), Ingredient.of(ModBlocks.SLIMY_COBBLESTONE.get())).group("slimy_cobblestone")
                .unlockedBy("has_slimy_cobblestone", has(ModBlocks.SLIMY_COBBLESTONE.get())).save(recipeOutput);

        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLIMY_COBBLESTONE_SLAB.get(), ModBlocks.SLIMY_COBBLESTONE.get());

        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLIMY_COBBLESTONE_WALL.get(), ModBlocks.SLIMY_COBBLESTONE.get());

        stairBuilder(ModBlocks.SLIMY_COBBLED_DEEPSLATE_STAIRS.get(), Ingredient.of(ModBlocks.SLIMY_COBBLED_DEEPSLATE.get())).group("slimy_cobbled_deepslate")
                .unlockedBy("has_slimy_cobbled_deepslate", has(ModBlocks.SLIMY_COBBLED_DEEPSLATE.get())).save(recipeOutput);

        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLIMY_COBBLED_DEEPSLATE_SLAB.get(), ModBlocks.SLIMY_COBBLED_DEEPSLATE.get());

        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLIMY_COBBLED_DEEPSLATE_WALL.get(), ModBlocks.SLIMY_COBBLED_DEEPSLATE.get());

        //Slime Ball Recipe
        slimeBlockToSlimeBall(recipeOutput, ModBlocks.DIRT_SLIME_BLOCK.get(), ModItems.DIRT_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.DIRT_SLIME_BALL.get(), ModBlocks.DIRT_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.STONE_SLIME_BLOCK.get(), ModItems.STONE_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.STONE_SLIME_BALL.get(), ModBlocks.STONE_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.COPPER_SLIME_BLOCK.get(), ModItems.COPPER_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.COPPER_SLIME_BALL.get(), ModBlocks.COPPER_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.IRON_SLIME_BLOCK.get(), ModItems.IRON_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.IRON_SLIME_BALL.get(), ModBlocks.IRON_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.GOLD_SLIME_BLOCK.get(), ModItems.GOLD_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.GOLD_SLIME_BALL.get(), ModBlocks.GOLD_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.DIAMOND_SLIME_BLOCK.get(), ModItems.DIAMOND_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.DIAMOND_SLIME_BALL.get(), ModBlocks.DIAMOND_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.NETHERITE_SLIME_BLOCK.get(), ModItems.NETHERITE_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.NETHERITE_SLIME_BALL.get(), ModBlocks.NETHERITE_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.LAPIS_SLIME_BLOCK.get(), ModItems.LAPIS_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.LAPIS_SLIME_BALL.get(), ModBlocks.LAPIS_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.REDSTONE_SLIME_BLOCK.get(), ModItems.REDSTONE_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.REDSTONE_SLIME_BALL.get(), ModBlocks.REDSTONE_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.OAK_SLIME_BLOCK.get(), ModItems.OAK_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.OAK_SLIME_BALL.get(), ModBlocks.OAK_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.SAND_SLIME_BLOCK.get(), ModItems.SAND_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.SAND_SLIME_BALL.get(), ModBlocks.SAND_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.ANDESITE_SLIME_BLOCK.get(), ModItems.ANDESITE_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.ANDESITE_SLIME_BALL.get(), ModBlocks.ANDESITE_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.SNOW_SLIME_BLOCK.get(), ModItems.SNOW_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.SNOW_SLIME_BALL.get(), ModBlocks.SNOW_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.ICE_SLIME_BLOCK.get(), ModItems.ICE_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.ICE_SLIME_BALL.get(), ModBlocks.ICE_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.MUD_SLIME_BLOCK.get(), ModItems.MUD_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.MUD_SLIME_BALL.get(), ModBlocks.MUD_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.CLAY_SLIME_BLOCK.get(), ModItems.CLAY_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.CLAY_SLIME_BALL.get(), ModBlocks.CLAY_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.RED_SAND_SLIME_BLOCK.get(), ModItems.RED_SAND_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.RED_SAND_SLIME_BALL.get(), ModBlocks.RED_SAND_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.MOSS_SLIME_BLOCK.get(), ModItems.MOSS_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.MOSS_SLIME_BALL.get(), ModBlocks.MOSS_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.DEEPSLATE_SLIME_BLOCK.get(), ModItems.DEEPSLATE_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.DEEPSLATE_SLIME_BALL.get(), ModBlocks.DEEPSLATE_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.GRANITE_SLIME_BLOCK.get(), ModItems.GRANITE_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.GRANITE_SLIME_BALL.get(), ModBlocks.GRANITE_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.DIORITE_SLIME_BLOCK.get(), ModItems.DIORITE_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.DIORITE_SLIME_BALL.get(), ModBlocks.DIORITE_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.CALCITE_SLIME_BLOCK.get(), ModItems.CALCITE_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.CALCITE_SLIME_BALL.get(), ModBlocks.CALCITE_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.TUFF_SLIME_BLOCK.get(), ModItems.TUFF_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.TUFF_SLIME_BALL.get(), ModBlocks.TUFF_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.DRIPSTONE_SLIME_BLOCK.get(), ModItems.DRIPSTONE_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.DRIPSTONE_SLIME_BALL.get(), ModBlocks.DRIPSTONE_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.PRISMARINE_SLIME_BLOCK.get(), ModItems.PRISMARINE_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.PRISMARINE_SLIME_BALL.get(), ModBlocks.PRISMARINE_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.MAGMA_SLIME_BLOCK.get(), ModItems.MAGMA_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.MAGMA_SLIME_BALL.get(), ModBlocks.MAGMA_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.OBSIDIAN_SLIME_BLOCK.get(), ModItems.OBSIDIAN_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.OBSIDIAN_SLIME_BALL.get(), ModBlocks.OBSIDIAN_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.NETHERRACK_SLIME_BLOCK.get(), ModItems.NETHERRACK_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.NETHERRACK_SLIME_BALL.get(), ModBlocks.NETHERRACK_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.SOUL_SAND_SLIME_BLOCK.get(), ModItems.SOUL_SAND_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.SOUL_SAND_SLIME_BALL.get(), ModBlocks.SOUL_SAND_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.SOUL_SOIL_SLIME_BLOCK.get(), ModItems.SOUL_SOIL_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.SOUL_SOIL_SLIME_BALL.get(), ModBlocks.SOUL_SOIL_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.BLACKSTONE_SLIME_BLOCK.get(), ModItems.BLACKSTONE_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.BLACKSTONE_SLIME_BALL.get(), ModBlocks.BLACKSTONE_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.BASALT_SLIME_BLOCK.get(), ModItems.BASALT_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.BASALT_SLIME_BALL.get(), ModBlocks.BASALT_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.ENDSTONE_SLIME_BLOCK.get(), ModItems.ENDSTONE_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.ENDSTONE_SLIME_BALL.get(), ModBlocks.ENDSTONE_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.QUARTZ_SLIME_BLOCK.get(), ModItems.QUARTZ_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.QUARTZ_SLIME_BALL.get(), ModBlocks.QUARTZ_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.GLOWSTONE_SLIME_BLOCK.get(), ModItems.GLOWSTONE_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.GLOWSTONE_SLIME_BALL.get(), ModBlocks.GLOWSTONE_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.AMETHYST_SLIME_BLOCK.get(), ModItems.AMETHYST_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.AMETHYST_SLIME_BALL.get(), ModBlocks.AMETHYST_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.BROWN_MUSHROOM_SLIME_BLOCK.get(), ModItems.BROWN_MUSHROOM_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.BROWN_MUSHROOM_SLIME_BALL.get(), ModBlocks.BROWN_MUSHROOM_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.RED_MUSHROOM_SLIME_BLOCK.get(), ModItems.RED_MUSHROOM_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.RED_MUSHROOM_SLIME_BALL.get(), ModBlocks.RED_MUSHROOM_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.CACTUS_SLIME_BLOCK.get(), ModItems.CACTUS_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.CACTUS_SLIME_BALL.get(), ModBlocks.CACTUS_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.COAL_SLIME_BLOCK.get(), ModItems.COAL_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.COAL_SLIME_BALL.get(), ModBlocks.COAL_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.GRAVEL_SLIME_BLOCK.get(), ModItems.GRAVEL_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.GRAVEL_SLIME_BALL.get(), ModBlocks.GRAVEL_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.ENERGY_SLIME_BLOCK.get(), ModItems.ENERGY_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.ENERGY_SLIME_BALL.get(), ModBlocks.ENERGY_SLIME_BLOCK.get());

        slimeBlockToSlimeBall(recipeOutput, ModBlocks.OAK_LEAVES_SLIME_BLOCK.get(), ModItems.OAK_LEAVES_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.OAK_LEAVES_SLIME_BALL.get(), ModBlocks.OAK_LEAVES_SLIME_BLOCK.get());

        //Melting Recipe
        meltingRecipe(recipeOutput, ModBlocks.DIRT_SLIME_BLOCK.get(), ModFluids.MOLTEN_DIRT_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.DIRT_SLIME_BALL.get(), ModFluids.MOLTEN_DIRT_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.STONE_SLIME_BLOCK.get(), ModFluids.MOLTEN_STONE_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.STONE_SLIME_BALL.get(), ModFluids.MOLTEN_STONE_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.COPPER_SLIME_BLOCK.get(), ModFluids.MOLTEN_COPPER_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.COPPER_SLIME_BALL.get(), ModFluids.MOLTEN_COPPER_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.IRON_SLIME_BLOCK.get(), ModFluids.MOLTEN_IRON_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.IRON_SLIME_BALL.get(), ModFluids.MOLTEN_IRON_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.GOLD_SLIME_BLOCK.get(), ModFluids.MOLTEN_GOLD_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.GOLD_SLIME_BALL.get(), ModFluids.MOLTEN_GOLD_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.DIAMOND_SLIME_BLOCK.get(), ModFluids.MOLTEN_DIAMOND_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.DIAMOND_SLIME_BALL.get(), ModFluids.MOLTEN_DIAMOND_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.NETHERITE_SLIME_BLOCK.get(), ModFluids.MOLTEN_NETHERITE_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.NETHERITE_SLIME_BALL.get(), ModFluids.MOLTEN_NETHERITE_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.LAPIS_SLIME_BLOCK.get(), ModFluids.MOLTEN_LAPIS_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.LAPIS_SLIME_BALL.get(), ModFluids.MOLTEN_LAPIS_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.REDSTONE_SLIME_BLOCK.get(), ModFluids.MOLTEN_REDSTONE_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.REDSTONE_SLIME_BALL.get(), ModFluids.MOLTEN_REDSTONE_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.OAK_SLIME_BLOCK.get(), ModFluids.MOLTEN_OAK_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.OAK_SLIME_BALL.get(), ModFluids.MOLTEN_OAK_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.SAND_SLIME_BLOCK.get(), ModFluids.MOLTEN_SAND_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.SAND_SLIME_BALL.get(), ModFluids.MOLTEN_SAND_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.ANDESITE_SLIME_BLOCK.get(), ModFluids.MOLTEN_ANDESITE_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.ANDESITE_SLIME_BALL.get(), ModFluids.MOLTEN_ANDESITE_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.SNOW_SLIME_BLOCK.get(), ModFluids.MOLTEN_SNOW_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.SNOW_SLIME_BALL.get(), ModFluids.MOLTEN_SNOW_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.ICE_SLIME_BLOCK.get(), ModFluids.MOLTEN_ICE_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.ICE_SLIME_BALL.get(), ModFluids.MOLTEN_ICE_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.MUD_SLIME_BLOCK.get(), ModFluids.MOLTEN_MUD_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.MUD_SLIME_BALL.get(), ModFluids.MOLTEN_MUD_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.CLAY_SLIME_BLOCK.get(), ModFluids.MOLTEN_CLAY_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.CLAY_SLIME_BALL.get(), ModFluids.MOLTEN_CLAY_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.RED_SAND_SLIME_BLOCK.get(), ModFluids.MOLTEN_RED_SAND_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.RED_SAND_SLIME_BALL.get(), ModFluids.MOLTEN_RED_SAND_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.MOSS_SLIME_BLOCK.get(), ModFluids.MOLTEN_MOSS_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.MOSS_SLIME_BALL.get(), ModFluids.MOLTEN_MOSS_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.DEEPSLATE_SLIME_BLOCK.get(), ModFluids.MOLTEN_DEEPSLATE_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.DEEPSLATE_SLIME_BALL.get(), ModFluids.MOLTEN_DEEPSLATE_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.GRANITE_SLIME_BLOCK.get(), ModFluids.MOLTEN_GRANITE_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.GRANITE_SLIME_BALL.get(), ModFluids.MOLTEN_GRANITE_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.DIORITE_SLIME_BLOCK.get(), ModFluids.MOLTEN_DIORITE_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.DIORITE_SLIME_BALL.get(), ModFluids.MOLTEN_DIORITE_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.CALCITE_SLIME_BLOCK.get(), ModFluids.MOLTEN_CALCITE_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.CALCITE_SLIME_BALL.get(), ModFluids.MOLTEN_CALCITE_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.TUFF_SLIME_BLOCK.get(), ModFluids.MOLTEN_TUFF_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.TUFF_SLIME_BALL.get(), ModFluids.MOLTEN_TUFF_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.DRIPSTONE_SLIME_BLOCK.get(), ModFluids.MOLTEN_DRIPSTONE_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.DRIPSTONE_SLIME_BALL.get(), ModFluids.MOLTEN_DRIPSTONE_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.PRISMARINE_SLIME_BLOCK.get(), ModFluids.MOLTEN_PRISMARINE_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.PRISMARINE_SLIME_BALL.get(), ModFluids.MOLTEN_PRISMARINE_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.MAGMA_SLIME_BLOCK.get(), ModFluids.MOLTEN_MAGMA_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.MAGMA_SLIME_BALL.get(), ModFluids.MOLTEN_MAGMA_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.OBSIDIAN_SLIME_BLOCK.get(), ModFluids.MOLTEN_OBSIDIAN_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.OBSIDIAN_SLIME_BALL.get(), ModFluids.MOLTEN_OBSIDIAN_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.NETHERRACK_SLIME_BLOCK.get(), ModFluids.MOLTEN_NETHERRACK_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.NETHERRACK_SLIME_BALL.get(), ModFluids.MOLTEN_NETHERRACK_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.SOUL_SAND_SLIME_BLOCK.get(), ModFluids.MOLTEN_SOUL_SAND_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.SOUL_SAND_SLIME_BALL.get(), ModFluids.MOLTEN_SOUL_SAND_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.SOUL_SOIL_SLIME_BLOCK.get(), ModFluids.MOLTEN_SOUL_SOIL_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.SOUL_SOIL_SLIME_BALL.get(), ModFluids.MOLTEN_SOUL_SOIL_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.BLACKSTONE_SLIME_BLOCK.get(), ModFluids.MOLTEN_BLACKSTONE_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.BLACKSTONE_SLIME_BALL.get(), ModFluids.MOLTEN_BLACKSTONE_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.BASALT_SLIME_BLOCK.get(), ModFluids.MOLTEN_BASALT_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.BASALT_SLIME_BALL.get(), ModFluids.MOLTEN_BASALT_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.ENDSTONE_SLIME_BLOCK.get(), ModFluids.MOLTEN_ENDSTONE_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.ENDSTONE_SLIME_BALL.get(), ModFluids.MOLTEN_ENDSTONE_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.QUARTZ_SLIME_BLOCK.get(), ModFluids.MOLTEN_QUARTZ_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.QUARTZ_SLIME_BALL.get(), ModFluids.MOLTEN_QUARTZ_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.GLOWSTONE_SLIME_BLOCK.get(), ModFluids.MOLTEN_GLOWSTONE_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.GLOWSTONE_SLIME_BALL.get(), ModFluids.MOLTEN_GLOWSTONE_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.AMETHYST_SLIME_BLOCK.get(), ModFluids.MOLTEN_AMETHYST_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.AMETHYST_SLIME_BALL.get(), ModFluids.MOLTEN_AMETHYST_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.BROWN_MUSHROOM_SLIME_BLOCK.get(), ModFluids.MOLTEN_BROWN_MUSHROOM_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.BROWN_MUSHROOM_SLIME_BALL.get(), ModFluids.MOLTEN_BROWN_MUSHROOM_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.RED_MUSHROOM_SLIME_BLOCK.get(), ModFluids.MOLTEN_RED_MUSHROOM_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.RED_MUSHROOM_SLIME_BALL.get(), ModFluids.MOLTEN_RED_MUSHROOM_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.CACTUS_SLIME_BLOCK.get(), ModFluids.MOLTEN_CACTUS_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.CACTUS_SLIME_BALL.get(), ModFluids.MOLTEN_CACTUS_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.COAL_SLIME_BLOCK.get(), ModFluids.MOLTEN_COAL_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.COAL_SLIME_BALL.get(), ModFluids.MOLTEN_COAL_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.GRAVEL_SLIME_BLOCK.get(), ModFluids.MOLTEN_GRAVEL_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.GRAVEL_SLIME_BALL.get(), ModFluids.MOLTEN_GRAVEL_BUCKET.get(), 4, 1);

        meltingRecipe(recipeOutput, ModBlocks.OAK_LEAVES_SLIME_BLOCK.get(), ModFluids.MOLTEN_OAK_LEAVES_BUCKET.get(), 2, 5);
        meltingRecipe(recipeOutput, ModItems.OAK_LEAVES_SLIME_BALL.get(), ModFluids.MOLTEN_OAK_LEAVES_BUCKET.get(), 4, 1);

        //Soliding Recipe
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_DIRT_BUCKET.get(), Items.DIRT, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_STONE_BUCKET.get(), Items.STONE, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_COPPER_BUCKET.get(), Items.COPPER_INGOT, 1, 1);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_IRON_BUCKET.get(), Items.IRON_INGOT, 1, 1);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_GOLD_BUCKET.get(), Items.GOLD_INGOT, 1, 1);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_DIAMOND_BUCKET.get(), Items.DIAMOND, 1, 1);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_NETHERITE_BUCKET.get(), Items.NETHERITE_INGOT, 1, 1);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_LAPIS_BUCKET.get(), Items.LAPIS_LAZULI, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_REDSTONE_BUCKET.get(), Items.REDSTONE, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_OAK_BUCKET.get(), Items.OAK_PLANKS, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_SAND_BUCKET.get(), Items.SAND, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_ANDESITE_BUCKET.get(), Items.ANDESITE, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_SNOW_BUCKET.get(), Items.SNOW, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_ICE_BUCKET.get(), Items.ICE, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_MUD_BUCKET.get(), Items.MUD, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_CLAY_BUCKET.get(), Items.CLAY, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_RED_SAND_BUCKET.get(), Items.RED_SAND, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_MOSS_BUCKET.get(), Items.MOSS_BLOCK, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_DEEPSLATE_BUCKET.get(), Items.DEEPSLATE, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_GRANITE_BUCKET.get(), Items.GRANITE, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_DIORITE_BUCKET.get(), Items.DIORITE, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_CALCITE_BUCKET.get(), Items.CALCITE, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_TUFF_BUCKET.get(), Items.TUFF, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_DRIPSTONE_BUCKET.get(), Items.POINTED_DRIPSTONE, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_PRISMARINE_BUCKET.get(), Items.PRISMARINE_SHARD, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_MAGMA_BUCKET.get(), Items.MAGMA_BLOCK, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_OBSIDIAN_BUCKET.get(), Items.OBSIDIAN, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_NETHERRACK_BUCKET.get(), Items.NETHERRACK, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_SOUL_SAND_BUCKET.get(), Items.SOUL_SAND, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_SOUL_SOIL_BUCKET.get(), Items.SOUL_SOIL, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_BLACKSTONE_BUCKET.get(), Items.BLACKSTONE, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_BASALT_BUCKET.get(), Items.BASALT, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_ENDSTONE_BUCKET.get(), Items.END_STONE, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_QUARTZ_BUCKET.get(), Items.QUARTZ, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_GLOWSTONE_BUCKET.get(), Items.GLOWSTONE_DUST, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_AMETHYST_BUCKET.get(), Items.AMETHYST_SHARD, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_BROWN_MUSHROOM_BUCKET.get(), Items.BROWN_MUSHROOM, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_RED_MUSHROOM_BUCKET.get(), Items.RED_MUSHROOM, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_CACTUS_BUCKET.get(), Items.CACTUS, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_COAL_BUCKET.get(), Items.COAL, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_GRAVEL_BUCKET.get(), Items.GRAVEL, 1, 2);
        solidingRecipe(recipeOutput, ModFluids.MOLTEN_OAK_LEAVES_BUCKET.get(), Items.OAK_LEAVES, 1, 2);

        dnaExtractingRecipe(recipeOutput, Items.SLIME_BALL, ModItems.SLIME_DNA.get(), 1, 0.9f);
        dnaExtractingRecipe(recipeOutput, ModItems.DIRT_SLIME_BALL.get(), ModItems.DIRT_SLIME_DNA.get(), 1, 0.75f);
        dnaExtractingRecipe(recipeOutput, ModItems.STONE_SLIME_BALL.get(), ModItems.STONE_SLIME_DNA.get(), 1, 0.70f);
        dnaExtractingRecipe(recipeOutput, ModItems.COPPER_SLIME_BALL.get(), ModItems.COPPER_SLIME_DNA.get(), 1, 0.6f);
        dnaExtractingRecipe(recipeOutput, ModItems.IRON_SLIME_BALL.get(), ModItems.IRON_SLIME_DNA.get(), 1, 0.6f);
        dnaExtractingRecipe(recipeOutput, ModItems.GOLD_SLIME_BALL.get(), ModItems.GOLD_SLIME_DNA.get(), 1, 0.5f);
        dnaExtractingRecipe(recipeOutput, ModItems.DIAMOND_SLIME_BALL.get(), ModItems.DIAMOND_SLIME_DNA.get(), 1, 0.4f);
        dnaExtractingRecipe(recipeOutput, ModItems.NETHERITE_SLIME_BALL.get(), ModItems.NETHERITE_SLIME_DNA.get(), 1, 0.3f);
        dnaExtractingRecipe(recipeOutput, ModItems.LAPIS_SLIME_BALL.get(), ModItems.LAPIS_SLIME_DNA.get(), 1, 0.6f);
        dnaExtractingRecipe(recipeOutput, ModItems.REDSTONE_SLIME_BALL.get(), ModItems.REDSTONE_SLIME_DNA.get(), 1, 0.6f);
        dnaExtractingRecipe(recipeOutput, ModItems.OAK_SLIME_BALL.get(), ModItems.OAK_SLIME_DNA.get(), 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.SAND_SLIME_BALL.get(), ModItems.SAND_SLIME_DNA.get(), 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.ANDESITE_SLIME_BALL.get(), ModItems.ANDESITE_SLIME_DNA.get(), 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.SNOW_SLIME_BALL.get(), ModItems.SNOW_SLIME_DNA.get(), 1, 0.65f);
        dnaExtractingRecipe(recipeOutput, ModItems.ICE_SLIME_BALL.get(), ModItems.ICE_SLIME_DNA.get(), 1, 0.6f);
        dnaExtractingRecipe(recipeOutput, ModItems.MUD_SLIME_BALL.get(), ModItems.MUD_SLIME_DNA.get(), 1, 0.8f);
        dnaExtractingRecipe(recipeOutput, ModItems.CLAY_SLIME_BALL.get(), ModItems.CLAY_SLIME_DNA.get(), 1, 0.75f);
        dnaExtractingRecipe(recipeOutput, ModItems.RED_SAND_SLIME_BALL.get(), ModItems.RED_SAND_SLIME_DNA.get(), 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.MOSS_SLIME_BALL.get(), ModItems.MOSS_SLIME_DNA.get(), 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.DEEPSLATE_SLIME_BALL.get(), ModItems.DEEPSLATE_SLIME_DNA.get(), 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.GRANITE_SLIME_BALL.get(), ModItems.GRANITE_SLIME_DNA.get(), 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.DIORITE_SLIME_BALL.get(), ModItems.DIORITE_SLIME_DNA.get(), 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.CALCITE_SLIME_BALL.get(), ModItems.CALCITE_SLIME_DNA.get(), 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.TUFF_SLIME_BALL.get(), ModItems.TUFF_SLIME_DNA.get(), 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.DRIPSTONE_SLIME_BALL.get(), ModItems.DRIPSTONE_SLIME_DNA.get(), 1, 0.6f);
        dnaExtractingRecipe(recipeOutput, ModItems.PRISMARINE_SLIME_BALL.get(), ModItems.PRISMARINE_SLIME_DNA.get(), 1, 0.5f);
        dnaExtractingRecipe(recipeOutput, ModItems.MAGMA_SLIME_BALL.get(), ModItems.MAGMA_SLIME_DNA.get(), 1, 0.5f);
        dnaExtractingRecipe(recipeOutput, ModItems.OBSIDIAN_SLIME_BALL.get(), ModItems.OBSIDIAN_SLIME_DNA.get(), 1, 0.45f);
        dnaExtractingRecipe(recipeOutput, ModItems.NETHERRACK_SLIME_BALL.get(), ModItems.NETHERRACK_SLIME_DNA.get(), 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.SOUL_SAND_SLIME_BALL.get(), ModItems.SOUL_SAND_SLIME_DNA.get(), 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.SOUL_SOIL_SLIME_BALL.get(), ModItems.SOUL_SOIL_SLIME_DNA.get(), 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.BLACKSTONE_SLIME_BALL.get(), ModItems.BLACKSTONE_SLIME_DNA.get(), 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.BASALT_SLIME_BALL.get(), ModItems.BASALT_SLIME_DNA.get(), 1, 0.7f);
        dnaExtractingRecipe(recipeOutput, ModItems.ENDSTONE_SLIME_BALL.get(), ModItems.ENDSTONE_SLIME_DNA.get(), 1, 0.6f);
        dnaExtractingRecipe(recipeOutput, ModItems.QUARTZ_SLIME_BALL.get(), ModItems.QUARTZ_SLIME_DNA.get(), 1, 0.55f);
        dnaExtractingRecipe(recipeOutput, ModItems.GLOWSTONE_SLIME_BALL.get(), ModItems.GLOWSTONE_SLIME_DNA.get(), 1, 0.5f);
        dnaExtractingRecipe(recipeOutput, ModItems.AMETHYST_SLIME_BALL.get(), ModItems.AMETHYST_SLIME_DNA.get(), 1, 0.4f);
        dnaExtractingRecipe(recipeOutput, ModItems.BROWN_MUSHROOM_SLIME_BALL.get(), ModItems.BROWN_MUSHROOM_SLIME_DNA.get(), 1, 0.3f);
        dnaExtractingRecipe(recipeOutput, ModItems.RED_MUSHROOM_SLIME_BALL.get(), ModItems.RED_MUSHROOM_SLIME_DNA.get(), 1, 0.3f);
        dnaExtractingRecipe(recipeOutput, ModItems.CACTUS_SLIME_BALL.get(), ModItems.CACTUS_SLIME_DNA.get(), 1, 0.6f);
        dnaExtractingRecipe(recipeOutput, ModItems.COAL_SLIME_BALL.get(), ModItems.COAL_SLIME_DNA.get(), 1, 0.65f);
        dnaExtractingRecipe(recipeOutput, ModItems.GRAVEL_SLIME_BALL.get(), ModItems.GRAVEL_SLIME_DNA.get(), 1, 0.6f);
        dnaExtractingRecipe(recipeOutput, ModItems.OAK_LEAVES_SLIME_BALL.get(), ModItems.OAK_LEAVES_SLIME_DNA.get(), 1, 0.7f);

        //DNA Synthesizing Recipe For Getting Self
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.DIRT_SLIME_SPAWN_EGG.get(), 2, ModItems.DIRT_SLIME_DNA.get(), ModItems.DIRT_SLIME_DNA.get(), Items.DIRT);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.STONE_SLIME_SPAWN_EGG.get(), 2, ModItems.STONE_SLIME_DNA.get(), ModItems.STONE_SLIME_DNA.get(), Items.STONE);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.COPPER_SLIME_SPAWN_EGG.get(), 2, ModItems.COPPER_SLIME_DNA.get(), ModItems.COPPER_SLIME_DNA.get(), Items.COPPER_BLOCK);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.IRON_SLIME_SPAWN_EGG.get(), 2, ModItems.IRON_SLIME_DNA.get(), ModItems.IRON_SLIME_DNA.get(), Items.IRON_BLOCK);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.GOLD_SLIME_SPAWN_EGG.get(), 2, ModItems.GOLD_SLIME_DNA.get(), ModItems.GOLD_SLIME_DNA.get(), Items.GOLD_BLOCK);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.DIAMOND_SLIME_SPAWN_EGG.get(), 2, ModItems.DIAMOND_SLIME_DNA.get(), ModItems.DIAMOND_SLIME_DNA.get(), Items.DIAMOND_BLOCK);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.NETHERITE_SLIME_SPAWN_EGG.get(), 2, ModItems.NETHERITE_SLIME_DNA.get(), ModItems.NETHERITE_SLIME_DNA.get(), Items.NETHERITE_BLOCK);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.LAPIS_SLIME_SPAWN_EGG.get(), 2, ModItems.LAPIS_SLIME_DNA.get(), ModItems.LAPIS_SLIME_DNA.get(), Items.LAPIS_BLOCK);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.REDSTONE_SLIME_SPAWN_EGG.get(), 2, ModItems.REDSTONE_SLIME_DNA.get(), ModItems.REDSTONE_SLIME_DNA.get(), Items.REDSTONE_BLOCK);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.OAK_SLIME_SPAWN_EGG.get(), 2, ModItems.OAK_SLIME_DNA.get(), ModItems.OAK_SLIME_DNA.get(), Items.OAK_PLANKS);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.SAND_SLIME_SPAWN_EGG.get(), 2, ModItems.SAND_SLIME_DNA.get(), ModItems.SAND_SLIME_DNA.get(), Items.SAND);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.ANDESITE_SLIME_SPAWN_EGG.get(), 2, ModItems.ANDESITE_SLIME_DNA.get(), ModItems.ANDESITE_SLIME_DNA.get(), Items.ANDESITE);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.SNOW_SLIME_SPAWN_EGG.get(), 2, ModItems.SNOW_SLIME_DNA.get(), ModItems.SNOW_SLIME_DNA.get(), Items.SNOW);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.ICE_SLIME_SPAWN_EGG.get(), 2, ModItems.ICE_SLIME_DNA.get(), ModItems.ICE_SLIME_DNA.get(), Items.ICE);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.MUD_SLIME_SPAWN_EGG.get(), 2, ModItems.MUD_SLIME_DNA.get(), ModItems.MUD_SLIME_DNA.get(), Items.MUD);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.CLAY_SLIME_SPAWN_EGG.get(), 2, ModItems.CLAY_SLIME_DNA.get(), ModItems.CLAY_SLIME_DNA.get(), Items.CLAY);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.RED_SAND_SLIME_SPAWN_EGG.get(), 2, ModItems.RED_SAND_SLIME_DNA.get(), ModItems.RED_SAND_SLIME_DNA.get(), Items.RED_SAND);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.MOSS_SLIME_SPAWN_EGG.get(), 2, ModItems.MOSS_SLIME_DNA.get(), ModItems.MOSS_SLIME_DNA.get(), Items.MOSS_BLOCK);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.DEEPSLATE_SLIME_SPAWN_EGG.get(), 2, ModItems.DEEPSLATE_SLIME_DNA.get(), ModItems.DEEPSLATE_SLIME_DNA.get(), Items.DEEPSLATE);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.GRANITE_SLIME_SPAWN_EGG.get(), 2, ModItems.GRANITE_SLIME_DNA.get(), ModItems.GRANITE_SLIME_DNA.get(), Items.GRANITE);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.DIORITE_SLIME_SPAWN_EGG.get(), 2, ModItems.DIORITE_SLIME_DNA.get(), ModItems.DIORITE_SLIME_DNA.get(), Items.DIORITE);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.CALCITE_SLIME_SPAWN_EGG.get(), 2, ModItems.CALCITE_SLIME_DNA.get(), ModItems.CALCITE_SLIME_DNA.get(), Items.CALCITE);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.TUFF_SLIME_SPAWN_EGG.get(), 2, ModItems.TUFF_SLIME_DNA.get(), ModItems.TUFF_SLIME_DNA.get(), Items.TUFF);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.DRIPSTONE_SLIME_SPAWN_EGG.get(), 2, ModItems.DRIPSTONE_SLIME_DNA.get(), ModItems.DRIPSTONE_SLIME_DNA.get(), Items.DRIPSTONE_BLOCK);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.PRISMARINE_SLIME_SPAWN_EGG.get(), 2, ModItems.PRISMARINE_SLIME_DNA.get(), ModItems.PRISMARINE_SLIME_DNA.get(), Items.PRISMARINE_SHARD);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.MAGMA_SLIME_SPAWN_EGG.get(), 2, ModItems.MAGMA_SLIME_DNA.get(), ModItems.MAGMA_SLIME_DNA.get(), Items.MAGMA_BLOCK);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.OBSIDIAN_SLIME_SPAWN_EGG.get(), 2, ModItems.OBSIDIAN_SLIME_DNA.get(), ModItems.OBSIDIAN_SLIME_DNA.get(), Items.OBSIDIAN);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.NETHERRACK_SLIME_SPAWN_EGG.get(), 2, ModItems.NETHERRACK_SLIME_DNA.get(), ModItems.NETHERRACK_SLIME_DNA.get(), Items.NETHERRACK);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.SOUL_SAND_SLIME_SPAWN_EGG.get(), 2, ModItems.SOUL_SAND_SLIME_DNA.get(), ModItems.SOUL_SAND_SLIME_DNA.get(), Items.SOUL_SAND);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.SOUL_SOIL_SLIME_SPAWN_EGG.get(), 2, ModItems.SOUL_SOIL_SLIME_DNA.get(), ModItems.SOUL_SOIL_SLIME_DNA.get(), Items.SOUL_SOIL);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.BLACKSTONE_SLIME_SPAWN_EGG.get(), 2, ModItems.BLACKSTONE_SLIME_DNA.get(), ModItems.BLACKSTONE_SLIME_DNA.get(), Items.BLACKSTONE);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.BASALT_SLIME_SPAWN_EGG.get(), 2, ModItems.BASALT_SLIME_DNA.get(), ModItems.BASALT_SLIME_DNA.get(), Items.BASALT);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.ENDSTONE_SLIME_SPAWN_EGG.get(), 2, ModItems.ENDSTONE_SLIME_DNA.get(), ModItems.ENDSTONE_SLIME_DNA.get(), Items.END_STONE);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.QUARTZ_SLIME_SPAWN_EGG.get(), 2, ModItems.QUARTZ_SLIME_DNA.get(), ModItems.QUARTZ_SLIME_DNA.get(), Items.QUARTZ);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.GLOWSTONE_SLIME_SPAWN_EGG.get(), 2, ModItems.GLOWSTONE_SLIME_DNA.get(), ModItems.GLOWSTONE_SLIME_DNA.get(), Items.GLOWSTONE_DUST);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.AMETHYST_SLIME_SPAWN_EGG.get(), 2, ModItems.AMETHYST_SLIME_DNA.get(), ModItems.AMETHYST_SLIME_DNA.get(), Items.AMETHYST_SHARD);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.BROWN_MUSHROOM_SLIME_SPAWN_EGG.get(), 2, ModItems.BROWN_MUSHROOM_SLIME_DNA.get(), ModItems.BROWN_MUSHROOM_SLIME_DNA.get(), Items.BROWN_MUSHROOM);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.RED_MUSHROOM_SLIME_SPAWN_EGG.get(), 2, ModItems.RED_MUSHROOM_SLIME_DNA.get(), ModItems.RED_MUSHROOM_SLIME_DNA.get(), Items.RED_MUSHROOM);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.CACTUS_SLIME_SPAWN_EGG.get(), 2, ModItems.CACTUS_SLIME_DNA.get(), ModItems.CACTUS_SLIME_DNA.get(), Items.CACTUS);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.COAL_SLIME_SPAWN_EGG.get(), 2, ModItems.COAL_SLIME_DNA.get(), ModItems.COAL_SLIME_DNA.get(), Items.COAL_BLOCK);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.GRAVEL_SLIME_SPAWN_EGG.get(), 2, ModItems.GRAVEL_SLIME_DNA.get(), ModItems.GRAVEL_SLIME_DNA.get(), Items.GRAVEL);
        dnaSynthesizingSelfRecipe(recipeOutput, ModItems.OAK_LEAVES_SLIME_SPAWN_EGG.get(), 2, ModItems.OAK_LEAVES_SLIME_DNA.get(), ModItems.OAK_LEAVES_SLIME_DNA.get(), Items.OAK_LEAVES);

        //DNA Synthesizing Recipe For Getting New Egg
        dnaSynthesizingRecipe(recipeOutput, ModItems.DIRT_SLIME_SPAWN_EGG.get(), 4,ModItems.SLIME_DNA.get(), ModItems.SLIME_DNA.get(), Items.DIRT);
        dnaSynthesizingRecipe(recipeOutput, ModItems.STONE_SLIME_SPAWN_EGG.get(), 4,ModItems.DIRT_SLIME_DNA.get(), ModItems.DIRT_SLIME_DNA.get(), Items.STONE);
        dnaSynthesizingRecipe(recipeOutput, ModItems.COAL_SLIME_SPAWN_EGG.get(), 4,ModItems.STONE_SLIME_DNA.get(), ModItems.STONE_SLIME_DNA.get(), Items.COAL_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.COPPER_SLIME_SPAWN_EGG.get(), 4,ModItems.COAL_SLIME_DNA.get(), ModItems.COAL_SLIME_DNA.get(), Items.COPPER_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.IRON_SLIME_SPAWN_EGG.get(), 4,ModItems.COPPER_SLIME_DNA.get(), ModItems.COPPER_SLIME_DNA.get(), Items.IRON_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.GOLD_SLIME_SPAWN_EGG.get(), 4,ModItems.IRON_SLIME_DNA.get(), ModItems.IRON_SLIME_DNA.get(), Items.GOLD_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.DIAMOND_SLIME_SPAWN_EGG.get(), 4,ModItems.GOLD_SLIME_DNA.get(), ModItems.GOLD_SLIME_DNA.get(), Items.DIAMOND_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.NETHERITE_SLIME_SPAWN_EGG.get(), 4,ModItems.DIAMOND_SLIME_DNA.get(), ModItems.DIAMOND_SLIME_DNA.get(), Items.NETHERITE_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.LAPIS_SLIME_SPAWN_EGG.get(), 4,ModItems.IRON_SLIME_DNA.get(), ModItems.IRON_SLIME_DNA.get(), Items.LAPIS_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.REDSTONE_SLIME_SPAWN_EGG.get(), 4,ModItems.GOLD_SLIME_DNA.get(), ModItems.GOLD_SLIME_DNA.get(), Items.REDSTONE_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.OAK_SLIME_SPAWN_EGG.get(), 4,ModItems.DIRT_SLIME_DNA.get(), ModItems.DIRT_SLIME_DNA.get(), Items.OAK_PLANKS);
        dnaSynthesizingRecipe(recipeOutput, ModItems.SAND_SLIME_SPAWN_EGG.get(), 4,ModItems.DIRT_SLIME_DNA.get(), ModItems.DIRT_SLIME_DNA.get(), Items.SAND);
        dnaSynthesizingRecipe(recipeOutput, ModItems.GRAVEL_SLIME_SPAWN_EGG.get(), 4,ModItems.SAND_SLIME_DNA.get(), ModItems.SAND_SLIME_DNA.get(), Items.GRAVEL);
        dnaSynthesizingRecipe(recipeOutput, ModItems.ANDESITE_SLIME_SPAWN_EGG.get(), 4,ModItems.STONE_SLIME_DNA.get(), ModItems.STONE_SLIME_DNA.get(), Items.ANDESITE);
        dnaSynthesizingRecipe(recipeOutput, ModItems.SNOW_SLIME_SPAWN_EGG.get(), 4,ModItems.SLIME_DNA.get(), ModItems.SLIME_DNA.get(), Items.SNOW);
        dnaSynthesizingRecipe(recipeOutput, ModItems.ICE_SLIME_SPAWN_EGG.get(), 4,ModItems.SNOW_SLIME_DNA.get(), ModItems.SNOW_SLIME_DNA.get(), Items.ICE);
        dnaSynthesizingRecipe(recipeOutput, ModItems.MUD_SLIME_SPAWN_EGG.get(), 4,ModItems.DIRT_SLIME_DNA.get(), ModItems.DIRT_SLIME_DNA.get(), Items.MUD);
        dnaSynthesizingRecipe(recipeOutput, ModItems.CLAY_SLIME_SPAWN_EGG.get(), 4,ModItems.MUD_SLIME_DNA.get(), ModItems.MUD_SLIME_DNA.get(), Items.CLAY);
        dnaSynthesizingRecipe(recipeOutput, ModItems.RED_SAND_SLIME_SPAWN_EGG.get(), 4,ModItems.SAND_SLIME_DNA.get(), ModItems.SAND_SLIME_DNA.get(), Items.RED_SAND);
        dnaSynthesizingRecipe(recipeOutput, ModItems.MOSS_SLIME_SPAWN_EGG.get(), 4,ModItems.DIRT_SLIME_DNA.get(), ModItems.DIRT_SLIME_DNA.get(), Items.MOSS_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.DEEPSLATE_SLIME_SPAWN_EGG.get(), 4,ModItems.STONE_SLIME_DNA.get(), ModItems.STONE_SLIME_DNA.get(), Items.DEEPSLATE);
        dnaSynthesizingRecipe(recipeOutput, ModItems.GRANITE_SLIME_SPAWN_EGG.get(), 4,ModItems.STONE_SLIME_DNA.get(), ModItems.STONE_SLIME_DNA.get(), Items.GRANITE);
        dnaSynthesizingRecipe(recipeOutput, ModItems.DIORITE_SLIME_SPAWN_EGG.get(), 4,ModItems.STONE_SLIME_DNA.get(), ModItems.STONE_SLIME_DNA.get(), Items.DIORITE);
        dnaSynthesizingRecipe(recipeOutput, ModItems.CALCITE_SLIME_SPAWN_EGG.get(), 4,ModItems.STONE_SLIME_DNA.get(), ModItems.STONE_SLIME_DNA.get(), Items.CALCITE);
        dnaSynthesizingRecipe(recipeOutput, ModItems.TUFF_SLIME_SPAWN_EGG.get(), 4,ModItems.STONE_SLIME_DNA.get(), ModItems.STONE_SLIME_DNA.get(), Items.TUFF);
        dnaSynthesizingRecipe(recipeOutput, ModItems.DRIPSTONE_SLIME_SPAWN_EGG.get(), 4,ModItems.STONE_SLIME_DNA.get(), ModItems.STONE_SLIME_DNA.get(), Items.DRIPSTONE_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.PRISMARINE_SLIME_SPAWN_EGG.get(), 4,ModItems.SAND_SLIME_DNA.get(), ModItems.SAND_SLIME_DNA.get(), Items.PRISMARINE_SHARD);
        dnaSynthesizingRecipe(recipeOutput, ModItems.MAGMA_SLIME_SPAWN_EGG.get(), 4,ModItems.NETHERITE_SLIME_DNA.get(), ModItems.NETHERITE_SLIME_DNA.get(), Items.MAGMA_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.OBSIDIAN_SLIME_SPAWN_EGG.get(), 4,ModItems.DEEPSLATE_SLIME_DNA.get(), ModItems.DEEPSLATE_SLIME_DNA.get(), Items.OBSIDIAN);
        dnaSynthesizingRecipe(recipeOutput, ModItems.NETHERRACK_SLIME_SPAWN_EGG.get(), 4,ModItems.STONE_SLIME_DNA.get(), ModItems.STONE_SLIME_DNA.get(), Items.NETHERRACK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.SOUL_SAND_SLIME_SPAWN_EGG.get(), 4,ModItems.SAND_SLIME_DNA.get(), ModItems.NETHERRACK_SLIME_DNA.get(), Items.SOUL_SAND);
        dnaSynthesizingRecipe(recipeOutput, ModItems.SOUL_SOIL_SLIME_SPAWN_EGG.get(), 4,ModItems.DIRT_SLIME_DNA.get(), ModItems.NETHERRACK_SLIME_DNA.get(), Items.SOUL_SOIL);
        dnaSynthesizingRecipe(recipeOutput, ModItems.BLACKSTONE_SLIME_SPAWN_EGG.get(), 4,ModItems.STONE_SLIME_DNA.get(), ModItems.NETHERRACK_SLIME_DNA.get(), Items.BLACKSTONE);
        dnaSynthesizingRecipe(recipeOutput, ModItems.BASALT_SLIME_SPAWN_EGG.get(), 4,ModItems.STONE_SLIME_DNA.get(), ModItems.NETHERRACK_SLIME_DNA.get(), Items.BASALT);
        dnaSynthesizingRecipe(recipeOutput, ModItems.ENDSTONE_SLIME_SPAWN_EGG.get(), 4,ModItems.DEEPSLATE_SLIME_DNA.get(), ModItems.NETHERRACK_SLIME_DNA.get(), Items.END_STONE);
        dnaSynthesizingRecipe(recipeOutput, ModItems.QUARTZ_SLIME_SPAWN_EGG.get(), 4,ModItems.IRON_SLIME_DNA.get(), ModItems.NETHERRACK_SLIME_DNA.get(), Items.QUARTZ_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.GLOWSTONE_SLIME_SPAWN_EGG.get(), 4,ModItems.GOLD_SLIME_DNA.get(), ModItems.NETHERRACK_SLIME_DNA.get(), Items.GLOWSTONE);
        dnaSynthesizingRecipe(recipeOutput, ModItems.AMETHYST_SLIME_SPAWN_EGG.get(), 4,ModItems.CALCITE_SLIME_DNA.get(), ModItems.GLOWSTONE_SLIME_DNA.get(), Items.AMETHYST_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.BROWN_MUSHROOM_SLIME_SPAWN_EGG.get(), 4,ModItems.MUD_SLIME_DNA.get(), ModItems.CACTUS_SLIME_DNA.get(), Items.BROWN_MUSHROOM_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.RED_MUSHROOM_SLIME_SPAWN_EGG.get(), 4,ModItems.MUD_SLIME_DNA.get(), ModItems.CACTUS_SLIME_DNA.get(), Items.RED_MUSHROOM_BLOCK);
        dnaSynthesizingRecipe(recipeOutput, ModItems.CACTUS_SLIME_SPAWN_EGG.get(), 4,ModItems.SAND_SLIME_DNA.get(), ModItems.SLIME_DNA.get(), Items.CACTUS);
        dnaSynthesizingRecipe(recipeOutput, ModItems.OAK_LEAVES_SLIME_SPAWN_EGG.get(), 4,ModItems.DIRT_SLIME_DNA.get(), ModItems.SLIME_DNA.get(), Items.OAK_LEAVES);
    }

    protected static void meltingRecipe(Consumer<FinishedRecipe> pRecipeOutput, ItemLike pIngredient, ItemLike pResult, int pInputCount, int outputCount) {
        MeltingRecipeBuilder.meltingRecipe()
                .addIngredient(Ingredient.of(pIngredient))
                .setInputCount(pInputCount)
                .addOutput(new ItemStack(pResult, outputCount))
                .setEnergy(200)
                .unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(pRecipeOutput, new ResourceLocation(ProductiveSlimes.MODID, "melting/" + getItemName(pIngredient) + "_melting"));
    }

    protected static void solidingRecipe(Consumer<FinishedRecipe> pRecipeOutput, ItemLike pIngredient, ItemLike pResult, int pInputCount, int outputCount) {
        SolidingRecipeBuilder.solidingRecipe()
                .addIngredient(Ingredient.of(pIngredient))
                .setInputCount(pInputCount)
                .addOutput(new ItemStack(pResult, outputCount))
                .addOutput(new ItemStack(Items.BUCKET, pInputCount))
                .setEnergy(200)
                .unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(pRecipeOutput, new ResourceLocation(ProductiveSlimes.MODID, "soliding/" + getItemName(pIngredient) + "_soliding"));
    }

    protected static void dnaExtractingRecipe(Consumer<FinishedRecipe> pRecipeOutput, ItemLike pIngredient, ItemLike pResult, int outputCount, float outputChance) {
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
                .save(pRecipeOutput, new ResourceLocation(ProductiveSlimes.MODID, "dna_extracting/" + getItemName(pIngredient) + "_dna_extracting"));

    }

    protected static void dnaSynthesizingSelfRecipe(Consumer<FinishedRecipe> pRecipeOutput, ItemLike pResult, int inputCount, ItemLike... pIngredient) {
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
                .save(pRecipeOutput, new ResourceLocation(ProductiveSlimes.MODID, "dna_synthesizing/" + getItemName(pResult) + "_dna_synthesizing_self"));

    }

    protected static void dnaSynthesizingRecipe(Consumer<FinishedRecipe> pRecipeOutput, ItemLike pResult, int inputCount, ItemLike... pIngredient) {
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
                .save(pRecipeOutput, new ResourceLocation(ProductiveSlimes.MODID, "dna_synthesizing/" + getItemName(pResult) + "_dna_synthesizing"));

    }

    protected static void slimeBlockToSlimeBall(Consumer<FinishedRecipe> pRecipeOutput, ItemLike pSlimeBlock, ItemLike pSlimeBall) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, pSlimeBall, 9)
                .requires(pSlimeBlock)
                .unlockedBy(getHasName(pSlimeBlock), has(pSlimeBlock))
                .save(pRecipeOutput, getItemName(pSlimeBall) + "_from_" + getItemName(pSlimeBlock));
    }

    protected static void slimeBallToSlimeBlock(Consumer<FinishedRecipe> pRecipeOutput, ItemLike pSlimeBall, ItemLike pSlimeBlock) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, pSlimeBlock, 1)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', pSlimeBall)
                .unlockedBy(getHasName(pSlimeBall), has(pSlimeBall))
                .save(pRecipeOutput, getItemName(pSlimeBlock) + "_from_" + getItemName(pSlimeBall));
    }
}
