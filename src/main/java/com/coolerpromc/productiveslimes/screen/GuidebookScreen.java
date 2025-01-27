package com.coolerpromc.productiveslimes.screen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.recipe.DnaExtractingRecipe;
import com.coolerpromc.productiveslimes.recipe.ModRecipes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeMap;
import net.minecraft.world.level.Level;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GuidebookScreen extends AbstractContainerScreen<GuidebookMenu> {
    private static final int NAVIGATION_WIDTH = 100; // Width of the navigation panel
    private static final int SCROLLBAR_WIDTH = 6; // Width of the scrollbar
    private static final int SECTION_HEIGHT = 20; // Height of each navigation section

    private final List<String> sections = List.of("Introduction", "Basics", "Advanced", "Tips", "Credits"); // Example sections
    private int scrollOffset = 0; // Tracks how far the navigation is scrolled
    private int selectedSection = 0; // Tracks the currently selected section
    // Example values — adjust these to suit your GUI sizes.
    public static final int RECIPE_WIDTH  = 153;
    public static final int RECIPE_HEIGHT = 83;

    // Number of columns in your grid.
    public static int COLUMNS = 2;

    // How many pixels to place between items horizontally and vertically
    public static final int H_SPACING = 5;
    public static final int V_SPACING = 5;

    private int contentScrollOffset = 0; // Tracks the scroll offset

    public GuidebookScreen(GuidebookMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelX = 1000000; // Hide default labels
        this.inventoryLabelY = 1000000;
        this.titleLabelX = 10000000;

        this.imageWidth = this.width;
        this.imageHeight = this.height;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        // Render background (optional)
        pGuiGraphics.fillGradient(0, 0, this.width, this.height, 0xFFC6C6C6, 0xFF8B8B8B);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        // Render navigation panel
        renderNavigationPanel(pGuiGraphics, pMouseX, pMouseY);

        // Render content panel
        renderContentPanel(pGuiGraphics, pMouseX, pMouseY);
    }

    private void renderNavigationPanel(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        int navigationX = 10;
        int navigationY = 10;
        int navigationHeight = this.height - 20;
        COLUMNS = (this.width - NAVIGATION_WIDTH - 30) / (RECIPE_WIDTH + H_SPACING);

        // Draw navigation background
        pGuiGraphics.fill(navigationX, navigationY, navigationX + NAVIGATION_WIDTH, navigationY + navigationHeight, 0xFF555555);

        // Draw sections
        int sectionY = navigationY + 10 - scrollOffset;
        for (int i = 0; i < sections.size(); i++) {
            if (sectionY + SECTION_HEIGHT > navigationY && sectionY < navigationY + navigationHeight) {
                boolean isSelected = i == selectedSection;
                int color = isSelected ? 0xFFFFFF00 : 0xFFFFFFFF; // Highlight selected section
                pGuiGraphics.drawString(this.font, sections.get(i), navigationX + 10, sectionY, color);
            }
            sectionY += SECTION_HEIGHT;
        }

        // Draw scrollbar
        int scrollbarX = navigationX + NAVIGATION_WIDTH - SCROLLBAR_WIDTH;
        int scrollbarHeight = (int) ((float) navigationHeight / sections.size() * navigationHeight);
        int scrollbarY = navigationY + (int) ((float) scrollOffset / (sections.size() * SECTION_HEIGHT) * navigationHeight);
        pGuiGraphics.fill(scrollbarX, scrollbarY, scrollbarX + SCROLLBAR_WIDTH, scrollbarY + scrollbarHeight, 0xFF888888);
    }

    private void renderContentPanel(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        int contentX = 10 + NAVIGATION_WIDTH + 10;
        int contentY = 10;
        int contentWidth = this.width - contentX - 10;
        int contentHeight = this.height - 20;

        // Draw content background
        pGuiGraphics.fill(NAVIGATION_WIDTH + 10, 0, width , height, 0xFF333333);

        switch (selectedSection) {
            case 0:
                ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/gui/rei/dna_extractor_gui.png");

                // Get the server level and recipe manager
                ServerLevel serverLevel = menu.level;
                RecipeManager recipeManager = serverLevel.recipeAccess();
                Iterable<RecipeHolder<?>> recipes = recipeManager.getRecipes();
                RecipeMap recipeMap = RecipeMap.create(recipes);

                Collection<RecipeHolder<DnaExtractingRecipe>> dnaExtractingRecipes = recipeMap.byType(ModRecipes.DNA_EXTRACTING_TYPE.get());
                List<DnaExtractingRecipe> dnaExtractingRecipeList = dnaExtractingRecipes.stream().map(RecipeHolder::value).toList();

                int index = 0;

                // Render each recipe
                int yOffset = -contentScrollOffset; // Track vertical position for rendering
                for (DnaExtractingRecipe recipe : dnaExtractingRecipeList) {
                    int row = index / COLUMNS;
                    int col = index % COLUMNS;

                    // Calculate the top-left corner for this recipe in the grid
                    int xPos = contentX + col * (RECIPE_WIDTH  + H_SPACING);
                    int yPos = contentY + row * (RECIPE_HEIGHT + V_SPACING) - contentScrollOffset;

                    // Render recipe background (optional)
                    pGuiGraphics.blit(
                            RenderType::guiTextured,
                            TEXTURE,
                            xPos, yPos,        // Where to draw the recipe background
                            0, 0,              // UV offsets on the texture
                            RECIPE_WIDTH,
                            RECIPE_HEIGHT,
                            256,               // Texture width
                            256                // Texture height
                    );

                    // Render recipe input
                    Ingredient input = recipe.getInputItems().get(0);
                    ItemStack inputStack = new ItemStack(input.getValues().get(0));
                    int inputX = xPos + 27;
                    int inputY = yPos + 34;
                    pGuiGraphics.renderItem(inputStack, inputX, inputY);
                    if (pMouseX >= inputX && pMouseX < inputX + 16 && pMouseY >= inputY && pMouseY < inputY + 16) {
                        pGuiGraphics.renderTooltip(font, inputStack, pMouseX, pMouseY);
                        pGuiGraphics.fill(RenderType.gui(), inputX, inputY, inputX + 16, inputY + 16, 0x80FFFFFF);
                    }

                    // Render recipe output
                    ItemStack output = recipe.getOutput().getFirst();
                    int outputX = xPos + 108;
                    int outputY = yPos + 34;
                    pGuiGraphics.renderItem(output, outputX, outputY);
                    if (pMouseX >= outputX && pMouseX < outputX + 16 && pMouseY >= outputY && pMouseY < outputY + 16) {
                        pGuiGraphics.renderTooltip(font, output, pMouseX, pMouseY);
                        pGuiGraphics.fill(RenderType.gui(), outputX, outputY, outputX + 16, outputY + 16, 0x80FFFFFF);
                    }

                    // Move to the next recipe position
                    if (index % COLUMNS == COLUMNS - 1) {
                        yOffset += RECIPE_HEIGHT + V_SPACING;
                    }
                    index++;
                }

                int scrollbarWidth = 6;
                int scrollbarX = width - scrollbarWidth;
                int scrollbarHeight = (int) ((float) contentHeight / (Math.ceil((double) dnaExtractingRecipeList.size() / COLUMNS) * RECIPE_HEIGHT + RECIPE_HEIGHT / 3) * height);
                int scrollbarY = (int) ((float) contentScrollOffset / (Math.ceil((double) dnaExtractingRecipeList.size() / COLUMNS) * RECIPE_HEIGHT + RECIPE_HEIGHT / 3) * height);
                pGuiGraphics.fill(scrollbarX, scrollbarY, scrollbarX + scrollbarWidth, scrollbarY + scrollbarHeight, 0xFF888888);

                break;
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        // We'll consider 'verticalAmount' as the usual scroll wheel direction
        double scroll = verticalAmount;

        // Example scroll speed
        int scrollSpeed = 10;

        // Check if mouse is over the left navigation panel
        int navX = 10;
        int navY = 10;
        int navWidth = NAVIGATION_WIDTH;
        int navHeight = this.height - 20;

        boolean overNav = (mouseX >= navX && mouseX < navX + navWidth
                && mouseY >= navY && mouseY < navY + navHeight);
        if (overNav) {
            int totalSectionHeight = sections.size() * SECTION_HEIGHT;
            int maxNavScroll = Math.max(0, totalSectionHeight - navHeight);

            // Adjust our navigation scroll offset
            scrollOffset -= scroll * scrollSpeed;
            if (scrollOffset < 0) scrollOffset = 0;
            if (scrollOffset > maxNavScroll) scrollOffset = maxNavScroll;

            return true; // Return true to indicate we handled the scroll
        }

        // Check if mouse is over the content panel
        int contentX = navX + navWidth + 10;
        int contentY = navY;
        int contentWidth = this.width - contentX - 10;
        int contentHeight = navHeight;

        ServerLevel serverLevel = menu.level;
        RecipeManager recipeManager = serverLevel.recipeAccess();
        Iterable<RecipeHolder<?>> recipes = recipeManager.getRecipes();
        RecipeMap recipeMap = RecipeMap.create(recipes);

        Collection<RecipeHolder<DnaExtractingRecipe>> dnaExtractingRecipes = recipeMap.byType(ModRecipes.DNA_EXTRACTING_TYPE.get());
        List<DnaExtractingRecipe> dnaExtractingRecipeList = dnaExtractingRecipes.stream().map(RecipeHolder::value).toList();

        boolean overContent = (mouseX >= contentX && mouseX < contentX + contentWidth
                && mouseY >= contentY && mouseY < contentY + contentHeight);
        if (overContent) {
            // If this section has recipes (for example), we scroll them
            if (selectedSection == 0) {
                // Suppose you have a list of recipes
                int totalRecipeHeight = (int) ((Math.ceil((double) dnaExtractingRecipeList.size() / COLUMNS)) * RECIPE_HEIGHT) + RECIPE_HEIGHT + RECIPE_HEIGHT / 3;
                int maxContentScroll = Math.max(0, totalRecipeHeight - contentHeight);

                contentScrollOffset -= scroll * scrollSpeed;
                if (contentScrollOffset < 0) contentScrollOffset = 0;
                if (contentScrollOffset > maxContentScroll) contentScrollOffset = maxContentScroll;

                return true; // We handled this scroll event
            }
        }

        // If we didn't handle it, fall back to the super method
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }


    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        // Handle section selection
        if (pMouseX >= 10 && pMouseX < 10 + NAVIGATION_WIDTH && pMouseY >= 10 && pMouseY < this.height - 10) {
            int sectionIndex = (int) ((pMouseY - 10 + scrollOffset) / SECTION_HEIGHT);
            if (sectionIndex >= 0 && sectionIndex < sections.size()) {
                selectedSection = sectionIndex;
                return true;
            }
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }
}