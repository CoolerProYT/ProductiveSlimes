package com.coolerpromc.productiveslimes.block;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.custom.*;
import com.coolerpromc.productiveslimes.block.custom.SlimeBlock;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
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
    public static final DeferredBlock<Block> SLIME_NEST = registerBlock("slime_nest", SlimeNestBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion());
    public static final DeferredBlock<Block> FLUID_TANK = registerBlock("fluid_tank", FluidTankBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion());
    public static final DeferredBlock<Block> SLIMEBALL_COLLECTOR = registerBlock("slimeball_collector", SlimeballCollectorBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion());

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

    public static final DeferredBlock<SlimeBlock> ENERGY_SLIME_BLOCK = registerSlimeBlock("energy_slime_block", MapColor.COLOR_YELLOW, 0xFFffff70);

    public static void registerTierBlocks(){
        for (Tier name : Tier.values()){
            ModTiers tier = ModTierLists.getTierByName(name);
            String blockName = tier.name() + "_slime_block";
            DeferredBlock<SlimeBlock> registeredSlimeBlock = registerSlimeBlock(blockName, MapColor.byId(tier.mapColorId()), tier.color());
            ModTierLists.addRegisteredBlock(tier.name(), registeredSlimeBlock);
        }
    }

    private static <T extends  Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, ? extends T> func, BlockBehaviour.Properties properties){
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, func, properties);
        registerBlockItem(name,toReturn);
        return toReturn;
    }

    private static DeferredBlock<SlimeBlock> registerSlimeBlock(String name, MapColor mapColor, int color){
        return registerBlock(name, properties -> new SlimeBlock(properties, mapColor, color), BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK).noOcclusion());
    }

    private static <T extends Block> DeferredItem<BlockItem> registerBlockItem(String name, DeferredBlock<T> block){
        return ModItems.ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}