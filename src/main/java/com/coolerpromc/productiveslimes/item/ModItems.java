package com.coolerpromc.productiveslimes.item;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.item.custom.DnaItem;
import com.coolerpromc.productiveslimes.item.custom.EnergyMultiplierUpgrade;
import com.coolerpromc.productiveslimes.item.custom.GuidebookItem;
import com.coolerpromc.productiveslimes.item.custom.SlimeballItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ProductiveSlimes.MODID);

    public static final DeferredItem<Item> GUIDEBOOK = ITEMS.register("guidebook", GuidebookItem::new);

    public static final DeferredItem<Item> ENERGY_MULTIPLIER_UPGRADE = ITEMS.register("energy_multiplier_upgrade", () -> new EnergyMultiplierUpgrade(new Item.Properties()));
    public static final DeferredItem<BlockItem> FLUID_TANK_ITEM = ITEMS.register("fluid_tank", () -> new BlockItem(ModBlocks.FLUID_TANK.get(), new Item.Properties()));
    public static final DeferredItem<Item> DIRT_SLIME_BALL = ITEMS.register("dirt_slimeball", () -> new SlimeballItem(0xFF866043));
    public static final DeferredItem<Item> STONE_SLIME_BALL = ITEMS.register("stone_slimeball", () -> new SlimeballItem(0xFF6F6969));
    public static final DeferredItem<Item> IRON_SLIME_BALL = ITEMS.register("iron_slimeball", () -> new SlimeballItem(0xFF898c8a));
    public static final DeferredItem<Item> COPPER_SLIME_BALL = ITEMS.register("copper_slimeball", () -> new SlimeballItem(0xFF6a3e15));
    public static final DeferredItem<Item> GOLD_SLIME_BALL = ITEMS.register("gold_slimeball", () -> new SlimeballItem(0xFFCCC16A));
    public static final DeferredItem<Item> DIAMOND_SLIME_BALL = ITEMS.register("diamond_slimeball", () -> new SlimeballItem(0xFF2BB7C7));
    public static final DeferredItem<Item> NETHERITE_SLIME_BALL = ITEMS.register("netherite_slimeball", () -> new SlimeballItem(0xFF704545));
    public static final DeferredItem<Item> LAPIS_SLIME_BALL = ITEMS.register("lapis_slimeball", () -> new SlimeballItem(0xFF4165E6));
    public static final DeferredItem<Item> REDSTONE_SLIME_BALL = ITEMS.register("redstone_slimeball", () -> new SlimeballItem(0xFFD43030));
    public static final DeferredItem<Item> OAK_SLIME_BALL = ITEMS.register("oak_slimeball", () -> new SlimeballItem(0xFFa69d6f));
    public static final DeferredItem<Item> SAND_SLIME_BALL = ITEMS.register("sand_slimeball", () -> new SlimeballItem(0xFFf7f7c6));
    public static final DeferredItem<Item> ANDESITE_SLIME_BALL = ITEMS.register("andesite_slimeball", () -> new SlimeballItem(0xFF9d9e9a));
    public static final DeferredItem<Item> SNOW_SLIME_BALL = ITEMS.register("snow_slimeball", () -> new SlimeballItem(0xFFf2fcfc));
    public static final DeferredItem<Item> ICE_SLIME_BALL = ITEMS.register("ice_slimeball", () -> new SlimeballItem(0xFF89b1fc));
    public static final DeferredItem<Item> MUD_SLIME_BALL = ITEMS.register("mud_slimeball", () -> new SlimeballItem(0xFF363339));
    public static final DeferredItem<Item> CLAY_SLIME_BALL = ITEMS.register("clay_slimeball", () -> new SlimeballItem(0xFF9ca2ac));
    public static final DeferredItem<Item> RED_SAND_SLIME_BALL = ITEMS.register("red_sand_slimeball", () -> new SlimeballItem(0xFFbb6520));
    public static final DeferredItem<Item> MOSS_SLIME_BALL = ITEMS.register("moss_slimeball", () -> new SlimeballItem(0xFF4a6029));
    public static final DeferredItem<Item> DEEPSLATE_SLIME_BALL = ITEMS.register("deepslate_slimeball", () -> new SlimeballItem(0xFF3c3c42));
    public static final DeferredItem<Item> GRANITE_SLIME_BALL = ITEMS.register("granite_slimeball", () -> new SlimeballItem(0xFF835949));
    public static final DeferredItem<Item> DIORITE_SLIME_BALL = ITEMS.register("diorite_slimeball", () -> new SlimeballItem(0xFFadacad));
    public static final DeferredItem<Item> CALCITE_SLIME_BALL = ITEMS.register("calcite_slimeball", () -> new SlimeballItem(0xFFe9e9e3));
    public static final DeferredItem<Item> TUFF_SLIME_BALL = ITEMS.register("tuff_slimeball", () -> new SlimeballItem(0xFF55564c));
    public static final DeferredItem<Item> DRIPSTONE_SLIME_BALL = ITEMS.register("dripstone_slimeball", () -> new SlimeballItem(0xFF806155));
    public static final DeferredItem<Item> PRISMARINE_SLIME_BALL = ITEMS.register("prismarine_slimeball", () -> new SlimeballItem(0xFF529584));
    public static final DeferredItem<Item> MAGMA_SLIME_BALL = ITEMS.register("magma_slimeball", () -> new SlimeballItem(0xFF561f1f));
    public static final DeferredItem<Item> OBSIDIAN_SLIME_BALL = ITEMS.register("obsidian_slimeball", () -> new SlimeballItem(0xFF030106));
    public static final DeferredItem<Item> NETHERRACK_SLIME_BALL = ITEMS.register("netherrack_slimeball", () -> new SlimeballItem(0xFF763535));
    public static final DeferredItem<Item> SOUL_SAND_SLIME_BALL = ITEMS.register("soul_sand_slimeball", () -> new SlimeballItem(0xFF413127));
    public static final DeferredItem<Item> SOUL_SOIL_SLIME_BALL = ITEMS.register("soul_soil_slimeball", () -> new SlimeballItem(0xFF392b23));
    public static final DeferredItem<Item> BLACKSTONE_SLIME_BALL = ITEMS.register("blackstone_slimeball", () -> new SlimeballItem(0xFF201819));
    public static final DeferredItem<Item> BASALT_SLIME_BALL = ITEMS.register("basalt_slimeball", () -> new SlimeballItem(0xFF565456));
    public static final DeferredItem<Item> ENDSTONE_SLIME_BALL = ITEMS.register("endstone_slimeball", () -> new SlimeballItem(0xFFcece8e));
    public static final DeferredItem<Item> QUARTZ_SLIME_BALL = ITEMS.register("quartz_slimeball", () -> new SlimeballItem(0xFFe4ddd3));
    public static final DeferredItem<Item> GLOWSTONE_SLIME_BALL = ITEMS.register("glowstone_slimeball", () -> new SlimeballItem(0xFF784e27));
    public static final DeferredItem<Item> AMETHYST_SLIME_BALL = ITEMS.register("amethyst_slimeball", () -> new SlimeballItem(0xFF6b4da5));
    public static final DeferredItem<Item> BROWN_MUSHROOM_SLIME_BALL = ITEMS.register("brown_mushroom_slimeball", () -> new SlimeballItem(0xFF967251));
    public static final DeferredItem<Item> RED_MUSHROOM_SLIME_BALL = ITEMS.register("red_mushroom_slimeball", () -> new SlimeballItem(0xFFc02624));
    public static final DeferredItem<Item> CACTUS_SLIME_BALL = ITEMS.register("cactus_slimeball", () -> new SlimeballItem(0xFF476d21));
    public static final DeferredItem<Item> COAL_SLIME_BALL = ITEMS.register("coal_slimeball", () -> new SlimeballItem(0xFF3b3d3b));
    public static final DeferredItem<Item> GRAVEL_SLIME_BALL = ITEMS.register("gravel_slimeball", () -> new SlimeballItem(0xFF4a444b));
    public static final DeferredItem<Item> ENERGY_SLIME_BALL = ITEMS.register("energy_slimeball", () -> new SlimeballItem(0xFFFFFF70));

    public static final DeferredItem<Item> SLIME_DNA = ITEMS.register("slime_dna", () -> new DnaItem(0xFF7BC35C));
    public static final DeferredItem<Item> DIRT_SLIME_DNA = ITEMS.register("dirt_slime_dna", () -> new DnaItem(0xFF866043));
    public static final DeferredItem<Item> STONE_SLIME_DNA = ITEMS.register("stone_slime_dna", () -> new DnaItem(0xFF6F6969));
    public static final DeferredItem<Item> IRON_SLIME_DNA = ITEMS.register("iron_slime_dna", () -> new DnaItem(0xFF898c8a));
    public static final DeferredItem<Item> COPPER_SLIME_DNA = ITEMS.register("copper_slime_dna", () -> new DnaItem(0xFF6a3e15));
    public static final DeferredItem<Item> GOLD_SLIME_DNA = ITEMS.register("gold_slime_dna", () -> new DnaItem(0xFFCCC16A));
    public static final DeferredItem<Item> DIAMOND_SLIME_DNA = ITEMS.register("diamond_slime_dna", () -> new DnaItem(0xFF2BB7C7));
    public static final DeferredItem<Item> NETHERITE_SLIME_DNA = ITEMS.register("netherite_slime_dna", () -> new DnaItem(0xFF704545));
    public static final DeferredItem<Item> LAPIS_SLIME_DNA = ITEMS.register("lapis_slime_dna", () -> new DnaItem(0xFF4165E6));
    public static final DeferredItem<Item> REDSTONE_SLIME_DNA = ITEMS.register("redstone_slime_dna", () -> new DnaItem(0xFFD43030));
    public static final DeferredItem<Item> OAK_SLIME_DNA = ITEMS.register("oak_slime_dna", () -> new DnaItem(0xFFa69d6f));
    public static final DeferredItem<Item> SAND_SLIME_DNA = ITEMS.register("sand_slime_dna", () -> new DnaItem(0xFFf7f7c6));
    public static final DeferredItem<Item> ANDESITE_SLIME_DNA = ITEMS.register("andesite_slime_dna", () -> new DnaItem(0xFF9d9e9a));
    public static final DeferredItem<Item> SNOW_SLIME_DNA = ITEMS.register("snow_slime_dna", () -> new DnaItem(0xFFf2fcfc));
    public static final DeferredItem<Item> ICE_SLIME_DNA = ITEMS.register("ice_slime_dna", () -> new DnaItem(0xFF89b1fc));
    public static final DeferredItem<Item> MUD_SLIME_DNA = ITEMS.register("mud_slime_dna", () -> new DnaItem(0xFF363339));
    public static final DeferredItem<Item> CLAY_SLIME_DNA = ITEMS.register("clay_slime_dna", () -> new DnaItem(0xFF9ca2ac));
    public static final DeferredItem<Item> RED_SAND_SLIME_DNA = ITEMS.register("red_sand_slime_dna", () -> new DnaItem(0xFFbb6520));
    public static final DeferredItem<Item> MOSS_SLIME_DNA = ITEMS.register("moss_slime_dna", () -> new DnaItem(0xFF4a6029));
    public static final DeferredItem<Item> DEEPSLATE_SLIME_DNA = ITEMS.register("deepslate_slime_dna", () -> new DnaItem(0xFF3c3c42));
    public static final DeferredItem<Item> GRANITE_SLIME_DNA = ITEMS.register("granite_slime_dna", () -> new DnaItem(0xFF835949));
    public static final DeferredItem<Item> DIORITE_SLIME_DNA = ITEMS.register("diorite_slime_dna", () -> new DnaItem(0xFFadacad));
    public static final DeferredItem<Item> CALCITE_SLIME_DNA = ITEMS.register("calcite_slime_dna", () -> new DnaItem(0xFFe9e9e3));
    public static final DeferredItem<Item> TUFF_SLIME_DNA = ITEMS.register("tuff_slime_dna", () -> new DnaItem(0xFF55564c));
    public static final DeferredItem<Item> DRIPSTONE_SLIME_DNA = ITEMS.register("dripstone_slime_dna", () -> new DnaItem(0xFF806155));
    public static final DeferredItem<Item> PRISMARINE_SLIME_DNA = ITEMS.register("prismarine_slime_dna", () -> new DnaItem(0xFF529584));
    public static final DeferredItem<Item> MAGMA_SLIME_DNA = ITEMS.register("magma_slime_dna", () -> new DnaItem(0xFF561f1f));
    public static final DeferredItem<Item> OBSIDIAN_SLIME_DNA = ITEMS.register("obsidian_slime_dna", () -> new DnaItem(0xFF030106));
    public static final DeferredItem<Item> NETHERRACK_SLIME_DNA = ITEMS.register("netherrack_slime_dna", () -> new DnaItem(0xFF763535));
    public static final DeferredItem<Item> SOUL_SAND_SLIME_DNA = ITEMS.register("soul_sand_slime_dna", () -> new DnaItem(0xFF413127));
    public static final DeferredItem<Item> SOUL_SOIL_SLIME_DNA = ITEMS.register("soul_soil_slime_dna", () -> new DnaItem(0xFF392b23));
    public static final DeferredItem<Item> BLACKSTONE_SLIME_DNA = ITEMS.register("blackstone_slime_dna", () -> new DnaItem(0xFF201819));
    public static final DeferredItem<Item> BASALT_SLIME_DNA = ITEMS.register("basalt_slime_dna", () -> new DnaItem(0xFF565456));
    public static final DeferredItem<Item> ENDSTONE_SLIME_DNA = ITEMS.register("endstone_slime_dna", () -> new DnaItem(0xFFcece8e));
    public static final DeferredItem<Item> QUARTZ_SLIME_DNA = ITEMS.register("quartz_slime_dna", () -> new DnaItem(0xFFe4ddd3));
    public static final DeferredItem<Item> GLOWSTONE_SLIME_DNA = ITEMS.register("glowstone_slime_dna", () -> new DnaItem(0xFF784e27));
    public static final DeferredItem<Item> AMETHYST_SLIME_DNA = ITEMS.register("amethyst_slime_dna", () -> new DnaItem(0xFF6b4da5));
    public static final DeferredItem<Item> BROWN_MUSHROOM_SLIME_DNA = ITEMS.register("brown_mushroom_slime_dna", () -> new DnaItem(0xFF967251));
    public static final DeferredItem<Item> RED_MUSHROOM_SLIME_DNA = ITEMS.register("red_mushroom_slime_dna", () -> new DnaItem(0xFFc02624));
    public static final DeferredItem<Item> CACTUS_SLIME_DNA = ITEMS.register("cactus_slime_dna", () -> new DnaItem(0xFF476d21));
    public static final DeferredItem<Item> COAL_SLIME_DNA = ITEMS.register("coal_slime_dna", () -> new DnaItem(0xFF3b3d3b));
    public static final DeferredItem<Item> GRAVEL_SLIME_DNA = ITEMS.register("gravel_slime_dna", () -> new DnaItem(0xFF4a444b));

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
    public static final DeferredItem<Item> SAND_SLIME_SPAWN_EGG = ITEMS.register("sand_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.SAND_SLIME.get(), 0xf7f7c6, 0xfcfcd9, new Item.Properties()));
    public static final DeferredItem<Item> ANDESITE_SLIME_SPAWN_EGG = ITEMS.register("andesite_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.ANDESITE_SLIME.get(), 0xFF9d9e9a, 0xFF7a7b77, new Item.Properties()));
    public static final DeferredItem<Item> SNOW_SLIME_SPAWN_EGG = ITEMS.register("snow_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.SNOW_SLIME.get(), 0xFFf2fcfc, 0xFFc2dcdc, new Item.Properties()));
    public static final DeferredItem<Item> ICE_SLIME_SPAWN_EGG = ITEMS.register("ice_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.ICE_SLIME.get(), 0xFF89b1fc, 0xFF637fbf, new Item.Properties()));
    public static final DeferredItem<Item> MUD_SLIME_SPAWN_EGG = ITEMS.register("mud_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.MUD_SLIME.get(), 0xFF363339, 0xFF272527, new Item.Properties()));
    public static final DeferredItem<Item> CLAY_SLIME_SPAWN_EGG = ITEMS.register("clay_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.CLAY_SLIME.get(), 0xFF9ca2ac, 0xFF7a8087, new Item.Properties()));
    public static final DeferredItem<Item> RED_SAND_SLIME_SPAWN_EGG = ITEMS.register("red_sand_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.RED_SAND_SLIME.get(), 0xFFbb6520, 0xFF8d4917, new Item.Properties()));
    public static final DeferredItem<Item> MOSS_SLIME_SPAWN_EGG = ITEMS.register("moss_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.MOSS_SLIME.get(), 0xFF4a6029, 0xFF35471f, new Item.Properties()));
    public static final DeferredItem<Item> DEEPSLATE_SLIME_SPAWN_EGG = ITEMS.register("deepslate_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.DEEPSLATE_SLIME.get(), 0xFF3c3c42, 0xFF292930, new Item.Properties()));
    public static final DeferredItem<Item> GRANITE_SLIME_SPAWN_EGG = ITEMS.register("granite_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.GRANITE_SLIME.get(), 0xFF835949, 0xFF624137, new Item.Properties()));
    public static final DeferredItem<Item> DIORITE_SLIME_SPAWN_EGG = ITEMS.register("diorite_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.DIORITE_SLIME.get(), 0xFFadacad, 0xFF848485, new Item.Properties()));
    public static final DeferredItem<Item> CALCITE_SLIME_SPAWN_EGG = ITEMS.register("calcite_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.CALCITE_SLIME.get(), 0xFFe9e9e3, 0xFFb3b3b0, new Item.Properties()));
    public static final DeferredItem<Item> TUFF_SLIME_SPAWN_EGG = ITEMS.register("tuff_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.TUFF_SLIME.get(), 0xFF55564c, 0xFF3e4037, new Item.Properties()));
    public static final DeferredItem<Item> DRIPSTONE_SLIME_SPAWN_EGG = ITEMS.register("dripstone_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.DRIPSTONE_SLIME.get(), 0xFF806155, 0xFF5f493f, new Item.Properties()));
    public static final DeferredItem<Item> PRISMARINE_SLIME_SPAWN_EGG = ITEMS.register("prismarine_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.PRISMARINE_SLIME.get(), 0xFF529584, 0xFF3d6f61, new Item.Properties()));
    public static final DeferredItem<Item> MAGMA_SLIME_SPAWN_EGG = ITEMS.register("magma_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.MAGMA_SLIME.get(), 0xFF561f1f, 0xFF3e1616, new Item.Properties()));
    public static final DeferredItem<Item> OBSIDIAN_SLIME_SPAWN_EGG = ITEMS.register("obsidian_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.OBSIDIAN_SLIME.get(), 0xFF030106, 0xFF010103, new Item.Properties()));
    public static final DeferredItem<Item> NETHERRACK_SLIME_SPAWN_EGG = ITEMS.register("netherrack_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.NETHERRACK_SLIME.get(), 0xFF763535, 0xFF582828, new Item.Properties()));
    public static final DeferredItem<Item> SOUL_SAND_SLIME_SPAWN_EGG = ITEMS.register("soul_sand_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.SOUL_SAND_SLIME.get(), 0xFF413127, 0xFF31241c, new Item.Properties()));
    public static final DeferredItem<Item> SOUL_SOIL_SLIME_SPAWN_EGG = ITEMS.register("soul_soil_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.SOUL_SOIL_SLIME.get(), 0xFF392b23, 0xFF2a1f1b, new Item.Properties()));
    public static final DeferredItem<Item> BLACKSTONE_SLIME_SPAWN_EGG = ITEMS.register("blackstone_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.BLACKSTONE_SLIME.get(), 0xFF201819, 0xFF181212, new Item.Properties()));
    public static final DeferredItem<Item> BASALT_SLIME_SPAWN_EGG = ITEMS.register("basalt_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.BASALT_SLIME.get(), 0xFF565456, 0xFF3f3e3f, new Item.Properties()));
    public static final DeferredItem<Item> ENDSTONE_SLIME_SPAWN_EGG = ITEMS.register("endstone_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.ENDSTONE_SLIME.get(), 0xFFcece8e, 0xFF99996b, new Item.Properties()));
    public static final DeferredItem<Item> QUARTZ_SLIME_SPAWN_EGG = ITEMS.register("quartz_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.QUARTZ_SLIME.get(), 0xFFe4ddd3, 0xFFb2aea6, new Item.Properties()));
    public static final DeferredItem<Item> GLOWSTONE_SLIME_SPAWN_EGG = ITEMS.register("glowstone_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.GLOWSTONE_SLIME.get(), 0xFF784e27, 0xFF5b3b1d, new Item.Properties()));
    public static final DeferredItem<Item> AMETHYST_SLIME_SPAWN_EGG = ITEMS.register("amethyst_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.AMETHYST_SLIME.get(), 0xFF6b4da5, 0xFF4f3a7d, new Item.Properties()));
    public static final DeferredItem<Item> BROWN_MUSHROOM_SLIME_SPAWN_EGG = ITEMS.register("brown_mushroom_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.BROWN_MUSHROOM_SLIME.get(), 0xFF967251, 0xFF73553e, new Item.Properties()));
    public static final DeferredItem<Item> RED_MUSHROOM_SLIME_SPAWN_EGG = ITEMS.register("red_mushroom_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.RED_MUSHROOM_SLIME.get(), 0xFFc02624, 0xFF901c1b, new Item.Properties()));
    public static final DeferredItem<Item> CACTUS_SLIME_SPAWN_EGG = ITEMS.register("cactus_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.CACTUS_SLIME.get(), 0xFF476d21, 0xFF35511a, new Item.Properties()));
    public static final DeferredItem<Item> COAL_SLIME_SPAWN_EGG = ITEMS.register("coal_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.COAL_SLIME.get(), 0x3b3d3b, 0x222222, new Item.Properties()));
    public static final DeferredItem<Item> GRAVEL_SLIME_SPAWN_EGG = ITEMS.register("gravel_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.GRAVEL_SLIME.get(), 0x4a444b, 0x2f2f2f, new Item.Properties()));
    public static final DeferredItem<Item> ENERGY_SLIME_SPAWN_EGG = ITEMS.register("energy_slime_spawn_egg",
            () -> new SpawnEggItem(ModEntities.ENERGY_SLIME.get(), 0xffff70, 0xFFFF00, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}