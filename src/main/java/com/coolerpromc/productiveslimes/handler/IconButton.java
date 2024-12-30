package com.coolerpromc.productiveslimes.handler;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

public class IconButton extends Button {
    public static final ResourceLocation iconTexture = new ResourceLocation(ProductiveSlimes.MODID,"textures/gui/widgets.png");
    private final int closedTextureX;
    private final int closedTextureY;
    private final int openTextureX;
    private final int openTextureY;
    private boolean isOpen;

    public IconButton(int x, int y, int width, int height, int closedTextureX, int closedTextureY, int openTextureX, int openTextureY, OnPress onPress) {
        super(x, y, width, height, new TextComponent(""), onPress);
        this.closedTextureX = closedTextureX;
        this.closedTextureY = closedTextureY;
        this.openTextureX = openTextureX;
        this.openTextureY = openTextureY;
        this.isOpen = true;
    }

    @Override
    public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        int textureX = isOpen ? closedTextureX : openTextureX;
        int textureY = isOpen ? closedTextureY : openTextureY;

        RenderSystem.setShaderTexture(0, iconTexture);

        Minecraft.getInstance().getTextureManager().bindForSetup(iconTexture);
        GuiComponent.blit(pPoseStack, this.x, this.y, textureX, textureY, this.width, this.height, 256, 256);

        if (this.isHovered) {
            GuiComponent.fill(pPoseStack, this.x, this.y, this.x + this.width, this.y + this.height, 0x80FFFFFF);
        }
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.isOpen = !this.isOpen;
        this.onPress.onPress(this);
    }
}
