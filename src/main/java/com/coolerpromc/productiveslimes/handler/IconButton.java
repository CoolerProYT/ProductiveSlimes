package com.coolerpromc.productiveslimes.handler;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class IconButton extends Button {
    public static final ResourceLocation iconTexture = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID,"textures/gui/widgets.png");
    private final int closedTextureX;
    private final int closedTextureY;
    private final int openTextureX;
    private final int openTextureY;
    private boolean isOpen;

    public IconButton(int x, int y, int width, int height, int closedTextureX, int closedTextureY, int openTextureX, int openTextureY, OnPress onPress) {
        super(x, y, width, height, Component.empty(), onPress, DEFAULT_NARRATION);
        this.closedTextureX = closedTextureX;
        this.closedTextureY = closedTextureY;
        this.openTextureX = openTextureX;
        this.openTextureY = openTextureY;
        this.isOpen = true;
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        int textureX = isOpen ? closedTextureX : openTextureX;
        int textureY = isOpen ? closedTextureY : openTextureY;
        pGuiGraphics.blit(iconTexture, this.getX(), this.getY(), textureX, textureY, this.width, this.height, 256, 256);

        if (this.isHovered()) {
            pGuiGraphics.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, 0x80FFFFFF);
        }
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.isOpen = !this.isOpen;
        this.onPress.onPress(this);
    }
}
