package com.coolerpromc.productiveslimes.entity.slime;

import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.mojang.blaze3d.platform.GlStateManager;
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
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

public class DirtSlime extends BaseSlime{
    public DirtSlime(EntityType<? extends Slime> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, 1000);
    }

    @Override
    public void dropResource() {
        ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), new ItemStack(ModItems.DIRT_SLIME_BALL.get(), this.getSize()));
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
                    if(pPlayer.getItemInHand(pHand).getItem() == Items.STONE && pPlayer.getItemInHand(pHand).getCount() > this.getSize()) {
                        super.transformSlime(pPlayer, pHand, this, ModEntities.STONE_SLIME.get().create(this.level()));
                    }

                    if(pPlayer.getItemInHand(pHand).getItem() == Items.SAND && pPlayer.getItemInHand(pHand).getCount() > this.getSize()) {
                        super.transformSlime(pPlayer, pHand, this, ModEntities.SAND_SLIME.get().create(this.level()));
                    }

                    if (pPlayer.getItemInHand(pHand).getItem() == Items.DIRT && this.getSize() < 4 && pPlayer.getItemInHand(pHand).getCount() > this.getSize()) {
                        growthSlime(pPlayer, pHand, this);
                    }
                }
            }
        }

        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    protected ParticleOptions getParticleType() {
        return new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.DIRT));
    }
}
