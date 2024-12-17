package com.coolerpromc.productiveslimes.block;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.custom.*;
import com.coolerpromc.productiveslimes.block.custom.SlimeBlock;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.worldgen.tree.ModTreeGrowers;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class ModBlocks {
    public static DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ProductiveSlimes.MODID);

    public static final DeferredBlock<Block> MELTING_STATION = registerBlock("melting_station", MeltingStationBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion());

    public static final DeferredBlock<Block> LIQUID_SOLIDING_STATION = registerBlock("soliding_station", SolidingStationBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion());

    public static final DeferredBlock<Block> ENERGY_GENERATOR = registerBlock("energy_generator", EnergyGeneratorBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion());

    public static final DeferredBlock<Block> CABLE = registerBlock("cable", CableBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion());

    public static final DeferredBlock<Block> DNA_EXTRACTOR = registerBlock("dna_extractor", DnaExtractorBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion());

    public static final DeferredBlock<Block> DNA_SYNTHESIZER = registerBlock("dna_synthesizer", DnaSynthesizerBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion());
    public static final DeferredBlock<Block> SLIME_SQUEEZER = registerBlock("slime_squeezer", SlimeSqueezerBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion());
    public static final DeferredBlock<Block> SQUEEZER = registerBlock("squeezer", SqueezerBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion().noLootTable());
    public static final DeferredBlock<Block> FLUID_TANK = registerBlock("fluid_tank", FluidTankBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion());

    public static final DeferredBlock<Block> SLIMY_GRASS_BLOCK = registerBlock("slimy_grass_block", SlimyBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK));
    public static final DeferredBlock<Block> SLIMY_DIRT = registerBlock("slimy_dirt", SlimyBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT));
    public static final DeferredBlock<Block> SLIMY_STONE = registerBlock("slimy_stone", SlimyBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));
    public static final DeferredBlock<Block> SLIMY_DEEPSLATE = registerBlock("slimy_deepslate", SlimyBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE));
    public static final DeferredBlock<Block> SLIMY_COBBLESTONE = registerBlock("slimy_cobblestone", SlimyBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE));
    public static final DeferredBlock<Block> SLIMY_COBBLED_DEEPSLATE = registerBlock("slimy_cobbled_deepslate", SlimyBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE));

    // Slimy Wood Set
    public static final DeferredBlock<Block> SLIMY_LOG = registerBlock("slimy_log", ModFlammableRotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG));
    public static final DeferredBlock<Block> STRIPPED_SLIMY_LOG = registerBlock("stripped_slimy_log", ModFlammableRotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG));
    public static final DeferredBlock<Block> SLIMY_WOOD = registerBlock("slimy_wood", ModFlammableRotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD));
    public static final DeferredBlock<Block> STRIPPED_SLIMY_WOOD = registerBlock("stripped_slimy_wood", ModFlammableRotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD));
    public static final DeferredBlock<Block> SLIMY_PLANKS = registerBlock("slimy_planks", ModFlammableBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> SLIMY_LEAVES = registerBlock("slimy_leaves", ModLeavesBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES));
    public static final DeferredBlock<Block> SLIMY_SAPLING = registerBlock("slimy_sapling", properties -> new ModSaplingBlock(ModTreeGrowers.SLIMY, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING));

    // Slimy Wood
    public static final DeferredBlock<SlabBlock> SLIMY_SLAB = registerBlock("slimy_slab", ModSlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<StairBlock> SLIMY_STAIRS = registerBlock("slimy_stairs", properties -> new ModStairBlock(ModBlocks.SLIMY_PLANKS.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<PressurePlateBlock> SLIMY_PRESSURE_PLATE = registerBlock("slimy_pressure_plate", properties -> new ModPressurePlateBlock(BlockSetType.OAK, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE));
    public static final DeferredBlock<ButtonBlock> SLIMY_BUTTON = registerBlock("slimy_button", properties -> new ModButtonBlock(BlockSetType.OAK, 20, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON));
    public static final DeferredBlock<FenceBlock> SLIMY_FENCE = registerBlock("slimy_fence", ModFenceBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE));
    public static final DeferredBlock<FenceGateBlock> SLIMY_FENCE_GATE = registerBlock("slimy_fence_gate", properties -> new ModFenceGateBlock(WoodType.OAK, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE));
    public static final DeferredBlock<DoorBlock> SLIMY_DOOR = registerBlock("slimy_door", properties -> new ModDoorBlock(BlockSetType.OAK, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR));
    public static final DeferredBlock<TrapDoorBlock> SLIMY_TRAPDOOR = registerBlock("slimy_trapdoor", properties -> new ModTrapDoorBlock(BlockSetType.OAK, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR));

    // Stone
    public static final DeferredBlock<StairBlock> SLIMY_STONE_STAIRS = registerBlock("slimy_stone_stairs", properties -> new ModStairBlock(ModBlocks.SLIMY_STONE.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_STAIRS));
    public static final DeferredBlock<SlabBlock> SLIMY_STONE_SLAB = registerBlock("slimy_stone_slab", ModSlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB));
    public static final DeferredBlock<PressurePlateBlock> SLIMY_STONE_PRESSURE_PLATE = registerBlock("slimy_stone_pressure_plate", properties -> new ModPressurePlateBlock(BlockSetType.STONE, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_PRESSURE_PLATE));
    public static final DeferredBlock<ButtonBlock> SLIMY_STONE_BUTTON = registerBlock("slimy_stone_button", properties -> new ModButtonBlock(BlockSetType.STONE, 20, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BUTTON));

    // Cobblestone
    public static final DeferredBlock<StairBlock> SLIMY_COBBLESTONE_STAIRS = registerBlock("slimy_cobblestone_stairs", properties -> new ModStairBlock(ModBlocks.SLIMY_COBBLESTONE.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_STAIRS));
    public static final DeferredBlock<SlabBlock> SLIMY_COBBLESTONE_SLAB = registerBlock("slimy_cobblestone_slab", ModSlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_SLAB));
    public static final DeferredBlock<WallBlock> SLIMY_COBBLESTONE_WALL = registerBlock("slimy_cobblestone_wall", ModWallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    // Cobbled Deepslate
    public static final DeferredBlock<StairBlock> SLIMY_COBBLED_DEEPSLATE_STAIRS = registerBlock("slimy_cobbled_deepslate_stairs", properties -> new ModStairBlock(ModBlocks.SLIMY_COBBLED_DEEPSLATE.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_STAIRS));
    public static final DeferredBlock<SlabBlock> SLIMY_COBBLED_DEEPSLATE_SLAB = registerBlock("slimy_cobbled_deepslate_slab", ModSlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_SLAB));
    public static final DeferredBlock<WallBlock> SLIMY_COBBLED_DEEPSLATE_WALL = registerBlock("slimy_cobbled_deepslate_wall", ModWallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_WALL));

    // Slime Blocks
    public static final DeferredBlock<Block> DIRT_SLIME_BLOCK = registerSlimeBlock("dirt_slime_block", MapColor.DIRT, 0xFF866043);

    public static final DeferredBlock<Block> STONE_SLIME_BLOCK = registerSlimeBlock("stone_slime_block", MapColor.STONE, 0xFF4a4545);

    public static final DeferredBlock<Block> COPPER_SLIME_BLOCK = registerSlimeBlock("copper_slime_block", MapColor.COLOR_BROWN, 0xFF6a3e15);

    public static final DeferredBlock<Block> IRON_SLIME_BLOCK = registerSlimeBlock("iron_slime_block", MapColor.COLOR_LIGHT_GRAY, 0xFF898c8a);

    public static final DeferredBlock<Block> GOLD_SLIME_BLOCK = registerSlimeBlock("gold_slime_block", MapColor.GOLD, 0xFFa5953f);

    public static final DeferredBlock<Block> DIAMOND_SLIME_BLOCK = registerSlimeBlock("diamond_slime_block", MapColor.DIAMOND, 0xFF178f9c);

    public static final DeferredBlock<Block> NETHERITE_SLIME_BLOCK = registerSlimeBlock("netherite_slime_block", MapColor.COLOR_BROWN, 0xFF4c2b2b);

    public static final DeferredBlock<Block> LAPIS_SLIME_BLOCK = registerSlimeBlock("lapis_slime_block", MapColor.LAPIS, 0xFF1c41ba);

    public static final DeferredBlock<Block> REDSTONE_SLIME_BLOCK = registerSlimeBlock("redstone_slime_block", MapColor.COLOR_RED, 0xFFa10505);

    public static final DeferredBlock<Block> OAK_SLIME_BLOCK = registerSlimeBlock("oak_slime_block", MapColor.COLOR_BROWN, 0xFFa69d6f);

    public static final DeferredBlock<Block> SAND_SLIME_BLOCK = registerSlimeBlock("sand_slime_block", MapColor.SAND, 0xFFf7f7c6);

    public static final DeferredBlock<Block> ANDESITE_SLIME_BLOCK = registerSlimeBlock("andesite_slime_block", MapColor.STONE, 0xFF9d9e9a);

    public static final DeferredBlock<Block> SNOW_SLIME_BLOCK = registerSlimeBlock("snow_slime_block", MapColor.SNOW, 0xFFf2fcfc);

    public static final DeferredBlock<Block> ICE_SLIME_BLOCK = registerSlimeBlock("ice_slime_block", MapColor.ICE, 0xFF89b1fc);

    public static final DeferredBlock<Block> MUD_SLIME_BLOCK = registerSlimeBlock("mud_slime_block", MapColor.DIRT, 0xFF363339);

    public static final DeferredBlock<Block> CLAY_SLIME_BLOCK = registerSlimeBlock("clay_slime_block", MapColor.CLAY, 0xFF9ca2ac);

    public static final DeferredBlock<Block> RED_SAND_SLIME_BLOCK = registerSlimeBlock("red_sand_slime_block", MapColor.COLOR_RED, 0xFFbb6520);

    public static final DeferredBlock<Block> MOSS_SLIME_BLOCK = registerSlimeBlock("moss_slime_block", MapColor.GRASS, 0xFF4a6029);

    public static final DeferredBlock<Block> DEEPSLATE_SLIME_BLOCK = registerSlimeBlock("deepslate_slime_block", MapColor.DEEPSLATE, 0xFF3c3c42);

    public static final DeferredBlock<Block> GRANITE_SLIME_BLOCK = registerSlimeBlock("granite_slime_block", MapColor.COLOR_RED, 0xFF835949);

    public static final DeferredBlock<Block> DIORITE_SLIME_BLOCK = registerSlimeBlock("diorite_slime_block", MapColor.TERRACOTTA_WHITE, 0xFFadacad);

    public static final DeferredBlock<Block> CALCITE_SLIME_BLOCK = registerSlimeBlock("calcite_slime_block", MapColor.TERRACOTTA_WHITE, 0xFFe9e9e3);

    public static final DeferredBlock<Block> TUFF_SLIME_BLOCK = registerSlimeBlock("tuff_slime_block", MapColor.STONE, 0xFF55564c);

    public static final DeferredBlock<Block> DRIPSTONE_SLIME_BLOCK = registerSlimeBlock("dripstone_slime_block", MapColor.COLOR_BROWN, 0xFF806155);

    public static final DeferredBlock<Block> PRISMARINE_SLIME_BLOCK = registerSlimeBlock("prismarine_slime_block", MapColor.COLOR_LIGHT_BLUE, 0xFF529584);

    public static final DeferredBlock<Block> MAGMA_SLIME_BLOCK = registerSlimeBlock("magma_slime_block", MapColor.FIRE, 0xFF561f1f);

    public static final DeferredBlock<Block> OBSIDIAN_SLIME_BLOCK = registerSlimeBlock("obsidian_slime_block", MapColor.COLOR_BLACK, 0xFF030106);

    public static final DeferredBlock<Block> NETHERRACK_SLIME_BLOCK = registerSlimeBlock("netherrack_slime_block", MapColor.NETHER, 0xFF763535);

    public static final DeferredBlock<Block> SOUL_SAND_SLIME_BLOCK = registerSlimeBlock("soul_sand_slime_block", MapColor.COLOR_BROWN, 0xFF413127);

    public static final DeferredBlock<Block> SOUL_SOIL_SLIME_BLOCK = registerSlimeBlock("soul_soil_slime_block", MapColor.COLOR_BROWN, 0xFF392b23);

    public static final DeferredBlock<Block> BLACKSTONE_SLIME_BLOCK = registerSlimeBlock("blackstone_slime_block", MapColor.DEEPSLATE, 0xFF201819);

    public static final DeferredBlock<Block> BASALT_SLIME_BLOCK = registerSlimeBlock("basalt_slime_block", MapColor.DEEPSLATE, 0xFF565456);

    public static final DeferredBlock<Block> ENDSTONE_SLIME_BLOCK = registerSlimeBlock("endstone_slime_block", MapColor.SAND, 0xFFcece8e);

    public static final DeferredBlock<Block> QUARTZ_SLIME_BLOCK = registerSlimeBlock("quartz_slime_block", MapColor.QUARTZ, 0xFFe4ddd3);

    public static final DeferredBlock<Block> GLOWSTONE_SLIME_BLOCK = registerSlimeBlock("glowstone_slime_block", MapColor.COLOR_YELLOW, 0xFF784e27);

    public static final DeferredBlock<Block> AMETHYST_SLIME_BLOCK = registerSlimeBlock("amethyst_slime_block", MapColor.COLOR_PINK, 0xFF6b4da5);

    public static final DeferredBlock<Block> BROWN_MUSHROOM_SLIME_BLOCK = registerSlimeBlock("brown_mushroom_slime_block", MapColor.COLOR_BROWN, 0xFF967251);

    public static final DeferredBlock<Block> RED_MUSHROOM_SLIME_BLOCK = registerSlimeBlock("red_mushroom_slime_block", MapColor.COLOR_RED, 0xFFc02624);

    public static final DeferredBlock<Block> CACTUS_SLIME_BLOCK = registerSlimeBlock("cactus_slime_block", MapColor.COLOR_GREEN, 0xFF476d21);
  
    public static final DeferredBlock<Block> COAL_SLIME_BLOCK = registerSlimeBlock("coal_slime_block", MapColor.COLOR_BLACK, 0xFF3b3d3b);

    public static final DeferredBlock<Block> GRAVEL_SLIME_BLOCK = registerSlimeBlock("gravel_slime_block", MapColor.COLOR_GRAY, 0xFF4a444b);

    public static final DeferredBlock<Block> ENERGY_SLIME_BLOCK = registerSlimeBlock("energy_slime_block", MapColor.COLOR_YELLOW, 0xFFffff70);

    public static final DeferredBlock<Block> OAK_LEAVES_SLIME_BLOCK = registerSlimeBlock("oak_leaves_slime_block", MapColor.COLOR_GREEN, 0xFF48b518);

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, ? extends T> func, BlockBehaviour.Properties properties){
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, func, properties);
        registerBlockItem(name,toReturn);
        return toReturn;
    }

    private static DeferredBlock<Block> registerSlimeBlock(String name, MapColor mapColor, int color){
        return registerBlock(name, properties -> new SlimeBlock(properties, mapColor, color), BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK).noOcclusion());
    }

    private static <T extends Block> DeferredItem<BlockItem> registerBlockItem(String name, DeferredBlock<T> block){
        return ModItems.ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}