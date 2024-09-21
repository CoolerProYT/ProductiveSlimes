package com.coolerpromc.productiveslimes.fluid;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class ModFluidTypes {
    public static final ResourceLocation WATER_STILL_RL = ResourceLocation.parse("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = ResourceLocation.parse("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY_RL = ResourceLocation.parse("block/water_overlay");

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, ProductiveSlimes.MODID);

    public static final Supplier<FluidType> MOLTEN_DIRT_FLUID_TYPE = registerFluidType("molten_dirt_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78866043,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_STONE_FLUID_TYPE = registerFluidType("molten_stone_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x784a4545,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_COPPER_FLUID_TYPE = registerFluidType("molten_copper_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x786a3e15,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_IRON_FLUID_TYPE = registerFluidType("molten_iron_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78898c8a,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_GOLD_FLUID_TYPE = registerFluidType("molten_gold_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78a5953f,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_DIAMOND_FLUID_TYPE = registerFluidType("molten_diamond_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78178f9c,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_NETHERITE_FLUID_TYPE = registerFluidType("molten_netherite_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x784c2b2b,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_LAPIS_FLUID_TYPE = registerFluidType("molten_lapis_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x781c41ba,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_REDSTONE_FLUID_TYPE = registerFluidType("molten_redstone_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78a10505,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_OAK_FLUID_TYPE = registerFluidType("molten_oak_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78a3854f,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_SAND_FLUID_TYPE = registerFluidType("molten_sand_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78f7f7c6,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_ANDESITE_FLUID_TYPE = registerFluidType("molten_andesite_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x789d9e9a,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_SNOW_FLUID_TYPE = registerFluidType("molten_snow_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78f2fcfc,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_ICE_FLUID_TYPE = registerFluidType("molten_ice_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x7889b1fc,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_MUD_FLUID_TYPE = registerFluidType("molten_mud_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78363339,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_CLAY_FLUID_TYPE = registerFluidType("molten_clay_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x789ca2ac,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_RED_SAND_FLUID_TYPE = registerFluidType("molten_red_sand_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78bb6520,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_MOSS_FLUID_TYPE = registerFluidType("molten_moss_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x784a6029,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_DEEPSLATE_FLUID_TYPE = registerFluidType("molten_deepslate_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x783c3c42,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_GRANITE_FLUID_TYPE = registerFluidType("molten_granite_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78835949,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_DIORITE_FLUID_TYPE = registerFluidType("molten_diorite_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78adacad,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_CALCITE_FLUID_TYPE = registerFluidType("molten_calcite_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78e9e9e3,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_TUFF_FLUID_TYPE = registerFluidType("molten_tuff_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x7855564c,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_DRIPSTONE_FLUID_TYPE = registerFluidType("molten_dripstone_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78806155,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_PRISMARINE_FLUID_TYPE = registerFluidType("molten_prismarine_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78529584,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_MAGMA_FLUID_TYPE = registerFluidType("molten_magma_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78561f1f,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_OBSIDIAN_FLUID_TYPE = registerFluidType("molten_obsidian_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78030106,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_NETHERRACK_FLUID_TYPE = registerFluidType("molten_netherrack_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78763535,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_SOUL_SAND_FLUID_TYPE = registerFluidType("molten_soul_sand_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78413127,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_SOUL_SOIL_FLUID_TYPE = registerFluidType("molten_soul_soil_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78392b23,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_BLACKSTONE_FLUID_TYPE = registerFluidType("molten_blackstone_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78201819,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_BASALT_FLUID_TYPE = registerFluidType("molten_basalt_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78565456,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_ENDSTONE_FLUID_TYPE = registerFluidType("molten_endstone_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78cece8e,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_QUARTZ_FLUID_TYPE = registerFluidType("molten_quartz_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78e4ddd3,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_GLOWSTONE_FLUID_TYPE = registerFluidType("molten_glowstone_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78784e27,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_AMETHYST_FLUID_TYPE = registerFluidType("molten_amethyst_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x786b4da5,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_BROWN_MUSHROOM_FLUID_TYPE = registerFluidType("molten_brown_mushroom_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78967251,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_RED_MUSHROOM_FLUID_TYPE = registerFluidType("molten_red_mushroom_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78c02624,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_CACTUS_FLUID_TYPE = registerFluidType("molten_cactus_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x78f7f7c6,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_COAL_FLUID_TYPE = registerFluidType("molten_coal_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x783b3d3b,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> MOLTEN_GRAVEL_FLUID_TYPE = registerFluidType("molten_gravel_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0x784a444b,
                    new Vector3f(108f / 255f, 168f / 255f, 212f / 255f),
                    FluidType.Properties.create()));

    private static Supplier<FluidType> registerFluidType(String name, FluidType fluidType) {
        return FLUID_TYPES.register(name, () -> fluidType);
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
