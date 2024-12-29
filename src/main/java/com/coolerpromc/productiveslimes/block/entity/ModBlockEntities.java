package com.coolerpromc.productiveslimes.block.entity;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ProductiveSlimes.MODID);

    public static final Supplier<BlockEntityType<MeltingStationBlockEntity>> MELTING_STATION_BE = BLOCK_ENTITIES.register("melting_station_be",
            () -> BlockEntityType.Builder.of(MeltingStationBlockEntity::new, ModBlocks.MELTING_STATION.get()).build(null));

    public static final Supplier<BlockEntityType<SolidingStationBlockEntity>> SOLIDING_STATION_BE = BLOCK_ENTITIES.register("soliding_station_be",
            () -> BlockEntityType.Builder.of(SolidingStationBlockEntity::new, ModBlocks.LIQUID_SOLIDING_STATION.get()).build(null));

    public static final Supplier<BlockEntityType<EnergyGeneratorBlockEntity>> ENERGY_GENERATOR_BE = BLOCK_ENTITIES.register("fluid_separator_be",
            () -> BlockEntityType.Builder.of(EnergyGeneratorBlockEntity::new, ModBlocks.ENERGY_GENERATOR.get()).build(null));

    public static final Supplier<BlockEntityType<CableBlockEntity>> CABLE_BE = BLOCK_ENTITIES.register("cable_be",
            () -> BlockEntityType.Builder.of(CableBlockEntity::new, ModBlocks.CABLE.get()).build(null));

    public static final Supplier<BlockEntityType<DnaExtractorBlockEntity>> DNA_EXTRACTOR_BE = BLOCK_ENTITIES.register("dna_extractor_be",
            () -> BlockEntityType.Builder.of(DnaExtractorBlockEntity::new, ModBlocks.DNA_EXTRACTOR.get()).build(null));

    public static final Supplier<BlockEntityType<DnaSynthesizerBlockEntity>> DNA_SYNTHESIZER_BE = BLOCK_ENTITIES.register("dna_synthesizer_be",
            () -> BlockEntityType.Builder.of(DnaSynthesizerBlockEntity::new, ModBlocks.DNA_SYNTHESIZER.get()).build(null));

    public static final Supplier<BlockEntityType<FluidTankBlockEntity>> FLUID_TANK_BE = BLOCK_ENTITIES.register("fluid_tank_be",
            () -> BlockEntityType.Builder.of(FluidTankBlockEntity::new, ModBlocks.FLUID_TANK.get()).build(null));

    public static final Supplier<BlockEntityType<SlimeSqueezerBlockEntity>> SLIME_SQUEEZER_BE = BLOCK_ENTITIES.register("slime_squeezer_be",
            () -> BlockEntityType.Builder.of(SlimeSqueezerBlockEntity::new, ModBlocks.SLIME_SQUEEZER.get()).build(null));

    public static final Supplier<BlockEntityType<SlimeNestBlockEntity>> SLIME_NEST_BE = BLOCK_ENTITIES.register("slime_nest_be",
            () -> BlockEntityType.Builder.of(SlimeNestBlockEntity::new, ModBlocks.SLIME_NEST.get()).build(null));
    public static final Supplier<BlockEntityType<SlimeballCollectorBlockEntity>> SLIMEBALL_COLLECTOR_BE = BLOCK_ENTITIES.register("slimeball_collector_be",
            () -> BlockEntityType.Builder.of(SlimeballCollectorBlockEntity::new, ModBlocks.SLIMEBALL_COLLECTOR.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
