package com.coolerpromc.productiveslimes.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;
import java.util.Random;

public class SlimyBlock extends Block implements BonemealableBlock {
    public SlimyBlock(Properties properties) {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return !state.isSolidRender(level, pos);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(level, pos, state, entity);

        if (!entity.isOnGround() || entity.isSpectator() || entity.isVehicle()) {
            return;
        }

        double slowFactor = 0.05;
        entity.setDeltaMovement(
                entity.getDeltaMovement().multiply(slowFactor, 1.0, slowFactor)
        );
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter blockGetter, BlockPos pPos, BlockState blockState, boolean b) {
        return blockGetter.getBlockState(pPos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level level, Random random, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    public void performBonemeal(ServerLevel pLevel, Random pRand, BlockPos pPos, BlockState pState) {
        BlockPos $$4 = pPos.above();
        BlockState $$5 = Blocks.GRASS.defaultBlockState();

        label46:
        for(int $$6 = 0; $$6 < 128; ++$$6) {
            BlockPos $$7 = $$4;

            for(int $$8 = 0; $$8 < $$6 / 16; ++$$8) {
                $$7 = $$7.offset(pRand.nextInt(3) - 1, (pRand.nextInt(3) - 1) * pRand.nextInt(3) / 2, pRand.nextInt(3) - 1);
                if (!pLevel.getBlockState($$7.below()).is(this) || pLevel.getBlockState($$7).isCollisionShapeFullBlock(pLevel, $$7)) {
                    continue label46;
                }
            }

            BlockState $$9 = pLevel.getBlockState($$7);
            if ($$9.is($$5.getBlock()) && pRand.nextInt(10) == 0) {
                ((BonemealableBlock)$$5.getBlock()).performBonemeal(pLevel, pRand, $$7, $$9);
            }

            if ($$9.isAir()) {
                Holder $$12;
                if (pRand.nextInt(8) == 0) {
                    List<ConfiguredFeature<?, ?>> $$10 = ((Biome)pLevel.getBiome($$7).value()).getGenerationSettings().getFlowerFeatures();
                    if ($$10.isEmpty()) {
                        continue;
                    }

                    $$12 = ((RandomPatchConfiguration)((ConfiguredFeature)$$10.get(0)).config()).feature();
                } else {
                    $$12 = VegetationPlacements.GRASS_BONEMEAL;
                }

                ((PlacedFeature)$$12.value()).place(pLevel, pLevel.getChunkSource().getGenerator(), pRand, $$7);
            }
        }

    }
}
