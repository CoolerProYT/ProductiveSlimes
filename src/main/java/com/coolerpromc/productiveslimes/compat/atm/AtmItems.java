package com.coolerpromc.productiveslimes.compat.atm;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.item.custom.DnaItem;
import com.coolerpromc.productiveslimes.item.custom.SlimeballItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AtmItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ProductiveSlimes.MODID);

    public static final DeferredItem<Item> ATM_SLIME_BALL = ITEMS.register("atm_slimeball", () -> new SlimeballItem(0xFFff8b04));
    public static final DeferredItem<Item> VIBRANIUM_SLIME_BALL = ITEMS.register("vibranium_slimeball", () -> new SlimeballItem(0xFF148484));
    public static final DeferredItem<Item> UNOBTAINIUM_SLIME_BALL = ITEMS.register("unobtainium_slimeball", () -> new SlimeballItem(0xFF6c1ce4));

    public static final DeferredItem<Item> ATM_SLIME_DNA = ITEMS.register("atm_slime_dna", () -> new DnaItem(0xFFff8b04));
    public static final DeferredItem<Item> VIBRANIUM_SLIME_DNA = ITEMS.register("vibranium_slime_dna", () -> new DnaItem(0xFF148484));
    public static final DeferredItem<Item> UNOBTAINIUM_SLIME_DNA = ITEMS.register("unobtainium_slime_dna", () -> new DnaItem(0xFF6c1ce4));

    public static final DeferredItem<Item> ATM_SLIME_SPAWN_EGG = ITEMS.register("atm_slime_spawn_egg",
            () -> new SpawnEggItem(AtmEntities.ATM_SLIME.get(), 0xff8b04, 0xECA046, new Item.Properties()));
    public static final DeferredItem<Item> VIBRANIUM_SLIME_SPAWN_EGG = ITEMS.register("vibranium_slime_spawn_egg",
            () -> new SpawnEggItem(AtmEntities.VIBRANIUM_SLIME.get(), 0x148484, 0x0A4C4C, new Item.Properties()));
    public static final DeferredItem<Item> UNOBTAINIUM_SLIME_SPAWN_EGG = ITEMS.register("unobtainium_slime_spawn_egg",
            () -> new SpawnEggItem(AtmEntities.UNOBTAINIUM_SLIME.get(), 0x6c1ce4, 0x3A1C6C, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
