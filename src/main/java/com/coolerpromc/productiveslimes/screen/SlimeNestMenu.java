package com.coolerpromc.productiveslimes.screen;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.block.entity.SlimeNestBlockEntity;
import com.coolerpromc.productiveslimes.datacomponent.ModDataComponents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class SlimeNestMenu extends AbstractContainerMenu {
    public final SlimeNestBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public SlimeNestMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(6));
    }

    public SlimeNestMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.SLIME_NEST_MENU.get(), pContainerId);
        checkContainerSize(inv, 3);
        blockEntity = (SlimeNestBlockEntity) entity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        IItemHandler upgradeHandler = blockEntity.getUpgradeHandler();
        this.addSlot(new SlotItemHandler(upgradeHandler, 0, 8, 7));
        this.addSlot(new SlotItemHandler(upgradeHandler, 1, 8, 25));
        this.addSlot(new SlotItemHandler(upgradeHandler, 2, 8, 43));
        this.addSlot(new SlotItemHandler(upgradeHandler, 3, 8, 61));
        IItemHandler inputHandler = blockEntity.getSlimeHandler();
        this.addSlot(new SlotItemHandler(inputHandler, 0, 33, 34));

        IItemHandler outputHandler = blockEntity.getOutputHandler();
        this.addSlot(new SlotItemHandler(outputHandler, 0, 116, 16));
        this.addSlot(new SlotItemHandler(outputHandler, 1, 134, 16));
        this.addSlot(new SlotItemHandler(outputHandler, 2, 152, 16));
        this.addSlot(new SlotItemHandler(outputHandler, 3, 116, 34));
        this.addSlot(new SlotItemHandler(outputHandler, 4, 134, 34));
        this.addSlot(new SlotItemHandler(outputHandler, 5, 152, 34));
        this.addSlot(new SlotItemHandler(outputHandler, 6, 116, 52));
        this.addSlot(new SlotItemHandler(outputHandler, 7, 134, 52));
        this.addSlot(new SlotItemHandler(outputHandler, 8, 152, 52));

        addDataSlots(data);
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 14;

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();
        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(pPlayer, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocks.SLIME_NEST.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    public int getCountdown() {
        return (data.get(1) - data.get(0)) / 20;
    }

    public boolean hasSlime() {
        return data.get(1) > 0;
    }

    public boolean hasOutputSlot() {
        return data.get(2) == 1;
    }

    public int getSlimeSize() {
        return data.get(3);
    }

    public int getCooldown(){
        return data.get(1);
    }
    public float getMultiplier(){
        return data.get(5) / 10000f;
    }
    public ItemStack getDrop(){
        if (blockEntity.getSlimeHandler().getStackInSlot(0).isEmpty()){
            return ItemStack.EMPTY;
        }
        return blockEntity.getSlime().get(ModDataComponents.SLIME_DATA.get()).dropItem();
    }
}