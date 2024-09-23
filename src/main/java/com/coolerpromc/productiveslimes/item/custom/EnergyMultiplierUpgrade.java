package com.coolerpromc.productiveslimes.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnergyMultiplierUpgrade extends Item {
    public EnergyMultiplierUpgrade(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);

        pTooltipComponents.add(
                Component.literal("Increases the energy multiplier of the Energy Generator.").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));

        pTooltipComponents.add(Component.literal(""));

        pTooltipComponents.add(Component.literal("Stack Count: ").setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GREEN))
                .append(Component.literal("1 / 2 / 3 / 4").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))));

        pTooltipComponents.add(Component.literal("Multiplier: ").setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GREEN))
                .append(Component.literal("x5 / x10 / x20 / x40").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))));
    }
}
