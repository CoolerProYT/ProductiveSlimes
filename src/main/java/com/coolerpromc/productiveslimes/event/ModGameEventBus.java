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
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.DrawHighlightEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = ProductiveSlimes.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModGameEventBus {
    @SubscribeEvent
    public void onDrawSelectionHighlightBlock(DrawHighlightEvent.HighlightBlock event) {
        if (event.getInfo().getEntity() instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) event.getInfo().getEntity();
            World world = living.level;
            BlockRayTraceResult rtr = event.getTarget();
            BlockPos pos = rtr.getBlockPos();
            Vector3d renderView = event.getInfo().getPosition();

            BlockState targetBlock = world.getBlockState(rtr.getBlockPos());
            if (targetBlock.getBlock() instanceof TranslucentHighlightFix) {
                ((LevelRendererAccess) event.getContext()).callRenderHitOutline(
                        event.getMatrix(), event.getBuffers().getBuffer(ModRenderTypes.LINES_NONTRANSLUCENT),
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
            Int2ObjectMap<List<VillagerTrades.ITrade>> trades = event.getTrades();

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
                    new ItemStack(ModTierLists.getSpawnEggItemByName(Tier.DIRT.getTierName()).get(), 1),
                    new ItemStack(Items.EMERALD, 12), 2, 30, 0.05f
            ));
        }
    }
}
