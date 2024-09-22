package com.coolerpromc.productiveslimes.block.entity.renderer;

import com.coolerpromc.productiveslimes.block.entity.SolidingStationBlockEntity;
import com.coolerpromc.productiveslimes.fluid.ModFluids;
import com.coolerpromc.productiveslimes.item.custom.BucketItem;
import com.coolerpromc.productiveslimes.item.custom.SlimeballItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.common.util.TransformationHelper;
import net.neoforged.neoforge.fluids.CauldronFluidContent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.Optional;

public class SolidingStationBlockEntityRenderer implements BlockEntityRenderer<SolidingStationBlockEntity> {
    public SolidingStationBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {

    }

    @Override
    public void render(SolidingStationBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemStack itemStack = pBlockEntity.getRenderStack();
        FluidStack fluidStack;
        int color = 0xFFFFFFFF;

        if (itemStack.getItem() instanceof BucketItem bucketItem) {
            fluidStack = bucketItem.getFluidStack();
            color = bucketItem.getColor();
        }
        else {
            fluidStack = FluidStack.EMPTY;
        }

        if (fluidStack.isEmpty()) return;

        Level level = pBlockEntity.getLevel();
        if (level == null) return;

        BlockPos pos = pBlockEntity.getBlockPos();

        IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
        ResourceLocation stillTexture = fluidTypeExtensions.getStillTexture(fluidStack);
        if (stillTexture == null) return;

        FluidState state = fluidStack.getFluid().defaultFluidState();

        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);
        int tintColor = color;

        float height = 0.8f;

        VertexConsumer builder = pBufferSource.getBuffer(ItemBlockRenderTypes.getRenderLayer(state));

        drawQuad(builder, pPoseStack, 0.2f, height, 0.2f, 0.8f, height, 0.8f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, tintColor);

        drawQuad(builder, pPoseStack, 0.2f, 0, 0.2f, 0.8f, height, 0.2f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, tintColor);
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YP.rotationDegrees(180));
        pPoseStack.translate(-1f, 0, -1.5f);
        drawQuad(builder, pPoseStack, 0.2f, 0, 0.8f, 0.8f, height, 0.8f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, tintColor);
        pPoseStack.popPose();
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
        pPoseStack.translate(-1f, 0, 0);
        drawQuad(builder, pPoseStack, 0.2f, 0, 0.2f, 0.8f, height, 0.2f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, tintColor);
        pPoseStack.popPose();
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YN.rotationDegrees(90));
        pPoseStack.translate(0, 0, -1f);
        drawQuad(builder, pPoseStack, 0.2f, 0, 0.2f, 0.8f, height, 0.2f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, tintColor);
        pPoseStack.popPose();
    }

    private static void drawVertex(VertexConsumer builder, PoseStack poseStack, float x, float y, float z, float u, float v, int packedLight, int color) {
        builder.addVertex(poseStack.last().pose(), x, y, z)
                .setColor(color)
                .setUv(u, v)
                .setLight(packedLight)
                .setNormal(1, 0, 0);
    }
    private static void drawQuad(VertexConsumer builder, PoseStack poseStack, float x0, float y0, float z0, float x1, float y1, float z1, float u0, float v0, float u1, float v1, int packedLight, int color) {
        drawVertex(builder, poseStack, x0, y0, z0, u0, v0, packedLight, color);
        drawVertex(builder, poseStack, x0, y1, z1, u0, v1, packedLight, color);
        drawVertex(builder, poseStack, x1, y1, z1, u1, v1, packedLight, color);
        drawVertex(builder, poseStack, x1, y0, z0, u1, v0, packedLight, color);
    }
}
