package com.coolerpromc.productiveslimes.entity.renderer;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.SlimeModel;
import com.coolerpromc.productiveslimes.entity.SlimeOuterLayer;
import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BaseSlimeRenderer extends MobRenderer<BaseSlime, SlimeModel<BaseSlime>> {
    private static final ResourceLocation BASE_TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/entity/template_slime_entity.png");

    public BaseSlimeRenderer(EntityRendererProvider.Context pContext, int color) {
        super(pContext, new SlimeModel<>(pContext.bakeLayer(ModelLayers.SLIME), color), 0.05f);
        this.addLayer(new SlimeOuterLayer<>(this, pContext.getModelSet(), color));
    }

    @Override
    public void render(BaseSlime pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        this.shadowRadius = 0.15f;
        float scale = pEntity.getSize();
        pPoseStack.scale(scale, scale, scale);

        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(BaseSlime pEntity) {
        return BASE_TEXTURE;
    }
}
