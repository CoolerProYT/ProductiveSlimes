package com.coolerpromc.productiveslimes.compat.jei;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.recipe.DnaSynthesizingRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
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
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public class DnaSynthesizingCategory implements IRecipeCategory<DnaSynthesizingRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(ProductiveSlimes.MODID,"dna_synthesizing");
    public static final ResourceLocation TEXTURE = new ResourceLocation(ProductiveSlimes.MODID,"textures/gui/dna_synthesizer_gui.png");
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
        return new TranslatableComponent("block.productiveslimes.dna_synthesizer");
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
    public void draw(DnaSynthesizingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindForSetup(TEXTURE);

        tickCount++;
        int arrowWidth = (tickCount % 600) * 26 / 600;

        GuiComponent.blit(stack, 72, 33, 176, 0, arrowWidth, 8, 256, 256);

        int energyScaled = (int) Math.ceil((double) recipe.getEnergy() / 10000 * 57);
        energyScaled = arrowWidth >= 25 ? 0 : energyScaled;

        GuiComponent.blit(stack, 4, 13 + (52 - energyScaled), 176, 65 - energyScaled, 9, energyScaled, 256, 256);
    }

    @Override
    public List<Component> getTooltipStrings(DnaSynthesizingRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
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
    public Class<? extends DnaSynthesizingRecipe> getRecipeClass() {
        return DnaSynthesizingRecipe.class;
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
