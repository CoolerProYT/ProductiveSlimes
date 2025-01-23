package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraft.data.TagsProvider;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModFluidTagsProvider extends FluidTagsProvider {
    public ModFluidTagsProvider(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, ProductiveSlimes.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        TagsProvider.Builder<Fluid> fluidTag = tag(FluidTags.WATER);
        for(Tier tier : Tier.values()){
            ModTiers modTiers = ModTierLists.getTierByName(tier);
            fluidTag.add(ModTierLists.getSourceByName(modTiers.name()).get());
            fluidTag.add(ModTierLists.getFlowByName(modTiers.name()).get());
        }
    }
}
