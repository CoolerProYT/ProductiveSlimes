package com.coolerpromc.productiveslimes.worldgen.biome;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModBiomes {
    public static final ResourceKey<Biome> SLIMY_LAND = ResourceKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(ProductiveSlimes.MODID, "slimy_land"));

    public static void boostrap(BootstrapContext<Biome> context){
        context.register(SLIMY_LAND, slimeLand(context));
    }

    public static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
        BiomeDefaultFeatures.addDefaultOres(builder);
    }

    private static Biome slimeLand(BootstrapContext<Biome> context){
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        spawnBuilder.addSpawn(MobCategory.MONSTER, 10, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 1, 1));
        spawnBuilder.addSpawn(MobCategory.MONSTER, 100, new MobSpawnSettings.SpawnerData(EntityType.BAT, 1, 1));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 100, new MobSpawnSettings.SpawnerData(ModTierLists.getEntityByName("dirt").get(), 1, 1));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 65, new MobSpawnSettings.SpawnerData(ModTierLists.getEntityByName("stone").get(), 1, 1));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(ModTierLists.getEntityByName("iron").get(), 1, 1));

        HolderGetter<PlacedFeature> placedFeatureHolderGetter = context.lookup(Registries.PLACED_FEATURE);

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatureHolderGetter.getOrThrow(ModPlacedFeatures.SLIMY_TREE));

        biomeBuilder.addFeature(GenerationStep.Decoration.LAKES, placedFeatureHolderGetter.getOrThrow(ModPlacedFeatures.LAKE_MOLTEN_DIRT));
        biomeBuilder.addFeature(GenerationStep.Decoration.LAKES, placedFeatureHolderGetter.getOrThrow(ModPlacedFeatures.LAKE_MOLTEN_STONE));

        globalOverworldGeneration(biomeBuilder);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.4f)
                .temperature(0.8f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 0xFFFFFF)
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 0x2b1b05)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 0x6EB1FF)
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0x254788)
                .build()).build();
    }
}