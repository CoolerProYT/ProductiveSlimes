package com.coolerpromc.productiveslimes;

import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.entity.renderer.*;
import com.coolerpromc.productiveslimes.item.ModCreativeTabs;
import com.coolerpromc.productiveslimes.item.ModItems;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(ProductiveSlimes.MODID)
public class ProductiveSlimes
{
    public static final String MODID = "productiveslimes";
    public ProductiveSlimes(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::commonSetup);

        ModCreativeTabs.register(modEventBus);
        ModEntities.register(modEventBus);
        ModItems.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ModEntities.DIRT_SLIME.get(), DirtSlimeRenderer::new);
            EntityRenderers.register(ModEntities.STONE_SLIME.get(), StoneSlimeRenderer::new);
            EntityRenderers.register(ModEntities.IRON_SLIME.get(), IronSlimeRenderer::new);
            EntityRenderers.register(ModEntities.COPPER_SLIME.get(), CopperSlimeRenderer::new);
            EntityRenderers.register(ModEntities.GOLD_SLIME.get(), GoldSlimeRenderer::new);
            EntityRenderers.register(ModEntities.DIAMOND_SLIME.get(), DiamondSlimeRenderer::new);
            EntityRenderers.register(ModEntities.NETHERITE_SLIME.get(), NetheriteSlimeRenderer::new);
            EntityRenderers.register(ModEntities.LAPIS_SLIME.get(), LapisSlimeRenderer::new);
            EntityRenderers.register(ModEntities.REDSTONE_SLIME.get(), RedstoneSlimeRenderer::new);
        }
    }
}
