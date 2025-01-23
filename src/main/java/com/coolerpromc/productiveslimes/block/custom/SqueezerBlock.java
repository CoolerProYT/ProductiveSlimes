package com.coolerpromc.productiveslimes.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class SqueezerBlock extends Block {
    public SqueezerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canSurvive(BlockState p_196260_1_, IWorldReader p_196260_2_, BlockPos p_196260_3_) {
        return false;
    }
}