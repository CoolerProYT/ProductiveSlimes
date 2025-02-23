package com.coolerpromc.productiveslimes.compat.rei.Soliding;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.screen.renderer.FluidTankRenderer;
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
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SolidingCategory implements DisplayCategory<SolidingRecipeDisplay> {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID,"textures/gui/rei/soliding_station_gui.png");

    private int tickCount = 0;

    @Override
    public CategoryIdentifier<? extends SolidingRecipeDisplay> getCategoryIdentifier() {
        return SolidingRecipeDisplay.CATEGORY;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.productiveslimes.soliding_station");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.LIQUID_SOLIDING_STATION.get());
    }

    @Override
    public List<Widget> setupDisplay(SolidingRecipeDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 77, bounds.getCenterY() - 41);
        List<Widget> widgets = new LinkedList<>();

        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y, 153, 83)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 127, startPoint.y + 34)).entries(display.getOutputEntries().getFirst()).markOutput());

        Component text = Component.translatable("tooltip.productiveslimes.energy_usage", display.recipe().value().energy());

        widgets.add(Widgets.createTooltip(new Rectangle(startPoint.x + 8, startPoint.y + 12, 10, 58), text));

        List<Component> fluidTankTooltip = new ArrayList<>();
        fluidTankTooltip.add(Component.translatable(display.recipe().value().fluidStack().getDescriptionId()));
        fluidTankTooltip.add(Component.translatable("productiveslimes.tooltip.liquid.amount", display.recipe().value().fluidStack().getAmount()));

        widgets.add(Widgets.createTooltip(new Rectangle(startPoint.x + 21, startPoint.y + 12, 14, 58), fluidTankTooltip));

        widgets.add(new Widget() {
            @Override
            public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
                Minecraft.getInstance().getTextureManager().getTexture(TEXTURE);

                // Arrow
                tickCount++;
                int arrowWidth = (tickCount % 600) * 26 / 600;
                FluidTankRenderer.renderFluidStack(guiGraphics, display.recipe().value().fluidStack(), display.recipe().value().fluidStack().getAmount(), 15, 57, startPoint.x + 22, startPoint.y + 13);
                guiGraphics.blit(RenderType::guiTextured, TEXTURE, startPoint.x + 69, startPoint.y + 38, 153, 0, arrowWidth, 8, 256, 256);

                // Energy bar
                int energyScaled = (int) Math.ceil((double) display.recipe().value().energy() / 10000 * 57);
                energyScaled = arrowWidth >= 25 ? 0 : energyScaled;

                guiGraphics.blit(RenderType::guiTextured, TEXTURE, startPoint.x + 9, (startPoint.y + 18) + (52 - energyScaled), 153, 65 - energyScaled, 9, energyScaled, 256, 256);

                if (mouseX >= startPoint.x + 23 && mouseX < startPoint.x + 23 + 14 && mouseY >= startPoint.y + 13 && mouseY < startPoint.y + 13 + 58) {
                    guiGraphics.fill(startPoint.x + 22, startPoint.y + 13, startPoint.x + 23 + 14, startPoint.y + 13 + 58, 0x80FFFFFF);
                }
            }

            @Override
            public List<? extends GuiEventListener> children() {
                return new ArrayList<>();
            }
        });

        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 83;
    }
}
