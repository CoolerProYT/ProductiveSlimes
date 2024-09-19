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
