package com.coolerpromc.productiveslimes.block.entity.renderer;

import com.coolerpromc.productiveslimes.block.entity.SolidingStationBlockEntity;
import com.coolerpromc.productiveslimes.item.custom.BucketItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public class SolidingStationBlockEntityRenderer extends TileEntityRenderer<SolidingStationBlockEntity> {
    public SolidingStationBlockEntityRenderer(TileEntityRendererDispatcher pContext) {
        super(pContext);

    }

    @Override
    public void render(SolidingStationBlockEntity pBlockEntity, float pPartialTick, MatrixStack pPoseStack, IRenderTypeBuffer pBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemStack itemStack = pBlockEntity.getRenderStack();
        FluidStack fluidStack;
        int color = 0xFFFFFFFF;

        if (itemStack.getItem() instanceof BucketItem) {
            BucketItem bucketItem = (BucketItem) itemStack.getItem();
            fluidStack = bucketItem.getFluidStack();
            color = bucketItem.getColor();
        }
        else {
            fluidStack = FluidStack.EMPTY;
        }

        if (fluidStack.isEmpty()) return;

        World level = pBlockEntity.getLevel();
        if (level == null) return;

        BlockPos pos = pBlockEntity.getBlockPos();

        ResourceLocation stillTexture = new ResourceLocation("block/water_still");

        FluidState state = fluidStack.getFluid().defaultFluidState();

        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(PlayerContainer.BLOCK_ATLAS).apply(stillTexture);
        int tintColor = color;

        float height = 0.8f;

        IVertexBuilder builder = pBufferSource.getBuffer(RenderTypeLookup.getRenderLayer(state));

        drawQuad(builder, pPoseStack, 0.2f, height, 0.2f, 0.8f, height, 0.8f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, tintColor);

        drawQuad(builder, pPoseStack, 0.2f, 0, 0.2f, 0.8f, height, 0.2f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, tintColor);
        pPoseStack.pushPose();
        pPoseStack.mulPose(Vector3f.YP.rotationDegrees(180));
        pPoseStack.translate(-1f, 0, -1.5f);
        drawQuad(builder, pPoseStack, 0.2f, 0, 0.8f, 0.8f, height, 0.8f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, tintColor);
        pPoseStack.popPose();
        pPoseStack.pushPose();
        pPoseStack.mulPose(Vector3f.YP.rotationDegrees(90));
        pPoseStack.translate(-1f, 0, 0);
        drawQuad(builder, pPoseStack, 0.2f, 0, 0.2f, 0.8f, height, 0.2f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, tintColor);
        pPoseStack.popPose();
        pPoseStack.pushPose();
        pPoseStack.mulPose(Vector3f.YN.rotationDegrees(90));
        pPoseStack.translate(0, 0, -1f);
        drawQuad(builder, pPoseStack, 0.2f, 0, 0.2f, 0.8f, height, 0.2f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, tintColor);
        pPoseStack.popPose();
    }

    private static void drawVertex(IVertexBuilder builder, MatrixStack poseStack, float x, float y, float z, float u, float v, int packedLight, int color) {
        int alpha = (color >> 24) & 0xFF;
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        builder.vertex(poseStack.last().pose(), x, y, z)
                .color(red, green, blue, alpha)
                .uv(u, v)
                .uv2(packedLight)
                .normal(1, 0, 0);
    }
    private static void drawQuad(IVertexBuilder builder, MatrixStack poseStack, float x0, float y0, float z0, float x1, float y1, float z1, float u0, float v0, float u1, float v1, int packedLight, int color) {
        drawVertex(builder, poseStack, x0, y0, z0, u0, v0, packedLight, color);
        drawVertex(builder, poseStack, x0, y1, z1, u0, v1, packedLight, color);
        drawVertex(builder, poseStack, x1, y1, z1, u1, v1, packedLight, color);
        drawVertex(builder, poseStack, x1, y0, z0, u1, v0, packedLight, color);
    }
}
