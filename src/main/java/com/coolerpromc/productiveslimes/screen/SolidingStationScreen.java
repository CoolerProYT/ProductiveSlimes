package com.coolerpromc.productiveslimes.screen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.screen.renderer.FluidTankRenderer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;

public class SolidingStationScreen extends AbstractContainerScreen<SolidingStationMenu>{
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/gui/soliding_station_gui.png");

    public SolidingStationScreen(SolidingStationMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 74;
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        this.titleLabelY = 4;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        pGuiGraphics.blit(RenderType::guiTextured, TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
        int energyScaled = this.menu.getEnergyStoredScaled();

        pGuiGraphics.blit(RenderType::guiTextured, TEXTURE, x + 9, y + 13 + (57 - energyScaled), 176, 65 - energyScaled, 9, energyScaled, 256, 256);

        renderProgressArrow(pGuiGraphics, x, y);

        FluidTankRenderer.renderFluidStack(pGuiGraphics, menu.blockEntity.getFluid(), menu.blockEntity.getFluidTank().getCapacity(), 15, 57, x + 22, y + 13);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(RenderType::guiTextured, TEXTURE, x + 94, y + 38, 176, 0, menu.getScaledProgress(), 8, 256, 256);
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
        fluidTankTooltip.add(Component.translatable("productiveslimes.tooltip.liquid.amount.with.capacity", menu.blockEntity.getFluid().getAmount(), menu.blockEntity.getFluidTank().getCapacity()));
        if(isHovering(22, 13, 15, 57, pMouseX, pMouseY)) {
            pGuiGraphics.renderComponentTooltip(this.font, fluidTankTooltip, pMouseX, pMouseY);
        }
    }
}
