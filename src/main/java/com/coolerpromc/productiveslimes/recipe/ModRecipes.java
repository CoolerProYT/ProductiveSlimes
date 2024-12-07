package com.coolerpromc.productiveslimes.recipe;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes{
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, ProductiveSlimes.MODID);
    public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, ProductiveSlimes.MODID);

    public static final RegistryObject<RecipeSerializer<MeltingRecipe>> MELTING_SERIALIZER =
            SERIALIZERS.register("melting", () -> MeltingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeType<MeltingRecipe>> MELTING_TYPE =
            TYPES.register("melting", () -> RecipeType.simple(new ResourceLocation(ProductiveSlimes.MODID, "melting")));

    public static final RegistryObject< RecipeSerializer<SolidingRecipe>> SOLIDING_SERIALIZER =
            SERIALIZERS.register("soliding", () -> SolidingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeType<SolidingRecipe>> SOLIDING_TYPE =
            TYPES.register("soliding", () -> RecipeType.simple(new ResourceLocation(ProductiveSlimes.MODID, "soliding")));

    public static final RegistryObject<RecipeSerializer<DnaExtractingRecipe>> DNA_EXTRACTING_SERIALIZER =
            SERIALIZERS.register("dna_extracting", () -> DnaExtractingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeType<DnaExtractingRecipe>> DNA_EXTRACTING_TYPE =
            TYPES.register("dna_extracting", () -> RecipeType.simple(new ResourceLocation(ProductiveSlimes.MODID, "dna_extracting")));

    public static final RegistryObject<RecipeSerializer<DnaSynthesizingRecipe>> DNA_SYNTHESIZING_SERIALIZER =
            SERIALIZERS.register("dna_synthesizing", () -> DnaSynthesizingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeType<DnaSynthesizingRecipe>> DNA_SYNTHESIZING_TYPE =
            TYPES.register("dna_synthesizing", () -> RecipeType.simple(new ResourceLocation(ProductiveSlimes.MODID, "dna_synthesizing")));

    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
