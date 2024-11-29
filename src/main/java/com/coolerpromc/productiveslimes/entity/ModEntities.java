package com.coolerpromc.productiveslimes.entity;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.entity.slime.*;
import com.coolerpromc.productiveslimes.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, ProductiveSlimes.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> DIRT_SLIME = registerSlime("dirt_slime", 1500, 0xFF866043, ModItems.DIRT_SLIME_BALL, Items.DIRT);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> STONE_SLIME = registerSlime("stone_slime", 1700, 0xFF4a4545, ModItems.STONE_SLIME_BALL, Items.STONE);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> IRON_SLIME = registerSlime("iron_slime", 3000, 0xFF898c8a, ModItems.IRON_SLIME_BALL, Items.IRON_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> COPPER_SLIME = registerSlime("copper_slime", 2500, 0xFF6a3e15, ModItems.COPPER_SLIME_BALL, Items.COPPER_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> GOLD_SLIME = registerSlime("gold_slime", 3200, 0xFFa5953f, ModItems.GOLD_SLIME_BALL, Items.GOLD_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> DIAMOND_SLIME = registerSlime("diamond_slime", 4000, 0xFF178f9c, ModItems.DIAMOND_SLIME_BALL, Items.DIAMOND_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> NETHERITE_SLIME = registerSlime("netherite_slime", 5000, 0xFF4c2b2b, ModItems.NETHERITE_SLIME_BALL, Items.NETHERITE_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> LAPIS_SLIME = registerSlime("lapis_slime", 2500, 0xFF1c41ba, ModItems.LAPIS_SLIME_BALL, Items.LAPIS_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> REDSTONE_SLIME = registerSlime("redstone_slime", 2700, 0xFFa10505, ModItems.REDSTONE_SLIME_BALL, Items.REDSTONE_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> OAK_SLIME = registerSlime("oak_slime", 1500, 0xFFa69d6f, ModItems.OAK_SLIME_BALL, Items.OAK_LOG);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> SAND_SLIME = registerSlime("sand_slime", 1500, 0xFFf7f7c6, ModItems.SAND_SLIME_BALL, Items.SAND);
    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> ANDESITE_SLIME = registerSlime("andesite_slime", 1500, 0xFF9d9e9a, ModItems.ANDESITE_SLIME_BALL, Items.ANDESITE);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> SNOW_SLIME = registerSlime("snow_slime", 1800, 0xFFf2fcfc, ModItems.SNOW_SLIME_BALL, Items.SNOW_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> ICE_SLIME = registerSlime("ice_slime", 1800, 0xFF89b1fc, ModItems.ICE_SLIME_BALL, Items.ICE);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> MUD_SLIME = registerSlime("mud_slime", 1500, 0xFF363339, ModItems.MUD_SLIME_BALL, Items.MUD);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> CLAY_SLIME = registerSlime("clay_slime", 1500, 0xFF9ca2ac, ModItems.CLAY_SLIME_BALL, Items.CLAY);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> RED_SAND_SLIME = registerSlime("red_sand_slime", 1500, 0xFFbb6520, ModItems.RED_SAND_SLIME_BALL, Items.RED_SAND);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> MOSS_SLIME = registerSlime("moss_slime", 1500, 0xFF4a6029, ModItems.MOSS_SLIME_BALL, Items.MOSS_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> DEEPSLATE_SLIME = registerSlime("deepslate_slime", 1500, 0xFF3c3c42, ModItems.DEEPSLATE_SLIME_BALL, Items.DEEPSLATE);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> GRANITE_SLIME = registerSlime("granite_slime", 1500, 0xFF835949, ModItems.GRANITE_SLIME_BALL, Items.GRANITE);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> DIORITE_SLIME = registerSlime("diorite_slime", 1500, 0xFFadacad, ModItems.DIORITE_SLIME_BALL, Items.DIORITE);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> CALCITE_SLIME = registerSlime("calcite_slime", 1500, 0xFFe9e9e3, ModItems.CALCITE_SLIME_BALL, Items.CALCITE);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> TUFF_SLIME = registerSlime("tuff_slime", 1500, 0xFF55564c, ModItems.TUFF_SLIME_BALL, Items.TUFF);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> DRIPSTONE_SLIME = registerSlime("dripstone_slime", 1500, 0xFF806155, ModItems.DRIPSTONE_SLIME_BALL, Items.DRIPSTONE_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> NETHERRACK_SLIME = registerSlime("netherrack_slime", 1500, 0xFF763535, ModItems.NETHERRACK_SLIME_BALL, Items.NETHERRACK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> PRISMARINE_SLIME = registerSlime("prismarine_slime", 3000, 0xFF529584, ModItems.PRISMARINE_SLIME_BALL, Items.PRISMARINE);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> MAGMA_SLIME = registerSlime("magma_slime", 2500, 0xFF561f1f, ModItems.MAGMA_SLIME_BALL, Items.MAGMA_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> OBSIDIAN_SLIME = registerSlime("obsidian_slime", 3500, 0xFF030106, ModItems.OBSIDIAN_SLIME_BALL, Items.OBSIDIAN);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> SOUL_SAND_SLIME = registerSlime("soul_sand_slime", 2000, 0xFF413127, ModItems.SOUL_SAND_SLIME_BALL, Items.SOUL_SAND);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> SOUL_SOIL_SLIME = registerSlime("soul_soil_slime", 2000, 0xFF392b23, ModItems.SOUL_SOIL_SLIME_BALL, Items.SOUL_SOIL);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> BLACKSTONE_SLIME = registerSlime("blackstone_slime", 1500, 0xFF201819, ModItems.BLACKSTONE_SLIME_BALL, Items.BLACKSTONE);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> BASALT_SLIME = registerSlime("basalt_slime", 1500, 0xFF565456, ModItems.BASALT_SLIME_BALL, Items.BASALT);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> QUARTZ_SLIME = registerSlime("quartz_slime", 3200, 0xFFe4ddd3, ModItems.QUARTZ_SLIME_BALL, Items.QUARTZ_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> GLOWSTONE_SLIME = registerSlime("glowstone_slime", 3000, 0xFF784e27, ModItems.GLOWSTONE_SLIME_BALL, Items.GLOWSTONE);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> ENDSTONE_SLIME = registerSlime("endstone_slime", 2000, 0xFFcece8e, ModItems.ENDSTONE_SLIME_BALL, Items.END_STONE);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> AMETHYST_SLIME = registerSlime("amethyst_slime", 3000, 0xFF6b4da5, ModItems.AMETHYST_SLIME_BALL, Items.AMETHYST_SHARD);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> BROWN_MUSHROOM_SLIME = registerSlime("brown_mushroom_slime", 3500, 0xFF967251, ModItems.BROWN_MUSHROOM_SLIME_BALL, Items.BROWN_MUSHROOM_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> RED_MUSHROOM_SLIME = registerSlime("red_mushroom_slime", 3500, 0xFFc02624, ModItems.RED_MUSHROOM_SLIME_BALL, Items.RED_MUSHROOM_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> CACTUS_SLIME = registerSlime("cactus_slime", 2000, 0xFF476d21, ModItems.CACTUS_SLIME_BALL, Items.CACTUS);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> COAL_SLIME = registerSlime("coal_slime", 1800, 0xFF3b3d3b, ModItems.COAL_SLIME_BALL, Items.COAL_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> GRAVEL_SLIME = registerSlime("gravel_slime", 1500, 0xFF4a444b, ModItems.GRAVEL_SLIME_BALL, Items.GRAVEL);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> ENERGY_SLIME = registerSlime("energy_slime", 2000, 0xFFffff70, ModItems.ENERGY_SLIME_BALL, ModBlocks.ENERGY_SLIME_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> registerSlime(String name, int cooldown, int color, ItemLike dropItem, ItemLike growthItem) {
        return ENTITY_TYPES.register(name, () -> EntityType.Builder.<BaseSlime>of(
                (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, cooldown, color, dropItem, growthItem),
                MobCategory.CREATURE).build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, name))));
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
