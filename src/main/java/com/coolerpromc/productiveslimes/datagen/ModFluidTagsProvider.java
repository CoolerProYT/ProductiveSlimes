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
                .add(ModFluids.FLOWING_MOLTEN_OAK.get())
                .add(ModFluids.SOURCE_MOLTEN_SAND.get())
                .add(ModFluids.FLOWING_MOLTEN_SAND.get())
                .add(ModFluids.SOURCE_MOLTEN_ANDESITE.get())
                .add(ModFluids.FLOWING_MOLTEN_ANDESITE.get())
                .add(ModFluids.SOURCE_MOLTEN_SNOW.get())
                .add(ModFluids.FLOWING_MOLTEN_SNOW.get())
                .add(ModFluids.SOURCE_MOLTEN_ICE.get())
                .add(ModFluids.FLOWING_MOLTEN_ICE.get())
                .add(ModFluids.SOURCE_MOLTEN_MUD.get())
                .add(ModFluids.FLOWING_MOLTEN_MUD.get())
                .add(ModFluids.SOURCE_MOLTEN_CLAY.get())
                .add(ModFluids.FLOWING_MOLTEN_CLAY.get())
                .add(ModFluids.SOURCE_MOLTEN_RED_SAND.get())
                .add(ModFluids.FLOWING_MOLTEN_RED_SAND.get())
                .add(ModFluids.SOURCE_MOLTEN_MOSS.get())
                .add(ModFluids.FLOWING_MOLTEN_MOSS.get())
                .add(ModFluids.SOURCE_MOLTEN_DEEPSLATE.get())
                .add(ModFluids.FLOWING_MOLTEN_DEEPSLATE.get())
                .add(ModFluids.SOURCE_MOLTEN_GRANITE.get())
                .add(ModFluids.FLOWING_MOLTEN_GRANITE.get())
                .add(ModFluids.SOURCE_MOLTEN_DIORITE.get())
                .add(ModFluids.FLOWING_MOLTEN_DIORITE.get())
                .add(ModFluids.SOURCE_MOLTEN_CALCITE.get())
                .add(ModFluids.FLOWING_MOLTEN_CALCITE.get())
                .add(ModFluids.SOURCE_MOLTEN_TUFF.get())
                .add(ModFluids.FLOWING_MOLTEN_TUFF.get())
                .add(ModFluids.SOURCE_MOLTEN_DRIPSTONE.get())
                .add(ModFluids.FLOWING_MOLTEN_DRIPSTONE.get())
                .add(ModFluids.SOURCE_MOLTEN_PRISMARINE.get())
                .add(ModFluids.FLOWING_MOLTEN_PRISMARINE.get())
                .add(ModFluids.SOURCE_MOLTEN_MAGMA.get())
                .add(ModFluids.FLOWING_MOLTEN_MAGMA.get())
                .add(ModFluids.SOURCE_MOLTEN_OBSIDIAN.get())
                .add(ModFluids.FLOWING_MOLTEN_OBSIDIAN.get())
                .add(ModFluids.SOURCE_MOLTEN_NETHERRACK.get())
                .add(ModFluids.FLOWING_MOLTEN_NETHERRACK.get())
                .add(ModFluids.SOURCE_MOLTEN_SOUL_SAND.get())
                .add(ModFluids.FLOWING_MOLTEN_SOUL_SAND.get())
                .add(ModFluids.SOURCE_MOLTEN_SOUL_SOIL.get())
                .add(ModFluids.FLOWING_MOLTEN_SOUL_SOIL.get())
                .add(ModFluids.SOURCE_MOLTEN_BLACKSTONE.get())
                .add(ModFluids.FLOWING_MOLTEN_BLACKSTONE.get())
                .add(ModFluids.SOURCE_MOLTEN_BASALT.get())
                .add(ModFluids.FLOWING_MOLTEN_BASALT.get())
                .add(ModFluids.SOURCE_MOLTEN_ENDSTONE.get())
                .add(ModFluids.FLOWING_MOLTEN_ENDSTONE.get())
                .add(ModFluids.SOURCE_MOLTEN_QUARTZ.get())
                .add(ModFluids.FLOWING_MOLTEN_QUARTZ.get())
                .add(ModFluids.SOURCE_MOLTEN_GLOWSTONE.get())
                .add(ModFluids.FLOWING_MOLTEN_GLOWSTONE.get())
                .add(ModFluids.SOURCE_MOLTEN_AMETHYST.get())
                .add(ModFluids.FLOWING_MOLTEN_AMETHYST.get())
                .add(ModFluids.SOURCE_MOLTEN_BROWN_MUSHROOM.get())
                .add(ModFluids.FLOWING_MOLTEN_BROWN_MUSHROOM.get())
                .add(ModFluids.SOURCE_MOLTEN_RED_MUSHROOM.get())
                .add(ModFluids.FLOWING_MOLTEN_RED_MUSHROOM.get())
                .add(ModFluids.SOURCE_MOLTEN_CACTUS.get())
                .add(ModFluids.FLOWING_MOLTEN_CACTUS.get())
                .add(ModFluids.SOURCE_MOLTEN_COAL.get())
                .add(ModFluids.FLOWING_MOLTEN_COAL.get());
    }
}
