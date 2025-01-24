package com.coolerpromc.productiveslimes.block.entity;

import com.coolerpromc.productiveslimes.handler.CustomEnergyStorage;
import com.coolerpromc.productiveslimes.recipe.SolidingRecipe;
import com.coolerpromc.productiveslimes.screen.SolidingStationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.antlr.v4.runtime.misc.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class SolidingStationBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler inputHandler = new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return true;
        }
    };

    private final ItemStackHandler outputHandler = new ItemStackHandler(2){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return false;
        }
    };

    private final CustomEnergyStorage energyHandler = new CustomEnergyStorage(10000, 1000, 0,0);

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public SolidingStationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SOLIDING_STATION_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> SolidingStationBlockEntity.this.progress;
                    case 1 -> SolidingStationBlockEntity.this.maxProgress;
                    case 2 -> SolidingStationBlockEntity.this.energyHandler.getEnergyStored();
                    case 3 -> SolidingStationBlockEntity.this.energyHandler.getMaxEnergyStored();
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> SolidingStationBlockEntity.this.progress = pValue;
                    case 1 -> SolidingStationBlockEntity.this.maxProgress = pValue;
                    case 2 -> SolidingStationBlockEntity.this.energyHandler.setEnergy(pValue);
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    public ItemStackHandler getInputHandler() {
        return inputHandler;
    }

    public ItemStackHandler getOutputHandler() {
        return outputHandler;
    }

    public CustomEnergyStorage getEnergyHandler() {
        return energyHandler;
    }

    private LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyHandler);
    private LazyOptional<ItemStackHandler> input = LazyOptional.of(() -> inputHandler);
    private LazyOptional<ItemStackHandler> output = LazyOptional.of(() -> outputHandler);

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY){
            return energy.cast();
        }

        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            if (side != Direction.DOWN){
                return input.cast();
            }
            else {
                return output.cast();
            }
        }

        return super.getCapability(cap, side);
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(3);
        inventory.setItem(0, inputHandler.getStackInSlot(0));
        inventory.setItem(1, outputHandler.getStackInSlot(0));
        inventory.setItem(2, outputHandler.getStackInSlot(1));

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.productiveslimes.soliding_station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new SolidingStationMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public CompoundTag save(CompoundTag pTag) {
        pTag.put("InputInventory", inputHandler.serializeNBT());
        pTag.put("OutputInventory", outputHandler.serializeNBT());
        pTag.putInt("EnergyInventory", energyHandler.getEnergyStored());

        pTag.putInt("soliding_station.progress", progress);

        return super.save(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        inputHandler.deserializeNBT(pTag.getCompound("InputInventory"));
        outputHandler.deserializeNBT(pTag.getCompound("OutputInventory"));
        energyHandler.setEnergy(pTag.getInt("EnergyInventory"));

        progress = pTag.getInt("soliding_station.progress");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        Optional<SolidingRecipe> recipe = getCurrentRecipe();
        if(hasRecipe() && energyHandler.getEnergyStored() >= recipe.get().getEnergy()) {
            increaseCraftingProgress();
            setChanged(pLevel, pPos, pState);

            if(hasProgressFinished()) {
                energyHandler.removeEnergy(recipe.get().getEnergy());
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {
        Optional<SolidingRecipe> recipe = getCurrentRecipe();
        if (recipe.isPresent()) {
            List<ItemStack> results = recipe.get().getOutputs();

            // Extract the input item from the input slot
            this.inputHandler.extractItem(0, recipe.get().getInputCount(), false);

            // Loop through each result item and find suitable output slots
            for (ItemStack result : results) {
                int outputSlot = findSuitableOutputSlot(result);
                if (outputSlot != -1) {
                    this.outputHandler.setStackInSlot(outputSlot, new ItemStack(result.getItem(),
                            this.outputHandler.getStackInSlot(outputSlot).getCount() + result.getCount()));
                } else {
                    // Handle the case where no suitable output slot is found
                    // This can be logging an error, throwing an exception, or any other handling logic
                    System.err.println("No suitable output slot found for item: " + result);
                }
            }
        }
    }

    private int findSuitableOutputSlot(ItemStack result) {
        // Implement logic to find a suitable output slot for the given result
        // Return the slot index or -1 if no suitable slot is found
        for (int i = 0; i < this.outputHandler.getSlots(); i++) {
            ItemStack stackInSlot = this.outputHandler.getStackInSlot(i);
            if (stackInSlot.isEmpty() || (stackInSlot.getItem() == result.getItem() && stackInSlot.getCount() + result.getCount() <= stackInSlot.getMaxStackSize())) {
                return i;
            }
        }
        return -1;
    }

    private boolean hasRecipe() {
        Optional<SolidingRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }

        if (inputHandler.getStackInSlot(0).getCount() < recipe.get().getInputCount()) {
            return false;
        }

        List<ItemStack> results = recipe.get().getOutputs();

        for (ItemStack result : results) {
            if (!canInsertAmountIntoOutputSlot(result) || !canInsertItemIntoOutputSlot(result.getItem())) {
                return false;
            }
        }

        return checkSlot(results);
    }

    private boolean checkSlot(List<ItemStack> results){
        int count = 0;
        int emptyCount = 0;
        for (ItemStack result : results){
            count++;
        }

        for (int i = 0; i < this.outputHandler.getSlots(); i++) {
            ItemStack stackInSlot = this.outputHandler.getStackInSlot(i);
            if(!stackInSlot.isEmpty()){
                for (ItemStack result : results){
                    if(stackInSlot.getItem() == result.getItem()){
                        if(stackInSlot.getCount() + result.getCount() <= 64){
                            emptyCount++;
                        }
                    }
                }
            }
            else {
                emptyCount++;
            }
        }

        return emptyCount >= count;
    }

    private Optional<SolidingRecipe> getCurrentRecipe(){
        return this.level.getRecipeManager().getRecipeFor(SolidingRecipe.Type.INSTANCE, new SimpleContainer(inputHandler.getStackInSlot(0)), level);
    }

    private boolean canInsertAmountIntoOutputSlot(ItemStack result) {
        for (int i = 0; i < this.outputHandler.getSlots(); i++) {
            ItemStack stackInSlot = this.outputHandler.getStackInSlot(i);
            if (stackInSlot.isEmpty() || (stackInSlot.getItem() == result.getItem() && stackInSlot.getCount() + result.getCount() <= stackInSlot.getMaxStackSize())) {
                return true;
            }
        }
        return false;
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        for (int i = 0; i < this.outputHandler.getSlots(); i++) {
            ItemStack stackInSlot = this.outputHandler.getStackInSlot(i);
            if (stackInSlot.isEmpty() || stackInSlot.getItem() == item) {
                return true;
            }
        }
        return false;
    }


    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    public ContainerData getData() {
        return data;
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

    public ItemStack getRenderStack() {
        return inputHandler.getStackInSlot(0);
    }
}
