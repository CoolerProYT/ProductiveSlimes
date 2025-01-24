package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.datagen.builder.*;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import com.coolerpromc.productiveslimes.util.ModTags;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> recipeOutput) {
        //Override vanilla recipes
        ShapelessRecipeBuilder.shapeless(Items.STICKY_PISTON, 1)
                .requires(Tags.Items.SLIMEBALLS)
                .requires(Items.PISTON)
                .unlockedBy(getHasName(Items.SLIME_BALL), has(Items.PISTON))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(Items.MAGMA_CREAM, 1)
                .requires(Tags.Items.SLIMEBALLS)
                .requires(Items.BLAZE_POWDER)
                .unlockedBy(getHasName(Items.SLIME_BALL), has(Items.BLAZE_POWDER))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(Items.LEAD,2)
                .pattern("AA ")
                .pattern("AB ")
                .pattern("  A")
                .define('A', Items.STRING)
                .define('B', Tags.Items.SLIMEBALLS)
                .unlockedBy(getHasName(Items.STICK), has(Tags.Items.SLIMEBALLS))
                .save(recipeOutput);

        //Mod Recipe
        ShapedRecipeBuilder.shaped(ModBlocks.MELTING_STATION.get(),1)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', Items.STONE)
                .define('B', Items.LAVA_BUCKET)
                .unlockedBy(getHasName(Items.STONE), has(Items.LAVA_BUCKET))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(ModBlocks.LIQUID_SOLIDING_STATION.get(),1)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', Items.STONE)
                .define('B', Items.WATER_BUCKET)
                .unlockedBy(getHasName(Items.STONE), has(Items.WATER_BUCKET))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(ModItems.ENERGY_SLIME_SPAWN_EGG.get(),1)
                .pattern("CAC")
                .pattern("ABA")
                .pattern("CAC")
                .define('A', Items.SLIME_BALL)
                .define('B', Items.EGG)
                .define('C', Items.REDSTONE)
                .unlockedBy(getHasName(Items.SLIME_BALL), has(Items.REDSTONE))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(ModBlocks.ENERGY_GENERATOR.get(),1)
                .pattern("CAC")
                .pattern("ABA")
                .pattern("CAC")
                .define('A', ModItems.ENERGY_SLIME_BALL.get())
                .define('B', Items.REDSTONE_BLOCK)
                .define('C', Items.REDSTONE)
                .unlockedBy(getHasName(Items.SLIME_BALL), has(Items.REDSTONE))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(ModBlocks.CABLE.get(),8)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', Items.REDSTONE)
                .define('B', Items.IRON_INGOT)
                .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(ModBlocks.DNA_EXTRACTOR.get(),1)
                .pattern("AAA")
                .pattern("ACA")
                .pattern("ABA")
                .define('A', Items.IRON_INGOT)
                .define('B', ModItems.ENERGY_SLIME_BALL.get())
                .define('C', Items.GLASS)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.GLASS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(ModBlocks.DNA_SYNTHESIZER.get(),1)
                .pattern("AAA")
                .pattern("CCC")
                .pattern("ABA")
                .define('A', Items.IRON_INGOT)
                .define('B', ModItems.ENERGY_SLIME_BALL.get())
                .define('C', Items.GLASS)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.GLASS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(ModItems.ENERGY_MULTIPLIER_UPGRADE.get(),1)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .define('A', Items.IRON_INGOT)
                .define('B', ModItems.ENERGY_SLIME_BALL.get())
                .define('C', Items.BLUE_WOOL)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(ModBlocks.FLUID_TANK.get(),1)
                .pattern("AAA")
                .pattern("BCB")
                .pattern("AAA")
                .define('A', Items.IRON_INGOT)
                .define('B', Items.GLASS)
                .define('C', Items.BUCKET)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.GLASS))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(ModItems.GUIDEBOOK.get(), 1)
                .requires(Items.BOOK)
                .requires(Tags.Items.SLIMEBALLS)
                .unlockedBy(getHasName(Items.BOOK), has(Items.SLIME_BALL))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(ModItems.SLIMEBALL_FRAGMENT.get(), 4)
                .requires(Items.SLIME_BALL)
                .unlockedBy(getHasName(Items.SLIME_BALL), has(Items.SLIME_BALL))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(Items.SLIME_BALL, 1)
                .pattern("AA ")
                .pattern("AA ")
                .pattern("   ")
                .define('A', ModItems.SLIMEBALL_FRAGMENT.get())
                .unlockedBy(getHasName(ModItems.SLIMEBALL_FRAGMENT.get()), has(ModItems.SLIMEBALL_FRAGMENT.get()))
                .save(recipeOutput, "slimeball_from_fragment");

        ShapedRecipeBuilder.shaped(ModBlocks.SQUEEZER.get(), 1)
                .pattern(" A ")
                .pattern(" A ")
                .pattern("AAA")
                .define('A', ModBlocks.SLIMY_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.SLIMY_PLANKS.get()), has(ModBlocks.SLIMY_PLANKS.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(ModBlocks.SLIME_SQUEEZER.get(), 1)
                .pattern("BAB")
                .pattern("C  ")
                .pattern("BBB")
                .define('A', ModBlocks.SQUEEZER.get())
                .define('B', ModBlocks.SLIMY_STONE.get())
                .define('C', ModItems.ENERGY_SLIME_BALL.get())
                .unlockedBy(getHasName(ModBlocks.SQUEEZER.get()), has(ModBlocks.SQUEEZER.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(ModBlocks.SLIME_NEST.get(), 1)
                .pattern("BBB")
                .pattern("BCB")
                .pattern("AAA")
                .define('A', ModBlocks.SLIMY_GRASS_BLOCK.get())
                .define('B', Items.GLASS_PANE)
                .define('C', Tags.Items.SLIMEBALLS)
                .unlockedBy(getHasName(ModBlocks.SLIMY_GRASS_BLOCK.get()), has(Items.GLASS_PANE))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(ModBlocks.SLIMEBALL_COLLECTOR.get(), 1)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .define('A', Items.IRON_INGOT)
                .define('B', Items.HOPPER)
                .define('C', Tags.Items.CHESTS)
                .unlockedBy(getHasName(Items.HOPPER), has(Tags.Items.CHESTS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(ModItems.SLIME_NEST_SPEED_UPGRADE_1.get(), 1)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .define('A', Items.REDSTONE_BLOCK)
                .define('B', ModTierLists.getBlockByName(Tier.IRON.getTierName()).get())
                .define('C', Tags.Items.INGOTS_IRON)
                .unlockedBy(getHasName(Items.REDSTONE_BLOCK), has(ModTierLists.getBlockByName(Tier.IRON.getTierName()).get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(ModItems.SLIME_NEST_SPEED_UPGRADE_2.get(), 1)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .define('A', ModItems.SLIME_NEST_SPEED_UPGRADE_1.get())
                .define('B', ModTierLists.getBlockByName(Tier.GOLD.getTierName()).get())
                .define('C', Tags.Items.INGOTS_GOLD)
                .unlockedBy(getHasName(ModItems.SLIME_NEST_SPEED_UPGRADE_1.get()), has(ModTierLists.getBlockByName(Tier.GOLD.getTierName()).get()))
                .save(recipeOutput);

        planksFromLogs(recipeOutput, ModBlocks.SLIMY_PLANKS.get(), ModTags.Items.SLIMY_LOG);

        woodFromLogs(recipeOutput, ModBlocks.SLIMY_WOOD.get(), ModBlocks.SLIMY_LOG.get());

        woodFromLogs(recipeOutput, ModBlocks.STRIPPED_SLIMY_WOOD.get(), ModBlocks.STRIPPED_SLIMY_LOG.get());

        stairBuilder(ModBlocks.SLIMY_STAIRS.get(), Ingredient.of(ModBlocks.SLIMY_PLANKS.get())).group("slimy")
                .unlockedBy("has_slimy", has(ModBlocks.SLIMY_PLANKS.get())).save(recipeOutput);

        slab(recipeOutput, ModBlocks.SLIMY_SLAB.get(), ModBlocks.SLIMY_PLANKS.get());
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

        slab(recipeOutput, ModBlocks.SLIMY_STONE_SLAB.get(), ModBlocks.SLIMY_STONE.get());

        buttonBuilder(ModBlocks.SLIMY_STONE_BUTTON.get(), Ingredient.of(ModBlocks.SLIMY_STONE.get())).group("slimy_stone")
                .unlockedBy("has_slimy_stone", has(ModBlocks.SLIMY_STONE.get())).save(recipeOutput);

        pressurePlate(recipeOutput, ModBlocks.SLIMY_STONE_PRESSURE_PLATE.get(), ModBlocks.SLIMY_STONE.get());

        stairBuilder(ModBlocks.SLIMY_COBBLESTONE_STAIRS.get(), Ingredient.of(ModBlocks.SLIMY_COBBLESTONE.get())).group("slimy_cobblestone")
                .unlockedBy("has_slimy_cobblestone", has(ModBlocks.SLIMY_COBBLESTONE.get())).save(recipeOutput);

        slab(recipeOutput, ModBlocks.SLIMY_COBBLESTONE_SLAB.get(), ModBlocks.SLIMY_COBBLESTONE.get());

        wall(recipeOutput, ModBlocks.SLIMY_COBBLESTONE_WALL.get(), ModBlocks.SLIMY_COBBLESTONE.get());

        //Slime Ball Recipe
        slimeBlockToSlimeBall(recipeOutput, ModBlocks.ENERGY_SLIME_BLOCK.get(), ModItems.ENERGY_SLIME_BALL.get());
        slimeBallToSlimeBlock(recipeOutput, ModItems.ENERGY_SLIME_BALL.get(), ModBlocks.ENERGY_SLIME_BLOCK.get());

        dnaExtractingRecipe(recipeOutput, Items.SLIME_BALL, ModItems.SLIME_DNA.get(), 1, 0.9f);

        for(Tier tier : Tier.values()){
            ModTiers modTiers = ModTierLists.getTierByName(tier);
            String name = modTiers.name();

            //Slime Ball Recipe
            slimeBlockToSlimeBall(recipeOutput, ModTierLists.getBlockByName(name).get(), ModTierLists.getSlimeballItemByName(name).get());
            slimeBallToSlimeBlock(recipeOutput, ModTierLists.getSlimeballItemByName(name).get(), ModTierLists.getBlockByName(name).get());

            //Melting Recipe
            meltingRecipe(recipeOutput, ModTierLists.getBlockByName(name).get(), ModTierLists.getBucketItemByName(name).get(), 2, 5);
            meltingRecipe(recipeOutput, ModTierLists.getSlimeballItemByName(name).get(), ModTierLists.getBucketItemByName(name).get(), 4, 1);

            //Soliding Recipe
            solidingRecipe(recipeOutput, ModTierLists.getBucketItemByName(name).get(), ModTierLists.getItemByKey(modTiers.solidingOutputKey()), 1, modTiers.solidingOutputAmount());

            //Dna Extracting Recipe
            dnaExtractingRecipe(recipeOutput, ModTierLists.getSlimeballItemByName(name).get(), ModTierLists.getDnaItemByName(name).get(), 1, modTiers.dnaOutputChance());

            //Dna Synthesizing Self Recipe
            dnaSynthesizingSelfRecipe(recipeOutput, ModTierLists.getSpawnEggItemByName(name).get(), 2, ModTierLists.getDnaItemByName(name).get(), ModTierLists.getDnaItemByName(name).get(), ModTierLists.getItemByKey(modTiers.synthesizingInputItemKey()));

            //Dna Synthesizing Recipe
            dnaSynthesizingRecipe(recipeOutput, ModTierLists.getSpawnEggItemByName(name).get(), 4, ModTierLists.getItemByKey(modTiers.synthesizingInputDnaKey1()), ModTierLists.getItemByKey(modTiers.synthesizingInputDnaKey2()), ModTierLists.getItemByKey(modTiers.synthesizingInputItemKey()));
        }

        squeezingRecipe(recipeOutput, ModBlocks.SLIMY_DIRT.get(), new ItemStack(Items.DIRT, 1), new ItemStack(ModItems.SLIMEBALL_FRAGMENT.get(), 1));
        squeezingRecipe(recipeOutput, ModBlocks.SLIMY_GRASS_BLOCK.get(), new ItemStack(Items.GRASS_BLOCK, 1), new ItemStack(ModItems.SLIMEBALL_FRAGMENT.get(), 1));
        squeezingRecipe(recipeOutput, ModBlocks.SLIMY_STONE.get(), new ItemStack(Items.STONE, 1), new ItemStack(ModItems.SLIMEBALL_FRAGMENT.get(), 1));
        squeezingRecipe(recipeOutput, ModBlocks.SLIMY_COBBLESTONE.get(), new ItemStack(Items.COBBLESTONE, 1), new ItemStack(ModItems.SLIMEBALL_FRAGMENT.get(), 1));
        squeezingRecipe(recipeOutput, ModBlocks.SLIMY_LOG.get(), new ItemStack(Items.OAK_LOG, 1), new ItemStack(ModItems.SLIMEBALL_FRAGMENT.get(), 1));
    }

    protected static void meltingRecipe(Consumer<IFinishedRecipe> pRecipeOutput, IItemProvider pIngredient, IItemProvider pResult, int pInputCount, int outputCount) {
        MeltingRecipeBuilder.meltingRecipe()
                .addIngredient(Ingredient.of(pIngredient))
                .setInputCount(pInputCount)
                .addOutput(new ItemStack(pResult, outputCount))
                .setEnergy(200)
                .unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(pRecipeOutput, new ResourceLocation(ProductiveSlimes.MODID, "melting/" + getItemName(pIngredient) + "_melting"));
    }

    protected static void solidingRecipe(Consumer<IFinishedRecipe> pRecipeOutput, IItemProvider pIngredient, IItemProvider pResult, int pInputCount, int outputCount) {
        SolidingRecipeBuilder.solidingRecipe()
                .addIngredient(Ingredient.of(pIngredient))
                .setInputCount(pInputCount)
                .addOutput(new ItemStack(pResult, outputCount))
                .addOutput(new ItemStack(Items.BUCKET, pInputCount))
                .setEnergy(200)
                .unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(pRecipeOutput, new ResourceLocation(ProductiveSlimes.MODID, "soliding/" + getItemName(pIngredient) + "_soliding"));
    }

    protected static void dnaExtractingRecipe(Consumer<IFinishedRecipe> pRecipeOutput, IItemProvider pIngredient, IItemProvider pResult, int outputCount, float outputChance) {
        DnaExtractingRecipeBuilder recipeBuilder = DnaExtractingRecipeBuilder.dnaExtractingRecipe()
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

    protected static void dnaSynthesizingSelfRecipe(Consumer<IFinishedRecipe> pRecipeOutput, IItemProvider pResult, int inputCount, IItemProvider... pIngredient) {
        DnaSynthesizingRecipeBuilder recipeBuilder = DnaSynthesizingRecipeBuilder.dnaSynthesizingRecipe();

        if (pIngredient.length != 3) {
            throw new IllegalArgumentException("Only accepts 3 ingredients.");
        }

        for (IItemProvider ingredient : pIngredient) {
            recipeBuilder.addIngredient(Ingredient.of(ingredient));
        }

        recipeBuilder
                .addOutput(new ItemStack(pResult, 1))
                .setInputCount(inputCount)
                .setEnergy(600)
                .unlockedBy(getHasName(Items.EGG), has(Items.EGG))
                .save(pRecipeOutput, new ResourceLocation(ProductiveSlimes.MODID, "dna_synthesizing/" + getItemName(pResult) + "_dna_synthesizing_self"));

    }

    protected static void dnaSynthesizingRecipe(Consumer<IFinishedRecipe> pRecipeOutput, IItemProvider pResult, int inputCount, IItemProvider... pIngredient) {
        DnaSynthesizingRecipeBuilder recipeBuilder = DnaSynthesizingRecipeBuilder.dnaSynthesizingRecipe();

        if (pIngredient.length != 3) {
            throw new IllegalArgumentException("Only accepts 3 ingredients.");
        }

        for (IItemProvider ingredient : pIngredient) {
            recipeBuilder.addIngredient(Ingredient.of(ingredient));
        }

        recipeBuilder
                .addOutput(new ItemStack(pResult, 1))
                .setInputCount(inputCount)
                .setEnergy(600)
                .unlockedBy(getHasName(Items.EGG), has(Items.EGG))
                .save(pRecipeOutput, new ResourceLocation(ProductiveSlimes.MODID, "dna_synthesizing/" + getItemName(pResult) + "_dna_synthesizing"));

    }

    protected static void squeezingRecipe(Consumer<IFinishedRecipe> pRecipeOutput, IItemProvider pIngredient, ItemStack pResult1, ItemStack pResult2) {
        SqueezingRecipeBuilder.squeezingRecipe()
                .addIngredient(Ingredient.of(pIngredient))
                .addOutput(pResult1)
                .addOutput(pResult2)
                .setEnergy(300)
                .unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(pRecipeOutput, new ResourceLocation(ProductiveSlimes.MODID, "squeezing/" + getItemName(pIngredient) + "_squeezing"));
    }

    protected static void slimeBlockToSlimeBall(Consumer<IFinishedRecipe> pRecipeOutput, IItemProvider pSlimeBlock, IItemProvider pSlimeBall) {
        ShapelessRecipeBuilder.shapeless(pSlimeBall, 9)
                .requires(pSlimeBlock)
                .unlockedBy(getHasName(pSlimeBlock), has(pSlimeBlock))
                .save(pRecipeOutput, getItemName(pSlimeBall) + "_from_" + getItemName(pSlimeBlock));
    }

    protected static void slimeBallToSlimeBlock(Consumer<IFinishedRecipe> pRecipeOutput, IItemProvider pSlimeBall, IItemProvider pSlimeBlock) {
        ShapedRecipeBuilder.shaped(pSlimeBlock, 1)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', pSlimeBall)
                .unlockedBy(getHasName(pSlimeBall), has(pSlimeBall))
                .save(pRecipeOutput, getItemName(pSlimeBlock) + "_from_" + getItemName(pSlimeBall));
    }

    private static String getHasName(IItemProvider pItemLike) {
        return "has_" + getItemName(pItemLike);
    }

    private static String getItemName(IItemProvider pItemLike) {
        return Registry.ITEM.getKey(pItemLike.asItem()).getPath();
    }

    private static void planksFromLogs(Consumer<IFinishedRecipe> pFinishedRecipeConsumer, IItemProvider pPlanks, Tags.IOptionalNamedTag<Item> pLogs) {
        ShapelessRecipeBuilder.shapeless(pPlanks, 4).requires(pLogs).group("planks").unlockedBy("has_logs", has(pLogs)).save(pFinishedRecipeConsumer);
    }

    private static void woodFromLogs(Consumer<IFinishedRecipe> pFinishedRecipeConsumer, IItemProvider pWood, IItemProvider pLog) {
        ShapedRecipeBuilder.shaped(pWood, 3).define('#', pLog).pattern("##").pattern("##").group("bark").unlockedBy("has_log", has(pLog)).save(pFinishedRecipeConsumer);
    }

    private static ShapelessRecipeBuilder buttonBuilder(IItemProvider pButton, Ingredient pMaterial) {
        return ShapelessRecipeBuilder.shapeless(pButton).requires(pMaterial);
    }

    private static ShapedRecipeBuilder doorBuilder(IItemProvider pDoor, Ingredient pMaterial) {
        return ShapedRecipeBuilder.shaped(pDoor, 3).define('#', pMaterial).pattern("##").pattern("##").pattern("##");
    }

    private static ShapedRecipeBuilder fenceBuilder(IItemProvider pFence, Ingredient pMaterial) {
        int i = pFence == Blocks.NETHER_BRICK_FENCE ? 6 : 3;
        Item item = pFence == Blocks.NETHER_BRICK_FENCE ? Items.NETHER_BRICK : Items.STICK;
        return ShapedRecipeBuilder.shaped(pFence, i).define('W', pMaterial).define('#', item).pattern("W#W").pattern("W#W");
    }

    private static ShapedRecipeBuilder fenceGateBuilder(IItemProvider pFenceGate, Ingredient pMaterial) {
        return ShapedRecipeBuilder.shaped(pFenceGate).define('#', Items.STICK).define('W', pMaterial).pattern("#W#").pattern("#W#");
    }

    private static void pressurePlate(Consumer<IFinishedRecipe> pFinishedRecipeConsumer, IItemProvider pPressurePlate, IItemProvider pMaterial) {
        pressurePlateBuilder(pPressurePlate, Ingredient.of(new IItemProvider[]{pMaterial})).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer);
    }

    private static ShapedRecipeBuilder pressurePlateBuilder(IItemProvider pPressurePlate, Ingredient pMaterial) {
        return ShapedRecipeBuilder.shaped(pPressurePlate).define('#', pMaterial).pattern("##");
    }

    private static void slab(Consumer<IFinishedRecipe> pFinishedRecipeConsumer, IItemProvider pSlab, IItemProvider pMaterial) {
        slabBuilder(pSlab, Ingredient.of(new IItemProvider[]{pMaterial})).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer);
    }

    private static ShapedRecipeBuilder slabBuilder(IItemProvider pSlab, Ingredient pMaterial) {
        return ShapedRecipeBuilder.shaped(pSlab, 6).define('#', pMaterial).pattern("###");
    }

    private static ShapedRecipeBuilder stairBuilder(IItemProvider pStairs, Ingredient pMaterial) {
        return ShapedRecipeBuilder.shaped(pStairs, 4).define('#', pMaterial).pattern("#  ").pattern("## ").pattern("###");
    }

    private static ShapedRecipeBuilder trapdoorBuilder(IItemProvider pTrapdoor, Ingredient pMaterial) {
        return ShapedRecipeBuilder.shaped(pTrapdoor, 2).define('#', pMaterial).pattern("###").pattern("###");
    }

    protected static void wall(Consumer<IFinishedRecipe> p_251034_,IItemProvider p_250499_, IItemProvider p_249970_) {
        wallBuilder(p_250499_, Ingredient.of(p_249970_)).unlockedBy(getHasName(p_249970_), has(p_249970_)).save(p_251034_);
    }

    protected static ShapedRecipeBuilder wallBuilder(IItemProvider p_250754_, Ingredient p_250311_) {
        return ShapedRecipeBuilder.shaped(p_250754_, 6).define('#', p_250311_).pattern("###").pattern("###");
    }
}
