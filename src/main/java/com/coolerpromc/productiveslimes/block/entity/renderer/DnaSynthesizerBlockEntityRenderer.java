package com.coolerpromc.productiveslimes.block.entity.renderer;

import com.coolerpromc.productiveslimes.block.entity.DnaSynthesizerBlockEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class DnaSynthesizerBlockEntityRenderer extends TileEntityRenderer<DnaSynthesizerBlockEntity> {
    public DnaSynthesizerBlockEntityRenderer(TileEntityRendererDispatcher pContext) {
        super(pContext);
    }

    @Override
    public void render(DnaSynthesizerBlockEntity pBlockEntity, float pPartialTick, MatrixStack pPoseStack, IRenderTypeBuffer pBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        pPoseStack.pushPose();

        Direction facing = pBlockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);

        switch (facing) {
            case EAST:
                // Render the input slots
                renderItem(pBlockEntity.getInputHandler().getStackInSlot(0), pPoseStack, pBufferSource, pBlockEntity, itemRenderer, 0.3f, 0, 0.25f);
                renderItem(pBlockEntity.getInputHandler().getStackInSlot(1), pPoseStack, pBufferSource, pBlockEntity, itemRenderer, -0.3f, 0, 0.25f);
                renderItem(pBlockEntity.getInputHandler().getStackInSlot(2), pPoseStack, pBufferSource, pBlockEntity, itemRenderer, 0, 0, 0.125f);

                // Render the output slot
                renderItem(pBlockEntity.getOutputHandler().getStackInSlot(0), pPoseStack, pBufferSource, pBlockEntity, itemRenderer, 0, 0, -0.25f);
                break;
            case WEST:
                // Render the input slots
                renderItem(pBlockEntity.getInputHandler().getStackInSlot(0), pPoseStack, pBufferSource, pBlockEntity, itemRenderer, -0.3f, 0, -0.25f);
                renderItem(pBlockEntity.getInputHandler().getStackInSlot(1), pPoseStack, pBufferSource, pBlockEntity, itemRenderer, 0.3f, 0, -0.25f);
                renderItem(pBlockEntity.getInputHandler().getStackInSlot(2), pPoseStack, pBufferSource, pBlockEntity, itemRenderer, 0, 0, -0.125f);

                // Render the output slot
                renderItem(pBlockEntity.getOutputHandler().getStackInSlot(0), pPoseStack, pBufferSource, pBlockEntity, itemRenderer, 0, 0, 0.25f);
                break;
            case NORTH:
                // Render the input slots
                renderItem(pBlockEntity.getInputHandler().getStackInSlot(0), pPoseStack, pBufferSource, pBlockEntity, itemRenderer, 0.25f, 0, 0.3f);
                renderItem(pBlockEntity.getInputHandler().getStackInSlot(1), pPoseStack, pBufferSource, pBlockEntity, itemRenderer, 0.25f, 0, -0.3f);
                renderItem(pBlockEntity.getInputHandler().getStackInSlot(2), pPoseStack, pBufferSource, pBlockEntity, itemRenderer, 0.125f, 0, 0);

                // Render the output slot
                renderItem(pBlockEntity.getOutputHandler().getStackInSlot(0), pPoseStack, pBufferSource, pBlockEntity, itemRenderer, -0.25f, 0, 0);
                break;
            case SOUTH:
                // Render the input slots
                renderItem(pBlockEntity.getInputHandler().getStackInSlot(0), pPoseStack, pBufferSource, pBlockEntity, itemRenderer, -0.25f, 0, -0.3f);
                renderItem(pBlockEntity.getInputHandler().getStackInSlot(1), pPoseStack, pBufferSource, pBlockEntity, itemRenderer, -0.25f, 0, 0.3f);
                renderItem(pBlockEntity.getInputHandler().getStackInSlot(2), pPoseStack, pBufferSource, pBlockEntity, itemRenderer, -0.125f, 0, 0);

                // Render the output slot
                renderItem(pBlockEntity.getOutputHandler().getStackInSlot(0), pPoseStack, pBufferSource, pBlockEntity, itemRenderer, 0.25f, 0, 0f);
                break;

        }

        pPoseStack.popPose();
    }

    private void renderItem(ItemStack itemStack, MatrixStack pPoseStack, IRenderTypeBuffer pBufferSource, DnaSynthesizerBlockEntity pBlockEntity, ItemRenderer itemRenderer, float xOffset, float yOffset, float zOffset) {
        pPoseStack.pushPose();
        pPoseStack.translate(0.5 + xOffset, 0.35 + yOffset, 0.5 + zOffset);
        pPoseStack.scale(0.25f, 0.25f, 0.25f);
        pPoseStack.mulPose(Vector3f.YP.rotationDegrees(pBlockEntity.getRenderingRotation()));

        itemRenderer.renderStatic(itemStack, ItemCameraTransforms.TransformType.FIXED, getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource);

        pPoseStack.popPose();
    }

    private int getLightLevel(World level, BlockPos blockPos) {
        int bLight = level.getBrightness(LightType.BLOCK, blockPos);
        int sLight = level.getBrightness(LightType.SKY, blockPos);
        return LightTexture.pack(bLight, sLight);
    }
}
