package com.coolerpromc.productiveslimes.screen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SlimeNestScreen extends AbstractContainerScreen<SlimeNestMenu>{
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/gui/slime_nest_gui.png");

    public SlimeNestScreen(SlimeNestMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 74;
        this.titleLabelX = 64;
        this.titleLabelY = 5;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);

        int countdown = menu.getCountdown();

        Component cd = Component.literal("CD: " + countdown + "s");
        Component size = Component.literal("Size: " + menu.getSlimeSize());

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        if (menu.hasSlime() && menu.hasOutputSlot()){
            if (countdown < 10){
                x += 20;
            }
            else if(countdown > 99){
                x += 13;

            }
            else {
                x += 17;
            }
        }
        else if(!menu.hasOutputSlot()){
            cd = Component.literal("No Slot");
            x += 16;
        }
        else {
            cd = Component.literal("No Slime");
            x += 17;
        }

        pGuiGraphics.drawWordWrap(Minecraft.getInstance().font, cd, x,  y + 53, 80, 0xFFFFFF);
        pGuiGraphics.drawWordWrap(Minecraft.getInstance().font, size, x,  y + 63, 80, 0xFFFFFF);
    }
}
