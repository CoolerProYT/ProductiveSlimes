package com.coolerpromc.productiveslimes.mixin;

import com.coolerpromc.productiveslimes.worldgen.biome.ModBiomes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.Random;

@Mixin(SlimeEntity.class)
public abstract class SlimeMixin {
    /**
     * @author CoolerProMC
     * @reason Slimes should not deal damage
     */
    @Overwrite
    protected boolean isDealsDamage() {
        return false;
    }

    @Redirect(method = "registerGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/entity/ai/goal/Goal;)V", ordinal = 1))
    private void redirectSlimeAttackGoal(GoalSelector instance, int priority, Goal goal) {
        // This effectively skips the addition of the SlimeAttackGoal
        // No operation (NOP), we do nothing here to skip adding the goal
    }

    @Inject(method = "checkSlimeSpawnRules", at = @At("HEAD"), cancellable = true)
    private static void onCheckSpawnRules(EntityType<SlimeEntity> slimeType, IWorld level, SpawnReason spawnType, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        Optional<RegistryKey<Biome>> biomeKey = level.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getResourceKey(level.getBiome(pos));

        if (biomeKey.isPresent() && biomeKey.get().location().equals(ModBiomes.SLIMY_LAND.get().getRegistryName())){
            cir.setReturnValue(true);
        }
    }
}
