package com.coolerpromc.productiveslimes.item;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.item.custom.*;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ProductiveSlimes.MODID);

    public static final DeferredItem<Item> GUIDEBOOK = ITEMS.register("guidebook", GuidebookItem::new);

    public static final DeferredItem<Item> SLIMEBALL_FRAGMENT = ITEMS.register("slimeball_fragment", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SLIME_ITEM = ITEMS.register("slime_item", () -> new SlimeItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> ENERGY_MULTIPLIER_UPGRADE = ITEMS.register("energy_multiplier_upgrade", () -> new EnergyMultiplierUpgrade(new Item.Properties()));
    public static final DeferredItem<Item> SLIME_NEST_SPEED_UPGRADE_1 = ITEMS.register("slime_nest_speed_upgrade_1", () -> new NestUpgradeItem(new Item.Properties(), 1.5f));
    public static final DeferredItem<Item> SLIME_NEST_SPEED_UPGRADE_2 = ITEMS.register("slime_nest_speed_upgrade_2", () -> new NestUpgradeItem(new Item.Properties(), 2f));

    public static final DeferredItem<Item> ENERGY_SLIME_BALL = ITEMS.register("energy_slimeball", () -> new SlimeballItem(0xFFFFFF70));

    public static final DeferredItem<Item> SLIME_DNA = ITEMS.register("slime_dna", () -> new DnaItem(0xFF7BC35C));

    public static final DeferredItem<Item> ENERGY_SLIME_SPAWN_EGG = ITEMS.register("energy_slime_spawn_egg", () -> new SpawnEggItem(ModEntities.ENERGY_SLIME.get(), 0xffff70, 0xFFFF00, new Item.Properties()));

    public static void registerTierItem(){
        for (Tier tier : Tier.values()){
            ModTiers modTiers = ModTierLists.getTierByName(tier);

            String slimeballName = modTiers.name() + "_slimeball";
            String dnaName = modTiers.name() + "_slime_dna";
            String spawnEggName = modTiers.name() + "_slime_spawn_egg";

            int color = modTiers.color();

            DeferredItem<Item> slimeball = ITEMS.register(slimeballName, () -> new SlimeballItem(color));
            DeferredItem<Item> dna = ITEMS.register(dnaName, () -> new DnaItem(color));
            DeferredItem<Item> spawnEgg = ITEMS.register(spawnEggName, () -> new SpawnEggItem(ModTierLists.getEntityByName(modTiers.name()).get(), color, color, new Item.Properties()));

            ModTierLists.addRegisteredSlimeballItem(modTiers.name(), slimeball);
            ModTierLists.addRegisteredDnaItem(modTiers.name(), dna);
            ModTierLists.addRegisteredSpawnEggItem(modTiers.name(), spawnEgg);
        }
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}