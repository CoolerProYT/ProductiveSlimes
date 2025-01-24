package com.coolerpromc.productiveslimes.screen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class SlimeballCollectorScreen extends ContainerScreen<SlimeballCollectorMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ProductiveSlimes.MODID, "textures/gui/slimeball_collector_gui.png");

    public SlimeballCollectorScreen(SlimeballCollectorMenu pMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
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
        this.addButton(new Button(x + 88, y + 61, 80, 20, new StringTextComponent("Toggle Area"), button -> {
            this.menu.blockEntity.setEnableOutline(this.menu.blockEntity.getData().get(0) == 0 ? 1 : 0);
        }));
    }

    @Override
    protected void renderBg(MatrixStack poseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(MatrixStack poseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(poseStack);
        super.render(poseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(poseStack, pMouseX, pMouseY);
    }
}