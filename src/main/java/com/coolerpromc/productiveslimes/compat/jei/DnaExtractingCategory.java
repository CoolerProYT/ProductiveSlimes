package com.coolerpromc.productiveslimes.compat.jei;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.recipe.DnaExtractingRecipe;
import com.coolerpromc.productiveslimes.recipe.ModRecipes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeHolderType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

public class DnaExtractingCategory extends AbstractRecipeCategory<RecipeHolder<DnaExtractingRecipe>> {
    public static final Identifier UID = Identifier.fromNamespaceAndPath(ProductiveSlimes.MODID,"dna_extracting");
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(ProductiveSlimes.MODID,"textures/gui/dna_extractor_gui.png");
    public static final IRecipeHolderType<DnaExtractingRecipe> DNA_EXTRACTING_TYPE = IRecipeHolderType.create(ModRecipes.DNA_EXTRACTING_TYPE.get());
    private int tickCount = 0;

    public DnaExtractingCategory(IGuiHelper helper) {
        super(DNA_EXTRACTING_TYPE, Component.translatable("block.productiveslimes.dna_extractor"), helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.DNA_EXTRACTOR.get())), 168, 77);
    }

    @Override
    public void draw(RecipeHolder<DnaExtractingRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, 0, 0, 5, 5, 168, 77, 256, 256);

        tickCount++;
        int arrowWidth = (tickCount % 600) * 26 / 600;

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, 72, 33, 176, 0, arrowWidth, 8, 256, 256);

        int energyScaled = (int) Math.ceil((double) recipe.value().getEnergy() / 10000 * 57);
        energyScaled = arrowWidth >= 25 ? 0 : energyScaled;

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, 4, 13 + (52 - energyScaled), 176, 65 - energyScaled, 9, energyScaled, 256, 256);

        Component text = Component.translatable("tooltip.productiveslimes.energy_usage", recipe.value().getEnergy());

        if (mouseX >= 4 && mouseX <= 13 && mouseY >= 8 && mouseY <= 65) {
            guiGraphics.setTooltipForNextFrame(Minecraft.getInstance().font, text, (int) mouseX, (int) mouseY);
        }

        Component outputChance = Component.translatable("gui.productiveslimes.output_chance", String.format("%.1f", recipe.value().getOutputChance() * 100) + "%");

        guiGraphics.drawString(Minecraft.getInstance().font, outputChance, 3, 68, 0xFFFFFF);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, RecipeHolder<DnaExtractingRecipe> DnaExtractingRecipe, IFocusGroup iFocusGroup) {
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,29,29).add(DnaExtractingRecipe.value().getInputItems().get(0));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT,110,29).add(DnaExtractingRecipe.value().getOutputs().get(0));
        if (DnaExtractingRecipe.value().getOutputs().size() > 1) {
            iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT, 130, 29).add(DnaExtractingRecipe.value().getOutputs().get(1));
        }
    }
}
