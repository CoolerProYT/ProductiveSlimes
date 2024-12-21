package com.coolerpromc.productiveslimes.block.entity;

import com.coolerpromc.productiveslimes.datacomponent.ModDataComponents;
import com.coolerpromc.productiveslimes.handler.SlimeData;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.screen.SlimeNestMenu;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SlimeNestBlockEntity extends BlockEntity implements MenuProvider{
    private SlimeData slimeData;
    private int cooldown = 0;
    private int counter = 0;
    private ItemStack dropItem = ItemStack.EMPTY;
    private final ContainerData data;
    private int hasSlot = 1;

    private final ItemStackHandler slimeHandler = new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();

            if (!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }

            ItemStack stack = getStackInSlot(slot);
            counter = 0;
            if(stack.isEmpty()){
                slimeData = null;
                dropItem = ItemStack.EMPTY;
            }
            else{
                if(stack.has(ModDataComponents.SLIME_DATA.get())){
                    slimeData = stack.get(ModDataComponents.SLIME_DATA.get());

                    assert slimeData != null;
                    cooldown = slimeData.cooldown();
                    dropItem = slimeData.dropItem();
                }
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            counter = 0;
            dropItem = ItemStack.EMPTY;
            return stack.getItem() == ModItems.SLIME_ITEM.get();
        }
    };

    private final ItemStackHandler outputHandler = new ItemStackHandler(9){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();

            if (!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return false;
        }
    };

    public ItemStackHandler getSlimeHandler() {
        return slimeHandler;
    }

    public ItemStackHandler getOutputHandler() {
        return outputHandler;
    }

    public SlimeNestBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SLIME_NEST_BE.get(), pos, blockState);

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                switch (index) {
                    case 0:
                        return counter;
                    case 1:
                        return cooldown;
                    case 2:
                        return hasSlot;
                    case 3:
                        return slimeData != null ? slimeData.size() : 0;
                    default:
                        return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0:
                        counter = value;
                        break;
                    case 1:
                        cooldown = value;
                        break;
                    case 2:
                        hasSlot = value;
                        break;
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
        return Component.translatable("block.productiveslimes.slime_nest");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new SlimeNestMenu(containerId, playerInventory, this, data);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("slimeHandler", slimeHandler.serializeNBT(registries));
        tag.put("outputHandler", outputHandler.serializeNBT(registries));
        tag.putInt("counter", counter);
        tag.putInt("cooldown", cooldown);
        if (slimeData != null){
            tag.put("dropItem", dropItem.save(registries));
            tag.put("slimeData", slimeData.toTag(new CompoundTag(), registries));
        }

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        slimeHandler.deserializeNBT(registries, tag.getCompound("slimeHandler"));
        outputHandler.deserializeNBT(registries, tag.getCompound("outputHandler"));
        counter = tag.getInt("counter");
        cooldown = tag.getInt("cooldown");
        dropItem = ItemStack.parseOptional(registries, tag.getCompound("dropItem"));
        slimeData = SlimeData.fromTag(tag.getCompound("slimeData"), registries);
    }

    public void tick(Level level, BlockPos pos, BlockState state){
        data.set(2, 1);

        if (slimeHandler.getStackInSlot(0).isEmpty()) {
            counter = 0;
            cooldown = 0;
            dropItem = ItemStack.EMPTY;
            return;
        }

        if (!hasAvailableSlot(dropItem)) {
            data.set(2, 0);
            return;
        }

        if(slimeData != null){
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
    }

    private boolean hasAvailableSlot(ItemStack stack){
        for (int i = 0; i < outputHandler.getSlots(); i++) {
            if (outputHandler.getStackInSlot(i).isEmpty() || (outputHandler.getStackInSlot(i).getItem() == stack.getItem() && outputHandler.getStackInSlot(i).getCount() < outputHandler.getStackInSlot(i).getMaxStackSize())) {
                return true;
            }
        }
        return false;
    }

    private int findSuitableSlot(ItemStack stack){
        for (int i = 0; i < outputHandler.getSlots(); i++) {
            if (outputHandler.getStackInSlot(i).isEmpty() || (outputHandler.getStackInSlot(i).getItem() == stack.getItem() && outputHandler.getStackInSlot(i).getCount() < outputHandler.getStackInSlot(i).getMaxStackSize())) {
                return i;
            }
        }
        return -1;
    }

    public void drops(){
        SimpleContainer container = new SimpleContainer(10);

        container.addItem(new ItemStack(slimeHandler.getStackInSlot(0).getItem()));

        for (int i = 0; i < outputHandler.getSlots(); i++) {
            if (!outputHandler.getStackInSlot(i).isEmpty()) {
                container.addItem(outputHandler.getStackInSlot(i + 1));
                outputHandler.setStackInSlot(i, ItemStack.EMPTY);
            }
        }

        Containers.dropContents(level, worldPosition, container);
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

    public ItemStack getSlime(){
        return slimeHandler.getStackInSlot(0);
    }

    public List<ItemStack> getOutput(){
        List<ItemStack> output = new ArrayList<>();

        for (int i = 0; i < outputHandler.getSlots(); i++) {
            output.add(outputHandler.getStackInSlot(i));
        }

        return output;
    }
}
