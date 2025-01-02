package com.coolerpromc.productiveslimes.block.custom;

import com.coolerpromc.productiveslimes.block.entity.ModBlockEntities;
import com.coolerpromc.productiveslimes.block.entity.SlimeballCollectorBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class SlimeballCollectorBlock extends BaseEntityBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final VoxelShape SOUTH_SHAPE = Stream.of(
            Stream.of(
                    Block.box(3, 3, 6, 4, 13, 8),
                    Block.box(12, 3, 6, 13, 13, 8),
                    Block.box(4, 3, 6, 12, 4, 8),
                    Block.box(4, 12, 6, 12, 13, 8)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(3, 3, 8, 4, 13, 10),
                    Block.box(12, 3, 8, 13, 13, 10),
                    Block.box(4, 3, 8, 12, 4, 10),
                    Block.box(4, 12, 8, 12, 13, 10)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(1, 1, 12, 2, 15, 14),
                    Block.box(14, 1, 12, 15, 15, 14),
                    Block.box(2, 1, 12, 14, 2, 14),
                    Block.box(2, 14, 12, 14, 15, 14)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(0, 0, 14, 16, 1, 16),
                    Block.box(15, 1, 14, 16, 15, 16),
                    Block.box(0, 1, 14, 1, 15, 16),
                    Block.box(0, 15, 14, 16, 16, 16)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(0, 0, 0, 16, 1, 2),
                    Block.box(15, 1, 0, 16, 15, 2),
                    Block.box(0, 1, 0, 1, 15, 2),
                    Block.box(0, 15, 0, 16, 16, 2)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(1, 1, 2, 2, 15, 4),
                    Block.box(14, 1, 2, 15, 15, 4),
                    Block.box(2, 1, 2, 14, 2, 4),
                    Block.box(2, 14, 2, 14, 15, 4)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(3, 13, 10, 13, 14, 12),
                    Block.box(3, 2, 10, 13, 3, 12),
                    Block.box(2, 2, 10, 3, 14, 12),
                    Block.box(13, 2, 10, 14, 14, 12)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(3, 13, 4, 13, 14, 6),
                    Block.box(3, 2, 4, 13, 3, 6),
                    Block.box(2, 2, 4, 3, 14, 6),
                    Block.box(13, 2, 4, 14, 14, 6)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Shapes.join(Block.box(5, 4, 5, 11, 10, 11), Block.box(7.5, 6.5, 11, 8.5, 8.5, 12), BooleanOp.OR)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape NORTH_SHAPE = Stream.of(
            Stream.of(
                    Block.box(12, 3, 8, 13, 13, 10),
                    Block.box(3, 3, 8, 4, 13, 10),
                    Block.box(4, 3, 8, 12, 4, 10),
                    Block.box(4, 12, 8, 12, 13, 10)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(12, 3, 6, 13, 13, 8),
                    Block.box(3, 3, 6, 4, 13, 8),
                    Block.box(4, 3, 6, 12, 4, 8),
                    Block.box(4, 12, 6, 12, 13, 8)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(14, 1, 2, 15, 15, 4),
                    Block.box(1, 1, 2, 2, 15, 4),
                    Block.box(2, 1, 2, 14, 2, 4),
                    Block.box(2, 14, 2, 14, 15, 4)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(0, 0, 0, 16, 1, 2),
                    Block.box(0, 1, 0, 1, 15, 2),
                    Block.box(15, 1, 0, 16, 15, 2),
                    Block.box(0, 15, 0, 16, 16, 2)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(0, 0, 14, 16, 1, 16),
                    Block.box(0, 1, 14, 1, 15, 16),
                    Block.box(15, 1, 14, 16, 15, 16),
                    Block.box(0, 15, 14, 16, 16, 16)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(14, 1, 12, 15, 15, 14),
                    Block.box(1, 1, 12, 2, 15, 14),
                    Block.box(2, 1, 12, 14, 2, 14),
                    Block.box(2, 14, 12, 14, 15, 14)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(3, 13, 4, 13, 14, 6),
                    Block.box(3, 2, 4, 13, 3, 6),
                    Block.box(13, 2, 4, 14, 14, 6),
                    Block.box(2, 2, 4, 3, 14, 6)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(3, 13, 10, 13, 14, 12),
                    Block.box(3, 2, 10, 13, 3, 12),
                    Block.box(13, 2, 10, 14, 14, 12),
                    Block.box(2, 2, 10, 3, 14, 12)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Shapes.join(Block.box(5, 4, 5, 11, 10, 11), Block.box(7.5, 6.5, 4, 8.5, 8.5, 5), BooleanOp.OR)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape EAST_SHAPE = Stream.of(
            Stream.of(
                    Block.box(6, 3, 12, 8, 13, 13),
                    Block.box(6, 3, 3, 8, 13, 4),
                    Block.box(6, 3, 4, 8, 4, 12),
                    Block.box(6, 12, 4, 8, 13, 12)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(8, 3, 12, 10, 13, 13),
                    Block.box(8, 3, 3, 10, 13, 4),
                    Block.box(8, 3, 4, 10, 4, 12),
                    Block.box(8, 12, 4, 10, 13, 12)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(12, 1, 14, 14, 15, 15),
                    Block.box(12, 1, 1, 14, 15, 2),
                    Block.box(12, 1, 2, 14, 2, 14),
                    Block.box(12, 14, 2, 14, 15, 14)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(14, 0, 0, 16, 1, 16),
                    Block.box(14, 1, 0, 16, 15, 1),
                    Block.box(14, 1, 15, 16, 15, 16),
                    Block.box(14, 15, 0, 16, 16, 16)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(0, 0, 0, 2, 1, 16),
                    Block.box(0, 1, 0, 2, 15, 1),
                    Block.box(0, 1, 15, 2, 15, 16),
                    Block.box(0, 15, 0, 2, 16, 16)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(2, 1, 14, 4, 15, 15),
                    Block.box(2, 1, 1, 4, 15, 2),
                    Block.box(2, 1, 2, 4, 2, 14),
                    Block.box(2, 14, 2, 4, 15, 14)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(10, 13, 3, 12, 14, 13),
                    Block.box(10, 2, 3, 12, 3, 13),
                    Block.box(10, 2, 13, 12, 14, 14),
                    Block.box(10, 2, 2, 12, 14, 3)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(4, 13, 3, 6, 14, 13),
                    Block.box(4, 2, 3, 6, 3, 13),
                    Block.box(4, 2, 13, 6, 14, 14),
                    Block.box(4, 2, 2, 6, 14, 3)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Shapes.join(Block.box(5, 4, 5, 11, 10, 11), Block.box(11, 6.5, 7.5, 12, 8.5, 8.5), BooleanOp.OR)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape WEST_SHAPE = Stream.of(
            Stream.of(
                    Block.box(8, 3, 3, 10, 13, 4),
                    Block.box(8, 3, 12, 10, 13, 13),
                    Block.box(8, 3, 4, 10, 4, 12),
                    Block.box(8, 12, 4, 10, 13, 12)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(6, 3, 3, 8, 13, 4),
                    Block.box(6, 3, 12, 8, 13, 13),
                    Block.box(6, 3, 4, 8, 4, 12),
                    Block.box(6, 12, 4, 8, 13, 12)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(2, 1, 1, 4, 15, 2),
                    Block.box(2, 1, 14, 4, 15, 15),
                    Block.box(2, 1, 2, 4, 2, 14),
                    Block.box(2, 14, 2, 4, 15, 14)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(0, 0, 0, 2, 1, 16),
                    Block.box(0, 1, 15, 2, 15, 16),
                    Block.box(0, 1, 0, 2, 15, 1),
                    Block.box(0, 15, 0, 2, 16, 16)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(14, 0, 0, 16, 1, 16),
                    Block.box(14, 1, 15, 16, 15, 16),
                    Block.box(14, 1, 0, 16, 15, 1),
                    Block.box(14, 15, 0, 16, 16, 16)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(12, 1, 1, 14, 15, 2),
                    Block.box(12, 1, 14, 14, 15, 15),
                    Block.box(12, 1, 2, 14, 2, 14),
                    Block.box(12, 14, 2, 14, 15, 14)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(4, 13, 3, 6, 14, 13),
                    Block.box(4, 2, 3, 6, 3, 13),
                    Block.box(4, 2, 2, 6, 14, 3),
                    Block.box(4, 2, 13, 6, 14, 14)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(10, 13, 3, 12, 14, 13),
                    Block.box(10, 2, 3, 12, 3, 13),
                    Block.box(10, 2, 2, 12, 14, 3),
                    Block.box(10, 2, 13, 12, 14, 14)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Shapes.join(Block.box(5, 4, 5, 11, 10, 11), Block.box(4, 6.5, 7.5, 5, 8.5, 8.5), BooleanOp.OR)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public SlimeballCollectorBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SlimeballCollectorBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide()) {
            return null;
        }
        return createTickerHelper(blockEntityType, ModBlockEntities.SLIMEBALL_COLLECTOR_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            default -> Shapes.block();
        };
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
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
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SlimeballCollectorBlockEntity slimeballCollectorBlockEntity) {
                slimeballCollectorBlockEntity.drops();
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public InteractionResult use(BlockState pState, Level level, BlockPos pos, Player player, InteractionHand pHand, BlockHitResult pHit) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof SlimeballCollectorBlockEntity slimeballCollectorBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer)player), slimeballCollectorBlockEntity, pos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }
}