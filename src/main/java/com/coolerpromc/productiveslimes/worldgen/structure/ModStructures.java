package com.coolerpromc.productiveslimes.worldgen.structure;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.worldgen.biome.ModBiomes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ModStructures {
    public static final ResourceKey<Structure> SLIMY_VILLAGE = ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "slimy_village"));

    public static void bootstrap(BootstrapContext<Structure> context){
        context.register(SLIMY_VILLAGE, slimyVillage(context));
    }

    public static Structure slimyVillage(BootstrapContext<Structure> context){
        HolderGetter<Biome> biomeSettings = context.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> templatePoolSettings = context.lookup(Registries.TEMPLATE_POOL);

        return new JigsawStructure(
                new Structure.StructureSettings(
                        HolderSet.direct(biomeSettings.getOrThrow(ModBiomes.SLIMY_LAND)),
                        new HashMap<>(),
                        GenerationStep.Decoration.SURFACE_STRUCTURES,
                        TerrainAdjustment.BEARD_THIN
                ),
                templatePoolSettings.getOrThrow(ModStructureTemplatePools.VILLAGE_CENTER),
                Optional.empty(),
                7,
                ConstantHeight.of(VerticalAnchor.absolute(0)),
                false,
                Optional.of(Heightmap.Types.WORLD_SURFACE_WG),
                116,
                List.of(),
                DimensionPadding.ZERO,
                LiquidSettings.APPLY_WATERLOGGING
        );
    }
}
