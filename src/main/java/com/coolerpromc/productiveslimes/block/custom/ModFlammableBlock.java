package com.coolerpromc.productiveslimes.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class ModFlammableBlock extends Block {
    public ModFlammableBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFlammable(BlockState state, IBlockReader level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader level, BlockPos pos, Direction direction) {
        return 20;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader level, BlockPos pos, Direction direction) {
        return 5;
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