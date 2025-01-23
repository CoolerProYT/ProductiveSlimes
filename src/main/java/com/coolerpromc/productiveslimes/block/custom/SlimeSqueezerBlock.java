package com.coolerpromc.productiveslimes.block.custom;

import com.coolerpromc.productiveslimes.block.entity.ModBlockEntities;
import com.coolerpromc.productiveslimes.block.entity.SlimeSqueezerBlockEntity;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
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

public class SlimeSqueezerBlock extends Block {
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

    public SlimeSqueezerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState rotate(BlockState state, IWorld level, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new SlimeSqueezerBlockEntity();
    }

    @Override
    public void onRemove(BlockState pState, World pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pState.getBlock() != pNewState.getBlock()) {
            TileEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof SlimeSqueezerBlockEntity) {
                SlimeSqueezerBlockEntity slimeSqueezerBlockEntity = (SlimeSqueezerBlockEntity) blockEntity;
                slimeSqueezerBlockEntity.drops();

                ItemStack stack = new ItemStack(this);

                CompoundNBT tag = stack.getOrCreateTag();
                tag.putInt("energy", slimeSqueezerBlockEntity.getEnergyHandler().getEnergyStored());
                stack.setTag(tag);

                Block.popResource(pLevel, pPos, stack);
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    public ActionResultType use(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockRayTraceResult pHit) {
        if (!pLevel.isClientSide()) {
            TileEntity entity = pLevel.getBlockEntity(pPos);
            if (entity instanceof SlimeSqueezerBlockEntity) {
                SlimeSqueezerBlockEntity slimeSqueezerBlockEntity = (SlimeSqueezerBlockEntity) entity;
                NetworkHooks.openGui(((ServerPlayerEntity) pPlayer), slimeSqueezerBlockEntity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }
        return ActionResultType.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    public void setPlacedBy(World pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        TileEntity be = pLevel.getBlockEntity(pPos);
        if (be instanceof SlimeSqueezerBlockEntity) {
            SlimeSqueezerBlockEntity slimeSqueezerBlockEntity = (SlimeSqueezerBlockEntity) be;
            if (pStack.hasTag() && pStack.getTag().contains("energy")) {
                slimeSqueezerBlockEntity.getEnergyHandler().setEnergy(pStack.getOrCreateTag().getInt("energy"));
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