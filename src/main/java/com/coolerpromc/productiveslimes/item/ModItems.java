package com.coolerpromc.productiveslimes.item;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.item.custom.*;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ProductiveSlimes.MODID);

    public static final RegistryObject<Item> GUIDEBOOK = ITEMS.register("guidebook", GuidebookItem::new);

    public static final RegistryObject<Item> SLIMEBALL_FRAGMENT = ITEMS.register("slimeball_fragment", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> SLIME_ITEM  = ITEMS.register("slime_item", () -> new SlimeItem(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> FLUID_TANK = ITEMS.register("fluid_tank", () -> new FluidTankBlockItem(ModBlocks.FLUID_TANK.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<Item> ENERGY_MULTIPLIER_UPGRADE = ITEMS.register("energy_multiplier_upgrade", () -> new EnergyMultiplierUpgrade(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> SLIME_NEST_SPEED_UPGRADE_1 = ITEMS.register("slime_nest_speed_upgrade_1", () -> new NestUpgradeItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), 1.5f));
    public static final RegistryObject<Item> SLIME_NEST_SPEED_UPGRADE_2 = ITEMS.register("slime_nest_speed_upgrade_2", () -> new NestUpgradeItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), 2f));

    public static final RegistryObject<Item> ENERGY_SLIME_BALL = ITEMS.register("energy_slimeball", () -> new SlimeballItem(0xFFFFFF70));

    public static final RegistryObject<Item> SLIME_DNA = ITEMS.register("slime_dna", () -> new DnaItem(0xFF7BC35C));

    public static final RegistryObject<Item> ENERGY_SLIME_SPAWN_EGG = ITEMS.register("energy_slime_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.ENERGY_SLIME, 0xffff70, 0xFFFF00, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static void registerTierItem() {
        for (Tier tier : Tier.values()) {
            ModTiers modTiers = ModTierLists.getTierByName(tier);
            String slimeballName = modTiers.name() + "_slimeball";
            String dnaName = modTiers.name() + "_slime_dna";
            String spawnEggName = modTiers.name() + "_slime_spawn_egg";
            int color = modTiers.color();
            RegistryObject<Item> slimeball = ITEMS.register(slimeballName, () -> new SlimeballItem(color));
            RegistryObject<Item> dna = ITEMS.register(dnaName, () -> new DnaItem(color));
            RegistryObject<Item> spawnEgg = ITEMS.register(spawnEggName, () -> new ForgeSpawnEggItem(ModTierLists.getEntityByName(modTiers.name()), color, color, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
            ModTierLists.addRegisteredSlimeballItem(modTiers.name(), slimeball);
            ModTierLists.addRegisteredDnaItem(modTiers.name(), dna);
            ModTierLists.addRegisteredSpawnEggItem(modTiers.name(), spawnEgg);
        }
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}