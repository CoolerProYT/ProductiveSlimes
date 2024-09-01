package com.coolerpromc.productiveslimes.compat;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import com.coolerpromc.productiveslimes.entity.slime.GoldSlime;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {
    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerEntityComponent(EntityInfoProvider.INSTANCE, BaseSlime.class);
    }

    public enum EntityInfoProvider implements IEntityComponentProvider {
        INSTANCE;

        @Override
        public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
            if (entityAccessor.getEntity() instanceof BaseSlime slime) {
                int nextDrop = slime.getNextDropTime();
                iTooltip.add(Component.literal("Next drop: " + (int) Math.ceil(nextDrop / 20) + "s"));
            }
        }

        @Override
        public ResourceLocation getUid() {
            return ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "slime_info");
        }
    }
}
