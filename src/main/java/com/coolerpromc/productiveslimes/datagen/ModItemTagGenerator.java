package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import com.coolerpromc.productiveslimes.util.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(DataGenerator p_275343_, BlockTagsProvider p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275322_, ProductiveSlimes.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        var slimeballTag = tag(Tags.Items.SLIMEBALLS);
        var dnaTag = tag(ModTags.Items.DNA_ITEM);

        slimeballTag.add(ModItems.ENERGY_SLIME_BALL.get());
        dnaTag.add(ModItems.SLIME_DNA.get());

        for(Tier tier : Tier.values()){
            ModTiers modTiers = ModTierLists.getTierByName(tier);
            slimeballTag.add(ModTierLists.getSlimeballItemByName(modTiers.name()).get());
            dnaTag.add(ModTierLists.getDnaItemByName(modTiers.name()).get());
        }

        tag(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.SLIMY_LOG.get().asItem())
                .add(ModBlocks.SLIMY_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_SLIMY_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_SLIMY_WOOD.get().asItem());

        tag(ItemTags.PLANKS)
                .add(ModBlocks.SLIMY_PLANKS.get().asItem());

        tag(ModTags.Items.SLIMY_LOG)
                .add(ModBlocks.SLIMY_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_SLIMY_LOG.get().asItem())
                .add(ModBlocks.SLIMY_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_SLIMY_WOOD.get().asItem());
    }
}
