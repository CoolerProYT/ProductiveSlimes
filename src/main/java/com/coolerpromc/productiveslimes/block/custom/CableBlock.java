package com.coolerpromc.productiveslimes.block.custom;

import com.coolerpromc.productiveslimes.block.entity.CableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class CableBlock extends Block implements EntityBlock {
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    private static final VoxelShape CORE_SHAPE = Block.box(5, 5, 5, 11, 11, 11);
    private static final VoxelShape UP_SHAPE = Block.box(5, 11, 5, 11, 15, 11);
    private static final VoxelShape DOWN_SHAPE = Block.box(5, 0, 5, 11, 5, 11);
    private static final VoxelShape NORTH_SHAPE = Block.box(5, 5, 0, 11, 11, 5);
    private static final VoxelShape SOUTH_SHAPE = Block.box(5, 5, 11, 11, 11, 15);
    private static final VoxelShape EAST_SHAPE = Block.box(11, 5, 5, 15, 11, 11);
    private static final VoxelShape WEST_SHAPE = Block.box(0, 5, 5, 5, 11, 11);

    public CableBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(UP, false)
                .setValue(DOWN, false)
                .setValue(NORTH, false)
                .setValue(SOUTH, false)
                .setValue(EAST, false)
                .setValue(WEST, false));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(UP, DOWN, NORTH, SOUTH, EAST, WEST);
    }
    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof CableBlockEntity cableBE) {
                cableBE.onRemoved();
            }
            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = pos.relative(direction);
                BlockEntity neighborBE = level.getBlockEntity(neighborPos);
                if (neighborBE instanceof CableBlockEntity neighborCable) {
                    neighborCable.reinitializeNetwork();
                }
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        LevelAccessor level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        return this.defaultBlockState()
                .setValue(UP, this.canConnectToBlock(level, pos.above()))
                .setValue(DOWN, this.canConnectToBlock(level, pos.below()))
                .setValue(NORTH, this.canConnectToBlock(level, pos.north()))
                .setValue(SOUTH, this.canConnectToBlock(level, pos.south()))
                .setValue(EAST, this.canConnectToBlock(level, pos.east()))
                .setValue(WEST, this.canConnectToBlock(level, pos.west()));
    }
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = CORE_SHAPE;
        if (state.getValue(UP)) {
            shape = Shapes.or(shape, UP_SHAPE);
        }
        if (state.getValue(DOWN)) {
            shape = Shapes.or(shape, DOWN_SHAPE);
        }
        if (state.getValue(NORTH)) {
            shape = Shapes.or(shape, NORTH_SHAPE);
        }
        if (state.getValue(SOUTH)) {
            shape = Shapes.or(shape, SOUTH_SHAPE);
        }
        if (state.getValue(EAST)) {
            shape = Shapes.or(shape, EAST_SHAPE);
        }
        if (state.getValue(WEST)) {
            shape = Shapes.or(shape, WEST_SHAPE);
        }
        return shape;
    }
    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        updateConnections(pLevel, pPos, pState);
    }
    private void updateConnections(Level level, BlockPos pos, BlockState state) {
        if (!level.isClientSide) {
            BlockState newState = state;
            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = pos.relative(direction);
                BooleanProperty property = getPropertyForDirection(direction);
                boolean canConnect = canConnectTo(level, neighborPos, direction);
                newState = newState.setValue(property, canConnect);
            }
            level.setBlock(pos, newState, 2);
        }
    }
    private boolean canConnectToBlock(LevelAccessor level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        // Define blocks that the cable can connect to
        return block instanceof CableBlock || canConnectBasedOnBlock(block);
    }
    private boolean canConnectBasedOnBlock(Block block) {
        return block instanceof IEnergyStorage;
    }
    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor levelAccessor,
                                  BlockPos pos, BlockPos neighborPos) {
        if (levelAccessor instanceof Level level) {
            boolean canConnect = this.canConnectTo(level, neighborPos, direction);
            return state.setValue(getPropertyForDirection(direction), canConnect);
        } else {
            return state;
        }
    }
    private BooleanProperty getPropertyForDirection(Direction direction) {
        switch (direction) {
            case UP: return UP;
            case DOWN: return DOWN;
            case NORTH: return NORTH;
            case SOUTH: return SOUTH;
            case EAST: return EAST;
            case WEST: return WEST;
            default: throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }
    private boolean canConnectTo(Level level, BlockPos pos, Direction direction) {
        // Access the capability at the neighbor position and side
        IEnergyStorage energyStorage = level.getCapability(Capabilities.EnergyStorage.BLOCK, pos, direction.getOpposite());
        if (energyStorage != null) {
            return true;
        } else {
            // Check if the block is another cable
            BlockState state = level.getBlockState(pos);
            return state.getBlock() instanceof CableBlock;
        }
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CableBlockEntity(pPos, pState);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return (lvl, pos, blockState, t) -> {
            if (t instanceof CableBlockEntity blockEntity) {
                CableBlockEntity.tick(lvl, pos, blockState, blockEntity);
            }
        };
    }
}