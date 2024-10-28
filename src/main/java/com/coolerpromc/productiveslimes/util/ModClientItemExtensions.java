package com.coolerpromc.productiveslimes.util;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class ModClientItemExtensions implements IClientItemExtensions {
    private final ModBlockEntityWithoutLevelRenderer blockEntityWithoutLevelRenderer = new ModBlockEntityWithoutLevelRenderer();

    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return blockEntityWithoutLevelRenderer;
    }
}
