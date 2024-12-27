package com.coolerpromc.productiveslimes.util;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record SlimeItemTint() implements ItemTintSource {
    @Override
    public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity) {
        return 0;
    }

    @Override
    public MapCodec<? extends ItemTintSource> type() {
        return null;
    }
}
