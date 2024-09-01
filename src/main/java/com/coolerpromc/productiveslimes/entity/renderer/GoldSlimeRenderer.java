package com.coolerpromc.productiveslimes.entity.renderer;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.slime.GoldSlime;
import com.coolerpromc.productiveslimes.entity.slime.IronSlime;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Slime;

public class GoldSlimeRenderer extends BaseSlimeRenderer {
    private static final ResourceLocation GOLD_SLIME_LOCATION = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/entity/gold_slime.png");

    public GoldSlimeRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(Slime pEntity) {
        return GOLD_SLIME_LOCATION;
    }
}
