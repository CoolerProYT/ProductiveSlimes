package com.coolerpromc.productiveslimes.block.entity;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.datacomponent.ModDataComponents;
import com.coolerpromc.productiveslimes.handler.CustomEnergyStorage;
import com.coolerpromc.productiveslimes.handler.ImmutableFluidStack;
import com.coolerpromc.productiveslimes.recipe.ModRecipes;
//import com.coolerpromc.productiveslimes.recipe.SolidingRecipe;
import com.coolerpromc.productiveslimes.recipe.SolidingRecipe;
import com.coolerpromc.productiveslimes.recipe.custom.SingleFluidRecipeInput;
import com.coolerpromc.productiveslimes.screen.SolidingStationMenu;
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
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class SolidingStationBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler fillInputHandler = new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return stack.has(ModDataComponents.FLUID_STACK) || stack.getItem() instanceof BucketItem || stack.getItem() == ModBlocks.FLUID_TANK.asItem();
        }
    };

    private final ItemStackHandler fillOutputHandler = new ItemStackHandler(1){
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

    private final ItemStackHandler drainInputHandler = new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return stack.getItem() == Items.BUCKET || stack.getItem() == ModBlocks.FLUID_TANK.asItem();
        }
    };

    private final ItemStackHandler drainOutputHandler = new ItemStackHandler(1){
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

    private final FluidTank fluidTank = new FluidTank(16000){
        @Override
        protected void onContentsChanged() {
            setChanged();
            if (!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return true;
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

    public ItemStackHandler getFillInputHandler() {
        return fillInputHandler;
    }

    public ItemStackHandler getFillOutputHandler() {
        return fillOutputHandler;
    }

    public ItemStackHandler getDrainInputHandler() {
        return drainInputHandler;
    }

    public ItemStackHandler getDrainOutputHandler() {
        return drainOutputHandler;
    }

    public ItemStackHandler getOutputHandler() {
        return outputHandler;
    }

    public CustomEnergyStorage getEnergyHandler() {
        return energyHandler;
    }

    public FluidTank getFluidTank() {
        return fluidTank;
    }

    public FluidStack getFluid() {
        return fluidTank.getFluid();
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(5);
        inventory.setItem(0, fillInputHandler.getStackInSlot(0));
        inventory.setItem(1, fillOutputHandler.getStackInSlot(0));
        inventory.setItem(2, drainInputHandler.getStackInSlot(0));
        inventory.setItem(3, drainOutputHandler.getStackInSlot(0));
        inventory.setItem(4, outputHandler.getStackInSlot(0));

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
        pTag.put("FillInputInventory", fillInputHandler.serializeNBT(pRegistries));
        pTag.put("FillOutputInventory", fillOutputHandler.serializeNBT(pRegistries));
        pTag.put("DrainInputInventory", drainInputHandler.serializeNBT(pRegistries));
        pTag.put("DrainOutputInventory", drainOutputHandler.serializeNBT(pRegistries));
        pTag.put("OutputInventory", outputHandler.serializeNBT(pRegistries));
        pTag.putInt("EnergyInventory", energyHandler.getEnergyStored());

        pTag.putInt("soliding_station.progress", progress);
        pTag = fluidTank.writeToNBT(pRegistries, pTag);

        super.saveAdditional(pTag, pRegistries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);

        fillInputHandler.deserializeNBT(pRegistries, pTag.getCompound("FillInputInventory"));
        fillOutputHandler.deserializeNBT(pRegistries, pTag.getCompound("FillOutputInventory"));
        drainInputHandler.deserializeNBT(pRegistries, pTag.getCompound("DrainInputInventory"));
        drainOutputHandler.deserializeNBT(pRegistries, pTag.getCompound("DrainOutputInventory"));
        outputHandler.deserializeNBT(pRegistries, pTag.getCompound("OutputInventory"));
        energyHandler.setEnergy(pTag.getInt("EnergyInventory"));

        progress = pTag.getInt("soliding_station.progress");
        fluidTank.readFromNBT(pRegistries, pTag);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if(pLevel.isClientSide) {
            return;
        }

        if(fillInputHandler.getStackInSlot(0).getItem() instanceof BucketItem bucketItem && fluidTank.getFluidAmount() < fluidTank.getCapacity()){
            if(bucketItem != Items.BUCKET){
                FluidStack fluidStack = new FluidStack(bucketItem.content, 1000);

                if (fluidTank.fill(fluidStack, IFluidHandler.FluidAction.SIMULATE) != 0 && (fillOutputHandler.getStackInSlot(0).getItem() == Items.BUCKET || fillOutputHandler.getStackInSlot(0).isEmpty()) && fillOutputHandler.getStackInSlot(0).getCount() < Items.BUCKET.getDefaultMaxStackSize()){
                    fluidTank.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
                    fillInputHandler.extractItem(0, 1, false);
                    ItemStack result = new ItemStack(Items.BUCKET);
                    this.fillOutputHandler.setStackInSlot(0, new ItemStack(result.getItem(), this.fillOutputHandler.getStackInSlot(0).getCount() + result.getCount()));
                }
            }
        }
        else if(fillInputHandler.getStackInSlot(0).getItem() == ModBlocks.FLUID_TANK.asItem()){
            ItemStack stack = fillInputHandler.getStackInSlot(0);

            if (stack.has(ModDataComponents.FLUID_STACK)){
                ImmutableFluidStack immutableFluidStack = stack.getOrDefault(ModDataComponents.FLUID_STACK, new ImmutableFluidStack(FluidStack.EMPTY));
                FluidStack fluidStack = new FluidStack(immutableFluidStack.fluidStack().getFluid(), Math.min(1000, immutableFluidStack.fluidStack().getAmount()));
                if (fluidTank.fill(fluidStack, IFluidHandler.FluidAction.SIMULATE) != 0 && (fillOutputHandler.getStackInSlot(0).getItem() == ModBlocks.FLUID_TANK.asItem() || fillOutputHandler.getStackInSlot(0).isEmpty()) && fillOutputHandler.getStackInSlot(0).getCount() < ModBlocks.FLUID_TANK.asItem().getDefaultMaxStackSize()){
                    int fluidFilled = fluidTank.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
                    FluidStack newFluidStack = fluidStack.copy();
                    if (immutableFluidStack.fluidStack().getAmount() - fluidFilled <= 0){
                        stack.remove(ModDataComponents.FLUID_STACK);
                        fillInputHandler.extractItem(0, 1, false);
                        fillOutputHandler.setStackInSlot(0, stack);
                    }
                    else {
                        newFluidStack.setAmount(immutableFluidStack.fluidStack().getAmount() - fluidFilled);
                        stack.set(ModDataComponents.FLUID_STACK, new ImmutableFluidStack(newFluidStack));
                    }
                }
            }
        }

        if(drainInputHandler.getStackInSlot(0).getItem() == Items.BUCKET && fluidTank.getFluidAmount() >= 1000){
            if ((drainOutputHandler.getStackInSlot(0).getItem() == fluidTank.getFluid().getFluid().getBucket() && drainOutputHandler.getStackInSlot(0).getCount() < drainOutputHandler.getStackInSlot(0).getMaxStackSize()) || drainOutputHandler.getStackInSlot(0).isEmpty()){
                FluidStack fluidStack = fluidTank.drain(1000, IFluidHandler.FluidAction.SIMULATE);
                if (!fluidStack.isEmpty()){
                    fluidTank.drain(1000, IFluidHandler.FluidAction.EXECUTE);
                    ItemStack result = new ItemStack(fluidStack.getFluid().getBucket());
                    this.drainOutputHandler.setStackInSlot(0, new ItemStack(result.getItem(), this.drainOutputHandler.getStackInSlot(0).getCount() + result.getCount()));
                    drainInputHandler.extractItem(0, 1, false);
                }
            }
        }
        else if(drainInputHandler.getStackInSlot(0).getItem() == ModBlocks.FLUID_TANK.asItem() && fluidTank.getFluidAmount() > 0){
            ItemStack stack = drainInputHandler.getStackInSlot(0);

            if (stack.has(ModDataComponents.FLUID_STACK)){
                ImmutableFluidStack immutableFluidStack = stack.getOrDefault(ModDataComponents.FLUID_STACK, new ImmutableFluidStack(FluidStack.EMPTY));
                FluidStack fluidStack = immutableFluidStack.fluidStack();
                if (fluidTank.getFluid().getFluid().isSame(fluidStack.getFluid())){
                    if(fluidStack.getAmount() < FluidTankBlockEntity.capacity){
                        FluidStack fluidStack2 = fluidTank.drain(Math.min(fluidTank.getFluidAmount(), Math.min(1000, FluidTankBlockEntity.capacity - fluidStack.getAmount())), IFluidHandler.FluidAction.SIMULATE);
                        if (!fluidStack2.isEmpty()){
                            FluidStack fluidStack3 = fluidTank.drain(Math.min(fluidTank.getFluidAmount(), Math.min(1000, FluidTankBlockEntity.capacity - fluidStack.getAmount())), IFluidHandler.FluidAction.EXECUTE);
                            fluidStack.setAmount(fluidStack.getAmount() + fluidStack3.getAmount());
                            stack.set(ModDataComponents.FLUID_STACK, new ImmutableFluidStack(fluidStack));
                            }
                    }
                }
            }
            else{
                FluidStack fluidStack = fluidTank.drain(Math.min(fluidTank.getFluidAmount(), 1000), IFluidHandler.FluidAction.SIMULATE);
                if (!fluidStack.isEmpty()){
                    FluidStack fluidStack2 = fluidTank.drain(Math.min(fluidTank.getFluidAmount(), 1000), IFluidHandler.FluidAction.EXECUTE);
                    ImmutableFluidStack immutableFluidStack = new ImmutableFluidStack(fluidStack2);
                    stack.set(ModDataComponents.FLUID_STACK, immutableFluidStack);
                }
            }
        }

        Optional<RecipeHolder<SolidingRecipe>> recipe = getCurrentRecipe();
        if(hasRecipe() && energyHandler.getEnergyStored() >= recipe.get().value().energy()) {
            increaseCraftingProgress();
            setChanged(pLevel, pPos, pState);

            if(hasProgressFinished()) {
                energyHandler.removeEnergy(recipe.get().value().energy());
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
            List<ItemStack> results = recipe.get().value().output();

            this.fluidTank.drain(recipe.get().value().fluidStack().getAmount(), IFluidHandler.FluidAction.EXECUTE);

            for (ItemStack result : results) {
                int outputSlot = findSuitableOutputSlot(result);
                if (outputSlot != -1) {
                    this.outputHandler.setStackInSlot(outputSlot, new ItemStack(result.getItem(),
                            this.outputHandler.getStackInSlot(outputSlot).getCount() + result.getCount()));
                } else {
                    System.err.println("No suitable output slot found for item: " + result);
                }
            }
        }
    }

    private int findSuitableOutputSlot(ItemStack result) {
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

        if (fluidTank.getFluidAmount() < recipe.get().value().fluidStack().getAmount()) {
            return false;
        }

        List<ItemStack> results = recipe.get().value().output();

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
        ServerLevel level = (ServerLevel) this.level;
        return level.recipeAccess().getRecipeFor(ModRecipes.SOLIDING_TYPE.get(), new SingleFluidRecipeInput(fluidTank.getFluid()), level);
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
}
