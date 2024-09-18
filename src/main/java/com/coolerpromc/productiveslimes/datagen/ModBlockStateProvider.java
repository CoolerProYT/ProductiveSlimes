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
    }

    private void registerSlimeBlock(Block block, String textureName){
        ModelFile customModel = models().withExistingParent("block/" + textureName, mcLoc("block/block"))
                .texture("particle", modLoc("block/" + textureName))
                .texture("texture", modLoc("block/" + textureName))
                .element().from(0, 0, 0).to(16, 16, 16)
                .face(Direction.DOWN).texture("#texture").cullface(Direction.DOWN).end()
                .face(Direction.UP).texture("#texture").cullface(Direction.UP).end()
                .face(Direction.NORTH).texture("#texture").cullface(Direction.NORTH).end()
                .face(Direction.SOUTH).texture("#texture").cullface(Direction.SOUTH).end()
                .face(Direction.WEST).texture("#texture").cullface(Direction.WEST).end()
                .face(Direction.EAST).texture("#texture").cullface(Direction.EAST).end()
                .end()
                .element().from(3, 3, 3).to(13, 13, 13)
                .face(Direction.DOWN).texture("#texture").end()
                .face(Direction.UP).texture("#texture").end()
                .face(Direction.NORTH).texture("#texture").end()
                .face(Direction.SOUTH).texture("#texture").end()
                .face(Direction.WEST).texture("#texture").end()
                .face(Direction.EAST).texture("#texture").end()
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
