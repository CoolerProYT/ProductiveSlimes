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
        slimeballItem(ModItems.ANDESITE_SLIME_BALL);
        slimeballItem(ModItems.SNOW_SLIME_BALL);
        slimeballItem(ModItems.ICE_SLIME_BALL);
        slimeballItem(ModItems.MUD_SLIME_BALL);
        slimeballItem(ModItems.CLAY_SLIME_BALL);
        slimeballItem(ModItems.RED_SAND_SLIME_BALL);
        slimeballItem(ModItems.MOSS_SLIME_BALL);
        slimeballItem(ModItems.DEEPSLATE_SLIME_BALL);
        slimeballItem(ModItems.GRANITE_SLIME_BALL);
        slimeballItem(ModItems.DIORITE_SLIME_BALL);
        slimeballItem(ModItems.CALCITE_SLIME_BALL);
        slimeballItem(ModItems.TUFF_SLIME_BALL);
        slimeballItem(ModItems.DRIPSTONE_SLIME_BALL);
        slimeballItem(ModItems.PRISMARINE_SLIME_BALL);
        slimeballItem(ModItems.MAGMA_SLIME_BALL);
        slimeballItem(ModItems.OBSIDIAN_SLIME_BALL);
        slimeballItem(ModItems.NETHERRACK_SLIME_BALL);
        slimeballItem(ModItems.SOUL_SAND_SLIME_BALL);
        slimeballItem(ModItems.SOUL_SOIL_SLIME_BALL);
        slimeballItem(ModItems.BLACKSTONE_SLIME_BALL);
        slimeballItem(ModItems.BASALT_SLIME_BALL);
        slimeballItem(ModItems.ENDSTONE_SLIME_BALL);
        slimeballItem(ModItems.QUARTZ_SLIME_BALL);
        slimeballItem(ModItems.GLOWSTONE_SLIME_BALL);
        slimeballItem(ModItems.AMETHYST_SLIME_BALL);
        slimeballItem(ModItems.BROWN_MUSHROOM_SLIME_BALL);
        slimeballItem(ModItems.RED_MUSHROOM_SLIME_BALL);
        slimeballItem(ModItems.CACTUS_SLIME_BALL);
        slimeballItem(ModItems.COAL_SLIME_BALL);
        slimeballItem(ModItems.GRAVEL_SLIME_BALL);

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
        bucketItem(ModFluids.MOLTEN_ANDESITE_BUCKET);
        bucketItem(ModFluids.MOLTEN_SNOW_BUCKET);
        bucketItem(ModFluids.MOLTEN_ICE_BUCKET);
        bucketItem(ModFluids.MOLTEN_MUD_BUCKET);
        bucketItem(ModFluids.MOLTEN_CLAY_BUCKET);
        bucketItem(ModFluids.MOLTEN_RED_SAND_BUCKET);
        bucketItem(ModFluids.MOLTEN_MOSS_BUCKET);
        bucketItem(ModFluids.MOLTEN_DEEPSLATE_BUCKET);
        bucketItem(ModFluids.MOLTEN_GRANITE_BUCKET);
        bucketItem(ModFluids.MOLTEN_DIORITE_BUCKET);
        bucketItem(ModFluids.MOLTEN_CALCITE_BUCKET);
        bucketItem(ModFluids.MOLTEN_TUFF_BUCKET);
        bucketItem(ModFluids.MOLTEN_DRIPSTONE_BUCKET);
        bucketItem(ModFluids.MOLTEN_PRISMARINE_BUCKET);
        bucketItem(ModFluids.MOLTEN_MAGMA_BUCKET);
        bucketItem(ModFluids.MOLTEN_OBSIDIAN_BUCKET);
        bucketItem(ModFluids.MOLTEN_NETHERRACK_BUCKET);
        bucketItem(ModFluids.MOLTEN_SOUL_SAND_BUCKET);
        bucketItem(ModFluids.MOLTEN_SOUL_SOIL_BUCKET);
        bucketItem(ModFluids.MOLTEN_BLACKSTONE_BUCKET);
        bucketItem(ModFluids.MOLTEN_BASALT_BUCKET);
        bucketItem(ModFluids.MOLTEN_ENDSTONE_BUCKET);
        bucketItem(ModFluids.MOLTEN_QUARTZ_BUCKET);
        bucketItem(ModFluids.MOLTEN_GLOWSTONE_BUCKET);
        bucketItem(ModFluids.MOLTEN_AMETHYST_BUCKET);
        bucketItem(ModFluids.MOLTEN_BROWN_MUSHROOM_BUCKET);
        bucketItem(ModFluids.MOLTEN_RED_MUSHROOM_BUCKET);
        bucketItem(ModFluids.MOLTEN_CACTUS_BUCKET);
        bucketItem(ModFluids.MOLTEN_COAL_BUCKET);
        bucketItem(ModFluids.MOLTEN_GRAVEL_BUCKET);

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
        withExistingParent(ModItems.ANDESITE_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.SNOW_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.ICE_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.MUD_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.CLAY_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.RED_SAND_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.MOSS_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.DEEPSLATE_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.GRANITE_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.DIORITE_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.CALCITE_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.TUFF_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.DRIPSTONE_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.PRISMARINE_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.MAGMA_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.OBSIDIAN_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.NETHERRACK_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.SOUL_SAND_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.SOUL_SOIL_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.BLACKSTONE_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.BASALT_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.ENDSTONE_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.QUARTZ_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.GLOWSTONE_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.AMETHYST_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.BROWN_MUSHROOM_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.RED_MUSHROOM_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.CACTUS_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.COAL_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.GRAVEL_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
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
