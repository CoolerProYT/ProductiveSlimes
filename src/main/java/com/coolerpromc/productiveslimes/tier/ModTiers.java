package com.coolerpromc.productiveslimes.tier;

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

    // Constructor
    public ModTiers(String name, int color, int mapColorId, int cooldown,
                    String growthItemKey, String solidingOutputKey, int solidingOutputAmount,
                    String synthesizingInputItemKey, String synthesizingInputDnaKey1,
                    String synthesizingInputDnaKey2, float dnaOutputChance) {
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

    // Getters (no 'get' prefix, for access like a record)
    public String name() {
        return name;
    }

    public int color() {
        return color;
    }

    public int mapColorId() {
        return mapColorId;
    }

    public int cooldown() {
        return cooldown;
    }

    public String growthItemKey() {
        return growthItemKey;
    }

    public String solidingOutputKey() {
        return solidingOutputKey;
    }

    public int solidingOutputAmount() {
        return solidingOutputAmount;
    }

    public String synthesizingInputItemKey() {
        return synthesizingInputItemKey;
    }

    public String synthesizingInputDnaKey1() {
        return synthesizingInputDnaKey1;
    }

    public String synthesizingInputDnaKey2() {
        return synthesizingInputDnaKey2;
    }

    public float dnaOutputChance() {
        return dnaOutputChance;
    }
}
