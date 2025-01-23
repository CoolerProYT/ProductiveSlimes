package com.coolerpromc.productiveslimes.item.custom;

import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import com.coolerpromc.productiveslimes.handler.SlimeData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

public class SlimeItem extends Item {
    public SlimeItem(Properties properties) {
        super(properties);
    }
    @Override
    public ActionResultType useOn(ItemUseContext context) {
        if (context.getLevel() instanceof ServerWorld) {
            ItemStack itemStack = context.getItemInHand();
            SlimeData slimeData = SlimeData.fromTag(itemStack.getTag().getCompound("slime_data"));
            assert slimeData != null;
            BaseSlime entity = slimeData.slime().create((ServerWorld) context.getLevel(), null, null, context.getPlayer(), context.getClickedPos(), SpawnReason.SPAWN_EGG, true, true);
            assert entity != null;
            entity.setSize(slimeData.size(), true);
            context.getLevel().addFreshEntity(entity);
            context.getPlayer().setItemInHand(context.getHand(), ItemStack.EMPTY);
        }
        return ActionResultType.SUCCESS;
    }
    @Override
    public ITextComponent getName(ItemStack stack) {
        if (!stack.hasTag() || !stack.getTag().contains("slime_data"))
            return new StringTextComponent("Invalid Slime Item");

        SlimeData slimeData = SlimeData.fromTag(stack.getTag().getCompound("slime_data"));
        if (slimeData == null)
            return new TranslationTextComponent("item.productiveslimes.slime_item");
        return new TranslationTextComponent(slimeData.slime().getDescriptionId());
    }
}