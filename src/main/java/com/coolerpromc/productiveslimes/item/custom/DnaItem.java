package com.coolerpromc.productiveslimes.item.custom;

import net.minecraft.world.item.Item;

public class DnaItem extends Item {
    public final int color;
    public DnaItem(int pColor) {
        super(new Item.Properties());
        this.color = pColor;
    }

    public int getColor() { return color; }
}
