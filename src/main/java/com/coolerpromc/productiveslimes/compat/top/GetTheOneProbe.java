package com.coolerpromc.productiveslimes.compat.top;

import mcjty.theoneprobe.api.ITheOneProbe;

import java.util.function.Function;

public class GetTheOneProbe implements Function<ITheOneProbe, Void> {
    @Override
    public Void apply(ITheOneProbe probe) {
        probe.registerEntityProvider(new TOPPlugin());
        return null;
    }
}