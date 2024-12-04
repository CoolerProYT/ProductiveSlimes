package com.coolerpromc.productiveslimes.util;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.block.custom.SlimeBlock;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.item.custom.BucketItem;
import com.coolerpromc.productiveslimes.item.custom.DnaItem;
import com.coolerpromc.productiveslimes.item.custom.SlimeballItem;
import com.coolerpromc.productiveslimes.item.custom.SpawnEggItem;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
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
        for (String name : ModTierLists.TIER_NAMES) {
            ModTiers tiers = ModTierLists.getTierByName(name);

            Path path = Paths.get("assets/productiveslimes/items/" + name + "_slime_block.json");

            String content = "{\n" +
                    "  \"model\": {\n" +
                    "    \"type\": \"minecraft:model\",\n" +
                    "    \"model\": \"productiveslimes:item/" + name + "_slime_block\",\n" +
                    "    \"tints\": [\n" +
                    "      {\n" +
                    "        \"type\": \"minecraft:constant\",\n" +
                    "        \"value\": " + tiers.getOpaqueColor() + "\n" +
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

    private static void registerSlimeball(){
        for (String name : ModTierLists.TIER_NAMES) {
            ModTiers tiers = ModTierLists.getTierByName(name);

            Path path = Paths.get("assets/productiveslimes/items/" + name + "_slimeball.json");

            String content = "{\n" +
                    "  \"model\": {\n" +
                    "    \"type\": \"minecraft:model\",\n" +
                    "    \"model\": \"productiveslimes:item/" + name + "_slimeball\",\n" +
                    "    \"tints\": [\n" +
                    "      {\n" +
                    "        \"type\": \"minecraft:constant\",\n" +
                    "        \"value\": " + tiers.getOpaqueColor() + "\n" +
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

    private static void registerDna(){
        for (String name : ModTierLists.TIER_NAMES) {
            ModTiers tiers = ModTierLists.getTierByName(name);

            Path path = Paths.get("assets/productiveslimes/items/" + name + "_slime_dna.json");

            String content = "{\n" +
                    "  \"model\": {\n" +
                    "    \"type\": \"minecraft:model\",\n" +
                    "    \"model\": \"productiveslimes:item/" + name + "_slime_dna\",\n" +
                    "    \"tints\": [\n" +
                    "      {\n" +
                    "        \"type\": \"minecraft:constant\",\n" +
                    "        \"value\": " + tiers.getOpaqueColor() + "\n" +
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

    private static void registerSpawnEgg(){
        for (String name : ModTierLists.TIER_NAMES) {
            ModTiers tiers = ModTierLists.getTierByName(name);

            Path path = Paths.get("assets/productiveslimes/items/" + name + "_slime_spawn_egg.json");

            String content = "{\n" +
                    "  \"model\": {\n" +
                    "    \"type\": \"minecraft:model\",\n" +
                    "    \"model\": \"productiveslimes:item/" + name + "_slime_spawn_egg\",\n" +
                    "    \"tints\": [\n" +
                    "      {\n" +
                    "        \"type\": \"minecraft:constant\",\n" +
                    "        \"value\": " + tiers.getOpaqueColor() + "\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"type\": \"minecraft:constant\",\n" +
                    "        \"value\": " + tiers.getOpaqueColor() + "\n" +
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

    private static void registerBucket(){
        for (String name : ModTierLists.TIER_NAMES) {
            ModTiers tiers = ModTierLists.getTierByName(name);

            Path path = Paths.get("assets/productiveslimes/items/molten_" + name + "_bucket.json");

            String content = "{\n" +
                    "  \"model\": {\n" +
                    "    \"type\": \"minecraft:model\",\n" +
                    "    \"model\": \"productiveslimes:item/molten_" + name + "_bucket\",\n" +
                    "    \"tints\": [\n" +
                    "      {\n" +
                    "        \"type\": \"minecraft:constant\",\n" +
                    "        \"value\": -1\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"type\": \"minecraft:constant\",\n" +
                    "        \"value\": " + tiers.getOpaqueColor() + "\n" +
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
}
