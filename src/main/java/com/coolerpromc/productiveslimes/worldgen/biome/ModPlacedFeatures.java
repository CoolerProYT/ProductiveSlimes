package com.coolerpromc.productiveslimes.worldgen.biome;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {
    public static ResourceKey<PlacedFeature> SLIMY_TREE = registerKey("slimy_tree");
    public static ResourceKey<PlacedFeature> LAKE_MOLTEN_DIRT = registerKey("lake_molten_dirt");
    public static ResourceKey<PlacedFeature> LAKE_MOLTEN_STONE = registerKey("lake_molten_stone");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatureGetter = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, ModPlacedFeatures.SLIMY_TREE, configuredFeatureGetter.getOrThrow(ModConfiguredFeatures.SLIMY_TREE), VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.005f, 1), ModBlocks.SLIMY_SAPLING.get()));
        register(context, ModPlacedFeatures.LAKE_MOLTEN_DIRT, configuredFeatureGetter.getOrThrow(ModConfiguredFeatures.LAKE_MOLTEN_DIRT), List.of(
                RarityFilter.onAverageOnceEvery(200),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG)
        ));
        register(context, ModPlacedFeatures.LAKE_MOLTEN_STONE, configuredFeatureGetter.getOrThrow(ModConfiguredFeatures.LAKE_MOLTEN_STONE), List.of(
                RarityFilter.onAverageOnceEvery(250),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG)
        ));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, name));
    }

    protected static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> placedFeatureKey, Holder<ConfiguredFeature<?, ?>> configuredFeature, List<PlacementModifier> modifiers) {
        context.register(placedFeatureKey, new PlacedFeature(configuredFeature, modifiers));
    }
}