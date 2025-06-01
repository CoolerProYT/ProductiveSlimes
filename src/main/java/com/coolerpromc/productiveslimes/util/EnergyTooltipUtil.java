package com.coolerpromc.productiveslimes.util;

import net.minecraft.network.chat.Component;

public record EnergyTooltipUtil(int mouseX1, int mouseX2, int mouseY1, int mouseY2, int energyRequired, int maxCapacity) {
    public Component getText(){
        return Component.translatable("gui.productiveslimes.energy_stored", energyRequired, maxCapacity);
    }
}