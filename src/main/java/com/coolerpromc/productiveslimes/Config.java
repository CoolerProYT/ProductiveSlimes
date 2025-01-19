package com.coolerpromc.productiveslimes;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    public static final Config CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;
    public final ModConfigSpec.BooleanValue vanillaSlimeCanAttackPlayer;
    public final ModConfigSpec.BooleanValue ironGolemCanAttackSlime;

    static {
        Pair<Config, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(Config::new);

        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }

    private Config(ModConfigSpec.Builder builder) {
        builder.push("Slime Settings");
        vanillaSlimeCanAttackPlayer = builder
                .comment("Should vanilla slime able to attack player")
                .define("vanilla_slime_can_attack_player", false);
        builder.pop();
        builder.push("Iron Golem Settings");
        ironGolemCanAttackSlime = builder
                .comment("Should iron golem able to attack slime")
                .define("iron_golem_can_attack_slime", false);
        builder.pop();
    }
}