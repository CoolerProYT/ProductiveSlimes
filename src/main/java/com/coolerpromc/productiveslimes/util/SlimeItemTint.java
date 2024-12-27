package com.coolerpromc.productiveslimes.util;

import com.coolerpromc.productiveslimes.datacomponent.ModDataComponents;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ARGB;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record SlimeItemTint(int defaultColor) implements ItemTintSource {
    public static final MapCodec<SlimeItemTint> MAP_CODEC = RecordCodecBuilder.mapCodec(slimeItemTintInstance ->
            slimeItemTintInstance.group(
                    ExtraCodecs.ARGB_COLOR_CODEC.fieldOf("default").forGetter(SlimeItemTint::defaultColor)
            ).apply(slimeItemTintInstance, SlimeItemTint::new)
    );

    @Override
    public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity) {
        if (stack.has(ModDataComponents.SLIME_DATA.get())) {
            return ARGB.opaque(stack.get(ModDataComponents.SLIME_DATA.get()).color());
        }
        return defaultColor;
    }

    @Override
    public MapCodec<? extends ItemTintSource> type() {
        return MAP_CODEC;
    }
}
