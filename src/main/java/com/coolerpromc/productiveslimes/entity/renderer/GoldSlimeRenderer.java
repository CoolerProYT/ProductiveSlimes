package com.coolerpromc.productiveslimes.entity.renderer;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.slime.GoldSlime;
import com.coolerpromc.productiveslimes.entity.slime.IronSlime;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Slime;

public class GoldSlimeRenderer extends BaseSlimeRenderer {
    public GoldSlimeRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, 0xF0a5953f);
    }
}
