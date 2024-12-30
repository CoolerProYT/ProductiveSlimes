package com.coolerpromc.productiveslimes.recipe;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipes{
    public static final DeferredRegister<IRecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ProductiveSlimes.MODID);

    public static final RegistryObject<IRecipeSerializer<MeltingRecipe>> MELTING_SERIALIZER =
            SERIALIZERS.register("melting", () -> MeltingRecipe.Serializer.INSTANCE);

    public static final RegistryObject< IRecipeSerializer<SolidingRecipe>> SOLIDING_SERIALIZER =
            SERIALIZERS.register("soliding", () -> SolidingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<IRecipeSerializer<DnaExtractingRecipe>> DNA_EXTRACTING_SERIALIZER =
            SERIALIZERS.register("dna_extracting", () -> DnaExtractingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<IRecipeSerializer<DnaSynthesizingRecipe>> DNA_SYNTHESIZING_SERIALIZER =
            SERIALIZERS.register("dna_synthesizing", () -> DnaSynthesizingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<IRecipeSerializer<SqueezingRecipe>> SQUEEZING_SERIALIZER =
            SERIALIZERS.register("squeezing", () -> SqueezingRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
    }
}
