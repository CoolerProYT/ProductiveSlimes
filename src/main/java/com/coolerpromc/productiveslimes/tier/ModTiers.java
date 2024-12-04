package com.coolerpromc.productiveslimes.tier;

import net.minecraft.util.ARGB;
import net.minecraft.world.level.material.MapColor;

public class ModTiers {
    private final String name;
    private final int color;
    private final int mapColorId;
    private final int cooldown;
    private final String growthItemKey;
    private final String solidingOutputKey;
    private final int solidingOutputAmount;
    private final String synthesizingInputItemKey;
    private final String synthesizingInputDnaKey1;
    private final String synthesizingInputDnaKey2;
    private final float dnaOutputChance;

    /**
     *
     * @param name Name of the tier
     * @param color Color of the tier (For items,block and entity)
     * @param mapColorId Color on the map (ID reference to {@link MapColor})
     * @param cooldown Cooldown for slime to drop slimeball (in ticks)
     * @param growthItemKey Resource key of the item that can be used to grow the slime
     * @param solidingOutputKey Resource key of the output item for soliding recipe
     * @param solidingOutputAmount Amount of output item for soliding recipe
     * @param synthesizingInputItemKey Resource key of the input item for synthesizing recipe
     * @param synthesizingInputDnaKey1 Resource key of the first input dna for synthesizing recipe
     * @param synthesizingInputDnaKey2 Resource key of the second input dna for synthesizing recipe
     * @param dnaOutputChance Chance of output dna for synthesizing recipe
     */
    public ModTiers(String name, int color, int mapColorId, int cooldown, String growthItemKey, String solidingOutputKey, int solidingOutputAmount, String synthesizingInputItemKey, String synthesizingInputDnaKey1, String synthesizingInputDnaKey2, float dnaOutputChance) {
        this.name = name;
        this.color = color;
        this.mapColorId = mapColorId;
        this.cooldown = cooldown;
        this.growthItemKey = growthItemKey;
        this.solidingOutputKey = solidingOutputKey;
        this.solidingOutputAmount = solidingOutputAmount;
        this.synthesizingInputItemKey = synthesizingInputItemKey;
        this.synthesizingInputDnaKey1 = synthesizingInputDnaKey1;
        this.synthesizingInputDnaKey2 = synthesizingInputDnaKey2;
        this.dnaOutputChance = dnaOutputChance;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public int getMapColorId() {
        return mapColorId;
    }

    public int getCooldown() {
        return cooldown;
    }

    public String getGrowthItemKey() {
        return growthItemKey;
    }

    public int getSolidingOutputAmount() {
        return solidingOutputAmount;
    }

    public float getDnaOutputChance() {
        return dnaOutputChance;
    }

    public String getSynthesizingInputDnaKey1() {
        return synthesizingInputDnaKey1;
    }

    public String getSynthesizingInputDnaKey2() {
        return synthesizingInputDnaKey2;
    }

    public String getSynthesizingInputItemKey() {
        return synthesizingInputItemKey;
    }

    public String getSolidingOutputKey() {
        return solidingOutputKey;
    }

    public int getOpaqueColor() {
        return ARGB.opaque(color);
    }
}
