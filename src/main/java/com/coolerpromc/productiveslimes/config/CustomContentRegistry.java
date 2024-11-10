package com.coolerpromc.productiveslimes.config;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.custom.SlimeBlock;
import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import com.coolerpromc.productiveslimes.entity.slime.Slime;
import com.coolerpromc.productiveslimes.item.custom.DnaItem;
import com.coolerpromc.productiveslimes.item.custom.FakeBucketItem;
import com.coolerpromc.productiveslimes.item.custom.SlimeballItem;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.*;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
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
    private static Map<ResourceLocation, DeferredItem<Item>> registeredDnaItems = new HashMap<>();
    private static Map<ResourceLocation, DeferredItem<Item>> registeredSpawnEggItems = new HashMap<>();
    private static Map<ResourceLocation, DeferredBlock<Block>> registeredBlocks = new HashMap<>();
    private static Map<ResourceLocation, DeferredHolder<EntityType<?>, EntityType<BaseSlime>>> registeredSlimes = new HashMap<>();

    public static void initialize(DeferredRegister.Items item, DeferredRegister.Blocks block, DeferredRegister<EntityType<?>> entityType) {
        createDefaultConfig();
        loadVariants(item, block, entityType);

        generateSlimeballTag();
        generateDnaTag();
        generateResourcePack();
        generateCraftingRecipe();
        generateModRecipe();
    }

    public static List<CustomVariants> getLoadedTiers() {
        return loadedVariants;
    }

    public static DeferredItem<Item> getSlimeballItemForVariant(String variantName){
        return registeredItems.get(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, variantName + "_slimeball"));
    }

    public static DeferredItem<Item> getDnaItemForVariant(String variantName){
        return registeredDnaItems.get(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, variantName + "_slime_dna"));
    }

    public static DeferredItem<Item> getSpawnEggItemForVariant(String variantName){
        return registeredSpawnEggItems.get(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, variantName + "_slime_spawn_egg"));
    }

    public static DeferredBlock<Block> getSlimeBlockForVariant(String variantName){
        return registeredBlocks.get(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, variantName + "_slime_block"));
    }

    public static DeferredHolder<EntityType<?>, EntityType<BaseSlime>> getSlimeForVariant(String variantName){
        return registeredSlimes.get(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, variantName + "_slime"));
    }

    private static void createDefaultConfig() {
        File configFile = new File(CONFIG_PATH);
        if (!configFile.exists()) {
            List<CustomVariants> defaultTiers = Arrays.asList(
                    new CustomVariants("birch", "#FFa69d6f",5, 1500, "minecraft:birch_log", "minecraft:birch_log",2,"productiveslimes:oak_slime_dna", "productiveslimes:oak_slime_dna", "minecraft:birch_log", 0.75)
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

    private static void loadVariants(DeferredRegister.Items ITEMS, DeferredRegister.Blocks BLOCKS, DeferredRegister<EntityType<?>> ENTITY_TYPES) {
        File configFile = new File(CONFIG_PATH);
        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                Type listType = new TypeToken<List<CustomVariants>>(){}.getType();
                List<CustomVariants> tiers = GSON.fromJson(reader, listType);
                loadedVariants = validateTiers(tiers);

                for (CustomVariants variant : loadedVariants){
                    registerSlimeballItem(ITEMS, variant);
                    registerDnaItem(ITEMS, variant);
                    registerSlimeBlock(BLOCKS, variant, ITEMS);
                    registerSlime(ENTITY_TYPES, variant);
                    registerSpawnEggItem(ITEMS, variant);
                }

                registerFluid(loadedVariants);

                LOGGER.info("Loaded " + loadedVariants.size() + " custom tiers");
            } catch (IOException e) {
                LOGGER.error("Failed to load tier config", e);
            }
        }
    }

    private static void registerSpawnEggItem(DeferredRegister.Items ITEMS, CustomVariants variant){
        String itemName = variant.getName() + "_slime_spawn_egg";
        ResourceLocation itemId = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, itemName);
        DeferredItem<Item> item = ITEMS.register(itemName,
                () -> new SpawnEggItem(getSlimeForVariant(variant.getName()).get(), variant.getColor(), variant.getColor(), new Item.Properties()){
                    @Override
                    public Component getName(ItemStack pStack) {
                        return Component.literal(
                                Arrays.stream(variant.getName().split("_"))
                                        .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                                        .collect(Collectors.joining(" ")) + " Slime Spawn Egg"
                        );

                    }
                });

        registeredSpawnEggItems.put(itemId, item);
    }

    private static void registerSlime(DeferredRegister<EntityType<?>> ENTITY_TYPES, CustomVariants variant){
        String slimeName = variant.getName() + "_slime";
        ResourceLocation slimeId = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, slimeName);

        DeferredHolder<EntityType<?>, EntityType<BaseSlime>> slime = ENTITY_TYPES.register(slimeName, () -> EntityType.Builder.<BaseSlime>of(
                (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, variant.getCooldown(), variant.getColor(), getSlimeballItemForVariant(variant.getName()).get(), BuiltInRegistries.ITEM.get(ResourceLocation.parse(variant.getGrowthItem()))){
                    @Override
                    public Component getName() {
                        return Component.literal(
                                Arrays.stream(variant.getName().split("_"))
                                        .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                                        .collect(Collectors.joining(" ")) + " Slime"
                        );

                    }
                },
                MobCategory.CREATURE).build(slimeName));

        registeredSlimes.put(slimeId, slime);
    }

    private static void registerSlimeBlock(DeferredRegister.Blocks BLOCKS, CustomVariants variant, DeferredRegister.Items ITEMS){
        String blockName = variant.getName() + "_slime_block";
        ResourceLocation blockId = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, blockName);
        DeferredBlock<Block> block = registerBlock(blockName, () -> new SlimeBlock(MapColor.byId(variant.getMapColorId()), variant.getColor()){
            @Override
            public MutableComponent getName() {
                return Component.literal(
                        Arrays.stream(variant.getName().split("_"))
                                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                                .collect(Collectors.joining(" ")) + " Slime Block"
                );

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
                return Component.literal(
                        Arrays.stream(variant.getName().split("_"))
                                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                                .collect(Collectors.joining(" ")) + " Slimeball"
                );

            }
        }, new Item.Properties());

        registeredItems.put(itemId, item);
    }

    private static void registerDnaItem(DeferredRegister.Items ITEMS, CustomVariants variant){
        String itemName = variant.getName() + "_slime_dna";
        ResourceLocation itemId = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, itemName);
        DeferredItem<Item> item = ITEMS.registerItem(variant.name + "_slime_dna", properties -> new DnaItem(variant.getColor()){
            @Override
            public Component getName(ItemStack stack) {
                return Component.literal(
                        Arrays.stream(variant.getName().split("_"))
                                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                                .collect(Collectors.joining(" ")) + " Slime DNA"
                );

            }
        }, new Item.Properties());

        registeredDnaItems.put(itemId, item);
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

    private static void generateDnaTag(){
        List<String> itemIds = new ArrayList<>();

        for (CustomVariants variants : getLoadedTiers()){
            itemIds.add("productiveslimes:" + variants.getName() + "_slime_dna");
        }

        Map<String, Object> tagJson = new HashMap<>();
        tagJson.put("replace", false);
        tagJson.put("values", itemIds);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonContent = gson.toJson(tagJson);

        if (Files.exists(Paths.get("saves"))) return;

        Path tagFile = Paths.get("world/datapacks/modify_tag/data/productiveslimes/tags/item/dna_item.json");
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

    private static void generateCraftingRecipe(){
        if (Files.exists(Paths.get("saves"))) return;

        File file = new File("world/datapacks/modify_tag/data/minecraft/recipe");

        try{
            FileUtils.deleteDirectory(file);
        }
        catch (IOException e){
            System.out.println(e);
        }

        for (CustomVariants variant : loadedVariants){
            slimeballToSlimeBlock(variant.getName(), null);
            slimeBlockToSlimeball(variant.getName(), null);
        }
    }

    public static void generateCraftingRecipe(Path worldFolder){
        File file = new File(worldFolder.resolve("datapacks/modify_tag/data/minecraft/recipe").toString());

        try{
            FileUtils.deleteDirectory(file);
        }
        catch (IOException e){
            System.out.println(e);
        }

        for (CustomVariants variant : loadedVariants){
            slimeballToSlimeBlock(variant.getName(), worldFolder);
            slimeBlockToSlimeball(variant.getName(), worldFolder);
        }
    }

    private static void slimeballToSlimeBlock(String name, Path worldFolder){
        Path recipeFile = Paths.get("world/datapacks/modify_tag/data/minecraft/recipe/" + name + "_slimeball_to_block.json");

        if(worldFolder != null){
            recipeFile = worldFolder.resolve("datapacks/modify_tag/data/minecraft/recipe/" + name + "_slimeball_to_block.json");
        }

        try{
            Files.createDirectories(recipeFile.getParent());
            Files.write(recipeFile, ("{\n" +
                    "  \"type\": \"minecraft:crafting_shaped\",\n" +
                    "  \"category\": \"building\",\n" +
                    "  \"key\": {\n" +
                    "    \"A\": {\n" +
                    "      \"item\": \"productiveslimes:" + name + "_slimeball\"\n" +
                    "    }\n" +
                    "  },\n" +
                    "  \"pattern\": [\n" +
                    "    \"AAA\",\n" +
                    "    \"AAA\",\n" +
                    "    \"AAA\"\n" +
                    "  ],\n" +
                    "  \"result\": {\n" +
                    "    \"count\": 1,\n" +
                    "    \"id\": \"productiveslimes:" + name + "_slime_block\"\n" +
                    "  }\n" +
                    "}").getBytes());
        }
        catch (IOException e){

        }
    }

    private static void slimeBlockToSlimeball(String name, Path worldFolder){
        Path recipeFile = Paths.get("world/datapacks/modify_tag/data/minecraft/recipe/" + name + "_slime_block_to_ball.json");

        if(worldFolder != null){
            recipeFile = worldFolder.resolve("datapacks/modify_tag/data/minecraft/recipe/" + name + "_slime_block_to_ball.json");
        }

        try{
            Files.createDirectories(recipeFile.getParent());
            Files.write(recipeFile, ("{\n" +
                    "  \"type\": \"minecraft:crafting_shapeless\",\n" +
                    "  \"category\": \"misc\",\n" +
                    "  \"ingredients\": [\n" +
                    "    {\n" +
                    "      \"item\": \"productiveslimes:" + name + "_slime_block\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"result\": {\n" +
                    "    \"count\": 9,\n" +
                    "    \"id\": \"productiveslimes:" + name + "_slimeball\"\n" +
                    "  }\n" +
                    "}").getBytes());
        }
        catch (IOException e){
            System.out.println(e);
        }
    }

    private static void generateResourcePack(){
        File file = new File("resourcepacks/productiveslimes");

        try{
            FileUtils.deleteDirectory(file);
        }
        catch (IOException e){
            System.out.println(e);
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

    public static void generateDnaTag(Path worldFolder){
        List<String> itemIds = new ArrayList<>();

        for (CustomVariants variants : getLoadedTiers()){
            itemIds.add("productiveslimes:" + variants.getName() + "_slime_dna");
        }

        Map<String, Object> tagJson = new HashMap<>();
        tagJson.put("replace", false);
        tagJson.put("values", itemIds);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonContent = gson.toJson(tagJson);

        Path tagFile = worldFolder.resolve("datapacks/modify_tag/data/productiveslimes/tags/item/dna_item.json");
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

    private static void registerFluid(List<CustomVariants> variants) {
        File file = new File("kubejs/startup_scripts/productiveslimes/config/fluids");

        try{
            FileUtils.deleteDirectory(file);
        }
        catch (IOException e){
            System.out.println(e);
        }

        for (CustomVariants variant : variants){
            registerFluid(variant);
        }
    }

    private static void registerFluid(CustomVariants variant){
        Path fluidFile = Paths.get("kubejs/startup_scripts/productiveslimes/config/fluids/" + variant.getName() + ".js");

        try{
            Files.createDirectories(fluidFile.getParent());
            Files.write(fluidFile, ("StartupEvents.registry('fluid', event => {\n" +
                    "    event.create('molten_" + variant.getName() + "', \"thin\")\n" +
                    "        .tint(" + variant.getColor() + ")\n" +
                    "        .translucent()\n" +
                    "        .displayName('Molten " + Arrays.stream(variant.getName().split("_"))
                    .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                    .collect(Collectors.joining(" ")) + "')\n" +
                    "});\n" +
                    "ItemEvents.modification(event => {\n" +
                    "    event.modify('kubejs:molten_" + variant.getName() + "_bucket', item => {\n" +
                    "        item.maxStackSize = 64\n" +
                    "    })\n" +
                    "})").getBytes());
        }
        catch (IOException e){
            LOGGER.error("Failed to generate tag JSON file for tag: slime_balls", e);
        }
    }

    private static void generateModRecipe(){
        if (Files.exists(Paths.get("saves"))) return;

        File file = new File("world/datapacks/modify_tag/data/productiveslimes/recipe");

        try{
            FileUtils.deleteDirectory(file);
        }
        catch (IOException e){
            System.out.println(e);
        }

        for (CustomVariants variant : loadedVariants){
            meltingRecipeBlock(variant.getName(), null);
            meltingRecipeBall(variant.getName(), null);
            solidingRecipe(variant, null);
            dnaExtracting(variant,null);
            dnaSynthesizingSelf(variant,null);
            dnaSynthesizing(variant, null);
        }
    }

    public static void generateModRecipe(Path worldFolder){
        File file = new File(worldFolder.resolve("datapacks/modify_tag/data/productiveslimes/recipe").toString());

        try{
            FileUtils.deleteDirectory(file);
        }
        catch (IOException e){
            System.out.println(e);
        }

        for (CustomVariants variant : loadedVariants){
            meltingRecipeBlock(variant.getName(), worldFolder);
            meltingRecipeBall(variant.getName(), worldFolder);
            solidingRecipe(variant, worldFolder);
            dnaExtracting(variant, worldFolder);
            dnaSynthesizingSelf(variant, worldFolder);
            dnaSynthesizing(variant, worldFolder);
        }
    }

    private static void meltingRecipeBlock(String name, Path worldFolder){
        Path recipeFile = Paths.get("world/datapacks/modify_tag/data/productiveslimes/recipe/" + name + "_slime_block_melting.json");

        if(worldFolder != null){
            recipeFile = worldFolder.resolve("datapacks/modify_tag/data/productiveslimes/recipe/" + name + "_slime_block_melting.json");
        }

        try{
            Files.createDirectories(recipeFile.getParent());
            Files.write(recipeFile, ("{\n" +
                    "  \"type\": \"productiveslimes:melting\",\n" +
                    "  \"energy\": 200,\n" +
                    "  \"ingredients\": [\n" +
                    "    {\n" +
                    "      \"item\": \"productiveslimes:" + name + "_slime_block\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"inputCount\": 2,\n" +
                    "  \"output\": [\n" +
                    "    {\n" +
                    "      \"count\": 5,\n" +
                    "      \"id\": \"kubejs:molten_" + name + "_bucket\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}").getBytes());
        }
        catch (IOException e){

        }
    }

    private static void meltingRecipeBall(String name, Path worldFolder){
        Path recipeFile = Paths.get("world/datapacks/modify_tag/data/productiveslimes/recipe/" + name + "_slimeball_melting.json");

        if(worldFolder != null){
            recipeFile = worldFolder.resolve("datapacks/modify_tag/data/productiveslimes/recipe/" + name + "_slimeball_melting.json");
        }

        try{
            Files.createDirectories(recipeFile.getParent());
            Files.write(recipeFile, ("{\n" +
                    "  \"type\": \"productiveslimes:melting\",\n" +
                    "  \"energy\": 200,\n" +
                    "  \"ingredients\": [\n" +
                    "    {\n" +
                    "      \"item\": \"productiveslimes:" + name + "_slimeball\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"inputCount\": 4,\n" +
                    "  \"output\": [\n" +
                    "    {\n" +
                    "      \"count\": 1,\n" +
                    "      \"id\": \"kubejs:molten_" + name + "_bucket\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}").getBytes());
        }
        catch (IOException e){

        }
    }

    private static void solidingRecipe(CustomVariants variant, Path worldFolder){
        Path recipeFile = Paths.get("world/datapacks/modify_tag/data/productiveslimes/recipe/molten_" + variant.getName() + "_bucket_soliding.json");

        if(worldFolder != null){
            recipeFile = worldFolder.resolve("datapacks/modify_tag/data/productiveslimes/recipe/molten_" + variant.getName() + "_bucket_soliding.json");
        }

        try{
            Files.createDirectories(recipeFile.getParent());
            Files.write(recipeFile, ("{\n" +
                    "  \"type\": \"productiveslimes:soliding\",\n" +
                    "  \"energy\": 200,\n" +
                    "  \"ingredients\": [\n" +
                    "    {\n" +
                    "      \"item\": \"kubejs:molten_" + variant.getName() + "_bucket\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"inputCount\": 1,\n" +
                    "  \"output\": [\n" +
                    "    {\n" +
                    "      \"count\": " + variant.getSolidingOutputCount() + ",\n" +
                    "      \"id\": \"" + variant.getSolidingOutput() + "\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"count\": 1,\n" +
                    "      \"id\": \"minecraft:bucket\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}").getBytes());
        }
        catch (IOException e){

        }
    }

    private static void dnaExtracting(CustomVariants variant, Path worldFolder){
        Path recipeFile = Paths.get("world/datapacks/modify_tag/data/productiveslimes/recipe/" + variant.getName() + "_slimeball_dna_extracting.json");

        if(worldFolder != null){
            recipeFile = worldFolder.resolve("datapacks/modify_tag/data/productiveslimes/recipe/" + variant.getName() + "_slimeball_dna_extracting.json");
        }

        try{
            Files.createDirectories(recipeFile.getParent());
            Files.write(recipeFile, ("{\n" +
                    "  \"type\": \"productiveslimes:dna_extracting\",\n" +
                    "  \"energy\": 400,\n" +
                    "  \"ingredients\": [\n" +
                    "    {\n" +
                    "      \"item\": \"productiveslimes:" + variant.getName() + "_slimeball\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"inputCount\": 1,\n" +
                    "  \"output\": [\n" +
                    "    {\n" +
                    "      \"count\": 1,\n" +
                    "      \"id\": \"productiveslimes:" + variant.getName() + "_slime_dna\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"count\": 1,\n" +
                    "      \"id\": \"minecraft:slime_ball\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"outputChance\": " + variant.getDnaOutputChance() + "\n" +
                    "}").getBytes());
        }
        catch (IOException e){

        }
    }

    private static void dnaSynthesizingSelf(CustomVariants variant, Path worldFolder){
        Path recipeFile = Paths.get("world/datapacks/modify_tag/data/productiveslimes/recipe/" + variant.getName() + "_slime_spawn_egg_synthesizing_self.json");

        if(worldFolder != null){
            recipeFile = worldFolder.resolve("datapacks/modify_tag/data/productiveslimes/recipe/" + variant.getName() + "_slime_spawn_egg_synthesizing_self.json");
        }

        try{
            Files.createDirectories(recipeFile.getParent());
            Files.write(recipeFile, ("{\n" +
                    "  \"type\": \"productiveslimes:dna_synthesizing\",\n" +
                    "  \"energy\": 600,\n" +
                    "  \"ingredients\": [\n" +
                    "    {\n" +
                    "      \"item\": \"productiveslimes:" + variant.getName() + "_slime_dna\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"item\": \"productiveslimes:" + variant.getName() + "_slime_dna\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"item\": \"" + variant.getSynthesizingInputItem() + "\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"inputCount\": 2,\n" +
                    "  \"output\": [\n" +
                    "    {\n" +
                    "      \"count\": 1,\n" +
                    "      \"id\": \"productiveslimes:" + variant.getName() + "_slime_spawn_egg\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}").getBytes());
        }
        catch (IOException e){

        }
    }

    private static void dnaSynthesizing(CustomVariants variant, Path worldFolder){
        Path recipeFile = Paths.get("world/datapacks/modify_tag/data/productiveslimes/recipe/" + variant.getName() + "_slime_spawn_egg_synthesizing.json");

        if(worldFolder != null){
            recipeFile = worldFolder.resolve("datapacks/modify_tag/data/productiveslimes/recipe/" + variant.getName() + "_slime_spawn_egg_synthesizing.json");
        }

        try{
            Files.createDirectories(recipeFile.getParent());
            Files.write(recipeFile, ("{\n" +
                    "  \"type\": \"productiveslimes:dna_synthesizing\",\n" +
                    "  \"energy\": 600,\n" +
                    "  \"ingredients\": [\n" +
                    "    {\n" +
                    "      \"item\": \"" + variant.getSynthesizingInputDna1() + "\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"item\": \"" + variant.getSynthesizingInputDna2() + "\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"item\": \"" + variant.getSynthesizingInputItem() + "\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"inputCount\": 4,\n" +
                    "  \"output\": [\n" +
                    "    {\n" +
                    "      \"count\": 1,\n" +
                    "      \"id\": \"productiveslimes:" + variant.getName() + "_slime_spawn_egg\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}").getBytes());
        }
        catch (IOException e){

        }
    }

    public static class CustomVariants {
        private final String name;
        private final String color;
        private final int mapColorId;
        private final int cooldown;
        private final String growthItem;
        private final String solidingOutput;
        private final int solidingOutputCount;
        private final String synthesizingInputItem;
        private final String synthesizingInputDna1;
        private final String synthesizingInputDna2;
        private final double dnaOutputChance;

        public CustomVariants(String name, String color, int mapColorId, int cooldown, String growthItem, String solidingOutput, int solidingOutputCount, String synthesizingInputDna1, String synthesizingInputDna2, String synthesizingInputItem, double dnaOutputChance) {
            this.name = name;
            this.color = color;
            this.mapColorId = mapColorId;
            this.cooldown = cooldown;
            this.growthItem = growthItem;
            this.solidingOutput = solidingOutput;
            this.solidingOutputCount = solidingOutputCount;
            this.synthesizingInputItem = synthesizingInputItem;
            this.synthesizingInputDna1 = synthesizingInputDna1;
            this.synthesizingInputDna2 = synthesizingInputDna2;
            this.dnaOutputChance = dnaOutputChance;
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

        public int getCooldown() {
            return cooldown;
        }

        public String getGrowthItem() {
            return growthItem;
        }

        public int getSolidingOutputCount() {
            return solidingOutputCount;
        }

        public String getSolidingOutput() {
            return solidingOutput;
        }

        public String getSynthesizingInputDna1() {
            return synthesizingInputDna1;
        }

        public String getSynthesizingInputDna2() {
            return synthesizingInputDna2;
        }

        public String getSynthesizingInputItem() {
            return synthesizingInputItem;
        }

        public double getDnaOutputChance() {
            return dnaOutputChance;
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
