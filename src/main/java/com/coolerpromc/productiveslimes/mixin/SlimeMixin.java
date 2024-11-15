/*
package com.coolerpromc.productiveslimes.mixin;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.monster.Slime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Slime.class)
public abstract class SlimeMixin {
    */
/**
     * Overrides the removeWhenFarAway() method in the Mob class.
     * <p>
     * This method ensures that the mob does not de-spawn.
     *
     * @author CoolerProYT
     * @reason Prevents mob de-spawning
     *//*

    @Overwrite
    protected boolean isDealsDamage() {
        return false;
    }

    @Redirect(method = "registerGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/world/entity/ai/goal/Goal;)V", ordinal = 1))
    private void redirectSlimeAttackGoal(GoalSelector instance, int priority, Goal goal) {
        // This effectively skips the addition of the SlimeAttackGoal
        // No operation (NOP), we do nothing here to skip adding the goal
    }
}
*/
