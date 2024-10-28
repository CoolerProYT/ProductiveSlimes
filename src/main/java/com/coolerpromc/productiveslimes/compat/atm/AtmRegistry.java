package com.coolerpromc.productiveslimes.compat.atm;

import net.neoforged.bus.api.IEventBus;

public class AtmRegistry {
    public static void register(IEventBus eventBus){
        AtmBlocks.register(eventBus);
        AtmItems.register(eventBus);
        AtmFluidTypes.register(eventBus);
        AtmFluids.register(eventBus);
        AtmEntities.register(eventBus);
    }
}
