package com.cookiejar.cookielicious.common.core.registry;

import com.cookiejar.cookielicious.common.core.Cookielicious;
import com.cookiejar.cookielicious.common.core.other.CookieliciousCompat;
import com.cookiejar.cookielicious.common.item.CookieItem;
import com.cookiejar.cookielicious.common.item.HealingCookieItem;
import com.teamabnormals.blueprint.core.util.item.CreativeModeTabContentsPopulator;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import com.teamabnormals.neapolitan.core.Neapolitan;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.registries.DeferredItem;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

import static net.minecraft.world.item.CreativeModeTabs.BUILDING_BLOCKS;
import static net.minecraft.world.item.CreativeModeTabs.FOOD_AND_DRINKS;
import static net.minecraft.world.item.crafting.Ingredient.of;

public class CookieliciousItems {

    public static final ItemSubRegistryHelper HELPER = Cookielicious.REGISTRY_HELPER.getItemSubHelper();

    public static final Map<ResourceKey<CreativeModeTab>, List<DeferredItem<? extends Item>>> TABS_FOR_ITEMS = new HashMap<>();
    /**
     * All cookies added by Cookielicious
     */
    public static final List<DeferredItem<Item>> ALL_COOKIES = new ArrayList<>();

    public static final DeferredItem<Item> VANILLA_COOKIE = createCookie("vanilla_cookie", () -> new CookieItem(Properties.VANILLA));
    public static final DeferredItem<Item> CHOCOLATE_COOKIE = createCookie("chocolate_cookie", () -> new CookieItem(Properties.CHOCOLATE));
    public static final DeferredItem<Item> STRAWBERRY_COOKIE = createCookie("strawberry_cookie", () -> new HealingCookieItem(1.0F, Properties.STRAWBERRY));
    public static final DeferredItem<Item> BANANA_COOKIE = createCookie("banana_cookie", () -> new CookieItem(Properties.BANANA));
    public static final DeferredItem<Item> MINT_COOKIE = createCookie("mint_cookie", () -> new CookieItem(Properties.MINT));
    public static final DeferredItem<Item> ADZUKI_COOKIE = createCookie("adzuki_cookie", () -> new CookieItem(Properties.ADZUKI));
    public static final DeferredItem<Item> BEETROOT_COOKIE = createCompatCookie("beetroot_cookie", () -> new CookieItem(Properties.BEETROOT), Set.of(CookieliciousCompat.SEASONALS));
    public static final DeferredItem<Item> PUMPKIN_COOKIE = createCompatCookie("pumpkin_cookie", () -> new CookieItem(Properties.PUMPKIN), Set.of(CookieliciousCompat.SEASONALS));


    protected static DeferredItem<Item> createCookie(String name, Supplier<Item> itemSupplier) {
        DeferredItem<Item> item = createItem(name, itemSupplier, FOOD_AND_DRINKS);
        ALL_COOKIES.add(item);
        return item;
    }

    protected static DeferredItem<Item> createCompatCookie(String name, Supplier<Item> itemSupplier, Set<String> modIds) {
        DeferredItem<Item> item = createCompatItem(name, itemSupplier, modIds, FOOD_AND_DRINKS);
        ALL_COOKIES.add(item);
        return item;
    }

    @SafeVarargs
    protected static DeferredItem<Item> createItem(String name, Supplier<Item> itemSupplier, @Nullable ResourceKey<CreativeModeTab>... creativeModeTabs) {
        DeferredItem<Item> item = HELPER.createItem(name, itemSupplier);

        if (creativeModeTabs != null) {
            queueForCreativeTabs(item, creativeModeTabs);
        }
        return item;
    }

    @SafeVarargs
    @SuppressWarnings("SameParameterValue")
    protected static DeferredItem<Item> createCompatItem(String name, Supplier<Item> itemSupplier, Set<String> modids, @Nullable ResourceKey<CreativeModeTab>... creativeModeTabs) {
        DeferredItem<Item> item = HELPER.createItem(name, itemSupplier);
        boolean addToTabs = true;

        for (String modid : modids) {
            if (!ModList.get().isLoaded(modid)) {
                addToTabs = false;
                break;
            }
        }
        if (creativeModeTabs != null && addToTabs) {
            queueForCreativeTabs(item, creativeModeTabs);
        }
        return item;
    }

    @SafeVarargs
    protected static void queueForCreativeTabs(DeferredItem<? extends Item> item, ResourceKey<CreativeModeTab>... creativeTabs) {
        for (ResourceKey<CreativeModeTab> tab : creativeTabs) {
            if (!TABS_FOR_ITEMS.containsKey(tab)) {
                List<DeferredItem<? extends Item>> list = new ArrayList<>();
                list.add(item);
                TABS_FOR_ITEMS.put(tab, list);
            } else {
                TABS_FOR_ITEMS.get(tab).add(item);
            }
        }
    }

    public static void setupTabEditors() {
        final CreativeModeTabContentsPopulator.Entry entry = CreativeModeTabContentsPopulator.mod(Neapolitan.MOD_ID);
        final ResourceLocation strawberryScones = ResourceLocation.fromNamespaceAndPath(CookieliciousCompat.NEAPOLITAN, "strawberry_scones");

        // Cookies
        entry.tab(FOOD_AND_DRINKS);
        ALL_COOKIES.forEach((regObj) -> {
            if (TABS_FOR_ITEMS.get(FOOD_AND_DRINKS).contains(regObj)) {
                // Add all the cookies right before Neapolitan's strawberry scones.
                entry.addItemsBefore(of(BuiltInRegistries.ITEM.get(strawberryScones)), regObj);
            }
        });

        // Cookie blocks
        entry.tab(BUILDING_BLOCKS);
        CookieliciousBlocks.ALL_COOKIE_BLOCKS.forEach((cookieTileSet) -> {
            List<Item> items = TABS_FOR_ITEMS.get(BUILDING_BLOCKS).stream().map(DeferredItem::asItem).toList();

            if (items.contains(cookieTileSet.tiles().asItem())) {
                entry.addItems(cookieTileSet.tiles());
            }
            if (items.contains(cookieTileSet.stairs().asItem())) {
                entry.addItems(cookieTileSet.stairs());
            }
            if (items.contains(cookieTileSet.slab().asItem())) {
                entry.addItems(cookieTileSet.slab());
            }
            if (items.contains(cookieTileSet.wall().asItem())) {
                entry.addItems(cookieTileSet.wall());
            }
        });
    }

    public static class Properties {
        public static final Item.Properties STRAWBERRY = getCookieProps();
        public static final Item.Properties VANILLA = getCookieProps(Effects.VANILLA_SCENT);
        public static final Item.Properties CHOCOLATE = getCookieProps(Effects.SUGAR_RUSH);
        public static final Item.Properties BANANA = getCookieProps(Effects.AGILITY);
        public static final Item.Properties MINT = getCookieProps(Effects.BERSERKING);
        public static final Item.Properties ADZUKI = getCookieProps(Effects.HARMONY);
        public static final Item.Properties BEETROOT = getCookieProps(Effects.ROOTED);
        public static final Item.Properties PUMPKIN = getCookieProps(Effects.STUFFED);

        public static Item.Properties getCookieProps(Holder<MobEffect> effect) {
            return new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(0.1F)
                    .fast()
                    .effect(() -> new MobEffectInstance(effect, 120, 0), 1F)
                    .build());
        }

        public static Item.Properties getCookieProps() {
            return new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(0.1F)
                    .fast()
                    .build());
        }
    }

    /**
     * Holds mob effect references for easy use later. Any references that ends up
     * pointing to nothing (likely because of a missing dependency), will point
     * to {@link MobEffects#MOVEMENT_SPEED} by default.
     */
    public static class Effects {
        public static final Holder<MobEffect> VANILLA_SCENT = getEffect(Neapolitan.MOD_ID, "vanilla_scent");
        public static final Holder<MobEffect> SUGAR_RUSH = getEffect(Neapolitan.MOD_ID, "sugar_rush");
        public static final Holder<MobEffect> AGILITY = getEffect(Neapolitan.MOD_ID, "agility");
        public static final Holder<MobEffect> BERSERKING = getEffect(Neapolitan.MOD_ID, "berserking");
        public static final Holder<MobEffect> HARMONY = getEffect(Neapolitan.MOD_ID, "harmony");
        public static final Holder<MobEffect> ROOTED = getEffect(CookieliciousCompat.SEASONALS, "rooted");
        public static final Holder<MobEffect> STUFFED = getEffect(CookieliciousCompat.SEASONALS, "stuffed");


        public static Holder<MobEffect> getEffect(String modId, String id) {
            Optional<Holder.Reference<MobEffect>> reference = BuiltInRegistries.MOB_EFFECT.getHolder(ResourceLocation.fromNamespaceAndPath(modId, id));
            if (reference.isPresent()) {
                return reference.get();
            } else {
                // Log a warning in dev environment.
                if (!FMLEnvironment.production) {
                    Cookielicious.LOG.warn("Mob effect with ID '{}' does not exist in the registry! " +
                            "Is it because of a missing dependency or did we mess up?", id);
                }
                return MobEffects.MOVEMENT_SPEED;
            }
        }
    }

    public static void init() {
    }
}
