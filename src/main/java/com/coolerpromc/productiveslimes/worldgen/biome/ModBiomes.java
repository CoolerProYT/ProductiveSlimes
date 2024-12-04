package com.coolerpromc.productiveslimes.worldgen.biome;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModBiomes {
    public static final ResourceKey<Biome> SLIME_LAND = ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "slime_land"));

    public static void boostrap(BootstrapContext<Biome> context){
        context.register(SLIME_LAND, slimeLand(context));
    }

    public static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
        BiomeDefaultFeatures.addDefaultOres(builder);
    }


    private static Biome slimeLand(BootstrapContext<Biome> context){
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        spawnBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 10, 1, 1));
        spawnBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.BAT, 100, 1, 1));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModTierLists.getEntityByName("dirt").get(), 100, 1, 1));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModTierLists.getEntityByName("stone").get(), 65, 1, 1));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModTierLists.getEntityByName("iron").get(), 10, 1, 1));

        HolderGetter<PlacedFeature> placedFeatureHolderGetter = context.lookup(Registries.PLACED_FEATURE);

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatureHolderGetter.getOrThrow(ModPlacedFeatures.SLIME_TREE));

        /*biomeBuilder.addFeature(GenerationStep.Decoration.LAKES, placedFeatureHolderGetter.getOrThrow(ModPlacedFeatures.LAKE_MOLTEN_DIRT));
        biomeBuilder.addFeature(GenerationStep.Decoration.LAKES, placedFeatureHolderGetter.getOrThrow(ModPlacedFeatures.LAKE_MOLTEN_STONE));*/


        globalOverworldGeneration(biomeBuilder);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.4f)
                .temperature(0.8f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .fogColor(0xFFFFFF)
                        .waterColor(0x254788)
                        .waterFogColor(0x2b1b05)
                        .skyColor(0x6EB1FF)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build())
                .build();
    }
}