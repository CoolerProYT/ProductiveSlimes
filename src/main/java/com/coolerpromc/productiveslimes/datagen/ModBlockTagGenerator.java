package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ProductiveSlimes.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.MELTING_STATION.get())
                .add(ModBlocks.LIQUID_SOLIDING_STATION.get())
                .add(ModBlocks.ENERGY_GENERATOR.get())
                .add(ModBlocks.DNA_EXTRACTOR.get())
                .add(ModBlocks.DNA_SYNTHESIZER.get())
                .add(ModBlocks.CABLE.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.MELTING_STATION.get())
                .add(ModBlocks.LIQUID_SOLIDING_STATION.get())
                .add(ModBlocks.DNA_EXTRACTOR.get())
                .add(ModBlocks.DNA_SYNTHESIZER.get())
                .add(ModBlocks.ENERGY_GENERATOR.get())
                .add(ModBlocks.CABLE.get());

        this.tag(Tags.Blocks.NEEDS_NETHERITE_TOOL);
//                .add(ModBlocks.ALUMINUM_BLOCK.get());
    }
}
