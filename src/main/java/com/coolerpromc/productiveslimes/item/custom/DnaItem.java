package com.coolerpromc.productiveslimes.item.custom;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class DnaItem extends Item {
    public final int color;
    public DnaItem(int pColor) {
        super(new Item.Properties().tab(CreativeModeTab.TAB_MISC));
        this.color = pColor;
    }

    public int getColor() { return color; }
}
