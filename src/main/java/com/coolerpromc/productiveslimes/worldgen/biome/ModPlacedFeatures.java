package com.coolerpromc.productiveslimes.worldgen.biome;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModPlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, ProductiveSlimes.MODID);

    public static final RegistryObject<PlacedFeature> SLIMY_TREE = PLACED_FEATURES.register("slimy_tree",
            () -> new PlacedFeature(ModConfiguredFeatures.SLIMY_TREE.getHolder().get(), VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.005f, 1), ModBlocks.SLIMY_SAPLING.get())));

    public static void register(IEventBus eventBus) {
        PLACED_FEATURES.register(eventBus);
    }
}
