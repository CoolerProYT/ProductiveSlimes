package com.coolerpromc.productiveslimes.item.custom;

import com.coolerpromc.productiveslimes.screen.GuidebookMenu;
import com.coolerpromc.productiveslimes.screen.GuidebookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GuidebookItem extends Item {
    public GuidebookItem(){
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide) {
            pPlayer.openMenu(new SimpleMenuProvider(
                    (windowId, playerInventory, playerEntity) -> new GuidebookMenu(windowId, playerInventory),
                    Component.literal("Guidebook")
            ));
        }
        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }
}
