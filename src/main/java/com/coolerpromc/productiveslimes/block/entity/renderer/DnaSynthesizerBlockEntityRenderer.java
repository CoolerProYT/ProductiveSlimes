package com.coolerpromc.productiveslimes.block.entity.renderer;

import com.coolerpromc.productiveslimes.block.entity.DnaExtractorBlockEntity;
import com.coolerpromc.productiveslimes.block.entity.DnaSynthesizerBlockEntity;
import com.coolerpromc.productiveslimes.item.custom.BucketItem;
import com.coolerpromc.productiveslimes.item.custom.SlimeballItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;

public class DnaSynthesizerBlockEntityRenderer implements BlockEntityRenderer<DnaSynthesizerBlockEntity> {
    public DnaSynthesizerBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {

    }

    @Override
    public void render(DnaSynthesizerBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
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

    private void renderItem(ItemStack itemStack, PoseStack pPoseStack, MultiBufferSource pBufferSource, DnaSynthesizerBlockEntity pBlockEntity, ItemRenderer itemRenderer, float xOffset, float yOffset, float zOffset) {
        pPoseStack.pushPose();
        pPoseStack.translate(0.5 + xOffset, 0.35 + yOffset, 0.5 + zOffset);
        pPoseStack.scale(0.25f, 0.25f, 0.25f);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(pBlockEntity.getRenderingRotation()));

        itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 1);

        pPoseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos blockPos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, blockPos);
        int sLight = level.getBrightness(LightLayer.SKY, blockPos);
        return LightTexture.pack(bLight, sLight);
    }
}
