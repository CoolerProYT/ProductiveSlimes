package com.coolerpromc.productiveslimes.block.custom;

import com.coolerpromc.productiveslimes.block.entity.FluidTankBlockEntity;
import com.coolerpromc.productiveslimes.block.entity.ModBlockEntities;
import com.coolerpromc.productiveslimes.datacomponent.ModDataComponents;
import com.coolerpromc.productiveslimes.handler.ImmutableFluidStack;
import com.coolerpromc.productiveslimes.item.custom.BucketItem;
import com.coolerpromc.productiveslimes.util.TranslucentHighlightFix;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FluidTankBlock extends BaseEntityBlock implements TranslucentHighlightFix {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public FluidTankBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(FluidTankBlock::new);
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        return Block.box(2, 0, 2, 14, 16, 14);
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide()) {
            bucketUsed(pLevel, pPos, pPlayer);
        }
        return InteractionResult.SUCCESS;
    }


    protected void bucketUsed(Level pLevel, BlockPos pPos, Player pPlayer) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof FluidTankBlockEntity fluidTankBlockEntity) {
            if (pPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof BucketItem bucketItem && bucketItem != Items.BUCKET) {
                if (!fluidTankBlockEntity.getFluidStack().isEmpty()) {
                    if (bucketItem.getFluidStack().getFluidType() == fluidTankBlockEntity.getFluidStack().getFluidType()) {
                        if (fluidTankBlockEntity.getFluidTank().getAmountAsInt(0) + 1000 <= fluidTankBlockEntity.getFluidTank().getCapacityAsInt(0, fluidTankBlockEntity.getFluidTank().getResource(0))) {
                            FluidStack fluidToAdd = new FluidStack(bucketItem.getFluidStack().getFluid(), 1000);
                            try(Transaction tx = Transaction.open(null)){
                                int filled = fluidTankBlockEntity.getFluidTank().insert(0, FluidResource.of(fluidToAdd), fluidToAdd.getAmount(), tx);
                                if (filled > 0) {
                                    tx.commit();
                                    if (!pPlayer.isCreative()){
                                        pPlayer.getItemInHand(InteractionHand.MAIN_HAND).shrink(1);
                                        pPlayer.addItem(new ItemStack(Items.BUCKET, 1));
                                    }
                                }
                            }
                        }
                    }
                } else {
                    FluidStack fluidToAdd = new FluidStack(bucketItem.getFluidStack().getFluid(), 1000);
                    try(Transaction tx = Transaction.open(null)){
                        int filled = fluidTankBlockEntity.getFluidTank().insert(0, FluidResource.of(fluidToAdd), fluidToAdd.getAmount(), tx);
                        if (filled > 0) {
                            tx.commit();
                            if (!pPlayer.isCreative()){
                                pPlayer.getItemInHand(InteractionHand.MAIN_HAND).shrink(1);
                                pPlayer.addItem(new ItemStack(Items.BUCKET, 1));
                            }
                        }
                    }
                }
            } else if (pPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.BUCKET) {
                if (!fluidTankBlockEntity.getFluidStack().isEmpty()) {
                    int drainedFluid;
                    try(Transaction tx = Transaction.open(null)){
                        drainedFluid = fluidTankBlockEntity.getFluidTank().extract(0, fluidTankBlockEntity.getFluidTank().getResource(0), 1000, tx);
                    }

                    if (drainedFluid == 1000) {
                        pPlayer.addItem(new ItemStack(fluidTankBlockEntity.getFluidStack().getFluid().getBucket()));
                        pPlayer.getItemInHand(InteractionHand.MAIN_HAND).shrink(1);
                        try(Transaction tx2 = Transaction.open(null)){
                            int extracted = fluidTankBlockEntity.getFluidTank().extract(0, fluidTankBlockEntity.getFluidTank().getResource(0), 1000, tx2);
                            if (extracted == 1000){
                                tx2.commit();
                                if (fluidTankBlockEntity.getFluidTank().getAmountAsInt(0) == 0) {
                                    fluidTankBlockEntity.setFluidStack(FluidStack.EMPTY);
                                }
                            }
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
    protected List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        List<ItemStack> drops = super.getDrops(pState, pParams);
        BlockEntity blockEntity = pParams.getOptionalParameter(LootContextParams.BLOCK_ENTITY);

        if (blockEntity instanceof FluidTankBlockEntity fluidTankBlockEntity) {
            ItemStack stack = new ItemStack(this);
            ImmutableFluidStack immutableFluidStack = new ImmutableFluidStack(fluidTankBlockEntity.getFluidStack().copy());

            if (immutableFluidStack.fluidStack() != FluidStack.EMPTY) {
                stack.set(ModDataComponents.FLUID_STACK.get(), immutableFluidStack);
            }

            drops.clear();
            drops.add(stack);
        }

        return drops;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        BlockEntity be = pLevel.getBlockEntity(pPos);
        if (be instanceof FluidTankBlockEntity fluidTankBlockEntity) {
            ImmutableFluidStack immutableFluidStack = pStack.get(ModDataComponents.FLUID_STACK.get());

            FluidStack fluidStack = (immutableFluidStack != null) ? immutableFluidStack.fluidStack() : FluidStack.EMPTY;

            fluidTankBlockEntity.setFluidStack(fluidStack);
        }

        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
    }
}