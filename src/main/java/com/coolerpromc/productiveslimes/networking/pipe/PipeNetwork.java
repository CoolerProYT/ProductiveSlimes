package com.coolerpromc.productiveslimes.networking.pipe;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
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

    public PipeNetwork() {}

    private PipeNetwork(int networkId, int totalFluid, int totalCapacity, List<BlockPos> cablePositions) {
        this.networkId = networkId;
        this.totalFluid = totalFluid;
        this.totalCapacity = totalCapacity;
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

    public static CompoundTag writeToNbt(PipeNetwork net, CompoundTag nbt) {
        nbt.putInt("NetworkId", net.networkId);

        nbt.putInt("totalFluid", net.totalFluid);
        nbt.putString("Fluid", BuiltInRegistries.FLUID.getKey(net.fluid).toString());
        nbt.putInt("TotalCapacity", net.totalCapacity);

        ListTag posList = new ListTag();
        for (BlockPos pos : net.cablePositions) {
            CompoundTag posTag = new CompoundTag();
            posTag.putInt("x", pos.getX());
            posTag.putInt("y", pos.getY());
            posTag.putInt("z", pos.getZ());
            posList.add(posTag);
        }
        nbt.put("Positions", posList);

        return nbt;
    }

    public static PipeNetwork readFromNbt(CompoundTag nbt) {
        PipeNetwork net = new PipeNetwork();

        if (nbt.contains("NetworkId")) {
            net.networkId = nbt.getInt("NetworkId");
        }

        net.totalFluid = nbt.getInt("totalFluid");
        net.fluid = BuiltInRegistries.FLUID.getValue(ResourceLocation.tryParse(nbt.getString("Fluid")));
        net.totalCapacity = nbt.getInt("TotalCapacity");

        if (nbt.contains("Positions", Tag.TAG_LIST)) {
            ListTag list = nbt.getList("Positions", Tag.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++) {
                CompoundTag posTag = list.getCompound(i);
                int x = posTag.getInt("x");
                int y = posTag.getInt("y");
                int z = posTag.getInt("z");
                net.cablePositions.add(new BlockPos(x, y, z));
            }
        }
        return net;
    }
}