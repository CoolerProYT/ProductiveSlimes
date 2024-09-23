package com.coolerpromc.productiveslimes.handler;

import com.coolerpromc.productiveslimes.block.entity.CableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.*;

public class EnergyNetwork implements IEnergyStorage {
    private final Set<CableBlockEntity> cables = new HashSet<>();
    private CableBlockEntity primaryCable;
    private int energyStored = 0;
    private int maxEnergyStored = 0;
    // Add a cable to the network
    public void addCable(CableBlockEntity cable) {
        if (cables.add(cable)) {
            maxEnergyStored += cable.getCapacity();
            cable.setNetwork(this);
            if (primaryCable == null || cable.getBlockPos().compareTo(primaryCable.getBlockPos()) < 0) {
                primaryCable = cable;
            }
        }
    }
    // Get the primary cable
    public CableBlockEntity getPrimaryCable() {
        return primaryCable;
    }
    // Update the primary cable if necessary
    private void updatePrimaryCable() {
        primaryCable = cables.stream()
                .min(Comparator.comparing(BlockEntity::getBlockPos))
                .orElse(null);
    }
    // Remove a cable from the network
    public void removeCable(CableBlockEntity cable) {
        if (cables.remove(cable)) {
            maxEnergyStored -= cable.getCapacity();
            energyStored = Math.min(energyStored, maxEnergyStored);
            if (cables.isEmpty()) {
                // Invalidate the network
                invalidate();
            } else {
                if (cable.equals(primaryCable)) {
                    updatePrimaryCable();
                }
                // Check for network splits
                splitNetwork(cable);
            }
            cable.setNetwork(null);
        }
    }
    private void splitNetwork(CableBlockEntity removedCable) {
        // Create new networks starting from the neighboring cables
        List<Set<CableBlockEntity>> subNetworks = new ArrayList<>();
        Set<CableBlockEntity> visited = new HashSet<>();
        for (CableBlockEntity cable : cables) {
            if (!visited.contains(cable)) {
                Set<CableBlockEntity> subNetworkCables = new HashSet<>();
                exploreNetwork(cable, subNetworkCables, visited);
                subNetworks.add(subNetworkCables);
            }
        }
        if (subNetworks.size() > 1) {
            // Split the energy among the new networks
            int totalCables = cables.size() + 1; // +1 for the removed cable
            int energyPerCable = energyStored / totalCables;
            for (Set<CableBlockEntity> subNetworkCables : subNetworks) {
                EnergyNetwork newNetwork = new EnergyNetwork();
                int newNetworkCapacity = 0;
                for (CableBlockEntity cable : subNetworkCables) {
                    newNetwork.addCable(cable);
                    cable.setNetwork(newNetwork);
                    newNetworkCapacity += cable.getCapacity();
                }
                newNetwork.setEnergyStored(energyPerCable * subNetworkCables.size());
            }
            // Invalidate the old network
            invalidate();
        }
    }
    private void exploreNetwork(CableBlockEntity cable, Set<CableBlockEntity> subNetworkCables, Set<CableBlockEntity> visited) {
        visited.add(cable);
        subNetworkCables.add(cable);
        for (Direction direction : Direction.values()) {
            BlockEntity neighborBE = cable.getLevel().getBlockEntity(cable.getBlockPos().relative(direction));
            if (neighborBE instanceof CableBlockEntity neighborCable) {
                if (cables.contains(neighborCable) && !visited.contains(neighborCable)) {
                    exploreNetwork(neighborCable, subNetworkCables, visited);
                }
            }
        }
    }
    // IEnergyStorage methods
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int energyReceived = Math.min(maxReceive, maxEnergyStored - energyStored);
        if (!simulate) {
            energyStored += energyReceived;
        }
        return energyReceived;
    }
    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int energyExtracted = Math.min(maxExtract, energyStored);
        if (!simulate) {
            energyStored -= energyExtracted;
        }
        return energyExtracted;
    }
    @Override
    public int getEnergyStored() {
        return energyStored;
    }
    @Override
    public int getMaxEnergyStored() {
        return maxEnergyStored;
    }
    @Override
    public boolean canExtract() {
        return true;
    }
    @Override
    public boolean canReceive() {
        return true;
    }
    public void setEnergyStored(int energyStored) {
        this.energyStored = energyStored;
    }
    // Merge this network with another
    public void merge(EnergyNetwork other) {
        if (other == this) return;
        energyStored += other.energyStored;
        maxEnergyStored += other.maxEnergyStored;
        for (CableBlockEntity cable : other.cables) {
            cables.add(cable);
            cable.setNetwork(this);
        }
        other.invalidate();
    }
    private void invalidate() {
        cables.clear();
        energyStored = 0;
        maxEnergyStored = 0;
    }
    public void distributeEnergy(Level level) {
        for (CableBlockEntity cable : cables) {
            BlockPos pos = cable.getBlockPos();
            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = pos.relative(direction);
                IEnergyStorage neighborEnergy = level.getCapability(Capabilities.EnergyStorage.BLOCK, neighborPos, direction.getOpposite());
                if (neighborEnergy != null && neighborEnergy != this && neighborEnergy.canReceive()) {
                    int energyAvailable = this.extractEnergy(cable.getTransferRate(), true);
                    int energyReceived = neighborEnergy.receiveEnergy(energyAvailable, true);
                    int transferAmount = Math.min(energyAvailable, energyReceived);
                    if (transferAmount > 0) {
                        this.extractEnergy(transferAmount, false);
                        neighborEnergy.receiveEnergy(transferAmount, false);
                    }
                }
            }
        }
    }
    public void collectEnergy(Level level) {
        for (CableBlockEntity cable : cables) {
            BlockPos pos = cable.getBlockPos();
            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = pos.relative(direction);
                IEnergyStorage neighborEnergy = level.getCapability(Capabilities.EnergyStorage.BLOCK, neighborPos, direction.getOpposite());
                if (neighborEnergy != null && neighborEnergy != this && neighborEnergy.canExtract()) {
                    int energyNeeded = this.receiveEnergy(cable.getTransferRate(), true);
                    int energyExtracted = neighborEnergy.extractEnergy(energyNeeded, true);
                    int transferAmount = Math.min(energyNeeded, energyExtracted);
                    if (transferAmount > 0) {
                        neighborEnergy.extractEnergy(transferAmount, false);
                        this.receiveEnergy(transferAmount, false);
                    }
                }
            }
        }
    }
}