package com.coolerpromc.productiveslimes.util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.IoSupplier;
import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
public class InMemoryDataPack implements PackResources {
    private final Map<String, byte[]> resources;
    public InMemoryDataPack(Map<String, byte[]> resources) {
        this.resources = resources;
    }
    @Nullable
    @Override
    public IoSupplier<InputStream> getRootResource(String... elements) {
        String path = String.join("/", elements);
        byte[] data = resources.get(path);
        if (data != null) {
            return () -> new ByteArrayInputStream(data);
        }
        return null;
    }
    @Nullable
    @Override
    public IoSupplier<InputStream> getResource(PackType packType, Identifier location) {
        if (packType != PackType.SERVER_DATA) {
            return null;
        }
        String path = "data/" + location.getNamespace() + "/" + location.getPath();
        byte[] data = resources.get(path);
        if (data != null) {
            return () -> new ByteArrayInputStream(data);
        }
        return null;
    }
    @Override
    public void listResources(PackType packType, String namespace, String path, ResourceOutput resourceOutput) {
        if (packType != PackType.SERVER_DATA) {
            return;
        }
        String prefix = "data/" + namespace + "/" + path;
        resources.forEach((key, data) -> {
            if (key.startsWith(prefix)) {
                String resourcePath = key.substring(("data/" + namespace + "/").length());
                Identifier location = Identifier.fromNamespaceAndPath(namespace, resourcePath);
                resourceOutput.accept(location, () -> new ByteArrayInputStream(data));
            }
        });
    }
    @Override
    public Set<String> getNamespaces(PackType type) {
        if (type != PackType.SERVER_DATA) {
            return Collections.emptySet();
        }
        Set<String> namespaces = new HashSet<>();
        resources.keySet().forEach(key -> {
            if (key.startsWith("data/")) {
                String[] parts = key.substring("data/".length()).split("/", 2);
                if (parts.length > 1) {
                    namespaces.add(parts[0]);
                }
            }
        });
        return namespaces;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public <T> T getMetadataSection(MetadataSectionType<T> sectionType) throws IOException {
        if ("pack".equals(sectionType.name())) { // Use name() method to get the section name
            IoSupplier<InputStream> supplier = getRootResource("pack.mcmeta");
            if (supplier != null) {
                try (InputStream stream = supplier.get()) {
                    JsonObject json = new Gson().fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8), JsonObject.class);
                    // Use the Codec from the sectionType to deserialize the JSON object
                    return sectionType.codec().parse(JsonOps.INSTANCE, json.getAsJsonObject("pack"))
                            .resultOrPartial(error -> {
                                System.err.println("Failed to parse metadata section: " + error);
                            })
                            .orElse(null);
                }
            }
        }
        return null;
    }


    @Override
    public void close() {
        // Nothing to close
    }

    @Override
    public PackLocationInfo location() {
        return new PackLocationInfo("productiveslimes_datapack", Component.literal("In Memory Pack"),
                new PackSource() {
                    @Override
                    public Component decorate(Component name) {
                        return Component.literal("In Memory Pack");
                    }
                    @Override
                    public boolean shouldAddAutomatically() {
                        return true;
                    }
                }, Optional.empty());
    }

    @Override
    public String packId() {
        return "productiveslimes_datapack";
    }
    @Override
    public boolean isHidden() {
        return true;
    }
}