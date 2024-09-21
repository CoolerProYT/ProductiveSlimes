package com.coolerpromc.productiveslimes.event;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.entity.slime.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = ProductiveSlimes.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEntitiesEvent {
    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ModEntities.DIRT_SLIME.get(), DirtSlime.createAttributes().build());
        event.put(ModEntities.STONE_SLIME.get(), StoneSlime.createAttributes().build());
        event.put(ModEntities.IRON_SLIME.get(), IronSlime.createAttributes().build());
        event.put(ModEntities.COPPER_SLIME.get(), IronSlime.createAttributes().build());
        event.put(ModEntities.GOLD_SLIME.get(), GoldSlime.createAttributes().build());
        event.put(ModEntities.DIAMOND_SLIME.get(), DiamondSlime.createAttributes().build());
        event.put(ModEntities.NETHERITE_SLIME.get(), NetheriteSlime.createAttributes().build());
        event.put(ModEntities.LAPIS_SLIME.get(), GoldSlime.createAttributes().build());
        event.put(ModEntities.REDSTONE_SLIME.get(), RedstoneSlime.createAttributes().build());
        event.put(ModEntities.OAK_SLIME.get(), OakSlime.createAttributes().build());
        event.put(ModEntities.COAL_SLIME.get(), CoalSlime.createAttributes().build());
        event.put(ModEntities.GRAVEL_SLIME.get(), GravelSlime.createAttributes().build());
        event.put(ModEntities.ANDESITE_SLIME.get(), AndesiteSlime.createAttributes().build());
        event.put(ModEntities.SNOW_SLIME.get(), SnowSlime.createAttributes().build());
        event.put(ModEntities.ICE_SLIME.get(), IceSlime.createAttributes().build());
        event.put(ModEntities.MUD_SLIME.get(), MudSlime.createAttributes().build());
        event.put(ModEntities.CLAY_SLIME.get(), ClaySlime.createAttributes().build());
        event.put(ModEntities.RED_SAND_SLIME.get(), RedSandSlime.createAttributes().build());
        event.put(ModEntities.MOSS_SLIME.get(), MossSlime.createAttributes().build());
        event.put(ModEntities.DEEPSLATE_SLIME.get(), DeepslateSlime.createAttributes().build());
        event.put(ModEntities.GRANITE_SLIME.get(), GraniteSlime.createAttributes().build());
        event.put(ModEntities.DIORITE_SLIME.get(), DioriteSlime.createAttributes().build());
        event.put(ModEntities.CALCITE_SLIME.get(), CalciteSlime.createAttributes().build());
        event.put(ModEntities.TUFF_SLIME.get(), TuffSlime.createAttributes().build());
        event.put(ModEntities.DRIPSTONE_SLIME.get(), DripstoneSlime.createAttributes().build());
        event.put(ModEntities.NETHERRACK_SLIME.get(), NetherrackSlime.createAttributes().build());
        event.put(ModEntities.PRISMARINE_SLIME.get(), PrismarineSlime.createAttributes().build());
        event.put(ModEntities.MAGMA_SLIME.get(), MagmaSlime.createAttributes().build());
        event.put(ModEntities.OBSIDIAN_SLIME.get(), ObsidianSlime.createAttributes().build());
        event.put(ModEntities.SOUL_SAND_SLIME.get(), SoulSandSlime.createAttributes().build());
        event.put(ModEntities.BLACKSTONE_SLIME.get(), BlackstoneSlime.createAttributes().build());
        event.put(ModEntities.BASALT_SLIME.get(), BasaltSlime.createAttributes().build());
        event.put(ModEntities.QUARTZ_SLIME.get(), QuartzSlime.createAttributes().build());
        event.put(ModEntities.GLOWSTONE_SLIME.get(), GlowstoneSlime.createAttributes().build());
        event.put(ModEntities.END_STONE_SLIME.get(), EndStoneSlime.createAttributes().build());
        event.put(ModEntities.AMETHYST_SLIME.get(), AmethystSlime.createAttributes().build());
        event.put(ModEntities.BROWN_MUSHROOM_SLIME.get(), BrownMushroomSlime.createAttributes().build());
        event.put(ModEntities.RED_MUSHROOM_SLIME.get(), RedMushroomSlime.createAttributes().build());
        event.put(ModEntities.CACTUS_SLIME.get(), CactusSlime.createAttributes().build());
    }
}
