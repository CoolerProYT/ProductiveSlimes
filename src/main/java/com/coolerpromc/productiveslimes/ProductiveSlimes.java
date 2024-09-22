package com.coolerpromc.productiveslimes;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.block.custom.SlimeBlock;
import com.coolerpromc.productiveslimes.block.entity.ModBlockEntities;
import com.coolerpromc.productiveslimes.block.entity.renderer.DnaExtractorBlockEntityRenderer;
import com.coolerpromc.productiveslimes.block.entity.renderer.SolidingStationBlockEntityRenderer;
import com.coolerpromc.productiveslimes.compat.top.GetTheOneProbe;
import com.coolerpromc.productiveslimes.datacomponent.ModDataComponents;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.entity.SlimeModel;
import com.coolerpromc.productiveslimes.entity.renderer.*;
import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
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
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
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
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.lang.reflect.Field;
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
            event.registerBlockEntityRenderer(ModBlockEntities.SOLIDING_STATION_BE.get(), SolidingStationBlockEntityRenderer::new);
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ModEntities.DIRT_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0866043));
            EntityRenderers.register(ModEntities.STONE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF04a4545));
            EntityRenderers.register(ModEntities.IRON_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0898c8a));
            EntityRenderers.register(ModEntities.COPPER_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF06a3e15));
            EntityRenderers.register(ModEntities.GOLD_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0a5953f));
            EntityRenderers.register(ModEntities.DIAMOND_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0178f9c));
            EntityRenderers.register(ModEntities.NETHERITE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF04c2b2b));
            EntityRenderers.register(ModEntities.LAPIS_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF01c41ba));
            EntityRenderers.register(ModEntities.REDSTONE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0a10505));
            EntityRenderers.register(ModEntities.OAK_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0a69d6f));
            EntityRenderers.register(ModEntities.SAND_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0f7f7c6));
            EntityRenderers.register(ModEntities.ANDESITE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF09d9e9a));
            EntityRenderers.register(ModEntities.SNOW_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0f2fcfc));
            EntityRenderers.register(ModEntities.ICE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF089b1fc));
            EntityRenderers.register(ModEntities.MUD_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0363339));
            EntityRenderers.register(ModEntities.CLAY_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF09ca2ac));
            EntityRenderers.register(ModEntities.RED_SAND_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0bb6520));
            EntityRenderers.register(ModEntities.MOSS_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF04a6029));
            EntityRenderers.register(ModEntities.DEEPSLATE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF03c3c42));
            EntityRenderers.register(ModEntities.GRANITE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0835949));
            EntityRenderers.register(ModEntities.DIORITE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0adacad));
            EntityRenderers.register(ModEntities.CALCITE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0e9e9e3));
            EntityRenderers.register(ModEntities.TUFF_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF055564c));
            EntityRenderers.register(ModEntities.DRIPSTONE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0806155));
            EntityRenderers.register(ModEntities.NETHERRACK_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0763535));
            EntityRenderers.register(ModEntities.PRISMARINE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0529584));
            EntityRenderers.register(ModEntities.MAGMA_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0561f1f));
            EntityRenderers.register(ModEntities.OBSIDIAN_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0030106));
            EntityRenderers.register(ModEntities.SOUL_SAND_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0413127));
            EntityRenderers.register(ModEntities.SOUL_SOIL_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0392b23));
            EntityRenderers.register(ModEntities.BLACKSTONE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0201819));
            EntityRenderers.register(ModEntities.BASALT_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0565456));
            EntityRenderers.register(ModEntities.QUARTZ_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0e4ddd3));
            EntityRenderers.register(ModEntities.GLOWSTONE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0784e27));
            EntityRenderers.register(ModEntities.ENDSTONE_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0cece8e));
            EntityRenderers.register(ModEntities.AMETHYST_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF06b4da5));
            EntityRenderers.register(ModEntities.BROWN_MUSHROOM_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0967251));
            EntityRenderers.register(ModEntities.RED_MUSHROOM_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0c02624));
            EntityRenderers.register(ModEntities.CACTUS_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0476d21));
            EntityRenderers.register(ModEntities.COAL_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF03b3d3b));
            EntityRenderers.register(ModEntities.GRAVEL_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF04a444b));
            EntityRenderers.register(ModEntities.ENERGY_SLIME.get(), pContext -> new BaseSlimeRenderer(pContext, 0xF0ffff70));

            event.enqueueWork(() -> {
                registerAllFluidRenderLayer();
                registerAllSlimeBlockRenderLayer();

                registerBlockRenderLayer(
                        ModBlocks.DNA_EXTRACTOR.get(),
                        ModBlocks.LIQUID_SOLIDING_STATION.get(),
                        ModBlocks.DNA_SYNTHESIZER.get()
                );

                ItemBlockRenderTypes.setRenderLayer(ModBlocks.CABLE.get(), renderType -> true);
            });
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
        }

        /*public static void registerAllSlimeEntityRenderer(){
            Field[] fields = ModEntities.class.getFields();

            for (Field field : fields) {
                try {
                    Object value = field.get(null);

                    if (value instanceof DeferredHolder<?, ?> holder && holder.get() instanceof EntityType<?> entityType) {
                        Integer color = ModEntities.SLIME_COLORS.get(entityType);
                        if (color != null) {
                            EntityRenderers.register((EntityType<? extends BaseSlime>) entityType, pContext -> new BaseSlimeRenderer(pContext, color.intValue()));
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }*/

        public static void registerAllFluidType(RegisterClientExtensionsEvent event){
            Field[] fields = ModFluidTypes.class.getFields();

            for (Field field : fields) {
                try {
                    Object value = field.get(null);

                    if (value instanceof Supplier<?> supplier) {
                        event.registerFluidType(((BaseFluidType) supplier.get()).getClientFluidTypeExtensions(),
                                (FluidType) supplier.get());
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
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
        }

        private static void registerBlockRenderLayer(Block... blocks) {
            for (Block b : blocks) {
                ItemBlockRenderTypes.setRenderLayer(b, RenderType.translucent());
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
        }
    }
}