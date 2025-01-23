package com.coolerpromc.productiveslimes.block.custom;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class ModFlammableRotatedPillarBlock extends RotatedPillarBlock {
    public ModFlammableRotatedPillarBlock(Properties properties) {
        super(properties);
    }
    @Override
    public boolean isFlammable(BlockState state, IBlockReader level, BlockPos pos, Direction direction) {
        return true;
    }
    @Override
    public int getFlammability(BlockState state, IBlockReader level, BlockPos pos, Direction direction) {
        return 5;
    }
    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolAction) {
        if(player.getItemInHand(Hand.MAIN_HAND).getItem() instanceof AxeItem) {
            if(state.is(ModBlocks.SLIMY_LOG.get())) {
                return ModBlocks.STRIPPED_SLIMY_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
            if(state.is(ModBlocks.SLIMY_WOOD.get())) {
                return ModBlocks.STRIPPED_SLIMY_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
        }
        return super.getToolModifiedState(state, world, pos, player, stack, toolAction);
    }

    @Override
    public void stepOn(World level, BlockPos pos, Entity entity) {
        super.stepOn(level, pos, entity);
        if (!entity.isOnGround() || entity.isSpectator() || entity.isVehicle()) {
            return;
        }
        double slowFactor = 0.05;
        entity.setDeltaMovement(
                entity.getDeltaMovement().multiply(slowFactor, 1.0, slowFactor)
        );
    }
}