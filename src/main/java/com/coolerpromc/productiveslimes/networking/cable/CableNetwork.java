package com.coolerpromc.productiveslimes.networking.cable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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

    public static final Codec<BlockPos> BLOCK_POS_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("x").forGetter(BlockPos::getX),
            Codec.INT.fieldOf("y").forGetter(BlockPos::getY),
            Codec.INT.fieldOf("z").forGetter(BlockPos::getZ)
    ).apply(instance, BlockPos::new));

    public static final Codec<CableNetwork> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("NetworkId", -1).forGetter(net -> net.networkId),
            Codec.INT.fieldOf("TotalEnergy").forGetter(net -> net.totalEnergy),
            Codec.INT.fieldOf("TotalCapacity").forGetter(net -> net.totalCapacity),
            BLOCK_POS_CODEC.listOf().fieldOf("Positions").forGetter(net -> net.cablePositions.stream().toList())
    ).apply(instance, CableNetwork::new));

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
}