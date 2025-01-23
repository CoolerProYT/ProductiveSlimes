package com.coolerpromc.productiveslimes.item.custom;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.*;
import net.minecraft.world.World;

import javax.annotation.Nullable;
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
    public void appendHoverText(ItemStack pStack, @Nullable World pLevel, List<ITextComponent> pTooltipComponents, ITooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);

        pTooltipComponents.add(
                new TranslationTextComponent("tooltip.productiveslimes.energy_multiplier_upgrade_desc").setStyle(Style.EMPTY.withColor(TextFormatting.GRAY)));

        pTooltipComponents.add(new TranslationTextComponent(""));

        pTooltipComponents.add(new TranslationTextComponent("tooltip.productiveslimes.stack_count").setStyle(Style.EMPTY.withColor(TextFormatting.DARK_GREEN))
                .append(new StringTextComponent("1 / 2 / 3 / 4").setStyle(Style.EMPTY.withColor(TextFormatting.GRAY))));

        pTooltipComponents.add(new TranslationTextComponent("tooltip.productiveslimes.multiplier").setStyle(Style.EMPTY.withColor(TextFormatting.DARK_GREEN))
                .append(new StringTextComponent("x5 / x10 / x20 / x40").setStyle(Style.EMPTY.withColor(TextFormatting.GRAY))));
    }
}
