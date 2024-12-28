package com.coolerpromc.productiveslimes.compat.rei.Melting;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
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

public class MeltingCategory implements DisplayCategory<MeltingRecipeDisplay> {
    public static final CategoryIdentifier<? extends MeltingRecipeDisplay> MELTING = CategoryIdentifier.of(ProductiveSlimes.MODID, "melting");
    public static final ResourceLocation TEXTURE = new ResourceLocation(ProductiveSlimes.MODID,"textures/gui/rei/melting_station_gui.png");

    private int tickCount = 0;

    @Override
    public CategoryIdentifier<? extends MeltingRecipeDisplay> getCategoryIdentifier() {
        return MELTING;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.productiveslimes.melting_station");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.MELTING_STATION.get());
    }

    @Override
    public List<Widget> setupDisplay(MeltingRecipeDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 77, bounds.getCenterY() - 41);
        List<Widget> widgets = new LinkedList<>();

        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y, 153, 83)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 25, startPoint.y + 34))
                .entries(Collections.singleton(EntryStacks.of(new ItemStack(Items.BUCKET, display.getOutputCount())))).markInput());

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 45, startPoint.y + 34))
                .entries(Collections.singleton(EntryStacks.of(display.getInputItem()))).markInput());

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 128, startPoint.y + 34))
                .entries(display.getOutputEntries().get(0)).markOutput());

        Component text = Component.translatable("tooltip.productiveslimes.energy_usage", display.getEnergy());

        widgets.add(Widgets.createTooltip(new Rectangle(startPoint.x + 8, startPoint.y + 12, 10, 58), text));

        widgets.add(new Widget() {
            @Override
            public void render(PoseStack stack, int i, int i1, float v) {
                RenderSystem.setShaderTexture(0, TEXTURE);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

                tickCount++;
                int arrowWidth = (tickCount % 600) * 26 / 600;

                GuiComponent.blit(stack, startPoint.x + 77, startPoint.y + 38, 153, 0, arrowWidth, 8, 256, 256);

                int energyScaled = (int) Math.ceil((double) display.getEnergy() / 10000 * 57);
                energyScaled = arrowWidth >= 25 ? 0 : energyScaled;

                GuiComponent.blit(stack, startPoint.x + 9, (startPoint.y + 18) + (52 - energyScaled), 153, 65 - energyScaled, 9, energyScaled, 256, 256);
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
