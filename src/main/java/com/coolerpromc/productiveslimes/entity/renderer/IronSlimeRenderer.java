package com.coolerpromc.productiveslimes.entity.renderer;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.slime.IronSlime;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Slime;

public class IronSlimeRenderer extends BaseSlimeRenderer {
    public IronSlimeRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, 0xF0898c8a);
    }
}
