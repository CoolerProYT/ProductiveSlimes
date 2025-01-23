package com.coolerpromc.productiveslimes.worldgen.biome;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.Tier;
import com.coolerpromc.productiveslimes.worldgen.biome.surface.ModConfiguredSurfaceBuilders;
import com.coolerpromc.productiveslimes.worldgen.structure.ModConfiguredStructures;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomes {
    public static final DeferredRegister<Biome> BIOME = DeferredRegister.create(ForgeRegistries.BIOMES, ProductiveSlimes.MODID);

    public static final RegistryObject<Biome> SLIMY_LAND = BIOME.register("slimy_land", ModBiomes::slimeLand);

    public static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder) {
        DefaultBiomeFeatures.addSurfaceFreezing(builder);
        DefaultBiomeFeatures.addDefaultOres(builder);
        DefaultBiomeFeatures.addDefaultCarvers(builder);
        DefaultBiomeFeatures.addDefaultMonsterRoom(builder);
        DefaultBiomeFeatures.addDefaultUndergroundVariety(builder);
    }

    private static Biome slimeLand(){
        MobSpawnInfo.Builder spawnBuilder = new MobSpawnInfo.Builder();

        spawnBuilder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SLIME, 2, 1, 1));
        spawnBuilder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.BAT, 100, 1, 1));
        spawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModTierLists.getEntityByName(Tier.DIRT.getTierName()).get(), 100, 1, 1));
        spawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModTierLists.getEntityByName(Tier.STONE.getTierName()).get(), 65, 1, 1));
        spawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModTierLists.getEntityByName(Tier.IRON.getTierName()).get(), 10, 1, 1));

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder();

        biomeBuilder.surfaceBuilder(ModConfiguredSurfaceBuilders.SLIMY_LAND);
        biomeBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SLIMY_TREE);
        biomeBuilder.addStructureStart(ModConfiguredStructures.SLIMY_VILLAGE);

        globalOverworldGeneration(biomeBuilder);

        return new Biome.Builder()
                .precipitation(Biome.RainType.RAIN)
                .biomeCategory(Biome.Category.PLAINS)
                .depth(0.125F)
                .scale(0.05F)
                .temperature(0.8f)
                .downfall(0.4f)
                .specialEffects((new BiomeAmbience.Builder())
                        .fogColor(0xFFFFFF)
                        .waterColor(0x254788)
                        .waterFogColor(0x2b1b05)
                        .skyColor(0x6EB1FF)
                        .foliageColorOverride(0x00FF00)
                        .grassColorOverride(0x00FF00)
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build())
                .mobSpawnSettings(spawnBuilder.build())
                .generationSettings(biomeBuilder.build())
                .build();
    }

    public static void register(IEventBus eventBus) {
        BIOME.register(eventBus);
    }
}
