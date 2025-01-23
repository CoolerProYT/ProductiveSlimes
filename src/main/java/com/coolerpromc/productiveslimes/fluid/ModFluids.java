package com.coolerpromc.productiveslimes.fluid;

import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.util.math.vector.Vector3f;

import java.awt.*;

public class ModFluids {
    public static void registerTierFluids() {
        for (Tier tier : Tier.values()) {
            ModTiers tiers = ModTierLists.getTierByName(tier);
            String name = tiers.name();
            Color colorObject = new Color(tiers.color());
            Vector3f FOG_COLOR = new Vector3f(colorObject.getRed()/255F, colorObject.getGreen()/255F, colorObject.getBlue()/255F);

            ModFluidResources.FluidStuff fluidStuff = ModFluidResources.register(() -> ModFluidResources.addFluid(
                    new ModBaseFluidType.FunkyFluidInfo(tiers.name(), tiers.color(), 0.1F, 1.5F, true),
                    AbstractBlock.Properties.copy(Blocks.WATER),
                    FlowingFluidBlock::new,
                    properties -> properties.explosionResistance(1000F).tickRate(20)));
            ModTierLists.addRegisteredLiquidBlock(name, fluidStuff.getBlock());
            ModTierLists.addRegisteredBucketItem(name, fluidStuff.getBucket());
            ModTierLists.addRegisteredFlow(name, fluidStuff.getFlow());
            ModTierLists.addRegisteredSource(name, fluidStuff.getSource());
        }
    }
}
