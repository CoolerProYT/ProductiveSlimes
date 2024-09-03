package com.coolerpromc.productiveslimes.compat.jei;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.recipe.MeltingRecipe;
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

public class MeltingCategory implements IRecipeCategory<MeltingRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID,"air_cooling");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID,"textures/gui/melting_station_gui.png");
    public static final RecipeType<MeltingRecipe> MELTING_TYPE = new RecipeType<>(UID, MeltingRecipe.class);
    private int tickCount = 0;

    private final IDrawable background;
    private final IDrawable icon;

    public MeltingCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE,5,5,168,77);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.MELTING_STATION.get()));
    }

    @Override
    public RecipeType<MeltingRecipe> getRecipeType() {
        return MELTING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.productiveslimes.melting_station");
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
    public void draw(MeltingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Minecraft.getInstance().getTextureManager().bindForSetup(TEXTURE);

        tickCount++;
        int arrowWidth = (tickCount % 600) * 26 / 600;

        guiGraphics.blit(TEXTURE, 71, 33, 176, 0, arrowWidth, 8);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, MeltingRecipe meltingRecipe, IFocusGroup iFocusGroup) {
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,20,29).addItemStack(new ItemStack(Items.BUCKET, 1));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,40,29).addItemStack(new ItemStack(meltingRecipe.getIngredients().get(0).getItems()[0].getItem(), meltingRecipe.getInputCount()));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT, 129, 29).addItemStack(meltingRecipe.getOutputs().get(0));
    }
}
