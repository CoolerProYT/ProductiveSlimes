package com.coolerpromc.productiveslimes.screen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.datacomponent.ModDataComponents;
import com.coolerpromc.productiveslimes.handler.SlimeData;
import com.coolerpromc.productiveslimes.networking.ClientRecipeManager;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.recipe.*;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.util.GuideBookScreenHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.NotNull;

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
    private static final int WELCOME_PAGE_HEIGHT = 150;
    private static int ENERGY_GENERATION_INFO_HEIGHT = 150;
    private static int WORLD_GEN_INFO_HEIGHT = 150;
    private static final ResourceLocation CRAFTING_TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/gui/guidebook/crafting_table_gui.png");
    private final List<Component> sections = List.of(
            Component.translatable("guidebook.productiveslimes.nav.welcome"),
            Component.translatable("guidebook.productiveslimes.slime_and_slimeball"),
            Component.translatable("guidebook.productiveslimes.energy_generation"),
            Component.translatable("guidebook.productiveslimes.nav.world_gen"),
            Component.translatable("guidebook.productiveslimes.dna_extracting"),
            Component.translatable("guidebook.productiveslimes.dna_synthesizing"),
            Component.translatable("guidebook.productiveslimes.nav.melting"),
            Component.translatable("guidebook.productiveslimes.soliding"),
            Component.translatable("guidebook.productiveslimes.squeezing")
    );
    private int selectedSection = 0;

    public static final int RECIPE_WIDTH = 153;
    public static final int RECIPE_HEIGHT = 83;

    public static int COLUMNS = 2;

    public static final int H_SPACING = 5;
    public static final int V_SPACING = 5;

    private int contentScrollOffset = 0;

    private static final List<DnaExtractingRecipe> dnaExtractingRecipeList = ClientRecipeManager.getDnaExtractingRecipes();
    private static final List<DnaSynthesizingRecipe> dnaSynthesizingRecipeList = ClientRecipeManager.getDnaSynthesizingRecipes();
    private static final List<MeltingRecipe> meltingRecipeList = ClientRecipeManager.getMeltingRecipes();
    private static final List<SolidingRecipe> solidingRecipeList = ClientRecipeManager.getSolidingRecipes();
    private static final List<SqueezingRecipe> squeezingRecipeList = ClientRecipeManager.getSqueezingRecipes();

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
    protected void renderBg(@NotNull GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {

    }

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        renderNavigationPanel(pGuiGraphics);
        renderContentPanel(pGuiGraphics, pMouseX, pMouseY);
    }

    private void renderNavigationPanel(GuiGraphics pGuiGraphics) {
        int navigationX = 0;
        int navigationY = 0;
        int navigationHeight = this.height;
        COLUMNS = (this.width - NAVIGATION_WIDTH) / (RECIPE_WIDTH + H_SPACING);

        pGuiGraphics.fill(navigationX, navigationY, navigationX + NAVIGATION_WIDTH + 20, navigationY + navigationHeight, 0x55555555);

        int sectionY = navigationY + 10;
        for (int i = 0; i < sections.size(); i++) {
            if (sectionY + NAV_TEXT_HEIGHT > navigationY && sectionY < navigationY + navigationHeight) {
                boolean isSelected = i == selectedSection;
                int color = isSelected ? 0xFFFFFF00 : 0xFFFFFFFF;
                pGuiGraphics.drawString(this.font, sections.get(i), navigationX + 10, sectionY, color);
            }
            sectionY += NAV_TEXT_HEIGHT;
        }
    }

    private void renderContentPanel(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        switch (selectedSection) {
            case 0:
                drawWelcomePage(pGuiGraphics, pMouseX, pMouseY);
                break;
            case 1:
                drawSlimeAndSlimeball(pGuiGraphics, pMouseX, pMouseY);
                break;
            case 2:
                drawEnergyGeneration(pGuiGraphics, pMouseX, pMouseY);
                break;
            case 3:
                drawWorldGen(pGuiGraphics, pMouseX, pMouseY);
                break;
            case 4:
                drawDnaExtracting(pGuiGraphics, pMouseX, pMouseY);
                break;
            case 5:
                drawDnaSynthesizing(pGuiGraphics, pMouseX, pMouseY);
                break;
            case 6:
                drawMelting(pGuiGraphics, pMouseX, pMouseY);
                break;
            case 7:
                drawSoliding(pGuiGraphics, pMouseX, pMouseY);
                break;
            case 8:
                drawSqueezing(pGuiGraphics, pMouseX, pMouseY);
                break;
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        int scrollSpeed = 10;
        int navX = 10;
        int navY = 10;
        int navHeight = this.height - 20;
        int contentX = navX + NAVIGATION_WIDTH + 10;
        int contentWidth = this.width - contentX - 10;

        boolean overContent = (mouseX >= contentX && mouseX < contentX + contentWidth && mouseY >= navY && mouseY < navY + navHeight);

        if (overContent) {
            if (selectedSection == 4) {
                int totalRecipeHeight = (int) ((Math.ceil((double) dnaExtractingRecipeList.size() / COLUMNS)) * RECIPE_HEIGHT) + RECIPE_HEIGHT + RECIPE_HEIGHT / 3 + INFO_SECTION_HEIGHT;
                int maxContentScroll = Math.max(0, totalRecipeHeight - navHeight);

                contentScrollOffset = GuideBookScreenHelper.scrollOffset(contentScrollOffset, verticalAmount, maxContentScroll, scrollSpeed);

                return true;
            } else if (selectedSection == 5) {
                int totalRecipeHeight = (int) ((Math.ceil((double) dnaSynthesizingRecipeList.size() / COLUMNS)) * RECIPE_HEIGHT) + RECIPE_HEIGHT + RECIPE_HEIGHT / 3 + INFO_SECTION_HEIGHT + 100;
                int maxContentScroll = Math.max(0, totalRecipeHeight - navHeight);

                contentScrollOffset = GuideBookScreenHelper.scrollOffset(contentScrollOffset, verticalAmount, maxContentScroll, scrollSpeed);

                return true;
            } else if (selectedSection == 6) {
                int totalRecipeHeight = (int) ((Math.ceil((double) meltingRecipeList.size() / COLUMNS)) * RECIPE_HEIGHT) + RECIPE_HEIGHT + RECIPE_HEIGHT / 3 + INFO_SECTION_HEIGHT + 100;
                int maxContentScroll = Math.max(0, totalRecipeHeight - navHeight);

                contentScrollOffset = GuideBookScreenHelper.scrollOffset(contentScrollOffset, verticalAmount, maxContentScroll, scrollSpeed);

                return true;
            } else if (selectedSection == 7) {
                int totalRecipeHeight = (int) ((Math.ceil((double) solidingRecipeList.size() / COLUMNS)) * RECIPE_HEIGHT) + INFO_SECTION_HEIGHT + 100;
                int maxContentScroll = Math.max(0, totalRecipeHeight - navHeight);

                contentScrollOffset = GuideBookScreenHelper.scrollOffset(contentScrollOffset, verticalAmount, maxContentScroll, scrollSpeed);

                return true;
            } else if (selectedSection == 8) {
                int totalRecipeHeight = (int) ((Math.ceil((double) squeezingRecipeList.size() / COLUMNS)) * RECIPE_HEIGHT) + INFO_SECTION_HEIGHT + 20;
                int maxContentScroll = Math.max(0, totalRecipeHeight - navHeight);

                contentScrollOffset = GuideBookScreenHelper.scrollOffset(contentScrollOffset, verticalAmount, maxContentScroll, scrollSpeed);

                return true;
            } else if (selectedSection == 1) {
                int totalRecipeHeight = (int) ((Math.ceil((double) ModTierLists.getRegisteredTiers().size() / COLUMNS)) * RECIPE_HEIGHT) + INFO_SECTION_HEIGHT;
                int totalCooldownHeight = (int) ((Math.ceil((double) ModTierLists.getRegisteredTiers().size() / COLUMNS)) * 46) + INFO_SECTION_HEIGHT;
                int maxContentScroll = Math.max(0, totalRecipeHeight - navHeight + SLIME_AND_SLIMEBALL_INFO_HEIGHT + SLIME_AND_SLIMEBALL_SECOND_INFO_HEIGHT + totalCooldownHeight + SLIME_AND_SLIMEBALL_THIRD_INFO_HEIGHT);

                contentScrollOffset = GuideBookScreenHelper.scrollOffset(contentScrollOffset, verticalAmount, maxContentScroll, scrollSpeed);

                return true;
            }
            else if (selectedSection == 0){
                int maxContentScroll = Math.max(0, WELCOME_PAGE_HEIGHT - navHeight);

                contentScrollOffset = GuideBookScreenHelper.scrollOffset(contentScrollOffset, verticalAmount, maxContentScroll, scrollSpeed);
            }
            else if (selectedSection == 2){
                int maxContentScroll = Math.max(0, ENERGY_GENERATION_INFO_HEIGHT - navHeight);

                contentScrollOffset = GuideBookScreenHelper.scrollOffset(contentScrollOffset, verticalAmount, maxContentScroll, scrollSpeed);
            }
            else if (selectedSection == 3){
                int maxContentScroll = Math.max(0, WORLD_GEN_INFO_HEIGHT - navHeight);

                contentScrollOffset = GuideBookScreenHelper.scrollOffset(contentScrollOffset, verticalAmount, maxContentScroll, scrollSpeed);
            }
        }

        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (pMouseX >= 10 && pMouseX < 10 + NAVIGATION_WIDTH && pMouseY >= 10 && pMouseY < this.height - 10) {
            int sectionIndex = (int) ((pMouseY - 10) / NAV_TEXT_HEIGHT);
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
    public void resize(@NotNull Minecraft minecraft, int width, int height) {
        super.resize(minecraft, width, height);
        contentScrollOffset = 0;
    }

    private void drawDnaExtracting(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        int contentX = 10 + NAVIGATION_WIDTH;
        int contentY = 10;
        int contentWidth = this.width - contentX - 10;
        contentX += (contentWidth - (RECIPE_WIDTH * COLUMNS)) / 2;

        ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "textures/gui/rei/dna_extractor_gui.png");

        int infoY = contentY - contentScrollOffset;

        // Render the info section
        Component title = Component.translatable("guidebook.productiveslimes.dna_extracting");
        int fontX = font.width(title);
        pGuiGraphics.drawString(font, title, (int) (contentX + (contentWidth - fontX) / 2 * 0.8f), infoY + 5, 0xFFFFFF);

        Component description = Component.translatable("guidebook.productiveslimes.dna_extracting.description");
        pGuiGraphics.drawWordWrap(font, description, contentX + 5, infoY + 20, contentWidth - 20, 0xAAAAAA);

        pGuiGraphics.blit(RenderType::guiTextured, CRAFTING_TEXTURE, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f), infoY + 45, 0, 0, RECIPE_WIDTH, RECIPE_HEIGHT, 256, 256);

        Optional<RecipeHolder<?>> extractor = ClientRecipeManager.getRecipe(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "dna_extractor"));
        if (extractor.isPresent()){
            if (extractor.get().value() instanceof ShapedRecipe shapedRecipe) {
                List<Optional<Ingredient>> ingredients = shapedRecipe.getIngredients();
                for (int i = 0; i < ingredients.size(); i++) {
                    Optional<Ingredient> ingredient = ingredients.get(i);
                    if (ingredient.isPresent()) {
                        ItemStack stacks = ingredient.get().getValues().get(0).value().getDefaultInstance();
                        GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, infoY + 46 + 16 + (i / 3) * 18, stacks, font);
                    }
                }

                ItemStack output = ModBlocks.DNA_EXTRACTOR.toStack();
                GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18, infoY + 46 + 16 + 18, output, font);
            }
        }

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
            pGuiGraphics.blit(RenderType::guiTextured, TEXTURE, xPos, yPos, 0, 0, RECIPE_WIDTH, RECIPE_HEIGHT, 256, 256);

            // Render energy bar
            int energyScaled = (int) (((float) recipe.energy() / (float) 10000) * 57);
            pGuiGraphics.blit(RenderType::guiTextured, TEXTURE, xPos + 9, yPos + 13 + (57 - energyScaled), 153, 8, 9, energyScaled, 256, 256);
            if (pMouseX >= xPos + 9 && pMouseX < xPos + 18 && pMouseY >= yPos + 13 && pMouseY < yPos + 70) {
                Component text = Component.translatable("gui.productiveslimes.energy_stored", recipe.energy(), 10000);
                pGuiGraphics.renderTooltip(font, text, pMouseX, pMouseY);
            }

            // Render recipe input
            Ingredient input = recipe.inputItems().getFirst();
            ItemStack inputStack = new ItemStack(input.getValues().get(0));
            int inputX = xPos + 27;
            int inputY = yPos + 34;
            GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, inputX, inputY, inputStack, font);

            // Render recipe output
            ItemStack output = recipe.output().getFirst();
            int outputX = xPos + 108;
            int outputY = yPos + 34;
            GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, outputX, outputY, output, font);

            if (!output.is(ModItems.SLIME_DNA)) {
                GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, outputX + 20, outputY, Items.SLIME_BALL.getDefaultInstance(), font);
            }

            pGuiGraphics.drawString(font, output.getDisplayName().getString().substring(1, output.getDisplayName().getString().length() - 1), xPos + 9, yPos + 4, 0x555555, false);

            Component outputChance = Component.translatable("gui.productiveslimes.output_chance", String.format("%.1f", recipe.outputChance() * 100) + "%");
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

        int infoY = contentY - contentScrollOffset;

        // Render the info section
        Component title = Component.translatable("guidebook.productiveslimes.dna_synthesizing");
        int fontX = font.width(title);
        pGuiGraphics.drawString(font, title, (int) (contentX + (contentWidth - fontX) / 2 * 0.8f), infoY + 5, 0xFFFFFF);

        Component description = Component.translatable("guidebook.productiveslimes.dna_synthesizing.description");
        pGuiGraphics.drawWordWrap(font, description, contentX + 5, infoY + 20, contentWidth - 30, 0xAAAAAA);

        pGuiGraphics.blit(RenderType::guiTextured, CRAFTING_TEXTURE, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f), infoY + 45, 0, 0, RECIPE_WIDTH, RECIPE_HEIGHT, 256, 256);

        Optional<RecipeHolder<?>> extractor = ClientRecipeManager.getRecipe(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "dna_synthesizer"));
        if (extractor.isPresent()){
            if (extractor.get().value() instanceof ShapedRecipe shapedRecipe) {
                List<Optional<Ingredient>> ingredients = shapedRecipe.getIngredients();
                for (int i = 0; i < ingredients.size(); i++) {
                    Optional<Ingredient> ingredient = ingredients.get(i);
                    if (ingredient.isPresent()) {
                        ItemStack stacks = ingredient.get().getValues().get(0).value().getDefaultInstance();
                        GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, infoY + 46 + 16 + (i / 3) * 18, stacks, font);
                    }
                }

                GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18, infoY + 46 + 16 + 18, ModBlocks.DNA_SYNTHESIZER.toStack(), font);
            }
        }

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

            pGuiGraphics.blit(RenderType::guiTextured, TEXTURE, xPos, yPos, 0, 0, RECIPE_WIDTH, RECIPE_HEIGHT, 256, 256);

            // Render energy bar
            int energyScaled = (int) (((float) recipe.energy() / (float) 10000) * 57);
            pGuiGraphics.blit(RenderType::guiTextured, TEXTURE, xPos + 9, yPos + 13 + (57 - energyScaled), 153, 8, 9, energyScaled, 256, 256);
            if (pMouseX >= xPos + 9 && pMouseX < xPos + 18 && pMouseY >= yPos + 13 && pMouseY < yPos + 70) {
                Component text = Component.translatable("gui.productiveslimes.energy_stored", recipe.energy(), 10000);
                pGuiGraphics.renderTooltip(font, text, pMouseX, pMouseY);
            }

            // Render recipe input
            List<Ingredient> input = recipe.inputItems();
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
                        inputCount = recipe.inputCount();
                        break;
                }

                GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, inputX, inputY, new ItemStack(inputStack.getItem(), inputCount), font);

                ingredientIndex++;
            }

            GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, xPos + 82, yPos + 55, Items.EGG.getDefaultInstance(), font);
            GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, xPos + 125, yPos + 34, recipe.output().getFirst(), font);

            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().translate(xPos + 9, yPos + 4, 0);
            pGuiGraphics.pose().scale(0.8f, 0.8f, 0.8f);
            pGuiGraphics.drawString(font, recipe.output().getFirst().getDisplayName().getString().substring(1, recipe.output().getFirst().getDisplayName().getString().length() - 1), 0, 0, 0x555555, false);
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

        int infoY = contentY - contentScrollOffset;

        // Render the info section
        Component title = Component.translatable("guidebook.productiveslimes.slimeball_melting");
        int fontX = font.width(title);
        pGuiGraphics.drawString(font, title, (int) (contentX + (contentWidth - fontX) / 2 * 0.8f), infoY + 5, 0xFFFFFF);

        Component description = Component.translatable("guidebook.productiveslimes.slimeball_melting.description");
        pGuiGraphics.drawWordWrap(font, description, contentX + 5, infoY + 20, contentWidth - 20, 0xAAAAAA);

        pGuiGraphics.blit(RenderType::guiTextured, CRAFTING_TEXTURE, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f), infoY + 45, 0, 0, RECIPE_WIDTH, RECIPE_HEIGHT, 256, 256);

        Optional<RecipeHolder<?>> extractor = ClientRecipeManager.getRecipe(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "melting_station"));
        if (extractor.isPresent()){
            if (extractor.get().value() instanceof ShapedRecipe shapedRecipe) {
                List<Optional<Ingredient>> ingredients = shapedRecipe.getIngredients();
                for (int i = 0; i < ingredients.size(); i++) {
                    Optional<Ingredient> ingredient = ingredients.get(i);
                    if (ingredient.isPresent()) {
                        ItemStack stacks = ingredient.get().getValues().get(0).value().getDefaultInstance();
                        GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, infoY + 46 + 16 + (i / 3) * 18, stacks, font);
                    }
                }

                GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18, infoY + 46 + 16 + 18, ModBlocks.DNA_EXTRACTOR.toStack(), font);
            }
        }

        int index = 0;
        int numRecipeRows = (int) Math.ceil((double) meltingRecipeList.size() / COLUMNS);
        int totalRecipeHeight = numRecipeRows * (RECIPE_HEIGHT + V_SPACING);
        int totalContentHeight = INFO_SECTION_HEIGHT + totalRecipeHeight;

        // Render each recipe
        for (MeltingRecipe recipe : meltingRecipeList) {
            int row = index / COLUMNS;
            int col = index % COLUMNS;
            int xPos = contentX + col * (RECIPE_WIDTH + H_SPACING);
            int yPos = contentY + INFO_SECTION_HEIGHT + row * (RECIPE_HEIGHT + V_SPACING) - contentScrollOffset;

            // Render recipe background (optional)
            pGuiGraphics.blit(RenderType::guiTextured, TEXTURE, xPos, yPos, 0, 0, RECIPE_WIDTH, RECIPE_HEIGHT, 256, 256);

            // Render energy bar
            int energyScaled = (int) (((float) recipe.energy() / (float) 10000) * 57);
            pGuiGraphics.blit(RenderType::guiTextured, TEXTURE, xPos + 9, yPos + 13 + (57 - energyScaled), 153, 8, 9, energyScaled, 256, 256);
            if (pMouseX >= xPos + 9 && pMouseX < xPos + 18 && pMouseY >= yPos + 13 && pMouseY < yPos + 70) {
                Component text = Component.translatable("gui.productiveslimes.energy_stored", recipe.energy(), 10000);
                pGuiGraphics.renderTooltip(font, text, pMouseX, pMouseY);
            }

            GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, xPos + 25, yPos + 34, new ItemStack(Items.BUCKET, recipe.output().getFirst().getCount()), font);
            GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, xPos + 45, yPos + 34, new ItemStack(recipe.inputItems().getFirst().getValues().get(0), recipe.inputCount()), font);
            GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, xPos + 108 + 20, yPos + 34, recipe.output().getFirst(), font);

            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().translate(xPos + 9, yPos + 4, 0);
            pGuiGraphics.pose().scale(0.8f, 0.8f, 0.8f);
            pGuiGraphics.drawString(font, recipe.output().getFirst().getDisplayName().getString().substring(1, recipe.output().getFirst().getDisplayName().getString().length() - 1), 0, 0, 0x555555, false);
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

        int infoY = contentY - contentScrollOffset;

        // Render the info section
        Component title = Component.translatable("guidebook.productiveslimes.soliding");
        int fontX = font.width(title);
        pGuiGraphics.drawString(font, title, (int) (contentX + (contentWidth - fontX) / 2 * 0.8f), infoY + 5, 0xFFFFFF);

        Component description = Component.translatable("guidebook.productiveslimes.soliding.description");
        pGuiGraphics.drawWordWrap(font, description, contentX + 5, infoY + 20, contentWidth - 20, 0xAAAAAA);

        pGuiGraphics.blit(RenderType::guiTextured, CRAFTING_TEXTURE, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f), infoY + 45, 0, 0, RECIPE_WIDTH, RECIPE_HEIGHT, 256, 256);

        Optional<RecipeHolder<?>> extractor = ClientRecipeManager.getRecipe(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "soliding_station"));
        if(extractor.isPresent()){
            if (extractor.get().value() instanceof ShapedRecipe shapedRecipe) {
                List<Optional<Ingredient>> ingredients = shapedRecipe.getIngredients();
                for (int i = 0; i < ingredients.size(); i++) {
                    Optional<Ingredient> ingredient = ingredients.get(i);
                    if (ingredient.isPresent()) {
                        GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, infoY + 46 + 16 + (i / 3) * 18, ingredient.get().getValues().get(0).value().getDefaultInstance(), font);
                    }
                }

                ItemStack output = ModBlocks.LIQUID_SOLIDING_STATION.toStack();
                GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18, infoY + 46 + 16 + 18, output, font);
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
            pGuiGraphics.blit(RenderType::guiTextured, TEXTURE, xPos, yPos, 0, 0, RECIPE_WIDTH, RECIPE_HEIGHT, 256, 256);

            // Render energy bar
            int energyScaled = (int) (((float) recipe.energy() / (float) 10000) * 57);
            pGuiGraphics.blit(RenderType::guiTextured, TEXTURE, xPos + 9, yPos + 13 + (57 - energyScaled), 153, 8, 9, energyScaled, 256, 256);
            if (pMouseX >= xPos + 9 && pMouseX < xPos + 18 && pMouseY >= yPos + 13 && pMouseY < yPos + 70) {
                Component text = Component.translatable("gui.productiveslimes.energy_stored", recipe.energy(), 10000);
                pGuiGraphics.renderTooltip(font, text, pMouseX, pMouseY);
            }

            GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, xPos + 26, yPos + 34, new ItemStack( recipe.inputItems().getFirst().getValues().get(0), recipe.inputCount()), font);
            GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, xPos + 87 + 20, yPos + 34, recipe.output().getFirst(), font);
            GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, xPos + 107 + 20, yPos + 34, recipe.output().get(1), font);

            pGuiGraphics.drawString(font, recipe.output().getFirst().getDisplayName().getString().substring(1, recipe.output().getFirst().getDisplayName().getString().length() - 1), xPos + 9, yPos + 4, 0x555555, false);

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

        int infoY = contentY - contentScrollOffset;

        // Render the info section
        Component title = Component.translatable("guidebook.productiveslimes.squeezing");
        int fontX = font.width(title);
        pGuiGraphics.drawString(font, title, (int) (contentX + (contentWidth - fontX) / 2 * 0.8f), infoY + 5, 0xFFFFFF);

        Component description = Component.translatable("guidebook.productiveslimes.squeezing.description");
        pGuiGraphics.drawWordWrap(font, description, contentX + 5, infoY + 20, contentWidth - 20, 0xAAAAAA);

        pGuiGraphics.blit(RenderType::guiTextured, CRAFTING_TEXTURE, contentX, infoY + 45, 0, 0, RECIPE_WIDTH, RECIPE_HEIGHT, 256, 256);
        pGuiGraphics.blit(RenderType::guiTextured, CRAFTING_TEXTURE, contentX + RECIPE_WIDTH + V_SPACING, infoY + 45, 0, 0, RECIPE_WIDTH, RECIPE_HEIGHT, 256, 256);

        Optional<RecipeHolder<?>> squeezer = ClientRecipeManager.getRecipe(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "squeezer"));
        if (squeezer.isPresent()){
            if (squeezer.get().value() instanceof ShapedRecipe shapedRecipe) {
                List<Optional<Ingredient>> ingredients = shapedRecipe.getIngredients();
                for (int i = 0; i < ingredients.size(); i++) {
                    Optional<Ingredient> ingredient = ingredients.get(i);
                    if (ingredient.isPresent()) {
                        GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, contentX + 19 + (i % 3) * 18, infoY + 46 + 16 + (i / 3) * 18, ingredient.get().getValues().get(0).value().getDefaultInstance(), font);
                    }
                }

                GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, contentX + 95 + 18, infoY + 46 + 16 + 18, ModBlocks.SQUEEZER.toStack(), font);
            }
        }

        Optional<RecipeHolder<?>> extractor = ClientRecipeManager.getRecipe(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "slime_squeezer"));
        if (extractor.isPresent()){
            if (extractor.get().value() instanceof ShapedRecipe shapedRecipe) {
                List<Optional<Ingredient>> ingredients = shapedRecipe.getIngredients();
                for (int i = 0; i < ingredients.size(); i++) {
                    Optional<Ingredient> ingredient = ingredients.get(i);
                    if (ingredient.isPresent()) {
                        GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, contentX + RECIPE_WIDTH + V_SPACING + 19 + (i % 3) * 18, infoY + 46 + 16 + (i / 3) * 18, ingredient.get().getValues().get(0).value().getDefaultInstance(), font);
                    }
                }

                GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, contentX + RECIPE_WIDTH + V_SPACING + 95 + 18, infoY + 46 + 16 + 18, ModBlocks.SLIME_SQUEEZER.toStack(), font);
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
            pGuiGraphics.blit(RenderType::guiTextured, TEXTURE, xPos, yPos, 0, 0, RECIPE_WIDTH, RECIPE_HEIGHT, 256, 256);

            // Render energy bar
            int energyScaled = (int) (((float) recipe.energy() / (float) 10000) * 57);
            pGuiGraphics.blit(RenderType::guiTextured, TEXTURE, xPos + 9, yPos + 13 + (57 - energyScaled), 153, 8, 9, energyScaled, 256, 256);
            if (pMouseX >= xPos + 9 && pMouseX < xPos + 18 && pMouseY >= yPos + 13 && pMouseY < yPos + 70) {
                Component text = Component.translatable("gui.productiveslimes.energy_stored", recipe.energy(), 10000);
                pGuiGraphics.renderTooltip(font, text, pMouseX, pMouseY);
            }

            GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, xPos + 26, yPos + 34, new ItemStack(recipe.inputItems().getFirst().getValues().get(0)), font);
            GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, xPos + 87 + 20, yPos + 34, recipe.output().getFirst(), font);
            GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, xPos + 107 + 20, yPos + 34, recipe.output().get(1), font);

            pGuiGraphics.drawString(font, recipe.output().getFirst().getDisplayName().getString().substring(1, recipe.output().getFirst().getDisplayName().getString().length() - 1), xPos + 9, yPos + 4, 0x555555, false);

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

        Component title = Component.translatable("guidebook.productiveslimes.slime_and_slimeball");
        int fontX = font.width(title);
        pGuiGraphics.drawString(font, title, (int) (contentX + (contentWidth - fontX) / 2 * 0.8f), infoY + 5, 0xFFFFFF);

        Component description = Component.translatable("guidebook.productiveslimes.slime_and_slimeball.description1");
        pGuiGraphics.drawWordWrap(font, description, contentX + 5, infoY + 20, wordWarpLength, 0xAAAAAA);

        Component description2 = Component.translatable("guidebook.productiveslimes.slime_and_slimeball.description2");
        pGuiGraphics.drawWordWrap(font, description2, contentX + 5, infoY + font.wordWrapHeight(description, wordWarpLength) + 25, (int) (contentWidth * 0.85f), 0xAAAAAA);

        Component title2 = Component.translatable("guidebook.productiveslimes.slime_growing");
        int fontX2 = font.width(title2);
        pGuiGraphics.drawString(font, title2, (int) (contentX + (contentWidth - fontX2) / 2 * 0.8f), infoY + font.wordWrapHeight(description, wordWarpLength) + font.wordWrapHeight(description2, wordWarpLength) + 30, 0xFFFFFF);

        Component description3 = Component.translatable("guidebook.productiveslimes.slime_growing.description");
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

            SlimeData slimeData = GuideBookScreenHelper.generateSlimeData(tiers);
            ItemStack slimeItem = new ItemStack(ModItems.SLIME_ITEM.get());
            slimeItem.set(ModDataComponents.SLIME_DATA, slimeData);

            GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, xPos + 63, yPos + 10, slimeItem, font);
            GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, xPos + 63, yPos + 36, slimeItem, font);
            GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, xPos + 63, yPos + 62, slimeItem, font);

            ItemStack growthItem = slimeData.growthItem();
            growthItem.setCount(3);

            GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, xPos + 124, yPos + 10, growthItem, font);
            growthItem.setCount(4);
            GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, xPos + 124, yPos + 36, growthItem, font);
            growthItem.setCount(5);
            GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, xPos + 124, yPos + 62, growthItem, font);

            index++;
        }

        int infoY2 = contentY - contentScrollOffset + SLIME_AND_SLIMEBALL_INFO_HEIGHT + totalRecipeHeight;

        Component title3 = Component.translatable("guidebook.productiveslimes.slimeball_obtaining");
        int fontX3 = font.width(title);
        pGuiGraphics.drawString(font, title3, (int) (contentX + (contentWidth - fontX3) / 2 * 0.8f), infoY2 + 5, 0xFFFFFF);

        Component description4 = Component.translatable("guidebook.productiveslimes.slimeball_obtaining.description");
        pGuiGraphics.drawWordWrap(font, description4, contentX + 5, infoY2 + 20, wordWarpLength, 0xAAAAAA);

        Component description5 = Component.translatable("guidebook.productiveslimes.slimeball_obtaining.description2");
        pGuiGraphics.drawWordWrap(font, description5, contentX + 5, infoY2 + 25 + font.wordWrapHeight(description4, wordWarpLength), wordWarpLength, 0xAAAAAA);

        Component title4 = Component.translatable("guidebook.productiveslimes.slime_cooldown_time");
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

            SlimeData slimeData = GuideBookScreenHelper.generateSlimeData(tiers);
            ItemStack slimeItem = new ItemStack(ModItems.SLIME_ITEM.get());
            slimeItem.set(ModDataComponents.SLIME_DATA, slimeData);

            GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, xPos + 29, yPos + 15, slimeItem, font);

            Component cooldownText = Component.translatable("guidebook.productiveslimes.cooldown", tiers.cooldown() / 20);
            pGuiGraphics.drawString(font, cooldownText, xPos + 55, yPos + 19, 0x555555, false);

            index2++;
        }

        int infoY3 = contentY - contentScrollOffset + SLIME_AND_SLIMEBALL_INFO_HEIGHT + totalRecipeHeight + SLIME_AND_SLIMEBALL_SECOND_INFO_HEIGHT + totalCooldownHeight;

        Component title5 = Component.translatable("block.productiveslimes.slimeball_collector");
        int fontX5 = font.width(title5);
        pGuiGraphics.drawString(font, title5, (int) (contentX + (contentWidth - fontX5) / 2 * 0.8f), infoY3 + 5, 0xFFFFFF);

        Component description6 = Component.translatable("guidebook.productiveslimes.slimeball_collector.description");
        pGuiGraphics.drawWordWrap(font, description6, contentX + 5, infoY3 + 20, wordWarpLength, 0xAAAAAA);

        Optional<RecipeHolder<?>> slimeballCollectorHolder = ClientRecipeManager.getRecipe(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "slimeball_collector"));
        RecipeHolder<ShapedRecipe> slimeballCollector = (RecipeHolder<ShapedRecipe>) slimeballCollectorHolder.get();
        List<Optional<Ingredient>> ingredients = slimeballCollector.value().getIngredients();

        int textureY = infoY3 + 20 + font.wordWrapHeight(description6, wordWarpLength) + 10;

        pGuiGraphics.blit(RenderType::guiTextured, CRAFTING_TEXTURE, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f), textureY, 0, 0, RECIPE_WIDTH, RECIPE_HEIGHT, 256, 256);

        for (int i = 0; i < ingredients.size(); i++) {
            Optional<Ingredient> ingredient = ingredients.get(i);
            if (ingredient.isPresent()) {
                ItemStack stacks = ingredient.get().getValues().get(0).value().getDefaultInstance();
                GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, textureY + 17 + (i / 3) * 18, stacks, font);
            }
        }

        ItemStack output = ModBlocks.SLIMEBALL_COLLECTOR.toStack();
        GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18, textureY + 17 + 18, output, font);

        Component note = Component.translatable("guidebook.productiveslimes.slimeball_collector.note");
        pGuiGraphics.drawString(font, note, (int) (contentX + (contentWidth - font.width(note)) / 2 * 0.8f), infoY3 + 110 + 18 + 26, 0x555555, false);

        Component title6 = Component.translatable("guidebook.productiveslimes.slime_simulation_chamber_and_upgrades");
        int fontX6 = font.width(title6);
        pGuiGraphics.drawString(font, title6, (int) (contentX + (contentWidth - fontX6) / 2 * 0.8f), infoY3 + 110 + 18 + 26 + font.wordWrapHeight(note, wordWarpLength) + 15, 0xFFFFFF);

        Component description7 = Component.translatable("guidebook.productiveslimes.slime_simulation_chamber_and_upgrades.description");
        pGuiGraphics.drawWordWrap(font, description7, contentX + 5, infoY3 + 110 + 18 + 26 + font.wordWrapHeight(note, wordWarpLength) + font.wordWrapHeight(title6, wordWarpLength) + 20, wordWarpLength, 0xAAAAAA);

        Component description8 = Component.translatable("guidebook.productiveslimes.slime_simulation_chamber_and_upgrades.description2");
        pGuiGraphics.drawWordWrap(font, description8, contentX + 5, infoY3 + 110 + 18 + 26 + font.wordWrapHeight(note, wordWarpLength) + font.wordWrapHeight(title6, wordWarpLength) + font.wordWrapHeight(description7, wordWarpLength) + 25, wordWarpLength, 0xAAAAAA);

        Optional<RecipeHolder<?>> slimeNestHolder = ClientRecipeManager.getRecipe(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "slime_nest"));
        RecipeHolder<ShapedRecipe> SlimeNest = (RecipeHolder<ShapedRecipe>) slimeNestHolder.get();
        List<Optional<Ingredient>> ingredients2 = SlimeNest.value().getIngredients();

        int textureY2 = infoY3 + 110 + 18 + 26 + font.wordWrapHeight(note, wordWarpLength) + font.wordWrapHeight(title6, wordWarpLength) + font.wordWrapHeight(description7, wordWarpLength) + font.wordWrapHeight(description8, wordWarpLength) + 30;

        pGuiGraphics.blit(RenderType::guiTextured, CRAFTING_TEXTURE, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f), textureY2, 0, 0, RECIPE_WIDTH, RECIPE_HEIGHT, 256, 256);

        for (int i = 0; i < ingredients2.size(); i++) {
            Optional<Ingredient> ingredient = ingredients2.get(i);
            if (ingredient.isPresent()) {
                ItemStack stacks = ingredient.get().getValues().get(0).value().getDefaultInstance();
                GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, textureY2 + 17 + (i / 3) * 18, stacks, font);
            }
        }

        ItemStack output2 = ModBlocks.SLIME_NEST.toStack();
        GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18, textureY2 + 17 + 18, output2, font);

        Optional<RecipeHolder<?>> upgrade1Holder = ClientRecipeManager.getRecipe(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "slime_nest_speed_upgrade_1"));
        RecipeHolder<ShapedRecipe> upgrade1 = (RecipeHolder<ShapedRecipe>) upgrade1Holder.get();
        List<Optional<Ingredient>> ingredients3 = upgrade1.value().getIngredients();

        int textureY3 = infoY3 + 110 + 18 + 26 + font.wordWrapHeight(note, wordWarpLength) + font.wordWrapHeight(title6, wordWarpLength) + font.wordWrapHeight(description7, wordWarpLength) + font.wordWrapHeight(description8, wordWarpLength) + 30 + RECIPE_HEIGHT + 10;

        pGuiGraphics.blit(RenderType::guiTextured, CRAFTING_TEXTURE, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f), textureY3, 0, 0, RECIPE_WIDTH, RECIPE_HEIGHT, 256, 256);

        for (int i = 0; i < ingredients3.size(); i++) {
            Optional<Ingredient> ingredient = ingredients3.get(i);
            if (ingredient.isPresent()) {
                ItemStack stacks = ingredient.get().getValues().get(0).value().getDefaultInstance();
                GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, textureY3 + 17 + (i / 3) * 18, stacks, font);
            }
        }

        ItemStack output3 = ModItems.SLIME_NEST_SPEED_UPGRADE_1.toStack();
        GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18, textureY3 + 17 + 18, output3, font);

        Optional<RecipeHolder<?>> upgrade2Holder = ClientRecipeManager.getRecipe(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "slime_nest_speed_upgrade_2"));
        RecipeHolder<ShapedRecipe> upgrade2 = (RecipeHolder<ShapedRecipe>) upgrade2Holder.get();
        List<Optional<Ingredient>> ingredients4 = upgrade2.value().getIngredients();

        int textureY4 = infoY3 + 110 + 18 + 26 + font.wordWrapHeight(note, wordWarpLength) + font.wordWrapHeight(title6, wordWarpLength) + font.wordWrapHeight(description7, wordWarpLength) + font.wordWrapHeight(description8, wordWarpLength) + 30 + RECIPE_HEIGHT + 10 + RECIPE_HEIGHT + 10;

        pGuiGraphics.blit(RenderType::guiTextured, CRAFTING_TEXTURE, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f), textureY4, 0, 0, RECIPE_WIDTH, RECIPE_HEIGHT, 256, 256);

        for (int i = 0; i < ingredients4.size(); i++) {
            Optional<Ingredient> ingredient = ingredients4.get(i);
            if (ingredient.isPresent()) {
                ItemStack stacks = ingredient.get().getValues().get(0).value().getDefaultInstance();
                GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (i % 3) * 18, textureY4 + 17 + (i / 3) * 18, stacks, font);
            }
        }

        ItemStack output4 = ModItems.SLIME_NEST_SPEED_UPGRADE_2.toStack();
        GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18, textureY4 + 17 + 18, output4, font);

        Component title7 = Component.translatable("guidebook.productiveslimes.slimeball_fragment");
        int fontX7 = font.width(title7);
        pGuiGraphics.drawString(font, title7, (int) (contentX + (contentWidth - fontX7) / 2 * 0.8f), infoY3 + 110 + 18 + 26 + font.wordWrapHeight(note, wordWarpLength) + font.wordWrapHeight(title6, wordWarpLength) + font.wordWrapHeight(description7, wordWarpLength) + font.wordWrapHeight(description8, wordWarpLength) + 30 + RECIPE_HEIGHT + 10 + RECIPE_HEIGHT + 10 + RECIPE_HEIGHT + 5, 0xFFFFFF);

        Component description9 = Component.translatable("guidebook.productiveslimes.slimeball_fragment.description");
        pGuiGraphics.drawWordWrap(font, description9, contentX + 5, infoY3 + 110 + 18 + 26 + font.wordWrapHeight(note, wordWarpLength) + font.wordWrapHeight(title6, wordWarpLength) + font.wordWrapHeight(description7, wordWarpLength) + font.wordWrapHeight(description8, wordWarpLength) + 30 + RECIPE_HEIGHT + 10 + RECIPE_HEIGHT + 10 + RECIPE_HEIGHT + 10 + 5, wordWarpLength, 0xAAAAAA);

        Optional<RecipeHolder<?>> slimeball = ClientRecipeManager.getRecipe(ResourceLocation.fromNamespaceAndPath("minecraft", "slimeball_from_fragment"));
        RecipeHolder<ShapedRecipe> slimeballFragment = (RecipeHolder<ShapedRecipe>) slimeball.get();
        List<Optional<Ingredient>> ingredients5 = slimeballFragment.value().getIngredients();

        int textureY5 = infoY3 + 110 + 18 + 26 + font.wordWrapHeight(note, wordWarpLength) + font.wordWrapHeight(title6, wordWarpLength) + font.wordWrapHeight(description7, wordWarpLength) + font.wordWrapHeight(description8, wordWarpLength) + 30 + RECIPE_HEIGHT + 10 + RECIPE_HEIGHT + 10 + RECIPE_HEIGHT + 10 + font.wordWrapHeight(description9, wordWarpLength) + 10;

        pGuiGraphics.blit(RenderType::guiTextured, CRAFTING_TEXTURE, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f), textureY5, 0, 0, RECIPE_WIDTH, RECIPE_HEIGHT, 256, 256);

        for (int i = 0, k = 0; i < ingredients5.size(); i++, k++) {
            Optional<Ingredient> ingredient = ingredients5.get(i);
            if (k == 2) k+=1;
            if (ingredient.isPresent()) {
                ItemStack stacks = ingredient.get().getValues().get(0).value().getDefaultInstance();
                GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 19 + (k % 3) * 18, textureY5 + 17 + (k / 3) * 18, stacks, font);
            }
        }

        ItemStack output5 = Items.SLIME_BALL.getDefaultInstance();
        GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, (int) (contentX + (contentWidth - RECIPE_WIDTH) / 2 * 0.8f) + 95 + 18, textureY5 + 17 + 18, output5, font);

        SLIME_AND_SLIMEBALL_THIRD_INFO_HEIGHT = 600;

        int totalContentHeight = SLIME_AND_SLIMEBALL_INFO_HEIGHT + totalRecipeHeight + SLIME_AND_SLIMEBALL_SECOND_INFO_HEIGHT + totalCooldownHeight + SLIME_AND_SLIMEBALL_THIRD_INFO_HEIGHT;

        int scrollbarX = width - SCROLLBAR_WIDTH;
        int scrollbarHeight = (int) ((float) height / totalContentHeight * height);
        int scrollbarY = (int) ((float) contentScrollOffset / totalContentHeight * height);
        pGuiGraphics.fill(scrollbarX, 0, scrollbarX + SCROLLBAR_WIDTH, height, 0x55555555);
        pGuiGraphics.fill(scrollbarX, scrollbarY, scrollbarX + SCROLLBAR_WIDTH, scrollbarY + scrollbarHeight, 0x55888888);
    }

    private void drawWelcomePage(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY){
        int contentX = 10 + NAVIGATION_WIDTH + 20;
        int contentY = 10;
        int contentWidth = this.width - contentX - 10;

        int infoY = contentY - contentScrollOffset;

        Component title = Component.translatable("guidebook.productiveslimes.welcome");
        int fontX = font.width(title);
        pGuiGraphics.drawString(font, title, contentX + (contentWidth - fontX) / 2, infoY + 5, 0xFFFFFF);

        Component description = Component.translatable("guidebook.productiveslimes.welcome.description");
        pGuiGraphics.drawWordWrap(font, description, contentX + 5, infoY + 20, contentWidth, 0xAAAAAA);

        Component description2 = Component.translatable("guidebook.productiveslimes.welcome.description2");
        pGuiGraphics.drawWordWrap(font, description2, contentX + 5, infoY + 20 + font.wordWrapHeight(description, contentWidth) + 5, contentWidth, 0xAAAAAA);

        Component description3 = Component.translatable("guidebook.productiveslimes.welcome.description3");
        pGuiGraphics.drawWordWrap(font, description3, contentX + 5, infoY + 20 + font.wordWrapHeight(description, contentWidth) + font.wordWrapHeight(description2, contentWidth) + 10, contentWidth, 0xAAAAAA);

        Component description4 = Component.translatable("guidebook.productiveslimes.welcome.description4");
        pGuiGraphics.drawWordWrap(font, description4, contentX + 5, infoY + 20 + font.wordWrapHeight(description, contentWidth) + font.wordWrapHeight(description2, contentWidth) + font.wordWrapHeight(description3, contentWidth) + 15, contentWidth, 0xAAAAAA);

        Component wikiLink = Component.translatable("guidebook.productiveslimes.welcome.wiki_link");
        int wikiLinkWidth = font.width(wikiLink);
        pGuiGraphics.drawString(font, wikiLink, contentX + (contentWidth - wikiLinkWidth) / 2, infoY + 20 + font.wordWrapHeight(description, contentWidth) + font.wordWrapHeight(description2, contentWidth) + font.wordWrapHeight(description3, contentWidth) + font.wordWrapHeight(description4, contentWidth) + 20, 0x5555FF);
    }

    private void drawEnergyGeneration(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        int contentX = 10 + NAVIGATION_WIDTH + 20;
        int contentY = 10;
        int contentWidth = this.width - contentX - 10 - SCROLLBAR_WIDTH;

        int infoY = contentY - contentScrollOffset;

        Component title = Component.translatable("guidebook.productiveslimes.energy_generation");
        int fontX = font.width(title);
        pGuiGraphics.drawString(font, title, contentX + (contentWidth - fontX) / 2, infoY + 5, 0xFFFFFF);

        Component description = Component.translatable("guidebook.productiveslimes.energy_generation.description");
        pGuiGraphics.drawWordWrap(font, description, contentX + 5, infoY + 20, contentWidth, 0xAAAAAA);

        int recipeBaseY = infoY + font.lineHeight + font.wordWrapHeight(description, contentWidth) + 25;

        pGuiGraphics.blit(RenderType::guiTextured, CRAFTING_TEXTURE, contentX + (contentWidth - RECIPE_WIDTH) / 2, recipeBaseY, 0, 0, RECIPE_WIDTH, RECIPE_HEIGHT, 256, 256);

        Optional<RecipeHolder<?>> recipeHolder = ClientRecipeManager.getRecipe(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "energy_slime_spawn_egg"));
        RecipeHolder<ShapedRecipe> recipe = (RecipeHolder<ShapedRecipe>) recipeHolder.get();
        List<Optional<Ingredient>> ingredients = recipe.value().getIngredients();

        for (int i = 0; i < ingredients.size(); i++) {
            Optional<Ingredient> ingredient = ingredients.get(i);
            if (ingredient.isPresent()) {
                ItemStack stacks = ingredient.get().getValues().get(0).value().getDefaultInstance();
                GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, contentX + (contentWidth - RECIPE_WIDTH) / 2 + 19 + (i % 3) * 18, recipeBaseY + 17 + (i / 3) * 18, stacks, font);
            }
        }

        ItemStack output = ModItems.ENERGY_SLIME_SPAWN_EGG.toStack();
        GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, contentX + (contentWidth - RECIPE_WIDTH) / 2 + 95 + 18, recipeBaseY + 17 + 18, output, font);

        Optional<RecipeHolder<?>> recipeHolder2 = ClientRecipeManager.getRecipe(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "energy_generator"));
        RecipeHolder<ShapedRecipe> recipe2 = (RecipeHolder<ShapedRecipe>) recipeHolder2.get();
        List<Optional<Ingredient>> ingredients2 = recipe2.value().getIngredients();

        int recipeBaseY2 = recipeBaseY + RECIPE_HEIGHT + 10;

        pGuiGraphics.blit(RenderType::guiTextured, CRAFTING_TEXTURE, contentX + (contentWidth - RECIPE_WIDTH) / 2, recipeBaseY2, 0, 0, RECIPE_WIDTH, RECIPE_HEIGHT, 256, 256);

        for (int i = 0; i < ingredients2.size(); i++) {
            Optional<Ingredient> ingredient = ingredients2.get(i);
            if (ingredient.isPresent()) {
                ItemStack stacks = ingredient.get().getValues().get(0).value().getDefaultInstance();
                GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, contentX + (contentWidth - RECIPE_WIDTH) / 2 + 19 + (i % 3) * 18, recipeBaseY2 + 17 + (i / 3) * 18, stacks, font);
            }
        }

        ItemStack output2 = ModBlocks.ENERGY_GENERATOR.toStack();
        GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, contentX + (contentWidth - RECIPE_WIDTH) / 2 + 95 + 18, recipeBaseY2 + 17 + 18, output2, font);

        Optional<RecipeHolder<?>> recipeHolder3 = ClientRecipeManager.getRecipe(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "energy_multiplier_upgrade"));
        RecipeHolder<ShapedRecipe> recipe3 = (RecipeHolder<ShapedRecipe>) recipeHolder3.get();
        List<Optional<Ingredient>> ingredients3 = recipe3.value().getIngredients();

        int recipeBaseY3 = recipeBaseY2 + RECIPE_HEIGHT + 10;

        pGuiGraphics.blit(RenderType::guiTextured, CRAFTING_TEXTURE, contentX + (contentWidth - RECIPE_WIDTH) / 2, recipeBaseY3, 0, 0, RECIPE_WIDTH, RECIPE_HEIGHT, 256, 256);

        for (int i = 0; i < ingredients3.size(); i++) {
            Optional<Ingredient> ingredient = ingredients3.get(i);
            if (ingredient.isPresent()) {
                ItemStack stacks = ingredient.get().getValues().get(0).value().getDefaultInstance();
                GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, contentX + (contentWidth - RECIPE_WIDTH) / 2 + 19 + (i % 3) * 18, recipeBaseY3 + 17 + (i / 3) * 18, stacks, font);
            }
        }

        ItemStack output3 = ModItems.ENERGY_MULTIPLIER_UPGRADE.toStack();
        GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, contentX + (contentWidth - RECIPE_WIDTH) / 2 + 95 + 18, recipeBaseY3 + 17 + 18, output3, font);

        Optional<RecipeHolder<?>> recipeHolder4 = ClientRecipeManager.getRecipe(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "cable"));
        RecipeHolder<ShapedRecipe> recipe4 = (RecipeHolder<ShapedRecipe>) recipeHolder4.get();
        List<Optional<Ingredient>> ingredients4 = recipe4.value().getIngredients();

        int recipeBaseY4 = recipeBaseY3 + RECIPE_HEIGHT + 10;

        pGuiGraphics.blit(RenderType::guiTextured, CRAFTING_TEXTURE, contentX + (contentWidth - RECIPE_WIDTH) / 2, recipeBaseY4, 0, 0, RECIPE_WIDTH, RECIPE_HEIGHT, 256, 256);

        for (int i = 0; i < ingredients4.size(); i++) {
            Optional<Ingredient> ingredient = ingredients4.get(i);
            if (ingredient.isPresent()) {
                ItemStack stacks = ingredient.get().getValues().get(0).value().getDefaultInstance();
                GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, contentX + (contentWidth - RECIPE_WIDTH) / 2 + 19 + (i % 3) * 18, recipeBaseY4 + 17 + (i / 3) * 18, stacks, font);
            }
        }

        ItemStack output4 = ModBlocks.CABLE.toStack();
        GuideBookScreenHelper.renderItemSlot(pGuiGraphics, pMouseX, pMouseY, contentX + (contentWidth - RECIPE_WIDTH) / 2 + 95 + 18, recipeBaseY4 + 17 + 18, output4, font);

        ENERGY_GENERATION_INFO_HEIGHT = RECIPE_HEIGHT * 4 + 50 + font.wordWrapHeight(description, contentWidth) + font.wordWrapHeight(title, contentWidth) + 20;

        int scrollbarX = width - SCROLLBAR_WIDTH;
        int scrollbarHeight = (int) ((float) height / ENERGY_GENERATION_INFO_HEIGHT * height);
        int scrollbarY = (int) ((float) contentScrollOffset / ENERGY_GENERATION_INFO_HEIGHT * height);
        pGuiGraphics.fill(scrollbarX, 0, scrollbarX + SCROLLBAR_WIDTH, height, 0x55555555);
        pGuiGraphics.fill(scrollbarX, scrollbarY, scrollbarX + SCROLLBAR_WIDTH, scrollbarY + scrollbarHeight, 0x55888888);
    }

    private void drawWorldGen(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        int contentX = 10 + NAVIGATION_WIDTH + 20;
        int contentY = 10;
        int contentWidth = this.width - contentX - 10 - SCROLLBAR_WIDTH;

        int infoY = contentY - contentScrollOffset;

        Component title = Component.translatable("guidebook.productiveslimes.world_generation");
        int fontX = font.width(title);
        pGuiGraphics.drawString(font, title, contentX + (contentWidth - fontX) / 2, infoY + 5, 0xFFFFFF);

        Component description = Component.translatable("guidebook.productiveslimes.world_generation.description");
        pGuiGraphics.drawWordWrap(font, description, contentX + 5, infoY + 20, contentWidth, 0xAAAAAA);

        Component description2 = Component.translatable("guidebook.productiveslimes.world_generation.description2");
        pGuiGraphics.drawWordWrap(font, description2, contentX + 5, infoY + 20 + font.wordWrapHeight(description, contentWidth) + 5, contentWidth, 0xAAAAAA);

        Component description3 = Component.translatable("guidebook.productiveslimes.world_generation.description3");
        pGuiGraphics.drawWordWrap(font, description3, contentX + 5, infoY + 20 + font.wordWrapHeight(description, contentWidth) + font.wordWrapHeight(description2, contentWidth) + 10, contentWidth, 0xAAAAAA);
    }
}