package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.block.custom.CableBlock;
import com.coolerpromc.productiveslimes.block.custom.SlimeBlock;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.item.custom.BucketItem;
import com.coolerpromc.productiveslimes.item.custom.DnaItem;
import com.coolerpromc.productiveslimes.item.custom.SlimeballItem;
import com.coolerpromc.productiveslimes.item.custom.SpawnEggItem;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.model.MultiVariant;
import net.minecraft.client.renderer.item.BlockModelWrapper;
import net.minecraft.client.renderer.item.EmptyModel;
import net.minecraft.client.renderer.item.ItemModels;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Collections;
import java.util.List;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, ProductiveSlimes.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        simpleBlockWithVariants(blockModels, ModBlocks.MELTING_STATION.get(), "melting_station");
        simpleBlockWithVariants(blockModels, ModBlocks.LIQUID_SOLIDING_STATION.get(), "soliding_station");
        simpleBlockWithVariants(blockModels, ModBlocks.ENERGY_GENERATOR.get(), "energy_generator");
        simpleBlockWithVariants(blockModels, ModBlocks.DNA_EXTRACTOR.get(), "dna_extractor");
        simpleBlockWithVariants(blockModels, ModBlocks.DNA_SYNTHESIZER.get(), "dna_synthesizer");
        simpleBlockWithVariants(blockModels, ModBlocks.SLIME_SQUEEZER.get(), "slime_squeezer");
        simpleBlockWithVariants(blockModels, ModBlocks.FLUID_TANK.get(), "fluid_tank");
        cableBlock(blockModels, ModBlocks.CABLE.get());

        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(ModBlocks.SQUEEZER.get(), Variant.variant().with(VariantProperties.MODEL, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "block/squeezer"))));

        simpleBlock(blockModels, ModBlocks.SLIMY_GRASS_BLOCK.get());

        block(blockModels, ModBlocks.SLIMY_DIRT.get());
        blockAndSlab(blockModels, ModBlocks.SLIMY_STONE.get(), ModBlocks.SLIMY_STONE_SLAB.get());
        block(blockModels, ModBlocks.SLIMY_DEEPSLATE.get());
        blockAndSlab(blockModels, ModBlocks.SLIMY_COBBLESTONE.get(), ModBlocks.SLIMY_COBBLESTONE_SLAB.get());
        blockAndSlab(blockModels, ModBlocks.SLIMY_COBBLED_DEEPSLATE.get(), ModBlocks.SLIMY_COBBLED_DEEPSLATE_SLAB.get());

        logBlock(blockModels, ModBlocks.SLIMY_LOG.get(), ModBlocks.SLIMY_WOOD.get());
        logBlock(blockModels, ModBlocks.STRIPPED_SLIMY_LOG.get(), ModBlocks.STRIPPED_SLIMY_WOOD.get());

        blockAndSlab(blockModels, ModBlocks.SLIMY_PLANKS.get(), ModBlocks.SLIMY_SLAB.get());

        leavesBlock(blockModels, ModBlocks.SLIMY_LEAVES.get());
        saplingBlock(blockModels, ModBlocks.SLIMY_SAPLING.get());

        stairsBlock(blockModels, ModBlocks.SLIMY_STAIRS.get(), ModBlocks.SLIMY_PLANKS.get());
        pressurePlateBlock(blockModels, ModBlocks.SLIMY_PRESSURE_PLATE.get(), ModBlocks.SLIMY_PLANKS.get());
        buttonBlock(blockModels, ModBlocks.SLIMY_BUTTON.get(), ModBlocks.SLIMY_PLANKS.get());
        fenceBlock(blockModels, ModBlocks.SLIMY_FENCE.get(), ModBlocks.SLIMY_PLANKS.get());
        fenceGateBlock(blockModels, ModBlocks.SLIMY_FENCE_GATE.get(), ModBlocks.SLIMY_PLANKS.get());
        trapdoorBlockWithRenderType(blockModels, ModBlocks.SLIMY_TRAPDOOR.get(), ModBlocks.SLIMY_PLANKS.get());
        doorBlockWithRenderType(blockModels, ModBlocks.SLIMY_DOOR.get(), ModBlocks.SLIMY_PLANKS.get());

        stairsBlock(blockModels, ModBlocks.SLIMY_STONE_STAIRS.get(), ModBlocks.SLIMY_STONE.get());
        pressurePlateBlock(blockModels, ModBlocks.SLIMY_STONE_PRESSURE_PLATE.get(), ModBlocks.SLIMY_STONE.get());
        buttonBlock(blockModels, ModBlocks.SLIMY_STONE_BUTTON.get(), ModBlocks.SLIMY_STONE.get());

        stairsBlock(blockModels, ModBlocks.SLIMY_COBBLESTONE_STAIRS.get(), ModBlocks.SLIMY_COBBLESTONE.get());
        wallBlock(blockModels, ModBlocks.SLIMY_COBBLESTONE_WALL.get(), ModBlocks.SLIMY_COBBLESTONE.get());

        stairsBlock(blockModels, ModBlocks.SLIMY_COBBLED_DEEPSLATE_STAIRS.get(), ModBlocks.SLIMY_COBBLED_DEEPSLATE.get());
        wallBlock(blockModels, ModBlocks.SLIMY_COBBLED_DEEPSLATE_WALL.get(), ModBlocks.SLIMY_COBBLED_DEEPSLATE.get());

        registerSlimeBlock(blockModels, ModBlocks.ENERGY_SLIME_BLOCK.get(), "energy_slime_block");

        for (Tier tier : Tier.values()){
            ModTiers tiers = ModTierLists.getTierByName(tier);
            String textureName = tiers.name() + "_slime_block";
            registerSlimeBlock(blockModels, ModTierLists.getBlockByName(tiers.name()).get(), textureName);
            fluidBlock(blockModels, ModTierLists.getLiquidBlockByName(tiers.name()).get());
        }

        // Item
        simpleItem(itemModels, ModItems.GUIDEBOOK.get());
        simpleItem(itemModels, ModItems.ENERGY_MULTIPLIER_UPGRADE.get());
        simpleItem(itemModels, ModItems.SLIMEBALL_FRAGMENT.get());

        /*saplingItem(ModBlocks.SLIMY_SAPLING);
        buttonItem(ModBlocks.SLIMY_BUTTON, ModBlocks.SLIMY_PLANKS);
        fenceItem(ModBlocks.SLIMY_FENCE, ModBlocks.SLIMY_PLANKS);
        basicItem(ModBlocks.SLIMY_DOOR.asItem());
        buttonItem(ModBlocks.SLIMY_STONE_BUTTON, ModBlocks.SLIMY_STONE);
        wallItem(ModBlocks.SLIMY_COBBLESTONE_WALL, ModBlocks.SLIMY_COBBLESTONE);
        wallItem(ModBlocks.SLIMY_COBBLED_DEEPSLATE_WALL, ModBlocks.SLIMY_COBBLED_DEEPSLATE);*/

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

    private void simpleBlockWithVariants(BlockModelGenerators blockModels, Block block, String modelName) {
        ResourceLocation modelLoc = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "block/" + modelName);
        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
                .select(Direction.SOUTH, Variant.variant().with(VariantProperties.MODEL, modelLoc))
                .select(Direction.WEST, Variant.variant().with(VariantProperties.MODEL, modelLoc).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.NORTH, Variant.variant().with(VariantProperties.MODEL, modelLoc).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.EAST, Variant.variant().with(VariantProperties.MODEL, modelLoc).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void simpleBlock(BlockModelGenerators blockModels, Block block){
        ResourceLocation model = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "block/" + block.getDescriptionId());
        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model)));
    }

    private void fluidBlock(BlockModelGenerators blockModels, Block block){
        ResourceLocation model = ResourceLocation.fromNamespaceAndPath("minecraft", "block/water");
        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model)));
    }

    private void blockAndSlab(BlockModelGenerators blockModels, Block block, Block slab){
        BlockModelGenerators.BlockFamilyProvider provider = blockModels.new BlockFamilyProvider(TextureMapping.defaultTexture(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "block/slimy_planks"))
                .put(TextureSlot.BOTTOM, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "block/slimy_planks"))
                .put(TextureSlot.TOP, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "block/slimy_planks"))
                .put(TextureSlot.SIDE, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "block/slimy_planks"))
                .put(TextureSlot.ALL, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "block/" + block.getDescriptionId()))
        );

        provider.fullBlock(block, ModelTemplates.CUBE_ALL);
        provider.slab(slab);
    }

    private void block(BlockModelGenerators blockModels, Block block){
        BlockModelGenerators.BlockFamilyProvider provider = blockModels.new BlockFamilyProvider(TextureMapping.defaultTexture(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "block/" + block.getDescriptionId()))
                .put(TextureSlot.ALL, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "block/" + block.getDescriptionId())));

        provider.fullBlock(block, ModelTemplates.CUBE_ALL);
    }

    private void logBlock(BlockModelGenerators blockModels, Block block, Block wood){
        blockModels.woodProvider(block).logWithHorizontal(block).wood(wood);
    }

    private void leavesBlock(BlockModelGenerators blockModels, Block block){
        blockModels.createTintedLeaves(block, TexturedModel.LEAVES, ARGB.opaque(0X00FF00));
    }

    private void saplingBlock(BlockModelGenerators blockModels, Block block){
        blockModels.registerSimpleItemModel(block.asItem(), BlockModelGenerators.PlantType.NOT_TINTED.createItemModel(blockModels, block));

        ResourceLocation resourcelocation = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "block/" + block.getDescriptionId());
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, resourcelocation));
    }

    private void slabBlock(BlockModelGenerators blockModels, Block block, Block materialBlock){
        BlockModelGenerators.BlockFamilyProvider provider = blockModels.new BlockFamilyProvider(TextureMapping.defaultTexture(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "block/slimy_planks"))
                .put(TextureSlot.BOTTOM, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "block/slimy_planks"))
                .put(TextureSlot.TOP, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "block/slimy_planks"))
                .put(TextureSlot.SIDE, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "block/slimy_planks"))
        );
    }

    private void cableBlock(BlockModelGenerators blockModels, Block block){
        // Define the core model
        ResourceLocation coreModelLoc = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "block/cable_core");

        // Define the part model
        ResourceLocation partModelLoc = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "block/cable_part");

        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
                .with(PropertyDispatch.property(CableBlock.UP)
                        .select(true, Variant.variant().with(VariantProperties.MODEL, partModelLoc).with(VariantProperties.UV_LOCK, false).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270))
                        .select(false, Variant.variant())
                )
                .with(PropertyDispatch.property(CableBlock.DOWN)
                        .select(true, Variant.variant().with(VariantProperties.MODEL, partModelLoc).with(VariantProperties.UV_LOCK, false).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
                        .select(false, Variant.variant())
                )
                .with(PropertyDispatch.property(CableBlock.NORTH)
                        .select(true, Variant.variant().with(VariantProperties.MODEL, partModelLoc).with(VariantProperties.UV_LOCK, false))
                        .select(false, Variant.variant())
                )
                .with(PropertyDispatch.property(CableBlock.SOUTH)
                        .select(true, Variant.variant().with(VariantProperties.MODEL, partModelLoc).with(VariantProperties.UV_LOCK, false).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                        .select(false, Variant.variant())
                )
                .with(PropertyDispatch.property(CableBlock.EAST)
                        .select(true, Variant.variant().with(VariantProperties.MODEL, partModelLoc).with(VariantProperties.UV_LOCK, false).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                        .select(false, Variant.variant())
                )
                .with(PropertyDispatch.property(CableBlock.WEST)
                        .select(true, Variant.variant().with(VariantProperties.MODEL, partModelLoc).with(VariantProperties.UV_LOCK, false).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                        .select(false, Variant.variant())
                )
        );

        // Define the item model
        blockModels.registerSimpleItemModel(block, coreModelLoc);
    }

    private void registerSlimeBlock(BlockModelGenerators blockModels, SlimeBlock block, String textureName){
        ResourceLocation modelLoc = ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "block/template_slime_block");

        blockModels.createTrivialCube(block);
        blockModels.registerSimpleTintedItemModel(block, modelLoc, ItemModelUtils.constantTint(block.getColor()));
    }

    private void pressurePlateBlock(BlockModelGenerators blockModels, Block block, Block materialBlock){
        BlockModelGenerators.BlockFamilyProvider provider = blockModels.new BlockFamilyProvider(TextureMapping.defaultTexture(materialBlock));

        provider.pressurePlate(block);
    }

    private void stairsBlock(BlockModelGenerators blockModels, Block block, Block materialBlock){
        BlockModelGenerators.BlockFamilyProvider provider = blockModels.new BlockFamilyProvider(TextureMapping.defaultTexture(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "block/slimy_planks"))
                        .put(TextureSlot.BOTTOM, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "block/slimy_planks"))
                        .put(TextureSlot.TOP, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "block/slimy_planks"))
                        .put(TextureSlot.SIDE, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "block/slimy_planks"))
        );

        provider.stairs(block);
    }

    private void buttonBlock(BlockModelGenerators blockModels, Block block, Block materialBlock){
        BlockModelGenerators.BlockFamilyProvider provider = blockModels.new BlockFamilyProvider(TextureMapping.defaultTexture(materialBlock));

        provider.button(block);
    }

    private void fenceBlock(BlockModelGenerators blockModels, Block block, Block materialBlock){
        BlockModelGenerators.BlockFamilyProvider provider = blockModels.new BlockFamilyProvider(TextureMapping.defaultTexture(materialBlock));

        provider.fence(block);
    }

    private void fenceGateBlock(BlockModelGenerators blockModels, Block block, Block materialBlock){
        BlockModelGenerators.BlockFamilyProvider provider = blockModels.new BlockFamilyProvider(TextureMapping.defaultTexture(materialBlock));

        provider.fenceGate(block);
    }

    private void trapdoorBlockWithRenderType(BlockModelGenerators blockModels, Block block, Block materialBlock){
        blockModels.createTrapdoor(block);
    }

    private void doorBlockWithRenderType(BlockModelGenerators blockModels, Block block, Block materialBlock){
        blockModels.createDoor(block);
    }

    private void wallBlock(BlockModelGenerators blockModels, Block block, Block materialBlock){
        BlockModelGenerators.BlockFamilyProvider provider = blockModels.new BlockFamilyProvider(TextureMapping.defaultTexture(materialBlock).put(
                TextureSlot.WALL, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "block/" + materialBlock.getDescriptionId())
        ));

        provider.wall(block);
    }

    private void simpleItem(ItemModelGenerators itemModels, Item item){
        itemModels.itemModelOutput.accept(item, new BlockModelWrapper.Unbaked(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "item/" + item.getDescriptionId()), Collections.emptyList()));
    }

    private void slimeballItem(ItemModelGenerators itemModels, DeferredItem<Item> item){
        TextureMapping textureMapping = new TextureMapping()
                .put(TextureSlot.LAYER0, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "item/template_slimeball"));

        ResourceLocation model = ModelTemplates.FLAT_ITEM.create(item.get(), textureMapping, itemModels.modelOutput);

        itemModels.itemModelOutput.accept(item.get(), new BlockModelWrapper.Unbaked(model, List.of(ItemModelUtils.constantTint(((SlimeballItem)item.get()).getColor()))));
    }

    private void dnaItem(ItemModelGenerators itemModels, DeferredItem<Item> item){
        TextureMapping textureMapping = new TextureMapping()
                .put(TextureSlot.LAYER0, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "item/template_dna"));

        ResourceLocation model = ModelTemplates.FLAT_ITEM.create(item.get(), textureMapping, itemModels.modelOutput);

        itemModels.itemModelOutput.accept(item.get(), new BlockModelWrapper.Unbaked(model, List.of(ItemModelUtils.constantTint(((DnaItem)item.get()).getColor()))));
    }

    private void bucketItem(ItemModelGenerators itemModels, DeferredItem<Item> item){
        TextureMapping textureMapping = new TextureMapping()
                .put(TextureSlot.LAYER0, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "item/bucket"))
                .put(TextureSlot.LAYER1, ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "item/bucket_fluid"));

        ResourceLocation model = ModelTemplates.TWO_LAYERED_ITEM.create(item.get(), textureMapping, itemModels.modelOutput);

        itemModels.itemModelOutput.accept(item.get(), new BlockModelWrapper.Unbaked(model, List.of(ItemModelUtils.constantTint(-1),ItemModelUtils.constantTint(((BucketItem)item.get()).getColor()))));
    }

    private void spawnEggItem(ItemModelGenerators itemModels, DeferredItem<Item> item) {
        SpawnEggItem spawnEggItem = (SpawnEggItem) item.get();
        itemModels.generateSpawnEgg(spawnEggItem, spawnEggItem.getBg(), spawnEggItem.getFg());
    }
}
