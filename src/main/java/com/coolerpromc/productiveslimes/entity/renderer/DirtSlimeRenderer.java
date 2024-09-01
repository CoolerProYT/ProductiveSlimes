package com.coolerpromc.productiveslimes.entity.renderer;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Slime;

public class DirtSlimeRenderer extends BaseSlimeRenderer {
    private static final ResourceLocation DIRT_SLIME_LOCATION = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/entity/dirt_slime.png");

    public DirtSlimeRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(Slime pEntity) {
        return DIRT_SLIME_LOCATION;
    }
}
