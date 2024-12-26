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
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.BlockModelWrapper;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
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
        horizontalBlockWithExistingBlockAndItemModel(blockModels, ModBlocks.SLIME_SQUEEZER.get(), "slime_squeezer");
        oppositeHorizontalBlockWithExistingBlockModel(blockModels, ModBlocks.FLUID_TANK.get(), "fluid_tank");
        cableBlock(blockModels, ModBlocks.CABLE.get(), "cable_core", "cable_part");

        simpleBlockWithExistingModel(blockModels, ModBlocks.SQUEEZER.get());
        simpleBlockWithExistingModel(blockModels, ModBlocks.SLIMY_GRASS_BLOCK.get());

        block(blockModels, ModBlocks.SLIMY_DIRT.get());
        blockWithSlab(blockModels, ModBlocks.SLIMY_STONE.get(), ModBlocks.SLIMY_STONE_SLAB.get());
        block(blockModels, ModBlocks.SLIMY_DEEPSLATE.get());
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

        registerSlimeBlock(blockModels, ModBlocks.ENERGY_SLIME_BLOCK.get());

        for (Tier tier : Tier.values()){
            ModTiers tiers = ModTierLists.getTierByName(tier);
            registerSlimeBlock(blockModels, ModTierLists.getBlockByName(tiers.name()).get());
            fluidBlock(blockModels, ModTierLists.getLiquidBlockByName(tiers.name()).get());
        }
    }

    private void registerItemModels(ItemModelGenerators itemModels){
        simpleItem(itemModels, ModItems.GUIDEBOOK.get());
        simpleItem(itemModels, ModItems.ENERGY_MULTIPLIER_UPGRADE.get());
        simpleItem(itemModels, ModItems.SLIMEBALL_FRAGMENT.get());

        slimeballItem(itemModels, ModItems.ENERGY_SLIME_BALL);
        dnaItem(itemModels, ModItems.SLIME_DNA);
        spawnEggItem(itemModels, ModItems.ENERGY_SLIME_SPAWN_EGG);

        for (Tier tier : Tier.values()){
            ModTiers tiers = ModTierLists.getTierByName(tier);

            slimeballItem(itemModels, ModTierLists.getSlimeballItemByName(tiers.name()));
            bucketItem(itemModels, ModTierLists.getBucketItemByName(tiers.name()));
            dnaItem(itemModels, ModTierLists.getDnaItemByName(tiers.name()));
            spawnEggItem(itemModels, ModTierLists.getSpawnEggItemByName(tiers.name()));
        }
    }

    private void oppositeHorizontalBlockWithExistingBlockModel(BlockModelGenerators blockModels, Block block, String modelName) {
        blockModels.blockStateOutput.accept(oppositeHorizontalRotation(block, blockLocation(modelName)));
    }

    private void oppositeHorizontalBlockWithExistingBlockAndItemModel(BlockModelGenerators blockModels, Block block, String modelName) {
        blockModels.blockStateOutput.accept(oppositeHorizontalRotation(block, blockLocation(modelName)));
        blockModels.itemModelOutput.accept(block.asItem(), new BlockModelWrapper.Unbaked(itemLocation(modelName), Collections.emptyList()));
    }

    private void horizontalBlockWithExistingBlockModel(BlockModelGenerators blockModels, Block block, String modelName) {
        blockModels.blockStateOutput.accept(horizontalRotation(block, blockLocation(modelName)));
    }

    private void horizontalBlockWithExistingBlockAndItemModel(BlockModelGenerators blockModels, Block block, String modelName) {
        blockModels.blockStateOutput.accept(horizontalRotation(block, blockLocation(modelName)));
        blockModels.itemModelOutput.accept(block.asItem(), new BlockModelWrapper.Unbaked(itemLocation(modelName), Collections.emptyList()));
    }

    private void simpleBlockWithExistingModel(BlockModelGenerators blockModels, Block block){
        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, blockLocation(getBlockName(block)))));
    }

    private void fluidBlock(BlockModelGenerators blockModels, Block block){
        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, mcLocation("block/water"))));
    }

    private void blockWithSlab(BlockModelGenerators blockModels, Block block, Block slab){
        ResourceLocation texture = blockLocation(getBlockName(block));
        blockModels.new BlockFamilyProvider(TextureMapping.cube(texture)
                .put(TextureSlot.BOTTOM, texture)
                .put(TextureSlot.TOP, texture)
                .put(TextureSlot.SIDE, texture)
        ).fullBlock(block, ModelTemplates.CUBE_ALL).slab(slab);
    }

    private void block(BlockModelGenerators blockModels, Block block){
        blockModels.new BlockFamilyProvider(TextureMapping.cube(blockLocation(getBlockName(block)))).fullBlock(block, ModelTemplates.CUBE_ALL);
    }

    private void logBlock(BlockModelGenerators blockModels, Block block, Block wood){
        blockModels.woodProvider(block).logWithHorizontal(block).wood(wood);
    }

    private void leavesBlock(BlockModelGenerators blockModels, Block block){
        blockModels.createTintedLeaves(block, TexturedModel.LEAVES, -1);
    }

    private void saplingBlock(BlockModelGenerators blockModels, Block block){
        blockModels.registerSimpleItemModel(block.asItem(), BlockModelGenerators.PlantType.NOT_TINTED.createItemModel(blockModels, block));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, ModelTemplates.CROSS.extend().renderType("cutout").build().create(block, TextureMapping.cross(block), blockModels.modelOutput)));
    }

    private void cableBlock(BlockModelGenerators blockModels, Block block, String core, String part){
        blockModels.blockStateOutput.accept(cablePart(block, blockLocation(core), blockLocation(part)));
        blockModels.registerSimpleItemModel(block, blockLocation(core));
    }

    private void registerSlimeBlock(BlockModelGenerators blockModels, SlimeBlock block){
        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, blockLocation("template_slime_block"))));
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
        ResourceLocation resourcelocation = ModModelTemplates.TRAPDOOR_TOP.create(block, texturemapping, blockModels.modelOutput);
        ResourceLocation resourcelocation1 = ModModelTemplates.TRAPDOOR_BOTTOM.create(block, texturemapping, blockModels.modelOutput);
        ResourceLocation resourcelocation2 = ModModelTemplates.TRAPDOOR_OPEN.create(block, texturemapping, blockModels.modelOutput);

        blockModels.blockStateOutput.accept(BlockModelGenerators.createTrapdoor(block, resourcelocation, resourcelocation1, resourcelocation2));
        blockModels.registerSimpleItemModel(block, resourcelocation1);
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
                                resourcelocation,
                                resourcelocation1,
                                resourcelocation2,
                                resourcelocation3,
                                resourcelocation4,
                                resourcelocation5,
                                resourcelocation6,
                                resourcelocation7
                        )
                );
    }

    private void wallBlock(BlockModelGenerators blockModels, Block block, Block materialBlock){
        blockModels.new BlockFamilyProvider(TextureMapping.defaultTexture(materialBlock).put(TextureSlot.WALL, blockLocation(getBlockName(materialBlock)))).wall(block);
    }

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
        itemModels.generateSpawnEgg(spawnEggItem, spawnEggItem.getBg(), spawnEggItem.getFg());
    }

    // Helper methods
    private MultiVariantGenerator oppositeHorizontalRotation(Block block, ResourceLocation modelLoc){
        return MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
                .select(Direction.SOUTH, Variant.variant().with(VariantProperties.MODEL, modelLoc))
                .select(Direction.WEST, Variant.variant().with(VariantProperties.MODEL, modelLoc).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.NORTH, Variant.variant().with(VariantProperties.MODEL, modelLoc).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.EAST, Variant.variant().with(VariantProperties.MODEL, modelLoc).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)));
    }

    private MultiVariantGenerator horizontalRotation(Block block, ResourceLocation modelLoc){
        return MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
                .select(Direction.SOUTH, Variant.variant().with(VariantProperties.MODEL, modelLoc).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, Variant.variant().with(VariantProperties.MODEL, modelLoc).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, Variant.variant().with(VariantProperties.MODEL, modelLoc))
                .select(Direction.EAST, Variant.variant().with(VariantProperties.MODEL, modelLoc).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)));
    }

    private MultiPartGenerator cablePart(Block block, ResourceLocation coreModelLoc, ResourceLocation partModelLoc){
        return MultiPartGenerator.multiPart(block)
                .with(Variant.variant().with(VariantProperties.MODEL, coreModelLoc))
                .with(Condition.condition().term(CableBlock.UP, true), variantRotation(partModelLoc, VariantProperties.X_ROT, VariantProperties.Rotation.R270))
                .with(Condition.condition().term(CableBlock.DOWN, true), variantRotation(partModelLoc, VariantProperties.X_ROT, VariantProperties.Rotation.R90))
                .with(Condition.condition().term(CableBlock.NORTH, true), variantRotation(partModelLoc, VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .with(Condition.condition().term(CableBlock.SOUTH, true), variantRotation(partModelLoc, VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .with(Condition.condition().term(CableBlock.EAST, true), variantRotation(partModelLoc, VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .with(Condition.condition().term(CableBlock.WEST, true), variantRotation(partModelLoc, VariantProperties.Y_ROT, VariantProperties.Rotation.R270));
    }

    private Variant variantRotation(ResourceLocation modelLoc, VariantProperty<VariantProperties.Rotation> rot, VariantProperties.Rotation rotation){
        return Variant.variant().with(VariantProperties.MODEL, modelLoc).with(VariantProperties.UV_LOCK, false).with(rot, rotation);
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
