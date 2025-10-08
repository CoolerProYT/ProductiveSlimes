package com.coolerpromc.productiveslimes.block.entity.renderer;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.block.custom.SlimeSqueezerBlock;
import com.coolerpromc.productiveslimes.block.entity.SlimeSqueezerBlockEntity;
import com.coolerpromc.productiveslimes.block.entity.renderstate.SlimeSqueezerBlockEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SlimeSqueezerBlockEntityRenderer implements BlockEntityRenderer<SlimeSqueezerBlockEntity, SlimeSqueezerBlockEntityRenderState> {
    public ItemModelResolver itemModelResolver;

    public SlimeSqueezerBlockEntityRenderer(BlockEntityRendererProvider.Context context){
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public void extractRenderState(SlimeSqueezerBlockEntity blockEntity, SlimeSqueezerBlockEntityRenderState renderState, float p_446851_, Vec3 p_445788_, @Nullable ModelFeatureRenderer.CrumblingOverlay p_446944_) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, p_446851_, p_445788_, p_446944_);
        renderState.blockEntity = blockEntity;

        List<ItemStack> itemStacks = List.of(
                blockEntity.getInputHandler().getResource(0).toStack(),
                blockEntity.getOutputHandler().getResource(0).toStack(),
                blockEntity.getOutputHandler().getResource(1).toStack()
        );

        ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
        ItemStackRenderState itemStackRenderState2 = new ItemStackRenderState();
        ItemStackRenderState itemStackRenderState3 = new ItemStackRenderState();

        this.itemModelResolver.updateForTopItem(itemStackRenderState, itemStacks.get(0), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 1);
        this.itemModelResolver.updateForTopItem(itemStackRenderState2, itemStacks.get(1), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 2);
        this.itemModelResolver.updateForTopItem(itemStackRenderState3, itemStacks.get(2), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 3);

        renderState.itemStackRenderStates.add(itemStackRenderState);
        renderState.itemStackRenderStates.add(itemStackRenderState2);
        renderState.itemStackRenderStates.add(itemStackRenderState3);
    }

    @Override
    public void submit(SlimeSqueezerBlockEntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState p_451022_) {
        SlimeSqueezerBlockEntity blockEntity = renderState.blockEntity;
        BlockStateModel squeezer = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getBlockModel(ModBlocks.SQUEEZER.get().defaultBlockState());
        float progressRatio = (float) blockEntity.getData().get(0) / (float) blockEntity.getData().get(1);
        float startPoint = 0.8f;
        float endPoint = 0.15f;
        float squeezerPosition = startPoint - ((startPoint - endPoint) * progressRatio);
        float x1 = 0, x2 = 0, y1 = 0, y2 = 0, z1 = 0, z2 = 0;

        Direction facing = blockEntity.getBlockState().getValue(SlimeSqueezerBlock.FACING);
        // Render the squeezer
        poseStack.pushPose();
        poseStack.translate(0, squeezerPosition, 0);
        renderModel(squeezer, poseStack, nodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        // Render the input item
        poseStack.pushPose();
        poseStack.translate(0.5f, 0.09, 0.5f);
        poseStack.scale(0.35f, 0.35f, 0.35f);
        poseStack.mulPose(Axis.XP.rotationDegrees(270));
        renderState.itemStackRenderStates.get(0).submit(poseStack, nodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
        switch (facing){
            case SOUTH:
                x1 = 0.0625f; x2 = 0.9375f; y1 = 0.175f; y2 = 0.175f; z1 = 0.5f; z2 = 0.5f;
                break;
            case NORTH:
                x1 = 0.9375f; x2 = 0.0625f; y1 = 0.175f; y2 = 0.175f; z1 = 0.5f; z2 = 0.5f;
                break;
            case EAST:
                x1 = 0.5f; x2 = 0.5f; y1 = 0.175f; y2 = 0.175f; z1 = 0.9375f; z2 = 0.0625f;
                break;
            case WEST:
                x1 = 0.5f; x2 = 0.5f; y1 = 0.175f; y2 = 0.175f; z1 = 0.0625f; z2 = 0.9375f;
                break;
        }
        // Render the output item 1
        poseStack.pushPose();
        poseStack.translate(x1, y1, z1);
        poseStack.scale(0.15f, 0.15f, 0.15f);
        poseStack.mulPose(Axis.XP.rotationDegrees(270));
        renderState.itemStackRenderStates.get(1).submit(poseStack, nodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
        // Render the output item 2
        poseStack.pushPose();
        poseStack.translate(x2, y2, z2);
        poseStack.scale(0.15f, 0.15f, 0.15f);
        poseStack.mulPose(Axis.XP.rotationDegrees(270));
        renderState.itemStackRenderStates.get(2).submit(poseStack, nodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }

    private void renderModel(BlockStateModel model, PoseStack poseStack, SubmitNodeCollector nodeCollector, int light, int overlay) {
        RandomSource rand = RandomSource.create();
        for (Direction direction : Direction.values()) {
            rand.setSeed(42L);
            nodeCollector.submitBlockModel(poseStack, RenderType.cutout(), model, 1.0f, 1.0f, 1.0f, light, overlay, 0);
        }
    }
    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

    @Override
    public SlimeSqueezerBlockEntityRenderState createRenderState() {
        return new SlimeSqueezerBlockEntityRenderState();
    }
}