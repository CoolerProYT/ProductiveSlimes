package com.coolerpromc.productiveslimes.util;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import com.coolerpromc.productiveslimes.handler.SlimeData;
import com.coolerpromc.productiveslimes.screen.GuidebookScreen;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

public class GuideBookScreenHelper {
    public static int scrollOffset(int contentScrollOffset, double scroll, int maxContentScroll, int scrollSpeed){
        contentScrollOffset -= (int) (scroll * scrollSpeed);
        if (contentScrollOffset < 0) contentScrollOffset = 0;
        if (contentScrollOffset > maxContentScroll) contentScrollOffset = maxContentScroll;
        return contentScrollOffset;
    }

    public static void renderItemSlot(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int inputX, int inputY, ItemStack inputStack, Font font){
        if (pMouseX >= inputX && pMouseX < inputX + 16 && pMouseY >= inputY && pMouseY < inputY + 16) {
            pGuiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, ResourceLocation.withDefaultNamespace("container/slot_highlight_back"), inputX - 4, inputY - 4, 24, 24);
        }
        pGuiGraphics.renderItem(inputStack, inputX, inputY);
        pGuiGraphics.renderItemDecorations(font, inputStack, inputX, inputY);
        GuidebookScreen.mouseUtils.add(new MouseUtil(inputX, inputX + 16, inputY, inputY + 16, inputStack));
    }

    public static void renderTooltip(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, Font font){
        for(MouseUtil mouseUtil : GuidebookScreen.mouseUtils){
            if (pMouseX >= mouseUtil.mouseX1() && pMouseX < mouseUtil.mouseX2() && pMouseY >= mouseUtil.mouseY1() && pMouseY < mouseUtil.mouseY2()) {
                pGuiGraphics.setTooltipForNextFrame(font, mouseUtil.inputStack(), pMouseX, pMouseY);
            }
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
}