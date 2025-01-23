package com.coolerpromc.productiveslimes.block.entity;

import com.coolerpromc.productiveslimes.handler.CustomEnergyStorage;
import com.coolerpromc.productiveslimes.handler.ModClientboundBlockEntityDataPacket;
import com.coolerpromc.productiveslimes.recipe.SqueezingRecipe;
import com.coolerpromc.productiveslimes.screen.SlimeSqueezerMenu;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.antlr.v4.runtime.misc.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class SlimeSqueezerBlockEntity extends TileEntity implements INamedContainerProvider, ITickableTileEntity {
    private final ItemStackHandler inputHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return true;
        }
    };
    private final ItemStackHandler outputHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return false;
        }
    };
    private final CustomEnergyStorage energyHandler = new CustomEnergyStorage(10000, 1000, 0, 0);

    protected final IIntArray data;
    private int progress = 0;
    private int maxProgress = 78;

    private LazyOptional<CustomEnergyStorage> energy = LazyOptional.of(() -> energyHandler);
    private LazyOptional<ItemStackHandler> input = LazyOptional.of(() -> inputHandler);
    private LazyOptional<ItemStackHandler> output = LazyOptional.of(() -> outputHandler);

    public SlimeSqueezerBlockEntity() {
        super(ModBlockEntities.SLIME_SQUEEZER_BE.get());
        this.data = new IIntArray() {
            @Override
            public int get(int pIndex) {
                switch (pIndex) {
                    case 0:
                        return SlimeSqueezerBlockEntity.this.progress;
                    case 1:
                        return SlimeSqueezerBlockEntity.this.maxProgress;
                    case 2:
                        return SlimeSqueezerBlockEntity.this.energyHandler.getEnergyStored();
                    case 3:
                        return SlimeSqueezerBlockEntity.this.energyHandler.getMaxEnergyStored();
                    default:
                        return 0;
                }
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0:
                        SlimeSqueezerBlockEntity.this.progress = pValue;
                        break;
                    case 1:
                        SlimeSqueezerBlockEntity.this.maxProgress = pValue;
                        break;
                    case 2:
                        SlimeSqueezerBlockEntity.this.energyHandler.setEnergy(pValue);
                        break;
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

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY) {
            return energy.cast();
        } else {
            if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                if (side != Direction.DOWN) {
                    return input.cast();
                } else {
                    return output.cast();
                }
            }
        }

        return super.getCapability(cap, side);
    }

    public void drops() {
        Inventory inventory = new Inventory(3);
        inventory.setItem(0, inputHandler.getStackInSlot(0));
        inventory.setItem(1, outputHandler.getStackInSlot(0));
        inventory.setItem(2, outputHandler.getStackInSlot(1));
        InventoryHelper.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("block.productiveslimes.slime_squeezer");
    }

    @Nullable
    @Override
    public Container createMenu(int containerId, PlayerInventory playerInventory, PlayerEntity player) {
        return new SlimeSqueezerMenu(containerId, playerInventory, this, this.data);
    }

    @Override
    public CompoundNBT save(CompoundNBT pTag) {
        pTag.put("InputInventory", inputHandler.serializeNBT());
        pTag.put("OutputInventory", outputHandler.serializeNBT());
        pTag.putInt("EnergyInventory", energyHandler.getEnergyStored());
        pTag.putInt("slime_squeezer.progress", progress);

        return super.save(pTag);
    }

    @Override
    public void load(BlockState state, CompoundNBT pTag) {
        super.load(state, pTag);
        inputHandler.deserializeNBT(pTag.getCompound("InputInventory"));
        outputHandler.deserializeNBT(pTag.getCompound("OutputInventory"));
        energyHandler.setEnergy(pTag.getInt("EnergyInventory"));
        progress = pTag.getInt("slime_squeezer.progress");
    }

    @Override
    public void tick() {
        Optional<SqueezingRecipe> recipe = getCurrentRecipe();
        if (hasRecipe() && energyHandler.getEnergyStored() >= recipe.get().getEnergy()) {
            increaseCraftingProgress();
            setChanged();
            if (hasProgressFinished()) {
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
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    private void craftItem() {
        Optional<SqueezingRecipe> recipe = getCurrentRecipe();
        if (recipe.isPresent()) {
            List<ItemStack> results = recipe.get().getOutputs();
            // Extract the input item from the input slot
            this.inputHandler.extractItem(0, 1, false);
            // Loop through each result item and find suitable output slots
            for (ItemStack result : results) {
                int outputSlot = findSuitableOutputSlot(result);
                if (outputSlot != -1) {
                    this.outputHandler.setStackInSlot(outputSlot, new ItemStack(result.getItem(), this.outputHandler.getStackInSlot(outputSlot).getCount() + result.getCount()));
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
        Optional<SqueezingRecipe> recipe = getCurrentRecipe();
        if (!recipe.isPresent()) {
            return false;
        }
        if (inputHandler.getStackInSlot(0).getCount() < 1) {
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

    private boolean checkSlot(List<ItemStack> results) {
        int count = 0;
        int emptyCount = 0;
        for (ItemStack result : results) {
            count++;
        }
        for (int i = 0; i < this.outputHandler.getSlots(); i++) {
            ItemStack stackInSlot = this.outputHandler.getStackInSlot(i);
            if (!stackInSlot.isEmpty()) {
                for (ItemStack result : results) {
                    if (stackInSlot.getItem() == result.getItem()) {
                        if (stackInSlot.getCount() + result.getCount() <= 64) {
                            emptyCount++;
                        }
                    }
                }
            } else {
                emptyCount++;
            }
        }
        return emptyCount >= count;
    }

    private Optional<SqueezingRecipe> getCurrentRecipe() {
        return this.level.getRecipeManager().getRecipeFor(SqueezingRecipe.Type.INSTANCE, new Inventory(inputHandler.getStackInSlot(0)), level);
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
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public IIntArray getData() {
        return data;
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

    public ItemStack getInputStack() {
        return inputHandler.getStackInSlot(0);
    }

    public ItemStack getOutputStack(int slot) {
        return outputHandler.getStackInSlot(slot);
    }
}