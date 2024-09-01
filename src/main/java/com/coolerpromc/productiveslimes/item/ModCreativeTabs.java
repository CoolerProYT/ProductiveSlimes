package com.coolerpromc.productiveslimes.item;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
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
                        pOutput.accept(ModItems.DIRT_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.IRON_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.GOLD_SLIME_SPAWN_EGG.get());
                        pOutput.accept(ModItems.DIRT_SLIME_BALL.get());
                        pOutput.accept(ModItems.IRON_SLIME_BALL.get());
                        pOutput.accept(ModItems.GOLD_SLIME_BALL.get());
                    })).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MOD_TABS.register(eventBus);
    }
}
