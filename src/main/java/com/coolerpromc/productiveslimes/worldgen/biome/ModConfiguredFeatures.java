package com.coolerpromc.productiveslimes.worldgen.biome;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.Tier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;

public class ModConfiguredFeatures {
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> SLIMY_TREE = register("slimy_tree", Feature.TREE.configured(
            new BaseTreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(ModBlocks.SLIMY_LOG.get().defaultBlockState()),
                    new SimpleBlockStateProvider(ModBlocks.SLIMY_LEAVES.get().defaultBlockState()),
                    new FancyFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(3), 3),
                    new FancyTrunkPlacer(4, 4, 3),
                    new TwoLayerFeature(1, 0, 2)
            ).ignoreVines().build())
    );

    public static final ConfiguredFeature<?, ?> MOLTEN_DIRT_LAKE = register("molten_dirt_lake", Feature.LAKE.configured(new BlockStateFeatureConfig(
            ModTierLists.getLiquidBlockByName(Tier.DIRT.getTierName()).get().defaultBlockState())
    ).count(10).squared());

    private static <FC extends IFeatureConfig>ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, name, configuredFeature);
    }
}
