package com.coolerpromc.productiveslimes.config;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.custom.SlimeBlock;
import com.coolerpromc.productiveslimes.config.fluid.FluidResources;
import com.coolerpromc.productiveslimes.config.fluid.ModBaseFluidType;
import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import com.coolerpromc.productiveslimes.entity.slime.Slime;
import com.coolerpromc.productiveslimes.item.custom.DnaItem;
import com.coolerpromc.productiveslimes.item.custom.SlimeballItem;
import com.coolerpromc.productiveslimes.util.InMemoryDataPack;
import com.coolerpromc.productiveslimes.util.InMemoryResourcePack;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

        // Add your pack to the pack repository
        server.getPackRepository().addPackFinder((consumer) -> consumer.accept(pack));
        // Reload data packs to include your new pack
        List<Pack> packs = new ArrayList<>(server.getPackRepository().getSelectedPacks());
        packs.add(pack);
        server.reloadResources(packs.stream().map(Pack::getId).collect(Collectors.toList()));
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
        DeferredItem<Item> item = ITEMS.registerItem(itemName, properties -> new SpawnEggItem(getSlimeForVariant(variant.getName()).get(), variant.getColor(), variant.getColor(), properties));

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
                    "  \"elements\": [\n" +
                    "    {\n" +
                    "      \"faces\": {\n" +
                    "        \"down\": {\n" +
                    "          \"texture\": \"#layer1\",\n" +
                    "          \"tintindex\": 1\n" +
                    "        },\n" +
                    "        \"east\": {\n" +
                    "          \"texture\": \"#layer1\",\n" +
                    "          \"tintindex\": 1\n" +
                    "        },\n" +
                    "        \"north\": {\n" +
                    "          \"texture\": \"#layer1\",\n" +
                    "          \"tintindex\": 1\n" +
                    "        },\n" +
                    "        \"south\": {\n" +
                    "          \"texture\": \"#layer1\",\n" +
                    "          \"tintindex\": 1\n" +
                    "        },\n" +
                    "        \"up\": {\n" +
                    "          \"texture\": \"#layer1\",\n" +
                    "          \"tintindex\": 1\n" +
                    "        },\n" +
                    "        \"west\": {\n" +
                    "          \"texture\": \"#layer1\",\n" +
                    "          \"tintindex\": 1\n" +
                    "        }\n" +
                    "      },\n" +
                    "      \"from\": [\n" +
                    "        0,\n" +
                    "        0,\n" +
                    "        0\n" +
                    "      ],\n" +
                    "      \"to\": [\n" +
                    "        16,\n" +
                    "        16,\n" +
                    "        16\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  ],\n" +
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
                    "  \"elements\": [\n" +
                    "    {\n" +
                    "      \"faces\": {\n" +
                    "        \"down\": {\n" +
                    "          \"texture\": \"#layer0\",\n" +
                    "          \"tintindex\": 0\n" +
                    "        },\n" +
                    "        \"east\": {\n" +
                    "          \"texture\": \"#layer0\",\n" +
                    "          \"tintindex\": 0\n" +
                    "        },\n" +
                    "        \"north\": {\n" +
                    "          \"texture\": \"#layer0\",\n" +
                    "          \"tintindex\": 0\n" +
                    "        },\n" +
                    "        \"south\": {\n" +
                    "          \"texture\": \"#layer0\",\n" +
                    "          \"tintindex\": 0\n" +
                    "        },\n" +
                    "        \"up\": {\n" +
                    "          \"texture\": \"#layer0\",\n" +
                    "          \"tintindex\": 0\n" +
                    "        },\n" +
                    "        \"west\": {\n" +
                    "          \"texture\": \"#layer0\",\n" +
                    "          \"tintindex\": 0\n" +
                    "        }\n" +
                    "      },\n" +
                    "      \"from\": [\n" +
                    "        0,\n" +
                    "        0,\n" +
                    "        0\n" +
                    "      ],\n" +
                    "      \"to\": [\n" +
                    "        16,\n" +
                    "        16,\n" +
                    "        16\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"textures\": {\n" +
                    "    \"layer0\": \"productiveslimes:item/template_dna\"\n" +
                    "  }\n" +
                    "}";

            String spawnEggModelContent = "{\n" +
                    "  \"parent\": \"minecraft:item/template_spawn_egg\"\n" +
                    "}";

            String slimeballModelContent = "{\n" +
                    "  \"parent\": \"minecraft:item/generated\",\n" +
                    "  \"elements\": [\n" +
                    "    {\n" +
                    "      \"faces\": {\n" +
                    "        \"down\": {\n" +
                    "          \"texture\": \"#layer0\",\n" +
                    "          \"tintindex\": 0\n" +
                    "        },\n" +
                    "        \"east\": {\n" +
                    "          \"texture\": \"#layer0\",\n" +
                    "          \"tintindex\": 0\n" +
                    "        },\n" +
                    "        \"north\": {\n" +
                    "          \"texture\": \"#layer0\",\n" +
                    "          \"tintindex\": 0\n" +
                    "        },\n" +
                    "        \"south\": {\n" +
                    "          \"texture\": \"#layer0\",\n" +
                    "          \"tintindex\": 0\n" +
                    "        },\n" +
                    "        \"up\": {\n" +
                    "          \"texture\": \"#layer0\",\n" +
                    "          \"tintindex\": 0\n" +
                    "        },\n" +
                    "        \"west\": {\n" +
                    "          \"texture\": \"#layer0\",\n" +
                    "          \"tintindex\": 0\n" +
                    "        }\n" +
                    "      },\n" +
                    "      \"from\": [\n" +
                    "        0,\n" +
                    "        0,\n" +
                    "        0\n" +
                    "      ],\n" +
                    "      \"to\": [\n" +
                    "        16,\n" +
                    "        16,\n" +
                    "        16\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"textures\": {\n" +
                    "    \"layer0\": \"productiveslimes:item/template_slimeball\"\n" +
                    "  }\n" +
                    "}";

            resourceData.put(blockstatePath, blockstateContent.getBytes(StandardCharsets.UTF_8));
            resourceData.put(modelPath, blockModelContent.getBytes(StandardCharsets.UTF_8));
            resourceData.put(bucketModelPath, bucketModelContent.getBytes(StandardCharsets.UTF_8));
            resourceData.put(slimeBlockModelPath, slimeBlockModelContent.getBytes(StandardCharsets.UTF_8));
            resourceData.put(dnaModelPath, dnaModelContent.getBytes(StandardCharsets.UTF_8));
            resourceData.put(spawnEggModelPath, spawnEggModelContent.getBytes(StandardCharsets.UTF_8));
            resourceData.put(slimeballModelPath, slimeballModelContent.getBytes(StandardCharsets.UTF_8));
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
            String lootTable = "{\n" +
                    "  \"type\": \"minecraft:block\",\n" +
                    "  \"pools\": [\n" +
                    "    {\n" +
                    "      \"bonus_rolls\": 0.0,\n" +
                    "      \"conditions\": [\n" +
                    "        {\n" +
                    "          \"condition\": \"minecraft:survives_explosion\"\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"entries\": [\n" +
                    "        {\n" +
                    "          \"type\": \"minecraft:item\",\n" +
                    "          \"name\": \"productiveslimes:" + variants.getName() + "_slime_block\"\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"rolls\": 1.0\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"random_sequence\": \"productiveslimes:blocks/" + variants.getName() + "_slime_block\"\n" +
                    "}";
            dataPackResources.put(lootTablePath, lootTable.getBytes(StandardCharsets.UTF_8));
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

        String recipe = "{\n" +
                "  \"type\": \"minecraft:crafting_shaped\",\n" +
                "  \"category\": \"building\",\n" +
                "  \"key\": {\n" +
                "    \"A\": \"productiveslimes:" + name + "_slimeball\"\n" +
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
                "}";

        dataPackResources.put(recipePath, recipe.getBytes(StandardCharsets.UTF_8));
    }

    private static void slimeBlockToSlimeball(String name){
        String recipePath = "data/minecraft/recipe/" + name + "_slime_block_to_ball.json";

        String recipe = "{\n" +
                "  \"type\": \"minecraft:crafting_shapeless\",\n" +
                "  \"category\": \"misc\",\n" +
                "  \"ingredients\": [\n" +
                "    \"productiveslimes:" + name + "_slime_block\"\n" +
                "  ],\n" +
                "  \"result\": {\n" +
                "    \"count\": 9,\n" +
                "    \"id\": \"productiveslimes:" + name + "_slimeball\"\n" +
                "  }\n" +
                "}";

        dataPackResources.put(recipePath, recipe.getBytes(StandardCharsets.UTF_8));
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

        String recipe = "{\n" +
                "  \"type\": \"productiveslimes:melting\",\n" +
                "  \"energy\": 200,\n" +
                "  \"ingredients\": [\n" +
                "      \"productiveslimes:" + name + "_slime_block\"\n" +
                "  ],\n" +
                "  \"inputCount\": 2,\n" +
                "  \"output\": [\n" +
                "    {\n" +
                "      \"count\": 5,\n" +
                "      \"id\": \"productiveslimes:molten_" + name + "_bucket\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        dataPackResources.put(recipePath, recipe.getBytes(StandardCharsets.UTF_8));
    }

    private static void meltingRecipeBall(String name){
        String recipePath = "data/productiveslimes/recipe/melting/" + name + "_slimeball_melting.json";

        String recipe = "{\n" +
                "  \"type\": \"productiveslimes:melting\",\n" +
                "  \"energy\": 200,\n" +
                "  \"ingredients\": [\n" +
                "      \"productiveslimes:" + name + "_slimeball\"\n" +
                "  ],\n" +
                "  \"inputCount\": 4,\n" +
                "  \"output\": [\n" +
                "    {\n" +
                "      \"count\": 1,\n" +
                "      \"id\": \"productiveslimes:molten_" + name + "_bucket\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        dataPackResources.put(recipePath, recipe.getBytes(StandardCharsets.UTF_8));
    }

    private static void solidingRecipe(CustomVariants variant){
        String recipePath = "data/productiveslimes/recipe/soliding/molten_" + variant.getName() + "_bucket_soliding.json";

        String recipe = "{\n" +
                "  \"type\": \"productiveslimes:soliding\",\n" +
                "  \"energy\": 200,\n" +
                "  \"ingredients\": [\n" +
                "      \"productiveslimes:molten_" + variant.getName() + "_bucket\"\n" +
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
                "}";

        dataPackResources.put(recipePath, recipe.getBytes(StandardCharsets.UTF_8));
    }

    private static void dnaExtracting(CustomVariants variant){
        String recipePath = "data/productiveslimes/recipe/dna_extracting/" + variant.getName() + "_slimeball_dna_extracting.json";

        String recipe = "{\n" +
                "  \"type\": \"productiveslimes:dna_extracting\",\n" +
                "  \"energy\": 400,\n" +
                "  \"ingredients\": [\n" +
                "    \"productiveslimes:" + variant.getName() + "_slimeball\"\n" +
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
                "}";

        dataPackResources.put(recipePath, recipe.getBytes(StandardCharsets.UTF_8));
    }

    private static void dnaSynthesizingSelf(CustomVariants variant){
        String recipePath = "data/productiveslimes/recipe/dna_synthesizing/" + variant.getName() + "_slime_spawn_egg_synthesizing_self.json";

        String recipe = "{\n" +
                "  \"type\": \"productiveslimes:dna_synthesizing\",\n" +
                "  \"energy\": 600,\n" +
                "  \"ingredients\": [\n" +
                "      \"productiveslimes:" + variant.getName() + "_slime_dna\",\n" +
                "      \"productiveslimes:" + variant.getName() + "_slime_dna\",\n" +
                "      \"" + variant.getSynthesizingInputItem() + "\"\n" +
                "  ],\n" +
                "  \"inputCount\": 2,\n" +
                "  \"output\": [\n" +
                "    {\n" +
                "      \"count\": 1,\n" +
                "      \"id\": \"productiveslimes:" + variant.getName() + "_slime_spawn_egg\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        dataPackResources.put(recipePath, recipe.getBytes(StandardCharsets.UTF_8));
    }

    private static void dnaSynthesizing(CustomVariants variant){
        String recipePath = "data/productiveslimes/recipe/dna_synthesizer/" + variant.getName() + "_slime_spawn_egg_synthesizing.json";

        String recipe = "{\n" +
                "  \"type\": \"productiveslimes:dna_synthesizing\",\n" +
                "  \"energy\": 600,\n" +
                "  \"ingredients\": [\n" +
                "  \"" + variant.getSynthesizingInputDna1() + "\",\n" +
                "  \"" + variant.getSynthesizingInputDna2() + "\",\n" +
                "  \"" + variant.getSynthesizingInputItem() + "\"\n" +
                "  ],\n" +
                "  \"inputCount\": 4,\n" +
                "  \"output\": [\n" +
                "    {\n" +
                "      \"count\": 1,\n" +
                "      \"id\": \"productiveslimes:" + variant.getName() + "_slime_spawn_egg\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        dataPackResources.put(recipePath, recipe.getBytes(StandardCharsets.UTF_8));
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
