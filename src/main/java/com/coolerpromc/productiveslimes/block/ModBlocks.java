package com.coolerpromc.productiveslimes.block;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.custom.*;
import com.coolerpromc.productiveslimes.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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

    public static final DeferredBlock<Block> ENERGY_GENERATOR = registerBlock("energy_generator",
            () -> new EnergyGeneratorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final DeferredBlock<Block> CABLE = registerBlock("cable",
            () -> new CableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final DeferredBlock<Block> DNA_EXTRACTOR = registerBlock("dna_extractor",
            () -> new DnaExtractorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final DeferredBlock<Block> DIRT_SLIME_BLOCK = registerBlock("dirt_slime_block",
            () -> new SlimeBlock(MapColor.DIRT, 0xF0866043));

    public static final DeferredBlock<Block> STONE_SLIME_BLOCK = registerBlock("stone_slime_block",
            () -> new SlimeBlock(MapColor.STONE, 0xF04a4545));

    public static final DeferredBlock<Block> COPPER_SLIME_BLOCK = registerBlock("copper_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_BROWN, 0xF06a3e15));

    public static final DeferredBlock<Block> IRON_SLIME_BLOCK = registerBlock("iron_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_LIGHT_GRAY, 0xF0898c8a));

    public static final DeferredBlock<Block> GOLD_SLIME_BLOCK = registerBlock("gold_slime_block",
            () -> new SlimeBlock(MapColor.GOLD, 0xF0a5953f));

    public static final DeferredBlock<Block> DIAMOND_SLIME_BLOCK = registerBlock("diamond_slime_block",
            () -> new SlimeBlock(MapColor.DIAMOND, 0xF0178f9c));

    public static final DeferredBlock<Block> NETHERITE_SLIME_BLOCK = registerBlock("netherite_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_BROWN, 0xF04c2b2b));

    public static final DeferredBlock<Block> LAPIS_SLIME_BLOCK = registerBlock("lapis_slime_block",
            () -> new SlimeBlock(MapColor.LAPIS, 0xF01c41ba));

    public static final DeferredBlock<Block> REDSTONE_SLIME_BLOCK = registerBlock("redstone_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_RED, 0xF0a10505));

    public static final DeferredBlock<Block> OAK_SLIME_BLOCK = registerBlock("oak_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_BROWN, 0xF0a69d6f));

    public static final DeferredBlock<Block> SAND_SLIME_BLOCK = registerBlock("sand_slime_block",
            () -> new SlimeBlock(MapColor.SAND, 0xF0f7f7c6));

    public static final DeferredBlock<Block> ANDESITE_SLIME_BLOCK = registerBlock("andesite_slime_block",
            () -> new SlimeBlock(MapColor.STONE, 0xF09d9e9a));

    public static final DeferredBlock<Block> SNOW_SLIME_BLOCK = registerBlock("snow_slime_block",
            () -> new SlimeBlock(MapColor.SNOW, 0xF0f2fcfc));

    public static final DeferredBlock<Block> ICE_SLIME_BLOCK = registerBlock("ice_slime_block",
            () -> new SlimeBlock(MapColor.ICE, 0xF089b1fc));

    public static final DeferredBlock<Block> MUD_SLIME_BLOCK = registerBlock("mud_slime_block",
            () -> new SlimeBlock(MapColor.DIRT, 0xF0363339));

    public static final DeferredBlock<Block> CLAY_SLIME_BLOCK = registerBlock("clay_slime_block",
            () -> new SlimeBlock(MapColor.CLAY, 0xF09ca2ac));

    public static final DeferredBlock<Block> RED_SAND_SLIME_BLOCK = registerBlock("red_sand_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_RED, 0xF0bb6520));

    public static final DeferredBlock<Block> MOSS_SLIME_BLOCK = registerBlock("moss_slime_block",
            () -> new SlimeBlock(MapColor.GRASS, 0xF04a6029));

    public static final DeferredBlock<Block> DEEPSLATE_SLIME_BLOCK = registerBlock("deepslate_slime_block",
            () -> new SlimeBlock(MapColor.DEEPSLATE, 0xF03c3c42));

    public static final DeferredBlock<Block> GRANITE_SLIME_BLOCK = registerBlock("granite_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_RED, 0xF0835949));

    public static final DeferredBlock<Block> DIORITE_SLIME_BLOCK = registerBlock("diorite_slime_block",
            () -> new SlimeBlock(MapColor.TERRACOTTA_WHITE, 0xF0adacad));

    public static final DeferredBlock<Block> CALCITE_SLIME_BLOCK = registerBlock("calcite_slime_block",
            () -> new SlimeBlock(MapColor.TERRACOTTA_WHITE, 0xF0e9e9e3));

    public static final DeferredBlock<Block> TUFF_SLIME_BLOCK = registerBlock("tuff_slime_block",
            () -> new SlimeBlock(MapColor.STONE, 0xF055564c));

    public static final DeferredBlock<Block> DRIPSTONE_SLIME_BLOCK = registerBlock("dripstone_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_BROWN, 0xF0806155));

    public static final DeferredBlock<Block> PRISMARINE_SLIME_BLOCK = registerBlock("prismarine_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_LIGHT_BLUE, 0xF0529584));

    public static final DeferredBlock<Block> MAGMA_SLIME_BLOCK = registerBlock("magma_slime_block",
            () -> new SlimeBlock(MapColor.FIRE, 0xF0561f1f));

    public static final DeferredBlock<Block> OBSIDIAN_SLIME_BLOCK = registerBlock("obsidian_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_BLACK, 0xF0030106));

    public static final DeferredBlock<Block> NETHERRACK_SLIME_BLOCK = registerBlock("netherrack_slime_block",
            () -> new SlimeBlock(MapColor.NETHER, 0xF0763535));

    public static final DeferredBlock<Block> SOUL_SAND_SLIME_BLOCK = registerBlock("soul_sand_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_BROWN, 0xF0413127));

    public static final DeferredBlock<Block> SOUL_SOIL_SLIME_BLOCK = registerBlock("soul_soil_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_BROWN, 0xF0392b23));

    public static final DeferredBlock<Block> BLACKSTONE_SLIME_BLOCK = registerBlock("blackstone_slime_block",
            () -> new SlimeBlock(MapColor.DEEPSLATE, 0xF0201819));

    public static final DeferredBlock<Block> BASALT_SLIME_BLOCK = registerBlock("basalt_slime_block",
            () -> new SlimeBlock(MapColor.DEEPSLATE, 0xF0565456));

    public static final DeferredBlock<Block> ENDSTONE_SLIME_BLOCK = registerBlock("endstone_slime_block",
            () -> new SlimeBlock(MapColor.SAND, 0xF0cece8e));

    public static final DeferredBlock<Block> QUARTZ_SLIME_BLOCK = registerBlock("quartz_slime_block",
            () -> new SlimeBlock(MapColor.QUARTZ, 0xF0e4ddd3));

    public static final DeferredBlock<Block> GLOWSTONE_SLIME_BLOCK = registerBlock("glowstone_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_YELLOW, 0xF0784e27));

    public static final DeferredBlock<Block> AMETHYST_SLIME_BLOCK = registerBlock("amethyst_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_PINK, 0xF06b4da5));

    public static final DeferredBlock<Block> BROWN_MUSHROOM_SLIME_BLOCK = registerBlock("brown_mushroom_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_BROWN, 0xF0967251));

    public static final DeferredBlock<Block> RED_MUSHROOM_SLIME_BLOCK = registerBlock("red_mushroom_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_RED, 0xF0c02624));

    public static final DeferredBlock<Block> CACTUS_SLIME_BLOCK = registerBlock("cactus_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_GREEN, 0xF0476d21));
  
    public static final DeferredBlock<Block> COAL_SLIME_BLOCK = registerBlock("coal_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_BLACK, 0xF03b3d3b));

    public static final DeferredBlock<Block> GRAVEL_SLIME_BLOCK = registerBlock("gravel_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_GRAY, 0xF04a444b));

    public static final DeferredBlock<Block> ENERGY_SLIME_BLOCK = registerBlock("energy_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_YELLOW, 0xF0ffff70));

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