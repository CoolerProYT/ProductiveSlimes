package com.coolerpromc.productiveslimes.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.Vec3;

public class SlimeBlock extends HalfTransparentBlock {
    public final int color;

    @Override
    public MapCodec<? extends SlimeBlock> codec() {
        return simpleCodec(SlimeBlock::new);
    }

    public SlimeBlock(Properties p_56402_) {
        super(p_56402_);
        this.color = 0x7F7F7F;
    }

    public SlimeBlock(MapColor mapColor, int color) {
        super(BlockBehaviour.Properties.of().mapColor(mapColor).friction(0.8F).sound(SoundType.SLIME_BLOCK).noOcclusion());
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    @Override
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
        if (pEntity.isSuppressingBounce()) {
            super.fallOn(pLevel, pState, pPos, pEntity, pFallDistance);
        } else {
            pEntity.causeFallDamage(pFallDistance, 0.0F, pLevel.damageSources().fall());
        }
    }

    /**
     * Called when an Entity lands on this Block.
     * This method is responsible for doing any modification on the motion of the entity that should result from the landing.
     */
    @Override
    public void updateEntityAfterFallOn(BlockGetter pLevel, Entity pEntity) {
        if (pEntity.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(pLevel, pEntity);
        } else {
            this.bounceUp(pEntity);
        }
    }

    private void bounceUp(Entity pEntity) {
        Vec3 vec3 = pEntity.getDeltaMovement();
        if (vec3.y < 0.0) {
            double d0 = pEntity instanceof LivingEntity ? 1.0 : 0.8;
            pEntity.setDeltaMovement(vec3.x, -vec3.y * d0, vec3.z);
        }
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        double d0 = Math.abs(pEntity.getDeltaMovement().y);
        if (d0 < 0.1 && !pEntity.isSteppingCarefully()) {
            double d1 = 0.4 + d0 * 0.2;
            pEntity.setDeltaMovement(pEntity.getDeltaMovement().multiply(d1, 1.0, d1));
        }

        super.stepOn(pLevel, pPos, pState, pEntity);
    }
}
