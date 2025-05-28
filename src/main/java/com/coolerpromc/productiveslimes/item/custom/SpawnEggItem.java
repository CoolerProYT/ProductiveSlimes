package com.coolerpromc.productiveslimes.item.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;

public class SpawnEggItem extends net.minecraft.world.item.SpawnEggItem {
    private final int bg;
    private final int fg;

    public SpawnEggItem(EntityType<? extends Mob> p_43207_, int bg, int fg, Properties p_43210_) {
        super(p_43207_, p_43210_);
        this.bg = bg;
        this.fg = fg;
    }

    public int getBg() {
        return bg;
    }

    public int getFg() {
        return fg;
    }
}
