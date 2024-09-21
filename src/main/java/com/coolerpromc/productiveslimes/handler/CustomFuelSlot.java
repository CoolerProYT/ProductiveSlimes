package com.coolerpromc.productiveslimes.handler;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class CustomFuelSlot extends SlotItemHandler {
    private final Predicate<ItemStack> fuelPredicate;

    public CustomFuelSlot(IItemHandler inventory, int index, int xPosition, int yPosition) {
        this(inventory, index, xPosition, yPosition, stack -> stack.getBurnTime(RecipeType.SMELTING) > 0);
    }

    public CustomFuelSlot(IItemHandler inventory, int index, int xPosition, int yPosition, Predicate<ItemStack> fuelPredicate) {
        super(inventory, index, xPosition, yPosition);
        this.fuelPredicate = fuelPredicate;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return this.fuelPredicate.test(stack);
    }
}
