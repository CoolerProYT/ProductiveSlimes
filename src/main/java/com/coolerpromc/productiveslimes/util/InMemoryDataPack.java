package com.coolerpromc.productiveslimes.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Predicate;

public class InMemoryDataPack implements IResourcePack {
    private final Map<String, byte[]> resources;

    public InMemoryDataPack(Map<String, byte[]> resources) {
        this.resources = resources;
    }

    @Nullable
    @Override
    public InputStream getRootResource(String s) throws IOException {
        byte[] data = resources.get(s);
        if (data != null) {
            return new ByteArrayInputStream(data);
        }
        return null;
    }

    @Override
    public InputStream getResource(ResourcePackType packType, ResourceLocation resourceLocation) throws IOException {
        if (packType != ResourcePackType.SERVER_DATA) {
            return null;
        }
        String path = "data/" + resourceLocation.getNamespace() + "/" + resourceLocation.getPath();
        byte[] data = resources.get(path);
        if (data != null) {
            return new ByteArrayInputStream(data);
        }
        return null;
    }

    @Override
    public Collection<ResourceLocation> getResources(ResourcePackType packType, String namespace, String path, int i, Predicate<String> filter) {
        Set<ResourceLocation> matchingResources = new HashSet<>();
        String prefix = "data/" + namespace + "/" + path;

        resources.forEach((key, data) -> {
            if (key.startsWith(prefix)) {
                String resourcePath = key.substring(("data/" + namespace + "/").length());

                ResourceLocation resourceLocation = new ResourceLocation(namespace, resourcePath);

                if (filter.test(resourcePath)) {
                    matchingResources.add(resourceLocation);
                }
            }
        });

        return matchingResources;
    }

    @Override
    public boolean hasResource(ResourcePackType packType, ResourceLocation resourceLocation) {
        String fullPath = "data/" + resourceLocation.getNamespace() + "/" + resourceLocation.getPath();
        return resources.containsKey(fullPath);
    }

    @Override
    public Set<String> getNamespaces(ResourcePackType type) {
        if (type != ResourcePackType.SERVER_DATA) {
            return Collections.emptySet();
        }
        Set<String> namespaces = new HashSet<>();
        resources.keySet().forEach(key -> {
            if (key.startsWith("data/")) {
                String[] parts = key.substring("data/".length()).split("/", 4);
                if (parts.length > 1) {
                    namespaces.add(parts[0]);
                }
            }
        });
        return namespaces;
    }

    @Override
    public void close() {
        // Nothing to close
    }

    @Override
    public <T> T getMetadataSection(IMetadataSectionSerializer<T> serializer) throws IOException {
        if ("pack".equals(serializer.getMetadataSectionName())) {
            InputStream supplier = getRootResource("pack.mcmeta");
            if (supplier != null) {
                try (InputStream stream = supplier) {
                    JsonObject json = new Gson().fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8), JsonObject.class);
                    return serializer.fromJson(json.getAsJsonObject("pack"));
                }
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return "productiveslimes_datapack";
    }

    @Override
    public boolean isHidden() {
        return true;
    }
}
