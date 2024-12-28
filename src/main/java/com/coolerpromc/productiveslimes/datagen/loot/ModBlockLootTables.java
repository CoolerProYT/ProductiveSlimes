package com.coolerpromc.productiveslimes.datagen.loot;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockLootTables extends BlockLoot {
    public ModBlockLootTables() {
    }

    @Override
    protected void addTables() {
        dropSelf(ModBlocks.CABLE.get());
        dropSelf(ModBlocks.SLIME_NEST.get());
        dropSelf(ModBlocks.SLIMEBALL_COLLECTOR.get());

        createSingleItemTableWithSilkTouch(ModBlocks.SLIMY_GRASS_BLOCK.get(), ModBlocks.SLIMY_GRASS_BLOCK.get());
        dropOther(ModBlocks.SLIMY_GRASS_BLOCK.get(), ModBlocks.SLIMY_DIRT.get());
        dropSelf(ModBlocks.SLIMY_DIRT.get());
        dropOther(ModBlocks.SLIMY_STONE.get(), ModBlocks.SLIMY_COBBLESTONE.get());
        dropOther(ModBlocks.SLIMY_DEEPSLATE.get(), ModBlocks.SLIMY_COBBLED_DEEPSLATE.get());
        dropSelf(ModBlocks.SLIMY_COBBLESTONE.get());
        dropSelf(ModBlocks.SLIMY_COBBLED_DEEPSLATE.get());

        dropSelf(ModBlocks.SLIMY_LOG.get());
        dropSelf(ModBlocks.SLIMY_WOOD.get());
        dropSelf(ModBlocks.STRIPPED_SLIMY_LOG.get());
        dropSelf(ModBlocks.STRIPPED_SLIMY_WOOD.get());
        dropSelf(ModBlocks.SLIMY_PLANKS.get());
        dropSelf(ModBlocks.SLIMY_SAPLING.get());

        add(ModBlocks.SLIMY_LEAVES.get(), block -> createLeavesDrops(block, ModBlocks.SLIMY_SAPLING.get(), 0.05F, 0.0625F, 0.083333336F, 0.1F));

        dropSelf(ModBlocks.SLIMY_STAIRS.get());
        add(ModBlocks.SLIMY_SLAB.get(), block -> createSlabItemTable(ModBlocks.SLIMY_SLAB.get()));
        dropSelf(ModBlocks.SLIMY_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.SLIMY_BUTTON.get());
        dropSelf(ModBlocks.SLIMY_FENCE.get());
        dropSelf(ModBlocks.SLIMY_FENCE_GATE.get());
        dropSelf(ModBlocks.SLIMY_TRAPDOOR.get());
        add(ModBlocks.SLIMY_DOOR.get(), block -> createDoorTable(ModBlocks.SLIMY_DOOR.get()));
        dropSelf(ModBlocks.SLIMY_STONE_STAIRS.get());
        add(ModBlocks.SLIMY_STONE_SLAB.get(), block -> createSlabItemTable(ModBlocks.SLIMY_STONE_SLAB.get()));
        dropSelf(ModBlocks.SLIMY_STONE_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.SLIMY_STONE_BUTTON.get());
        dropSelf(ModBlocks.SLIMY_COBBLESTONE_STAIRS.get());
        add(ModBlocks.SLIMY_COBBLESTONE_SLAB.get(), block -> createSlabItemTable(ModBlocks.SLIMY_COBBLESTONE_SLAB.get()));
        dropSelf(ModBlocks.SLIMY_COBBLESTONE_WALL.get());
        dropSelf(ModBlocks.SLIMY_COBBLED_DEEPSLATE_STAIRS.get());
        add(ModBlocks.SLIMY_COBBLED_DEEPSLATE_SLAB.get(), block -> createSlabItemTable(ModBlocks.SLIMY_COBBLED_DEEPSLATE_SLAB.get()));
        dropSelf(ModBlocks.SLIMY_COBBLED_DEEPSLATE_WALL.get());

        dropSelf(ModBlocks.ENERGY_SLIME_BLOCK.get());

        for(Tier tier : Tier.values()) {
            ModTiers modTiers = ModTierLists.getTierByName(tier);
            dropSelf(ModTierLists.getBlockByName(modTiers.name()).get());
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries() // Get all registered entries
                .stream() // Stream the wrapped objects
                .flatMap(RegistryObject::stream) // Get the object if available
                ::iterator; // Create the iterable
    }
}
