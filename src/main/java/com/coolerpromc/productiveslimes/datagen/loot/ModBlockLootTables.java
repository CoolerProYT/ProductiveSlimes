package com.coolerpromc.productiveslimes.datagen.loot;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.MELTING_STATION.get());
        dropSelf(ModBlocks.LIQUID_SOLIDING_STATION.get());
        dropSelf(ModBlocks.ENERGY_GENERATOR.get());
        dropSelf(ModBlocks.CABLE.get());
        dropSelf(ModBlocks.DNA_EXTRACTOR.get());
        dropSelf(ModBlocks.DNA_SYNTHESIZER.get());
        dropSelf(ModBlocks.FLUID_TANK.get());
        dropSelf(ModBlocks.SLIME_SQUEEZER.get());
        dropSelf(ModBlocks.SLIME_NEST.get());
        dropSelf(ModBlocks.SLIMEBALL_COLLECTOR.get());

        add(ModBlocks.SLIMY_GRASS_BLOCK.get(), block -> createSingleItemTableWithSilkTouch(block, ModBlocks.SLIMY_DIRT.get()));
        dropSelf(ModBlocks.SLIMY_DIRT.get());
        add(ModBlocks.SLIMY_STONE.get(), block -> createSingleItemTableWithSilkTouch(block, ModBlocks.SLIMY_COBBLESTONE.get()));
        add(ModBlocks.SLIMY_DEEPSLATE.get(), block -> createSingleItemTableWithSilkTouch(block, ModBlocks.SLIMY_COBBLED_DEEPSLATE.get()));
        dropSelf(ModBlocks.SLIMY_COBBLESTONE.get());
        dropSelf(ModBlocks.SLIMY_COBBLED_DEEPSLATE.get());

        dropSelf(ModBlocks.SLIMY_LOG.get());
        dropSelf(ModBlocks.SLIMY_WOOD.get());
        dropSelf(ModBlocks.STRIPPED_SLIMY_LOG.get());
        dropSelf(ModBlocks.STRIPPED_SLIMY_WOOD.get());
        dropSelf(ModBlocks.SLIMY_PLANKS.get());
        dropSelf(ModBlocks.SLIMY_SAPLING.get());
        add(ModBlocks.SLIMY_LEAVES.get(), block -> createLeavesDrops(block, ModBlocks.SLIMY_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
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

        for (Tier tier : Tier.values()){
            ModTiers tiers = ModTierLists.getTierByName(tier);
            dropSelf(ModTierLists.getBlockByName(tiers.name()).get());
        }
    }

    protected LootTable.Builder createCopperLikeOreDrops(Block pBlock, Item item){
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock, (LootPoolEntryContainer.Builder)this.applyExplosionDecay(pBlock, LootItem.lootTableItem(Items.RAW_COPPER).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))).apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BuiltInRegistries.BLOCK.stream()
                .filter(block -> Optional.of(BuiltInRegistries.BLOCK.getKey(block))
                .filter(key -> key.getNamespace().equals(ProductiveSlimes.MODID)).isPresent()).collect(Collectors.toSet());
    }
}
