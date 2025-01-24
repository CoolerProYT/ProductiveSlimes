package com.coolerpromc.productiveslimes.block.entity;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.handler.CustomEnergyStorage;
import com.coolerpromc.productiveslimes.handler.ModClientboundBlockEntityDataPacket;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.screen.EnergyGeneratorMenu;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
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
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.antlr.v4.runtime.misc.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class EnergyGeneratorBlockEntity extends TileEntity implements INamedContainerProvider, ITickableTileEntity {
    private final ItemStackHandler itemHandler = new ItemStackHandler(1){
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return canBurn(stack);
        }
    };
    protected final IIntArray data;

    private final CustomEnergyStorage energyHandler = new CustomEnergyStorage(10000, 0, 100, 0){
        @Override
        public boolean canExtract() {
            return true;
        }
    };

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

    private final LazyOptional<IEnergyStorage> energy = LazyOptional.of(this::getEnergyHandler);
    private final LazyOptional<ItemStackHandler> items = LazyOptional.of(() -> itemHandler);

    public IEnergyStorage getEnergyHandler() {
        return energyHandler;
    }

    public IIntArray getData() {
        return data;
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    public ItemStackHandler getUpgradeHandler() {
        return upgradeHandler;
    }

    public EnergyGeneratorBlockEntity() {
        super(ModBlockEntities.ENERGY_GENERATOR_BE.get());
        this.data = new IIntArray() {
            @Override
            public int get(int pIndex) {
                switch (pIndex) {
                    case 0 : return EnergyGeneratorBlockEntity.this.energyHandler.getEnergyStored();
                    case 1 : return EnergyGeneratorBlockEntity.this.energyHandler.getMaxEnergyStored();
                    case 2 : return EnergyGeneratorBlockEntity.this.progress;
                    case 3 : return EnergyGeneratorBlockEntity.this.maxProgress;
                    default : return 0;
                }
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 : EnergyGeneratorBlockEntity.this.energyHandler.setEnergy(pValue); break;
                    case 2 : EnergyGeneratorBlockEntity.this.progress = pValue; break;
                    case 3 : EnergyGeneratorBlockEntity.this.maxProgress = pValue; break;
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
    protected void invalidateCaps() {
        super.invalidateCaps();
        energy.invalidate();
        items.invalidate();
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("block.productiveslimes.energy_generator");
    }

    @Nullable
    @Override
    public Container createMenu(int pContainerId, PlayerInventory pPlayerInventory, PlayerEntity pPlayer) {
        return new EnergyGeneratorMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public void tick() {
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

                int energy = 5;

                switch (upgrade) {
                    case 1 : energy = 10; break;
                    case 2 : energy = 15; break;
                    case 3 : energy = 25; break;
                    case 4 : energy = 40; break;
                };

                this.energyHandler.addEnergy(energy);
                isDirty.set(true);
            }
        }

        if (!this.level.isClientSide) {
            for (Direction direction : Direction.values()) {
                World level = this.level;

                Optional<LazyOptional<IEnergyStorage>> neighborEnergy = Optional.empty();
                Optional<TileEntity> be = Optional.ofNullable(level.getBlockEntity(this.getBlockPos().relative(direction.getOpposite())));

                if (be.isPresent()) {
                    neighborEnergy = Optional.of(be.get().getCapability(CapabilityEnergy.ENERGY, direction));
                }

                if (neighborEnergy.isPresent()) {
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
    public CompoundNBT save(CompoundNBT pTag) {
        pTag.put("Inventory", itemHandler.serializeNBT());
        pTag.putInt("Energy", energyHandler.getEnergyStored());
        pTag.putInt("Progress", progress);
        pTag.putInt("MaxProgress", maxProgress);
        pTag.put("Upgrades", upgradeHandler.serializeNBT());

        return super.save(pTag);
    }

    @Override
    public void load(BlockState state, CompoundNBT pTag) {
        super.load(state, pTag);

        this.itemHandler.deserializeNBT(pTag.getCompound("Inventory"));
        this.energyHandler.setEnergy(pTag.getInt("Energy"));
        this.progress = pTag.getInt("Progress");
        this.maxProgress = pTag.getInt("MaxProgress");
        this.upgradeHandler.deserializeNBT(pTag.getCompound("Upgrades"));
    }

    public void drops() {
        Inventory inventory = new Inventory(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        InventoryHelper.dropContents(this.level, this.worldPosition, inventory);
    }

    private void sendUpdate() {
        setChanged();

        if(this.level != null)
            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), 3);
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
}
