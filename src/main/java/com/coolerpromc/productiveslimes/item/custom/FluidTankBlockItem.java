package com.coolerpromc.productiveslimes.item.custom;

import com.coolerpromc.productiveslimes.util.ModClientItemExtensions;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class FluidTankBlockItem extends BlockItem {
    public FluidTankBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new ModClientItemExtensions());
    }
}
