package com.coolerpromc.productiveslimes.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Slime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MeleeAttackGoal.class)
public abstract class CustomMeleeAttackGoalMixin {
    @Shadow public abstract void stop();

    @Accessor
    public abstract PathfinderMob getMob();

    @Inject(method = "checkAndPerformAttack", at = @At("HEAD"))
    private void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr, CallbackInfo ci) {
        if (pEnemy instanceof Slime) {
            stop();
        }
    }

    @Inject(method = "start", at = @At("HEAD"), cancellable = true)
    private void start(CallbackInfo ci) {
        if (getMob().getTarget() instanceof Slime) {
            ci.cancel();
        }
    }

    @Inject(method = "stop", at = @At("HEAD"))
    private void stop(CallbackInfo ci) {
        if (getMob().getTarget() instanceof Slime) {
            getMob().setTarget(null);
        }
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tick(CallbackInfo ci) {
        if (getMob().getTarget() instanceof Slime) {
            getMob().setTarget(null);
            ci.cancel();
        }
    }
}