package com.coolerpromc.productiveslimes.block.custom;

import com.coolerpromc.productiveslimes.block.entity.CableBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.Random;

public class CableBlock extends Block {
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
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(UP, DOWN, NORTH, SOUTH, EAST, WEST);
        super.createBlockStateDefinition(pBuilder);
    }

    @Override
    public void onRemove(BlockState state, World level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof CableBlockEntity) {
                CableBlockEntity cableBE = (CableBlockEntity) blockEntity;
                cableBE.onRemoved();
            }
            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = pos.relative(direction);
                TileEntity neighborBE = level.getBlockEntity(neighborPos);
                if (neighborBE instanceof CableBlockEntity) {
                    CableBlockEntity neighborCable = (CableBlockEntity) blockEntity;
                    neighborCable.reinitializeNetwork();
                }
            }
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext pContext) {
        World level = pContext.getLevel();
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
    public VoxelShape getShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context) {
        VoxelShape shape = CORE_SHAPE;
        if (state.getValue(UP)) {
            shape = VoxelShapes.or(shape, UP_SHAPE);
        }
        if (state.getValue(DOWN)) {
            shape = VoxelShapes.or(shape, DOWN_SHAPE);
        }
        if (state.getValue(NORTH)) {
            shape = VoxelShapes.or(shape, NORTH_SHAPE);
        }
        if (state.getValue(SOUTH)) {
            shape = VoxelShapes.or(shape, SOUTH_SHAPE);
        }
        if (state.getValue(EAST)) {
            shape = VoxelShapes.or(shape, EAST_SHAPE);
        }
        if (state.getValue(WEST)) {
            shape = VoxelShapes.or(shape, WEST_SHAPE);
        }
        return shape;
    }

    @Override
    public void setPlacedBy(World pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        updateConnections(pLevel, pPos, pState);
    }

    private void updateConnections(World level, BlockPos pos, BlockState state) {
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
    private boolean canConnectToBlock(World level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();

        return block instanceof CableBlock || canConnectBasedOnBlock(block);
    }
    private boolean canConnectBasedOnBlock(Block block) {
        return block instanceof IEnergyStorage;
    }
    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, IWorld levelAccessor, BlockPos pos, BlockPos neighborPos) {
        if (levelAccessor instanceof World) {
            World level = (World) levelAccessor;
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
    private boolean canConnectTo(World level, BlockPos pos, Direction direction) {
        // Get the BlockEntity at the target position
        TileEntity blockEntity = level.getBlockEntity(pos);

        // Check if the target BlockEntity has the energy capability on the specified side
        if (blockEntity != null) {
            LazyOptional<IEnergyStorage> energyStorage = blockEntity.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite());
            if (energyStorage.isPresent()) {
                return true;
            }
        }

        BlockState state = level.getBlockState(pos);
        return state.getBlock() instanceof CableBlock || canConnectBasedOnBlock(state.getBlock());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CableBlockEntity();
    }
}