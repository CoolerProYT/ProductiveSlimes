package com.coolerpromc.productiveslimes.entity;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.slime.GoldSlime;
import com.coolerpromc.productiveslimes.entity.slime.IronSlime;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, ProductiveSlimes.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<IronSlime>> IRON_SLIME =
            ENTITY_TYPES.register("iron_slime", () -> EntityType.Builder.of(IronSlime::new, MobCategory.CREATURE).build("iron_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<GoldSlime>> GOLD_SLIME =
            ENTITY_TYPES.register("gold_slime", () -> EntityType.Builder.of(GoldSlime::new, MobCategory.CREATURE).build("gold_slime"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
