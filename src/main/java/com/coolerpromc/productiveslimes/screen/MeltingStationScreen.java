package com.coolerpromc.productiveslimes.screen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.screen.renderer.FluidTankRenderer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;

public class MeltingStationScreen extends AbstractContainerScreen<MeltingStationMenu>{
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/gui/melting_station_gui.png");

    public MeltingStationScreen(MeltingStationMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
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
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        pGuiGraphics.blit(RenderType::guiTextured, TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
        int energyScaled = this.menu.getEnergyStoredScaled();

        pGuiGraphics.blit(RenderType::guiTextured, TEXTURE, x + 9, y + 13 + (57 - energyScaled), 176, 65 - energyScaled, 9, energyScaled, 256, 256);

        renderProgressArrow(pGuiGraphics, x, y);

        FluidTankRenderer.renderFluidStack(pGuiGraphics, menu.blockEntity.getFluid(), menu.blockEntity.getOutputHandler().getCapacity(), 15, 57, x + 153, y + 13);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(RenderType::guiTextured, TEXTURE, x + 77, y + 38, 176, 0, menu.getScaledProgress(), 8, 256, 256);
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);

        int energyStored = this.menu.getEnergy();
        int maxEnergy = this.menu.getMaxEnergy();

        Component text = Component.translatable("gui.productiveslimes.energy_stored", energyStored, maxEnergy);
        if(isHovering(9, 13, 9, 57, pMouseX, pMouseY)) {
            pGuiGraphics.renderTooltip(this.font, text, pMouseX, pMouseY);
        }

        List<Component> fluidTankTooltip = new ArrayList<>();
        fluidTankTooltip.add(Component.translatable(menu.blockEntity.getFluid().getDescriptionId()));
        fluidTankTooltip.add(Component.translatable("productiveslimes.tooltip.liquid.amount.with.capacity", menu.blockEntity.getFluid().getAmount(), menu.blockEntity.getOutputHandler().getCapacity()));
        if(isHovering(153, 13, 15, 57, pMouseX, pMouseY)) {
            pGuiGraphics.renderComponentTooltip(this.font, fluidTankTooltip, pMouseX, pMouseY);
        }
    }
}
