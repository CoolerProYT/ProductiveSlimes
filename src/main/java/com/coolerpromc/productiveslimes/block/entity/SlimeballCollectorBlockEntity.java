package com.coolerpromc.productiveslimes.block.entity;

import com.coolerpromc.productiveslimes.screen.SlimeballCollectorMenu;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.antlr.v4.runtime.misc.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class SlimeballCollectorBlockEntity extends TileEntity implements INamedContainerProvider, ITickableTileEntity {
    private static final int RANGE_XZ = 8;
    private static final int RANGE_Y = 256;
    private int enableOutline = 0;
    private final ItemStackHandler inventory = new ItemStackHandler(9) {
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return false;
        }

        @Override
        public int getSlotLimit(int slot) {
            return 64;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private final IIntArray data;

    public SlimeballCollectorBlockEntity() {
        super(ModBlockEntities.SLIMEBALL_COLLECTOR_BE.get());
        this.data = new IIntArray() {
            @Override
            public int get(int index) {
                switch (index) {
                    case 0 : return enableOutline;
                    case 1 : return 0;
                    default : return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0:
                        break;
                    case 1:
                        break;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    private LazyOptional<ItemStackHandler> inventoryHolder = LazyOptional.of(() -> inventory);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return inventoryHolder.cast();
        }
        return super.getCapability(cap);
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public IIntArray getData() {
        return data;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("block.productiveslimes.slimeball_collector");
    }

    @Nullable
    @Override
    public Container createMenu(int containerId, PlayerInventory playerInventory, PlayerEntity player) {
        return new SlimeballCollectorMenu(containerId, playerInventory, this, this.data);
    }

    @Override
    public CompoundNBT save(CompoundNBT pTag) {
        pTag.put("inventory", inventory.serializeNBT());
        pTag.putInt("enableOutline", enableOutline);

        return super.save(pTag);
    }

    @Override
    public void load(BlockState state, CompoundNBT tag) {
        super.load(state, tag);
        inventory.deserializeNBT(tag.getCompound("inventory"));
        enableOutline = tag.getInt("enableOutline");
    }

    public void drops() {
        Inventory container = new Inventory(9);
        for (int i = 0; i < inventory.getSlots(); i++) {
            if (!inventory.getStackInSlot(i).isEmpty()) {
                container.addItem(inventory.getStackInSlot(i));
            }
        }
        InventoryHelper.dropContents(level, worldPosition, container);
    }

    @Override
    public void tick() {
        if (this.level == null || this.level.isClientSide) return;
        // Define the collection area: 16x16 in X and Z, full height in Y.
        AxisAlignedBB collectionArea = new AxisAlignedBB(
                worldPosition.getX() - RANGE_XZ, -64, worldPosition.getZ() - RANGE_XZ,
                worldPosition.getX() + RANGE_XZ + 1, RANGE_Y, worldPosition.getZ() + RANGE_XZ + 1
        );
        // Find all dropped items in the collection area.
        List<ItemEntity> items = this.level.getEntitiesOfClass(ItemEntity.class, collectionArea);
        for (ItemEntity item : items) {
            if (item.isAlive() && item.getItem().getItem().is(Tags.Items.SLIMEBALLS)) {
                collectItem(item);
            }
        }
    }

    private void collectItem(ItemEntity item) {
        if (!hasSpaceForItem(item.getItem())) {
            return;
        }
        for (int i = 0; i < inventory.getSlots(); i++) {
            if (inventory.getStackInSlot(i).isEmpty()) {
                inventory.setStackInSlot(i, item.getItem());
                item.remove(true);
                return;
            } else if (inventory.getStackInSlot(i).getItem().equals(item.getItem().getItem()) && inventory.getStackInSlot(i).getCount() + item.getItem().getCount() <= inventory.getStackInSlot(i).getMaxStackSize()) {
                inventory.getStackInSlot(i).grow(item.getItem().getCount());
                item.remove(true);
                return;
            }
        }
    }

    private boolean hasSpaceForItem(ItemStack stack) {
        for (int i = 0; i < inventory.getSlots(); i++) {
            if (inventory.getStackInSlot(i).isEmpty() || inventory.getStackInSlot(i).getItem().equals(stack.getItem()) && inventory.getStackInSlot(i).getCount() + stack.getCount() <= inventory.getStackInSlot(i).getMaxStackSize()) {
                return true;
            }
        }
        return false;
    }

    public void setEnableOutline(int enableOutline) {
        this.enableOutline = enableOutline;
        setChanged();
    }
}