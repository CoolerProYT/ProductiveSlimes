package com.coolerpromc.productiveslimes.worldgen.biome.surface;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;

import java.util.Random;

public class SlimyLandSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderBaseConfiguration> {
    public SlimyLandSurfaceBuilder(Codec<SurfaceBuilderBaseConfiguration> codec) {
        super(codec);
    }

    @Override
    public void apply(Random random, ChunkAccess chunkAccess, Biome biome, int x, int z, int height,
                      double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel,
                      int minY, long seed, SurfaceBuilderBaseConfiguration config) {

        BlockState topBlock = ModBlocks.SLIMY_GRASS_BLOCK.get().defaultBlockState();
        BlockState underBlock = ModBlocks.SLIMY_DIRT.get().defaultBlockState();
        BlockState underunderBlock = ModBlocks.SLIMY_STONE.get().defaultBlockState();
        BlockState slimyDeepslate = ModBlocks.SLIMY_DEEPSLATE.get().defaultBlockState();

        // Generate bedrock layer
        for(int y = minY; y <= minY + 5; y++) {
            if(random.nextInt(5) <= y - minY) {
                chunkAccess.setBlockState(new BlockPos(x, y, z),
                        Blocks.BEDROCK.defaultBlockState(), false);
            }
        }

        // Get the noise-based height
        int surfaceHeight = height;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(x, surfaceHeight, z);

        // Apply surface blocks from top down
        boolean foundSurface = false;
        for (int y = surfaceHeight; y >= minY; y--) {
            pos.setY(y);
            BlockState currentBlock = chunkAccess.getBlockState(pos);

            if (currentBlock.isAir() || currentBlock == defaultFluid) {
                foundSurface = false;
                continue;
            }

            if (!foundSurface) {
                // First solid block from top - place grass
                chunkAccess.setBlockState(pos, topBlock, false);
                foundSurface = true;
            } else {
                // Calculate depth from surface
                int depthFromSurface = surfaceHeight - y;

                if (depthFromSurface <= 5) {
                    // Dirt layer
                    chunkAccess.setBlockState(pos, underBlock, false);
                } else if (y <= 0) {
                    // Below sea level - all stone
                    chunkAccess.setBlockState(pos, underunderBlock, false);
                } else if (y <= -80) {
                    // Deep underground - deepslate
                    chunkAccess.setBlockState(pos, slimyDeepslate, false);
                } else {
                    // Regular stone layer
                    chunkAccess.setBlockState(pos, underunderBlock, false);
                }
            }
        }
    }
}