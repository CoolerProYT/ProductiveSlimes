package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.fluid.ModFluids;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.FluidTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModFluidTagsProvider extends FluidTagsProvider {
    public ModFluidTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider,
                                @Nullable ExistingFileHelper existingFileHelper) {super(pOutput, pProvider, ProductiveSlimes.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(FluidTags.WATER)
                .add(ModFluids.SOURCE_MOLTEN_DIRT.get())
                .add(ModFluids.FLOWING_MOLTEN_DIRT.get())
                .add(ModFluids.SOURCE_MOLTEN_STONE.get())
                .add(ModFluids.FLOWING_MOLTEN_STONE.get())
                .add(ModFluids.SOURCE_MOLTEN_IRON.get())
                .add(ModFluids.FLOWING_MOLTEN_IRON.get())
                .add(ModFluids.SOURCE_MOLTEN_GOLD.get())
                .add(ModFluids.FLOWING_MOLTEN_GOLD.get())
                .add(ModFluids.SOURCE_MOLTEN_COPPER.get())
                .add(ModFluids.FLOWING_MOLTEN_COPPER.get())
                .add(ModFluids.SOURCE_MOLTEN_DIAMOND.get())
                .add(ModFluids.FLOWING_MOLTEN_DIAMOND.get())
                .add(ModFluids.SOURCE_MOLTEN_NETHERITE.get())
                .add(ModFluids.FLOWING_MOLTEN_NETHERITE.get())
                .add(ModFluids.SOURCE_MOLTEN_LAPIS.get())
                .add(ModFluids.FLOWING_MOLTEN_LAPIS.get())
                .add(ModFluids.SOURCE_MOLTEN_REDSTONE.get())
                .add(ModFluids.FLOWING_MOLTEN_REDSTONE.get())
                .add(ModFluids.SOURCE_MOLTEN_OAK.get())
                .add(ModFluids.FLOWING_MOLTEN_COAL.get()).add(ModFluids.SOURCE_MOLTEN_COAL.get())
                .add(ModFluids.FLOWING_MOLTEN_COAL.get());
    }
}
