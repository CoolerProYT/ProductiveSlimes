package com.coolerpromc.productiveslimes.block.entity.renderer;

import com.coolerpromc.productiveslimes.block.entity.DnaExtractorBlockEntity;
import com.coolerpromc.productiveslimes.item.custom.SlimeballItem;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class DnaExtractorBlockEntityRenderer extends TileEntityRenderer<DnaExtractorBlockEntity> {
    public DnaExtractorBlockEntityRenderer(TileEntityRendererDispatcher pContext) {
        super(pContext);
    }

    @Override
    public void render(DnaExtractorBlockEntity pBlockEntity, float pPartialTick, MatrixStack pPoseStack, IRenderTypeBuffer pBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack itemStack = pBlockEntity.getRenderStack();

        pPoseStack.pushPose();
        if (itemStack.getItem() instanceof SlimeballItem) {
            pPoseStack.translate(0.5, 0.4, 0.5);
        } else {
            pPoseStack.translate(0.5, 0.5, 0.5);
        }
        pPoseStack.scale(0.35f, 0.35f, 0.35f);
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
