package com.coolerpromc.productiveslimes.item.custom;

import com.coolerpromc.productiveslimes.screen.GuidebookMenu;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class GuidebookItem extends Item {
    public GuidebookItem(){
        super(new Item.Properties().stacksTo(1).tab(ItemGroup.TAB_MISC));
    }

    @Override
    public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pUsedHand) {
        if (!pLevel.isClientSide) {
            pPlayer.openMenu(new SimpleNamedContainerProvider(
                    (windowId, playerInventory, playerEntity) -> new GuidebookMenu(windowId, playerInventory),
                    new StringTextComponent("item.productiveslimes.guidebook")
            ));
        }
        return ActionResult.success(pPlayer.getItemInHand(pUsedHand));
    }
}
