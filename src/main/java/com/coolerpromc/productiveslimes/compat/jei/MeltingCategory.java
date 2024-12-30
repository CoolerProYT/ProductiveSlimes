package com.coolerpromc.productiveslimes.compat.jei;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.recipe.MeltingRecipe;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.List;

public class MeltingCategory implements IRecipeCategory<MeltingRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(ProductiveSlimes.MODID,"melting");
    public static final ResourceLocation TEXTURE = new ResourceLocation(ProductiveSlimes.MODID,"textures/gui/melting_station_gui.png");
    private int tickCount = 0;

    private final IDrawable background;
    private final IDrawable icon;

    public MeltingCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE,5,5,168,77);
        this.icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.MELTING_STATION.get()));
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("block.productiveslimes.melting_station");
    }

    @Nullable
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setIngredients(MeltingRecipe meltingRecipe, IIngredients iIngredients) {
        List<Ingredient> input = List.of(Ingredient.of(Items.BUCKET), meltingRecipe.getIngredients().get(0));
        iIngredients.setInputIngredients(input);
        iIngredients.setOutput(VanillaTypes.ITEM, meltingRecipe.getOutputs().get(0));
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, MeltingRecipe meltingRecipe, IIngredients iIngredients) {
        iRecipeLayout.getItemStacks().init(0, true,19,28);
        iRecipeLayout.getItemStacks().init(1, true,39,28);
        iRecipeLayout.getItemStacks().init(2, false,128,28);

        iRecipeLayout.getItemStacks().set(iIngredients);
    }

    @Override
    public void draw(MeltingRecipe recipe, PoseStack stack, double mouseX, double mouseY) {
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
    public List<Component> getTooltipStrings(MeltingRecipe recipe, double mouseX, double mouseY) {
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
    public Class<? extends MeltingRecipe> getRecipeClass() {
        return MeltingRecipe.class;
    }
}
