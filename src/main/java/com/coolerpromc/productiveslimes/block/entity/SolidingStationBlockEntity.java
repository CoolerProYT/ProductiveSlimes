package com.coolerpromc.productiveslimes.block.entity;

import com.coolerpromc.productiveslimes.handler.CustomEnergyStorage;
import com.coolerpromc.productiveslimes.recipe.ModRecipes;
import com.coolerpromc.productiveslimes.recipe.SolidingRecipe;
import com.coolerpromc.productiveslimes.screen.SolidingStationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

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

    @Override
    public void onLoad() {
        super.onLoad();
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
        return Component.translatable("block.productiveslimes.soliding_station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new SolidingStationMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        pTag.put("InputInventory", inputHandler.serializeNBT(pRegistries));
        pTag.put("OutputInventory", outputHandler.serializeNBT(pRegistries));
        pTag.putInt("EnergyInventory", energyHandler.getEnergyStored());

        pTag.putInt("soliding_station.progress", progress);

        super.saveAdditional(pTag, pRegistries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);

        inputHandler.deserializeNBT(pRegistries, pTag.getCompound("InputInventory"));
        outputHandler.deserializeNBT(pRegistries, pTag.getCompound("OutputInventory"));
        energyHandler.setEnergy(pTag.getInt("EnergyInventory"));

        progress = pTag.getInt("soliding_station.progress");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        Optional<RecipeHolder<SolidingRecipe>> recipe = getCurrentRecipe();
        if(hasRecipe() && energyHandler.getEnergyStored() >= recipe.get().value().getEnergy()) {
            increaseCraftingProgress();
            setChanged(pLevel, pPos, pState);

            if(hasProgressFinished()) {
                energyHandler.removeEnergy(recipe.get().value().getEnergy());
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
        Optional<RecipeHolder<SolidingRecipe>> recipe = getCurrentRecipe();
        if (recipe.isPresent()) {
            List<ItemStack> results = recipe.get().value().getOutputs();

            // Extract the input item from the input slot
            this.inputHandler.extractItem(0, recipe.get().value().getInputCount(), false);

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
        Optional<RecipeHolder<SolidingRecipe>> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }

        if (inputHandler.getStackInSlot(0).getCount() < recipe.get().value().getInputCount()) {
            return false;
        }

        List<ItemStack> results = recipe.get().value().getOutputs();

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

    private Optional<RecipeHolder<SolidingRecipe>> getCurrentRecipe(){
        return this.level.getRecipeManager().getRecipeFor(ModRecipes.SOLIDING_TYPE.get(), new SingleRecipeInput(inputHandler.getStackInSlot(0)), level);
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

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }

    public ItemStack getRenderStack() {
        return inputHandler.getStackInSlot(0);
    }
}
