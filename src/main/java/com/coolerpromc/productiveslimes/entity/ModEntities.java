package com.coolerpromc.productiveslimes.entity;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.entity.slime.*;
import com.coolerpromc.productiveslimes.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, ProductiveSlimes.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> DIRT_SLIME =
            ENTITY_TYPES.register("dirt_slime", () -> EntityType.Builder.<BaseSlime>of(
                    ((pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 1500, 0xFF866043, ModItems.DIRT_SLIME_BALL.get(), Items.DIRT)),
                    MobCategory.CREATURE).build("dirt_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> STONE_SLIME =
            ENTITY_TYPES.register("stone_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 1700, 0xFF4a4545, ModItems.STONE_SLIME_BALL.get(), Items.STONE),
                    MobCategory.CREATURE).build("stone_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> IRON_SLIME =
            ENTITY_TYPES.register("iron_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 3000, 0xFF898c8a, ModItems.IRON_SLIME_BALL.get(), Items.IRON_BLOCK),
                    MobCategory.CREATURE).build("iron_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> COPPER_SLIME =
            ENTITY_TYPES.register("copper_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 2500, 0xFF6a3e15, ModItems.COPPER_SLIME_BALL.get(), Items.COPPER_BLOCK),
                    MobCategory.CREATURE).build("copper_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> GOLD_SLIME =
            ENTITY_TYPES.register("gold_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 3200, 0xFFa5953f, ModItems.GOLD_SLIME_BALL.get(), Items.GOLD_BLOCK),
                    MobCategory.CREATURE).build("gold_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> DIAMOND_SLIME =
            ENTITY_TYPES.register("diamond_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 4000, 0xFF178f9c, ModItems.DIAMOND_SLIME_BALL.get(), Items.DIAMOND_BLOCK),
                    MobCategory.CREATURE).build("diamond_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> NETHERITE_SLIME =
            ENTITY_TYPES.register("netherite_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 5000, 0xFF4c2b2b, ModItems.NETHERITE_SLIME_BALL.get(), Items.NETHERITE_BLOCK),
                    MobCategory.CREATURE).build("netherite_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> LAPIS_SLIME =
            ENTITY_TYPES.register("lapis_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 2500, 0xFF1c41ba, ModItems.LAPIS_SLIME_BALL.get(), Items.LAPIS_BLOCK),
                    MobCategory.CREATURE).build("lapis_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> REDSTONE_SLIME =
            ENTITY_TYPES.register("redstone_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 2700, 0xFFa10505, ModItems.REDSTONE_SLIME_BALL.get(), Items.REDSTONE_BLOCK),
                    MobCategory.CREATURE).build("redstone_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> OAK_SLIME =
            ENTITY_TYPES.register("oak_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 1500, 0xFFa69d6f, ModItems.OAK_SLIME_BALL.get(), Items.OAK_LOG),
                    MobCategory.CREATURE).build("oak_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> SAND_SLIME =
            ENTITY_TYPES.register("sand_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 1500, 0xFFf7f7c6, ModItems.SAND_SLIME_BALL.get(), Items.SAND),
                    MobCategory.CREATURE).build("sand_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> ANDESITE_SLIME =
            ENTITY_TYPES.register("andesite_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 1500, 0xFF9d9e9a, ModItems.ANDESITE_SLIME_BALL.get(), Items.ANDESITE),
                    MobCategory.CREATURE).build("andesite_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> SNOW_SLIME =
            ENTITY_TYPES.register("snow_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 1800, 0xFFf2fcfc, ModItems.SNOW_SLIME_BALL.get(), Items.SNOW_BLOCK),
                    MobCategory.CREATURE).build("snow_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> ICE_SLIME =
            ENTITY_TYPES.register("ice_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 1800, 0xFF89b1fc, ModItems.ICE_SLIME_BALL.get(), Items.ICE),
                    MobCategory.CREATURE).build("ice_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> MUD_SLIME =
            ENTITY_TYPES.register("mud_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 1500, 0xFF363339, ModItems.MUD_SLIME_BALL.get(), Items.MUD),
                    MobCategory.CREATURE).build("mud_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> CLAY_SLIME =
            ENTITY_TYPES.register("clay_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 1500, 0xFF9ca2ac, ModItems.CLAY_SLIME_BALL.get(), Items.CLAY),
                    MobCategory.CREATURE).build("clay_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> RED_SAND_SLIME =
            ENTITY_TYPES.register("red_sand_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 1500, 0xFFbb6520, ModItems.RED_SAND_SLIME_BALL.get(), Items.RED_SAND),
                    MobCategory.CREATURE).build("red_sand_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> MOSS_SLIME =
            ENTITY_TYPES.register("moss_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 1500, 0xFF4a6029, ModItems.MOSS_SLIME_BALL.get(), Items.MOSS_BLOCK),
                    MobCategory.CREATURE).build("moss_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> DEEPSLATE_SLIME =
            ENTITY_TYPES.register("deepslate_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 1500, 0xFF3c3c42, ModItems.DEEPSLATE_SLIME_BALL.get(), Items.DEEPSLATE),
                    MobCategory.CREATURE).build("deepslate_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> GRANITE_SLIME =
            ENTITY_TYPES.register("granite_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 1500, 0xFF835949, ModItems.GRANITE_SLIME_BALL.get(), Items.GRANITE),
                    MobCategory.CREATURE).build("granite_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> DIORITE_SLIME =
            ENTITY_TYPES.register("diorite_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 1500, 0xFFadacad, ModItems.DIORITE_SLIME_BALL.get(), Items.DIORITE),
                    MobCategory.CREATURE).build("diorite_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> CALCITE_SLIME =
            ENTITY_TYPES.register("calcite_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 1500, 0xFFe9e9e3, ModItems.CALCITE_SLIME_BALL.get(), Items.CALCITE),
                    MobCategory.CREATURE).build("calcite_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> TUFF_SLIME =
            ENTITY_TYPES.register("tuff_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 1500, 0xFF55564c, ModItems.TUFF_SLIME_BALL.get(), Items.TUFF),
                    MobCategory.CREATURE).build("tuff_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> DRIPSTONE_SLIME =
            ENTITY_TYPES.register("dripstone_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 1500, 0xFF806155, ModItems.DRIPSTONE_SLIME_BALL.get(), Items.DRIPSTONE_BLOCK),
                    MobCategory.CREATURE).build("dripstone_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> NETHERRACK_SLIME =
            ENTITY_TYPES.register("netherrack_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 1500, 0xFF763535, ModItems.NETHERRACK_SLIME_BALL.get(), Items.NETHERRACK),
                    MobCategory.CREATURE).build("netherrack_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> PRISMARINE_SLIME =
            ENTITY_TYPES.register("prismarine_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 3000, 0xFF529584, ModItems.PRISMARINE_SLIME_BALL.get(), Items.PRISMARINE),
                    MobCategory.CREATURE).build("prismarine_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> MAGMA_SLIME =
            ENTITY_TYPES.register("magma_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 2500, 0xFF561f1f, ModItems.MAGMA_SLIME_BALL.get(), Items.MAGMA_BLOCK),
                    MobCategory.CREATURE).build("magma_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> OBSIDIAN_SLIME =
            ENTITY_TYPES.register("obsidian_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 3500, 0xFF030106, ModItems.OBSIDIAN_SLIME_BALL.get(), Items.OBSIDIAN),
                    MobCategory.CREATURE).build("obsidian_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> SOUL_SAND_SLIME =
            ENTITY_TYPES.register("soul_sand_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 2000, 0xFF413127, ModItems.SOUL_SAND_SLIME_BALL.get(), Items.SOUL_SAND),
                    MobCategory.CREATURE).build("soul_sand_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> SOUL_SOIL_SLIME =
            ENTITY_TYPES.register("soul_soil_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 2000, 0xFF392b23, ModItems.SOUL_SOIL_SLIME_BALL.get(), Items.SOUL_SOIL),
                    MobCategory.CREATURE).build("soul_soil_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> BLACKSTONE_SLIME =
            ENTITY_TYPES.register("blackstone_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 1500, 0xFF201819, ModItems.BLACKSTONE_SLIME_BALL.get(), Items.BLACKSTONE),
                    MobCategory.CREATURE).build("blackstone_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> BASALT_SLIME =
            ENTITY_TYPES.register("basalt_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 1500, 0xFF565456, ModItems.BASALT_SLIME_BALL.get(), Items.BASALT),
                    MobCategory.CREATURE).build("basalt_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> QUARTZ_SLIME =
            ENTITY_TYPES.register("quartz_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 3200, 0xFFe4ddd3, ModItems.QUARTZ_SLIME_BALL.get(), Items.QUARTZ_BLOCK),
                    MobCategory.CREATURE).build("quartz_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> GLOWSTONE_SLIME =
            ENTITY_TYPES.register("glowstone_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 3000, 0xFF784e27, ModItems.GLOWSTONE_SLIME_BALL.get(), Items.GLOWSTONE),
                    MobCategory.CREATURE).build("glowstone_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> ENDSTONE_SLIME =
            ENTITY_TYPES.register("endstone_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 2000, 0xFFcece8e, ModItems.ENDSTONE_SLIME_BALL.get(), Items.END_STONE),
                    MobCategory.CREATURE).build("endstone_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> AMETHYST_SLIME =
            ENTITY_TYPES.register("amethyst_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 3000, 0xFF6b4da5, ModItems.AMETHYST_SLIME_BALL.get(), Items.AMETHYST_SHARD),
                    MobCategory.CREATURE).build("amethyst_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> BROWN_MUSHROOM_SLIME =
            ENTITY_TYPES.register("brown_mushroom_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 3500, 0xFF967251, ModItems.BROWN_MUSHROOM_SLIME_BALL.get(), Items.BROWN_MUSHROOM_BLOCK),
                    MobCategory.CREATURE).build("brown_mushroom_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> RED_MUSHROOM_SLIME =
            ENTITY_TYPES.register("red_mushroom_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 3500, 0xFFc02624, ModItems.RED_MUSHROOM_SLIME_BALL.get(), Items.RED_MUSHROOM_BLOCK),
                    MobCategory.CREATURE).build("red_mushroom_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> CACTUS_SLIME =
            ENTITY_TYPES.register("cactus_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 2000, 0xFF476d21, ModItems.CACTUS_SLIME_BALL.get(), Items.CACTUS),
                    MobCategory.CREATURE).build("cactus_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> COAL_SLIME =
            ENTITY_TYPES.register("coal_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 1800, 0xFF3b3d3b, ModItems.COAL_SLIME_BALL.get(), Items.COAL_BLOCK),
                    MobCategory.CREATURE).build("coal_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> GRAVEL_SLIME =
            ENTITY_TYPES.register("gravel_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 1500, 0xFF4a444b, ModItems.GRAVEL_SLIME_BALL.get(), Items.GRAVEL),
                    MobCategory.CREATURE).build("gravel_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> ENERGY_SLIME =
            ENTITY_TYPES.register("energy_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 2000, 0xFFffff70, ModItems.ENERGY_SLIME_BALL.get(), ModBlocks.ENERGY_SLIME_BLOCK.get().asItem()),
                    MobCategory.CREATURE).build("energy_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> OAK_LEAVES_SLIME =
            ENTITY_TYPES.register("oak_leaves_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 1500, 0xFF48b518, ModItems.OAK_LEAVES_SLIME_BALL.get(), ModBlocks.OAK_LEAVES_SLIME_BLOCK.get().asItem()),
                    MobCategory.CREATURE).build("oak_leaves_slime"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
