package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = ProductiveSlimes.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();

        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(new ModRecipeProvider(generator));
        generator.addProvider(new ModLootTableProvider(generator));

        generator.addProvider(new ModBlockStateProvider(generator, existingFileHelper));
        generator.addProvider(new ModItemModelProvider(generator, existingFileHelper));

        ModBlockTagGenerator blockTagGenerator = new ModBlockTagGenerator(generator, existingFileHelper);
        generator.addProvider(blockTagGenerator);
        generator.addProvider(new ModItemTagGenerator(generator, blockTagGenerator, existingFileHelper));

        generator.addProvider(new ModFluidTagsProvider(generator, existingFileHelper));
    }
}
