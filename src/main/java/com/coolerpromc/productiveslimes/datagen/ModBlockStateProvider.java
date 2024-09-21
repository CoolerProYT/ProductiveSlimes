package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
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
}
