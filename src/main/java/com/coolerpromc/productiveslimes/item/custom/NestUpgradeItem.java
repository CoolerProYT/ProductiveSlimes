package com.coolerpromc.productiveslimes.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable("tooltip.productiveslimes.nest_upgrade", String.format("%.2f", multiplier)));
    }
}