package com.coolerpromc.productiveslimes.block.entity;

import com.coolerpromc.productiveslimes.handler.ModClientboundBlockEntityDataPacket;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
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

    LazyOptional<FluidTank> fluidTankLazyOptional = LazyOptional.of(() -> fluidTank);

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
            return fluidTankLazyOptional.cast();
        }

        return super.getCapability(cap);
    }

    public void tick(World level, BlockPos blockPos, BlockState blockState){
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
    public CompoundNBT save(CompoundNBT pTag) {
        pTag = fluidTank.writeToNBT(pTag);

        return super.save(pTag);
    }

    @Override
    public void load(BlockState state, CompoundNBT pTag) {
        super.load(state, pTag);

        fluidTank.readFromNBT(pTag);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return ModClientboundBlockEntityDataPacket.create(this);
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
