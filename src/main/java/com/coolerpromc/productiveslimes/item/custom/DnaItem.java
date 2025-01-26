package com.coolerpromc.productiveslimes.item.custom;

import net.minecraft.world.item.Item;

public class DnaItem extends Item {
    public final int color;
    public DnaItem(int pColor, Item.Properties pProperties) {
        super(pProperties);
        this.color = pColor;
    }

    public int getColor() { return color; }
}
