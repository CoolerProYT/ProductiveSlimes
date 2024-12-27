package com.coolerpromc.productiveslimes.fluid;

import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import org.joml.Vector3f;

import java.awt.*;

public class ModFluids {
    public static void registerTierFluids() {
        ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
        ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
        ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");

        for (Tier tier : Tier.values()) {
            ModTiers tiers = ModTierLists.getTierByName(tier);
            String name = tiers.name();
            Color colorObject = new Color(tiers.color());
            Vector3f FOG_COLOR = new Vector3f(colorObject.getRed()/255F, colorObject.getGreen()/255F, colorObject.getBlue()/255F);

            ModFluidResources.FluidStuff fluidStuff = ModFluidResources.register(() -> ModFluidResources.addFluid(
                    new ModBaseFluidType.FunkyFluidInfo(tiers.name(), tiers.color(), 0.1F, 1.5F, true), BlockBehaviour.Properties.copy(Blocks.WATER).mapColor(MapColor.byId(tiers.mapColorId())),
                    ((properties, funkyFluidInfo) -> new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, tiers.color(), FOG_COLOR, properties)),
                    (supplier, properties) -> new LiquidBlock(supplier.get(), properties),
                    properties -> properties.explosionResistance(1000F).tickRate(20),
                    FluidType.Properties.create().canExtinguish(true).supportsBoating(true).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).canHydrate(true).viscosity(3000).motionScale(0.007D)));
            ModTierLists.addRegisteredFluidType(name, fluidStuff.getType());
            ModTierLists.addRegisteredLiquidBlock(name, fluidStuff.getBlock());
            ModTierLists.addRegisteredBucketItem(name, fluidStuff.getBucket());
            ModTierLists.addRegisteredFlow(name, fluidStuff.getFlow());
            ModTierLists.addRegisteredSource(name, fluidStuff.getSource());
        }
    }
}
