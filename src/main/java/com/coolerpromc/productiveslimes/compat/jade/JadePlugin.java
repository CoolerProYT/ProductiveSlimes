package com.coolerpromc.productiveslimes.compat.jade;

import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import mcp.mobius.waila.api.*;
import mcp.mobius.waila.api.config.IPluginConfig;
import net.minecraft.network.chat.TranslatableComponent;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {
    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerComponentProvider(EntityInfoProvider.INSTANCE, TooltipPosition.BODY,BaseSlime.class);
    }

    public enum EntityInfoProvider implements IEntityComponentProvider {
        INSTANCE;

        @Override
        public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
            if (entityAccessor.getEntity() instanceof BaseSlime slime) {
                int nextDrop = slime.getNextDropTime();
                iTooltip.add(new TranslatableComponent("tooltip.productiveslimes.next_drop" , (int) Math.ceil(nextDrop / 20) + "s"));
            }
        }
    }
}
