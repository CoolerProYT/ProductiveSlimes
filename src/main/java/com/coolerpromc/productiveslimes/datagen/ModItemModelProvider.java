package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ProductiveSlimes.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.GUIDEBOOK);
        simpleItem(ModItems.ENERGY_MULTIPLIER_UPGRADE);
        simpleItem(ModItems.SLIMEBALL_FRAGMENT);
        simpleItem(ModItems.SLIME_NEST_SPEED_UPGRADE_1);
        simpleItem(ModItems.SLIME_NEST_SPEED_UPGRADE_2);

        saplingItem(ModBlocks.SLIMY_SAPLING);

        buttonItem(ModBlocks.SLIMY_BUTTON, ModBlocks.SLIMY_PLANKS);
        fenceItem(ModBlocks.SLIMY_FENCE, ModBlocks.SLIMY_PLANKS);
        simpleItem("slimy_door");
        buttonItem(ModBlocks.SLIMY_STONE_BUTTON, ModBlocks.SLIMY_STONE);
        wallItem(ModBlocks.SLIMY_COBBLESTONE_WALL, ModBlocks.SLIMY_COBBLESTONE);

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

    private ItemModelBuilder slimeballItem(RegistryObject<Item> item){
        return getBuilder(item.getId().getPath())
                .parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", new ResourceLocation(ProductiveSlimes.MODID, "item/template_slimeball"))
                .element()
                .face(Direction.DOWN).texture("#layer0").tintindex(0).end()
                .face(Direction.UP).texture("#layer0").tintindex(0).end()
                .face(Direction.NORTH).texture("#layer0").tintindex(0).end()
                .face(Direction.SOUTH).texture("#layer0").tintindex(0).end()
                .face(Direction.WEST).texture("#layer0").tintindex(0).end()
                .face(Direction.EAST).texture("#layer0").tintindex(0).end()
                .end();
    }

    private ItemModelBuilder dnaItem(RegistryObject<Item> item){
        return getBuilder(item.getId().getPath())
                .parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", new ResourceLocation(ProductiveSlimes.MODID, "item/template_dna"))
                .element()
                .face(Direction.DOWN).texture("#layer0").tintindex(0).end()
                .face(Direction.UP).texture("#layer0").tintindex(0).end()
                .face(Direction.NORTH).texture("#layer0").tintindex(0).end()
                .face(Direction.SOUTH).texture("#layer0").tintindex(0).end()
                .face(Direction.WEST).texture("#layer0").tintindex(0).end()
                .face(Direction.EAST).texture("#layer0").tintindex(0).end()
                .end();
    }

    private ItemModelBuilder bucketItem(RegistryObject<Item> item){
        return getBuilder(item.getId().getPath())
                .parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", new ResourceLocation(ProductiveSlimes.MODID, "item/bucket"))
                .texture("layer1", new ResourceLocation(ProductiveSlimes.MODID, "item/bucket_fluid"))
                .element()
                .face(Direction.DOWN).texture("#layer1").tintindex(1).end()
                .face(Direction.UP).texture("#layer1").tintindex(1).end()
                .face(Direction.NORTH).texture("#layer1").tintindex(1).end()
                .face(Direction.SOUTH).texture("#layer1").tintindex(1).end()
                .face(Direction.WEST).texture("#layer1").tintindex(1).end()
                .face(Direction.EAST).texture("#layer1").tintindex(1).end()
                .end();
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.tryParse("item/generated")).texture("layer0",
                new ResourceLocation(ProductiveSlimes.MODID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleItem(String name){
        return withExistingParent(name,
                ResourceLocation.tryParse("item/generated")).texture("layer0",
                new ResourceLocation(ProductiveSlimes.MODID,"item/" + name));
    }

    private ItemModelBuilder saplingItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(ProductiveSlimes.MODID, "block/" + item.getId().getPath()));
    }

    public void buttonItem(RegistryObject<?> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  new ResourceLocation(ProductiveSlimes.MODID,
                        "block/" + baseBlock.getId().getPath()));
    }
    public void fenceItem(RegistryObject<?> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  new ResourceLocation(ProductiveSlimes.MODID,
                        "block/" + baseBlock.getId().getPath()));
    }
    public void wallItem(RegistryObject<?> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  new ResourceLocation(ProductiveSlimes.MODID,
                        "block/" + baseBlock.getId().getPath()));
    }
}
