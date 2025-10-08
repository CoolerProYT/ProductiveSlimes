package com.coolerpromc.productiveslimes.block.entity;

import com.coolerpromc.productiveslimes.networking.CableNetwork;
import com.coolerpromc.productiveslimes.networking.ModNetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.jetbrains.annotations.Nullable;

public class CableBlockEntity extends BlockEntity implements EnergyHandler {
    public static final int CAPACITY_PER_CABLE = 10_000;
    private boolean initialized = false;
    private boolean newlyPlaced = true;

    public CableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CABLE_BE.get(), pos, state);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            if (shouldRemoveCableEntity(serverLevel)) {
                ModNetworkManager.onCableRemoved(serverLevel, this.getBlockPos());
            }
        }
    }

    private boolean shouldRemoveCableEntity(ServerLevel serverWorld) {
        if (serverWorld.hasChunkAt(this.getBlockPos())) {
            return !(serverWorld.getBlockEntity(this.getBlockPos()) instanceof CableBlockEntity);
        }
        return false;
    }

    public static void tick(Level level, BlockPos pos, CableBlockEntity blockEntity) {
        if (!blockEntity.initialized) {
            blockEntity.initialized = true;
            if (!level.isClientSide() && level instanceof ServerLevel serverWorld && blockEntity.newlyPlaced) {
                ModNetworkManager.rebuildNetwork(serverWorld, pos);
            }
        }
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    protected void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        valueOutput.putBoolean("NewlyPlaced", newlyPlaced);
    }

    @Override
    protected void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);
        newlyPlaced = valueInput.getBooleanOr("NewlyPlaced", true);
    }

    @Override
    public long getAmountAsLong() {
        CableNetwork net = ModNetworkManager.getNetwork(this.getBlockPos());
        return net == null ? 0 : net.getTotalEnergy();
    }

    @Override
    public long getCapacityAsLong() {
        CableNetwork net = ModNetworkManager.getNetwork(this.getBlockPos());
        return net == null ? 0 : net.getTotalCapacity();
    }

    @Override
    public int insert(int amount, TransactionContext transaction) {
        CableNetwork net = ModNetworkManager.getNetwork(this.getBlockPos());
        return net == null ? 0 : net.insertEnergy(amount, transaction);
    }

    @Override
    public int extract(int amount, TransactionContext transaction) {
        CableNetwork net = ModNetworkManager.getNetwork(this.getBlockPos());
        return net == null ? 0 : net.extractEnergy(amount, transaction);
    }
}