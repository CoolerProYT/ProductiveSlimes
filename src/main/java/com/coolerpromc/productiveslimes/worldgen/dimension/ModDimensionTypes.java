package com.coolerpromc.productiveslimes.worldgen.dimension;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.OptionalLong;

public class ModDimensionTypes {
    public static final ResourceKey<DimensionType> SLIMY_WORLD = ResourceKey.create(Registries.DIMENSION_TYPE, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "slimy_world"));

    public static void boostrap(BootstrapContext<DimensionType> context){
        context.register(SLIMY_WORLD, slimyWorld(context));
    }

    public static DimensionType slimyWorld(BootstrapContext<DimensionType> bootstrap){
        return new DimensionType(
                OptionalLong.of(6000),
                true,
                false,
                false,
                true,
                1.0,
                true,
                true,
                -64,
                384,
                256,
                BlockTags.INFINIBURN_OVERWORLD,
                ResourceLocation.parse("minecraft:overworld"),
                0,
                new DimensionType.MonsterSettings(
                        false,
                        false,
                        UniformInt.of(0, 0),
                        0
                )
        );
    }
}
