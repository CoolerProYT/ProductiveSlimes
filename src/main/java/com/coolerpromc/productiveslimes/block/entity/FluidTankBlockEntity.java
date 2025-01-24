package com.coolerpromc.productiveslimes.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.antlr.v4.runtime.misc.NotNull;

import javax.annotation.Nullable;

public class FluidTankBlockEntity extends TileEntity {
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

    LazyOptional<IFluidHandler> fluidTankLazyOptional = LazyOptional.of(() -> fluidTank);

    public FluidTankBlockEntity() {
        super(ModBlockEntities.FLUID_TANK_BE.get());
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
            System.out.println("Fluid capability requested");
            return fluidTankLazyOptional.cast();
        }

        return super.getCapability(cap);
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
    public CompoundNBT save(CompoundNBT pTag) {
        super.save(pTag);
        pTag.put("fluid", fluidTank.writeToNBT(new CompoundNBT()));
        return pTag;
    }

    @Override
    public void load(BlockState state, CompoundNBT pTag) {
        super.load(state, pTag);

        fluidTank.readFromNBT(pTag.getCompound("fluid"));
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT tag = new CompoundNBT();
        save(tag);
        return new SUpdateTileEntityPacket(worldPosition, 1, tag);
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        this.load(state, tag);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT compoundTag = new CompoundNBT();
        this.save(compoundTag);
        return compoundTag;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        CompoundNBT tag = pkt.getTag();
        if (tag != null) {
            handleUpdateTag(this.getBlockState(), tag);
        }
    }
}
