package com.coolerpromc.productiveslimes.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class GuidebookMenu extends Container {
    public GuidebookMenu(int containerId, PlayerInventory playerInventory) {
        super(ModMenuTypes.GUIDEBOOK_MENU.get(), containerId);
    }

    public GuidebookMenu(int containerId, PlayerInventory playerInventory, PacketBuffer extraData) {
        this(containerId, playerInventory);
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity pPlayer, int pIndex) {
        return ItemStack.EMPTY;
    }
}
