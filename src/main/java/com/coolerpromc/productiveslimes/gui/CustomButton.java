package com.coolerpromc.productiveslimes.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class CustomButton extends Button {
    private final ItemStack icon;

    public CustomButton(int pX, int pY, int pWidth, int pHeight, OnPress pOnPress, ItemStack icon) {
        super(pX, pY, pWidth, pHeight, Component.empty(), pOnPress, DEFAULT_NARRATION);
        this.icon = icon;
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.renderWidget(pGuiGraphics, pMouseX, pMouseY, pPartialTick);


        if (!icon.isEmpty()) {
            RenderSystem.enableBlend();
            pGuiGraphics.renderItem(icon, this.getX() + this.width / 2 - 8, this.getY() + this.height / 2 - 8);
            RenderSystem.disableBlend();
        }
    }
}
