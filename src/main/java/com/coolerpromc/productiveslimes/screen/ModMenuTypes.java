package com.coolerpromc.productiveslimes.screen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModMenuTypes {
    public static final DeferredRegister<ContainerType<?>> MENUS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ProductiveSlimes.MODID);

    public static final Supplier<ContainerType<MeltingStationMenu>> MELTING_STATION_MENU =
            registerMenuType("melting_station_menu", MeltingStationMenu::new);

    public static final Supplier<ContainerType<SolidingStationMenu>> SOLIDING_STATION_MENU =
            registerMenuType("soliding_station_menu", SolidingStationMenu::new);

    public static final Supplier<ContainerType<GuidebookMenu>> GUIDEBOOK_MENU =
            registerMenuType("guidebook_menu", GuidebookMenu::new);

    public static final Supplier<ContainerType<EnergyGeneratorMenu>> ENERGY_GENERATOR_MENU =
            registerMenuType("energy_generator_menu", EnergyGeneratorMenu::new);

    public static final Supplier<ContainerType<DnaExtractorMenu>> DNA_EXTRACTOR_MENU =
            registerMenuType("dna_extractor_menu", DnaExtractorMenu::new);

    public static final Supplier<ContainerType<DnaSynthesizerMenu>> DNA_SYNTHESIZER_MENU =
            registerMenuType("dna_synthesizer_menu", DnaSynthesizerMenu::new);

    public static final Supplier<ContainerType<SlimeSqueezerMenu>> SLIME_SQUEEZER_MENU =
            registerMenuType("slime_squeezer_menu", SlimeSqueezerMenu::new);

    public static final Supplier<ContainerType<SlimeNestMenu>> SLIME_NEST_MENU =
            registerMenuType("slime_nest_menu", SlimeNestMenu::new);
    public static final Supplier<ContainerType<SlimeballCollectorMenu>> SLIMEBALL_COLLECTOR_MENU =
            registerMenuType("slimeball_collector_menu", SlimeballCollectorMenu::new);

    private static <T extends Container> Supplier<ContainerType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeContainerType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
