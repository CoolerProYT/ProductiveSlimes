package com.coolerpromc.productiveslimes.worldgen.noise;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.worldgen.biome.surface.ModSurfaceRules;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;

public class ModNoiseSettings {
    public static final ResourceKey<NoiseGeneratorSettings> SLIMY_WORLD = ResourceKey.create(Registries.NOISE_SETTINGS, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "slimy_world"));

    public static void boostrap(BootstrapContext<NoiseGeneratorSettings> context) {
        context.register(SLIMY_WORLD, slimyWorld(context));
    }

    public static NoiseGeneratorSettings slimyWorld(BootstrapContext<NoiseGeneratorSettings> context) {
        HolderGetter<NormalNoise.NoiseParameters> noiseParamSettings = context.lookup(Registries.NOISE);
        HolderGetter<DensityFunction> densityFunctionSettings = context.lookup(Registries.DENSITY_FUNCTION);

        DensityFunction shiftX = new DensityFunctions.HolderHolder(densityFunctionSettings.getOrThrow(NoiseRouterData.SHIFT_X));
        DensityFunction shiftZ = new DensityFunctions.HolderHolder(densityFunctionSettings.getOrThrow(NoiseRouterData.SHIFT_Z));
        DensityFunction continent = new DensityFunctions.HolderHolder(densityFunctionSettings.getOrThrow(NoiseRouterData.CONTINENTS));
        DensityFunction erosion = new DensityFunctions.HolderHolder(densityFunctionSettings.getOrThrow(NoiseRouterData.EROSION));
        DensityFunction depth = new DensityFunctions.HolderHolder(densityFunctionSettings.getOrThrow(NoiseRouterData.DEPTH));
        DensityFunction ridges = new DensityFunctions.HolderHolder(densityFunctionSettings.getOrThrow(NoiseRouterData.RIDGES));
        DensityFunction factor = new DensityFunctions.HolderHolder(densityFunctionSettings.getOrThrow(NoiseRouterData.FACTOR));
        DensityFunction slopeCheese = new DensityFunctions.HolderHolder(densityFunctionSettings.getOrThrow(NoiseRouterData.SLOPED_CHEESE));
        DensityFunction entrances = new DensityFunctions.HolderHolder(densityFunctionSettings.getOrThrow(NoiseRouterData.ENTRANCES));
        DensityFunction spaghetti2d = new DensityFunctions.HolderHolder(densityFunctionSettings.getOrThrow(NoiseRouterData.SPAGHETTI_2D));
        DensityFunction spaghettiRoughness = new DensityFunctions.HolderHolder(densityFunctionSettings.getOrThrow(NoiseRouterData.SPAGHETTI_ROUGHNESS_FUNCTION));
        DensityFunction pillars = new DensityFunctions.HolderHolder(densityFunctionSettings.getOrThrow(NoiseRouterData.PILLARS));
        DensityFunction noodle = new DensityFunctions.HolderHolder(densityFunctionSettings.getOrThrow(NoiseRouterData.NOODLE));
        DensityFunction y = new DensityFunctions.HolderHolder(densityFunctionSettings.getOrThrow(NoiseRouterData.Y));

        return new NoiseGeneratorSettings(
                new NoiseSettings(-64, 384, 1, 2),
                ModBlocks.SLIMY_STONE.get().defaultBlockState(),
                Blocks.WATER.defaultBlockState(),
                new NoiseRouter(
                        DensityFunctions.noise(noiseParamSettings.getOrThrow(Noises.AQUIFER_BARRIER), 1.0, 0.5),
                        DensityFunctions.noise(noiseParamSettings.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_FLOODEDNESS), 1.0, 0.67),
                        DensityFunctions.noise(noiseParamSettings.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_SPREAD), 1.0, 0.7142857142857143),
                        DensityFunctions.noise(noiseParamSettings.getOrThrow(Noises.AQUIFER_LAVA), 1.0, 1.0),
                        DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, noiseParamSettings.getOrThrow(Noises.TEMPERATURE)),
                        DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, noiseParamSettings.getOrThrow(Noises.VEGETATION)),
                        continent,
                        erosion,
                        depth,
                        ridges,
                        DensityFunctions.add(
                                DensityFunctions.constant(0.1171875),
                                DensityFunctions.mul(
                                        DensityFunctions.yClampedGradient(-64, -40, 0.0, 1.0),
                                        DensityFunctions.add(
                                                DensityFunctions.constant(-0.1171875),
                                                DensityFunctions.add(
                                                        DensityFunctions.constant(-0.078125),
                                                        DensityFunctions.mul(
                                                                DensityFunctions.yClampedGradient(240, 256, 1.0, 0.0),
                                                                DensityFunctions.add(
                                                                        DensityFunctions.constant(0.078125),
                                                                        new DensityFunctions.Clamp(
                                                                                DensityFunctions.add(
                                                                                        DensityFunctions.constant(-0.703125),
                                                                                        DensityFunctions.mul(
                                                                                                DensityFunctions.constant(4.0),
                                                                                                DensityFunctions.mul(
                                                                                                        depth,
                                                                                                        DensityFunctions.cache2d(factor)
                                                                                                ).quarterNegative()
                                                                                        )
                                                                                ),
                                                                                -64.0,
                                                                                64
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        ),
                        DensityFunctions.min(
                                DensityFunctions.mul(
                                        DensityFunctions.constant(0.64),
                                        DensityFunctions.interpolated(
                                                DensityFunctions.blendDensity(
                                                        DensityFunctions.add(
                                                                DensityFunctions.constant(0.1171875),
                                                                DensityFunctions.mul(
                                                                        DensityFunctions.yClampedGradient(-64, -40, 0.0, 1.0),
                                                                        DensityFunctions.add(
                                                                                DensityFunctions.constant(-0.1171875),
                                                                                DensityFunctions.add(
                                                                                        DensityFunctions.constant(-0.078125),
                                                                                        DensityFunctions.mul(
                                                                                                DensityFunctions.yClampedGradient(240, 256, 1.0, 0.0),
                                                                                                DensityFunctions.add(
                                                                                                        DensityFunctions.constant(0.078125),
                                                                                                        DensityFunctions.rangeChoice(
                                                                                                                slopeCheese,
                                                                                                                -1000000.0,
                                                                                                                1.5625,
                                                                                                                DensityFunctions.min(
                                                                                                                        slopeCheese,
                                                                                                                        DensityFunctions.mul(
                                                                                                                                DensityFunctions.constant(5.0),
                                                                                                                                entrances
                                                                                                                        )
                                                                                                                ),
                                                                                                                DensityFunctions.max(
                                                                                                                        DensityFunctions.min(
                                                                                                                                DensityFunctions.min(
                                                                                                                                        DensityFunctions.add(
                                                                                                                                                DensityFunctions.mul(
                                                                                                                                                        DensityFunctions.constant(4.0),
                                                                                                                                                        DensityFunctions.noise(
                                                                                                                                                                noiseParamSettings.getOrThrow(Noises.CAVE_LAYER),
                                                                                                                                                                1.0,8.0
                                                                                                                                                        ).square()
                                                                                                                                                ),
                                                                                                                                                DensityFunctions.add(
                                                                                                                                                        DensityFunctions.add(
                                                                                                                                                                DensityFunctions.constant(0.27),
                                                                                                                                                                DensityFunctions.noise(
                                                                                                                                                                        noiseParamSettings.getOrThrow(Noises.CAVE_CHEESE),
                                                                                                                                                                        1.0,
                                                                                                                                                                        0.6666666666666666
                                                                                                                                                                )
                                                                                                                                                        ).clamp(-1.0,1.0),
                                                                                                                                                        DensityFunctions.add(
                                                                                                                                                                DensityFunctions.constant(1.5),
                                                                                                                                                                DensityFunctions.mul(
                                                                                                                                                                        DensityFunctions.constant(-0.64),
                                                                                                                                                                        slopeCheese
                                                                                                                                                                )
                                                                                                                                                        ).clamp(0.0, 0.5)
                                                                                                                                                )
                                                                                                                                        ),
                                                                                                                                        entrances
                                                                                                                                ),
                                                                                                                                DensityFunctions.add(
                                                                                                                                        spaghetti2d,
                                                                                                                                        spaghettiRoughness
                                                                                                                                )
                                                                                                                        ),
                                                                                                                        DensityFunctions.rangeChoice(
                                                                                                                                pillars,
                                                                                                                                -1000000.0,
                                                                                                                                0.03,
                                                                                                                                DensityFunctions.constant(-1000000.0),
                                                                                                                                pillars
                                                                                                                        )
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                ).squeeze(),
                                noodle
                        ),
                        DensityFunctions.interpolated(
                                DensityFunctions.rangeChoice(
                                        y,
                                        -60.0,
                                        51.0,
                                        DensityFunctions.noise(
                                                noiseParamSettings.getOrThrow(Noises.ORE_VEININESS),
                                                1.5,
                                                1.5
                                        ),
                                        DensityFunctions.constant(0.0)
                                )
                        ),
                        DensityFunctions.add(
                                DensityFunctions.constant(-0.07999999821186066),
                                DensityFunctions.max(
                                        DensityFunctions.interpolated(
                                                DensityFunctions.rangeChoice(
                                                        y,
                                                        -60.0,
                                                        51.0,
                                                        DensityFunctions.noise(
                                                                noiseParamSettings.getOrThrow(Noises.ORE_VEIN_A),
                                                                4.0,
                                                                4.0
                                                        ),
                                                        DensityFunctions.constant(0.0)
                                                )
                                        ).abs(),
                                        DensityFunctions.interpolated(
                                                DensityFunctions.rangeChoice(
                                                        y,
                                                        -60.0,
                                                        51.0,
                                                        DensityFunctions.noise(
                                                                noiseParamSettings.getOrThrow(Noises.ORE_VEIN_B),
                                                                4.0,
                                                                4.0
                                                        ),
                                                        DensityFunctions.constant(0.0)
                                                )
                                        ).abs()
                                )
                        ),
                        DensityFunctions.noise(
                                noiseParamSettings.getOrThrow(Noises.ORE_GAP),
                                1.0,
                                1.0
                        )
                ),
                ModSurfaceRules.makeRules(true, false, true),
                List.of(
                        new Climate.ParameterPoint(
                                Climate.Parameter.span(-1.0f, 1.0f),
                                Climate.Parameter.span(-1.0f, 1.0f),
                                Climate.Parameter.span(-0.11f, 1.0f),
                                Climate.Parameter.span(-1.0f, 1.0f),
                                Climate.Parameter.point(0.0f),
                                Climate.Parameter.span(-1.0f, -0.16f),
                                0
                        ),
                        new Climate.ParameterPoint(
                                Climate.Parameter.span(-1.0f, 1.0f),
                                Climate.Parameter.span(-1.0f, 1.0f),
                                Climate.Parameter.span(-0.11f, 1.0f),
                                Climate.Parameter.span(-1.0f, 1.0f),
                                Climate.Parameter.point(0.0f),
                                Climate.Parameter.span(0.16f, 1.0f),
                                0
                        )
                ),
                63,
                true,
                true,
                false,
                false
        );
    }
}
