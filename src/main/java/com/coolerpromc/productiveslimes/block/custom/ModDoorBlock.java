package com.coolerpromc.productiveslimes.block.custom;

import net.minecraft.block.DoorBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ModDoorBlock extends DoorBlock {
    public ModDoorBlock(Properties properties) {
        super(properties);
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
}
