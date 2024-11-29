package com.coolerpromc.productiveslimes.worldgen.biome;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.fluid.ModFluids;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?,?>> LAKE_MOLTEN_DIRT = registerKey("lake_molten_dirt");
    public static final ResourceKey<ConfiguredFeature<?,?>> LAKE_MOLTEN_STONE = registerKey("lake_molten_stone");
    public static final ResourceKey<ConfiguredFeature<?,?>> SLIME_TREE = registerKey("slime_tree");
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context){
        register(context, ModConfiguredFeatures.LAKE_MOLTEN_DIRT, Feature.LAKE, new LakeFeature.Configuration(BlockStateProvider.simple(ModFluids.MOLTEN_DIRT_BLOCK.get().defaultBlockState()), BlockStateProvider.simple(ModBlocks.SLIMY_DIRT.get().defaultBlockState())));
        register(context, ModConfiguredFeatures.LAKE_MOLTEN_STONE, Feature.LAKE, new LakeFeature.Configuration(BlockStateProvider.simple(ModFluids.MOLTEN_STONE_BLOCK.get().defaultBlockState()), BlockStateProvider.simple(ModBlocks.SLIMY_DIRT.get().defaultBlockState())));
        register(context, ModConfiguredFeatures.SLIME_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.OAK_SLIME_BLOCK.get()),
                new DarkOakTrunkPlacer(7, 2, 2),
                BlockStateProvider.simple(ModBlocks.OAK_LEAVES_SLIME_BLOCK.get()),
                new DarkOakFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                new TwoLayersFeatureSize(1, 0, 1)
        ).dirt(BlockStateProvider.simple(ModBlocks.SLIMY_DIRT.get())).build());
    }
    private static ResourceKey<ConfiguredFeature<?,?>> registerKey(String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, name));
    }
    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureKey, F feature, FC configuration)
    {
        context.register(configuredFeatureKey, new ConfiguredFeature<>(feature, configuration));
    }
}