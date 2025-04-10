
package com.coolerpromc.productiveslimes.item.custom;

import com.coolerpromc.productiveslimes.datacomponent.ModDataComponents;
import com.coolerpromc.productiveslimes.datacomponent.custom.ImmutableFluidStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.function.Consumer;

public class ModBlockItem extends BlockItem {
    public ModBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext p_339594_, TooltipDisplay p_399753_, Consumer<Component> p_399884_, TooltipFlag p_41424_) {
        if (itemStack.has(ModDataComponents.ENERGY.get())){
            int energy = itemStack.getOrDefault(ModDataComponents.ENERGY.get(), 0);
            p_399884_.accept(Component.translatable("tooltip.productiveslimes.energy_stored")
                    .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x00FF00)))
                    .append(Component.translatable("tooltip.productiveslimes.energy_amount", energy)
                            .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFFF)))));
        }

        if (itemStack.getOrDefault(ModDataComponents.FLUID_STACK.get(), FluidStack.EMPTY) != FluidStack.EMPTY) {
            ImmutableFluidStack immutableFluidStack = itemStack.get(ModDataComponents.FLUID_STACK.get());
            FluidStack fluidStack = (immutableFluidStack != null) ? immutableFluidStack.fluidStack() : FluidStack.EMPTY;
            p_399884_.accept(Component.translatable("tooltip.productiveslimes.fluid_stored").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x00FF00))).append(Component.translatable(fluidStack.getDescriptionId()).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFFF)))));
            p_399884_.accept(Component.translatable("tooltip.productiveslimes.stored_amount").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x00FF00))).append(Component.translatable("tooltip.productiveslimes.fluid_amount", fluidStack.getAmount() / 1000).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFFF)))));
        }
    }
}