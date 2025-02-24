package com.coolerpromc.productiveslimes.event;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.datacomponent.ModDataComponents;
import com.coolerpromc.productiveslimes.entity.slime.Slime;
import com.coolerpromc.productiveslimes.datacomponent.custom.SlimeData;
import com.coolerpromc.productiveslimes.item.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = ProductiveSlimes.MODID)
public class ModEntityInteractEvent {
    @SubscribeEvent
    public static void onPlayerInteractEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getTarget() instanceof Slime)) return;
        if (event.getHand() != InteractionHand.MAIN_HAND) return;
        if (!event.getEntity().isCrouching()) return;
        if (event.getEntity().getItemInHand(event.getHand()).getItem() != Items.AIR) return;

        Slime slime = (Slime) event.getTarget();

        ItemStack itemStack = new ItemStack(ModItems.SLIME_ITEM.get());
        itemStack.set(ModDataComponents.SLIME_DATA.get(), SlimeData.fromSlime(slime));

        event.getEntity().setItemInHand(event.getHand(), itemStack);
        event.getTarget().remove(Entity.RemovalReason.UNLOADED_WITH_PLAYER);
    }
}
