package com.coolerpromc.productiveslimes.worldgen.biome.surface;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;

public class SlimyLandSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
    public SlimyLandSurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
    }

    @Override
    public void apply(Random random, IChunk chunkAccess, Biome biome, int x, int z, int height,
                      double noise, BlockState defaultBlock, BlockState defaultFluid,
                      int minY, long seed, SurfaceBuilderConfig config) {

        BlockState topBlock = ModBlocks.SLIMY_GRASS_BLOCK.get().defaultBlockState();
        BlockState underBlock = ModBlocks.SLIMY_DIRT.get().defaultBlockState();
        BlockState underunderBlock = ModBlocks.SLIMY_STONE.get().defaultBlockState();

        // Generate bedrock layer
        for(int y = minY; y <= minY + 5; y++) {
            if(random.nextInt(5) <= y - minY) {
                chunkAccess.setBlockState(new BlockPos(x, y, z),
                        Blocks.BEDROCK.defaultBlockState(), false);
            }
        }

        // Get the noise-based height
        int surfaceHeight = height;
        BlockPos.Mutable pos = new BlockPos.Mutable(x, surfaceHeight, z);

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
                } else {
                    // Regular stone layer
                    chunkAccess.setBlockState(pos, underunderBlock, false);
                }
            }
        }
    }
}