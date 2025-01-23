package com.coolerpromc.productiveslimes.block.custom;

import com.coolerpromc.productiveslimes.block.entity.DnaSynthesizerBlockEntity;
import com.coolerpromc.productiveslimes.block.entity.ModBlockEntities;
import com.coolerpromc.productiveslimes.util.TranslucentHighlightFix;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
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
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

public class DnaSynthesizerBlock extends Block implements TranslucentHighlightFix {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public DnaSynthesizerBlock(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public VoxelShape getShape(BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
        Direction direction = pState.getValue(FACING);
        return Block.box(0, 0, 0, 16, 16, 16);
    }

    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, World pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pState.getBlock() != pNewState.getBlock()){
            TileEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof DnaSynthesizerBlockEntity){
                DnaSynthesizerBlockEntity dnaSynthesizerBlockEntity = (DnaSynthesizerBlockEntity) blockEntity;
                dnaSynthesizerBlockEntity.drops();
                ItemStack stack = new ItemStack(this);

                CompoundNBT energyTag = stack.getOrCreateTag();
                energyTag.putInt("energy", dnaSynthesizerBlockEntity.getEnergyHandler().getEnergyStored());
                stack.setTag(energyTag);

                Block.popResource(pLevel, pPos, stack);
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    public ActionResultType use(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockRayTraceResult pHit) {
        if (!pLevel.isClientSide()) {
            TileEntity entity = pLevel.getBlockEntity(pPos);
            if (entity instanceof DnaSynthesizerBlockEntity) {
                DnaSynthesizerBlockEntity dnaSynthesizerBlockEntity = (DnaSynthesizerBlockEntity) entity;
                NetworkHooks.openGui(((ServerPlayerEntity)pPlayer), dnaSynthesizerBlockEntity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return ActionResultType.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState pState, IBlockReader world) {
        return new DnaSynthesizerBlockEntity();
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
    public void setPlacedBy(World pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        TileEntity be = pLevel.getBlockEntity(pPos);
        if (be instanceof DnaSynthesizerBlockEntity) {
            DnaSynthesizerBlockEntity dnaSynthesizerBlockEntity = (DnaSynthesizerBlockEntity) be;
            if (pStack.hasTag() && pStack.getTag().contains("energy")) {
                dnaSynthesizerBlockEntity.getEnergyHandler().setEnergy(pStack.getOrCreateTag().getInt("energy"));
            }
        }

        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable IBlockReader pLevel, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);

        if (pStack.hasTag() && pStack.getTag().getInt("energy") != 0) {
            int energy = pStack.getTag().getInt("energy");
            pTooltip.add(new TranslationTextComponent("tooltip.productiveslimes.energy_stored")
                    .setStyle(Style.EMPTY.withColor(Color.fromRgb(0x00FF00)))
                    .append(new TranslationTextComponent("tooltip.productiveslimes.energy_amount", energy)
                            .setStyle(Style.EMPTY.withColor(Color.fromRgb(0xFFFFF)))));
        }
    }
}
