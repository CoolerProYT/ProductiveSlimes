package com.coolerpromc.productiveslimes.item;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.fluid.ModFluids;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ProductiveSlimes.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PRODUCTIVE_SLIMES_TAB = CREATIVE_MOD_TABS.register("productive_slimes",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.SLIME_BLOCK))
                    .title(Component.translatable("creativetab.productiveslimes"))
                    .displayItems(((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.GUIDEBOOK.get());

                        pOutput.accept(ModBlocks.MELTING_STATION.get());
                        pOutput.accept(ModBlocks.LIQUID_SOLIDING_STATION.get());

                        pOutput.accept(ModItems.DIRT_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.STONE_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.IRON_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.COPPER_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.GOLD_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.DIAMOND_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.NETHERITE_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.LAPIS_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.REDSTONE_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.OAK_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.SAND_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.ANDESITE_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.SNOW_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.ICE_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.MUD_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.CLAY_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.RED_SAND_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.MOSS_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.DEEPSLATE_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.GRANITE_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.DIORITE_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.CALCITE_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.TUFF_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.DRIPSTONE_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.PRISMARINE_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.MAGMA_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.OBSIDIAN_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.NETHERRACK_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.SOUL_SAND_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.SOUL_SOIL_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.BLACKSTONE_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.BASALT_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.ENDSTONE_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.QUARTZ_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.GLOWSTONE_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.AMETHYST_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.BROWN_MUSHROOM_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.RED_MUSHROOM_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.CACTUS_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.COAL_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.GRAVEL_SLIME_SPAWN_EGG.get());

                        pOutput.accept(ModItems.DIRT_SLIME_BALL.get());
                        pOutput.accept(ModItems.STONE_SLIME_BALL.get());
                        pOutput.accept(ModItems.IRON_SLIME_BALL.get());
                        pOutput.accept(ModItems.COPPER_SLIME_BALL.get());
                        pOutput.accept(ModItems.GOLD_SLIME_BALL.get());
                        pOutput.accept(ModItems.DIAMOND_SLIME_BALL.get());
                        pOutput.accept(ModItems.NETHERITE_SLIME_BALL.get());
                        pOutput.accept(ModItems.LAPIS_SLIME_BALL.get());
                        pOutput.accept(ModItems.REDSTONE_SLIME_BALL.get());
                        pOutput.accept(ModItems.OAK_SLIME_BALL.get());
                        pOutput.accept(ModItems.SAND_SLIME_BALL.get());
                        pOutput.accept(ModItems.ANDESITE_SLIME_BALL.get());
                        pOutput.accept(ModItems.SNOW_SLIME_BALL.get());
                        pOutput.accept(ModItems.ICE_SLIME_BALL.get());
                        pOutput.accept(ModItems.MUD_SLIME_BALL.get());
                        pOutput.accept(ModItems.CLAY_SLIME_BALL.get());
                        pOutput.accept(ModItems.RED_SAND_SLIME_BALL.get());
                        pOutput.accept(ModItems.MOSS_SLIME_BALL.get());
                        pOutput.accept(ModItems.DEEPSLATE_SLIME_BALL.get());
                        pOutput.accept(ModItems.GRANITE_SLIME_BALL.get());
                        pOutput.accept(ModItems.DIORITE_SLIME_BALL.get());
                        pOutput.accept(ModItems.CALCITE_SLIME_BALL.get());
                        pOutput.accept(ModItems.TUFF_SLIME_BALL.get());
                        pOutput.accept(ModItems.DRIPSTONE_SLIME_BALL.get());
                        pOutput.accept(ModItems.PRISMARINE_SLIME_BALL.get());
                        pOutput.accept(ModItems.MAGMA_SLIME_BALL.get());
                        pOutput.accept(ModItems.OBSIDIAN_SLIME_BALL.get());
                        pOutput.accept(ModItems.NETHERRACK_SLIME_BALL.get());
                        pOutput.accept(ModItems.SOUL_SAND_SLIME_BALL.get());
                        pOutput.accept(ModItems.SOUL_SOIL_SLIME_BALL.get());
                        pOutput.accept(ModItems.BLACKSTONE_SLIME_BALL.get());
                        pOutput.accept(ModItems.BASALT_SLIME_BALL.get());
                        pOutput.accept(ModItems.ENDSTONE_SLIME_BALL.get());
                        pOutput.accept(ModItems.QUARTZ_SLIME_BALL.get());
                        pOutput.accept(ModItems.GLOWSTONE_SLIME_BALL.get());
                        pOutput.accept(ModItems.AMETHYST_SLIME_BALL.get());
                        pOutput.accept(ModItems.BROWN_MUSHROOM_SLIME_BALL.get());
                        pOutput.accept(ModItems.RED_MUSHROOM_SLIME_BALL.get());
                        pOutput.accept(ModItems.CACTUS_SLIME_BALL.get());
                        pOutput.accept(ModItems.COAL_SLIME_BALL.get());
                        pOutput.accept(ModItems.GRAVEL_SLIME_BALL.get());

                        pOutput.accept(ModBlocks.DIRT_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.STONE_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.IRON_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.COPPER_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.GOLD_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.DIAMOND_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.NETHERITE_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.LAPIS_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.REDSTONE_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.OAK_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.SAND_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.ANDESITE_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.SNOW_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.ICE_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.MUD_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.CLAY_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.RED_SAND_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.MOSS_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.DEEPSLATE_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.GRANITE_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.DIORITE_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.CALCITE_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.TUFF_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.DRIPSTONE_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.PRISMARINE_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.MAGMA_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.OBSIDIAN_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.NETHERRACK_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.SOUL_SAND_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.SOUL_SOIL_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.BLACKSTONE_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.BASALT_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.ENDSTONE_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.QUARTZ_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.GLOWSTONE_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.AMETHYST_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.BROWN_MUSHROOM_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.RED_MUSHROOM_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.CACTUS_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.COAL_SLIME_BLOCK.get());
                        pOutput.accept(ModBlocks.GRAVEL_SLIME_BLOCK.get());

                        pOutput.accept(ModFluids.MOLTEN_DIRT_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_STONE_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_IRON_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_COPPER_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_GOLD_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_DIAMOND_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_NETHERITE_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_LAPIS_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_REDSTONE_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_OAK_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_SAND_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_ANDESITE_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_SNOW_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_ICE_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_MUD_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_CLAY_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_RED_SAND_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_MOSS_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_DEEPSLATE_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_GRANITE_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_DIORITE_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_CALCITE_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_TUFF_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_DRIPSTONE_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_PRISMARINE_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_MAGMA_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_OBSIDIAN_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_NETHERRACK_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_SOUL_SAND_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_SOUL_SOIL_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_BLACKSTONE_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_BASALT_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_ENDSTONE_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_QUARTZ_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_GLOWSTONE_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_AMETHYST_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_BROWN_MUSHROOM_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_RED_MUSHROOM_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_CACTUS_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_COAL_BUCKET.get());
                        pOutput.accept(ModFluids.MOLTEN_GRAVEL_BUCKET.get());
                    })).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MOD_TABS.register(eventBus);
    }
}
