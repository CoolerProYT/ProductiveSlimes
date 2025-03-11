package com.coolerpromc.productiveslimes.worldgen.structure;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.EmptyPoolElement;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ModStructureTemplatePools {
    public static final ResourceKey<StructureTemplatePool> BUILDINGS = ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "slimy_village/buildings"));
    public static final ResourceKey<StructureTemplatePool> STREETS = ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "slimy_village/streets"));
    public static final ResourceKey<StructureTemplatePool> VILLAGE_CENTER = ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "slimy_village/village_center"));

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        context.register(BUILDINGS, buildings(context));
        context.register(STREETS, streets(context));
        context.register(VILLAGE_CENTER, villageCenter(context));
    }

    public static StructureTemplatePool buildings(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> structureTemplatePoolSettings = context.lookup(Registries.TEMPLATE_POOL);
        HolderGetter<StructureProcessorList> processorListSettings = context.lookup(Registries.PROCESSOR_LIST);

        List<Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>> factories = List.of(
                new Pair<>(projection -> new SinglePoolElement(Either.left(ResourceLocation.parse("productiveslimes:slimy_village/houses/small_village_house")), processorListSettings.getOrThrow(ProcessorLists.EMPTY), projection, Optional.empty()), 10),
                new Pair<>(projection -> new SinglePoolElement(Either.left(ResourceLocation.parse("productiveslimes:slimy_village/houses/slimy_profession_house")), processorListSettings.getOrThrow(ProcessorLists.EMPTY), projection, Optional.empty()), 2),
                new Pair<>(projection -> new SinglePoolElement(Either.left(ResourceLocation.parse("productiveslimes:slimy_village/houses/slimy_armorer_house")), processorListSettings.getOrThrow(ProcessorLists.EMPTY), projection, Optional.empty()), 4),
                new Pair<>(projection -> new SinglePoolElement(Either.left(ResourceLocation.parse("productiveslimes:slimy_village/houses/slimy_fletcher_house")), processorListSettings.getOrThrow(ProcessorLists.EMPTY), projection, Optional.empty()), 4),
                new Pair<>(projection -> new SinglePoolElement(Either.left(ResourceLocation.parse("productiveslimes:slimy_village/houses/slime_statue")), processorListSettings.getOrThrow(ProcessorLists.EMPTY), projection, Optional.empty()), 1),
                new Pair<>(projection -> new SinglePoolElement(Either.left(ResourceLocation.parse("productiveslimes:slimy_village/houses/slimy_farm")), processorListSettings.getOrThrow(ProcessorLists.EMPTY), projection, Optional.empty()), 1)
        );

        return new StructureTemplatePool(
                structureTemplatePoolSettings.getOrThrow(Pools.EMPTY),
                factories,
                StructureTemplatePool.Projection.RIGID
        );
    }

    public static StructureTemplatePool streets(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> structureTemplatePoolSettings = context.lookup(Registries.TEMPLATE_POOL);
        HolderGetter<StructureProcessorList> processorListSettings = context.lookup(Registries.PROCESSOR_LIST);

        List<Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>> factories = List.of(
                new Pair<>(projection -> new EmptyPoolElement(), 3),
                new Pair<>(projection -> new SinglePoolElement(Either.left(ResourceLocation.parse("productiveslimes:slimy_village/streets/straight_path")), processorListSettings.getOrThrow(ProcessorLists.EMPTY), projection, Optional.empty()), 3),
                new Pair<>(projection -> new SinglePoolElement(Either.left(ResourceLocation.parse("productiveslimes:slimy_village/streets/t_path")), processorListSettings.getOrThrow(ProcessorLists.EMPTY), projection, Optional.empty()), 2),
                new Pair<>(projection -> new SinglePoolElement(Either.left(ResourceLocation.parse("productiveslimes:slimy_village/streets/corner_path")), processorListSettings.getOrThrow(ProcessorLists.EMPTY), projection, Optional.empty()), 2),
                new Pair<>(projection -> new SinglePoolElement(Either.left(ResourceLocation.parse("productiveslimes:slimy_village/streets/cross_path")), processorListSettings.getOrThrow(ProcessorLists.EMPTY), projection, Optional.empty()), 1)
        );

        return new StructureTemplatePool(
                structureTemplatePoolSettings.getOrThrow(Pools.EMPTY),
                factories,
                StructureTemplatePool.Projection.RIGID
        );
    }

    public static StructureTemplatePool villageCenter(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> structureTemplatePoolSettings = context.lookup(Registries.TEMPLATE_POOL);
        HolderGetter<StructureProcessorList> processorListSettings = context.lookup(Registries.PROCESSOR_LIST);

        List<Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>> factories = List.of(
                new Pair<>(projection -> new SinglePoolElement(Either.left(ResourceLocation.parse("productiveslimes:slimy_village/village_center/village_center")), processorListSettings.getOrThrow(ProcessorLists.EMPTY), projection, Optional.empty()), 1)
        );

        return new StructureTemplatePool(
                structureTemplatePoolSettings.getOrThrow(Pools.EMPTY),
                factories,
                StructureTemplatePool.Projection.RIGID
        );
    }
}
