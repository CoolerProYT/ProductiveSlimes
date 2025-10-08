package com.coolerpromc.productiveslimes.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.fluid.FluidStacksResourceHandler;
import org.jetbrains.annotations.Nullable;

public class FluidTankBlockEntity extends BlockEntity {
    public final int capacity = 50000;

    private final FluidStacksResourceHandler fluidTank = new FluidStacksResourceHandler(1, capacity){
        @Override
        protected void onContentsChanged(int index, FluidStack previousContents) {
            setChanged();
            if (level != null && !level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isValid(int index, FluidResource resource) {
            return true;
        }
    };

    public FluidTankBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FLUID_TANK_BE.get(), pPos, pBlockState);
        fluidTank.set(0, FluidResource.EMPTY, 0);
    }

    public FluidStacksResourceHandler getFluidTank() {
        return fluidTank;
    }

    public void setFluidStack(FluidStack stack) {
        fluidTank.set(0, FluidResource.of(stack), stack.getAmount());
    }

    public FluidStack getFluidStack() {
        return fluidTank.getResource(0).toStack(fluidTank.getAmountAsInt(0));
    }

    public void drops(){

    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState){

    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    protected void saveAdditional(ValueOutput valueOutput) {
        fluidTank.serialize(valueOutput);
        super.saveAdditional(valueOutput);
    }

    @Override
    protected void loadAdditional(ValueInput valueInput) {
        fluidTank.deserialize(valueInput);
        super.loadAdditional(valueInput);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }
}