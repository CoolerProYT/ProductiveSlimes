package com.coolerpromc.productiveslimes.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(WorldRenderer.class)
public interface LevelRendererAccess {
    @Invoker
    void callRenderHitOutline(
            MatrixStack matrixStackIn, IVertexBuilder bufferIn, Entity entityIn, double xIn, double yIn, double zIn,
            BlockPos blockPosIn, BlockState blockStateIn
    );
}