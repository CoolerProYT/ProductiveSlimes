package com.coolerpromc.productiveslimes.mixin;

import com.coolerpromc.productiveslimes.Config;
import com.coolerpromc.productiveslimes.worldgen.biome.ModBiomes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slime.class)
public abstract class SlimeMixin {
    /**
     * @author CoolerProMC
     * @reason Slimes should not deal damage
     */
    @Overwrite
    protected boolean isDealsDamage() {
        return Config.CONFIG.vanillaSlimeCanAttackPlayer.get();
    }

    @Redirect(method = "registerGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/world/entity/ai/goal/Goal;)V", ordinal = 1))
    private void redirectSlimeAttackGoal(GoalSelector instance, int priority, Goal goal) {
        if (Config.CONFIG.vanillaSlimeCanAttackPlayer.get()) {
            instance.addGoal(priority, goal);
        }
    }

    @Inject(method = "checkSlimeSpawnRules", at = @At("HEAD"), cancellable = true)
    private static void onCheckSpawnRules(EntityType<Slime> slimeType, LevelAccessor level, EntitySpawnReason spawnType, BlockPos pos, RandomSource random, CallbackInfoReturnable<Boolean> cir) {
        if (level.getBiome(pos).is(ModBiomes.SLIMY_LAND)) {
            cir.setReturnValue(true);
        }
    }
}