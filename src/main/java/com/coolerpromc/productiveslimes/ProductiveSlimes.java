package com.coolerpromc.productiveslimes;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.block.entity.ModBlockEntities;
import com.coolerpromc.productiveslimes.compat.top.GetTheOneProbe;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.entity.renderer.*;
import com.coolerpromc.productiveslimes.fluid.BaseFluidType;
import com.coolerpromc.productiveslimes.fluid.ModFluidTypes;
import com.coolerpromc.productiveslimes.fluid.ModFluids;
import com.coolerpromc.productiveslimes.item.ModCreativeTabs;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.recipe.ModRecipes;
import com.coolerpromc.productiveslimes.screen.ModMenuTypes;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.InterModComms;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(ProductiveSlimes.MODID)
public class ProductiveSlimes
{
    public static final String MODID = "productiveslimes";
    public ProductiveSlimes(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::commonSetup);
        if (ModList.get().isLoaded("theoneprobe"))
        {
            modEventBus.addListener(this::enqueueIMC);
        }

        ModBlocks.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ModEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModFluids.register(modEventBus);
        ModFluidTypes.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

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

    private void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo("theoneprobe", "getTheOneProbe", GetTheOneProbe::new);
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

            event.enqueueWork(() -> {
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_DIRT.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_DIRT.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_STONE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_STONE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_IRON.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_IRON.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_COPPER.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_COPPER.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_GOLD.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_GOLD.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_DIAMOND.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_DIAMOND.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_NETHERITE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_NETHERITE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_LAPIS.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_LAPIS.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_REDSTONE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_REDSTONE.get(), RenderType.translucent());

                ItemBlockRenderTypes.setRenderLayer(ModBlocks.DIRT_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.STONE_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.IRON_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.COPPER_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.GOLD_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.DIAMOND_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.NETHERITE_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.LAPIS_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.REDSTONE_SLIME_BLOCK.get(), RenderType.translucent());
            });
        }

        @SubscribeEvent
        public static void onClientExtensions(RegisterClientExtensionsEvent event) {
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_DIRT_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_DIRT_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_STONE_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_STONE_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_IRON_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_IRON_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_COPPER_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_COPPER_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_GOLD_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_GOLD_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_DIAMOND_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_DIAMOND_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_NETHERITE_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_NETHERITE_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_LAPIS_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_LAPIS_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_REDSTONE_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_REDSTONE_FLUID_TYPE.get());
        }
    }
}
