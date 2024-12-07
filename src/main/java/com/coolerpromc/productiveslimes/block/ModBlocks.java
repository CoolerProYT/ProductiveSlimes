package com.coolerpromc.productiveslimes.block;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.custom.*;
import com.coolerpromc.productiveslimes.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ProductiveSlimes.MODID);

    public static final RegistryObject<Block> MELTING_STATION = registerBlock("melting_station",
            () -> new MeltingStationBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> LIQUID_SOLIDING_STATION = registerBlock("soliding_station",
            () -> new SolidingStationBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> ENERGY_GENERATOR = registerBlock("energy_generator",
            () -> new EnergyGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> CABLE = registerBlock("cable",
            () -> new CableBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> DNA_EXTRACTOR = registerBlock("dna_extractor",
            () -> new DnaExtractorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> DNA_SYNTHESIZER = registerBlock("dna_synthesizer",
            () -> new DnaSynthesizerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> FLUID_TANK = registerBlock("fluid_tank",
            () -> new FluidTankBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

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

    public static final RegistryObject<Block> DIRT_SLIME_BLOCK = registerBlock("dirt_slime_block",
            () -> new SlimeBlock(MapColor.DIRT, 0xFF866043));

    public static final RegistryObject<Block> STONE_SLIME_BLOCK = registerBlock("stone_slime_block",
            () -> new SlimeBlock(MapColor.STONE, 0xFF4a4545));

    public static final RegistryObject<Block> COPPER_SLIME_BLOCK = registerBlock("copper_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_BROWN, 0xFF6a3e15));

    public static final RegistryObject<Block> IRON_SLIME_BLOCK = registerBlock("iron_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_LIGHT_GRAY, 0xFF898c8a));

    public static final RegistryObject<Block> GOLD_SLIME_BLOCK = registerBlock("gold_slime_block",
            () -> new SlimeBlock(MapColor.GOLD, 0xFFa5953f));

    public static final RegistryObject<Block> DIAMOND_SLIME_BLOCK = registerBlock("diamond_slime_block",
            () -> new SlimeBlock(MapColor.DIAMOND, 0xFF178f9c));

    public static final RegistryObject<Block> NETHERITE_SLIME_BLOCK = registerBlock("netherite_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_BROWN, 0xFF4c2b2b));

    public static final RegistryObject<Block> LAPIS_SLIME_BLOCK = registerBlock("lapis_slime_block",
            () -> new SlimeBlock(MapColor.LAPIS, 0xFF1c41ba));

    public static final RegistryObject<Block> REDSTONE_SLIME_BLOCK = registerBlock("redstone_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_RED, 0xFFa10505));

    public static final RegistryObject<Block> OAK_SLIME_BLOCK = registerBlock("oak_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_BROWN, 0xFFa69d6f));

    public static final RegistryObject<Block> SAND_SLIME_BLOCK = registerBlock("sand_slime_block",
            () -> new SlimeBlock(MapColor.SAND, 0xFFf7f7c6));

    public static final RegistryObject<Block> ANDESITE_SLIME_BLOCK = registerBlock("andesite_slime_block",
            () -> new SlimeBlock(MapColor.STONE, 0xFF9d9e9a));

    public static final RegistryObject<Block> SNOW_SLIME_BLOCK = registerBlock("snow_slime_block",
            () -> new SlimeBlock(MapColor.SNOW, 0xFFf2fcfc));

    public static final RegistryObject<Block> ICE_SLIME_BLOCK = registerBlock("ice_slime_block",
            () -> new SlimeBlock(MapColor.ICE, 0xFF89b1fc));

    public static final RegistryObject<Block> MUD_SLIME_BLOCK = registerBlock("mud_slime_block",
            () -> new SlimeBlock(MapColor.DIRT, 0xFF363339));

    public static final RegistryObject<Block> CLAY_SLIME_BLOCK = registerBlock("clay_slime_block",
            () -> new SlimeBlock(MapColor.CLAY, 0xFF9ca2ac));

    public static final RegistryObject<Block> RED_SAND_SLIME_BLOCK = registerBlock("red_sand_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_RED, 0xFFbb6520));

    public static final RegistryObject<Block> MOSS_SLIME_BLOCK = registerBlock("moss_slime_block",
            () -> new SlimeBlock(MapColor.GRASS, 0xFF4a6029));

    public static final RegistryObject<Block> DEEPSLATE_SLIME_BLOCK = registerBlock("deepslate_slime_block",
            () -> new SlimeBlock(MapColor.DEEPSLATE, 0xFF3c3c42));

    public static final RegistryObject<Block> GRANITE_SLIME_BLOCK = registerBlock("granite_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_RED, 0xFF835949));

    public static final RegistryObject<Block> DIORITE_SLIME_BLOCK = registerBlock("diorite_slime_block",
            () -> new SlimeBlock(MapColor.TERRACOTTA_WHITE, 0xFFadacad));

    public static final RegistryObject<Block> CALCITE_SLIME_BLOCK = registerBlock("calcite_slime_block",
            () -> new SlimeBlock(MapColor.TERRACOTTA_WHITE, 0xFFe9e9e3));

    public static final RegistryObject<Block> TUFF_SLIME_BLOCK = registerBlock("tuff_slime_block",
            () -> new SlimeBlock(MapColor.STONE, 0xFF55564c));

    public static final RegistryObject<Block> DRIPSTONE_SLIME_BLOCK = registerBlock("dripstone_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_BROWN, 0xFF806155));

    public static final RegistryObject<Block> PRISMARINE_SLIME_BLOCK = registerBlock("prismarine_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_LIGHT_BLUE, 0xFF529584));

    public static final RegistryObject<Block> MAGMA_SLIME_BLOCK = registerBlock("magma_slime_block",
            () -> new SlimeBlock(MapColor.FIRE, 0xFF561f1f));

    public static final RegistryObject<Block> OBSIDIAN_SLIME_BLOCK = registerBlock("obsidian_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_BLACK, 0xFF030106));

    public static final RegistryObject<Block> NETHERRACK_SLIME_BLOCK = registerBlock("netherrack_slime_block",
            () -> new SlimeBlock(MapColor.NETHER, 0xFF763535));

    public static final RegistryObject<Block> SOUL_SAND_SLIME_BLOCK = registerBlock("soul_sand_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_BROWN, 0xFF413127));

    public static final RegistryObject<Block> SOUL_SOIL_SLIME_BLOCK = registerBlock("soul_soil_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_BROWN, 0xFF392b23));

    public static final RegistryObject<Block> BLACKSTONE_SLIME_BLOCK = registerBlock("blackstone_slime_block",
            () -> new SlimeBlock(MapColor.DEEPSLATE, 0xFF201819));

    public static final RegistryObject<Block> BASALT_SLIME_BLOCK = registerBlock("basalt_slime_block",
            () -> new SlimeBlock(MapColor.DEEPSLATE, 0xFF565456));

    public static final RegistryObject<Block> ENDSTONE_SLIME_BLOCK = registerBlock("endstone_slime_block",
            () -> new SlimeBlock(MapColor.SAND, 0xFFcece8e));

    public static final RegistryObject<Block> QUARTZ_SLIME_BLOCK = registerBlock("quartz_slime_block",
            () -> new SlimeBlock(MapColor.QUARTZ, 0xFFe4ddd3));

    public static final RegistryObject<Block> GLOWSTONE_SLIME_BLOCK = registerBlock("glowstone_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_YELLOW, 0xFF784e27));

    public static final RegistryObject<Block> AMETHYST_SLIME_BLOCK = registerBlock("amethyst_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_PINK, 0xFF6b4da5));

    public static final RegistryObject<Block> BROWN_MUSHROOM_SLIME_BLOCK = registerBlock("brown_mushroom_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_BROWN, 0xFF967251));

    public static final RegistryObject<Block> RED_MUSHROOM_SLIME_BLOCK = registerBlock("red_mushroom_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_RED, 0xFFc02624));

    public static final RegistryObject<Block> CACTUS_SLIME_BLOCK = registerBlock("cactus_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_GREEN, 0xFF476d21));
  
    public static final RegistryObject<Block> COAL_SLIME_BLOCK = registerBlock("coal_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_BLACK, 0xFF3b3d3b));

    public static final RegistryObject<Block> GRAVEL_SLIME_BLOCK = registerBlock("gravel_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_GRAY, 0xFF4a444b));

    public static final RegistryObject<Block> ENERGY_SLIME_BLOCK = registerBlock("energy_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_YELLOW, 0xFFffff70));

    public static final RegistryObject<Block> OAK_LEAVES_SLIME_BLOCK = registerBlock("oak_leaves_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_GREEN, 0xFF48b518));

    private static RegistryObject<Block> registerBlock(String name, Supplier<Block> block){
        RegistryObject<Block> toReturn = BLOCKS.register(name, block);
        if (!(block.get() instanceof FluidTankBlock)){
            registerBlockItem(name,toReturn);
        }
        return toReturn;
    }

    private static RegistryObject<BlockItem> registerBlockItem(String name, RegistryObject<Block> block){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}