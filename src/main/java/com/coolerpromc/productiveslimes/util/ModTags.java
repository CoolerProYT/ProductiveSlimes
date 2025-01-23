package com.coolerpromc.productiveslimes.util;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public class ModTags {
    public static class Blocks {
        public static final Tags.IOptionalNamedTag<Block> SLIMY_LOGS = createTag("slimy_logs");

        private static Tags.IOptionalNamedTag<Block> createTag(String name) {
            return BlockTags.createOptional(new ResourceLocation(ProductiveSlimes.MODID, name));
        }
    }
    public static class Items {
        public static final Tags.IOptionalNamedTag<Item> DNA_ITEM = createTag("dna_item");
        public static final Tags.IOptionalNamedTag<Item> SLIMY_LOG = createTag("slimy_log");

        private static Tags.IOptionalNamedTag<Item> createTag(String name) {
            return ItemTags.createOptional(new ResourceLocation(ProductiveSlimes.MODID, name));
        }
    }
}
