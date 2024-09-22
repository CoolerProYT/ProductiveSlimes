package com.coolerpromc.productiveslimes;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.block.custom.SlimeBlock;
import com.coolerpromc.productiveslimes.block.entity.ModBlockEntities;
import com.coolerpromc.productiveslimes.block.entity.renderer.DnaExtractorBlockEntityRenderer;
import com.coolerpromc.productiveslimes.compat.top.GetTheOneProbe;
import com.coolerpromc.productiveslimes.datacomponent.ModDataComponents;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.entity.SlimeModel;
import com.coolerpromc.productiveslimes.entity.renderer.*;
import com.coolerpromc.productiveslimes.fluid.BaseFluidType;
import com.coolerpromc.productiveslimes.fluid.ModFluidTypes;
import com.coolerpromc.productiveslimes.fluid.ModFluids;
import com.coolerpromc.productiveslimes.item.ModCreativeTabs;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.item.custom.BucketItem;
import com.coolerpromc.productiveslimes.item.custom.DnaItem;
import com.coolerpromc.productiveslimes.item.custom.SlimeballItem;
import com.coolerpromc.productiveslimes.recipe.ModRecipes;
import com.coolerpromc.productiveslimes.screen.ModMenuTypes;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
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
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.swing.text.html.parser.Entity;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

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
        ModDataComponents.register(modEventBus);

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
        public static void onEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.DNA_EXTRACTOR_BE.get(), DnaExtractorBlockEntityRenderer::new);
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
            EntityRenderers.register(ModEntities.ENERGY_SLIME.get(), EnergySlimeRenderer::new);

            event.enqueueWork(() -> {
                registerFluidRenderLayer(
                        ModFluids.SOURCE_MOLTEN_DIRT.get(),
                        ModFluids.FLOWING_MOLTEN_DIRT.get(),
                        ModFluids.SOURCE_MOLTEN_STONE.get(),
                        ModFluids.FLOWING_MOLTEN_STONE.get(),
                        ModFluids.SOURCE_MOLTEN_IRON.get(),
                        ModFluids.FLOWING_MOLTEN_IRON.get(),
                        ModFluids.SOURCE_MOLTEN_COPPER.get(),
                        ModFluids.FLOWING_MOLTEN_COPPER.get(),
                        ModFluids.SOURCE_MOLTEN_GOLD.get(),
                        ModFluids.FLOWING_MOLTEN_GOLD.get(),
                        ModFluids.SOURCE_MOLTEN_DIAMOND.get(),
                        ModFluids.FLOWING_MOLTEN_DIAMOND.get(),
                        ModFluids.SOURCE_MOLTEN_NETHERITE.get(),
                        ModFluids.FLOWING_MOLTEN_NETHERITE.get(),
                        ModFluids.SOURCE_MOLTEN_LAPIS.get(),
                        ModFluids.FLOWING_MOLTEN_LAPIS.get(),
                        ModFluids.SOURCE_MOLTEN_REDSTONE.get(),
                        ModFluids.FLOWING_MOLTEN_REDSTONE.get(),
                        ModFluids.SOURCE_MOLTEN_OAK.get(),
                        ModFluids.FLOWING_MOLTEN_OAK.get(),
                        ModFluids.SOURCE_MOLTEN_SAND.get(),
                        ModFluids.FLOWING_MOLTEN_SAND.get(),
                        ModFluids.SOURCE_MOLTEN_ANDESITE.get(),
                        ModFluids.FLOWING_MOLTEN_ANDESITE.get(),
                        ModFluids.SOURCE_MOLTEN_SNOW.get(),
                        ModFluids.FLOWING_MOLTEN_SNOW.get(),
                        ModFluids.SOURCE_MOLTEN_ICE.get(),
                        ModFluids.FLOWING_MOLTEN_ICE.get(),
                        ModFluids.SOURCE_MOLTEN_MUD.get(),
                        ModFluids.FLOWING_MOLTEN_MUD.get(),
                        ModFluids.SOURCE_MOLTEN_CLAY.get(),
                        ModFluids.FLOWING_MOLTEN_CLAY.get(),
                        ModFluids.SOURCE_MOLTEN_RED_SAND.get(),
                        ModFluids.FLOWING_MOLTEN_RED_SAND.get(),
                        ModFluids.SOURCE_MOLTEN_MOSS.get(),
                        ModFluids.FLOWING_MOLTEN_MOSS.get(),
                        ModFluids.SOURCE_MOLTEN_DEEPSLATE.get(),
                        ModFluids.FLOWING_MOLTEN_DEEPSLATE.get(),
                        ModFluids.SOURCE_MOLTEN_GRANITE.get(),
                        ModFluids.FLOWING_MOLTEN_GRANITE.get(),
                        ModFluids.SOURCE_MOLTEN_DIORITE.get(),
                        ModFluids.FLOWING_MOLTEN_DIORITE.get(),
                        ModFluids.SOURCE_MOLTEN_CALCITE.get(),
                        ModFluids.FLOWING_MOLTEN_CALCITE.get(),
                        ModFluids.SOURCE_MOLTEN_TUFF.get(),
                        ModFluids.FLOWING_MOLTEN_TUFF.get(),
                        ModFluids.SOURCE_MOLTEN_DRIPSTONE.get(),
                        ModFluids.FLOWING_MOLTEN_DRIPSTONE.get(),
                        ModFluids.SOURCE_MOLTEN_NETHERRACK.get(),
                        ModFluids.FLOWING_MOLTEN_NETHERRACK.get(),
                        ModFluids.SOURCE_MOLTEN_PRISMARINE.get(),
                        ModFluids.FLOWING_MOLTEN_PRISMARINE.get(),
                        ModFluids.SOURCE_MOLTEN_MAGMA.get(),
                        ModFluids.FLOWING_MOLTEN_MAGMA.get(),
                        ModFluids.SOURCE_MOLTEN_OBSIDIAN.get(),
                        ModFluids.FLOWING_MOLTEN_OBSIDIAN.get(),
                        ModFluids.SOURCE_MOLTEN_SOUL_SAND.get(),
                        ModFluids.FLOWING_MOLTEN_SOUL_SAND.get(),
                        ModFluids.SOURCE_MOLTEN_SOUL_SOIL.get(),
                        ModFluids.FLOWING_MOLTEN_SOUL_SOIL.get(),
                        ModFluids.SOURCE_MOLTEN_BLACKSTONE.get(),
                        ModFluids.FLOWING_MOLTEN_BLACKSTONE.get(),
                        ModFluids.SOURCE_MOLTEN_BASALT.get(),
                        ModFluids.FLOWING_MOLTEN_BASALT.get(),
                        ModFluids.SOURCE_MOLTEN_QUARTZ.get(),
                        ModFluids.FLOWING_MOLTEN_QUARTZ.get(),
                        ModFluids.SOURCE_MOLTEN_GLOWSTONE.get(),
                        ModFluids.FLOWING_MOLTEN_GLOWSTONE.get(),
                        ModFluids.SOURCE_MOLTEN_ENDSTONE.get(),
                        ModFluids.FLOWING_MOLTEN_ENDSTONE.get(),
                        ModFluids.SOURCE_MOLTEN_AMETHYST.get(),
                        ModFluids.FLOWING_MOLTEN_AMETHYST.get(),
                        ModFluids.SOURCE_MOLTEN_BROWN_MUSHROOM.get(),
                        ModFluids.FLOWING_MOLTEN_BROWN_MUSHROOM.get(),
                        ModFluids.SOURCE_MOLTEN_RED_MUSHROOM.get(),
                        ModFluids.FLOWING_MOLTEN_RED_MUSHROOM.get(),
                        ModFluids.SOURCE_MOLTEN_CACTUS.get(),
                        ModFluids.FLOWING_MOLTEN_CACTUS.get(),
                        ModFluids.SOURCE_MOLTEN_COAL.get(),
                        ModFluids.FLOWING_MOLTEN_COAL.get(),
                        ModFluids.SOURCE_MOLTEN_GRAVEL.get(),
                        ModFluids.FLOWING_MOLTEN_GRAVEL.get()
                );

                registerBlockRenderLayer(
                        ModBlocks.DIRT_SLIME_BLOCK.get(),
                        ModBlocks.STONE_SLIME_BLOCK.get(),
                        ModBlocks.IRON_SLIME_BLOCK.get(),
                        ModBlocks.COPPER_SLIME_BLOCK.get(),
                        ModBlocks.GOLD_SLIME_BLOCK.get(),
                        ModBlocks.DIAMOND_SLIME_BLOCK.get(),
                        ModBlocks.NETHERITE_SLIME_BLOCK.get(),
                        ModBlocks.LAPIS_SLIME_BLOCK.get(),
                        ModBlocks.REDSTONE_SLIME_BLOCK.get(),
                        ModBlocks.OAK_SLIME_BLOCK.get(),
                        ModBlocks.COAL_SLIME_BLOCK.get(),
                        ModBlocks.GRAVEL_SLIME_BLOCK.get(),
                        ModBlocks.SAND_SLIME_BLOCK.get(),
                        ModBlocks.ANDESITE_SLIME_BLOCK.get(),
                        ModBlocks.SNOW_SLIME_BLOCK.get(),
                        ModBlocks.ICE_SLIME_BLOCK.get(),
                        ModBlocks.MUD_SLIME_BLOCK.get(),
                        ModBlocks.CLAY_SLIME_BLOCK.get(),
                        ModBlocks.RED_SAND_SLIME_BLOCK.get(),
                        ModBlocks.MOSS_SLIME_BLOCK.get(),
                        ModBlocks.DEEPSLATE_SLIME_BLOCK.get(),
                        ModBlocks.GRANITE_SLIME_BLOCK.get(),
                        ModBlocks.DIORITE_SLIME_BLOCK.get(),
                        ModBlocks.CALCITE_SLIME_BLOCK.get(),
                        ModBlocks.TUFF_SLIME_BLOCK.get(),
                        ModBlocks.DRIPSTONE_SLIME_BLOCK.get(),
                        ModBlocks.NETHERRACK_SLIME_BLOCK.get(),
                        ModBlocks.PRISMARINE_SLIME_BLOCK.get(),
                        ModBlocks.MAGMA_SLIME_BLOCK.get(),
                        ModBlocks.OBSIDIAN_SLIME_BLOCK.get(),
                        ModBlocks.SOUL_SAND_SLIME_BLOCK.get(),
                        ModBlocks.SOUL_SOIL_SLIME_BLOCK.get(),
                        ModBlocks.BLACKSTONE_SLIME_BLOCK.get(),
                        ModBlocks.BASALT_SLIME_BLOCK.get(),
                        ModBlocks.QUARTZ_SLIME_BLOCK.get(),
                        ModBlocks.GLOWSTONE_SLIME_BLOCK.get(),
                        ModBlocks.ENDSTONE_SLIME_BLOCK.get(),
                        ModBlocks.AMETHYST_SLIME_BLOCK.get(),
                        ModBlocks.BROWN_MUSHROOM_SLIME_BLOCK.get(),
                        ModBlocks.RED_MUSHROOM_SLIME_BLOCK.get(),
                        ModBlocks.CACTUS_SLIME_BLOCK.get(),
                        ModBlocks.ENERGY_SLIME_BLOCK.get(),
                        ModBlocks.DNA_EXTRACTOR.get()
                );

                ItemBlockRenderTypes.setRenderLayer(ModBlocks.CABLE.get(), renderType -> true);
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
                    ModBlocks.CACTUS_SLIME_BLOCK.value(),
                    ModBlocks.ENERGY_SLIME_BLOCK.value()
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
                    ModBlocks.CACTUS_SLIME_BLOCK.value().asItem(),
                    ModBlocks.ENERGY_SLIME_BLOCK.value().asItem()
            );

            registerAllSlimeballColor(event);

            registerAllSlimeDnaColor(event);

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

        public static void registerAllSlimeballColor(RegisterColorHandlersEvent.Item event) {
            Field[] fields = ModItems.class.getFields();

            for (Field field : fields) {
                try {
                    Object value = field.get(null);

                    if (value instanceof Supplier<?> supplier) {
                        Item item = (Item) supplier.get();
                        if (item instanceof SlimeballItem) {
                            event.register((stack, tintIndex) -> ((SlimeballItem) item).getColor(), item);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        public static void registerAllSlimeDnaColor(RegisterColorHandlersEvent.Item event) {
            Field[] fields = ModItems.class.getFields();

            for (Field field : fields) {
                try {
                    Object value = field.get(null);

                    if (value instanceof Supplier<?> supplier) {
                        Item item = (Item) supplier.get();
                        if (item instanceof DnaItem) {
                            event.register((stack, tintIndex) -> ((DnaItem) item).getColor(), item);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
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

        private static void registerFluidRenderLayer(Fluid... fluids) {
            for (Fluid f : fluids) {
                ItemBlockRenderTypes.setRenderLayer(f, RenderType.translucent());
            }
        }

        private static void registerBlockRenderLayer(Block... blocks) {
            for (Block b : blocks) {
                ItemBlockRenderTypes.setRenderLayer(b, RenderType.translucent());
            }
        }
    }
}