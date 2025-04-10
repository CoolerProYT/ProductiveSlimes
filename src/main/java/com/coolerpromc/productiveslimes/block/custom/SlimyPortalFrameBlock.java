package com.coolerpromc.productiveslimes.block.custom;

import com.coolerpromc.productiveslimes.util.SlimyPortalShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Optional;

public class SlimyPortalFrameBlock extends Block {
    public SlimyPortalFrameBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean p_394545_) {
        if (!level.isClientSide) {
            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = pos.relative(direction);
                BlockState neighborState = level.getBlockState(neighborPos);
                if (neighborState.getBlock() instanceof SlimyPortalBlock) {
                    ((SlimyPortalBlock) neighborState.getBlock()).removePortal(level, neighborPos);
                }
            }
        }
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide && stack.getItem() == Items.SLIME_BALL) {
            Optional<SlimyPortalShape> optional = SlimyPortalShape.findEmptyPortalShape(level, pos.above(), Direction.Axis.X);
            if (optional.isEmpty()) {
                optional = SlimyPortalShape.findEmptyPortalShape(level, pos.above(), Direction.Axis.Z);
            }
            if (optional.isPresent()) {
                SlimyPortalShape portalShape = optional.get();
                portalShape.createPortalBlocks(level);
                level.playSound(
                        null,
                        pos,
                        SoundEvents.SLIME_SQUISH,
                        SoundSource.BLOCKS,
                        1.0F,
                        level.getRandom().nextFloat() * 0.4F + 0.8F
                );
                stack.shrink(1);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }
}
