package com.coolerpromc.productiveslimes.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.List;
import java.util.function.Consumer;

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
    public void appendHoverText(ItemStack p_41421_, TooltipContext p_339594_, TooltipDisplay p_399753_, Consumer<Component> p_399884_, TooltipFlag p_41424_) {
        p_399884_.accept(Component.translatable("tooltip.productiveslimes.nest_upgrade", String.format("%.2f", multiplier)));
    }
}