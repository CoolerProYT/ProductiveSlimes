package com.coolerpromc.productiveslimes;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.block.custom.SlimeBlock;
import com.coolerpromc.productiveslimes.block.entity.ModBlockEntities;
import com.coolerpromc.productiveslimes.block.entity.renderer.*;
import com.coolerpromc.productiveslimes.compat.top.GetTheOneProbe;
import com.coolerpromc.productiveslimes.config.CustomContentRegistry;
import com.coolerpromc.productiveslimes.config.fluid.FluidResources;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.entity.SlimeModel;
import com.coolerpromc.productiveslimes.entity.renderer.*;
import com.coolerpromc.productiveslimes.fluid.ModFluidTypes;
import com.coolerpromc.productiveslimes.fluid.ModFluids;
import com.coolerpromc.productiveslimes.item.ModCreativeTabs;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.item.custom.BucketItem;
import com.coolerpromc.productiveslimes.item.custom.DnaItem;
import com.coolerpromc.productiveslimes.item.custom.SlimeballItem;
import com.coolerpromc.productiveslimes.recipe.ModRecipes;
import com.coolerpromc.productiveslimes.screen.*;
import com.coolerpromc.productiveslimes.villager.ModVillagers;
import com.coolerpromc.productiveslimes.worldgen.biome.ModTerrablender;
import com.coolerpromc.productiveslimes.worldgen.biome.surface.ModSurfaceRules;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import terrablender.api.SurfaceRuleManager;

import java.lang.reflect.Field;
import java.util.function.Supplier;

@Mod(ProductiveSlimes.MODID)
public class ProductiveSlimes
{
    public static final String MODID = "productiveslimes";

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ProductiveSlimes.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ProductiveSlimes.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ProductiveSlimes.MODID);

    public ProductiveSlimes()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

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

        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModFluids.register(modEventBus);
        ModFluidTypes.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModVillagers.register(modEventBus);

        ModTerrablender.registerBiomes();

        MinecraftForge.EVENT_BUS.register(this);
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
    public void onPlayer(PlayerEvent.PlayerLoggedInEvent event) {
        event.getEntity().getServer().getCommands().performCommand(event.getEntity().getServer().getCommands().getDispatcher().parse("reload", event.getEntity().getServer().createCommandSourceStack()), "reload");
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
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            MenuScreens.register(ModMenuTypes.MELTING_STATION_MENU.get(), MeltingStationScreen::new);
            MenuScreens.register(ModMenuTypes.SOLIDING_STATION_MENU.get(), SolidingStationScreen::new);
            MenuScreens.register(ModMenuTypes.GUIDEBOOK_MENU.get(), GuidebookScreen::new);
            MenuScreens.register(ModMenuTypes.ENERGY_GENERATOR_MENU.get(), EnergyGeneratorScreen::new);
            MenuScreens.register(ModMenuTypes.DNA_EXTRACTOR_MENU.get(), DnaExtractorScreen::new);
            MenuScreens.register(ModMenuTypes.DNA_SYNTHESIZER_MENU.get(), DnaSynthesizerScreen::new);
            MenuScreens.register(ModMenuTypes.SLIME_SQUEEZER_MENU.get(), SlimeSqueezerScreen::new);

            EntityRenderers.register(ModEntities.DIRT_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF866043));
            EntityRenderers.register(ModEntities.STONE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF4a4545));
            EntityRenderers.register(ModEntities.IRON_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF898c8a));
            EntityRenderers.register(ModEntities.COPPER_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF6a3e15));
            EntityRenderers.register(ModEntities.GOLD_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFFa5953f));
            EntityRenderers.register(ModEntities.DIAMOND_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF178f9c));
            EntityRenderers.register(ModEntities.NETHERITE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF4c2b2b));
            EntityRenderers.register(ModEntities.LAPIS_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF1c41ba));
            EntityRenderers.register(ModEntities.REDSTONE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFFa10505));
            EntityRenderers.register(ModEntities.OAK_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFFa69d6f));
            EntityRenderers.register(ModEntities.SAND_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFFf7f7c6));
            EntityRenderers.register(ModEntities.ANDESITE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF9d9e9a));
            EntityRenderers.register(ModEntities.SNOW_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFFf2fcfc));
            EntityRenderers.register(ModEntities.ICE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF89b1fc));
            EntityRenderers.register(ModEntities.MUD_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF363339));
            EntityRenderers.register(ModEntities.CLAY_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF9ca2ac));
            EntityRenderers.register(ModEntities.RED_SAND_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFFbb6520));
            EntityRenderers.register(ModEntities.MOSS_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF4a6029));
            EntityRenderers.register(ModEntities.DEEPSLATE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF3c3c42));
            EntityRenderers.register(ModEntities.GRANITE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF835949));
            EntityRenderers.register(ModEntities.DIORITE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFFadacad));
            EntityRenderers.register(ModEntities.CALCITE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFFe9e9e3));
            EntityRenderers.register(ModEntities.TUFF_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF55564c));
            EntityRenderers.register(ModEntities.DRIPSTONE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF806155));
            EntityRenderers.register(ModEntities.NETHERRACK_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF763535));
            EntityRenderers.register(ModEntities.PRISMARINE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF529584));
            EntityRenderers.register(ModEntities.MAGMA_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF561f1f));
            EntityRenderers.register(ModEntities.OBSIDIAN_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF030106));
            EntityRenderers.register(ModEntities.SOUL_SAND_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF413127));
            EntityRenderers.register(ModEntities.SOUL_SOIL_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF392b23));
            EntityRenderers.register(ModEntities.BLACKSTONE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF201819));
            EntityRenderers.register(ModEntities.BASALT_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF565456));
            EntityRenderers.register(ModEntities.QUARTZ_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFFe4ddd3));
            EntityRenderers.register(ModEntities.GLOWSTONE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF784e27));
            EntityRenderers.register(ModEntities.ENDSTONE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFFcece8e));
            EntityRenderers.register(ModEntities.AMETHYST_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF6b4da5));
            EntityRenderers.register(ModEntities.BROWN_MUSHROOM_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF967251));
            EntityRenderers.register(ModEntities.RED_MUSHROOM_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFFc02624));
            EntityRenderers.register(ModEntities.CACTUS_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF476d21));
            EntityRenderers.register(ModEntities.COAL_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF3b3d3b));
            EntityRenderers.register(ModEntities.GRAVEL_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF4a444b));
            EntityRenderers.register(ModEntities.ENERGY_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFFffff70));
            EntityRenderers.register(ModEntities.OAK_LEAVES_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xFF48b518));

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
        public static void onRegisterColorHandlers(RegisterColorHandlersEvent.Block event) {
            registerAllSlimeBlockColor(event);
        }

        @SubscribeEvent
        public static void onRegisterColorHandlers(RegisterColorHandlersEvent.Item event) {
            registerAllSlimeballColor(event);
            registerAllSlimeDnaColor(event);
            registerAllBucketColor(event);
            registerAllSlimeBlockColor(event);
        }

        public static void registerAllSlimeBlockColor(RegisterColorHandlersEvent.Block event) {
            Field[] fields = ModBlocks.class.getFields();

            for (Field field : fields) {
                try {
                    Object value = field.get(null);

                    if (value instanceof Supplier<?> supplier) {
                        Block block = (Block) supplier.get();
                        if (block instanceof SlimeBlock) {
                            event.register((pState, pLevel, pPos, pTintIndex) -> {
                                if (pState.getBlock() instanceof SlimeBlock slimeBlock) {
                                    return slimeBlock.getColor();
                                }
                                return 0xFFFFFFFF; // Default no color
                            }, block);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                if (CustomContentRegistry.getSlimeBlockForVariant(variant.getName()).get() instanceof SlimeBlock block){
                    event.register((pState, pLevel, pPos, pTintIndex) -> block.getColor(), block);
                }
            }
        }

        public static void registerAllSlimeBlockColor(RegisterColorHandlersEvent.Item event) {
            Field[] fields = ModBlocks.class.getFields();

            for (Field field : fields) {
                try {
                    Object value = field.get(null);

                    if (value instanceof Supplier<?> supplier) {
                        Block block = (Block) supplier.get();
                        if (block instanceof SlimeBlock) {
                            event.register((itemStack, pTintIndex) -> {
                                if (itemStack.getItem() instanceof BlockItem blockItem) {
                                    if (blockItem.getBlock() instanceof SlimeBlock slimeBlock) {
                                        return slimeBlock.getColor();
                                    }
                                }
                                return 0xFFFFFFFF; // Default no color
                            }, block.asItem());
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
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

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                if (CustomContentRegistry.getSlimeballItemForVariant(variant.getName()).get() instanceof SlimeballItem item){
                    event.register((stack, tintIndex) -> item.getColor(), item);
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

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                if (CustomContentRegistry.getDnaItemForVariant(variant.getName()).get() instanceof DnaItem item){
                    event.register((stack, tintIndex) -> item.getColor(), item);
                }
            }
        }

        public static void registerAllBucketColor(RegisterColorHandlersEvent.Item event) {
            Field[] fields = ModFluids.class.getFields();

            for (Field field : fields) {
                try {
                    Object value = field.get(null);

                    if (value instanceof Supplier<?> supplier) {
                        if (supplier.get() instanceof BucketItem) {
                            Item item = (Item) supplier.get();
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
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                if (BuiltInRegistries.ITEM.get(new ResourceLocation(ProductiveSlimes.MODID, "molten_" + variant.getName() + "_bucket")) instanceof BucketItem bucketItem){
                    event.register((itemStack, pTintIndex) -> pTintIndex == 1 ? bucketItem.getColor() : 0xFFFFFFFF, bucketItem);
                }
            }
        }

        public static void registerAllFluidRenderLayer() {
            Field[] fields = ModFluids.class.getFields();

            for (Field field : fields) {
                try {
                    Object value = field.get(null);

                    if (value instanceof Supplier<?> supplier) {
                        if (supplier.get() instanceof FlowingFluid fluid) {
                            ItemBlockRenderTypes.setRenderLayer(fluid, RenderType.translucent());
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
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
            Field[] fields = ModBlocks.class.getFields();

            for (Field field : fields) {
                try {
                    Object value = field.get(null);

                    if (value instanceof Supplier<?> supplier) {
                        if (supplier.get() instanceof SlimeBlock slimeBlock) {
                            ItemBlockRenderTypes.setRenderLayer(slimeBlock, RenderType.translucent());
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
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