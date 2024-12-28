package com.coolerpromc.productiveslimes.fluid;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.item.custom.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModFluidResources {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ProductiveSlimes.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ProductiveSlimes.MODID);
    public static final DeferredRegister<FluidType> FLUIDTYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, ProductiveSlimes.MODID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, ProductiveSlimes.MODID);
    public static List<FluidStuff> fluidList = new ArrayList<FluidStuff>();

    public static FluidStuff addFluid(ModBaseFluidType.FunkyFluidInfo info, Block.Properties properties, BiFunction<FluidType.Properties, ModBaseFluidType.FunkyFluidInfo, FluidType> type, BiFunction<Supplier<? extends FlowingFluid>, BlockBehaviour.Properties, LiquidBlock> block, Function<ForgeFlowingFluid.Properties, ForgeFlowingFluid.Source> source, Function<ForgeFlowingFluid.Properties, ForgeFlowingFluid.Flowing> flowing, @Nullable Consumer<ForgeFlowingFluid.Properties> fluidProperties, FluidType.Properties prop) {
        FluidStuff fluid = new FluidStuff(info.name, info.color, info.isTranslucent, type.apply(prop, info), block, fluidProperties, source, flowing, properties);
        fluidList.add(fluid);
        return fluid;
    }

    public static FluidStuff addFluid(ModBaseFluidType.FunkyFluidInfo info, Block.Properties properties, BiFunction<FluidType.Properties, ModBaseFluidType.FunkyFluidInfo, FluidType> type, BiFunction<Supplier<? extends FlowingFluid>, BlockBehaviour.Properties, LiquidBlock> block, @Nullable Consumer<ForgeFlowingFluid.Properties> fluidProperties, FluidType.Properties prop) {
        return addFluid(info, properties, type, block, ForgeFlowingFluid.Source::new, ForgeFlowingFluid.Flowing::new, fluidProperties, prop);
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);
        FLUIDTYPES.register(modEventBus);
        FLUIDS.register(modEventBus);
    }

    public static FluidStuff register(Supplier<FluidStuff> fluidStuffSupplier) {
        return fluidStuffSupplier.get();
    }

    public static class FluidStuff {
        public final ForgeFlowingFluid.Properties PROPERTIES;
        public final Supplier<ForgeFlowingFluid.Source> FLUID;
        public final Supplier<ForgeFlowingFluid.Flowing> FLUID_FLOW;
        public final Supplier<FluidType> TYPE;
        public final Supplier<LiquidBlock> FLUID_BLOCK;
        public final RegistryObject<Item> FLUID_BUCKET;
        public final String name;
        public final int color;
        public final boolean isTranslucent;

        public FluidStuff(String name, int color, boolean isTranslucent, FluidType type, BiFunction<Supplier<? extends FlowingFluid>, BlockBehaviour.Properties, LiquidBlock> block, @Nullable Consumer<ForgeFlowingFluid.Properties> fluidProperties, Function<ForgeFlowingFluid.Properties, ForgeFlowingFluid.Source> source, Function<ForgeFlowingFluid.Properties, ForgeFlowingFluid.Flowing> flowing, Block.Properties properties) {
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
            PROPERTIES = new ForgeFlowingFluid.Properties(TYPE, FLUID, FLUID_FLOW);
            if (fluidProperties != null)
                fluidProperties.accept(PROPERTIES);
            FLUID_BLOCK = BLOCKS.register(fluidBlockName, () -> block.apply(FLUID, properties.lightLevel((state) -> {
                return type.getLightLevel();
            }).randomTicks().strength(100.0F).noLootTable()));
            FLUID_BUCKET = ITEMS.register(fluidBucketName, () -> new BucketItem(FLUID.get(), new BucketItem.Properties().craftRemainder(Items.BUCKET).stacksTo(64), color));
            PROPERTIES.bucket(FLUID_BUCKET).block(FLUID_BLOCK);
        }

        public ForgeFlowingFluid.Properties getFluidProperties() {
            return PROPERTIES;
        }

        public Supplier<FluidType> getType() {
            return TYPE;
        }

        public Supplier<LiquidBlock> getBlock() {
            return FLUID_BLOCK;
        }

        public RegistryObject<Item> getBucket() {
            return FLUID_BUCKET;
        }

        public Supplier<ForgeFlowingFluid.Source> getSource() {
            return FLUID;
        }

        public Supplier<ForgeFlowingFluid.Flowing> getFlow() {
            return FLUID_FLOW;
        }
    }
}