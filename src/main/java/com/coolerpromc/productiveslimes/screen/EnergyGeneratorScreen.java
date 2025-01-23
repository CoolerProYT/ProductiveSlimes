package com.coolerpromc.productiveslimes.screen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.handler.IconButton;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collections;
import java.util.List;

public class EnergyGeneratorScreen extends ContainerScreen<EnergyGeneratorMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ProductiveSlimes.MODID, "textures/gui/energy_generator_gui.png");

    public EnergyGeneratorScreen(EnergyGeneratorMenu pMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
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

        this.addButton(iconButton);
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
    protected void renderBg(MatrixStack poseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(TEXTURE);

        int x = (width - 176) / 2;
        int y = (height - imageHeight) / 2;

        blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);
        int energyScaled = this.menu.getEnergyStoredScaled();

        blit(poseStack, x + 9, y + 13 + (57 - energyScaled), 232, 57 - energyScaled, 9, energyScaled);

        renderProgressArrow(poseStack, x, y);
    }

    private void renderProgressArrow(MatrixStack poseStack, int x, int y) {
        if(menu.isCrafting()) {
            int k = menu.getScaledProgress();
            blit(poseStack, x + 81, y + 47 + 14 - k, 218, 14 - k, 14, k);
        }
    }

    @Override
    public void render(MatrixStack poseStack, int mouseX, int mouseY, float delta) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, delta);
        renderTooltip(poseStack, mouseX, mouseY);

        int energyStored = this.menu.getEnergy();
        int maxEnergy = this.menu.getMaxEnergy();

        ITextComponent text = new TranslationTextComponent("gui.productiveslimes.energy_stored", energyStored, maxEnergy);
        if(isHovering(9, 13, 9, 57, mouseX, mouseY)) {
            List<ITextComponent> tooltip = Collections.singletonList(text);
            renderComponentTooltip(poseStack, tooltip, mouseX, mouseY);
        }
    }
}
