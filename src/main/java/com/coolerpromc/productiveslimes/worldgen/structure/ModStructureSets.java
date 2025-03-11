package com.coolerpromc.productiveslimes.worldgen.structure;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

import java.util.List;

public class ModStructureSets {
    public static final ResourceKey<StructureSet> SLIMY_VILLAGE = ResourceKey.create(Registries.STRUCTURE_SET, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "slimy_village"));

    public static void bootstrap(BootstrapContext<StructureSet> context){
        context.register(SLIMY_VILLAGE, slimyVillage(context));
    }

    public static StructureSet slimyVillage(BootstrapContext<StructureSet> context){
        HolderGetter<Structure> structureSettings = context.lookup(Registries.STRUCTURE);

        return new StructureSet(
                List.of(
                        new StructureSet.StructureSelectionEntry(structureSettings.getOrThrow(ModStructures.SLIMY_VILLAGE), 1)
                ),
                new RandomSpreadStructurePlacement(34, 8, RandomSpreadType.TRIANGULAR, 19011220)
        );
    }
}
