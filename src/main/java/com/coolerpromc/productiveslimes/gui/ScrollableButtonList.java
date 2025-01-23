package com.coolerpromc.productiveslimes.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.AbstractList;

import java.util.ArrayList;
import java.util.List;

public class ScrollableButtonList extends AbstractList<ScrollableButtonList.Entry> {
    private final List<Entry> entries = new ArrayList<>();
    private final int margin;

    public ScrollableButtonList(Minecraft minecraft, int width, int height, int top, int x, int itemHeight) {
        super(minecraft, width, height, top, itemHeight + 2, itemHeight);
        super.setRenderHeader(false, 0);
        super.x0 = x;
        super.x1 = x + width;
        this.margin = 2;
    }

    public void addButton(Button button) {
        this.entries.add(new Entry(button));
        this.addEntry(new Entry(button));
    }

    @Override
    protected int getScrollbarPosition() {
        return this.x0 + 22;
    }

    @Override
    public int getRowWidth() {
        return 16; // Adjust the width of the rows if needed
    }

    @Override
    public boolean isFocused() {
        return false;
    }

    public static class Entry extends AbstractList.AbstractListEntry<Entry> {
        private final Button button;

        public Entry(Button button) {
            this.button = button;
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            return this.button.mouseClicked(mouseX, mouseY, button);
        }

        @Override
        public boolean mouseReleased(double mouseX, double mouseY, int button) {
            return this.button.mouseReleased(mouseX, mouseY, button);
        }

        @Override
        public void render(MatrixStack poseStack, int index, int y, int x, int rowWidth, int rowHeight, int mouseX, int mouseY, boolean isSelected, float partialTick) {
            this.button.y = (y - 4);
            this.button.render(poseStack, mouseX, mouseY, partialTick);
        }
    }
}