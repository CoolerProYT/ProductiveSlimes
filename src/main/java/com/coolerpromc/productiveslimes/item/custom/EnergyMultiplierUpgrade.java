package com.coolerpromc.productiveslimes.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class EnergyMultiplierUpgrade extends Item {
    public EnergyMultiplierUpgrade(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, TooltipContext p_339594_, TooltipDisplay p_399753_, Consumer<Component> p_399884_, TooltipFlag p_41424_) {
        p_399884_.accept(
                Component.translatable("tooltip.productiveslimes.energy_multiplier_upgrade_desc").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));

        p_399884_.accept(Component.translatable(""));

        p_399884_.accept(Component.translatable("tooltip.productiveslimes.stack_count").setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GREEN))
                .append(Component.literal("1 / 2 / 3 / 4").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))));

        p_399884_.accept(Component.translatable("tooltip.productiveslimes.multiplier").setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GREEN))
                .append(Component.literal("x5 / x10 / x20 / x40").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))));
    }
}
