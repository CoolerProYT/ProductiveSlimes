package com.coolerpromc.productiveslimes.item;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.item.custom.*;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ProductiveSlimes.MODID);

    public static final DeferredItem<Item> GUIDEBOOK = ITEMS.registerItem("guidebook", GuidebookItem::new, new Item.Properties());

    public static final DeferredItem<Item> ENERGY_MULTIPLIER_UPGRADE = ITEMS.registerItem("energy_multiplier_upgrade", EnergyMultiplierUpgrade::new, new Item.Properties());
    public static final DeferredItem<Item> SLIMEBALL_FRAGMENT = ITEMS.registerItem("slimeball_fragment", Item::new, new Item.Properties());

    public static final DeferredItem<Item> ENERGY_SLIME_BALL = ITEMS.registerItem("energy_slimeball", properties -> new SlimeballItem(0xFFFFFF70, properties), new Item.Properties());
    public static final DeferredItem<Item> ENERGY_SLIME_SPAWN_EGG = ITEMS.registerItem("energy_slime_spawn_egg", properties -> new SpawnEggItem(ModEntities.ENERGY_SLIME.get(), 0xffff70, 0xFFFF00, properties), new Item.Properties());

    public static final DeferredItem<Item> SLIME_DNA = ITEMS.registerItem("slime_dna", properties -> new DnaItem(0xFF7BC35C, properties), new Item.Properties());

    public static void registerTierItems() {
        for (Tier name : Tier.values()){
            ModTiers tiers = ModTierLists.getTierByName(name);
            String slimeballName = tiers.name() + "_slimeball";
            String dnaName = tiers.name() + "_slime_dna";
            String spawnEggName = tiers.name() + "_slime_spawn_egg";

            int color = tiers.color();

            DeferredItem<Item> slimeball = ITEMS.registerItem(slimeballName, properties -> new SlimeballItem(color, properties), new Item.Properties());
            DeferredItem<Item> dna = ITEMS.registerItem(dnaName, properties -> new DnaItem(color, properties), new Item.Properties());
            DeferredItem<Item> spawnEgg = ITEMS.registerItem(spawnEggName, properties -> new SpawnEggItem(ModTierLists.getEntityByName(tiers.name()).get(), color, color, properties), new Item.Properties());

            ModTierLists.addRegisteredSlimeballItem(tiers.name(), slimeball);
            ModTierLists.addRegisteredDnaItem(tiers.name(), dna);
            ModTierLists.addRegisteredSpawnEggItem(tiers.name(), spawnEgg);
        }
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}