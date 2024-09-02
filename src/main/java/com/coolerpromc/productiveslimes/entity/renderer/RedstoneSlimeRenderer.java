package com.coolerpromc.productiveslimes.entity.renderer;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Slime;

public class RedstoneSlimeRenderer extends BaseSlimeRenderer {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/entity/netherite_slime.png");

    public RedstoneSlimeRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(Slime pEntity) {
        return TEXTURE;
    }
}
