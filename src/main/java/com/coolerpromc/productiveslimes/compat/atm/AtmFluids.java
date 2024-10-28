package com.coolerpromc.productiveslimes.compat.atm;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.fluid.ModFluidTypes;
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

public class AtmFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(BuiltInRegistries.FLUID, ProductiveSlimes.MODID);

    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_ATM = FLUIDS.register("source_molten_atm",
            () -> new BaseFlowingFluid.Source(AtmFluids.MOLTEN_ATM_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_ATM = FLUIDS.register("flowing_molten_atm",
            () -> new BaseFlowingFluid.Flowing(AtmFluids.MOLTEN_ATM_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_ATM_BLOCK = ModBlocks.BLOCKS.register("molten_atm_block",
            () -> new LiquidBlock(AtmFluids.SOURCE_MOLTEN_ATM.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));

    public static final DeferredItem<Item> MOLTEN_ATM_BUCKET = ModItems.ITEMS.registerItem("molten_atm_bucket",
            properties -> new BucketItem(AtmFluids.SOURCE_MOLTEN_ATM.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFFff8b04));
    public static final BaseFlowingFluid.Properties MOLTEN_ATM_PROPERTIES = new BaseFlowingFluid.Properties(
            AtmFluidTypes.MOLTEN_ATM_FLUID_TYPE, SOURCE_MOLTEN_ATM, FLOWING_MOLTEN_ATM)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(AtmFluids.MOLTEN_ATM_BLOCK).bucket(AtmFluids.MOLTEN_ATM_BUCKET);

    //vibranium
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_VIBRANIUM = FLUIDS.register("source_molten_vibranium",
            () -> new BaseFlowingFluid.Source(AtmFluids.MOLTEN_VIBRANIUM_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_VIBRANIUM = FLUIDS.register("flowing_molten_vibranium",
            () -> new BaseFlowingFluid.Flowing(AtmFluids.MOLTEN_VIBRANIUM_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_VIBRANIUM_BLOCK = ModBlocks.BLOCKS.register("molten_vibranium_block",
            () -> new LiquidBlock(AtmFluids.SOURCE_MOLTEN_VIBRANIUM.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));

    public static final DeferredItem<Item> MOLTEN_VIBRANIUM_BUCKET = ModItems.ITEMS.registerItem("molten_vibranium_bucket",
            properties -> new BucketItem(AtmFluids.SOURCE_MOLTEN_VIBRANIUM.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF148484));
    public static final BaseFlowingFluid.Properties MOLTEN_VIBRANIUM_PROPERTIES = new BaseFlowingFluid.Properties(
            AtmFluidTypes.MOLTEN_VIBRANIUM_FLUID_TYPE, SOURCE_MOLTEN_VIBRANIUM, FLOWING_MOLTEN_VIBRANIUM)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(AtmFluids.MOLTEN_VIBRANIUM_BLOCK).bucket(AtmFluids.MOLTEN_VIBRANIUM_BUCKET);

    //unobtainium
    public static final Supplier<FlowingFluid> SOURCE_MOLTEN_UNOBTAINIUM = FLUIDS.register("source_molten_unobtainium",
            () -> new BaseFlowingFluid.Source(AtmFluids.MOLTEN_UNOBTAINIUM_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_MOLTEN_UNOBTAINIUM = FLUIDS.register("flowing_molten_unobtainium",
            () -> new BaseFlowingFluid.Flowing(AtmFluids.MOLTEN_UNOBTAINIUM_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> MOLTEN_UNOBTAINIUM_BLOCK = ModBlocks.BLOCKS.register("molten_unobtainium_block",
            () -> new LiquidBlock(AtmFluids.SOURCE_MOLTEN_UNOBTAINIUM.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));

    public static final DeferredItem<Item> MOLTEN_UNOBTAINIUM_BUCKET = ModItems.ITEMS.registerItem("molten_unobtainium_bucket",
            properties -> new BucketItem(AtmFluids.SOURCE_MOLTEN_UNOBTAINIUM.get(), properties.craftRemainder(Items.BUCKET).stacksTo(64), 0xFF6c1ce4));
    public static final BaseFlowingFluid.Properties MOLTEN_UNOBTAINIUM_PROPERTIES = new BaseFlowingFluid.Properties(
            AtmFluidTypes.MOLTEN_UNOBTAINIUM_FLUID_TYPE, SOURCE_MOLTEN_UNOBTAINIUM, FLOWING_MOLTEN_UNOBTAINIUM)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(AtmFluids.MOLTEN_UNOBTAINIUM_BLOCK).bucket(AtmFluids.MOLTEN_UNOBTAINIUM_BUCKET);

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
