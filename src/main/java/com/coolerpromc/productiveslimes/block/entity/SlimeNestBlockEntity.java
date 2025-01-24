package com.coolerpromc.productiveslimes.block.entity;

import com.coolerpromc.productiveslimes.handler.SlimeData;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.item.custom.NestUpgradeItem;
import com.coolerpromc.productiveslimes.screen.SlimeNestMenu;
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
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.antlr.v4.runtime.misc.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SlimeNestBlockEntity extends TileEntity implements INamedContainerProvider, ITickableTileEntity {
    private SlimeData slimeData;
    private int cooldown = 0;
    private int counter = 0;
    private ItemStack dropItem = ItemStack.EMPTY;
    private final IIntArray data;
    private int hasSlot = 1;
    private int tick = 0;
    private float multiplier = 1;
    private final ItemStackHandler upgradeHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return stack.getItem() instanceof NestUpgradeItem;
        }
    };
    private final ItemStackHandler slimeHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
            ItemStack stack = getStackInSlot(slot);
            counter = 0;
            if (stack.isEmpty()) {
                slimeData = null;
                dropItem = ItemStack.EMPTY;
            } else {
                if (stack.hasTag() && stack.getTag().contains("slime_data")) {
                    slimeData = SlimeData.fromTag(stack.getTag().getCompound("slime_data"));
                    cooldown = slimeData.cooldown();
                    dropItem = slimeData.dropItem();
                }
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            counter = 0;
            dropItem = ItemStack.EMPTY;
            return stack.getItem() == ModItems.SLIME_ITEM.get() &&  stack.getTag().contains("slime_data");
        }
    };
    private final ItemStackHandler outputHandler = new ItemStackHandler(9) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return false;
        }
    };

    private LazyOptional<ItemStackHandler> upgradeHandlerCap = LazyOptional.of(() -> upgradeHandler);
    private LazyOptional<ItemStackHandler> slimeHandlerCap = LazyOptional.of(() -> slimeHandler);
    private LazyOptional<ItemStackHandler> outputHandlerCap = LazyOptional.of(() -> outputHandler);

    public ItemStackHandler getUpgradeHandler() {
        return upgradeHandler;
    }

    public ItemStackHandler getSlimeHandler() {
        return slimeHandler;
    }

    public ItemStackHandler getOutputHandler() {
        return outputHandler;
    }

    public SlimeNestBlockEntity() {
        super(ModBlockEntities.SLIME_NEST_BE.get());
        this.data = new IIntArray() {
            @Override
            public int get(int index) {
                switch (index) {
                    case 0:
                        return SlimeNestBlockEntity.this.counter;
                    case 1:
                        return SlimeNestBlockEntity.this.cooldown;
                    case 2:
                        return SlimeNestBlockEntity.this.hasSlot;
                    case 3:
                        return SlimeNestBlockEntity.this.slimeData != null ? SlimeNestBlockEntity.this.slimeData.size() : 0;
                    case 4:
                        return SlimeNestBlockEntity.this.tick;
                    case 5:
                        return (int) (SlimeNestBlockEntity.this.multiplier * 10000);
                    default:
                        return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0:
                        SlimeNestBlockEntity.this.counter = value;
                        break;
                    case 1:
                        SlimeNestBlockEntity.this.cooldown = value;
                        break;
                    case 2:
                        SlimeNestBlockEntity.this.hasSlot = value;
                        break;
                }
            }

            @Override
            public int getCount() {
                return 6;
            }
        };
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("block.productiveslimes.slime_nest");
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == Direction.UP) {
                return slimeHandlerCap.cast();
            } else {
                return outputHandlerCap.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    protected void invalidateCaps() {
        super.invalidateCaps();
        upgradeHandlerCap.invalidate();
        slimeHandlerCap.invalidate();
        outputHandlerCap.invalidate();
    }

    @Nullable
    @Override
    public Container createMenu(int containerId, PlayerInventory playerInventory, PlayerEntity player) {
        return new SlimeNestMenu(containerId, playerInventory, this, data);
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        tag.put("upgradeHandler", upgradeHandler.serializeNBT());
        tag.put("slimeHandler", slimeHandler.serializeNBT());
        tag.put("outputHandler", outputHandler.serializeNBT());
        tag.putInt("counter", counter);
        tag.putInt("cooldown", cooldown);
        if (slimeData != null && !dropItem.isEmpty()) {
            tag.put("dropItem", dropItem.save(new CompoundNBT()));
            tag.put("slimeData", slimeData.toTag(new CompoundNBT()));
        }
        tag.putInt("tick", tick);
        tag.putFloat("multiplier", multiplier);

        return super.save(tag);
    }

    @Override
    public void load(BlockState state, CompoundNBT tag) {
        super.load(state, tag);
        upgradeHandler.deserializeNBT(tag.getCompound("upgradeHandler"));
        slimeHandler.deserializeNBT(tag.getCompound("slimeHandler"));
        outputHandler.deserializeNBT(tag.getCompound("outputHandler"));
        counter = tag.getInt("counter");
        cooldown = tag.getInt("cooldown");
        dropItem = ItemStack.of(tag.getCompound("dropItem"));
        slimeData = SlimeData.fromTag(tag.getCompound("slimeData"));
        tick = tag.getInt("tick");
        multiplier = tag.getInt("multiplier");
    }

    @Override
    public void tick() {
        data.set(2, 1);
        if (slimeHandler.getStackInSlot(0).isEmpty()) {
            counter = 0;
            cooldown = 0;
            dropItem = ItemStack.EMPTY;
            return;
        }
        float speed = 1;
        cooldown = slimeData.cooldown();
        for (int i = 0; i < upgradeHandler.getSlots(); i++) {
            if (upgradeHandler.getStackInSlot(i).getItem() instanceof NestUpgradeItem) {
                NestUpgradeItem nestUpgradeItem = (NestUpgradeItem) upgradeHandler.getStackInSlot(i).getItem();
                speed *= nestUpgradeItem.getMultiplier();
            }
        }
        multiplier = speed;
        tick += 3;
        cooldown = (int) Math.ceil(cooldown / speed);
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
        if (!hasAvailableSlot(dropItem)) {
            data.set(2, 0);
            return;
        }
        if (slimeData != null) {
            counter++;
            if (counter >= cooldown) {
                counter = 0;
                int outputSlot = findSuitableSlot(dropItem);
                if (outputSlot != -1) {
                    int stackSize = outputHandler.getStackInSlot(outputSlot).getCount() + slimeData.size();
                    if (stackSize > dropItem.getMaxStackSize()) {
                        stackSize = dropItem.getMaxStackSize();
                    }
                    outputHandler.setStackInSlot(outputSlot, new ItemStack(dropItem.copy().getItem(), stackSize));
                }
            }
        }
        if (tick % 20 == 0) {
            level.playSound(null, this.getBlockPos(), SoundEvents.SLIME_SQUISH, SoundCategory.BLOCKS, 0.5F, 1.0F);
        }
    }

    private boolean hasAvailableSlot(ItemStack stack) {
        for (int i = 0; i < outputHandler.getSlots(); i++) {
            if (outputHandler.getStackInSlot(i).isEmpty() || (outputHandler.getStackInSlot(i).getItem() == stack.getItem() && outputHandler.getStackInSlot(i).getCount() < outputHandler.getStackInSlot(i).getMaxStackSize())) {
                return true;
            }
        }
        return false;
    }

    private int findSuitableSlot(ItemStack stack) {
        for (int i = 0; i < outputHandler.getSlots(); i++) {
            if (outputHandler.getStackInSlot(i).isEmpty() || (outputHandler.getStackInSlot(i).getItem() == stack.getItem() && outputHandler.getStackInSlot(i).getCount() < outputHandler.getStackInSlot(i).getMaxStackSize())) {
                return i;
            }
        }
        return -1;
    }

    public void drops() {
        Inventory container = new Inventory(10);
        container.addItem(slimeHandler.getStackInSlot(0));
        for (int i = 0; i < outputHandler.getSlots(); i++) {
            if (!outputHandler.getStackInSlot(i).isEmpty()) {
                container.addItem(outputHandler.getStackInSlot(i));
            }
        }
        InventoryHelper.dropContents(level, worldPosition, container);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT tag = new CompoundNBT();
        save(tag);
        return new SUpdateTileEntityPacket(this.getBlockPos(), 1, tag);
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

    public ItemStack getSlime() {
        return slimeHandler.getStackInSlot(0);
    }

    public List<ItemStack> getOutput() {
        List<ItemStack> output = new ArrayList<>();
        for (int i = 0; i < outputHandler.getSlots(); i++) {
            output.add(outputHandler.getStackInSlot(i));
        }
        return output;
    }

    public IIntArray getData() {
        return data;
    }
}