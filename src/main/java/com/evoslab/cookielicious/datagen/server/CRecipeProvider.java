package com.evoslab.cookielicious.datagen.server;

import com.evoslab.cookielicious.common.core.Cookielicious;
import com.evoslab.cookielicious.common.core.other.CookieliciousCompat;
import com.evoslab.cookielicious.common.core.registry.CookieliciousBlocks;
import com.evoslab.cookielicious.common.core.registry.CookieliciousItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.http.cookie.Cookie;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CRecipeProvider extends RecipeProvider {


    public CRecipeProvider(DataGenerator generator) {
        super(generator.getPackOutput());
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        addCookieRecipe(CookieliciousItems.VANILLA_COOKIE, () -> getItem(CookieliciousCompat.NEAPOLITAN, "dried_vanilla_pods"), consumer);
        addCookieRecipe(CookieliciousItems.STRAWBERRY_COOKIE, () -> getItem(CookieliciousCompat.NEAPOLITAN, "strawberries"), consumer);
        addCookieRecipe(CookieliciousItems.CHOCOLATE_COOKIE, () -> getItem(CookieliciousCompat.NEAPOLITAN, "chocolate_bar"), consumer);
        addCookieRecipe(CookieliciousItems.MINT_COOKIE, () -> getItem(CookieliciousCompat.NEAPOLITAN, "mint_leaves"), consumer);
        addCookieRecipe(CookieliciousItems.BANANA_COOKIE, () -> getItem(CookieliciousCompat.NEAPOLITAN, "banana"), consumer);
        addCookieRecipe(CookieliciousItems.ADZUKI_COOKIE, () -> getItem(CookieliciousCompat.NEAPOLITAN, "roasted_adzuki_beans"), consumer);
        addModLoadedCookieRecipe(CookieliciousCompat.SEASONALS, CookieliciousItems.PUMPKIN_COOKIE, () -> getItem(CookieliciousCompat.SEASONALS, "pumpkin_puree"), consumer);
        addModLoadedCookieRecipe(CookieliciousCompat.SEASONALS, CookieliciousItems.BEETROOT_COOKIE, () -> getItem(CookieliciousCompat.SEASONALS, "roasted_beetroot"), consumer);

        addCookieTileRecipes(CookieliciousItems.VANILLA_COOKIE, CookieliciousBlocks.VANILLA_COOKIE_TILES, CookieliciousBlocks.VANILLA_COOKIE_TILE_STAIRS, CookieliciousBlocks.VANILLA_COOKIE_TILE_SLAB, CookieliciousBlocks.VANILLA_COOKIE_TILE_WALL, consumer);
        addCookieTileRecipes(() -> Items.COOKIE, CookieliciousBlocks.COOKIE_TILES, CookieliciousBlocks.COOKIE_TILE_STAIRS, CookieliciousBlocks.COOKIE_TILE_SLAB, CookieliciousBlocks.COOKIE_TILE_WALL, consumer);
        addCookieTileRecipes(CookieliciousItems.STRAWBERRY_COOKIE, CookieliciousBlocks.STRAWBERRY_COOKIE_TILES, CookieliciousBlocks.STRAWBERRY_COOKIE_TILE_STAIRS, CookieliciousBlocks.STRAWBERRY_COOKIE_TILE_SLAB, CookieliciousBlocks.STRAWBERRY_COOKIE_TILE_WALL, consumer);
        addCookieTileRecipes(CookieliciousItems.CHOCOLATE_COOKIE, CookieliciousBlocks.CHOCOLATE_COOKIE_TILES, CookieliciousBlocks.CHOCOLATE_COOKIE_TILE_STAIRS, CookieliciousBlocks.CHOCOLATE_COOKIE_TILE_SLAB, CookieliciousBlocks.CHOCOLATE_COOKIE_TILE_WALL, consumer);
        addModLoadedCookieTileRecipes(CookieliciousCompat.FARMERS_DELIGHT, () -> getItem(CookieliciousCompat.FARMERS_DELIGHT, "honey_cookie"), CookieliciousBlocks.HONEY_COOKIE_TILES, CookieliciousBlocks.HONEY_COOKIE_TILE_STAIRS, CookieliciousBlocks.HONEY_COOKIE_TILE_SLAB, CookieliciousBlocks.HONEY_COOKIE_TILE_WALL, consumer);
        addModLoadedCookieTileRecipes(CookieliciousCompat.FARMERS_DELIGHT, () -> getItem(CookieliciousCompat.FARMERS_DELIGHT, "sweet_berry_cookie"), CookieliciousBlocks.SWEET_BERRY_COOKIE_TILES, CookieliciousBlocks.SWEET_BERRY_COOKIE_TILE_STAIRS, CookieliciousBlocks.SWEET_BERRY_COOKIE_TILE_SLAB, CookieliciousBlocks.SWEET_BERRY_COOKIE_TILE_WALL, consumer);
        addCookieTileRecipes(CookieliciousItems.BANANA_COOKIE, CookieliciousBlocks.BANANA_COOKIE_TILES, CookieliciousBlocks.BANANA_COOKIE_TILE_STAIRS, CookieliciousBlocks.BANANA_COOKIE_TILE_SLAB, CookieliciousBlocks.BANANA_COOKIE_TILE_WALL, consumer);
        addCookieTileRecipes(CookieliciousItems.MINT_COOKIE, CookieliciousBlocks.MINT_COOKIE_TILES, CookieliciousBlocks.MINT_COOKIE_TILE_STAIRS, CookieliciousBlocks.MINT_COOKIE_TILE_SLAB, CookieliciousBlocks.MINT_COOKIE_TILE_WALL, consumer);
        addCookieTileRecipes(CookieliciousItems.ADZUKI_COOKIE, CookieliciousBlocks.ADZUKI_COOKIE_TILES, CookieliciousBlocks.ADZUKI_COOKIE_TILE_STAIRS, CookieliciousBlocks.ADZUKI_COOKIE_TILE_SLAB, CookieliciousBlocks.ADZUKI_COOKIE_TILE_WALL, consumer);
        addModLoadedCookieTileRecipes(CookieliciousCompat.ABNORMALS_DELIGHT, () -> getItem(CookieliciousCompat.ABNORMALS_DELIGHT, "cherry_cookie"), CookieliciousBlocks.CHERRY_COOKIE_TILES, CookieliciousBlocks.CHERRY_COOKIE_TILE_STAIRS, CookieliciousBlocks.CHERRY_COOKIE_TILE_SLAB, CookieliciousBlocks.CHERRY_COOKIE_TILE_WALL, consumer);
        addModLoadedCookieTileRecipes(CookieliciousCompat.ABNORMALS_DELIGHT, () -> getItem(CookieliciousCompat.ABNORMALS_DELIGHT, "mulberry_cookie"), CookieliciousBlocks.MULBERRY_COOKIE_TILES, CookieliciousBlocks.MULBERRY_COOKIE_TILE_STAIRS, CookieliciousBlocks.MULBERRY_COOKIE_TILE_SLAB, CookieliciousBlocks.MULBERRY_COOKIE_TILE_WALL, consumer);
        addModLoadedCookieTileRecipes(CookieliciousCompat.ABNORMALS_DELIGHT, () -> getItem(CookieliciousCompat.ABNORMALS_DELIGHT, "maple_cookie"), CookieliciousBlocks.MAPLE_COOKIE_TILES, CookieliciousBlocks.MAPLE_COOKIE_TILE_STAIRS, CookieliciousBlocks.MAPLE_COOKIE_TILE_SLAB, CookieliciousBlocks.MAPLE_COOKIE_TILE_WALL, consumer);

    }

    private static void addCookieRecipe(Supplier<? extends ItemLike> result,
                                        Supplier<? extends ItemLike> input, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, result.get(), 8)
                .define('W', Items.WHEAT)
                .define('R', input.get())
                .pattern("WRW")
                .unlockedBy(getHasName(input.get()), has(input.get()))
                .save(consumer);
    }

    private static void addModLoadedCookieRecipe(String modId, Supplier<? extends ItemLike> result,
                                                 Supplier<? extends ItemLike> input, Consumer<FinishedRecipe> consumer) {
        conditionalModLoadedRecipe(modId, ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, result.get(), 8)
                .define('W', Items.WHEAT)
                .define('R', input.get())
                .pattern("WRW").unlockedBy(getHasName(input.get()), has(input.get())), RecipeCategory.FOOD, consumer, null);
    }

    private static void addCookieTileRecipes(Supplier<? extends ItemLike> cookie, Supplier<? extends ItemLike> tilesBlock,
                                             Supplier<? extends ItemLike> stair, Supplier<? extends ItemLike> slab,
                                             Supplier<? extends ItemLike> wall,
                                             Consumer<FinishedRecipe> consumer) {

        //Tiles
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, tilesBlock.get(), 4)
                .define('#', cookie.get()).pattern("##").pattern("##")
                .unlockedBy(getHasName(cookie.get()), has(cookie.get()))
                .save(consumer);
        //Stairs
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stair.get(), 4)
                .define('#', tilesBlock.get()).pattern("#  ").pattern("## ").pattern("###")
                .unlockedBy(getHasName(tilesBlock.get()), has(tilesBlock.get()))
                .save(consumer);

        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, stair.get(), tilesBlock.get());

        //Slab
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab.get(), 6)
                .define('#', tilesBlock.get()).pattern("###")
                .unlockedBy(getHasName(tilesBlock.get()), has(tilesBlock.get()))
                .save(consumer);

        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, slab.get(), tilesBlock.get(), 2);

        //Wall
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wall.get(), 6)
                .define('#', tilesBlock.get()).pattern("###").pattern("###")
                .unlockedBy(getHasName(tilesBlock.get()), has(tilesBlock.get()))
                .save(consumer);

        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, wall.get(), tilesBlock.get());
    }

    private static void addModLoadedCookieTileRecipes(String modId, Supplier<? extends ItemLike> cookie,
                                                      Supplier<? extends ItemLike> tilesBlock, Supplier<? extends ItemLike> stair,
                                                      Supplier<? extends ItemLike> slab, Supplier<? extends ItemLike> wall,
                                                      Consumer<FinishedRecipe> consumer) {

        //Tiles
        conditionalModLoadedRecipe(modId, ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, tilesBlock.get(), 4)
                .define('#', cookie.get()).pattern("##").pattern("##")
                .unlockedBy(getHasName(cookie.get()), has(cookie.get())), RecipeCategory.BUILDING_BLOCKS, consumer, null);
        //Stairs
        conditionalModLoadedRecipe(modId, ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stair.get(), 4)
                .define('#', tilesBlock.get()).pattern("#  ").pattern("## ").pattern("###")
                .unlockedBy(getHasName(tilesBlock.get()), has(tilesBlock.get())), RecipeCategory.BUILDING_BLOCKS, consumer, null);

        conditionalModLoadedRecipe(modId, SingleItemRecipeBuilder.stonecutting(Ingredient.of(tilesBlock.get()), RecipeCategory.BUILDING_BLOCKS, stair.get())
                .unlockedBy(getHasName(tilesBlock.get()), has(tilesBlock.get())), RecipeCategory.BUILDING_BLOCKS, consumer,
                getConversionRecipeName(stair.get(), tilesBlock.get()) + "_stonecutting");
        //Slab
        conditionalModLoadedRecipe(modId, ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab.get(), 6)
                .define('#', tilesBlock.get()).pattern("###")
                .unlockedBy(getHasName(tilesBlock.get()), has(tilesBlock.get())), RecipeCategory.BUILDING_BLOCKS, consumer, null);

        conditionalModLoadedRecipe(modId, SingleItemRecipeBuilder.stonecutting(Ingredient.of(tilesBlock.get()), RecipeCategory.BUILDING_BLOCKS, slab.get(), 2)
                .unlockedBy(getHasName(tilesBlock.get()), has(tilesBlock.get())), RecipeCategory.BUILDING_BLOCKS, consumer,
                getConversionRecipeName(slab.get(), tilesBlock.get()) + "_stonecutting");
        //Wall
        conditionalModLoadedRecipe(modId, ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wall.get(), 6)
                .define('#', tilesBlock.get()).pattern("###").pattern("###")
                .unlockedBy(getHasName(tilesBlock.get()), has(tilesBlock.get())), RecipeCategory.BUILDING_BLOCKS, consumer, null);

        conditionalModLoadedRecipe(modId, SingleItemRecipeBuilder.stonecutting(Ingredient.of(tilesBlock.get()), RecipeCategory.BUILDING_BLOCKS, wall.get())
                .unlockedBy(getHasName(tilesBlock.get()), has(tilesBlock.get())), RecipeCategory.BUILDING_BLOCKS, consumer,
                getConversionRecipeName(wall.get(), tilesBlock.get()) + "_stonecutting");
    }

    public static void conditionalModLoadedRecipe(String modId, RecipeBuilder recipe, RecipeCategory category, Consumer<FinishedRecipe> consumer, @Nullable String customPath) {
        ResourceLocation recipeId = customPath == null
                ? Cookielicious.modPrefix("conditional/" + RecipeBuilder.getDefaultRecipeId(recipe.getResult()).getPath())
                : Cookielicious.modPrefix("conditional/" + customPath);

        ConditionalRecipe.builder()
                .addCondition(new ModLoadedCondition(modId))
                .addRecipe(consumer1 -> recipe.save(consumer1, recipeId))
                .generateAdvancement(new ResourceLocation(recipeId.getNamespace(), "recipes/" + category.getFolderName() + "/" + recipeId.getPath()))
                .build(consumer, recipeId);
    }

    public static void conditionalRecipe(ICondition condition, RecipeBuilder recipe, RecipeCategory category, Consumer<FinishedRecipe> consumer, String customPath) {
        ResourceLocation recipeId = customPath == null
                ? Cookielicious.modPrefix("conditional/" + RecipeBuilder.getDefaultRecipeId(recipe.getResult()).getPath())
                : Cookielicious.modPrefix("conditional/" + customPath);

        ConditionalRecipe.builder()
                .addCondition(condition)
                .addRecipe(consumer1 -> recipe.save(consumer1, recipeId))
                .generateAdvancement(new ResourceLocation(recipeId.getNamespace(), "recipes/" + category.getFolderName() + "/" + recipeId.getPath()))
                .build(consumer, recipeId);
    }

    public static Item getItem(String modId, String id) {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(modId, id));
    }
}