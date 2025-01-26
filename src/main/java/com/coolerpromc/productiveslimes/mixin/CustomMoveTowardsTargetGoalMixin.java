package com.coolerpromc.productiveslimes.mixin;

import com.coolerpromc.productiveslimes.Config;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.monster.Slime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MoveTowardsTargetGoal.class)
public abstract class CustomMoveTowardsTargetGoalMixin {
    @Shadow public abstract void stop();
    @Accessor
    public abstract PathfinderMob getMob();
    @Inject(method = "start", at = @At("HEAD"), cancellable = true)
    private void start(CallbackInfo ci) {
        if (getMob().getTarget() instanceof Slime && !Config.CONFIG.ironGolemCanAttackSlime.get()) {
            ci.cancel();
        }
    }
    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
    private void canUse(CallbackInfoReturnable<Boolean> cir) {
        if (getMob().getTarget() instanceof Slime && !Config.CONFIG.ironGolemCanAttackSlime.get()) {
            cir.setReturnValue(false);
        }
    }
    @Inject(method = "canContinueToUse", at = @At("HEAD"), cancellable = true)
    private void canContinueToUse(CallbackInfoReturnable<Boolean> cir) {
        if (getMob().getTarget() instanceof Slime && !Config.CONFIG.ironGolemCanAttackSlime.get()) {
            cir.setReturnValue(false);
        }
    }
}