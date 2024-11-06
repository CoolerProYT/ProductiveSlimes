package com.coolerpromc.productiveslimes.config;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.item.custom.SlimeballItem;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class CustomContentRegistry {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String CONFIG_PATH = "config/productiveslimes/variants.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static List<CustomVariants> loadedVariants = new ArrayList<>();
    private static Map<ResourceLocation, DeferredItem<Item>> registeredItems = new HashMap<>();

    public static void initialize(DeferredRegister.Items item) {
        createDefaultConfig();
        loadVariants(item);
    }

    public static List<CustomVariants> getLoadedTiers() {
        return loadedVariants;
    }

    public static DeferredItem<Item> getSlimeballItemForVariant(String variantName){
        return registeredItems.get(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, variantName + "_slimeball"));
    }

    private static void createDefaultConfig() {
        File configFile = new File(CONFIG_PATH);
        if (!configFile.exists()) {
            List<CustomVariants> defaultTiers = Arrays.asList();

            try {
                // Ensure the config directory exists
                configFile.getParentFile().mkdirs();

                // Write the default config
                try (FileWriter writer = new FileWriter(configFile)) {
                    GSON.toJson(defaultTiers, writer);
                }
            } catch (IOException e) {
                LOGGER.error("Failed to create default tier config", e);
            }
        }
    }

    private static void loadVariants(DeferredRegister.Items ITEMS) {
        File configFile = new File(CONFIG_PATH);
        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                Type listType = new TypeToken<List<CustomVariants>>(){}.getType();
                List<CustomVariants> tiers = GSON.fromJson(reader, listType);
                loadedVariants = validateTiers(tiers);

                for (CustomVariants variant : loadedVariants){
                    registerItem(ITEMS, variant);
                }

                LOGGER.info("Loaded " + loadedVariants.size() + " custom tiers");
            } catch (IOException e) {
                LOGGER.error("Failed to load tier config", e);
            }
        }
    }

    private static void registerItem(DeferredRegister.Items ITEMS, CustomVariants variant){
        String itemName = variant.getName() + "_slimeball";
        ResourceLocation itemId = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, itemName);
        DeferredItem<Item> item = ITEMS.registerItem(variant.name + "_slimeball", properties -> new SlimeballItem(variant.getColor()){
            @Override
            public Component getName(ItemStack stack) {
                return Component.literal(variant.name.substring(0,1).toUpperCase() + variant.name.substring(1) + " Slimeball");
            }
        }, new Item.Properties());

        registeredItems.put(itemId, item);
    }

    private static List<CustomVariants> validateTiers(List<CustomVariants> tiers) {
        return tiers.stream()
                .filter(tier -> {
                    // Validate name (no spaces, special characters, etc.)
                    if (!tier.name.matches("^[a-z0-9_]+$")) {
                        LOGGER.error("Invalid name format for tier: " + tier.name);
                        return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

    public static class CustomVariants {
        private final String name;
        private final String color;

        public CustomVariants(String name, String color) {
            this.name = name;
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public int getColor() {
            return hexToInt(color);
        }

        public int hexToInt(String hexColor) {
            // Remove the leading '#' if present
            if (hexColor.startsWith("#")) {
                hexColor = hexColor.substring(1);
            }

            // Parse the hex string and prepend 0x
            return (int) Long.parseLong(hexColor, 16);
        }
    }
}
