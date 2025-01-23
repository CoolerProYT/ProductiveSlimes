package com.coolerpromc.productiveslimes.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Predicate;

public class InMemoryResourcePack implements IResourcePack {
    private final Map<String, byte[]> resources;

    public InMemoryResourcePack(Map<String, byte[]> resources) {
        this.resources = resources;
    }


    @Override
    public InputStream getRootResource(String s) throws IOException {
        byte[] data = resources.get(s);
        if (data != null) {
            return new ByteArrayInputStream(data);
        }
        return null;
    }

    @Override
    public InputStream getResource(ResourcePackType resourcePackType, ResourceLocation resourceLocation) throws IOException {
        String path = resourcePackType.getDirectory() + "/" + resourceLocation.getNamespace() + "/" + resourceLocation.getPath();
        byte[] data = resources.get(path);
        if (data != null) {
            return new ByteArrayInputStream(data);
        }
        return null;
    }

    @Override
    public Collection<ResourceLocation> getResources(ResourcePackType resourcePackType, String s, String s1, int i, Predicate<String> predicate) {
        Set<ResourceLocation> matchingResources = new HashSet<>();
        String prefix = resourcePackType.getDirectory() + "/" + s + "/" + s1;

        resources.forEach((key, data) -> {
            if (key.startsWith(prefix)) {
                String resourcePath = key.substring((resourcePackType.getDirectory() + "/" + s + "/").length());

                ResourceLocation resourceLocation = new ResourceLocation(s, resourcePath);

                if (predicate.test(resourcePath)) {
                    matchingResources.add(resourceLocation);
                }
            }
        });

        return matchingResources;
    }

    @Override
    public boolean hasResource(ResourcePackType resourcePackType, ResourceLocation resourceLocation) {
        String fullPath = resourcePackType.getDirectory() + "/" + resourceLocation.getNamespace() + "/" + resourceLocation.getPath();
        return resources.containsKey(fullPath);
    }

    @Override
    public Set<String> getNamespaces(ResourcePackType resourcePackType) {
        Set<String> namespaces = new HashSet<>();
        String prefix = resourcePackType.getDirectory() + "/";
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

    }

    @Nullable
    @Override
    public <T> T getMetadataSection(IMetadataSectionSerializer<T> iMetadataSectionSerializer) throws IOException {
        if ("pack".equals(iMetadataSectionSerializer.getMetadataSectionName())) {
            InputStream supplier = getRootResource("pack.mcmeta");
            if (supplier != null) {
                try (InputStream stream = supplier) {
                    JsonObject json = new Gson().fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8), JsonObject.class);
                    return iMetadataSectionSerializer.fromJson(json.getAsJsonObject("pack"));
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
