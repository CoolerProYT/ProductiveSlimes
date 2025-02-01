package com.coolerpromc.productiveslimes.screen;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GuidebookMenu extends AbstractContainerMenu {
    public final Level level;

    public GuidebookMenu(int containerId, Inventory playerInventory) {
        super(ModMenuTypes.GUIDEBOOK_MENU.get(), containerId);
        this.level = playerInventory.player.level();
    }

    public GuidebookMenu(int containerId, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(containerId, playerInventory);
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return ItemStack.EMPTY;
    }
}
