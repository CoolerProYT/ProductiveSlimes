package com.coolerpromc.productiveslimes.worldgen.biome;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;

public class ModTerrablender {
    public static void registerBiomes() {
        Regions.register(new ModOverworldRegion(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "overworld"), 5));
    }
}
