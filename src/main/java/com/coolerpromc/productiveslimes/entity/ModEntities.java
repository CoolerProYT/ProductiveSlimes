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

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> DIRT_SLIME = registerSlime("dirt_slime", 1500, 0xF0866043, ModItems.DIRT_SLIME_BALL, Items.DIRT);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> STONE_SLIME = registerSlime("stone_slime", 1700, 0xF04a4545, ModItems.STONE_SLIME_BALL, Items.STONE);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> IRON_SLIME = registerSlime("iron_slime", 3000, 0xF0898c8a, ModItems.IRON_SLIME_BALL, Items.IRON_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> COPPER_SLIME = registerSlime("copper_slime", 2500, 0xF06a3e15, ModItems.COPPER_SLIME_BALL, Items.COPPER_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> GOLD_SLIME = registerSlime("gold_slime", 3200, 0xF0a5953f, ModItems.GOLD_SLIME_BALL, Items.GOLD_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> DIAMOND_SLIME = registerSlime("diamond_slime", 4000, 0xF0178f9c, ModItems.DIAMOND_SLIME_BALL, Items.DIAMOND_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> NETHERITE_SLIME = registerSlime("netherite_slime", 5000, 0xF04c2b2b, ModItems.NETHERITE_SLIME_BALL, Items.NETHERITE_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> LAPIS_SLIME = registerSlime("lapis_slime", 2500, 0xF01c41ba, ModItems.LAPIS_SLIME_BALL, Items.LAPIS_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> REDSTONE_SLIME = registerSlime("redstone_slime", 2700, 0xF0a10505, ModItems.REDSTONE_SLIME_BALL, Items.REDSTONE_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> OAK_SLIME = registerSlime("oak_slime", 1500, 0xF0a69d6f, ModItems.OAK_SLIME_BALL, Items.OAK_LOG);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> SAND_SLIME = registerSlime("sand_slime", 1500, 0xF0f7f7c6, ModItems.SAND_SLIME_BALL, Items.SAND);
    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> ANDESITE_SLIME = registerSlime("andesite_slime", 1500, 0xF09d9e9a, ModItems.ANDESITE_SLIME_BALL, Items.ANDESITE);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> SNOW_SLIME = registerSlime("snow_slime", 1800, 0xF0f2fcfc, ModItems.SNOW_SLIME_BALL, Items.SNOW_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> ICE_SLIME = registerSlime("ice_slime", 1800, 0xF089b1fc, ModItems.ICE_SLIME_BALL, Items.ICE);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> MUD_SLIME = registerSlime("mud_slime", 1500, 0xF0363339, ModItems.MUD_SLIME_BALL, Items.MUD);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> CLAY_SLIME = registerSlime("clay_slime", 1500, 0xF09ca2ac, ModItems.CLAY_SLIME_BALL, Items.CLAY);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> RED_SAND_SLIME = registerSlime("red_sand_slime", 1500, 0xF0bb6520, ModItems.RED_SAND_SLIME_BALL, Items.RED_SAND);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> MOSS_SLIME = registerSlime("moss_slime", 1500, 0xF04a6029, ModItems.MOSS_SLIME_BALL, Items.MOSS_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> DEEPSLATE_SLIME = registerSlime("deepslate_slime", 1500, 0xF03c3c42, ModItems.DEEPSLATE_SLIME_BALL, Items.DEEPSLATE);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> GRANITE_SLIME = registerSlime("granite_slime", 1500, 0xF0835949, ModItems.GRANITE_SLIME_BALL, Items.GRANITE);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> DIORITE_SLIME = registerSlime("diorite_slime", 1500, 0xF0adacad, ModItems.DIORITE_SLIME_BALL, Items.DIORITE);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> CALCITE_SLIME = registerSlime("calcite_slime", 1500, 0xF0e9e9e3, ModItems.CALCITE_SLIME_BALL, Items.CALCITE);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> TUFF_SLIME = registerSlime("tuff_slime", 1500, 0xF055564c, ModItems.TUFF_SLIME_BALL, Items.TUFF);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> DRIPSTONE_SLIME = registerSlime("dripstone_slime", 1500, 0xF0806155, ModItems.DRIPSTONE_SLIME_BALL, Items.DRIPSTONE_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> NETHERRACK_SLIME = registerSlime("netherrack_slime", 1500, 0xF0763535, ModItems.NETHERRACK_SLIME_BALL, Items.NETHERRACK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> PRISMARINE_SLIME = registerSlime("prismarine_slime", 3000, 0xF0529584, ModItems.PRISMARINE_SLIME_BALL, Items.PRISMARINE);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> MAGMA_SLIME = registerSlime("magma_slime", 2500, 0xF0561f1f, ModItems.MAGMA_SLIME_BALL, Items.MAGMA_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> OBSIDIAN_SLIME = registerSlime("obsidian_slime", 3500, 0xF0030106, ModItems.OBSIDIAN_SLIME_BALL, Items.OBSIDIAN);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> SOUL_SAND_SLIME = registerSlime("soul_sand_slime", 2000, 0xF0413127, ModItems.SOUL_SAND_SLIME_BALL, Items.SOUL_SAND);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> SOUL_SOIL_SLIME = registerSlime("soul_soil_slime", 2000, 0xF0392b23, ModItems.SOUL_SOIL_SLIME_BALL, Items.SOUL_SOIL);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> BLACKSTONE_SLIME = registerSlime("blackstone_slime", 1500, 0xF0201819, ModItems.BLACKSTONE_SLIME_BALL, Items.BLACKSTONE);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> BASALT_SLIME = registerSlime("basalt_slime", 1500, 0xF0565456, ModItems.BASALT_SLIME_BALL, Items.BASALT);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> QUARTZ_SLIME = registerSlime("quartz_slime", 3200, 0xF0e4ddd3, ModItems.QUARTZ_SLIME_BALL, Items.QUARTZ_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> GLOWSTONE_SLIME = registerSlime("glowstone_slime", 3000, 0xF0784e27, ModItems.GLOWSTONE_SLIME_BALL, Items.GLOWSTONE);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> ENDSTONE_SLIME = registerSlime("endstone_slime", 2000, 0xF0cece8e, ModItems.ENDSTONE_SLIME_BALL, Items.END_STONE);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> AMETHYST_SLIME = registerSlime("amethyst_slime", 3000, 0xF06b4da5, ModItems.AMETHYST_SLIME_BALL, Items.AMETHYST_SHARD);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> BROWN_MUSHROOM_SLIME = registerSlime("brown_mushroom_slime", 3500, 0xF0967251, ModItems.BROWN_MUSHROOM_SLIME_BALL, Items.BROWN_MUSHROOM_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> RED_MUSHROOM_SLIME = registerSlime("red_mushroom_slime", 3500, 0xF0c02624, ModItems.RED_MUSHROOM_SLIME_BALL, Items.RED_MUSHROOM_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> CACTUS_SLIME = registerSlime("cactus_slime", 2000, 0xF0476d21, ModItems.CACTUS_SLIME_BALL, Items.CACTUS);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> COAL_SLIME = registerSlime("coal_slime", 1800, 0xF03b3d3b, ModItems.COAL_SLIME_BALL, Items.COAL_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> GRAVEL_SLIME = registerSlime("gravel_slime", 1500, 0xF04a444b, ModItems.GRAVEL_SLIME_BALL, Items.GRAVEL);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> ENERGY_SLIME = registerSlime("energy_slime", 2000, 0xF0ffff70, ModItems.ENERGY_SLIME_BALL, ModBlocks.ENERGY_SLIME_BLOCK);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> registerSlime(String name, int cooldown, int color, ItemLike dropItem, ItemLike growthItem) {
        return ENTITY_TYPES.register(name, () -> EntityType.Builder.<BaseSlime>of(
                (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, cooldown, color, dropItem, growthItem),
                MobCategory.CREATURE).build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, name))));
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
