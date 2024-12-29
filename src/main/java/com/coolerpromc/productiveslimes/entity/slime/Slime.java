package com.coolerpromc.productiveslimes.entity.slime;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.registries.RegistryObject;

public class Slime extends BaseSlime {
    private final RegistryObject<Item> item;
    private final ItemLike growthItem;
    private final int color;
    private final int cooldown;
    private final EntityType<BaseSlime> entityType;

    public Slime(EntityType<BaseSlime> entityType, Level level, int cooldown, int color, RegistryObject<Item> item, ItemLike growthItem) {
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
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        if(pHand == InteractionHand.MAIN_HAND) {
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
    public void remove(Entity.RemovalReason pReason) {
        super.remove(pReason);
        this.setRemoved(pReason);
        if (pReason == Entity.RemovalReason.KILLED) {
            this.gameEvent(GameEvent.ENTITY_KILLED);

            if(this.getSize() == 1){
                this.dropResource();
            }
        }
    }

    @Override
    protected ParticleOptions getParticleType() {
        return new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(this.growthItem));
    }
}
