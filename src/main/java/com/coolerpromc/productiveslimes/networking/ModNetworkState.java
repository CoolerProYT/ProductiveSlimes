package com.coolerpromc.productiveslimes.networking;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;

public class ModNetworkState extends SavedData {
    private final Map<Integer, CableNetwork> networks = new HashMap<>();
    private int nextId = 1;

    public static final SavedData.Factory<ModNetworkState> MY_TYPE =
            new SavedData.Factory<>(
                    ModNetworkState::new,
                    (nbt, registry) -> {
                        ModNetworkState state = new ModNetworkState();
                        state.readNbt(nbt, registry);
                        return state;
                    },
                    DataFixTypes.LEVEL
            );

    public ModNetworkState() {
        super();
    }

    public int getNextId() {
        return nextId;
    }

    public CableNetwork getNetwork(int netId) {
        return networks.get(netId);
    }

    public int createNetwork() {
        int id = nextId++;
        CableNetwork net = new CableNetwork();
        net.setNetworkId(id);
        networks.put(id, net);
        this.setDirty(true);
        return id;
    }

    public void removeNetwork(int netId) {
        networks.remove(netId);
        this.setDirty(true);
    }

    public Map<Integer, CableNetwork> getAllNetworks() {
        return networks;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        ListTag list = new ListTag();
        for (Map.Entry<Integer, CableNetwork> entry : networks.entrySet()) {
            int netId = entry.getKey();
            CableNetwork net = entry.getValue();

            CompoundTag netTag = new CompoundTag();
            netTag.putInt("NetId", netId);
            netTag.put("CableNetwork", CableNetwork.writeToNbt(net, new CompoundTag()));
            list.add(netTag);
        }
        tag.put("Networks", list);

        tag.putInt("NextId", this.nextId);
        return tag;
    }

    protected void readNbt(CompoundTag nbt, HolderLookup.Provider registry) {
        networks.clear();

        if (nbt.contains("Networks", Tag.TAG_LIST)) {
            ListTag list = nbt.getList("Networks", Tag.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++) {
                CompoundTag netTag = list.getCompound(i);
                int netId = netTag.getInt("NetId");
                CableNetwork net = CableNetwork.readFromNbt(netTag.getCompound("CableNetwork"));

                // Make sure the CableNetwork’s own ID is set:
                net.setNetworkId(netId);

                networks.put(netId, net);
                if (netId >= nextId) {
                    nextId = netId + 1;
                }
            }
        }

        this.nextId = Math.max(this.nextId, nbt.getInt("NextId"));
    }
}