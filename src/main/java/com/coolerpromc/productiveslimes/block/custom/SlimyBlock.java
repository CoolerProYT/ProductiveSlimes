package com.coolerpromc.productiveslimes.block.custom;

import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.levelgen.feature.AbstractFlowerFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

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

    @Override
    public void performBonemeal(ServerLevel pLevel, Random pRand, BlockPos pPos, BlockState pState) {
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
                ((BonemealableBlock)var6.getBlock()).performBonemeal(pLevel, pRand, var8, var12);
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

    private static <U extends FeatureConfiguration> BlockState getBlockState(Random pRandom, BlockPos pPos, ConfiguredFeature<U, ?> pFlowerFeature) {
        AbstractFlowerFeature<U> var3 = (AbstractFlowerFeature)pFlowerFeature.feature;
        return var3.getRandomFlower(pRandom, pPos, pFlowerFeature.config());
    }
}
