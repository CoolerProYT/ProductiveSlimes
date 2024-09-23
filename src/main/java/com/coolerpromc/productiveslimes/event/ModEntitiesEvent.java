package com.coolerpromc.productiveslimes.event;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.entity.slime.*;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
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
        Field[] fields = ModEntities.class.getFields();

        for (Field field : fields) {
            try {
                Object value = field.get(null);

                if (value instanceof DeferredHolder<?, ?> holder && holder.get() instanceof EntityType<?> entityType) {
                    event.put((EntityType<? extends BaseSlime>) entityType, BaseSlime.createAttributes().build());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
