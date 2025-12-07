package com.coolerpromc.productiveslimes.screen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
public class SlimeballCollectorScreen extends AbstractContainerScreen<SlimeballCollectorMenu>{
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/gui/slimeball_collector_gui.png");
    public SlimeballCollectorScreen(SlimeballCollectorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }
    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 74;
        this.titleLabelX = 49;
        this.titleLabelY = 5;
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        this.addRenderableWidget(new Button.Builder(Component.translatable("gui.productiveslimes.toggle_area"), button -> {
            this.menu.blockEntity.setEnableOutline(this.menu.blockEntity.getData().get(0) == 0 ? 1 : 0);
        }).pos(x + 88, y + 65).size(80, 16).build());
    }
    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        pGuiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
    }
    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}