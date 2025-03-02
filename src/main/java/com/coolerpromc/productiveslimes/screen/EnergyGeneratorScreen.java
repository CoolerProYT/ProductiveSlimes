package com.coolerpromc.productiveslimes.screen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.screen.custom.IconButton;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class EnergyGeneratorScreen extends AbstractContainerScreen<EnergyGeneratorMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/gui/energy_generator_gui.png");

    public EnergyGeneratorScreen(EnergyGeneratorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 74;
        this.titleLabelY = 5;
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        this.imageWidth = 217;

        int x = (width - 176) / 2;
        int y = (height - imageHeight) / 2;

        Button iconButton = new IconButton(x + 155, y + 62, 16, 16, 0, 0, 16, 0, button -> onButtonPress());

        this.addRenderableWidget(iconButton);
    }

    private void onButtonPress(){
        switch (this.imageWidth) {
            case 217:
                this.imageWidth = 176;
                break;
            case 176:
                this.imageWidth = 217;
                break;
        }
        this.menu.toggleExtraSlots();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - 176) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(RenderType::guiTextured, TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
        int energyScaled = this.menu.getEnergyStoredScaled();

        guiGraphics.blit(RenderType::guiTextured, TEXTURE, x + 9, y + 13 + (57 - energyScaled), 232, 57 - energyScaled, 9, energyScaled, 256, 256);

        renderProgressArrow(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            int k = menu.getScaledProgress();
            guiGraphics.blit(RenderType::guiTextured, TEXTURE, x + 81, y + 47 + 14 - k, 218, 14 - k, 14, k, 256, 256);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);

        int energyStored = this.menu.getEnergy();
        int maxEnergy = this.menu.getMaxEnergy();

        Component text = Component.translatable("gui.productiveslimes.energy_stored", energyStored, maxEnergy);
        if(isHovering(9, 13, 9, 57, mouseX, mouseY)) {
            guiGraphics.renderTooltip(this.font, text, mouseX, mouseY);
        }
    }
}
