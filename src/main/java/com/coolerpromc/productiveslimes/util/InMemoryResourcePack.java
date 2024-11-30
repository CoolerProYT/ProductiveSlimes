package com.coolerpromc.productiveslimes.util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.IoSupplier;
import org.jetbrains.annotations.Nullable;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class InMemoryResourcePack implements PackResources {
    private final Map<String, byte[]> resources;
    public InMemoryResourcePack(Map<String, byte[]> resources) {
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
    public IoSupplier<InputStream> getResource(PackType packType, ResourceLocation location) {
        String path = packType.getDirectory() + "/" + location.getNamespace() + "/" + location.getPath();
        byte[] data = resources.get(path);
        if (data != null) {
            return () -> new ByteArrayInputStream(data);
        }
        return null;
    }
    @Override
    public void listResources(PackType packType, String namespace, String path, ResourceOutput resourceOutput) {
        String prefix = packType.getDirectory() + "/" + namespace + "/" + path;
        resources.forEach((key, data) -> {
            if (key.startsWith(prefix)) {
                String resourcePath = key.substring((packType.getDirectory() + "/" + namespace + "/").length());
                ResourceLocation location = ResourceLocation.fromNamespaceAndPath(namespace, resourcePath);
                resourceOutput.accept(location, () -> new ByteArrayInputStream(data));
            }
        });
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
            IoSupplier<InputStream> supplier = getRootResource("pack.mcmeta");
            if (supplier != null) {
                try (InputStream stream = supplier.get()) {
                    JsonObject json = new Gson().fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8), JsonObject.class);
                    return serializer.fromJson(json.getAsJsonObject("pack"));
                }
            }
        }
        return null;
    }
    @Override
    public PackLocationInfo location() {
        return new PackLocationInfo("productiveslimes", Component.literal("In Memory Pack"),
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
    public boolean isHidden() {
        return true;
    }
}