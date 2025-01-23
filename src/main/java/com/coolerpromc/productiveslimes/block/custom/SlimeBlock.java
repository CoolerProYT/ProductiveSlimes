package com.coolerpromc.productiveslimes.block.custom;

import com.coolerpromc.productiveslimes.util.TranslucentHighlightFix;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class SlimeBlock extends BreakableBlock implements TranslucentHighlightFix {
    public final int color;

    public SlimeBlock(int color) {
        super(AbstractBlock.Properties.copy(Blocks.SLIME_BLOCK).friction(0.8F).sound(SoundType.SLIME_BLOCK).noOcclusion());
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    @Override
    public void fallOn(World pLevel, BlockPos pPos, Entity pEntity, float pFallDistance) {
        if (pEntity.isSuppressingBounce()) {
            super.fallOn(pLevel, pPos, pEntity, pFallDistance);
        } else {
            pEntity.causeFallDamage(pFallDistance, 0.0F);
        }
    }

    /**
     * Called when an Entity lands on this Block.
     * This method is responsible for doing any modification on the motion of the entity that should result from the landing.
     */
    @Override
    public void updateEntityAfterFallOn(IBlockReader pLevel, Entity pEntity) {
        if (pEntity.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(pLevel, pEntity);
        } else {
            this.bounceUp(pEntity);
        }
    }

    private void bounceUp(Entity pEntity) {
        Vector3d vec3 = pEntity.getDeltaMovement();
        if (vec3.y < 0.0) {
            double d0 = pEntity instanceof LivingEntity ? 1.0 : 0.8;
            pEntity.setDeltaMovement(vec3.x, -vec3.y * d0, vec3.z);
        }
    }

    @Override
    public void stepOn(World pLevel, BlockPos pPos, Entity pEntity) {
        double d0 = Math.abs(pEntity.getDeltaMovement().y);
        if (d0 < 0.1 && !pEntity.isSteppingCarefully()) {
            double d1 = 0.4 + d0 * 0.2;
            pEntity.setDeltaMovement(pEntity.getDeltaMovement().multiply(d1, 1.0, d1));
        }

        super.stepOn(pLevel, pPos, pEntity);
    }
}
