package com.coolerpromc.productiveslimes.handler;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import java.util.function.Function;

public class ModClientboundBlockEntityDataPacket extends SUpdateTileEntityPacket {
    public static SUpdateTileEntityPacket create(TileEntity pBlockEntity) {
        return new ModClientboundBlockEntityDataPacket(pBlockEntity, TileEntity::getUpdateTag);
    }

    public ModClientboundBlockEntityDataPacket(TileEntity pBlockEntity, Function<TileEntity, CompoundNBT> pTag) {
        this(pBlockEntity.getBlockPos(), -1, pTag.apply(pBlockEntity));
    }

    public ModClientboundBlockEntityDataPacket(BlockPos pPos, int pType, CompoundNBT pTag) {
        super(pPos, pType, pTag);
    }
}
