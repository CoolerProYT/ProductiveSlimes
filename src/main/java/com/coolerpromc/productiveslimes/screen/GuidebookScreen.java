package com.coolerpromc.productiveslimes.screen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.recipe.DnaExtractingRecipe;
import com.coolerpromc.productiveslimes.recipe.DnaSynthesizingRecipe;
import com.coolerpromc.productiveslimes.recipe.ModRecipes;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class GuidebookScreen extends AbstractContainerScreen<GuidebookMenu> {
    private static final int NAVIGATION_WIDTH = 100;
    private static final int SCROLLBAR_WIDTH = 6;
    private static final int NAV_TEXT_HEIGHT = 20;
    private static final int INFO_SECTION_HEIGHT = 150;
    private final List<String> sections = List.of("Dna Extracting", "Dna Synthesizing", "Advanced", "Tips", "Credits"); // Example sections
    private int scrollOffset = 0;
    private int selectedSection = 0;

    public static final int RECIPE_WIDTH  = 153;
    public static final int RECIPE_HEIGHT = 83;

    public static int COLUMNS = 2;

    // How many pixels to place between items horizontally and vertically
    public static final int H_SPACING = 5;
    public static final int V_SPACING = 5;

    private int contentScrollOffset = 0;

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

    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        renderNavigationPanel(pGuiGraphics, pMouseX, pMouseY);
        renderContentPanel(pGuiGraphics, pMouseX, pMouseY);
    }

    private void renderNavigationPanel(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        int navigationX = 0;
        int navigationY = 0;
        int navigationHeight = this.height;
        COLUMNS = (this.width - NAVIGATION_WIDTH) / (RECIPE_WIDTH + H_SPACING);

        pGuiGraphics.fill(navigationX, navigationY, navigationX + NAVIGATION_WIDTH + 20, navigationY + navigationHeight, 0x55555555);

        int sectionY = navigationY + 10 - scrollOffset;
        for (int i = 0; i < sections.size(); i++) {
            if (sectionY + NAV_TEXT_HEIGHT > navigationY && sectionY < navigationY + navigationHeight) {
                boolean isSelected = i == selectedSection;
                int color = isSelected ? 0xFFFFFF00 : 0xFFFFFFFF; // Highlight selected section
                pGuiGraphics.drawString(this.font, sections.get(i), navigationX + 10, sectionY, color);
            }
            sectionY += NAV_TEXT_HEIGHT;
        }
    }

    private void renderContentPanel(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        switch (selectedSection) {
            case 0:
                drawDnaExtracting(pGuiGraphics, pMouseX, pMouseY);
                break;
            case 1:
                drawDnaSynthesizing(pGuiGraphics, pMouseX, pMouseY);
                break;
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        double scroll = verticalAmount;

        int scrollSpeed = 10;

        int navX = 10;
        int navY = 10;
        int navWidth = NAVIGATION_WIDTH;
        int navHeight = this.height - 20;

        int contentX = navX + navWidth + 10;
        int contentY = navY;
        int contentWidth = this.width - contentX - 10;
        int contentHeight = navHeight;

        ServerLevel serverLevel = ProductiveSlimes.serverLevel;
        RecipeManager recipeManager = serverLevel.recipeAccess();
        Iterable<RecipeHolder<?>> recipes = recipeManager.getRecipes();
        RecipeMap recipeMap = RecipeMap.create(recipes);

        Collection<RecipeHolder<DnaExtractingRecipe>> dnaExtractingRecipes = recipeMap.byType(ModRecipes.DNA_EXTRACTING_TYPE.get());
        List<DnaExtractingRecipe> dnaExtractingRecipeList = dnaExtractingRecipes.stream().map(RecipeHolder::value).toList();

        Collection<RecipeHolder<DnaSynthesizingRecipe>> dnaSynthesizingRecipes = recipeMap.byType(ModRecipes.DNA_SYNTHESIZING_TYPE.get());
        List<DnaSynthesizingRecipe> dnaSynthesizingRecipeList = dnaSynthesizingRecipes.stream().map(RecipeHolder::value).toList();

        boolean overContent = (mouseX >= contentX && mouseX < contentX + contentWidth
                && mouseY >= contentY && mouseY < contentY + contentHeight);
        if (overContent) {
            if (selectedSection == 0) {
                int totalRecipeHeight = (int) ((Math.ceil((double) dnaExtractingRecipeList.size() / COLUMNS)) * RECIPE_HEIGHT) + RECIPE_HEIGHT + RECIPE_HEIGHT / 3 + INFO_SECTION_HEIGHT;
                int maxContentScroll = Math.max(0, totalRecipeHeight - contentHeight);

                contentScrollOffset -= scroll * scrollSpeed;
                if (contentScrollOffset < 0) contentScrollOffset = 0;
                if (contentScrollOffset > maxContentScroll) contentScrollOffset = maxContentScroll;

                return true;
            }
            else if (selectedSection == 1) {
                int totalRecipeHeight = (int) ((Math.ceil((double) dnaSynthesizingRecipeList.size() / COLUMNS)) * RECIPE_HEIGHT) + RECIPE_HEIGHT + RECIPE_HEIGHT / 3 + INFO_SECTION_HEIGHT + 100;
                int maxContentScroll = Math.max(0, totalRecipeHeight - contentHeight);

                contentScrollOffset -= scroll * scrollSpeed;
                if (contentScrollOffset < 0) contentScrollOffset = 0;
                if (contentScrollOffset > maxContentScroll) contentScrollOffset = maxContentScroll;

                return true;
            }
        }

        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }


    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (pMouseX >= 10 && pMouseX < 10 + NAVIGATION_WIDTH && pMouseY >= 10 && pMouseY < this.height - 10) {
            int sectionIndex = (int) ((pMouseY - 10 + scrollOffset) / NAV_TEXT_HEIGHT);
            if (sectionIndex >= 0 && sectionIndex < sections.size()) {
                if (sectionIndex == selectedSection) {
                    return true;
                }
                selectedSection = sectionIndex;
                contentScrollOffset = 0;
                return true;
            }
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    private void drawDnaExtracting(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY){
        int contentX = 10 + NAVIGATION_WIDTH;
        int contentY = 10;
        int contentWidth = this.width - contentX - 10;
        contentX += (contentWidth - (RECIPE_WIDTH * COLUMNS)) / 2;

        ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/gui/rei/dna_extractor_gui.png");
        ResourceLocation CRAFTING_TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/gui/guidebook/crafting_table_gui.png");

        int infoY = contentY - contentScrollOffset;

        // Render the info section
        Component title = Component.literal("DNA Extracting");
        int fontX = font.width(title);
        pGuiGraphics.drawString(font, title, (int)(contentX + (contentWidth - fontX) / 2 * 0.8f), infoY + 5, 0xFFFFFF);

        Component description = Component.literal("Slime DNA's can be extracted from slime balls using the DNA Extractor.");
        pGuiGraphics.drawWordWrap(font, description, contentX + 5, infoY + 20, contentWidth - 20, 0xAAAAAA);

        pGuiGraphics.blit(
                RenderType::guiTextured,
                CRAFTING_TEXTURE,
                (int)(contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f), infoY + 45,
                0, 0,
                RECIPE_WIDTH,
                RECIPE_HEIGHT,
                256,
                256
        );

        ServerLevel serverLevel = ProductiveSlimes.serverLevel;
        RecipeManager recipeManager = serverLevel.recipeAccess();
        Iterable<RecipeHolder<?>> recipes = recipeManager.getRecipes();
        RecipeMap recipeMap = RecipeMap.create(recipes);

        Optional<RecipeHolder<?>> extractor = recipeManager.byKey(ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "dna_extractor")));
        if (extractor.get().value() instanceof ShapedRecipe shapedRecipe){
            List<Optional<Ingredient>> ingredients = shapedRecipe.getIngredients();
            for (int i = 0; i < ingredients.size(); i++) {
                Optional<Ingredient> ingredient = ingredients.get(i);
                if (ingredient.isPresent()) {
                    ItemStack stacks = ingredient.get().getValues().get(0).value().getDefaultInstance();
                    pGuiGraphics.renderItem(stacks, (int)(contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, infoY + 46 + 16 + (i / 3) * 18);
                    if (pMouseX >= (int)(contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 && pMouseX < (int)(contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 + 16 && pMouseY >= infoY + 46 + 16 + (i / 3) * 18 && pMouseY < infoY + 46 + 16 + (i / 3) * 18 + 16) {
                        pGuiGraphics.renderTooltip(font, stacks, pMouseX, pMouseY);
                        pGuiGraphics.fill(RenderType.gui(), (int)(contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, infoY + 46 + 16 + (i / 3) * 18, (int)(contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 + 16, infoY + 46 + 16 + (i / 3) * 18 + 16, 0x80FFFFFF);
                    }
                }
            }

            ItemStack output = ModBlocks.DNA_EXTRACTOR.toStack();
            pGuiGraphics.renderItem(output, (int)(contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18, infoY + 46 + 16 + 18);
            if (pMouseX >= (int)(contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 91 + 18 && pMouseX < (int)(contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18 + 20 && pMouseY >= infoY + 42 + 16 + 18 && pMouseY < infoY + 46 + 16 + 18 + 20) {
                pGuiGraphics.renderTooltip(font, output, pMouseX, pMouseY);
                pGuiGraphics.fill(RenderType.gui(), (int)(contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 91 + 18, infoY + 42 + 16 + 18, (int)(contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18 + 20, infoY + 46 + 16 + 18 + 20, 0x80FFFFFF);
            }
        }

        // Render the Dna Extracting recipes
        Collection<RecipeHolder<DnaExtractingRecipe>> dnaExtractingRecipes = recipeMap.byType(ModRecipes.DNA_EXTRACTING_TYPE.get());
        List<DnaExtractingRecipe> dnaExtractingRecipeList = dnaExtractingRecipes.stream().map(RecipeHolder::value).toList();

        int index = 0;
        int numRecipeRows = (int) Math.ceil((double) dnaExtractingRecipeList.size() / COLUMNS);
        int totalRecipeHeight = numRecipeRows * (RECIPE_HEIGHT + V_SPACING);
        int totalContentHeight = INFO_SECTION_HEIGHT + totalRecipeHeight;

        // Render each recipe
        for (DnaExtractingRecipe recipe : dnaExtractingRecipeList) {
            int row = index / COLUMNS;
            int col = index % COLUMNS;
            int xPos = contentX + col * (RECIPE_WIDTH + H_SPACING);
            int yPos = contentY + INFO_SECTION_HEIGHT + row * (RECIPE_HEIGHT + V_SPACING) - contentScrollOffset;

            // Render recipe background (optional)
            pGuiGraphics.blit(
                    RenderType::guiTextured,
                    TEXTURE,
                    xPos, yPos,
                    0, 0,
                    RECIPE_WIDTH,
                    RECIPE_HEIGHT,
                    256,
                    256
            );

            // Render energy bar
            int energyScaled = (int) (((float) recipe.getEnergy() / (float) 10000) * 57);
            pGuiGraphics.blit(RenderType::guiTextured, TEXTURE, xPos + 9, yPos + 13 + (57 - energyScaled), 153, 8, 9, energyScaled, 256, 256);
            if (pMouseX >= xPos + 9 && pMouseX < xPos + 18 && pMouseY >= yPos + 13 && pMouseY < yPos + 70) {
                Component text = Component.translatable("gui.productiveslimes.energy_stored", recipe.getEnergy(), 10000);
                pGuiGraphics.renderTooltip(font, text, pMouseX, pMouseY);
            }

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

            if (!output.is(ModItems.SLIME_DNA)){
                pGuiGraphics.renderItem(Items.SLIME_BALL.getDefaultInstance(), outputX + 20, outputY);
                if (pMouseX >= outputX + 20 && pMouseX < outputX + 36 && pMouseY >= outputY && pMouseY < outputY + 16) {
                    pGuiGraphics.renderTooltip(font, Items.SLIME_BALL.getDefaultInstance(), pMouseX, pMouseY);
                    pGuiGraphics.fill(RenderType.gui(), outputX + 20, outputY, outputX + 36, outputY + 16, 0x80FFFFFF);
                }
            }

            pGuiGraphics.drawString(font, output.getDisplayName().getString().substring(1, output.getDisplayName().getString().length() - 1), xPos + 9, yPos + 4, 0x555555, false);

            Component outputChance = Component.translatable("gui.productiveslimes.output_chance", String.format("%.1f", recipe.getOutputChance() * 100) + "%");
            pGuiGraphics.drawString(font, outputChance, xPos + 9, yPos + 72, 0x555555, false);

            index++;
        }

        int scrollbarX = width - SCROLLBAR_WIDTH;
        int scrollbarHeight = (int) ((float) height / totalContentHeight * height);
        int scrollbarY = (int) ((float) contentScrollOffset / totalContentHeight * height);
        pGuiGraphics.fill(scrollbarX, 0, scrollbarX + SCROLLBAR_WIDTH, height, 0x55555555);
        pGuiGraphics.fill(scrollbarX, scrollbarY, scrollbarX + SCROLLBAR_WIDTH, scrollbarY + scrollbarHeight, 0x55888888);
    }

    private void drawDnaSynthesizing(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY){
        int contentX = 10 + NAVIGATION_WIDTH;
        int contentY = 10;
        int contentWidth = this.width - contentX - 10;
        contentX += (contentWidth - (RECIPE_WIDTH * COLUMNS)) / 2;

        ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/gui/rei/dna_synthesizer_gui.png");
        ResourceLocation CRAFTING_TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/gui/guidebook/crafting_table_gui.png");

        int infoY = contentY - contentScrollOffset;

        // Render the info section
        Component title = Component.literal("DNA Synthesizing");
        int fontX = font.width(title);
        pGuiGraphics.drawString(font, title, (int)(contentX + (contentWidth - fontX) / 2 * 0.8f), infoY + 5, 0xFFFFFF);

        Component description = Component.literal("Slime Spawn Eggs can be obtained from DNA synthesizing using the DNA Synthesizer.");
        pGuiGraphics.drawWordWrap(font, description, contentX + 5, infoY + 20, contentWidth - 30, 0xAAAAAA);

        pGuiGraphics.blit(
                RenderType::guiTextured,
                CRAFTING_TEXTURE,
                (int)(contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f), infoY + 45,
                0, 0,
                RECIPE_WIDTH,
                RECIPE_HEIGHT,
                256,
                256
        );

        ServerLevel serverLevel = ProductiveSlimes.serverLevel;
        RecipeManager recipeManager = serverLevel.recipeAccess();
        Iterable<RecipeHolder<?>> recipes = recipeManager.getRecipes();
        RecipeMap recipeMap = RecipeMap.create(recipes);

        Optional<RecipeHolder<?>> extractor = recipeManager.byKey(ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "dna_synthesizer")));
        if (extractor.get().value() instanceof ShapedRecipe shapedRecipe){
            List<Optional<Ingredient>> ingredients = shapedRecipe.getIngredients();
            for (int i = 0; i < ingredients.size(); i++) {
                Optional<Ingredient> ingredient = ingredients.get(i);
                if (ingredient.isPresent()) {
                    ItemStack stacks = ingredient.get().getValues().get(0).value().getDefaultInstance();
                    pGuiGraphics.renderItem(stacks, (int)(contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, infoY + 46 + 16 + (i / 3) * 18);
                    if (pMouseX >= (int)(contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 && pMouseX < (int)(contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 + 16 && pMouseY >= infoY + 46 + 16 + (i / 3) * 18 && pMouseY < infoY + 46 + 16 + (i / 3) * 18 + 16) {
                        pGuiGraphics.renderTooltip(font, stacks, pMouseX, pMouseY);
                        pGuiGraphics.fill(RenderType.gui(), (int)(contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, infoY + 46 + 16 + (i / 3) * 18, (int)(contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 + 16, infoY + 46 + 16 + (i / 3) * 18 + 16, 0x80FFFFFF);
                    }
                }
            }

            ItemStack output = ModBlocks.DNA_SYNTHESIZER.toStack();
            pGuiGraphics.renderItem(output, (int)(contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18, infoY + 46 + 16 + 18);
            if (pMouseX >= (int)(contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 91 + 18 && pMouseX < (int)(contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18 + 20 && pMouseY >= infoY + 42 + 16 + 18 && pMouseY < infoY + 46 + 16 + 18 + 20) {
                pGuiGraphics.renderTooltip(font, output, pMouseX, pMouseY);
                pGuiGraphics.fill(RenderType.gui(), (int)(contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 91 + 18, infoY + 42 + 16 + 18, (int)(contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18 + 20, infoY + 46 + 16 + 18 + 20, 0x80FFFFFF);
            }
        }

        // Render the Dna Extracting recipes
        Collection<RecipeHolder<DnaSynthesizingRecipe>> dnaSynthesizingRecipes = recipeMap.byType(ModRecipes.DNA_SYNTHESIZING_TYPE.get());
        List<DnaSynthesizingRecipe> dnaSynthesizingRecipeList = dnaSynthesizingRecipes.stream().map(RecipeHolder::value).sorted(Comparator.comparing(DnaSynthesizingRecipe::getName)).toList();

        int index = 0;
        int numRecipeRows = (int) Math.ceil((double) dnaSynthesizingRecipeList.size() / COLUMNS);
        int totalRecipeHeight = numRecipeRows * (RECIPE_HEIGHT + V_SPACING);
        int totalContentHeight = INFO_SECTION_HEIGHT + totalRecipeHeight;

        // Render each recipe
        for (DnaSynthesizingRecipe recipe : dnaSynthesizingRecipeList) {
            int row = index / COLUMNS;
            int col = index % COLUMNS;
            int xPos = contentX + col * (RECIPE_WIDTH + H_SPACING);
            int yPos = contentY + INFO_SECTION_HEIGHT + row * (RECIPE_HEIGHT + V_SPACING) - contentScrollOffset;

            // Render recipe background (optional)
            pGuiGraphics.blit(
                    RenderType::guiTextured,
                    TEXTURE,
                    xPos, yPos,
                    0, 0,
                    RECIPE_WIDTH,
                    RECIPE_HEIGHT,
                    256,
                    256
            );

            // Render energy bar
            int energyScaled = (int) (((float) recipe.getEnergy() / (float) 10000) * 57);
            pGuiGraphics.blit(RenderType::guiTextured, TEXTURE, xPos + 9, yPos + 13 + (57 - energyScaled), 153, 8, 9, energyScaled, 256, 256);
            if (pMouseX >= xPos + 9 && pMouseX < xPos + 18 && pMouseY >= yPos + 13 && pMouseY < yPos + 70) {
                Component text = Component.translatable("gui.productiveslimes.energy_stored", recipe.getEnergy(), 10000);
                pGuiGraphics.renderTooltip(font, text, pMouseX, pMouseY);
            }

            // Render recipe input
            List<Ingredient> input = recipe.getInputItems();
            int ingredientIndex = 0;
            for (Ingredient ingredient : input){
                ItemStack inputStack = new ItemStack(ingredient.getValues().get(0));
                int inputX = xPos;
                int inputY = yPos;
                int inputCount = 1;
                switch (ingredientIndex){
                    case 0:
                        inputX += 31;
                        inputY += 12;
                        break;
                    case 1:
                        inputX += 31;
                        inputY += 55;
                        break;
                    case 2:
                        inputX += 52;
                        inputY += 34;
                        inputCount = recipe.getInputCount();
                        break;
                }

                pGuiGraphics.renderItem(new ItemStack(inputStack.getItem(), inputCount), inputX, inputY);
                pGuiGraphics.renderItemDecorations(font, new ItemStack(inputStack.getItem(), inputCount), inputX, inputY);
                if (pMouseX >= inputX && pMouseX < inputX + 16 && pMouseY >= inputY && pMouseY < inputY + 16) {
                    pGuiGraphics.renderTooltip(font, inputStack, pMouseX, pMouseY);
                    pGuiGraphics.fill(RenderType.gui(), inputX, inputY, inputX + 16, inputY + 16, 0x80FFFFFF);
                }
                ingredientIndex++;
            }

            // Render egg
            ItemStack egg = Items.EGG.getDefaultInstance();
            int eggX = xPos + 82;
            int eggY = yPos + 55;
            pGuiGraphics.renderItem(egg, eggX, eggY);
            if (pMouseX >= eggX && pMouseX < eggX + 16 && pMouseY >= eggY && pMouseY < eggY + 16) {
                pGuiGraphics.renderTooltip(font, egg, pMouseX, pMouseY);
                pGuiGraphics.fill(RenderType.gui(), eggX, eggY, eggX + 16, eggY + 16, 0x80FFFFFF);
            }

            // Render recipe output
            ItemStack output = recipe.getOutput().getFirst();
            int outputX = xPos + 125;
            int outputY = yPos + 34;
            pGuiGraphics.renderItem(output, outputX, outputY);
            if (pMouseX >= outputX && pMouseX < outputX + 16 && pMouseY >= outputY && pMouseY < outputY + 16) {
                pGuiGraphics.renderTooltip(font, output, pMouseX, pMouseY);
                pGuiGraphics.fill(RenderType.gui(), outputX, outputY, outputX + 16, outputY + 16, 0x80FFFFFF);
            }

            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().translate(xPos + 9, yPos + 4, 0);
            pGuiGraphics.pose().scale(0.8f, 0.8f, 0.8f);
            pGuiGraphics.drawString(font, output.getDisplayName().getString().substring(1, output.getDisplayName().getString().length() - 1), 0, 0, 0x555555, false);
            pGuiGraphics.pose().popPose();

            index++;
        }

        int scrollbarX = width - SCROLLBAR_WIDTH;
        int scrollbarHeight = (int) ((float) height / totalContentHeight * height);
        int scrollbarY = (int) ((float) contentScrollOffset / totalContentHeight * height);
        pGuiGraphics.fill(scrollbarX, 0, scrollbarX + SCROLLBAR_WIDTH, height, 0x55555555);
        pGuiGraphics.fill(scrollbarX, scrollbarY, scrollbarX + SCROLLBAR_WIDTH, scrollbarY + scrollbarHeight, 0x55888888);
    }
}