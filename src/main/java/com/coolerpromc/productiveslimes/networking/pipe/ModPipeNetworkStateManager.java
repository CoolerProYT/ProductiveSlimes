package com.coolerpromc.productiveslimes.networking.pipe;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class ModPipeNetworkStateManager {
    private static final String KEY = "productiveslimes_pipe_networks";

    public static ModPipeNetworkState getOrCreate(ServerLevel world) {
        DimensionDataStorage manager = world.getDataStorage();
        ModPipeNetworkState existing = manager.get(
                ModPipeNetworkState.MY_TYPE,
                KEY
        );

        if (existing == null) {
            existing = new ModPipeNetworkState();
            manager.set(KEY, existing);
        }

        return existing;
    }

    public static void markDirty(ServerLevel world) {
        ModPipeNetworkState state = getOrCreate(world);
        state.setDirty(true);
    }

    public static void forceSave(ServerLevel world) {
        world.getDataStorage().saveAndJoin();
    }

    /**
     * Call this once (e.g. on server/world load) to re-register all stored networks
     * into ModNetworkManager’s in-memory map.
     */
    public static void loadAllNetworksToManager(ServerLevel world) {
        ModPipeNetworkState state = getOrCreate(world);

        for (PipeNetwork net : state.getAllNetworks().values()) {
            ModPipeNetworkManager.addExistingNetwork(world, net);
        }
    }
}