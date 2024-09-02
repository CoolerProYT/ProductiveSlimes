package com.coolerpromc.productiveslimes.entity.slime;

import com.coolerpromc.productiveslimes.item.ModItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class CopperSlime extends BaseSlime{
    public CopperSlime(EntityType<? extends Slime> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, 1500);
    }

    @Override
    public void dropResource() {
        ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), new ItemStack(ModItems.COPPER_SLIME_BALL.get(), this.getSize()));
        this.level().addFreshEntity(itemEntity);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 0)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    @Override
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        if(pHand == InteractionHand.MAIN_HAND) {
            if(pPlayer.isCrouching()) {
                if(!level().isClientSide){
                    /*if(pPlayer.getItemInHand(pHand).getItem() == Items.GOLD_BLOCK && pPlayer.getItemInHand(pHand).getCount() > this.getSize()) {
                        transformSlime(pPlayer, pHand);
                    }*/

                    if (pPlayer.getItemInHand(pHand).getItem() == Items.COPPER_BLOCK && this.getSize() < 4 && pPlayer.getItemInHand(pHand).getCount() > this.getSize()) {
                        growthSlime(pPlayer, pHand);
                    }
                }
            }
        }

        return super.mobInteract(pPlayer, pHand);
    }

    protected void transformSlime(Player pPlayer, InteractionHand pHand){
        /*GoldSlime slime = this;
        ItemStack itemStack = pPlayer.getItemInHand(pHand);

        if (!pPlayer.getAbilities().instabuild){
            itemStack.shrink(slime.getSize() + 1);
        }

        GoldSlime goldSlime = ModEntities.GOLD_SLIME.get().create(this.level());
        if (goldSlime != null) {
            goldSlime.moveTo(slime.getX(), slime.getY(), slime.getZ(), slime.getYRot(), slime.getXRot());
            goldSlime.setSize(slime.getSize(), true);
            this.level().addFreshEntity(goldSlime);
        }

        slime.discard();*/
    }

    protected void growthSlime(Player pPlayer, InteractionHand pHand){
        this.setSize(this.getSize() + 1, false);
        this.setHealth(this.getMaxHealth());
        pPlayer.getItemInHand(pHand).shrink(this.getSize() + 1);
    }

    @Override
    protected ParticleOptions getParticleType() {
        return new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(ModItems.COPPER_SLIME_BALL.get()));
    }
}
