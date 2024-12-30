package com.coolerpromc.productiveslimes.worldgen.tree;

import com.coolerpromc.productiveslimes.worldgen.biome.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import javax.annotation.Nullable;
import java.util.Random;

public class ModTreeGrowers extends AbstractTreeGrower {
    @Nullable
    @Override
    protected ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(Random randomSource, boolean b) {
        return (ConfiguredFeature<TreeConfiguration, ?>) ModConfiguredFeatures.SLIMY_TREE;
    }
}