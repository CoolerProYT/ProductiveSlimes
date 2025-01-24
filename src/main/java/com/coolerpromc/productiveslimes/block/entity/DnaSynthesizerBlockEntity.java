package com.coolerpromc.productiveslimes.block.entity;

import com.coolerpromc.productiveslimes.handler.CustomEnergyStorage;
import com.coolerpromc.productiveslimes.recipe.DnaSynthesizingRecipe;
import com.coolerpromc.productiveslimes.screen.DnaSynthesizerMenu;
import com.coolerpromc.productiveslimes.util.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.antlr.v4.runtime.misc.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class DnaSynthesizerBlockEntity extends TileEntity implements INamedContainerProvider, ITickableTileEntity {
    private float rotation;
    private final CustomEnergyStorage energyHandler = new CustomEnergyStorage(10000, 1000, 0, 0);
    private final ItemStackHandler inputHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            if (slot != 2) {
                return stack.getItem().is(ModTags.Items.DNA_ITEM);
            } else {
                return !(stack.getItem().is(ModTags.Items.DNA_ITEM));
            }
        }
    };

    private final ItemStackHandler outputHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return false;
        }
    };

    private final ItemStackHandler eggHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return stack.getItem() == Items.EGG;
        }
    };

    protected final IIntArray data;
    private int progress = 0;
    private int maxProgress = 120;

    public DnaSynthesizerBlockEntity() {
        super(ModBlockEntities.DNA_SYNTHESIZER_BE.get());

        this.data = new IIntArray() {
            @Override
            public int get(int pIndex) {
                switch (pIndex) {
                    case 0:
                        return DnaSynthesizerBlockEntity.this.progress;
                    case 1:
                        return DnaSynthesizerBlockEntity.this.maxProgress;
                    case 2:
                        return DnaSynthesizerBlockEntity.this.energyHandler.getEnergyStored();
                    case 3:
                        return DnaSynthesizerBlockEntity.this.energyHandler.getMaxEnergyStored();
                    default:
                        return 0;
                }
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0:
                        DnaSynthesizerBlockEntity.this.progress = pValue;
                        break;
                    case 1:
                        DnaSynthesizerBlockEntity.this.maxProgress = pValue;
                        break;
                    case 2:
                        DnaSynthesizerBlockEntity.this.energyHandler.setEnergy(pValue);
                        break;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    public CustomEnergyStorage getEnergyHandler() {
        return energyHandler;
    }

    public ItemStackHandler getInputHandler() {
        return inputHandler;
    }

    public ItemStackHandler getOutputHandler() {
        return outputHandler;
    }

    public ItemStackHandler getEggHandler() {
        return eggHandler;
    }

    private LazyOptional<CustomEnergyStorage> energy = LazyOptional.of(() -> energyHandler);
    private LazyOptional<ItemStackHandler> input = LazyOptional.of(() -> inputHandler);
    private LazyOptional<ItemStackHandler> output = LazyOptional.of(() -> outputHandler);
    private LazyOptional<ItemStackHandler> egg = LazyOptional.of(() -> eggHandler);

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY) {
            return energy.cast();
        }

        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == Direction.UP) {
                return egg.cast();
            } else if (side == Direction.DOWN) {
                return output.cast();
            } else {
                return input.cast();
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    protected void invalidateCaps() {
        super.invalidateCaps();
        energy.invalidate();
        input.invalidate();
        output.invalidate();
        egg.invalidate();
    }

    public void drops() {
        Inventory inventory = new Inventory(5);
        inventory.setItem(0, inputHandler.getStackInSlot(0));
        inventory.setItem(1, inputHandler.getStackInSlot(1));
        inventory.setItem(2, inputHandler.getStackInSlot(2));
        inventory.setItem(3, outputHandler.getStackInSlot(0));
        inventory.setItem(4, eggHandler.getStackInSlot(0));

        InventoryHelper.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("block.productiveslimes.dna_synthesizer");
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new DnaSynthesizerMenu(i, playerInventory, this, this.data);
    }

    @Override
    public CompoundNBT save(CompoundNBT pTag) {
        pTag.put("InputSlot", inputHandler.serializeNBT());
        pTag.put("OutputSlot", outputHandler.serializeNBT());
        pTag.put("EggSlot", eggHandler.serializeNBT());
        pTag.putInt("Energy", energyHandler.getEnergyStored());

        pTag.putInt("dna_synthesizing.progress", progress);

        return super.save(pTag);
    }

    @Override
    public void load(BlockState state, CompoundNBT pTag) {
        super.load(state, pTag);
        inputHandler.deserializeNBT(pTag.getCompound("InputSlot"));
        outputHandler.deserializeNBT(pTag.getCompound("OutputSlot"));
        eggHandler.deserializeNBT(pTag.getCompound("EggSlot"));
        energyHandler.setEnergy(pTag.getInt("Energy"));

        progress = pTag.getInt("dna_synthesizing.progress");
    }

    @Override
    public void tick() {
        Optional<DnaSynthesizingRecipe> recipe = getCurrentRecipe();

        if (hasRecipe() && energyHandler.getEnergyStored() >= recipe.get().getEnergy() && !eggHandler.getStackInSlot(0).isEmpty() && inputHandler.getStackInSlot(2).getCount() >= recipe.get().getInputCount()) {
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
    }

    private void craftItem() {
        Optional<DnaSynthesizingRecipe> recipe = getCurrentRecipe();
        if (recipe.isPresent()) {
            List<ItemStack> results = recipe.get().getOutput();

            // Extract the input item from the input slot
            this.inputHandler.extractItem(0, 1, false);
            this.inputHandler.extractItem(1, 1, false);
            this.inputHandler.extractItem(2, recipe.get().getInputCount(), false);
            this.eggHandler.extractItem(0, 1, false);

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
        Optional<DnaSynthesizingRecipe> recipe = getCurrentRecipe();

        if (!recipe.isPresent()) {
            return false;
        }

        List<ItemStack> results = recipe.get().getOutput();

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

    private Optional<DnaSynthesizingRecipe> getCurrentRecipe() {
        Inventory input = new Inventory(inputHandler.getStackInSlot(0), inputHandler.getStackInSlot(1), inputHandler.getStackInSlot(2));
        return this.level.getRecipeManager().getRecipeFor(DnaSynthesizingRecipe.Type.INSTANCE, input, level);
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

    public IIntArray getData() {
        return data;
    }

    public float getRenderingRotation() {
        rotation += 0.5f;
        if (rotation >= 360) {
            rotation = 0;
        }
        return rotation;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT tag = new CompoundNBT();
        save(tag);
        return new SUpdateTileEntityPacket(this.getBlockPos(), 1, tag);
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
