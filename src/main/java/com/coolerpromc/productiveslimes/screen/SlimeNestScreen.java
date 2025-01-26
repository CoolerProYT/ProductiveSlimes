package com.coolerpromc.productiveslimes.screen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;

import java.util.List;

public class SlimeNestScreen extends ContainerScreen<SlimeNestMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ProductiveSlimes.MODID, "textures/gui/slime_nest_gui.png");

    public SlimeNestScreen(SlimeNestMenu pMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
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
        int countdown = menu.getCountdown();
        ITextComponent cd = new StringTextComponent("Cooldown: " + countdown + "s").setStyle(Style.EMPTY.withColor(Color.fromRgb(0xa5f5a6)));
        ITextComponent size = new StringTextComponent("Slime Size: " + menu.getSlimeSize()).setStyle(Style.EMPTY.withColor(Color.fromRgb(0xa5f5a6)));
        ITextComponent multiplier = new StringTextComponent("Multiplier: " + menu.getMultiplier()).setStyle(Style.EMPTY.withColor(Color.fromRgb(0xa5f5a6)));
        ITextComponent dropItem = new StringTextComponent("Drop Item: ").append(new TranslationTextComponent(menu.getDrop().getItem().getDescriptionId())).setStyle(Style.EMPTY.withColor(Color.fromRgb(0xa5f5a6)));

        int guiLeft = (width - imageWidth) / 2;
        int guiTop = (height - imageHeight) / 2;

        int textX = guiLeft + 54;
        int textY = guiTop + 17;

        if (!(menu.hasSlime() && menu.hasOutputSlot())) {
            if (!menu.hasOutputSlot()) {
                cd = new StringTextComponent("No Output Slot").setStyle(Style.EMPTY.withColor(Color.fromRgb(0xd59c20)));
            } else {
                cd = new StringTextComponent("No Slime Found").setStyle(Style.EMPTY.withColor(Color.fromRgb(0xc70d0d)));
            }
        }

        poseStack.pushPose();
        poseStack.translate(textX, textY, 0);
        poseStack.scale(0.75f, 0.75f, 0.75f);
        drawString(poseStack, Minecraft.getInstance().font, cd, 0, 0, 0xFFFFFF);
        poseStack.popPose();

        if (menu.hasSlime()) {
            poseStack.pushPose();
            poseStack.translate(textX, textY + 8, 0);
            poseStack.scale(0.75f, 0.75f, 0.75f);
            drawString(poseStack, Minecraft.getInstance().font, size, 0, 0, 0xFFFFFF);
            poseStack.popPose();
        }

        if (menu.hasSlime()) {
            poseStack.pushPose();
            if (String.valueOf(menu.getMultiplier()).length() >= 6) {
                poseStack.translate(textX, textY + 16, 0);
                poseStack.scale(0.7f, 0.7f, 0.7f);
                drawString(poseStack, Minecraft.getInstance().font, multiplier, 0, 0, 0xFFFFFF);
            } else {
                poseStack.translate(textX, textY + 16, 0);
                poseStack.scale(0.75f, 0.75f, 0.75f);
                drawString(poseStack, Minecraft.getInstance().font, multiplier, 0, 0, 0xFFFFFF);
            }
            poseStack.popPose();

            List<IReorderingProcessor> lines = font.split(dropItem, 85);
            for (int i = 0; i < lines.size(); i++) {
                poseStack.pushPose();
                poseStack.translate(textX, textY + 24 + (i * font.lineHeight - (2 * i)), 0);
                poseStack.scale(0.75f, 0.75f, 0.75f);
                font.draw(poseStack, lines.get(i), 0, 0, 0xFFFFFF);
                poseStack.popPose();
            }
        }
    }
}