package com.coolerpromc.productiveslimes.fluid;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.item.custom.BucketItem;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFluids {
    public static void registerTierFluids() {
        for (Tier tier : Tier.values()){
            ModTiers tiers = ModTierLists.getTierByName(tier);

            ModFluidResources.FluidStuff fluidStuff = ModFluidResources.register(() -> ModFluidResources.addFluid(
                    new ModBaseFluidType.FunkyFluidInfo(tiers.name(), tiers.color(), 0.1F, 1.5F, true), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).mapColor(MapColor.byId(tiers.mapColorId())), ((properties, funkyFluidInfo) -> new ModBaseFluidType(properties, funkyFluidInfo, tiers.color())),
                    (supplier, properties) -> new LiquidBlock(supplier.get(), properties.setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "molten_" + tiers.name() + "_block")))),
                    properties -> properties.explosionResistance(1000F).tickRate(20),
                    FluidType.Properties.create().canExtinguish(true).supportsBoating(true).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).canHydrate(true).viscosity(3000).motionScale(0.007D)));

            ModTierLists.addRegisteredFluidType(tiers.name(), fluidStuff.getType());
            ModTierLists.addRegisteredLiquidBlock(tiers.name(), fluidStuff.getBlock());
            ModTierLists.addRegisteredBucketItem(tiers.name(), fluidStuff.getBucket());
            ModTierLists.addRegisteredFlow(tiers.name(), fluidStuff.getFlow());
            ModTierLists.addRegisteredSource(tiers.name(), fluidStuff.getSource());
        }
    }
}
