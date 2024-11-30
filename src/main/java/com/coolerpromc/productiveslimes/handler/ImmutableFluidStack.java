package com.coolerpromc.productiveslimes.handler;

import com.coolerpromc.productiveslimes.fluid.FluidRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Objects;

public record ImmutableFluidStack(FluidStack fluidStack) {

    // Custom Codec for ImmutableFluidStack
    public static final Codec<ImmutableFluidStack> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("amount").forGetter(stack -> stack.fluidStack().getAmount()),
                    Codec.STRING.fieldOf("fluid").forGetter(stack -> {
                        ResourceLocation registryName = BuiltInRegistries.FLUID.getKey(stack.fluidStack().getFluid());
                        if (registryName != null) {
                            return registryName.toString();
                        } else {
                            return ""; // Return empty string if registry name is missing
                        }
                    })
            ).apply(instance, (amount, fluidName) -> {
                if (fluidName.isEmpty()) {
                    return new ImmutableFluidStack(FluidStack.EMPTY);
                }
                FluidStack retrievedFluidStack = FluidRegistry.getFluidStackByName(fluidName, amount);
                if (retrievedFluidStack == null) {
                    return new ImmutableFluidStack(FluidStack.EMPTY);
                }

                return new ImmutableFluidStack(retrievedFluidStack);
            })
    );

    // Ensure immutability by preventing external modification
    public FluidStack fluidStack() {
        return fluidStack.copy(); // Assuming FluidStack has a copy method
    }

    // Override equals and hashCode to delegate to fluidStack's properties
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ImmutableFluidStack other)) return false;
        return Objects.equals(this.fluidStack().getFluid(), other.fluidStack().getFluid()) &&
                this.fluidStack().getAmount() == other.fluidStack().getAmount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.fluidStack().getFluid(), this.fluidStack().getAmount());
    }
}