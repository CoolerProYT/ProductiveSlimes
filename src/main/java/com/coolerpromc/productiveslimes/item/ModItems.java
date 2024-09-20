package com.coolerpromc.productiveslimes.item;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.item.custom.GuidebookItem;
import com.coolerpromc.productiveslimes.item.custom.SlimeballItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ProductiveSlimes.MODID);

    public static final DeferredItem<Item> GUIDEBOOK = ITEMS.register("guidebook", GuidebookItem::new);

    public static final DeferredItem<Item> DIRT_SLIME_BALL = ITEMS.register("dirt_slimeball", () -> new SlimeballItem(0xFF866043));
    public static final DeferredItem<Item> STONE_SLIME_BALL = ITEMS.register("stone_slimeball", () -> new SlimeballItem(0xFF6F6969));
    public static final DeferredItem<Item> IRON_SLIME_BALL = ITEMS.register("iron_slimeball", () -> new SlimeballItem(0xFF8B5D2D));
    public static final DeferredItem<Item> COPPER_SLIME_BALL = ITEMS.register("copper_slimeball", () -> new SlimeballItem(0xFFAEAEAC));
    public static final DeferredItem<Item> GOLD_SLIME_BALL = ITEMS.register("gold_slimeball", () -> new SlimeballItem(0xFFCCC16A));
    public static final DeferredItem<Item> DIAMOND_SLIME_BALL = ITEMS.register("diamond_slimeball", () -> new SlimeballItem(0xFF2BB7C7));
    public static final DeferredItem<Item> NETHERITE_SLIME_BALL = ITEMS.register("netherite_slimeball", () -> new SlimeballItem(0xFF704545));
    public static final DeferredItem<Item> LAPIS_SLIME_BALL = ITEMS.register("lapis_slimeball", () -> new SlimeballItem(0xFF4165E6));
    public static final DeferredItem<Item> REDSTONE_SLIME_BALL = ITEMS.register("redstone_slimeball", () -> new SlimeballItem(0xFFD43030));
    public static final DeferredItem<Item> OAK_SLIME_BALL = ITEMS.register("oak_slimeball", () -> new SlimeballItem(0xFFa69d6f));


    public static final DeferredItem<Item> DIRT_SLIME_SPAWN_EGG = ITEMS.register("dirt_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.DIRT_SLIME.get(), 0x5e3a1c, 0x885022, new Item.Properties()));
    public static final DeferredItem<Item> STONE_SLIME_SPAWN_EGG = ITEMS.register("stone_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.STONE_SLIME.get(), 0x4a4545, 0x918b8b, new Item.Properties()));
    public static final DeferredItem<Item> IRON_SLIME_SPAWN_EGG = ITEMS.register("iron_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.IRON_SLIME.get(), 0x898c8a, 0xcfd1d0, new Item.Properties()));
    public static final DeferredItem<Item> COPPER_SLIME_SPAWN_EGG = ITEMS.register("copper_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.COPPER_SLIME.get(), 0x6a3e15, 0xb87333, new Item.Properties()));
    public static final DeferredItem<Item> GOLD_SLIME_SPAWN_EGG = ITEMS.register("gold_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.GOLD_SLIME.get(), 0xa5953f, 0xd9c245, new Item.Properties()));
    public static final DeferredItem<Item> DIAMOND_SLIME_SPAWN_EGG = ITEMS.register("diamond_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.DIAMOND_SLIME.get(), 0x178f9c, 0x2fcadc, new Item.Properties()));
    public static final DeferredItem<Item> NETHERITE_SLIME_SPAWN_EGG = ITEMS.register("netherite_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.NETHERITE_SLIME.get(), 0x4c2b2b, 0x453a3a, new Item.Properties()));
    public static final DeferredItem<Item> LAPIS_SLIME_SPAWN_EGG = ITEMS.register("lapis_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.LAPIS_SLIME.get(), 0x1c41ba, 0x2853e0, new Item.Properties()));
    public static final DeferredItem<Item> REDSTONE_SLIME_SPAWN_EGG = ITEMS.register("redstone_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.REDSTONE_SLIME.get(), 0xa10505, 0xc92020, new Item.Properties()));
    public static final DeferredItem<Item> OAK_SLIME_SPAWN_EGG = ITEMS.register("oak_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.OAK_SLIME.get(), 0xa69d6f, 0xded4a4, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}