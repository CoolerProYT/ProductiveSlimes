package com.coolerpromc.productiveslimes.block.entity;

import com.coolerpromc.productiveslimes.screen.SlimeballCollectorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.antlr.v4.runtime.misc.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class SlimeballCollectorBlockEntity extends BlockEntity implements MenuProvider {
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
    private final ContainerData data;

    public SlimeballCollectorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SLIMEBALL_COLLECTOR_BE.get(), pos, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> enableOutline;
                    case 1 -> 0;
                    default -> 0;
                };
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

    public ContainerData getData() {
        return data;
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.productiveslimes.slimeball_collector");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new SlimeballCollectorMenu(containerId, playerInventory, this, this.data);
    }

    @Override
    public CompoundTag save(CompoundTag pTag) {
        pTag.put("inventory", inventory.serializeNBT());
        pTag.putInt("enableOutline", enableOutline);

        return super.save(pTag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        inventory.deserializeNBT(tag.getCompound("inventory"));
        enableOutline = tag.getInt("enableOutline");
    }

    public void drops() {
        SimpleContainer container = new SimpleContainer(9);
        for (int i = 0; i < inventory.getSlots(); i++) {
            if (!inventory.getStackInSlot(i).isEmpty()) {
                container.addItem(inventory.getStackInSlot(i));
            }
        }
        Containers.dropContents(level, worldPosition, container);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (this.level == null || this.level.isClientSide) return;
        // Define the collection area: 16x16 in X and Z, full height in Y.
        AABB collectionArea = new AABB(
                worldPosition.getX() - RANGE_XZ, -64, worldPosition.getZ() - RANGE_XZ,
                worldPosition.getX() + RANGE_XZ + 1, RANGE_Y, worldPosition.getZ() + RANGE_XZ + 1
        );
        // Find all dropped items in the collection area.
        List<ItemEntity> items = this.level.getEntitiesOfClass(ItemEntity.class, collectionArea);
        for (ItemEntity item : items) {
            if (!item.isRemoved() && item.getItem().is(Tags.Items.SLIMEBALLS)) {
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
                item.remove(Entity.RemovalReason.KILLED);
                return;
            } else if (inventory.getStackInSlot(i).is(item.getItem().getItem()) && inventory.getStackInSlot(i).getCount() + item.getItem().getCount() <= inventory.getStackInSlot(i).getMaxStackSize()) {
                inventory.getStackInSlot(i).grow(item.getItem().getCount());
                item.remove(Entity.RemovalReason.KILLED);
                return;
            }
        }
    }

    private boolean hasSpaceForItem(ItemStack stack) {
        for (int i = 0; i < inventory.getSlots(); i++) {
            if (inventory.getStackInSlot(i).isEmpty() || inventory.getStackInSlot(i).is(stack.getItem()) && inventory.getStackInSlot(i).getCount() + stack.getCount() <= inventory.getStackInSlot(i).getMaxStackSize()) {
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