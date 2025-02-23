package com.coolerpromc.productiveslimes.util;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import com.coolerpromc.productiveslimes.handler.SlimeData;
import com.coolerpromc.productiveslimes.screen.renderer.FluidTankRenderer;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class GuideBookScreenHelper {
    public static int scrollOffset(int contentScrollOffset, double scroll, int maxContentScroll, int scrollSpeed){
        contentScrollOffset -= scroll * scrollSpeed;
        if (contentScrollOffset < 0) contentScrollOffset = 0;
        if (contentScrollOffset > maxContentScroll) contentScrollOffset = maxContentScroll;
        return contentScrollOffset;
    }

    public static void renderItemSlot(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int inputX, int inputY, ItemStack inputStack, Font font){
        pGuiGraphics.renderItem(inputStack, inputX, inputY);
        pGuiGraphics.renderItemDecorations(font, inputStack, inputX, inputY);
        if (pMouseX >= inputX && pMouseX < inputX + 16 && pMouseY >= inputY && pMouseY < inputY + 16) {
            pGuiGraphics.renderTooltip(font, inputStack, pMouseX, pMouseY);
            pGuiGraphics.fill(RenderType.gui(), inputX, inputY, inputX + 16, inputY + 16, 0x80FFFFFF);
        }
    }

    public static SlimeData generateSlimeData(ModTiers tiers){
        return new SlimeData(
                1,
                tiers.color(),
                tiers.cooldown(),
                BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, tiers.name() + "_slimeball")).get().value().getDefaultInstance(),
                ModTierLists.getItemByKey(tiers.growthItemKey()).asItem().getDefaultInstance(),
                (EntityType<BaseSlime>) BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, tiers.name() + "_slime")).get().value()
        );
    }

    public static void renderFluidStack(GuiGraphics guiGraphics, FluidStack fluidStack, int tankCapacity, int w, int h, int x, int y, int mouseX, int mouseY, Font font){
        FluidTankRenderer.renderFluidStack(guiGraphics, fluidStack, tankCapacity, w, h, x, y);
        if (mouseX >= x && mouseX < x + w && mouseY >= y && mouseY < y + h) {
            List<Component> fluidTankTooltip = new ArrayList<>();
            fluidTankTooltip.add(Component.translatable(fluidStack.getDescriptionId()));
            fluidTankTooltip.add(Component.translatable("productiveslimes.tooltip.liquid.amount", fluidStack.getAmount()));
            guiGraphics.renderComponentTooltip(font, fluidTankTooltip, mouseX, mouseY);
            guiGraphics.fill(RenderType.gui(), x, y, x + w, y + h, 0x80FFFFFF);
        }
    }
}
