package com.coolerpromc.productiveslimes.entity.slime;

import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.item.ModItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;

public class GoldSlime extends BaseSlime implements IWailaPlugin {
    public GoldSlime(EntityType<? extends Slime> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, 6500);
    }

    @Override
    public void dropResource() {
        ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), new ItemStack(ModItems.GOLD_SLIME_BALL.get(), this.getSize()));
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
                    /*if(pPlayer.getItemInHand(pHand).getItem() == Items.GOLD_BLOCK) {
                        GoldSlime slime = this;
                        ItemStack itemStack = pPlayer.getItemInHand(pHand);

                        if (!pPlayer.getAbilities().instabuild){
                            itemStack.shrink(slime.getSize() + 1);
                        }

                        IronSlime ironSlime = ModEntities.IRON_SLIME.get().create(this.level());
                        if (ironSlime != null) {
                            ironSlime.moveTo(slime.getX(), slime.getY(), slime.getZ(), slime.getYRot(), slime.getXRot());
                            ironSlime.setSize(slime.getSize(), true);
                            this.level().addFreshEntity(ironSlime);
                        }

                        slime.discard();
                    }*/

                    if (pPlayer.getItemInHand(pHand).getItem() == Items.GOLD_BLOCK && this.getSize() < 4 && pPlayer.getItemInHand(pHand).getCount() > this.getSize()) {
                        this.setSize(this.getSize() + 1, false);
                        this.setHealth(this.getMaxHealth());
                        pPlayer.getItemInHand(pHand).shrink(this.getSize() + 1);
                    }
                }
            }
        }

        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    protected ParticleOptions getParticleType() {
        return new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(ModItems.GOLD_SLIME_BALL.get()));
    }
}
