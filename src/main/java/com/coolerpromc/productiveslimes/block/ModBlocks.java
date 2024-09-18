package com.coolerpromc.productiveslimes.block;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.custom.MeltingStationBlock;
import com.coolerpromc.productiveslimes.block.custom.SolidingStationBlock;
import com.coolerpromc.productiveslimes.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ProductiveSlimes.MODID);

    public static final DeferredBlock<Block> MELTING_STATION = registerBlock("melting_station",
            () -> new MeltingStationBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final DeferredBlock<Block> LIQUID_SOLIDING_STATION = registerBlock("soliding_station",
            () -> new SolidingStationBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final DeferredBlock<Block> DIRT_SLIME_BLOCK = registerBlock("dirt_slime_block",
            () -> new SlimeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).friction(0.8F).sound(SoundType.SLIME_BLOCK).noOcclusion()));

    public static final DeferredBlock<Block> STONE_SLIME_BLOCK = registerBlock("stone_slime_block",
            () -> new SlimeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).friction(0.8F).sound(SoundType.SLIME_BLOCK).noOcclusion()));

    public static final DeferredBlock<Block> COPPER_SLIME_BLOCK = registerBlock("copper_slime_block",
            () -> new SlimeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).friction(0.8F).sound(SoundType.SLIME_BLOCK).noOcclusion()));

    public static final DeferredBlock<Block> IRON_SLIME_BLOCK = registerBlock("iron_slime_block",
            () -> new SlimeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).friction(0.8F).sound(SoundType.SLIME_BLOCK).noOcclusion()));

    public static final DeferredBlock<Block> GOLD_SLIME_BLOCK = registerBlock("gold_slime_block",
            () -> new SlimeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GOLD).friction(0.8F).sound(SoundType.SLIME_BLOCK).noOcclusion()));

    public static final DeferredBlock<Block> DIAMOND_SLIME_BLOCK = registerBlock("diamond_slime_block",
            () -> new SlimeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).friction(0.8F).sound(SoundType.SLIME_BLOCK).noOcclusion()));

    public static final DeferredBlock<Block> NETHERITE_SLIME_BLOCK = registerBlock("netherite_slime_block",
            () -> new SlimeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).friction(0.8F).sound(SoundType.SLIME_BLOCK).noOcclusion()));

    public static final DeferredBlock<Block> LAPIS_SLIME_BLOCK = registerBlock("lapis_slime_block",
            () -> new SlimeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.LAPIS).friction(0.8F).sound(SoundType.SLIME_BLOCK).noOcclusion()));

    public static final DeferredBlock<Block> REDSTONE_SLIME_BLOCK = registerBlock("redstone_slime_block",
            () -> new SlimeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).friction(0.8F).sound(SoundType.SLIME_BLOCK).noOcclusion()));

    private static DeferredBlock<Block> registerBlock(String name, Supplier<Block> block){
        DeferredBlock<Block> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name,toReturn);
        return toReturn;
    }

    private static DeferredItem<BlockItem> registerBlockItem(String name, DeferredBlock<Block> block){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
