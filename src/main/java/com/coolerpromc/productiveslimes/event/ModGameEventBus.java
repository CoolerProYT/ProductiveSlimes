package com.coolerpromc.productiveslimes.event;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.mixin.LevelRendererAccess;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.Tier;
import com.coolerpromc.productiveslimes.util.ModRenderTypes;
import com.coolerpromc.productiveslimes.util.TranslucentHighlightFix;
import com.coolerpromc.productiveslimes.villager.ModVillagers;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.DrawSelectionEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = ProductiveSlimes.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModGameEventBus {
    @SubscribeEvent
    public void onDrawSelectionHighlightBlock(DrawSelectionEvent.HighlightBlock event) {
        if (event.getCamera().getEntity() instanceof LivingEntity living) {
            Level world = living.level;
            BlockHitResult rtr = event.getTarget();
            BlockPos pos = rtr.getBlockPos();
            Vec3 renderView = event.getCamera().getPosition();

            BlockState targetBlock = world.getBlockState(rtr.getBlockPos());
            if (targetBlock.getBlock() instanceof TranslucentHighlightFix) {
                ((LevelRendererAccess) event.getLevelRenderer()).callRenderHitOutline(
                        event.getPoseStack(), event.getMultiBufferSource().getBuffer(ModRenderTypes.LINES_NONTRANSLUCENT),
                        living, renderView.x, renderView.y, renderView.z,
                        pos, targetBlock
                );
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {
        if (event.getType() == ModVillagers.SCIENTIST.get()){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            //Novice
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModTierLists.getSlimeballItemByName(Tier.DIRT.getTierName()).get(), 10),
                    new ItemStack(Items.EMERALD, 1), 8, 2, 0.05f
            ));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModTierLists.getSlimeballItemByName(Tier.STONE.getTierName()).get(), 10),
                    new ItemStack(Items.EMERALD, 1), 8, 2, 0.05f
            ));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.SLIME_BALL, 20),
                    new ItemStack(Items.EMERALD, 1), 8, 2, 0.05f
            ));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(ModTierLists.getSlimeballItemByName(Tier.COPPER.getTierName()).get(), 4), 8, 1, 0.05f
            ));
            //Apprentice
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(ModItems.ENERGY_SLIME_BALL.get(), 4), 4, 10, 0.05f
            ));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(ModTierLists.getSlimeballItemByName(Tier.IRON.getTierName()).get(), 4), 4, 10, 0.05f
            ));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(ModTierLists.getSlimeballItemByName(Tier.STONE.getTierName()).get(), 6), 4, 10, 0.05f
            ));
            //Journeyman
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModTierLists.getBucketItemByName(Tier.DIRT.getTierName()).get(), 16),
                    new ItemStack(Items.EMERALD, 1), 4, 15, 0.05f
            ));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModTierLists.getBucketItemByName(Tier.STONE.getTierName()).get(), 12),
                    new ItemStack(Items.EMERALD, 1), 4, 15, 0.05f
            ));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(ModItems.ENERGY_SLIME_SPAWN_EGG.get(), 1), 4, 15, 0.05f
            ));
            //Expert
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 32),
                    new ItemStack(ModTierLists.getDnaItemByName(Tier.IRON.getTierName()).get(), 1), 4, 20, 0.05f
            ));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 28),
                    new ItemStack(ModTierLists.getDnaItemByName(Tier.GOLD.getTierName()).get(), 1), 4, 20, 0.05f
            ));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 48),
                    new ItemStack(ModTierLists.getDnaItemByName(Tier.DIAMOND.getTierName()).get(), 1), 4, 20, 0.05f
            ));
            //Master
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 64),
                    new ItemStack(ModTierLists.getSpawnEggItemByName(Tier.DIAMOND.getTierName()).get(), 1), 2, 30, 0.05f
            ));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 40),
                    new ItemStack(ModTierLists.getSpawnEggItemByName(Tier.GOLD.getTierName()).get(), 1), 2, 30, 0.05f
            ));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 48),
                    new ItemStack(ModTierLists.getSpawnEggItemByName(Tier.IRON.getTierName()).get(), 1), 2, 30, 0.05f
            ));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 32),
                    new ItemStack(ModTierLists.getSpawnEggItemByName(Tier.COPPER.getTierName()).get(), 1), 2, 30, 0.05f
            ));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModTierLists.getSpawnEggItemByName(Tier.DIRT.getTierName()).get(), 1),
                    new ItemStack(Items.EMERALD, 12), 2, 30, 0.05f
            ));
        }
    }
}
