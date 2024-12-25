package com.coolerpromc.productiveslimes.item.custom;

import com.coolerpromc.productiveslimes.datacomponent.ModDataComponents;
import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import com.coolerpromc.productiveslimes.handler.SlimeData;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
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
            SlimeData slimeData = itemStack.get(ModDataComponents.SLIME_DATA.get());
            assert slimeData != null;
            BaseSlime entity = slimeData.slime().create((ServerLevel) context.getLevel(), null, context.getClickedPos().above(), EntitySpawnReason.MOB_SUMMONED, true, false);
            assert entity != null;
            entity.setSize(slimeData.size(), true);
            context.getLevel().addFreshEntity(entity);
            context.getPlayer().setItemInHand(context.getHand(), ItemStack.EMPTY);
        }
        return InteractionResult.SUCCESS;
    }
    @Override
    public Component getName(ItemStack stack) {
        SlimeData slimeData = stack.getOrDefault(ModDataComponents.SLIME_DATA.get(), null);
        if (slimeData == null)
            return Component.translatable("item.productiveslimes.slime_item");
        return slimeData.slime().getDescription();
    }
}