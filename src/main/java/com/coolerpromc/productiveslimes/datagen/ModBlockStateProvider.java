package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator generator, ExistingFileHelper exFileHelper) {
        super(generator, ProductiveSlimes.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithVariants(ModBlocks.MELTING_STATION.get(), "melting_station");
        simpleBlockWithVariants(ModBlocks.LIQUID_SOLIDING_STATION.get(), "soliding_station");
        simpleBlockWithVariants(ModBlocks.ENERGY_GENERATOR.get(), "energy_generator");
        simpleBlockWithVariants(ModBlocks.DNA_EXTRACTOR.get(), "dna_extractor");
        simpleBlockWithVariants(ModBlocks.DNA_SYNTHESIZER.get(), "dna_synthesizer");
        simpleBlockWithVariants(ModBlocks.FLUID_TANK.get(), "fluid_tank");
        simpleBlockWithVariants(ModBlocks.SLIME_NEST.get(), "slime_nest");
        simpleBlockWithVariants(ModBlocks.SLIMEBALL_COLLECTOR.get(), "slimeball_collector");

        blockWithItem(ModBlocks.SLIMY_GRASS_BLOCK, new ModelFile.UncheckedModelFile(modLoc("block/slimy_grass_block")));
        blockWithItem(ModBlocks.SLIMY_DIRT);
        blockWithItem(ModBlocks.SLIMY_STONE);
        blockWithItem(ModBlocks.SLIMY_COBBLESTONE);

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
        trapdoorBlock(ModBlocks.SLIMY_TRAPDOOR.get(), modLoc("block/slimy_trapdoor"), true);
        doorBlock(ModBlocks.SLIMY_DOOR.get(), modLoc("block/slimy_door_bottom"), modLoc("block/slimy_door_top"));
        stairsBlock(ModBlocks.SLIMY_STONE_STAIRS.get(), blockTexture(ModBlocks.SLIMY_STONE.get()));
        slabBlock(ModBlocks.SLIMY_STONE_SLAB.get(), blockTexture(ModBlocks.SLIMY_STONE.get()), blockTexture(ModBlocks.SLIMY_STONE.get()));
        pressurePlateBlock(ModBlocks.SLIMY_STONE_PRESSURE_PLATE.get(), blockTexture(ModBlocks.SLIMY_STONE.get()));
        buttonBlock(ModBlocks.SLIMY_STONE_BUTTON.get(), blockTexture(ModBlocks.SLIMY_STONE.get()));
        stairsBlock(ModBlocks.SLIMY_COBBLESTONE_STAIRS.get(), blockTexture(ModBlocks.SLIMY_COBBLESTONE.get()));
        slabBlock(ModBlocks.SLIMY_COBBLESTONE_SLAB.get(), blockTexture(ModBlocks.SLIMY_COBBLESTONE.get()), blockTexture(ModBlocks.SLIMY_COBBLESTONE.get()));
        wallBlock(ModBlocks.SLIMY_COBBLESTONE_WALL.get(), blockTexture(ModBlocks.SLIMY_COBBLESTONE.get()));
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

        registerSlimeBlock(ModBlocks.ENERGY_SLIME_BLOCK.get(), "energy_slime_block");

        for(Tier tier : Tier.values()){
            ModTiers modTiers = ModTierLists.getTierByName(tier);
            String textureName = modTiers.name() + "_slime_block";
            registerSlimeBlock(ModTierLists.getBlockByName(modTiers.name()).get(), textureName);
        }
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

    private void blockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlock(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
        simpleBlockItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject, ModelFile model){
        simpleBlock(blockRegistryObject.get(), model);
        simpleBlockItem(blockRegistryObject.get(), model);
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

    private void blockItem(RegistryObject<? extends Block> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("productiveslimes:block/" + deferredBlock.getId().getPath()));
    }
    private void blockItem(RegistryObject<? extends Block> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("productiveslimes:block/" + deferredBlock.getId().getPath() + appendix));
    }
    private void saplingBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())));
    }
    private void leavesBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().singleTexture(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), new ResourceLocation("minecraft:block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())));
        simpleBlockItem(blockRegistryObject.get(), models().singleTexture(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), new ResourceLocation("minecraft:block/leaves"),
                "all", blockTexture(blockRegistryObject.get())));
    }

    public void pressurePlateBlock(PressurePlateBlock block, ResourceLocation texture) {
        ModelFile pressurePlate = this.models().withExistingParent(this.name(block), this.mcLoc("block/pressure_plate_up")).texture("texture", texture);
        ModelFile pressurePlateDown = this.models().withExistingParent(this.name(block) + "_down", this.mcLoc("block/pressure_plate_down")).texture("texture", texture);
        this.getVariantBuilder(block).partialState().with(PressurePlateBlock.POWERED, true).addModels(new ConfiguredModel[]{new ConfiguredModel(pressurePlateDown)}).partialState().with(PressurePlateBlock.POWERED, false).addModels(new ConfiguredModel[]{new ConfiguredModel(pressurePlate)});
    }

    public void buttonBlock(AbstractButtonBlock block,  ResourceLocation texture) {
        ModelFile button = this.models().withExistingParent(this.name(block), this.mcLoc("block/button")).texture("texture", texture);
        ModelFile buttonPressed = this.models().withExistingParent(this.name(block), this.mcLoc("block/button_pressed")).texture("texture", texture);

        this.getVariantBuilder(block).forAllStates((state) -> {
            Direction facing = (Direction)state.getValue(AbstractButtonBlock.FACING);
            AttachFace face = (AttachFace)state.getValue(AbstractButtonBlock.FACE);
            boolean powered = (Boolean)state.getValue(AbstractButtonBlock.POWERED);
            return ConfiguredModel.builder().modelFile(powered ? buttonPressed : button).rotationX(face == AttachFace.FLOOR ? 0 : (face == AttachFace.WALL ? 90 : 180)).rotationY((int)(face == AttachFace.CEILING ? facing : facing.getOpposite()).toYRot()).uvLock(face == AttachFace.WALL).build();
        });
    }

    private String name(Block block) {
        return this.key(block).getPath();
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    public ResourceLocation mcLoc(String name) {
        return new ResourceLocation(name);
    }
}
