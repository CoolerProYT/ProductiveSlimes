package com.coolerpromc.productiveslimes.item;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.config.CustomContentRegistry;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Field;
import java.util.function.Supplier;

public class ModCreativeTabs {
    public static final CreativeModeTab PRODUCTIVE_SLIMES_TAB = new CreativeModeTab("productiveslimes") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.SLIME_BLOCK);
        }

        @Override
        public void fillItemList(NonNullList<ItemStack> items) {
            // Add custom items to the creative tab
            items.add(ModItems.GUIDEBOOK.get().getDefaultInstance());
            items.add(ModItems.ENERGY_MULTIPLIER_UPGRADE.get().getDefaultInstance());
            items.add(ModItems.SLIME_NEST_SPEED_UPGRADE_1.get().getDefaultInstance());
            items.add(ModItems.SLIME_NEST_SPEED_UPGRADE_2.get().getDefaultInstance());
            items.add(ModItems.SLIMEBALL_FRAGMENT.get().getDefaultInstance());

            // Reflectively add blocks from ModBlocks
            for (Field field : ModBlocks.class.getFields()) {
                try {
                    if (Supplier.class.isAssignableFrom(field.getType())) {
                        Supplier<?> supplier = (Supplier<?>) field.get(null);
                        if (supplier.get() instanceof Block block) {
                            items.add(new ItemStack(block));
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            // Add items and blocks dynamically
            for (Tier tier : Tier.values()) {
                ModTiers modTiers = ModTierLists.getTierByName(tier);
                items.add(ModTierLists.getBlockByName(modTiers.name()).get().asItem().getDefaultInstance());
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()) {
                items.add(CustomContentRegistry.getSlimeBlockForVariant(variant.getName()).get().asItem().getDefaultInstance());
            }

            items.add(ModItems.ENERGY_SLIME_BALL.get().getDefaultInstance());
            for (Tier tier : Tier.values()) {
                ModTiers modTiers = ModTierLists.getTierByName(tier);
                items.add(ModTierLists.getSlimeballItemByName(modTiers.name()).get().getDefaultInstance());
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()) {
                items.add(CustomContentRegistry.getSlimeballItemForVariant(variant.getName()).get().getDefaultInstance());
            }

            items.add(ModItems.SLIME_DNA.get().getDefaultInstance());
            for (Tier tier : Tier.values()) {
                ModTiers modTiers = ModTierLists.getTierByName(tier);
                items.add(ModTierLists.getDnaItemByName(modTiers.name()).get().getDefaultInstance());
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()) {
                items.add(CustomContentRegistry.getDnaItemForVariant(variant.getName()).get().getDefaultInstance());
            }

            items.add(ModItems.ENERGY_SLIME_SPAWN_EGG.get().getDefaultInstance());
            for (Tier tier : Tier.values()) {
                ModTiers modTiers = ModTierLists.getTierByName(tier);
                items.add(ModTierLists.getSpawnEggItemByName(modTiers.name()).get().getDefaultInstance());
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()) {
                items.add(CustomContentRegistry.getSpawnEggItemForVariant(variant.getName()).get().getDefaultInstance());
            }

            for (Tier tier : Tier.values()) {
                ModTiers modTiers = ModTierLists.getTierByName(tier);
                items.add(ModTierLists.getBucketItemByName(modTiers.name()).get().getDefaultInstance());
            }

            for (CustomContentRegistry.CustomVariants variant : CustomContentRegistry.getLoadedTiers()) {
                items.add(ForgeRegistries.ITEMS.getValue(new ResourceLocation(ProductiveSlimes.MODID, "molten_" + variant.getName() + "_bucket")).getDefaultInstance());
            }
        }
    };

    public static void register() {
        // Nothing needs to be registered explicitly for CreativeModeTab in 1.19.2
    }
}
