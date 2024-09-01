package com.coolerpromc.productiveslimes.item;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ProductiveSlimes.MODID);

    public static final DeferredItem<Item> DIRT_SLIME_BALL = ITEMS.register("dirt_slimeball", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> IRON_SLIME_BALL = ITEMS.register("iron_slimeball", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GOLD_SLIME_BALL = ITEMS.register("gold_slimeball", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DIRT_SLIME_SPAWN_EGG = ITEMS.register("dirt_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.DIRT_SLIME.get(), 0x5e3a1c, 0x885022, new Item.Properties()));
    public static final DeferredItem<Item> IRON_SLIME_SPAWN_EGG = ITEMS.register("iron_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.IRON_SLIME.get(), 0x898c8a, 0xcfd1d0, new Item.Properties()));
    public static final DeferredItem<Item> GOLD_SLIME_SPAWN_EGG = ITEMS.register("gold_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.GOLD_SLIME.get(), 0xa5953f, 0xd9c245, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
