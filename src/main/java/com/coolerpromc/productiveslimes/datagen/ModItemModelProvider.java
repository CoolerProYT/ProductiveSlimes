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

        basicItem(ModFluids.MOLTEN_DIRT_BUCKET.get());
        basicItem(ModFluids.MOLTEN_STONE_BUCKET.get());
        basicItem(ModFluids.MOLTEN_IRON_BUCKET.get());
        basicItem(ModFluids.MOLTEN_COPPER_BUCKET.get());
        basicItem(ModFluids.MOLTEN_GOLD_BUCKET.get());
        basicItem(ModFluids.MOLTEN_DIAMOND_BUCKET.get());
        basicItem(ModFluids.MOLTEN_NETHERITE_BUCKET.get());
        basicItem(ModFluids.MOLTEN_LAPIS_BUCKET.get());
        basicItem(ModFluids.MOLTEN_REDSTONE_BUCKET.get());

        withExistingParent(ModItems.DIRT_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.STONE_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.IRON_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.COPPER_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.GOLD_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.DIAMOND_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.NETHERITE_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.LAPIS_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.REDSTONE_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
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

    private ItemModelBuilder simpleItem(DeferredItem<Item> item){
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.tryParse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID,"item/" + item.getId().getPath()));
    }
}
