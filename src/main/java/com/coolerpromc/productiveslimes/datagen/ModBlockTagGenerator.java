package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.util.ModTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, ProductiveSlimes.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.SLIMY_LOG.get())
                .add(ModBlocks.SLIMY_WOOD.get())
                .add(ModBlocks.STRIPPED_SLIMY_LOG.get())
                .add(ModBlocks.STRIPPED_SLIMY_WOOD.get());
        this.tag(BlockTags.PLANKS)
                .add(ModBlocks.SLIMY_PLANKS.get());
        this.tag(BlockTags.SAPLINGS)
                .add(ModBlocks.SLIMY_SAPLING.get());
        this.tag(BlockTags.LEAVES)
                .add(ModBlocks.SLIMY_LEAVES.get());
        this.tag(BlockTags.LOGS)
                .add(ModBlocks.SLIMY_LOG.get())
                .add(ModBlocks.SLIMY_WOOD.get())
                .add(ModBlocks.STRIPPED_SLIMY_LOG.get())
                .add(ModBlocks.STRIPPED_SLIMY_WOOD.get());

        this.tag(ModTags.Blocks.SLIMY_LOGS)
                .add(ModBlocks.SLIMY_LOG.get())
                .add(ModBlocks.SLIMY_WOOD.get())
                .add(ModBlocks.STRIPPED_SLIMY_LOG.get())
                .add(ModBlocks.STRIPPED_SLIMY_WOOD.get());
        this.tag(BlockTags.FENCES)
                .add(ModBlocks.SLIMY_FENCE.get());
        this.tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.SLIMY_FENCE_GATE.get());
        this.tag(BlockTags.WALLS)
                .add(ModBlocks.SLIMY_COBBLESTONE_WALL.get());
    }
}
