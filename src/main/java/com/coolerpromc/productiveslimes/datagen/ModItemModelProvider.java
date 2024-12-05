package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;


public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ProductiveSlimes.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.GUIDEBOOK);
        simpleItem(ModItems.ENERGY_MULTIPLIER_UPGRADE);

        slimeballItem(ModItems.ENERGY_SLIME_BALL);
        dnaItem(ModItems.SLIME_DNA);
        withExistingParent(ModItems.ENERGY_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        for (Tier tier : Tier.values()){
            ModTiers tiers = ModTierLists.getTierByName(tier);

            slimeballItem(ModTierLists.getSlimeballItemByName(tiers.getName()));
            bucketItem(ModTierLists.getBucketItemByName(tiers.getName()));
            dnaItem(ModTierLists.getDnaItemByName(tiers.getName()));
            withExistingParent(ModTierLists.getSpawnEggItemByName(tiers.getName()).getId().getPath(), mcLoc("item/template_spawn_egg"));
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
}
