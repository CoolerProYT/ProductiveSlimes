package com.coolerpromc.productiveslimes.worldgen.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPatternRegistry;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;

public class SlimyVillagePools {
    public static final JigsawPattern START;

    public SlimyVillagePools() {
    }

    static {
        // Register the village center/start pool
        START = JigsawPatternRegistry.register(new JigsawPattern(
                new ResourceLocation("productiveslimes:slimy_village/village_center"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                        Pair.of(JigsawPiece.legacy("productiveslimes:slimy_village/village_center/village_center"), 1)
                ),
                JigsawPattern.PlacementBehaviour.RIGID
        ));

        JigsawPatternRegistry.register(new JigsawPattern(
                new ResourceLocation("productiveslimes:slimy_village/streets"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                        Pair.of(JigsawPiece.legacy("minecraft:empty_pool_element"), 3),
                        Pair.of(JigsawPiece.legacy("productiveslimes:slimy_village/streets/straight_path"), 3),
                        Pair.of(JigsawPiece.legacy("productiveslimes:slimy_village/streets/t_path"), 2),
                        Pair.of(JigsawPiece.legacy("productiveslimes:slimy_village/streets/corner_path"), 2),
                        Pair.of(JigsawPiece.legacy("productiveslimes:slimy_village/streets/cross_path"), 1)
                ),
                JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING
        ));

        JigsawPatternRegistry.register(new JigsawPattern(
                new ResourceLocation("productiveslimes:slimy_village/buildings"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                        Pair.of(JigsawPiece.legacy("productiveslimes:slimy_village/houses/small_village_house"), 10),
                        Pair.of(JigsawPiece.legacy("productiveslimes:slimy_village/houses/slimy_profession_house"), 2),
                        Pair.of(JigsawPiece.legacy("productiveslimes:slimy_village/houses/slimy_armorer_house"), 4),
                        Pair.of(JigsawPiece.legacy("productiveslimes:slimy_village/houses/slimy_fletcher_house"), 4),
                        Pair.of(JigsawPiece.legacy("productiveslimes:slimy_village/houses/slimy_farm"), 2)
                ),
                JigsawPattern.PlacementBehaviour.RIGID
        ));
    }

    public static void bootstrap() {
        // Called during mod initialization
    }
}