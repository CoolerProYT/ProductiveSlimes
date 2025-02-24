package com.coolerpromc.productiveslimes.networking.cable;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class ModCableNetworkStateManager {
    private static final String KEY = "productiveslimes_cable_networks";

    public static ModCableNetworkState getOrCreate(ServerLevel world) {
        DimensionDataStorage manager = world.getDataStorage();
        ModCableNetworkState existing = manager.get(
                ModCableNetworkState.MY_TYPE,
                KEY
        );

        if (existing == null) {
            existing = new ModCableNetworkState();
            manager.set(KEY, existing);
        }

        return existing;
    }

    public static void markDirty(ServerLevel world) {
        ModCableNetworkState state = getOrCreate(world);
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
        ModCableNetworkState state = getOrCreate(world);
        // Clear the manager's current map if you prefer a fresh load
        // ModNetworkManager.clear(); // <-- optionally clear your static map

        for (CableNetwork net : state.getAllNetworks().values()) {
            ModCableNetworkManager.addExistingNetwork(world, net);
        }
    }
}