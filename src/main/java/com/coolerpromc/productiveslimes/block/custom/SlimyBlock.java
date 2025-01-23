package com.coolerpromc.productiveslimes.block.custom;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.Random;

public class SlimyBlock extends Block implements IGrowable {
    public SlimyBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader level, BlockPos pos) {
        return !state.isSolidRender(level, pos);
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

    @Override
    public boolean isValidBonemealTarget(IBlockReader blockGetter, BlockPos pPos, BlockState blockState, boolean b) {
        return blockGetter.getBlockState(pPos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(World level, Random random, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerWorld pLevel, Random pRand, BlockPos pPos, BlockState pState) {
        BlockPos var5 = pPos.above();
        BlockState var6 = Blocks.GRASS.defaultBlockState();

        label48:
        for(int var7 = 0; var7 < 128; ++var7) {
            BlockPos var8 = var5;

            for(int var9 = 0; var9 < var7 / 16; ++var9) {
                var8 = var8.offset(pRand.nextInt(3) - 1, (pRand.nextInt(3) - 1) * pRand.nextInt(3) / 2, pRand.nextInt(3) - 1);
                if (!pLevel.getBlockState(var8.below()).is(this) || pLevel.getBlockState(var8).isCollisionShapeFullBlock(pLevel, var8)) {
                    continue label48;
                }
            }

            BlockState var12 = pLevel.getBlockState(var8);
            if (var12.is(var6.getBlock()) && pRand.nextInt(10) == 0) {
                ((IGrowable)var6.getBlock()).performBonemeal(pLevel, pRand, var8, var12);
            }

            if (var12.isAir()) {
                BlockState var10;
                if (pRand.nextInt(8) == 0) {
                    List<ConfiguredFeature<?, ?>> var11 = pLevel.getBiome(var8).getGenerationSettings().getFlowerFeatures();
                    if (var11.isEmpty()) {
                        continue;
                    }

                    var10 = getBlockState(pRand, var8, (ConfiguredFeature)var11.get(0));
                } else {
                    var10 = var6;
                }

                if (var10.canSurvive(pLevel, var8)) {
                    pLevel.setBlock(var8, var10, 3);
                }
            }
        }
    }

    private static <U extends IFeatureConfig> BlockState getBlockState(Random pRandom, BlockPos pPos, ConfiguredFeature<U, ?> pFlowerFeature) {
        FlowersFeature<U> var3 = (FlowersFeature) pFlowerFeature.feature;
        return var3.getRandomFlower(pRandom, pPos, pFlowerFeature.config());
    }
}
