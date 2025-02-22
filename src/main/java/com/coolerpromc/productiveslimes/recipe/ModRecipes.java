package com.coolerpromc.productiveslimes.recipe;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipes{
    public static final DeferredRegister<RecipeBookCategory> CATEGORIES = DeferredRegister.create(BuiltInRegistries.RECIPE_BOOK_CATEGORY, ProductiveSlimes.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, ProductiveSlimes.MODID);
    public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, ProductiveSlimes.MODID);

    public static final Supplier<RecipeBookCategory> MELTING_CATEGORY = CATEGORIES.register("melting", RecipeBookCategory::new);
    public static final Supplier<RecipeSerializer<MeltingRecipe>> MELTING_SERIALIZER = SERIALIZERS.register("melting", () -> MeltingRecipe.Serializer.INSTANCE);
    public static final Supplier<RecipeType<MeltingRecipe>> MELTING_TYPE = TYPES.register("melting", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "melting")));

    public static final Supplier<RecipeBookCategory> SOLIDING_CATEGORY = CATEGORIES.register("soliding", RecipeBookCategory::new);
    public static final Supplier<RecipeSerializer<SolidingRecipe>> SOLIDING_SERIALIZER = SERIALIZERS.register("soliding", () -> SolidingRecipe.Serializer.INSTANCE);
    public static final Supplier<RecipeType<SolidingRecipe>> SOLIDING_TYPE = TYPES.register("soliding", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "soliding")));

    public static final Supplier<RecipeBookCategory> DNA_EXTRACTING_CATEGORY = CATEGORIES.register("dna_extracting", RecipeBookCategory::new);
    public static final Supplier<RecipeSerializer<DnaExtractingRecipe>> DNA_EXTRACTING_SERIALIZER = SERIALIZERS.register("dna_extracting", () -> DnaExtractingRecipe.Serializer.INSTANCE);
    public static final Supplier<RecipeType<DnaExtractingRecipe>> DNA_EXTRACTING_TYPE = TYPES.register("dna_extracting", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "dna_extracting")));

    public static final Supplier<RecipeBookCategory> DNA_SYNTHESIZING_CATEGORY = CATEGORIES.register("dna_synthesizing", RecipeBookCategory::new);
    public static final Supplier<RecipeSerializer<DnaSynthesizingRecipe>> DNA_SYNTHESIZING_SERIALIZER = SERIALIZERS.register("dna_synthesizing", () -> DnaSynthesizingRecipe.Serializer.INSTANCE);
    public static final Supplier<RecipeType<DnaSynthesizingRecipe>> DNA_SYNTHESIZING_TYPE = TYPES.register("dna_synthesizing", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "dna_synthesizing")));

    public static final Supplier<RecipeBookCategory> SQUEEZING_CATEGORY = CATEGORIES.register("squeezing", RecipeBookCategory::new);
    public static final Supplier<RecipeSerializer<SqueezingRecipe>> SQUEEZING_SERIALIZER = SERIALIZERS.register("squeezing", () -> SqueezingRecipe.Serializer.INSTANCE);
    public static final Supplier<RecipeType<SqueezingRecipe>> SQUEEZING_TYPE = TYPES.register("squeezing", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(ProductiveSlimes.MODID, "squeezing")));

    public static void register(IEventBus eventBus){
        CATEGORIES.register(eventBus);
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
