package com.coolerpromc.productiveslimes.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class CustomButton extends Button {
    private final ItemStack icon;

    public CustomButton(int pX, int pY, int pWidth, int pHeight, OnPress pOnPress, ItemStack icon) {
        super(pX, pY, pWidth, pHeight, Component.empty(), pOnPress);
        this.icon = icon;
    }

    @Override
    public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
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
