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
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBlocks {
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ProductiveSlimes.MODID);

    public static final RegistryObject<Block> MELTING_STATION = registerBlock("melting_station",
            () -> new MeltingStationBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().noDrops()));

    public static final RegistryObject<Block> LIQUID_SOLIDING_STATION = registerBlock("soliding_station",
            () -> new SolidingStationBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().noDrops()));

    public static final RegistryObject<Block> ENERGY_GENERATOR = registerBlock("energy_generator",
            () -> new EnergyGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().noDrops()));

    public static final RegistryObject<Block> CABLE = registerBlock("cable",
            () -> new CableBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> DNA_EXTRACTOR = registerBlock("dna_extractor",
            () -> new DnaExtractorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().noDrops()));

    public static final RegistryObject<Block> DNA_SYNTHESIZER = registerBlock("dna_synthesizer",
            () -> new DnaSynthesizerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().noDrops()));

    public static final RegistryObject<Block> SLIME_SQUEEZER = registerBlock("slime_squeezer",
            () -> new SlimeSqueezerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().noDrops()));
    public static final RegistryObject<Block> SQUEEZER = registerBlock("squeezer",
            () -> new SqueezerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().noDrops()));

    public static final RegistryObject<Block> SLIME_NEST = registerBlock("slime_nest",
            () -> new SlimeNestBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> FLUID_TANK = registerBlockWithoutItem("fluid_tank",
            () -> new FluidTankBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().noDrops()));

    public static final RegistryObject<Block> SLIMEBALL_COLLECTOR = registerBlock("slimeball_collector",
            () -> new SlimeballCollectorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> SLIMY_GRASS_BLOCK = registerBlock("slimy_grass_block",
            () -> new SlimyBlock(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)));

    public static final RegistryObject<Block> SLIMY_DIRT = registerBlock("slimy_dirt",
            () -> new SlimyBlock(BlockBehaviour.Properties.copy(Blocks.DIRT)));

    public static final RegistryObject<Block> SLIMY_STONE = registerBlock("slimy_stone",
            () -> new SlimyBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> SLIMY_DEEPSLATE = registerBlock("slimy_deepslate",
            () -> new SlimyBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)));

    public static final RegistryObject<Block> SLIMY_COBBLESTONE = registerBlock("slimy_cobblestone",
            () -> new SlimyBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));

    public static final RegistryObject<Block> SLIMY_COBBLED_DEEPSLATE = registerBlock("slimy_cobbled_deepslate",
            () -> new SlimyBlock(BlockBehaviour.Properties.copy(Blocks.COBBLED_DEEPSLATE)));

    // Slimy Wood Set
    public static final RegistryObject<Block> SLIMY_LOG = registerBlock("slimy_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<Block> STRIPPED_SLIMY_LOG = registerBlock("stripped_slimy_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)));
    public static final RegistryObject<Block> SLIMY_WOOD = registerBlock("slimy_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)));
    public static final RegistryObject<Block> STRIPPED_SLIMY_WOOD = registerBlock("stripped_slimy_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)));
    public static final RegistryObject<Block> SLIMY_PLANKS = registerBlock("slimy_planks",
            () -> new ModFlammableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> SLIMY_LEAVES = registerBlock("slimy_leaves",
            () -> new ModLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistryObject<Block> SLIMY_SAPLING = registerBlock("slimy_sapling",
            () -> new ModSaplingBlock(new ModTreeGrowers(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));


    // Slimy Wood
    public static final RegistryObject<SlabBlock> SLIMY_SLAB = registerBlock("slimy_slab",
            () -> new ModSlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<StairBlock> SLIMY_STAIRS = registerBlock("slimy_stairs",
            () -> new ModStairBlock(ModBlocks.SLIMY_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<PressurePlateBlock> SLIMY_PRESSURE_PLATE = registerBlock("slimy_pressure_plate",
            () -> new ModPressurePlateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE)));
    public static final RegistryObject<ButtonBlock> SLIMY_BUTTON = registerBlock("slimy_button",
            () -> new ModButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON)));
    public static final RegistryObject<FenceBlock> SLIMY_FENCE = registerBlock("slimy_fence",
            () -> new ModFenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<FenceGateBlock> SLIMY_FENCE_GATE = registerBlock("slimy_fence_gate",
            () -> new ModFenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE)));
    public static final RegistryObject<DoorBlock> SLIMY_DOOR = registerBlock("slimy_door",
            () -> new ModDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR)));
    public static final RegistryObject<TrapDoorBlock> SLIMY_TRAPDOOR = registerBlock("slimy_trapdoor",
            () -> new ModTrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR)));

    // Stone
    public static final RegistryObject<StairBlock> SLIMY_STONE_STAIRS = registerBlock("slimy_stone_stairs",
            () -> new ModStairBlock(ModBlocks.SLIMY_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_STAIRS)));
    public static final RegistryObject<SlabBlock> SLIMY_STONE_SLAB = registerBlock("slimy_stone_slab",
            () -> new ModSlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_SLAB)));
    public static final RegistryObject<PressurePlateBlock> SLIMY_STONE_PRESSURE_PLATE = registerBlock("slimy_stone_pressure_plate",
            () -> new ModPressurePlateBlock(BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE)));
    public static final RegistryObject<ButtonBlock> SLIMY_STONE_BUTTON = registerBlock("slimy_stone_button",
            () -> new ModButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON)));

    // Cobblestone
    public static final RegistryObject<StairBlock> SLIMY_COBBLESTONE_STAIRS = registerBlock("slimy_cobblestone_stairs",
            () -> new ModStairBlock(ModBlocks.SLIMY_COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.COBBLESTONE_STAIRS)));
    public static final RegistryObject<SlabBlock> SLIMY_COBBLESTONE_SLAB = registerBlock("slimy_cobblestone_slab",
            () -> new ModSlabBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE_SLAB)));
    public static final RegistryObject<WallBlock> SLIMY_COBBLESTONE_WALL = registerBlock("slimy_cobblestone_wall",
            () -> new ModWallBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE_WALL)));

    // Cobbled Deepslate
    public static final RegistryObject<StairBlock> SLIMY_COBBLED_DEEPSLATE_STAIRS = registerBlock("slimy_cobbled_deepslate_stairs",
            () -> new ModStairBlock(ModBlocks.SLIMY_COBBLED_DEEPSLATE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.COBBLED_DEEPSLATE_STAIRS)));
    public static final RegistryObject<SlabBlock> SLIMY_COBBLED_DEEPSLATE_SLAB = registerBlock("slimy_cobbled_deepslate_slab",
            () -> new ModSlabBlock(BlockBehaviour.Properties.copy(Blocks.COBBLED_DEEPSLATE_SLAB)));
    public static final RegistryObject<WallBlock> SLIMY_COBBLED_DEEPSLATE_WALL = registerBlock("slimy_cobbled_deepslate_wall",
            () -> new ModWallBlock(BlockBehaviour.Properties.copy(Blocks.COBBLED_DEEPSLATE_WALL)));

    // Slime Block
    public static final RegistryObject<Block> ENERGY_SLIME_BLOCK = registerBlock("energy_slime_block",
            () -> new SlimeBlock(0xFFffff70));

    public static void registerTierBlock(){
        for(Tier tier : Tier.values()){
            ModTiers modTiers = ModTierLists.getTierByName(tier);
            String blockName = modTiers.name() + "_slime_block";
            RegistryObject<Block> registeredBlock = registerBlock(blockName, () -> new SlimeBlock(modTiers.color()));
            ModTierLists.addRegisteredBlock(modTiers.name(), registeredBlock);
        }
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name,toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBlockWithoutItem(String name, Supplier<T> block){
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<BlockItem> registerBlockItem(String name, RegistryObject<T> block){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}