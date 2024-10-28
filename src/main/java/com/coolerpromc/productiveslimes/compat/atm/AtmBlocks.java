package com.coolerpromc.productiveslimes.compat.atm;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.custom.SlimeBlock;
import com.coolerpromc.productiveslimes.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class AtmBlocks {
    public static DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ProductiveSlimes.MODID);

    public static final DeferredBlock<Block> ATM_SLIME_BLOCK = registerBlock("atm_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_ORANGE, 0xF0ff8b04));

    public static final DeferredBlock<Block> VIBRANIUM_SLIME_BLOCK = registerBlock("vibranium_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_LIGHT_BLUE, 0xF0148484));

    public static final DeferredBlock<Block> UNOBTAINIUM_SLIME_BLOCK = registerBlock("unobtainium_slime_block",
            () -> new SlimeBlock(MapColor.COLOR_PURPLE, 0xF06c1ce4));

    private static DeferredBlock<Block> registerBlock(String name, Supplier<Block> block){
        DeferredBlock<Block> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name,toReturn);
        return toReturn;
    }

    private static DeferredItem<BlockItem> registerBlockItem(String name, DeferredBlock<Block> block){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
