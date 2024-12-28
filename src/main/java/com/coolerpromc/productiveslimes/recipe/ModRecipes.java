package com.coolerpromc.productiveslimes.recipe;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes{
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ProductiveSlimes.MODID);

    public static final RegistryObject<RecipeSerializer<MeltingRecipe>> MELTING_SERIALIZER =
            SERIALIZERS.register("melting", () -> MeltingRecipe.Serializer.INSTANCE);

    public static final RegistryObject< RecipeSerializer<SolidingRecipe>> SOLIDING_SERIALIZER =
            SERIALIZERS.register("soliding", () -> SolidingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<DnaExtractingRecipe>> DNA_EXTRACTING_SERIALIZER =
            SERIALIZERS.register("dna_extracting", () -> DnaExtractingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<DnaSynthesizingRecipe>> DNA_SYNTHESIZING_SERIALIZER =
            SERIALIZERS.register("dna_synthesizing", () -> DnaSynthesizingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<SqueezingRecipe>> SQUEEZING_SERIALIZER =
            SERIALIZERS.register("squeezing", () -> SqueezingRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
    }
}
