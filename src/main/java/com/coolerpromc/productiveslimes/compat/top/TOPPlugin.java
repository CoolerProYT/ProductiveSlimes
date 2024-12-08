package com.coolerpromc.productiveslimes.compat.top;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import mcjty.theoneprobe.api.IProbeHitEntityData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoEntityProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class TOPPlugin implements IProbeInfoEntityProvider {
    @Override
    public String getID() {
        return ProductiveSlimes.MODID + ":slime_info";
    }

    @Override
    public void addProbeEntityInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, Entity entity, IProbeHitEntityData iProbeHitEntityData) {
        if (entity instanceof BaseSlime slime) {
            int nextDrop = slime.getNextDropTime();
            iProbeInfo.text("Next drop: " + (int) Math.ceil(nextDrop / 20) + "s");
        }
    }
}
