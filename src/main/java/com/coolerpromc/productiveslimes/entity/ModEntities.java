package com.coolerpromc.productiveslimes.entity;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.entity.slime.*;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
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

    public static final DeferredHolder<EntityType<?>, EntityType<BaseSlime>> ENERGY_SLIME = registerSlime("energy_slime", 2000, 0xFFffff70, ModItems.ENERGY_SLIME_BALL, ModBlocks.ENERGY_SLIME_BLOCK);

    public static void registerTierEntities(){
        for(Tier tier : Tier.values()) {
            ModTiers modTiers = ModTierLists.getTierByName(tier);
            String name = modTiers.name() + "_slime";
            int cooldown = modTiers.cooldown();
            int color = modTiers.color();
            ItemLike dropItem = ModTierLists.getSlimeballItemByName(modTiers.name());
            ItemLike growthItem = ModTierLists.getItemByKey(modTiers.growthItemKey());
            DeferredHolder<EntityType<?>, EntityType<BaseSlime>> slime = registerSlime(name, cooldown, color, dropItem, growthItem);
            ModTierLists.addRegisteredSlime(modTiers.name(), slime);
        }
    }

    public static DeferredHolder<EntityType<?>, EntityType<BaseSlime>> registerSlime(String name, int cooldown, int color, ItemLike dropItem, ItemLike growthItem) {
        return ENTITY_TYPES.register(name, () -> EntityType.Builder.<BaseSlime>of(
                (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, cooldown, color, dropItem, growthItem),
                MobCategory.CREATURE).build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, name))));
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
