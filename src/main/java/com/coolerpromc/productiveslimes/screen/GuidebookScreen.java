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
        this.description = "Welcome to the Productive Slimes Guidebook! Click on a slimeball to learn more about it. Also try to use same tier of block on the slime, eg. Dirt on Dirt Slime (Except max size slime).";
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

        Button dirtSlimeballButton = new CustomButton(x + 5, y + 9, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.DIRT_SLIME_BALL.get());
            this.description = "Drop from Dirt Slime. Dirt Slime is obtainable by using a Dirt on a Slime.";
        }, new ItemStack(ModItems.DIRT_SLIME_BALL.get()));

        Button stoneSlimeballButton = new CustomButton(x + 5, y + 27, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.STONE_SLIME_BALL.get());
            this.description = "Drop from Stone Slime. Stone Slime is obtainable by using a Stone on a Dirt Slime.";
        }, new ItemStack(ModItems.STONE_SLIME_BALL.get()));

        Button copperSlimeballButton = new CustomButton(x + 5, y + 45, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.COPPER_SLIME_BALL.get());
            this.description = "Drop from Copper Slime. Copper Slime is obtainable by using a Copper Block on a Slime.";
        }, new ItemStack(ModItems.COPPER_SLIME_BALL.get()));

        Button ironSlimeballButton = new CustomButton(x + 5, y + 63, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.IRON_SLIME_BALL.get());
            this.description = "Drop from Iron Slime. Iron Slime is obtainable by using a Iron Block on a Copper Slime.";
        }, new ItemStack(ModItems.IRON_SLIME_BALL.get()));

        Button goldSlimeballButton = new CustomButton(x + 5, y + 81, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.GOLD_SLIME_BALL.get());
            this.description = "Drop from Gold Slime. Gold Slime is obtainable by using a Gold Block on a Iron Slime.";
        }, new ItemStack(ModItems.GOLD_SLIME_BALL.get()));

        Button diamondSlimeballButton = new CustomButton(x + 5, y + 99, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.DIAMOND_SLIME_BALL.get());
            this.description = "Drop from Diamond Slime. Diamond Slime is obtainable by using a Diamond Block on a Gold Slime.";
        }, new ItemStack(ModItems.DIAMOND_SLIME_BALL.get()));

        Button netheriteSlimeballButton = new CustomButton(x + 5, y + 117, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.NETHERITE_SLIME_BALL.get());
            this.description = "Drop from Netherite Slime. Netherite Slime is obtainable by using a Netherite Block on a Diamond Slime.";
        }, new ItemStack(ModItems.NETHERITE_SLIME_BALL.get()));

        Button lapisSlimeballButton = new CustomButton(x + 5, y + 135, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.LAPIS_SLIME_BALL.get());
            this.description = "Drop from Lapis Slime. Lapis Slime is obtainable by using a Lapis Block on a Gold Slime.";
        }, new ItemStack(ModItems.LAPIS_SLIME_BALL.get()));

        Button redstoneSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.REDSTONE_SLIME_BALL.get());
            this.description = "Drop from Redstone Slime. Redstone Slime is obtainable by using a Redstone Block on a Iron Slime.";
        }, new ItemStack(ModItems.REDSTONE_SLIME_BALL.get()));

        Button oakSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.OAK_SLIME_BALL.get());
            this.description = "Drop from Oak Slime. Oak Slime is obtainable by using an Oak Planks on a Stone Slime.";
        }, new ItemStack(ModItems.OAK_SLIME_BALL.get()));

        Button coalSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.COAL_SLIME_BALL.get());
            this.description = "Drop from Coal Slime. Coal Slime is obtainable by using a Coal Block on a Stone Slime.";
        }, new ItemStack(ModItems.COAL_SLIME_BALL.get()));

//        this.addRenderableWidget(dirtSlimeballButton);
//        this.addRenderableWidget(stoneSlimeballButton);
//        this.addRenderableWidget(copperSlimeballButton);
//        this.addRenderableWidget(ironSlimeballButton);
//        this.addRenderableWidget(goldSlimeballButton);
//        this.addRenderableWidget(diamondSlimeballButton);
//        this.addRenderableWidget(netheriteSlimeballButton);
//        this.addRenderableWidget(lapisSlimeballButton);
//        this.addRenderableWidget(redstoneSlimeballButton);

        scrollableButtonList.addButton(homeSlimeButton);
        scrollableButtonList.addButton(dirtSlimeballButton);
        scrollableButtonList.addButton(stoneSlimeballButton);
        scrollableButtonList.addButton(copperSlimeballButton);
        scrollableButtonList.addButton(ironSlimeballButton);
        scrollableButtonList.addButton(goldSlimeballButton);
        scrollableButtonList.addButton(diamondSlimeballButton);
        scrollableButtonList.addButton(netheriteSlimeballButton);
        scrollableButtonList.addButton(lapisSlimeballButton);
        scrollableButtonList.addButton(redstoneSlimeballButton);
        scrollableButtonList.addButton(oakSlimeballButton);
        scrollableButtonList.addButton(coalSlimeballButton);

        this.addRenderableWidget(scrollableButtonList);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (this.width - imageWidth) / 2;
        int y = (this.height - imageHeight) / 2;
        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
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