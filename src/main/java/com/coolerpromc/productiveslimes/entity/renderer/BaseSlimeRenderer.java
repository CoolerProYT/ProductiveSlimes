package com.coolerpromc.productiveslimes.entity.renderer;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.SlimeModel;
import com.coolerpromc.productiveslimes.entity.SlimeOuterLayer;
import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.SlimeRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BaseSlimeRenderer extends MobRenderer<BaseSlime, SlimeRenderState, SlimeModel> {
    public static final ResourceLocation BASE_TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/entity/template_slime_entity.png");

    public BaseSlimeRenderer(EntityRendererProvider.Context pContext, int color) {
        super(pContext, new SlimeModel(pContext.bakeLayer(ModelLayers.SLIME), color), 0.05f);
        this.addLayer(new SlimeOuterLayer(this, pContext.getModelSet(), color));
    }

    @Override
    public void submit(SlimeRenderState p_433493_, PoseStack p_434615_, SubmitNodeCollector p_433768_, CameraRenderState p_450931_) {
        this.shadowRadius = 0.25F * (float)p_433493_.size;
        super.submit(p_433493_, p_434615_, p_433768_, p_450931_);
    }

    @Override
    public SlimeRenderState createRenderState() {
        return new SlimeRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(SlimeRenderState p_368654_) {
        return BASE_TEXTURE;
    }

    @Override
    protected void scale(SlimeRenderState p_362272_, PoseStack p_115315_) {
        float f = 0.999F;
        p_115315_.scale(0.999F, 0.999F, 0.999F);
        p_115315_.translate(0.0F, 0.001F, 0.0F);
        float f1 = (float)p_362272_.size;
        float f2 = p_362272_.squish / (f1 * 0.5F + 1.0F);
        float f3 = 1.0F / (f2 + 1.0F);
        p_115315_.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }

    @Override
    public void extractRenderState(BaseSlime p_362733_, SlimeRenderState p_360515_, float p_361157_) {
        super.extractRenderState(p_362733_, p_360515_, p_361157_);
        p_360515_.squish = Mth.lerp(p_361157_, p_362733_.oSquish, p_362733_.squish);
        p_360515_.size = p_362733_.getSize();
    }
}
