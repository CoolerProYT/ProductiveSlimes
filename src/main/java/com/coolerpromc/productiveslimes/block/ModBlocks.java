package com.coolerpromc.productiveslimes.block;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.custom.*;
import com.coolerpromc.productiveslimes.block.custom.SlimeBlock;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.worldgen.tree.ModTreeGrowers;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
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

    public static final DeferredBlock<Block> DNA_SYNTHESIZER = registerBlock("dna_synthesizer",
            () -> new DnaSynthesizerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final DeferredBlock<Block> FLUID_TANK = registerBlock("fluid_tank",
            () -> new FluidTankBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final DeferredBlock<Block> SLIMY_GRASS_BLOCK = registerBlock("slimy_grass_block",
            () -> new SlimyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK)));

    public static final DeferredBlock<Block> SLIMY_DIRT = registerBlock("slimy_dirt",
            () -> new SlimyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)));

    public static final DeferredBlock<Block> SLIMY_STONE = registerBlock("slimy_stone",
            () -> new SlimyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

    public static final DeferredBlock<Block> SLIMY_DEEPSLATE = registerBlock("slimy_deepslate",
            () -> new SlimyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE)));

    public static final DeferredBlock<Block> SLIMY_COBBLESTONE = registerBlock("slimy_cobblestone",
            () -> new SlimyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE)));

    public static final DeferredBlock<Block> SLIMY_COBBLED_DEEPSLATE = registerBlock("slimy_cobbled_deepslate",
            () -> new SlimyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE)));

    // Slimy Wood Set
    public static final DeferredBlock<Block> SLIMY_LOG = registerBlock("slimy_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));

    public static final DeferredBlock<Block> STRIPPED_SLIMY_LOG = registerBlock("stripped_slimy_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)));

    public static final DeferredBlock<Block> SLIMY_WOOD = registerBlock("slimy_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));

    public static final DeferredBlock<Block> STRIPPED_SLIMY_WOOD = registerBlock("stripped_slimy_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));

    public static final DeferredBlock<Block> SLIMY_PLANKS = registerBlock("slimy_planks",
            () -> new ModFlammableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));

    public static final DeferredBlock<Block> SLIMY_LEAVES = registerBlock("slimy_leaves",
            () -> new ModLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)));

    public static final DeferredBlock<Block> SLIMY_SAPLING = registerBlock("slimy_sapling",
            () -> new SaplingBlock(ModTreeGrowers.SLIMY, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));

    // Slimy Wood
    public static final DeferredBlock<SlabBlock> SLIMY_SLAB = registerBlock("slimy_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB)));

    public static final DeferredBlock<StairBlock> SLIMY_STAIRS = registerBlock("slimy_stairs",
            () -> new StairBlock(ModBlocks.SLIMY_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS)));

    public static final DeferredBlock<PressurePlateBlock> SLIMY_PRESSURE_PLATE = registerBlock("slimy_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)));

    public static final DeferredBlock<ButtonBlock> SLIMY_BUTTON = registerBlock("slimy_button",
            () -> new ButtonBlock(BlockSetType.OAK, 20, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON)));

    public static final DeferredBlock<FenceBlock> SLIMY_FENCE = registerBlock("slimy_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)));

    public static final DeferredBlock<FenceGateBlock> SLIMY_FENCE_GATE = registerBlock("slimy_fence_gate",
            () -> new FenceGateBlock(WoodType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)));

    public static final DeferredBlock<DoorBlock> SLIMY_DOOR = registerBlock("slimy_door",
            () -> new DoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR)));

    public static final DeferredBlock<TrapDoorBlock> SLIMY_TRAPDOOR = registerBlock("slimy_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)));

    // Stone
    public static final DeferredBlock<StairBlock> SLIMY_STONE_STAIRS = registerBlock("slimy_stone_stairs",
            () -> new StairBlock(ModBlocks.SLIMY_STONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_STAIRS)));

    public static final DeferredBlock<SlabBlock> SLIMY_STONE_SLAB = registerBlock("slimy_stone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB)));

    public static final DeferredBlock<PressurePlateBlock> SLIMY_STONE_PRESSURE_PLATE = registerBlock("slimy_stone_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.STONE, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_PRESSURE_PLATE)));

    public static final DeferredBlock<ButtonBlock> SLIMY_STONE_BUTTON = registerBlock("slimy_stone_button",
            () -> new ButtonBlock(BlockSetType.STONE, 20, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BUTTON)));

    // Cobblestone
    public static final DeferredBlock<StairBlock> SLIMY_COBBLESTONE_STAIRS = registerBlock("slimy_cobblestone_stairs",
            () -> new StairBlock(ModBlocks.SLIMY_COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_STAIRS)));

    public static final DeferredBlock<SlabBlock> SLIMY_COBBLESTONE_SLAB = registerBlock("slimy_cobblestone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_SLAB)));

    public static final DeferredBlock<WallBlock> SLIMY_COBBLESTONE_WALL = registerBlock("slimy_cobblestone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL)));

    // Cobbled Deepslate
    public static final DeferredBlock<StairBlock> SLIMY_COBBLED_DEEPSLATE_STAIRS = registerBlock("slimy_cobbled_deepslate_stairs",
            () -> new StairBlock(ModBlocks.SLIMY_COBBLED_DEEPSLATE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_STAIRS)));

    public static final DeferredBlock<SlabBlock> SLIMY_COBBLED_DEEPSLATE_SLAB = registerBlock("slimy_cobbled_deepslate_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_SLAB)));

    public static final DeferredBlock<WallBlock> SLIMY_COBBLED_DEEPSLATE_WALL = registerBlock("slimy_cobbled_deepslate_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_WALL)));

    // Slime Blocks
    public static final DeferredBlock<Block> DIRT_SLIME_BLOCK = registerBlock("dirt_slime_block",
            () -> new SlimeBlock(MapColor.DIRT, 0xFF866043));

    public static final DeferredBlock<Block> STONE_SLIME_BLOCK = registerBlock("stone_slime_block",
            () -> new SlimeBlock(MapColor.STONE, 0xFF4a4545));

    public static final DeferredBlock<Block> COPPER_SLIME_BLOCK = registerBlock("copper_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_BROWN, 0xFF6a3e15));

    public static final DeferredBlock<Block> IRON_SLIME_BLOCK = registerBlock("iron_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_LIGHT_GRAY, 0xFF898c8a));

    public static final DeferredBlock<Block> GOLD_SLIME_BLOCK = registerBlock("gold_slime_block",
            () -> new SlimeBlock(MapColor.GOLD, 0xFFa5953f));

    public static final DeferredBlock<Block> DIAMOND_SLIME_BLOCK = registerBlock("diamond_slime_block",
            () -> new SlimeBlock(MapColor.DIAMOND, 0xFF178f9c));

    public static final DeferredBlock<Block> NETHERITE_SLIME_BLOCK = registerBlock("netherite_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_BROWN, 0xFF4c2b2b));

    public static final DeferredBlock<Block> LAPIS_SLIME_BLOCK = registerBlock("lapis_slime_block",
            () -> new SlimeBlock(MapColor.LAPIS, 0xFF1c41ba));

    public static final DeferredBlock<Block> REDSTONE_SLIME_BLOCK = registerBlock("redstone_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_RED, 0xFFa10505));

    public static final DeferredBlock<Block> OAK_SLIME_BLOCK = registerBlock("oak_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_BROWN, 0xFFa69d6f));

    public static final DeferredBlock<Block> SAND_SLIME_BLOCK = registerBlock("sand_slime_block",
            () -> new SlimeBlock(MapColor.SAND, 0xFFf7f7c6));

    public static final DeferredBlock<Block> ANDESITE_SLIME_BLOCK = registerBlock("andesite_slime_block",
            () -> new SlimeBlock(MapColor.STONE, 0xFF9d9e9a));

    public static final DeferredBlock<Block> SNOW_SLIME_BLOCK = registerBlock("snow_slime_block",
            () -> new SlimeBlock(MapColor.SNOW, 0xFFf2fcfc));

    public static final DeferredBlock<Block> ICE_SLIME_BLOCK = registerBlock("ice_slime_block",
            () -> new SlimeBlock(MapColor.ICE, 0xFF89b1fc));

    public static final DeferredBlock<Block> MUD_SLIME_BLOCK = registerBlock("mud_slime_block",
            () -> new SlimeBlock(MapColor.DIRT, 0xFF363339));

    public static final DeferredBlock<Block> CLAY_SLIME_BLOCK = registerBlock("clay_slime_block",
            () -> new SlimeBlock(MapColor.CLAY, 0xFF9ca2ac));

    public static final DeferredBlock<Block> RED_SAND_SLIME_BLOCK = registerBlock("red_sand_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_RED, 0xFFbb6520));

    public static final DeferredBlock<Block> MOSS_SLIME_BLOCK = registerBlock("moss_slime_block",
            () -> new SlimeBlock(MapColor.GRASS, 0xFF4a6029));

    public static final DeferredBlock<Block> DEEPSLATE_SLIME_BLOCK = registerBlock("deepslate_slime_block",
            () -> new SlimeBlock(MapColor.DEEPSLATE, 0xFF3c3c42));

    public static final DeferredBlock<Block> GRANITE_SLIME_BLOCK = registerBlock("granite_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_RED, 0xFF835949));

    public static final DeferredBlock<Block> DIORITE_SLIME_BLOCK = registerBlock("diorite_slime_block",
            () -> new SlimeBlock(MapColor.TERRACOTTA_WHITE, 0xFFadacad));

    public static final DeferredBlock<Block> CALCITE_SLIME_BLOCK = registerBlock("calcite_slime_block",
            () -> new SlimeBlock(MapColor.TERRACOTTA_WHITE, 0xFFe9e9e3));

    public static final DeferredBlock<Block> TUFF_SLIME_BLOCK = registerBlock("tuff_slime_block",
            () -> new SlimeBlock(MapColor.STONE, 0xFF55564c));

    public static final DeferredBlock<Block> DRIPSTONE_SLIME_BLOCK = registerBlock("dripstone_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_BROWN, 0xFF806155));

    public static final DeferredBlock<Block> PRISMARINE_SLIME_BLOCK = registerBlock("prismarine_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_LIGHT_BLUE, 0xFF529584));

    public static final DeferredBlock<Block> MAGMA_SLIME_BLOCK = registerBlock("magma_slime_block",
            () -> new SlimeBlock(MapColor.FIRE, 0xFF561f1f));

    public static final DeferredBlock<Block> OBSIDIAN_SLIME_BLOCK = registerBlock("obsidian_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_BLACK, 0xFF030106));

    public static final DeferredBlock<Block> NETHERRACK_SLIME_BLOCK = registerBlock("netherrack_slime_block",
            () -> new SlimeBlock(MapColor.NETHER, 0xFF763535));

    public static final DeferredBlock<Block> SOUL_SAND_SLIME_BLOCK = registerBlock("soul_sand_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_BROWN, 0xFF413127));

    public static final DeferredBlock<Block> SOUL_SOIL_SLIME_BLOCK = registerBlock("soul_soil_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_BROWN, 0xFF392b23));

    public static final DeferredBlock<Block> BLACKSTONE_SLIME_BLOCK = registerBlock("blackstone_slime_block",
            () -> new SlimeBlock(MapColor.DEEPSLATE, 0xFF201819));

    public static final DeferredBlock<Block> BASALT_SLIME_BLOCK = registerBlock("basalt_slime_block",
            () -> new SlimeBlock(MapColor.DEEPSLATE, 0xFF565456));

    public static final DeferredBlock<Block> ENDSTONE_SLIME_BLOCK = registerBlock("endstone_slime_block",
            () -> new SlimeBlock(MapColor.SAND, 0xFFcece8e));

    public static final DeferredBlock<Block> QUARTZ_SLIME_BLOCK = registerBlock("quartz_slime_block",
            () -> new SlimeBlock(MapColor.QUARTZ, 0xFFe4ddd3));

    public static final DeferredBlock<Block> GLOWSTONE_SLIME_BLOCK = registerBlock("glowstone_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_YELLOW, 0xFF784e27));

    public static final DeferredBlock<Block> AMETHYST_SLIME_BLOCK = registerBlock("amethyst_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_PINK, 0xFF6b4da5));

    public static final DeferredBlock<Block> BROWN_MUSHROOM_SLIME_BLOCK = registerBlock("brown_mushroom_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_BROWN, 0xFF967251));

    public static final DeferredBlock<Block> RED_MUSHROOM_SLIME_BLOCK = registerBlock("red_mushroom_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_RED, 0xFFc02624));

    public static final DeferredBlock<Block> CACTUS_SLIME_BLOCK = registerBlock("cactus_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_GREEN, 0xFF476d21));
  
    public static final DeferredBlock<Block> COAL_SLIME_BLOCK = registerBlock("coal_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_BLACK, 0xFF3b3d3b));

    public static final DeferredBlock<Block> GRAVEL_SLIME_BLOCK = registerBlock("gravel_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_GRAY, 0xFF4a444b));

    public static final DeferredBlock<Block> ENERGY_SLIME_BLOCK = registerBlock("energy_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_YELLOW, 0xFFffff70));

    public static final DeferredBlock<Block> OAK_LEAVES_SLIME_BLOCK = registerBlock("oak_leaves_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_GREEN, 0xFF48b518));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name,toReturn);
        return toReturn;
    }

    private static <T extends Block> DeferredItem<BlockItem> registerBlockItem(String name, DeferredBlock<T> block){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}