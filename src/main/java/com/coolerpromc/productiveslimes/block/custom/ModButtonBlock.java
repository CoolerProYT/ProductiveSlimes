package com.coolerpromc.productiveslimes.block.custom;

import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ModButtonBlock extends AbstractButtonBlock {
    public ModButtonBlock(Properties properties) {
        super(false, properties);
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
    protected SoundEvent getSound(boolean b) {
        return SoundEvents.WOODEN_BUTTON_CLICK_ON;
    }
}
