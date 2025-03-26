package com.coolerpromc.productiveslimes.networking;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ModNetworkState extends SavedData {
    private final Map<Integer, CableNetwork> networks = new HashMap<>();
    private int nextId = 1;
    public static final SavedDataType<ModNetworkState> MY_TYPE =
            new SavedDataType<>(
                    "productiveslimes_cable_networks",
                    ModNetworkState::new,
                    ctx -> RecordCodecBuilder.create(instance -> instance.group(
                                    Codec.list(
                                            RecordCodecBuilder.<Map.Entry<Integer, CableNetwork>>create(entryInstance ->
                                                    entryInstance.group(
                                                            Codec.INT.fieldOf("NetId").forGetter(Map.Entry::getKey),
                                                            CableNetwork.CODEC.fieldOf("CableNetwork").forGetter(Map.Entry::getValue)
                                                    ).apply(entryInstance, AbstractMap.SimpleEntry::new)
                                            )
                                    ).xmap(
                                            entries -> entries.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)),
                                            map -> new ArrayList<>(map.entrySet())
                                    ).fieldOf("Networks").forGetter(ModNetworkState::getAllNetworks),
                                    Codec.INT.fieldOf("NextId").forGetter(ModNetworkState::getNextId)
                            ).apply(instance, ModNetworkState::new)
                    ),
                    DataFixTypes.LEVEL
            );

    public ModNetworkState() {
        super();
    }

    public ModNetworkState(Map<Integer, CableNetwork> networks, int nextId) {
        this.networks.putAll(networks);
        this.nextId = nextId;
    }

    public ModNetworkState(Context context) {
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
}