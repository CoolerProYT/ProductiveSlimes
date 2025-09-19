package com.coolerpromc.productiveslimes.block.entity.renderer;

import com.coolerpromc.productiveslimes.block.entity.DnaSynthesizerBlockEntity;
import com.coolerpromc.productiveslimes.block.entity.renderstate.DnaSynthesizerBlockEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DnaSynthesizerBlockEntityRenderer implements BlockEntityRenderer<DnaSynthesizerBlockEntity, DnaSynthesizerBlockEntityRenderState> {
    private final ItemModelResolver itemModelResolver;

    public DnaSynthesizerBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {
        this.itemModelResolver = pContext.itemModelResolver();
    }

    @Override
    public void extractRenderState(DnaSynthesizerBlockEntity blockEntity, DnaSynthesizerBlockEntityRenderState renderState, float p_446851_, Vec3 p_445788_, @Nullable ModelFeatureRenderer.CrumblingOverlay p_446944_) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, p_446851_, p_445788_, p_446944_);
        renderState.blockEntity = blockEntity;

        List<ItemStack> itemStacks = List.of(
                blockEntity.getInputHandler().getStackInSlot(0),
                blockEntity.getInputHandler().getStackInSlot(1),
                blockEntity.getInputHandler().getStackInSlot(2),
                blockEntity.getOutputHandler().getStackInSlot(0)
        );

        ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
        ItemStackRenderState itemStackRenderState2 = new ItemStackRenderState();
        ItemStackRenderState itemStackRenderState3 = new ItemStackRenderState();
        ItemStackRenderState itemStackRenderState4 = new ItemStackRenderState();

        this.itemModelResolver.updateForTopItem(itemStackRenderState, itemStacks.get(0), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 1);
        this.itemModelResolver.updateForTopItem(itemStackRenderState2, itemStacks.get(1), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 2);
        this.itemModelResolver.updateForTopItem(itemStackRenderState3, itemStacks.get(2), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 3);
        this.itemModelResolver.updateForTopItem(itemStackRenderState4, itemStacks.get(3), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 4);

        renderState.itemStackRenderStates.add(itemStackRenderState);
        renderState.itemStackRenderStates.add(itemStackRenderState2);
        renderState.itemStackRenderStates.add(itemStackRenderState3);
        renderState.itemStackRenderStates.add(itemStackRenderState4);
    }

    @Override
    public void submit(DnaSynthesizerBlockEntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState p_451022_) {
        poseStack.pushPose();

        Direction facing = renderState.blockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);

        switch (facing) {
            case EAST:
                // Render the input slots
                renderItem(renderState.itemStackRenderStates.get(0), poseStack, nodeCollector, renderState.blockEntity, renderState, 0.3f, 0, 0.25f);
                renderItem(renderState.itemStackRenderStates.get(1), poseStack, nodeCollector, renderState.blockEntity, renderState, -0.3f, 0, 0.25f);
                renderItem(renderState.itemStackRenderStates.get(2), poseStack, nodeCollector, renderState.blockEntity, renderState, 0, 0, 0.125f);

                // Render the output slot
                renderItem(renderState.itemStackRenderStates.get(3), poseStack, nodeCollector, renderState.blockEntity, renderState, 0, 0, -0.25f);
                break;
            case WEST:
                // Render the input slots
                renderItem(renderState.itemStackRenderStates.get(0), poseStack, nodeCollector, renderState.blockEntity, renderState, -0.3f, 0, -0.25f);
                renderItem(renderState.itemStackRenderStates.get(1), poseStack, nodeCollector, renderState.blockEntity, renderState, 0.3f, 0, -0.25f);
                renderItem(renderState.itemStackRenderStates.get(2), poseStack, nodeCollector, renderState.blockEntity, renderState, 0, 0, -0.125f);

                // Render the output slot
                renderItem(renderState.itemStackRenderStates.get(3), poseStack, nodeCollector, renderState.blockEntity, renderState, 0, 0, 0.25f);
                break;
            case NORTH:
                // Render the input slots
                renderItem(renderState.itemStackRenderStates.get(0), poseStack, nodeCollector, renderState.blockEntity, renderState, 0.25f, 0, 0.3f);
                renderItem(renderState.itemStackRenderStates.get(1), poseStack, nodeCollector, renderState.blockEntity, renderState, 0.25f, 0, -0.3f);
                renderItem(renderState.itemStackRenderStates.get(2), poseStack, nodeCollector, renderState.blockEntity, renderState, 0.125f, 0, 0);

                // Render the output slot
                renderItem(renderState.itemStackRenderStates.get(3), poseStack, nodeCollector, renderState.blockEntity, renderState, -0.25f, 0, 0);
                break;
            case SOUTH:
                // Render the input slots
                renderItem(renderState.itemStackRenderStates.get(0), poseStack, nodeCollector, renderState.blockEntity, renderState, -0.25f, 0, -0.3f);
                renderItem(renderState.itemStackRenderStates.get(1), poseStack, nodeCollector, renderState.blockEntity, renderState, -0.25f, 0, 0.3f);
                renderItem(renderState.itemStackRenderStates.get(2), poseStack, nodeCollector, renderState.blockEntity, renderState, -0.125f, 0, 0);

                // Render the output slot
                renderItem(renderState.itemStackRenderStates.get(3), poseStack, nodeCollector, renderState.blockEntity, renderState, 0.25f, 0, 0f);
                break;

        }

        poseStack.popPose();
    }

    private void renderItem(ItemStackRenderState itemStackRenderState, PoseStack pPoseStack, SubmitNodeCollector nodeCollector, DnaSynthesizerBlockEntity pBlockEntity, DnaSynthesizerBlockEntityRenderState renderState, float xOffset, float yOffset, float zOffset) {
        pPoseStack.pushPose();
        pPoseStack.translate(0.5 + xOffset, 0.35 + yOffset, 0.5 + zOffset);
        pPoseStack.scale(0.25f, 0.25f, 0.25f);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(pBlockEntity.getRenderingRotation()));

        itemStackRenderState.submit(pPoseStack, nodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);

        pPoseStack.popPose();
    }

    @Override
    public DnaSynthesizerBlockEntityRenderState createRenderState() {
        return new DnaSynthesizerBlockEntityRenderState();
    }
}
