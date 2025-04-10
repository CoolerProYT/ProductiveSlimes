package com.coolerpromc.productiveslimes.block.custom;

import com.coolerpromc.productiveslimes.block.entity.PipeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
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
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;

public class PipeBlock extends Block implements EntityBlock {
    public static final BooleanProperty UP    = BooleanProperty.create("up");
    public static final BooleanProperty DOWN  = BooleanProperty.create("down");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty EAST  = BooleanProperty.create("east");
    public static final BooleanProperty WEST  = BooleanProperty.create("west");

    private static final VoxelShape CORE_SHAPE  = Block.box(5, 5, 5, 11, 11, 11);
    private static final VoxelShape UP_SHAPE    = Block.box(5, 11, 5, 11, 15, 11);
    private static final VoxelShape DOWN_SHAPE  = Block.box(5, 0, 5, 11, 5, 11);
    private static final VoxelShape NORTH_SHAPE = Block.box(5, 5, 0, 11, 11, 5);
    private static final VoxelShape SOUTH_SHAPE = Block.box(5, 5, 11, 11, 11, 15);
    private static final VoxelShape EAST_SHAPE  = Block.box(11, 5, 5, 15, 11, 11);
    private static final VoxelShape WEST_SHAPE  = Block.box(0, 5, 5, 5, 11, 11);

    public PipeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(UP, false)
                .setValue(DOWN, false)
                .setValue(NORTH, false)
                .setValue(SOUTH, false)
                .setValue(EAST, false)
                .setValue(WEST, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, SOUTH, EAST, WEST);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelAccessor level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        return this.defaultBlockState()
                .setValue(UP, canConnectToBlock(level, pos.above()))
                .setValue(DOWN, canConnectToBlock(level, pos.below()))
                .setValue(NORTH, canConnectToBlock(level, pos.north()))
                .setValue(SOUTH, canConnectToBlock(level, pos.south()))
                .setValue(EAST, canConnectToBlock(level, pos.east()))
                .setValue(WEST, canConnectToBlock(level, pos.west()));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = CORE_SHAPE;
        if (state.getValue(UP))    shape = Shapes.or(shape, UP_SHAPE);
        if (state.getValue(DOWN))  shape = Shapes.or(shape, DOWN_SHAPE);
        if (state.getValue(NORTH)) shape = Shapes.or(shape, NORTH_SHAPE);
        if (state.getValue(SOUTH)) shape = Shapes.or(shape, SOUTH_SHAPE);
        if (state.getValue(EAST))  shape = Shapes.or(shape, EAST_SHAPE);
        if (state.getValue(WEST))  shape = Shapes.or(shape, WEST_SHAPE);
        return shape;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide()) {
            // Instead of updating immediately, schedule a tick update.
            level.scheduleTick(pos, this, 1);
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (!level.isClientSide()) {
            level.scheduleTick(pos, this, 1);
            // Also schedule ticks for neighbors so they update their connections.
            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = pos.relative(direction);
                level.scheduleTick(neighborPos, this, 1);
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        updateConnections(level, pos, state);
    }

    /**
     * Update this pipe’s connection properties based on neighbors.
     */
    private void updateConnections(Level level, BlockPos pos, BlockState state) {
        if (!level.isClientSide()) {
            BlockState newState = state;
            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = pos.relative(direction);
                BooleanProperty property = getPropertyForDirection(direction);
                boolean canConnect = canConnectTo(level, pos, neighborPos, direction);
                newState = newState.setValue(property, canConnect);
            }
            level.setBlock(pos, newState, 2);
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (level instanceof Level lvl) {
            boolean canConnect = canConnectTo(lvl, pos, neighborPos, direction);
            return state.setValue(getPropertyForDirection(direction), canConnect);
        }
        return state;
    }

    private BooleanProperty getPropertyForDirection(Direction direction) {
        switch (direction) {
            case UP:    return UP;
            case DOWN:  return DOWN;
            case NORTH: return NORTH;
            case SOUTH: return SOUTH;
            case EAST:  return EAST;
            case WEST:  return WEST;
            default:    throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }

    /**
     * Connection check that uses the fluid handler capabilities.
     * If both sides have a handler, compare their FluidStacks (empty vs. non-empty and fluid type).
     * Otherwise, if the neighbor is a PipeBlock, attempt a fallback check.
     */
    private boolean canConnectTo(Level level, BlockPos currentPos, BlockPos pos, Direction direction) {
        // Get neighbor's handler from the side facing current block.
        IFluidHandler neighborHandler = level.getCapability(Capabilities.FluidHandler.BLOCK, pos, direction.getOpposite());
        // Get current block's handler from the side facing neighbor.
        IFluidHandler currentHandler = level.getCapability(Capabilities.FluidHandler.BLOCK, currentPos, direction);

        if (!(level.getBlockEntity(pos) instanceof PipeBlockEntity)) {
            return neighborHandler != null;
        }

        if (neighborHandler != null && currentHandler != null) {
            FluidStack currentFluid = currentHandler.getFluidInTank(0);
            FluidStack neighborFluid = neighborHandler.getFluidInTank(0);

            // Both empty: connect
            if (currentFluid.isEmpty() && neighborFluid.isEmpty()) {
                return true;
            }
            // One empty but not the other: do not connect
            if (currentFluid.isEmpty() || neighborFluid.isEmpty()) {
                return false;
            }
            // Otherwise, connect only if the fluids are the same
            return currentFluid.getFluid().isSame(neighborFluid.getFluid());
        }

        // Fallback: if neighbor is a PipeBlock, try to re-acquire the handlers.
        BlockState neighborState = level.getBlockState(pos);
        if (neighborState.getBlock() instanceof PipeBlock) {
            IFluidHandler pipeNeighborHandler = level.getCapability(Capabilities.FluidHandler.BLOCK, pos, direction.getOpposite());
            IFluidHandler pipeCurrentHandler  = level.getCapability(Capabilities.FluidHandler.BLOCK, currentPos, direction);
            if (pipeNeighborHandler != null && pipeCurrentHandler != null) {
                FluidStack currentFluid = pipeCurrentHandler.getFluidInTank(0);
                FluidStack neighborFluid = pipeNeighborHandler.getFluidInTank(0);

                if (currentFluid.isEmpty() && neighborFluid.isEmpty()) {
                    return true;
                }
                if (currentFluid.isEmpty() || neighborFluid.isEmpty()) {
                    return false;
                }
                return currentFluid.getFluid().isSame(neighborFluid.getFluid());
            }
            // Fallback for pipes if handlers still aren’t available.
            return true;
        }

        return false;
    }

    private boolean canConnectToBlock(LevelAccessor level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        return block instanceof PipeBlock || canConnectBasedOnBlock(block);
    }

    private boolean canConnectBasedOnBlock(Block block) {
        return block instanceof IFluidHandler;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PipeBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (lvl, pos, blockState, t) -> {
            if (t instanceof PipeBlockEntity pipeEntity) {
                PipeBlockEntity.tick(lvl, pos, pipeEntity);
            }
        };
    }
}
