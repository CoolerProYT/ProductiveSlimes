package com.coolerpromc.productiveslimes.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarrationElementOutput;

import java.util.ArrayList;
import java.util.List;

public class ScrollableButtonList extends AbstractSelectionList<ScrollableButtonList.Entry> {
    private final List<Entry> entries = new ArrayList<>();
    private final int margin;

    public ScrollableButtonList(Minecraft minecraft, int width, int height, int top, int x, int itemHeight) {
        super(minecraft, width, height, top, itemHeight + 2);
        super.setRenderHeader(false, 0);
        super.setX(x);
        this.margin = 2;
    }

    public void addButton(Button button) {
        this.entries.add(new Entry(button));
        this.addEntry(new Entry(button));
    }

    @Override
    protected int getScrollbarPosition() {
        return this.getX() + 22;
    }

    @Override
    public int getRowWidth() {
        return 16; // Adjust the width of the rows if needed
    }

    @Override
    public boolean isFocused() {
        return false;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

    }

    @Override
    protected void renderListBackground(GuiGraphics pGuiGraphics) {

    }

    @Override
    protected void renderListSeparators(GuiGraphics pGuiGraphics) {

    }

    @Override
    protected void renderDecorations(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {

    }

    @Override
    protected void renderSelection(GuiGraphics pGuiGraphics, int pTop, int pWidth, int pHeight, int pOuterColor, int pInnerColor) {

    }

    public static class Entry extends AbstractSelectionList.Entry<Entry> {
        private final Button button;

        public Entry(Button button) {
            this.button = button;
        }

        @Override
        public void render(GuiGraphics guiGraphics, int index, int y, int x, int rowWidth, int rowHeight, int mouseX, int mouseY, boolean isSelected, float partialTick) {
            this.button.setY(y - 4);
            this.button.render(guiGraphics, mouseX, mouseY, partialTick);
        }

        @Override
        public void renderBack(GuiGraphics pGuiGraphics, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean pIsMouseOver, float pPartialTick) {

        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            return this.button.mouseClicked(mouseX, mouseY, button);
        }

        @Override
        public boolean mouseReleased(double mouseX, double mouseY, int button) {
            return this.button.mouseReleased(mouseX, mouseY, button);
        }
    }
}