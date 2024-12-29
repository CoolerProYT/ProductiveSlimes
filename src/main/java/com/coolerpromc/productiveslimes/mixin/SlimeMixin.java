package com.coolerpromc.productiveslimes.mixin;

import com.coolerpromc.productiveslimes.worldgen.biome.ModBiomes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
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

import java.util.Random;

@Mixin(Slime.class)
public abstract class SlimeMixin {
    /**
     * @author CoolerProMC
     * @reason Slimes should not deal damage
     */
    @Overwrite
    protected boolean isDealsDamage() {
        return false;
    }

    @Redirect(method = "registerGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/world/entity/ai/goal/Goal;)V", ordinal = 1))
    private void redirectSlimeAttackGoal(GoalSelector instance, int priority, Goal goal) {
        // This effectively skips the addition of the SlimeAttackGoal
        // No operation (NOP), we do nothing here to skip adding the goal
    }

    @Inject(method = "checkSlimeSpawnRules", at = @At("HEAD"), cancellable = true)
    private static void onCheckSpawnRules(EntityType<Slime> slimeType, LevelAccessor level,
                                          MobSpawnType spawnType, BlockPos pos, Random random,
                                          CallbackInfoReturnable<Boolean> cir) {
        assert ModBiomes.SLIMY_LAND.getKey() != null;
        if (level.getBiome(pos).is(ModBiomes.SLIMY_LAND.getKey())) {
            cir.setReturnValue(true);
        }
    }
}
