package com.coolerpromc.productiveslimes.block.custom;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.worldgen.dimension.ModDimensions;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class SlimyPortalBlock extends Block implements Portal {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    protected static final VoxelShape X_AXIS_AABB = Block.box(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
    protected static final VoxelShape Z_AXIS_AABB = Block.box(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);


    public SlimyPortalBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.X));
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity.canUsePortal(false)){
            entity.setAsInsidePortal(this, pos);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    @Override
    public int getPortalTransitionTime(ServerLevel serverLevel, Entity entity) {
        return entity instanceof Player player
                ? Math.max(
                0,
                serverLevel.getGameRules()
                        .getInt(
                                player.getAbilities().invulnerable
                                        ? GameRules.RULE_PLAYERS_NETHER_PORTAL_CREATIVE_DELAY
                                        : GameRules.RULE_PLAYERS_NETHER_PORTAL_DEFAULT_DELAY
                        )
        )
                : 0;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        switch (state.getValue(AXIS)) {
            case Z:
                return Z_AXIS_AABB;
            case X:
            default:
                return X_AXIS_AABB;
        }
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rot) {
        switch (rot) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch (state.getValue(AXIS)) {
                    case Z:
                        return state.setValue(AXIS, Direction.Axis.X);
                    case X:
                        return state.setValue(AXIS, Direction.Axis.Z);
                    default:
                        return state;
                }
            default:
                return state;
        }
    }

    @Nullable
    public TeleportTransition getPortalDestination(ServerLevel serverLevel, Entity entity, BlockPos blockPos) {
        ResourceKey<Level> resourcekey = serverLevel.dimension() == ModDimensions.SLIMY_WORLD ? Level.OVERWORLD : ModDimensions.SLIMY_WORLD;
        ServerLevel serverlevel = serverLevel.getServer().getLevel(resourcekey);
        if (serverlevel == null) {
            return null;
        } else {
            boolean flag = serverlevel.dimension() == ModDimensions.SLIMY_WORLD;
            WorldBorder worldborder = serverlevel.getWorldBorder();
            double d0 = DimensionType.getTeleportationScale(serverLevel.dimensionType(), serverlevel.dimensionType());
            BlockPos blockpos = worldborder.clampToBounds(entity.getX() * d0, entity.getY(), entity.getZ() * d0);
            return this.getExitPortal(serverlevel, entity, blockPos, blockpos, flag, worldborder);
        }
    }

    private TeleportTransition getExitPortal(ServerLevel level, Entity entity, BlockPos pos, BlockPos exitPos, boolean isSlimyWorld, WorldBorder worldBorder) {
        Optional<BlockPos> optional = findClosestSlimyPortal(level, exitPos, 16);
        BlockUtil.FoundRectangle blockutil$foundrectangle;
        TeleportTransition.PostTeleportTransition teleporttransition$postteleporttransition;

        if (optional.isPresent()) {
            BlockPos blockpos = optional.get();
            BlockState blockstate = level.getBlockState(blockpos);
            blockutil$foundrectangle = BlockUtil.getLargestRectangleAround(
                    blockpos,
                    blockstate.getValue(BlockStateProperties.HORIZONTAL_AXIS),
                    21,
                    Direction.Axis.Y,
                    21,
                    checkPos -> level.getBlockState(checkPos) == blockstate
            );
            teleporttransition$postteleporttransition = TeleportTransition.PLAY_PORTAL_SOUND.then(entity1 -> entity1.placePortalTicket(blockpos));
        } else {
            Direction.Axis direction$axis = entity.level().getBlockState(pos).getOptionalValue(AXIS).orElse(Direction.Axis.X);
            Optional<BlockUtil.FoundRectangle> optional1 = buildCustomPortal(level, exitPos, direction$axis);
            if (optional1.isEmpty()) {
                return null;
            }
            blockutil$foundrectangle = optional1.get();
            teleporttransition$postteleporttransition = TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET);
        }

        return getDimensionTransitionFromExit(entity, pos, blockutil$foundrectangle, level, teleporttransition$postteleporttransition);
    }

    private Optional<BlockPos> findClosestSlimyPortal(ServerLevel level, BlockPos center, int horizontalRadius) {
        BlockPos closest = null;
        double minDistance = Double.MAX_VALUE;
        int cx = center.getX();
        int cz = center.getZ();
        for (int x = cx - horizontalRadius; x <= cx + horizontalRadius; x++) {
            for (int z = cz - horizontalRadius; z <= cz + horizontalRadius; z++) {
                for (int y = level.getMinY(); y < level.getMaxY(); y++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (level.getBlockState(pos).getBlock() == ModBlocks.SLIMY_PORTAL.get()) {
                        double distance = pos.distSqr(center);
                        if (distance < minDistance) {
                            minDistance = distance;
                            closest = pos;
                        }
                    }
                }
            }
        }
        return Optional.ofNullable(closest);
    }

    private Optional<BlockUtil.FoundRectangle> buildCustomPortal(ServerLevel level, BlockPos pos, Direction.Axis axis) {
        Direction direction = Direction.get(Direction.AxisDirection.POSITIVE, axis);
        double d0 = -1.0;
        BlockPos blockpos = null;
        double d1 = -1.0;
        BlockPos blockpos1 = null;
        WorldBorder worldborder = level.getWorldBorder();
        int i = Math.min(level.getMaxY(), 63 + level.getLogicalHeight() - 1);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();

        int exitChunkX = pos.getX() >> 4;
        int exitChunkZ = pos.getZ() >> 4;

        for (BlockPos.MutableBlockPos blockpos$mutableblockpos1 : BlockPos.spiralAround(pos, 16, Direction.EAST, Direction.SOUTH)) {
            int currentChunkX = blockpos$mutableblockpos1.getX() >> 4;
            int currentChunkZ = blockpos$mutableblockpos1.getZ() >> 4;
            if (currentChunkX != exitChunkX || currentChunkZ != exitChunkZ) {
                continue;
            }

            int k = Math.min(i, level.getHeight(Heightmap.Types.MOTION_BLOCKING, blockpos$mutableblockpos1.getX(), blockpos$mutableblockpos1.getZ()));
            if (worldborder.isWithinBounds(blockpos$mutableblockpos1) && worldborder.isWithinBounds(blockpos$mutableblockpos1.move(direction, 1))) {
                blockpos$mutableblockpos1.move(direction.getOpposite(), 1);

                for (int l = k; l >= 63; l--) {
                    blockpos$mutableblockpos1.setY(l);
                    if (this.canPortalReplaceBlock(level, blockpos$mutableblockpos1)) {
                        int i1 = l;

                        while (l > 63 && this.canPortalReplaceBlock(level, blockpos$mutableblockpos1.move(Direction.DOWN))) {
                            l--;
                        }

                        if (l + 4 <= i) {
                            int j1 = i1 - l;
                            if (j1 <= 0 || j1 >= 3) {
                                blockpos$mutableblockpos1.setY(l);
                                if (this.canHostFrame(level, blockpos$mutableblockpos1, blockpos$mutableblockpos, direction, 0)) {
                                    double d2 = pos.distSqr(blockpos$mutableblockpos1);
                                    if (this.canHostFrame(level, blockpos$mutableblockpos1, blockpos$mutableblockpos, direction, -1)
                                            && this.canHostFrame(level, blockpos$mutableblockpos1, blockpos$mutableblockpos, direction, 1)
                                            && (d0 == -1.0 || d0 > d2)) {
                                        d0 = d2;
                                        blockpos = blockpos$mutableblockpos1.immutable();
                                    }

                                    if (d0 == -1.0 && (d1 == -1.0 || d1 > d2)) {
                                        d1 = d2;
                                        blockpos1 = blockpos$mutableblockpos1.immutable();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (d0 == -1.0 && d1 != -1.0) {
            blockpos = blockpos1;
            d0 = d1;
        }

        if (d0 == -1.0) {
            int k1 = Math.max(63 - -1, 70);
            int i2 = i - 9;
            if (i2 < k1) {
                return Optional.empty();
            }

            blockpos = new BlockPos(pos.getX() - direction.getStepX() * 1, Mth.clamp(pos.getY(), k1, i2), pos.getZ() - direction.getStepZ() * 1)
                    .immutable();
            blockpos = worldborder.clampToBounds(blockpos);
            Direction direction1 = direction.getClockWise();

            for (int i3 = -1; i3 < 2; i3++) {
                for (int j3 = 0; j3 < 2; j3++) {
                    for (int k3 = -1; k3 < 3; k3++) {
                        BlockState blockstate1 = k3 < 0 ? ModBlocks.SLIMY_PORTAL_FRAME.get().defaultBlockState() : Blocks.AIR.defaultBlockState();
                        blockpos$mutableblockpos.setWithOffset(
                                blockpos, j3 * direction.getStepX() + i3 * direction1.getStepX(), k3, j3 * direction.getStepZ() + i3 * direction1.getStepZ()
                        );
                        level.setBlockAndUpdate(blockpos$mutableblockpos, blockstate1);
                    }
                }
            }
        }

        for (int l1 = -1; l1 < 3; l1++) {
            for (int j2 = -1; j2 < 4; j2++) {
                if (l1 == -1 || l1 == 2 || j2 == -1 || j2 == 3) {
                    blockpos$mutableblockpos.setWithOffset(blockpos, l1 * direction.getStepX(), j2, l1 * direction.getStepZ());
                    level.setBlock(blockpos$mutableblockpos, ModBlocks.SLIMY_PORTAL_FRAME.get().defaultBlockState(), 3);
                }
            }
        }

        BlockState blockstate = ModBlocks.SLIMY_PORTAL.get().defaultBlockState().setValue(AXIS, axis);

        for (int k2 = 0; k2 < 2; k2++) {
            for (int l2 = 0; l2 < 3; l2++) {
                blockpos$mutableblockpos.setWithOffset(blockpos, k2 * direction.getStepX(), l2, k2 * direction.getStepZ());
                level.setBlock(blockpos$mutableblockpos, blockstate, 18);
            }
        }

        return Optional.of(new BlockUtil.FoundRectangle(blockpos.immutable(), 2, 3));
    }

    private boolean canPortalReplaceBlock(ServerLevel level, BlockPos.MutableBlockPos pos) {
        BlockState blockstate = level.getBlockState(pos);
        return blockstate.canBeReplaced() && blockstate.getFluidState().isEmpty();
    }

    private boolean canHostFrame(ServerLevel level, BlockPos originalPos, BlockPos.MutableBlockPos offsetPos, Direction p_direction, int offsetScale) {
        Direction direction = p_direction.getClockWise();

        for (int i = -1; i < 3; i++) {
            for (int j = -1; j < 4; j++) {
                offsetPos.setWithOffset(
                        originalPos, p_direction.getStepX() * i + direction.getStepX() * offsetScale, j, p_direction.getStepZ() * i + direction.getStepZ() * offsetScale
                );
                if (j < 0 && !level.getBlockState(offsetPos).isSolid()) {
                    return false;
                }

                if (j >= 0 && !canPortalReplaceBlock(level, offsetPos)) {
                    return false;
                }
            }
        }

        return true;
    }

    private static TeleportTransition getDimensionTransitionFromExit(Entity entity, BlockPos pos, BlockUtil.FoundRectangle rectangle, ServerLevel level, TeleportTransition.PostTeleportTransition postTeleportTransition) {
        BlockState blockstate = entity.level().getBlockState(pos);
        Direction.Axis direction$axis;
        Vec3 vec3;
        if (blockstate.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
            direction$axis = blockstate.getValue(BlockStateProperties.HORIZONTAL_AXIS);
            BlockUtil.FoundRectangle blockutil$foundrectangle = BlockUtil.getLargestRectangleAround(
                    pos, direction$axis, 21, Direction.Axis.Y, 21, blockPos -> entity.level().getBlockState(blockPos) == blockstate
            );
            vec3 = entity.getRelativePortalPosition(direction$axis, blockutil$foundrectangle);
        } else {
            direction$axis = Direction.Axis.X;
            vec3 = new Vec3(0.5, 0.0, 0.0);
        }

        return createDimensionTransition(level, rectangle, direction$axis, vec3, entity, postTeleportTransition);
    }

    private static TeleportTransition createDimensionTransition(ServerLevel level, BlockUtil.FoundRectangle rectangle, Direction.Axis axis, Vec3 offset, Entity entity, TeleportTransition.PostTeleportTransition postTeleportTransition) {
        BlockPos blockpos = rectangle.minCorner;
        BlockState blockstate = level.getBlockState(blockpos);
        Direction.Axis direction$axis = blockstate.getOptionalValue(BlockStateProperties.HORIZONTAL_AXIS).orElse(Direction.Axis.X);
        double d0 = rectangle.axis1Size;
        double d1 = rectangle.axis2Size;
        EntityDimensions entitydimensions = entity.getDimensions(entity.getPose());
        int i = axis == direction$axis ? 0 : 90;
        double d2 = (double)entitydimensions.width() / 2.0 + (d0 - (double)entitydimensions.width()) * offset.x();
        double d3 = (d1 - (double)entitydimensions.height()) * offset.y();
        double d4 = 0.5 + offset.z();
        boolean flag = direction$axis == Direction.Axis.X;
        Vec3 vec3 = new Vec3((double)blockpos.getX() + (flag ? d2 : d4), (double)blockpos.getY() + d3, (double)blockpos.getZ() + (flag ? d4 : d2));
        Vec3 vec31 = PortalShape.findCollisionFreePosition(vec3, level, entity, entitydimensions);
        return new TeleportTransition(level, vec31, Vec3.ZERO, (float)i, 0.0F, Relative.union(Relative.DELTA, Relative.ROTATION), postTeleportTransition);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource p_221797_) {
        if (p_221797_.nextInt(100) == 0) {
            level.playLocalSound(
                    (double)blockPos.getX() + 0.5,
                    (double)blockPos.getY() + 0.5,
                    (double)blockPos.getZ() + 0.5,
                    SoundEvents.PORTAL_AMBIENT,
                    SoundSource.BLOCKS,
                    0.5F,
                    p_221797_.nextFloat() * 0.4F + 0.8F,
                    false
            );
        }

        for (int i = 0; i < 4; i++) {
            double d0 = (double)blockPos.getX() + p_221797_.nextDouble();
            double d1 = (double)blockPos.getY() + p_221797_.nextDouble();
            double d2 = (double)blockPos.getZ() + p_221797_.nextDouble();
            double d3 = ((double)p_221797_.nextFloat() - 0.5) * 0.5;
            double d4 = ((double)p_221797_.nextFloat() - 0.5) * 0.5;
            double d5 = ((double)p_221797_.nextFloat() - 0.5) * 0.5;
            int j = p_221797_.nextInt(2) * 2 - 1;
            if (!level.getBlockState(blockPos.west()).is(this) && !level.getBlockState(blockPos.east()).is(this)) {
                d0 = (double)blockPos.getX() + 0.5 + 0.25 * (double)j;
                d3 = p_221797_.nextFloat() * 2.0F * (float)j;
            } else {
                d2 = (double)blockPos.getZ() + 0.5 + 0.25 * (double)j;
                d5 = p_221797_.nextFloat() * 2.0F * (float)j;
            }

            level.addParticle(ParticleTypes.ITEM_SLIME, d0, d1, d2, d3, d4, d5);
        }
    }

    @Override
    public Transition getLocalTransition() {
        return Portal.Transition.CONFUSION;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
        if (!level.isClientSide && !state.is(newState.getBlock())) {
            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = pos.relative(direction);
                BlockState neighborState = level.getBlockState(neighborPos);
                if (neighborState.getBlock() instanceof SlimyPortalBlock) {
                    ((SlimyPortalBlock) neighborState.getBlock()).removePortal(level, neighborPos);
                }
            }
        }
    }

    protected void removePortal(Level level, BlockPos pos) {
        if (level.isClientSide) {
            return;
        }

        BlockState state = level.getBlockState(pos);
        if (!state.is(this)) {
            return;
        }

        Direction.Axis axis = state.getValue(AXIS);
        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> queue = new LinkedList<>();
        queue.add(pos.immutable());
        visited.add(pos.immutable());

        while (!queue.isEmpty()) {
            BlockPos currentPos = queue.poll();
            level.setBlock(currentPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);

            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = currentPos.relative(direction);
                if (!visited.contains(neighborPos) && level.getBlockState(neighborPos).is(this)) {
                    BlockState neighborState = level.getBlockState(neighborPos);
                    if (neighborState.getValue(AXIS) == axis) {
                        visited.add(neighborPos);
                        queue.add(neighborPos);
                    }
                }
            }
        }
    }
}
