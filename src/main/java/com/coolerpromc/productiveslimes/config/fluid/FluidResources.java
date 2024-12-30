package com.coolerpromc.productiveslimes.config.fluid;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.item.custom.BucketItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class FluidResources {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ProductiveSlimes.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ProductiveSlimes.MODID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, ProductiveSlimes.MODID);

    public static List<FluidStuff> fluidList = new ArrayList<FluidStuff>();

    public static FluidStuff addFluid(String localizedName, ModBaseFluidType.FunkyFluidInfo info, Block.Properties properties, BiFunction<Supplier<? extends FlowingFluid>, BlockBehaviour.Properties, LiquidBlock> block, Function<ForgeFlowingFluid.Properties, ForgeFlowingFluid.Source> source, Function<ForgeFlowingFluid.Properties, ForgeFlowingFluid.Flowing> flowing, @Nullable Consumer<ForgeFlowingFluid.Properties> fluidProperties) {
        FluidStuff fluid = new FluidStuff(info.name, localizedName, info.color,info.isTranslucent, block, fluidProperties, source, flowing, properties);
        fluidList.add(fluid);
        return fluid;
    }

    public static FluidStuff addFluid(String localizedName, ModBaseFluidType.FunkyFluidInfo info, Block.Properties properties, BiFunction<Supplier<? extends FlowingFluid>, BlockBehaviour.Properties, LiquidBlock> block, @Nullable Consumer<ForgeFlowingFluid.Properties> fluidProperties) {
        return addFluid(localizedName, info, properties, block, ForgeFlowingFluid.Source::new, ForgeFlowingFluid.Flowing::new, fluidProperties);
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);
        FLUIDS.register(modEventBus);
    }

    public static FluidStuff register(Supplier<FluidStuff> fluidStuffSupplier){
        return fluidStuffSupplier.get();
    }

    public static class FluidStuff {

        public final ForgeFlowingFluid.Properties PROPERTIES;

        public final Supplier<ForgeFlowingFluid.Source> FLUID;
        public final Supplier<ForgeFlowingFluid.Flowing> FLUID_FLOW;
        public final Supplier<LiquidBlock> FLUID_BLOCK;

        public final RegistryObject<Item> FLUID_BUCKET;

        public final String name;
        public final String localizedName;
        public final int color;
        public final boolean isTranslucent;

        public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
        public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
        public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");


        public FluidStuff(String name, String localizedName, int color,boolean isTranslucent, BiFunction<Supplier<? extends FlowingFluid>, BlockBehaviour.Properties, LiquidBlock> block, @Nullable Consumer<ForgeFlowingFluid.Properties> fluidProperties, Function<ForgeFlowingFluid.Properties, ForgeFlowingFluid.Source> source, Function<ForgeFlowingFluid.Properties, ForgeFlowingFluid.Flowing> flowing, Block.Properties properties) {
            this.name = name;
            this.localizedName = localizedName;
            this.color = color;
            this.isTranslucent = isTranslucent;

            FLUID = FLUIDS.register(name, () -> source.apply(getFluidProperties()));
            FLUID_FLOW = FLUIDS.register("flowing_" + name, () -> flowing.apply(getFluidProperties()));

            PROPERTIES = new ForgeFlowingFluid.Properties(FLUID, FLUID_FLOW, FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL).color(color).overlay(WATER_OVERLAY_RL).luminosity(2).density(15).viscosity(5).sound(SoundEvents.BUCKET_FILL))
                    .slopeFindDistance(2).levelDecreasePerBlock(2);
            if (fluidProperties != null)
                fluidProperties.accept(PROPERTIES);

            FLUID_BLOCK = BLOCKS.register("molten_" + name + "_block", () -> block.apply(FLUID, properties.randomTicks().strength(100.0F).noDrops()));
            FLUID_BUCKET = ITEMS.register("molten_" + name + "_bucket", () -> new BucketItem(FLUID, new BucketItem.Properties().craftRemainder(Items.BUCKET).stacksTo(64).tab(CreativeModeTab.TAB_MISC), color));

            PROPERTIES.bucket(FLUID_BUCKET).block(FLUID_BLOCK);
        }

        public ForgeFlowingFluid.Properties getFluidProperties() {
            return PROPERTIES;
        }
    }
}