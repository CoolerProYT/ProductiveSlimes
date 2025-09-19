package com.coolerpromc.productiveslimes.block.entity.renderer;

import com.coolerpromc.productiveslimes.block.entity.DnaExtractorBlockEntity;
import com.coolerpromc.productiveslimes.block.entity.renderstate.DnaExtractorBlockEntityRenderState;
import com.coolerpromc.productiveslimes.item.custom.SlimeballItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class DnaExtractorBlockEntityRenderer implements BlockEntityRenderer<DnaExtractorBlockEntity, DnaExtractorBlockEntityRenderState> {
    private final ItemModelResolver itemModelResolver;
    public DnaExtractorBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {
        this.itemModelResolver = pContext.itemModelResolver();
    }

    private int getLightLevel(Level level, BlockPos blockPos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, blockPos);
        int sLight = level.getBrightness(LightLayer.SKY, blockPos);
        return LightTexture.pack(bLight, sLight);
    }

    @Override
    public DnaExtractorBlockEntityRenderState createRenderState() {
        return new DnaExtractorBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(DnaExtractorBlockEntity blockEntity, DnaExtractorBlockEntityRenderState renderState, float partialTick, Vec3 p_445788_, @Nullable ModelFeatureRenderer.CrumblingOverlay p_446944_) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, p_445788_, p_446944_);

        renderState.blockEntity = blockEntity;
        renderState.rotation = renderState.getRenderingRotation();

        ItemStackRenderState itemstackrenderstate = new ItemStackRenderState();
        this.itemModelResolver.updateForTopItem(itemstackrenderstate, blockEntity.getRenderStack(), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 1);
        renderState.itemStackRenderState = itemstackrenderstate;
    }

    @Override
    public void submit(DnaExtractorBlockEntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        DnaExtractorBlockEntity blockEntity = renderState.blockEntity;
        ItemStack itemStack = blockEntity.getRenderStack();

        poseStack.pushPose();
        if (itemStack.getItem() instanceof SlimeballItem){
            poseStack.translate(0.5, 0.4, 0.5);
        }
        else{
            poseStack.translate(0.5, 0.5, 0.5);
        }
        poseStack.scale(0.35f, 0.35f, 0.35f);
        poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getRenderingRotation()));
        renderState.itemStackRenderState.submit(poseStack, nodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
//        ItemRenderer.renderItem(itemStack, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, pBufferSource, blockEntity.getLevel(), 1);

        poseStack.popPose();
    }
}
