package com.coolerpromc.productiveslimes.compat.jei;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.recipe.MeltingRecipe;
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

public class MeltingCategory extends AbstractRecipeCategory<RecipeHolder<MeltingRecipe>> {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID,"textures/gui/melting_station_gui.png");
    public static final IRecipeHolderType<MeltingRecipe> MELTING_TYPE = IRecipeHolderType.create(ModRecipes.MELTING_TYPE.get());
    private int tickCount = 0;

    public MeltingCategory(IGuiHelper helper) {
        super(MELTING_TYPE, Component.translatable("block.productiveslimes.melting_station"), helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.MELTING_STATION.get())), 168, 77);
    }

    @Override
    public void draw(RecipeHolder<MeltingRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
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
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, RecipeHolder<MeltingRecipe> recipe, IFocusGroup focuses) {
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,20,29).add(new ItemStack(Items.BUCKET, recipe.value().getOutputs().get(0).getCount()));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,40,29).add(new ItemStack(recipe.value().getInputItems().get(0).getValues().get(0), recipe.value().getInputCount()));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT, 129, 29).add(recipe.value().getOutputs().get(0));
    }
}
