package com.coolerpromc.productiveslimes.block.entity;

import com.coolerpromc.productiveslimes.networking.pipe.ModPipeNetworkManager;
import com.coolerpromc.productiveslimes.networking.pipe.PipeNetwork;
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
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

public class PipeBlockEntity extends BlockEntity implements IFluidHandler {
    public static final int CAPACITY_PER_CABLE = 1000;
    private boolean initialized = false;
    private boolean newlyPlaced = true;

    public PipeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PIPE_BE.get(), pos, state);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if(!level.isClientSide && level instanceof ServerLevel serverLevel) {
            if (shouldRemoveCableEntity(serverLevel)) {
                ModPipeNetworkManager.onCableRemoved(serverLevel, this.getBlockPos());
            }
        }
    }

    private boolean shouldRemoveCableEntity(ServerLevel serverWorld) {
        if (serverWorld.hasChunkAt(this.getBlockPos())) {
            return !(serverWorld.getBlockEntity(this.getBlockPos()) instanceof PipeBlockEntity);
        }
        return false;
    }

    public static void tick(Level level, BlockPos pos, PipeBlockEntity blockEntity) {
        if (!blockEntity.initialized) {
            blockEntity.initialized = true;
            if (!level.isClientSide && level instanceof ServerLevel serverWorld && blockEntity.newlyPlaced) {
                ModPipeNetworkManager.rebuildNetwork(serverWorld, pos);
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
        return saveCustomAndMetadata(registries);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.putBoolean("NewlyPlaced", newlyPlaced);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        newlyPlaced = pTag.getBoolean("NewlyPlaced");
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Override
    public FluidStack getFluidInTank(int tank) {
        PipeNetwork net = ModPipeNetworkManager.getNetwork(this.getBlockPos());
        return net == null ? FluidStack.EMPTY : net.getFluidStack();
    }

    @Override
    public int getTankCapacity(int tank) {
        PipeNetwork net = ModPipeNetworkManager.getNetwork(this.getBlockPos());
        return net == null ? 0 : net.getTotalCapacity();
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        PipeNetwork net = ModPipeNetworkManager.getNetwork(this.getBlockPos());
        return stack.is(net.getFluid()) || net.getFluidStack().isEmpty();
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        PipeNetwork net = ModPipeNetworkManager.getNetwork(this.getBlockPos());
        return net == null ? 0 : net.insertFluid(resource, action == FluidAction.SIMULATE);
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        PipeNetwork net = ModPipeNetworkManager.getNetwork(this.getBlockPos());
        return net == null ? FluidStack.EMPTY : net.extractFluid(resource, action == FluidAction.SIMULATE);
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        PipeNetwork net = ModPipeNetworkManager.getNetwork(this.getBlockPos());
        return net == null ? FluidStack.EMPTY : net.drain(maxDrain, action == FluidAction.SIMULATE);
    }
}