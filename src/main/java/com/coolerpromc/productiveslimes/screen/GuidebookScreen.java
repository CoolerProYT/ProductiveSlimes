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
            this.description = "Drop from Dirt Slime.";
        }, new ItemStack(ModItems.DIRT_SLIME_BALL.get()));

        Button stoneSlimeballButton = new CustomButton(x + 5, y + 27, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.STONE_SLIME_BALL.get());
            this.description = "Drop from Stone Slime.";
        }, new ItemStack(ModItems.STONE_SLIME_BALL.get()));

        Button copperSlimeballButton = new CustomButton(x + 5, y + 45, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.COPPER_SLIME_BALL.get());
            this.description = "Drop from Copper Slime.";
        }, new ItemStack(ModItems.COPPER_SLIME_BALL.get()));

        Button ironSlimeballButton = new CustomButton(x + 5, y + 63, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.IRON_SLIME_BALL.get());
            this.description = "Drop from Iron Slime.";
        }, new ItemStack(ModItems.IRON_SLIME_BALL.get()));

        Button goldSlimeballButton = new CustomButton(x + 5, y + 81, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.GOLD_SLIME_BALL.get());
            this.description = "Drop from Gold Slime.";
        }, new ItemStack(ModItems.GOLD_SLIME_BALL.get()));

        Button diamondSlimeballButton = new CustomButton(x + 5, y + 99, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.DIAMOND_SLIME_BALL.get());
            this.description = "Drop from Diamond Slime.";
        }, new ItemStack(ModItems.DIAMOND_SLIME_BALL.get()));

        Button netheriteSlimeballButton = new CustomButton(x + 5, y + 117, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.NETHERITE_SLIME_BALL.get());
            this.description = "Drop from Netherite Slime.";
        }, new ItemStack(ModItems.NETHERITE_SLIME_BALL.get()));

        Button lapisSlimeballButton = new CustomButton(x + 5, y + 135, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.LAPIS_SLIME_BALL.get());
            this.description = "Drop from Lapis Slime.";
        }, new ItemStack(ModItems.LAPIS_SLIME_BALL.get()));

        Button redstoneSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.REDSTONE_SLIME_BALL.get());
            this.description = "Drop from Redstone Slime.";
        }, new ItemStack(ModItems.REDSTONE_SLIME_BALL.get()));

        Button oakSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.OAK_SLIME_BALL.get());
            this.description = "Drop from Oak Slime.";
        }, new ItemStack(ModItems.OAK_SLIME_BALL.get()));

        Button sandSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.SAND_SLIME_BALL.get());
            this.description = "Drop from Sand Slime.";
        }, new ItemStack(ModItems.SAND_SLIME_BALL.get()));

        Button andesiteSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.ANDESITE_SLIME_BALL.get());
            this.description = "Drop from Andesite Slime.";
        }, new ItemStack(ModItems.ANDESITE_SLIME_BALL.get()));

        Button snowSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.SNOW_SLIME_BALL.get());
            this.description = "Drop from Snow Slime.";
        }, new ItemStack(ModItems.SNOW_SLIME_BALL.get()));

        Button iceSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.ICE_SLIME_BALL.get());
            this.description = "Drop from Ice Slime.";
        }, new ItemStack(ModItems.ICE_SLIME_BALL.get()));

        Button mudSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.MUD_SLIME_BALL.get());
            this.description = "Drop from Mud Slime.";
        }, new ItemStack(ModItems.MUD_SLIME_BALL.get()));

        Button claySlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.CLAY_SLIME_BALL.get());
            this.description = "Drop from Clay Slime.";
        }, new ItemStack(ModItems.CLAY_SLIME_BALL.get()));

        Button redSandSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.RED_SAND_SLIME_BALL.get());
            this.description = "Drop from Red Sand Slime.";
        }, new ItemStack(ModItems.RED_SAND_SLIME_BALL.get()));

        Button mossSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.MOSS_SLIME_BALL.get());
            this.description = "Drop from Moss Slime.";
        }, new ItemStack(ModItems.MOSS_SLIME_BALL.get()));

        Button deepslateSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.DEEPSLATE_SLIME_BALL.get());
            this.description = "Drop from Deepslate Slime.";
        }, new ItemStack(ModItems.DEEPSLATE_SLIME_BALL.get()));

        Button graniteSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.GRANITE_SLIME_BALL.get());
            this.description = "Drop from Granite Slime.";
        }, new ItemStack(ModItems.GRANITE_SLIME_BALL.get()));

        Button dioriteSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.DIORITE_SLIME_BALL.get());
            this.description = "Drop from Diorite Slime.";
        }, new ItemStack(ModItems.DIORITE_SLIME_BALL.get()));

        Button calciteSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.CALCITE_SLIME_BALL.get());
            this.description = "Drop from Calcite Slime.";
        }, new ItemStack(ModItems.CALCITE_SLIME_BALL.get()));

        Button tuffSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.TUFF_SLIME_BALL.get());
            this.description = "Drop from Tuff Slime.";
        }, new ItemStack(ModItems.TUFF_SLIME_BALL.get()));

        Button dripstoneSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.DRIPSTONE_SLIME_BALL.get());
            this.description = "Drop from Dripstone Slime.";
        }, new ItemStack(ModItems.DRIPSTONE_SLIME_BALL.get()));

        Button netherrackSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.NETHERITE_SLIME_BALL.get());
            this.description = "Drop from Netherrack Slime.";
        }, new ItemStack(ModItems.NETHERITE_SLIME_BALL.get()));

        Button prismarineSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.PRISMARINE_SLIME_BALL.get());
            this.description = "Drop from Prismarine Slime.";
        }, new ItemStack(ModItems.PRISMARINE_SLIME_BALL.get()));

        Button magmaSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.MAGMA_SLIME_BALL.get());
            this.description = "Drop from Magma Slime.";
        }, new ItemStack(ModItems.MAGMA_SLIME_BALL.get()));

        Button obsidianSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.OBSIDIAN_SLIME_BALL.get());
            this.description = "Drop from Obsidian Slime.";
        }, new ItemStack(ModItems.OBSIDIAN_SLIME_BALL.get()));

        Button soulSandSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.SOUL_SAND_SLIME_BALL.get());
            this.description = "Drop from Soul Sand Slime.";
        }, new ItemStack(ModItems.SOUL_SAND_SLIME_BALL.get()));

        Button soulSoilSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.SOUL_SOIL_SLIME_BALL.get());
            this.description = "Drop from Soul Soil Slime.";
        }, new ItemStack(ModItems.SOUL_SOIL_SLIME_BALL.get()));

        Button blackstoneSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.BLACKSTONE_SLIME_BALL.get());
            this.description = "Drop from Blackstone Slime.";
        }, new ItemStack(ModItems.BLACKSTONE_SLIME_BALL.get()));

        Button basaltSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.BASALT_SLIME_BALL.get());
            this.description = "Drop from Basalt Slime.";
        }, new ItemStack(ModItems.BASALT_SLIME_BALL.get()));

        Button endstoneSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.ENDSTONE_SLIME_BALL.get());
            this.description = "Drop from Endstone Slime.";
        }, new ItemStack(ModItems.ENDSTONE_SLIME_BALL.get()));

        Button quartzSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.QUARTZ_SLIME_BALL.get());
            this.description = "Drop from Quartz Slime.";
        }, new ItemStack(ModItems.QUARTZ_SLIME_BALL.get()));

        Button glowstoneSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.GLOWSTONE_SLIME_BALL.get());
            this.description = "Drop from Glowstone Slime.";
        }, new ItemStack(ModItems.GLOWSTONE_SLIME_BALL.get()));

        Button amethystSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.AMETHYST_SLIME_BALL.get());
            this.description = "Drop from Amethyst Slime.";
        }, new ItemStack(ModItems.AMETHYST_SLIME_BALL.get()));

        Button brownMushroomSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.BROWN_MUSHROOM_SLIME_BALL.get());
            this.description = "Drop from Brown Mushroom Slime.";
        }, new ItemStack(ModItems.BROWN_MUSHROOM_SLIME_BALL.get()));

        Button redMushroomSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.RED_MUSHROOM_SLIME_BALL.get());
            this.description = "Drop from Red Mushroom Slime.";
        }, new ItemStack(ModItems.RED_MUSHROOM_SLIME_BALL.get()));

        Button cactusSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.CACTUS_SLIME_BALL.get());
            this.description = "Drop from Cactus Slime.";
        }, new ItemStack(ModItems.CACTUS_SLIME_BALL.get()));

        Button coalSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.COAL_SLIME_BALL.get());
            this.description = "Drop from Coal Slime.";
        }, new ItemStack(ModItems.COAL_SLIME_BALL.get()));

        Button gravelSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModItems.GRAVEL_SLIME_BALL.get());
            this.description = "Drop from Gravel Slime.";
        }, new ItemStack(ModItems.GRAVEL_SLIME_BALL.get()));

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
        scrollableButtonList.addButton(sandSlimeballButton);
        scrollableButtonList.addButton(andesiteSlimeballButton);
        scrollableButtonList.addButton(snowSlimeballButton);
        scrollableButtonList.addButton(iceSlimeballButton);
        scrollableButtonList.addButton(mudSlimeballButton);
        scrollableButtonList.addButton(claySlimeballButton);
        scrollableButtonList.addButton(redSandSlimeballButton);
        scrollableButtonList.addButton(mossSlimeballButton);
        scrollableButtonList.addButton(deepslateSlimeballButton);
        scrollableButtonList.addButton(graniteSlimeballButton);
        scrollableButtonList.addButton(dioriteSlimeballButton);
        scrollableButtonList.addButton(calciteSlimeballButton);
        scrollableButtonList.addButton(tuffSlimeballButton);
        scrollableButtonList.addButton(dripstoneSlimeballButton);
        scrollableButtonList.addButton(netherrackSlimeballButton);
        scrollableButtonList.addButton(prismarineSlimeballButton);
        scrollableButtonList.addButton(magmaSlimeballButton);
        scrollableButtonList.addButton(obsidianSlimeballButton);
        scrollableButtonList.addButton(soulSandSlimeballButton);
        scrollableButtonList.addButton(soulSoilSlimeballButton);
        scrollableButtonList.addButton(blackstoneSlimeballButton);
        scrollableButtonList.addButton(basaltSlimeballButton);
        scrollableButtonList.addButton(endstoneSlimeballButton);
        scrollableButtonList.addButton(quartzSlimeballButton);
        scrollableButtonList.addButton(glowstoneSlimeballButton);
        scrollableButtonList.addButton(amethystSlimeballButton);
        scrollableButtonList.addButton(brownMushroomSlimeballButton);
        scrollableButtonList.addButton(redMushroomSlimeballButton);
        scrollableButtonList.addButton(cactusSlimeballButton);
        scrollableButtonList.addButton(coalSlimeballButton);
        scrollableButtonList.addButton(gravelSlimeballButton);

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