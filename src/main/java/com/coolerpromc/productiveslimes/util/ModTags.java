package com.coolerpromc.productiveslimes.util;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final Tag.Named<Block> SLIMY_LOGS = createTag("slimy_logs");

        private static Tag.Named<Block> createTag(String name) {
            return BlockTags.createOptional(new ResourceLocation(ProductiveSlimes.MODID, name));
        }
    }
    public static class Items {
        public static final Tag.Named<Item> DNA_ITEM = createTag("dna_item");
        public static final Tag.Named<Item> SLIMY_LOG = createTag("slimy_log");

        private static Tag.Named<Item> createTag(String name) {
            return ItemTags.createOptional(new ResourceLocation(ProductiveSlimes.MODID, name));
        }
    }
}
