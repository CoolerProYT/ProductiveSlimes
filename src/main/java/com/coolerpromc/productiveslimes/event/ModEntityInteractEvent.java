package com.coolerpromc.productiveslimes.event;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import com.coolerpromc.productiveslimes.entity.slime.IronSlime;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = ProductiveSlimes.MODID)
public class ModEntityInteractEvent {
    @SubscribeEvent
    public static void onPlayerInteractEntityInteract(PlayerInteractEvent.EntityInteract event) {
        /*if (event.getTarget() instanceof Slime && !(event.getTarget() instanceof BaseSlime)) {
            Player player = event.getEntity();
            ItemStack itemStack = player.getItemInHand(event.getHand());

            if (itemStack.getItem() == Items.COPPER_BLOCK){
                transformSlime(event, player, itemStack, ModEntities.COPPER_SLIME.get().create(event.getLevel()));
            }

            if(itemStack.getItem() == Items.DIRT){
                transformSlime(event, player, itemStack, ModEntities.DIRT_SLIME.get().create(event.getLevel()));
            }
        }*/
    }

    protected static void transformSlime(PlayerInteractEvent.EntityInteract event, Player player, ItemStack itemStack, BaseSlime entity){
        Level level = event.getLevel();
        if (!level.isClientSide) {
            Slime vanillaSlime = (Slime) event.getTarget();

            if (player.getItemInHand(event.getHand()).getCount() > vanillaSlime.getSize()){
                if (!player.getAbilities().instabuild){
                    itemStack.shrink(vanillaSlime.getSize() + 1);
                }

                if (entity != null) {
                    entity.moveTo(vanillaSlime.getX(), vanillaSlime.getY(), vanillaSlime.getZ(), vanillaSlime.getYRot(), vanillaSlime.getXRot());
                    entity.setSize(vanillaSlime.getSize(), true);
                    level.addFreshEntity(entity);
                }

                vanillaSlime.discard();

                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
            }
        }
    }
}
