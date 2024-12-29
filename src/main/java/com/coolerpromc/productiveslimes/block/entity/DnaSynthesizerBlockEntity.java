package com.coolerpromc.productiveslimes.block.entity;

import com.coolerpromc.productiveslimes.handler.CustomEnergyStorage;
import com.coolerpromc.productiveslimes.recipe.DnaSynthesizingRecipe;
import com.coolerpromc.productiveslimes.screen.DnaSynthesizerMenu;
import com.coolerpromc.productiveslimes.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class DnaSynthesizerBlockEntity extends BlockEntity implements MenuProvider {
    private float rotation;
    private final CustomEnergyStorage energyHandler = new CustomEnergyStorage(10000, 1000, 0,0);
    private final ItemStackHandler inputHandler = new ItemStackHandler(3){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            if (slot != 2){
                return stack.is(ModTags.Items.DNA_ITEM);
            }
            else {
                return !(stack.is(ModTags.Items.DNA_ITEM));
            }
        }
    };

    private final ItemStackHandler outputHandler = new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return false;
        }
    };

    private final ItemStackHandler eggHandler = new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return stack.getItem() == Items.EGG;
        }
    };

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 120;

    public DnaSynthesizerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.DNA_SYNTHESIZER_BE.get(), pPos, pBlockState);

        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> DnaSynthesizerBlockEntity.this.progress;
                    case 1 -> DnaSynthesizerBlockEntity.this.maxProgress;
                    case 2 -> DnaSynthesizerBlockEntity.this.energyHandler.getEnergyStored();
                    case 3 -> DnaSynthesizerBlockEntity.this.energyHandler.getMaxEnergyStored();
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> DnaSynthesizerBlockEntity.this.progress = pValue;
                    case 1 -> DnaSynthesizerBlockEntity.this.maxProgress = pValue;
                    case 2 -> DnaSynthesizerBlockEntity.this.energyHandler.setEnergy(pValue);
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
        if (cap == CapabilityEnergy.ENERGY){
            return energy.cast();
        }

        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            if (side == Direction.UP){
                return egg.cast();
            }
            else if (side == Direction.DOWN){
                return output.cast();
            }
            else{
                return input.cast();
            }
        }

        return super.getCapability(cap, side);
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(5);
        inventory.setItem(0, inputHandler.getStackInSlot(0));
        inventory.setItem(1, inputHandler.getStackInSlot(1));
        inventory.setItem(2, inputHandler.getStackInSlot(2));
        inventory.setItem(3, outputHandler.getStackInSlot(0));
        inventory.setItem(4, eggHandler.getStackInSlot(0));

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.productiveslimes.dna_synthesizer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new DnaSynthesizerMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("InputSlot", inputHandler.serializeNBT());
        pTag.put("OutputSlot", outputHandler.serializeNBT());
        pTag.put("EggSlot", eggHandler.serializeNBT());
        pTag.putInt("Energy", energyHandler.getEnergyStored());

        pTag.putInt("dna_synthesizing.progress", progress);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        inputHandler.deserializeNBT(pTag.getCompound("InputSlot"));
        outputHandler.deserializeNBT(pTag.getCompound("OutputSlot"));
        eggHandler.deserializeNBT(pTag.getCompound("EggSlot"));
        energyHandler.setEnergy(pTag.getInt("Energy"));

        progress = pTag.getInt("dna_synthesizing.progress");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        Optional<DnaSynthesizingRecipe> recipe = getCurrentRecipe();

        if(hasRecipe() && energyHandler.getEnergyStored() >= recipe.get().getEnergy() && !eggHandler.getStackInSlot(0).isEmpty() && inputHandler.getStackInSlot(2).getCount() >= recipe.get().getInputCount()){
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

        if (recipe.isEmpty()) {
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

    private Optional<DnaSynthesizingRecipe> getCurrentRecipe(){
        SimpleContainer input = new SimpleContainer(inputHandler.getStackInSlot(0), inputHandler.getStackInSlot(1), inputHandler.getStackInSlot(2));
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

    public ContainerData getData() {
        return data;
    }

    public float getRenderingRotation() {
        rotation += 0.5f;
        if(rotation >= 360) {
            rotation = 0;
        }
        return rotation;
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
}
