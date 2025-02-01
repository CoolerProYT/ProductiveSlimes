package com.coolerpromc.productiveslimes.block.entity;

import com.coolerpromc.productiveslimes.networking.CableNetwork;
import com.coolerpromc.productiveslimes.networking.ModNetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CableBlockEntity extends BlockEntity implements IEnergyStorage {
    public static final int CAPACITY_PER_CABLE = 10_000;
    private boolean initialized = false;
    private boolean newlyPlaced = true;

    public CableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CABLE_BE.get(), pos, state);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return cap == ForgeCapabilities.ENERGY ? LazyOptional.of(() -> this).cast() : super.getCapability(cap);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
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
            if (!level.isClientSide && level instanceof ServerLevel serverWorld && blockEntity.newlyPlaced) {
                ModNetworkManager.rebuildNetwork(serverWorld, pos);
            }
        }
    }

    @Override
    public int receiveEnergy(int toReceive, boolean simulate) {
        CableNetwork net = ModNetworkManager.getNetwork(this.getBlockPos());
        return net == null ? 0 : net.insertEnergy(toReceive, simulate);
    }

    @Override
    public int extractEnergy(int toExtract, boolean simulate) {
        CableNetwork net = ModNetworkManager.getNetwork(this.getBlockPos());
        return net == null ? 0 : net.extractEnergy(toExtract, simulate);
    }

    @Override
    public int getEnergyStored() {
        CableNetwork net = ModNetworkManager.getNetwork(this.getBlockPos());
        return net == null ? 0 : net.getTotalEnergy();
    }

    @Override
    public int getMaxEnergyStored() {
        CableNetwork net = ModNetworkManager.getNetwork(this.getBlockPos());
        return net == null ? 0 : net.getTotalCapacity();
    }

    @Override
    public boolean canExtract() {
        CableNetwork net = ModNetworkManager.getNetwork(this.getBlockPos());
        return net != null && net.getTotalEnergy() > 0;
    }

    @Override
    public boolean canReceive() {
        CableNetwork net = ModNetworkManager.getNetwork(this.getBlockPos());
        return net != null && net.getTotalEnergy() < net.getTotalCapacity();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putBoolean("NewlyPlaced", newlyPlaced);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        newlyPlaced = pTag.getBoolean("NewlyPlaced");
    }
}