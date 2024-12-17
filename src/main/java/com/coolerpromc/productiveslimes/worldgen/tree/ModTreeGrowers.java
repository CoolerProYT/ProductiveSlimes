package com.coolerpromc.productiveslimes.worldgen.tree;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.worldgen.biome.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower SLIMY = new TreeGrower(ProductiveSlimes.MODID + ":slimy", Optional.empty(),
            Optional.of(ModConfiguredFeatures.SLIMY_TREE), Optional.empty());
}