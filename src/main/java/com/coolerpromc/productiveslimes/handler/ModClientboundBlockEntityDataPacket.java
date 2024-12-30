package com.coolerpromc.productiveslimes.handler;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.function.Function;

public class ModClientboundBlockEntityDataPacket extends ClientboundBlockEntityDataPacket {
    public static ClientboundBlockEntityDataPacket create(BlockEntity pBlockEntity) {
        return new ModClientboundBlockEntityDataPacket(pBlockEntity, BlockEntity::getUpdateTag);
    }

    public ModClientboundBlockEntityDataPacket(BlockEntity pBlockEntity, Function<BlockEntity, CompoundTag> pTag) {
        this(pBlockEntity.getBlockPos(), -1, pTag.apply(pBlockEntity));
    }

    public ModClientboundBlockEntityDataPacket(BlockPos pPos, int pType, CompoundTag pTag) {
        super(pPos, pType, pTag);
    }
}
