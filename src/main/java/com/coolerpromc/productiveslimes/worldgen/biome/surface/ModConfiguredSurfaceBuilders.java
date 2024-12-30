package com.coolerpromc.productiveslimes.worldgen.biome.surface;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;

public class ModConfiguredSurfaceBuilders {
    public static ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> SLIMY_LAND = register("slimy_land", new ConfiguredSurfaceBuilder<>(ModSurfaceBuilders.SLIMY_LAND, new SurfaceBuilderBaseConfiguration(
            ModBlocks.SLIMY_GRASS_BLOCK.get().defaultBlockState(),
            ModBlocks.SLIMY_DIRT.get().defaultBlockState(),
            ModBlocks.SLIMY_STONE.get().defaultBlockState())));

    private static <C extends SurfaceBuilderBaseConfiguration, F extends ConfiguredSurfaceBuilder<C>> F register(String key, F builder)
    {
        return Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, new ResourceLocation(ProductiveSlimes.MODID, key), builder);
    }
}
