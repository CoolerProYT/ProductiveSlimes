/*
package com.coolerpromc.productiveslimes.compat.jade;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
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
                iTooltip.add(Component.translatable("tooltip.productiveslimes.next_drop" , (int) Math.ceil((double) nextDrop / 20) + "s"));
            }
        }

        @Override
        public Identifier getUid() {
            return Identifier.fromNamespaceAndPath(ProductiveSlimes.MODID, "slime_info");
        }
    }
}
*/
// TODO: uncomment this class when jade updated