package com.coolerpromc.productiveslimes.event;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.config.CustomContentRegistry;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.entity.slime.*;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = ProductiveSlimes.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
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

                if (value instanceof RegistryObject<?> holder && holder.get() instanceof EntityType<?> entityType) {
                    event.put((EntityType<? extends BaseSlime>) entityType, BaseSlime.createAttributes().build());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
            RegistryObject<EntityType<BaseSlime>> slime = CustomContentRegistry.getSlimeForVariant(variant.getName());
            event.put(slime.get(), BaseSlime.createAttributes().build());
        }
    }
}
