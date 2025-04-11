package com.evoslab.cookielicious.datagen.server;

import com.evoslab.cookielicious.common.core.Cookielicious;
import com.evoslab.cookielicious.common.core.other.CookieliciousCompat;
import com.evoslab.cookielicious.common.core.registry.CookieliciousBlocks;
import com.evoslab.cookielicious.common.core.registry.CookieliciousItems;
import com.evoslab.cookielicious.common.core.registry.util.CookieTileSet;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
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

        addModLoadedCookieRecipe(CookieliciousCompat.SEASONALS, CookieliciousItems.BEETROOT_COOKIE, () -> getItem(CookieliciousCompat.SEASONALS, "roasted_beetroot"), consumer);
        addModLoadedCookieRecipe(CookieliciousCompat.SEASONALS, CookieliciousItems.PUMPKIN_COOKIE, () -> getItem(CookieliciousCompat.SEASONALS, "pumpkin_puree"), consumer);


        addCookieTileRecipes(() -> Items.COOKIE, CookieliciousBlocks.NORMAL_COOKIE, consumer);
        addCookieTileRecipes(CookieliciousItems.VANILLA_COOKIE, CookieliciousBlocks.VANILLA_COOKIE, consumer);
        addCookieTileRecipes(CookieliciousItems.STRAWBERRY_COOKIE, CookieliciousBlocks.STRAWBERRY_COOKIE, consumer);
        addCookieTileRecipes(CookieliciousItems.CHOCOLATE_COOKIE, CookieliciousBlocks.CHOCOLATE_COOKIE, consumer);
        addCookieTileRecipes(CookieliciousItems.BANANA_COOKIE, CookieliciousBlocks.BANANA_COOKIE, consumer);
        addCookieTileRecipes(CookieliciousItems.MINT_COOKIE, CookieliciousBlocks.MINT_COOKIE, consumer);
        addCookieTileRecipes(CookieliciousItems.ADZUKI_COOKIE, CookieliciousBlocks.ADZUKI_COOKIE, consumer);

        addModLoadedCookieTileRecipes(CookieliciousCompat.SEASONALS, CookieliciousItems.BEETROOT_COOKIE, CookieliciousBlocks.BEETROOT_COOKIE, consumer);
        addModLoadedCookieTileRecipes(CookieliciousCompat.SEASONALS, CookieliciousItems.PUMPKIN_COOKIE, CookieliciousBlocks.PUMPKIN_COOKIE, consumer);
        addModLoadedCookieTileRecipes(CookieliciousCompat.FARMERS_DELIGHT, () -> getItem(CookieliciousCompat.FARMERS_DELIGHT, "honey_cookie"), CookieliciousBlocks.HONEY_COOKIE, consumer);
        addModLoadedCookieTileRecipes(CookieliciousCompat.FARMERS_DELIGHT, () -> getItem(CookieliciousCompat.FARMERS_DELIGHT, "sweet_berry_cookie"), CookieliciousBlocks.SWEET_BERRY_COOKIE, consumer);
        addModLoadedCookieTileRecipes(CookieliciousCompat.ABNORMALS_DELIGHT, () -> getItem(CookieliciousCompat.ABNORMALS_DELIGHT, "cherry_cookie"), CookieliciousBlocks.CHERRY_COOKIE, consumer);
        addModLoadedCookieTileRecipes(CookieliciousCompat.ABNORMALS_DELIGHT, () -> getItem(CookieliciousCompat.ABNORMALS_DELIGHT, "mulberry_cookie"), CookieliciousBlocks.MULBERRY_COOKIE, consumer);
        addModLoadedCookieTileRecipes(CookieliciousCompat.ABNORMALS_DELIGHT, () -> getItem(CookieliciousCompat.ABNORMALS_DELIGHT, "maple_cookie"), CookieliciousBlocks.MAPLE_COOKIE, consumer);
        addModLoadedCookieTileRecipes(CookieliciousCompat.MINERS_DELIGHT, () -> getItem(CookieliciousCompat.MINERS_DELIGHT, "bat_cookie"), CookieliciousBlocks.BAT_COOKIE, consumer);
        addModLoadedCookieTileRecipes(CookieliciousCompat.FARMERS_RESPITE, () -> getItem(CookieliciousCompat.FARMERS_RESPITE, "green_tea_cookie"), CookieliciousBlocks.GREEN_TEA_COOKIE, consumer);
        addModLoadedCookieTileRecipes(CookieliciousCompat.FARMERS_RESPITE, () -> getItem(CookieliciousCompat.COLLECTORS_REAP, "lime_cookie"), CookieliciousBlocks.LIME_COOKIE, consumer);

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

    private static void addCookieTileRecipes(Supplier<? extends ItemLike> cookie, CookieTileSet cookieTileSet, Consumer<FinishedRecipe> consumer) {
        final Block tiles = cookieTileSet.tiles().get();

        //Tiles
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, tiles, 4)
                .define('#', cookie.get()).pattern("##").pattern("##")
                .unlockedBy(getHasName(cookie.get()), has(cookie.get()))
                .save(consumer);
        //Stairs
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, cookieTileSet.stairs().get(), 4)
                .define('#', tiles).pattern("#  ").pattern("## ").pattern("###")
                .unlockedBy(getHasName(tiles), has(tiles))
                .save(consumer);

        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, cookieTileSet.stairs().get(), tiles);

        //Slab
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, cookieTileSet.slab().get(), 6)
                .define('#', tiles).pattern("###")
                .unlockedBy(getHasName(tiles), has(tiles))
                .save(consumer);

        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, cookieTileSet.slab().get(), tiles, 2);

        //Wall
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, cookieTileSet.wall().get(), 6)
                .define('#', tiles).pattern("###").pattern("###")
                .unlockedBy(getHasName(tiles), has(tiles))
                .save(consumer);

        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, cookieTileSet.wall().get(), tiles);
    }

    private static void addModLoadedCookieTileRecipes(String modId, Supplier<? extends ItemLike> cookie,
                                                      CookieTileSet cookieTileSet, Consumer<FinishedRecipe> consumer) {
        final Block tiles = cookieTileSet.tiles().get();

        //Tiles
        conditionalModLoadedRecipe(modId, ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, tiles, 4)
                .define('#', cookie.get()).pattern("##").pattern("##")
                .unlockedBy(getHasName(cookie.get()), has(cookie.get())), RecipeCategory.BUILDING_BLOCKS, consumer, null);
        //Stairs
        conditionalModLoadedRecipe(modId, ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, cookieTileSet.stairs().get(), 4)
                .define('#', tiles).pattern("#  ").pattern("## ").pattern("###")
                .unlockedBy(getHasName(tiles), has(tiles)), RecipeCategory.BUILDING_BLOCKS, consumer, null);

        conditionalModLoadedRecipe(modId, SingleItemRecipeBuilder.stonecutting(Ingredient.of(tiles), RecipeCategory.BUILDING_BLOCKS, cookieTileSet.stairs().get())
                .unlockedBy(getHasName(tiles), has(tiles)), RecipeCategory.BUILDING_BLOCKS, consumer,
                getConversionRecipeName(cookieTileSet.stairs().get(), tiles) + "_stonecutting");
        //Slab
        conditionalModLoadedRecipe(modId, ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, cookieTileSet.slab().get(), 6)
                .define('#', tiles).pattern("###")
                .unlockedBy(getHasName(tiles), has(tiles)), RecipeCategory.BUILDING_BLOCKS, consumer, null);

        conditionalModLoadedRecipe(modId, SingleItemRecipeBuilder.stonecutting(Ingredient.of(tiles), RecipeCategory.BUILDING_BLOCKS, cookieTileSet.slab().get(), 2)
                .unlockedBy(getHasName(tiles), has(tiles)), RecipeCategory.BUILDING_BLOCKS, consumer,
                getConversionRecipeName(cookieTileSet.slab().get(), tiles) + "_stonecutting");
        //Wall
        conditionalModLoadedRecipe(modId, ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, cookieTileSet.wall().get(), 6)
                .define('#', tiles).pattern("###").pattern("###")
                .unlockedBy(getHasName(tiles), has(tiles)), RecipeCategory.BUILDING_BLOCKS, consumer, null);

        conditionalModLoadedRecipe(modId, SingleItemRecipeBuilder.stonecutting(Ingredient.of(tiles), RecipeCategory.BUILDING_BLOCKS, cookieTileSet.wall().get())
                .unlockedBy(getHasName(tiles), has(tiles)), RecipeCategory.BUILDING_BLOCKS, consumer,
                getConversionRecipeName(cookieTileSet.wall().get(), tiles) + "_stonecutting");
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