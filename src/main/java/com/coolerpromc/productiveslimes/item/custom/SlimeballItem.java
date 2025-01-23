package com.coolerpromc.productiveslimes.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class SlimeballItem extends Item {
    public final int color;
    public SlimeballItem(int pColor) {
        super(new Item.Properties().tab(ItemGroup.TAB_MISC));
        this.color = pColor;
    }

    public int getColor() {
        return color;
    }
}
