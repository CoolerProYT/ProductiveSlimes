package com.coolerpromc.productiveslimes.item.custom;

import net.minecraft.world.item.Item;

public class SlimeballItem extends Item {
    public final int color;
    public SlimeballItem(int pColor) {
        super(new Item.Properties());
        this.color = pColor;
    }

    public int getColor() {
        return color;
    }
}
