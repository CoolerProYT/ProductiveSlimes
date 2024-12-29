package com.coolerpromc.productiveslimes.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
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
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);

        pTooltipComponents.add(
                new TranslatableComponent("tooltip.productiveslimes.energy_multiplier_upgrade_desc").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));

        pTooltipComponents.add(new TranslatableComponent(""));

        pTooltipComponents.add(new TranslatableComponent("tooltip.productiveslimes.stack_count").setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GREEN))
                .append(new TextComponent("1 / 2 / 3 / 4").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))));

        pTooltipComponents.add(new TranslatableComponent("tooltip.productiveslimes.multiplier").setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GREEN))
                .append(new TextComponent("x5 / x10 / x20 / x40").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))));
    }
}
