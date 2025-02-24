package com.coolerpromc.productiveslimes.networking.pipe;

import com.coolerpromc.productiveslimes.block.entity.FluidTankBlockEntity;
import com.coolerpromc.productiveslimes.block.entity.PipeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import java.util.*;

public class ModPipeNetworkManager {
    private static final Map<BlockPos, PipeNetwork> networkByPos = new HashMap<>();

    /**
     * Call this after placing a cable or somehow wanting to unify networks.
     */
    public static void rebuildNetwork(ServerLevel world, BlockPos startPos) {
        if (!(world.getBlockEntity(startPos) instanceof PipeBlockEntity)) {
            return;
        }

        // Determine the base fluid from the starting cable.
        Fluid baseFluid = Fluids.EMPTY;
        PipeNetwork startingNetwork = networkByPos.get(startPos);
        if (startingNetwork != null && startingNetwork.getFluid() != Fluids.EMPTY) {
            baseFluid = startingNetwork.getFluid();
        }

        // BFS to find all connected, fluid-compatible cables.
        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> queue = new LinkedList<>();
        queue.add(startPos);

        List<PipeNetwork> oldNetworks = new ArrayList<>();
        Map<Fluid, Integer> fluidTotals = new HashMap<>(); // Track fluid amounts by type
        int totalOldCapacity = 0;

        while (!queue.isEmpty()) {
            BlockPos currentPos = queue.poll();
            if (!visited.add(currentPos)) continue;

            if (world.getBlockEntity(currentPos) instanceof PipeBlockEntity) {
                PipeNetwork oldNet = networkByPos.get(currentPos);
                Fluid currentFluid = Fluids.EMPTY;
                if (oldNet != null) {
                    currentFluid = oldNet.getFluid();
                }
                // If our network has not yet set a fluid and we encounter a non-empty one,
                // adopt that as the base fluid.
                if (baseFluid == Fluids.EMPTY && currentFluid != Fluids.EMPTY) {
                    baseFluid = currentFluid;
                }
                // If both the base and current cable have fluids and they differ, skip this cable.
                if (baseFluid != Fluids.EMPTY && currentFluid != Fluids.EMPTY && !baseFluid.equals(currentFluid)) {
                    continue;
                }

                // Add information from the old network (if any)
                if (oldNet != null && !oldNetworks.contains(oldNet)) {
                    oldNetworks.add(oldNet);
                    Fluid fluid = oldNet.getFluid();
                    int fluidAmount = oldNet.getTotalFluid();
                    fluidTotals.merge(fluid, fluidAmount, Integer::sum);
                    totalOldCapacity += oldNet.getTotalCapacity();
                }

                // Check all neighboring positions
                for (Direction dir : Direction.values()) {
                    BlockPos neighborPos = currentPos.relative(dir);
                    if (world.getBlockEntity(neighborPos) instanceof PipeBlockEntity) {
                        // Check neighbor’s fluid state.
                        PipeNetwork neighborNet = networkByPos.get(neighborPos);
                        Fluid neighborFluid = Fluids.EMPTY;
                        if (neighborNet != null) {
                            neighborFluid = neighborNet.getFluid();
                        }
                        // If our network is still empty and the neighbor has a fluid, adopt it.
                        if (baseFluid == Fluids.EMPTY && neighborFluid != Fluids.EMPTY) {
                            baseFluid = neighborFluid;
                        }
                        // If both are non-empty and they differ, do not add this neighbor.
                        if (baseFluid != Fluids.EMPTY && neighborFluid != Fluids.EMPTY && !baseFluid.equals(neighborFluid)) {
                            continue;
                        }
                        queue.add(neighborPos);
                    }
                }
            }
        }

        // Use the determined base fluid as the network’s fluid.
        Fluid newFluid = baseFluid;
        int totalFluidStored = 0;
        if (fluidTotals.containsKey(newFluid)) {
            totalFluidStored = fluidTotals.get(newFluid);
        }

        // Clean up old networks: remove all cables we visited and unregister them.
        ModPipeNetworkState state = ModPipeNetworkStateManager.getOrCreate(world);
        for (PipeNetwork oldNet : oldNetworks) {
            for (BlockPos cablePos : visited) {
                oldNet.removeCable(cablePos, PipeBlockEntity.CAPACITY_PER_CABLE);
                networkByPos.remove(cablePos);
            }
            if (oldNet.getCablePositions().isEmpty()) {
                state.removeNetwork(oldNet.getNetworkId());
            }
        }

        // Create the new network from the collected cables.
        int newNetId = state.createNetwork();
        PipeNetwork newNetwork = state.getNetwork(newNetId);

        for (BlockPos cablePos : visited) {
            networkByPos.put(cablePos, newNetwork);
            newNetwork.addCable(cablePos, PipeBlockEntity.CAPACITY_PER_CABLE);
        }

        // Set the fluid and its amount (capped by the network’s capacity).
        int mergedFluid = Math.min(totalFluidStored, newNetwork.getTotalCapacity());
        newNetwork.setFluid(newFluid);
        newNetwork.setTotalFluid(mergedFluid);

        ModPipeNetworkStateManager.markDirty(world);
    }

    /**
     * Call this after removing a cable at removedPos.
     * We then have to split the old network into sub-networks if applicable.
     */
    public static void onCableRemoved(ServerLevel world, BlockPos removedPos) {
        PipeNetwork oldNet = networkByPos.get(removedPos);
        if (oldNet == null) {
            return;
        }

        int oldEnergy = oldNet.getTotalFluid();
        Fluid oldFluid = oldNet.getFluid();
        oldNet.removeCable(removedPos, PipeBlockEntity.CAPACITY_PER_CABLE);
        networkByPos.remove(removedPos);

        ModPipeNetworkState state = ModPipeNetworkStateManager.getOrCreate(world);
        if (oldNet.getCablePositions().isEmpty()) {
            state.removeNetwork(oldNet.getNetworkId());
            ModPipeNetworkStateManager.markDirty(world);
            return;
        }

        List<Set<BlockPos>> newSubnetworksCables = findSubnetworksAfterRemoval(world, oldNet);
        if (newSubnetworksCables.isEmpty()) {
            return;
        }

        // Calculate total capacity of remaining cables
        int totalNewCapacity = 0;
        List<Integer> subCaps = new ArrayList<>();
        for (Set<BlockPos> subSet : newSubnetworksCables) {
            int subCapacity = subSet.size() * PipeBlockEntity.CAPACITY_PER_CABLE;
            subCaps.add(subCapacity);
            totalNewCapacity += subCapacity;
        }

        // Ensure total fluid doesn't exceed new total capacity
        int adjustedOldEnergy = Math.min(oldEnergy, totalNewCapacity);

        // Remove cables from old network
        Set<BlockPos> oldPositions = new HashSet<>(oldNet.getCablePositions());
        for (BlockPos pos : oldPositions) {
            oldNet.removeCable(pos, PipeBlockEntity.CAPACITY_PER_CABLE);
            networkByPos.remove(pos);
        }
        if (oldNet.getCablePositions().isEmpty()) {
            state.removeNetwork(oldNet.getNetworkId());
        }

        // Distribute fluid precisely using integer arithmetic
        List<Integer> subFluids = new ArrayList<>();
        int remainingFluid = adjustedOldEnergy;
        for (int i = 0; i < subCaps.size(); i++) {
            int cap = subCaps.get(i);
            int fluidForThis = (i == subCaps.size() - 1)
                    ? remainingFluid
                    : (int) (((long) cap * adjustedOldEnergy) / totalNewCapacity);
            fluidForThis = Math.min(fluidForThis, cap); // Cap at subnet capacity
            subFluids.add(fluidForThis);
            remainingFluid -= fluidForThis;
        }

        // Create new networks
        for (int i = 0; i < newSubnetworksCables.size(); i++) {
            Set<BlockPos> subSet = newSubnetworksCables.get(i);
            int subCapacity = subCaps.get(i);
            int subFluid = subFluids.get(i);

            int newId = state.createNetwork();
            PipeNetwork newNet = state.getNetwork(newId);

            for (BlockPos cablePos : subSet) {
                newNet.addCable(cablePos, PipeBlockEntity.CAPACITY_PER_CABLE);
                networkByPos.put(cablePos, newNet);
            }
            newNet.setFluid(oldFluid);
            newNet.setTotalFluid(subFluid); // Already capped at capacity
        }

        ModPipeNetworkStateManager.markDirty(world);
    }

    /**
     * BFS among oldNet's positions to see how many disconnected cable clusters exist.
     */
    private static List<Set<BlockPos>> findSubnetworksAfterRemoval(ServerLevel world, PipeNetwork oldNet) {
        List<Set<BlockPos>> results = new ArrayList<>();
        Set<BlockPos> visited = new HashSet<>();

        for (BlockPos start : oldNet.getCablePositions()) {
            if (!visited.contains(start)) {
                Set<BlockPos> subSet = new HashSet<>();
                Queue<BlockPos> queue = new LinkedList<>();
                queue.add(start);

                while (!queue.isEmpty()) {
                    BlockPos current = queue.poll();
                    if (!visited.add(current)) continue;

                    subSet.add(current);

                    for (Direction dir : Direction.values()) {
                        BlockPos neighbor = current.relative(dir);
                        if (oldNet.getCablePositions().contains(neighbor)) {
                            queue.add(neighbor);
                        }
                    }
                }
                results.add(subSet);
            }
        }

        return results;
    }

    public static PipeNetwork getNetwork(BlockPos pos) {
        return networkByPos.get(pos);
    }

    public static void addExistingNetwork(ServerLevel world, PipeNetwork network) {
        for (BlockPos pos : network.getCablePositions()) {
            networkByPos.put(pos, network);
        }
    }

    public static void tickAllNetworks(ServerLevel world) {
        ModPipeNetworkState state = ModPipeNetworkStateManager.getOrCreate(world);

        for (PipeNetwork network : state.getAllNetworks().values()) {
            Set<IFluidHandler> consumers = findAllConsumersForNetwork(world, network);
            if (!consumers.isEmpty() && network.getTotalFluid() > 0) {
                distributeEnergyFairly(network, consumers);
            }
        }
    }

    private static Set<IFluidHandler> findAllConsumersForNetwork(ServerLevel world, PipeNetwork network) {
        Set<IFluidHandler> consumers = new HashSet<>();

        for (BlockPos cablePos : network.getCablePositions()) {
            for (Direction dir : Direction.values()) {
                BlockPos neighborPos = cablePos.relative(dir);
                if (neighborPos.equals(cablePos.above())){
                    if (world.getBlockEntity(neighborPos) instanceof FluidTankBlockEntity) {
                        continue;
                    }
                }
                if (world.getBlockEntity(neighborPos) instanceof PipeBlockEntity) {
                    continue;
                }

                IFluidHandler maybeStorage = world.getCapability(Capabilities.FluidHandler.BLOCK, neighborPos, dir.getOpposite());
                if (maybeStorage != null) {
                    consumers.add(maybeStorage);
                }
            }
        }
        return consumers;
    }

    private static void distributeEnergyFairly(PipeNetwork net, Set<IFluidHandler> consumers) {
        FluidStack fluidToSend = net.getFluidStack();
        if (fluidToSend.isEmpty()) return;

        int maxDrainPerTick = 1000;
        int availableToSend = Math.min(fluidToSend.getAmount(), maxDrainPerTick);
        int totalAccepted = 0;

        Map<IFluidHandler, Integer> acceptanceMap = new HashMap<>();
        int totalPossibleAcceptance = 0;

        for (IFluidHandler handler : consumers) {
            if (!handler.isFluidValid(0, fluidToSend)) continue;

            int simulatedAccept = handler.fill(
                    new FluidStack(fluidToSend.getFluid(), availableToSend),
                    IFluidHandler.FluidAction.SIMULATE
            );

            if (simulatedAccept > 0) {
                acceptanceMap.put(handler, simulatedAccept);
                totalPossibleAcceptance += simulatedAccept;
            }
        }

        if (totalPossibleAcceptance == 0) return;

        int amountToDistribute = Math.min(availableToSend, totalPossibleAcceptance);
        for (Map.Entry<IFluidHandler, Integer> entry : acceptanceMap.entrySet()) {
            IFluidHandler handler = entry.getKey();
            int fairShare = (int) Math.min(
                    entry.getValue(),
                    (long) entry.getValue() * amountToDistribute / totalPossibleAcceptance
            );

            FluidStack fluidStack = new FluidStack(fluidToSend.getFluid(), fairShare);
            int actuallyAccepted = handler.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
            totalAccepted += actuallyAccepted;
        }

        if (totalAccepted > 0) {
            net.setTotalFluid(net.getTotalFluid() - totalAccepted);
            if (net.getTotalFluid() <= 0) {
                net.setFluid(Fluids.EMPTY);
            }
        }
    }
}