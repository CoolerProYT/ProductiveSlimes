package com.coolerpromc.productiveslimes.villager;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModVillagers {
    public static final DeferredRegister<PointOfInterestType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, ProductiveSlimes.MODID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, ProductiveSlimes.MODID);

    public static final RegistryObject<PointOfInterestType> SLIMY_POI = POI_TYPES.register("slimy_poi",
            () -> new PointOfInterestType("slimy_poi", ImmutableSet.copyOf(ModBlocks.DNA_EXTRACTOR.get().getStateDefinition().getPossibleStates()), 1, 1));

    public static final RegistryObject<VillagerProfession> SCIENTIST = VILLAGER_PROFESSIONS.register("scientist",
            () -> new VillagerProfession("scientist", SLIMY_POI.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_MASON));

    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
