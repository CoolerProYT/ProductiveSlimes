package com.coolerpromc.productiveslimes;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.block.custom.SlimeBlock;
import com.coolerpromc.productiveslimes.block.entity.ModBlockEntities;
import com.coolerpromc.productiveslimes.block.entity.renderer.DnaExtractorBlockEntityRenderer;
import com.coolerpromc.productiveslimes.block.entity.renderer.DnaSynthesizerBlockEntityRenderer;
import com.coolerpromc.productiveslimes.block.entity.renderer.FluidTankBlockEntityRenderer;
import com.coolerpromc.productiveslimes.block.entity.renderer.SolidingStationBlockEntityRenderer;
import com.coolerpromc.productiveslimes.config.CustomContentRegistry;
import com.coolerpromc.productiveslimes.config.fluid.FluidResources;
import com.coolerpromc.productiveslimes.config.fluid.ModBaseFluidType;
import com.coolerpromc.productiveslimes.datacomponent.ModDataComponents;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.entity.SlimeModel;
import com.coolerpromc.productiveslimes.entity.renderer.*;
import com.coolerpromc.productiveslimes.fluid.ModFluidResources;
import com.coolerpromc.productiveslimes.fluid.ModFluids;
import com.coolerpromc.productiveslimes.item.ModCreativeTabs;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.recipe.ModRecipes;
import com.coolerpromc.productiveslimes.screen.ModMenuTypes;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import com.coolerpromc.productiveslimes.util.*;
import com.coolerpromc.productiveslimes.util.property.*;
import com.coolerpromc.productiveslimes.villager.ModVillagers;
import com.coolerpromc.productiveslimes.worldgen.biome.ModTerrablender;
import com.coolerpromc.productiveslimes.worldgen.biome.surface.ModSurfaceRules;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementTypes;
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
import net.neoforged.neoforge.client.event.RegisterConditionalItemModelPropertyEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.NeoForge;
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
//        InterModComms.sendTo("theoneprobe", "getTheOneProbe", GetTheOneProbe::new);
    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        event.getEntity().getServer().getCommands().performCommand(event.getEntity().getServer().getCommands().getDispatcher().parse("reload", event.getEntity().getServer().createCommandSourceStack()), "reload");
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
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ModEntities.ENERGY_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0ffff70));

            for (Tier name : Tier.values()){
                ModTiers tiers = ModTierLists.getTierByName(name);
                EntityRenderers.register(ModTierLists.getEntityByName(tiers.name()).get(), pContext -> new BaseSlimeRenderer(pContext, tiers.color()));
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                EntityRenderers.register(CustomContentRegistry.getSlimeForVariant(variant.getName()).get(), pContext -> new BaseSlimeRenderer(pContext, variant.getColor()));
            }

            event.enqueueWork(() -> {
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
        public static void entitySpawnRestriction(RegisterSpawnPlacementsEvent event) {
            event.register(ModTierLists.getEntityByName("dirt").get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, serverLevel, spawnType, pos, random) -> serverLevel.getBlockState(pos.below()).getBlock() == ModBlocks.SLIMY_GRASS_BLOCK.get(), RegisterSpawnPlacementsEvent.Operation.REPLACE);
        }

        @SubscribeEvent
        public static void onClientExtensions(RegisterClientExtensionsEvent event) {
            registerAllFluidType(event);
        }

        @SubscribeEvent
        public static void onRegisterColorHandlers(RegisterColorHandlersEvent.ItemTintSources event) {
            event.register(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "fluid_tank_tint"), FluidTankTint.MAP_CODEC);
        }

        @SubscribeEvent
        public static void onRegisterConditionalItemModelProperty(RegisterConditionalItemModelPropertyEvent event) {
            event.register(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "fluid_tank_empty"), FluidTankProperty.MAP_CODEC);
            event.register(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "fluid_tank_less_than_3k"), FluidTankProperty3k.MAP_CODEC);
            event.register(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "fluid_tank_less_than_6k"), FluidTankProperty6k.MAP_CODEC);
            event.register(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "fluid_tank_less_than_9k"), FluidTankProperty9k.MAP_CODEC);
            event.register(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "fluid_tank_less_than_12k"), FluidTankProperty12k.MAP_CODEC);
            event.register(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "fluid_tank_less_than_15k"), FluidTankProperty15k.MAP_CODEC);
            event.register(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "fluid_tank_less_than_18k"), FluidTankProperty18k.MAP_CODEC);
            event.register(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "fluid_tank_less_than_21k"), FluidTankProperty21k.MAP_CODEC);
            event.register(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "fluid_tank_less_than_24k"), FluidTankProperty24k.MAP_CODEC);
            event.register(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "fluid_tank_less_than_27k"), FluidTankProperty27k.MAP_CODEC);
            event.register(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "fluid_tank_less_than_30k"), FluidTankProperty30k.MAP_CODEC);
            event.register(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "fluid_tank_less_than_33k"), FluidTankProperty33k.MAP_CODEC);
            event.register(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "fluid_tank_less_than_36k"), FluidTankProperty36k.MAP_CODEC);
            event.register(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "fluid_tank_less_than_40k"), FluidTankProperty40k.MAP_CODEC);
            event.register(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "fluid_tank_less_than_45k"), FluidTankProperty45k.MAP_CODEC);
        }

        @SubscribeEvent
        public static void onRegisterColorHandlers(RegisterColorHandlersEvent.Block event) {
            registerAllSlimeBlockColor(event);
        }

        public static void registerAllFluidType(RegisterClientExtensionsEvent event){
            for (Tier tier : Tier.values()){
                ModTiers tiers = ModTierLists.getTierByName(tier);
                if (ModTierLists.getFluidTypeByName(tiers.name()).get() instanceof com.coolerpromc.productiveslimes.fluid.ModBaseFluidType modBaseFluidType)
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
                ModTiers tiers = ModTierLists.getTierByName(tier);
                if (ModTierLists.getBlockByName(tiers.name()).get() instanceof SlimeBlock block){
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
                ModTiers tiers = ModTierLists.getTierByName(tier);
                ItemBlockRenderTypes.setRenderLayer(ModTierLists.getSourceByName(tiers.name()).get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModTierLists.getFlowByName(tiers.name()).get(), RenderType.translucent());
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
                ModTiers tiers = ModTierLists.getTierByName(tier);
                ItemBlockRenderTypes.setRenderLayer(ModTierLists.getBlockByName(tiers.name()).get(), RenderType.translucent());
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                if (CustomContentRegistry.getSlimeBlockForVariant(variant.getName()).get() instanceof SlimeBlock block){
                    ItemBlockRenderTypes.setRenderLayer(block, RenderType.TRANSLUCENT);
                }
            }
        }
    }
}