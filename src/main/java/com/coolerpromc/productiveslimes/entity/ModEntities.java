package com.coolerpromc.productiveslimes.entity;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.entity.slime.*;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ProductiveSlimes.MODID);

    public static final RegistryObject<EntityType<BaseSlime>> ENERGY_SLIME =
            ENTITY_TYPES.register("energy_slime", () -> EntityType.Builder.<BaseSlime>of(
                    (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, 2000, 0xFFffff70, ModItems.ENERGY_SLIME_BALL, ModBlocks.ENERGY_SLIME_BLOCK.get().asItem()),
                    MobCategory.CREATURE).build("energy_slime"));

    public static void registerTierEntities(){
        for(Tier tier : Tier.values()) {
            ModTiers modTiers = ModTierLists.getTierByName(tier);
            String name = modTiers.name() + "_slime";
            int cooldown = modTiers.cooldown();
            int color = modTiers.color();
            RegistryObject<Item> dropItem = ModTierLists.getSlimeballItemByName(modTiers.name());
            ItemLike growthItem = ModTierLists.getItemByKey(modTiers.growthItemKey());
            RegistryObject<EntityType<BaseSlime>> slime = registerSlime(name, cooldown, color, dropItem, growthItem);
            ModTierLists.addRegisteredSlime(modTiers.name(), slime);
        }
    }
    public static RegistryObject<EntityType<BaseSlime>> registerSlime(String name, int cooldown, int color, RegistryObject<Item> dropItem, ItemLike growthItem){
        return ENTITY_TYPES.register(name, () -> EntityType.Builder.<BaseSlime>of(
                (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, cooldown, color, dropItem, growthItem),
                MobCategory.CREATURE).build(name));
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
