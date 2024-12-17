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
        public static final TagKey<Block> SLIMY_LOGS = createTag("slimy_logs");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, name));
        }
    }
    public static class Items {
        public static final TagKey<Item> DNA_ITEM = createTag("dna_item");
        public static final TagKey<Item> SLIMY_LOG = createTag("slimy_log");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, name));
        }
    }
}
