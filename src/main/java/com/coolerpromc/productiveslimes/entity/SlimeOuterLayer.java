package com.coolerpromc.productiveslimes.entity;

import com.coolerpromc.productiveslimes.entity.renderer.BaseSlimeRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.SlimeRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;

public class SlimeOuterLayer extends RenderLayer<SlimeRenderState, SlimeModel> {
    private final SlimeModel model;

    public SlimeOuterLayer(RenderLayerParent<SlimeRenderState, SlimeModel> pRenderer, EntityModelSet pModelSet, int color) {
        super(pRenderer);
        this.model = new SlimeModel(pModelSet.bakeLayer(ModelLayers.SLIME_OUTER), color);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int light, SlimeRenderState slimeRenderState, float p_435384_, float p_433573_) {
        boolean flag = slimeRenderState.appearsGlowing() && slimeRenderState.isInvisible;
        if (!slimeRenderState.isInvisible || flag) {
            int i = LivingEntityRenderer.getOverlayCoords(slimeRenderState, 0.0F);
            if (flag) {
                nodeCollector.order(1).submitModel(this.model, slimeRenderState, poseStack, RenderTypes.outline(BaseSlimeRenderer.BASE_TEXTURE), light, i, this.model.color, null, slimeRenderState.outlineColor, null);
            } else {
                nodeCollector.order(1).submitModel(this.model, slimeRenderState, poseStack, RenderTypes.entityTranslucent(BaseSlimeRenderer.BASE_TEXTURE), light, i, this.model.color, null, slimeRenderState.outlineColor, null);
            }
            nodeCollector.order(0).submitModel(this.getParentModel(), slimeRenderState, poseStack, RenderTypes.entityTranslucent(BaseSlimeRenderer.BASE_TEXTURE), light, i, this.model.color, null, slimeRenderState.outlineColor, null);
        }
    }
}
