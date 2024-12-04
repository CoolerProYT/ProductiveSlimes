package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.util.TempItemsGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = ProductiveSlimes.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event){
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();

        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        event.addProvider(new ModRecipeProvider.Runner(packOutput, event.getLookupProvider()));
        event.addProvider(new ModLootTableProvider(packOutput, lookupProvider));

        event.addProvider(new ModBlockStateProvider(packOutput, existingFileHelper));
        event.addProvider(new ModItemModelProvider(packOutput, existingFileHelper));

        ModBlockTagGenerator blockTagGenerator = event.addProvider(new ModBlockTagGenerator(packOutput, lookupProvider, existingFileHelper));
        event.addProvider(new ModItemTagGenerator(packOutput, lookupProvider, blockTagGenerator.contentsGetter(), existingFileHelper));

        event.addProvider(new ModFluidTagsProvider(packOutput, lookupProvider, existingFileHelper));
        event.addProvider(new ModWorldGenProvider(packOutput, lookupProvider));

        TempItemsGenerator.init();
    }
}
