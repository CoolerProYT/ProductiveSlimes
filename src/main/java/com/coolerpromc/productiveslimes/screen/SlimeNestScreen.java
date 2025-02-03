package com.coolerpromc.productiveslimes.screen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
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
        this.inventoryLabelY = 100000;
        this.titleLabelX = 35;
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

        Component cd = Component.translatable("slimenest.productiveslimes.cooldown", countdown).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xa5f5a6)));
        Component size = Component.translatable("slimenest.productiveslimes.slime_size", menu.getSlimeSize()).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xa5f5a6)));
        Component multiplier = Component.translatable("slimenest.productiveslimes.multiplier", menu.getMultiplier()).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xa5f5a6)));
        Component dropItem = Component.translatable("slimenest.productiveslimes.drop_item").append(Component.translatable(menu.getDrop().getItem().getDescriptionId())).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xa5f5a6)));

        int guiLeft = (width - imageWidth) / 2;
        int guiTop = (height - imageHeight) / 2;

        int textX = guiLeft + 54;
        int textY = guiTop + 17;

        if (!(menu.hasSlime() && menu.hasOutputSlot())) {
            if (!menu.hasOutputSlot()) {
                cd = Component.translatable("slimenest.productiveslimes.no_output_slot").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xd59c20)));
            } else {
                cd = Component.translatable("slimenest.productiveslimes.no_slime_found").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xc70d0d)));
            }
        }

        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate(textX, textY, 0);
        pGuiGraphics.pose().scale(0.75f, 0.75f, 0.75f);
        pGuiGraphics.drawString(Minecraft.getInstance().font, cd, 0, 0, 0xFFFFFF);
        pGuiGraphics.pose().popPose();

        if (menu.hasSlime()) {
            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().translate(textX, textY + 8, 0);
            pGuiGraphics.pose().scale(0.75f, 0.75f, 0.75f);
            pGuiGraphics.drawString(Minecraft.getInstance().font, size, 0, 0, 0xFFFFFF);
            pGuiGraphics.pose().popPose();
        }

        if (menu.hasSlime()) {
            pGuiGraphics.pose().pushPose();
            if (String.valueOf(menu.getMultiplier()).length() >= 6) {
                pGuiGraphics.pose().translate(textX, textY + 16, 0);
                pGuiGraphics.pose().scale(0.7f, 0.7f, 0.7f);
                pGuiGraphics.drawString(Minecraft.getInstance().font, multiplier, 0, 0, 0xFFFFFF);
            } else {
                pGuiGraphics.pose().translate(textX, textY + 16, 0);
                pGuiGraphics.pose().scale(0.75f, 0.75f, 0.75f);
                pGuiGraphics.drawString(Minecraft.getInstance().font, multiplier, 0, 0, 0xFFFFFF);
            }
            pGuiGraphics.pose().popPose();

            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().translate(textX, textY + 24, 0);
            pGuiGraphics.pose().scale(0.75f, 0.75f, 0.75f);
            pGuiGraphics.drawWordWrap(Minecraft.getInstance().font, dropItem, 0, 0, 85, 0xFFFFFF);
            pGuiGraphics.pose().popPose();
        }
    }
}
