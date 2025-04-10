package com.coolerpromc.productiveslimes.worldgen.biome;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModBiomes {
    public static final ResourceKey<Biome> SLIMY_LAND = ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "slimy_land"));
    public static final ResourceKey<Biome> SLIMY_OCEAN = ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "slimy_ocean"));

    public static void boostrap(BootstrapContext<Biome> context){
        context.register(SLIMY_LAND, slimyLand(context));
        context.register(SLIMY_OCEAN, slimyOcean(context));
    }

    private static Biome slimyLand(BootstrapContext<Biome> context){
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        spawnBuilder.addSpawn(MobCategory.MONSTER, 10, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 1, 1));
        spawnBuilder.addSpawn(MobCategory.MONSTER, 100, new MobSpawnSettings.SpawnerData(EntityType.BAT, 1, 1));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 100, new MobSpawnSettings.SpawnerData(ModTiers.getEntityByName("dirt").get(), 1, 1));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 65, new MobSpawnSettings.SpawnerData(ModTiers.getEntityByName("stone").get(), 1, 1));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(ModTiers.getEntityByName("iron").get(), 1, 1));

        HolderGetter<PlacedFeature> placedFeatureHolderGetter = context.lookup(Registries.PLACED_FEATURE);

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatureHolderGetter.getOrThrow(ModPlacedFeatures.SLIMY_TREE));

        biomeBuilder.addFeature(GenerationStep.Decoration.LAKES, placedFeatureHolderGetter.getOrThrow(ModPlacedFeatures.LAKE_MOLTEN_DIRT));
        biomeBuilder.addFeature(GenerationStep.Decoration.LAKES, placedFeatureHolderGetter.getOrThrow(ModPlacedFeatures.LAKE_MOLTEN_STONE));

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.4f)
                .temperature(0.8f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .fogColor(0xFFFFFF)
                        .waterColor(0x69c95d)
                        .waterFogColor(0xb3f28f)
                        .skyColor(0x6EB1FF)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build())
                .build();
    }

    private static Biome slimyOcean(BootstrapContext<Biome> context){
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER)
        );

        return new Biome.BiomeBuilder()
                .temperature(0.5f)
                .downfall(0.5f)
                .hasPrecipitation(true)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .fogColor(0xFFFFFF)
                        .waterColor(0x69c95d)
                        .waterFogColor(0xb3f28f)
                        .skyColor(0x6EB1FF)
                        .build())
                .mobSpawnSettings(MobSpawnSettings.EMPTY)
                .generationSettings(biomeBuilder.build())
                .build();
    }
}