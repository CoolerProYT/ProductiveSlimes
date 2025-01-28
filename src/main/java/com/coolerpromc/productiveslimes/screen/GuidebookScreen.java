package com.coolerpromc.productiveslimes.screen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.datacomponent.ModDataComponents;
import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import com.coolerpromc.productiveslimes.handler.SlimeData;
import com.coolerpromc.productiveslimes.networking.ClientRecipeManager;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.recipe.*;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;

import java.util.List;
import java.util.Optional;

public class GuidebookScreen extends AbstractContainerScreen<GuidebookMenu> {
    private static final int NAVIGATION_WIDTH = 100;
    private static final int SCROLLBAR_WIDTH = 6;
    private static final int NAV_TEXT_HEIGHT = 20;
    private static final int INFO_SECTION_HEIGHT = 150;
    private static int SLIME_AND_SLIMEBALL_INFO_HEIGHT = 150;
    private static int SLIME_AND_SLIMEBALL_SECOND_INFO_HEIGHT = 150;
    private static int SLIME_AND_SLIMEBALL_THIRD_INFO_HEIGHT = 150;
    private final List<String> sections = List.of("Welcome", "Slime & Slimeball", "Energy Generation", "Villager", "World Gen", "Dna Extracting", "Dna Synthesizing", "Melting", "Soliding", "Squeezing");
    private int scrollOffset = 0;
    private int selectedSection = 0;

    public static final int RECIPE_WIDTH = 153;
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
        this.inventoryLabelX = 1000000;
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
            case 1:
                drawSlimeAndSlimeball(pGuiGraphics, pMouseX, pMouseY);
                break;
            case 5:
                drawDnaExtracting(pGuiGraphics, pMouseX, pMouseY);
                break;
            case 6:
                drawDnaSynthesizing(pGuiGraphics, pMouseX, pMouseY);
                break;
            case 7:
                drawMelting(pGuiGraphics, pMouseX, pMouseY);
                break;
            case 8:
                drawSoliding(pGuiGraphics, pMouseX, pMouseY);
                break;
            case 9:
                drawSqueezing(pGuiGraphics, pMouseX, pMouseY);
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

        List<DnaExtractingRecipe> dnaExtractingRecipeList = ClientRecipeManager.getDnaExtractingRecipes();
        List<DnaSynthesizingRecipe> dnaSynthesizingRecipeList = ClientRecipeManager.getDnaSynthesizingRecipes();
        List<MeltingRecipe> meltingRecipeList = ClientRecipeManager.getMeltingRecipes();
        List<SolidingRecipe> solidingRecipeList = ClientRecipeManager.getSolidingRecipes();
        List<SqueezingRecipe> squeezingRecipeList = ClientRecipeManager.getSqueezingRecipes();

        boolean overContent = (mouseX >= contentX && mouseX < contentX + contentWidth
                && mouseY >= contentY && mouseY < contentY + contentHeight);
        if (overContent) {
            if (selectedSection == 5) {
                int totalRecipeHeight = (int) ((Math.ceil((double) dnaExtractingRecipeList.size() / COLUMNS)) * RECIPE_HEIGHT) + RECIPE_HEIGHT + RECIPE_HEIGHT / 3 + INFO_SECTION_HEIGHT;
                int maxContentScroll = Math.max(0, totalRecipeHeight - contentHeight);

                contentScrollOffset -= scroll * scrollSpeed;
                if (contentScrollOffset < 0) contentScrollOffset = 0;
                if (contentScrollOffset > maxContentScroll) contentScrollOffset = maxContentScroll;

                return true;
            } else if (selectedSection == 6) {
                int totalRecipeHeight = (int) ((Math.ceil((double) dnaSynthesizingRecipeList.size() / COLUMNS)) * RECIPE_HEIGHT) + RECIPE_HEIGHT + RECIPE_HEIGHT / 3 + INFO_SECTION_HEIGHT + 100;
                int maxContentScroll = Math.max(0, totalRecipeHeight - contentHeight);

                contentScrollOffset -= scroll * scrollSpeed;
                if (contentScrollOffset < 0) contentScrollOffset = 0;
                if (contentScrollOffset > maxContentScroll) contentScrollOffset = maxContentScroll;

                return true;
            } else if (selectedSection == 7) {
                int totalRecipeHeight = (int) ((Math.ceil((double) meltingRecipeList.size() / COLUMNS)) * RECIPE_HEIGHT) + RECIPE_HEIGHT + RECIPE_HEIGHT / 3 + INFO_SECTION_HEIGHT + 100;
                int maxContentScroll = Math.max(0, totalRecipeHeight - contentHeight);

                contentScrollOffset -= scroll * scrollSpeed;
                if (contentScrollOffset < 0) contentScrollOffset = 0;
                if (contentScrollOffset > maxContentScroll) contentScrollOffset = maxContentScroll;

                return true;
            } else if (selectedSection == 8) {
                int totalRecipeHeight = (int) ((Math.ceil((double) solidingRecipeList.size() / COLUMNS)) * RECIPE_HEIGHT) + INFO_SECTION_HEIGHT + 100;
                int maxContentScroll = Math.max(0, totalRecipeHeight - contentHeight);

                contentScrollOffset -= scroll * scrollSpeed;
                if (contentScrollOffset < 0) contentScrollOffset = 0;
                if (contentScrollOffset > maxContentScroll) contentScrollOffset = maxContentScroll;

                return true;
            } else if (selectedSection == 9) {
                int totalRecipeHeight = (int) ((Math.ceil((double) squeezingRecipeList.size() / COLUMNS)) * RECIPE_HEIGHT) + INFO_SECTION_HEIGHT + 20;
                int maxContentScroll = Math.max(0, totalRecipeHeight - contentHeight);

                contentScrollOffset -= scroll * scrollSpeed;
                if (contentScrollOffset < 0) contentScrollOffset = 0;
                if (contentScrollOffset > maxContentScroll) contentScrollOffset = maxContentScroll;

                return true;
            } else if (selectedSection == 1) {
                int totalRecipeHeight = (int) ((Math.ceil((double) ModTierLists.getRegisteredTiers().size() / COLUMNS)) * RECIPE_HEIGHT) + INFO_SECTION_HEIGHT;
                int totalCooldownHeight = (int) ((Math.ceil((double) ModTierLists.getRegisteredTiers().size() / COLUMNS)) * 46) + INFO_SECTION_HEIGHT;
                int maxContentScroll = Math.max(0, totalRecipeHeight - contentHeight + SLIME_AND_SLIMEBALL_INFO_HEIGHT + SLIME_AND_SLIMEBALL_SECOND_INFO_HEIGHT + totalCooldownHeight + SLIME_AND_SLIMEBALL_THIRD_INFO_HEIGHT);

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

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        super.resize(minecraft, width, height);
        contentScrollOffset = 0;
    }

    private void drawDnaExtracting(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
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
        pGuiGraphics.drawString(font, title, (int) (contentX + (contentWidth - fontX) / 2 * 0.8f), infoY + 5, 0xFFFFFF);

        Component description = Component.literal("Slime DNA's can be extracted from slime balls using the DNA Extractor.");
        pGuiGraphics.drawWordWrap(font, description, contentX + 5, infoY + 20, contentWidth - 20, 0xAAAAAA);

        pGuiGraphics.blit(
                RenderType::guiTextured,
                CRAFTING_TEXTURE,
                (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f), infoY + 45,
                0, 0,
                RECIPE_WIDTH,
                RECIPE_HEIGHT,
                256,
                256
        );

        Optional<RecipeHolder<?>> extractor = ClientRecipeManager.getRecipe(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "dna_extractor"));
        if (extractor.get().value() instanceof ShapedRecipe shapedRecipe) {
            List<Optional<Ingredient>> ingredients = shapedRecipe.getIngredients();
            for (int i = 0; i < ingredients.size(); i++) {
                Optional<Ingredient> ingredient = ingredients.get(i);
                if (ingredient.isPresent()) {
                    ItemStack stacks = ingredient.get().getValues().get(0).value().getDefaultInstance();
                    pGuiGraphics.renderItem(stacks, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, infoY + 46 + 16 + (i / 3) * 18);
                    if (pMouseX >= (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 && pMouseX < (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 + 16 && pMouseY >= infoY + 46 + 16 + (i / 3) * 18 && pMouseY < infoY + 46 + 16 + (i / 3) * 18 + 16) {
                        pGuiGraphics.renderTooltip(font, stacks, pMouseX, pMouseY);
                        pGuiGraphics.fill(RenderType.gui(), (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, infoY + 46 + 16 + (i / 3) * 18, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 + 16, infoY + 46 + 16 + (i / 3) * 18 + 16, 0x80FFFFFF);
                    }
                }
            }

            ItemStack output = ModBlocks.DNA_EXTRACTOR.toStack();
            pGuiGraphics.renderItem(output, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18, infoY + 46 + 16 + 18);
            if (pMouseX >= (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 91 + 18 && pMouseX < (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18 + 20 && pMouseY >= infoY + 42 + 16 + 18 && pMouseY < infoY + 46 + 16 + 18 + 20) {
                pGuiGraphics.renderTooltip(font, output, pMouseX, pMouseY);
                pGuiGraphics.fill(RenderType.gui(), (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 91 + 18, infoY + 42 + 16 + 18, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18 + 20, infoY + 46 + 16 + 18 + 20, 0x80FFFFFF);
            }
        }

        // Render the Dna Extracting recipes
        List<DnaExtractingRecipe> dnaExtractingRecipeList = ClientRecipeManager.getDnaExtractingRecipes();

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

            if (!output.is(ModItems.SLIME_DNA)) {
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

    private void drawDnaSynthesizing(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
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
        pGuiGraphics.drawString(font, title, (int) (contentX + (contentWidth - fontX) / 2 * 0.8f), infoY + 5, 0xFFFFFF);

        Component description = Component.literal("Slime Spawn Eggs can be obtained from DNA synthesizing using the DNA Synthesizer.");
        pGuiGraphics.drawWordWrap(font, description, contentX + 5, infoY + 20, contentWidth - 30, 0xAAAAAA);

        pGuiGraphics.blit(
                RenderType::guiTextured,
                CRAFTING_TEXTURE,
                (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f), infoY + 45,
                0, 0,
                RECIPE_WIDTH,
                RECIPE_HEIGHT,
                256,
                256
        );// Example usage in client code

        Optional<RecipeHolder<?>> extractor = ClientRecipeManager.getRecipe(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "dna_synthesizer"));
        if (extractor.get().value() instanceof ShapedRecipe shapedRecipe) {
            List<Optional<Ingredient>> ingredients = shapedRecipe.getIngredients();
            for (int i = 0; i < ingredients.size(); i++) {
                Optional<Ingredient> ingredient = ingredients.get(i);
                if (ingredient.isPresent()) {
                    ItemStack stacks = ingredient.get().getValues().get(0).value().getDefaultInstance();
                    pGuiGraphics.renderItem(stacks, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, infoY + 46 + 16 + (i / 3) * 18);
                    if (pMouseX >= (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 && pMouseX < (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 + 16 && pMouseY >= infoY + 46 + 16 + (i / 3) * 18 && pMouseY < infoY + 46 + 16 + (i / 3) * 18 + 16) {
                        pGuiGraphics.renderTooltip(font, stacks, pMouseX, pMouseY);
                        pGuiGraphics.fill(RenderType.gui(), (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, infoY + 46 + 16 + (i / 3) * 18, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 + 16, infoY + 46 + 16 + (i / 3) * 18 + 16, 0x80FFFFFF);
                    }
                }
            }

            ItemStack output = ModBlocks.DNA_SYNTHESIZER.toStack();
            pGuiGraphics.renderItem(output, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18, infoY + 46 + 16 + 18);
            if (pMouseX >= (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 91 + 18 && pMouseX < (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18 + 20 && pMouseY >= infoY + 42 + 16 + 18 && pMouseY < infoY + 46 + 16 + 18 + 20) {
                pGuiGraphics.renderTooltip(font, output, pMouseX, pMouseY);
                pGuiGraphics.fill(RenderType.gui(), (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 91 + 18, infoY + 42 + 16 + 18, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18 + 20, infoY + 46 + 16 + 18 + 20, 0x80FFFFFF);
            }
        }

        // Render the Dna Extracting recipes
        List<DnaSynthesizingRecipe> dnaSynthesizingRecipeList = ClientRecipeManager.getDnaSynthesizingRecipes();

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
            for (Ingredient ingredient : input) {
                ItemStack inputStack = new ItemStack(ingredient.getValues().get(0));
                int inputX = xPos;
                int inputY = yPos;
                int inputCount = 1;
                switch (ingredientIndex) {
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

    private void drawMelting(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        int contentX = 10 + NAVIGATION_WIDTH;
        int contentY = 10;
        int contentWidth = this.width - contentX - 10;
        contentX += (contentWidth - (RECIPE_WIDTH * COLUMNS)) / 2;

        ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/gui/rei/melting_station_gui.png");
        ResourceLocation CRAFTING_TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/gui/guidebook/crafting_table_gui.png");

        int infoY = contentY - contentScrollOffset;

        // Render the info section
        Component title = Component.literal("Slimeball Melting");
        int fontX = font.width(title);
        pGuiGraphics.drawString(font, title, (int) (contentX + (contentWidth - fontX) / 2 * 0.8f), infoY + 5, 0xFFFFFF);

        Component description = Component.literal("Slimeball can be melted into liquid using the Melting Station.");
        pGuiGraphics.drawWordWrap(font, description, contentX + 5, infoY + 20, contentWidth - 20, 0xAAAAAA);

        pGuiGraphics.blit(
                RenderType::guiTextured,
                CRAFTING_TEXTURE,
                (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f), infoY + 45,
                0, 0,
                RECIPE_WIDTH,
                RECIPE_HEIGHT,
                256,
                256
        );


        Optional<RecipeHolder<?>> extractor = ClientRecipeManager.getRecipe(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "melting_station"));
        if (extractor.get().value() instanceof ShapedRecipe shapedRecipe) {
            List<Optional<Ingredient>> ingredients = shapedRecipe.getIngredients();
            for (int i = 0; i < ingredients.size(); i++) {
                Optional<Ingredient> ingredient = ingredients.get(i);
                if (ingredient.isPresent()) {
                    ItemStack stacks = ingredient.get().getValues().get(0).value().getDefaultInstance();
                    pGuiGraphics.renderItem(stacks, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, infoY + 46 + 16 + (i / 3) * 18);
                    if (pMouseX >= (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 && pMouseX < (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 + 16 && pMouseY >= infoY + 46 + 16 + (i / 3) * 18 && pMouseY < infoY + 46 + 16 + (i / 3) * 18 + 16) {
                        pGuiGraphics.renderTooltip(font, stacks, pMouseX, pMouseY);
                        pGuiGraphics.fill(RenderType.gui(), (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, infoY + 46 + 16 + (i / 3) * 18, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 + 16, infoY + 46 + 16 + (i / 3) * 18 + 16, 0x80FFFFFF);
                    }
                }
            }

            ItemStack output = ModBlocks.DNA_EXTRACTOR.toStack();
            pGuiGraphics.renderItem(output, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18, infoY + 46 + 16 + 18);
            if (pMouseX >= (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 91 + 18 && pMouseX < (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18 + 20 && pMouseY >= infoY + 42 + 16 + 18 && pMouseY < infoY + 46 + 16 + 18 + 20) {
                pGuiGraphics.renderTooltip(font, output, pMouseX, pMouseY);
                pGuiGraphics.fill(RenderType.gui(), (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 91 + 18, infoY + 42 + 16 + 18, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18 + 20, infoY + 46 + 16 + 18 + 20, 0x80FFFFFF);
            }
        }

        // Render the Dna Extracting recipes
        List<MeltingRecipe> dnaExtractingRecipeList = ClientRecipeManager.getMeltingRecipes();

        int index = 0;
        int numRecipeRows = (int) Math.ceil((double) dnaExtractingRecipeList.size() / COLUMNS);
        int totalRecipeHeight = numRecipeRows * (RECIPE_HEIGHT + V_SPACING);
        int totalContentHeight = INFO_SECTION_HEIGHT + totalRecipeHeight;

        // Render each recipe
        for (MeltingRecipe recipe : dnaExtractingRecipeList) {
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
            ItemStack bucketStack = new ItemStack(Items.BUCKET, recipe.getOutputs().get(0).getCount());
            int bucketX = xPos + 25;
            int bucketY = yPos + 34;
            pGuiGraphics.renderItem(bucketStack, bucketX, bucketY);
            pGuiGraphics.renderItemDecorations(font, bucketStack, bucketX, bucketY);
            if (pMouseX >= bucketX && pMouseX < bucketX + 16 && pMouseY >= bucketY && pMouseY < bucketY + 16) {
                pGuiGraphics.renderTooltip(font, bucketStack, pMouseX, pMouseY);
                pGuiGraphics.fill(RenderType.gui(), bucketX, bucketY, bucketX + 16, bucketY + 16, 0x80FFFFFF);
            }

            Ingredient input = recipe.getInputItems().get(0);
            ItemStack inputStack = new ItemStack(input.getValues().get(0), recipe.getInputCount());
            int inputX = xPos + 45;
            int inputY = yPos + 34;
            pGuiGraphics.renderItem(inputStack, inputX, inputY);
            pGuiGraphics.renderItemDecorations(font, inputStack, inputX, inputY);
            if (pMouseX >= inputX && pMouseX < inputX + 16 && pMouseY >= inputY && pMouseY < inputY + 16) {
                pGuiGraphics.renderTooltip(font, inputStack, pMouseX, pMouseY);
                pGuiGraphics.fill(RenderType.gui(), inputX, inputY, inputX + 16, inputY + 16, 0x80FFFFFF);
            }

            // Render recipe output
            ItemStack output = recipe.getOutputs().getFirst();
            int outputX = xPos + 108;
            int outputY = yPos + 34;
            pGuiGraphics.renderItem(output, outputX + 20, outputY);
            pGuiGraphics.renderItemDecorations(font, output, outputX + 20, outputY);
            if (pMouseX >= outputX + 20 && pMouseX < outputX + 36 && pMouseY >= outputY && pMouseY < outputY + 16) {
                pGuiGraphics.renderTooltip(font, output, pMouseX, pMouseY);
                pGuiGraphics.fill(RenderType.gui(), outputX + 20, outputY, outputX + 36, outputY + 16, 0x80FFFFFF);
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

    private void drawSoliding(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        int contentX = 10 + NAVIGATION_WIDTH;
        int contentY = 10;
        int contentWidth = this.width - contentX - 10;
        contentX += (contentWidth - (RECIPE_WIDTH * COLUMNS)) / 2;

        ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/gui/rei/soliding_station_gui.png");
        ResourceLocation CRAFTING_TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/gui/guidebook/crafting_table_gui.png");

        int infoY = contentY - contentScrollOffset;

        // Render the info section
        Component title = Component.literal("Soliding");
        int fontX = font.width(title);
        pGuiGraphics.drawString(font, title, (int) (contentX + (contentWidth - fontX) / 2 * 0.8f), infoY + 5, 0xFFFFFF);

        Component description = Component.literal("Molten slimes can be solidified into resources using the Soliding Station.");
        pGuiGraphics.drawWordWrap(font, description, contentX + 5, infoY + 20, contentWidth - 20, 0xAAAAAA);

        pGuiGraphics.blit(
                RenderType::guiTextured,
                CRAFTING_TEXTURE,
                (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f), infoY + 45,
                0, 0,
                RECIPE_WIDTH,
                RECIPE_HEIGHT,
                256,
                256
        );


        Optional<RecipeHolder<?>> extractor = ClientRecipeManager.getRecipe(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "soliding_station"));
        if (extractor.get().value() instanceof ShapedRecipe shapedRecipe) {
            List<Optional<Ingredient>> ingredients = shapedRecipe.getIngredients();
            for (int i = 0; i < ingredients.size(); i++) {
                Optional<Ingredient> ingredient = ingredients.get(i);
                if (ingredient.isPresent()) {
                    ItemStack stacks = ingredient.get().getValues().get(0).value().getDefaultInstance();
                    pGuiGraphics.renderItem(stacks, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, infoY + 46 + 16 + (i / 3) * 18);
                    if (pMouseX >= (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 && pMouseX < (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 + 16 && pMouseY >= infoY + 46 + 16 + (i / 3) * 18 && pMouseY < infoY + 46 + 16 + (i / 3) * 18 + 16) {
                        pGuiGraphics.renderTooltip(font, stacks, pMouseX, pMouseY);
                        pGuiGraphics.fill(RenderType.gui(), (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, infoY + 46 + 16 + (i / 3) * 18, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 + 16, infoY + 46 + 16 + (i / 3) * 18 + 16, 0x80FFFFFF);
                    }
                }
            }

            ItemStack output = ModBlocks.LIQUID_SOLIDING_STATION.toStack();
            pGuiGraphics.renderItem(output, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18, infoY + 46 + 16 + 18);
            if (pMouseX >= (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 91 + 18 && pMouseX < (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18 + 20 && pMouseY >= infoY + 42 + 16 + 18 && pMouseY < infoY + 46 + 16 + 18 + 20) {
                pGuiGraphics.renderTooltip(font, output, pMouseX, pMouseY);
                pGuiGraphics.fill(RenderType.gui(), (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 91 + 18, infoY + 42 + 16 + 18, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18 + 20, infoY + 46 + 16 + 18 + 20, 0x80FFFFFF);
            }
        }

        // Render the Dna Extracting recipes
        List<SolidingRecipe> solidingRecipeList = ClientRecipeManager.getSolidingRecipes();

        int index = 0;
        int numRecipeRows = (int) Math.ceil((double) solidingRecipeList.size() / COLUMNS);
        int totalRecipeHeight = numRecipeRows * (RECIPE_HEIGHT + V_SPACING);
        int totalContentHeight = INFO_SECTION_HEIGHT + totalRecipeHeight;

        // Render each recipe
        for (SolidingRecipe recipe : solidingRecipeList) {
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
            ItemStack inputStack = new ItemStack(input.getValues().get(0), recipe.getInputCount());
            int inputX = xPos + 26;
            int inputY = yPos + 34;
            pGuiGraphics.renderItem(inputStack, inputX, inputY);
            pGuiGraphics.renderItemDecorations(font, inputStack, inputX, inputY);
            if (pMouseX >= inputX && pMouseX < inputX + 16 && pMouseY >= inputY && pMouseY < inputY + 16) {
                pGuiGraphics.renderTooltip(font, inputStack, pMouseX, pMouseY);
                pGuiGraphics.fill(RenderType.gui(), inputX, inputY, inputX + 16, inputY + 16, 0x80FFFFFF);
            }

            // Render recipe output
            ItemStack output = recipe.getOutputs().getFirst();
            int outputX = xPos + 87;
            int outputY = yPos + 34;
            pGuiGraphics.renderItem(output, outputX + 20, outputY);
            pGuiGraphics.renderItemDecorations(font, output, outputX + 20, outputY);
            if (pMouseX >= outputX + 20 && pMouseX < outputX + 36 && pMouseY >= outputY && pMouseY < outputY + 16) {
                pGuiGraphics.renderTooltip(font, output, pMouseX, pMouseY);
                pGuiGraphics.fill(RenderType.gui(), outputX + 20, outputY, outputX + 36, outputY + 16, 0x80FFFFFF);
            }

            ItemStack output2 = recipe.getOutputs().get(1);
            int output2X = xPos + 107;
            int output2Y = yPos + 34;
            pGuiGraphics.renderItem(output2, output2X + 20, output2Y);
            pGuiGraphics.renderItemDecorations(font, output2, output2X + 20, output2Y);
            if (pMouseX >= output2X + 20 && pMouseX < output2X + 36 && pMouseY >= output2Y && pMouseY < output2Y + 16) {
                pGuiGraphics.renderTooltip(font, output2, pMouseX, pMouseY);
                pGuiGraphics.fill(RenderType.gui(), output2X + 20, output2Y, output2X + 36, output2Y + 16, 0x80FFFFFF);
            }

            pGuiGraphics.drawString(font, output.getDisplayName().getString().substring(1, output.getDisplayName().getString().length() - 1), xPos + 9, yPos + 4, 0x555555, false);

            index++;
        }

        int scrollbarX = width - SCROLLBAR_WIDTH;
        int scrollbarHeight = (int) ((float) height / totalContentHeight * height);
        int scrollbarY = (int) ((float) contentScrollOffset / totalContentHeight * height);
        pGuiGraphics.fill(scrollbarX, 0, scrollbarX + SCROLLBAR_WIDTH, height, 0x55555555);
        pGuiGraphics.fill(scrollbarX, scrollbarY, scrollbarX + SCROLLBAR_WIDTH, scrollbarY + scrollbarHeight, 0x55888888);
    }

    private void drawSqueezing(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        int contentX = 10 + NAVIGATION_WIDTH;
        int contentY = 10;
        int contentWidth = this.width - contentX - 10;
        contentX += (contentWidth - (RECIPE_WIDTH * COLUMNS)) / 2;

        ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/gui/rei/slime_squeezer_gui.png");
        ResourceLocation CRAFTING_TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/gui/guidebook/crafting_table_gui.png");

        int infoY = contentY - contentScrollOffset;

        // Render the info section
        Component title = Component.literal("Squeezing");
        int fontX = font.width(title);
        pGuiGraphics.drawString(font, title, (int) (contentX + (contentWidth - fontX) / 2 * 0.8f), infoY + 5, 0xFFFFFF);

        Component description = Component.literal("Some of the slimy blocks can be squeezed into vanilla block and slimeball fragment using the Slime Squeezer.");
        pGuiGraphics.drawWordWrap(font, description, contentX + 5, infoY + 20, contentWidth - 20, 0xAAAAAA);

        pGuiGraphics.blit(
                RenderType::guiTextured,
                CRAFTING_TEXTURE,
                contentX, infoY + 45,
                0, 0,
                RECIPE_WIDTH,
                RECIPE_HEIGHT,
                256,
                256
        );

        pGuiGraphics.blit(
                RenderType::guiTextured,
                CRAFTING_TEXTURE,
                contentX + RECIPE_WIDTH + V_SPACING, infoY + 45,
                0, 0,
                RECIPE_WIDTH,
                RECIPE_HEIGHT,
                256,
                256
        );

        Optional<RecipeHolder<?>> squeezer = ClientRecipeManager.getRecipe(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "squeezer"));
        if (squeezer.get().value() instanceof ShapedRecipe shapedRecipe) {
            List<Optional<Ingredient>> ingredients = shapedRecipe.getIngredients();
            for (int i = 0; i < ingredients.size(); i++) {
                Optional<Ingredient> ingredient = ingredients.get(i);
                if (ingredient.isPresent()) {
                    ItemStack stacks = ingredient.get().getValues().get(0).value().getDefaultInstance();
                    pGuiGraphics.renderItem(stacks, contentX + 19 + (i % 3) * 18, infoY + 46 + 16 + (i / 3) * 18);
                    if (pMouseX >= contentX + 19 + (i % 3) * 18 && pMouseX < contentX + 19 + (i % 3) * 18 + 16 && pMouseY >= infoY + 46 + 16 + (i / 3) * 18 && pMouseY < infoY + 46 + 16 + (i / 3) * 18 + 16) {
                        pGuiGraphics.renderTooltip(font, stacks, pMouseX, pMouseY);
                        pGuiGraphics.fill(RenderType.gui(), contentX + 19 + (i % 3) * 18, infoY + 46 + 16 + (i / 3) * 18, contentX + 19 + (i % 3) * 18 + 16, infoY + 46 + 16 + (i / 3) * 18 + 16, 0x80FFFFFF);
                    }
                }
            }

            ItemStack output = ModBlocks.SQUEEZER.toStack();
            pGuiGraphics.renderItem(output, contentX + 95 + 18, infoY + 46 + 16 + 18);
            if (pMouseX >= contentX + 91 + 18 && pMouseX < contentX + 95 + 18 + 20 && pMouseY >= infoY + 42 + 16 + 18 && pMouseY < infoY + 46 + 16 + 18 + 20) {
                pGuiGraphics.renderTooltip(font, output, pMouseX, pMouseY);
                pGuiGraphics.fill(RenderType.gui(), contentX + 91 + 18, infoY + 42 + 16 + 18, contentX + 95 + 18 + 20, infoY + 46 + 16 + 18 + 20, 0x80FFFFFF);
            }
        }

        Optional<RecipeHolder<?>> extractor = ClientRecipeManager.getRecipe(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "slime_squeezer"));
        if (extractor.get().value() instanceof ShapedRecipe shapedRecipe) {
            List<Optional<Ingredient>> ingredients = shapedRecipe.getIngredients();
            for (int i = 0; i < ingredients.size(); i++) {
                Optional<Ingredient> ingredient = ingredients.get(i);
                if (ingredient.isPresent()) {
                    ItemStack stacks = ingredient.get().getValues().get(0).value().getDefaultInstance();
                    pGuiGraphics.renderItem(stacks, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + (i % 3) * 18, infoY + 46 + 16 + (i / 3) * 18);
                    if (pMouseX >= (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + (i % 3) * 18 && pMouseX < (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + (i % 3) * 18 + 16 && pMouseY >= infoY + 46 + 16 + (i / 3) * 18 && pMouseY < infoY + 46 + 16 + (i / 3) * 18 + 16) {
                        pGuiGraphics.renderTooltip(font, stacks, pMouseX, pMouseY);
                        pGuiGraphics.fill(RenderType.gui(), (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + (i % 3) * 18, infoY + 46 + 16 + (i / 3) * 18, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + (i % 3) * 18 + 16, infoY + 46 + 16 + (i / 3) * 18 + 16, 0x80FFFFFF);
                    }
                }
            }

            ItemStack output = ModBlocks.SLIME_SQUEEZER.toStack();
            pGuiGraphics.renderItem(output, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 171 + 18, infoY + 46 + 16 + 18);
            if (pMouseX >= (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 167 + 18 && pMouseX < (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 171 + 18 + 20 && pMouseY >= infoY + 42 + 16 + 18 && pMouseY < infoY + 46 + 16 + 18 + 20) {
                pGuiGraphics.renderTooltip(font, output, pMouseX, pMouseY);
                pGuiGraphics.fill(RenderType.gui(), (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 167 + 18, infoY + 42 + 16 + 18, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 171 + 18 + 20, infoY + 46 + 16 + 18 + 20, 0x80FFFFFF);
            }
        }

        // Render the Dna Extracting recipes
        List<SqueezingRecipe> squeezingRecipeList = ClientRecipeManager.getSqueezingRecipes();

        int index = 0;
        int numRecipeRows = (int) Math.ceil((double) squeezingRecipeList.size() / COLUMNS);
        int totalRecipeHeight = numRecipeRows * (RECIPE_HEIGHT + V_SPACING);
        int totalContentHeight = INFO_SECTION_HEIGHT + totalRecipeHeight;

        // Render each recipe
        for (SqueezingRecipe recipe : squeezingRecipeList) {
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
            int inputX = xPos + 26;
            int inputY = yPos + 34;
            pGuiGraphics.renderItem(inputStack, inputX, inputY);
            pGuiGraphics.renderItemDecorations(font, inputStack, inputX, inputY);
            if (pMouseX >= inputX && pMouseX < inputX + 16 && pMouseY >= inputY && pMouseY < inputY + 16) {
                pGuiGraphics.renderTooltip(font, inputStack, pMouseX, pMouseY);
                pGuiGraphics.fill(RenderType.gui(), inputX, inputY, inputX + 16, inputY + 16, 0x80FFFFFF);
            }

            // Render recipe output
            ItemStack output = recipe.getOutputs().getFirst();
            int outputX = xPos + 87;
            int outputY = yPos + 34;
            pGuiGraphics.renderItem(output, outputX + 20, outputY);
            pGuiGraphics.renderItemDecorations(font, output, outputX + 20, outputY);
            if (pMouseX >= outputX + 20 && pMouseX < outputX + 36 && pMouseY >= outputY && pMouseY < outputY + 16) {
                pGuiGraphics.renderTooltip(font, output, pMouseX, pMouseY);
                pGuiGraphics.fill(RenderType.gui(), outputX + 20, outputY, outputX + 36, outputY + 16, 0x80FFFFFF);
            }

            ItemStack output2 = recipe.getOutputs().get(1);
            int output2X = xPos + 107;
            int output2Y = yPos + 34;
            pGuiGraphics.renderItem(output2, output2X + 20, output2Y);
            pGuiGraphics.renderItemDecorations(font, output2, output2X + 20, output2Y);
            if (pMouseX >= output2X + 20 && pMouseX < output2X + 36 && pMouseY >= output2Y && pMouseY < output2Y + 16) {
                pGuiGraphics.renderTooltip(font, output2, pMouseX, pMouseY);
                pGuiGraphics.fill(RenderType.gui(), output2X + 20, output2Y, output2X + 36, output2Y + 16, 0x80FFFFFF);
            }

            pGuiGraphics.drawString(font, output.getDisplayName().getString().substring(1, output.getDisplayName().getString().length() - 1), xPos + 9, yPos + 4, 0x555555, false);

            index++;
        }

        int scrollbarX = width - SCROLLBAR_WIDTH;
        int scrollbarHeight = (int) ((float) height / totalContentHeight * height);
        int scrollbarY = (int) ((float) contentScrollOffset / totalContentHeight * height);
        pGuiGraphics.fill(scrollbarX, 0, scrollbarX + SCROLLBAR_WIDTH, height, 0x55555555);
        pGuiGraphics.fill(scrollbarX, scrollbarY, scrollbarX + SCROLLBAR_WIDTH, scrollbarY + scrollbarHeight, 0x55888888);
    }

    private void drawSlimeAndSlimeball(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        int contentX = 10 + NAVIGATION_WIDTH;
        int contentY = 10;
        int contentWidth = this.width - contentX - 10;
        contentX += (contentWidth - (RECIPE_WIDTH * COLUMNS)) / 2;

        int cooldownGUIHeight = 46;

        int infoY = contentY - contentScrollOffset;
        int wordWarpLength = (int) (contentWidth * 0.85f);

        Component title = Component.literal("Slime & Slimeball");
        int fontX = font.width(title);
        pGuiGraphics.drawString(font, title, (int) (contentX + (contentWidth - fontX) / 2 * 0.8f), infoY + 5, 0xFFFFFF);

        Component description = Component.literal("In Productive Slimes, there are many different types of slimes and slimeballs. Each slime has its own Slimeball and Growth Item (Will be explained later).");
        pGuiGraphics.drawWordWrap(font, description, contentX + 5, infoY + 20, wordWarpLength, 0xAAAAAA);

        Component description2 = Component.literal("In this mod, vanilla slime will not attack any entities, Iron Golem will not attack vanilla slime, and vanilla slime will not attack Iron Golem. (This is toggleable in the config file)");
        pGuiGraphics.drawWordWrap(font, description2, contentX + 5, infoY + font.wordWrapHeight(description, wordWarpLength) + 25, (int) (contentWidth * 0.85f), 0xAAAAAA);

        Component title2 = Component.literal("Slime Growing");
        int fontX2 = font.width(title2);
        pGuiGraphics.drawString(font, title2, (int) (contentX + (contentWidth - fontX2) / 2 * 0.8f), infoY + font.wordWrapHeight(description, wordWarpLength) + font.wordWrapHeight(description2, wordWarpLength) + 30, 0xFFFFFF);

        Component description3 = Component.literal("The maximum size of a slime is 4. When the slime size is smaller than 4 and player holding their growth item, the slime will follow the player. To grow a slime, hold their growth item and sneak right click on them.");
        pGuiGraphics.drawWordWrap(font, description3, contentX + 5, infoY + font.wordWrapHeight(description, wordWarpLength) + font.wordWrapHeight(description2, wordWarpLength) + font.wordWrapHeight(title2, wordWarpLength) + 35, (int) (contentWidth * 0.85f), 0xAAAAAA);

        SLIME_AND_SLIMEBALL_INFO_HEIGHT = contentY + font.wordWrapHeight(description, wordWarpLength) + font.wordWrapHeight(description2, wordWarpLength) + font.wordWrapHeight(title2, wordWarpLength) + font.wordWrapHeight(description3, wordWarpLength) + 40;

        List<ModTiers> registeredTiers = ModTierLists.getRegisteredTiers();
        int index = 0;
        int numRecipeRows = (int) Math.ceil((double) registeredTiers.size() / COLUMNS);
        int totalRecipeHeight = numRecipeRows * (RECIPE_HEIGHT + V_SPACING);

        ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/gui/guidebook/slime_grow_gui.png");

        for (ModTiers tiers : registeredTiers) {
            int row = index / COLUMNS;
            int col = index % COLUMNS;
            int xPos = contentX + col * (RECIPE_WIDTH + H_SPACING);
            int yPos = contentY + SLIME_AND_SLIMEBALL_INFO_HEIGHT + row * (RECIPE_HEIGHT + V_SPACING) - contentScrollOffset;

            pGuiGraphics.blit(RenderType::guiTextured, TEXTURE, xPos, yPos, 0, 0, RECIPE_WIDTH, RECIPE_HEIGHT, 256, 256);

            Component tierName = Component.translatable("entity.productiveslimes." + tiers.name() + "_slime");
            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().translate(xPos + 5, yPos + 3, 0);
            pGuiGraphics.pose().scale(0.8f, 0.8f, 0.8f);
            pGuiGraphics.drawString(font, tierName, 0, 0, 0x555555, false);
            pGuiGraphics.pose().popPose();

            SlimeData slimeData = new SlimeData(
                    1,
                    tiers.color(),
                    tiers.cooldown(),
                    BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, tiers.name() + "_slimeball")).get().value().getDefaultInstance(),
                    ModTierLists.getItemByKey(tiers.growthItemKey()).asItem().getDefaultInstance(),
                    (EntityType<BaseSlime>) BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, tiers.name() + "_slime")).get().value()
            );
            ItemStack slimeItem = new ItemStack(ModItems.SLIME_ITEM.get());
            slimeItem.set(ModDataComponents.SLIME_DATA, slimeData);

            pGuiGraphics.renderItem(slimeItem, xPos + 63, yPos + 10);
            if (pMouseX >= xPos + 63 && pMouseX < xPos + 79 && pMouseY >= yPos + 10 && pMouseY < yPos + 26) {
                pGuiGraphics.renderTooltip(font, slimeItem, pMouseX, pMouseY);
                pGuiGraphics.fill(RenderType.gui(), xPos + 63, yPos + 10, xPos + 79, yPos + 26, 0x80FFFFFF);
            }
            pGuiGraphics.renderItem(slimeItem, xPos + 63, yPos + 36);
            if (pMouseX >= xPos + 63 && pMouseX < xPos + 79 && pMouseY >= yPos + 36 && pMouseY < yPos + 52) {
                pGuiGraphics.renderTooltip(font, slimeItem, pMouseX, pMouseY);
                pGuiGraphics.fill(RenderType.gui(), xPos + 63, yPos + 36, xPos + 79, yPos + 52, 0x80FFFFFF);
            }
            pGuiGraphics.renderItem(slimeItem, xPos + 63, yPos + 62);
            if (pMouseX >= xPos + 63 && pMouseX < xPos + 79 && pMouseY >= yPos + 62 && pMouseY < yPos + 78) {
                pGuiGraphics.renderTooltip(font, slimeItem, pMouseX, pMouseY);
                pGuiGraphics.fill(RenderType.gui(), xPos + 63, yPos + 62, xPos + 79, yPos + 78, 0x80FFFFFF);
            }

            ItemStack growthItem = slimeData.growthItem();
            growthItem.setCount(3);

            pGuiGraphics.renderItem(growthItem, xPos + 124, yPos + 10);
            pGuiGraphics.renderItemDecorations(font, growthItem, xPos + 124, yPos + 10);
            if (pMouseX >= xPos + 124 && pMouseX < xPos + 140 && pMouseY >= yPos + 10 && pMouseY < yPos + 26) {
                pGuiGraphics.renderTooltip(font, growthItem, pMouseX, pMouseY);
                pGuiGraphics.fill(RenderType.gui(), xPos + 124, yPos + 10, xPos + 140, yPos + 26, 0x80FFFFFF);
            }
            growthItem.setCount(4);
            pGuiGraphics.renderItem(growthItem, xPos + 124, yPos + 36);
            pGuiGraphics.renderItemDecorations(font, growthItem, xPos + 124, yPos + 36);
            if (pMouseX >= xPos + 124 && pMouseX < xPos + 140 && pMouseY >= yPos + 36 && pMouseY < yPos + 52) {
                pGuiGraphics.renderTooltip(font, growthItem, pMouseX, pMouseY);
                pGuiGraphics.fill(RenderType.gui(), xPos + 124, yPos + 36, xPos + 140, yPos + 52, 0x80FFFFFF);
            }
            growthItem.setCount(5);
            pGuiGraphics.renderItem(growthItem, xPos + 124, yPos + 62);
            pGuiGraphics.renderItemDecorations(font, growthItem, xPos + 124, yPos + 62);
            if (pMouseX >= xPos + 124 && pMouseX < xPos + 140 && pMouseY >= yPos + 62 && pMouseY < yPos + 78) {
                pGuiGraphics.renderTooltip(font, growthItem, pMouseX, pMouseY);
                pGuiGraphics.fill(RenderType.gui(), xPos + 124, yPos + 62, xPos + 140, yPos + 78, 0x80FFFFFF);
            }

            index++;
        }

        int infoY2 = contentY - contentScrollOffset + SLIME_AND_SLIMEBALL_INFO_HEIGHT + totalRecipeHeight;

        Component title3 = Component.literal("Slimeball Obtaining");
        int fontX3 = font.width(title);
        pGuiGraphics.drawString(font, title3, (int) (contentX + (contentWidth - fontX3) / 2 * 0.8f), infoY2 + 5, 0xFFFFFF);

        Component description4 = Component.literal("Slimeball can be obtained from squeezing slimy blocks into slimeball fragment or dropping from slimes. Every slime (Except vanilla slime) will drop slimeball every x Seconds (Different for every slime). Install Jade to view next drop time of the slime. The amount of slimeball drop based on their size.");
        pGuiGraphics.drawWordWrap(font, description4, contentX + 5, infoY2 + 20, wordWarpLength, 0xAAAAAA);

        Component description5 = Component.literal("Player can let slime randomly walk around in the world and use a Slimeball Collector to collect their drops or put them into Slime Simulation Chamber.");
        pGuiGraphics.drawWordWrap(font, description5, contentX + 5, infoY2 + 25 + font.wordWrapHeight(description4, wordWarpLength), wordWarpLength, 0xAAAAAA);

        Component title4 = Component.literal("Slime Cooldown Time");
        int fontX4 = font.width(title);
        pGuiGraphics.drawString(font, title4, (int) (contentX + (contentWidth - fontX4) / 2 * 0.8f), infoY2 + 25 + font.wordWrapHeight(description4, wordWarpLength) + font.wordWrapHeight(description5, wordWarpLength) + 5, 0xFFFFFF);

        SLIME_AND_SLIMEBALL_SECOND_INFO_HEIGHT = font.wordWrapHeight(description4, wordWarpLength) + font.wordWrapHeight(description5, wordWarpLength) + 45;

        int index2 = 0;
        int numCooldownRow = (int) Math.ceil((double) registeredTiers.size() / COLUMNS);
        int totalCooldownHeight = numCooldownRow * (cooldownGUIHeight + V_SPACING);

        ResourceLocation COOLDOWN_TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/gui/guidebook/slime_cooldown_gui.png");

        for (ModTiers tiers : registeredTiers) {
            int row = index2 / COLUMNS;
            int col = index2 % COLUMNS;
            int xPos = contentX + col * (RECIPE_WIDTH + H_SPACING);
            int yPos = contentY + SLIME_AND_SLIMEBALL_INFO_HEIGHT + SLIME_AND_SLIMEBALL_SECOND_INFO_HEIGHT + totalRecipeHeight + row * (cooldownGUIHeight + V_SPACING) - contentScrollOffset;

            pGuiGraphics.blit(RenderType::guiTextured, COOLDOWN_TEXTURE, xPos, yPos, 0, 0, RECIPE_WIDTH, cooldownGUIHeight, 256, 256);

            Component tierName = Component.translatable("entity.productiveslimes." + tiers.name() + "_slime");
            pGuiGraphics.drawString(font, tierName, xPos + 6, yPos + 4, 0x555555, false);

            SlimeData slimeData = new SlimeData(
                    1,
                    tiers.color(),
                    tiers.cooldown(),
                    BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, tiers.name() + "_slimeball")).get().value().getDefaultInstance(),
                    ModTierLists.getItemByKey(tiers.growthItemKey()).asItem().getDefaultInstance(),
                    (EntityType<BaseSlime>) BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, tiers.name() + "_slime")).get().value()
            );
            ItemStack slimeItem = new ItemStack(ModItems.SLIME_ITEM.get());
            slimeItem.set(ModDataComponents.SLIME_DATA, slimeData);

            pGuiGraphics.renderItem(slimeItem, xPos + 29, yPos + 15);
            if (pMouseX >= xPos + 29 && pMouseX < xPos + 45 && pMouseY >= yPos + 15 && pMouseY < yPos + 31) {
                pGuiGraphics.renderTooltip(font, slimeItem, pMouseX, pMouseY);
                pGuiGraphics.fill(RenderType.gui(), xPos + 29, yPos + 15, xPos + 45, yPos + 31, 0x80FFFFFF);
            }

            Component cooldownText = Component.literal("Cooldown: " + tiers.cooldown() / 20 + "s");
            pGuiGraphics.drawString(font, cooldownText, xPos + 55, yPos + 19, 0x555555, false);

            index2++;
        }

        int infoY3 = contentY - contentScrollOffset + SLIME_AND_SLIMEBALL_INFO_HEIGHT + totalRecipeHeight + SLIME_AND_SLIMEBALL_SECOND_INFO_HEIGHT + totalCooldownHeight;

        Component title5 = Component.literal("Slimeball Collector");
        int fontX5 = font.width(title5);
        pGuiGraphics.drawString(font, title5, (int) (contentX + (contentWidth - fontX5) / 2 * 0.8f), infoY3 + 5, 0xFFFFFF);

        Component description6 = Component.literal("Slimeball Collector is a block that can collect dropped slimeball within a certain radius. The collected slimeball will be stored in the block and can be extracted using a hopper or a pipe.");
        pGuiGraphics.drawWordWrap(font, description6, contentX + 5, infoY3 + 20, wordWarpLength, 0xAAAAAA);

        Optional<RecipeHolder<?>> slimeballCollectorHolder = ClientRecipeManager.getRecipe(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "slimeball_collector"));
        RecipeHolder<ShapedRecipe> slimeballCollector = (RecipeHolder<ShapedRecipe>) slimeballCollectorHolder.get();
        List<Optional<Ingredient>> ingredients = slimeballCollector.value().getIngredients();

        ResourceLocation CRAFTING_TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/gui/guidebook/crafting_table_gui.png");

        int textureY = infoY3 + 20 + font.wordWrapHeight(description6, wordWarpLength) + 10;

        pGuiGraphics.blit(
                RenderType::guiTextured,
                CRAFTING_TEXTURE,
                (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f), textureY,
                0, 0,
                RECIPE_WIDTH,
                RECIPE_HEIGHT,
                256,
                256
        );

        for (int i = 0; i < ingredients.size(); i++) {
            Optional<Ingredient> ingredient = ingredients.get(i);
            if (ingredient.isPresent()) {
                ItemStack stacks = ingredient.get().getValues().get(0).value().getDefaultInstance();
                pGuiGraphics.renderItem(stacks, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, textureY + 17 + (i / 3) * 18);
                if (pMouseX >= (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 && pMouseX < (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 + 16 && pMouseY >= textureY + 17 + (i / 3) * 18 && pMouseY < textureY + 17 + (i / 3) * 18 + 16) {
                    pGuiGraphics.renderTooltip(font, stacks, pMouseX, pMouseY);
                    pGuiGraphics.fill(RenderType.gui(), (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, textureY + 17 + (i / 3) * 18, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 + 16, textureY + 17 + (i / 3) * 18 + 16, 0x80FFFFFF);
                }
            }
        }

        ItemStack output = ModBlocks.SLIMEBALL_COLLECTOR.toStack();
        pGuiGraphics.renderItem(output, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18, textureY + 17 + 18);
        if (pMouseX >= (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 91 + 18 && pMouseX < (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18 + 20 && pMouseY >= textureY + 13 + 18 && pMouseY < textureY + 17 + 18 + 20) {
            pGuiGraphics.renderTooltip(font, output, pMouseX, pMouseY);
            pGuiGraphics.fill(RenderType.gui(), (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 91 + 18, textureY + 13 + 18, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18 + 20, textureY + 17 + 18 + 20, 0x80FFFFFF);
        }

        Component note = Component.literal("Note: It accept any input with chest tag.");
        pGuiGraphics.drawString(font, note, (int) (contentX + (contentWidth - font.width(note)) / 2 * 0.8f), infoY3 + 110 + 18 + 26, 0x555555, false);

        Component title6 = Component.literal("Slime Simulation Chamber & Upgrades");
        int fontX6 = font.width(title6);
        pGuiGraphics.drawString(font, title6, (int) (contentX + (contentWidth - fontX6) / 2 * 0.8f), infoY3 + 110 + 18 + 26 + font.wordWrapHeight(note, wordWarpLength) + 15, 0xFFFFFF);

        Component description7 = Component.literal("Slime Simulation Chamber is a block entity that can simulate slimeball dropping from slime. Sneak + Right Click a slime to pickup a slime and put it in to the chamber.");
        pGuiGraphics.drawWordWrap(font, description7, contentX + 5, infoY3 + 110 + 18 + 26 + font.wordWrapHeight(note, wordWarpLength) + font.wordWrapHeight(title6, wordWarpLength) + 20, wordWarpLength, 0xAAAAAA);

        Component description8 = Component.literal("There are 2 speed uprgades for the chamber, Speed Upgrade 1 and Speed Upgrade 2. Speed Upgrade 1 will increase the speed of the chamber by 1.5x and Speed Upgrade 2 will increase the speed of the chamber by 2x. They are stackable.");
        pGuiGraphics.drawWordWrap(font, description8, contentX + 5, infoY3 + 110 + 18 + 26 + font.wordWrapHeight(note, wordWarpLength) + font.wordWrapHeight(title6, wordWarpLength) + font.wordWrapHeight(description7, wordWarpLength) + 25, wordWarpLength, 0xAAAAAA);

        Optional<RecipeHolder<?>> slimeNestHolder = ClientRecipeManager.getRecipe(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "slime_nest"));
        RecipeHolder<ShapedRecipe> SlimeNest = (RecipeHolder<ShapedRecipe>) slimeNestHolder.get();
        List<Optional<Ingredient>> ingredients2 = SlimeNest.value().getIngredients();

        int textureY2 = infoY3 + 110 + 18 + 26 + font.wordWrapHeight(note, wordWarpLength) + font.wordWrapHeight(title6, wordWarpLength) + font.wordWrapHeight(description7, wordWarpLength) + font.wordWrapHeight(description8, wordWarpLength) + 30;

        pGuiGraphics.blit(
                RenderType::guiTextured,
                CRAFTING_TEXTURE,
                (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f), textureY2,
                0, 0,
                RECIPE_WIDTH,
                RECIPE_HEIGHT,
                256,
                256
        );

        for (int i = 0; i < ingredients2.size(); i++) {
            Optional<Ingredient> ingredient = ingredients2.get(i);
            if (ingredient.isPresent()) {
                ItemStack stacks = ingredient.get().getValues().get(0).value().getDefaultInstance();
                pGuiGraphics.renderItem(stacks, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, textureY2 + 17 + (i / 3) * 18);
                if (pMouseX >= (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 && pMouseX < (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 + 16 && pMouseY >= textureY2 + 17 + (i / 3) * 18 && pMouseY < textureY2 + 17 + (i / 3) * 18 + 16) {
                    pGuiGraphics.renderTooltip(font, stacks, pMouseX, pMouseY);
                    pGuiGraphics.fill(RenderType.gui(), (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, textureY2 + 17 + (i / 3) * 18, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 + 16, textureY2 + 17 + (i / 3) * 18 + 16, 0x80FFFFFF);
                }
            }
        }

        ItemStack output2 = ModBlocks.SLIME_NEST.toStack();
        pGuiGraphics.renderItem(output2, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18, textureY2 + 17 + 18);
        if (pMouseX >= (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 91 + 18 && pMouseX < (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18 + 20 && pMouseY >= textureY2 + 13 + 18 && pMouseY < textureY2 + 17 + 18 + 20) {
            pGuiGraphics.renderTooltip(font, output2, pMouseX, pMouseY);
            pGuiGraphics.fill(RenderType.gui(), (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 91 + 18, textureY2 + 13 + 18, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18 + 20, textureY2 + 17 + 18 + 20, 0x80FFFFFF);
        }

        Optional<RecipeHolder<?>> upgrade1Holder = ClientRecipeManager.getRecipe(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "slime_nest_speed_upgrade_1"));
        RecipeHolder<ShapedRecipe> upgrade1 = (RecipeHolder<ShapedRecipe>) upgrade1Holder.get();
        List<Optional<Ingredient>> ingredients3 = upgrade1.value().getIngredients();

        int textureY3 = infoY3 + 110 + 18 + 26 + font.wordWrapHeight(note, wordWarpLength) + font.wordWrapHeight(title6, wordWarpLength) + font.wordWrapHeight(description7, wordWarpLength) + font.wordWrapHeight(description8, wordWarpLength) + 30 + RECIPE_HEIGHT + 10;

        pGuiGraphics.blit(
                RenderType::guiTextured,
                CRAFTING_TEXTURE,
                (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f), textureY3,
                0, 0,
                RECIPE_WIDTH,
                RECIPE_HEIGHT,
                256,
                256
        );

        for (int i = 0; i < ingredients3.size(); i++) {
            Optional<Ingredient> ingredient = ingredients3.get(i);
            if (ingredient.isPresent()) {
                ItemStack stacks = ingredient.get().getValues().get(0).value().getDefaultInstance();
                pGuiGraphics.renderItem(stacks, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, textureY3 + 17 + (i / 3) * 18);
                if (pMouseX >= (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 && pMouseX < (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 + 16 && pMouseY >= textureY3 + 17 + (i / 3) * 18 && pMouseY < textureY3 + 17 + (i / 3) * 18 + 16) {
                    pGuiGraphics.renderTooltip(font, stacks, pMouseX, pMouseY);
                    pGuiGraphics.fill(RenderType.gui(), (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, textureY3 + 17 + (i / 3) * 18, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 + 16, textureY3 + 17 + (i / 3) * 18 + 16, 0x80FFFFFF);
                }
            }
        }

        ItemStack output3 = ModItems.SLIME_NEST_SPEED_UPGRADE_1.toStack();
        pGuiGraphics.renderItem(output3, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18, textureY3 + 17 + 18);
        if (pMouseX >= (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 91 + 18 && pMouseX < (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18 + 20 && pMouseY >= textureY3 + 13 + 18 && pMouseY < textureY3 + 17 + 18 + 20) {
            pGuiGraphics.renderTooltip(font, output3, pMouseX, pMouseY);
            pGuiGraphics.fill(RenderType.gui(), (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 91 + 18, textureY3 + 13 + 18, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18 + 20, textureY3 + 17 + 18 + 20, 0x80FFFFFF);
        }

        Optional<RecipeHolder<?>> upgrade2Holder = ClientRecipeManager.getRecipe(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "slime_nest_speed_upgrade_2"));
        RecipeHolder<ShapedRecipe> upgrade2 = (RecipeHolder<ShapedRecipe>) upgrade2Holder.get();
        List<Optional<Ingredient>> ingredients4 = upgrade2.value().getIngredients();

        int textureY4 = infoY3 + 110 + 18 + 26 + font.wordWrapHeight(note, wordWarpLength) + font.wordWrapHeight(title6, wordWarpLength) + font.wordWrapHeight(description7, wordWarpLength) + font.wordWrapHeight(description8, wordWarpLength) + 30 + RECIPE_HEIGHT + 10 + RECIPE_HEIGHT + 10;

        pGuiGraphics.blit(
                RenderType::guiTextured,
                CRAFTING_TEXTURE,
                (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f), textureY4,
                0, 0,
                RECIPE_WIDTH,
                RECIPE_HEIGHT,
                256,
                256
        );

        for (int i = 0; i < ingredients4.size(); i++) {
            Optional<Ingredient> ingredient = ingredients4.get(i);
            if (ingredient.isPresent()) {
                ItemStack stacks = ingredient.get().getValues().get(0).value().getDefaultInstance();
                pGuiGraphics.renderItem(stacks, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, textureY4 + 17 + (i / 3) * 18);
                if (pMouseX >= (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 && pMouseX < (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 + 16 && pMouseY >= textureY4 + 17 + (i / 3) * 18 && pMouseY < textureY4 + 17 + (i / 3) * 18 + 16) {
                    pGuiGraphics.renderTooltip(font, stacks, pMouseX, pMouseY);
                    pGuiGraphics.fill(RenderType.gui(), (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, textureY4 + 17 + (i / 3) * 18, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18 + 16, textureY4 + 17 + (i / 3) * 18 + 16, 0x80FFFFFF);
                }
            }
        }

        ItemStack output4 = ModItems.SLIME_NEST_SPEED_UPGRADE_2.toStack();
        pGuiGraphics.renderItem(output4, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18, textureY4 + 17 + 18);
        if (pMouseX >= (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 91 + 18 && pMouseX < (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18 + 20 && pMouseY >= textureY4 + 13 + 18 && pMouseY < textureY4 + 17 + 18 + 20) {
            pGuiGraphics.renderTooltip(font, output4, pMouseX, pMouseY);
            pGuiGraphics.fill(RenderType.gui(), (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 91 + 18, textureY4 + 13 + 18, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18 + 20, textureY4 + 17 + 18 + 20, 0x80FFFFFF);
        }

        Component title7 = Component.literal("Slimeball Fragment");
        int fontX7 = font.width(title7);
        pGuiGraphics.drawString(font, title7, (int) (contentX + (contentWidth - fontX7) / 2 * 0.8f), infoY3 + 110 + 18 + 26 + font.wordWrapHeight(note, wordWarpLength) + font.wordWrapHeight(title6, wordWarpLength) + font.wordWrapHeight(description7, wordWarpLength) + font.wordWrapHeight(description8, wordWarpLength) + 30 + RECIPE_HEIGHT + 10 + RECIPE_HEIGHT + 10 + RECIPE_HEIGHT + 5, 0xFFFFFF);

        Component description9 = Component.literal("Slimeball Fragment can be crafted into slimeball. It can be obtained by squeezing slimy blocks in a Slimeball Squeezer.");
        pGuiGraphics.drawWordWrap(font, description9, contentX + 5, infoY3 + 110 + 18 + 26 + font.wordWrapHeight(note, wordWarpLength) + font.wordWrapHeight(title6, wordWarpLength) + font.wordWrapHeight(description7, wordWarpLength) + font.wordWrapHeight(description8, wordWarpLength) + 30 + RECIPE_HEIGHT + 10 + RECIPE_HEIGHT + 10 + RECIPE_HEIGHT + 10 + 5, wordWarpLength, 0xAAAAAA);

        Optional<RecipeHolder<?>> slimeball = ClientRecipeManager.getRecipe(ResourceLocation.fromNamespaceAndPath("minecraft", "slimeball_from_fragment"));
        RecipeHolder<ShapedRecipe> slimeballFragment = (RecipeHolder<ShapedRecipe>) slimeball.get();
        List<Optional<Ingredient>> ingredients5 = slimeballFragment.value().getIngredients();

        int textureY5 = infoY3 + 110 + 18 + 26 + font.wordWrapHeight(note, wordWarpLength) + font.wordWrapHeight(title6, wordWarpLength) + font.wordWrapHeight(description7, wordWarpLength) + font.wordWrapHeight(description8, wordWarpLength) + 30 + RECIPE_HEIGHT + 10 + RECIPE_HEIGHT + 10 + RECIPE_HEIGHT + 10 + font.wordWrapHeight(description9, wordWarpLength) + 10;

        pGuiGraphics.blit(
                RenderType::guiTextured,
                CRAFTING_TEXTURE,
                (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f), textureY5,
                0, 0,
                RECIPE_WIDTH,
                RECIPE_HEIGHT,
                256,
                256
        );

        for (int i = 0, k = 0; i < ingredients5.size(); i++, k++) {
            Optional<Ingredient> ingredient = ingredients5.get(i);
            if (k == 2) k+=1;
            if (ingredient.isPresent()) {
                ItemStack stacks = ingredient.get().getValues().get(0).value().getDefaultInstance();
                pGuiGraphics.renderItem(stacks, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (k % 3) * 18, textureY5 + 17 + (k / 3) * 18);
                if (pMouseX >= (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (k % 3) * 18 && pMouseX < (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (k % 3) * 18 + 16 && pMouseY >= textureY5 + 17 + (k / 3) * 18 && pMouseY < textureY5 + 17 + (k / 3) * 18 + 16) {
                    pGuiGraphics.renderTooltip(font, stacks, pMouseX, pMouseY);
                    pGuiGraphics.fill(RenderType.gui(), (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (k % 3) * 18, textureY5 + 17 + (k / 3) * 18, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (k % 3) * 18 + 16, textureY5 + 17 + (k / 3) * 18 + 16, 0x80FFFFFF);
                }
            }
        }

        ItemStack output5 = Items.SLIME_BALL.getDefaultInstance();
        pGuiGraphics.renderItem(output5, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18, textureY5 + 17 + 18);
        if (pMouseX >= (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 91 + 18 && pMouseX < (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18 + 20 && pMouseY >= textureY5 + 13 + 18 && pMouseY < textureY5 + 17 + 18 + 20) {
            pGuiGraphics.renderTooltip(font, output5, pMouseX, pMouseY);
            pGuiGraphics.fill(RenderType.gui(), (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 91 + 18, textureY5 + 13 + 18, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18 + 20, textureY5 + 17 + 18 + 20, 0x80FFFFFF);
        }

        SLIME_AND_SLIMEBALL_THIRD_INFO_HEIGHT = 600;

        int totalContentHeight = SLIME_AND_SLIMEBALL_INFO_HEIGHT + totalRecipeHeight + SLIME_AND_SLIMEBALL_SECOND_INFO_HEIGHT + totalCooldownHeight + SLIME_AND_SLIMEBALL_THIRD_INFO_HEIGHT;

        int scrollbarX = width - SCROLLBAR_WIDTH;
        int scrollbarHeight = (int) ((float) height / totalContentHeight * height);
        int scrollbarY = (int) ((float) contentScrollOffset / totalContentHeight * height);
        pGuiGraphics.fill(scrollbarX, 0, scrollbarX + SCROLLBAR_WIDTH, height, 0x55555555);
        pGuiGraphics.fill(scrollbarX, scrollbarY, scrollbarX + SCROLLBAR_WIDTH, scrollbarY + scrollbarHeight, 0x55888888);
    }
}