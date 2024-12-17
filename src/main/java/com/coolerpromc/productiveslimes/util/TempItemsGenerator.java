package com.coolerpromc.productiveslimes.util;

import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TempItemsGenerator {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void init() {
        registerBlock();
        registerSlimeball();
        registerDna();
        registerSpawnEgg();
        registerBucket();

        registerCustomItems("slime_squeezer");
        registerCustomItems("squeezer");
        registerCustomItems("slimy_log");
        registerCustomItems("stripped_slimy_log");
        registerCustomItems("slimy_wood");
        registerCustomItems("stripped_slimy_wood");
        registerCustomItems("slimy_planks");
        registerCustomItems("slimy_leaves");
        registerCustomItems("slimy_sapling");

        registerCustomItems("slimy_slab");
        registerCustomItems("slimy_stairs");
        registerCustomItems("slimy_pressure_plate");
        registerCustomItems("slimy_button");
        registerCustomItems("slimy_fence");
        registerCustomItems("slimy_fence_gate");
        registerCustomItems("slimy_door");
        registerCustomItems("slimy_trapdoor");

        registerCustomItems("slimy_stone_stairs");
        registerCustomItems("slimy_stone_slab");
        registerCustomItems("slimy_stone_pressure_plate");
        registerCustomItems("slimy_stone_button");

        registerCustomItems("slimy_cobblestone_stairs");
        registerCustomItems("slimy_cobblestone_slab");
        registerCustomItems("slimy_cobblestone_wall");

        registerCustomItems("slimy_cobbled_deepslate_stairs");
        registerCustomItems("slimy_cobbled_deepslate_slab");
        registerCustomItems("slimy_cobbled_deepslate_wall");

        registerCustomItems("slimeball_fragment");
    }

    private static void registerCustomItems(String id){
        Path path = Paths.get("assets/productiveslimes/items/" + id + ".json");

        String content = "{\n" +
                "  \"model\": {\n" +
                "    \"type\": \"minecraft:model\",\n" +
                "    \"model\": \"productiveslimes:item/" + id + "\"\n" +
                "  }\n" +
                "}\n";

        try{
            Files.createDirectories(path.getParent());
            Files.write(path, content.getBytes(StandardCharsets.UTF_8));
        }
        catch (IOException e){

        }
    }

    private static void registerBlock(){
        for (Tier tier : Tier.values()) {
            ModTiers tiers = ModTierLists.getTierByName(tier);

            Path path = Paths.get("assets/productiveslimes/items/" + tiers.name() + "_slime_block.json");

            String content = "{\n" +
                    "  \"model\": {\n" +
                    "    \"type\": \"minecraft:model\",\n" +
                    "    \"model\": \"productiveslimes:item/" + tiers.name() + "_slime_block\",\n" +
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
        for (Tier tier : Tier.values()) {
            ModTiers tiers = ModTierLists.getTierByName(tier);

            Path path = Paths.get("assets/productiveslimes/items/" + tiers.name() + "_slimeball.json");

            String content = "{\n" +
                    "  \"model\": {\n" +
                    "    \"type\": \"minecraft:model\",\n" +
                    "    \"model\": \"productiveslimes:item/" + tiers.name() + "_slimeball\",\n" +
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
        for (Tier tier : Tier.values()) {
            ModTiers tiers = ModTierLists.getTierByName(tier);

            Path path = Paths.get("assets/productiveslimes/items/" + tiers.name() + "_slime_dna.json");

            String content = "{\n" +
                    "  \"model\": {\n" +
                    "    \"type\": \"minecraft:model\",\n" +
                    "    \"model\": \"productiveslimes:item/" + tiers.name() + "_slime_dna\",\n" +
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
        for (Tier tier : Tier.values()) {
            ModTiers tiers = ModTierLists.getTierByName(tier);

            Path path = Paths.get("assets/productiveslimes/items/" + tiers.name() + "_slime_spawn_egg.json");

            String content = "{\n" +
                    "  \"model\": {\n" +
                    "    \"type\": \"minecraft:model\",\n" +
                    "    \"model\": \"productiveslimes:item/" + tiers.name() + "_slime_spawn_egg\",\n" +
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
        for (Tier tier : Tier.values()) {
            ModTiers tiers = ModTierLists.getTierByName(tier);

            Path path = Paths.get("assets/productiveslimes/items/molten_" + tiers.name() + "_bucket.json");

            String content = "{\n" +
                    "  \"model\": {\n" +
                    "    \"type\": \"minecraft:model\",\n" +
                    "    \"model\": \"productiveslimes:item/molten_" + tiers.name() + "_bucket\",\n" +
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
