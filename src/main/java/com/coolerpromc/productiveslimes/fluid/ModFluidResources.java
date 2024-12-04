package com.coolerpromc.productiveslimes.fluid;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.item.custom.BucketItem;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModFluidResources {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, ProductiveSlimes.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ProductiveSlimes.MODID);
    public static final DeferredRegister<FluidType> FLUIDTYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, ProductiveSlimes.MODID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, ProductiveSlimes.MODID);

    public static List<FluidStuff> fluidList = new ArrayList<FluidStuff>();

    public static FluidStuff addFluid(ModBaseFluidType.FunkyFluidInfo info, Block.Properties properties, BiFunction<FluidType.Properties, ModBaseFluidType.FunkyFluidInfo, FluidType> type, BiFunction<Supplier<? extends FlowingFluid>, BlockBehaviour.Properties, LiquidBlock> block, Function<BaseFlowingFluid.Properties, BaseFlowingFluid.Source> source, Function<BaseFlowingFluid.Properties, BaseFlowingFluid.Flowing> flowing, @Nullable Consumer<BaseFlowingFluid.Properties> fluidProperties, FluidType.Properties prop) {
        FluidStuff fluid = new FluidStuff(info.name, info.color,info.isTranslucent, type.apply(prop, info), block, fluidProperties, source, flowing, properties);
        fluidList.add(fluid);
        return fluid;
    }

    public static FluidStuff addFluid(ModBaseFluidType.FunkyFluidInfo info, Block.Properties properties, BiFunction<FluidType.Properties, ModBaseFluidType.FunkyFluidInfo, FluidType> type, BiFunction<Supplier<? extends FlowingFluid>, BlockBehaviour.Properties, LiquidBlock> block, @Nullable Consumer<BaseFlowingFluid.Properties> fluidProperties, FluidType.Properties prop) {
        return addFluid(info, properties, type, block, BaseFlowingFluid.Source::new, BaseFlowingFluid.Flowing::new, fluidProperties, prop);
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);
        FLUIDTYPES.register(modEventBus);
        FLUIDS.register(modEventBus);
    }

    public static FluidStuff register(Supplier<FluidStuff> fluidStuffSupplier){
        return fluidStuffSupplier.get();
    }

    public static class FluidStuff {

        public final BaseFlowingFluid.Properties PROPERTIES;

        public final Supplier<BaseFlowingFluid.Source> FLUID;
        public final Supplier<BaseFlowingFluid.Flowing> FLUID_FLOW;
        public final Supplier<FluidType> TYPE;

        public final Supplier<LiquidBlock> FLUID_BLOCK;

        public final DeferredItem<Item> FLUID_BUCKET;

        public final String name;
        public final int color;
        public final boolean isTranslucent;


        public FluidStuff(String name, int color,boolean isTranslucent, FluidType type, BiFunction<Supplier<? extends FlowingFluid>, BlockBehaviour.Properties, LiquidBlock> block, @Nullable Consumer<BaseFlowingFluid.Properties> fluidProperties, Function<BaseFlowingFluid.Properties, BaseFlowingFluid.Source> source, Function<BaseFlowingFluid.Properties, BaseFlowingFluid.Flowing> flowing, Block.Properties properties) {
            this.name = name;
            this.color = color;
            this.isTranslucent = isTranslucent;

            String sourceName = "source_molten_" + name;
            String flowingName = "flowing_molten_" + name;
            String fluidBlockName = "molten_" + name + "_block";
            String fluidBucketName = "molten_" + name + "_bucket";
            String typeName = "molten_" + name + "_fluid";

            FLUID = FLUIDS.register(sourceName, () -> source.apply(getFluidProperties()));
            FLUID_FLOW = FLUIDS.register(flowingName, () -> flowing.apply(getFluidProperties()));
            TYPE = FLUIDTYPES.register(typeName, () -> type);

            PROPERTIES = new BaseFlowingFluid.Properties(TYPE, FLUID, FLUID_FLOW);
            if (fluidProperties != null)
                fluidProperties.accept(PROPERTIES);

            FLUID_BLOCK = BLOCKS.register(fluidBlockName, () -> block.apply(FLUID, properties.lightLevel((state) -> { return type.getLightLevel(); }).randomTicks().strength(100.0F).noLootTable()));
            FLUID_BUCKET = ITEMS.registerItem(fluidBucketName, properties1 -> new BucketItem(FLUID.get(), properties1.craftRemainder(Items.BUCKET).stacksTo(64), color));

            PROPERTIES.bucket(FLUID_BUCKET).block(FLUID_BLOCK);
        }

        public BaseFlowingFluid.Properties getFluidProperties() {
            return PROPERTIES;
        }

        public Supplier<FluidType> getType() {
            return TYPE;
        }

        public Supplier<LiquidBlock> getBlock() {
            return FLUID_BLOCK;
        }

        public DeferredItem<Item> getBucket() {
            return FLUID_BUCKET;
        }

        public Supplier<BaseFlowingFluid.Source> getSource() {
            return FLUID;
        }

        public Supplier<BaseFlowingFluid.Flowing> getFlow() {
            return FLUID_FLOW;
        }
    }
}