package com.coolerpromc.productiveslimes.worldgen.biome;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.Tier;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.Features;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.placement.FrequencyWithExtraChanceDecoratorConfiguration;

public class ModConfiguredFeatures {
    public static final ConfiguredFeature<?, ?> SLIMY_TREE = register("slimy_tree", Feature.TREE.configured(
            new TreeConfiguration.TreeConfigurationBuilder(
                    new SimpleStateProvider(ModBlocks.SLIMY_LOG.get().defaultBlockState()),
                    new FancyTrunkPlacer(4, 4, 3),
                    new SimpleStateProvider(ModBlocks.SLIMY_LEAVES.get().defaultBlockState()),
                    new SimpleStateProvider(ModBlocks.SLIMY_SAPLING.get().defaultBlockState()),
                    new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(3), 3),
                    new TwoLayersFeatureSize(1, 0, 2)
            ).dirt(new SimpleStateProvider(ModBlocks.SLIMY_DIRT.get().defaultBlockState())).build()).decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(
                    new FrequencyWithExtraChanceDecoratorConfiguration(2, 0.1F, 1)
            ))
    );

    public static final ConfiguredFeature<?, ?> MOLTEN_DIRT_LAKE = register("molten_dirt_lake", Feature.LAKE.configured(new BlockStateConfiguration(
            ModTierLists.getLiquidBlockByName(Tier.DIRT.getTierName()).get().defaultBlockState())
    ).count(10).squared());

    private static <FC extends FeatureConfiguration>ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, name, configuredFeature);
    }
}
