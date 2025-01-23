package com.coolerpromc.productiveslimes.event;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.config.CustomContentRegistry;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.entity.slime.*;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ProductiveSlimes.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntitiesEvent {
    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        registerAllSlimeEntityAttribute(event);
    }

    public static void registerAllSlimeEntityAttribute(EntityAttributeCreationEvent event){
        event.put(ModEntities.ENERGY_SLIME.get(), BaseSlime.createAttributes().build());

        for(Tier tier : Tier.values()) {
            ModTiers modTiers = ModTierLists.getTierByName(tier);
            RegistryObject<EntityType<BaseSlime>> slime = ModTierLists.getEntityByName(modTiers.name());

            event.put(slime.get(), BaseSlime.createAttributes().build());
        }

        for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
            RegistryObject<EntityType<BaseSlime>> slime = CustomContentRegistry.getSlimeForVariant(variant.getName());
            event.put(slime.get(), BaseSlime.createAttributes().build());
        }
    }
}
