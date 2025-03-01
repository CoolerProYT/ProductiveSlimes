package com.coolerpromc.productiveslimes;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.block.custom.SlimeBlock;
import com.coolerpromc.productiveslimes.block.entity.ModBlockEntities;
import com.coolerpromc.productiveslimes.block.entity.renderer.*;
import com.coolerpromc.productiveslimes.command.ModCommands;
import com.coolerpromc.productiveslimes.config.CustomContentRegistry;
import com.coolerpromc.productiveslimes.config.fluid.FluidResources;
import com.coolerpromc.productiveslimes.config.fluid.ModBaseFluidType;
import com.coolerpromc.productiveslimes.datacomponent.ModDataComponents;
import com.coolerpromc.productiveslimes.datagen.model.special.FluidTankSpecialRenderer;
import com.coolerpromc.productiveslimes.datagen.model.tint.SlimeItemTint;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.entity.SlimeModel;
import com.coolerpromc.productiveslimes.entity.renderer.*;
import com.coolerpromc.productiveslimes.fluid.ModFluidResources;
import com.coolerpromc.productiveslimes.fluid.ModFluids;
import com.coolerpromc.productiveslimes.networking.cable.ModCableNetworkManager;
import com.coolerpromc.productiveslimes.networking.cable.ModCableNetworkStateManager;
import com.coolerpromc.productiveslimes.networking.pipe.ModPipeNetworkManager;
import com.coolerpromc.productiveslimes.networking.pipe.ModPipeNetworkStateManager;
import com.coolerpromc.productiveslimes.networking.recipe.RecipeSyncPayload;
import com.coolerpromc.productiveslimes.item.ModCreativeTabs;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.recipe.ModRecipes;
import com.coolerpromc.productiveslimes.screen.ModMenuTypes;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.ModTier;
import com.coolerpromc.productiveslimes.tier.Tier;
import com.coolerpromc.productiveslimes.villager.ModVillagers;
import com.coolerpromc.productiveslimes.worldgen.biome.ModTerrablender;
import com.coolerpromc.productiveslimes.worldgen.biome.surface.ModSurfaceRules;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
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
import net.neoforged.neoforge.client.event.RegisterSpecialModelRendererEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
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

        if (!Boolean.getBoolean("neoforge.datagen")){
            CustomContentRegistry.initialize(ITEMS, BLOCKS, ENTITY_TYPES);

            ITEMS.register(modEventBus);
            BLOCKS.register(modEventBus);
            ENTITY_TYPES.register(modEventBus);
        }

        FluidResources.register(modEventBus);

        ModTiers.init();

        ModItems.registerTierItems();
        ModItems.register(modEventBus);

        ModBlocks.registerTierBlocks();
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
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.CONFIG_SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MODID, ModSurfaceRules.makeRules());
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
//        InterModComms.sendTo("theoneprobe", "getTheOneProbe", GetTheOneProbe::new);
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        ModCommands.register(event.getDispatcher());
    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
    }

    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event) {
        ServerLevel overworld = event.getServer().overworld();
        ModCableNetworkStateManager.forceSave(overworld);
        ModPipeNetworkStateManager.forceSave(overworld);
    }

    @SubscribeEvent
    public void onServerTick(ServerTickEvent.Post event) {
        for (ServerLevel level : event.getServer().getAllLevels()){
            ModCableNetworkManager.tickAllNetworks(level);
            ModPipeNetworkManager.tickAllNetworks(level);
        }
    }

    @SubscribeEvent
    public void onLevel(LevelEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel serverWorld) {
            ModCableNetworkStateManager.loadAllNetworksToManager(serverWorld);
            ModPipeNetworkStateManager.loadAllNetworksToManager(serverWorld);
        }
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
        RecipeManager recipeManager = player.server.getRecipeManager();

        player.connection.send(new RecipeSyncPayload(recipeManager.getRecipes().stream().toList()));
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
                    ModTier tiers = ModTiers.getTierByName(name);
                    EntityRenderers.register(ModTiers.getEntityByName(tiers.name()).get(), pContext -> new BaseSlimeRenderer(pContext, tiers.color()));
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
        }

        @SubscribeEvent
        public static void entitySpawnRestriction(RegisterSpawnPlacementsEvent event) {
            event.register(ModTiers.getEntityByName("dirt").get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, serverLevel, spawnType, pos, random) -> serverLevel.getBlockState(pos.below()).getBlock() == ModBlocks.SLIMY_GRASS_BLOCK.get(), RegisterSpawnPlacementsEvent.Operation.REPLACE);
        }

        @SubscribeEvent
        public static void onClientExtensions(RegisterClientExtensionsEvent event) {
            registerAllFluidType(event);
        }

        @SubscribeEvent
        public static void onRegisterColorHandlers(RegisterColorHandlersEvent.ItemTintSources event) {
            event.register(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "slime_item_tint"), SlimeItemTint.MAP_CODEC);
        }

        @SubscribeEvent
        public static void onRegisterSpecialModelRenderer(RegisterSpecialModelRendererEvent event) {
            event.register(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "fluid_tank"), FluidTankSpecialRenderer.Unbaked.MAP_CODEC);
        }

        @SubscribeEvent
        public static void onRegisterColorHandlers(RegisterColorHandlersEvent.Block event) {
            registerAllSlimeBlockColor(event);
        }

        public static void registerAllFluidType(RegisterClientExtensionsEvent event){
            for (Tier tier : Tier.values()){
                ModTier tiers = ModTiers.getTierByName(tier);
                if (ModTiers.getFluidTypeByName(tiers.name()).get() instanceof com.coolerpromc.productiveslimes.fluid.ModBaseFluidType modBaseFluidType)
                    event.registerFluidType(modBaseFluidType.getClientExtensions(), modBaseFluidType);
            }

            FluidResources.fluidList.forEach(fluid -> {
                if (fluid.TYPE.get() instanceof ModBaseFluidType modBaseFluidType)
                    event.registerFluidType(modBaseFluidType.getClientExtensions(), modBaseFluidType);
            });
        }

        public static void registerAllSlimeBlockColor(RegisterColorHandlersEvent.Block event) {
            if (ModBlocks.ENERGY_SLIME_BLOCK.get() instanceof SlimeBlock block){
                event.register((pState, pLevel, pPos, pTintIndex) -> block.getColor(), block);
            }

            for (Tier tier : Tier.values()){
                ModTier tiers = ModTiers.getTierByName(tier);
                if (ModTiers.getBlockByName(tiers.name()).get() instanceof SlimeBlock block){
                    event.register((pState, pLevel, pPos, pTintIndex) -> block.getColor(), block);
                }
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                if (CustomContentRegistry.getSlimeBlockForVariant(variant.getName()).get() instanceof SlimeBlock block){
                    event.register((pState, pLevel, pPos, pTintIndex) -> block.getColor(), block);
                }
            }
        }

        public static void registerAllFluidRenderLayer() {
            for (Tier tier : Tier.values()){
                ModTier tiers = ModTiers.getTierByName(tier);
                ItemBlockRenderTypes.setRenderLayer(ModTiers.getSourceByName(tiers.name()).get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModTiers.getFlowByName(tiers.name()).get(), RenderType.translucent());
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
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ENERGY_SLIME_BLOCK.get(), RenderType.translucent());

            for (Tier tier : Tier.values()){
                ModTier tiers = ModTiers.getTierByName(tier);
                ItemBlockRenderTypes.setRenderLayer(ModTiers.getBlockByName(tiers.name()).get(), RenderType.translucent());
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                if (CustomContentRegistry.getSlimeBlockForVariant(variant.getName()).get() instanceof SlimeBlock block){
                    ItemBlockRenderTypes.setRenderLayer(block, RenderType.TRANSLUCENT);
                }
            }
        }
    }
}