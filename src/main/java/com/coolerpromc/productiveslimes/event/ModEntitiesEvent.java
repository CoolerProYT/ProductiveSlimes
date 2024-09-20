package com.coolerpromc.productiveslimes.event;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.entity.slime.DirtSlime;
import com.coolerpromc.productiveslimes.entity.slime.GoldSlime;
import com.coolerpromc.productiveslimes.entity.slime.IronSlime;
import com.coolerpromc.productiveslimes.entity.slime.StoneSlime;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = ProductiveSlimes.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEntitiesEvent {
    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ModEntities.DIRT_SLIME.get(), DirtSlime.createAttributes().build());
        event.put(ModEntities.STONE_SLIME.get(), StoneSlime.createAttributes().build());
        event.put(ModEntities.IRON_SLIME.get(), IronSlime.createAttributes().build());
        event.put(ModEntities.COPPER_SLIME.get(), IronSlime.createAttributes().build());
        event.put(ModEntities.GOLD_SLIME.get(), GoldSlime.createAttributes().build());
        event.put(ModEntities.DIAMOND_SLIME.get(), GoldSlime.createAttributes().build());
        event.put(ModEntities.NETHERITE_SLIME.get(), GoldSlime.createAttributes().build());
        event.put(ModEntities.LAPIS_SLIME.get(), GoldSlime.createAttributes().build());
        event.put(ModEntities.REDSTONE_SLIME.get(), GoldSlime.createAttributes().build());
        event.put(ModEntities.OAK_SLIME.get(), GoldSlime.createAttributes().build());
    }
}
