package com.coolerpromc.productiveslimes.fluid;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.item.custom.BucketItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(BuiltInRegistries.FLUID, ProductiveSlimes.MODID);

    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_DIRT = FLUIDS.register("source_molten_dirt",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_DIRT_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_DIRT = FLUIDS.register("flowing_molten_dirt",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_DIRT_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_DIRT_BLOCK = ModBlocks.BLOCKS.register("molten_dirt_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_DIRT.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));

    public static final DeferredItem<Item> MOLTEN_DIRT_BUCKET = ModItems.ITEMS.registerItem("molten_dirt_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_DIRT.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF866043));
    public static final BaseFlowingFluid.Properties MOLTEN_DIRT_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_DIRT_FLUID_TYPE, SOURCE_MOLTEN_DIRT, FLOWING_MOLTEN_DIRT)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_DIRT_BLOCK).bucket(ModFluids.MOLTEN_DIRT_BUCKET);

    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_STONE = FLUIDS.register("source_molten_stone",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_STONE_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_STONE = FLUIDS.register("flowing_molten_stone",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_STONE_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_STONE_BLOCK = ModBlocks.BLOCKS.register("molten_stone_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_STONE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));

    public static final DeferredItem<Item> MOLTEN_STONE_BUCKET = ModItems.ITEMS.registerItem("molten_stone_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_STONE.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF4a4545));
    public static final BaseFlowingFluid.Properties MOLTEN_STONE_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_STONE_FLUID_TYPE, SOURCE_MOLTEN_STONE, FLOWING_MOLTEN_STONE)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_STONE_BLOCK).bucket(ModFluids.MOLTEN_STONE_BUCKET);

    // Copper
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_COPPER = FLUIDS.register("source_molten_copper",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_COPPER_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_COPPER = FLUIDS.register("flowing_molten_copper",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_COPPER_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_COPPER_BLOCK = ModBlocks.BLOCKS.register("molten_copper_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_COPPER.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_COPPER_BUCKET = ModItems.ITEMS.registerItem("molten_copper_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_COPPER.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF6a3e15));
    public static final BaseFlowingFluid.Properties MOLTEN_COPPER_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_COPPER_FLUID_TYPE, SOURCE_MOLTEN_COPPER, FLOWING_MOLTEN_COPPER)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_COPPER_BLOCK).bucket(ModFluids.MOLTEN_COPPER_BUCKET);

    // Iron
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_IRON = FLUIDS.register("source_molten_iron",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_IRON_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_IRON = FLUIDS.register("flowing_molten_iron",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_IRON_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_IRON_BLOCK = ModBlocks.BLOCKS.register("molten_iron_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_IRON.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_IRON_BUCKET = ModItems.ITEMS.registerItem("molten_iron_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_IRON.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF898c8a));
    public static final BaseFlowingFluid.Properties MOLTEN_IRON_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_IRON_FLUID_TYPE, SOURCE_MOLTEN_IRON, FLOWING_MOLTEN_IRON)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_IRON_BLOCK).bucket(ModFluids.MOLTEN_IRON_BUCKET);

    // Gold
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_GOLD = FLUIDS.register("source_molten_gold",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_GOLD_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_GOLD = FLUIDS.register("flowing_molten_gold",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_GOLD_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_GOLD_BLOCK = ModBlocks.BLOCKS.register("molten_gold_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_GOLD.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_GOLD_BUCKET = ModItems.ITEMS.registerItem("molten_gold_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_GOLD.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFFa5953f));
    public static final BaseFlowingFluid.Properties MOLTEN_GOLD_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_GOLD_FLUID_TYPE, SOURCE_MOLTEN_GOLD, FLOWING_MOLTEN_GOLD)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_GOLD_BLOCK).bucket(ModFluids.MOLTEN_GOLD_BUCKET);

    // Diamond
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_DIAMOND = FLUIDS.register("source_molten_diamond",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_DIAMOND_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_DIAMOND = FLUIDS.register("flowing_molten_diamond",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_DIAMOND_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_DIAMOND_BLOCK = ModBlocks.BLOCKS.register("molten_diamond_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_DIAMOND.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_DIAMOND_BUCKET = ModItems.ITEMS.registerItem("molten_diamond_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_DIAMOND.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF178f9c));
    public static final BaseFlowingFluid.Properties MOLTEN_DIAMOND_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_DIAMOND_FLUID_TYPE, SOURCE_MOLTEN_DIAMOND, FLOWING_MOLTEN_DIAMOND)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_DIAMOND_BLOCK).bucket(ModFluids.MOLTEN_DIAMOND_BUCKET);

    // Netherite
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_NETHERITE = FLUIDS.register("source_molten_netherite",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_NETHERITE_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_NETHERITE = FLUIDS.register("flowing_molten_netherite",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_NETHERITE_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_NETHERITE_BLOCK = ModBlocks.BLOCKS.register("molten_netherite_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_NETHERITE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_NETHERITE_BUCKET = ModItems.ITEMS.registerItem("molten_netherite_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_NETHERITE.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF4c2b2b));
    public static final BaseFlowingFluid.Properties MOLTEN_NETHERITE_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_NETHERITE_FLUID_TYPE, SOURCE_MOLTEN_NETHERITE, FLOWING_MOLTEN_NETHERITE)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_NETHERITE_BLOCK).bucket(ModFluids.MOLTEN_NETHERITE_BUCKET);

    // Lapis
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_LAPIS = FLUIDS.register("source_molten_lapis",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_LAPIS_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_LAPIS = FLUIDS.register("flowing_molten_lapis",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_LAPIS_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_LAPIS_BLOCK = ModBlocks.BLOCKS.register("molten_lapis_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_LAPIS.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_LAPIS_BUCKET = ModItems.ITEMS.registerItem("molten_lapis_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_LAPIS.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF1c41ba));
    public static final BaseFlowingFluid.Properties MOLTEN_LAPIS_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_LAPIS_FLUID_TYPE, SOURCE_MOLTEN_LAPIS, FLOWING_MOLTEN_LAPIS)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_LAPIS_BLOCK).bucket(ModFluids.MOLTEN_LAPIS_BUCKET);

    // Redstone
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_REDSTONE = FLUIDS.register("source_molten_redstone",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_REDSTONE_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_REDSTONE = FLUIDS.register("flowing_molten_redstone",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_REDSTONE_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_REDSTONE_BLOCK = ModBlocks.BLOCKS.register("molten_redstone_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_REDSTONE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_REDSTONE_BUCKET = ModItems.ITEMS.registerItem("molten_redstone_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_REDSTONE.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFFa10505));
    public static final BaseFlowingFluid.Properties MOLTEN_REDSTONE_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_REDSTONE_FLUID_TYPE, SOURCE_MOLTEN_REDSTONE, FLOWING_MOLTEN_REDSTONE)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_REDSTONE_BLOCK).bucket(ModFluids.MOLTEN_REDSTONE_BUCKET);

    // Oak
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_OAK = FLUIDS.register("source_molten_oak",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_OAK_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_OAK = FLUIDS.register("flowing_molten_oak",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_OAK_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_OAK_BLOCK = ModBlocks.BLOCKS.register("molten_oak_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_OAK.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_OAK_BUCKET = ModItems.ITEMS.registerItem("molten_oak_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_OAK.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFFa69d6f));
    public static final BaseFlowingFluid.Properties MOLTEN_OAK_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_OAK_FLUID_TYPE, SOURCE_MOLTEN_OAK, FLOWING_MOLTEN_OAK)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_OAK_BLOCK).bucket(ModFluids.MOLTEN_OAK_BUCKET);

    // Sand
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_SAND = FLUIDS.register("source_molten_sand",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_SAND_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_SAND = FLUIDS.register("flowing_molten_sand",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_SAND_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_SAND_BLOCK = ModBlocks.BLOCKS.register("molten_sand_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_SAND.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_SAND_BUCKET = ModItems.ITEMS.registerItem("molten_sand_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_SAND.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFFf7f7c6));
    public static final BaseFlowingFluid.Properties MOLTEN_SAND_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_SAND_FLUID_TYPE, SOURCE_MOLTEN_SAND, FLOWING_MOLTEN_SAND)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_SAND_BLOCK).bucket(ModFluids.MOLTEN_SAND_BUCKET);

    // Andesite
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_ANDESITE = FLUIDS.register("source_molten_andesite",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_ANDESITE_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_ANDESITE = FLUIDS.register("flowing_molten_andesite",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_ANDESITE_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_ANDESITE_BLOCK = ModBlocks.BLOCKS.register("molten_andesite_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_ANDESITE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_ANDESITE_BUCKET = ModItems.ITEMS.registerItem("molten_andesite_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_ANDESITE.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF9d9e9a));
    public static final BaseFlowingFluid.Properties MOLTEN_ANDESITE_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_ANDESITE_FLUID_TYPE, SOURCE_MOLTEN_ANDESITE, FLOWING_MOLTEN_ANDESITE)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_ANDESITE_BLOCK).bucket(ModFluids.MOLTEN_ANDESITE_BUCKET);

    // Snow
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_SNOW = FLUIDS.register("source_molten_snow",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_SNOW_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_SNOW = FLUIDS.register("flowing_molten_snow",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_SNOW_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_SNOW_BLOCK = ModBlocks.BLOCKS.register("molten_snow_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_SNOW.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_SNOW_BUCKET = ModItems.ITEMS.registerItem("molten_snow_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_SNOW.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFFf2fcfc));
    public static final BaseFlowingFluid.Properties MOLTEN_SNOW_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_SNOW_FLUID_TYPE, SOURCE_MOLTEN_SNOW, FLOWING_MOLTEN_SNOW)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_SNOW_BLOCK).bucket(ModFluids.MOLTEN_SNOW_BUCKET);

    // Ice
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_ICE = FLUIDS.register("source_molten_ice",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_ICE_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_ICE = FLUIDS.register("flowing_molten_ice",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_ICE_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_ICE_BLOCK = ModBlocks.BLOCKS.register("molten_ice_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_ICE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_ICE_BUCKET = ModItems.ITEMS.registerItem("molten_ice_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_ICE.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF89b1fc));
    public static final BaseFlowingFluid.Properties MOLTEN_ICE_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_ICE_FLUID_TYPE, SOURCE_MOLTEN_ICE, FLOWING_MOLTEN_ICE)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_ICE_BLOCK).bucket(ModFluids.MOLTEN_ICE_BUCKET);

    // Mud
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_MUD = FLUIDS.register("source_molten_mud",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_MUD_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_MUD = FLUIDS.register("flowing_molten_mud",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_MUD_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_MUD_BLOCK = ModBlocks.BLOCKS.register("molten_mud_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_MUD.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_MUD_BUCKET = ModItems.ITEMS.registerItem("molten_mud_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_MUD.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF363339));
    public static final BaseFlowingFluid.Properties MOLTEN_MUD_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_MUD_FLUID_TYPE, SOURCE_MOLTEN_MUD, FLOWING_MOLTEN_MUD)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_MUD_BLOCK).bucket(ModFluids.MOLTEN_MUD_BUCKET);

    // Clay
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_CLAY = FLUIDS.register("source_molten_clay",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_CLAY_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_CLAY = FLUIDS.register("flowing_molten_clay",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_CLAY_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_CLAY_BLOCK = ModBlocks.BLOCKS.register("molten_clay_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_CLAY.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_CLAY_BUCKET = ModItems.ITEMS.registerItem("molten_clay_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_CLAY.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF9ca2ac));
    public static final BaseFlowingFluid.Properties MOLTEN_CLAY_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_CLAY_FLUID_TYPE, SOURCE_MOLTEN_CLAY, FLOWING_MOLTEN_CLAY)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_CLAY_BLOCK).bucket(ModFluids.MOLTEN_CLAY_BUCKET);

    // Red Sand
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_RED_SAND = FLUIDS.register("source_molten_red_sand",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_RED_SAND_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_RED_SAND = FLUIDS.register("flowing_molten_red_sand",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_RED_SAND_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_RED_SAND_BLOCK = ModBlocks.BLOCKS.register("molten_red_sand_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_RED_SAND.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_RED_SAND_BUCKET = ModItems.ITEMS.registerItem("molten_red_sand_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_RED_SAND.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFFbb6520));
    public static final BaseFlowingFluid.Properties MOLTEN_RED_SAND_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_RED_SAND_FLUID_TYPE, SOURCE_MOLTEN_RED_SAND, FLOWING_MOLTEN_RED_SAND)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_RED_SAND_BLOCK).bucket(ModFluids.MOLTEN_RED_SAND_BUCKET);

    // Moss
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_MOSS = FLUIDS.register("source_molten_moss",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_MOSS_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_MOSS = FLUIDS.register("flowing_molten_moss",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_MOSS_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_MOSS_BLOCK = ModBlocks.BLOCKS.register("molten_moss_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_MOSS.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_MOSS_BUCKET = ModItems.ITEMS.registerItem("molten_moss_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_MOSS.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF4a6029));
    public static final BaseFlowingFluid.Properties MOLTEN_MOSS_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_MOSS_FLUID_TYPE, SOURCE_MOLTEN_MOSS, FLOWING_MOLTEN_MOSS)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_MOSS_BLOCK).bucket(ModFluids.MOLTEN_MOSS_BUCKET);

    // Deepslate
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_DEEPSLATE = FLUIDS.register("source_molten_deepslate",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_DEEPSLATE_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_DEEPSLATE = FLUIDS.register("flowing_molten_deepslate",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_DEEPSLATE_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_DEEPSLATE_BLOCK = ModBlocks.BLOCKS.register("molten_deepslate_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_DEEPSLATE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_DEEPSLATE_BUCKET = ModItems.ITEMS.registerItem("molten_deepslate_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_DEEPSLATE.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF3c3c42));
    public static final BaseFlowingFluid.Properties MOLTEN_DEEPSLATE_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_DEEPSLATE_FLUID_TYPE, SOURCE_MOLTEN_DEEPSLATE, FLOWING_MOLTEN_DEEPSLATE)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_DEEPSLATE_BLOCK).bucket(ModFluids.MOLTEN_DEEPSLATE_BUCKET);

    // Granite
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_GRANITE = FLUIDS.register("source_molten_granite",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_GRANITE_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_GRANITE = FLUIDS.register("flowing_molten_granite",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_GRANITE_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_GRANITE_BLOCK = ModBlocks.BLOCKS.register("molten_granite_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_GRANITE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_GRANITE_BUCKET = ModItems.ITEMS.registerItem("molten_granite_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_GRANITE.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF835949));
    public static final BaseFlowingFluid.Properties MOLTEN_GRANITE_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_GRANITE_FLUID_TYPE, SOURCE_MOLTEN_GRANITE, FLOWING_MOLTEN_GRANITE)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_GRANITE_BLOCK).bucket(ModFluids.MOLTEN_GRANITE_BUCKET);

    // Diorite
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_DIORITE = FLUIDS.register("source_molten_diorite",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_DIORITE_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_DIORITE = FLUIDS.register("flowing_molten_diorite",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_DIORITE_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_DIORITE_BLOCK = ModBlocks.BLOCKS.register("molten_diorite_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_DIORITE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_DIORITE_BUCKET = ModItems.ITEMS.registerItem("molten_diorite_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_DIORITE.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFFadacad));
    public static final BaseFlowingFluid.Properties MOLTEN_DIORITE_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_DIORITE_FLUID_TYPE, SOURCE_MOLTEN_DIORITE, FLOWING_MOLTEN_DIORITE)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_DIORITE_BLOCK).bucket(ModFluids.MOLTEN_DIORITE_BUCKET);

    // Calcite
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_CALCITE = FLUIDS.register("source_molten_calcite",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_CALCITE_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_CALCITE = FLUIDS.register("flowing_molten_calcite",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_CALCITE_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_CALCITE_BLOCK = ModBlocks.BLOCKS.register("molten_calcite_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_CALCITE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_CALCITE_BUCKET = ModItems.ITEMS.registerItem("molten_calcite_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_CALCITE.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFFe9e9e3));
    public static final BaseFlowingFluid.Properties MOLTEN_CALCITE_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_CALCITE_FLUID_TYPE, SOURCE_MOLTEN_CALCITE, FLOWING_MOLTEN_CALCITE)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_CALCITE_BLOCK).bucket(ModFluids.MOLTEN_CALCITE_BUCKET);

    // Tuff
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_TUFF = FLUIDS.register("source_molten_tuff",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_TUFF_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_TUFF = FLUIDS.register("flowing_molten_tuff",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_TUFF_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_TUFF_BLOCK = ModBlocks.BLOCKS.register("molten_tuff_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_TUFF.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_TUFF_BUCKET = ModItems.ITEMS.registerItem("molten_tuff_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_TUFF.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF55564c));
    public static final BaseFlowingFluid.Properties MOLTEN_TUFF_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_TUFF_FLUID_TYPE, SOURCE_MOLTEN_TUFF, FLOWING_MOLTEN_TUFF)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_TUFF_BLOCK).bucket(ModFluids.MOLTEN_TUFF_BUCKET);

    // Dripstone
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_DRIPSTONE = FLUIDS.register("source_molten_dripstone",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_DRIPSTONE_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_DRIPSTONE = FLUIDS.register("flowing_molten_dripstone",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_DRIPSTONE_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_DRIPSTONE_BLOCK = ModBlocks.BLOCKS.register("molten_dripstone_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_DRIPSTONE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_DRIPSTONE_BUCKET = ModItems.ITEMS.registerItem("molten_dripstone_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_DRIPSTONE.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF806155));
    public static final BaseFlowingFluid.Properties MOLTEN_DRIPSTONE_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_DRIPSTONE_FLUID_TYPE, SOURCE_MOLTEN_DRIPSTONE, FLOWING_MOLTEN_DRIPSTONE)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_DRIPSTONE_BLOCK).bucket(ModFluids.MOLTEN_DRIPSTONE_BUCKET);

    // Prismarine
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_PRISMARINE = FLUIDS.register("source_molten_prismarine",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_PRISMARINE_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_PRISMARINE = FLUIDS.register("flowing_molten_prismarine",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_PRISMARINE_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_PRISMARINE_BLOCK = ModBlocks.BLOCKS.register("molten_prismarine_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_PRISMARINE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_PRISMARINE_BUCKET = ModItems.ITEMS.registerItem("molten_prismarine_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_PRISMARINE.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF529584));
    public static final BaseFlowingFluid.Properties MOLTEN_PRISMARINE_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_PRISMARINE_FLUID_TYPE, SOURCE_MOLTEN_PRISMARINE, FLOWING_MOLTEN_PRISMARINE)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_PRISMARINE_BLOCK).bucket(ModFluids.MOLTEN_PRISMARINE_BUCKET);

    // Magma
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_MAGMA = FLUIDS.register("source_molten_magma",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_MAGMA_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_MAGMA = FLUIDS.register("flowing_molten_magma",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_MAGMA_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_MAGMA_BLOCK = ModBlocks.BLOCKS.register("molten_magma_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_MAGMA.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_MAGMA_BUCKET = ModItems.ITEMS.registerItem("molten_magma_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_MAGMA.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF561f1f));
    public static final BaseFlowingFluid.Properties MOLTEN_MAGMA_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_MAGMA_FLUID_TYPE, SOURCE_MOLTEN_MAGMA, FLOWING_MOLTEN_MAGMA)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_MAGMA_BLOCK).bucket(ModFluids.MOLTEN_MAGMA_BUCKET);

    // Obsidian
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_OBSIDIAN = FLUIDS.register("source_molten_obsidian",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_OBSIDIAN_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_OBSIDIAN = FLUIDS.register("flowing_molten_obsidian",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_OBSIDIAN_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_OBSIDIAN_BLOCK = ModBlocks.BLOCKS.register("molten_obsidian_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_OBSIDIAN.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_OBSIDIAN_BUCKET = ModItems.ITEMS.registerItem("molten_obsidian_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_OBSIDIAN.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF030106));
    public static final BaseFlowingFluid.Properties MOLTEN_OBSIDIAN_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_OBSIDIAN_FLUID_TYPE, SOURCE_MOLTEN_OBSIDIAN, FLOWING_MOLTEN_OBSIDIAN)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_OBSIDIAN_BLOCK).bucket(ModFluids.MOLTEN_OBSIDIAN_BUCKET);

    // Netherrack
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_NETHERRACK = FLUIDS.register("source_molten_netherrack",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_NETHERRACK_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_NETHERRACK = FLUIDS.register("flowing_molten_netherrack",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_NETHERRACK_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_NETHERRACK_BLOCK = ModBlocks.BLOCKS.register("molten_netherrack_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_NETHERRACK.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_NETHERRACK_BUCKET = ModItems.ITEMS.registerItem("molten_netherrack_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_NETHERRACK.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF763535));
    public static final BaseFlowingFluid.Properties MOLTEN_NETHERRACK_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_NETHERRACK_FLUID_TYPE, SOURCE_MOLTEN_NETHERRACK, FLOWING_MOLTEN_NETHERRACK)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_NETHERRACK_BLOCK).bucket(ModFluids.MOLTEN_NETHERRACK_BUCKET);

    // Soul_Sand
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_SOUL_SAND = FLUIDS.register("source_molten_soul_sand",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_SOUL_SAND_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_SOUL_SAND = FLUIDS.register("flowing_molten_soul_sand",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_SOUL_SAND_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_SOUL_SAND_BLOCK = ModBlocks.BLOCKS.register("molten_soul_sand_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_SOUL_SAND.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_SOUL_SAND_BUCKET = ModItems.ITEMS.registerItem("molten_soul_sand_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_SOUL_SAND.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF413127));
    public static final BaseFlowingFluid.Properties MOLTEN_SOUL_SAND_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_SOUL_SAND_FLUID_TYPE, SOURCE_MOLTEN_SOUL_SAND, FLOWING_MOLTEN_SOUL_SAND)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_SOUL_SAND_BLOCK).bucket(ModFluids.MOLTEN_SOUL_SAND_BUCKET);

    // Soul_Soil
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_SOUL_SOIL = FLUIDS.register("source_molten_soul_soil",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_SOUL_SOIL_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_SOUL_SOIL = FLUIDS.register("flowing_molten_soul_soil",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_SOUL_SOIL_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_SOUL_SOIL_BLOCK = ModBlocks.BLOCKS.register("molten_soul_soil_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_SOUL_SOIL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_SOUL_SOIL_BUCKET = ModItems.ITEMS.registerItem("molten_soul_soil_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_SOUL_SOIL.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF392b23));
    public static final BaseFlowingFluid.Properties MOLTEN_SOUL_SOIL_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_SOUL_SOIL_FLUID_TYPE, SOURCE_MOLTEN_SOUL_SOIL, FLOWING_MOLTEN_SOUL_SOIL)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_SOUL_SOIL_BLOCK).bucket(ModFluids.MOLTEN_SOUL_SOIL_BUCKET);

    // Blackstone
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_BLACKSTONE = FLUIDS.register("source_molten_blackstone",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_BLACKSTONE_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_BLACKSTONE = FLUIDS.register("flowing_molten_blackstone",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_BLACKSTONE_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_BLACKSTONE_BLOCK = ModBlocks.BLOCKS.register("molten_blackstone_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_BLACKSTONE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_BLACKSTONE_BUCKET = ModItems.ITEMS.registerItem("molten_blackstone_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_BLACKSTONE.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF201819));
    public static final BaseFlowingFluid.Properties MOLTEN_BLACKSTONE_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_BLACKSTONE_FLUID_TYPE, SOURCE_MOLTEN_BLACKSTONE, FLOWING_MOLTEN_BLACKSTONE)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_BLACKSTONE_BLOCK).bucket(ModFluids.MOLTEN_BLACKSTONE_BUCKET);

    // Basalt
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_BASALT = FLUIDS.register("source_molten_basalt",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_BASALT_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_BASALT = FLUIDS.register("flowing_molten_basalt",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_BASALT_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_BASALT_BLOCK = ModBlocks.BLOCKS.register("molten_basalt_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_BASALT.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_BASALT_BUCKET = ModItems.ITEMS.registerItem("molten_basalt_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_BASALT.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF565456));
    public static final BaseFlowingFluid.Properties MOLTEN_BASALT_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_BASALT_FLUID_TYPE, SOURCE_MOLTEN_BASALT, FLOWING_MOLTEN_BASALT)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_BASALT_BLOCK).bucket(ModFluids.MOLTEN_BASALT_BUCKET);

    // Endstone
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_ENDSTONE = FLUIDS.register("source_molten_endstone",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_ENDSTONE_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_ENDSTONE = FLUIDS.register("flowing_molten_endstone",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_ENDSTONE_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_ENDSTONE_BLOCK = ModBlocks.BLOCKS.register("molten_endstone_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_ENDSTONE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_ENDSTONE_BUCKET = ModItems.ITEMS.registerItem("molten_endstone_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_ENDSTONE.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFFcece8e));
    public static final BaseFlowingFluid.Properties MOLTEN_ENDSTONE_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_ENDSTONE_FLUID_TYPE, SOURCE_MOLTEN_ENDSTONE, FLOWING_MOLTEN_ENDSTONE)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_ENDSTONE_BLOCK).bucket(ModFluids.MOLTEN_ENDSTONE_BUCKET);

    // Quartz
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_QUARTZ = FLUIDS.register("source_molten_quartz",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_QUARTZ_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_QUARTZ = FLUIDS.register("flowing_molten_quartz",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_QUARTZ_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_QUARTZ_BLOCK = ModBlocks.BLOCKS.register("molten_quartz_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_QUARTZ.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_QUARTZ_BUCKET = ModItems.ITEMS.registerItem("molten_quartz_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_QUARTZ.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFFe4ddd3));
    public static final BaseFlowingFluid.Properties MOLTEN_QUARTZ_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_QUARTZ_FLUID_TYPE, SOURCE_MOLTEN_QUARTZ, FLOWING_MOLTEN_QUARTZ)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_QUARTZ_BLOCK).bucket(ModFluids.MOLTEN_QUARTZ_BUCKET);

    // Glowstone
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_GLOWSTONE = FLUIDS.register("source_molten_glowstone",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_GLOWSTONE_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_GLOWSTONE = FLUIDS.register("flowing_molten_glowstone",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_GLOWSTONE_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_GLOWSTONE_BLOCK = ModBlocks.BLOCKS.register("molten_glowstone_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_GLOWSTONE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_GLOWSTONE_BUCKET = ModItems.ITEMS.registerItem("molten_glowstone_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_GLOWSTONE.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF784e27));
    public static final BaseFlowingFluid.Properties MOLTEN_GLOWSTONE_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_GLOWSTONE_FLUID_TYPE, SOURCE_MOLTEN_GLOWSTONE, FLOWING_MOLTEN_GLOWSTONE)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_GLOWSTONE_BLOCK).bucket(ModFluids.MOLTEN_GLOWSTONE_BUCKET);

    // Amethyst
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_AMETHYST = FLUIDS.register("source_molten_amethyst",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_AMETHYST_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_AMETHYST = FLUIDS.register("flowing_molten_amethyst",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_AMETHYST_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_AMETHYST_BLOCK = ModBlocks.BLOCKS.register("molten_amethyst_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_AMETHYST.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_AMETHYST_BUCKET = ModItems.ITEMS.registerItem("molten_amethyst_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_AMETHYST.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF6b4da5));
    public static final BaseFlowingFluid.Properties MOLTEN_AMETHYST_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_AMETHYST_FLUID_TYPE, SOURCE_MOLTEN_AMETHYST, FLOWING_MOLTEN_AMETHYST)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_AMETHYST_BLOCK).bucket(ModFluids.MOLTEN_AMETHYST_BUCKET);

    // Brown_Mushroom
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_BROWN_MUSHROOM = FLUIDS.register("source_molten_brown_mushroom",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_BROWN_MUSHROOM_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_BROWN_MUSHROOM = FLUIDS.register("flowing_molten_brown_mushroom",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_BROWN_MUSHROOM_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_BROWN_MUSHROOM_BLOCK = ModBlocks.BLOCKS.register("molten_brown_mushroom_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_BROWN_MUSHROOM.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_BROWN_MUSHROOM_BUCKET = ModItems.ITEMS.registerItem("molten_brown_mushroom_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_BROWN_MUSHROOM.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF967251));
    public static final BaseFlowingFluid.Properties MOLTEN_BROWN_MUSHROOM_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_BROWN_MUSHROOM_FLUID_TYPE, SOURCE_MOLTEN_BROWN_MUSHROOM, FLOWING_MOLTEN_BROWN_MUSHROOM)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_BROWN_MUSHROOM_BLOCK).bucket(ModFluids.MOLTEN_BROWN_MUSHROOM_BUCKET);

    // Red_Mushroom
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_RED_MUSHROOM = FLUIDS.register("source_molten_red_mushroom",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_RED_MUSHROOM_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_RED_MUSHROOM = FLUIDS.register("flowing_molten_red_mushroom",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_RED_MUSHROOM_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_RED_MUSHROOM_BLOCK = ModBlocks.BLOCKS.register("molten_red_mushroom_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_RED_MUSHROOM.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_RED_MUSHROOM_BUCKET = ModItems.ITEMS.registerItem("molten_red_mushroom_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_RED_MUSHROOM.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFFc02624));
    public static final BaseFlowingFluid.Properties MOLTEN_RED_MUSHROOM_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_RED_MUSHROOM_FLUID_TYPE, SOURCE_MOLTEN_RED_MUSHROOM, FLOWING_MOLTEN_RED_MUSHROOM)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_RED_MUSHROOM_BLOCK).bucket(ModFluids.MOLTEN_RED_MUSHROOM_BUCKET);

    // Cactus
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_CACTUS = FLUIDS.register("source_molten_cactus",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_CACTUS_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_CACTUS = FLUIDS.register("flowing_molten_cactus",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_CACTUS_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_CACTUS_BLOCK = ModBlocks.BLOCKS.register("molten_cactus_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_CACTUS.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_CACTUS_BUCKET = ModItems.ITEMS.registerItem("molten_cactus_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_CACTUS.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF476d21));
    public static final BaseFlowingFluid.Properties MOLTEN_CACTUS_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_CACTUS_FLUID_TYPE, SOURCE_MOLTEN_CACTUS, FLOWING_MOLTEN_CACTUS)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_CACTUS_BLOCK).bucket(ModFluids.MOLTEN_CACTUS_BUCKET);

    // Coal
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_COAL = FLUIDS.register("source_molten_coal",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_COAL_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_COAL = FLUIDS.register("flowing_molten_coal",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_COAL_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_COAL_BLOCK = ModBlocks.BLOCKS.register("molten_coal_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_COAL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_COAL_BUCKET = ModItems.ITEMS.registerItem("molten_coal_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_COAL.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF3b3d3b));
    public static final BaseFlowingFluid.Properties MOLTEN_COAL_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_COAL_FLUID_TYPE, SOURCE_MOLTEN_COAL, FLOWING_MOLTEN_COAL)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_COAL_BLOCK).bucket(ModFluids.MOLTEN_COAL_BUCKET);

    // Gravel
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_GRAVEL = FLUIDS.register("source_molten_gravel",
            () -> new BaseFlowingFluid.Source(ModFluids.MOLTEN_GRAVEL_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_GRAVEL = FLUIDS.register("flowing_molten_gravel",
            () -> new BaseFlowingFluid.Flowing(ModFluids.MOLTEN_GRAVEL_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_GRAVEL_BLOCK = ModBlocks.BLOCKS.register("molten_gravel_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_GRAVEL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> MOLTEN_GRAVEL_BUCKET = ModItems.ITEMS.registerItem("molten_gravel_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_GRAVEL.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF4a444b));
    public static final BaseFlowingFluid.Properties MOLTEN_GRAVEL_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_GRAVEL_FLUID_TYPE, SOURCE_MOLTEN_GRAVEL, FLOWING_MOLTEN_GRAVEL)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_GRAVEL_BLOCK).bucket(ModFluids.MOLTEN_GRAVEL_BUCKET);

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
