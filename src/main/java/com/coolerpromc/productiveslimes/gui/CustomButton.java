package com.coolerpromc.productiveslimes.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;

public class CustomButton extends Button {
    private final ItemStack icon;

    public CustomButton(int pX, int pY, int pWidth, int pHeight, IPressable pOnPress, ItemStack icon) {
        super(pX, pY, pWidth, pHeight, new StringTextComponent(""), pOnPress);
        this.icon = icon;
    }

    @Override
    public void renderButton(MatrixStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        super.renderButton(pPoseStack, pMouseX, pMouseY, pPartialTick);

        if (!icon.isEmpty()) {
            RenderSystem.enableBlend();

            Minecraft minecraft = Minecraft.getInstance();
            ItemRenderer itemRenderer = minecraft.getItemRenderer();

            itemRenderer.renderAndDecorateItem(icon, this.x + this.width / 2 - 8, this.y + this.height / 2 - 8);

            RenderSystem.disableBlend();
        }
    }
}
