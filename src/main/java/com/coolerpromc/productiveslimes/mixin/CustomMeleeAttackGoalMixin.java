package com.coolerpromc.productiveslimes.mixin;

import com.coolerpromc.productiveslimes.entity.slime.Slime;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MeleeAttackGoal.class)
public abstract class CustomMeleeAttackGoalMixin {
    @Shadow public abstract void stop();

    @Accessor
    public abstract CreatureEntity getMob();

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