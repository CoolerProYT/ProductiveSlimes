package com.coolerpromc.productiveslimes.entity.renderer;

import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import com.coolerpromc.productiveslimes.entity.slime.IronSlime;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SlimeOuterLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Slime;

public abstract class BaseSlimeRenderer extends MobRenderer<Slime, SlimeModel<Slime>> {
    public BaseSlimeRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new SlimeModel<>(pContext.bakeLayer(ModelLayers.SLIME)), 0.05f);
        this.addLayer(new SlimeOuterLayer<>(this, pContext.getModelSet()));
    }

    @Override
    public abstract ResourceLocation getTextureLocation(Slime pEntity);

    @Override
    public void render(Slime pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        this.shadowRadius = 0.15f;
        float scale = pEntity.getSize();
        pPoseStack.scale(scale, scale, scale);
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}
