package com.coolerpromc.productiveslimes.event;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = ProductiveSlimes.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvent {
    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        Field[] fields = ModEntities.class.getFields();

        for (Field field : fields) {
            try {
                Object value = field.get(null);

                if (value instanceof RegistryObject<?> holder && holder.get() instanceof EntityType<?> entityType) {
                    event.register((EntityType<? extends BaseSlime>) entityType, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                            (entityTypes, serverLevel, spawnType, pos, random) -> serverLevel.getBlockState(pos.below()).getBlock() == ModBlocks.SLIMY_GRASS_BLOCK.get(), SpawnPlacementRegisterEvent.Operation.REPLACE);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
