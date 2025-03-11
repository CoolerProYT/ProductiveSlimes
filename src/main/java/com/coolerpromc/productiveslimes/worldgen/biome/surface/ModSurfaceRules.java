package com.coolerpromc.productiveslimes.worldgen.biome.surface;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.worldgen.biome.ModBiomes;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource SLIMY_GRASS_BLOCK = makeStateRule(ModBlocks.SLIMY_GRASS_BLOCK.get());
    private static final SurfaceRules.RuleSource SLIMY_DIRT = makeStateRule(ModBlocks.SLIMY_DIRT.get());
    private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
    private static final SurfaceRules.RuleSource SLIMY_STONE = makeStateRule(ModBlocks.SLIMY_STONE.get());
    private static final SurfaceRules.RuleSource SLIMY_DEEPSLATE = makeStateRule(ModBlocks.SLIMY_DEEPSLATE.get());

    public static SurfaceRules.RuleSource makeRules(boolean aboveGround, boolean bedrockRoof, boolean bedrockFloor) {
        SurfaceRules.ConditionSource waterBlockCheck = SurfaceRules.waterBlockCheck(0, 0);

        // Define common surface rules for both biomes
        SurfaceRules.RuleSource surfaceRulesForBiome = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.abovePreliminarySurface(),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(
                                        SurfaceRules.stoneDepthCheck(0, false, CaveSurface.FLOOR),
                                        SurfaceRules.sequence(SurfaceRules.ifTrue(waterBlockCheck, SLIMY_GRASS_BLOCK), SLIMY_DIRT)
                                ),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.stoneDepthCheck(1, true, 5, CaveSurface.FLOOR),
                                        SLIMY_DIRT
                                )
                        )
                )
        );

        SurfaceRules.RuleSource biomeRules = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(ModBiomes.SLIMY_LAND),
                        surfaceRulesForBiome
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(ModBiomes.SLIMY_OCEAN),
                        surfaceRulesForBiome
                )
        );

        // Build the final rules with bedrock and deepslate transitions
        ImmutableList.Builder<SurfaceRules.RuleSource> builder = ImmutableList.builder();
        if (bedrockRoof) {
            builder.add(
                    SurfaceRules.ifTrue(
                            SurfaceRules.not(SurfaceRules.verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())),
                            BEDROCK
                    )
            );
        }
        if (bedrockFloor) {
            builder.add(
                    SurfaceRules.ifTrue(
                            SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)),
                            BEDROCK
                    )
            );
        }

        // Add the biome-specific rules
        builder.add(biomeRules);

        // Add deepslate transition below y=0
        builder.add(
                SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient("deepslate", VerticalAnchor.absolute(0), VerticalAnchor.absolute(8)),
                        SLIMY_DEEPSLATE
                )
        );

        return SurfaceRules.sequence(builder.build().toArray(SurfaceRules.RuleSource[]::new));
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}