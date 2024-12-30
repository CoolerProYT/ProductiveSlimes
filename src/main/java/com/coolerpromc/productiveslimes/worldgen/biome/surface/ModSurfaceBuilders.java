package com.coolerpromc.productiveslimes.worldgen.biome.surface;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSurfaceBuilders {
    public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, ProductiveSlimes.MODID);

    public static final SurfaceBuilder<SurfaceBuilderBaseConfiguration> SLIMY_LAND = register("slimy_land", new SlimyLandSurfaceBuilder(SurfaceBuilderBaseConfiguration.CODEC.stable()));

    public static void register(IEventBus eventBus) {
        SURFACE_BUILDERS.register(eventBus);;
    }

    private static <C extends SurfaceBuilderBaseConfiguration, F extends SurfaceBuilder<C>> F register(String key, F builder)
    {
        builder.setRegistryName(new ResourceLocation(ProductiveSlimes.MODID, key));
        ForgeRegistries.SURFACE_BUILDERS.register(builder);
        return builder;
    }
}
