package com.coolerpromc.productiveslimes.event;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import com.coolerpromc.productiveslimes.networking.ClientRecipeManager;
import com.coolerpromc.productiveslimes.networking.RecipeSyncPayload;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredHolder;
import java.lang.reflect.Field;

@EventBusSubscriber(modid = ProductiveSlimes.MODID)
public class ModEventBusEvent {
    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        Field[] fields = ModEntities.class.getFields();
        for (Field field : fields) {
            try {
                Object value = field.get(null);
                if (value instanceof DeferredHolder<?, ?> holder && holder.get() instanceof EntityType<?> entityType) {
                    event.register((EntityType<? extends BaseSlime>) entityType, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                            BaseSlime::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterPayloadHandlers(RegisterPayloadHandlersEvent event)
    {
        final PayloadRegistrar registrar = event.registrar(ProductiveSlimes.MODID)
                .versioned("1.0")
                .optional();

        registrar.commonToClient(RecipeSyncPayload.TYPE, RecipeSyncPayload.STREAM_CODEC, (payload, context) -> context.channelHandlerContext().executor().execute(() -> ClientRecipeManager.updateRecipes(payload.recipes())));
    }
}