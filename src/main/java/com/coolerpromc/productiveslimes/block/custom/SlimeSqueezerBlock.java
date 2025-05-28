package com.coolerpromc.productiveslimes.block.custom;

import com.coolerpromc.productiveslimes.block.entity.ModBlockEntities;
import com.coolerpromc.productiveslimes.block.entity.SlimeSqueezerBlockEntity;
import com.coolerpromc.productiveslimes.datacomponent.ModDataComponents;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
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
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class SlimeSqueezerBlock extends BaseEntityBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public SlimeSqueezerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(SlimeSqueezerBlock::new);
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        // Set the facing direction based on the player's facing direction
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }
    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return state.setValue(FACING,direction.rotate(state.getValue(FACING)));
    }
    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SlimeSqueezerBlockEntity(pos, state);
    }
    @Override
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pState.getBlock() != pNewState.getBlock()){
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof SlimeSqueezerBlockEntity slimeSqueezerBlockEntity){
                slimeSqueezerBlockEntity.drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }
    @Override
    protected List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        List<ItemStack> drops = super.getDrops(pState, pParams);
        BlockEntity blockEntity = pParams.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof SlimeSqueezerBlockEntity slimeSqueezerBlockEntity) {
            ItemStack stack = new ItemStack(this);
            stack.set(ModDataComponents.ENERGY.get(), slimeSqueezerBlockEntity.getEnergyHandler().getEnergyStored());
            drops.clear();
            drops.add(stack);
        }
        return drops;
    }
    @Override
    protected InteractionResult useItemOn(ItemStack p_316304_, BlockState p_316362_, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand p_316595_, BlockHitResult p_316140_) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if (entity instanceof SlimeSqueezerBlockEntity) {
                MenuProvider containerProvider = (SlimeSqueezerBlockEntity) entity;
                pPlayer.openMenu(containerProvider, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }
        return createTickerHelper(pBlockEntityType, ModBlockEntities.SLIME_SQUEEZER_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }
    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        BlockEntity be = pLevel.getBlockEntity(pPos);
        if (be instanceof SlimeSqueezerBlockEntity slimeSqueezerBlockEntity) {
            int energy = pStack.getOrDefault(ModDataComponents.ENERGY.get(), 0);
            slimeSqueezerBlockEntity.getEnergyHandler().setEnergy(energy);
        }
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
    }
    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltip, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltip, pTooltipFlag);
        if (pStack.getOrDefault(ModDataComponents.ENERGY.get(), 0) != 0) {
            int energy = pStack.getOrDefault(ModDataComponents.ENERGY.get(), 0);
            pTooltip.add(Component.translatable("tooltip.productiveslimes.energy_stored")
                    .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x00FF00)))
                    .append(Component.translatable("tooltip.productiveslimes.energy_amount", energy)
                            .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFFF)))));
        }
    }
}