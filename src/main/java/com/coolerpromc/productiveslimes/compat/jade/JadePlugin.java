package com.coolerpromc.productiveslimes.compat.jade;

import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import mcp.mobius.waila.api.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {
    @Override
    public void register(IRegistrar iRegistrar) {
        iRegistrar.registerComponentProvider(EntityInfoProvider.INSTANCE, TooltipPosition.BODY, BaseSlime.class);
    }

    public enum EntityInfoProvider implements IEntityComponentProvider {
        INSTANCE;

        @Override
        public void appendBody(List<ITextComponent> tooltip, IEntityAccessor accessor, IPluginConfig config) {
            if (accessor.getEntity() instanceof BaseSlime) {
                BaseSlime slime = (BaseSlime) accessor.getEntity();
                int nextDrop = slime.getNextDropTime();
                tooltip.add(new TranslationTextComponent("tooltip.productiveslimes.next_drop", (int) Math.ceil(nextDrop / 20) + "s"));
            }
        }
    }
}
