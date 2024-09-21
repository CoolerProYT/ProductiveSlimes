package com.coolerpromc.productiveslimes;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.block.custom.SlimeBlock;
import com.coolerpromc.productiveslimes.block.entity.ModBlockEntities;
import com.coolerpromc.productiveslimes.compat.top.GetTheOneProbe;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.entity.SlimeModel;
import com.coolerpromc.productiveslimes.entity.renderer.*;
import com.coolerpromc.productiveslimes.entity.slime.OakSlime;
import com.coolerpromc.productiveslimes.fluid.BaseFluidType;
import com.coolerpromc.productiveslimes.fluid.ModFluidTypes;
import com.coolerpromc.productiveslimes.fluid.ModFluids;
import com.coolerpromc.productiveslimes.item.ModCreativeTabs;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.item.custom.BucketItem;
import com.coolerpromc.productiveslimes.item.custom.SlimeballItem;
import com.coolerpromc.productiveslimes.recipe.ModRecipes;
import com.coolerpromc.productiveslimes.screen.ModMenuTypes;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
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
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import javax.swing.text.html.parser.Entity;
import java.util.Arrays;

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
        public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(SlimeModel.SLIME_TEXTURE, SlimeModel::createOuterBodyLayer);
        }

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
            EntityRenderers.register(ModEntities.OAK_SLIME.get(), OakSlimeRenderer::new);
            EntityRenderers.register(ModEntities.SAND_SLIME.get(), SandSlimeRenderer::new);
            EntityRenderers.register(ModEntities.ANDESITE_SLIME.get(), AndesiteSlimeRenderer::new);
            EntityRenderers.register(ModEntities.SNOW_SLIME.get(), SnowSlimeRenderer::new);
            EntityRenderers.register(ModEntities.ICE_SLIME.get(), IceSlimeRenderer::new);
            EntityRenderers.register(ModEntities.MUD_SLIME.get(), MudSlimeRenderer::new);
            EntityRenderers.register(ModEntities.CLAY_SLIME.get(), ClaySlimeRenderer::new);
            EntityRenderers.register(ModEntities.RED_SAND_SLIME.get(), RedSandSlimeRenderer::new);
            EntityRenderers.register(ModEntities.MOSS_SLIME.get(), MossSlimeRenderer::new);
            EntityRenderers.register(ModEntities.DEEPSLATE_SLIME.get(), DeepslateSlimeRenderer::new);
            EntityRenderers.register(ModEntities.GRANITE_SLIME.get(), GraniteSlimeRenderer::new);
            EntityRenderers.register(ModEntities.DIORITE_SLIME.get(), DioriteSlimeRenderer::new);
            EntityRenderers.register(ModEntities.CALCITE_SLIME.get(), CalciteSlimeRenderer::new);
            EntityRenderers.register(ModEntities.TUFF_SLIME.get(), TuffSlimeRenderer::new);
            EntityRenderers.register(ModEntities.DRIPSTONE_SLIME.get(), DripstoneSlimeRenderer::new);
            EntityRenderers.register(ModEntities.NETHERRACK_SLIME.get(), NetherrackSlimeRenderer::new);
            EntityRenderers.register(ModEntities.PRISMARINE_SLIME.get(), PrismarineSlimeRenderer::new);
            EntityRenderers.register(ModEntities.MAGMA_SLIME.get(), MagmaSlimeRenderer::new);
            EntityRenderers.register(ModEntities.OBSIDIAN_SLIME.get(), ObsidianSlimeRenderer::new);
            EntityRenderers.register(ModEntities.SOUL_SAND_SLIME.get(), SoulSandSlimeRenderer::new);
            EntityRenderers.register(ModEntities.SOUL_SOIL_SLIME.get(), SoulSoilSlimeRenderer::new);
            EntityRenderers.register(ModEntities.BLACKSTONE_SLIME.get(), BlackstoneSlimeRenderer::new);
            EntityRenderers.register(ModEntities.BASALT_SLIME.get(), BasaltSlimeRenderer::new);
            EntityRenderers.register(ModEntities.QUARTZ_SLIME.get(), QuartzSlimeRenderer::new);
            EntityRenderers.register(ModEntities.GLOWSTONE_SLIME.get(), GlowstoneSlimeRenderer::new);
            EntityRenderers.register(ModEntities.ENDSTONE_SLIME.get(), EndStoneSlimeRenderer::new);
            EntityRenderers.register(ModEntities.AMETHYST_SLIME.get(), AmethystSlimeRenderer::new);
            EntityRenderers.register(ModEntities.BROWN_MUSHROOM_SLIME.get(), BrownMushroomSlimeRenderer::new);
            EntityRenderers.register(ModEntities.RED_MUSHROOM_SLIME.get(), RedMushroomSlimeRenderer::new);
            EntityRenderers.register(ModEntities.CACTUS_SLIME.get(), CactusSlimeRenderer::new);
            EntityRenderers.register(ModEntities.COAL_SLIME.get(), CoalSlimeRenderer::new);
            EntityRenderers.register(ModEntities.GRAVEL_SLIME.get(), GravelSlimeRenderer::new);

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
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_OAK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_OAK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_SAND.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_SAND.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_ANDESITE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_ANDESITE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_SNOW.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_SNOW.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_ICE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_ICE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_MUD.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_MUD.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_CLAY.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_CLAY.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_RED_SAND.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_RED_SAND.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_MOSS.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_MOSS.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_DEEPSLATE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_DEEPSLATE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_GRANITE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_GRANITE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_DIORITE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_DIORITE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_CALCITE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_CALCITE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_TUFF.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_TUFF.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_DRIPSTONE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_DRIPSTONE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_NETHERRACK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_NETHERRACK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_PRISMARINE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_PRISMARINE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_MAGMA.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_MAGMA.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_OBSIDIAN.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_OBSIDIAN.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_SOUL_SAND.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_SOUL_SAND.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_SOUL_SOIL.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_SOUL_SOIL.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_BLACKSTONE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_BLACKSTONE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_BASALT.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_BASALT.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_QUARTZ.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_QUARTZ.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_GLOWSTONE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_GLOWSTONE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_ENDSTONE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_ENDSTONE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_AMETHYST.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_AMETHYST.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_BROWN_MUSHROOM.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_BROWN_MUSHROOM.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_RED_MUSHROOM.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_RED_MUSHROOM.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_CACTUS.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_CACTUS.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_COAL.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_COAL.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_GRAVEL.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_GRAVEL.get(), RenderType.translucent());

                ItemBlockRenderTypes.setRenderLayer(ModBlocks.DIRT_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.STONE_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.IRON_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.COPPER_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.GOLD_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.DIAMOND_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.NETHERITE_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.LAPIS_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.REDSTONE_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.OAK_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.SAND_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.ANDESITE_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.SNOW_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.ICE_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.MUD_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.CLAY_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.RED_SAND_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.MOSS_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.DEEPSLATE_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.GRANITE_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.DIORITE_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.CALCITE_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.TUFF_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.DRIPSTONE_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.NETHERRACK_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.PRISMARINE_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.MAGMA_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.OBSIDIAN_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.SOUL_SAND_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.SOUL_SOIL_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.BLACKSTONE_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.BASALT_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.QUARTZ_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.GLOWSTONE_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.ENDSTONE_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.AMETHYST_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.BROWN_MUSHROOM_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.RED_MUSHROOM_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.CACTUS_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.COAL_SLIME_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.GRAVEL_SLIME_BLOCK.get(), RenderType.translucent());
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
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_OAK_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_OAK_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_SAND_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_SAND_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_ANDESITE_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_ANDESITE_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_SNOW_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_SNOW_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_ICE_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_ICE_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_MUD_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_MUD_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_CLAY_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_CLAY_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_RED_SAND_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_RED_SAND_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_MOSS_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_MOSS_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_DEEPSLATE_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_DEEPSLATE_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_GRANITE_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_GRANITE_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_DIORITE_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_DIORITE_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_CALCITE_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_CALCITE_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_TUFF_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_TUFF_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_DRIPSTONE_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_DRIPSTONE_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_NETHERRACK_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_NETHERRACK_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_PRISMARINE_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_PRISMARINE_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_MAGMA_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_MAGMA_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_OBSIDIAN_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_OBSIDIAN_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_SOUL_SAND_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_SOUL_SAND_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_SOUL_SOIL_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_SOUL_SOIL_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_BLACKSTONE_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_BLACKSTONE_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_BASALT_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_BASALT_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_QUARTZ_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_QUARTZ_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_GLOWSTONE_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_GLOWSTONE_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_ENDSTONE_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_ENDSTONE_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_AMETHYST_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_AMETHYST_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_BROWN_MUSHROOM_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_BROWN_MUSHROOM_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_RED_MUSHROOM_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_RED_MUSHROOM_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_CACTUS_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_CACTUS_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_COAL_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_COAL_FLUID_TYPE.get());
            event.registerFluidType(((BaseFluidType) ModFluidTypes.MOLTEN_GRAVEL_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.MOLTEN_GRAVEL_FLUID_TYPE.get());
        }

        @SubscribeEvent
        public static void onRegisterColorHandlers(RegisterColorHandlersEvent.Block event) {
            registerSlimeBlockColorHandlers(event,
                    ModBlocks.DIRT_SLIME_BLOCK.value(),
                    ModBlocks.STONE_SLIME_BLOCK.value(),
                    ModBlocks.IRON_SLIME_BLOCK.value(),
                    ModBlocks.COPPER_SLIME_BLOCK.value(),
                    ModBlocks.GOLD_SLIME_BLOCK.value(),
                    ModBlocks.DIAMOND_SLIME_BLOCK.value(),
                    ModBlocks.NETHERITE_SLIME_BLOCK.value(),
                    ModBlocks.LAPIS_SLIME_BLOCK.value(),
                    ModBlocks.REDSTONE_SLIME_BLOCK.value(),
                    ModBlocks.OAK_SLIME_BLOCK.value(),
                    ModBlocks.COAL_SLIME_BLOCK.value(),
                    ModBlocks.GRAVEL_SLIME_BLOCK.value(),
                    ModBlocks.SAND_SLIME_BLOCK.value(),
                    ModBlocks.ANDESITE_SLIME_BLOCK.value(),
                    ModBlocks.SNOW_SLIME_BLOCK.value(),
                    ModBlocks.ICE_SLIME_BLOCK.value(),
                    ModBlocks.MUD_SLIME_BLOCK.value(),
                    ModBlocks.CLAY_SLIME_BLOCK.value(),
                    ModBlocks.RED_SAND_SLIME_BLOCK.value(),
                    ModBlocks.MOSS_SLIME_BLOCK.value(),
                    ModBlocks.DEEPSLATE_SLIME_BLOCK.value(),
                    ModBlocks.GRANITE_SLIME_BLOCK.value(),
                    ModBlocks.DIORITE_SLIME_BLOCK.value(),
                    ModBlocks.CALCITE_SLIME_BLOCK.value(),
                    ModBlocks.TUFF_SLIME_BLOCK.value(),
                    ModBlocks.DRIPSTONE_SLIME_BLOCK.value(),
                    ModBlocks.NETHERRACK_SLIME_BLOCK.value(),
                    ModBlocks.PRISMARINE_SLIME_BLOCK.value(),
                    ModBlocks.MAGMA_SLIME_BLOCK.value(),
                    ModBlocks.OBSIDIAN_SLIME_BLOCK.value(),
                    ModBlocks.SOUL_SAND_SLIME_BLOCK.value(),
                    ModBlocks.SOUL_SOIL_SLIME_BLOCK.value(),
                    ModBlocks.BLACKSTONE_SLIME_BLOCK.value(),
                    ModBlocks.BASALT_SLIME_BLOCK.value(),
                    ModBlocks.QUARTZ_SLIME_BLOCK.value(),
                    ModBlocks.GLOWSTONE_SLIME_BLOCK.value(),
                    ModBlocks.ENDSTONE_SLIME_BLOCK.value(),
                    ModBlocks.AMETHYST_SLIME_BLOCK.value(),
                    ModBlocks.BROWN_MUSHROOM_SLIME_BLOCK.value(),
                    ModBlocks.RED_MUSHROOM_SLIME_BLOCK.value(),
                    ModBlocks.CACTUS_SLIME_BLOCK.value()
            );
        }

        @SubscribeEvent
        public static void onRegisterColorHandlers(RegisterColorHandlersEvent.Item event) {
            registerSlimeBlockColorHandlers(event,
                    ModBlocks.DIRT_SLIME_BLOCK.value().asItem(),
                    ModBlocks.STONE_SLIME_BLOCK.value().asItem(),
                    ModBlocks.IRON_SLIME_BLOCK.value().asItem(),
                    ModBlocks.COPPER_SLIME_BLOCK.value().asItem(),
                    ModBlocks.GOLD_SLIME_BLOCK.value().asItem(),
                    ModBlocks.DIAMOND_SLIME_BLOCK.value().asItem(),
                    ModBlocks.NETHERITE_SLIME_BLOCK.value().asItem(),
                    ModBlocks.LAPIS_SLIME_BLOCK.value().asItem(),
                    ModBlocks.REDSTONE_SLIME_BLOCK.value().asItem(),
                    ModBlocks.OAK_SLIME_BLOCK.value().asItem(),
                    ModBlocks.COAL_SLIME_BLOCK.value().asItem(),
                    ModBlocks.GRAVEL_SLIME_BLOCK.value().asItem(),
                    ModBlocks.SAND_SLIME_BLOCK.value().asItem(),
                    ModBlocks.ANDESITE_SLIME_BLOCK.value().asItem(),
                    ModBlocks.SNOW_SLIME_BLOCK.value().asItem(),
                    ModBlocks.ICE_SLIME_BLOCK.value().asItem(),
                    ModBlocks.MUD_SLIME_BLOCK.value().asItem(),
                    ModBlocks.CLAY_SLIME_BLOCK.value().asItem(),
                    ModBlocks.RED_SAND_SLIME_BLOCK.value().asItem(),
                    ModBlocks.MOSS_SLIME_BLOCK.value().asItem(),
                    ModBlocks.DEEPSLATE_SLIME_BLOCK.value().asItem(),
                    ModBlocks.GRANITE_SLIME_BLOCK.value().asItem(),
                    ModBlocks.DIORITE_SLIME_BLOCK.value().asItem(),
                    ModBlocks.CALCITE_SLIME_BLOCK.value().asItem(),
                    ModBlocks.TUFF_SLIME_BLOCK.value().asItem(),
                    ModBlocks.DRIPSTONE_SLIME_BLOCK.value().asItem(),
                    ModBlocks.NETHERRACK_SLIME_BLOCK.value().asItem(),
                    ModBlocks.PRISMARINE_SLIME_BLOCK.value().asItem(),
                    ModBlocks.MAGMA_SLIME_BLOCK.value().asItem(),
                    ModBlocks.OBSIDIAN_SLIME_BLOCK.value().asItem(),
                    ModBlocks.SOUL_SAND_SLIME_BLOCK.value().asItem(),
                    ModBlocks.SOUL_SOIL_SLIME_BLOCK.value().asItem(),
                    ModBlocks.BLACKSTONE_SLIME_BLOCK.value().asItem(),
                    ModBlocks.BASALT_SLIME_BLOCK.value().asItem(),
                    ModBlocks.QUARTZ_SLIME_BLOCK.value().asItem(),
                    ModBlocks.GLOWSTONE_SLIME_BLOCK.value().asItem(),
                    ModBlocks.ENDSTONE_SLIME_BLOCK.value().asItem(),
                    ModBlocks.AMETHYST_SLIME_BLOCK.value().asItem(),
                    ModBlocks.BROWN_MUSHROOM_SLIME_BLOCK.value().asItem(),
                    ModBlocks.RED_MUSHROOM_SLIME_BLOCK.value().asItem(),
                    ModBlocks.CACTUS_SLIME_BLOCK.value().asItem()
            );

            registerSlimeballColorHandlers(event,
                    ModItems.DIRT_SLIME_BALL.value().asItem(),
                    ModItems.STONE_SLIME_BALL.value().asItem(),
                    ModItems.IRON_SLIME_BALL.value().asItem(),
                    ModItems.COPPER_SLIME_BALL.value().asItem(),
                    ModItems.GOLD_SLIME_BALL.value().asItem(),
                    ModItems.DIAMOND_SLIME_BALL.value().asItem(),
                    ModItems.NETHERITE_SLIME_BALL.value().asItem(),
                    ModItems.LAPIS_SLIME_BALL.value().asItem(),
                    ModItems.REDSTONE_SLIME_BALL.value().asItem(),
                    ModItems.OAK_SLIME_BALL.value().asItem(),
                    ModItems.COAL_SLIME_BALL.value().asItem(),
                    ModItems.GRAVEL_SLIME_BALL.value().asItem(),
                    ModItems.SAND_SLIME_BALL.value().asItem(),
                    ModItems.ANDESITE_SLIME_BALL.value().asItem(),
                    ModItems.SNOW_SLIME_BALL.value().asItem(),
                    ModItems.ICE_SLIME_BALL.value().asItem(),
                    ModItems.MUD_SLIME_BALL.value().asItem(),
                    ModItems.CLAY_SLIME_BALL.value().asItem(),
                    ModItems.RED_SAND_SLIME_BALL.value().asItem(),
                    ModItems.MOSS_SLIME_BALL.value().asItem(),
                    ModItems.DEEPSLATE_SLIME_BALL.value().asItem(),
                    ModItems.GRANITE_SLIME_BALL.value().asItem(),
                    ModItems.DIORITE_SLIME_BALL.value().asItem(),
                    ModItems.CALCITE_SLIME_BALL.value().asItem(),
                    ModItems.TUFF_SLIME_BALL.value().asItem(),
                    ModItems.DRIPSTONE_SLIME_BALL.value().asItem(),
                    ModItems.NETHERRACK_SLIME_BALL.value().asItem(),
                    ModItems.PRISMARINE_SLIME_BALL.value().asItem(),
                    ModItems.MAGMA_SLIME_BALL.value().asItem(),
                    ModItems.OBSIDIAN_SLIME_BALL.value().asItem(),
                    ModItems.SOUL_SAND_SLIME_BALL.value().asItem(),
                    ModItems.SOUL_SOIL_SLIME_BALL.value().asItem(),
                    ModItems.BLACKSTONE_SLIME_BALL.value().asItem(),
                    ModItems.BASALT_SLIME_BALL.value().asItem(),
                    ModItems.QUARTZ_SLIME_BALL.value().asItem(),
                    ModItems.GLOWSTONE_SLIME_BALL.value().asItem(),
                    ModItems.ENDSTONE_SLIME_BALL.value().asItem(),
                    ModItems.AMETHYST_SLIME_BALL.value().asItem(),
                    ModItems.BROWN_MUSHROOM_SLIME_BALL.value().asItem(),
                    ModItems.RED_MUSHROOM_SLIME_BALL.value().asItem(),
                    ModItems.CACTUS_SLIME_BALL.value().asItem()
            );

            registerBucketColorHandlers(event,
                    ModFluids.MOLTEN_DIRT_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_STONE_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_IRON_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_COPPER_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_GOLD_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_DIAMOND_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_NETHERITE_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_LAPIS_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_REDSTONE_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_OAK_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_COAL_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_GRAVEL_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_SAND_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_ANDESITE_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_SNOW_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_ICE_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_MUD_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_CLAY_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_RED_SAND_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_MOSS_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_DEEPSLATE_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_GRANITE_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_DIORITE_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_CALCITE_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_TUFF_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_DRIPSTONE_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_NETHERRACK_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_PRISMARINE_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_MAGMA_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_OBSIDIAN_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_SOUL_SAND_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_SOUL_SOIL_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_BLACKSTONE_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_BASALT_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_QUARTZ_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_GLOWSTONE_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_ENDSTONE_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_AMETHYST_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_BROWN_MUSHROOM_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_RED_MUSHROOM_BUCKET.value().asItem(),
                    ModFluids.MOLTEN_CACTUS_BUCKET.value().asItem()
            );
        }

        private static void registerSlimeBlockColorHandlers(RegisterColorHandlersEvent.Block event, Block... blocks) {
            for (Block block : blocks) {
                event.register((pState, pLevel, pPos, pTintIndex) -> {
                    if (pState.getBlock() instanceof SlimeBlock slimeBlock) {
                        return slimeBlock.getColor();
                    }
                    return 0xFFFFFFFF; // Default no color
                }, block);
            }
        }


        private static void registerSlimeBlockColorHandlers(RegisterColorHandlersEvent.Item event, Item... items) {
            for (Item item : items) {
                event.register((itemStack, pTintIndex) -> {
                    if (itemStack.getItem() instanceof BlockItem blockItem) {
                        if (blockItem.getBlock() instanceof SlimeBlock slimeBlock) {
                            return slimeBlock.getColor();
                        }
                    }
                    return 0xFFFFFFFF; // Default no color
                }, item);
            }
        }

        private static void registerSlimeballColorHandlers(RegisterColorHandlersEvent.Item event, Item... items) {
            for (Item item : items) {
                event.register((itemStack, pTintIndex) -> {
                    if (itemStack.getItem() instanceof SlimeballItem slimeballItem) {
                        return slimeballItem.getColor();
                    }

                    return 0xFFFFFFFF; // Default no color
                }, item);
            }
        }

        private static void registerBucketColorHandlers(RegisterColorHandlersEvent.Item event, Item... items) {
            for (Item item : items) {
                event.register((itemStack, pTintIndex) -> {
                    if (itemStack.getItem() instanceof BucketItem bucketItem) {
                        if (pTintIndex == 1) {
                            return bucketItem.getColor();
                        }
                    }

                    return 0xFFFFFFFF; // Default no color
                }, item);
            }
        }
    }
}