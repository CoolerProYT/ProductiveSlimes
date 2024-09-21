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
    public static final DeferredHolder<EntityType<?>, EntityType<SandSlime>> SAND_SLIME =
            ENTITY_TYPES.register("sand_slime", () -> EntityType.Builder.of(SandSlime::new, MobCategory.CREATURE).build("sand_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<AndesiteSlime>> ANDESITE_SLIME =
            ENTITY_TYPES.register("andesite_slime", () -> EntityType.Builder.of(AndesiteSlime::new, MobCategory.CREATURE).build("andesite_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<SnowSlime>> SNOW_SLIME =
            ENTITY_TYPES.register("snow_slime", () -> EntityType.Builder.of(SnowSlime::new, MobCategory.CREATURE).build("snow_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<IceSlime>> ICE_SLIME =
            ENTITY_TYPES.register("ice_slime", () -> EntityType.Builder.of(IceSlime::new, MobCategory.CREATURE).build("ice_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<MudSlime>> MUD_SLIME =
            ENTITY_TYPES.register("mud_slime", () -> EntityType.Builder.of(MudSlime::new, MobCategory.CREATURE).build("mud_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<ClaySlime>> CLAY_SLIME =
            ENTITY_TYPES.register("clay_slime", () -> EntityType.Builder.of(ClaySlime::new, MobCategory.CREATURE).build("clay_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<RedSandSlime>> RED_SAND_SLIME =
            ENTITY_TYPES.register("red_sand_slime", () -> EntityType.Builder.of(RedSandSlime::new, MobCategory.CREATURE).build("red_sand_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<MossSlime>> MOSS_SLIME =
            ENTITY_TYPES.register("moss_slime", () -> EntityType.Builder.of(MossSlime::new, MobCategory.CREATURE).build("moss_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<DeepslateSlime>> DEEPSLATE_SLIME =
            ENTITY_TYPES.register("deepslate_slime", () -> EntityType.Builder.of(DeepslateSlime::new, MobCategory.CREATURE).build("deepslate_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<GraniteSlime>> GRANITE_SLIME =
            ENTITY_TYPES.register("granite_slime", () -> EntityType.Builder.of(GraniteSlime::new, MobCategory.CREATURE).build("granite_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<DioriteSlime>> DIORITE_SLIME =
            ENTITY_TYPES.register("diorite_slime", () -> EntityType.Builder.of(DioriteSlime::new, MobCategory.CREATURE).build("diorite_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<CalciteSlime>> CALCITE_SLIME =
            ENTITY_TYPES.register("calcite_slime", () -> EntityType.Builder.of(CalciteSlime::new, MobCategory.CREATURE).build("calcite_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<TuffSlime>> TUFF_SLIME =
            ENTITY_TYPES.register("tuff_slime", () -> EntityType.Builder.of(TuffSlime::new, MobCategory.CREATURE).build("tuff_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<DripstoneSlime>> DRIPSTONE_SLIME =
            ENTITY_TYPES.register("dripstone_slime", () -> EntityType.Builder.of(DripstoneSlime::new, MobCategory.CREATURE).build("dripstone_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<PrismarineSlime>> PRISMARINE_SLIME =
            ENTITY_TYPES.register("prismarine_slime", () -> EntityType.Builder.of(PrismarineSlime::new, MobCategory.CREATURE).build("prismarine_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<MagmaSlime>> MAGMA_SLIME =
            ENTITY_TYPES.register("magma_slime", () -> EntityType.Builder.of(MagmaSlime::new, MobCategory.CREATURE).build("magma_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<ObsidianSlime>> OBSIDIAN_SLIME =
            ENTITY_TYPES.register("obsidian_slime", () -> EntityType.Builder.of(ObsidianSlime::new, MobCategory.CREATURE).build("obsidian_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<NetherrackSlime>> NETHERRACK_SLIME =
            ENTITY_TYPES.register("netherrack_slime", () -> EntityType.Builder.of(NetherrackSlime::new, MobCategory.CREATURE).build("netherrack_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<SoulSandSlime>> SOUL_SAND_SLIME =
            ENTITY_TYPES.register("soul_sand_slime", () -> EntityType.Builder.of(SoulSandSlime::new, MobCategory.CREATURE).build("soul_sand_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<SoulSoilSlime>> SOUL_SOIL_SLIME =
            ENTITY_TYPES.register("soul_soil_slime", () -> EntityType.Builder.of(SoulSoilSlime::new, MobCategory.CREATURE).build("soul_soil_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<BlackstoneSlime>> BLACKSTONE_SLIME =
            ENTITY_TYPES.register("blackstone_slime", () -> EntityType.Builder.of(BlackstoneSlime::new, MobCategory.CREATURE).build("blackstone_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<BasaltSlime>> BASALT_SLIME =
            ENTITY_TYPES.register("basalt_slime", () -> EntityType.Builder.of(BasaltSlime::new, MobCategory.CREATURE).build("basalt_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<EndStoneSlime>> ENDSTONE_SLIME =
            ENTITY_TYPES.register("endstone_slime", () -> EntityType.Builder.of(EndStoneSlime::new, MobCategory.CREATURE).build("endstone_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<QuartzSlime>> QUARTZ_SLIME =
            ENTITY_TYPES.register("quartz_slime", () -> EntityType.Builder.of(QuartzSlime::new, MobCategory.CREATURE).build("quartz_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<GlowstoneSlime>> GLOWSTONE_SLIME =
            ENTITY_TYPES.register("glowstone_slime", () -> EntityType.Builder.of(GlowstoneSlime::new, MobCategory.CREATURE).build("glowstone_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<AmethystSlime>> AMETHYST_SLIME =
            ENTITY_TYPES.register("amethyst_slime", () -> EntityType.Builder.of(AmethystSlime::new, MobCategory.CREATURE).build("amethyst_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<BrownMushroomSlime>> BROWN_MUSHROOM_SLIME =
            ENTITY_TYPES.register("brown_mushroom_slime", () -> EntityType.Builder.of(BrownMushroomSlime::new, MobCategory.CREATURE).build("brown_mushroom_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<RedMushroomSlime>> RED_MUSHROOM_SLIME =
            ENTITY_TYPES.register("red_mushroom_slime", () -> EntityType.Builder.of(RedMushroomSlime::new, MobCategory.CREATURE).build("red_mushroom_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<CactusSlime>> CACTUS_SLIME =
            ENTITY_TYPES.register("cactus_slime", () -> EntityType.Builder.of(CactusSlime::new, MobCategory.CREATURE).build("cactus_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<CoalSlime>> COAL_SLIME =
            ENTITY_TYPES.register("coal_slime", () -> EntityType.Builder.of(CoalSlime::new, MobCategory.CREATURE).build("coal_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<GravelSlime>> GRAVEL_SLIME =
            ENTITY_TYPES.register("gravel_slime", () -> EntityType.Builder.of(GravelSlime::new, MobCategory.CREATURE).build("gravel_slime"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
