package com.coolerpromc.productiveslimes.entity.slime;

import com.coolerpromc.productiveslimes.entity.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public abstract class BaseSlime extends Slime {
    private static final EntityDataAccessor<ItemStack> RESOURCE =
            SynchedEntityData.defineId(BaseSlime.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Integer> ID_SIZE =
            SynchedEntityData.defineId(BaseSlime.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> GROWTH_COUNTER =
            SynchedEntityData.defineId(BaseSlime.class, EntityDataSerializers.INT);

    public static int growthTime = 6000;
    public BaseSlime(EntityType<? extends Slime> entityType, Level level, int cooldown) {
        super(entityType, level);
        this.moveControl = new BaseSlime.SlimeMoveControl(this);
        growthTime = cooldown;
    }

    @Override
    public Component getName() {
        return super.getName();
    }

    @Nullable
    @Override
    public Component getCustomName() {
        return super.getCustomName();
    }

    private void changeResourceDEBUG(ItemStack stack) {
        this.setResource(stack);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new BaseSlime.SlimeFloatGoal(this));
        this.goalSelector.addGoal(3, new BaseSlime.SlimeRandomDirectionGoal(this));
        this.goalSelector.addGoal(5, new BaseSlime.SlimeKeepOnJumpingGoal(this));
    }

    public int getNextDropTime(){
        return growthTime - this.entityData.get(GROWTH_COUNTER);
    }

    @Override
    protected boolean isDealsDamage() {
        return false;
    }

    public static EntityDataAccessor<Integer> getGrowthCounter() {
        return GROWTH_COUNTER;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("growth_counter", this.entityData.get(GROWTH_COUNTER));
        pCompound.putInt("size", this.entityData.get(ID_SIZE));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.entityData.set(ID_SIZE, pCompound.getInt("size"));
    }

    public void setResource(ItemStack stack) {
        this.entityData.set(RESOURCE, stack);
        resetGrowthCount();
    }

    public ItemStack getResourceItem() {
        if(!this.entityData.get(RESOURCE).isEmpty()) {
            return this.entityData.get(RESOURCE);
        }

        return ItemStack.EMPTY;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);

        pBuilder.define(RESOURCE, ItemStack.EMPTY);
        pBuilder.define(ID_SIZE, 1);
        pBuilder.define(GROWTH_COUNTER, 0);
    }

    public abstract void dropResource();

    @Override
    public void tick() {
        super.tick();
        if(!isDeadOrDying()) {
            countGrowth();

            if(readyForNewResource()) {
                dropResource();
                resetGrowthCount();
            }
        }
    }

    private void resetGrowthCount() {
        this.entityData.set(GROWTH_COUNTER, 0);
    }

    private void recalculateSize() {
        setSize(0, true);
    }

    private boolean readyForNewResource() {
        return this.entityData.get(GROWTH_COUNTER) >= growthTime;
    }

    private void countGrowth() {
        this.entityData.set(GROWTH_COUNTER, this.entityData.get(GROWTH_COUNTER) + 1);
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    public int getSize() {
        return this.entityData.get(ID_SIZE);
    }

    @Override
    public void setSize(int pSize, boolean pResetHealth) {
        // Setting the size based on the number of resources
        // int newSize = this.entityData.get(RESOURCE).getCount() * 2 - 1; // INSANE GROWTH (64 -> Size 127)
        int i = Mth.clamp(pSize, 1, 127);
        this.entityData.set(ID_SIZE, i);
        this.reapplyPosition();
        this.refreshDimensions();
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((i * i));
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((0.2F + 0.1F * (float)i));
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(i);

        this.xpReward = i;
    }

    @Override
    public void remove(Entity.RemovalReason pReason) {
        this.setRemoved(pReason);
        if (pReason == Entity.RemovalReason.KILLED) {
            this.gameEvent(GameEvent.ENTITY_DIE);

            this.spawnAtLocation(this.entityData.get(RESOURCE));
        }
    }

    float getSoundPitch() {
        float f = this.isTiny() ? 1.4F : 0.8F;
        return ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * f;
    }

    @Override
    public void refreshDimensions() {
        double width = 0.6F * (float)this.getSize();
        double height = 0.8F * (float)this.getSize();
        this.setBoundingBox(new AABB(-width / 2.0D, 0.0D, -width / 2.0D, width / 2.0D, height, width / 2.0D));
    }

    public void transformSlime(Player pPlayer, InteractionHand pHand, BaseSlime originalSlime, BaseSlime newSlime){
        ItemStack itemStack = pPlayer.getItemInHand(pHand);

        if (!pPlayer.getAbilities().instabuild){
            itemStack.shrink(originalSlime.getSize() + 1);
        }

        if (newSlime != null) {
            newSlime.moveTo(originalSlime.getX(), originalSlime.getY(), originalSlime.getZ(), originalSlime.getYRot(), originalSlime.getXRot());
            newSlime.setSize(originalSlime.getSize(), true);
            this.level().addFreshEntity(newSlime);
        }

        originalSlime.discard();
    }

    public void growthSlime(Player pPlayer, InteractionHand pHand, BaseSlime slime){
        slime.setSize(slime.getSize() + 1, false);
        slime.setHealth(slime.getMaxHealth());
        pPlayer.getItemInHand(pHand).shrink(slime.getSize() + 1);
    }

    static class SlimeFloatGoal extends Goal {
        private final BaseSlime slime;

        public SlimeFloatGoal(BaseSlime p_33655_) {
            this.slime = p_33655_;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
            p_33655_.getNavigation().setCanFloat(true);
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return (this.slime.isInWater() || this.slime.isInLava()) && this.slime.getMoveControl() instanceof BaseSlime.SlimeMoveControl;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (this.slime.getRandom().nextFloat() < 0.8F) {
                this.slime.getJumpControl().jump();
            }

            ((BaseSlime.SlimeMoveControl)this.slime.getMoveControl()).setWantedMovement(1.2D);
        }
    }

    static class SlimeKeepOnJumpingGoal extends Goal {
        private final BaseSlime slime;

        public SlimeKeepOnJumpingGoal(BaseSlime p_33660_) {
            this.slime = p_33660_;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return !this.slime.isPassenger();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            ((BaseSlime.SlimeMoveControl)this.slime.getMoveControl()).setWantedMovement(1.0D);
        }
    }

    static class SlimeMoveControl extends MoveControl {
        private float yRot;
        private int jumpDelay;
        private final BaseSlime slime;
        private boolean isAggressive;

        public SlimeMoveControl(BaseSlime p_33668_) {
            super(p_33668_);
            this.slime = p_33668_;
            this.yRot = 180.0F * p_33668_.getYRot() / (float)Math.PI;
        }

        public void setDirection(float pYRot, boolean pAggressive) {
            this.yRot = pYRot;
            this.isAggressive = pAggressive;
        }

        public void setWantedMovement(double pSpeed) {
            this.speedModifier = pSpeed;
            this.operation = MoveControl.Operation.MOVE_TO;
        }

        public void tick() {
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRot, 90.0F));
            this.mob.yHeadRot = this.mob.getYRot();
            this.mob.yBodyRot = this.mob.getYRot();
            if (this.operation != MoveControl.Operation.MOVE_TO) {
                this.mob.setZza(0.0F);
            } else {
                this.operation = MoveControl.Operation.WAIT;
                if (this.mob.onGround()) {
                    this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                    if (this.jumpDelay-- <= 0) {
                        this.jumpDelay = this.slime.getJumpDelay();
                        if (this.isAggressive) {
                            this.jumpDelay /= 3;
                        }

                        this.slime.getJumpControl().jump();
                        if (this.slime.doPlayJumpSound()) {
                            this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(), this.slime.getSoundPitch());
                        }
                    } else {
                        this.slime.xxa = 0.0F;
                        this.slime.zza = 0.0F;
                        this.mob.setSpeed(0.0F);
                    }
                } else {
                    this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                }

            }
        }
    }

    static class SlimeRandomDirectionGoal extends Goal {
        private final BaseSlime slime;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public SlimeRandomDirectionGoal(BaseSlime p_33679_) {
            this.slime = p_33679_;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return this.slime.getTarget() == null && (this.slime.onGround() || this.slime.isInWater() || this.slime.isInLava() ||
                    this.slime.hasEffect(MobEffects.LEVITATION)) && this.slime.getMoveControl() instanceof BaseSlime.SlimeMoveControl;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (--this.nextRandomizeTime <= 0) {
                this.nextRandomizeTime = this.adjustedTickDelay(40 + this.slime.getRandom().nextInt(60));
                this.chosenDegrees = (float)this.slime.getRandom().nextInt(360);
            }

            ((BaseSlime.SlimeMoveControl)this.slime.getMoveControl()).setDirection(this.chosenDegrees, false);
        }
    }
}
