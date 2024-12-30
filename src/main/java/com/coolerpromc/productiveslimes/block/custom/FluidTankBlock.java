package com.coolerpromc.productiveslimes.block.custom;

import com.coolerpromc.productiveslimes.block.entity.FluidTankBlockEntity;
import com.coolerpromc.productiveslimes.block.entity.ModBlockEntities;
import com.coolerpromc.productiveslimes.util.TranslucentHighlightFix;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;
import java.util.List;

public class FluidTankBlock extends BaseEntityBlock implements TranslucentHighlightFix {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public FluidTankBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        return Block.box(2, 0, 2, 14, 16, 14);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            bucketUsed(pLevel, pPos, pPlayer);
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    protected void bucketUsed(Level pLevel, BlockPos pPos, Player pPlayer) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof FluidTankBlockEntity fluidTankBlockEntity) {
            if (pPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof net.minecraft.world.item.BucketItem bucketItem && bucketItem != Items.BUCKET) {
                if (!fluidTankBlockEntity.getFluidStack().isEmpty()) {
                    if (bucketItem.getFluid() == fluidTankBlockEntity.getFluidStack().getFluid()) {
                        if (fluidTankBlockEntity.getFluidTank().getFluidAmount() + 1000 <= fluidTankBlockEntity.getFluidTank().getCapacity()) {
                            FluidStack fluidToAdd = new FluidStack(bucketItem.getFluid(), 1000);
                            int filled = fluidTankBlockEntity.getFluidTank().fill(fluidToAdd, IFluidHandler.FluidAction.EXECUTE);
                            if (filled > 0 && !pPlayer.isCreative()) {
                                pPlayer.getItemInHand(InteractionHand.MAIN_HAND).shrink(1);
                                pPlayer.addItem(new ItemStack(Items.BUCKET, 1));
                            }
                        }
                    }
                } else {
                    FluidStack fluidToAdd = new FluidStack(bucketItem.getFluid(), 1000);
                    int filled = fluidTankBlockEntity.getFluidTank().fill(fluidToAdd, IFluidHandler.FluidAction.EXECUTE);
                    if (filled > 0 && !pPlayer.isCreative()) {
                        pPlayer.getItemInHand(InteractionHand.MAIN_HAND).shrink(1);
                        pPlayer.addItem(new ItemStack(Items.BUCKET, 1));
                    }
                }
            } else if (pPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.BUCKET) {
                if (!fluidTankBlockEntity.getFluidStack().isEmpty()) {
                    FluidStack drainedFluid = fluidTankBlockEntity.getFluidTank().drain(1000, IFluidHandler.FluidAction.SIMULATE);
                    if (drainedFluid.getAmount() == 1000) {
                        pPlayer.addItem(new ItemStack(fluidTankBlockEntity.getFluidStack().getFluid().getBucket()));
                        pPlayer.getItemInHand(InteractionHand.MAIN_HAND).shrink(1);
                        fluidTankBlockEntity.getFluidTank().drain(1000, IFluidHandler.FluidAction.EXECUTE);
                        if (fluidTankBlockEntity.getFluidTank().getFluidAmount() == 0) {
                            fluidTankBlockEntity.setFluidStack(FluidStack.EMPTY);
                        }
                    }
                }
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new FluidTankBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.FLUID_TANK_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FACING);
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return state.setValue(FACING,direction.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pState.getBlock() != pNewState.getBlock()){
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof FluidTankBlockEntity fluidTankBlockEntity){
                fluidTankBlockEntity.drops();

                ItemStack stack = new ItemStack(this);

                CompoundTag tag = stack.getOrCreateTag();

                CompoundTag fluidTag = new CompoundTag();
                fluidTankBlockEntity.getFluidTank().getFluid().writeToNBT(fluidTag);
                tag.put("fluid", fluidTag);

                stack.setTag(tag);

                Block.popResource(pLevel, pPos, stack);
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        BlockEntity be = pLevel.getBlockEntity(pPos);
        if (be instanceof FluidTankBlockEntity fluidTankBlockEntity) {
            CompoundTag tag = pStack.getTag();

            if (tag != null && tag.contains("fluid")){
                CompoundTag fluidTag = tag.getCompound("fluid");
                FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(fluidTag);
                fluidTankBlockEntity.setFluidStack(fluidStack);
            }
        }

        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);

        CompoundTag tag = pStack.getTag();

        if (tag != null && tag.contains("fluid")){
            CompoundTag fluidTag = tag.getCompound("fluid");
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(fluidTag);

            if (fluidStack != FluidStack.EMPTY) {
                pTooltip.add(new TranslatableComponent("tooltip.productiveslimes.fluid_stored").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x00FF00))).append(new TranslatableComponent(fluidStack.getDisplayName().getString()).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFFF)))));
                pTooltip.add(new TranslatableComponent("tooltip.productiveslimes.stored_amount").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x00FF00))).append(new TranslatableComponent("tooltip.productiveslimes.fluid_amount", fluidStack.getAmount() / 1000).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFFF)))));
            }
        }
    }
}
