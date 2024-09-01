package com.coolerpromc.productiveslimes.entity.renderer;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.slime.IronSlime;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Slime;

public class IronSlimeRenderer extends BaseSlimeRenderer {
    private static final ResourceLocation IRON_SLIME_LOCATION = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/entity/iron_slime.png");

    public IronSlimeRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(Slime pEntity) {
        return IRON_SLIME_LOCATION;
    }
}
