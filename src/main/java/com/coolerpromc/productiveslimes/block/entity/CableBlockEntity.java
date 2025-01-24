package com.coolerpromc.productiveslimes.block.entity;

import com.coolerpromc.productiveslimes.handler.EnergyNetwork;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.HashSet;
import java.util.Set;

public class CableBlockEntity extends TileEntity implements IEnergyStorage, ITickableTileEntity {
    private int energyStoredToLoad = -1;
    private EnergyNetwork network;
    private final int capacity = 10000; // Example capacity
    private final int transferRate = 500; // Energy transfer rate per tick

    private final LazyOptional<IEnergyStorage> storageLazyOptional = LazyOptional.of(() -> this);

    public CableBlockEntity() {
        super(ModBlockEntities.CABLE_BE.get());
    }

    public void setNetwork(EnergyNetwork network) {
        this.network = network;
    }

    public EnergyNetwork getNetwork() {
        return network;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getTransferRate() {
        return transferRate;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return network != null ? network.receiveEnergy(maxReceive, simulate) : 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return network != null ? network.extractEnergy(maxExtract, simulate) : 0;
    }

    @Override
    public int getEnergyStored() {
        return network != null ? network.getEnergyStored() : 0;
    }

    @Override
    public int getMaxEnergyStored() {
        return network != null ? network.getMaxEnergyStored() : capacity;
    }

    @Override
    public boolean canExtract() {
        return network != null && network.canExtract();
    }

    @Override
    public boolean canReceive() {
        return network != null && network.canReceive();
    }

    // Initialize or join a network when the block entity is loaded
    @Override
    public void onLoad() {
        super.onLoad();
        if (!level.isClientSide) {
            initializeNetwork();
            if (energyStoredToLoad >= 0 && network != null) {
                if (isPrimaryCable()) {
                    network.setEnergyStored(energyStoredToLoad);
                }
                energyStoredToLoad = -1; // Reset the temporary value
            }
        }
    }

    private boolean isPrimaryCable() {
        // For example, the cable with the lowest position
        return network != null && this.equals(network.getPrimaryCable());
    }

    // Clean up when the block entity is removed
    @Override
    public void setRemoved() {
        super.setRemoved();
        if (!level.isClientSide && network != null) {
            network.removeCable(this);
            network = null;
        }
    }

    private void initializeNetwork() {
        if (network != null) {
            return; // Already initialized
        }
        Set<EnergyNetwork> adjacentNetworks = new HashSet<>();
        for (Direction direction : Direction.values()) {
            TileEntity neighborBE = level.getBlockEntity(worldPosition.relative(direction));
            if (neighborBE instanceof CableBlockEntity) {
                CableBlockEntity neighborCable = (CableBlockEntity) neighborBE;
                if (neighborCable.network != null) {
                    adjacentNetworks.add(neighborCable.network);
                }
            }
        }
        if (adjacentNetworks.isEmpty()) {
            network = new EnergyNetwork();
            network.addCable(this);
        } else {
            // Merge all adjacent networks
            network = adjacentNetworks.iterator().next();
            network.addCable(this);
            for (EnergyNetwork adjNetwork : adjacentNetworks) {
                if (adjNetwork != network) {
                    network.merge(adjNetwork);
                }
            }
        }
    }

    @Override
    public void tick() {
        if (!level.isClientSide && this.network != null) {
            this.network.collectEnergy(level);
            this.network.distributeEnergy(level);
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT pTag) {
        int energyStored = network != null ? network.getEnergyStored() : 0;
        pTag.putInt("EnergyStored", energyStored);
        return super.save(pTag);
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT pTag) {
        super.load(p_230337_1_, pTag);
        energyStoredToLoad = pTag.getInt("EnergyStored");
    }

    public void onRemoved() {
        if (network != null) {
            network.removeCable(this);
            network = null;
        }
    }

    public void reinitializeNetwork() {
        if (!level.isClientSide) {
            if (network != null) {
                network.removeCable(this);
                network = null;
            }
            initializeNetwork();
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == CapabilityEnergy.ENERGY) {
            return this.storageLazyOptional.cast();
        }

        return super.getCapability(cap);
    }

    @Override
    protected void invalidateCaps() {
        super.invalidateCaps();
        storageLazyOptional.invalidate();
    }
}