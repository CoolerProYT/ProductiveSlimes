package com.coolerpromc.productiveslimes.networking.pipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PipeNetwork {
    private int networkId = -1;
    private Fluid fluid = Fluids.EMPTY;
    private int totalFluid = 0;
    private int totalCapacity = 0;

    private final Set<BlockPos> cablePositions = new HashSet<>();

    public static final Codec<BlockPos> BLOCK_POS_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("x").forGetter(BlockPos::getX),
            Codec.INT.fieldOf("y").forGetter(BlockPos::getY),
            Codec.INT.fieldOf("z").forGetter(BlockPos::getZ)
    ).apply(instance, BlockPos::new));

    public static final Codec<PipeNetwork> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("NetworkId", -1).forGetter(net -> net.networkId),
            Codec.INT.fieldOf("TotalFluid").forGetter(net -> net.totalFluid),
            Codec.STRING.fieldOf("Fluid").forGetter(net -> BuiltInRegistries.FLUID.getKey(net.fluid).toString()),
            Codec.INT.fieldOf("TotalCapacity").forGetter(net -> net.totalCapacity),
            BLOCK_POS_CODEC.listOf().fieldOf("Positions").forGetter(net -> net.cablePositions.stream().toList())
    ).apply(instance, PipeNetwork::new));

    public PipeNetwork() {}

    private PipeNetwork(int networkId, int totalFluid, String fluid, int totalCapacity, List<BlockPos> cablePositions) {
        this.networkId = networkId;
        this.totalFluid = totalFluid;
        this.totalCapacity = totalCapacity;
        this.fluid = BuiltInRegistries.FLUID.getValue(ResourceLocation.parse(fluid));
        this.cablePositions.addAll(cablePositions);
    }

    // NEW: Accessors for the network ID
    public int getNetworkId() {
        return networkId;
    }

    public void setNetworkId(int id) {
        this.networkId = id;
    }

    public void addCable(BlockPos pos, int cableCapacity) {
        if (cablePositions.add(pos)) {
            totalCapacity += cableCapacity;
            if (totalFluid > totalCapacity) {
                totalFluid = totalCapacity;
            }
        }
    }

    public void removeCable(BlockPos pos, int cableCapacity) {
        if (cablePositions.remove(pos)) {
            totalCapacity -= cableCapacity;
            if (totalCapacity < 0) totalCapacity = 0;
            if (totalFluid > totalCapacity) totalFluid = totalCapacity;
        }
    }

    public Set<BlockPos> getCablePositions() {
        return cablePositions;
    }

    public Fluid getFluid() {
        return fluid;
    }

    public int getTotalFluid() {
        return totalFluid;
    }

    public FluidStack getFluidStack(){
        return new FluidStack(getFluid(), getTotalFluid());
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalFluid(int totalFluid) {
        this.totalFluid = totalFluid;
    }

    public void setFluid(Fluid fluid) {
        this.fluid = fluid;
    }

    public int insertFluid(FluidStack fluidStack, boolean simulate) {
        if (this.fluid.isSame(Fluids.EMPTY)) {
            int accepted = Math.min(fluidStack.getAmount(), this.totalCapacity);
            if (!simulate) {
                this.fluid = fluidStack.getFluid();
                this.totalFluid = accepted;
            }
            return accepted;
        }
        int space = totalCapacity - totalFluid;
        int accepted = Math.min(space, fluidStack.getAmount());
        if (!simulate) {
            totalFluid += accepted;
        }
        return accepted;
    }

    public FluidStack extractFluid(FluidStack fluidStack, boolean simulate) {
        if (fluidStack.isEmpty() || !FluidStack.isSameFluidSameComponents(fluidStack, getFluidStack())) {
            return FluidStack.EMPTY;
        }
        return drain(fluidStack.getAmount(), simulate);
    }

    public FluidStack drain(int maxDrain, boolean simulate) {
        int drained = Math.min(maxDrain, this.totalFluid);
        Fluid fluid = this.fluid;
        if (drained <= 0) {
            return FluidStack.EMPTY;
        }
        if (!simulate) {
            this.totalFluid -= drained;
            if (this.totalFluid == 0) {
                this.fluid = Fluids.EMPTY;
            }
        }
        return new FluidStack(fluid, drained);
    }
}