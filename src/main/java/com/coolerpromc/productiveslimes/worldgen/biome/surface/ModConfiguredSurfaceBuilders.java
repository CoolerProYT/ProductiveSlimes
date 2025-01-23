package com.coolerpromc.productiveslimes.worldgen.biome.surface;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class ModConfiguredSurfaceBuilders {
    public static ConfiguredSurfaceBuilder<SurfaceBuilderConfig> SLIMY_LAND = register("slimy_land", new ConfiguredSurfaceBuilder<>(ModSurfaceBuilders.SLIMY_LAND, new SurfaceBuilderConfig(
            ModBlocks.SLIMY_GRASS_BLOCK.get().defaultBlockState(),
            ModBlocks.SLIMY_DIRT.get().defaultBlockState(),
            ModBlocks.SLIMY_STONE.get().defaultBlockState())));

    private static <C extends SurfaceBuilderConfig, F extends ConfiguredSurfaceBuilder<C>> F register(String key, F builder)
    {
        return Registry.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, new ResourceLocation(ProductiveSlimes.MODID, key), builder);
    }
}
