package com.coolerpromc.productiveslimes.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.antlr.v4.runtime.misc.NotNull;

public class FluidTankBlockEntity extends BlockEntity {
    public final int capacity = 50000;

    private final FluidTank fluidTank = new FluidTank(capacity){
        @Override
        protected void onContentsChanged() {
            setChanged();
            if (!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return super.isFluidValid(stack);
        }
    };

    LazyOptional<FluidTank> fluidTankLazyOptional = LazyOptional.of(() -> fluidTank);

    public FluidTankBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FLUID_TANK_BE.get(), pPos, pBlockState);
        fluidTank.setFluid(FluidStack.EMPTY);
    }

    public FluidTank getFluidTank() {
        return fluidTank;
    }

    public void setFluidStack(FluidStack stack) {
        fluidTank.setFluid(stack);
    }

    public FluidStack getFluidStack() {
        return fluidTank.getFluid();
    }

    public void drops(){

    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || cap == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY){
            return fluidTankLazyOptional.cast();
        }

        return super.getCapability(cap);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState){
        setChanged();
        if (!level.isClientSide()){
            level.sendBlockUpdated(blockPos, blockState, blockState, 3);
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();

        fluidTankLazyOptional.invalidate();
    }

    @Override
    public CompoundTag save(CompoundTag pTag) {
        pTag = fluidTank.writeToNBT(pTag);

        return super.save(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        fluidTank.readFromNBT(pTag);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket(){
        CompoundTag tag = new CompoundTag();
        save(tag);
        return new ClientboundBlockEntityDataPacket(this.getBlockPos(), 0, tag);
    }
    @Override
    public void handleUpdateTag(CompoundTag tag) {
        this.load(tag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compoundTag = new CompoundTag();
        this.save(compoundTag);
        return compoundTag;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        CompoundTag tag = pkt.getTag();
        if (tag != null) {
            handleUpdateTag(tag);
        }
    }
}
