package com.coolerpromc.productiveslimes.item;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.config.CustomContentRegistry;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
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
                        pOutput.accept(ModItems.SLIME_NEST_SPEED_UPGRADE_1);
                        pOutput.accept(ModItems.SLIME_NEST_SPEED_UPGRADE_2);
                        pOutput.accept(ModItems.SLIMEBALL_FRAGMENT);

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

                        for (Tier tier : Tier.values()){
                            ModTiers tiers = ModTierLists.getTierByName(tier);
                            pOutput.accept(ModTierLists.getBlockByName(tiers.name()).get());
                        }

                        for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                            pOutput.accept(CustomContentRegistry.getSlimeBlockForVariant(variant.getName()));
                        }

                        pOutput.accept(ModItems.ENERGY_SLIME_BALL);

                        for (Tier tier : Tier.values()){
                            ModTiers tiers = ModTierLists.getTierByName(tier);
                            pOutput.accept(ModTierLists.getSlimeballItemByName(tiers.name()).get());
                        }

                        for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                            pOutput.accept(CustomContentRegistry.getSlimeballItemForVariant(variant.getName()));
                        }

                        pOutput.accept(ModItems.SLIME_DNA);

                        for (Tier tier : Tier.values()){
                            ModTiers tiers = ModTierLists.getTierByName(tier);
                            pOutput.accept(ModTierLists.getDnaItemByName(tiers.name()).get());
                        }

                        for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                            pOutput.accept(CustomContentRegistry.getDnaItemForVariant(variant.getName()));
                        }

                        pOutput.accept(ModItems.ENERGY_SLIME_SPAWN_EGG);

                        for (Tier tier : Tier.values()){
                            ModTiers tiers = ModTierLists.getTierByName(tier);
                            pOutput.accept(ModTierLists.getSpawnEggItemByName(tiers.name()).get());
                        }

                        for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                            pOutput.accept(CustomContentRegistry.getSpawnEggItemForVariant(variant.getName()));
                        }

                        for (Tier tier : Tier.values()){
                            ModTiers tiers = ModTierLists.getTierByName(tier);
                            pOutput.accept(ModTierLists.getBucketItemByName(tiers.name()).get());
                        }

                        for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                            pOutput.accept(BuiltInRegistries.ITEM.get(Identifier.fromNamespaceAndPath(ProductiveSlimes.MODID, "molten_" + variant.getName() + "_bucket")).get().value());
                        }
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MOD_TABS.register(eventBus);
    }
}
