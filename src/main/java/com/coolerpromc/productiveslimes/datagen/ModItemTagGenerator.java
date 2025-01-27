package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import com.coolerpromc.productiveslimes.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_) {
        super(p_275343_, p_275729_, p_275322_, ProductiveSlimes.MODID, null);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        var slimeballTag = tag(Tags.Items.SLIME_BALLS);

        slimeballTag.add(ModItems.ENERGY_SLIME_BALL.get());

        for (Tier tier : Tier.values()){
            ModTiers tiers = ModTierLists.getTierByName(tier);
            slimeballTag.add(ModTierLists.getSlimeballItemByName(tiers.name()).get());
        }

        var dnaTag = tag(ModTags.Items.DNA_ITEM);
        dnaTag.add(ModItems.SLIME_DNA.get());

        for (Tier tier : Tier.values()){
            ModTiers tiers = ModTierLists.getTierByName(tier);
            dnaTag.add(ModTierLists.getDnaItemByName(tiers.name()).get());
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
