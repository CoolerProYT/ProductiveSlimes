package com.coolerpromc.productiveslimes.worldgen.biome;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.Tier;
import com.coolerpromc.productiveslimes.worldgen.biome.surface.ModConfiguredSurfaceBuilders;
import com.coolerpromc.productiveslimes.worldgen.structure.ModConfiguredStructures;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomes {
    public static final DeferredRegister<Biome> BIOME = DeferredRegister.create(ForgeRegistries.BIOMES, ProductiveSlimes.MODID);

    public static final RegistryObject<Biome> SLIMY_LAND = BIOME.register("slimy_land", ModBiomes::slimeLand);

    public static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
        BiomeDefaultFeatures.addDefaultOres(builder);
        BiomeDefaultFeatures.addDefaultCarvers(builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
    }

    private static Biome slimeLand(){
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        spawnBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 2, 1, 1));
        spawnBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.BAT, 100, 1, 1));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModTierLists.getEntityByName(Tier.DIRT.getTierName()).get(), 100, 1, 1));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModTierLists.getEntityByName(Tier.STONE.getTierName()).get(), 65, 1, 1));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModTierLists.getEntityByName(Tier.IRON.getTierName()).get(), 10, 1, 1));

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder();

        biomeBuilder.surfaceBuilder(ModConfiguredSurfaceBuilders.SLIMY_LAND);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SLIMY_TREE);
        biomeBuilder.addStructureStart(ModConfiguredStructures.SLIMY_VILLAGE);

        globalOverworldGeneration(biomeBuilder);

        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.RAIN)
                .biomeCategory(Biome.BiomeCategory.PLAINS)
                .depth(0.125F)
                .scale(0.05F)
                .temperature(0.8f)
                .downfall(0.4f)
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .fogColor(0xFFFFFF)
                        .waterColor(0x254788)
                        .waterFogColor(0x2b1b05)
                        .skyColor(0x6EB1FF)
                        .foliageColorOverride(0x00FF00)
                        .grassColorOverride(0x00FF00)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build())
                .mobSpawnSettings(spawnBuilder.build())
                .generationSettings(biomeBuilder.build())
                .build();
    }

    public static void register(IEventBus eventBus) {
        BIOME.register(eventBus);
    }
}
