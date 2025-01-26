package com.coolerpromc.productiveslimes.util;

import com.coolerpromc.productiveslimes.datacomponent.ModDataComponents;
import com.coolerpromc.productiveslimes.handler.ImmutableFluidStack;
import com.coolerpromc.productiveslimes.item.custom.BucketItem;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ARGB;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

public record FluidTankTint(int defaultColor) implements ItemTintSource {
    public static final MapCodec<FluidTankTint> MAP_CODEC = RecordCodecBuilder.mapCodec(fluidTankTintInstance ->
            fluidTankTintInstance.group(
                    ExtraCodecs.ARGB_COLOR_CODEC.fieldOf("default").forGetter(FluidTankTint::defaultColor)
                    ).apply(fluidTankTintInstance, FluidTankTint::new)
    );

    public FluidTankTint(int defaultColor) {
        this.defaultColor = defaultColor;
    }

    @Override
    public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity) {
        if (stack.has(ModDataComponents.FLUID_STACK.get())) {
            ImmutableFluidStack immutableFluidStack = stack.get(ModDataComponents.FLUID_STACK.get());
            FluidStack fluidStack = (immutableFluidStack != null) ? immutableFluidStack.fluidStack() : FluidStack.EMPTY;
            if (fluidStack.getFluid().getBucket() instanceof BucketItem bucketItem) {
                return ARGB.opaque(bucketItem.getColor());
            }
        }
        return defaultColor;
    }

    @Override
    public MapCodec<? extends ItemTintSource> type() {
        return MAP_CODEC;
    }
}
