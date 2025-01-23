package com.coolerpromc.productiveslimes.block.entity.renderer;

import com.coolerpromc.productiveslimes.block.entity.SlimeNestBlockEntity;
import com.coolerpromc.productiveslimes.handler.SlimeData;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class SlimeNestBlockEntityRenderer extends TileEntityRenderer<SlimeNestBlockEntity> {
    public int tick;

    public SlimeNestBlockEntityRenderer(TileEntityRendererDispatcher context) {
        super(context);
    }

    @Override
    public void render(SlimeNestBlockEntity blockEntity, float partialTick, MatrixStack poseStack, IRenderTypeBuffer bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.getSlime() == null) return;
        if (blockEntity.getSlime().isEmpty()) return;
        if (!blockEntity.getSlime().getTag().contains("slime_data")) return;

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack slime = blockEntity.getSlime();
        World level = blockEntity.getLevel();
        // Get the center of the block
        double centerX = blockEntity.getBlockPos().getX() + 0.5;
        double centerY = blockEntity.getBlockPos().getY() + 0.5;
        double centerZ = blockEntity.getBlockPos().getZ() + 0.5;
        // Ensure level is not null and is client-side
        if (level == null || !level.isClientSide) return;
        tick = blockEntity.getData().get(4);
        // Calculate squishAmount based on tickCount
        float squishAmount = 1.0F + 0.1F * (float) Math.sin(tick * 0.1F);
        // Vertical bounce sync with squish
        float bounce = 0.1F * (float) Math.sin(tick * 0.1F);
        // Adjust the scale and position based on squish
        float scaleX = squishAmount;
        float scaleY = 1.0F / squishAmount; // Opposite squish for a "flattening" effect
        float scaleZ = squishAmount;
        // Center the rendered item
        float renderX = 0.0F; // Offset in the pose stack to remain centered
        float renderY = 0.0F + bounce; // Apply vertical bounce
        float renderZ = 0.0F;
        // Spawn particles during the squish
        if (tick % 20 == 0) {
            for (int i = 0; i < 5; i++) { // Spawn 5 particles
                double offsetX = level.random.nextDouble() * 0.4 - 0.2; // Random offset around the X-axis
                double offsetY = level.random.nextDouble() * 0.3;       // Random offset upward
                double offsetZ = level.random.nextDouble() * 0.4 - 0.2; // Random offset around the Z-axis
                // Add the particle with a randomized position surrounding the slime
                level.addParticle(
                        new ItemParticleData(ParticleTypes.ITEM, new ItemStack(SlimeData.fromTag(slime.getTag().getCompound("slime_data")).growthItem().getItem())),
                        centerX + offsetX,
                        centerY + offsetY,
                        centerZ + offsetZ,
                        0.0, 0.1, 0.0
                );
            }
        }
        Direction direction = blockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        int degree = 0;
        switch (direction) {
            case NORTH:
                degree = 0;
                break;
            case EAST:
                degree = 270;
                break;
            case SOUTH:
                degree = 180;
                break;
            case WEST:
                degree = 90;
                break;
        }
        // Render the squishing slime at the center of the block
        poseStack.pushPose();
        poseStack.translate(centerX - blockEntity.getBlockPos().getX() + renderX, centerY - blockEntity.getBlockPos().getY() + renderY - 0.05f, centerZ - blockEntity.getBlockPos().getZ() + renderZ);
        poseStack.scale(scaleX, scaleY, scaleZ); // Apply squish scaling
        poseStack.mulPose(Vector3f.YP.rotationDegrees(degree));
        itemRenderer.renderStatic(slime, ItemCameraTransforms.TransformType.FIXED, packedLight, packedOverlay, poseStack, bufferSource);
        poseStack.popPose();
    }

    private int getLightLevel(World level, BlockPos pos) {
        int bLight = level.getBrightness(LightType.BLOCK, pos);
        int sLight = level.getBrightness(LightType.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}