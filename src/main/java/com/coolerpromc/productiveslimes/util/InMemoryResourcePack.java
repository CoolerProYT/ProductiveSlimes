package com.coolerpromc.productiveslimes.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Predicate;

public class InMemoryResourcePack implements PackResources {
    private final Map<String, byte[]> resources;

    public InMemoryResourcePack(Map<String, byte[]> resources) {
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
    public InputStream getResource(PackType packType, ResourceLocation location) throws IOException {
        String path = packType.getDirectory() + "/" + location.getNamespace() + "/" + location.getPath();
        byte[] data = resources.get(path);
        if (data != null) {
            return new ByteArrayInputStream(data);
        }
        return null;
    }

    @Override
    public Collection<ResourceLocation> getResources(PackType packType, String namespace, String path, int i, Predicate<String> filter) {
        Set<ResourceLocation> matchingResources = new HashSet<>();
        String prefix = packType.getDirectory() + "/" + namespace + "/" + path;

        resources.forEach((key, data) -> {
            if (key.startsWith(prefix)) {
                String resourcePath = key.substring((packType.getDirectory() + "/" + namespace + "/").length());

                ResourceLocation resourceLocation = new ResourceLocation(namespace, resourcePath);

                if (filter.test(resourcePath)) {
                    matchingResources.add(resourceLocation);
                }
            }
        });

        return matchingResources;
    }

    @Override
    public boolean hasResource(PackType packType, ResourceLocation resourceLocation) {
        String fullPath = packType.getDirectory() + "/" + resourceLocation.getNamespace() + "/" + resourceLocation.getPath();
        return resources.containsKey(fullPath);
    }


    @Override
    public Set<String> getNamespaces(PackType type) {
        Set<String> namespaces = new HashSet<>();
        String prefix = type.getDirectory() + "/";
        resources.keySet().forEach(key -> {
            if (key.startsWith(prefix)) {
                String[] parts = key.substring(prefix.length()).split("/", 2);
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
    public <T> T getMetadataSection(MetadataSectionSerializer<T> serializer) throws IOException {
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
        return "productiveslimes_resourcepack";
    }

    @Override
    public boolean isHidden() {
        return true;
    }
}
