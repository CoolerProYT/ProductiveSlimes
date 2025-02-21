package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.FluidTags;

import java.util.concurrent.CompletableFuture;

public class ModFluidTagsProvider extends FluidTagsProvider {
    public ModFluidTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider) {super(pOutput, pProvider, ProductiveSlimes.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        var fluidTag = tag(FluidTags.WATER);

        for (Tier tier : Tier.values()){
            ModTiers tiers = ModTierLists.getTierByName(tier);
            fluidTag.add(ModTierLists.getSourceByName(tiers.name()).get());
            fluidTag.add(ModTierLists.getFlowByName(tiers.name()).get());
        }
    }
}
