package com.coolerpromc.productiveslimes.util;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.block.custom.SlimeBlock;
import com.coolerpromc.productiveslimes.fluid.ModFluids;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.item.custom.BucketItem;
import com.coolerpromc.productiveslimes.item.custom.DnaItem;
import com.coolerpromc.productiveslimes.item.custom.SlimeballItem;
import com.coolerpromc.productiveslimes.item.custom.SpawnEggItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.util.ARGB;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;

public class TempItemsGenerator {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void init() {
        registerBlock();
        registerSlimeball();
        registerDna();
        registerSpawnEgg();
        registerBucket();
    }

    private static void registerBlock(){
        Field[] fields = ModBlocks.class.getFields();

        for (Field field : fields) {
            try {
                Object value = field.get(null);

                if (value instanceof Supplier<?> supplier) {
                    if (supplier.get() instanceof SlimeBlock slimeBlock) {
                        Path path = Paths.get("assets/productiveslimes/items/" + slimeBlock.getDescriptionId().substring(23) + ".json");

                        String content = "{\n" +
                                "  \"model\": {\n" +
                                "    \"type\": \"minecraft:model\",\n" +
                                "    \"model\": \"productiveslimes:item/" + slimeBlock.getDescriptionId().substring(23) + "\",\n" +
                                "    \"tints\": [\n" +
                                "      {\n" +
                                "        \"type\": \"minecraft:constant\",\n" +
                                "        \"value\": " + ARGB.opaque(slimeBlock.getColor()) + "\n" +
                                "      }\n" +
                                "    ]\n" +
                                "  }\n" +
                                "}";

                        try{
                            Files.createDirectories(path.getParent());
                            Files.write(path, content.getBytes(StandardCharsets.UTF_8));
                        }
                        catch (IOException e){

                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static void registerSlimeball(){
        Field[] fields = ModItems.class.getFields();

        for (Field field : fields) {
            try {
                Object value = field.get(null);

                if (value instanceof Supplier<?> supplier) {
                    if (supplier.get() instanceof SlimeballItem slimeballItem) {
                        Path path = Paths.get("assets/productiveslimes/items/" + slimeballItem.getDescriptionId().substring(22) + ".json");

                        String content = "{\n" +
                                "  \"model\": {\n" +
                                "    \"type\": \"minecraft:model\",\n" +
                                "    \"model\": \"productiveslimes:item/" + slimeballItem.getDescriptionId().substring(22) + "\",\n" +
                                "    \"tints\": [\n" +
                                "      {\n" +
                                "        \"type\": \"minecraft:constant\",\n" +
                                "        \"value\": " + ARGB.opaque(slimeballItem.getColor()) + "\n" +
                                "      }\n" +
                                "    ]\n" +
                                "  }\n" +
                                "}";

                        try{
                            Files.createDirectories(path.getParent());
                            Files.write(path, content.getBytes(StandardCharsets.UTF_8));
                        }
                        catch (IOException e){

                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static void registerDna(){
        Field[] fields = ModItems.class.getFields();

        for (Field field : fields) {
            try {
                Object value = field.get(null);

                if (value instanceof Supplier<?> supplier) {
                    if (supplier.get() instanceof DnaItem dnaItem) {
                        Path path = Paths.get("assets/productiveslimes/items/" + dnaItem.getDescriptionId().substring(22) + ".json");

                        String content = "{\n" +
                                "  \"model\": {\n" +
                                "    \"type\": \"minecraft:model\",\n" +
                                "    \"model\": \"productiveslimes:item/" + dnaItem.getDescriptionId().substring(22) + "\",\n" +
                                "    \"tints\": [\n" +
                                "      {\n" +
                                "        \"type\": \"minecraft:constant\",\n" +
                                "        \"value\": " + ARGB.opaque(dnaItem.getColor()) + "\n" +
                                "      }\n" +
                                "    ]\n" +
                                "  }\n" +
                                "}";

                        try{
                            Files.createDirectories(path.getParent());
                            Files.write(path, content.getBytes(StandardCharsets.UTF_8));
                        }
                        catch (IOException e){

                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static void registerSpawnEgg(){
        Field[] fields = ModItems.class.getFields();

        for (Field field : fields) {
            try {
                Object value = field.get(null);

                if (value instanceof Supplier<?> supplier) {
                    if (supplier.get() instanceof SpawnEggItem spawnEggItem) {
                        Path path = Paths.get("assets/productiveslimes/items/" + spawnEggItem.getDescriptionId().substring(22) + ".json");

                        String content = "{\n" +
                                "  \"model\": {\n" +
                                "    \"type\": \"minecraft:model\",\n" +
                                "    \"model\": \"productiveslimes:item/" + spawnEggItem.getDescriptionId().substring(22) + "\",\n" +
                                "    \"tints\": [\n" +
                                "      {\n" +
                                "        \"type\": \"minecraft:constant\",\n" +
                                "        \"value\": " + ARGB.opaque(spawnEggItem.getBg()) + "\n" +
                                "      },\n" +
                                "      {\n" +
                                "        \"type\": \"minecraft:constant\",\n" +
                                "        \"value\": " + ARGB.opaque(spawnEggItem.getFg()) + "\n" +
                                "      }\n" +
                                "    ]\n" +
                                "  }\n" +
                                "}";

                        try{
                            Files.createDirectories(path.getParent());
                            Files.write(path, content.getBytes(StandardCharsets.UTF_8));
                        }
                        catch (IOException e){

                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static void registerBucket(){
        Field[] fields = ModFluids.class.getFields();

        for (Field field : fields) {
            try {
                Object value = field.get(null);

                if (value instanceof Supplier<?> supplier) {
                    if (supplier.get() instanceof BucketItem bucketItem) {
                        Path path = Paths.get("assets/productiveslimes/items/" + bucketItem.getDescriptionId().substring(22) + ".json");

                        String content = "{\n" +
                                "  \"model\": {\n" +
                                "    \"type\": \"minecraft:model\",\n" +
                                "    \"model\": \"productiveslimes:item/" + bucketItem.getDescriptionId().substring(22) + "\",\n" +
                                "    \"tints\": [\n" +
                                "      {\n" +
                                "        \"type\": \"minecraft:constant\",\n" +
                                "        \"value\": -1\n" +
                                "      },\n" +
                                "      {\n" +
                                "        \"type\": \"minecraft:constant\",\n" +
                                "        \"value\": " + ARGB.opaque(bucketItem.getColor()) + "\n" +
                                "      }\n" +
                                "    ]\n" +
                                "  }\n" +
                                "}";

                        try{
                            Files.createDirectories(path.getParent());
                            Files.write(path, content.getBytes(StandardCharsets.UTF_8));
                        }
                        catch (IOException e){

                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
