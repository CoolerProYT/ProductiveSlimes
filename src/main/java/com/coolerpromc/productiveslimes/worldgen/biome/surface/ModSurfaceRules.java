package com.coolerpromc.productiveslimes.worldgen.biome.surface;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.worldgen.biome.ModBiomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource SLIMY_GRASS_BLOCK = makeStateRule(ModBlocks.SLIMY_GRASS_BLOCK.get());
    private static final SurfaceRules.RuleSource SLIMY_DIRT = makeStateRule(ModBlocks.SLIMY_DIRT.get());
    private static final SurfaceRules.RuleSource DEEP = makeStateRule(Blocks.BAMBOO_BLOCK);
    private static final SurfaceRules.RuleSource VERY_DEEP = makeStateRule(Blocks.BONE_BLOCK);


    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);

        SurfaceRules.RuleSource grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK));

        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(ModBiomes.SLIME_LAND),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(
                                        SurfaceRules.abovePreliminarySurface(),
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.stoneDepthCheck(0, false, CaveSurface.FLOOR),
                                                SLIMY_GRASS_BLOCK
                                        )
                                ),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.stoneDepthCheck(1, true, 128, CaveSurface.FLOOR),
                                        SLIMY_DIRT
                                )
                        )
                )
        );

    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
