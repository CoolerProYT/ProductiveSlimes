package com.coolerpromc.productiveslimes.worldgen.structure;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;

public class ModConfiguredStructures {
    public static final StructureFeature<?, ?> SLIMY_VILLAGE = Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(ProductiveSlimes.MODID, "slimy_village"),
            Structure.VILLAGE.configured(new VillageConfig(() -> SlimyVillagePools.START, 6))
    );
}
