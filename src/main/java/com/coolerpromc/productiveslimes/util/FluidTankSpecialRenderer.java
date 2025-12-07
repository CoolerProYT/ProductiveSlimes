package com.coolerpromc.productiveslimes.util;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.block.entity.renderer.FluidTankBlockEntityRenderer;
import com.coolerpromc.productiveslimes.datacomponent.ModDataComponents;
import com.coolerpromc.productiveslimes.handler.ImmutableFluidStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.function.Consumer;

public record FluidTankSpecialRenderer() implements SpecialModelRenderer<ImmutableFluidStack> {
    @Nullable
    @Override
    public ImmutableFluidStack extractArgument(ItemStack stack) {
        return stack.get(ModDataComponents.FLUID_STACK);
    }

    @Override
    public void submit(@Nullable ImmutableFluidStack patterns, ItemDisplayContext displayContext, PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, int packedOverlay, boolean hasFoilType, int i) {
        poseStack.pushPose();
        BlockState blockState = ModBlocks.FLUID_TANK.get().defaultBlockState();
        nodeCollector.submitBlockModel(poseStack, RenderTypes.cutoutMovingBlock(), Minecraft.getInstance().getBlockRenderer().getBlockModel(blockState), -1, -1, -1, packedLight, packedOverlay, 0);
        poseStack.popPose();

        if (patterns instanceof ImmutableFluidStack immutableFluidStack){
            FluidStack fluidStack = immutableFluidStack.fluidStack();
            FluidTankBlockEntityRenderer.renderFluid(poseStack, nodeCollector, packedLight, packedOverlay, fluidStack);
        }
    }

    @Override
    public void getExtents(Consumer<Vector3fc> extents) {
        extents.accept(new Vector3f(0.0f, 0.0f, 0.0f));
        extents.accept(new Vector3f(1.0f, 1.0f, 1.0f));
    }

    public record Unbaked(Identifier texture) implements SpecialModelRenderer.Unbaked{
        public static final MapCodec<Unbaked> MAP_CODEC = Identifier.CODEC.fieldOf("texture").xmap(FluidTankSpecialRenderer.Unbaked::new, FluidTankSpecialRenderer.Unbaked::texture);

        @Override
        public @Nullable SpecialModelRenderer<?> bake(BakingContext p_433472_) {
            return new FluidTankSpecialRenderer();
        }

        @Override
        public MapCodec<? extends SpecialModelRenderer.Unbaked> type() {
            return MAP_CODEC;
        }
    }
}