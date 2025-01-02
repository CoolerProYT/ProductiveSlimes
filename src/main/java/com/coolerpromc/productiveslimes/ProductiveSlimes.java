package com.coolerpromc.productiveslimes;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.block.custom.SlimeBlock;
import com.coolerpromc.productiveslimes.block.entity.ModBlockEntities;
import com.coolerpromc.productiveslimes.block.entity.renderer.*;
import com.coolerpromc.productiveslimes.command.ModCommands;
import com.coolerpromc.productiveslimes.compat.top.GetTheOneProbe;
import com.coolerpromc.productiveslimes.config.CustomContentRegistry;
import com.coolerpromc.productiveslimes.config.fluid.FluidResources;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.entity.SlimeModel;
import com.coolerpromc.productiveslimes.entity.renderer.*;
import com.coolerpromc.productiveslimes.fluid.ModFluidResources;
import com.coolerpromc.productiveslimes.fluid.ModFluids;
import com.coolerpromc.productiveslimes.handler.SlimeData;
import com.coolerpromc.productiveslimes.item.ModCreativeTabs;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.item.custom.BucketItem;
import com.coolerpromc.productiveslimes.item.custom.DnaItem;
import com.coolerpromc.productiveslimes.item.custom.SlimeballItem;
import com.coolerpromc.productiveslimes.recipe.ModRecipes;
import com.coolerpromc.productiveslimes.screen.*;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import com.coolerpromc.productiveslimes.villager.ModVillagers;
import com.coolerpromc.productiveslimes.worldgen.biome.ModBiomeGeneration;
import com.coolerpromc.productiveslimes.worldgen.biome.ModBiomes;
import com.coolerpromc.productiveslimes.worldgen.biome.ModConfiguredFeatures;
import com.coolerpromc.productiveslimes.worldgen.biome.surface.ModConfiguredSurfaceBuilders;
import com.coolerpromc.productiveslimes.worldgen.biome.surface.ModSurfaceBuilders;
import com.coolerpromc.productiveslimes.worldgen.structure.SlimyVillagePools;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmlserverevents.FMLServerStartingEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(ProductiveSlimes.MODID)
public class ProductiveSlimes
{
    public static final String MODID = "productiveslimes";

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ProductiveSlimes.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ProductiveSlimes.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, ProductiveSlimes.MODID);

    public ProductiveSlimes()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        if (ModList.get().isLoaded("theoneprobe"))
        {
            modEventBus.addListener(this::enqueueIMC);
        }

        CustomContentRegistry.initialize(ITEMS, BLOCKS, ENTITY_TYPES);

        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);
        ENTITY_TYPES.register(modEventBus);

        FluidResources.register(modEventBus);

        ModTierLists.init();

        ModItems.registerTierItem();
        ModItems.register(modEventBus);

        ModBlocks.registerTierBlock();
        ModBlocks.register(modEventBus);

        ModEntities.registerTierEntities();
        ModEntities.register(modEventBus);

        ModFluids.registerTierFluids();
        ModFluidResources.register(modEventBus);

        ModCreativeTabs.register();
        ModRecipes.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModVillagers.register(modEventBus);

        SlimyVillagePools.bootstrap();
        ModBiomes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            ModBiomeGeneration.generateBiomes();
        });
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event)
    {
        CustomContentRegistry.handleDatapack(event.getServer());
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo("theoneprobe", "getTheOneProbe", GetTheOneProbe::new);
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        ModCommands.register(event.getDispatcher());
    }

    @SubscribeEvent
    public void onPlayer(PlayerEvent.PlayerLoggedInEvent event) {
        event.getEntity().getServer().getCommands().performCommand(event.getEntity().createCommandSourceStack(), "reload");
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(SlimeModel.SLIME_TEXTURE, SlimeModel::createOuterBodyLayer);
        }

        @SubscribeEvent
        public static void onEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.DNA_EXTRACTOR_BE.get(), DnaExtractorBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.SOLIDING_STATION_BE.get(), SolidingStationBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.DNA_SYNTHESIZER_BE.get(), DnaSynthesizerBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.FLUID_TANK_BE.get(), FluidTankBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.SLIME_SQUEEZER_BE.get(), SlimeSqueezerBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.SLIME_NEST_BE.get(), SlimeNestBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.SLIMEBALL_COLLECTOR_BE.get(), SlimeballCollectorBlockEntityRenderer::new);
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            event.enqueueWork(() -> {
                MenuScreens.register(ModMenuTypes.MELTING_STATION_MENU.get(), MeltingStationScreen::new);
                MenuScreens.register(ModMenuTypes.SOLIDING_STATION_MENU.get(), SolidingStationScreen::new);
                MenuScreens.register(ModMenuTypes.GUIDEBOOK_MENU.get(), GuidebookScreen::new);
                MenuScreens.register(ModMenuTypes.ENERGY_GENERATOR_MENU.get(), EnergyGeneratorScreen::new);
                MenuScreens.register(ModMenuTypes.DNA_EXTRACTOR_MENU.get(), DnaExtractorScreen::new);
                MenuScreens.register(ModMenuTypes.DNA_SYNTHESIZER_MENU.get(), DnaSynthesizerScreen::new);
                MenuScreens.register(ModMenuTypes.SLIME_SQUEEZER_MENU.get(), SlimeSqueezerScreen::new);
                MenuScreens.register(ModMenuTypes.SLIME_NEST_MENU.get(), SlimeNestScreen::new);
                MenuScreens.register(ModMenuTypes.SLIMEBALL_COLLECTOR_MENU.get(), SlimeballCollectorScreen::new);

                EntityRenderers.register(ModEntities.ENERGY_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFFffff70));

                for (Tier name : Tier.values()){
                    ModTiers tiers = ModTierLists.getTierByName(name);
                    EntityRenderers.register(ModTierLists.getEntityByName(tiers.name()).get(), pContext -> new BaseSlimeRenderer(pContext, tiers.color()));
                }

                for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                    EntityRenderers.register(CustomContentRegistry.getSlimeForVariant(variant.getName()).get(), pContext -> new BaseSlimeRenderer(pContext, variant.getColor()));
                }

                registerAllFluidRenderLayer();
                registerAllSlimeBlockRenderLayer();

                ItemBlockRenderTypes.setRenderLayer(ModBlocks.SLIMY_SAPLING.get(), RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.SLIMY_LEAVES.get(), RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.SLIMY_DOOR.get(), RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.SLIMY_TRAPDOOR.get(), RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.LIQUID_SOLIDING_STATION.get(), RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.SLIME_NEST.get(), RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.FLUID_TANK.get(), RenderType.cutout());

                ItemBlockRenderTypes.setRenderLayer(ModBlocks.DNA_SYNTHESIZER.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.DNA_EXTRACTOR.get(), RenderType.translucent());

                SpawnPlacements.register(ModTierLists.getEntityByName(Tier.DIRT.getTierName()).get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityTypes, serverLevel, spawnType, pos, random) -> serverLevel.getBlockState(pos.below()).getBlock() == ModBlocks.SLIMY_GRASS_BLOCK.get());
                SpawnPlacements.register(ModTierLists.getEntityByName(Tier.STONE.getTierName()).get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityTypes, serverLevel, spawnType, pos, random) -> serverLevel.getBlockState(pos.below()).getBlock() == ModBlocks.SLIMY_GRASS_BLOCK.get());
                SpawnPlacements.register(ModTierLists.getEntityByName(Tier.IRON.getTierName()).get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityTypes, serverLevel, spawnType, pos, random) -> serverLevel.getBlockState(pos.below()).getBlock() == ModBlocks.SLIMY_GRASS_BLOCK.get());
            });

            CustomContentRegistry.handleResourcePack();
        }

        @SubscribeEvent
        public static void onRegisterColorHandlers(ColorHandlerEvent.Block event) {
            registerAllSlimeBlockColor(event);
        }

        @SubscribeEvent
        public static void onRegisterColorHandlers(ColorHandlerEvent.Item event) {
            registerAllSlimeballColor(event);
            registerAllSlimeDnaColor(event);
            registerAllBucketColor(event);
            registerAllSlimeBlockColor(event);

            event.getItemColors().register((stack, tintIndex) -> {
                assert stack.getTag() != null;
                if (!stack.hasTag())
                    return 0xFFFFFFFF;

                SlimeData slimeData = SlimeData.fromTag(stack.getTag().getCompound("slime_data"));
                return slimeData.color();
            }, ModItems.SLIME_ITEM.get());
        }

        public static void registerAllSlimeBlockColor(ColorHandlerEvent.Block event) {
            if (ModBlocks.ENERGY_SLIME_BLOCK.get() instanceof SlimeBlock block){
                event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) -> block.getColor(), block);
            }

            for (Tier tier : Tier.values()) {
                ModTiers modTiers = ModTierLists.getTierByName(tier);
                if(ModTierLists.getBlockByName(modTiers.name()).get() instanceof SlimeBlock block)
                    event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) -> block.getColor(), block);
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                if (CustomContentRegistry.getSlimeBlockForVariant(variant.getName()).get() instanceof SlimeBlock block){
                    event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) -> block.getColor(), block);
                }
            }
        }

        public static void registerAllSlimeBlockColor(ColorHandlerEvent.Item event) {
            if (ModBlocks.ENERGY_SLIME_BLOCK.get() instanceof SlimeBlock block){
                event.getItemColors().register((stack, pTintIndex) -> {
                    if (stack.getItem() instanceof BlockItem blockItem) {
                        if (blockItem.getBlock() instanceof SlimeBlock slimeBlock) {
                            return slimeBlock.getColor();
                        }
                    }
                    return 0xFFFFFFFF;
                }, block.asItem());
            }

            for (Tier tier : Tier.values()) {
                ModTiers modTiers = ModTierLists.getTierByName(tier);
                if(ModTierLists.getBlockByName(modTiers.name()).get() instanceof SlimeBlock block) {
                    event.getItemColors().register((stack, pTintIndex) -> {
                        if (stack.getItem() instanceof BlockItem blockItem) {
                            if (blockItem.getBlock() instanceof SlimeBlock slimeBlock) {
                                return slimeBlock.getColor();
                            }
                        }
                        return 0xFFFFFFFF;
                    }, block.asItem());
                }
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                if (CustomContentRegistry.getSlimeBlockForVariant(variant.getName()).get() instanceof SlimeBlock block){
                    event.getItemColors().register((stack, tintIndex) -> {
                        if (stack.getItem() instanceof BlockItem blockItem){
                            if (blockItem.getBlock() instanceof SlimeBlock slimeBlock){
                                return slimeBlock.getColor();
                            }
                        }
                        return 0xFFFFFFFF;
                    }, block.asItem());
                }
            }
        }

        public static void registerAllSlimeballColor(ColorHandlerEvent.Item event) {
            if (ModItems.ENERGY_SLIME_BALL.get() instanceof SlimeballItem item){
                event.getItemColors().register((stack, tintIndex) -> item.getColor(), item);
            }

            for (Tier tier : Tier.values()) {
                ModTiers modTiers = ModTierLists.getTierByName(tier);

                if (ModTierLists.getSlimeballItemByName(modTiers.name()).get() instanceof SlimeballItem item){
                    event.getItemColors().register((stack, tintIndex) -> item.getColor(), item);
                }
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                if (CustomContentRegistry.getSlimeballItemForVariant(variant.getName()).get() instanceof SlimeballItem item){
                    event.getItemColors().register((stack, tintIndex) -> item.getColor(), item);
                }
            }
        }

        public static void registerAllSlimeDnaColor(ColorHandlerEvent.Item event) {
            if (ModItems.SLIME_DNA.get() instanceof DnaItem item){
                event.getItemColors().register((stack, tintIndex) -> item.getColor(), item);
            }

            for (Tier tier : Tier.values()) {
                ModTiers modTiers = ModTierLists.getTierByName(tier);

                if (ModTierLists.getDnaItemByName(modTiers.name()).get() instanceof DnaItem item){
                    event.getItemColors().register((stack, tintIndex) -> item.getColor(), item);
                }
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                if (CustomContentRegistry.getDnaItemForVariant(variant.getName()).get() instanceof DnaItem item){
                    event.getItemColors().register((stack, tintIndex) -> item.getColor(), item);
                }
            }
        }

        public static void registerAllBucketColor(ColorHandlerEvent.Item event) {
            for (Tier tier : Tier.values()) {
                ModTiers modTiers = ModTierLists.getTierByName(tier);
                if (ModTierLists.getBucketItemByName(modTiers.name()).get() instanceof BucketItem bucketItem){
                    event.getItemColors().register((itemStack, pTintIndex) -> pTintIndex == 1 ? bucketItem.getColor() : 0xFFFFFFFF, bucketItem);
                }
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                if (ForgeRegistries.ITEMS.getValue(new ResourceLocation(ProductiveSlimes.MODID, "molten_" + variant.getName() + "_bucket")) instanceof BucketItem bucketItem){
                    event.getItemColors().register((itemStack, pTintIndex) -> pTintIndex == 1 ? bucketItem.getColor() : 0xFFFFFFFF, bucketItem);
                }
            }
        }

        public static void registerAllFluidRenderLayer() {
            for (Tier tier : Tier.values()) {
                ModTiers modTiers = ModTierLists.getTierByName(tier);
                String name = modTiers.name();

                ItemBlockRenderTypes.setRenderLayer(ModTierLists.getSourceByName(name).get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModTierLists.getFlowByName(name).get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModTierLists.getLiquidBlockByName(name).get(), RenderType.translucent());
            }

            FluidResources.fluidList.stream()
                    .filter(fluid -> fluid.isTranslucent)
                    .forEach(fluid -> {
                        ItemBlockRenderTypes.setRenderLayer(fluid.FLUID.get(), RenderType.translucent());
                        ItemBlockRenderTypes.setRenderLayer(fluid.FLUID_FLOW.get(), RenderType.translucent());
                        ItemBlockRenderTypes.setRenderLayer(fluid.FLUID_BLOCK.get(), RenderType.translucent());
                    });
        }

        public static void registerAllSlimeBlockRenderLayer() {
            if (ModBlocks.ENERGY_SLIME_BLOCK.get() instanceof SlimeBlock block){
                ItemBlockRenderTypes.setRenderLayer(block, RenderType.translucent());
            }

            for (Tier tier : Tier.values()) {
                ModTiers modTiers = ModTierLists.getTierByName(tier);

                if (ModTierLists.getBlockByName(modTiers.name()).get() instanceof SlimeBlock block){
                    ItemBlockRenderTypes.setRenderLayer(block, RenderType.translucent());
                }
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                if (CustomContentRegistry.getSlimeBlockForVariant(variant.getName()).get() instanceof SlimeBlock block){
                    ItemBlockRenderTypes.setRenderLayer(block, RenderType.translucent());
                }
            }
        }
    }
}