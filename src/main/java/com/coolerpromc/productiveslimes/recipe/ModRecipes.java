package com.coolerpromc.productiveslimes.recipe;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes{
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, ProductiveSlimes.MODID);
    public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, ProductiveSlimes.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> MELTING_SERIALIZER =
            SERIALIZERS.register("melting", () -> MeltingRecipe.Serializer.INSTANCE);

    public static final DeferredHolder<RecipeType<?>, RecipeType<MeltingRecipe>> MELTING_TYPE =
            TYPES.register("melting", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "melting")));

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> SOLIDING_SERIALIZER =
            SERIALIZERS.register("soliding", () -> SolidingRecipe.Serializer.INSTANCE);

    public static final DeferredHolder<RecipeType<?>, RecipeType<SolidingRecipe>> SOLIDING_TYPE =
            TYPES.register("soliding", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "soliding")));

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> DNA_EXTRACTING_SERIALIZER =
            SERIALIZERS.register("dna_extracting", () -> DnaExtractingRecipe.Serializer.INSTANCE);

    public static final DeferredHolder<RecipeType<?>, RecipeType<DnaExtractingRecipe>> DNA_EXTRACTING_TYPE =
            TYPES.register("dna_extracting", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "dna_extracting")));

    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
