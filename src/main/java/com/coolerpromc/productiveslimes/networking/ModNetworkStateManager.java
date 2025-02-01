package com.coolerpromc.productiveslimes.networking;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class ModNetworkStateManager {
    private static final String KEY = "productiveslimes_cable_networks";

    public static ModNetworkState getOrCreate(ServerLevel world) {
        DimensionDataStorage manager = world.getDataStorage();
        ModNetworkState existing = manager.get(
                ModNetworkState.MY_TYPE,
                KEY
        );
        if (existing == null) {
            existing = new ModNetworkState();
            manager.set(KEY, existing);
        }
        return existing;
    }

    public static void markDirty(ServerLevel world) {
        ModNetworkState state = getOrCreate(world);
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
        ModNetworkState state = getOrCreate(world);
        // Clear the manager's current map if you prefer a fresh load
        // ModNetworkManager.clear(); // <-- optionally clear your static map
        for (CableNetwork net : state.getAllNetworks().values()) {
            ModNetworkManager.addExistingNetwork(world, net);
        }
    }
}