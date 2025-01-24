package com.coolerpromc.productiveslimes.entity;

import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;

public class SlimeOuterLayer<T extends BaseSlime> extends LayerRenderer<T, SlimeModel<T>> {
    private final EntityModel<T> model;
    private final int color;

    public SlimeOuterLayer(IEntityRenderer<T, SlimeModel<T>> pRenderer, int color) {
        super(pRenderer);
        this.model = new SlimeModel<>(0, color);
        this.color = color;
    }

    public void render(MatrixStack pPoseStack, IRenderTypeBuffer pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        Minecraft minecraft = Minecraft.getInstance();
        boolean flag = minecraft.shouldEntityAppearGlowing(pLivingEntity) && pLivingEntity.isInvisible();
        if (!pLivingEntity.isInvisible() || flag) {
            IVertexBuilder vertexconsumer;
            if (flag) {
                vertexconsumer = pBuffer.getBuffer(RenderType.outline(this.getTextureLocation(pLivingEntity)));
            } else {
                vertexconsumer = pBuffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(pLivingEntity)));
            }

            int alpha = (this.color >> 24) & 0xFF;
            int red = (this.color >> 16) & 0xFF;
            int green = (this.color >> 8) & 0xFF;
            int blue = this.color & 0xFF;

            float normalizedAlpha = alpha / 255.0f;
            float normalizedRed = red / 255.0f;
            float normalizedGreen = green / 255.0f;
            float normalizedBlue = blue / 255.0f;

            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks);
            this.model.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
            this.model.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, LivingRenderer.getOverlayCoords(pLivingEntity, 0.0F), normalizedRed, normalizedGreen, normalizedBlue, normalizedAlpha);
        }
    }
}
