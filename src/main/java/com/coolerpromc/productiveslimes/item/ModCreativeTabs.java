package com.coolerpromc.productiveslimes.item;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.config.CustomContentRegistry;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.lang.reflect.Field;
import java.util.function.Supplier;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ProductiveSlimes.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PRODUCTIVE_SLIMES_TAB = CREATIVE_MOD_TABS.register("productive_slimes",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.SLIME_BLOCK))
                    .title(Component.translatable("creativetab.productiveslimes"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.GUIDEBOOK);
                        pOutput.accept(ModItems.ENERGY_MULTIPLIER_UPGRADE);

                        // Use reflection to get all the fields from ModBlocks
                        for (Field field : ModBlocks.class.getFields()) {
                            try {
                                // Ensure the field is a Supplier of Block (for blocks)
                                if (Supplier.class.isAssignableFrom(field.getType())) {
                                    Supplier<?> supplier = (Supplier<?>) field.get(null);
                                    if (supplier.get() instanceof Block) {
                                        pOutput.accept((Block) supplier.get()); // Add block to the output
                                    }
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }

                        for (String name : ModTierLists.TIER_NAMES){
                            pOutput.accept(ModTierLists.getBlockByName(name).get());
                        }

                        for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                            pOutput.accept(CustomContentRegistry.getSlimeBlockForVariant(variant.getName()));
                        }

                        pOutput.accept(ModItems.ENERGY_SLIME_BALL);

                        for (String name : ModTierLists.TIER_NAMES){
                            pOutput.accept(ModTierLists.getSlimeballItemByName(name).get());
                        }

                        for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                            pOutput.accept(CustomContentRegistry.getSlimeballItemForVariant(variant.getName()));
                        }

                        pOutput.accept(ModItems.SLIME_DNA);

                        for (String name : ModTierLists.TIER_NAMES){
                            pOutput.accept(ModTierLists.getDnaItemByName(name).get());
                        }

                        for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                            pOutput.accept(CustomContentRegistry.getDnaItemForVariant(variant.getName()));
                        }

                        pOutput.accept(ModItems.ENERGY_SLIME_SPAWN_EGG);

                        for (String name : ModTierLists.TIER_NAMES){
                            pOutput.accept(ModTierLists.getSpawnEggItemByName(name).get());
                        }

                        for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                            pOutput.accept(CustomContentRegistry.getSpawnEggItemForVariant(variant.getName()));
                        }

                        for (String name : ModTierLists.TIER_NAMES){
                            pOutput.accept(ModTierLists.getBucketItemByName(name).get());
                        }

                        for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                            pOutput.accept(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "molten_" + variant.getName() + "_bucket")).get().value());
                        }
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MOD_TABS.register(eventBus);
    }
}
