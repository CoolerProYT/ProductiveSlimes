package com.coolerpromc.productiveslimes.util;

import com.coolerpromc.productiveslimes.datacomponent.ModDataComponents;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record FluidTankProperty() implements ConditionalItemModelProperty {
    public static final MapCodec<FluidTankProperty> MAP_CODEC = MapCodec.unit(new FluidTankProperty());
    @Override
    public boolean get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int p_388885_, ItemDisplayContext displayContext) {
        return !stack.has(ModDataComponents.FLUID_STACK.get());
    }

    @Override
    public MapCodec<? extends ConditionalItemModelProperty> type() {
        return MAP_CODEC;
    }
}
