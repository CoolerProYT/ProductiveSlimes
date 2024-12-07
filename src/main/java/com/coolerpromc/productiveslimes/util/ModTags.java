package com.coolerpromc.productiveslimes.util;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(new ResourceLocation(ProductiveSlimes.MODID, name));
        }
    }
    public static class Items {
        public static final TagKey<Item> DNA_ITEM = createTag("dna_item");
        public static final TagKey<Item> ATM_DNA_ITEM = createTag("atm_dna_item");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(new ResourceLocation(ProductiveSlimes.MODID, name));
        }
    }
}
