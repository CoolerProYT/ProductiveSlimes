package com.coolerpromc.productiveslimes.item.custom;

import com.coolerpromc.productiveslimes.screen.GuidebookMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GuidebookItem extends Item {
    public GuidebookItem(){
        super(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide) {
            pPlayer.openMenu(new SimpleMenuProvider(
                    (windowId, playerInventory, playerEntity) -> new GuidebookMenu(windowId, playerInventory),
                    new TranslatableComponent("item.productiveslimes.guidebook")
            ));
        }
        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }
}
