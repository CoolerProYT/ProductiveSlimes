package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.fluid.ModFluids;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import net.minecraft.core.Direction;
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

        for(Tier tier : Tier.values()){
            ModTiers modTiers = ModTierLists.getTierByName(tier);
            String name = modTiers.name();
            slimeballItem(ModTierLists.getSlimeballItemByName(name));
            bucketItem(ModTierLists.getBucketItemByName(name));
            dnaItem(ModTierLists.getDnaItemByName(name));
            withExistingParent(ModTierLists.getSpawnEggItemByName(name).getId().getPath(), mcLoc("item/template_spawn_egg"));
        }
    }

    private ItemModelBuilder slimeballItem(DeferredItem<Item> item){
        return getBuilder(item.getId().getPath())
                .parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "item/template_slimeball"))
                .element()
                .face(Direction.DOWN).texture("#layer0").tintindex(0).end()
                .face(Direction.UP).texture("#layer0").tintindex(0).end()
                .face(Direction.NORTH).texture("#layer0").tintindex(0).end()
                .face(Direction.SOUTH).texture("#layer0").tintindex(0).end()
                .face(Direction.WEST).texture("#layer0").tintindex(0).end()
                .face(Direction.EAST).texture("#layer0").tintindex(0).end()
                .end();
    }

    private ItemModelBuilder dnaItem(DeferredItem<Item> item){
        return getBuilder(item.getId().getPath())
                .parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "item/template_dna"))
                .element()
                .face(Direction.DOWN).texture("#layer0").tintindex(0).end()
                .face(Direction.UP).texture("#layer0").tintindex(0).end()
                .face(Direction.NORTH).texture("#layer0").tintindex(0).end()
                .face(Direction.SOUTH).texture("#layer0").tintindex(0).end()
                .face(Direction.WEST).texture("#layer0").tintindex(0).end()
                .face(Direction.EAST).texture("#layer0").tintindex(0).end()
                .end();
    }

    private ItemModelBuilder bucketItem(DeferredItem<Item> item){
        return getBuilder(item.getId().getPath())
                .parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "item/bucket"))
                .texture("layer1", ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "item/bucket_fluid"))
                .element()
                .face(Direction.DOWN).texture("#layer1").tintindex(1).end()
                .face(Direction.UP).texture("#layer1").tintindex(1).end()
                .face(Direction.NORTH).texture("#layer1").tintindex(1).end()
                .face(Direction.SOUTH).texture("#layer1").tintindex(1).end()
                .face(Direction.WEST).texture("#layer1").tintindex(1).end()
                .face(Direction.EAST).texture("#layer1").tintindex(1).end()
                .end();
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
