package com.coolerpromc.productiveslimes.compat.rei.DnaSynthesizing;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DnaSynthesizingCategory implements DisplayCategory<DnaSynthesizingRecipeDisplay> {
    public static final CategoryIdentifier<? extends DnaSynthesizingRecipeDisplay> DNA_SYNTHESIZING = CategoryIdentifier.of(ProductiveSlimes.MODID, "dna_synthesizing");
    public static final ResourceLocation TEXTURE = new ResourceLocation(ProductiveSlimes.MODID,"textures/gui/rei/dna_synthesizer_gui.png");

    private int tickCount = 0;

    @Override
    public CategoryIdentifier<? extends DnaSynthesizingRecipeDisplay> getCategoryIdentifier() {
        return DNA_SYNTHESIZING;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.productiveslimes.dna_synthesizer");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.DNA_SYNTHESIZER.get());
    }

    @Override
    public List<Widget> setupDisplay(DnaSynthesizingRecipeDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 77, bounds.getCenterY() - 41);
        List<Widget> widgets = new LinkedList<>();

        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y, 153, 83)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 31, startPoint.y + 12))
                .entries(display.getInputEntries().get(0)).markInput());

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 31, startPoint.y + 55))
                .entries(display.getInputEntries().get(1)).markInput());

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 52, startPoint.y + 34))
                .entries(Collections.singleton(EntryStacks.of(display.getInputItem()))).markInput());

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 82, startPoint.y + 55))
                .entries(Collections.singleton(EntryStacks.of(Items.EGG))).markInput());

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 125, startPoint.y + 34))
                .entries(display.getOutputEntries().get(0)).markOutput());

        Component text = Component.translatable("tooltip.productiveslimes.energy_usage", display.getEnergy());

        widgets.add(Widgets.createTooltip(new Rectangle(startPoint.x + 8, startPoint.y + 12, 10, 58), text));

        widgets.add(new Widget() {
            @Override
            public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
                Minecraft.getInstance().getTextureManager().bindForSetup(TEXTURE);

                // Arrow
                tickCount++;
                int arrowWidth = (tickCount % 600) * 26 / 600;
                int dnaHeight = (tickCount % 600) * 23 / 600;

                guiGraphics.blit(TEXTURE, startPoint.x + 77, startPoint.y + 38, 153, 0, arrowWidth, 8);

                // Energy bar
                int energyScaled = (int) Math.ceil((double) display.getEnergy() / 10000 * 57);
                energyScaled = arrowWidth >= 25 ? 0 : energyScaled;

                guiGraphics.blit(TEXTURE, startPoint.x + 9, (startPoint.y + 18) + (52 - energyScaled), 153, 65 - energyScaled, 9, energyScaled);
                guiGraphics.blit(TEXTURE, startPoint.x + 36, startPoint.y + 30, 153, 66, 6, dnaHeight);
            }

            @Override
            public List<? extends GuiEventListener> children() {
                return new ArrayList<>();
            }

            @Override
            public boolean isDragging() {
                return false;
            }

            @Override
            public void setDragging(boolean b) {

            }

            @Nullable
            @Override
            public GuiEventListener getFocused() {
                return null;
            }

            @Override
            public void setFocused(@Nullable GuiEventListener guiEventListener) {

            }
        });

        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 83;
    }
}
