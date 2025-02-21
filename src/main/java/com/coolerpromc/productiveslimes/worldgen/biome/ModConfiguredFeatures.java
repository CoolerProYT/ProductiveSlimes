package com.coolerpromc.productiveslimes.worldgen.biome;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.fluid.ModFluids;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.Tier;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?,?>> SLIMY_TREE = registerKey("slimy_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> LAKE_MOLTEN_DIRT = registerKey("lake_molten_dirt");
    public static final ResourceKey<ConfiguredFeature<?,?>> LAKE_MOLTEN_STONE = registerKey("lake_molten_stone");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context){
        register(context, ModConfiguredFeatures.SLIMY_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.SLIMY_LOG.get()),
                new FancyTrunkPlacer(4, 4, 3),
                BlockStateProvider.simple(ModBlocks.SLIMY_LEAVES.get()),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(3), 3),
                new TwoLayersFeatureSize(1, 0, 2)
        ).dirt(BlockStateProvider.simple(ModBlocks.SLIMY_DIRT.get())).build());

        register(context, ModConfiguredFeatures.LAKE_MOLTEN_DIRT, Feature.LAKE, new LakeFeature.Configuration(BlockStateProvider.simple(ModTierLists.getLiquidBlockByName(Tier.DIRT.getTierName()).get().defaultBlockState()), BlockStateProvider.simple(ModBlocks.SLIMY_DIRT.get().defaultBlockState())));
        register(context, ModConfiguredFeatures.LAKE_MOLTEN_STONE, Feature.LAKE, new LakeFeature.Configuration(BlockStateProvider.simple(ModTierLists.getLiquidBlockByName(Tier.STONE.getTierName()).get().defaultBlockState()), BlockStateProvider.simple(ModBlocks.SLIMY_DIRT.get().defaultBlockState())));
    }

    private static ResourceKey<ConfiguredFeature<?,?>> registerKey(String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(ProductiveSlimes.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureKey, F feature, FC configuration)
    {
        context.register(configuredFeatureKey, new ConfiguredFeature<>(feature, configuration));
    }
}
