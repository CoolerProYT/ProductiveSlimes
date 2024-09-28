package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.compat.atm.AtmItems;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, ProductiveSlimes.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(Tags.Items.SLIME_BALLS)
                .add(ModItems.DIRT_SLIME_BALL.get())
                .add(ModItems.STONE_SLIME_BALL.get())
                .add(ModItems.COPPER_SLIME_BALL.get())
                .add(ModItems.IRON_SLIME_BALL.get())
                .add(ModItems.GOLD_SLIME_BALL.get())
                .add(ModItems.DIAMOND_SLIME_BALL.get())
                .add(ModItems.LAPIS_SLIME_BALL.get())
                .add(ModItems.REDSTONE_SLIME_BALL.get())
                .add(ModItems.NETHERITE_SLIME_BALL.get())
                .add(ModItems.OAK_SLIME_BALL.get())
                .add(ModItems.SAND_SLIME_BALL.get())
                .add(ModItems.ANDESITE_SLIME_BALL.get())
                .add(ModItems.SNOW_SLIME_BALL.get())
                .add(ModItems.ICE_SLIME_BALL.get())
                .add(ModItems.MUD_SLIME_BALL.get())
                .add(ModItems.CLAY_SLIME_BALL.get())
                .add(ModItems.RED_SAND_SLIME_BALL.get())
                .add(ModItems.MOSS_SLIME_BALL.get())
                .add(ModItems.DEEPSLATE_SLIME_BALL.get())
                .add(ModItems.GRANITE_SLIME_BALL.get())
                .add(ModItems.DIORITE_SLIME_BALL.get())
                .add(ModItems.CALCITE_SLIME_BALL.get())
                .add(ModItems.TUFF_SLIME_BALL.get())
                .add(ModItems.DRIPSTONE_SLIME_BALL.get())
                .add(ModItems.PRISMARINE_SLIME_BALL.get())
                .add(ModItems.MAGMA_SLIME_BALL.get())
                .add(ModItems.OBSIDIAN_SLIME_BALL.get())
                .add(ModItems.NETHERRACK_SLIME_BALL.get())
                .add(ModItems.SOUL_SAND_SLIME_BALL.get())
                .add(ModItems.SOUL_SOIL_SLIME_BALL.get())
                .add(ModItems.BLACKSTONE_SLIME_BALL.get())
                .add(ModItems.BASALT_SLIME_BALL.get())
                .add(ModItems.ENDSTONE_SLIME_BALL.get())
                .add(ModItems.QUARTZ_SLIME_BALL.get())
                .add(ModItems.GLOWSTONE_SLIME_BALL.get())
                .add(ModItems.AMETHYST_SLIME_BALL.get())
                .add(ModItems.BROWN_MUSHROOM_SLIME_BALL.get())
                .add(ModItems.RED_MUSHROOM_SLIME_BALL.get())
                .add(ModItems.CACTUS_SLIME_BALL.get())
                .add(ModItems.COAL_SLIME_BALL.get())
                .add(ModItems.GRAVEL_SLIME_BALL.get())
                .add(ModItems.ENERGY_SLIME_BALL.get())
                .add(AtmItems.ATM_SLIME_BALL.get())
                .add(AtmItems.VIBRANIUM_SLIME_BALL.get())
                .add(AtmItems.UNOBTAINIUM_SLIME_BALL.get());

        tag(ModTags.Items.DNA_ITEM)
                .add(ModItems.DIRT_SLIME_DNA.get())
                .add(ModItems.STONE_SLIME_DNA.get())
                .add(ModItems.COPPER_SLIME_DNA.get())
                .add(ModItems.IRON_SLIME_DNA.get())
                .add(ModItems.GOLD_SLIME_DNA.get())
                .add(ModItems.DIAMOND_SLIME_DNA.get())
                .add(ModItems.LAPIS_SLIME_DNA.get())
                .add(ModItems.REDSTONE_SLIME_DNA.get())
                .add(ModItems.NETHERITE_SLIME_DNA.get())
                .add(ModItems.OAK_SLIME_DNA.get())
                .add(ModItems.SAND_SLIME_DNA.get())
                .add(ModItems.ANDESITE_SLIME_DNA.get())
                .add(ModItems.SNOW_SLIME_DNA.get())
                .add(ModItems.ICE_SLIME_DNA.get())
                .add(ModItems.MUD_SLIME_DNA.get())
                .add(ModItems.CLAY_SLIME_DNA.get())
                .add(ModItems.RED_SAND_SLIME_DNA.get())
                .add(ModItems.MOSS_SLIME_DNA.get())
                .add(ModItems.DEEPSLATE_SLIME_DNA.get())
                .add(ModItems.GRANITE_SLIME_DNA.get())
                .add(ModItems.DIORITE_SLIME_DNA.get())
                .add(ModItems.CALCITE_SLIME_DNA.get())
                .add(ModItems.TUFF_SLIME_DNA.get())
                .add(ModItems.DRIPSTONE_SLIME_DNA.get())
                .add(ModItems.PRISMARINE_SLIME_DNA.get())
                .add(ModItems.MAGMA_SLIME_DNA.get())
                .add(ModItems.OBSIDIAN_SLIME_DNA.get())
                .add(ModItems.NETHERRACK_SLIME_DNA.get())
                .add(ModItems.SOUL_SAND_SLIME_DNA.get())
                .add(ModItems.SOUL_SOIL_SLIME_DNA.get())
                .add(ModItems.BLACKSTONE_SLIME_DNA.get())
                .add(ModItems.BASALT_SLIME_DNA.get())
                .add(ModItems.ENDSTONE_SLIME_DNA.get())
                .add(ModItems.QUARTZ_SLIME_DNA.get())
                .add(ModItems.GLOWSTONE_SLIME_DNA.get())
                .add(ModItems.AMETHYST_SLIME_DNA.get())
                .add(ModItems.BROWN_MUSHROOM_SLIME_DNA.get())
                .add(ModItems.RED_MUSHROOM_SLIME_DNA.get())
                .add(ModItems.CACTUS_SLIME_DNA.get())
                .add(ModItems.COAL_SLIME_DNA.get())
                .add(ModItems.GRAVEL_SLIME_DNA.get())
                .add(ModItems.SLIME_DNA.get())
                .add(AtmItems.ATM_SLIME_DNA.get())
                .add(AtmItems.VIBRANIUM_SLIME_DNA.get())
                .add(AtmItems.UNOBTAINIUM_SLIME_DNA.get());
    }
}
