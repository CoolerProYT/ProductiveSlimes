package com.coolerpromc.productiveslimes.event;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.slime.Slime;
import com.coolerpromc.productiveslimes.handler.SlimeData;
import com.coolerpromc.productiveslimes.item.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ProductiveSlimes.MODID)
public class ModEntityInteractEvent {
    @SubscribeEvent
    public static void onPlayerInteractEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getTarget() instanceof Slime)) return;
        if (event.getHand() != Hand.MAIN_HAND) return;
        if (!event.getEntity().isCrouching()) return;
        if (event.getPlayer().getItemInHand(event.getHand()).getItem() != Items.AIR) return;
        Slime slime = (Slime) event.getTarget();

        ItemStack itemStack = new ItemStack(ModItems.SLIME_ITEM.get());
        CompoundNBT tag = new CompoundNBT();
        tag.put("slime_data", SlimeData.fromSlime(slime).toTag(new CompoundNBT()));
        itemStack.setTag(tag);

        event.getPlayer().setItemInHand(event.getHand(), itemStack);
        event.getTarget().remove(true);
    }
}
