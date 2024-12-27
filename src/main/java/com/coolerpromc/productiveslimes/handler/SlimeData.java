package com.coolerpromc.productiveslimes.handler;

import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import com.coolerpromc.productiveslimes.entity.slime.Slime;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import java.util.Objects;

public record SlimeData(int size, int color, int cooldown, ItemStack dropItem, ItemStack growthItem, EntityType<BaseSlime> slime){
    public static final Codec<SlimeData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("size").forGetter(SlimeData::size),
                    Codec.INT.fieldOf("color").forGetter(SlimeData::color),
                    Codec.INT.fieldOf("cooldown").forGetter(SlimeData::cooldown),
                    ItemStack.CODEC.fieldOf("drop").forGetter(SlimeData::dropItem),
                    ItemStack.CODEC.fieldOf("growth_item").forGetter(SlimeData::growthItem),
                    BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("slime").forGetter(SlimeData::slime)
            ).apply(instance, (size, color, cooldown, dropItem, growthItem, slime) -> new SlimeData(size, color, cooldown, dropItem, growthItem, (EntityType<BaseSlime>) slime))
    );
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