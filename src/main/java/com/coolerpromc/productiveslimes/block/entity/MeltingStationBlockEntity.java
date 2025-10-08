package com.coolerpromc.productiveslimes.block.entity;

import com.coolerpromc.productiveslimes.handler.CustomEnergyStorage;
import com.coolerpromc.productiveslimes.recipe.MeltingRecipe;
import com.coolerpromc.productiveslimes.recipe.ModRecipes;
import com.coolerpromc.productiveslimes.screen.MeltingStationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class MeltingStationBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStacksResourceHandler bucketHandler = new ItemStacksResourceHandler(1){
        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            setChanged();
        }

        @Override
        public boolean isValid(int index, ItemResource resource) {
            return resource.getItem() == Items.BUCKET;
        }
    };

    private final ItemStacksResourceHandler inputHandler = new ItemStacksResourceHandler(1){
        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            setChanged();
        }

        @Override
        public boolean isValid(int index, ItemResource resource) {
            return resource.getItem() != Items.BUCKET;
        }
    };

    private final ItemStacksResourceHandler outputHandler = new ItemStacksResourceHandler(1){
        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            setChanged();
        }

        @Override
        public boolean isValid(int index, ItemResource resource) {
            return false;
        }
    };

    private final CustomEnergyStorage energyHandler = new CustomEnergyStorage(10000, 1000, 0, 0);

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public MeltingStationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MELTING_STATION_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> MeltingStationBlockEntity.this.progress;
                    case 1 -> MeltingStationBlockEntity.this.maxProgress;
                    case 2 -> MeltingStationBlockEntity.this.energyHandler.getAmountAsInt();
                    case 3 -> MeltingStationBlockEntity.this.energyHandler.getCapacityAsInt();
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> MeltingStationBlockEntity.this.progress = pValue;
                    case 1 -> MeltingStationBlockEntity.this.maxProgress = pValue;
                    case 2 -> MeltingStationBlockEntity.this.energyHandler.setEnergy(pValue);
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    public ItemStacksResourceHandler getBucketHandler() {
        return bucketHandler;
    }

    public ItemStacksResourceHandler getInputHandler() {
        return inputHandler;
    }

    public ItemStacksResourceHandler getOutputHandler() {
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
        inventory.setItem(0, bucketHandler.getResource(0).toStack(bucketHandler.getAmountAsInt(0)));
        inventory.setItem(1, inputHandler.getResource(0).toStack(inputHandler.getAmountAsInt(0)));
        inventory.setItem(2, outputHandler.getResource(0).toStack(outputHandler.getAmountAsInt(0)));

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.productiveslimes.melting_station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new MeltingStationMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(ValueOutput valueOutput) {
        bucketHandler.serialize(valueOutput.child("bucketHandler"));
        inputHandler.serialize(valueOutput.child("inputHandler"));
        outputHandler.serialize(valueOutput.child("outputHandler"));
        valueOutput.putInt("EnergyInventory", energyHandler.getAmountAsInt());
        valueOutput.putInt("melting_station.progress", progress);

        super.saveAdditional(valueOutput);
    }

    @Override
    protected void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);

        bucketHandler.deserialize(valueInput.childOrEmpty("bucketHandler"));
        inputHandler.deserialize(valueInput.childOrEmpty("inputHandler"));
        outputHandler.deserialize(valueInput.childOrEmpty("outputHandler"));
        energyHandler.setEnergy(valueInput.getIntOr("EnergyInventory", 0));
        progress = valueInput.getIntOr("melting_station.progress", 0);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        Optional<RecipeHolder<MeltingRecipe>> recipe = getCurrentRecipe();
        if(hasRecipe() && bucketHandler.getAmountAsInt(0) >= recipe.get().value().getOutputs().get(0).getCount() && energyHandler.getAmountAsInt() >= recipe.get().value().getEnergy()){
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
        Optional<RecipeHolder<MeltingRecipe>> recipe = getCurrentRecipe();
        if (recipe.isPresent()) {
            List<ItemStack> results = recipe.get().value().getOutputs();

            // Extract the input item from the input slot
            try(Transaction tx = Transaction.open(null)){
                int i1 = this.inputHandler.extract(0, this.inputHandler.getResource(0), recipe.get().value().getInputCount(), tx);
                int o1 = this.bucketHandler.extract(0, this.bucketHandler.getResource(0), recipe.get().value().getOutputs().get(0).getCount(), tx);

                if (i1 == recipe.get().value().getInputCount() && o1 == recipe.get().value().getOutputs().get(0).getCount()){
                    tx.commit();
                }
            }

            // Loop through each result item and find suitable output slots
            for (ItemStack result : results) {
                int outputSlot = findSuitableOutputSlot(result);
                if (outputSlot != -1) {
                    this.outputHandler.set(outputSlot, ItemResource.of(result.getItem()), this.outputHandler.getAmountAsInt(outputSlot) + result.getCount());
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
        for (int i = 0; i < this.outputHandler.size(); i++) {
            ItemStack stackInSlot = this.outputHandler.getResource(i).toStack(this.outputHandler.getAmountAsInt(i));
            if (stackInSlot.isEmpty() || (stackInSlot.getItem() == result.getItem() && stackInSlot.getCount() + result.getCount() <= stackInSlot.getMaxStackSize())) {
                return i;
            }
        }
        return -1;
    }

    private boolean hasRecipe() {
        Optional<RecipeHolder<MeltingRecipe>> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }

        if (inputHandler.getAmountAsInt(0) < recipe.get().value().getInputCount()) {
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

        for (int i = 0; i < this.outputHandler.size(); i++) {
            ItemStack stackInSlot = this.outputHandler.getResource(i).toStack(this.outputHandler.getAmountAsInt(i));
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

    private Optional<RecipeHolder<MeltingRecipe>> getCurrentRecipe(){
        ServerLevel level = (ServerLevel) this.level;
        return level.recipeAccess().getRecipeFor(ModRecipes.MELTING_TYPE.get(), new SingleRecipeInput(inputHandler.getResource(0).toStack(inputHandler.getAmountAsInt(0))), level);
    }

    private boolean canInsertAmountIntoOutputSlot(ItemStack result) {
        for (int i = 0; i < this.outputHandler.size(); i++) {
            ItemStack stackInSlot = this.outputHandler.getResource(i).toStack(this.outputHandler.getAmountAsInt(i));
            if (stackInSlot.isEmpty() || (stackInSlot.getItem() == result.getItem() && stackInSlot.getCount() + result.getCount() <= stackInSlot.getMaxStackSize())) {
                return true;
            }
        }
        return false;
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        for (int i = 0; i < this.outputHandler.size(); i++) {
            ItemStack stackInSlot = this.outputHandler.getResource(i).toStack(this.outputHandler.getAmountAsInt(i));
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
    public void preRemoveSideEffects(BlockPos p_394577_, BlockState p_394161_) {
        drops();
    }
}
