package com.coolerpromc.productiveslimes.worldgen.biome;

import net.minecraft.world.level.biome.Climate;

public class BiomeRegister {
    public static void init(){
        OverworldBiomeInjector.registerBiome(
                ModBiomes.SLIMY_LAND,
                new Climate.ParameterPoint(
                        Climate.Parameter.span(0.1F, 0.8F),
                        Climate.Parameter.span(0.2F, 0.7F),
                        Climate.Parameter.span(-0.19F, 0.4F),
                        Climate.Parameter.span(-0.22F, 0.55F),
                        Climate.Parameter.span(0.0F, 0.0F),
                        Climate.Parameter.span(-0.56F, 0.56F),
                        0L
                ),
                7
        );

    }
}
