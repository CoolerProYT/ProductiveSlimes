package com.coolerpromc.productiveslimes.worldgen.tree;

import com.coolerpromc.productiveslimes.worldgen.biome.ModConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class ModTreeGrowers extends AbstractTreeGrower {
    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random randomSource, boolean b) {
        return ModConfiguredFeatures.SLIMY_TREE.getHolder().get();
    }
}