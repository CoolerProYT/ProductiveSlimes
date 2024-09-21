package com.coolerpromc.productiveslimes.datagen.loot;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
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

        dropSelf(ModBlocks.DIRT_SLIME_BLOCK.get());
        dropSelf(ModBlocks.STONE_SLIME_BLOCK.get());
        dropSelf(ModBlocks.COPPER_SLIME_BLOCK.get());
        dropSelf(ModBlocks.IRON_SLIME_BLOCK.get());
        dropSelf(ModBlocks.GOLD_SLIME_BLOCK.get());
        dropSelf(ModBlocks.DIAMOND_SLIME_BLOCK.get());
        dropSelf(ModBlocks.NETHERITE_SLIME_BLOCK.get());
        dropSelf(ModBlocks.LAPIS_SLIME_BLOCK.get());
        dropSelf(ModBlocks.REDSTONE_SLIME_BLOCK.get());
        dropSelf(ModBlocks.OAK_SLIME_BLOCK.get());
        dropSelf(ModBlocks.SAND_SLIME_BLOCK.get());
        dropSelf(ModBlocks.ANDESITE_SLIME_BLOCK.get());
        dropSelf(ModBlocks.SNOW_SLIME_BLOCK.get());
        dropSelf(ModBlocks.ICE_SLIME_BLOCK.get());
        dropSelf(ModBlocks.MUD_SLIME_BLOCK.get());
        dropSelf(ModBlocks.CLAY_SLIME_BLOCK.get());
        dropSelf(ModBlocks.RED_SAND_SLIME_BLOCK.get());
        dropSelf(ModBlocks.MOSS_SLIME_BLOCK.get());
        dropSelf(ModBlocks.DEEPSLATE_SLIME_BLOCK.get());
        dropSelf(ModBlocks.GRANITE_SLIME_BLOCK.get());
        dropSelf(ModBlocks.DIORITE_SLIME_BLOCK.get());
        dropSelf(ModBlocks.CALCITE_SLIME_BLOCK.get());
        dropSelf(ModBlocks.TUFF_SLIME_BLOCK.get());
        dropSelf(ModBlocks.DRIPSTONE_SLIME_BLOCK.get());
        dropSelf(ModBlocks.PRISMARINE_SLIME_BLOCK.get());
        dropSelf(ModBlocks.MAGMA_SLIME_BLOCK.get());
        dropSelf(ModBlocks.OBSIDIAN_SLIME_BLOCK.get());
        dropSelf(ModBlocks.NETHERRACK_SLIME_BLOCK.get());
        dropSelf(ModBlocks.SOUL_SAND_SLIME_BLOCK.get());
        dropSelf(ModBlocks.SOUL_SOIL_SLIME_BLOCK.get());
        dropSelf(ModBlocks.BLACKSTONE_SLIME_BLOCK.get());
        dropSelf(ModBlocks.BASALT_SLIME_BLOCK.get());
        dropSelf(ModBlocks.ENDSTONE_SLIME_BLOCK.get());
        dropSelf(ModBlocks.QUARTZ_SLIME_BLOCK.get());
        dropSelf(ModBlocks.GLOWSTONE_SLIME_BLOCK.get());
        dropSelf(ModBlocks.AMETHYST_SLIME_BLOCK.get());
        dropSelf(ModBlocks.BROWN_MUSHROOM_SLIME_BLOCK.get());
        dropSelf(ModBlocks.RED_MUSHROOM_SLIME_BLOCK.get());
        dropSelf(ModBlocks.CACTUS_SLIME_BLOCK.get());
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
