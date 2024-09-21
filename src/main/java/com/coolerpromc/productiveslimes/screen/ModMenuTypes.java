package com.coolerpromc.productiveslimes.screen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, ProductiveSlimes.MODID);

    public static final Supplier<MenuType<MeltingStationMenu>> MELTING_STATION_MENU =
            registerMenuType("melting_station_menu", MeltingStationMenu::new);

    public static final Supplier<MenuType<SolidingStationMenu>> SOLIDING_STATION_MENU =
            registerMenuType("soliding_station_menu", SolidingStationMenu::new);

    public static final Supplier<MenuType<GuidebookMenu>> GUIDEBOOK_MENU =
            registerMenuType("guidebook_menu", GuidebookMenu::new);

    public static final Supplier<MenuType<EnergyGeneratorMenu>> ENERGY_GENERATOR_MENU =
            registerMenuType("energy_generator_menu", EnergyGeneratorMenu::new);

    private static <T extends AbstractContainerMenu> Supplier<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
