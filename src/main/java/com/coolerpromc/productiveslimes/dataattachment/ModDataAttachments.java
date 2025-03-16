package com.coolerpromc.productiveslimes.dataattachment;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, ProductiveSlimes.MODID);

    public static final Supplier<AttachmentType<Boolean>> IS_FIRST_TIME_LOGIN = ATTACHMENT_TYPES.register("is_first_time_login", () -> AttachmentType.builder(() -> true).serialize(Codec.BOOL).copyOnDeath().build());

    public static void register(IEventBus eventBus){
        ATTACHMENT_TYPES.register(eventBus);
    }
}
