package com.coolerpromc.productiveslimes.worldgen.structure;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;

public class ModConfiguredStructures {
    public static final ConfiguredStructureFeature<?, ?> SLIMY_VILLAGE = Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(ProductiveSlimes.MODID, "slimy_village"),
            StructureFeature.VILLAGE.configured(new JigsawConfiguration(() -> SlimyVillagePools.START, 7))
    );
}
