package com.coolerpromc.productiveslimes.item;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.fluid.ModFluids;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

                        // Use reflection to get all the fields from ModItems
                        for (Field field : ModItems.class.getFields()) {
                            try {
                                // Ensure the field is a Supplier of Item (for items)
                                if (Supplier.class.isAssignableFrom(field.getType())) {
                                    Supplier<?> supplier = (Supplier<?>) field.get(null);
                                    if (supplier.get() instanceof Item) {
                                        pOutput.accept((Item) supplier.get()); // Add item to the output
                                    }
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }

                        // Use reflection to get all the fields from ModFluids
                        for (Field field : ModFluids.class.getFields()) {
                            try {
                                // Ensure the field is a Supplier of Item (for items)
                                if (Supplier.class.isAssignableFrom(field.getType())) {
                                    Supplier<?> supplier = (Supplier<?>) field.get(null);
                                    if (supplier.get() instanceof Item) {
                                        pOutput.accept((Item) supplier.get()); // Add items to the output
                                    }
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MOD_TABS.register(eventBus);
    }
}
