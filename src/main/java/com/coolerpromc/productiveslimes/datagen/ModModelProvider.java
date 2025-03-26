package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.block.custom.CableBlock;
import com.coolerpromc.productiveslimes.block.custom.SlimeBlock;
import com.coolerpromc.productiveslimes.datagen.template.ModModelTemplates;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.item.custom.BucketItem;
import com.coolerpromc.productiveslimes.item.custom.DnaItem;
import com.coolerpromc.productiveslimes.item.custom.SlimeballItem;
import com.coolerpromc.productiveslimes.item.custom.SpawnEggItem;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import com.coolerpromc.productiveslimes.util.FluidTankSpecialRenderer;
import com.coolerpromc.productiveslimes.util.SlimeItemTint;
import com.mojang.math.Quadrant;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.renderer.block.model.VariantMutator;
import net.minecraft.client.renderer.item.BlockModelWrapper;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Collections;
import java.util.List;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, ProductiveSlimes.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        registerBlockModels(blockModels);
        registerItemModels(itemModels);
    }

    private void registerBlockModels(BlockModelGenerators blockModels){
        oppositeHorizontalBlockWithExistingBlockModel(blockModels, ModBlocks.MELTING_STATION.get(), "melting_station");
        oppositeHorizontalBlockWithExistingBlockModel(blockModels, ModBlocks.LIQUID_SOLIDING_STATION.get(), "soliding_station");
        oppositeHorizontalBlockWithExistingBlockModel(blockModels, ModBlocks.ENERGY_GENERATOR.get(), "energy_generator");
        oppositeHorizontalBlockWithExistingBlockModel(blockModels, ModBlocks.DNA_EXTRACTOR.get(), "dna_extractor");
        oppositeHorizontalBlockWithExistingBlockModel(blockModels, ModBlocks.DNA_SYNTHESIZER.get(), "dna_synthesizer");
        oppositeHorizontalBlockWithExistingBlockModel(blockModels, ModBlocks.SLIME_NEST.get(), "slime_nest");
        oppositeHorizontalBlockWithExistingBlockModel(blockModels, ModBlocks.SLIMEBALL_COLLECTOR.get(), "slimeball_collector");
        horizontalBlockWithExistingBlockAndItemModel(blockModels, ModBlocks.SLIME_SQUEEZER.get(), "slime_squeezer");
        fluidTank(blockModels, ModBlocks.FLUID_TANK.get());
        cableBlock(blockModels, ModBlocks.CABLE.get(), "cable_core", "cable_part");

        simpleBlockWithExistingModel(blockModels, ModBlocks.SQUEEZER.get());
        simpleBlockWithExistingModel(blockModels, ModBlocks.SLIMY_GRASS_BLOCK.get());

        simpleBlock(blockModels, ModBlocks.SLIMY_DIRT.get());
        blockWithSlab(blockModels, ModBlocks.SLIMY_STONE.get(), ModBlocks.SLIMY_STONE_SLAB.get());
        simpleBlock(blockModels, ModBlocks.SLIMY_DEEPSLATE.get());
        blockWithSlab(blockModels, ModBlocks.SLIMY_COBBLESTONE.get(), ModBlocks.SLIMY_COBBLESTONE_SLAB.get());
        blockWithSlab(blockModels, ModBlocks.SLIMY_COBBLED_DEEPSLATE.get(), ModBlocks.SLIMY_COBBLED_DEEPSLATE_SLAB.get());

        logBlock(blockModels, ModBlocks.SLIMY_LOG.get(), ModBlocks.SLIMY_WOOD.get());
        logBlock(blockModels, ModBlocks.STRIPPED_SLIMY_LOG.get(), ModBlocks.STRIPPED_SLIMY_WOOD.get());

        blockWithSlab(blockModels, ModBlocks.SLIMY_PLANKS.get(), ModBlocks.SLIMY_SLAB.get());

        leavesBlock(blockModels, ModBlocks.SLIMY_LEAVES.get());
        saplingBlock(blockModels, ModBlocks.SLIMY_SAPLING.get());

        stairsBlock(blockModels, ModBlocks.SLIMY_STAIRS.get(), ModBlocks.SLIMY_PLANKS.get());
        pressurePlateBlock(blockModels, ModBlocks.SLIMY_PRESSURE_PLATE.get(), ModBlocks.SLIMY_PLANKS.get());
        buttonBlock(blockModels, ModBlocks.SLIMY_BUTTON.get(), ModBlocks.SLIMY_PLANKS.get());
        fenceBlock(blockModels, ModBlocks.SLIMY_FENCE.get(), ModBlocks.SLIMY_PLANKS.get());
        fenceGateBlock(blockModels, ModBlocks.SLIMY_FENCE_GATE.get(), ModBlocks.SLIMY_PLANKS.get());
        trapdoorBlockWithRenderType(blockModels, ModBlocks.SLIMY_TRAPDOOR.get());
        doorBlockWithRenderType(blockModels, ModBlocks.SLIMY_DOOR.get());

        stairsBlock(blockModels, ModBlocks.SLIMY_STONE_STAIRS.get(), ModBlocks.SLIMY_STONE.get());
        pressurePlateBlock(blockModels, ModBlocks.SLIMY_STONE_PRESSURE_PLATE.get(), ModBlocks.SLIMY_STONE.get());
        buttonBlock(blockModels, ModBlocks.SLIMY_STONE_BUTTON.get(), ModBlocks.SLIMY_STONE.get());

        stairsBlock(blockModels, ModBlocks.SLIMY_COBBLESTONE_STAIRS.get(), ModBlocks.SLIMY_COBBLESTONE.get());
        wallBlock(blockModels, ModBlocks.SLIMY_COBBLESTONE_WALL.get(), ModBlocks.SLIMY_COBBLESTONE.get());

        stairsBlock(blockModels, ModBlocks.SLIMY_COBBLED_DEEPSLATE_STAIRS.get(), ModBlocks.SLIMY_COBBLED_DEEPSLATE.get());
        wallBlock(blockModels, ModBlocks.SLIMY_COBBLED_DEEPSLATE_WALL.get(), ModBlocks.SLIMY_COBBLED_DEEPSLATE.get());

        slimeBlock(blockModels, ModBlocks.ENERGY_SLIME_BLOCK.get());

        for (Tier tier : Tier.values()){
            ModTiers tiers = ModTierLists.getTierByName(tier);
            slimeBlock(blockModels, ModTierLists.getBlockByName(tiers.name()).get());
            fluidBlock(blockModels, ModTierLists.getLiquidBlockByName(tiers.name()).get());
        }
    }

    private void registerItemModels(ItemModelGenerators itemModels){
        simpleItem(itemModels, ModItems.GUIDEBOOK.get());
        simpleItem(itemModels, ModItems.ENERGY_MULTIPLIER_UPGRADE.get());
        simpleItem(itemModels, ModItems.SLIMEBALL_FRAGMENT.get());
        simpleItem(itemModels, ModItems.SLIME_NEST_SPEED_UPGRADE_1.get());
        simpleItem(itemModels, ModItems.SLIME_NEST_SPEED_UPGRADE_2.get());

        slimeballItem(itemModels, ModItems.ENERGY_SLIME_BALL);
        dnaItem(itemModels, ModItems.SLIME_DNA);
        spawnEggItem(itemModels, ModItems.ENERGY_SLIME_SPAWN_EGG);

        slimeItem(itemModels, ModItems.SLIME_ITEM.get());

        for (Tier tier : Tier.values()){
            ModTiers tiers = ModTierLists.getTierByName(tier);

            slimeballItem(itemModels, ModTierLists.getSlimeballItemByName(tiers.name()));
            bucketItem(itemModels, ModTierLists.getBucketItemByName(tiers.name()));
            dnaItem(itemModels, ModTierLists.getDnaItemByName(tiers.name()));
            spawnEggItem(itemModels, ModTierLists.getSpawnEggItemByName(tiers.name()));
        }
    }

    // Block models
    private void simpleBlock(BlockModelGenerators blockModels, Block block){
        blockModels.new BlockFamilyProvider(TextureMapping.cube(blockLocation(getBlockName(block)))).fullBlock(block, ModelTemplates.CUBE_ALL);
    }

    private void simpleBlockWithExistingModel(BlockModelGenerators blockModels, Block block){
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, new MultiVariant(WeightedList.of(new Variant(blockLocation(getBlockName(block)))))));
    }

    private void fluidTank(BlockModelGenerators blockModels, Block block){
        oppositeHorizontalBlockWithExistingBlockModel(blockModels, block, "fluid_tank");
        blockModels.itemModelOutput.accept(block.asItem(), ItemModelUtils.specialModel(blockLocation("fluid_tank"), new FluidTankSpecialRenderer.Unbaked(blockLocation("fluid_tank"))));
    }

    private void oppositeHorizontalBlockWithExistingBlockModel(BlockModelGenerators blockModels, Block block, String modelName) {
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, new MultiVariant(WeightedList.of(new Variant(blockLocation(modelName))))).with(oppositeHorizontalRotation()));
    }

    private void oppositeHorizontalBlockWithExistingBlockAndItemModel(BlockModelGenerators blockModels, Block block, String modelName) {
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, new MultiVariant(WeightedList.of(new Variant(blockLocation(modelName))))).with(oppositeHorizontalRotation()));
        blockModels.itemModelOutput.accept(block.asItem(), new BlockModelWrapper.Unbaked(itemLocation(modelName), Collections.emptyList()));
    }

    private void horizontalBlockWithExistingBlockModel(BlockModelGenerators blockModels, Block block, String modelName) {
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, new MultiVariant(WeightedList.of(new Variant(blockLocation(modelName))))).with(horizontalRotation()));
    }

    private void horizontalBlockWithExistingBlockAndItemModel(BlockModelGenerators blockModels, Block block, String modelName) {
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, new MultiVariant(WeightedList.of(new Variant(blockLocation(modelName))))).with(horizontalRotation()));
        blockModels.itemModelOutput.accept(block.asItem(), new BlockModelWrapper.Unbaked(itemLocation(modelName), Collections.emptyList()));
    }

    private void fluidBlock(BlockModelGenerators blockModels, Block block){
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, new MultiVariant(WeightedList.of(new Variant(mcLocation("block/water"))))));
    }

    private void blockWithSlab(BlockModelGenerators blockModels, Block block, Block slab){
        ResourceLocation texture = blockLocation(getBlockName(block));
        blockModels.new BlockFamilyProvider(TextureMapping.cube(texture)
                .put(TextureSlot.BOTTOM, texture)
                .put(TextureSlot.TOP, texture)
                .put(TextureSlot.SIDE, texture)
        ).fullBlock(block, ModelTemplates.CUBE_ALL).slab(slab);
    }

    private void logBlock(BlockModelGenerators blockModels, Block block, Block wood){
        blockModels.woodProvider(block).logWithHorizontal(block).wood(wood);
    }

    private void leavesBlock(BlockModelGenerators blockModels, Block block){
        blockModels.createTintedLeaves(block, TexturedModel.LEAVES, -1);
    }

    private void saplingBlock(BlockModelGenerators blockModels, Block block){
        blockModels.registerSimpleItemModel(block.asItem(), BlockModelGenerators.PlantType.NOT_TINTED.createItemModel(blockModels, block));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, new MultiVariant(WeightedList.of(new Variant(ModelTemplates.CROSS.extend().renderType("cutout").build().create(block, TextureMapping.cross(block), blockModels.modelOutput))))));
    }

    private void cableBlock(BlockModelGenerators blockModels, Block block, String core, String part){
        blockModels.blockStateOutput.accept(cablePart(block, blockLocation(core), blockLocation(part)));
        blockModels.registerSimpleItemModel(block, blockLocation(core));
    }

    private void slimeBlock(BlockModelGenerators blockModels, SlimeBlock block){
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, new MultiVariant(WeightedList.of(new Variant(blockLocation("template_slime_block"))))));
        blockModels.registerSimpleTintedItemModel(block, blockLocation("template_slime_block"), ItemModelUtils.constantTint(block.getColor()));
    }

    private void pressurePlateBlock(BlockModelGenerators blockModels, Block block, Block materialBlock){
        blockModels.new BlockFamilyProvider(TextureMapping.defaultTexture(materialBlock)).pressurePlate(block);
    }

    private void stairsBlock(BlockModelGenerators blockModels, Block block, Block materialBlock){
        ResourceLocation texture = blockLocation(getBlockName(materialBlock));
        blockModels.new BlockFamilyProvider(TextureMapping.defaultTexture(texture)
                .put(TextureSlot.BOTTOM, texture)
                .put(TextureSlot.TOP, texture)
                .put(TextureSlot.SIDE, texture)
        ).stairs(block);
    }

    private void buttonBlock(BlockModelGenerators blockModels, Block block, Block materialBlock){
        blockModels.new BlockFamilyProvider(TextureMapping.defaultTexture(materialBlock)).button(block);
    }

    private void fenceBlock(BlockModelGenerators blockModels, Block block, Block materialBlock){
        blockModels.new BlockFamilyProvider(TextureMapping.defaultTexture(materialBlock)).fence(block);
    }

    private void fenceGateBlock(BlockModelGenerators blockModels, Block block, Block materialBlock){
        blockModels.new BlockFamilyProvider(TextureMapping.defaultTexture(materialBlock)).fenceGate(block);
    }

    private void trapdoorBlockWithRenderType(BlockModelGenerators blockModels, Block block){
        TextureMapping texturemapping = TextureMapping.defaultTexture(block);
        ResourceLocation texture = ModModelTemplates.TRAPDOOR_TOP.create(block, texturemapping, blockModels.modelOutput);
        ResourceLocation texture2 = ModModelTemplates.TRAPDOOR_BOTTOM.create(block, texturemapping, blockModels.modelOutput);
        MultiVariant resourcelocation = new MultiVariant(WeightedList.of(new Variant(texture)));
        MultiVariant resourcelocation1 = new MultiVariant(WeightedList.of(new Variant(texture2)));
        MultiVariant resourcelocation2 = new MultiVariant(WeightedList.of(new Variant(ModModelTemplates.TRAPDOOR_OPEN.create(block, texturemapping, blockModels.modelOutput))));

        blockModels.blockStateOutput.accept(BlockModelGenerators.createTrapdoor(block, resourcelocation, resourcelocation1, resourcelocation2));
        blockModels.registerSimpleItemModel(block, texture2);
    }

    private void doorBlockWithRenderType(BlockModelGenerators blockModels, Block block){
        TextureMapping texturemapping = TextureMapping.door(block);
        ResourceLocation resourcelocation = ModelTemplates.DOOR_BOTTOM_LEFT.create(block, texturemapping, blockModels.modelOutput);
        ResourceLocation resourcelocation1 = ModelTemplates.DOOR_BOTTOM_LEFT_OPEN.create(block, texturemapping, blockModels.modelOutput);
        ResourceLocation resourcelocation2 = ModelTemplates.DOOR_BOTTOM_RIGHT.create(block, texturemapping, blockModels.modelOutput);
        ResourceLocation resourcelocation3 = ModelTemplates.DOOR_BOTTOM_RIGHT_OPEN.create(block, texturemapping, blockModels.modelOutput);
        ResourceLocation resourcelocation4 = ModModelTemplates.DOOR_TOP_LEFT.create(block, texturemapping, blockModels.modelOutput);
        ResourceLocation resourcelocation5 = ModModelTemplates.DOOR_TOP_LEFT_OPEN.create(block, texturemapping, blockModels.modelOutput);
        ResourceLocation resourcelocation6 = ModModelTemplates.DOOR_TOP_RIGHT.create(block, texturemapping, blockModels.modelOutput);
        ResourceLocation resourcelocation7 = ModModelTemplates.DOOR_TOP_RIGHT_OPEN.create(block, texturemapping, blockModels.modelOutput);

        blockModels.registerSimpleFlatItemModel(block.asItem());
        blockModels.blockStateOutput
                .accept(
                        BlockModelGenerators.createDoor(
                                block,
                                new MultiVariant(WeightedList.of(new Variant(resourcelocation))),
                                new MultiVariant(WeightedList.of(new Variant(resourcelocation1))),
                                new MultiVariant(WeightedList.of(new Variant(resourcelocation2))),
                                new MultiVariant(WeightedList.of(new Variant(resourcelocation3))),
                                new MultiVariant(WeightedList.of(new Variant(resourcelocation4))),
                                new MultiVariant(WeightedList.of(new Variant(resourcelocation5))),
                                new MultiVariant(WeightedList.of(new Variant(resourcelocation6))),
                                new MultiVariant(WeightedList.of(new Variant(resourcelocation7)))
                        )
                );
    }

    private void wallBlock(BlockModelGenerators blockModels, Block block, Block materialBlock){
        blockModels.new BlockFamilyProvider(TextureMapping.defaultTexture(materialBlock).put(TextureSlot.WALL, blockLocation(getBlockName(materialBlock)))).wall(block);
    }

    // Item models
    private void simpleItem(ItemModelGenerators itemModels, Item item){
        TextureMapping textureMapping = new TextureMapping().put(TextureSlot.LAYER0, itemLocation(getItemName(item)));
        itemModels.itemModelOutput.accept(item, new BlockModelWrapper.Unbaked(ModelTemplates.FLAT_ITEM.create(item, textureMapping, itemModels.modelOutput), Collections.emptyList()));
    }

    private void slimeballItem(ItemModelGenerators itemModels, DeferredItem<Item> item){
        TextureMapping textureMapping = new TextureMapping().put(TextureSlot.LAYER0, itemLocation("template_slimeball"));
        itemModels.itemModelOutput.accept(item.get(), new BlockModelWrapper.Unbaked(ModelTemplates.FLAT_ITEM.create(item.get(), textureMapping, itemModels.modelOutput), List.of(ItemModelUtils.constantTint(((SlimeballItem)item.get()).getColor()))));
    }

    private void dnaItem(ItemModelGenerators itemModels, DeferredItem<Item> item){
        TextureMapping textureMapping = new TextureMapping().put(TextureSlot.LAYER0, itemLocation("template_dna"));
        itemModels.itemModelOutput.accept(item.get(), new BlockModelWrapper.Unbaked(ModelTemplates.FLAT_ITEM.create(item.get(), textureMapping, itemModels.modelOutput), List.of(ItemModelUtils.constantTint(((DnaItem)item.get()).getColor()))));
    }

    private void bucketItem(ItemModelGenerators itemModels, DeferredItem<Item> item){
        TextureMapping textureMapping = new TextureMapping().put(TextureSlot.LAYER0, itemLocation("bucket")).put(TextureSlot.LAYER1, itemLocation("bucket_fluid"));
        itemModels.itemModelOutput.accept(item.get(), new BlockModelWrapper.Unbaked(ModelTemplates.TWO_LAYERED_ITEM.create(item.get(), textureMapping, itemModels.modelOutput), List.of(ItemModelUtils.constantTint(-1), ItemModelUtils.constantTint(((BucketItem)item.get()).getColor()))));
    }

    private void spawnEggItem(ItemModelGenerators itemModels, DeferredItem<Item> item) {
        SpawnEggItem spawnEggItem = (SpawnEggItem) item.get();
        itemModels.itemModelOutput.accept(spawnEggItem, ItemModelUtils.tintedModel(itemLocation("template_slime_spawn_egg"), ItemModelUtils.constantTint(spawnEggItem.getColor())));
    }

    private void slimeItem(ItemModelGenerators itemModels, Item item){
        ResourceLocation model = itemLocation("slime_item");
        itemModels.itemModelOutput.accept(item, new BlockModelWrapper.Unbaked(model, List.of(new SlimeItemTint(-1), new SlimeItemTint(-1))));
    }

    // Helper methods
    private PropertyDispatch<VariantMutator> oppositeHorizontalRotation(){
        return PropertyDispatch.modify(BlockStateProperties.HORIZONTAL_FACING)
                .select(Direction.SOUTH, BlockModelGenerators.NOP)
                .select(Direction.WEST, BlockModelGenerators.Y_ROT_90)
                .select(Direction.NORTH, BlockModelGenerators.Y_ROT_180)
                .select(Direction.EAST, BlockModelGenerators.Y_ROT_270);
    }

    private PropertyDispatch<VariantMutator> horizontalRotation(){
        return PropertyDispatch.modify(BlockStateProperties.HORIZONTAL_FACING)
                .select(Direction.SOUTH, BlockModelGenerators.Y_ROT_180)
                .select(Direction.WEST, BlockModelGenerators.Y_ROT_270)
                .select(Direction.NORTH, BlockModelGenerators.NOP)
                .select(Direction.EAST, BlockModelGenerators.Y_ROT_90);
    }

    private MultiPartGenerator cablePart(Block block, ResourceLocation coreModelLoc, ResourceLocation partModelLoc){
        return MultiPartGenerator.multiPart(block)
                .with(new MultiVariant(WeightedList.of(new Variant(coreModelLoc))))
                .with(new ConditionBuilder().term(CableBlock.UP, true), variantRotation(partModelLoc, VariantMutator.X_ROT.withValue(Quadrant.R270)))
                .with(new ConditionBuilder().term(CableBlock.DOWN, true), variantRotation(partModelLoc, VariantMutator.X_ROT.withValue(Quadrant.R90)))
                .with(new ConditionBuilder().term(CableBlock.NORTH, true), variantRotation(partModelLoc, VariantMutator.Y_ROT.withValue(Quadrant.R0)))
                .with(new ConditionBuilder().term(CableBlock.SOUTH, true), variantRotation(partModelLoc, VariantMutator.Y_ROT.withValue(Quadrant.R180)))
                .with(new ConditionBuilder().term(CableBlock.EAST, true), variantRotation(partModelLoc, VariantMutator.Y_ROT.withValue(Quadrant.R90)))
                .with(new ConditionBuilder().term(CableBlock.WEST, true), variantRotation(partModelLoc, VariantMutator.Y_ROT.withValue(Quadrant.R270)));
    }

    private MultiVariant variantRotation(ResourceLocation modelLoc, VariantMutator rot){
        return new MultiVariant(WeightedList.of(new Variant(modelLoc).with(VariantMutator.UV_LOCK.withValue(false)).with(rot)));
    }

    private String getBlockName(Block block){
        ResourceLocation location = BuiltInRegistries.BLOCK.getKey(block);
        return location.getPath();
    }

    private String getItemName(Item item){
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(item);
        return location.getPath();
    }

    private ResourceLocation blockLocation(String modelName){
        return ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "block/" + modelName);
    }

    private ResourceLocation itemLocation(String modelName){
        return ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "item/" + modelName);
    }
}
