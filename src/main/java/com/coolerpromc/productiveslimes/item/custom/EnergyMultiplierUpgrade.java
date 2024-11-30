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
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);

        pTooltipComponents.add(
                Component.translatable("tooltip.productiveslimes.energy_multiplier_upgrade_desc").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));

        pTooltipComponents.add(Component.translatable(""));

        pTooltipComponents.add(Component.translatable("tooltip.productiveslimes.stack_count").setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GREEN))
                .append(Component.literal("1 / 2 / 3 / 4").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))));

        pTooltipComponents.add(Component.translatable("tooltip.productiveslimes.multiplier").setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GREEN))
                .append(Component.literal("x5 / x10 / x20 / x40").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))));
    }
}
