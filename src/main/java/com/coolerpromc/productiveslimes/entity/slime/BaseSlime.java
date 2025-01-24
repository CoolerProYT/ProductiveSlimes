package com.coolerpromc.productiveslimes.entity.slime;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.EnumSet;

public abstract class BaseSlime extends SlimeEntity {
    private static final DataParameter<ItemStack> RESOURCE =
            EntityDataManager.defineId(BaseSlime.class, DataSerializers.ITEM_STACK);
    private static final DataParameter<Integer> ID_SIZE =
            EntityDataManager.defineId(BaseSlime.class, DataSerializers.INT);
    private static final DataParameter<Integer> GROWTH_COUNTER =
            EntityDataManager.defineId(BaseSlime.class, DataSerializers.INT);

    public final int growthTime;
    public final Item growthItem;

    public BaseSlime(EntityType<BaseSlime> entityType, World level, int cooldown, Item growthItem) {
        super(entityType, level);
        this.moveControl = new BaseSlime.SlimeMoveControl(this);
        growthTime = cooldown;
        this.growthItem = growthItem.asItem();
        this.goalSelector.addGoal(1, new BaseSlime.SlimeFollowGoal(this, this.growthItem));
    }

    @Override
    public ITextComponent getName() {
        return super.getName();
    }

    @Nullable
    @Override
    public ITextComponent getCustomName() {
        return super.getCustomName();
    }

    private void changeResourceDEBUG(ItemStack stack) {
        this.setResource(stack);
    }

    @Override
    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new BaseSlime.SlimeFloatGoal(this));
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

    public static DataParameter<Integer> getGrowthCounter() {
        return GROWTH_COUNTER;
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("growth_counter", this.entityData.get(GROWTH_COUNTER));
        pCompound.putInt("size", this.entityData.get(ID_SIZE));
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.entityData.set(ID_SIZE, pCompound.getInt("size"));
        this.entityData.set(GROWTH_COUNTER, pCompound.getInt("growth_counter"));
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
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(RESOURCE, ItemStack.EMPTY);
        this.entityData.define(ID_SIZE, 1);
        this.entityData.define(GROWTH_COUNTER, 0);
    }

    @Override
    public boolean save(CompoundNBT pCompound) {
        pCompound.putInt("size", this.getSize());
        pCompound.putInt("growth_counter", this.entityData.get(GROWTH_COUNTER));
        pCompound.put("resource", this.entityData.get(RESOURCE).save(new CompoundNBT()));

        return super.save(pCompound);
    }

    @Override
    public void load(CompoundNBT pCompound) {
        super.load(pCompound);

        if (pCompound.contains("size", 99)) {
            this.setSize(pCompound.getInt("size"), false);
        }

        if (pCompound.contains("growth_counter", 99)) {
            this.entityData.set(GROWTH_COUNTER, pCompound.getInt("growth_counter"));
        }

        if (pCompound.contains("resource", 10)) {
            this.setResource(ItemStack.of(pCompound.getCompound("resource")));
        }
    }

    @Override
    public Vector3d getDismountLocationForPassenger(LivingEntity pPassenger) {
        return new Vector3d(0.0, (double)this.level.getHeight() - 0.015625 * (double)this.getSize(), 0.0);
    }

    @Override
    public void onSyncedDataUpdated(DataParameter<?> key) {
        if (ID_SIZE.equals(key)) {
            this.refreshDimensions();
            this.setYHeadRot(this.yHeadRot);
            this.yBodyRot = this.yHeadRot;
            if (this.isInWater() && this.random.nextInt(20) == 0) {
                this.doWaterSplashEffect();
            }
        }

        super.onSyncedDataUpdated(key);
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
        int i = MathHelper.clamp(pSize, 1, 127);
        this.entityData.set(ID_SIZE, i);
        this.reapplyPosition();
        this.refreshDimensions();
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((i * i));
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((0.2F + 0.1F * (float)i));
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(i);

        this.xpReward = i;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MonsterEntity.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 0)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    float getSoundPitch() {
        float f = this.isTiny() ? 1.4F : 0.8F;
        return ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * f;
    }

    @Override
    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.refreshDimensions();
        this.setBoundingBox(new AxisAlignedBB(this.getX(), this.getY(), this.getZ(), this.getX() + (double)this.getBbWidth(), this.getY() + (double)this.getBbHeight(), this.getZ() + (double)this.getBbWidth()));
        this.setPos(d0, d1, d2);
    }

    @Override
    public EntitySize getDimensions(Pose pPose) {
        return EntitySize.scalable((float) (0.5 * this.getSize()), (float) (0.5 * this.getSize()));
    }

    public void growthSlime(PlayerEntity pPlayer, Hand pHand, BaseSlime slime){
        slime.setSize(slime.getSize() + 1, false);
        slime.setHealth(slime.getMaxHealth());
        slime.setPos(slime.getX(), slime.getY() + 1, slime.getZ());
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

    static class SlimeMoveControl extends MovementController {
        private float yRot;
        private int jumpDelay;
        private final BaseSlime slime;
        private boolean isAggressive;

        public SlimeMoveControl(BaseSlime p_33668_) {
            super(p_33668_);
            this.slime = p_33668_;
            this.yRot = 180.0F * p_33668_.yRot / (float)Math.PI;
        }

        public void setDirection(float pYRot, boolean pAggressive) {
            this.yRot = pYRot;
            this.isAggressive = pAggressive;
        }

        public void setWantedMovement(double pSpeed) {
            this.speedModifier = pSpeed;
            this.operation = MovementController.Action.MOVE_TO;
        }

        public void tick() {
            this.mob.yRot = this.rotlerp(this.mob.yRot, this.yRot, 90.0F);
            this.mob.yHeadRot = this.mob.yRot;
            this.mob.yBodyRot = this.mob.yRot;
            if (this.operation != MovementController.Action.MOVE_TO) {
                this.mob.setZza(0.0F);
            } else {
                this.operation = MovementController.Action.WAIT;
                if (this.mob.isOnGround()) {
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
            return this.slime.getTarget() == null && (this.slime.onGround || this.slime.isInWater() || this.slime.isInLava() || this.slime.hasEffect(Effects.LEVITATION)) && this.slime.getMoveControl() instanceof BaseSlime.SlimeMoveControl;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (--this.nextRandomizeTime <= 0) {
                this.nextRandomizeTime = 40 + this.slime.getRandom().nextInt(60);
                this.chosenDegrees = (float)this.slime.getRandom().nextInt(360);
            }

            ((BaseSlime.SlimeMoveControl)this.slime.getMoveControl()).setDirection(this.chosenDegrees, false);
        }
    }

    static class SlimeFollowGoal extends Goal {
        private final SlimeEntity slime;
        private int growTiredTimer;
        private final Item targetItem; // The item to check for

        public SlimeFollowGoal(SlimeEntity slime, Item targetItem) {
            this.slime = slime;
            this.targetItem = targetItem;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        private boolean isPlayerHoldingTargetItem(PlayerEntity player) {
            return player.getMainHandItem().getItem().equals(targetItem) || player.getOffhandItem().getItem().equals(targetItem);
        }

        private boolean isInRange(PlayerEntity player) {
            return this.slime.distanceTo(player) <= 8.0F;
        }

        private PlayerEntity findNearestPlayerWithItem() {
            return this.slime.level.getNearestPlayer(
                    EntityPredicate.DEFAULT.allowNonAttackable().selector(livingEntity -> {
                        if (livingEntity instanceof PlayerEntity) {
                            PlayerEntity player = (PlayerEntity) livingEntity;
                            return isPlayerHoldingTargetItem(player) && this.slime.getSize() < 4 && isInRange(player);
                        }
                        return false;
                    }),
                    this.slime.getX(),
                    this.slime.getY(),
                    this.slime.getZ()
            );
        }

        @Override
        public boolean canUse() {
            PlayerEntity player = findNearestPlayerWithItem();
            if (player == null) {
                return false;
            }
            this.slime.setTarget(player);
            return this.slime.getMoveControl() instanceof BaseSlime.SlimeMoveControl;
        }

        @Override
        public void start() {
            this.growTiredTimer = 300;
            super.start();
        }

        @Override
        public boolean canContinueToUse() {
            PlayerEntity player = findNearestPlayerWithItem();
            if (player == null) {
                return false;
            }
            this.slime.setTarget(player);
            return --this.growTiredTimer > 0;
        }

        @Override
        public void tick() {
            PlayerEntity player = findNearestPlayerWithItem();
            if (player != null) {
                this.slime.lookAt(player, 10.0F, 10.0F);
            }

            if (this.slime.getMoveControl() instanceof BaseSlime.SlimeMoveControl) {
                BaseSlime.SlimeMoveControl slimeMoveControl = (BaseSlime.SlimeMoveControl) this.slime.getMoveControl();
                slimeMoveControl.setDirection(this.slime.getYHeadRot(), false);
            }
        }
    }
}