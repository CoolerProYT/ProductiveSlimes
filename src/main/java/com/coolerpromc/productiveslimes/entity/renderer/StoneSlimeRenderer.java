package com.coolerpromc.productiveslimes.entity.renderer;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Slime;

public class StoneSlimeRenderer extends BaseSlimeRenderer {
    private static final ResourceLocation STONE_SLIME_LOCATION =
            ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/entity/stone_slime.png");

    public StoneSlimeRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(Slime pEntity) {
        return STONE_SLIME_LOCATION;
    }
}
