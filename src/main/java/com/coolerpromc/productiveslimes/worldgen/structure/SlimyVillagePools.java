package com.coolerpromc.productiveslimes.worldgen.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.structures.StructurePoolElement;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;

public class SlimyVillagePools {
    public static final StructureTemplatePool START;

    public SlimyVillagePools() {
    }

    static {
        // Register the village center/start pool
        START = Pools.register(new StructureTemplatePool(
                new ResourceLocation("productiveslimes:slimy_village/village_center"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                        Pair.of(StructurePoolElement.legacy("productiveslimes:slimy_village/village_center/village_center"), 1)
                ),
                StructureTemplatePool.Projection.RIGID
        ));

        Pools.register(new StructureTemplatePool(
                new ResourceLocation("productiveslimes:slimy_village/streets"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                        Pair.of(StructurePoolElement.legacy("minecraft:empty_pool_element"), 3),
                        Pair.of(StructurePoolElement.legacy("productiveslimes:slimy_village/streets/straight_path"), 3),
                        Pair.of(StructurePoolElement.legacy("productiveslimes:slimy_village/streets/t_path"), 2),
                        Pair.of(StructurePoolElement.legacy("productiveslimes:slimy_village/streets/corner_path"), 2),
                        Pair.of(StructurePoolElement.legacy("productiveslimes:slimy_village/streets/cross_path"), 1)
                ),
                StructureTemplatePool.Projection.TERRAIN_MATCHING
        ));

        Pools.register(new StructureTemplatePool(
                new ResourceLocation("productiveslimes:slimy_village/buildings"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                        Pair.of(StructurePoolElement.legacy("productiveslimes:slimy_village/houses/small_village_house"), 10),
                        Pair.of(StructurePoolElement.legacy("productiveslimes:slimy_village/houses/slimy_profession_house"), 2),
                        Pair.of(StructurePoolElement.legacy("productiveslimes:slimy_village/houses/slimy_armorer_house"), 4),
                        Pair.of(StructurePoolElement.legacy("productiveslimes:slimy_village/houses/slimy_fletcher_house"), 4),
                        Pair.of(StructurePoolElement.legacy("productiveslimes:slimy_village/houses/slime_statue"), 1),
                        Pair.of(StructurePoolElement.legacy("productiveslimes:slimy_village/houses/slimy_farm"), 2)
                ),
                StructureTemplatePool.Projection.RIGID
        ));
    }

    public static void bootstrap() {
        // Called during mod initialization
    }
}