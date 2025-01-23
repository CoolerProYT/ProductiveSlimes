package com.coolerpromc.productiveslimes.block.custom;

import com.coolerpromc.productiveslimes.block.entity.FluidTankBlockEntity;
import com.coolerpromc.productiveslimes.block.entity.ModBlockEntities;
import com.coolerpromc.productiveslimes.util.TranslucentHighlightFix;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;
import java.util.List;

public class FluidTankBlock extends Block implements TranslucentHighlightFix {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public FluidTankBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
        Direction direction = pState.getValue(FACING);
        return Block.box(2, 0, 2, 14, 16, 14);
    }

    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResultType use(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockRayTraceResult pHit) {
        if (!pLevel.isClientSide()) {
            bucketUsed(pLevel, pPos, pPlayer);
        }
        return ActionResultType.sidedSuccess(pLevel.isClientSide());
    }

    protected void bucketUsed(World pLevel, BlockPos pPos, PlayerEntity pPlayer) {
        TileEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof FluidTankBlockEntity) {
            FluidTankBlockEntity fluidTankBlockEntity = (FluidTankBlockEntity) blockEntity;
            BucketItem bucketItem = null;
            try{
                bucketItem = (BucketItem) pPlayer.getItemInHand(Hand.MAIN_HAND).getItem();
            }
            catch (ClassCastException ignored){

            }
            if (pPlayer.getItemInHand(Hand.MAIN_HAND).getItem() instanceof BucketItem && bucketItem != Items.BUCKET) {
                if (!fluidTankBlockEntity.getFluidStack().isEmpty()) {
                    if (bucketItem.getFluid() == fluidTankBlockEntity.getFluidStack().getFluid()) {
                        if (fluidTankBlockEntity.getFluidTank().getFluidAmount() + 1000 <= fluidTankBlockEntity.getFluidTank().getCapacity()) {
                            FluidStack fluidToAdd = new FluidStack(bucketItem.getFluid(), 1000);
                            int filled = fluidTankBlockEntity.getFluidTank().fill(fluidToAdd, IFluidHandler.FluidAction.EXECUTE);
                            if (filled > 0 && !pPlayer.isCreative()) {
                                pPlayer.getItemInHand(Hand.MAIN_HAND).shrink(1);
                                pPlayer.addItem(new ItemStack(Items.BUCKET, 1));
                            }
                        }
                    }
                } else {
                    FluidStack fluidToAdd = new FluidStack(bucketItem.getFluid(), 1000);
                    int filled = fluidTankBlockEntity.getFluidTank().fill(fluidToAdd, IFluidHandler.FluidAction.EXECUTE);
                    if (filled > 0 && !pPlayer.isCreative()) {
                        pPlayer.getItemInHand(Hand.MAIN_HAND).shrink(1);
                        pPlayer.addItem(new ItemStack(Items.BUCKET, 1));
                    }
                }
            } else if (pPlayer.getItemInHand(Hand.MAIN_HAND).getItem() == Items.BUCKET) {
                if (!fluidTankBlockEntity.getFluidStack().isEmpty()) {
                    FluidStack drainedFluid = fluidTankBlockEntity.getFluidTank().drain(1000, IFluidHandler.FluidAction.SIMULATE);
                    if (drainedFluid.getAmount() == 1000) {
                        pPlayer.addItem(new ItemStack(fluidTankBlockEntity.getFluidStack().getFluid().getBucket()));
                        pPlayer.getItemInHand(Hand.MAIN_HAND).shrink(1);
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
    public TileEntity createTileEntity(BlockState pState, IBlockReader world) {
        return new FluidTankBlockEntity();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FACING);
    }

    @Override
    public BlockState rotate(BlockState state, IWorld level, BlockPos pos, Rotation direction) {
        return state.setValue(FACING,direction.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    public void onRemove(BlockState pState, World pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pState.getBlock() != pNewState.getBlock()){
            TileEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof FluidTankBlockEntity){
                FluidTankBlockEntity fluidTankBlockEntity = (FluidTankBlockEntity) blockEntity;
                fluidTankBlockEntity.drops();

                ItemStack stack = new ItemStack(this);

                CompoundNBT tag = stack.getOrCreateTag();

                CompoundNBT fluidTag = new CompoundNBT();
                fluidTankBlockEntity.getFluidTank().getFluid().writeToNBT(fluidTag);
                tag.put("fluid", fluidTag);

                stack.setTag(tag);

                Block.popResource(pLevel, pPos, stack);
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    public void setPlacedBy(World pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        TileEntity be = pLevel.getBlockEntity(pPos);
        if (be instanceof FluidTankBlockEntity) {
            FluidTankBlockEntity fluidTankBlockEntity = (FluidTankBlockEntity) be;
            CompoundNBT tag = pStack.getTag();

            if (tag != null && tag.contains("fluid")){
                CompoundNBT fluidTag = tag.getCompound("fluid");
                FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(fluidTag);
                fluidTankBlockEntity.setFluidStack(fluidStack);
            }
        }

        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable IBlockReader pLevel, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);

        CompoundNBT tag = pStack.getTag();

        if (tag != null && tag.contains("fluid")){
            CompoundNBT fluidTag = tag.getCompound("fluid");
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(fluidTag);

            if (fluidStack != FluidStack.EMPTY) {
                pTooltip.add(new TranslationTextComponent("tooltip.productiveslimes.fluid_stored").setStyle(Style.EMPTY.withColor(Color.fromRgb(0x00FF00))).append(new TranslationTextComponent(fluidStack.getDisplayName().getString()).setStyle(Style.EMPTY.withColor(Color.fromRgb(0xFFFFF)))));
                pTooltip.add(new TranslationTextComponent("tooltip.productiveslimes.stored_amount").setStyle(Style.EMPTY.withColor(Color.fromRgb(0x00FF00))).append(new TranslationTextComponent("tooltip.productiveslimes.fluid_amount", fluidStack.getAmount() / 1000).setStyle(Style.EMPTY.withColor(Color.fromRgb(0xFFFFF)))));
            }
        }
    }
}
