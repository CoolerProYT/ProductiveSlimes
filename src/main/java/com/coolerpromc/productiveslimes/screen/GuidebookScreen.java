package com.coolerpromc.productiveslimes.screen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.gui.CustomButton;
import com.coolerpromc.productiveslimes.gui.ScrollableButtonList;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
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
        this.description = "Welcome to the Productive Slimes Guidebook! For more information please visit the wiki at https://coolerproyt.github.io\n/ProductiveSlimes-Wiki/";
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
            this.description = "Welcome to the Productive Slimes Guidebook! For more information please visit the wiki at https://coolerproyt.github.io\n/ProductiveSlimes-Wiki/";
        }, new ItemStack(Items.SLIME_BALL));

        Button dirtSlimeballButton = new CustomButton(x + 5, y + 9, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("dirt").get());
            this.description = "Drop from Dirt Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("dirt").get()));

        Button stoneSlimeballButton = new CustomButton(x + 5, y + 27, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("stone").get());
            this.description = "Drop from Stone Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("stone").get()));

        Button copperSlimeballButton = new CustomButton(x + 5, y + 45, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("copper").get());
            this.description = "Drop from Copper Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("copper").get()));

        Button ironSlimeballButton = new CustomButton(x + 5, y + 63, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("iron").get());
            this.description = "Drop from Iron Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("iron").get()));

        Button goldSlimeballButton = new CustomButton(x + 5, y + 81, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("gold").get());
            this.description = "Drop from Gold Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("gold").get()));

        Button diamondSlimeballButton = new CustomButton(x + 5, y + 99, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("diamond").get());
            this.description = "Drop from Diamond Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("diamond").get()));

        Button netheriteSlimeballButton = new CustomButton(x + 5, y + 117, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("netherite").get());
            this.description = "Drop from Netherite Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("netherite").get()));

        Button lapisSlimeballButton = new CustomButton(x + 5, y + 135, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("lapis").get());
            this.description = "Drop from Lapis Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("lapis").get()));

        Button redstoneSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("redstone").get());
            this.description = "Drop from Redstone Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("redstone").get()));

        Button oakSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("oak").get());
            this.description = "Drop from Oak Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("oak").get()));

        Button sandSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("sand").get());
            this.description = "Drop from Sand Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("sand").get()));

        Button andesiteSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("andesite").get());
            this.description = "Drop from Andesite Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("andesite").get()));

        Button snowSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("snow").get());
            this.description = "Drop from Snow Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("snow").get()));

        Button iceSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("ice").get());
            this.description = "Drop from Ice Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("ice").get()));

        Button mudSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("mud").get());
            this.description = "Drop from Mud Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("mud").get()));

        Button claySlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("clay").get());
            this.description = "Drop from Clay Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("clay").get()));

        Button redSandSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("red_sand").get());
            this.description = "Drop from Red Sand Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("red_sand").get()));

        Button mossSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("moss").get());
            this.description = "Drop from Moss Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("moss").get()));

        Button deepslateSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("deepslate").get());
            this.description = "Drop from Deepslate Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("deepslate").get()));

        Button graniteSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("granite").get());
            this.description = "Drop from Granite Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("granite").get()));

        Button dioriteSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("diorite").get());
            this.description = "Drop from Diorite Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("diorite").get()));

        Button calciteSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("calcite").get());
            this.description = "Drop from Calcite Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("calcite").get()));

        Button tuffSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("tuff").get());
            this.description = "Drop from Tuff Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("tuff").get()));

        Button dripstoneSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("dripstone").get());
            this.description = "Drop from Dripstone Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("dripstone").get()));

        Button netherrackSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("netherite").get());
            this.description = "Drop from Netherrack Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("netherite").get()));

        Button prismarineSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("prismarine").get());
            this.description = "Drop from Prismarine Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("prismarine").get()));

        Button magmaSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("magma").get());
            this.description = "Drop from Magma Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("magma").get()));

        Button obsidianSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("obsidian").get());
            this.description = "Drop from Obsidian Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("obsidian").get()));

        Button soulSandSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("soul_sand").get());
            this.description = "Drop from Soul Sand Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("soul_sand").get()));

        Button soulSoilSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("soul_soil").get());
            this.description = "Drop from Soul Soil Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("soul_soil").get()));

        Button blackstoneSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("blackstone").get());
            this.description = "Drop from Blackstone Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("blackstone").get()));

        Button basaltSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("basalt").get());
            this.description = "Drop from Basalt Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("basalt").get()));

        Button endstoneSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("end_stone").get());
            this.description = "Drop from Endstone Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("end_stone").get()));

        Button quartzSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("quartz").get());
            this.description = "Drop from Quartz Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("quartz").get()));

        Button glowstoneSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("glowstone").get());
            this.description = "Drop from Glowstone Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("glowstone").get()));

        Button amethystSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("amethyst").get());
            this.description = "Drop from Amethyst Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("amethyst").get()));

        Button brownMushroomSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("brown_mushroom").get());
            this.description = "Drop from Brown Mushroom Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("brown_mushroom").get()));

        Button redMushroomSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("red_mushroom").get());
            this.description = "Drop from Red Mushroom Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("red_mushroom").get()));

        Button cactusSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("cactus").get());
            this.description = "Drop from Cactus Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("cactus").get()));

        Button coalSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("coal").get());
            this.description = "Drop from Coal Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("coal").get()));

        Button gravelSlimeballButton = new CustomButton(x + 5, y + 153, 16, 16, (button) -> {
            this.displayItem = new ItemStack(ModTierLists.getSlimeballItemByName("gravel").get());
            this.description = "Drop from Gravel Slime.";
        }, new ItemStack(ModTierLists.getSlimeballItemByName("gravel").get()));

        addScrollableButtonList(
                homeSlimeButton,
                dirtSlimeballButton,
                stoneSlimeballButton,
                copperSlimeballButton,
                ironSlimeballButton,
                goldSlimeballButton,
                diamondSlimeballButton,
                netheriteSlimeballButton,
                lapisSlimeballButton,
                redstoneSlimeballButton,
                oakSlimeballButton,
                sandSlimeballButton,
                andesiteSlimeballButton,
                snowSlimeballButton,
                iceSlimeballButton,
                mudSlimeballButton,
                claySlimeballButton,
                redSandSlimeballButton,
                mossSlimeballButton,
                deepslateSlimeballButton,
                graniteSlimeballButton,
                dioriteSlimeballButton,
                calciteSlimeballButton,
                tuffSlimeballButton,
                dripstoneSlimeballButton,
                netherrackSlimeballButton,
                prismarineSlimeballButton,
                magmaSlimeballButton,
                obsidianSlimeballButton,
                soulSandSlimeballButton,
                soulSoilSlimeballButton,
                blackstoneSlimeballButton,
                basaltSlimeballButton,
                endstoneSlimeballButton,
                quartzSlimeballButton,
                glowstoneSlimeballButton,
                amethystSlimeballButton,
                brownMushroomSlimeballButton,
                redMushroomSlimeballButton,
                cactusSlimeballButton,
                coalSlimeballButton,
                gravelSlimeballButton
        );

        this.addRenderableWidget(scrollableButtonList);
    }

    private void addScrollableButtonList(Button ...button) {
        for (Button b : button) {
            scrollableButtonList.addButton(b);
        }
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