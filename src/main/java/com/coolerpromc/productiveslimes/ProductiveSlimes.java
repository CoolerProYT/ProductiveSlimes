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
import com.coolerpromc.productiveslimes.entity.SlimeOuterLayer;
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
import com.coolerpromc.productiveslimes.worldgen.structure.SlimyVillagePools;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
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
        event.enqueueWork(ModBiomeGeneration::generateBiomes);
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event)
    {
        CustomContentRegistry.handleDatapack(event.getServer());
        event.getServer().getCommands().performCommand(event.getServer().createCommandSourceStack(), "reload");
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
//        event.getEntity().getServer().getCommands().performCommand(event.getEntity().createCommandSourceStack(), "reload");
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            RenderingRegistry.registerEntityRenderingHandler(ModEntities.ENERGY_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFFffff70));

            for (Tier name : Tier.values()){
                ModTiers tiers = ModTierLists.getTierByName(name);
                RenderingRegistry.registerEntityRenderingHandler(ModTierLists.getEntityByName(tiers.name()).get(), pContext -> new BaseSlimeRenderer(pContext, tiers.color()));
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                RenderingRegistry.registerEntityRenderingHandler(CustomContentRegistry.getSlimeForVariant(variant.getName()).get(), pContext -> new BaseSlimeRenderer(pContext, variant.getColor()));
            }

            event.enqueueWork(() -> {
                ScreenManager.register(ModMenuTypes.MELTING_STATION_MENU.get(), MeltingStationScreen::new);
                ScreenManager.register(ModMenuTypes.SOLIDING_STATION_MENU.get(), SolidingStationScreen::new);
                ScreenManager.register(ModMenuTypes.GUIDEBOOK_MENU.get(), GuidebookScreen::new);
                ScreenManager.register(ModMenuTypes.ENERGY_GENERATOR_MENU.get(), EnergyGeneratorScreen::new);
                ScreenManager.register(ModMenuTypes.DNA_EXTRACTOR_MENU.get(), DnaExtractorScreen::new);
                ScreenManager.register(ModMenuTypes.DNA_SYNTHESIZER_MENU.get(), DnaSynthesizerScreen::new);
                ScreenManager.register(ModMenuTypes.SLIME_SQUEEZER_MENU.get(), SlimeSqueezerScreen::new);
                ScreenManager.register(ModMenuTypes.SLIME_NEST_MENU.get(), SlimeNestScreen::new);
                ScreenManager.register(ModMenuTypes.SLIMEBALL_COLLECTOR_MENU.get(), SlimeballCollectorScreen::new);

                ClientRegistry.bindTileEntityRenderer(ModBlockEntities.DNA_EXTRACTOR_BE.get(), DnaExtractorBlockEntityRenderer::new);
                ClientRegistry.bindTileEntityRenderer(ModBlockEntities.SOLIDING_STATION_BE.get(), SolidingStationBlockEntityRenderer::new);
                ClientRegistry.bindTileEntityRenderer(ModBlockEntities.DNA_SYNTHESIZER_BE.get(), DnaSynthesizerBlockEntityRenderer::new);
                ClientRegistry.bindTileEntityRenderer(ModBlockEntities.FLUID_TANK_BE.get(), FluidTankBlockEntityRenderer::new);
                ClientRegistry.bindTileEntityRenderer(ModBlockEntities.SLIME_SQUEEZER_BE.get(), SlimeSqueezerBlockEntityRenderer::new);
                ClientRegistry.bindTileEntityRenderer(ModBlockEntities.SLIME_NEST_BE.get(), SlimeNestBlockEntityRenderer::new);
                ClientRegistry.bindTileEntityRenderer(ModBlockEntities.SLIMEBALL_COLLECTOR_BE.get(), SlimeballCollectorBlockEntityRenderer::new);

                registerAllFluidRenderLayer();
                registerAllSlimeBlockRenderLayer();

                RenderTypeLookup.setRenderLayer(ModBlocks.SLIMY_SAPLING.get(), RenderType.cutout());
                RenderTypeLookup.setRenderLayer(ModBlocks.SLIMY_LEAVES.get(), RenderType.cutout());
                RenderTypeLookup.setRenderLayer(ModBlocks.SLIMY_DOOR.get(), RenderType.cutout());
                RenderTypeLookup.setRenderLayer(ModBlocks.SLIMY_TRAPDOOR.get(), RenderType.cutout());
                RenderTypeLookup.setRenderLayer(ModBlocks.LIQUID_SOLIDING_STATION.get(), RenderType.cutout());
                RenderTypeLookup.setRenderLayer(ModBlocks.SLIME_NEST.get(), RenderType.cutout());
                RenderTypeLookup.setRenderLayer(ModBlocks.FLUID_TANK.get(), RenderType.cutout());

                RenderTypeLookup.setRenderLayer(ModBlocks.DNA_SYNTHESIZER.get(), RenderType.translucent());
                RenderTypeLookup.setRenderLayer(ModBlocks.DNA_EXTRACTOR.get(), RenderType.translucent());

                EntitySpawnPlacementRegistry.register(ModTierLists.getEntityByName(Tier.DIRT.getTierName()).get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (entityTypes, serverLevel, spawnType, pos, random) -> serverLevel.getBlockState(pos.below()).getBlock() == ModBlocks.SLIMY_GRASS_BLOCK.get());
                EntitySpawnPlacementRegistry.register(ModTierLists.getEntityByName(Tier.STONE.getTierName()).get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (entityTypes, serverLevel, spawnType, pos, random) -> serverLevel.getBlockState(pos.below()).getBlock() == ModBlocks.SLIMY_GRASS_BLOCK.get());
                EntitySpawnPlacementRegistry.register(ModTierLists.getEntityByName(Tier.IRON.getTierName()).get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (entityTypes, serverLevel, spawnType, pos, random) -> serverLevel.getBlockState(pos.below()).getBlock() == ModBlocks.SLIMY_GRASS_BLOCK.get());
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
                if (stack.hasTag() && stack.getTag().contains("slime_data")) {
                    CompoundNBT slimeDataTag = stack.getTag().getCompound("slime_data");
                    SlimeData slimeData = SlimeData.fromTag(slimeDataTag);
                    return slimeData.color();
                }
                return 0xFFFFFF;
            }, ModItems.SLIME_ITEM.get());
        }

        public static void registerAllSlimeBlockColor(ColorHandlerEvent.Block event) {
            if (ModBlocks.ENERGY_SLIME_BLOCK.get() instanceof SlimeBlock){
                SlimeBlock block = (SlimeBlock) ModBlocks.ENERGY_SLIME_BLOCK.get();
                event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) -> block.getColor(), block);
            }

            for (Tier tier : Tier.values()) {
                ModTiers modTiers = ModTierLists.getTierByName(tier);
                if(ModTierLists.getBlockByName(modTiers.name()).get() instanceof SlimeBlock){
                    SlimeBlock block = (SlimeBlock) ModTierLists.getBlockByName(modTiers.name()).get();
                    event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) -> block.getColor(), block);
                }
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                if (CustomContentRegistry.getSlimeBlockForVariant(variant.getName()).get() instanceof SlimeBlock){
                    SlimeBlock block = (SlimeBlock) CustomContentRegistry.getSlimeBlockForVariant(variant.getName()).get();
                    event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) -> block.getColor(), block);
                }
            }
        }

        public static void registerAllSlimeBlockColor(ColorHandlerEvent.Item event) {
            if (ModBlocks.ENERGY_SLIME_BLOCK.get() instanceof SlimeBlock){
                SlimeBlock block = (SlimeBlock) ModBlocks.ENERGY_SLIME_BLOCK.get();
                event.getItemColors().register((stack, pTintIndex) -> {
                    if (stack.getItem() instanceof BlockItem) {
                        BlockItem blockItem = (BlockItem) stack.getItem();
                        if (blockItem.getBlock() instanceof SlimeBlock) {
                            SlimeBlock slimeBlock = (SlimeBlock) blockItem.getBlock();
                            return slimeBlock.getColor();
                        }
                    }
                    return 0xFFFFFFFF;
                }, block.asItem());
            }

            for (Tier tier : Tier.values()) {
                ModTiers modTiers = ModTierLists.getTierByName(tier);
                if(ModTierLists.getBlockByName(modTiers.name()).get() instanceof SlimeBlock) {
                    SlimeBlock block = (SlimeBlock) ModTierLists.getBlockByName(modTiers.name()).get();
                    event.getItemColors().register((stack, pTintIndex) -> {
                        if (stack.getItem() instanceof BlockItem) {
                            BlockItem blockItem = (BlockItem) stack.getItem();
                            if (blockItem.getBlock() instanceof SlimeBlock) {
                                SlimeBlock slimeBlock = (SlimeBlock) blockItem.getBlock();
                                return slimeBlock.getColor();
                            }
                        }
                        return 0xFFFFFFFF;
                    }, block.asItem());
                }
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                if (CustomContentRegistry.getSlimeBlockForVariant(variant.getName()).get() instanceof SlimeBlock){
                    SlimeBlock block = (SlimeBlock) CustomContentRegistry.getSlimeBlockForVariant(variant.getName()).get();
                    event.getItemColors().register((stack, tintIndex) -> {
                        if (stack.getItem() instanceof BlockItem){
                            BlockItem blockItem = (BlockItem) stack.getItem();
                            if (blockItem.getBlock() instanceof SlimeBlock){
                                SlimeBlock slimeBlock = (SlimeBlock) blockItem.getBlock();
                                return slimeBlock.getColor();
                            }
                        }
                        return 0xFFFFFFFF;
                    }, block.asItem());
                }
            }
        }

        public static void registerAllSlimeballColor(ColorHandlerEvent.Item event) {
            if (ModItems.ENERGY_SLIME_BALL.get() instanceof SlimeballItem){
                SlimeballItem item = (SlimeballItem) ModItems.ENERGY_SLIME_BALL.get();
                event.getItemColors().register((stack, tintIndex) -> item.getColor(), item);
            }

            for (Tier tier : Tier.values()) {
                ModTiers modTiers = ModTierLists.getTierByName(tier);

                if (ModTierLists.getSlimeballItemByName(modTiers.name()).get() instanceof SlimeballItem){
                    SlimeballItem item = (SlimeballItem) ModTierLists.getSlimeballItemByName(modTiers.name()).get();
                    event.getItemColors().register((stack, tintIndex) -> item.getColor(), item);
                }
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                if (CustomContentRegistry.getSlimeballItemForVariant(variant.getName()).get() instanceof SlimeballItem){
                    SlimeballItem item = (SlimeballItem) CustomContentRegistry.getSlimeballItemForVariant(variant.getName()).get();
                    event.getItemColors().register((stack, tintIndex) -> item.getColor(), item);
                }
            }
        }

        public static void registerAllSlimeDnaColor(ColorHandlerEvent.Item event) {
            if (ModItems.SLIME_DNA.get() instanceof DnaItem){
                DnaItem item = (DnaItem) ModItems.SLIME_DNA.get();
                event.getItemColors().register((stack, tintIndex) -> item.getColor(), item);
            }

            for (Tier tier : Tier.values()) {
                ModTiers modTiers = ModTierLists.getTierByName(tier);

                if (ModTierLists.getDnaItemByName(modTiers.name()).get() instanceof DnaItem){
                    DnaItem item = (DnaItem) ModTierLists.getDnaItemByName(modTiers.name()).get();
                    event.getItemColors().register((stack, tintIndex) -> item.getColor(), item);
                }
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                if (CustomContentRegistry.getDnaItemForVariant(variant.getName()).get() instanceof DnaItem){
                    DnaItem item = (DnaItem) CustomContentRegistry.getDnaItemForVariant(variant.getName()).get();
                    event.getItemColors().register((stack, tintIndex) -> item.getColor(), item);
                }
            }
        }

        public static void registerAllBucketColor(ColorHandlerEvent.Item event) {
            for (Tier tier : Tier.values()) {
                ModTiers modTiers = ModTierLists.getTierByName(tier);
                if (ModTierLists.getBucketItemByName(modTiers.name()).get() instanceof BucketItem){
                    BucketItem bucketItem = (BucketItem) ModTierLists.getBucketItemByName(modTiers.name()).get();
                    event.getItemColors().register((itemStack, pTintIndex) -> pTintIndex == 1 ? bucketItem.getColor() : 0xFFFFFFFF, bucketItem);
                }
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                if (ForgeRegistries.ITEMS.getValue(new ResourceLocation(ProductiveSlimes.MODID, "molten_" + variant.getName() + "_bucket")) instanceof BucketItem){
                    BucketItem bucketItem = (BucketItem) ForgeRegistries.ITEMS.getValue(new ResourceLocation(ProductiveSlimes.MODID, "molten_" + variant.getName() + "_bucket"));
                    event.getItemColors().register((itemStack, pTintIndex) -> pTintIndex == 1 ? bucketItem.getColor() : 0xFFFFFFFF, bucketItem);
                }
            }
        }

        public static void registerAllFluidRenderLayer() {
            for (Tier tier : Tier.values()) {
                ModTiers modTiers = ModTierLists.getTierByName(tier);
                String name = modTiers.name();

                RenderTypeLookup.setRenderLayer(ModTierLists.getSourceByName(name).get(), RenderType.translucent());
                RenderTypeLookup.setRenderLayer(ModTierLists.getFlowByName(name).get(), RenderType.translucent());
                RenderTypeLookup.setRenderLayer(ModTierLists.getLiquidBlockByName(name).get(), RenderType.translucent());
            }

            FluidResources.fluidList.stream()
                    .filter(fluid -> fluid.isTranslucent)
                    .forEach(fluid -> {
                        RenderTypeLookup.setRenderLayer(fluid.FLUID.get(), RenderType.translucent());
                        RenderTypeLookup.setRenderLayer(fluid.FLUID_FLOW.get(), RenderType.translucent());
                        RenderTypeLookup.setRenderLayer(fluid.FLUID_BLOCK.get(), RenderType.translucent());
                    });
        }

        public static void registerAllSlimeBlockRenderLayer() {
            if (ModBlocks.ENERGY_SLIME_BLOCK.get() instanceof SlimeBlock){
                RenderTypeLookup.setRenderLayer(ModBlocks.ENERGY_SLIME_BLOCK.get(), RenderType.translucent());
            }

            for (Tier tier : Tier.values()) {
                ModTiers modTiers = ModTierLists.getTierByName(tier);

                if (ModTierLists.getBlockByName(modTiers.name()).get() instanceof SlimeBlock){
                    RenderTypeLookup.setRenderLayer(ModTierLists.getBlockByName(modTiers.name()).get(), RenderType.translucent());
                }
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                if (CustomContentRegistry.getSlimeBlockForVariant(variant.getName()).get() instanceof SlimeBlock){
                    RenderTypeLookup.setRenderLayer(CustomContentRegistry.getSlimeBlockForVariant(variant.getName()).get(), RenderType.translucent());
                }
            }
        }
    }
}