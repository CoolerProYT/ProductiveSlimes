package com.coolerpromc.productiveslimes.event;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.screen.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = ProductiveSlimes.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModScreens {
    @SubscribeEvent
    public static void onRegisterScreens(RegisterMenuScreensEvent event)
    {
        event.register(ModMenuTypes.MELTING_STATION_MENU.get(), MeltingStationScreen::new);
        event.register(ModMenuTypes.SOLIDING_STATION_MENU.get(), SolidingStationScreen::new);
        event.register(ModMenuTypes.GUIDEBOOK_MENU.get(), GuidebookScreen::new);
        event.register(ModMenuTypes.ENERGY_GENERATOR_MENU.get(), EnergyGeneratorScreen::new);
    }
}