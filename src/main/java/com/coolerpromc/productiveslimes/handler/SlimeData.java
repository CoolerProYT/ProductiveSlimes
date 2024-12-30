package com.coolerpromc.productiveslimes.handler;

import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import com.coolerpromc.productiveslimes.entity.slime.Slime;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

@SuppressWarnings("unchecked")
public class SlimeData{
    public final int size;
    public final int color;
    public final int cooldown;
    public final ItemStack dropItem;
    public final ItemStack growthItem;
    public final EntityType<BaseSlime> slime;

    public SlimeData(int size, int color, int cooldown, ItemStack dropItem, ItemStack growthItem, EntityType<BaseSlime> slime){
        this.size = size;
        this.color = color;
        this.cooldown = cooldown;
        this.dropItem = dropItem;
        this.growthItem = growthItem;
        this.slime = slime;
    }

    public int size() {
        return size;
    }

    public int color() {
        return color;
    }

    public int cooldown() {
        return cooldown;
    }

    public ItemStack dropItem() {
        return dropItem;
    }

    public ItemStack growthItem() {
        return growthItem;
    }

    public EntityType<BaseSlime> slime() {
        return slime;
    }

    public static SlimeData fromSlime(Slime slime) {
        return new SlimeData(
                slime.getSize(),
                slime.getColor(),
                slime.getCooldown(),
                slime.getItem(),
                slime.getGrowthItem(),
                slime.getEntityType()
        );
    }

    public CompoundNBT toTag(CompoundNBT tag) {
        tag.putInt("size", size);
        tag.putInt("color", color);
        tag.putInt("cooldown", cooldown);
        tag.put("drop", dropItem.save(new CompoundNBT()));
        tag.put("growth_item", growthItem.save(new CompoundNBT()));
        if (slime != null) {
            tag.putString("slime", ForgeRegistries.ENTITIES.getKey(slime).toString());
        }
        return tag;
    }

    public static SlimeData fromTag(CompoundNBT tag) {
        EntityType<BaseSlime> entityType = null;
        if (tag.contains("slime")) {
            entityType = (EntityType<BaseSlime>) ForgeRegistries.ENTITIES.getValue(new ResourceLocation(tag.getString("slime")));
        }
        return new SlimeData(
                tag.getInt("size"),
                tag.getInt("color"),
                tag.getInt("cooldown"),
                ItemStack.of(tag.getCompound("drop")),
                ItemStack.of(tag.getCompound("growth_item")),
                entityType
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SlimeData slimeData = (SlimeData) o;
        return size == slimeData.size && color == slimeData.color && cooldown == slimeData.cooldown && Objects.equals(dropItem, slimeData.dropItem) && Objects.equals(growthItem, slimeData.growthItem) && Objects.equals(slime, slimeData.slime);
    }
    @Override
    public int hashCode() {
        return Objects.hash(size, color, cooldown, dropItem, growthItem, slime);
    }
}