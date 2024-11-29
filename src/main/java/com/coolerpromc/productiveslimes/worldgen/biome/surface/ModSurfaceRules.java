package com.coolerpromc.productiveslimes.worldgen.biome.surface;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.fluid.ModFluidTypes;
import com.coolerpromc.productiveslimes.fluid.ModFluids;
import com.coolerpromc.productiveslimes.worldgen.biome.ModBiomes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import terrablender.api.SurfaceRuleManager;

public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource SLIMY_GRASS_BLOCK = makeStateRule(ModBlocks.SLIMY_GRASS_BLOCK.get());
    private static final SurfaceRules.RuleSource SLIMY_DIRT = makeStateRule(ModBlocks.SLIMY_DIRT.get());
    private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
    private static final SurfaceRules.RuleSource SLIMY_STONE = makeStateRule(ModBlocks.SLIMY_STONE.get());
    private static final SurfaceRules.RuleSource SLIMY_DEEPSLATE = makeStateRule(ModBlocks.SLIMY_DEEPSLATE.get());

    public static SurfaceRules.RuleSource makeRules() {
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient(
                                "minecraft:bedrock_floor",
                                VerticalAnchor.aboveBottom(0),
                                VerticalAnchor.aboveBottom(5)
                        ),
                        BEDROCK
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(ModBiomes.SLIME_LAND),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(
                                        SurfaceRules.abovePreliminarySurface(),
                                        SurfaceRules.sequence(
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.stoneDepthCheck(0, false, CaveSurface.FLOOR),
                                                        SLIMY_GRASS_BLOCK
                                                ),
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.stoneDepthCheck(1, true, 5, CaveSurface.FLOOR),
                                                        SLIMY_DIRT
                                                )
                                        )
                                ),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.yStartCheck(VerticalAnchor.absolute(0), 5),
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.stoneDepthCheck(5, true, 80, CaveSurface.FLOOR),
                                                SLIMY_STONE
                                        )
                                ),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.stoneDepthCheck(80, true, 256, CaveSurface.FLOOR),
                                        SLIMY_DEEPSLATE
                                )
                        )
                )
        );

    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
