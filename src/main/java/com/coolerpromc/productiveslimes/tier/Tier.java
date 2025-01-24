package com.coolerpromc.productiveslimes.tier;

public enum Tier {
    DIRT,
    STONE,
    IRON,
    GOLD,
    DIAMOND,
    NETHERITE,
    LAPIS,
    REDSTONE,
    OAK,
    SAND,
    ANDESITE,
    SNOW,
    ICE,
    CLAY,
    RED_SAND,
    GRANITE,
    DIORITE,
    PRISMARINE,
    MAGMA,
    OBSIDIAN,
    NETHERRACK,
    SOUL_SAND,
    SOUL_SOIL,
    BLACKSTONE,
    BASALT,
    END_STONE,
    QUARTZ,
    GLOWSTONE,
    BROWN_MUSHROOM,
    RED_MUSHROOM,
    CACTUS,
    COAL,
    GRAVEL,
    OAK_LEAVES;

    public String getTierName() {
        return this.name().toLowerCase();
    }
}
