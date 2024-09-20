package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.fluid.ModFluids;
import com.coolerpromc.productiveslimes.item.ModItems;
import net.minecraft.core.Direction;
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

        slimeballItem(ModItems.DIRT_SLIME_BALL);
        slimeballItem(ModItems.STONE_SLIME_BALL);
        slimeballItem(ModItems.IRON_SLIME_BALL);
        slimeballItem(ModItems.COPPER_SLIME_BALL);
        slimeballItem(ModItems.GOLD_SLIME_BALL);
        slimeballItem(ModItems.DIAMOND_SLIME_BALL);
        slimeballItem(ModItems.NETHERITE_SLIME_BALL);
        slimeballItem(ModItems.LAPIS_SLIME_BALL);
        slimeballItem(ModItems.REDSTONE_SLIME_BALL);
        slimeballItem(ModItems.OAK_SLIME_BALL);
        slimeballItem(ModItems.SAND_SLIME_BALL);

        bucketItem(ModFluids.MOLTEN_DIRT_BUCKET);
        bucketItem(ModFluids.MOLTEN_STONE_BUCKET);
        bucketItem(ModFluids.MOLTEN_IRON_BUCKET);
        bucketItem(ModFluids.MOLTEN_COPPER_BUCKET);
        bucketItem(ModFluids.MOLTEN_GOLD_BUCKET);
        bucketItem(ModFluids.MOLTEN_DIAMOND_BUCKET);
        bucketItem(ModFluids.MOLTEN_NETHERITE_BUCKET);
        bucketItem(ModFluids.MOLTEN_LAPIS_BUCKET);
        bucketItem(ModFluids.MOLTEN_REDSTONE_BUCKET);
        bucketItem(ModFluids.MOLTEN_OAK_BUCKET);
        bucketItem(ModFluids.MOLTEN_SAND_BUCKET);

        withExistingParent(ModItems.DIRT_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.STONE_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.IRON_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.COPPER_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.GOLD_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.DIAMOND_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.NETHERITE_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.LAPIS_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.REDSTONE_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.OAK_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.SAND_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
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
}
