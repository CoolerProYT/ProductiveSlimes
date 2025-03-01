package com.coolerpromc.productiveslimes.event;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.config.CustomContentRegistry;
import com.coolerpromc.productiveslimes.config.asset.CustomContentDataPack;
import com.coolerpromc.productiveslimes.config.asset.CustomContentResourcePack;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import com.coolerpromc.productiveslimes.networking.recipe.ClientRecipeManager;
import com.coolerpromc.productiveslimes.networking.recipe.RecipeSyncPayload;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredHolder;
import java.lang.reflect.Field;
import java.util.Optional;

@EventBusSubscriber(modid = ProductiveSlimes.MODID, bus = EventBusSubscriber.Bus.MOD)
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

    @SubscribeEvent
    public static void onAddPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA) {
            CustomContentDataPack dataPack = new CustomContentDataPack(CustomContentRegistry.dataPackResources);
            Pack pack = Pack.readMetaAndCreate(
                    new PackLocationInfo("productiveslimes_datapack", Component.literal("In Memory Pack"),
                            PackSource.DEFAULT, Optional.empty()),
                    new Pack.ResourcesSupplier() {
                        @Override
                        public PackResources openPrimary(PackLocationInfo location) {
                            return dataPack;
                        }

                        @Override
                        public PackResources openFull(PackLocationInfo location, Pack.Metadata metadata) {
                            return dataPack;
                        }
                    },
                    PackType.SERVER_DATA,
                    new PackSelectionConfig(true, Pack.Position.TOP, true)
            );

            event.addRepositorySource((consumer) -> consumer.accept(pack));
        }
        else if (event.getPackType() == PackType.CLIENT_RESOURCES){
            CustomContentResourcePack resourcePack = new CustomContentResourcePack(CustomContentRegistry.resourceData);
            Pack pack = Pack.readMetaAndCreate(
                    resourcePack.location(),
                    new Pack.ResourcesSupplier() {
                        @Override
                        public PackResources openPrimary(PackLocationInfo location) {
                            return resourcePack;
                        }
                        @Override
                        public PackResources openFull(PackLocationInfo location, Pack.Metadata metadata) {
                            return resourcePack;
                        }
                    },
                    PackType.CLIENT_RESOURCES,
                    new PackSelectionConfig(true, Pack.Position.TOP, true)
            );

            event.addRepositorySource((consumer) -> consumer.accept(pack));
        }
    }
}