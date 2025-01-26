package com.coolerpromc.productiveslimes.entity.slime;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;

public class Slime extends BaseSlime {
    private final ItemLike item;
    private final ItemLike growthItem;
    private final int color;
    private final int cooldown;
    private final EntityType<BaseSlime> entityType;

    public Slime(EntityType<BaseSlime> entityType, Level level, int cooldown, int color, ItemLike item, ItemLike growthItem) {
        super(entityType, level, cooldown, growthItem);
        this.item = item;
        this.growthItem = growthItem;
        this.color = color;
        this.cooldown = cooldown;
        this.entityType = entityType;
    }

    public ItemStack getItem() {
        return item.asItem().getDefaultInstance();
    }
    public ItemStack getGrowthItem() {
        return growthItem.asItem().getDefaultInstance();
    }
    public EntityType<BaseSlime> getEntityType() {
        return entityType;
    }
    public int getColor() {
        return color;
    }
    public int getCooldown() {
        return cooldown;
    }

    @Override
    public void dropResource() {
        ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), new ItemStack(this.item, this.getSize()));
        this.level().addFreshEntity(itemEntity);
    }

    @Override
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        if(pHand == InteractionHand.MAIN_HAND) {
            if(pPlayer.isCrouching()) {
                if(!level().isClientSide){
                    if (pPlayer.getItemInHand(pHand).getItem() == growthItem && this.getSize() < 4 && pPlayer.getItemInHand(pHand).getCount() > this.getSize()) {
                        super.growthSlime(pPlayer, pHand, this);
                    }
                }
            }
        }

        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    public void remove(Entity.RemovalReason pReason) {
        super.remove(pReason);
        this.setRemoved(pReason);
        if (pReason == Entity.RemovalReason.KILLED) {
            this.gameEvent(GameEvent.ENTITY_DIE);

            if(this.getSize() == 1){
                this.dropResource();
            }
        }
    }

    @Override
    protected ParticleOptions getParticleType() {
        return new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(this.growthItem));
    }

    public static boolean checkMobSpawnRules(
            EntityType<? extends Mob> type, LevelAccessor level, EntitySpawnReason spawnType, BlockPos pos, RandomSource random
    ) {
        boolean flag = EntitySpawnReason.ignoresLightRequirements(spawnType) || isBrightEnoughToSpawn(level, pos);
        return level.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && flag;
    }
    protected static boolean isBrightEnoughToSpawn(BlockAndTintGetter level, BlockPos pos) {
        return level.getRawBrightness(pos, 0) > 8;
    }
}
