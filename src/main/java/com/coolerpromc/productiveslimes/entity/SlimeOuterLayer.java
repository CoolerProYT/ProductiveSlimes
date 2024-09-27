package com.coolerpromc.productiveslimes.entity;

import com.coolerpromc.productiveslimes.entity.renderer.BaseSlimeRenderer;
import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.SlimeRenderState;

public class SlimeOuterLayer extends RenderLayer<SlimeRenderState, SlimeModel> {
    private final SlimeModel model;

    public SlimeOuterLayer(RenderLayerParent<SlimeRenderState, SlimeModel> pRenderer, EntityModelSet pModelSet, int color) {
        super(pRenderer);
        this.model = new SlimeModel(pModelSet.bakeLayer(ModelLayers.SLIME_OUTER), color);
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, SlimeRenderState p_361554_, float p_117353_, float p_117354_) {
        Minecraft minecraft = Minecraft.getInstance();
        boolean flag = p_361554_.appearsGlowing && p_361554_.isInvisible;
        if (!p_361554_.isInvisible || flag) {
            VertexConsumer vertexconsumer;
            if (flag) {
                vertexconsumer = pBuffer.getBuffer(RenderType.outline(BaseSlimeRenderer.BASE_TEXTURE));
            } else {
                vertexconsumer = pBuffer.getBuffer(RenderType.entityTranslucent(BaseSlimeRenderer.BASE_TEXTURE));
            }

            this.model.setupAnim(p_361554_);
            this.model.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, LivingEntityRenderer.getOverlayCoords(p_361554_, 0.0F));
        }
    }
}
