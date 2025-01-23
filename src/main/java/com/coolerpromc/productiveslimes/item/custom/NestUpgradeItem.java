package com.coolerpromc.productiveslimes.item.custom;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

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
    public void appendHoverText(ItemStack pStack, @Nullable World pLevel, List<ITextComponent> pTooltipComponents, ITooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(new StringTextComponent("Increases the speed of the slime nest by " + String.format("%.2f", multiplier) + "x"));
    }
}