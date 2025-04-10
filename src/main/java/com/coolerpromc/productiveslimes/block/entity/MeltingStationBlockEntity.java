package com.coolerpromc.productiveslimes.block.entity;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.datacomponent.ModDataComponents;
import com.coolerpromc.productiveslimes.util.CustomEnergyStorage;
import com.coolerpromc.productiveslimes.datacomponent.custom.ImmutableFluidStack;
import com.coolerpromc.productiveslimes.recipe.MeltingRecipe;
import com.coolerpromc.productiveslimes.recipe.ModRecipes;
import com.coolerpromc.productiveslimes.screen.MeltingStationMenu;
import com.coolerpromc.productiveslimes.util.ExtractOnlyFluidTank;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MeltingStationBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler drainInputSlot = new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return stack.getItem() == Items.BUCKET || stack.getItem() == ModBlocks.FLUID_TANK.asItem();
        }
    };

    private final ItemStackHandler drainOutputSlot = new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return false;
        }
    };

    private final ItemStackHandler inputHandler = new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return stack.getItem() != Items.BUCKET;
        }
    };

    private final ExtractOnlyFluidTank outputHandler = new ExtractOnlyFluidTank(16000){
        @Override
        protected void onContentsChanged() {
            setChanged();
            if (!level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
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
                    case 2 -> MeltingStationBlockEntity.this.energyHandler.getEnergyStored();
                    case 3 -> MeltingStationBlockEntity.this.energyHandler.getMaxEnergyStored();
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

    public ItemStackHandler getDrainInputSlot() {
        return drainInputSlot;
    }

    public ItemStackHandler getDrainOutputSlot() {
        return drainOutputSlot;
    }

    public ItemStackHandler getInputHandler() {
        return inputHandler;
    }

    public ExtractOnlyFluidTank getOutputHandler() {
        return outputHandler;
    }

    public FluidStack getFluid() {
        return outputHandler.getFluid();
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
        inventory.setItem(1, inputHandler.getStackInSlot(0));

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
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        pTag.put("DrainInputInventory", drainInputSlot.serializeNBT(pRegistries));
        pTag.put("DrainOutputInventory", drainOutputSlot.serializeNBT(pRegistries));
        pTag.put("InputInventory", inputHandler.serializeNBT(pRegistries));
        pTag.putInt("EnergyInventory", energyHandler.getEnergyStored());

        pTag.putInt("melting_station.progress", progress);
        pTag = outputHandler.writeToNBT(pRegistries, pTag);

        super.saveAdditional(pTag, pRegistries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);

        drainInputSlot.deserializeNBT(pRegistries, pTag.getCompoundOrEmpty("DrainInputInventory"));
        drainOutputSlot.deserializeNBT(pRegistries, pTag.getCompoundOrEmpty("DrainOutputInventory"));
        inputHandler.deserializeNBT(pRegistries, pTag.getCompoundOrEmpty("InputInventory"));;
        energyHandler.setEnergy(pTag.getIntOr("EnergyInventory", 0));

        progress = pTag.getIntOr("melting_station.progress", 0);
        outputHandler.readFromNBT(pRegistries, pTag);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = this.getBlockPos().relative(direction);
            IFluidHandler neighborStorage = level.getCapability(Capabilities.FluidHandler.BLOCK, neighborPos, direction.getOpposite());

            if (neighborStorage != null) {
                FluidStack availableFluid = outputHandler.getFluidInTank(0);
                FluidStack neighborFluid = neighborStorage.getFluidInTank(0);
                if ((!availableFluid.isEmpty() && availableFluid.getFluid().isSame(neighborFluid.getFluid())) || neighborFluid.isEmpty()) {
                    FluidStack simulatedDrain = outputHandler.drain(availableFluid.copy(), IFluidHandler.FluidAction.SIMULATE);
                    if (!simulatedDrain.isEmpty()) {
                        int simulatedFill = neighborStorage.fill(simulatedDrain, IFluidHandler.FluidAction.SIMULATE);
                        if (simulatedFill > 0) {
                            FluidStack drained = outputHandler.drain(new FluidStack(simulatedDrain.getFluid(), simulatedFill), IFluidHandler.FluidAction.EXECUTE);
                            if (!drained.isEmpty()) {
                                neighborStorage.fill(drained, IFluidHandler.FluidAction.EXECUTE);
                            }
                        }
                    }
                }
            }
        }

        if(drainInputSlot.getStackInSlot(0).getItem() == Items.BUCKET && outputHandler.getFluidAmount() >= 1000){
            if ((drainOutputSlot.getStackInSlot(0).getItem() == outputHandler.getFluid().getFluid().getBucket() && drainOutputSlot.getStackInSlot(0).getCount() < drainOutputSlot.getStackInSlot(0).getMaxStackSize()) || drainOutputSlot.getStackInSlot(0).isEmpty()){
                FluidStack fluidStack = outputHandler.drain(1000, IFluidHandler.FluidAction.SIMULATE);
                if (!fluidStack.isEmpty()){
                    outputHandler.drain(1000, IFluidHandler.FluidAction.EXECUTE);
                    ItemStack result = new ItemStack(fluidStack.getFluid().getBucket());
                    this.drainOutputSlot.setStackInSlot(0, new ItemStack(result.getItem(), this.drainOutputSlot.getStackInSlot(0).getCount() + result.getCount()));
                    drainInputSlot.extractItem(0, 1, false);
                }
            }
        }
        else if(drainInputSlot.getStackInSlot(0).getItem() == ModBlocks.FLUID_TANK.asItem() && outputHandler.getFluidAmount() > 0){
            ItemStack stack = drainInputSlot.getStackInSlot(0);

            if (stack.has(ModDataComponents.FLUID_STACK)){
                ImmutableFluidStack immutableFluidStack = stack.getOrDefault(ModDataComponents.FLUID_STACK, new ImmutableFluidStack(FluidStack.EMPTY));
                FluidStack fluidStack = immutableFluidStack.fluidStack();
                if (outputHandler.getFluid().getFluid().isSame(fluidStack.getFluid())){
                    if(fluidStack.getAmount() < FluidTankBlockEntity.capacity){
                        FluidStack fluidStack2 = outputHandler.drain(Math.min(outputHandler.getFluidAmount(), Math.min(1000, FluidTankBlockEntity.capacity - fluidStack.getAmount())), IFluidHandler.FluidAction.SIMULATE);
                        if (!fluidStack2.isEmpty()){
                            FluidStack fluidStack3 = outputHandler.drain(Math.min(outputHandler.getFluidAmount(), Math.min(1000, FluidTankBlockEntity.capacity - fluidStack.getAmount())), IFluidHandler.FluidAction.EXECUTE);
                            fluidStack.setAmount(fluidStack.getAmount() + fluidStack3.getAmount());
                            stack.set(ModDataComponents.FLUID_STACK, new ImmutableFluidStack(fluidStack));
                        }
                    }
                }
            }
            else{
                FluidStack fluidStack = outputHandler.drain(Math.min(outputHandler.getFluidAmount(), 1000), IFluidHandler.FluidAction.SIMULATE);
                if (!fluidStack.isEmpty()){
                    FluidStack fluidStack2 = outputHandler.drain(Math.min(outputHandler.getFluidAmount(), 1000), IFluidHandler.FluidAction.EXECUTE);
                    ImmutableFluidStack immutableFluidStack = new ImmutableFluidStack(fluidStack2);
                    stack.set(ModDataComponents.FLUID_STACK, immutableFluidStack);
                }
            }
        }

        Optional<RecipeHolder<MeltingRecipe>> recipe = getCurrentRecipe();
        if(hasRecipe() && energyHandler.getEnergyStored() >= recipe.get().value().energy()){
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
        Optional<RecipeHolder<MeltingRecipe>> recipe = getCurrentRecipe();
        if (recipe.isPresent()) {
            FluidStack results = recipe.get().value().output();
            this.inputHandler.extractItem(0, recipe.get().value().inputItems().count(), false);
            this.outputHandler.internalFill(results, IFluidHandler.FluidAction.EXECUTE);
        }
    }

    private boolean hasRecipe() {
        Optional<RecipeHolder<MeltingRecipe>> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }

        if (inputHandler.getStackInSlot(0).getCount() < recipe.get().value().inputItems().count()) {
            return false;
        }

        FluidStack result = recipe.get().value().output();

        if (!canInsertAmountIntoOutputSlot(result) || !canInsertItemIntoOutputSlot(result.getFluid())) {
            return false;
        }

        return checkSlot(result);
    }

    private boolean checkSlot(FluidStack results){
        return outputHandler.getFluid().isEmpty() || outputHandler.getFluid().getFluid().isSame(results.getFluid()) && outputHandler.getFluidAmount() + results.getAmount() <= outputHandler.getCapacity();
    }

    private Optional<RecipeHolder<MeltingRecipe>> getCurrentRecipe(){
        ServerLevel level = (ServerLevel) this.level;
        return level.recipeAccess().getRecipeFor(ModRecipes.MELTING_TYPE.get(), new SingleRecipeInput(inputHandler.getStackInSlot(0)), level);
    }

    private boolean canInsertAmountIntoOutputSlot(FluidStack result) {
        return outputHandler.getFluidAmount() + result.getAmount() <= outputHandler.getCapacity();
    }

    private boolean canInsertItemIntoOutputSlot(Fluid item) {
        return outputHandler.getFluid().isEmpty() || outputHandler.getFluid().getFluid().isSame(item);
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

    @Override
    public void preRemoveSideEffects(BlockPos p_394577_, BlockState p_394161_) {
        drops();
    }
}
