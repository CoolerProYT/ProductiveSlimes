package com.coolerpromc.productiveslimes.block.entity;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.handler.CustomEnergyStorage;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.screen.EnergyGeneratorMenu;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
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
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class EnergyGeneratorBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(1){
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return canBurn(stack);
        }
    };
    protected final ContainerData data;

    private final CustomEnergyStorage energyHandler = new CustomEnergyStorage(10000, 0, 100, 0);

    private final ItemStackHandler upgradeHandler = new ItemStackHandler(4){
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return stack.getItem() == ModItems.ENERGY_MULTIPLIER_UPGRADE.get();
        }
    };

    private int progress = 0;
    private int maxProgress = 100;

    private LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyHandler);
    private LazyOptional<ItemStackHandler> items = LazyOptional.of(() -> itemHandler);

    public CustomEnergyStorage getEnergyHandler() {
        return energyHandler;
    }

    public ContainerData getData() {
        return data;
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    public ItemStackHandler getUpgradeHandler() {
        return upgradeHandler;
    }

    public EnergyGeneratorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ENERGY_GENERATOR_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> EnergyGeneratorBlockEntity.this.energyHandler.getEnergyStored();
                    case 1 -> EnergyGeneratorBlockEntity.this.energyHandler.getMaxEnergyStored();
                    case 2 -> EnergyGeneratorBlockEntity.this.progress;
                    case 3 -> EnergyGeneratorBlockEntity.this.maxProgress;
                    default -> throw new UnsupportedOperationException("Unexpected value: " + pIndex);
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> EnergyGeneratorBlockEntity.this.energyHandler.setEnergy(pValue);
                    case 2 -> EnergyGeneratorBlockEntity.this.progress = pValue;
                    case 3 -> EnergyGeneratorBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY) return energy.cast();
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return items.cast();

        return super.getCapability(cap, side);
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.productiveslimes.energy_generator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new EnergyGeneratorMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (this.level == null || this.level.isClientSide())
            return;

        AtomicBoolean isDirty = new AtomicBoolean(false);

        if (this.energyHandler.getEnergyStored() < this.energyHandler.getMaxEnergyStored()) {
            if (this.progress <= 0) {
                if (canBurn(this.itemHandler.getStackInSlot(0))) {
                    this.progress = this.maxProgress = getBurnTime(this.itemHandler.getStackInSlot(0));
                    this.itemHandler.getStackInSlot(0).shrink(1);
                    isDirty.set(true);
                }
            } else {
                this.progress--;

                int upgrade = 0;
                for (int i = 0; i < this.upgradeHandler.getSlots(); i++) {
                    if (this.upgradeHandler.getStackInSlot(i).isEmpty()) continue;

                    if (this.upgradeHandler.getStackInSlot(i).getItem() == ModItems.ENERGY_MULTIPLIER_UPGRADE.get()) {
                        upgrade++;
                    }
                }

                int energy = switch (upgrade) {
                    case 1 -> 10;
                    case 2 -> 15;
                    case 3 -> 25;
                    case 4 -> 40;
                    default -> 5;
                };

                this.energyHandler.addEnergy(energy);
                isDirty.set(true);
            }
        }

        if (!this.level.isClientSide) {
            for (Direction direction : Direction.values()) {
                Level level = this.level;

                Optional<LazyOptional<IEnergyStorage>> neighborEnergy = Optional.of(level.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()));

                if (neighborEnergy.get().isPresent()) {
                    LazyOptional<IEnergyStorage> neighborStorage = neighborEnergy.get();

                    neighborStorage.ifPresent(neighbor -> {
                        if (neighbor.canReceive()) {
                            int energyToExtract = Math.min(
                                    this.energyHandler.extractEnergy(1000, true),
                                    neighbor.receiveEnergy(1000, true)
                            );

                            // Perform the actual transfer
                            this.energyHandler.extractEnergy(energyToExtract, false);
                            neighbor.receiveEnergy(energyToExtract, false);
                        }
                    });
                }
            }
        }

        if (isDirty.get()) {
            sendUpdate();
        }
    }

    @Override
    public CompoundTag save(CompoundTag pTag) {
        pTag.put("Inventory", itemHandler.serializeNBT());
        pTag.put("Energy", energyHandler.serializeNBT());
        pTag.putInt("Progress", progress);
        pTag.putInt("MaxProgress", maxProgress);
        pTag.put("Upgrades", upgradeHandler.serializeNBT());

        return super.save(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        this.itemHandler.deserializeNBT(pTag.getCompound("Inventory"));
        this.energyHandler.deserializeNBT(pTag.get("Energy"));
        this.progress = pTag.getInt("Progress");
        this.maxProgress = pTag.getInt("MaxProgress");
        this.upgradeHandler.deserializeNBT(pTag.getCompound("Upgrades"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    private void sendUpdate() {
        setChanged();

        if(this.level != null)
            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
    }

    public int getBurnTime(ItemStack stack) {
        if(stack.getItem() == ModBlocks.ENERGY_SLIME_BLOCK.get().asItem()){
            return 1000;
        }
        else if (stack.getItem() == ModItems.ENERGY_SLIME_BALL.get()) {
            return 100;
        }

        return 0;
    }

    public boolean canBurn(ItemStack stack) {
        return getBurnTime(stack) > 0;
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
}
