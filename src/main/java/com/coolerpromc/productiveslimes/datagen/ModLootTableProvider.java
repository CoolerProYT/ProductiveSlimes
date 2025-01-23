package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.datagen.loot.ModBlockLootTables;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.loot.*;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        ImmutableList.Builder<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> builder = new ImmutableList.Builder<>();
        builder.add(Pair.of(ModBlockLootTables::new, LootParameterSets.ALL_PARAMS));

        return builder.build();
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationTracker) {
        map.forEach((key, lootTable) -> {
            LootTableManager.validate(validationTracker, key, lootTable);
        });
    }
}