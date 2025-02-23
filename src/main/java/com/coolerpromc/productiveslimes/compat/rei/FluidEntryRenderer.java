package com.coolerpromc.productiveslimes.compat.rei;

import com.coolerpromc.productiveslimes.screen.renderer.FluidTankRenderer;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.FluidStackHooks;
import dev.architectury.platform.Platform;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.entry.renderer.BatchedEntryRenderer;
import me.shedaniel.rei.api.client.gui.widgets.Tooltip;
import me.shedaniel.rei.api.client.gui.widgets.TooltipContext;
import me.shedaniel.rei.api.common.entry.EntryStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("removal")
public class FluidEntryRenderer implements BatchedEntryRenderer<FluidStack, TextureAtlasSprite> {
    private static final String FLUID_AMOUNT = Platform.isForge() ? "tooltip.rei.fluid_amount.forge" : "tooltip.rei.fluid_amount";

    @Override
    public TextureAtlasSprite getExtraData(EntryStack<FluidStack> entry) {
        dev.architectury.fluid.FluidStack stack = entry.getValue();
        if (stack.isEmpty()) return null;
        return FluidStackHooks.getStillTexture(stack);
    }

    @Override
    public int getBatchIdentifier(EntryStack<FluidStack> entry, Rectangle bounds, TextureAtlasSprite extraData) {
        return 0;
    }

    @Override
    public void startBatch(EntryStack<FluidStack> entry, TextureAtlasSprite extraData, GuiGraphics graphics, float delta) {

    }

    @Override
    public void renderBase(EntryStack<FluidStack> entry, TextureAtlasSprite sprite, GuiGraphics graphics, MultiBufferSource.BufferSource immediate, Rectangle bounds, int mouseX, int mouseY, float delta) {
        FluidTankRenderer.renderFluidStack(graphics, new net.neoforged.neoforge.fluids.FluidStack(entry.getValue().getFluid(), ((int) entry.getValue().getAmount())), (int) entry.getValue().getAmount(), bounds.width, bounds.height, bounds.x, bounds.y);

        if (bounds.contains(mouseX, mouseY)) {
            graphics.fill(bounds.x, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height, 0x80FFFFFF);
        }
    }

    @Override
    public void afterBase(EntryStack<FluidStack> entry, TextureAtlasSprite extraData, GuiGraphics graphics, float delta) {

    }

    @Override
    public void renderOverlay(EntryStack<FluidStack> entry, TextureAtlasSprite extraData, GuiGraphics graphics, MultiBufferSource.BufferSource immediate, Rectangle bounds, int mouseX, int mouseY, float delta) {

    }

    @Override
    public void endBatch(EntryStack<FluidStack> entry, TextureAtlasSprite extraData, GuiGraphics graphics, float delta) {

    }

    @Override
    public @Nullable Tooltip getTooltip(EntryStack<FluidStack> entry, TooltipContext context) {
        if (entry.isEmpty())
            return null;
        List<Component> toolTip = Lists.newArrayList(entry.asFormattedText(context));
        long amount = entry.getValue().getAmount();
        if (amount >= 0 && entry.get(EntryStack.Settings.FLUID_AMOUNT_VISIBLE)) {
            String amountTooltip = I18n.get(FLUID_AMOUNT, entry.getValue().getAmount());
            if (amountTooltip != null) {
                toolTip.addAll(Stream.of(amountTooltip.split("\n")).map(Component::literal).collect(Collectors.toList()));
            }
        }
        if (Minecraft.getInstance().options.advancedItemTooltips) {
            ResourceLocation fluidId = BuiltInRegistries.FLUID.getKey(entry.getValue().getFluid());
            toolTip.add((Component.literal(fluidId.toString())).withStyle(ChatFormatting.DARK_GRAY));
        }
        return Tooltip.create(toolTip);
    }
}
