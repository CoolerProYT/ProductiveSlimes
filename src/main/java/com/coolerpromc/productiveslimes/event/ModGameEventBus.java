package com.coolerpromc.productiveslimes.event;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.mixin.LevelRendererAccess;
import com.coolerpromc.productiveslimes.util.ModRenderTypes;
import com.coolerpromc.productiveslimes.util.TranslucentHighlightFix;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderHighlightEvent;

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
                        pos, targetBlock
                );
                event.setCanceled(true);
            }
        }
    }
}
