package com.cookiejar.cookielicious.datagen.server;

import com.cookiejar.cookielicious.common.core.Cookielicious;
import com.cookiejar.cookielicious.common.core.other.CookieliciousCompat;
import com.cookiejar.cookielicious.common.core.registry.CookieliciousBlocks;
import com.cookiejar.cookielicious.common.core.registry.CookieliciousItems;
import com.cookiejar.cookielicious.common.core.registry.util.CookieTileSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class CRecipeProvider extends RecipeProvider {


    public CRecipeProvider(DataGenerator generator, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(generator.getPackOutput(), lookupProvider);
    }

    // TODO - Readd commented out recipe gen when the relevant mods update
    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        addCookieRecipe(CookieliciousItems.VANILLA_COOKIE, () -> getItem(CookieliciousCompat.NEAPOLITAN, "dried_vanilla_pods"), recipeOutput);
        addCookieRecipe(CookieliciousItems.STRAWBERRY_COOKIE, () -> getItem(CookieliciousCompat.NEAPOLITAN, "strawberries"), recipeOutput);
        addCookieRecipe(CookieliciousItems.CHOCOLATE_COOKIE, () -> getItem(CookieliciousCompat.NEAPOLITAN, "chocolate_bar"), recipeOutput);
        addCookieRecipe(CookieliciousItems.MINT_COOKIE, () -> getItem(CookieliciousCompat.NEAPOLITAN, "mint_leaves"), recipeOutput);
        addCookieRecipe(CookieliciousItems.BANANA_COOKIE, () -> getItem(CookieliciousCompat.NEAPOLITAN, "banana"), recipeOutput);
        addCookieRecipe(CookieliciousItems.ADZUKI_COOKIE, () -> getItem(CookieliciousCompat.NEAPOLITAN, "roasted_adzuki_beans"), recipeOutput);

        //addModLoadedCookieRecipe(CookieliciousCompat.SEASONALS, CookieliciousItems.BEETROOT_COOKIE, () -> getItem(CookieliciousCompat.SEASONALS, "roasted_beetroot"), recipeOutput);
        //addModLoadedCookieRecipe(CookieliciousCompat.SEASONALS, CookieliciousItems.PUMPKIN_COOKIE, () -> getItem(CookieliciousCompat.SEASONALS, "pumpkin_puree"), recipeOutput);

        addCookieTileRecipes(() -> Items.COOKIE, CookieliciousBlocks.NORMAL_COOKIE, recipeOutput);
        addCookieTileRecipes(CookieliciousItems.VANILLA_COOKIE, CookieliciousBlocks.VANILLA_COOKIE, recipeOutput);
        addCookieTileRecipes(CookieliciousItems.STRAWBERRY_COOKIE, CookieliciousBlocks.STRAWBERRY_COOKIE, recipeOutput);
        addCookieTileRecipes(CookieliciousItems.CHOCOLATE_COOKIE, CookieliciousBlocks.CHOCOLATE_COOKIE, recipeOutput);
        addCookieTileRecipes(CookieliciousItems.BANANA_COOKIE, CookieliciousBlocks.BANANA_COOKIE, recipeOutput);
        addCookieTileRecipes(CookieliciousItems.MINT_COOKIE, CookieliciousBlocks.MINT_COOKIE, recipeOutput);
        addCookieTileRecipes(CookieliciousItems.ADZUKI_COOKIE, CookieliciousBlocks.ADZUKI_COOKIE, recipeOutput);

        //addModLoadedCookieTileRecipes(CookieliciousCompat.SEASONALS, CookieliciousItems.BEETROOT_COOKIE, CookieliciousBlocks.BEETROOT_COOKIE, recipeOutput);
        //addModLoadedCookieTileRecipes(CookieliciousCompat.SEASONALS, CookieliciousItems.PUMPKIN_COOKIE, CookieliciousBlocks.PUMPKIN_COOKIE, recipeOutput);

        addModLoadedCookieTileRecipes(CookieliciousCompat.FARMERS_DELIGHT, () -> getItem(CookieliciousCompat.FARMERS_DELIGHT, "honey_cookie"), CookieliciousBlocks.HONEY_COOKIE, recipeOutput);
        addModLoadedCookieTileRecipes(CookieliciousCompat.FARMERS_DELIGHT, () -> getItem(CookieliciousCompat.FARMERS_DELIGHT, "sweet_berry_cookie"), CookieliciousBlocks.SWEET_BERRY_COOKIE, recipeOutput);

        addModLoadedCookieTileRecipes(CookieliciousCompat.ABNORMALS_DELIGHT, () -> getItem(CookieliciousCompat.ABNORMALS_DELIGHT, "cherry_cookie"), CookieliciousBlocks.CHERRY_COOKIE, recipeOutput);
        addModLoadedCookieTileRecipes(CookieliciousCompat.ABNORMALS_DELIGHT, () -> getItem(CookieliciousCompat.ABNORMALS_DELIGHT, "mulberry_cookie"), CookieliciousBlocks.MULBERRY_COOKIE, recipeOutput);
        addModLoadedCookieTileRecipes(CookieliciousCompat.ABNORMALS_DELIGHT, () -> getItem(CookieliciousCompat.ABNORMALS_DELIGHT, "maple_cookie"), CookieliciousBlocks.MAPLE_COOKIE, recipeOutput);

        addModLoadedCookieTileRecipes(CookieliciousCompat.MINERS_DELIGHT, () -> getItem(CookieliciousCompat.MINERS_DELIGHT, "bat_cookie"), CookieliciousBlocks.BAT_COOKIE, recipeOutput);

        //addModLoadedCookieTileRecipes(CookieliciousCompat.FARMERS_RESPITE, () -> getItem(CookieliciousCompat.FARMERS_RESPITE, "green_tea_cookie"), CookieliciousBlocks.GREEN_TEA_COOKIE, recipeOutput);
        //addModLoadedCookieTileRecipes(CookieliciousCompat.FARMERS_RESPITE, () -> getItem(CookieliciousCompat.COLLECTORS_REAP, "lime_cookie"), CookieliciousBlocks.LIME_COOKIE, recipeOutput);
    }

    private static void addCookieRecipe(Supplier<? extends ItemLike> result,
                                        Supplier<? extends ItemLike> input, RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, result.get(), 8)
                .define('W', Items.WHEAT)
                .define('R', input.get())
                .pattern("WRW")
                .unlockedBy(getHasName(input.get()), has(input.get()))
                .save(recipeOutput);
    }

    private static void addModLoadedCookieRecipe(String modId, Supplier<? extends ItemLike> result,
                                                 Supplier<? extends ItemLike> input, RecipeOutput recipeOutput) {
        conditionalModLoadedRecipe(modId, ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, result.get(), 8)
                        .define('W', Items.WHEAT)
                        .define('R', input.get())
                        .pattern("WRW")
                        .unlockedBy(getHasName(input.get()), has(input.get())),
                recipeOutput, null
        );
    }

    private static void addCookieTileRecipes(Supplier<? extends ItemLike> cookie, CookieTileSet cookieTileSet, RecipeOutput recipeOutput) {
        final Block tiles = cookieTileSet.tiles().get();

        //Tiles
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, tiles, 4)
                .define('#', cookie.get()).pattern("##").pattern("##")
                .unlockedBy(getHasName(cookie.get()), has(cookie.get()))
                .save(recipeOutput);
        //Stairs
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, cookieTileSet.stairs().get(), 4)
                .define('#', tiles).pattern("#  ").pattern("## ").pattern("###")
                .unlockedBy(getHasName(tiles), has(tiles))
                .save(recipeOutput);

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, cookieTileSet.stairs().get(), tiles);

        //Slab
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, cookieTileSet.slab().get(), 6)
                .define('#', tiles).pattern("###")
                .unlockedBy(getHasName(tiles), has(tiles))
                .save(recipeOutput);

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, cookieTileSet.slab().get(), tiles, 2);

        //Wall
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, cookieTileSet.wall().get(), 6)
                .define('#', tiles).pattern("###").pattern("###")
                .unlockedBy(getHasName(tiles), has(tiles))
                .save(recipeOutput);

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, cookieTileSet.wall().get(), tiles);
    }

    private static void addModLoadedCookieTileRecipes(String modId, Supplier<? extends ItemLike> cookie,
                                                      CookieTileSet cookieTileSet, RecipeOutput recipeOutput) {
        final Block tiles = cookieTileSet.tiles().get();

        //Tiles
        conditionalModLoadedRecipe(modId, ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, tiles, 4)
                .define('#', cookie.get()).pattern("##").pattern("##")
                .unlockedBy(getHasName(cookie.get()), has(cookie.get())), recipeOutput, null);
        //Stairs
        conditionalModLoadedRecipe(modId, ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, cookieTileSet.stairs().get(), 4)
                .define('#', tiles).pattern("#  ").pattern("## ").pattern("###")
                .unlockedBy(getHasName(tiles), has(tiles)), recipeOutput, null);

        conditionalModLoadedRecipe(modId, SingleItemRecipeBuilder.stonecutting(Ingredient.of(tiles), RecipeCategory.BUILDING_BLOCKS, cookieTileSet.stairs().get())
                        .unlockedBy(getHasName(tiles), has(tiles)), recipeOutput,
                getConversionRecipeName(cookieTileSet.stairs().get(), tiles) + "_stonecutting");
        //Slab
        conditionalModLoadedRecipe(modId, ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, cookieTileSet.slab().get(), 6)
                .define('#', tiles).pattern("###")
                .unlockedBy(getHasName(tiles), has(tiles)), recipeOutput, null);

        conditionalModLoadedRecipe(modId, SingleItemRecipeBuilder.stonecutting(Ingredient.of(tiles), RecipeCategory.BUILDING_BLOCKS, cookieTileSet.slab().get(), 2)
                        .unlockedBy(getHasName(tiles), has(tiles)), recipeOutput,
                getConversionRecipeName(cookieTileSet.slab().get(), tiles) + "_stonecutting");
        //Wall
        conditionalModLoadedRecipe(modId, ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, cookieTileSet.wall().get(), 6)
                .define('#', tiles).pattern("###").pattern("###")
                .unlockedBy(getHasName(tiles), has(tiles)), recipeOutput, null);

        conditionalModLoadedRecipe(modId, SingleItemRecipeBuilder.stonecutting(Ingredient.of(tiles), RecipeCategory.BUILDING_BLOCKS, cookieTileSet.wall().get())
                        .unlockedBy(getHasName(tiles), has(tiles)), recipeOutput,
                getConversionRecipeName(cookieTileSet.wall().get(), tiles) + "_stonecutting");
    }

    public static void conditionalModLoadedRecipe(String modId, RecipeBuilder recipe, RecipeOutput recipeOutput, @Nullable String customPath) {
        ResourceLocation recipeId = customPath == null
                ? Cookielicious.modPrefix("conditional/" + RecipeBuilder.getDefaultRecipeId(recipe.getResult()).getPath())
                : Cookielicious.modPrefix("conditional/" + customPath);

        RecipeOutput conditionalOutput = recipeOutput.withConditions(new ModLoadedCondition(modId));
        recipe.save(conditionalOutput, recipeId);
    }

    public static void conditionalRecipe(RecipeBuilder recipe, RecipeOutput recipeOutput, String customPath, ICondition... conditions) {
        ResourceLocation recipeId = customPath == null
                ? Cookielicious.modPrefix("conditional/" + RecipeBuilder.getDefaultRecipeId(recipe.getResult()).getPath())
                : Cookielicious.modPrefix("conditional/" + customPath);

        RecipeOutput conditionalOutput = recipeOutput.withConditions(conditions);
        recipe.save(conditionalOutput, recipeId);
    }

    public static Item getItem(String modId, String id) {
        Item item = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(modId, id));
        if (item == Items.AIR) {
            throw new IllegalStateException("Item " + id + " does not exist in the registry!");
        }
        return item;
    }
}