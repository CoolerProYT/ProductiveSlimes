package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.worldgen.biome.ModBiomes;
import com.coolerpromc.productiveslimes.worldgen.biome.ModConfiguredFeatures;
import com.coolerpromc.productiveslimes.worldgen.biome.ModPlacedFeatures;
import com.coolerpromc.productiveslimes.worldgen.biome.surface.ModSurfaceRules;
import com.coolerpromc.productiveslimes.worldgen.dimension.ModDimensionTypes;
import com.coolerpromc.productiveslimes.worldgen.dimension.ModDimensions;
import com.coolerpromc.productiveslimes.worldgen.noise.ModNoiseSettings;
import com.coolerpromc.productiveslimes.worldgen.structure.ModStructureSets;
import com.coolerpromc.productiveslimes.worldgen.structure.ModStructureTemplatePools;
import com.coolerpromc.productiveslimes.worldgen.structure.ModStructures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            .add(Registries.NOISE_SETTINGS, ModNoiseSettings::boostrap)
            .add(Registries.BIOME, ModBiomes::boostrap)
            .add(Registries.LEVEL_STEM, ModDimensions::boostrap)
            .add(Registries.DIMENSION_TYPE, ModDimensionTypes::boostrap)
            .add(Registries.STRUCTURE_SET, ModStructureSets::bootstrap)
            .add(Registries.STRUCTURE, ModStructures::bootstrap)
            .add(Registries.TEMPLATE_POOL, ModStructureTemplatePools::bootstrap);

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(ProductiveSlimes.MODID));
    }
}