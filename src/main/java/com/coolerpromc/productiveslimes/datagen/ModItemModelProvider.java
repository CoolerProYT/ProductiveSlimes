package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;


public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ProductiveSlimes.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.GUIDEBOOK);
        simpleItem(ModItems.ENERGY_MULTIPLIER_UPGRADE);
        simpleItem(ModItems.SLIMEBALL_FRAGMENT);

        saplingItem(ModBlocks.SLIMY_SAPLING);
        buttonItem(ModBlocks.SLIMY_BUTTON, ModBlocks.SLIMY_PLANKS);
        fenceItem(ModBlocks.SLIMY_FENCE, ModBlocks.SLIMY_PLANKS);
        basicItem(ModBlocks.SLIMY_DOOR.asItem());
        buttonItem(ModBlocks.SLIMY_STONE_BUTTON, ModBlocks.SLIMY_STONE);
        wallItem(ModBlocks.SLIMY_COBBLESTONE_WALL, ModBlocks.SLIMY_COBBLESTONE);
        wallItem(ModBlocks.SLIMY_COBBLED_DEEPSLATE_WALL, ModBlocks.SLIMY_COBBLED_DEEPSLATE);

        slimeballItem(ModItems.ENERGY_SLIME_BALL);
        dnaItem(ModItems.SLIME_DNA);
        withExistingParent(ModItems.ENERGY_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        for (Tier tier : Tier.values()){
            ModTiers tiers = ModTierLists.getTierByName(tier);

            slimeballItem(ModTierLists.getSlimeballItemByName(tiers.name()));
            bucketItem(ModTierLists.getBucketItemByName(tiers.name()));
            dnaItem(ModTierLists.getDnaItemByName(tiers.name()));
            withExistingParent(ModTierLists.getSpawnEggItemByName(tiers.name()).getId().getPath(), mcLoc("item/template_spawn_egg"));
        }
    }

    private ItemModelBuilder slimeballItem(DeferredItem<Item> item){
        return getBuilder(item.getId().getPath())
                .parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "item/template_slimeball"));
    }

    private ItemModelBuilder dnaItem(DeferredItem<Item> item){
        return getBuilder(item.getId().getPath())
                .parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "item/template_dna"));
    }

    private ItemModelBuilder bucketItem(DeferredItem<Item> item){
        return getBuilder(item.getId().getPath())
                .parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "item/bucket"))
                .texture("layer1", ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "item/bucket_fluid"));
    }

    private ItemModelBuilder simpleItem(DeferredItem<Item> item){
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.tryParse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder saplingItem(DeferredBlock<Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "block/" + item.getId().getPath()));
    }
    public void buttonItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID,
                        "block/" + baseBlock.getId().getPath()));
    }
    public void fenceItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID,
                        "block/" + baseBlock.getId().getPath()));
    }
    public void wallItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID,
                        "block/" + baseBlock.getId().getPath()));
    }
}
