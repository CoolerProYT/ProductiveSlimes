package com.coolerpromc.productiveslimes.screen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collections;
import java.util.List;

public class DnaExtractorScreen extends ContainerScreen<DnaExtractorMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ProductiveSlimes.MODID, "textures/gui/dna_extractor_gui.png");

    public DnaExtractorScreen(DnaExtractorMenu pMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 74;
        this.titleLabelX = 54;
        this.titleLabelY = 5;
    }

    @Override
    protected void renderBg(MatrixStack poseStack, float v, int i, int i1) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        blit(poseStack, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
        int energyScaled = this.menu.getEnergyStoredScaled();

        blit(poseStack, x + 9, y + 13 + (57 - energyScaled), 176, 65 - energyScaled, 9, energyScaled, 256, 256);

        renderProgressArrow(poseStack, x, y);
    }

    private void renderProgressArrow(MatrixStack poseStack, int x, int y) {
        if(menu.isCrafting()) {
            blit(poseStack, x + 77, y + 38, 176, 0, menu.getScaledProgress(), 8, 256, 256);
        }
    }

    @Override
    public void render(MatrixStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);

        int energyStored = this.menu.getEnergy();
        int maxEnergy = this.menu.getMaxEnergy();

        ITextComponent text = new TranslationTextComponent("gui.productiveslimes.energy_stored", energyStored, maxEnergy);
        if(isHovering(9, 13, 9, 57, pMouseX, pMouseY)) {
            List<ITextComponent> tooltip = Collections.singletonList(text);
            renderComponentTooltip(pPoseStack, tooltip, pMouseX, pMouseY);
        }
    }
}
