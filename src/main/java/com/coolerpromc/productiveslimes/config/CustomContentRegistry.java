package com.coolerpromc.productiveslimes.config;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.custom.SlimeBlock;
import com.coolerpromc.productiveslimes.config.fluid.FluidResources;
import com.coolerpromc.productiveslimes.config.fluid.ModBaseFluidType;
import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import com.coolerpromc.productiveslimes.entity.slime.Slime;
import com.coolerpromc.productiveslimes.item.custom.DnaItem;
import com.coolerpromc.productiveslimes.item.custom.SlimeballItem;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.ModTier;
import com.coolerpromc.productiveslimes.util.InMemoryDataPack;
import com.coolerpromc.productiveslimes.util.InMemoryResourcePack;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.*;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;
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
    private static Map<String, byte[]> resourceData = new HashMap<>();
    private static Map<String, byte[]> dataPackResources = new HashMap<>();

    public static void initialize(DeferredRegister.Items item, DeferredRegister.Blocks block, DeferredRegister<EntityType<?>> entityType) {
        createDefaultConfig();
        loadVariants(item, block, entityType);

        generateResourcePackInMemory();
        generateDataPackInMemory();
    }

    public static void handleResourcePack(){
        InMemoryResourcePack resourcePack = new InMemoryResourcePack(resourceData);
        PackRepository packRepository = Minecraft.getInstance().getResourcePackRepository();
        Pack pack = Pack.readMetaAndCreate(
                resourcePack.location(),
                new Pack.ResourcesSupplier() {
                    @Override
                    public PackResources openPrimary(PackLocationInfo location) {
                        return resourcePack;
                    }
                    @Override
                    public PackResources openFull(PackLocationInfo location, Pack.Metadata metadata) {
                        return resourcePack;
                    }
                },
                PackType.CLIENT_RESOURCES,
                new PackSelectionConfig(true, Pack.Position.TOP, true)
        );
        packRepository.addPackFinder((consumer) -> {
            consumer.accept(pack);
        });
        Minecraft.getInstance().reloadResourcePacks();
    }

    public static void handleDatapack(MinecraftServer server) {
        InMemoryDataPack dataPack = new InMemoryDataPack(dataPackResources);
        Pack pack = Pack.readMetaAndCreate(
                new PackLocationInfo("productiveslimes_datapack", Component.literal("In Memory Pack"),
                        new PackSource() {
                            @Override
                            public Component decorate(Component name) {
                                return Component.literal("In Memory Pack");
                            }

                            @Override
                            public boolean shouldAddAutomatically() {
                                return true;
                            }
                        }, Optional.empty()),
                new Pack.ResourcesSupplier() {
                    @Override
                    public PackResources openPrimary(PackLocationInfo location) {
                        return dataPack;
                    }

                    @Override
                    public PackResources openFull(PackLocationInfo location, Pack.Metadata metadata) {
                        return dataPack;
                    }
                },
                PackType.SERVER_DATA,
                new PackSelectionConfig(true, Pack.Position.TOP, true)
        );

        server.getPackRepository().addPackFinder((consumer) -> consumer.accept(pack));
        List<Pack> packs = new ArrayList<>(server.getPackRepository().getSelectedPacks());
        packs.add(pack);
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
                    registerFluid(variant);

                    ModTier registerTier = new ModTier(variant.getName(), variant.getColor(), variant.mapColorId, variant.cooldown, variant.growthItem, variant.solidingOutput, variant.solidingOutputCount, variant.synthesizingInputItem, variant.synthesizingInputDna1, variant.synthesizingInputDna2, (float) variant.dnaOutputChance);
                    ModTiers.addRegisteredTier(variant.getName(), registerTier);
                }

                LOGGER.info("Loaded " + loadedVariants.size() + " custom tiers");
            } catch (IOException e) {
                LOGGER.error("Failed to load tier config", e);
            }
        }
    }

    private static void registerSpawnEggItem(DeferredRegister.Items ITEMS, CustomVariants variant){
        String itemName = variant.getName() + "_slime_spawn_egg";
        ResourceLocation itemId = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, itemName);
        DeferredItem<Item> item = ITEMS.registerItem(itemName, properties -> new SpawnEggItem(getSlimeForVariant(variant.getName()).get(), properties));

        registeredSpawnEggItems.put(itemId, item);
    }

    private static void registerSlime(DeferredRegister<EntityType<?>> ENTITY_TYPES, CustomVariants variant){
        String slimeName = variant.getName() + "_slime";
        ResourceLocation slimeId = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, slimeName);

        DeferredHolder<EntityType<?>, EntityType<BaseSlime>> slime = ENTITY_TYPES.register(slimeName, () -> EntityType.Builder.<BaseSlime>of(
                (pEntityType, pLevel) -> new Slime(pEntityType, pLevel, variant.getCooldown(), variant.getColor(), getSlimeballItemForVariant(variant.getName()).get(), BuiltInRegistries.ITEM.get(ResourceLocation.parse(variant.getGrowthItem())).get().value()),
                MobCategory.CREATURE).build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, variant.getName() + "_slime"))));

        registeredSlimes.put(slimeId, slime);
    }

    private static void registerSlimeBlock(DeferredRegister.Blocks BLOCKS, CustomVariants variant, DeferredRegister.Items ITEMS){
        String blockName = variant.getName() + "_slime_block";
        ResourceLocation blockId = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, blockName);
        DeferredBlock<Block> block = registerBlock(blockName, MapColor.byId(variant.getMapColorId()), variant.getColor(), BLOCKS, ITEMS, variant.getName());

        registeredBlocks.put(blockId, block);
    }

    private static void registerSlimeballItem(DeferredRegister.Items ITEMS, CustomVariants variant){
        String itemName = variant.getName() + "_slimeball";
        ResourceLocation itemId = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, itemName);
        DeferredItem<Item> item = ITEMS.registerItem(variant.name + "_slimeball", properties -> new SlimeballItem(variant.getColor(), properties), new Item.Properties());

        registeredItems.put(itemId, item);
    }

    private static void registerDnaItem(DeferredRegister.Items ITEMS, CustomVariants variant){
        String itemName = variant.getName() + "_slime_dna";
        ResourceLocation itemId = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, itemName);
        DeferredItem<Item> item = ITEMS.registerItem(variant.name + "_slime_dna", properties -> new DnaItem(variant.getColor(), properties), new Item.Properties());

        registeredDnaItems.put(itemId, item);
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

    private static DeferredBlock<Block> registerBlock(String name, MapColor mapColor, int color, DeferredRegister.Blocks BLOCKS, DeferredRegister.Items ITEMS, String variantName){
        DeferredBlock<Block> toReturn = BLOCKS.registerBlock(name, properties -> new SlimeBlock(properties, mapColor, color), BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK).noOcclusion());
        registerBlockItem(name,toReturn, ITEMS, variantName);
        return toReturn;
    }

    private static DeferredItem<BlockItem> registerBlockItem(String name, DeferredBlock<Block> block, DeferredRegister.Items ITEMS, String variantName){
        return ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
    }

    private static void registerFluid(CustomVariants variants) {
        FluidResources.register(() -> FluidResources.addFluid(variants.getName().substring(0,1).toUpperCase() + variants.getName().substring(1),
                new ModBaseFluidType.FunkyFluidInfo(variants.getName(), variants.getColor(), 0.1F, 1.5F, true), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).mapColor(MapColor.byId(variants.getMapColorId())), ((properties, funkyFluidInfo) -> new ModBaseFluidType(properties, funkyFluidInfo, variants.getColor())),
                (supplier, properties) -> new LiquidBlock(supplier.get(), properties.setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "molten_" + variants.getName() + "_block")))),
                properties -> properties.explosionResistance(1000F).tickRate(20),
                FluidType.Properties.create().canExtinguish(true).supportsBoating(true).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).canHydrate(true).viscosity(3000).motionScale(0.007D)));
    }

    private static void generateResourcePackInMemory(){
        Map<String, String> langJson = new HashMap<>();

        // Prepare pack.mcmeta content
        String packMcmetaContent = "{\n" +
                "  \"pack\": {\n" +
                "    \"pack_format\": 34,\n" +
                "    \"description\": \"Custom variants resources\"\n" +
                "  }\n" +
                "}";

        resourceData.put("pack.mcmeta", packMcmetaContent.getBytes(StandardCharsets.UTF_8));

        for (CustomVariants variants : getLoadedTiers()){
            String id = variants.getName() + "_slime_block";


            String blockstatePath = "assets/productiveslimes/blockstates/" + id + ".json";
            String modelPath = "assets/productiveslimes/models/block/" + id + ".json";
            String bucketModelPath = "assets/productiveslimes/models/item/molten_" + variants.getName() + "_bucket.json";
            String slimeBlockModelPath = "assets/productiveslimes/models/item/" + variants.getName() + "_slime_block.json";
            String dnaModelPath = "assets/productiveslimes/models/item/" + variants.getName() + "_slime_dna.json";
            String spawnEggModelPath = "assets/productiveslimes/models/item/" + variants.getName() + "_slime_spawn_egg.json";
            String slimeballModelPath = "assets/productiveslimes/models/item/" + variants.getName() + "_slimeball.json";

            String itemsBucketPath = "assets/productiveslimes/items/molten_" + variants.getName() + "_bucket.json";
            String itemsSlimeBlockPath = "assets/productiveslimes/items/" + variants.getName() + "_slime_block.json";
            String itemsDnaPath = "assets/productiveslimes/items/" + variants.getName() + "_slime_dna.json";
            String itemsSpawnEggPath = "assets/productiveslimes/items/" + variants.getName() + "_slime_spawn_egg.json";
            String itemsSlimeballPath = "assets/productiveslimes/items/" + variants.getName() + "_slimeball.json";

            // Generate formatted name
            String formattedName = Arrays.stream(variants.getName().split("_")).map(word -> word.substring(0, 1).toUpperCase() + word.substring(1)).collect(Collectors.joining(" "));

            langJson.put("block.productiveslimes." + variants.getName() + "_slime_block", formattedName + " Slime Block");
            langJson.put("item.productiveslimes." + variants.getName()  + "_slime_spawn_egg", formattedName + " Slime Spawn Egg");
            langJson.put("item.productiveslimes." + variants.getName()  + "_slimeball", formattedName + " Slimeball");
            langJson.put("item.productiveslimes." + variants.getName()  + "_slime_dna", formattedName + " Slime DNA");
            langJson.put("entity.productiveslimes." + variants.getName()  + "_slime", formattedName + " Slime");
            langJson.put("block.productiveslimes." + "molten_" + variants.getName() + "_block", "Molten " + formattedName);
            langJson.put("item.productiveslimes." + "molten_" + variants.getName() + "_bucket", "Molten " + formattedName + " Bucket");
            langJson.put("fluid_type.productiveslimes." + variants.getName(), "Molten " + formattedName);

            String blockModelContent = "{\n" +
                    "  \"parent\": \"productiveslimes:block/template_slime_block\"\n" +
                    "}";

            String blockstateContent = "{\n" +
                    "  \"variants\": {\n" +
                    "    \"\": {\n" +
                    "      \"model\": \"productiveslimes:block/"+ id + "\"\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";

            String bucketModelContent = "{\n" +
                    "  \"parent\": \"minecraft:item/generated\",\n" +
                    "  \"textures\": {\n" +
                    "    \"layer0\": \"productiveslimes:item/bucket\",\n" +
                    "    \"layer1\": \"productiveslimes:item/bucket_fluid\"\n" +
                    "  }\n" +
                    "}";

            String slimeBlockModelContent = "{\n" +
                    "  \"parent\": \"productiveslimes:block/template_slime_block\"\n" +
                    "}";

            String dnaModelContent = "{\n" +
                    "  \"parent\": \"minecraft:item/generated\",\n" +
                    "  \"textures\": {\n" +
                    "    \"layer0\": \"productiveslimes:item/template_dna\"\n" +
                    "  }\n" +
                    "}";

            String spawnEggModelContent = "{\n" +
                    "  \"parent\": \"minecraft:item/template_spawn_egg\"\n" +
                    "}";

            String slimeballModelContent = "{\n" +
                    "  \"parent\": \"minecraft:item/generated\",\n" +
                    "  \"textures\": {\n" +
                    "    \"layer0\": \"productiveslimes:item/template_slimeball\"\n" +
                    "  }\n" +
                    "}";

            String itemsBucketContent = "{\n" +
                    "  \"model\": {\n" +
                    "    \"type\": \"minecraft:model\",\n" +
                    "    \"model\": \"productiveslimes:item/molten_" + variants.getName() + "_bucket\",\n" +
                    "    \"tints\": [\n" +
                    "      {\n" +
                    "        \"type\": \"minecraft:constant\",\n" +
                    "        \"value\": -1\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"type\": \"minecraft:constant\",\n" +
                    "        \"value\": " + ARGB.opaque(variants.getColor()) + "\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  }\n" +
                    "}";

            String itemsSlimeBlockContent = "{\n" +
                    "  \"model\": {\n" +
                    "    \"type\": \"minecraft:model\",\n" +
                    "    \"model\": \"productiveslimes:item/" + variants.getName() + "_slime_block\",\n" +
                    "    \"tints\": [\n" +
                    "      {\n" +
                    "        \"type\": \"minecraft:constant\",\n" +
                    "        \"value\": " + ARGB.opaque(variants.getColor()) + "\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  }\n" +
                    "}";

            String itemsDnaContent = "{\n" +
                    "  \"model\": {\n" +
                    "    \"type\": \"minecraft:model\",\n" +
                    "    \"model\": \"productiveslimes:item/" + variants.getName() + "_slime_dna\",\n" +
                    "    \"tints\": [\n" +
                    "      {\n" +
                    "        \"type\": \"minecraft:constant\",\n" +
                    "        \"value\": " + ARGB.opaque(variants.getColor()) + "\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  }\n" +
                    "}";

            String itemsSpawnEggContent = "{\n" +
                    "  \"model\": {\n" +
                    "    \"type\": \"minecraft:model\",\n" +
                    "    \"model\": \"productiveslimes:item/template_slime_spawn_egg\",\n" +
                    "    \"tints\": [\n" +
                    "      {\n" +
                    "        \"type\": \"minecraft:constant\",\n" +
                    "        \"value\": " + ARGB.opaque(variants.getColor()) + "\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"type\": \"minecraft:constant\",\n" +
                    "        \"value\": " + ARGB.opaque(variants.getColor()) + "\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  }\n" +
                    "}";

            String itemsSlimeballContent = "{\n" +
                    "  \"model\": {\n" +
                    "    \"type\": \"minecraft:model\",\n" +
                    "    \"model\": \"productiveslimes:item/" + variants.getName() + "_slimeball\",\n" +
                    "    \"tints\": [\n" +
                    "      {\n" +
                    "        \"type\": \"minecraft:constant\",\n" +
                    "        \"value\": " + ARGB.opaque(variants.getColor()) + "\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  }\n" +
                    "}";

            resourceData.put(blockstatePath, blockstateContent.getBytes(StandardCharsets.UTF_8));
            resourceData.put(modelPath, blockModelContent.getBytes(StandardCharsets.UTF_8));
            resourceData.put(bucketModelPath, bucketModelContent.getBytes(StandardCharsets.UTF_8));
            resourceData.put(slimeBlockModelPath, slimeBlockModelContent.getBytes(StandardCharsets.UTF_8));
            resourceData.put(dnaModelPath, dnaModelContent.getBytes(StandardCharsets.UTF_8));
            resourceData.put(spawnEggModelPath, spawnEggModelContent.getBytes(StandardCharsets.UTF_8));
            resourceData.put(slimeballModelPath, slimeballModelContent.getBytes(StandardCharsets.UTF_8));
            resourceData.put(itemsBucketPath, itemsBucketContent.getBytes(StandardCharsets.UTF_8));
            resourceData.put(itemsSlimeBlockPath, itemsSlimeBlockContent.getBytes(StandardCharsets.UTF_8));
            resourceData.put(itemsDnaPath, itemsDnaContent.getBytes(StandardCharsets.UTF_8));
            resourceData.put(itemsSpawnEggPath, itemsSpawnEggContent.getBytes(StandardCharsets.UTF_8));
            resourceData.put(itemsSlimeballPath, itemsSlimeballContent.getBytes(StandardCharsets.UTF_8));
        }

        // Convert langJson map to JSON string
        String langJsonContent = new GsonBuilder().setPrettyPrinting().create().toJson(langJson);

        // Add language file to resource data
        String langFilePath = "assets/productiveslimes/lang/en_us.json";
        resourceData.put(langFilePath, langJsonContent.getBytes(StandardCharsets.UTF_8));
    }

    private static void generateDataPackInMemory() {
        dataPackResources.clear();
        addPackMcmeta();
        generateSlimeballTag();
        generateDnaTag();
        generateSlimeBlockLootTable();
        generateCraftingRecipe();
        generateModRecipe();
    }
    private static void addPackMcmeta() {
        String packMcmetaContent = "{\n" +
                "  \"pack\": {\n" +
                "    \"pack_format\": 57,\n" + // Adjust pack_format according to Minecraft version
                "    \"description\": \"Productive Slimes Generated Data Pack\"\n" +
                "  }\n" +
                "}";
        dataPackResources.put("pack.mcmeta", packMcmetaContent.getBytes(StandardCharsets.UTF_8));
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

        String tagPath = "data/c/tags/item/slime_balls.json";
        dataPackResources.put(tagPath, jsonContent.getBytes(StandardCharsets.UTF_8));
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

        String tagPath = "data/productiveslimes/tags/item/dna_item.json";
        dataPackResources.put(tagPath, jsonContent.getBytes(StandardCharsets.UTF_8));
    }

    private static void generateSlimeBlockLootTable(){
        for (CustomVariants variants : getLoadedTiers()){
            String lootTablePath = "data/productiveslimes/loot_table/blocks/" + variants.getName() + "_slime_block.json";

            JsonObject lootTableObj = new JsonObject();
            lootTableObj.addProperty("type", "minecraft:block");

            JsonArray poolsArray = new JsonArray();
            JsonObject poolObj = new JsonObject();

            poolObj.addProperty("bonus_rolls", 0.0);

            JsonArray conditionsArray = new JsonArray();
            JsonObject conditionObj = new JsonObject();
            conditionObj.addProperty("condition", "minecraft:survives_explosion");
            conditionsArray.add(conditionObj);
            poolObj.add("conditions", conditionsArray);

            JsonArray entriesArray = new JsonArray();
            JsonObject entryObj = new JsonObject();
            entryObj.addProperty("type", "minecraft:item");
            entryObj.addProperty("name", "productiveslimes:" + variants.getName() + "_slime_block");
            entriesArray.add(entryObj);
            poolObj.add("entries", entriesArray);

            poolObj.addProperty("rolls", 1.0);

            poolsArray.add(poolObj);
            lootTableObj.add("pools", poolsArray);

            lootTableObj.addProperty("random_sequence", "productiveslimes:blocks/" + variants.getName() + "_slime_block");

            String lootTableJson = new GsonBuilder().setPrettyPrinting().create().toJson(lootTableObj);
            dataPackResources.put(lootTablePath, lootTableJson.getBytes(StandardCharsets.UTF_8));
        }
    }

    public static void generateCraftingRecipe(){
        for (CustomVariants variant : loadedVariants){
            slimeballToSlimeBlock(variant.getName());
            slimeBlockToSlimeball(variant.getName());
        }
    }

    private static void slimeballToSlimeBlock(String name){
        String recipePath = "data/minecraft/recipe/" + name + "_slimeball_to_block.json";

        JsonObject recipeObject = new JsonObject();
        recipeObject.addProperty("type", "minecraft:crafting_shaped");
        recipeObject.addProperty("category", "building");

        JsonObject keyObject = new JsonObject();
        keyObject.addProperty("A", "productiveslimes:" + name + "_slimeball");
        recipeObject.add("key", keyObject);

        JsonArray patternArray = new JsonArray();
        patternArray.add("AAA");
        patternArray.add("AAA");
        patternArray.add("AAA");
        recipeObject.add("pattern", patternArray);

        JsonObject resultObject = new JsonObject();
        resultObject.addProperty("count", 1);
        resultObject.addProperty("id", "productiveslimes:" + name + "_slime_block");
        recipeObject.add("result", resultObject);

        String recipeJson = new GsonBuilder().setPrettyPrinting().create().toJson(recipeObject);
        dataPackResources.put(recipePath, recipeJson.getBytes(StandardCharsets.UTF_8));
    }

    private static void slimeBlockToSlimeball(String name){
        String recipePath = "data/minecraft/recipe/" + name + "_slime_block_to_ball.json";

        JsonObject recipeObject = new JsonObject();
        recipeObject.addProperty("type", "minecraft:crafting_shapeless");
        recipeObject.addProperty("category", "misc");

        JsonArray ingredients = new JsonArray();
        ingredients.add("productiveslimes:" + name + "_slime_block");
        recipeObject.add("ingredients", ingredients);

        JsonObject result = new JsonObject();
        result.addProperty("count", 9);
        result.addProperty("id", "productiveslimes:" + name + "_slimeball");
        recipeObject.add("result", result);

        String recipeJson = new GsonBuilder().setPrettyPrinting().create().toJson(recipeObject);
        dataPackResources.put(recipePath, recipeJson.getBytes(StandardCharsets.UTF_8));
    }

    private static void generateModRecipe(){
        for (CustomVariants variant : loadedVariants){
            meltingRecipeBlock(variant.getName());
            meltingRecipeBall(variant.getName());
            solidingRecipe(variant);
            dnaExtracting(variant);
            dnaSynthesizingSelf(variant);
            dnaSynthesizing(variant);
        }
    }

    private static void meltingRecipeBlock(String name){
        String recipePath = "data/productiveslimes/recipe/melting/" + name + "_slime_block_melting.json";

        JsonObject recipeObj = new JsonObject();
        recipeObj.addProperty("type", "productiveslimes:melting");
        recipeObj.addProperty("energy", 200);

        JsonObject ingredientsObj = new JsonObject();
        ingredientsObj.addProperty("count", 2);
        ingredientsObj.addProperty("ingredient", "productiveslimes:" + name + "_slime_block");
        recipeObj.add("ingredients", ingredientsObj);

        JsonArray outputArray = new JsonArray();
        JsonObject outputObj = new JsonObject();
        outputObj.addProperty("count", 5);
        outputObj.addProperty("id", "productiveslimes:molten_" + name + "_bucket");
        outputArray.add(outputObj);
        recipeObj.add("output", outputArray);

        String recipeJson = new GsonBuilder().setPrettyPrinting().create().toJson(recipeObj);
        dataPackResources.put(recipePath, recipeJson.getBytes(StandardCharsets.UTF_8));
    }

    private static void meltingRecipeBall(String name){
        String recipePath = "data/productiveslimes/recipe/melting/" + name + "_slimeball_melting.json";

        JsonObject recipeObj = new JsonObject();
        recipeObj.addProperty("type", "productiveslimes:melting");
        recipeObj.addProperty("energy", 200);

        JsonObject ingredientsObj = new JsonObject();
        ingredientsObj.addProperty("count", 4);
        ingredientsObj.addProperty("ingredient", "productiveslimes:" + name + "_slimeball");
        recipeObj.add("ingredients", ingredientsObj);

        JsonArray outputArray = new JsonArray();
        JsonObject outputObj = new JsonObject();
        outputObj.addProperty("count", 1);
        outputObj.addProperty("id", "productiveslimes:molten_" + name + "_bucket");
        outputArray.add(outputObj);
        recipeObj.add("output", outputArray);

        String recipeJson = new GsonBuilder().setPrettyPrinting().create().toJson(recipeObj);
        dataPackResources.put(recipePath, recipeJson.getBytes(StandardCharsets.UTF_8));
    }

    private static void solidingRecipe(CustomVariants variant){
        String recipePath = "data/productiveslimes/recipe/soliding/molten_" + variant.getName() + "_bucket_soliding.json";

        JsonObject recipeObj = new JsonObject();
        recipeObj.addProperty("type", "productiveslimes:soliding");
        recipeObj.addProperty("energy", 200);

        JsonArray ingredientsArray = new JsonArray();
        ingredientsArray.add("productiveslimes:molten_" + variant.getName() + "_bucket");
        recipeObj.add("ingredients", ingredientsArray);

        recipeObj.addProperty("inputCount", 1);

        JsonArray outputArray = new JsonArray();

        JsonObject output1 = new JsonObject();
        output1.addProperty("count", variant.getSolidingOutputCount());
        output1.addProperty("id", variant.getSolidingOutput());
        outputArray.add(output1);

        JsonObject output2 = new JsonObject();
        output2.addProperty("count", 1);
        output2.addProperty("id", "minecraft:bucket");
        outputArray.add(output2);

        recipeObj.add("output", outputArray);

        String recipeJson = new GsonBuilder().setPrettyPrinting().create().toJson(recipeObj);
        dataPackResources.put(recipePath, recipeJson.getBytes(StandardCharsets.UTF_8));
    }

    private static void dnaExtracting(CustomVariants variant){
        String recipePath = "data/productiveslimes/recipe/dna_extracting/" + variant.getName() + "_slimeball_dna_extracting.json";

        JsonObject recipeObj = new JsonObject();
        recipeObj.addProperty("type", "productiveslimes:dna_extracting");
        recipeObj.addProperty("energy", 400);

        JsonArray ingredientsArray = new JsonArray();
        ingredientsArray.add("productiveslimes:" + variant.getName() + "_slimeball");
        recipeObj.add("ingredients", ingredientsArray);

        recipeObj.addProperty("inputCount", 1);

        JsonArray outputArray = new JsonArray();

        JsonObject output1 = new JsonObject();
        output1.addProperty("count", 1);
        output1.addProperty("id", "productiveslimes:" + variant.getName() + "_slime_dna");
        outputArray.add(output1);

        JsonObject output2 = new JsonObject();
        output2.addProperty("count", 1);
        output2.addProperty("id", "minecraft:slime_ball");
        outputArray.add(output2);

        recipeObj.add("output", outputArray);

        recipeObj.addProperty("outputChance", variant.getDnaOutputChance());

        String recipeJson = new GsonBuilder().setPrettyPrinting().create().toJson(recipeObj);
        dataPackResources.put(recipePath, recipeJson.getBytes(StandardCharsets.UTF_8));
    }

    private static void dnaSynthesizingSelf(CustomVariants variant){
        String recipePath = "data/productiveslimes/recipe/dna_synthesizing/" + variant.getName() + "_slime_spawn_egg_synthesizing_self.json";

        JsonObject recipeObj = new JsonObject();
        recipeObj.addProperty("type", "productiveslimes:dna_synthesizing");
        recipeObj.addProperty("energy", 600);

        JsonArray ingredientsArray = new JsonArray();

        JsonObject ingredient1 = new JsonObject();
        ingredient1.addProperty("count", 1);
        ingredient1.addProperty("ingredient", "productiveslimes:" + variant.getName() + "_slime_dna");
        ingredientsArray.add(ingredient1);

        JsonObject ingredient2 = new JsonObject();
        ingredient2.addProperty("count", 1);
        ingredient2.addProperty("ingredient", "productiveslimes:" + variant.getName() + "_slime_dna");
        ingredientsArray.add(ingredient2);

        JsonObject ingredient3 = new JsonObject();
        ingredient3.addProperty("count", 2);
        ingredient3.addProperty("ingredient", variant.getSynthesizingInputItem());
        ingredientsArray.add(ingredient3);

        recipeObj.add("ingredients", ingredientsArray);

        JsonArray outputArray = new JsonArray();
        JsonObject outputObj = new JsonObject();
        outputObj.addProperty("count", 1);
        outputObj.addProperty("id", "productiveslimes:" + variant.getName() + "_slime_spawn_egg");
        outputArray.add(outputObj);

        recipeObj.add("output", outputArray);

        String recipeJson = new GsonBuilder().setPrettyPrinting().create().toJson(recipeObj);
        dataPackResources.put(recipePath, recipeJson.getBytes(StandardCharsets.UTF_8));
    }

    private static void dnaSynthesizing(CustomVariants variant){
        String recipePath = "data/productiveslimes/recipe/dna_synthesizing/" + variant.getName() + "_slime_spawn_egg_synthesizing.json";

        JsonObject recipeObj = new JsonObject();
        recipeObj.addProperty("type", "productiveslimes:dna_synthesizing");
        recipeObj.addProperty("energy", 600);

        JsonArray ingredientsArray = new JsonArray();

        JsonObject ingredient1 = new JsonObject();
        ingredient1.addProperty("count", 1);
        ingredient1.addProperty("ingredient", variant.getSynthesizingInputDna1());
        ingredientsArray.add(ingredient1);

        JsonObject ingredient2 = new JsonObject();
        ingredient2.addProperty("count", 1);
        ingredient2.addProperty("ingredient", variant.getSynthesizingInputDna2());
        ingredientsArray.add(ingredient2);

        JsonObject ingredient3 = new JsonObject();
        ingredient3.addProperty("count", 4);
        ingredient3.addProperty("ingredient", variant.getSynthesizingInputItem());
        ingredientsArray.add(ingredient3);

        recipeObj.add("ingredients", ingredientsArray);

        JsonArray outputArray = new JsonArray();
        JsonObject outputObj = new JsonObject();
        outputObj.addProperty("count", 1);
        outputObj.addProperty("id", "productiveslimes:" + variant.getName() + "_slime_spawn_egg");
        outputArray.add(outputObj);
        recipeObj.add("output", outputArray);

        String recipeJson = new GsonBuilder().setPrettyPrinting().create().toJson(recipeObj);
        dataPackResources.put(recipePath, recipeJson.getBytes(StandardCharsets.UTF_8));
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
