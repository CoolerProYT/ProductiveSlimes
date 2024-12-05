package com.coolerpromc.productiveslimes.block;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.custom.*;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
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
    public static final DeferredBlock<Block> FLUID_TANK = registerBlock("fluid_tank", FluidTankBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion());

    public static final DeferredBlock<Block> SLIMY_GRASS_BLOCK = registerBlock("slimy_grass_block", SlimyBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK));
    public static final DeferredBlock<Block> SLIMY_DIRT = registerBlock("slimy_dirt", SlimyBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT));
    public static final DeferredBlock<Block> SLIMY_STONE = registerBlock("slimy_stone", SlimyBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));
    public static final DeferredBlock<Block> SLIMY_DEEPSLATE = registerBlock("slimy_deepslate", SlimyBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE));
    public static final DeferredBlock<Block> SLIMY_COBBLESTONE = registerBlock("slimy_cobblestone", SlimyBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE));
    public static final DeferredBlock<Block> SLIMY_COBBLED_DEEPSLATE = registerBlock("slimy_cobbled_deepslate", SlimyBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE));

    public static final DeferredBlock<Block> ENERGY_SLIME_BLOCK = registerSlimeBlock("energy_slime_block", MapColor.COLOR_YELLOW, 0xFFffff70);

    public static void registerTierBlocks(){
        for (Tier name : Tier.values()){
            ModTiers tier = ModTierLists.getTierByName(name);
            String blockName = tier.getName() + "_slime_block";
            DeferredBlock<Block> registeredSlimeBlock = registerSlimeBlock(blockName, MapColor.byId(tier.getMapColorId()), tier.getColor());
            ModTierLists.addRegisteredBlock(tier.getName(), registeredSlimeBlock);
        }
    }

    private static DeferredBlock<Block> registerBlock(String name, Function<BlockBehaviour.Properties, ? extends Block> func, BlockBehaviour.Properties properties){
        DeferredBlock<Block> toReturn = BLOCKS.registerBlock(name, func, properties);
        registerBlockItem(name,toReturn);
        return toReturn;
    }

    private static DeferredBlock<Block> registerSlimeBlock(String name, MapColor mapColor, int color){
        return registerBlock(name, properties -> new SlimeBlock(properties, mapColor, color), BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK).noOcclusion());
    }

    private static DeferredItem<BlockItem> registerBlockItem(String name, DeferredBlock<Block> block){
        return ModItems.ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}