package com.coolerpromc.productiveslimes.util.property;

import com.coolerpromc.productiveslimes.datacomponent.ModDataComponents;
import com.coolerpromc.productiveslimes.handler.ImmutableFluidStack;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

public record FluidTankProperty6k() implements ConditionalItemModelProperty {
    public static final MapCodec<FluidTankProperty6k> MAP_CODEC = MapCodec.unit(new FluidTankProperty6k());
    @Override
    public boolean get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int p_388885_, ItemDisplayContext displayContext) {
        ImmutableFluidStack immutableFluidStack = stack.get(ModDataComponents.FLUID_STACK.get());
        FluidStack fluidStack = (immutableFluidStack != null) ? immutableFluidStack.fluidStack() : FluidStack.EMPTY;
        return fluidStack.getAmount() <= 6000;
    }

    @Override
    public MapCodec<? extends ConditionalItemModelProperty> type() {
        return MAP_CODEC;
    }
}
