package com.coolerpromc.productiveslimes.networking.cable;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CableNetwork {
    private int networkId = -1;
    private int totalEnergy = 0;
    private int totalCapacity = 0;

    private final Set<BlockPos> cablePositions = new HashSet<>();

    public CableNetwork() {}

    private CableNetwork(int networkId, int totalEnergy, int totalCapacity, List<BlockPos> cablePositions) {
        this.networkId = networkId;
        this.totalEnergy = totalEnergy;
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
            if (totalEnergy > totalCapacity) {
                totalEnergy = totalCapacity;
            }
        }
    }

    public void removeCable(BlockPos pos, int cableCapacity) {
        if (cablePositions.remove(pos)) {
            totalCapacity -= cableCapacity;
            if (totalCapacity < 0) totalCapacity = 0;
            if (totalEnergy > totalCapacity) totalEnergy = totalCapacity;
        }
    }

    public Set<BlockPos> getCablePositions() {
        return cablePositions;
    }

    public int getTotalEnergy() {
        return totalEnergy;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalEnergy(int newAmount) {
        this.totalEnergy = Math.min(newAmount, totalCapacity);
    }

    public int insertEnergy(int amount, boolean simulate) {
        int space = totalCapacity - totalEnergy;
        int accepted = Math.min(space, amount);
        if (!simulate) {
            totalEnergy += accepted;
        }
        return accepted;
    }

    public int extractEnergy(int amount, boolean simulate) {
        int extracted = Math.min(totalEnergy, amount);
        if (!simulate) {
            totalEnergy -= extracted;
        }
        return extracted;
    }

    public static CompoundTag writeToNbt(CableNetwork net, CompoundTag nbt) {
        nbt.putInt("NetworkId", net.networkId);

        nbt.putInt("TotalEnergy", net.totalEnergy);
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

    public static CableNetwork readFromNbt(CompoundTag nbt) {
        CableNetwork net = new CableNetwork();

        if (nbt.contains("NetworkId")) {
            net.networkId = nbt.getInt("NetworkId");
        }

        net.totalEnergy = nbt.getInt("TotalEnergy");
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