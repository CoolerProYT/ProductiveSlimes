package com.coolerpromc.productiveslimes.compat.jei;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.recipe.DnaExtractingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class DnaExtractingCategory implements IRecipeCategory<DnaExtractingRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID,"dna_extracting");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID,"textures/gui/dna_extractor_gui.png");
    public static final RecipeType<DnaExtractingRecipe> DNA_EXTRACTING_TYPE = new RecipeType<>(UID, DnaExtractingRecipe.class);
    private int tickCount = 0;

    private final IDrawable background;
    private final IDrawable icon;

    public DnaExtractingCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE,5,5,168,77);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.DNA_EXTRACTOR.get()));
    }

    @Override
    public RecipeType<DnaExtractingRecipe> getRecipeType() {
        return DNA_EXTRACTING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.productiveslimes.dna_extractor");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void draw(DnaExtractingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Minecraft.getInstance().getTextureManager().bindForSetup(TEXTURE);

        tickCount++;
        int arrowWidth = (tickCount % 600) * 26 / 600;

        guiGraphics.blit(TEXTURE, 72, 33, 176, 0, arrowWidth, 8);

        int energyScaled = (int) Math.ceil((double) recipe.getEnergy() / 10000 * 57);
        energyScaled = arrowWidth >= 25 ? 0 : energyScaled;

        guiGraphics.blit(TEXTURE, 4, 13 + (52 - energyScaled), 176, 65 - energyScaled, 9, energyScaled);

        Component text = Component.literal("Energy: " + recipe.getEnergy() + " / " + 10000 + " FE");

        if (mouseX >= 4 && mouseX <= 13 && mouseY >= 8 && mouseY <= 65) {
            List<Component> tooltip = new ArrayList<>();
            tooltip.add(Component.literal("Tooltip Text Here"));
            guiGraphics.renderTooltip(Minecraft.getInstance().font, text, (int) mouseX, (int) mouseY);
        }

        String outputChance = String.format("DNA Output Chance: %.1f%%", recipe.getOutputChance() * 100);

        guiGraphics.drawString(Minecraft.getInstance().font, outputChance, 3, 68, 0xFFFFFF);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, DnaExtractingRecipe DnaExtractingRecipe, IFocusGroup iFocusGroup) {
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,29,29).addIngredients(DnaExtractingRecipe.getIngredients().get(0));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT,110,29).addItemStack(DnaExtractingRecipe.getOutputs().get(0));
        if (DnaExtractingRecipe.getOutputs().size() > 1) {
            iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT, 130, 29).addItemStack(DnaExtractingRecipe.getOutputs().get(1));
        }
    }
}
