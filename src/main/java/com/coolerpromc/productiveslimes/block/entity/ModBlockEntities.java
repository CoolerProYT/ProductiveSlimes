package com.coolerpromc.productiveslimes.block.entity;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ProductiveSlimes.MODID);

    public static final Supplier<BlockEntityType<MeltingStationBlockEntity>> MELTING_STATION_BE = BLOCK_ENTITIES.register("melting_station_be",
            () -> BlockEntityType.Builder.of(MeltingStationBlockEntity::new, ModBlocks.MELTING_STATION.get()).build(null));

    public static final Supplier<BlockEntityType<SolidingStationBlockEntity>> SOLIDING_STATION_BE = BLOCK_ENTITIES.register("soliding_station_be",
            () -> BlockEntityType.Builder.of(SolidingStationBlockEntity::new, ModBlocks.LIQUID_SOLIDING_STATION.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
