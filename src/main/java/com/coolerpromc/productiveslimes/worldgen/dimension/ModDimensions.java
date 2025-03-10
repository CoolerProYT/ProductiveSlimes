package com.coolerpromc.productiveslimes.worldgen.dimension;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.worldgen.biome.ModBiomes;
import com.coolerpromc.productiveslimes.worldgen.noise.ModNoiseSettings;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.List;

public class ModDimensions {
    public static final ResourceKey<Level> SLIMY_WORLD = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "slimy_world"));
    public static final ResourceKey<LevelStem> SLIMY_WORLD_STEM = ResourceKey.create(Registries.LEVEL_STEM, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "slimy_world"));

    public static void boostrap(BootstrapContext<LevelStem> context){
        context.register(SLIMY_WORLD_STEM, slimyWorld(context));
    }

    public static LevelStem slimyWorld(BootstrapContext<LevelStem> context){
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

        NoiseBasedChunkGenerator wrappedChunkGenerator = new NoiseBasedChunkGenerator(
                MultiNoiseBiomeSource.createFromList(
                        new Climate.ParameterList<>(
                                List.of(
                                        Pair.of(
                                                new Climate.ParameterPoint(
                                                        Climate.Parameter.span(-1.0F, 1.0F),
                                                        Climate.Parameter.span(-1.0F, 1.0F),
                                                        Climate.Parameter.span(-1.0F, -0.1F),
                                                        Climate.Parameter.span(-1.0F, 1.0F),
                                                        Climate.Parameter.point(0.0F),
                                                        Climate.Parameter.span(-1.0F, 1.0F),
                                                        0L
                                                ),
                                               biomeRegistry.getOrThrow(ModBiomes.SLIMY_OCEAN)
                                        ),
                                        Pair.of(
                                                new Climate.ParameterPoint(
                                                        Climate.Parameter.span(-1.0F, 1.0F),
                                                        Climate.Parameter.span(-1.0F, 1.0F),
                                                        Climate.Parameter.span(0.1F, 1.0F),
                                                        Climate.Parameter.span(-1.0F, 1.0F),
                                                        Climate.Parameter.point(0.0F),
                                                        Climate.Parameter.span(-1.0F, 1.0F),
                                                        0L
                                                ),
                                                biomeRegistry.getOrThrow(ModBiomes.SLIMY_LAND)
                                        )
                                )
                        )
                ),
                noiseGenSettings.getOrThrow(ModNoiseSettings.SLIMY_WORLD)
        );

        return new LevelStem(dimTypes.getOrThrow(ModDimensionTypes.SLIMY_WORLD), wrappedChunkGenerator);
    }
}
