package com.coolerpromc.productiveslimes.entity.renderer;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.SlimeModel;
import com.coolerpromc.productiveslimes.entity.SlimeOuterLayer;
import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class BaseSlimeRenderer extends MobRenderer<BaseSlime, SlimeModel<BaseSlime>> {
    public static final ResourceLocation BASE_TEXTURE = new ResourceLocation(ProductiveSlimes.MODID, "textures/entity/template_slime_entity.png");

    public BaseSlimeRenderer(EntityRendererManager pContext, int color) {
        super(pContext, new SlimeModel<>(16, color), 0.05f);
        this.addLayer(new SlimeOuterLayer<>(this, color));
    }

    @Override
    public void render(BaseSlime p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
        this.shadowRadius = 0.25F * (float)p_225623_1_.getSize();
        super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
    }

    protected void scale(SlimeEntity p_225620_1_, MatrixStack p_225620_2_, float p_225620_3_) {
        float lvt_4_1_ = 0.999F;
        p_225620_2_.scale(0.999F, 0.999F, 0.999F);
        p_225620_2_.translate(0.0, 0.0010000000474974513, 0.0);
        float lvt_5_1_ = (float)p_225620_1_.getSize();
        float lvt_6_1_ = MathHelper.lerp(p_225620_3_, p_225620_1_.oSquish, p_225620_1_.squish) / (lvt_5_1_ * 0.5F + 1.0F);
        float lvt_7_1_ = 1.0F / (lvt_6_1_ + 1.0F);
        p_225620_2_.scale(lvt_7_1_ * lvt_5_1_, 1.0F / lvt_7_1_ * lvt_5_1_, lvt_7_1_ * lvt_5_1_);
    }

    @Override
    public ResourceLocation getTextureLocation(BaseSlime baseSlime) {
        return BASE_TEXTURE;
    }
}
