package com.coolerpromc.productiveslimes.entity.renderer;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.SlimeModel;
import com.coolerpromc.productiveslimes.entity.SlimeOuterLayer;
import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class BaseSlimeRenderer extends MobRenderer<BaseSlime, SlimeModel<BaseSlime>> {
    public static final ResourceLocation BASE_TEXTURE = new ResourceLocation(ProductiveSlimes.MODID, "textures/entity/template_slime_entity.png");

    public BaseSlimeRenderer(EntityRendererManager pContext, int color) {
        super(pContext, new SlimeModel<>(16, color), 0.25f);
        this.addLayer(new SlimeOuterLayer<>(this, color));
    }

    @Override
    public void render(BaseSlime pEntity, float pEntityYaw, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight) {
        this.shadowRadius = 0.25F * (float)pEntity.getSize();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    protected void scale(BaseSlime pLivingEntity, MatrixStack pPoseStack, float pPartialTickTime) {
        float f = 0.999F;
        pPoseStack.scale(0.999F, 0.999F, 0.999F);
        pPoseStack.translate(0.0D, (double)0.001F, 0.0D);
        float f1 = (float)pLivingEntity.getSize();
        float f2 = MathHelper.lerp(pPartialTickTime, pLivingEntity.oSquish, pLivingEntity.squish) / (f1 * 0.5F + 1.0F);
        float f3 = 1.0F / (f2 + 1.0F);
        pPoseStack.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }

    @Override
    public ResourceLocation getTextureLocation(BaseSlime baseSlime) {
        return BASE_TEXTURE;
    }
}
