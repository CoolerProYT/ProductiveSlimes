package com.coolerpromc.productiveslimes.util;

import com.coolerpromc.productiveslimes.datacomponent.ModDataComponents;
import com.coolerpromc.productiveslimes.entity.SlimeModel;
import com.coolerpromc.productiveslimes.entity.renderer.BaseSlimeRenderer;
import com.coolerpromc.productiveslimes.handler.SlimeData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.SlimeRenderState;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Set;

public record SlimeItemSpecialRenderer() implements SpecialModelRenderer<SlimeData> {
    @Nullable
    @Override
    public SlimeData extractArgument(ItemStack stack) {
        return stack.get(ModDataComponents.SLIME_DATA);
    }

    @Override
    public void submit(@Nullable SlimeData slimeData, ItemDisplayContext displayContext, PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, int packedOverlay, boolean hasFoilType) {
        EntityModelSet entityModelSet = Minecraft.getInstance().getEntityModels();
        SlimeModel slimeModel = new SlimeModel(entityModelSet.bakeLayer(ModelLayers.SLIME), -1);
        SlimeModel slimeModelOuter = new SlimeModel(entityModelSet.bakeLayer(ModelLayers.SLIME_OUTER), -1);

        if (slimeData != null){
            poseStack.pushPose();
            poseStack.scale(2, 2, 2);
            poseStack.translate(0.25f, 1.5f, 0.25f);
            poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            nodeCollector.order(0).submitModel(slimeModel, new SlimeRenderState(), poseStack, RenderType.entityTranslucent(BaseSlimeRenderer.BASE_TEXTURE), packedLight, packedOverlay, slimeData.color(), null, 0, null);
            nodeCollector.order(1).submitModel(slimeModelOuter, new SlimeRenderState(), poseStack, RenderType.entityTranslucent(BaseSlimeRenderer.BASE_TEXTURE), packedLight, packedOverlay, slimeData.color(), null, 0, null);
            poseStack.popPose();
        }
    }

    @Override
    public void getExtents(Set<Vector3f> extents) {
        extents.add(new Vector3f(0.0f, 0.0f, 0.0f));
        extents.add(new Vector3f(1.0f, 1.0f, 1.0f));
    }

    public record Unbaked(ResourceLocation texture) implements SpecialModelRenderer.Unbaked{
        public static final MapCodec<Unbaked> MAP_CODEC = ResourceLocation.CODEC.fieldOf("texture").xmap(SlimeItemSpecialRenderer.Unbaked::new, SlimeItemSpecialRenderer.Unbaked::texture);

        @Override
        public @Nullable SpecialModelRenderer<?> bake(BakingContext p_433472_) {
            return new SlimeItemSpecialRenderer();
        }

        @Override
        public MapCodec<? extends SpecialModelRenderer.Unbaked> type() {
            return MAP_CODEC;
        }
    }
}