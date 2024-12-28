package com.coolerpromc.productiveslimes.event;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.slime.Slime;
import com.coolerpromc.productiveslimes.handler.SlimeData;
import com.coolerpromc.productiveslimes.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ProductiveSlimes.MODID)
public class ModEntityInteractEvent {
    @SubscribeEvent
    public static void onPlayerInteractEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getTarget() instanceof Slime slime)) return;
        if (event.getHand() != InteractionHand.MAIN_HAND) return;
        if (!event.getEntity().isCrouching()) return;
        if (event.getEntity().getItemInHand(event.getHand()).getItem() != Items.AIR) return;

        ItemStack itemStack = new ItemStack(ModItems.SLIME_ITEM.get());
        CompoundTag tag = new CompoundTag();
        tag.put("slime_data", SlimeData.fromSlime(slime).toTag(new CompoundTag()));
        itemStack.setTag(tag);

        event.getEntity().setItemInHand(event.getHand(), itemStack);
        event.getTarget().remove(Entity.RemovalReason.UNLOADED_WITH_PLAYER);
    }
}
