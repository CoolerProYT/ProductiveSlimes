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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class EnergyGeneratorBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStacksResourceHandler itemHandler = new ItemStacksResourceHandler(1){
        @Override
        public boolean isValid(int index, ItemResource resource) {
            return canBurn(resource.toStack());
        }
    };
    protected final ContainerData data;

    private final CustomEnergyStorage energyHandler = new CustomEnergyStorage(10000, 0, 100, 0);

    private final ItemStacksResourceHandler upgradeHandler = new ItemStacksResourceHandler(4){
        @Override
        protected int getCapacity(int index, ItemResource resource) {
            return 1;
        }

        @Override
        public boolean isValid(int index, ItemResource resource) {
            return resource.getItem() == ModItems.ENERGY_MULTIPLIER_UPGRADE.get();
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

    public ItemStacksResourceHandler getItemHandler() {
        return itemHandler;
    }

    public ItemStacksResourceHandler getUpgradeHandler() {
        return upgradeHandler;
    }

    public EnergyGeneratorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ENERGY_GENERATOR_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> EnergyGeneratorBlockEntity.this.energyHandler.getAmountAsInt();
                    case 1 -> EnergyGeneratorBlockEntity.this.energyHandler.getCapacityAsInt();
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

        if (this.energyHandler.getAmountAsInt() < this.energyHandler.getCapacityAsInt()) {
            if (this.progress <= 0) {
                if (canBurn(this.itemHandler.getResource(0).toStack())) {
                    this.progress = this.maxProgress = getBurnTime(this.itemHandler.getResource(0).toStack());
                    try(Transaction tx = Transaction.open(null)){
                        if (this.itemHandler.extract(0, this.itemHandler.getResource(0), 1, tx) == 1){
                            tx.commit();
                            isDirty.set(true);
                        }
                    }
                }
            } else {
                this.progress--;

                int upgrade = 0;
                for (int i = 0; i < this.upgradeHandler.size(); i++) {
                    if (this.upgradeHandler.getResource(i).isEmpty()) continue;

                    if (this.upgradeHandler.getResource(i).getItem() == ModItems.ENERGY_MULTIPLIER_UPGRADE.get()) {
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

        if (!this.level.isClientSide()) {
            for (Direction direction : Direction.values()) {
                Level level = this.level;
                BlockPos neighborPos = this.getBlockPos().relative(direction);

                Optional<EnergyHandler> neighborEnergy = Optional.ofNullable(level.getCapability(Capabilities.Energy.BLOCK, neighborPos, direction.getOpposite()));

                if (neighborEnergy.isPresent()) {
                    EnergyHandler neighborStorage = neighborEnergy.get();
                    int energyToExtract;

                    try(Transaction tx = Transaction.open(null)){
                        energyToExtract = Math.min(neighborStorage.insert(100, tx), this.energyHandler.extract(100, tx));
                    }

                    if (energyToExtract > 0){
                        try(Transaction tx2 = Transaction.open(null)){
                            if (this.energyHandler.extract(energyToExtract, tx2) == neighborStorage.insert(energyToExtract, tx2)){
                                tx2.commit();
                            }
                        }
                    }
                }
            }
        }

        if (isDirty.get()) {
            sendUpdate();
        }
    }

    @Override
    protected void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);

        itemHandler.serialize(valueOutput.child("ItemHandler"));
        upgradeHandler.serialize(valueOutput.child("UpgradeHandler"));
        valueOutput.putInt("Energy", energyHandler.getAmountAsInt());
        valueOutput.putInt("Progress", progress);
        valueOutput.putInt("MaxProgress", maxProgress);
    }

    @Override
    protected void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);

        itemHandler.deserialize(valueInput.childOrEmpty("ItemHandler"));
        upgradeHandler.deserialize(valueInput.childOrEmpty("UpgradeHandler"));
        this.energyHandler.setEnergy(valueInput.getIntOr("Energy", 0));
        this.progress = valueInput.getIntOr("Progress", 0);
        this.maxProgress = valueInput.getIntOr("MaxProgress", 100);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.size());
        for(int i = 0; i < itemHandler.size(); i++) {
            inventory.setItem(i, itemHandler.getResource(i).toStack(itemHandler.getAmountAsInt(i)));
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
    public void preRemoveSideEffects(BlockPos p_394577_, BlockState p_394161_) {
        drops();
    }
}
