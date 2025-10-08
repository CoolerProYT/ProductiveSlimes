package com.coolerpromc.productiveslimes.block.entity;

import com.coolerpromc.productiveslimes.screen.SlimeballCollectorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SlimeballCollectorBlockEntity extends BlockEntity implements MenuProvider {
    private static final int RANGE_XZ = 8;
    private static final int RANGE_Y = 256;
    private int enableOutline = 0;
    private final ItemStacksResourceHandler inventory = new ItemStacksResourceHandler(9) {
        @Override
        public boolean isValid(int index, ItemResource resource) {
            return false;
        }

        @Override
        protected int getCapacity(int index, ItemResource resource) {
            return 64;
        }

        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
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

    public ItemStacksResourceHandler getInventory() {
        return inventory;
    }

    public ContainerData getData() {
        return data;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.productiveslimes.slimeball_collector");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new SlimeballCollectorMenu(containerId, playerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(ValueOutput valueOutput) {
        inventory.serialize(valueOutput.child("inventory"));
        valueOutput.putInt("enableOutline", enableOutline);
        super.saveAdditional(valueOutput);
    }

    @Override
    protected void loadAdditional(ValueInput valueInput) {
        inventory.deserialize(valueInput.childOrEmpty("inventory"));
        enableOutline = valueInput.getIntOr("enableOutline", 0);
        super.loadAdditional(valueInput);
    }

    public void drops() {
        SimpleContainer container = new SimpleContainer(9);
        for (int i = 0; i < inventory.size(); i++) {
            if (!inventory.getResource(i).isEmpty()) {
                container.addItem(inventory.getResource(i).toStack(inventory.getAmountAsInt(i)));
            }
        }
        Containers.dropContents(level, worldPosition, container);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (this.level == null || this.level.isClientSide()) return;
        // Define the collection area: 16x16 in X and Z, full height in Y.
        AABB collectionArea = new AABB(
                worldPosition.getX() - RANGE_XZ, -64, worldPosition.getZ() - RANGE_XZ,
                worldPosition.getX() + RANGE_XZ + 1, RANGE_Y, worldPosition.getZ() + RANGE_XZ + 1
        );
        // Find all dropped items in the collection area.
        List<ItemEntity> items = this.level.getEntitiesOfClass(ItemEntity.class, collectionArea);
        for (ItemEntity item : items) {
            if (!item.isRemoved() && item.getItem().is(Tags.Items.SLIME_BALLS)) {
                collectItem(item);
            }
        }
    }

    private void collectItem(ItemEntity item) {
        if (!hasSpaceForItem(item.getItem())) {
            return;
        }
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.getResource(i).isEmpty()) {
                inventory.set(i, ItemResource.of(item.getItem()), item.getItem().getCount());
                item.remove(Entity.RemovalReason.KILLED);
                return;
            } else if (inventory.getResource(i).is(item.getItem().getItem()) && inventory.getAmountAsInt(i) + item.getItem().getCount() <= inventory.getResource(i).getMaxStackSize()) {
                inventory.set(i, inventory.getResource(i), item.getItem().getCount() + inventory.getAmountAsInt(i));
                item.remove(Entity.RemovalReason.KILLED);
                return;
            }
        }
    }

    private boolean hasSpaceForItem(ItemStack stack) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.getResource(i).isEmpty() || inventory.getResource(i).is(stack.getItem()) && inventory.getAmountAsInt(i) + stack.getCount() <= inventory.getResource(i).getMaxStackSize()) {
                return true;
            }
        }
        return false;
    }

    public void setEnableOutline(int enableOutline) {
        this.enableOutline = enableOutline;
        setChanged();
    }

    @Override
    public void preRemoveSideEffects(BlockPos p_394577_, BlockState p_394161_) {
        drops();
    }
}