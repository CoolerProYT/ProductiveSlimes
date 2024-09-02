package com.coolerpromc.productiveslimes.fluid;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BucketItem;
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
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_DIRT.get(), properties.craftRemainder(Items.BUCKET).stacksTo(1)));
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
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_STONE.get(), properties.craftRemainder(Items.BUCKET).stacksTo(1)));
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
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_COPPER.get(), properties.craftRemainder(Items.BUCKET).stacksTo(1)));
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
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_IRON.get(), properties.craftRemainder(Items.BUCKET).stacksTo(1)));
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
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_GOLD.get(), properties.craftRemainder(Items.BUCKET).stacksTo(1)));
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
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_DIAMOND.get(), properties.craftRemainder(Items.BUCKET).stacksTo(1)));
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
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_NETHERITE.get(), properties.craftRemainder(Items.BUCKET).stacksTo(1)));
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
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_LAPIS.get(), properties.craftRemainder(Items.BUCKET).stacksTo(1)));
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
            properties -> new BucketItem(ModFluids.SOURCE_MOLTEN_REDSTONE.get(), properties.craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final BaseFlowingFluid.Properties MOLTEN_REDSTONE_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_REDSTONE_FLUID_TYPE, SOURCE_MOLTEN_REDSTONE, FLOWING_MOLTEN_REDSTONE)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.MOLTEN_REDSTONE_BLOCK).bucket(ModFluids.MOLTEN_REDSTONE_BUCKET);

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
