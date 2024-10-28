package com.coolerpromc.productiveslimes.item;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.item.custom.DnaItem;
import com.coolerpromc.productiveslimes.item.custom.EnergyMultiplierUpgrade;
import com.coolerpromc.productiveslimes.item.custom.GuidebookItem;
import com.coolerpromc.productiveslimes.item.custom.SlimeballItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ProductiveSlimes.MODID);

//    public static final DeferredItem<Item> GUIDEBOOK = ITEMS.register("guidebook", GuidebookItem::new);

    public static final DeferredItem<Item> ENERGY_MULTIPLIER_UPGRADE = ITEMS.registerItem("energy_multiplier_upgrade", EnergyMultiplierUpgrade::new, new Item.Properties());

    public static final DeferredItem<Item> DIRT_SLIME_BALL = ITEMS.registerItem("dirt_slimeball", properties -> new SlimeballItem(0xFF866043, properties), new Item.Properties());
    public static final DeferredItem<Item> STONE_SLIME_BALL = ITEMS.registerItem("stone_slimeball", properties -> new SlimeballItem(0xFF6F6969, properties), new Item.Properties());
    public static final DeferredItem<Item> IRON_SLIME_BALL = ITEMS.registerItem("iron_slimeball", properties -> new SlimeballItem(0xFF898c8a, properties), new Item.Properties());
    public static final DeferredItem<Item> COPPER_SLIME_BALL = ITEMS.registerItem("copper_slimeball", properties -> new SlimeballItem(0xFF6a3e15, properties), new Item.Properties());
    public static final DeferredItem<Item> GOLD_SLIME_BALL = ITEMS.registerItem("gold_slimeball", properties -> new SlimeballItem(0xFFCCC16A, properties), new Item.Properties());
    public static final DeferredItem<Item> DIAMOND_SLIME_BALL = ITEMS.registerItem("diamond_slimeball", properties -> new SlimeballItem(0xFF2BB7C7, properties), new Item.Properties());
    public static final DeferredItem<Item> NETHERITE_SLIME_BALL = ITEMS.registerItem("netherite_slimeball", properties -> new SlimeballItem(0xFF704545, properties), new Item.Properties());
    public static final DeferredItem<Item> LAPIS_SLIME_BALL = ITEMS.registerItem("lapis_slimeball", properties -> new SlimeballItem(0xFF4165E6, properties), new Item.Properties());
    public static final DeferredItem<Item> REDSTONE_SLIME_BALL = ITEMS.registerItem("redstone_slimeball", properties -> new SlimeballItem(0xFFD43030, properties), new Item.Properties());
    public static final DeferredItem<Item> OAK_SLIME_BALL = ITEMS.registerItem("oak_slimeball", properties -> new SlimeballItem(0xFFa69d6f, properties), new Item.Properties());
    public static final DeferredItem<Item> SAND_SLIME_BALL = ITEMS.registerItem("sand_slimeball", properties -> new SlimeballItem(0xFFf7f7c6, properties), new Item.Properties());
    public static final DeferredItem<Item> ANDESITE_SLIME_BALL = ITEMS.registerItem("andesite_slimeball", properties -> new SlimeballItem(0xFF9d9e9a, properties), new Item.Properties());
    public static final DeferredItem<Item> SNOW_SLIME_BALL = ITEMS.registerItem("snow_slimeball", properties -> new SlimeballItem(0xFFf2fcfc, properties), new Item.Properties());
    public static final DeferredItem<Item> ICE_SLIME_BALL = ITEMS.registerItem("ice_slimeball", properties -> new SlimeballItem(0xFF89b1fc, properties), new Item.Properties());
    public static final DeferredItem<Item> MUD_SLIME_BALL = ITEMS.registerItem("mud_slimeball", properties -> new SlimeballItem(0xFF363339, properties), new Item.Properties());
    public static final DeferredItem<Item> CLAY_SLIME_BALL = ITEMS.registerItem("clay_slimeball", properties -> new SlimeballItem(0xFF9ca2ac, properties), new Item.Properties());
    public static final DeferredItem<Item> RED_SAND_SLIME_BALL = ITEMS.registerItem("red_sand_slimeball", properties -> new SlimeballItem(0xFFbb6520, properties), new Item.Properties());
    public static final DeferredItem<Item> MOSS_SLIME_BALL = ITEMS.registerItem("moss_slimeball", properties -> new SlimeballItem(0xFF4a6029, properties), new Item.Properties());
    public static final DeferredItem<Item> DEEPSLATE_SLIME_BALL = ITEMS.registerItem("deepslate_slimeball", properties -> new SlimeballItem(0xFF3c3c42, properties), new Item.Properties());
    public static final DeferredItem<Item> GRANITE_SLIME_BALL = ITEMS.registerItem("granite_slimeball", properties -> new SlimeballItem(0xFF835949, properties), new Item.Properties());
    public static final DeferredItem<Item> DIORITE_SLIME_BALL = ITEMS.registerItem("diorite_slimeball", properties -> new SlimeballItem(0xFFadacad, properties), new Item.Properties());
    public static final DeferredItem<Item> CALCITE_SLIME_BALL = ITEMS.registerItem("calcite_slimeball", properties -> new SlimeballItem(0xFFe9e9e3, properties), new Item.Properties());
    public static final DeferredItem<Item> TUFF_SLIME_BALL = ITEMS.registerItem("tuff_slimeball", properties -> new SlimeballItem(0xFF55564c, properties), new Item.Properties());
    public static final DeferredItem<Item> DRIPSTONE_SLIME_BALL = ITEMS.registerItem("dripstone_slimeball", properties -> new SlimeballItem(0xFF806155, properties), new Item.Properties());
    public static final DeferredItem<Item> PRISMARINE_SLIME_BALL = ITEMS.registerItem("prismarine_slimeball", properties -> new SlimeballItem(0xFF529584, properties), new Item.Properties());
    public static final DeferredItem<Item> MAGMA_SLIME_BALL = ITEMS.registerItem("magma_slimeball", properties -> new SlimeballItem(0xFF561f1f, properties), new Item.Properties());
    public static final DeferredItem<Item> OBSIDIAN_SLIME_BALL = ITEMS.registerItem("obsidian_slimeball", properties -> new SlimeballItem(0xFF030106, properties), new Item.Properties());
    public static final DeferredItem<Item> NETHERRACK_SLIME_BALL = ITEMS.registerItem("netherrack_slimeball", properties -> new SlimeballItem(0xFF763535, properties), new Item.Properties());
    public static final DeferredItem<Item> SOUL_SAND_SLIME_BALL = ITEMS.registerItem("soul_sand_slimeball", properties -> new SlimeballItem(0xFF413127, properties), new Item.Properties());
    public static final DeferredItem<Item> SOUL_SOIL_SLIME_BALL = ITEMS.registerItem("soul_soil_slimeball", properties -> new SlimeballItem(0xFF392b23, properties), new Item.Properties());
    public static final DeferredItem<Item> BLACKSTONE_SLIME_BALL = ITEMS.registerItem("blackstone_slimeball", properties -> new SlimeballItem(0xFF201819, properties), new Item.Properties());
    public static final DeferredItem<Item> BASALT_SLIME_BALL = ITEMS.registerItem("basalt_slimeball", properties -> new SlimeballItem(0xFF565456, properties), new Item.Properties());
    public static final DeferredItem<Item> ENDSTONE_SLIME_BALL = ITEMS.registerItem("endstone_slimeball", properties -> new SlimeballItem(0xFFcece8e, properties), new Item.Properties());
    public static final DeferredItem<Item> QUARTZ_SLIME_BALL = ITEMS.registerItem("quartz_slimeball", properties -> new SlimeballItem(0xFFe4ddd3, properties), new Item.Properties());
    public static final DeferredItem<Item> GLOWSTONE_SLIME_BALL = ITEMS.registerItem("glowstone_slimeball", properties -> new SlimeballItem(0xFF784e27, properties), new Item.Properties());
    public static final DeferredItem<Item> AMETHYST_SLIME_BALL = ITEMS.registerItem("amethyst_slimeball", properties -> new SlimeballItem(0xFF6b4da5, properties), new Item.Properties());
    public static final DeferredItem<Item> BROWN_MUSHROOM_SLIME_BALL = ITEMS.registerItem("brown_mushroom_slimeball", properties -> new SlimeballItem(0xFF967251, properties), new Item.Properties());
    public static final DeferredItem<Item> RED_MUSHROOM_SLIME_BALL = ITEMS.registerItem("red_mushroom_slimeball", properties -> new SlimeballItem(0xFFc02624, properties), new Item.Properties());
    public static final DeferredItem<Item> CACTUS_SLIME_BALL = ITEMS.registerItem("cactus_slimeball", properties -> new SlimeballItem(0xFF476d21, properties), new Item.Properties());
    public static final DeferredItem<Item> COAL_SLIME_BALL = ITEMS.registerItem("coal_slimeball", properties -> new SlimeballItem(0xFF3b3d3b, properties), new Item.Properties());
    public static final DeferredItem<Item> GRAVEL_SLIME_BALL = ITEMS.registerItem("gravel_slimeball", properties -> new SlimeballItem(0xFF4a444b, properties), new Item.Properties());
    public static final DeferredItem<Item> ENERGY_SLIME_BALL = ITEMS.registerItem("energy_slimeball", properties -> new SlimeballItem(0xFFFFFF70, properties), new Item.Properties());

    public static final DeferredItem<Item> SLIME_DNA = ITEMS.registerItem("slime_dna", properties -> new DnaItem(0xFF7BC35C, properties), new Item.Properties());
    public static final DeferredItem<Item> DIRT_SLIME_DNA = ITEMS.registerItem("dirt_slime_dna", properties -> new DnaItem(0xFF866043, properties), new Item.Properties());
    public static final DeferredItem<Item> STONE_SLIME_DNA = ITEMS.registerItem("stone_slime_dna", properties -> new DnaItem(0xFF6F6969, properties), new Item.Properties());
    public static final DeferredItem<Item> IRON_SLIME_DNA = ITEMS.registerItem("iron_slime_dna", properties -> new DnaItem(0xFF898c8a, properties), new Item.Properties());
    public static final DeferredItem<Item> COPPER_SLIME_DNA = ITEMS.registerItem("copper_slime_dna", properties -> new DnaItem(0xFF6a3e15, properties), new Item.Properties());
    public static final DeferredItem<Item> GOLD_SLIME_DNA = ITEMS.registerItem("gold_slime_dna", properties -> new DnaItem(0xFFCCC16A, properties), new Item.Properties());
    public static final DeferredItem<Item> DIAMOND_SLIME_DNA = ITEMS.registerItem("diamond_slime_dna", properties -> new DnaItem(0xFF2BB7C7, properties), new Item.Properties());
    public static final DeferredItem<Item> NETHERITE_SLIME_DNA = ITEMS.registerItem("netherite_slime_dna", properties -> new DnaItem(0xFF704545, properties), new Item.Properties());
    public static final DeferredItem<Item> LAPIS_SLIME_DNA = ITEMS.registerItem("lapis_slime_dna", properties -> new DnaItem(0xFF4165E6, properties), new Item.Properties());
    public static final DeferredItem<Item> REDSTONE_SLIME_DNA = ITEMS.registerItem("redstone_slime_dna", properties -> new DnaItem(0xFFD43030, properties), new Item.Properties());
    public static final DeferredItem<Item> OAK_SLIME_DNA = ITEMS.registerItem("oak_slime_dna", properties -> new DnaItem(0xFFa69d6f, properties), new Item.Properties());
    public static final DeferredItem<Item> SAND_SLIME_DNA = ITEMS.registerItem("sand_slime_dna", properties -> new DnaItem(0xFFf7f7c6, properties), new Item.Properties());
    public static final DeferredItem<Item> ANDESITE_SLIME_DNA = ITEMS.registerItem("andesite_slime_dna", properties -> new DnaItem(0xFF9d9e9a, properties), new Item.Properties());
    public static final DeferredItem<Item> SNOW_SLIME_DNA = ITEMS.registerItem("snow_slime_dna", properties -> new DnaItem(0xFFf2fcfc, properties), new Item.Properties());
    public static final DeferredItem<Item> ICE_SLIME_DNA = ITEMS.registerItem("ice_slime_dna", properties -> new DnaItem(0xFF89b1fc, properties), new Item.Properties());
    public static final DeferredItem<Item> MUD_SLIME_DNA = ITEMS.registerItem("mud_slime_dna", properties -> new DnaItem(0xFF363339, properties), new Item.Properties());
    public static final DeferredItem<Item> CLAY_SLIME_DNA = ITEMS.registerItem("clay_slime_dna", properties -> new DnaItem(0xFF9ca2ac, properties), new Item.Properties());
    public static final DeferredItem<Item> RED_SAND_SLIME_DNA = ITEMS.registerItem("red_sand_slime_dna", properties -> new DnaItem(0xFFbb6520, properties), new Item.Properties());
    public static final DeferredItem<Item> MOSS_SLIME_DNA = ITEMS.registerItem("moss_slime_dna", properties -> new DnaItem(0xFF4a6029, properties), new Item.Properties());
    public static final DeferredItem<Item> DEEPSLATE_SLIME_DNA = ITEMS.registerItem("deepslate_slime_dna", properties -> new DnaItem(0xFF3c3c42, properties), new Item.Properties());
    public static final DeferredItem<Item> GRANITE_SLIME_DNA = ITEMS.registerItem("granite_slime_dna", properties -> new DnaItem(0xFF835949, properties), new Item.Properties());
    public static final DeferredItem<Item> DIORITE_SLIME_DNA = ITEMS.registerItem("diorite_slime_dna", properties -> new DnaItem(0xFFadacad, properties), new Item.Properties());
    public static final DeferredItem<Item> CALCITE_SLIME_DNA = ITEMS.registerItem("calcite_slime_dna", properties -> new DnaItem(0xFFe9e9e3, properties), new Item.Properties());
    public static final DeferredItem<Item> TUFF_SLIME_DNA = ITEMS.registerItem("tuff_slime_dna", properties -> new DnaItem(0xFF55564c, properties), new Item.Properties());
    public static final DeferredItem<Item> DRIPSTONE_SLIME_DNA = ITEMS.registerItem("dripstone_slime_dna", properties -> new DnaItem(0xFF806155, properties), new Item.Properties());
    public static final DeferredItem<Item> PRISMARINE_SLIME_DNA = ITEMS.registerItem("prismarine_slime_dna", properties -> new DnaItem(0xFF529584, properties), new Item.Properties());
    public static final DeferredItem<Item> MAGMA_SLIME_DNA = ITEMS.registerItem("magma_slime_dna", properties -> new DnaItem(0xFF561f1f, properties), new Item.Properties());
    public static final DeferredItem<Item> OBSIDIAN_SLIME_DNA = ITEMS.registerItem("obsidian_slime_dna", properties -> new DnaItem(0xFF030106, properties), new Item.Properties());
    public static final DeferredItem<Item> NETHERRACK_SLIME_DNA = ITEMS.registerItem("netherrack_slime_dna", properties -> new DnaItem(0xFF763535, properties), new Item.Properties());
    public static final DeferredItem<Item> SOUL_SAND_SLIME_DNA = ITEMS.registerItem("soul_sand_slime_dna", properties -> new DnaItem(0xFF413127, properties), new Item.Properties());
    public static final DeferredItem<Item> SOUL_SOIL_SLIME_DNA = ITEMS.registerItem("soul_soil_slime_dna", properties -> new DnaItem(0xFF392b23, properties), new Item.Properties());
    public static final DeferredItem<Item> BLACKSTONE_SLIME_DNA = ITEMS.registerItem("blackstone_slime_dna", properties -> new DnaItem(0xFF201819, properties), new Item.Properties());
    public static final DeferredItem<Item> BASALT_SLIME_DNA = ITEMS.registerItem("basalt_slime_dna", properties -> new DnaItem(0xFF565456, properties), new Item.Properties());
    public static final DeferredItem<Item> ENDSTONE_SLIME_DNA = ITEMS.registerItem("endstone_slime_dna", properties -> new DnaItem(0xFFcece8e, properties), new Item.Properties());
    public static final DeferredItem<Item> QUARTZ_SLIME_DNA = ITEMS.registerItem("quartz_slime_dna", properties -> new DnaItem(0xFFe4ddd3, properties), new Item.Properties());
    public static final DeferredItem<Item> GLOWSTONE_SLIME_DNA = ITEMS.registerItem("glowstone_slime_dna", properties -> new DnaItem(0xFF784e27, properties), new Item.Properties());
    public static final DeferredItem<Item> AMETHYST_SLIME_DNA = ITEMS.registerItem("amethyst_slime_dna", properties -> new DnaItem(0xFF6b4da5, properties), new Item.Properties());
    public static final DeferredItem<Item> BROWN_MUSHROOM_SLIME_DNA = ITEMS.registerItem("brown_mushroom_slime_dna", properties -> new DnaItem(0xFF967251, properties), new Item.Properties());
    public static final DeferredItem<Item> RED_MUSHROOM_SLIME_DNA = ITEMS.registerItem("red_mushroom_slime_dna", properties -> new DnaItem(0xFFc02624, properties), new Item.Properties());
    public static final DeferredItem<Item> CACTUS_SLIME_DNA = ITEMS.registerItem("cactus_slime_dna", properties -> new DnaItem(0xFF476d21, properties), new Item.Properties());
    public static final DeferredItem<Item> COAL_SLIME_DNA = ITEMS.registerItem("coal_slime_dna", properties -> new DnaItem(0xFF3b3d3b, properties), new Item.Properties());
    public static final DeferredItem<Item> GRAVEL_SLIME_DNA = ITEMS.registerItem("gravel_slime_dna", properties -> new DnaItem(0xFF4a444b, properties), new Item.Properties());

    public static final DeferredItem<Item> DIRT_SLIME_SPAWN_EGG = ITEMS.registerItem("dirt_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.DIRT_SLIME.get(), 0x5e3a1c, 0x885022, properties), new Item.Properties());
    public static final DeferredItem<Item> STONE_SLIME_SPAWN_EGG = ITEMS.registerItem("stone_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.STONE_SLIME.get(), 0x4a4545, 0x918b8b, properties), new Item.Properties());
    public static final DeferredItem<Item> IRON_SLIME_SPAWN_EGG = ITEMS.registerItem("iron_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.IRON_SLIME.get(), 0x898c8a, 0xcfd1d0, properties), new Item.Properties());
    public static final DeferredItem<Item> COPPER_SLIME_SPAWN_EGG = ITEMS.registerItem("copper_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.COPPER_SLIME.get(), 0x6a3e15, 0xb87333, properties), new Item.Properties());
    public static final DeferredItem<Item> GOLD_SLIME_SPAWN_EGG = ITEMS.registerItem("gold_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.GOLD_SLIME.get(), 0xa5953f, 0xd9c245, properties), new Item.Properties());
    public static final DeferredItem<Item> DIAMOND_SLIME_SPAWN_EGG = ITEMS.registerItem("diamond_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.DIAMOND_SLIME.get(), 0x178f9c, 0x2fcadc, properties), new Item.Properties());
    public static final DeferredItem<Item> NETHERITE_SLIME_SPAWN_EGG = ITEMS.registerItem("netherite_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.NETHERITE_SLIME.get(), 0x4c2b2b, 0x453a3a, properties), new Item.Properties());
    public static final DeferredItem<Item> LAPIS_SLIME_SPAWN_EGG = ITEMS.registerItem("lapis_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.LAPIS_SLIME.get(), 0x1c41ba, 0x2853e0, properties), new Item.Properties());
    public static final DeferredItem<Item> REDSTONE_SLIME_SPAWN_EGG = ITEMS.registerItem("redstone_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.REDSTONE_SLIME.get(), 0xa10505, 0xc92020, properties), new Item.Properties());
    public static final DeferredItem<Item> OAK_SLIME_SPAWN_EGG = ITEMS.registerItem("oak_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.OAK_SLIME.get(), 0xa69d6f, 0xded4a4, properties), new Item.Properties());
    public static final DeferredItem<Item> SAND_SLIME_SPAWN_EGG = ITEMS.registerItem("sand_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.SAND_SLIME.get(), 0xf7f7c6, 0xfcfcd9, properties), new Item.Properties());
    public static final DeferredItem<Item> ANDESITE_SLIME_SPAWN_EGG = ITEMS.registerItem("andesite_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.ANDESITE_SLIME.get(), 0xFF9d9e9a, 0xFF7a7b77, properties), new Item.Properties());
    public static final DeferredItem<Item> SNOW_SLIME_SPAWN_EGG = ITEMS.registerItem("snow_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.SNOW_SLIME.get(), 0xFFf2fcfc, 0xFFc2dcdc, properties), new Item.Properties());
    public static final DeferredItem<Item> ICE_SLIME_SPAWN_EGG = ITEMS.registerItem("ice_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.ICE_SLIME.get(), 0xFF89b1fc, 0xFF637fbf, properties), new Item.Properties());
    public static final DeferredItem<Item> MUD_SLIME_SPAWN_EGG = ITEMS.registerItem("mud_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.MUD_SLIME.get(), 0xFF363339, 0xFF272527, properties), new Item.Properties());
    public static final DeferredItem<Item> CLAY_SLIME_SPAWN_EGG = ITEMS.registerItem("clay_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.CLAY_SLIME.get(), 0xFF9ca2ac, 0xFF7a8087, properties), new Item.Properties());
    public static final DeferredItem<Item> RED_SAND_SLIME_SPAWN_EGG = ITEMS.registerItem("red_sand_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.RED_SAND_SLIME.get(), 0xFFbb6520, 0xFF8d4917, properties), new Item.Properties());
    public static final DeferredItem<Item> MOSS_SLIME_SPAWN_EGG = ITEMS.registerItem("moss_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.MOSS_SLIME.get(), 0xFF4a6029, 0xFF35471f, properties), new Item.Properties());
    public static final DeferredItem<Item> DEEPSLATE_SLIME_SPAWN_EGG = ITEMS.registerItem("deepslate_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.DEEPSLATE_SLIME.get(), 0xFF3c3c42, 0xFF292930, properties), new Item.Properties());
    public static final DeferredItem<Item> GRANITE_SLIME_SPAWN_EGG = ITEMS.registerItem("granite_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.GRANITE_SLIME.get(), 0xFF835949, 0xFF624137, properties), new Item.Properties());
    public static final DeferredItem<Item> DIORITE_SLIME_SPAWN_EGG = ITEMS.registerItem("diorite_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.DIORITE_SLIME.get(), 0xFFadacad, 0xFF848485, properties), new Item.Properties());
    public static final DeferredItem<Item> CALCITE_SLIME_SPAWN_EGG = ITEMS.registerItem("calcite_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.CALCITE_SLIME.get(), 0xFFe9e9e3, 0xFFb3b3b0, properties), new Item.Properties());
    public static final DeferredItem<Item> TUFF_SLIME_SPAWN_EGG = ITEMS.registerItem("tuff_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.TUFF_SLIME.get(), 0xFF55564c, 0xFF3e4037, properties), new Item.Properties());
    public static final DeferredItem<Item> DRIPSTONE_SLIME_SPAWN_EGG = ITEMS.registerItem("dripstone_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.DRIPSTONE_SLIME.get(), 0xFF806155, 0xFF5f493f, properties), new Item.Properties());
    public static final DeferredItem<Item> PRISMARINE_SLIME_SPAWN_EGG = ITEMS.registerItem("prismarine_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.PRISMARINE_SLIME.get(), 0xFF529584, 0xFF3d6f61, properties), new Item.Properties());
    public static final DeferredItem<Item> MAGMA_SLIME_SPAWN_EGG = ITEMS.registerItem("magma_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.MAGMA_SLIME.get(), 0xFF561f1f, 0xFF3e1616, properties), new Item.Properties());
    public static final DeferredItem<Item> OBSIDIAN_SLIME_SPAWN_EGG = ITEMS.registerItem("obsidian_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.OBSIDIAN_SLIME.get(), 0xFF030106, 0xFF010103, properties), new Item.Properties());
    public static final DeferredItem<Item> NETHERRACK_SLIME_SPAWN_EGG = ITEMS.registerItem("netherrack_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.NETHERRACK_SLIME.get(), 0xFF763535, 0xFF582828, properties), new Item.Properties());
    public static final DeferredItem<Item> SOUL_SAND_SLIME_SPAWN_EGG = ITEMS.registerItem("soul_sand_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.SOUL_SAND_SLIME.get(), 0xFF413127, 0xFF31241c, properties), new Item.Properties());
    public static final DeferredItem<Item> SOUL_SOIL_SLIME_SPAWN_EGG = ITEMS.registerItem("soul_soil_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.SOUL_SOIL_SLIME.get(), 0xFF392b23, 0xFF2a1f1b, properties), new Item.Properties());
    public static final DeferredItem<Item> BLACKSTONE_SLIME_SPAWN_EGG = ITEMS.registerItem("blackstone_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.BLACKSTONE_SLIME.get(), 0xFF201819, 0xFF181212, properties), new Item.Properties());
    public static final DeferredItem<Item> BASALT_SLIME_SPAWN_EGG = ITEMS.registerItem("basalt_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.BASALT_SLIME.get(), 0xFF565456, 0xFF3f3e3f, properties), new Item.Properties());
    public static final DeferredItem<Item> ENDSTONE_SLIME_SPAWN_EGG = ITEMS.registerItem("endstone_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.ENDSTONE_SLIME.get(), 0xFFcece8e, 0xFF99996b, properties), new Item.Properties());
    public static final DeferredItem<Item> QUARTZ_SLIME_SPAWN_EGG = ITEMS.registerItem("quartz_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.QUARTZ_SLIME.get(), 0xFFe4ddd3, 0xFFb2aea6, properties), new Item.Properties());
    public static final DeferredItem<Item> GLOWSTONE_SLIME_SPAWN_EGG = ITEMS.registerItem("glowstone_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.GLOWSTONE_SLIME.get(), 0xFF784e27, 0xFF5b3b1d, properties), new Item.Properties());
    public static final DeferredItem<Item> AMETHYST_SLIME_SPAWN_EGG = ITEMS.registerItem("amethyst_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.AMETHYST_SLIME.get(), 0xFF6b4da5, 0xFF4f3a7d, properties), new Item.Properties());
    public static final DeferredItem<Item> BROWN_MUSHROOM_SLIME_SPAWN_EGG = ITEMS.registerItem("brown_mushroom_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.BROWN_MUSHROOM_SLIME.get(), 0xFF967251, 0xFF73553e, properties), new Item.Properties());
    public static final DeferredItem<Item> RED_MUSHROOM_SLIME_SPAWN_EGG = ITEMS.registerItem("red_mushroom_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.RED_MUSHROOM_SLIME.get(), 0xFFc02624, 0xFF901c1b, properties), new Item.Properties());
    public static final DeferredItem<Item> CACTUS_SLIME_SPAWN_EGG = ITEMS.registerItem("cactus_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.CACTUS_SLIME.get(), 0xFF476d21, 0xFF35511a, properties), new Item.Properties());
    public static final DeferredItem<Item> COAL_SLIME_SPAWN_EGG = ITEMS.registerItem("coal_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.COAL_SLIME.get(), 0x3b3d3b, 0x222222, properties), new Item.Properties());
    public static final DeferredItem<Item> GRAVEL_SLIME_SPAWN_EGG = ITEMS.registerItem("gravel_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.GRAVEL_SLIME.get(), 0x4a444b, 0x2f2f2f, properties), new Item.Properties());
    public static final DeferredItem<Item> ENERGY_SLIME_SPAWN_EGG = ITEMS.registerItem("energy_slime_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.ENERGY_SLIME.get(), 0xffff70, 0xFFFF00, properties), new Item.Properties());

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}