package com.coolerpromc.productiveslimes.villager;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, ProductiveSlimes.MODID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, ProductiveSlimes.MODID);
    public static final Holder<PoiType> SLIMY_POI = POI_TYPES.register("slimy_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.DNA_EXTRACTOR.get().getStateDefinition().getPossibleStates()), 1, 1));
    public static final Holder<VillagerProfession> SCIENTIST = VILLAGER_PROFESSIONS.register("scientist",
            () -> new VillagerProfession("scientist", holder -> holder.value() == SLIMY_POI.value(), holder -> holder.value() == SLIMY_POI.value(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_MASON));
    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}