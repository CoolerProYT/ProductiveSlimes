package com.coolerpromc.productiveslimes.item.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;

public class SpawnEggItem extends net.minecraft.world.item.SpawnEggItem {
    private final int color;

    public SpawnEggItem(EntityType<? extends Mob> p_43207_, int color, Properties p_43210_) {
        super(p_43207_, p_43210_);
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
