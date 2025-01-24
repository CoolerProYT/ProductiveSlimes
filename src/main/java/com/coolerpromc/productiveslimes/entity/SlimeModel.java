package com.coolerpromc.productiveslimes.entity;

import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class SlimeModel<T extends BaseSlime> extends SegmentedModel<T> {
    public final int color;
    private final ModelRenderer cube;
    private final ModelRenderer eye0;
    private final ModelRenderer eye1;
    private final ModelRenderer mouth;

    public SlimeModel(int p_i1157_1_, int color) {
        this.cube = new ModelRenderer(this, 0, p_i1157_1_);
        this.eye0 = new ModelRenderer(this, 32, 0);
        this.eye1 = new ModelRenderer(this, 32, 4);
        this.mouth = new ModelRenderer(this, 32, 8);
        if (p_i1157_1_ > 0) {
            this.cube.addBox(-3.0F, 17.0F, -3.0F, 6.0F, 6.0F, 6.0F);
            this.eye0.addBox(-3.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F);
            this.eye1.addBox(1.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F);
            this.mouth.addBox(0.0F, 21.0F, -3.5F, 1.0F, 1.0F, 1.0F);
        } else {
            this.cube.addBox(-4.0F, 16.0F, -4.0F, 8.0F, 8.0F, 8.0F);
        }

        this.color = color;
    }

    @Override
    public void setupAnim(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
    }

    @Override
    public void renderToBuffer(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
        int alpha = (this.color >> 24) & 0xFF;
        int red = (this.color >> 16) & 0xFF;
        int green = (this.color >> 8) & 0xFF;
        int blue = this.color & 0xFF;

        float normalizedAlpha = alpha / 255.0f;
        float normalizedRed = red / 255.0f;
        float normalizedGreen = green / 255.0f;
        float normalizedBlue = blue / 255.0f;

        super.renderToBuffer(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, normalizedRed, normalizedGreen, normalizedBlue, normalizedAlpha);
    }

    @Override
    public Iterable<ModelRenderer> parts() {
        return ImmutableList.of(this.cube, this.eye0, this.eye1, this.mouth);
    }
}
