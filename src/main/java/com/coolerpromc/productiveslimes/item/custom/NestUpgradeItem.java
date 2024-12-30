package com.coolerpromc.productiveslimes.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class NestUpgradeItem extends Item {
    private final float multiplier;

    public NestUpgradeItem(Properties pProperties, float multiplier) {
        super(pProperties);
        this.multiplier = multiplier;
    }

    public float getMultiplier() {
        return multiplier;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(new TextComponent("Increases the speed of the slime nest by " + String.format("%.2f", multiplier) + "x"));
    }
}