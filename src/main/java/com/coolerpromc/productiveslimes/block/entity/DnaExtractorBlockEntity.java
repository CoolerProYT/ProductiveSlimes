package com.coolerpromc.productiveslimes.block.entity;

import com.coolerpromc.productiveslimes.handler.CustomEnergyStorage;
import com.coolerpromc.productiveslimes.recipe.DnaExtractingRecipe;
import com.coolerpromc.productiveslimes.recipe.ModRecipes;
import com.coolerpromc.productiveslimes.screen.DnaExtractorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
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
import java.util.Random;

public class DnaExtractorBlockEntity extends BlockEntity implements MenuProvider {
    private float rotation;
    private final ItemStacksResourceHandler inputHandler = new ItemStacksResourceHandler(1){
        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            setChanged();
            if (level != null && !level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isValid(int index, ItemResource resource) {
            return true;
        }
    };

    private final ItemStacksResourceHandler outputHandler = new ItemStacksResourceHandler(2){
        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            setChanged();
            if (level != null && !level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isValid(int index, ItemResource resource) {
            return false;
        }
    };

    private final CustomEnergyStorage energyHandler = new CustomEnergyStorage(10000, 1000, 0,0);

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public DnaExtractorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.DNA_EXTRACTOR_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> DnaExtractorBlockEntity.this.progress;
                    case 1 -> DnaExtractorBlockEntity.this.maxProgress;
                    case 2 -> DnaExtractorBlockEntity.this.energyHandler.getAmountAsInt();
                    case 3 -> DnaExtractorBlockEntity.this.energyHandler.getCapacityAsInt();
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> DnaExtractorBlockEntity.this.progress = pValue;
                    case 1 -> DnaExtractorBlockEntity.this.maxProgress = pValue;
                    case 2 -> DnaExtractorBlockEntity.this.energyHandler.setEnergy(pValue);
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
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
        inventory.setItem(0, inputHandler.getResource(0).toStack(inputHandler.getAmountAsInt(0)));
        inventory.setItem(1, outputHandler.getResource(0).toStack(outputHandler.getAmountAsInt(0)));
        inventory.setItem(2, outputHandler.getResource(1).toStack(outputHandler.getAmountAsInt(1)));

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.productiveslimes.dna_extractor");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new DnaExtractorMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);

        inputHandler.serialize(valueOutput.child("inputHandler"));
        outputHandler.serialize(valueOutput.child("outputHandler"));
        valueOutput.putInt("EnergyInventory", energyHandler.getAmountAsInt());
        valueOutput.putInt("dna_extractor.progress", progress);
    }

    @Override
    protected void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);

        inputHandler.deserialize(valueInput.childOrEmpty("inputHandler"));
        outputHandler.deserialize(valueInput.childOrEmpty("outputHandler"));
        energyHandler.setEnergy(valueInput.getIntOr("EnergyInventory", 0));
        progress = valueInput.getIntOr("dna_extractor.progress", 0);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        Optional<RecipeHolder<DnaExtractingRecipe>> recipe = getCurrentRecipe();
        if(hasRecipe() && energyHandler.getAmountAsInt() >= recipe.get().value().getEnergy()){
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
        Optional<RecipeHolder<DnaExtractingRecipe>> recipe = getCurrentRecipe();
        if (recipe.isPresent()) {
            List<ItemStack> results = recipe.get().value().getOutputs();

            // Extract the input item from the input slot
            try(Transaction tx = Transaction.open(null)){
                if (this.inputHandler.extract(0, this.inputHandler.getResource(0), recipe.get().value().getInputCount(), tx) == recipe.get().value().getInputCount()){
                    tx.commit();
                }
            }

            // Loop through each result item and find suitable output slots
            for (ItemStack result : results) {
                int outputSlot = findSuitableOutputSlot(result);
                if (outputSlot != -1) {
                    if (result.getItem() == Items.SLIME_BALL){
                        this.outputHandler.set(outputSlot, ItemResource.of(result.getItem()), this.outputHandler.getAmountAsInt(outputSlot) + result.getCount());
                    }
                    else{
                        Random random = new Random();
                        float chance = recipe.get().value().getOutputChance();
                        if (random.nextFloat() < chance){
                            this.outputHandler.set(outputSlot, ItemResource.of(result.getItem()), this.outputHandler.getAmountAsInt(outputSlot) + result.getCount());
                        }
                    }

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
        Optional<RecipeHolder<DnaExtractingRecipe>> recipe = getCurrentRecipe();

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

    private Optional<RecipeHolder<DnaExtractingRecipe>> getCurrentRecipe(){
        ServerLevel level = (ServerLevel) this.level;
        return level.recipeAccess().getRecipeFor(ModRecipes.DNA_EXTRACTING_TYPE.get(), new SingleRecipeInput(inputHandler.getResource(0).toStack(inputHandler.getAmountAsInt(0))), level);
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

    public ItemStack getRenderStack() {
        if (outputHandler.getResource(0).isEmpty() && outputHandler.getResource(1).isEmpty()) {
            return inputHandler.getResource(0).toStack(inputHandler.getAmountAsInt(0));
        }
        else {
            if (!outputHandler.getResource(0).isEmpty() && outputHandler.getResource(0).getItem() != Items.SLIME_BALL) {
                return outputHandler.getResource(0).toStack(outputHandler.getAmountAsInt(0));
            }
            else {
                if (outputHandler.getResource(1).isEmpty()){
                    return outputHandler.getResource(0).toStack(outputHandler.getAmountAsInt(0));
                }
                else {
                    return outputHandler.getResource(1).toStack(outputHandler.getAmountAsInt(1));

                }
            }
        }
    }

    public float getRenderingRotation() {
        rotation += 1f;
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
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }

    @Override
    public void preRemoveSideEffects(BlockPos p_394577_, BlockState p_394161_) {
        drops();
    }
}
