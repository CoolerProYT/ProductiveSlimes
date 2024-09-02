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


    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
