package com.coolerpromc.productiveslimes.item;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.item.custom.*;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ProductiveSlimes.MODID);

    public static final DeferredItem<Item> GUIDEBOOK = ITEMS.registerItem("guidebook", GuidebookItem::new, new Item.Properties());

    public static final DeferredItem<Item> ENERGY_MULTIPLIER_UPGRADE = ITEMS.registerItem("energy_multiplier_upgrade", EnergyMultiplierUpgrade::new, new Item.Properties());

    public static final DeferredItem<Item> ENERGY_SLIME_BALL = ITEMS.registerItem("energy_slimeball", properties -> new SlimeballItem(0xFFFFFF70, properties), new Item.Properties());
    public static final DeferredItem<Item> ENERGY_SLIME_SPAWN_EGG = ITEMS.registerItem("energy_slime_spawn_egg", properties -> new SpawnEggItem(ModEntities.ENERGY_SLIME.get(), 0xffff70, 0xFFFF00, properties), new Item.Properties());

    public static final DeferredItem<Item> SLIME_DNA = ITEMS.registerItem("slime_dna", properties -> new DnaItem(0xFF7BC35C, properties), new Item.Properties());

    public static void registerTierItems() {
        for (String name : ModTierLists.TIER_NAMES){
            String slimeballName = name + "_slimeball";
            String dnaName = name + "_slime_dna";
            String spawnEggName = name + "_slime_spawn_egg";

            int color = ModTierLists.getTierByName(name).getColor();

            DeferredItem<Item> slimeball = ITEMS.registerItem(slimeballName, properties -> new SlimeballItem(color, properties), new Item.Properties());
            DeferredItem<Item> dna = ITEMS.registerItem(dnaName, properties -> new DnaItem(color, properties), new Item.Properties());
            DeferredItem<Item> spawnEgg = ITEMS.registerItem(spawnEggName, properties -> new SpawnEggItem(ModTierLists.getEntityByName(name).get(), color, color, properties), new Item.Properties());

            ModTierLists.addRegisteredSlimeballItem(name, slimeball);
            ModTierLists.addRegisteredDnaItem(name, dna);
            ModTierLists.addRegisteredSpawnEggItem(name, spawnEgg);
        }
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}