package com.coolerpromc.productiveslimes.compat.jei;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.recipe.DnaSynthesizingRecipe;
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
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class DnaSynthesizingCategory implements IRecipeCategory<DnaSynthesizingRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID,"dna_synthesizing");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID,"textures/gui/dna_synthesizer_gui.png");
    public static final RecipeType<DnaSynthesizingRecipe> DNA_SYNTHESIZING_TYPE = new RecipeType<>(UID, DnaSynthesizingRecipe.class);
    private int tickCount = 0;

    private final IDrawable background;
    private final IDrawable icon;

    public DnaSynthesizingCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE,5,5,168,77);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.DNA_SYNTHESIZER.get()));
    }

    @Override
    public RecipeType<DnaSynthesizingRecipe> getRecipeType() {
        return DNA_SYNTHESIZING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.productiveslimes.dna_synthesizer");
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
    public void draw(DnaSynthesizingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Minecraft.getInstance().getTextureManager().bindForSetup(TEXTURE);

        tickCount++;
        int arrowWidth = (tickCount % 600) * 26 / 600;
        int dnaHeight = (tickCount % 600) * 23 / 600;

        guiGraphics.blit(TEXTURE, 72, 33, 176, 0, arrowWidth, 8);

        int energyScaled = (int) Math.ceil((double) recipe.getEnergy() / 10000 * 57);
        energyScaled = arrowWidth >= 25 ? 0 : energyScaled;

        guiGraphics.blit(TEXTURE, 4, 13 + (52 - energyScaled), 176, 65 - energyScaled, 9, energyScaled);
        guiGraphics.blit(TEXTURE, 31, 25, 176, 66, 6, dnaHeight);

        Component text = Component.literal("Energy: " + recipe.getEnergy() + " / " + 10000 + " FE");

        if (mouseX >= 4 && mouseX <= 13 && mouseY >= 8 && mouseY <= 65) {
            List<Component> tooltip = new ArrayList<>();
            tooltip.add(Component.literal("Tooltip Text Here"));
            guiGraphics.renderTooltip(Minecraft.getInstance().font, text, (int) mouseX, (int) mouseY);
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, DnaSynthesizingRecipe dnaSynthesizingRecipe, IFocusGroup iFocusGroup) {
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,26,7).addIngredients(dnaSynthesizingRecipe.getInputItems().get(0));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,26,50).addIngredients(dnaSynthesizingRecipe.getInputItems().get(1));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,47,29).addItemStack(new ItemStack(dnaSynthesizingRecipe.getInputItems().get(2).getItems()[0].getItem(), dnaSynthesizingRecipe.getInputCount()));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,77,49).addItemStack(new ItemStack(Items.EGG, 1));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT,120,29).addItemStack(dnaSynthesizingRecipe.getOutput().get(0));
    }
}
