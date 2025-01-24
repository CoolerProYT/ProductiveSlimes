package com.coolerpromc.productiveslimes.handler;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class IconButton extends Button {
    public static final ResourceLocation iconTexture = new ResourceLocation(ProductiveSlimes.MODID,"textures/gui/widgets.png");
    private final int closedTextureX;
    private final int closedTextureY;
    private final int openTextureX;
    private final int openTextureY;
    private boolean isOpen;

    public IconButton(int x, int y, int width, int height, int closedTextureX, int closedTextureY, int openTextureX, int openTextureY, IPressable onPress) {
        super(x, y, width, height, new StringTextComponent(""), onPress);
        this.closedTextureX = closedTextureX;
        this.closedTextureY = closedTextureY;
        this.openTextureX = openTextureX;
        this.openTextureY = openTextureY;
        this.isOpen = true;
    }

    @Override
    public void renderButton(MatrixStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        int textureX = isOpen ? closedTextureX : openTextureX;
        int textureY = isOpen ? closedTextureY : openTextureY;

        Minecraft.getInstance().getTextureManager().bind(iconTexture);
        AbstractGui.blit(pPoseStack, this.x, this.y, textureX, textureY, this.width, this.height, 256, 256);

        if (this.isHovered) {
            AbstractGui.fill(pPoseStack, this.x, this.y, this.x + this.width, this.y + this.height, 0x80FFFFFF);
        }
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.isOpen = !this.isOpen;
        this.onPress.onPress(this);
    }
}
