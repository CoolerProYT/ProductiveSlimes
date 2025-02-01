package com.coolerpromc.productiveslimes.networking;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;

public class ModNetworkState extends SavedData {
    private final Map<Integer, CableNetwork> networks = new HashMap<>();
    private int nextId = 1;

    public ModNetworkState() {
        super();
    }

    private ModNetworkState(Map<Integer, CableNetwork> networks, int nextId) {
        networks.clear();
        this.networks.putAll(networks);
        this.nextId = nextId;
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
    public CompoundTag save(CompoundTag tag) {
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

    protected static ModNetworkState readNbt(CompoundTag nbt) {
        Map<Integer, CableNetwork> networks = new HashMap<>();
        int nextId = -1;

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
        nextId = Math.max(nextId, nbt.getInt("NextId"));

        return new ModNetworkState(networks, nextId);
    }
}