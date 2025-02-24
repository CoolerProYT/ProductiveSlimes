package com.coolerpromc.productiveslimes.util;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.block.entity.renderer.FluidTankBlockEntityRenderer;
import com.coolerpromc.productiveslimes.datacomponent.ModDataComponents;
import com.coolerpromc.productiveslimes.datacomponent.custom.ImmutableFluidStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

public record FluidTankSpecialRenderer() implements SpecialModelRenderer<ImmutableFluidStack> {
    @Nullable
    @Override
    public ImmutableFluidStack extractArgument(ItemStack stack) {
        return stack.get(ModDataComponents.FLUID_STACK);
    }

    @Override
    public void render(@Nullable ImmutableFluidStack immutableFluidStack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, boolean hasFoilType) {
        poseStack.pushPose();
        BlockState blockState = ModBlocks.FLUID_TANK.get().defaultBlockState();
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(blockState, poseStack, bufferSource, packedLight, packedOverlay, ModelData.EMPTY, RenderType.CUTOUT);
        poseStack.popPose();

        if (immutableFluidStack == null) return;

        FluidStack fluidStack = immutableFluidStack.fluidStack();
        FluidTankBlockEntityRenderer.renderFluid(poseStack, bufferSource, packedLight, packedOverlay, fluidStack);
    }

    public record Unbaked(ResourceLocation texture) implements SpecialModelRenderer.Unbaked{
        public static final MapCodec<Unbaked> MAP_CODEC = ResourceLocation.CODEC.fieldOf("texture").xmap(FluidTankSpecialRenderer.Unbaked::new, FluidTankSpecialRenderer.Unbaked::texture);

        @Nullable
        @Override
        public SpecialModelRenderer<?> bake(EntityModelSet modelSet) {
            return new FluidTankSpecialRenderer();
        }

        @Override
        public MapCodec<? extends SpecialModelRenderer.Unbaked> type() {
            return MAP_CODEC;
        }
    }
}
