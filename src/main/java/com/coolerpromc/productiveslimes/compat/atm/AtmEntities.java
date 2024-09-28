package com.coolerpromc.productiveslimes.compat.atm;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import com.coolerpromc.productiveslimes.entity.slime.Slime;
import com.coolerpromc.productiveslimes.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AtmEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, ProductiveSlimes.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> ATM_SLIME =
            ENTITY_TYPES.register("atm_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 10000, 0xF0ff8b04, AtmItems.ATM_SLIME_BALL.get(), BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("allthemodium", "allthemodium_block"))),
                    MobCategory.CREATURE).build("atm_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> VIBRANIUM_SLIME =
            ENTITY_TYPES.register("vibranium_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 15000, 0xF0148484, AtmItems.VIBRANIUM_SLIME_BALL.get(), BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("allthemodium", "vibranium_block"))),
                    MobCategory.CREATURE).build("vibranium_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> UNOBTAINIUM_SLIME =
            ENTITY_TYPES.register("unobtainium_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 20000, 0xF06c1ce4, AtmItems.UNOBTAINIUM_SLIME_BALL.get(), BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("allthemodium", "unobtainium_block"))),
                    MobCategory.CREATURE).build("unobtainium_slime"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
