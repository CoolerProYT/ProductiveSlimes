package com.coolerpromc.productiveslimes.item.custom;

import com.coolerpromc.productiveslimes.util.ModBlockEntityWithoutLevelRenderer;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;


public class FluidTankBlockItem extends BlockItem {
    public FluidTankBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties.setISTER(() -> ModBlockEntityWithoutLevelRenderer::new));
    }
}
