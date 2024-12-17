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

        //Slime Ball Recipe
        slimeBlockToSlimeBall(output, ModBlocks.DIRT_SLIME_BLOCK, ModItems.DIRT_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.DIRT_SLIME_BALL, ModBlocks.DIRT_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.STONE_SLIME_BLOCK, ModItems.STONE_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.STONE_SLIME_BALL, ModBlocks.STONE_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.COPPER_SLIME_BLOCK, ModItems.COPPER_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.COPPER_SLIME_BALL, ModBlocks.COPPER_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.IRON_SLIME_BLOCK, ModItems.IRON_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.IRON_SLIME_BALL, ModBlocks.IRON_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.GOLD_SLIME_BLOCK, ModItems.GOLD_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.GOLD_SLIME_BALL, ModBlocks.GOLD_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.DIAMOND_SLIME_BLOCK, ModItems.DIAMOND_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.DIAMOND_SLIME_BALL, ModBlocks.DIAMOND_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.NETHERITE_SLIME_BLOCK, ModItems.NETHERITE_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.NETHERITE_SLIME_BALL, ModBlocks.NETHERITE_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.LAPIS_SLIME_BLOCK, ModItems.LAPIS_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.LAPIS_SLIME_BALL, ModBlocks.LAPIS_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.REDSTONE_SLIME_BLOCK, ModItems.REDSTONE_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.REDSTONE_SLIME_BALL, ModBlocks.REDSTONE_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.OAK_SLIME_BLOCK, ModItems.OAK_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.OAK_SLIME_BALL, ModBlocks.OAK_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.SAND_SLIME_BLOCK, ModItems.SAND_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.SAND_SLIME_BALL, ModBlocks.SAND_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.ANDESITE_SLIME_BLOCK, ModItems.ANDESITE_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.ANDESITE_SLIME_BALL, ModBlocks.ANDESITE_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.SNOW_SLIME_BLOCK, ModItems.SNOW_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.SNOW_SLIME_BALL, ModBlocks.SNOW_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.ICE_SLIME_BLOCK, ModItems.ICE_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.ICE_SLIME_BALL, ModBlocks.ICE_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.MUD_SLIME_BLOCK, ModItems.MUD_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.MUD_SLIME_BALL, ModBlocks.MUD_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.CLAY_SLIME_BLOCK, ModItems.CLAY_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.CLAY_SLIME_BALL, ModBlocks.CLAY_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.RED_SAND_SLIME_BLOCK, ModItems.RED_SAND_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.RED_SAND_SLIME_BALL, ModBlocks.RED_SAND_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.MOSS_SLIME_BLOCK, ModItems.MOSS_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.MOSS_SLIME_BALL, ModBlocks.MOSS_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.DEEPSLATE_SLIME_BLOCK, ModItems.DEEPSLATE_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.DEEPSLATE_SLIME_BALL, ModBlocks.DEEPSLATE_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.GRANITE_SLIME_BLOCK, ModItems.GRANITE_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.GRANITE_SLIME_BALL, ModBlocks.GRANITE_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.DIORITE_SLIME_BLOCK, ModItems.DIORITE_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.DIORITE_SLIME_BALL, ModBlocks.DIORITE_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.CALCITE_SLIME_BLOCK, ModItems.CALCITE_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.CALCITE_SLIME_BALL, ModBlocks.CALCITE_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.TUFF_SLIME_BLOCK, ModItems.TUFF_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.TUFF_SLIME_BALL, ModBlocks.TUFF_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.DRIPSTONE_SLIME_BLOCK, ModItems.DRIPSTONE_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.DRIPSTONE_SLIME_BALL, ModBlocks.DRIPSTONE_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.PRISMARINE_SLIME_BLOCK, ModItems.PRISMARINE_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.PRISMARINE_SLIME_BALL, ModBlocks.PRISMARINE_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.MAGMA_SLIME_BLOCK, ModItems.MAGMA_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.MAGMA_SLIME_BALL, ModBlocks.MAGMA_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.OBSIDIAN_SLIME_BLOCK, ModItems.OBSIDIAN_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.OBSIDIAN_SLIME_BALL, ModBlocks.OBSIDIAN_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.NETHERRACK_SLIME_BLOCK, ModItems.NETHERRACK_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.NETHERRACK_SLIME_BALL, ModBlocks.NETHERRACK_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.SOUL_SAND_SLIME_BLOCK, ModItems.SOUL_SAND_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.SOUL_SAND_SLIME_BALL, ModBlocks.SOUL_SAND_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.SOUL_SOIL_SLIME_BLOCK, ModItems.SOUL_SOIL_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.SOUL_SOIL_SLIME_BALL, ModBlocks.SOUL_SOIL_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.BLACKSTONE_SLIME_BLOCK, ModItems.BLACKSTONE_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.BLACKSTONE_SLIME_BALL, ModBlocks.BLACKSTONE_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.BASALT_SLIME_BLOCK, ModItems.BASALT_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.BASALT_SLIME_BALL, ModBlocks.BASALT_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.ENDSTONE_SLIME_BLOCK, ModItems.ENDSTONE_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.ENDSTONE_SLIME_BALL, ModBlocks.ENDSTONE_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.QUARTZ_SLIME_BLOCK, ModItems.QUARTZ_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.QUARTZ_SLIME_BALL, ModBlocks.QUARTZ_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.GLOWSTONE_SLIME_BLOCK, ModItems.GLOWSTONE_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.GLOWSTONE_SLIME_BALL, ModBlocks.GLOWSTONE_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.AMETHYST_SLIME_BLOCK, ModItems.AMETHYST_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.AMETHYST_SLIME_BALL, ModBlocks.AMETHYST_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.BROWN_MUSHROOM_SLIME_BLOCK, ModItems.BROWN_MUSHROOM_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.BROWN_MUSHROOM_SLIME_BALL, ModBlocks.BROWN_MUSHROOM_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.RED_MUSHROOM_SLIME_BLOCK, ModItems.RED_MUSHROOM_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.RED_MUSHROOM_SLIME_BALL, ModBlocks.RED_MUSHROOM_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.CACTUS_SLIME_BLOCK, ModItems.CACTUS_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.CACTUS_SLIME_BALL, ModBlocks.CACTUS_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.COAL_SLIME_BLOCK, ModItems.COAL_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.COAL_SLIME_BALL, ModBlocks.COAL_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.GRAVEL_SLIME_BLOCK, ModItems.GRAVEL_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.GRAVEL_SLIME_BALL, ModBlocks.GRAVEL_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.ENERGY_SLIME_BLOCK, ModItems.ENERGY_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.ENERGY_SLIME_BALL, ModBlocks.ENERGY_SLIME_BLOCK);

        slimeBlockToSlimeBall(output, ModBlocks.OAK_LEAVES_SLIME_BLOCK, ModItems.OAK_LEAVES_SLIME_BALL);
        slimeBallToSlimeBlock(output, ModItems.OAK_LEAVES_SLIME_BALL, ModBlocks.OAK_LEAVES_SLIME_BLOCK);

        //Melting Recipe
        meltingRecipe(output, ModBlocks.DIRT_SLIME_BLOCK, ModFluids.MOLTEN_DIRT_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.DIRT_SLIME_BALL, ModFluids.MOLTEN_DIRT_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.STONE_SLIME_BLOCK, ModFluids.MOLTEN_STONE_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.STONE_SLIME_BALL, ModFluids.MOLTEN_STONE_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.COPPER_SLIME_BLOCK, ModFluids.MOLTEN_COPPER_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.COPPER_SLIME_BALL, ModFluids.MOLTEN_COPPER_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.IRON_SLIME_BLOCK, ModFluids.MOLTEN_IRON_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.IRON_SLIME_BALL, ModFluids.MOLTEN_IRON_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.GOLD_SLIME_BLOCK, ModFluids.MOLTEN_GOLD_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.GOLD_SLIME_BALL, ModFluids.MOLTEN_GOLD_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.DIAMOND_SLIME_BLOCK, ModFluids.MOLTEN_DIAMOND_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.DIAMOND_SLIME_BALL, ModFluids.MOLTEN_DIAMOND_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.NETHERITE_SLIME_BLOCK, ModFluids.MOLTEN_NETHERITE_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.NETHERITE_SLIME_BALL, ModFluids.MOLTEN_NETHERITE_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.LAPIS_SLIME_BLOCK, ModFluids.MOLTEN_LAPIS_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.LAPIS_SLIME_BALL, ModFluids.MOLTEN_LAPIS_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.REDSTONE_SLIME_BLOCK, ModFluids.MOLTEN_REDSTONE_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.REDSTONE_SLIME_BALL, ModFluids.MOLTEN_REDSTONE_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.OAK_SLIME_BLOCK, ModFluids.MOLTEN_OAK_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.OAK_SLIME_BALL, ModFluids.MOLTEN_OAK_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.SAND_SLIME_BLOCK, ModFluids.MOLTEN_SAND_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.SAND_SLIME_BALL, ModFluids.MOLTEN_SAND_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.ANDESITE_SLIME_BLOCK, ModFluids.MOLTEN_ANDESITE_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.ANDESITE_SLIME_BALL, ModFluids.MOLTEN_ANDESITE_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.SNOW_SLIME_BLOCK, ModFluids.MOLTEN_SNOW_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.SNOW_SLIME_BALL, ModFluids.MOLTEN_SNOW_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.ICE_SLIME_BLOCK, ModFluids.MOLTEN_ICE_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.ICE_SLIME_BALL, ModFluids.MOLTEN_ICE_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.MUD_SLIME_BLOCK, ModFluids.MOLTEN_MUD_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.MUD_SLIME_BALL, ModFluids.MOLTEN_MUD_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.CLAY_SLIME_BLOCK, ModFluids.MOLTEN_CLAY_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.CLAY_SLIME_BALL, ModFluids.MOLTEN_CLAY_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.RED_SAND_SLIME_BLOCK, ModFluids.MOLTEN_RED_SAND_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.RED_SAND_SLIME_BALL, ModFluids.MOLTEN_RED_SAND_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.MOSS_SLIME_BLOCK, ModFluids.MOLTEN_MOSS_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.MOSS_SLIME_BALL, ModFluids.MOLTEN_MOSS_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.DEEPSLATE_SLIME_BLOCK, ModFluids.MOLTEN_DEEPSLATE_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.DEEPSLATE_SLIME_BALL, ModFluids.MOLTEN_DEEPSLATE_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.GRANITE_SLIME_BLOCK, ModFluids.MOLTEN_GRANITE_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.GRANITE_SLIME_BALL, ModFluids.MOLTEN_GRANITE_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.DIORITE_SLIME_BLOCK, ModFluids.MOLTEN_DIORITE_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.DIORITE_SLIME_BALL, ModFluids.MOLTEN_DIORITE_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.CALCITE_SLIME_BLOCK, ModFluids.MOLTEN_CALCITE_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.CALCITE_SLIME_BALL, ModFluids.MOLTEN_CALCITE_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.TUFF_SLIME_BLOCK, ModFluids.MOLTEN_TUFF_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.TUFF_SLIME_BALL, ModFluids.MOLTEN_TUFF_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.DRIPSTONE_SLIME_BLOCK, ModFluids.MOLTEN_DRIPSTONE_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.DRIPSTONE_SLIME_BALL, ModFluids.MOLTEN_DRIPSTONE_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.PRISMARINE_SLIME_BLOCK, ModFluids.MOLTEN_PRISMARINE_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.PRISMARINE_SLIME_BALL, ModFluids.MOLTEN_PRISMARINE_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.MAGMA_SLIME_BLOCK, ModFluids.MOLTEN_MAGMA_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.MAGMA_SLIME_BALL, ModFluids.MOLTEN_MAGMA_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.OBSIDIAN_SLIME_BLOCK, ModFluids.MOLTEN_OBSIDIAN_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.OBSIDIAN_SLIME_BALL, ModFluids.MOLTEN_OBSIDIAN_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.NETHERRACK_SLIME_BLOCK, ModFluids.MOLTEN_NETHERRACK_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.NETHERRACK_SLIME_BALL, ModFluids.MOLTEN_NETHERRACK_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.SOUL_SAND_SLIME_BLOCK, ModFluids.MOLTEN_SOUL_SAND_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.SOUL_SAND_SLIME_BALL, ModFluids.MOLTEN_SOUL_SAND_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.SOUL_SOIL_SLIME_BLOCK, ModFluids.MOLTEN_SOUL_SOIL_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.SOUL_SOIL_SLIME_BALL, ModFluids.MOLTEN_SOUL_SOIL_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.BLACKSTONE_SLIME_BLOCK, ModFluids.MOLTEN_BLACKSTONE_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.BLACKSTONE_SLIME_BALL, ModFluids.MOLTEN_BLACKSTONE_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.BASALT_SLIME_BLOCK, ModFluids.MOLTEN_BASALT_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.BASALT_SLIME_BALL, ModFluids.MOLTEN_BASALT_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.ENDSTONE_SLIME_BLOCK, ModFluids.MOLTEN_ENDSTONE_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.ENDSTONE_SLIME_BALL, ModFluids.MOLTEN_ENDSTONE_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.QUARTZ_SLIME_BLOCK, ModFluids.MOLTEN_QUARTZ_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.QUARTZ_SLIME_BALL, ModFluids.MOLTEN_QUARTZ_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.GLOWSTONE_SLIME_BLOCK, ModFluids.MOLTEN_GLOWSTONE_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.GLOWSTONE_SLIME_BALL, ModFluids.MOLTEN_GLOWSTONE_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.AMETHYST_SLIME_BLOCK, ModFluids.MOLTEN_AMETHYST_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.AMETHYST_SLIME_BALL, ModFluids.MOLTEN_AMETHYST_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.BROWN_MUSHROOM_SLIME_BLOCK, ModFluids.MOLTEN_BROWN_MUSHROOM_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.BROWN_MUSHROOM_SLIME_BALL, ModFluids.MOLTEN_BROWN_MUSHROOM_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.RED_MUSHROOM_SLIME_BLOCK, ModFluids.MOLTEN_RED_MUSHROOM_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.RED_MUSHROOM_SLIME_BALL, ModFluids.MOLTEN_RED_MUSHROOM_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.CACTUS_SLIME_BLOCK, ModFluids.MOLTEN_CACTUS_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.CACTUS_SLIME_BALL, ModFluids.MOLTEN_CACTUS_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.COAL_SLIME_BLOCK, ModFluids.MOLTEN_COAL_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.COAL_SLIME_BALL, ModFluids.MOLTEN_COAL_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.GRAVEL_SLIME_BLOCK, ModFluids.MOLTEN_GRAVEL_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.GRAVEL_SLIME_BALL, ModFluids.MOLTEN_GRAVEL_BUCKET, 4, 1);

        meltingRecipe(output, ModBlocks.OAK_LEAVES_SLIME_BLOCK, ModFluids.MOLTEN_OAK_LEAVES_BUCKET, 2, 5);
        meltingRecipe(output, ModItems.OAK_LEAVES_SLIME_BALL, ModFluids.MOLTEN_OAK_LEAVES_BUCKET, 4, 1);

        //Soliding Recipe
        solidingRecipe(output, ModFluids.MOLTEN_DIRT_BUCKET, Items.DIRT, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_STONE_BUCKET, Items.STONE, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_COPPER_BUCKET, Items.COPPER_INGOT, 1, 1);
        solidingRecipe(output, ModFluids.MOLTEN_IRON_BUCKET, Items.IRON_INGOT, 1, 1);
        solidingRecipe(output, ModFluids.MOLTEN_GOLD_BUCKET, Items.GOLD_INGOT, 1, 1);
        solidingRecipe(output, ModFluids.MOLTEN_DIAMOND_BUCKET, Items.DIAMOND, 1, 1);
        solidingRecipe(output, ModFluids.MOLTEN_NETHERITE_BUCKET, Items.NETHERITE_INGOT, 1, 1);
        solidingRecipe(output, ModFluids.MOLTEN_LAPIS_BUCKET, Items.LAPIS_LAZULI, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_REDSTONE_BUCKET, Items.REDSTONE, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_OAK_BUCKET, Items.OAK_PLANKS, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_SAND_BUCKET, Items.SAND, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_ANDESITE_BUCKET, Items.ANDESITE, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_SNOW_BUCKET, Items.SNOW, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_ICE_BUCKET, Items.ICE, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_MUD_BUCKET, Items.MUD, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_CLAY_BUCKET, Items.CLAY, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_RED_SAND_BUCKET, Items.RED_SAND, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_MOSS_BUCKET, Items.MOSS_BLOCK, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_DEEPSLATE_BUCKET, Items.DEEPSLATE, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_GRANITE_BUCKET, Items.GRANITE, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_DIORITE_BUCKET, Items.DIORITE, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_CALCITE_BUCKET, Items.CALCITE, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_TUFF_BUCKET, Items.TUFF, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_DRIPSTONE_BUCKET, Items.POINTED_DRIPSTONE, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_PRISMARINE_BUCKET, Items.PRISMARINE_SHARD, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_MAGMA_BUCKET, Items.MAGMA_BLOCK, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_OBSIDIAN_BUCKET, Items.OBSIDIAN, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_NETHERRACK_BUCKET, Items.NETHERRACK, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_SOUL_SAND_BUCKET, Items.SOUL_SAND, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_SOUL_SOIL_BUCKET, Items.SOUL_SOIL, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_BLACKSTONE_BUCKET, Items.BLACKSTONE, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_BASALT_BUCKET, Items.BASALT, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_ENDSTONE_BUCKET, Items.END_STONE, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_QUARTZ_BUCKET, Items.QUARTZ, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_GLOWSTONE_BUCKET, Items.GLOWSTONE_DUST, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_AMETHYST_BUCKET, Items.AMETHYST_SHARD, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_BROWN_MUSHROOM_BUCKET, Items.BROWN_MUSHROOM, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_RED_MUSHROOM_BUCKET, Items.RED_MUSHROOM, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_CACTUS_BUCKET, Items.CACTUS, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_COAL_BUCKET, Items.COAL, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_GRAVEL_BUCKET, Items.GRAVEL, 1, 2);
        solidingRecipe(output, ModFluids.MOLTEN_OAK_LEAVES_BUCKET, Items.OAK_LEAVES, 1, 2);

        dnaExtractingRecipe(output, Items.SLIME_BALL, ModItems.SLIME_DNA, 1, 0.9f);
        dnaExtractingRecipe(output, ModItems.DIRT_SLIME_BALL, ModItems.DIRT_SLIME_DNA, 1, 0.75f);
        dnaExtractingRecipe(output, ModItems.STONE_SLIME_BALL, ModItems.STONE_SLIME_DNA, 1, 0.70f);
        dnaExtractingRecipe(output, ModItems.COPPER_SLIME_BALL, ModItems.COPPER_SLIME_DNA, 1, 0.6f);
        dnaExtractingRecipe(output, ModItems.IRON_SLIME_BALL, ModItems.IRON_SLIME_DNA, 1, 0.6f);
        dnaExtractingRecipe(output, ModItems.GOLD_SLIME_BALL, ModItems.GOLD_SLIME_DNA, 1, 0.5f);
        dnaExtractingRecipe(output, ModItems.DIAMOND_SLIME_BALL, ModItems.DIAMOND_SLIME_DNA, 1, 0.4f);
        dnaExtractingRecipe(output, ModItems.NETHERITE_SLIME_BALL, ModItems.NETHERITE_SLIME_DNA, 1, 0.3f);
        dnaExtractingRecipe(output, ModItems.LAPIS_SLIME_BALL, ModItems.LAPIS_SLIME_DNA, 1, 0.6f);
        dnaExtractingRecipe(output, ModItems.REDSTONE_SLIME_BALL, ModItems.REDSTONE_SLIME_DNA, 1, 0.6f);
        dnaExtractingRecipe(output, ModItems.OAK_SLIME_BALL, ModItems.OAK_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(output, ModItems.SAND_SLIME_BALL, ModItems.SAND_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(output, ModItems.ANDESITE_SLIME_BALL, ModItems.ANDESITE_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(output, ModItems.SNOW_SLIME_BALL, ModItems.SNOW_SLIME_DNA, 1, 0.65f);
        dnaExtractingRecipe(output, ModItems.ICE_SLIME_BALL, ModItems.ICE_SLIME_DNA, 1, 0.6f);
        dnaExtractingRecipe(output, ModItems.MUD_SLIME_BALL, ModItems.MUD_SLIME_DNA, 1, 0.8f);
        dnaExtractingRecipe(output, ModItems.CLAY_SLIME_BALL, ModItems.CLAY_SLIME_DNA, 1, 0.75f);
        dnaExtractingRecipe(output, ModItems.RED_SAND_SLIME_BALL, ModItems.RED_SAND_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(output, ModItems.MOSS_SLIME_BALL, ModItems.MOSS_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(output, ModItems.DEEPSLATE_SLIME_BALL, ModItems.DEEPSLATE_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(output, ModItems.GRANITE_SLIME_BALL, ModItems.GRANITE_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(output, ModItems.DIORITE_SLIME_BALL, ModItems.DIORITE_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(output, ModItems.CALCITE_SLIME_BALL, ModItems.CALCITE_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(output, ModItems.TUFF_SLIME_BALL, ModItems.TUFF_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(output, ModItems.DRIPSTONE_SLIME_BALL, ModItems.DRIPSTONE_SLIME_DNA, 1, 0.6f);
        dnaExtractingRecipe(output, ModItems.PRISMARINE_SLIME_BALL, ModItems.PRISMARINE_SLIME_DNA, 1, 0.5f);
        dnaExtractingRecipe(output, ModItems.MAGMA_SLIME_BALL, ModItems.MAGMA_SLIME_DNA, 1, 0.5f);
        dnaExtractingRecipe(output, ModItems.OBSIDIAN_SLIME_BALL, ModItems.OBSIDIAN_SLIME_DNA, 1, 0.45f);
        dnaExtractingRecipe(output, ModItems.NETHERRACK_SLIME_BALL, ModItems.NETHERRACK_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(output, ModItems.SOUL_SAND_SLIME_BALL, ModItems.SOUL_SAND_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(output, ModItems.SOUL_SOIL_SLIME_BALL, ModItems.SOUL_SOIL_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(output, ModItems.BLACKSTONE_SLIME_BALL, ModItems.BLACKSTONE_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(output, ModItems.BASALT_SLIME_BALL, ModItems.BASALT_SLIME_DNA, 1, 0.7f);
        dnaExtractingRecipe(output, ModItems.ENDSTONE_SLIME_BALL, ModItems.ENDSTONE_SLIME_DNA, 1, 0.6f);
        dnaExtractingRecipe(output, ModItems.QUARTZ_SLIME_BALL, ModItems.QUARTZ_SLIME_DNA, 1, 0.55f);
        dnaExtractingRecipe(output, ModItems.GLOWSTONE_SLIME_BALL, ModItems.GLOWSTONE_SLIME_DNA, 1, 0.5f);
        dnaExtractingRecipe(output, ModItems.AMETHYST_SLIME_BALL, ModItems.AMETHYST_SLIME_DNA, 1, 0.4f);
        dnaExtractingRecipe(output, ModItems.BROWN_MUSHROOM_SLIME_BALL, ModItems.BROWN_MUSHROOM_SLIME_DNA, 1, 0.3f);
        dnaExtractingRecipe(output, ModItems.RED_MUSHROOM_SLIME_BALL, ModItems.RED_MUSHROOM_SLIME_DNA, 1, 0.3f);
        dnaExtractingRecipe(output, ModItems.CACTUS_SLIME_BALL, ModItems.CACTUS_SLIME_DNA, 1, 0.6f);
        dnaExtractingRecipe(output, ModItems.COAL_SLIME_BALL, ModItems.COAL_SLIME_DNA, 1, 0.65f);
        dnaExtractingRecipe(output, ModItems.GRAVEL_SLIME_BALL, ModItems.GRAVEL_SLIME_DNA, 1, 0.6f);
        dnaExtractingRecipe(output, ModItems.OAK_LEAVES_SLIME_BALL, ModItems.OAK_LEAVES_SLIME_DNA, 1, 0.7f);

        //DNA Synthesizing Recipe For Getting Self
        dnaSynthesizingSelfRecipe(output, ModItems.DIRT_SLIME_SPAWN_EGG, 2, ModItems.DIRT_SLIME_DNA, ModItems.DIRT_SLIME_DNA, Items.DIRT);
        dnaSynthesizingSelfRecipe(output, ModItems.STONE_SLIME_SPAWN_EGG, 2, ModItems.STONE_SLIME_DNA, ModItems.STONE_SLIME_DNA, Items.STONE);
        dnaSynthesizingSelfRecipe(output, ModItems.COPPER_SLIME_SPAWN_EGG, 2, ModItems.COPPER_SLIME_DNA, ModItems.COPPER_SLIME_DNA, Items.COPPER_BLOCK);
        dnaSynthesizingSelfRecipe(output, ModItems.IRON_SLIME_SPAWN_EGG, 2, ModItems.IRON_SLIME_DNA, ModItems.IRON_SLIME_DNA, Items.IRON_BLOCK);
        dnaSynthesizingSelfRecipe(output, ModItems.GOLD_SLIME_SPAWN_EGG, 2, ModItems.GOLD_SLIME_DNA, ModItems.GOLD_SLIME_DNA, Items.GOLD_BLOCK);
        dnaSynthesizingSelfRecipe(output, ModItems.DIAMOND_SLIME_SPAWN_EGG, 2, ModItems.DIAMOND_SLIME_DNA, ModItems.DIAMOND_SLIME_DNA, Items.DIAMOND_BLOCK);
        dnaSynthesizingSelfRecipe(output, ModItems.NETHERITE_SLIME_SPAWN_EGG, 2, ModItems.NETHERITE_SLIME_DNA, ModItems.NETHERITE_SLIME_DNA, Items.NETHERITE_BLOCK);
        dnaSynthesizingSelfRecipe(output, ModItems.LAPIS_SLIME_SPAWN_EGG, 2, ModItems.LAPIS_SLIME_DNA, ModItems.LAPIS_SLIME_DNA, Items.LAPIS_BLOCK);
        dnaSynthesizingSelfRecipe(output, ModItems.REDSTONE_SLIME_SPAWN_EGG, 2, ModItems.REDSTONE_SLIME_DNA, ModItems.REDSTONE_SLIME_DNA, Items.REDSTONE_BLOCK);
        dnaSynthesizingSelfRecipe(output, ModItems.OAK_SLIME_SPAWN_EGG, 2, ModItems.OAK_SLIME_DNA, ModItems.OAK_SLIME_DNA, Items.OAK_PLANKS);
        dnaSynthesizingSelfRecipe(output, ModItems.SAND_SLIME_SPAWN_EGG, 2, ModItems.SAND_SLIME_DNA, ModItems.SAND_SLIME_DNA, Items.SAND);
        dnaSynthesizingSelfRecipe(output, ModItems.ANDESITE_SLIME_SPAWN_EGG, 2, ModItems.ANDESITE_SLIME_DNA, ModItems.ANDESITE_SLIME_DNA, Items.ANDESITE);
        dnaSynthesizingSelfRecipe(output, ModItems.SNOW_SLIME_SPAWN_EGG, 2, ModItems.SNOW_SLIME_DNA, ModItems.SNOW_SLIME_DNA, Items.SNOW);
        dnaSynthesizingSelfRecipe(output, ModItems.ICE_SLIME_SPAWN_EGG, 2, ModItems.ICE_SLIME_DNA, ModItems.ICE_SLIME_DNA, Items.ICE);
        dnaSynthesizingSelfRecipe(output, ModItems.MUD_SLIME_SPAWN_EGG, 2, ModItems.MUD_SLIME_DNA, ModItems.MUD_SLIME_DNA, Items.MUD);
        dnaSynthesizingSelfRecipe(output, ModItems.CLAY_SLIME_SPAWN_EGG, 2, ModItems.CLAY_SLIME_DNA, ModItems.CLAY_SLIME_DNA, Items.CLAY);
        dnaSynthesizingSelfRecipe(output, ModItems.RED_SAND_SLIME_SPAWN_EGG, 2, ModItems.RED_SAND_SLIME_DNA, ModItems.RED_SAND_SLIME_DNA, Items.RED_SAND);
        dnaSynthesizingSelfRecipe(output, ModItems.MOSS_SLIME_SPAWN_EGG, 2, ModItems.MOSS_SLIME_DNA, ModItems.MOSS_SLIME_DNA, Items.MOSS_BLOCK);
        dnaSynthesizingSelfRecipe(output, ModItems.DEEPSLATE_SLIME_SPAWN_EGG, 2, ModItems.DEEPSLATE_SLIME_DNA, ModItems.DEEPSLATE_SLIME_DNA, Items.DEEPSLATE);
        dnaSynthesizingSelfRecipe(output, ModItems.GRANITE_SLIME_SPAWN_EGG, 2, ModItems.GRANITE_SLIME_DNA, ModItems.GRANITE_SLIME_DNA, Items.GRANITE);
        dnaSynthesizingSelfRecipe(output, ModItems.DIORITE_SLIME_SPAWN_EGG, 2, ModItems.DIORITE_SLIME_DNA, ModItems.DIORITE_SLIME_DNA, Items.DIORITE);
        dnaSynthesizingSelfRecipe(output, ModItems.CALCITE_SLIME_SPAWN_EGG, 2, ModItems.CALCITE_SLIME_DNA, ModItems.CALCITE_SLIME_DNA, Items.CALCITE);
        dnaSynthesizingSelfRecipe(output, ModItems.TUFF_SLIME_SPAWN_EGG, 2, ModItems.TUFF_SLIME_DNA, ModItems.TUFF_SLIME_DNA, Items.TUFF);
        dnaSynthesizingSelfRecipe(output, ModItems.DRIPSTONE_SLIME_SPAWN_EGG, 2, ModItems.DRIPSTONE_SLIME_DNA, ModItems.DRIPSTONE_SLIME_DNA, Items.DRIPSTONE_BLOCK);
        dnaSynthesizingSelfRecipe(output, ModItems.PRISMARINE_SLIME_SPAWN_EGG, 2, ModItems.PRISMARINE_SLIME_DNA, ModItems.PRISMARINE_SLIME_DNA, Items.PRISMARINE_SHARD);
        dnaSynthesizingSelfRecipe(output, ModItems.MAGMA_SLIME_SPAWN_EGG, 2, ModItems.MAGMA_SLIME_DNA, ModItems.MAGMA_SLIME_DNA, Items.MAGMA_BLOCK);
        dnaSynthesizingSelfRecipe(output, ModItems.OBSIDIAN_SLIME_SPAWN_EGG, 2, ModItems.OBSIDIAN_SLIME_DNA, ModItems.OBSIDIAN_SLIME_DNA, Items.OBSIDIAN);
        dnaSynthesizingSelfRecipe(output, ModItems.NETHERRACK_SLIME_SPAWN_EGG, 2, ModItems.NETHERRACK_SLIME_DNA, ModItems.NETHERRACK_SLIME_DNA, Items.NETHERRACK);
        dnaSynthesizingSelfRecipe(output, ModItems.SOUL_SAND_SLIME_SPAWN_EGG, 2, ModItems.SOUL_SAND_SLIME_DNA, ModItems.SOUL_SAND_SLIME_DNA, Items.SOUL_SAND);
        dnaSynthesizingSelfRecipe(output, ModItems.SOUL_SOIL_SLIME_SPAWN_EGG, 2, ModItems.SOUL_SOIL_SLIME_DNA, ModItems.SOUL_SOIL_SLIME_DNA, Items.SOUL_SOIL);
        dnaSynthesizingSelfRecipe(output, ModItems.BLACKSTONE_SLIME_SPAWN_EGG, 2, ModItems.BLACKSTONE_SLIME_DNA, ModItems.BLACKSTONE_SLIME_DNA, Items.BLACKSTONE);
        dnaSynthesizingSelfRecipe(output, ModItems.BASALT_SLIME_SPAWN_EGG, 2, ModItems.BASALT_SLIME_DNA, ModItems.BASALT_SLIME_DNA, Items.BASALT);
        dnaSynthesizingSelfRecipe(output, ModItems.ENDSTONE_SLIME_SPAWN_EGG, 2, ModItems.ENDSTONE_SLIME_DNA, ModItems.ENDSTONE_SLIME_DNA, Items.END_STONE);
        dnaSynthesizingSelfRecipe(output, ModItems.QUARTZ_SLIME_SPAWN_EGG, 2, ModItems.QUARTZ_SLIME_DNA, ModItems.QUARTZ_SLIME_DNA, Items.QUARTZ);
        dnaSynthesizingSelfRecipe(output, ModItems.GLOWSTONE_SLIME_SPAWN_EGG, 2, ModItems.GLOWSTONE_SLIME_DNA, ModItems.GLOWSTONE_SLIME_DNA, Items.GLOWSTONE_DUST);
        dnaSynthesizingSelfRecipe(output, ModItems.AMETHYST_SLIME_SPAWN_EGG, 2, ModItems.AMETHYST_SLIME_DNA, ModItems.AMETHYST_SLIME_DNA, Items.AMETHYST_SHARD);
        dnaSynthesizingSelfRecipe(output, ModItems.BROWN_MUSHROOM_SLIME_SPAWN_EGG, 2, ModItems.BROWN_MUSHROOM_SLIME_DNA, ModItems.BROWN_MUSHROOM_SLIME_DNA, Items.BROWN_MUSHROOM);
        dnaSynthesizingSelfRecipe(output, ModItems.RED_MUSHROOM_SLIME_SPAWN_EGG, 2, ModItems.RED_MUSHROOM_SLIME_DNA, ModItems.RED_MUSHROOM_SLIME_DNA, Items.RED_MUSHROOM);
        dnaSynthesizingSelfRecipe(output, ModItems.CACTUS_SLIME_SPAWN_EGG, 2, ModItems.CACTUS_SLIME_DNA, ModItems.CACTUS_SLIME_DNA, Items.CACTUS);
        dnaSynthesizingSelfRecipe(output, ModItems.COAL_SLIME_SPAWN_EGG, 2, ModItems.COAL_SLIME_DNA, ModItems.COAL_SLIME_DNA, Items.COAL_BLOCK);
        dnaSynthesizingSelfRecipe(output, ModItems.GRAVEL_SLIME_SPAWN_EGG, 2, ModItems.GRAVEL_SLIME_DNA, ModItems.GRAVEL_SLIME_DNA, Items.GRAVEL);
        dnaSynthesizingSelfRecipe(output, ModItems.OAK_LEAVES_SLIME_SPAWN_EGG, 2, ModItems.OAK_LEAVES_SLIME_DNA, ModItems.OAK_LEAVES_SLIME_DNA, Items.OAK_LEAVES);

        //DNA Synthesizing Recipe For Getting New Egg
        dnaSynthesizingRecipe(output, ModItems.DIRT_SLIME_SPAWN_EGG, 4,ModItems.SLIME_DNA, ModItems.SLIME_DNA, Items.DIRT);
        dnaSynthesizingRecipe(output, ModItems.STONE_SLIME_SPAWN_EGG, 4,ModItems.DIRT_SLIME_DNA, ModItems.DIRT_SLIME_DNA, Items.STONE);
        dnaSynthesizingRecipe(output, ModItems.COAL_SLIME_SPAWN_EGG, 4,ModItems.STONE_SLIME_DNA, ModItems.STONE_SLIME_DNA, Items.COAL_BLOCK);
        dnaSynthesizingRecipe(output, ModItems.COPPER_SLIME_SPAWN_EGG, 4,ModItems.COAL_SLIME_DNA, ModItems.COAL_SLIME_DNA, Items.COPPER_BLOCK);
        dnaSynthesizingRecipe(output, ModItems.IRON_SLIME_SPAWN_EGG, 4,ModItems.COPPER_SLIME_DNA, ModItems.COPPER_SLIME_DNA, Items.IRON_BLOCK);
        dnaSynthesizingRecipe(output, ModItems.GOLD_SLIME_SPAWN_EGG, 4,ModItems.IRON_SLIME_DNA, ModItems.IRON_SLIME_DNA, Items.GOLD_BLOCK);
        dnaSynthesizingRecipe(output, ModItems.DIAMOND_SLIME_SPAWN_EGG, 4,ModItems.GOLD_SLIME_DNA, ModItems.GOLD_SLIME_DNA, Items.DIAMOND_BLOCK);
        dnaSynthesizingRecipe(output, ModItems.NETHERITE_SLIME_SPAWN_EGG, 4,ModItems.DIAMOND_SLIME_DNA, ModItems.DIAMOND_SLIME_DNA, Items.NETHERITE_BLOCK);
        dnaSynthesizingRecipe(output, ModItems.LAPIS_SLIME_SPAWN_EGG, 4,ModItems.IRON_SLIME_DNA, ModItems.IRON_SLIME_DNA, Items.LAPIS_BLOCK);
        dnaSynthesizingRecipe(output, ModItems.REDSTONE_SLIME_SPAWN_EGG, 4,ModItems.GOLD_SLIME_DNA, ModItems.GOLD_SLIME_DNA, Items.REDSTONE_BLOCK);
        dnaSynthesizingRecipe(output, ModItems.OAK_SLIME_SPAWN_EGG, 4,ModItems.DIRT_SLIME_DNA, ModItems.DIRT_SLIME_DNA, Items.OAK_PLANKS);
        dnaSynthesizingRecipe(output, ModItems.SAND_SLIME_SPAWN_EGG, 4,ModItems.DIRT_SLIME_DNA, ModItems.DIRT_SLIME_DNA, Items.SAND);
        dnaSynthesizingRecipe(output, ModItems.GRAVEL_SLIME_SPAWN_EGG, 4,ModItems.SAND_SLIME_DNA, ModItems.SAND_SLIME_DNA, Items.GRAVEL);
        dnaSynthesizingRecipe(output, ModItems.ANDESITE_SLIME_SPAWN_EGG, 4,ModItems.STONE_SLIME_DNA, ModItems.STONE_SLIME_DNA, Items.ANDESITE);
        dnaSynthesizingRecipe(output, ModItems.SNOW_SLIME_SPAWN_EGG, 4,ModItems.SLIME_DNA, ModItems.SLIME_DNA, Items.SNOW);
        dnaSynthesizingRecipe(output, ModItems.ICE_SLIME_SPAWN_EGG, 4,ModItems.SNOW_SLIME_DNA, ModItems.SNOW_SLIME_DNA, Items.ICE);
        dnaSynthesizingRecipe(output, ModItems.MUD_SLIME_SPAWN_EGG, 4,ModItems.DIRT_SLIME_DNA, ModItems.DIRT_SLIME_DNA, Items.MUD);
        dnaSynthesizingRecipe(output, ModItems.CLAY_SLIME_SPAWN_EGG, 4,ModItems.MUD_SLIME_DNA, ModItems.MUD_SLIME_DNA, Items.CLAY);
        dnaSynthesizingRecipe(output, ModItems.RED_SAND_SLIME_SPAWN_EGG, 4,ModItems.SAND_SLIME_DNA, ModItems.SAND_SLIME_DNA, Items.RED_SAND);
        dnaSynthesizingRecipe(output, ModItems.MOSS_SLIME_SPAWN_EGG, 4,ModItems.DIRT_SLIME_DNA, ModItems.DIRT_SLIME_DNA, Items.MOSS_BLOCK);
        dnaSynthesizingRecipe(output, ModItems.DEEPSLATE_SLIME_SPAWN_EGG, 4,ModItems.STONE_SLIME_DNA, ModItems.STONE_SLIME_DNA, Items.DEEPSLATE);
        dnaSynthesizingRecipe(output, ModItems.GRANITE_SLIME_SPAWN_EGG, 4,ModItems.STONE_SLIME_DNA, ModItems.STONE_SLIME_DNA, Items.GRANITE);
        dnaSynthesizingRecipe(output, ModItems.DIORITE_SLIME_SPAWN_EGG, 4,ModItems.STONE_SLIME_DNA, ModItems.STONE_SLIME_DNA, Items.DIORITE);
        dnaSynthesizingRecipe(output, ModItems.CALCITE_SLIME_SPAWN_EGG, 4,ModItems.STONE_SLIME_DNA, ModItems.STONE_SLIME_DNA, Items.CALCITE);
        dnaSynthesizingRecipe(output, ModItems.TUFF_SLIME_SPAWN_EGG, 4,ModItems.STONE_SLIME_DNA, ModItems.STONE_SLIME_DNA, Items.TUFF);
        dnaSynthesizingRecipe(output, ModItems.DRIPSTONE_SLIME_SPAWN_EGG, 4,ModItems.STONE_SLIME_DNA, ModItems.STONE_SLIME_DNA, Items.DRIPSTONE_BLOCK);
        dnaSynthesizingRecipe(output, ModItems.PRISMARINE_SLIME_SPAWN_EGG, 4,ModItems.SAND_SLIME_DNA, ModItems.SAND_SLIME_DNA, Items.PRISMARINE_SHARD);
        dnaSynthesizingRecipe(output, ModItems.MAGMA_SLIME_SPAWN_EGG, 4,ModItems.NETHERITE_SLIME_DNA, ModItems.NETHERITE_SLIME_DNA, Items.MAGMA_BLOCK);
        dnaSynthesizingRecipe(output, ModItems.OBSIDIAN_SLIME_SPAWN_EGG, 4,ModItems.DEEPSLATE_SLIME_DNA, ModItems.DEEPSLATE_SLIME_DNA, Items.OBSIDIAN);
        dnaSynthesizingRecipe(output, ModItems.NETHERRACK_SLIME_SPAWN_EGG, 4,ModItems.STONE_SLIME_DNA, ModItems.STONE_SLIME_DNA, Items.NETHERRACK);
        dnaSynthesizingRecipe(output, ModItems.SOUL_SAND_SLIME_SPAWN_EGG, 4,ModItems.SAND_SLIME_DNA, ModItems.NETHERRACK_SLIME_DNA, Items.SOUL_SAND);
        dnaSynthesizingRecipe(output, ModItems.SOUL_SOIL_SLIME_SPAWN_EGG, 4,ModItems.DIRT_SLIME_DNA, ModItems.NETHERRACK_SLIME_DNA, Items.SOUL_SOIL);
        dnaSynthesizingRecipe(output, ModItems.BLACKSTONE_SLIME_SPAWN_EGG, 4,ModItems.STONE_SLIME_DNA, ModItems.NETHERRACK_SLIME_DNA, Items.BLACKSTONE);
        dnaSynthesizingRecipe(output, ModItems.BASALT_SLIME_SPAWN_EGG, 4,ModItems.STONE_SLIME_DNA, ModItems.NETHERRACK_SLIME_DNA, Items.BASALT);
        dnaSynthesizingRecipe(output, ModItems.ENDSTONE_SLIME_SPAWN_EGG, 4,ModItems.DEEPSLATE_SLIME_DNA, ModItems.NETHERRACK_SLIME_DNA, Items.END_STONE);
        dnaSynthesizingRecipe(output, ModItems.QUARTZ_SLIME_SPAWN_EGG, 4,ModItems.IRON_SLIME_DNA, ModItems.NETHERRACK_SLIME_DNA, Items.QUARTZ_BLOCK);
        dnaSynthesizingRecipe(output, ModItems.GLOWSTONE_SLIME_SPAWN_EGG, 4,ModItems.GOLD_SLIME_DNA, ModItems.NETHERRACK_SLIME_DNA, Items.GLOWSTONE);
        dnaSynthesizingRecipe(output, ModItems.AMETHYST_SLIME_SPAWN_EGG, 4,ModItems.CALCITE_SLIME_DNA, ModItems.GLOWSTONE_SLIME_DNA, Items.AMETHYST_BLOCK);
        dnaSynthesizingRecipe(output, ModItems.BROWN_MUSHROOM_SLIME_SPAWN_EGG, 4,ModItems.MUD_SLIME_DNA, ModItems.CACTUS_SLIME_DNA, Items.BROWN_MUSHROOM_BLOCK);
        dnaSynthesizingRecipe(output, ModItems.RED_MUSHROOM_SLIME_SPAWN_EGG, 4,ModItems.MUD_SLIME_DNA, ModItems.CACTUS_SLIME_DNA, Items.RED_MUSHROOM_BLOCK);
        dnaSynthesizingRecipe(output, ModItems.CACTUS_SLIME_SPAWN_EGG, 4,ModItems.SAND_SLIME_DNA, ModItems.SLIME_DNA, Items.CACTUS);
        dnaSynthesizingRecipe(output, ModItems.OAK_LEAVES_SLIME_SPAWN_EGG, 4,ModItems.DIRT_SLIME_DNA, ModItems.SLIME_DNA, Items.OAK_LEAVES);
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
