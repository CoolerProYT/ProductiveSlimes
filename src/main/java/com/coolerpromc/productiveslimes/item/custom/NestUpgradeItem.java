package com.coolerpromc.productiveslimes.item.custom;

import net.minecraft.world.item.Item;

public class NestUpgradeItem extends Item {
    private final int multiplier;

    public NestUpgradeItem(Properties pProperties, int multiplier) {
        super(pProperties);
        this.multiplier = multiplier;
    }

    public int getMultiplier() {
        return multiplier;
    }
}
