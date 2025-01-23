package com.coolerpromc.productiveslimes.entity.slime;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

public class Slime extends BaseSlime {
    private final RegistryObject<Item> item;
    private final Item growthItem;
    private final int color;
    private final int cooldown;
    private final EntityType<BaseSlime> entityType;

    public Slime(EntityType<BaseSlime> entityType, World level, int cooldown, int color, RegistryObject<Item> item, Item growthItem) {
        super(entityType, level, cooldown, growthItem);
        this.item = item;
        this.growthItem = growthItem;
        this.color = color;
        this.cooldown = cooldown;
        this.entityType = entityType;
    }

    public ItemStack getItem() {
        return item.get().asItem().getDefaultInstance();
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
        ItemEntity itemEntity = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(this.item.get(), this.getSize()));
        this.level.addFreshEntity(itemEntity);
    }

    @Override
    protected ActionResultType mobInteract(PlayerEntity pPlayer, Hand pHand) {
        if(pHand == Hand.MAIN_HAND) {
            if(pPlayer.isCrouching()) {
                if(!level.isClientSide){
                    if (pPlayer.getItemInHand(pHand).getItem() == growthItem && this.getSize() < 4 && pPlayer.getItemInHand(pHand).getCount() > this.getSize()) {
                        super.growthSlime(pPlayer, pHand, this);
                    }
                }
            }
        }

        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    public void remove(boolean pReason) {
        super.remove(pReason);
        if (this.isDeadOrDying()) {
            if(this.getSize() == 1){
                this.dropResource();
            }
        }
    }

    @Override
    protected IParticleData getParticleType() {
        return new ItemParticleData(ParticleTypes.ITEM, new ItemStack(this.growthItem));
    }
}
