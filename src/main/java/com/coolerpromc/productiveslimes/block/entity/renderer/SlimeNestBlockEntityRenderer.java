package com.coolerpromc.productiveslimes.block.entity.renderer;

import com.coolerpromc.productiveslimes.block.entity.SlimeNestBlockEntity;
import com.coolerpromc.productiveslimes.block.entity.renderstate.SlimeNestBlockEntityRenderState;
import com.coolerpromc.productiveslimes.datacomponent.ModDataComponents;
import com.coolerpromc.productiveslimes.entity.SlimeModel;
import com.coolerpromc.productiveslimes.entity.renderer.BaseSlimeRenderer;
import com.coolerpromc.productiveslimes.handler.SlimeData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.state.SlimeRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class SlimeNestBlockEntityRenderer implements BlockEntityRenderer<SlimeNestBlockEntity, SlimeNestBlockEntityRenderState> {
    public int tick;
    private final ItemModelResolver itemModelResolver;
    private final EntityModelSet entityModelSet;
    private final SlimeModel slimeModel;
    private final SlimeModel slimeModelOuter;

    public SlimeNestBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
        this.entityModelSet = context.entityModelSet();
        this.slimeModel = new SlimeModel(entityModelSet.bakeLayer(ModelLayers.SLIME), -1);
        this.slimeModelOuter = new SlimeModel(entityModelSet.bakeLayer(ModelLayers.SLIME_OUTER), -1);
    }

    @Override
    public void extractRenderState(SlimeNestBlockEntity blockEntity, SlimeNestBlockEntityRenderState renderState, float p_446851_, Vec3 p_445788_, @Nullable ModelFeatureRenderer.CrumblingOverlay p_446944_) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, p_446851_, p_445788_, p_446944_);
        renderState.blockEntity = blockEntity;
        SlimeData slimeData = blockEntity.getSlimeHandler().getStackInSlot(0).get(ModDataComponents.SLIME_DATA.get());
        if (slimeData != null){
            renderState.slimeColor = slimeData.color();
        } else {
            renderState.slimeColor = -1; // default
        }

        ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
        this.itemModelResolver.updateForTopItem(itemStackRenderState, blockEntity.getSlime(), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 0);
        renderState.itemStackRenderState = itemStackRenderState;
    }

    @Override
    public void submit(SlimeNestBlockEntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState p_451022_) {
        SlimeNestBlockEntity blockEntity = renderState.blockEntity;
        if (blockEntity.getSlime() == null) return;
        if (blockEntity.getSlime().isEmpty()) return;
        if (blockEntity.getSlime().get(ModDataComponents.SLIME_DATA.get()) == null) return;
        Level level = blockEntity.getLevel();

        double centerX = blockEntity.getBlockPos().getX() + 0.5;
        double centerY = blockEntity.getBlockPos().getY() + 0.5;
        double centerZ = blockEntity.getBlockPos().getZ() + 0.5;

        if (level == null || !level.isClientSide()) return;
        tick = blockEntity.getData().get(4);

        float squishAmount = 1.0F + 0.1F * (float) Math.sin(tick * 0.1F);
        float bounce = 0.1F * (float) Math.sin(tick * 0.1F);
        float scaleX = squishAmount;
        float scaleY = 1.0F / squishAmount;
        float scaleZ = squishAmount;
        float renderX = 0.0F;
        float renderY = 0.0F + bounce;
        float renderZ = 0.0F;

        if (tick % 20 == 0) {
            for (int i = 0; i < 5; i++) {
                double offsetX = level.random.nextDouble() * 0.4 - 0.2;
                double offsetY = level.random.nextDouble() * 0.3;
                double offsetZ = level.random.nextDouble() * 0.4 - 0.2;

                level.addParticle(
                        new ItemParticleOption(ParticleTypes.ITEM,
                                new ItemStack(blockEntity.getSlime().get(ModDataComponents.SLIME_DATA.get()).growthItem().getItem())),
                        centerX + offsetX,
                        centerY + offsetY,
                        centerZ + offsetZ,
                        0.0, 0.1, 0.0
                );
            }
        }
        Direction direction = blockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        float degree = direction.toYRot();
        poseStack.pushPose();
        poseStack.translate(centerX - blockEntity.getBlockPos().getX() + renderX, 1.7, centerZ - blockEntity.getBlockPos().getZ() + renderZ);
        poseStack.scale(scaleX, scaleY, scaleZ);
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(degree));
        nodeCollector.order(0).submitModel(this.slimeModel, new SlimeRenderState(), poseStack, RenderType.entityTranslucent(BaseSlimeRenderer.BASE_TEXTURE), renderState.lightCoords, OverlayTexture.NO_OVERLAY, renderState.slimeColor, null, 0, null);
        nodeCollector.order(1).submitModel(this.slimeModelOuter, new SlimeRenderState(), poseStack, RenderType.entityTranslucent(BaseSlimeRenderer.BASE_TEXTURE), renderState.lightCoords, OverlayTexture.NO_OVERLAY, renderState.slimeColor, null, 0, null);
        poseStack.popPose();
    }

    @Override
    public SlimeNestBlockEntityRenderState createRenderState() {
        return new SlimeNestBlockEntityRenderState();
    }
}