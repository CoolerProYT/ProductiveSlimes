package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ProductiveSlimes.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithVariants(ModBlocks.MELTING_STATION.get(), "melting_station");
        simpleBlockWithVariants(ModBlocks.LIQUID_SOLIDING_STATION.get(), "soliding_station");
        simpleBlockWithVariants(ModBlocks.ENERGY_GENERATOR.get(), "energy_generator");
        simpleBlockWithVariants(ModBlocks.DNA_EXTRACTOR.get(), "dna_extractor");
        simpleBlockWithVariants(ModBlocks.DNA_SYNTHESIZER.get(), "dna_synthesizer");
        simpleBlockWithVariants(ModBlocks.FLUID_TANK.get(), "fluid_tank");

        simpleBlockWithItem(ModBlocks.SLIMY_GRASS_BLOCK.get(), new ModelFile.UncheckedModelFile(modLoc("block/slimy_grass_block")));
        blockWithItem(ModBlocks.SLIMY_DIRT);
        blockWithItem(ModBlocks.SLIMY_STONE);
        blockWithItem(ModBlocks.SLIMY_DEEPSLATE);
        blockWithItem(ModBlocks.SLIMY_COBBLESTONE);
        blockWithItem(ModBlocks.SLIMY_COBBLED_DEEPSLATE);

        logBlock(((RotatedPillarBlock) ModBlocks.SLIMY_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.SLIMY_WOOD.get()), blockTexture(ModBlocks.SLIMY_LOG.get()), blockTexture(ModBlocks.SLIMY_LOG.get()));
        logBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_SLIMY_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_SLIMY_WOOD.get()), blockTexture(ModBlocks.STRIPPED_SLIMY_LOG.get()), blockTexture(ModBlocks.STRIPPED_SLIMY_LOG.get()));

        blockItem(ModBlocks.SLIMY_LOG);
        blockItem(ModBlocks.SLIMY_WOOD);
        blockItem(ModBlocks.STRIPPED_SLIMY_LOG);
        blockItem(ModBlocks.STRIPPED_SLIMY_WOOD);

        blockWithItem(ModBlocks.SLIMY_PLANKS);

        leavesBlock(ModBlocks.SLIMY_LEAVES);
        saplingBlock(ModBlocks.SLIMY_SAPLING);

        stairsBlock(ModBlocks.SLIMY_STAIRS.get(), blockTexture(ModBlocks.SLIMY_PLANKS.get()));
        slabBlock(ModBlocks.SLIMY_SLAB.get(), blockTexture(ModBlocks.SLIMY_PLANKS.get()), blockTexture(ModBlocks.SLIMY_PLANKS.get()));
        pressurePlateBlock(ModBlocks.SLIMY_PRESSURE_PLATE.get(), blockTexture(ModBlocks.SLIMY_PLANKS.get()));
        buttonBlock(ModBlocks.SLIMY_BUTTON.get(), blockTexture(ModBlocks.SLIMY_PLANKS.get()));
        fenceBlock(ModBlocks.SLIMY_FENCE.get(), blockTexture(ModBlocks.SLIMY_PLANKS.get()));
        fenceGateBlock(ModBlocks.SLIMY_FENCE_GATE.get(), blockTexture(ModBlocks.SLIMY_PLANKS.get()));
        trapdoorBlockWithRenderType(ModBlocks.SLIMY_TRAPDOOR.get(), modLoc("block/slimy_trapdoor"), true, "cutout");
        doorBlockWithRenderType(ModBlocks.SLIMY_DOOR.get(), modLoc("block/slimy_door_bottom"), modLoc("block/slimy_door_top"), "cutout");

        stairsBlock(ModBlocks.SLIMY_STONE_STAIRS.get(), blockTexture(ModBlocks.SLIMY_STONE.get()));
        slabBlock(ModBlocks.SLIMY_STONE_SLAB.get(), blockTexture(ModBlocks.SLIMY_STONE.get()), blockTexture(ModBlocks.SLIMY_STONE.get()));
        pressurePlateBlock(ModBlocks.SLIMY_STONE_PRESSURE_PLATE.get(), blockTexture(ModBlocks.SLIMY_STONE.get()));
        buttonBlock(ModBlocks.SLIMY_STONE_BUTTON.get(), blockTexture(ModBlocks.SLIMY_STONE.get()));

        stairsBlock(ModBlocks.SLIMY_COBBLESTONE_STAIRS.get(), blockTexture(ModBlocks.SLIMY_COBBLESTONE.get()));
        slabBlock(ModBlocks.SLIMY_COBBLESTONE_SLAB.get(), blockTexture(ModBlocks.SLIMY_COBBLESTONE.get()), blockTexture(ModBlocks.SLIMY_COBBLESTONE.get()));
        wallBlock(ModBlocks.SLIMY_COBBLESTONE_WALL.get(), blockTexture(ModBlocks.SLIMY_COBBLESTONE.get()));

        stairsBlock(ModBlocks.SLIMY_COBBLED_DEEPSLATE_STAIRS.get(), blockTexture(ModBlocks.SLIMY_COBBLED_DEEPSLATE.get()));
        slabBlock(ModBlocks.SLIMY_COBBLED_DEEPSLATE_SLAB.get(), blockTexture(ModBlocks.SLIMY_COBBLED_DEEPSLATE.get()), blockTexture(ModBlocks.SLIMY_COBBLED_DEEPSLATE.get()));
        wallBlock(ModBlocks.SLIMY_COBBLED_DEEPSLATE_WALL.get(), blockTexture(ModBlocks.SLIMY_COBBLED_DEEPSLATE.get()));

        blockItem(ModBlocks.SLIMY_STAIRS);
        blockItem(ModBlocks.SLIMY_SLAB);
        blockItem(ModBlocks.SLIMY_PRESSURE_PLATE);
        blockItem(ModBlocks.SLIMY_FENCE_GATE);
        blockItem(ModBlocks.SLIMY_TRAPDOOR, "_bottom");

        blockItem(ModBlocks.SLIMY_STONE_STAIRS);
        blockItem(ModBlocks.SLIMY_STONE_SLAB);
        blockItem(ModBlocks.SLIMY_STONE_PRESSURE_PLATE);

        blockItem(ModBlocks.SLIMY_COBBLESTONE_STAIRS);
        blockItem(ModBlocks.SLIMY_COBBLESTONE_SLAB);

        blockItem(ModBlocks.SLIMY_COBBLED_DEEPSLATE_STAIRS);
        blockItem(ModBlocks.SLIMY_COBBLED_DEEPSLATE_SLAB);

        registerSlimeBlock(ModBlocks.DIRT_SLIME_BLOCK.get(), "dirt_slime_block");
        registerSlimeBlock(ModBlocks.STONE_SLIME_BLOCK.get(), "stone_slime_block");
        registerSlimeBlock(ModBlocks.COPPER_SLIME_BLOCK.get(), "copper_slime_block");
        registerSlimeBlock(ModBlocks.IRON_SLIME_BLOCK.get(), "iron_slime_block");
        registerSlimeBlock(ModBlocks.GOLD_SLIME_BLOCK.get(), "gold_slime_block");
        registerSlimeBlock(ModBlocks.DIAMOND_SLIME_BLOCK.get(), "diamond_slime_block");
        registerSlimeBlock(ModBlocks.NETHERITE_SLIME_BLOCK.get(), "netherite_slime_block");
        registerSlimeBlock(ModBlocks.LAPIS_SLIME_BLOCK.get(), "lapis_slime_block");
        registerSlimeBlock(ModBlocks.REDSTONE_SLIME_BLOCK.get(), "redstone_slime_block");
        registerSlimeBlock(ModBlocks.OAK_SLIME_BLOCK.get(), "oak_slime_block");
        registerSlimeBlock(ModBlocks.SAND_SLIME_BLOCK.get(), "sand_slime_block");
        registerSlimeBlock(ModBlocks.ANDESITE_SLIME_BLOCK.get(), "andesite_slime_block");
        registerSlimeBlock(ModBlocks.SNOW_SLIME_BLOCK.get(), "snow_slime_block");
        registerSlimeBlock(ModBlocks.ICE_SLIME_BLOCK.get(), "ice_slime_block");
        registerSlimeBlock(ModBlocks.MUD_SLIME_BLOCK.get(), "mud_slime_block");
        registerSlimeBlock(ModBlocks.CLAY_SLIME_BLOCK.get(), "clay_slime_block");
        registerSlimeBlock(ModBlocks.RED_SAND_SLIME_BLOCK.get(), "red_sand_slime_block");
        registerSlimeBlock(ModBlocks.MOSS_SLIME_BLOCK.get(), "moss_slime_block");
        registerSlimeBlock(ModBlocks.DEEPSLATE_SLIME_BLOCK.get(), "deepslate_slime_block");
        registerSlimeBlock(ModBlocks.GRANITE_SLIME_BLOCK.get(), "granite_slime_block");
        registerSlimeBlock(ModBlocks.DIORITE_SLIME_BLOCK.get(), "diorite_slime_block");
        registerSlimeBlock(ModBlocks.CALCITE_SLIME_BLOCK.get(), "calcite_slime_block");
        registerSlimeBlock(ModBlocks.TUFF_SLIME_BLOCK.get(), "tuff_slime_block");
        registerSlimeBlock(ModBlocks.DRIPSTONE_SLIME_BLOCK.get(), "dripstone_slime_block");
        registerSlimeBlock(ModBlocks.PRISMARINE_SLIME_BLOCK.get(), "prismarine_slime_block");
        registerSlimeBlock(ModBlocks.MAGMA_SLIME_BLOCK.get(), "magma_slime_block");
        registerSlimeBlock(ModBlocks.OBSIDIAN_SLIME_BLOCK.get(), "obsidian_slime_block");
        registerSlimeBlock(ModBlocks.NETHERRACK_SLIME_BLOCK.get(), "netherrack_slime_block");
        registerSlimeBlock(ModBlocks.SOUL_SAND_SLIME_BLOCK.get(), "soul_sand_slime_block");
        registerSlimeBlock(ModBlocks.SOUL_SOIL_SLIME_BLOCK.get(), "soul_soil_slime_block");
        registerSlimeBlock(ModBlocks.BLACKSTONE_SLIME_BLOCK.get(), "blackstone_slime_block");
        registerSlimeBlock(ModBlocks.BASALT_SLIME_BLOCK.get(), "basalt_slime_block");
        registerSlimeBlock(ModBlocks.ENDSTONE_SLIME_BLOCK.get(), "endstone_slime_block");
        registerSlimeBlock(ModBlocks.QUARTZ_SLIME_BLOCK.get(), "quartz_slime_block");
        registerSlimeBlock(ModBlocks.GLOWSTONE_SLIME_BLOCK.get(), "glowstone_slime_block");
        registerSlimeBlock(ModBlocks.AMETHYST_SLIME_BLOCK.get(), "amethyst_slime_block");
        registerSlimeBlock(ModBlocks.BROWN_MUSHROOM_SLIME_BLOCK.get(), "brown_mushroom_slime_block");
        registerSlimeBlock(ModBlocks.RED_MUSHROOM_SLIME_BLOCK.get(), "red_mushroom_slime_block");
        registerSlimeBlock(ModBlocks.CACTUS_SLIME_BLOCK.get(), "cactus_slime_block");
        registerSlimeBlock(ModBlocks.COAL_SLIME_BLOCK.get(), "coal_slime_block");
        registerSlimeBlock(ModBlocks.GRAVEL_SLIME_BLOCK.get(), "gravel_slime_block");
        registerSlimeBlock(ModBlocks.ENERGY_SLIME_BLOCK.get(), "energy_slime_block");
        registerSlimeBlock(ModBlocks.OAK_LEAVES_SLIME_BLOCK.get(), "oak_leaves_slime_block");
    }

    private void registerSlimeBlock(Block block, String textureName){
        ModelFile customModel = models().withExistingParent("block/" + textureName, mcLoc("block/block"))
                .texture("particle", modLoc("block/template_slime_block"))
                .texture("texture", modLoc("block/template_slime_block"))
                .element().from(0, 0, 0).to(16, 16, 16)
                .face(Direction.DOWN).texture("#texture").cullface(Direction.DOWN).tintindex(0).end()
                .face(Direction.UP).texture("#texture").cullface(Direction.UP).tintindex(0).end()
                .face(Direction.NORTH).texture("#texture").cullface(Direction.NORTH).tintindex(0).end()
                .face(Direction.SOUTH).texture("#texture").cullface(Direction.SOUTH).tintindex(0).end()
                .face(Direction.WEST).texture("#texture").cullface(Direction.WEST).tintindex(0).end()
                .face(Direction.EAST).texture("#texture").cullface(Direction.EAST).tintindex(0).end()
                .end()
                .element().from(3, 3, 3).to(13, 13, 13)
                .face(Direction.DOWN).texture("#texture").tintindex(0).end()
                .face(Direction.UP).texture("#texture").tintindex(0).end()
                .face(Direction.NORTH).texture("#texture").tintindex(0).end()
                .face(Direction.SOUTH).texture("#texture").tintindex(0).end()
                .face(Direction.WEST).texture("#texture").tintindex(0).end()
                .face(Direction.EAST).texture("#texture").tintindex(0).end()
                .end();

        getVariantBuilder(block)
                .partialState().setModels(new ConfiguredModel(customModel));

        simpleBlockItem(block, customModel);
    }

    private void blockWithItem(DeferredBlock<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void simpleBlockWithVariants(Block block, String modelName) {
        ResourceLocation blockModel = modLoc("block/" + modelName);
        ModelFile model = new ModelFile.UncheckedModelFile(blockModel);

        getVariantBuilder(block).forAllStates(state -> {
            Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            int yRotation = direction == Direction.SOUTH ? 0 : direction.get2DDataValue() * 90;

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY(yRotation)
                    .build();
        });
    }

    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("productiveslimes:block/" + deferredBlock.getId().getPath()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("productiveslimes:block/" + deferredBlock.getId().getPath() + appendix));
    }

    private void saplingBlock(DeferredBlock<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(BuiltInRegistries.BLOCK.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void leavesBlock(DeferredBlock<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(BuiltInRegistries.BLOCK.getKey(blockRegistryObject.get()).getPath(), ResourceLocation.parse("minecraft:block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }
}
