package com.coolerpromc.productiveslimes;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.block.custom.SlimeBlock;
import com.coolerpromc.productiveslimes.block.entity.ModBlockEntities;
import com.coolerpromc.productiveslimes.block.entity.renderer.*;
import com.coolerpromc.productiveslimes.command.ModCommands;
import com.coolerpromc.productiveslimes.compat.top.GetTheOneProbe;
import com.coolerpromc.productiveslimes.config.CustomContentRegistry;
import com.coolerpromc.productiveslimes.config.fluid.FluidResources;
import com.coolerpromc.productiveslimes.datacomponent.ModDataComponents;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.entity.SlimeModel;
import com.coolerpromc.productiveslimes.entity.renderer.*;
import com.coolerpromc.productiveslimes.fluid.ModBaseFluidType;
import com.coolerpromc.productiveslimes.fluid.ModFluidResources;
import com.coolerpromc.productiveslimes.fluid.ModFluids;
import com.coolerpromc.productiveslimes.handler.SlimeData;
import com.coolerpromc.productiveslimes.item.ModCreativeTabs;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.item.custom.BucketItem;
import com.coolerpromc.productiveslimes.item.custom.DnaItem;
import com.coolerpromc.productiveslimes.item.custom.SlimeballItem;
import com.coolerpromc.productiveslimes.recipe.ModRecipes;
import com.coolerpromc.productiveslimes.screen.ModMenuTypes;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import com.coolerpromc.productiveslimes.util.ModClientItemExtensions;
import com.coolerpromc.productiveslimes.villager.ModVillagers;
import com.coolerpromc.productiveslimes.worldgen.biome.ModTerrablender;
import com.coolerpromc.productiveslimes.worldgen.biome.surface.ModSurfaceRules;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;
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
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import terrablender.api.SurfaceRuleManager;

@Mod(ProductiveSlimes.MODID)
public class ProductiveSlimes
{
    public static final String MODID = "productiveslimes";

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ProductiveSlimes.MODID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ProductiveSlimes.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, ProductiveSlimes.MODID);

    public ProductiveSlimes(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::commonSetup);
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

        ModCreativeTabs.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModDataComponents.register(modEventBus);
        ModVillagers.register(modEventBus);

        ModTerrablender.registerBiomes();

        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MODID, ModSurfaceRules.makeRules());
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
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
        event.getEntity().getServer().getCommands().performCommand(event.getEntity().createCommandSourceStack().dispatcher().parse("reload", event.getEntity().createCommandSourceStack()), "reload");
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
                EntityRenderers.register(ModEntities.ENERGY_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0ffff70));

                for (Tier name : Tier.values()){
                    ModTiers tiers = ModTierLists.getTierByName(name);
                    EntityRenderers.register(ModTierLists.getEntityByName(tiers.name()).get(), pContext -> new BaseSlimeRenderer(pContext, tiers.color()));
                }

                for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                    EntityRenderers.register(CustomContentRegistry.getSlimeForVariant(variant.getName()).get(), pContext -> new BaseSlimeRenderer(pContext, variant.getColor()));
                }


                registerAllFluidRenderLayer();
                registerAllSlimeBlockRenderLayer();

                registerBlockRenderLayer(
                        ModBlocks.LIQUID_SOLIDING_STATION.get(),
                        ModBlocks.FLUID_TANK.get()
                );

                ItemBlockRenderTypes.setRenderLayer(ModBlocks.CABLE.get(), renderType -> true);
            });

            CustomContentRegistry.handleResourcePack();
        }

        @SubscribeEvent
        public static void onModel(ModelEvent.RegisterAdditional event) {
            ModelResourceLocation slimeballModelLocation = new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "item/template_slimeball"), "standalone");
            ModelResourceLocation slimeBlockItemModelLocation = new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "item/template_slime_block"), "standalone");
            ModelResourceLocation slimeBlockModelLocation = new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "block/template_slime_block"), "standalone");
            ModelResourceLocation dnaItemModelLocation = new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "item/template_slime_dna"), "standalone");
            ModelResourceLocation spawnEggItemModelLocation = new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "item/template_slime_spawn_egg"), "standalone");
            ModelResourceLocation moltenBucketLocation = new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "item/template_bucket"), "standalone");

            event.register(slimeballModelLocation);
            event.register(slimeBlockItemModelLocation);
            event.register(slimeBlockModelLocation);
            event.register(dnaItemModelLocation);
            event.register(spawnEggItemModelLocation);
            event.register(moltenBucketLocation);

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                ItemModelShaper itemModelShaper = Minecraft.getInstance().getItemRenderer().getItemModelShaper();

                itemModelShaper.register(CustomContentRegistry.getSlimeballItemForVariant(variant.getName()).get(), slimeballModelLocation);
                itemModelShaper.register(CustomContentRegistry.getSlimeBlockForVariant(variant.getName()).get().asItem(), slimeBlockItemModelLocation);
                itemModelShaper.register(CustomContentRegistry.getDnaItemForVariant(variant.getName()).get(), dnaItemModelLocation);
                itemModelShaper.register(CustomContentRegistry.getSpawnEggItemForVariant(variant.getName()).get(), spawnEggItemModelLocation);
                itemModelShaper.register(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "molten_" + variant.getName() + "_bucket")), moltenBucketLocation);
            }
        }

        @SubscribeEvent
        public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
            event.registerItem(
                    new ModClientItemExtensions(),
                    ModBlocks.FLUID_TANK.get().asItem()
            );
        }

        @SubscribeEvent
        public static void entitySpawnRestriction(RegisterSpawnPlacementsEvent event) {
            event.register(ModTierLists.getEntityByName(Tier.DIRT.getTierName()).get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, serverLevel, spawnType, pos, random) -> serverLevel.getBlockState(pos.below()).getBlock() == ModBlocks.SLIMY_GRASS_BLOCK.get(), RegisterSpawnPlacementsEvent.Operation.REPLACE);
        }

        @SubscribeEvent
        public static void onClientExtensions(RegisterClientExtensionsEvent event) {
            registerAllFluidType(event);
        }

        @SubscribeEvent
        public static void onRegisterColorHandlers(RegisterColorHandlersEvent.Block event) {
            registerAllSlimeBlockColor(event);
        }

        @SubscribeEvent
        public static void onRegisterColorHandlers(RegisterColorHandlersEvent.Item event) {
            registerAllSlimeballColor(event);
            registerAllSlimeDnaColor(event);
            registerAllBucketColor(event);
            registerAllSlimeBlockColor(event);

            event.register((stack, tintIndex) -> {
                if (!stack.has(ModDataComponents.SLIME_DATA.get()))
                    return 0xFFFFFFFF;

                SlimeData slimeData = stack.get(ModDataComponents.SLIME_DATA.get());
                return slimeData != null ? slimeData.color() : 0xFFFFFFFF;
            }, ModItems.SLIME_ITEM.get());
        }

        public static void registerAllFluidType(RegisterClientExtensionsEvent event){
            for (Tier tier : Tier.values()){
                ModTiers modTiers = ModTierLists.getTierByName(tier);

                if (ModTierLists.getFluidTypeByName(modTiers.name()).get() instanceof ModBaseFluidType modBaseFluidType)
                    event.registerFluidType(modBaseFluidType.getClientExtensions(), modBaseFluidType);
            }

            FluidResources.fluidList.forEach(fluid -> {
                if (fluid.TYPE.get() instanceof com.coolerpromc.productiveslimes.config.fluid.ModBaseFluidType modBaseFluidType)
                    event.registerFluidType(modBaseFluidType.getClientExtensions(), modBaseFluidType);
            });
        }

        public static void registerAllSlimeBlockColor(RegisterColorHandlersEvent.Block event) {
            if (ModBlocks.ENERGY_SLIME_BLOCK.get() instanceof SlimeBlock block){
                event.register((pState, pLevel, pPos, pTintIndex) -> block.getColor(), block);
            }

            for (Tier tier : Tier.values()) {
                ModTiers modTiers = ModTierLists.getTierByName(tier);

                if(ModTierLists.getBlockByName(modTiers.name()).get() instanceof SlimeBlock block)
                    event.register((pState, pLevel, pPos, pTintIndex) -> block.getColor(), block);
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                if (CustomContentRegistry.getSlimeBlockForVariant(variant.getName()).get() instanceof SlimeBlock block){
                    event.register((pState, pLevel, pPos, pTintIndex) -> block.getColor(), block);
                }
            }
        }

        public static void registerAllSlimeBlockColor(RegisterColorHandlersEvent.Item event) {
            if (ModBlocks.ENERGY_SLIME_BLOCK.get() instanceof SlimeBlock block){
                event.register((stack, pTintIndex) -> {
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
                    event.register((stack, pTintIndex) -> {
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
                    event.register((stack, tintIndex) -> {
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

        public static void registerAllSlimeballColor(RegisterColorHandlersEvent.Item event) {
            if (ModItems.ENERGY_SLIME_BALL.get() instanceof SlimeballItem item){
                event.register((stack, tintIndex) -> item.getColor(), item);
            }

            for (Tier tier : Tier.values()) {
                ModTiers modTiers = ModTierLists.getTierByName(tier);

                if (ModTierLists.getSlimeballItemByName(modTiers.name()).get() instanceof SlimeballItem item){
                    event.register((stack, tintIndex) -> item.getColor(), item);
                }
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                if (CustomContentRegistry.getSlimeballItemForVariant(variant.getName()).get() instanceof SlimeballItem item){
                    event.register((stack, tintIndex) -> item.getColor(), item);
                }
            }
        }

        public static void registerAllSlimeDnaColor(RegisterColorHandlersEvent.Item event) {
            if (ModItems.SLIME_DNA.get() instanceof DnaItem item){
                event.register((stack, tintIndex) -> item.getColor(), item);
            }

            for (Tier tier : Tier.values()) {
                ModTiers modTiers = ModTierLists.getTierByName(tier);

                if (ModTierLists.getDnaItemByName(modTiers.name()).get() instanceof DnaItem item){
                    event.register((stack, tintIndex) -> item.getColor(), item);
                }
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                if (CustomContentRegistry.getDnaItemForVariant(variant.getName()).get() instanceof DnaItem item){
                    event.register((stack, tintIndex) -> item.getColor(), item);
                }
            }
        }

        public static void registerAllBucketColor(RegisterColorHandlersEvent.Item event) {
            for (Tier tier : Tier.values()) {
                ModTiers modTiers = ModTierLists.getTierByName(tier);

                if (ModTierLists.getBucketItemByName(modTiers.name()).get() instanceof BucketItem bucketItem){
                    event.register((itemStack, pTintIndex) -> pTintIndex == 1 ? bucketItem.getColor() : 0xFFFFFFFF, bucketItem);
                }
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                if (BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "molten_" + variant.getName() + "_bucket")) instanceof BucketItem bucketItem){
                    event.register((itemStack, pTintIndex) -> pTintIndex == 1 ? bucketItem.getColor() : 0xFFFFFFFF, bucketItem);
                }
            }
        }

        public static void registerAllFluidRenderLayer() {
            for (Tier tier : Tier.values()) {
                ModTiers modTiers = ModTierLists.getTierByName(tier);
                String name = modTiers.name();

                ItemBlockRenderTypes.setRenderLayer(ModTierLists.getSourceByName(name).get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModTierLists.getFlowByName(name).get(), RenderType.translucent());
            }

            FluidResources.fluidList.stream()
                    .filter(fluid -> fluid.isTranslucent)
                    .forEach(fluid -> {
                        ItemBlockRenderTypes.setRenderLayer(fluid.FLUID.get(), RenderType.translucent());
                        ItemBlockRenderTypes.setRenderLayer(fluid.FLUID_FLOW.get(), RenderType.translucent());
                    });
        }

        private static void registerBlockRenderLayer(Block... blocks) {
            for (Block b : blocks) {
                ItemBlockRenderTypes.setRenderLayer(b, RenderType.cutout());
            }
        }

        public static void registerAllSlimeBlockRenderLayer() {
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ENERGY_SLIME_BLOCK.get(), RenderType.TRANSLUCENT);
            for (Tier tier : Tier.values()) {
                ModTiers modTiers = ModTierLists.getTierByName(tier);

                if (ModTierLists.getBlockByName(modTiers.name()).get() instanceof SlimeBlock block){
                    ItemBlockRenderTypes.setRenderLayer(block, RenderType.TRANSLUCENT);
                }
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                if (CustomContentRegistry.getSlimeBlockForVariant(variant.getName()).get() instanceof SlimeBlock block){
                    ItemBlockRenderTypes.setRenderLayer(block, RenderType.TRANSLUCENT);
                }
            }
        }
    }
}