package com.coolerpromc.productiveslimes.screen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.handler.IconButton;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.Collections;
import java.util.List;

public class EnergyGeneratorScreen extends AbstractContainerScreen<EnergyGeneratorMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ProductiveSlimes.MODID, "textures/gui/energy_generator_gui.png");

    public EnergyGeneratorScreen(EnergyGeneratorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 74;
        this.titleLabelY = 5;
        this.titleLabelX = 43;
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
    protected void renderBg(PoseStack poseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - 176) / 2;
        int y = (height - imageHeight) / 2;

        blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);
        int energyScaled = this.menu.getEnergyStoredScaled();

        blit(poseStack, x + 9, y + 13 + (57 - energyScaled), 232, 57 - energyScaled, 9, energyScaled);

        renderProgressArrow(poseStack, x, y);
    }

    private void renderProgressArrow(PoseStack poseStack, int x, int y) {
        if(menu.isCrafting()) {
            int k = menu.getScaledProgress();
            blit(poseStack, x + 81, y + 47 + 14 - k, 218, 14 - k, 14, k);
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, delta);
        renderTooltip(poseStack, mouseX, mouseY);

        int energyStored = this.menu.getEnergy();
        int maxEnergy = this.menu.getMaxEnergy();

        Component text = new TranslatableComponent("gui.productiveslimes.energy_stored", energyStored, maxEnergy);
        if(isHovering(9, 13, 9, 57, mouseX, mouseY)) {
            List<Component> tooltip = Collections.singletonList(text);
            renderComponentTooltip(poseStack, tooltip, mouseX, mouseY);
        }
    }
}
