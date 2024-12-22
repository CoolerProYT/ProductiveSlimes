package com.coolerpromc.productiveslimes.event;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.config.CustomContentRegistry;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.entity.renderer.BaseSlimeRenderer;
import com.coolerpromc.productiveslimes.entity.slime.*;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.lang.reflect.Field;

@EventBusSubscriber(modid = ProductiveSlimes.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEntitiesEvent {
    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        registerAllSlimeEntityAttribute(event);
    }

    public static void registerAllSlimeEntityAttribute(EntityAttributeCreationEvent event){
        for(Tier tier : Tier.values()) {
            ModTiers modTiers = ModTierLists.getTierByName(tier);
            DeferredHolder<EntityType<?>, EntityType<BaseSlime>> slime = ModTierLists.getEntityByName(modTiers.name());

            event.put(slime.get(), BaseSlime.createAttributes().build());
        }

        for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
            DeferredHolder<EntityType<?>, EntityType<BaseSlime>> slime = CustomContentRegistry.getSlimeForVariant(variant.getName());
            event.put(slime.get(), BaseSlime.createAttributes().build());
        }
    }
}
