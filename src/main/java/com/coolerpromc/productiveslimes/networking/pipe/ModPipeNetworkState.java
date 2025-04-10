package com.coolerpromc.productiveslimes.networking.pipe;

import com.coolerpromc.productiveslimes.networking.cable.ModCableNetworkState;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ModPipeNetworkState extends SavedData {
    private final Map<Integer, PipeNetwork> networks = new HashMap<>();
    private int nextId = 1;

    public static final SavedDataType<ModPipeNetworkState> MY_TYPE =
            new SavedDataType<>(
                    "productiveslimes_pipe_networks",
                    ModPipeNetworkState::new,
                    ctx -> RecordCodecBuilder.create(instance -> instance.group(
                                    Codec.list(
                                            RecordCodecBuilder.<Map.Entry<Integer, PipeNetwork>>create(entryInstance ->
                                                    entryInstance.group(
                                                            Codec.INT.fieldOf("NetId").forGetter(Map.Entry::getKey),
                                                            PipeNetwork.CODEC.fieldOf("PipeNetwork").forGetter(Map.Entry::getValue)
                                                    ).apply(entryInstance, AbstractMap.SimpleEntry::new)
                                            )
                                    ).xmap(
                                            entries -> entries.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)),
                                            map -> new ArrayList<>(map.entrySet())
                                    ).fieldOf("Networks").forGetter(ModPipeNetworkState::getAllNetworks),
                                    Codec.INT.fieldOf("NextId").forGetter(ModPipeNetworkState::getNextId)
                            ).apply(instance, ModPipeNetworkState::new)
                    ),
                    DataFixTypes.LEVEL
            );

    public ModPipeNetworkState() {
        super();
    }

    private ModPipeNetworkState(Map<Integer, PipeNetwork> networks, int nextId) {
        this.networks.putAll(networks);
        this.nextId = nextId;
    }

    public ModPipeNetworkState(Context context) {
    }

    public int getNextId() {
        return nextId;
    }

    public PipeNetwork getNetwork(int netId) {
        return networks.get(netId);
    }

    public int createNetwork() {
        int id = nextId++;
        PipeNetwork net = new PipeNetwork();
        net.setNetworkId(id);
        networks.put(id, net);
        this.setDirty(true);
        return id;
    }

    public void removeNetwork(int netId) {
        networks.remove(netId);
        this.setDirty(true);
    }

    public Map<Integer, PipeNetwork> getAllNetworks() {
        return networks;
    }
}