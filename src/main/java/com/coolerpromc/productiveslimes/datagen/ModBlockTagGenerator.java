package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ProductiveSlimes.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.DIRT)
                .add(ModBlocks.SLIMY_GRASS_BLOCK.get())
                .add(ModBlocks.SLIMY_DIRT.get());

        this.tag(BlockTags.STONE_ORE_REPLACEABLES)
                .add(ModBlocks.SLIMY_STONE.get());

        this.tag(BlockTags.DEEPSLATE_ORE_REPLACEABLES)
                .add(ModBlocks.SLIMY_DEEPSLATE.get());

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
                .add(ModBlocks.SLIMY_COBBLESTONE_WALL.get())
                .add(ModBlocks.SLIMY_COBBLED_DEEPSLATE_WALL.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.MELTING_STATION.get())
                .add(ModBlocks.LIQUID_SOLIDING_STATION.get())
                .add(ModBlocks.ENERGY_GENERATOR.get())
                .add(ModBlocks.DNA_EXTRACTOR.get())
                .add(ModBlocks.DNA_SYNTHESIZER.get())
                .add(ModBlocks.SLIME_SQUEEZER.get())
                .add(ModBlocks.FLUID_TANK.get())
                .add(ModBlocks.CABLE.get())
                .add(ModBlocks.SLIME_NEST.get())
                .add(ModBlocks.SLIMEBALL_COLLECTOR.get())
                .add(ModBlocks.SLIMY_STONE.get())
                .add(ModBlocks.SLIMY_DEEPSLATE.get())
                .add(ModBlocks.SLIMY_COBBLESTONE.get())
                .add(ModBlocks.SLIMY_COBBLED_DEEPSLATE.get());

        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.SLIMY_LOG.get())
                .add(ModBlocks.SLIMY_WOOD.get())
                .add(ModBlocks.STRIPPED_SLIMY_LOG.get())
                .add(ModBlocks.STRIPPED_SLIMY_WOOD.get());

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ModBlocks.SLIMY_GRASS_BLOCK.get())
                .add(ModBlocks.SLIMY_DIRT.get());

        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.MELTING_STATION.get())
                .add(ModBlocks.LIQUID_SOLIDING_STATION.get())
                .add(ModBlocks.DNA_EXTRACTOR.get())
                .add(ModBlocks.DNA_SYNTHESIZER.get())
                .add(ModBlocks.ENERGY_GENERATOR.get())
                .add(ModBlocks.FLUID_TANK.get())
                .add(ModBlocks.SLIME_SQUEEZER.get())
                .add(ModBlocks.CABLE.get())
                .add(ModBlocks.SLIME_NEST.get())
                .add(ModBlocks.SLIMEBALL_COLLECTOR.get());

        this.tag(Tags.Blocks.NEEDS_NETHERITE_TOOL);
//                .add(ModBlocks.ALUMINUM_BLOCK.get());
    }
}
