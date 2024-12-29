package com.coolerpromc.productiveslimes.util;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.IItemRenderProperties;

public class ModClientItemExtensions implements IItemRenderProperties {
    private final ModBlockEntityWithoutLevelRenderer blockEntityWithoutLevelRenderer = new ModBlockEntityWithoutLevelRenderer();

    @Override
    public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
        return blockEntityWithoutLevelRenderer;
    }
}
