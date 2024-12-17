package com.coolerpromc.productiveslimes.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class SqueezerBlock extends Block {
    public SqueezerBlock(Properties properties) {
        super(properties);
    }
    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return false;
    }
}