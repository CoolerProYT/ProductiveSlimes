package com.coolerpromc.productiveslimes.screen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SlimeNestScreen extends AbstractContainerScreen<SlimeNestMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/gui/slime_nest_gui.png");

    public SlimeNestScreen(SlimeNestMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 100000;
        this.titleLabelX = 35;
        this.titleLabelY = 5;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        pGuiGraphics.blit(RenderType::guiTextured, TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);

        int countdown = menu.getCountdown();

        Component cd = Component.literal("Cooldown: " + countdown + "s").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xa5f5a6)));
        Component size = Component.literal("Slime Size: " + menu.getSlimeSize()).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xa5f5a6)));
        Component multiplier = Component.literal("Multiplier: " + menu.getMultiplier()).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xa5f5a6)));
        Component dropItem = Component.literal("Drop Item: ").append(Component.translatable(menu.getDrop().getItem().getDescriptionId())).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xa5f5a6)));

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        if (!(menu.hasSlime() && menu.hasOutputSlot())){
            if(!menu.hasOutputSlot()){
                cd = Component.literal("No Output Slot").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xd59c20)));
            }
            else {
                cd = Component.literal("No Slime Found").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xc70d0d)));
            }
        }

        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().scale(0.75f, 0.75f, 0.75f);
        pGuiGraphics.drawString(Minecraft.getInstance().font, cd, x + 123,  y + 40, 0xFFFFFF);
        if (menu.hasSlime()){
            pGuiGraphics.drawString(Minecraft.getInstance().font, size, x + 123,  y + 52, 0xFFFFFF);
        }
        pGuiGraphics.pose().popPose();
        if (menu.hasSlime()){
            pGuiGraphics.pose().pushPose();

            if (String.valueOf(menu.getMultiplier()).length() >= 6){
                pGuiGraphics.pose().scale(0.7f, 0.7f, 0.7f);
                pGuiGraphics.drawString(Minecraft.getInstance().font, multiplier, x + 143,  y + 72, 0xFFFFFF);
            }
            else{
                pGuiGraphics.pose().scale(0.75f, 0.75f, 0.75f);
                pGuiGraphics.drawString(Minecraft.getInstance().font, multiplier, x + 123,  y + 64, 0xFFFFFF);
            }
            pGuiGraphics.pose().popPose();
            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().scale(0.75f, 0.75f, 0.75f);
            pGuiGraphics.drawWordWrap(Minecraft.getInstance().font, dropItem, x + 123,  y + 76, 85,  0xFFFFFF);
            pGuiGraphics.pose().popPose();
        }
    }
}