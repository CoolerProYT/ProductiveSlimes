package com.coolerpromc.productiveslimes.event;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.entity.renderer.BaseSlimeRenderer;
import com.coolerpromc.productiveslimes.entity.slime.*;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

import java.lang.reflect.Field;

@EventBusSubscriber(modid = ProductiveSlimes.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEntitiesEvent {
    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ModEntities.DIRT_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.STONE_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.IRON_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.COPPER_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.GOLD_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.DIAMOND_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.NETHERITE_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.LAPIS_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.REDSTONE_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.OAK_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.COAL_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.GRAVEL_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.SAND_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.ANDESITE_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.SNOW_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.ICE_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.MUD_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.CLAY_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.RED_SAND_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.MOSS_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.DEEPSLATE_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.GRANITE_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.DIORITE_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.CALCITE_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.TUFF_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.DRIPSTONE_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.NETHERRACK_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.PRISMARINE_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.MAGMA_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.OBSIDIAN_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.SOUL_SAND_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.SOUL_SOIL_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.BLACKSTONE_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.BASALT_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.QUARTZ_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.GLOWSTONE_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.ENDSTONE_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.AMETHYST_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.BROWN_MUSHROOM_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.RED_MUSHROOM_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.CACTUS_SLIME.get(), BaseSlime.createAttributes().build());
        event.put(ModEntities.ENERGY_SLIME.get(), BaseSlime.createAttributes().build());
    }

    public static void registerAllSlimeEntityAttribute(EntityAttributeCreationEvent event){
        Field[] fields = ModEntities.class.getFields();

        for (Field field : fields) {
            try {
                Object value = field.get(null);

                if (value instanceof EntityType<?> entityType) {
                    if (BaseSlime.class.isAssignableFrom(entityType.getBaseClass())) {
                        BaseSlime slimeInstance = (BaseSlime) entityType.create(null);

                        if (slimeInstance instanceof BaseSlime slime){
                            event.put((EntityType) entityType, slime.createAttributes().build());
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
