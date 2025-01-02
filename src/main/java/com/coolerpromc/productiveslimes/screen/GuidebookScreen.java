package com.coolerpromc.productiveslimes.screen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.gui.CustomButton;
import com.coolerpromc.productiveslimes.gui.ScrollableButtonList;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class GuidebookScreen extends AbstractContainerScreen<GuidebookMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/gui/guidebook_gui.png");
    private ScrollableButtonList scrollableButtonList;

    private ItemStack displayItem;
    private String description;


    public GuidebookScreen(GuidebookMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.displayItem = new ItemStack(Items.SLIME_BALL);
        this.description = "Welcome to the Productive Slimes Guidebook! For more information please visit the wiki at https://coolerproyt.github.io/ProductiveSlimes-Wiki/";
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelX = 1000000;
        this.inventoryLabelY = 1000000;
        this.titleLabelX = 10000000;

        int x = (this.width - imageWidth) / 2;
        int y = (this.height - imageHeight) / 2;

        scrollableButtonList = new ScrollableButtonList(minecraft, 22, 148, y + 9, x, 16);

        Button homeSlimeButton = new CustomButton(x + 5, y, 16, 16, (button) -> {
            this.displayItem = new ItemStack(Items.SLIME_BALL);
            this.description = "Welcome to the Productive Slimes Guidebook! Click on a slimeball to learn more about it. Also try to use same tier of block on the slime, eg. Dirt on Dirt Slime (Except max size slime).";
        }, new ItemStack(Items.SLIME_BALL));

        scrollableButtonList.addButton(homeSlimeButton);

        this.addRenderableWidget(scrollableButtonList);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (this.width - imageWidth) / 2;
        int y = (this.height - imageHeight) / 2;
        pGuiGraphics.blit(RenderType::guiTextured, TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        int x = (this.width - imageWidth) / 2;
        int y = (this.height - imageHeight) / 2;

        if (this.description != null && this.displayItem != null){
            if (this.displayItem.getItem() == Items.SLIME_BALL){
                pGuiGraphics.drawCenteredString(this.font, "Productive Slimes", x + (imageWidth / 2) + 15, y + 10, 0x404040);
            } else {
                pGuiGraphics.drawCenteredString(this.font, displayItem.getHoverName(), x + (imageWidth / 2) + 15, y + 10, 0x404040);
            }

            pGuiGraphics.renderItem(displayItem, x + (imageWidth) / 2 + 5, y + 25);

            if (pMouseX >= x + (imageWidth) / 2 + 5 && pMouseX < x + 26 + imageWidth/2 && pMouseY >= y + 25 && pMouseY < y + 41) {
                pGuiGraphics.renderTooltip(this.font, displayItem, pMouseX, pMouseY);
            }

            List<String> lines = wrapText(description, 25);

            for (int i = 0; i < lines.size(); i++){
                pGuiGraphics.drawCenteredString(this.font, lines.get(i), x + (imageWidth / 2) + 15, y + 50 + (i * 10), 0x404040);
            }
        }

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    public static List<String> wrapText(String description, int maxLineLength) {
        List<String> lines = new ArrayList<>();

        String[] words = description.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            if (currentLine.length() + word.length() + 1 > maxLineLength) {
                // Add the current line to the list if it exceeds the maxLineLength
                lines.add(currentLine.toString());
                currentLine = new StringBuilder();
            }
            if (currentLine.length() > 0) {
                currentLine.append(" ");
            }
            currentLine.append(word);
        }

        // Add any remaining text as the last line
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines;
    }
}