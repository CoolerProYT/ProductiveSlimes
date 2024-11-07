package com.coolerpromc.productiveslimes.config;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.custom.SlimeBlock;
import com.coolerpromc.productiveslimes.item.custom.SlimeballItem;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CustomContentRegistry {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String CONFIG_PATH = "config/productiveslimes/variants.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static List<CustomVariants> loadedVariants = new ArrayList<>();
    private static Map<ResourceLocation, DeferredItem<Item>> registeredItems = new HashMap<>();
    private static Map<ResourceLocation, DeferredBlock<Block>> registeredBlocks = new HashMap<>();

    public static void initialize(DeferredRegister.Items item, DeferredRegister.Blocks block) {
        createDefaultConfig();
        loadVariants(item, block);

        generateSlimeballTag();
        generateResourcePack();
    }

    public static List<CustomVariants> getLoadedTiers() {
        return loadedVariants;
    }

    public static DeferredItem<Item> getSlimeballItemForVariant(String variantName){
        return registeredItems.get(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, variantName + "_slimeball"));
    }

    public static DeferredBlock<Block> getSlimeBlockForVariant(String variantName){
        return registeredBlocks.get(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, variantName + "_slime_block"));
    }

    private static void createDefaultConfig() {
        File configFile = new File(CONFIG_PATH);
        if (!configFile.exists()) {
            List<CustomVariants> defaultTiers = Arrays.asList(
                    new CustomVariants("birch", "#FF674d2e",5)
            );

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

    private static void loadVariants(DeferredRegister.Items ITEMS, DeferredRegister.Blocks BLOCKS) {
        File configFile = new File(CONFIG_PATH);
        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                Type listType = new TypeToken<List<CustomVariants>>(){}.getType();
                List<CustomVariants> tiers = GSON.fromJson(reader, listType);
                loadedVariants = validateTiers(tiers);

                for (CustomVariants variant : loadedVariants){
                    registerSlimeballItem(ITEMS, variant);
                    registerSlimeBlock(BLOCKS, variant, ITEMS);
                }

                LOGGER.info("Loaded " + loadedVariants.size() + " custom tiers");
            } catch (IOException e) {
                LOGGER.error("Failed to load tier config", e);
            }
        }
    }

    private static void registerSlimeBlock(DeferredRegister.Blocks BLOCKS, CustomVariants variant, DeferredRegister.Items ITEMS){
        String blockName = variant.getName() + "_slime_block";
        ResourceLocation blockId = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, blockName);
        DeferredBlock<Block> block = registerBlock(blockName, () -> new SlimeBlock(MapColor.byId(variant.getMapColorId()), variant.getColor()){
            @Override
            public MutableComponent getName() {
                return Component.literal(variant.getName().substring(0,1).toUpperCase() + variant.getName().substring(1) + " Slime Block");
            }
        }, BLOCKS, ITEMS, variant.getName());

        registeredBlocks.put(blockId, block);
    }

    private static void registerSlimeballItem(DeferredRegister.Items ITEMS, CustomVariants variant){
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

    private static void generateSlimeballTag(){
        List<String> itemIds = new ArrayList<>();

        for (CustomVariants variants : getLoadedTiers()){
            itemIds.add("productiveslimes:" + variants.getName() + "_slimeball");
        }

        Map<String, Object> tagJson = new HashMap<>();
        tagJson.put("replace", false);
        tagJson.put("values", itemIds);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonContent = gson.toJson(tagJson);

        if (Files.exists(Paths.get("saves"))) return;

        Path tagFile = Paths.get("world/datapacks/modify_tag/data/c/tags/item/slime_balls.json");
        Path mcmeta = Paths.get("world/datapacks/modify_tag/pack.mcmeta");
        try{
            Files.createDirectories(tagFile.getParent());
            Files.write(tagFile, jsonContent.getBytes(StandardCharsets.UTF_8));

            Files.write(mcmeta, ("{\n" +
                                "    \"pack\": {\n" +
                                "        \"description\": \"The default data for Minecraft\",\n" +
                                "        \"pack_format\": 48\n" +
                                "    }\n" +
                                "}").getBytes());
        }
        catch (IOException e){
            LOGGER.error("Failed to generate tag JSON file for tag: slime_balls", e);
        }
    }

    private static void generateResourcePack(){
        File file = new File("resourcepacks/productiveslimes");

        try{
            FileUtils.deleteDirectory(file);
        }
        catch (IOException e){

        }

        Path blockstatePath = Paths.get("resourcepacks/productiveslimes/assets/productiveslimes/blockstates/birch_slime_balls.json");
        Path modelPath = Paths.get("resourcepacks/productiveslimes/assets/productiveslimes/models/block/birch_slime_balls.json");
        Path mcmeta = Paths.get("resourcepacks/productiveslimes/pack.mcmeta");

        Path blockstate;
        Path blockModel;

        try{
            Files.createDirectories(blockstatePath.getParent());
            Files.createDirectories(modelPath.getParent());

            Files.write(mcmeta, ("{\n" +
                    "  \"pack\": {\n" +
                    "    \"pack_format\": 34,\n" +
                    "    \"description\": \"Custom variants resources\"\n" +
                    "  }\n" +
                    "}}").getBytes());
        }
        catch (IOException e){
            LOGGER.error("Failed to generate tag JSON file for tag: slime_balls", e);
        }

        for (CustomVariants variants : getLoadedTiers()){
            String id = variants.getName() + "_slime_block";
            blockstate = Paths.get("resourcepacks/productiveslimes/assets/productiveslimes/blockstates/" + id + ".json");
            blockModel = Paths.get("resourcepacks/productiveslimes/assets/productiveslimes/models/block/" + id + ".json");

            try{
                Files.write(blockModel, ("{\n" +
                        "  \"parent\": \"productiveslimes:block/template_slime_block\"\n" +
                        "}").getBytes());

                Files.write(blockstate, ("{\n" +
                        "  \"variants\": {\n" +
                        "    \"\": {\n" +
                        "      \"model\": \"productiveslimes:block/"+ id + "\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}").getBytes());
            }
            catch (IOException e){

            }
        }
    }

    public static void generateSlimeballTag(Path worldFolder){
        List<String> itemIds = new ArrayList<>();

        for (CustomVariants variants : getLoadedTiers()){
            itemIds.add("productiveslimes:" + variants.getName() + "_slimeball");
        }

        Map<String, Object> tagJson = new HashMap<>();
        tagJson.put("replace", false);
        tagJson.put("values", itemIds);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonContent = gson.toJson(tagJson);

        Path tagFile = worldFolder.resolve("datapacks/modify_tag/data/c/tags/item/slime_balls.json");
        Path mcmeta = worldFolder.resolve("datapacks/modify_tag/pack.mcmeta");

        try{
            Files.createDirectories(tagFile.getParent());
            Files.write(tagFile, jsonContent.getBytes(StandardCharsets.UTF_8));

            Files.write(mcmeta, ("{\n" +
                    "    \"pack\": {\n" +
                    "        \"description\": \"The default data for Minecraft\",\n" +
                    "        \"pack_format\": 48\n" +
                    "    }\n" +
                    "}").getBytes());
        }
        catch (IOException e){
            LOGGER.error("Failed to generate tag JSON file for tag: slime_balls", e);
        }
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

    private static DeferredBlock<Block> registerBlock(String name, Supplier<Block> block, DeferredRegister.Blocks BLOCKS, DeferredRegister.Items ITEMS, String variantName){
        DeferredBlock<Block> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name,toReturn, ITEMS, variantName);
        return toReturn;
    }

    private static DeferredItem<BlockItem> registerBlockItem(String name, DeferredBlock<Block> block, DeferredRegister.Items ITEMS, String variantName){
        return ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()){
            @Override
            public Component getName(ItemStack pStack) {
                return Component.literal(variantName.substring(0,1).toUpperCase() + variantName.substring(1) + " Slime Block");
            }
        });
    }

    public static class CustomVariants {
        private final String name;
        private final String color;
        private final int mapColorId;

        public CustomVariants(String name, String color, int mapColorId) {
            this.name = name;
            this.color = color;
            this.mapColorId = mapColorId;
        }

        public String getName() {
            return name;
        }

        public int getColor() {
            return hexToInt(color);
        }

        public int getMapColorId() {
            return mapColorId;
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
