package com.coolerpromc.productiveslimes.item;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.config.CustomContentRegistry;
import com.coolerpromc.productiveslimes.fluid.ModFluids;
import com.coolerpromc.productiveslimes.item.custom.DnaItem;
import com.coolerpromc.productiveslimes.item.custom.SlimeballItem;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.Field;
import java.util.function.Supplier;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ProductiveSlimes.MODID);

    public static final RegistryObject<CreativeModeTab> PRODUCTIVE_SLIMES_TAB = CREATIVE_MOD_TABS.register("productive_slimes",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.SLIME_BLOCK))
                    .title(Component.translatable("creativetab.productiveslimes"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.GUIDEBOOK.get());
                        pOutput.accept(ModItems.ENERGY_MULTIPLIER_UPGRADE.get());
                        pOutput.accept(ModItems.SLIMEBALL_FRAGMENT.get());

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
                            ModTiers modTiers = ModTierLists.getTierByName(tier);
                            pOutput.accept(ModTierLists.getBlockByName(modTiers.name()).get());
                        }

                        for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                            pOutput.accept(CustomContentRegistry.getSlimeBlockForVariant(variant.getName()).get());
                        }

                        pOutput.accept(ModItems.ENERGY_SLIME_BALL.get());
                        for (Tier tier : Tier.values()){
                            ModTiers modTiers = ModTierLists.getTierByName(tier);
                            pOutput.accept(ModTierLists.getSlimeballItemByName(modTiers.name()).get());
                        }

                        for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                            pOutput.accept(CustomContentRegistry.getSlimeballItemForVariant(variant.getName()).get());
                        }

                        pOutput.accept(ModItems.SLIME_DNA.get());
                        for (Tier tier : Tier.values()){
                            ModTiers modTiers = ModTierLists.getTierByName(tier);
                            pOutput.accept(ModTierLists.getDnaItemByName(modTiers.name()).get());
                        }

                        for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                            pOutput.accept(CustomContentRegistry.getDnaItemForVariant(variant.getName()).get());
                        }

                        pOutput.accept(ModItems.ENERGY_SLIME_SPAWN_EGG.get());
                        for (Tier tier : Tier.values()){
                            ModTiers modTiers = ModTierLists.getTierByName(tier);
                            pOutput.accept(ModTierLists.getSpawnEggItemByName(modTiers.name()).get());
                        }

                        for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                            pOutput.accept(CustomContentRegistry.getSpawnEggItemForVariant(variant.getName()).get());
                        }

                        for (Tier tier : Tier.values()){
                            ModTiers modTiers = ModTierLists.getTierByName(tier);
                            pOutput.accept(ModTierLists.getBucketItemByName(modTiers.name()).get());
                        }

                        for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()){
                            pOutput.accept(BuiltInRegistries.ITEM.get(new ResourceLocation(ProductiveSlimes.MODID, "molten_" + variant.getName() + "_bucket")));
                        }
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MOD_TABS.register(eventBus);
    }
}
