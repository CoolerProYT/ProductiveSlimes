package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.block.custom.CableBlock;
import com.coolerpromc.productiveslimes.block.custom.SlimeBlock;
import com.coolerpromc.productiveslimes.datagen.model.template.ModModelTemplates;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.item.custom.BucketItem;
import com.coolerpromc.productiveslimes.item.custom.DnaItem;
import com.coolerpromc.productiveslimes.item.custom.SlimeballItem;
import com.coolerpromc.productiveslimes.item.custom.SpawnEggItem;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.ModTier;
import com.coolerpromc.productiveslimes.tier.Tier;
import com.coolerpromc.productiveslimes.datagen.model.special.FluidTankSpecialRenderer;
import com.coolerpromc.productiveslimes.datagen.model.tint.SlimeItemTint;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.BlockModelWrapper;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
        BlockModelGenerators.BlockFamilyProvider slimyWoodSet = blockModels.family(ModBlocks.SLIMY_PLANKS.get());
        BlockModelGenerators.BlockFamilyProvider slimyStoneSet = blockModels.family(ModBlocks.SLIMY_STONE.get());
        BlockModelGenerators.BlockFamilyProvider slimyCobblestoneSet = blockModels.family(ModBlocks.SLIMY_COBBLESTONE.get());
        BlockModelGenerators.BlockFamilyProvider slimyCobbledDeepslateSet = blockModels.family(ModBlocks.SLIMY_COBBLED_DEEPSLATE.get());

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
        simpleBlock(blockModels, ModBlocks.SLIMY_DEEPSLATE.get());

        logBlock(blockModels, ModBlocks.SLIMY_LOG.get(), ModBlocks.SLIMY_WOOD.get());
        logBlock(blockModels, ModBlocks.STRIPPED_SLIMY_LOG.get(), ModBlocks.STRIPPED_SLIMY_WOOD.get());

        slimyStoneSet.slab(ModBlocks.SLIMY_STONE_SLAB.get());
        slimyStoneSet.stairs(ModBlocks.SLIMY_STONE_STAIRS.get());
        slimyStoneSet.pressurePlate(ModBlocks.SLIMY_STONE_PRESSURE_PLATE.get());
        slimyStoneSet.button(ModBlocks.SLIMY_STONE_BUTTON.get());

        slimyCobblestoneSet.slab(ModBlocks.SLIMY_COBBLESTONE_SLAB.get());
        slimyCobblestoneSet.stairs(ModBlocks.SLIMY_COBBLESTONE_STAIRS.get());
        slimyCobblestoneSet.wall(ModBlocks.SLIMY_COBBLESTONE_WALL.get());

        slimyCobbledDeepslateSet.slab(ModBlocks.SLIMY_COBBLED_DEEPSLATE_SLAB.get());
        slimyCobbledDeepslateSet.stairs(ModBlocks.SLIMY_COBBLED_DEEPSLATE_STAIRS.get());
        slimyCobbledDeepslateSet.wall(ModBlocks.SLIMY_COBBLED_DEEPSLATE_WALL.get());

        slimyWoodSet.slab(ModBlocks.SLIMY_SLAB.get());
        slimyWoodSet.stairs(ModBlocks.SLIMY_STAIRS.get());
        slimyWoodSet.pressurePlate(ModBlocks.SLIMY_PRESSURE_PLATE.get());
        slimyWoodSet.button(ModBlocks.SLIMY_BUTTON.get());
        slimyWoodSet.fence(ModBlocks.SLIMY_FENCE.get());
        slimyWoodSet.fenceGate(ModBlocks.SLIMY_FENCE_GATE.get());

        leavesBlock(blockModels, ModBlocks.SLIMY_LEAVES.get());
        saplingBlock(blockModels, ModBlocks.SLIMY_SAPLING.get());

        trapdoorBlockWithRenderType(blockModels, ModBlocks.SLIMY_TRAPDOOR.get());
        doorBlockWithRenderType(blockModels, ModBlocks.SLIMY_DOOR.get());

        // Slimes Blocks
        slimeBlock(blockModels, ModBlocks.ENERGY_SLIME_BLOCK.get());

        for (Tier tier : Tier.values()){
            ModTier tiers = ModTiers.getTierByName(tier);
            slimeBlock(blockModels, ModTiers.getBlockByName(tiers.name()).get());
            fluidBlock(blockModels, ModTiers.getLiquidBlockByName(tiers.name()).get());
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
            ModTier tiers = ModTiers.getTierByName(tier);

            slimeballItem(itemModels, ModTiers.getSlimeballItemByName(tiers.name()));
            bucketItem(itemModels, ModTiers.getBucketItemByName(tiers.name()));
            dnaItem(itemModels, ModTiers.getDnaItemByName(tiers.name()));
            spawnEggItem(itemModels, ModTiers.getSpawnEggItemByName(tiers.name()));
        }
    }

    // Block models
    private void simpleBlock(BlockModelGenerators blockModels, Block block){
        blockModels.new BlockFamilyProvider(TextureMapping.cube(blockLocation(getBlockName(block)))).fullBlock(block, ModelTemplates.CUBE_ALL);
    }

    private void simpleBlockWithExistingModel(BlockModelGenerators blockModels, Block block){
        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, blockLocation(getBlockName(block)))));
    }

    private void fluidTank(BlockModelGenerators blockModels, Block block){
        oppositeHorizontalBlockWithExistingBlockModel(blockModels, block, "fluid_tank");
        blockModels.itemModelOutput.accept(block.asItem(), ItemModelUtils.specialModel(blockLocation("fluid_tank"), new FluidTankSpecialRenderer.Unbaked(blockLocation("fluid_tank"))));
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

    private void fluidBlock(BlockModelGenerators blockModels, Block block){
        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, mcLocation("block/water"))));
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

    private void slimeBlock(BlockModelGenerators blockModels, SlimeBlock block){
        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, blockLocation("template_slime_block"))));
        blockModels.registerSimpleTintedItemModel(block, blockLocation("template_slime_block"), ItemModelUtils.constantTint(block.getColor()));
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

    private void bucketItem(ItemModelGenerators itemModels, DeferredItem<BucketItem> item){
        TextureMapping textureMapping = new TextureMapping().put(TextureSlot.LAYER0, itemLocation("bucket")).put(TextureSlot.LAYER1, itemLocation("bucket_fluid"));
        itemModels.itemModelOutput.accept(item.get(), new BlockModelWrapper.Unbaked(ModelTemplates.TWO_LAYERED_ITEM.create(item.get(), textureMapping, itemModels.modelOutput), List.of(ItemModelUtils.constantTint(-1), ItemModelUtils.constantTint(((BucketItem)item.get()).getColor()))));
    }

    private void spawnEggItem(ItemModelGenerators itemModels, DeferredItem<SpawnEggItem> item) {
        ResourceLocation model =itemLocation("template_slime_spawn_egg");
        itemModels.itemModelOutput.accept(item.get(), ItemModelUtils.tintedModel(model, ItemModelUtils.constantTint(item.get().getColor())));
    }

    private void slimeItem(ItemModelGenerators itemModels, Item item){
        itemModels.itemModelOutput.accept(item, new BlockModelWrapper.Unbaked(itemLocation("slime_item"), List.of(new SlimeItemTint(-1), new SlimeItemTint(-1))));
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
