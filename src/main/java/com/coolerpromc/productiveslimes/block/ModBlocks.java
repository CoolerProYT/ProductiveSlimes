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

    public static final DeferredBlock<Block> SLIME_SQUEEZER = registerBlock("slime_squeezer",
            () -> new SlimeSqueezerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final DeferredBlock<Block> SQUEEZER = registerBlock("squeezer",
            () -> new SqueezerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion().noLootTable()));

    public static final DeferredBlock<Block> SLIME_NEST = registerBlock("slime_nest",
            () -> new SlimeNestBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion()));

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
            () -> new ModSaplingBlock(ModTreeGrowers.SLIMY, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));

    // Slimy Wood
    public static final DeferredBlock<SlabBlock> SLIMY_SLAB = registerBlock("slimy_slab",
            () -> new ModSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB)));

    public static final DeferredBlock<StairBlock> SLIMY_STAIRS = registerBlock("slimy_stairs",
            () -> new ModStairBlock(ModBlocks.SLIMY_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS)));

    public static final DeferredBlock<PressurePlateBlock> SLIMY_PRESSURE_PLATE = registerBlock("slimy_pressure_plate",
            () -> new ModPressurePlateBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)));

    public static final DeferredBlock<ButtonBlock> SLIMY_BUTTON = registerBlock("slimy_button",
            () -> new ModButtonBlock(BlockSetType.OAK, 20, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON)));

    public static final DeferredBlock<FenceBlock> SLIMY_FENCE = registerBlock("slimy_fence",
            () -> new ModFenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)));

    public static final DeferredBlock<FenceGateBlock> SLIMY_FENCE_GATE = registerBlock("slimy_fence_gate",
            () -> new ModFenceGateBlock(WoodType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)));

    public static final DeferredBlock<DoorBlock> SLIMY_DOOR = registerBlock("slimy_door",
            () -> new ModDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR)));

    public static final DeferredBlock<TrapDoorBlock> SLIMY_TRAPDOOR = registerBlock("slimy_trapdoor",
            () -> new ModTrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)));

    // Stone
    public static final DeferredBlock<StairBlock> SLIMY_STONE_STAIRS = registerBlock("slimy_stone_stairs",
            () -> new ModStairBlock(ModBlocks.SLIMY_STONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_STAIRS)));

    public static final DeferredBlock<SlabBlock> SLIMY_STONE_SLAB = registerBlock("slimy_stone_slab",
            () -> new ModSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB)));

    public static final DeferredBlock<PressurePlateBlock> SLIMY_STONE_PRESSURE_PLATE = registerBlock("slimy_stone_pressure_plate",
            () -> new ModPressurePlateBlock(BlockSetType.STONE, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_PRESSURE_PLATE)));

    public static final DeferredBlock<ButtonBlock> SLIMY_STONE_BUTTON = registerBlock("slimy_stone_button",
            () -> new ModButtonBlock(BlockSetType.STONE, 20, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BUTTON)));

    // Cobblestone
    public static final DeferredBlock<StairBlock> SLIMY_COBBLESTONE_STAIRS = registerBlock("slimy_cobblestone_stairs",
            () -> new ModStairBlock(ModBlocks.SLIMY_COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_STAIRS)));

    public static final DeferredBlock<SlabBlock> SLIMY_COBBLESTONE_SLAB = registerBlock("slimy_cobblestone_slab",
            () -> new ModSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_SLAB)));

    public static final DeferredBlock<WallBlock> SLIMY_COBBLESTONE_WALL = registerBlock("slimy_cobblestone_wall",
            () -> new ModWallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL)));

    // Cobbled Deepslate
    public static final DeferredBlock<StairBlock> SLIMY_COBBLED_DEEPSLATE_STAIRS = registerBlock("slimy_cobbled_deepslate_stairs",
            () -> new ModStairBlock(ModBlocks.SLIMY_COBBLED_DEEPSLATE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_STAIRS)));

    public static final DeferredBlock<SlabBlock> SLIMY_COBBLED_DEEPSLATE_SLAB = registerBlock("slimy_cobbled_deepslate_slab",
            () -> new ModSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_SLAB)));

    public static final DeferredBlock<WallBlock> SLIMY_COBBLED_DEEPSLATE_WALL = registerBlock("slimy_cobbled_deepslate_wall",
            () -> new ModWallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_WALL)));

    // Slime Blocks
    public static final DeferredBlock<Block> ENERGY_SLIME_BLOCK = registerBlock("energy_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_YELLOW, 0xFFffff70));

    public static void registerTierBlock(){
        for(Tier tier : Tier.values()){
            ModTiers modTiers = ModTierLists.getTierByName(tier);
            String blockName = modTiers.name() + "_slime_block";
            DeferredBlock<Block> registeredBlock = registerBlock(blockName, () -> new SlimeBlock(MapColor.byId(modTiers.mapColorId()), modTiers.color()));
            ModTierLists.addRegisteredBlock(modTiers.name(), registeredBlock);
        }
    }

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