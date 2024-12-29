package com.coolerpromc.productiveslimes.item.custom;

import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import com.coolerpromc.productiveslimes.handler.SlimeData;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public class SlimeItem extends Item {
    public SlimeItem(Properties properties) {
        super(properties);
    }
    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel() instanceof ServerLevel) {
            ItemStack itemStack = context.getItemInHand();
            SlimeData slimeData = SlimeData.fromTag(itemStack.getTag().getCompound("slime_data"));
            assert slimeData != null;
            BaseSlime entity = slimeData.slime().create((ServerLevel) context.getLevel(), null, null, context.getPlayer(), context.getClickedPos(), MobSpawnType.SPAWN_EGG, true, true);
            assert entity != null;
            entity.setSize(slimeData.size(), true);
            context.getLevel().addFreshEntity(entity);
            context.getPlayer().setItemInHand(context.getHand(), ItemStack.EMPTY);
        }
        return InteractionResult.SUCCESS;
    }
    @Override
    public Component getName(ItemStack stack) {
        if (!stack.hasTag() || !stack.getTag().contains("slime_data"))
            return new TextComponent("Invalid Slime Item");

        SlimeData slimeData = SlimeData.fromTag(stack.getTag().getCompound("slime_data"));
        if (slimeData == null)
            return new TranslatableComponent("item.productiveslimes.slime_item");
        return new TranslatableComponent(slimeData.slime().getDescriptionId());
    }
}