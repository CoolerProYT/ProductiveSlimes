package com.coolerpromc.productiveslimes.event;
import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.mixin.LevelRendererAccess;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.util.ModRenderTypes;
import com.coolerpromc.productiveslimes.util.TranslucentHighlightFix;
import com.coolerpromc.productiveslimes.villager.ModVillagers;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderHighlightEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import java.util.List;

@EventBusSubscriber(modid = ProductiveSlimes.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
public class ModGameEventBus {
    @SubscribeEvent
    public static void onRenderHighlight(RenderHighlightEvent.Block event) {
        if (event.getCamera().getEntity() instanceof LivingEntity living) {
            Level world = living.level();
            BlockHitResult rtr = event.getTarget();
            BlockPos pos = rtr.getBlockPos();
            Vec3 renderView = event.getCamera().getPosition();
            BlockState targetBlock = world.getBlockState(rtr.getBlockPos());
            if (targetBlock.getBlock() instanceof TranslucentHighlightFix) {
                ((LevelRendererAccess) event.getLevelRenderer()).callRenderHitOutline(
                        event.getPoseStack(), event.getMultiBufferSource().getBuffer(ModRenderTypes.LINES_NONTRANSLUCENT),
                        living, renderView.x, renderView.y, renderView.z,
                        pos, targetBlock, 0xFF222222
                );
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {
        if (event.getType() == ModVillagers.SCIENTIST.value()){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            //Novice
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(ModTierLists.getSlimeballItemByName("dirt"), 10),
                    new ItemStack(Items.EMERALD, 1), 8, 2, 0.05f
            ));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(ModTierLists.getSlimeballItemByName("stone"), 10),
                    new ItemStack(Items.EMERALD, 1), 8, 2, 0.05f
            ));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(Items.SLIME_BALL, 20),
                    new ItemStack(Items.EMERALD, 1), 8, 2, 0.05f
            ));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModTierLists.getSlimeballItemByName("copper").get(), 4), 8, 1, 0.05f
            ));
            //Apprentice
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.ENERGY_SLIME_BALL.get(), 4), 4, 10, 0.05f
            ));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModTierLists.getSlimeballItemByName("iron").get(), 4), 4, 10, 0.05f
            ));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModTierLists.getSlimeballItemByName("stone").get(), 6), 4, 10, 0.05f
            ));
            //Journeyman
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(ModTierLists.getBucketItemByName("dirt").get(), 16),
                    new ItemStack(Items.EMERALD, 1), 4, 15, 0.05f
            ));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(ModTierLists.getBucketItemByName("stone").get(), 12),
                    new ItemStack(Items.EMERALD, 1), 4, 15, 0.05f
            ));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 20),
                    new ItemStack(ModItems.ENERGY_SLIME_SPAWN_EGG.get(), 1), 4, 15, 0.05f
            ));
            //Expert
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 32),
                    new ItemStack(ModTierLists.getDnaItemByName("iron").get(), 1), 4, 20, 0.05f
            ));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 28),
                    new ItemStack(ModTierLists.getDnaItemByName("gold").get(), 1), 4, 20, 0.05f
            ));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 48),
                    new ItemStack(ModTierLists.getDnaItemByName("diamond").get(), 1), 4, 20, 0.05f
            ));
            //Master
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 64),
                    new ItemStack(ModTierLists.getSpawnEggItemByName("diamond").get(), 1), 2, 30, 0.05f
            ));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 40),
                    new ItemStack(ModTierLists.getSpawnEggItemByName("gold").get(), 1), 2, 30, 0.05f
            ));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 48),
                    new ItemStack(ModTierLists.getSpawnEggItemByName("iron").get(), 1), 2, 30, 0.05f
            ));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 32),
                    new ItemStack(ModTierLists.getSpawnEggItemByName("copper").get(), 1), 2, 30, 0.05f
            ));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(ModTierLists.getSpawnEggItemByName("dirt").get(), 1),
                    new ItemStack(Items.EMERALD, 12), 2, 30, 0.05f
            ));
        }
    }
}
