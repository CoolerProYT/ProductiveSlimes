package com.coolerpromc.productiveslimes.compat.jei;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.recipe.DnaSynthesizingRecipe;
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
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;

public class DnaSynthesizingCategory extends AbstractRecipeCategory<RecipeHolder<DnaSynthesizingRecipe>> {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID,"textures/gui/dna_synthesizer_gui.png");
    public static final IRecipeHolderType<DnaSynthesizingRecipe> DNA_SYNTHESIZING_TYPE = IRecipeHolderType.create(ModRecipes.DNA_SYNTHESIZING_TYPE.get());
    private int tickCount = 0;

    public DnaSynthesizingCategory(IGuiHelper helper) {
        super(DNA_SYNTHESIZING_TYPE, Component.translatable("block.productiveslimes.dna_synthesizer"), helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.DNA_SYNTHESIZER.get())), 168, 77);
    }

    @Override
    public void draw(RecipeHolder<DnaSynthesizingRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, 0, 0, 5, 5, 168, 77, 256, 256);

        tickCount++;
        int arrowWidth = (tickCount % 600) * 26 / 600;
        int dnaHeight = (tickCount % 600) * 23 / 600;

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, 72, 33, 176, 0, arrowWidth, 8, 256, 256);

        int energyScaled = (int) Math.ceil((double) recipe.value().getEnergy() / 10000 * 57);
        energyScaled = arrowWidth >= 25 ? 0 : energyScaled;

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, 4, 13 + (52 - energyScaled), 176, 65 - energyScaled, 9, energyScaled, 256, 256);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, 31, 25, 176, 66, 6, dnaHeight, 256, 256);

        Component text = Component.translatable("tooltip.productiveslimes.energy_usage", recipe.value().getEnergy());

        if (mouseX >= 4 && mouseX <= 13 && mouseY >= 8 && mouseY <= 65) {
            guiGraphics.setTooltipForNextFrame(Minecraft.getInstance().font, text, (int) mouseX, (int) mouseY);
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, RecipeHolder<DnaSynthesizingRecipe> dnaSynthesizingRecipe, IFocusGroup iFocusGroup) {
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,26,7).add(dnaSynthesizingRecipe.value().getInputItems().get(0));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,26,50).add(dnaSynthesizingRecipe.value().getInputItems().get(1));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,47,29).add(new ItemStack(dnaSynthesizingRecipe.value().getInputItems().get(2).getValues().get(0).value(), dnaSynthesizingRecipe.value().getInputCount()));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,77,49).add(new ItemStack(Items.EGG, 1));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT,120,29).add(dnaSynthesizingRecipe.value().getOutput().get(0));
    }
}
