package com.coolerpromc.productiveslimes.block.custom;

import com.coolerpromc.productiveslimes.block.entity.DnaExtractorBlockEntity;
import com.coolerpromc.productiveslimes.block.entity.FluidTankBlockEntity;
import com.coolerpromc.productiveslimes.block.entity.ModBlockEntities;
import com.coolerpromc.productiveslimes.datacomponent.ModDataComponents;
import com.coolerpromc.productiveslimes.handler.ImmutableFluidStack;
import com.coolerpromc.productiveslimes.item.custom.BucketItem;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
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
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FluidTankBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

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
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pState.getBlock() != pNewState.getBlock()){
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof FluidTankBlockEntity){
                ((FluidTankBlockEntity) blockEntity).drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide()) {
            bucketUsed(pLevel, pPos, pPlayer);
        }
        return ItemInteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    protected void bucketUsed(Level pLevel, BlockPos pPos, Player pPlayer) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof FluidTankBlockEntity fluidTankBlockEntity) {
            if (pPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof BucketItem bucketItem && bucketItem != Items.BUCKET) {
                if (!fluidTankBlockEntity.getFluidStack().isEmpty()) {
                    if (bucketItem.getFluidStack().getFluidType() == fluidTankBlockEntity.getFluidStack().getFluidType()) {
                        if (fluidTankBlockEntity.getFluidTank().getFluidAmount() + 1000 <= fluidTankBlockEntity.getFluidTank().getCapacity()) {
                            FluidStack fluidToAdd = new FluidStack(bucketItem.getFluidStack().getFluid(), 1000);
                            int filled = fluidTankBlockEntity.getFluidTank().fill(fluidToAdd, IFluidHandler.FluidAction.EXECUTE);
                            if (filled > 0) {
                                pPlayer.getItemInHand(InteractionHand.MAIN_HAND).shrink(1);
                                pPlayer.addItem(new ItemStack(Items.BUCKET, 1));
                            }
                        }
                    }
                } else {
                    FluidStack fluidToAdd = new FluidStack(bucketItem.getFluidStack().getFluid(), 1000);
                    int filled = fluidTankBlockEntity.getFluidTank().fill(fluidToAdd, IFluidHandler.FluidAction.EXECUTE);
                    if (filled > 0) {
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
    protected List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        List<ItemStack> drops = super.getDrops(pState, pParams);
        BlockEntity blockEntity = pParams.getOptionalParameter(LootContextParams.BLOCK_ENTITY);

        if (blockEntity instanceof FluidTankBlockEntity fluidTankBlockEntity) {
            ItemStack stack = new ItemStack(this);
            ImmutableFluidStack immutableFluidStack = new ImmutableFluidStack(fluidTankBlockEntity.getFluidStack().copy());

            stack.set(ModDataComponents.FLUID_STACK.get(), immutableFluidStack);

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


    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltip, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltip, pTooltipFlag);

        if (pStack.getOrDefault(ModDataComponents.FLUID_STACK.get(), FluidStack.EMPTY) != FluidStack.EMPTY) {
            ImmutableFluidStack immutableFluidStack = pStack.get(ModDataComponents.FLUID_STACK.get());
            FluidStack fluidStack = (immutableFluidStack != null) ? immutableFluidStack.fluidStack() : FluidStack.EMPTY;
            pTooltip.add(Component.literal("Fluid Stored: ").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x00FF00))).append(Component.translatable(fluidStack.getDescriptionId()).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFFF)))));
            pTooltip.add(Component.literal("Stored Amount: ").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x00FF00))).append(Component.translatable(fluidStack.getAmount() / 1000 + "B").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFFF)))));
        }
    }
}
