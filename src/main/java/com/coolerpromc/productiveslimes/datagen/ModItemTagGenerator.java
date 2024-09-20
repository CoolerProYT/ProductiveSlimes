package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.item.ModItems;
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
                .add(ModItems.SAND_SLIME_BALL.get());
    }
}
