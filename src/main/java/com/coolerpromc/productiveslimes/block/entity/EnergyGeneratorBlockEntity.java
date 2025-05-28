package com.coolerpromc.productiveslimes.block.entity;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.handler.CustomEnergyStorage;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.screen.EnergyGeneratorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    public Component getDisplayName() {
        return Component.translatable("block.productiveslimes.energy_generator");
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
                BlockPos neighborPos = this.getBlockPos().relative(direction);

                Optional<IEnergyStorage> neighborEnergy = Optional.ofNullable(level.getCapability(Capabilities.EnergyStorage.BLOCK, neighborPos, direction.getOpposite()));

                if (neighborEnergy.isPresent()) {
                    IEnergyStorage neighborStorage = neighborEnergy.get();

                    if (neighborStorage.canReceive()) {
                        int energyToExtract = Math.min(this.energyHandler.extractEnergy(1000, true), neighborStorage.receiveEnergy(1000, true));

                        this.energyHandler.extractEnergy(energyToExtract, false);
                        neighborStorage.receiveEnergy(energyToExtract, false);
                    }
                }
            }
        }

        if (isDirty.get()) {
            sendUpdate();
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);

        pTag.put("Inventory", itemHandler.serializeNBT(pRegistries));
        pTag.put("Energy", energyHandler.serializeNBT(pRegistries));
        pTag.putInt("Progress", progress);
        pTag.putInt("MaxProgress", maxProgress);
        pTag.put("Upgrades", upgradeHandler.serializeNBT(pRegistries));
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);

        this.itemHandler.deserializeNBT(pRegistries, pTag.getCompound("Inventory"));
        this.energyHandler.deserializeNBT(pRegistries, pTag.get("Energy"));
        this.progress = pTag.getInt("Progress");
        this.maxProgress = pTag.getInt("MaxProgress");
        this.upgradeHandler.deserializeNBT(pRegistries, pTag.getCompound("Upgrades"));
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
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
}
