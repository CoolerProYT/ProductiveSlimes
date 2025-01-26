package com.coolerpromc.productiveslimes.block.custom;

import com.coolerpromc.productiveslimes.util.TranslucentHighlightFix;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;
import java.util.Optional;

public class SlimyBlock extends Block implements BonemealableBlock, TranslucentHighlightFix {
    public SlimyBlock(Properties properties) {
        super(properties);
    }
    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
    @Override
    protected boolean propagatesSkylightDown(BlockState state) {
        return !state.isSolidRender();
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(level, pos, state, entity);
        if (!entity.onGround() || entity.isSpectator() || entity.isVehicle()) {
            return;
        }
        double slowFactor = 0.05;
        entity.setDeltaMovement(
                entity.getDeltaMovement().multiply(slowFactor, 1.0, slowFactor)
        );
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return level.getBlockState(pos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos blockpos = pos.above();
        BlockState blockstate = Blocks.SHORT_GRASS.defaultBlockState();
        Optional<Holder.Reference<PlacedFeature>> optional = level.registryAccess()
                .lookupOrThrow(Registries.PLACED_FEATURE)
                .get(VegetationPlacements.GRASS_BONEMEAL);
        label49:
        for (int i = 0; i < 128; i++) {
            BlockPos blockpos1 = blockpos;
            for (int j = 0; j < i / 16; j++) {
                blockpos1 = blockpos1.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                if (!level.getBlockState(blockpos1.below()).is(this) || level.getBlockState(blockpos1).isCollisionShapeFullBlock(level, blockpos1)) {
                    continue label49;
                }
            }
            BlockState blockstate1 = level.getBlockState(blockpos1);
            if (blockstate1.is(blockstate.getBlock()) && random.nextInt(10) == 0) {
                ((BonemealableBlock)blockstate.getBlock()).performBonemeal(level, random, blockpos1, blockstate1);
            }
            if (blockstate1.isAir()) {
                Holder<PlacedFeature> holder;
                if (random.nextInt(8) == 0) {
                    List<ConfiguredFeature<?, ?>> list = level.getBiome(blockpos1).value().getGenerationSettings().getFlowerFeatures();
                    if (list.isEmpty()) {
                        continue;
                    }
                    holder = ((RandomPatchConfiguration)list.get(0).config()).feature();
                } else {
                    if (!optional.isPresent()) {
                        continue;
                    }
                    holder = optional.get();
                }
                holder.value().place(level, level.getChunkSource().getGenerator(), random, blockpos1);
            }
        }
    }
}