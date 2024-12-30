package com.coolerpromc.productiveslimes.compat.jei;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.recipe.DnaExtractingRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class DnaExtractingCategory implements IRecipeCategory<DnaExtractingRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(ProductiveSlimes.MODID,"dna_extracting");
    public static final ResourceLocation TEXTURE = new ResourceLocation(ProductiveSlimes.MODID,"textures/gui/dna_extractor_gui.png");
    private int tickCount = 0;

    private final IDrawable background;
    private final IDrawable icon;

    public DnaExtractingCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE,5,5,168,77);
        this.icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.DNA_EXTRACTOR.get()));
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("block.productiveslimes.dna_extractor");
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
    public void setIngredients(DnaExtractingRecipe dnaExtractingRecipe, IIngredients iIngredients) {
        iIngredients.setInputIngredients(dnaExtractingRecipe.getIngredients());
        iIngredients.setOutputs(VanillaTypes.ITEM, dnaExtractingRecipe.getOutputs());
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayoutBuilder, DnaExtractingRecipe dnaExtractingRecipe, IIngredients iIngredients) {
        iRecipeLayoutBuilder.getItemStacks().init(0, true, 28, 28);
        iRecipeLayoutBuilder.getItemStacks().init(1, false, 109, 28);

        if (dnaExtractingRecipe.getOutputs().size() > 1) {
            iRecipeLayoutBuilder.getItemStacks().init(2, false, 129, 28);
        }
        iRecipeLayoutBuilder.getItemStacks().set(iIngredients);
    }

    @Override
    public void draw(DnaExtractingRecipe recipe, PoseStack stack, double mouseX, double mouseY) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindForSetup(TEXTURE);

        tickCount++;
        int arrowWidth = (tickCount % 600) * 26 / 600;

        GuiComponent.blit(stack, 72, 33, 176, 0, arrowWidth, 8, 256, 256);

        int energyScaled = (int) Math.ceil((double) recipe.getEnergy() / 10000 * 57);
        energyScaled = arrowWidth >= 25 ? 0 : energyScaled;

        GuiComponent.blit(stack, 4, 13 + (52 - energyScaled), 176, 65 - energyScaled, 9, energyScaled, 256, 256);

        Component outputChance = new TranslatableComponent("gui.productiveslimes.output_chance", String.format("%.1f", recipe.getOutputChance() * 100) + "%");
        minecraft.font.draw(stack, outputChance, 3, 68, 0xFFFFFF);
    }

    @Override
    public List<Component> getTooltipStrings(DnaExtractingRecipe recipe, double mouseX, double mouseY) {
        Component text = new TranslatableComponent("tooltip.productiveslimes.energy_usage", recipe.getEnergy());

        if (mouseX >= 4 && mouseX <= 13 && mouseY >= 8 && mouseY <= 65) {
            return List.of(text);
        }

        return List.of();
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends DnaExtractingRecipe> getRecipeClass() {
        return DnaExtractingRecipe.class;
    }
}
