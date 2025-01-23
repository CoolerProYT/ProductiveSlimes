package com.coolerpromc.productiveslimes.worldgen.tree;

import com.coolerpromc.productiveslimes.worldgen.biome.ModConfiguredFeatures;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import javax.annotation.Nullable;
import java.util.Random;

public class ModTreeGrowers extends Tree {
    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random randomSource, boolean b) {
        return (ConfiguredFeature<BaseTreeFeatureConfig, ?>) ModConfiguredFeatures.SLIMY_TREE;
    }
}