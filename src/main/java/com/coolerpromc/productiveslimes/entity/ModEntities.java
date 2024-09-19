package com.coolerpromc.productiveslimes.entity;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.slime.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, ProductiveSlimes.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<DirtSlime>> DIRT_SLIME =
            ENTITY_TYPES.register("dirt_slime", () -> EntityType.Builder.of(DirtSlime::new, MobCategory.CREATURE).build("dirt_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<StoneSlime>> STONE_SLIME =
            ENTITY_TYPES.register("stone_slime", () -> EntityType.Builder.of(StoneSlime::new, MobCategory.CREATURE).build("stone_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<IronSlime>> IRON_SLIME =
            ENTITY_TYPES.register("iron_slime", () -> EntityType.Builder.of(IronSlime::new, MobCategory.CREATURE).build("iron_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<CopperSlime>> COPPER_SLIME =
            ENTITY_TYPES.register("copper_slime", () -> EntityType.Builder.of(CopperSlime::new, MobCategory.CREATURE).build("copper_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<GoldSlime>> GOLD_SLIME =
            ENTITY_TYPES.register("gold_slime", () -> EntityType.Builder.of(GoldSlime::new, MobCategory.CREATURE).build("gold_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<DiamondSlime>> DIAMOND_SLIME =
            ENTITY_TYPES.register("diamond_slime", () -> EntityType.Builder.of(DiamondSlime::new, MobCategory.CREATURE).build("diamond_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<NetheriteSlime>> NETHERITE_SLIME =
            ENTITY_TYPES.register("netherite_slime", () -> EntityType.Builder.of(NetheriteSlime::new, MobCategory.CREATURE).build("netherite_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<LapisSlime>> LAPIS_SLIME =
            ENTITY_TYPES.register("lapis_slime", () -> EntityType.Builder.of(LapisSlime::new, MobCategory.CREATURE).build("lapis_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<RedstoneSlime>> REDSTONE_SLIME =
            ENTITY_TYPES.register("redstone_slime", () -> EntityType.Builder.of(RedstoneSlime::new, MobCategory.CREATURE).build("redstone_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<OakSlime>> OAK_SLIME =
            ENTITY_TYPES.register("oak_slime", () -> EntityType.Builder.of(OakSlime::new, MobCategory.CREATURE).build("oak_slime"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
