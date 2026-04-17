package com.evoslab.cookielicious.common.core.registry;

import com.evoslab.cookielicious.common.core.Cookielicious;
import com.evoslab.cookielicious.common.core.other.CookieliciousCompat;
import com.evoslab.cookielicious.common.item.CookieItem;
import com.evoslab.cookielicious.common.item.HealingCookieItem;
import com.teamabnormals.blueprint.core.util.item.CreativeModeTabContentsPopulator;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import com.teamabnormals.neapolitan.core.Neapolitan;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

import static net.minecraft.world.item.CreativeModeTabs.BUILDING_BLOCKS;
import static net.minecraft.world.item.CreativeModeTabs.FOOD_AND_DRINKS;
import static net.minecraft.world.item.crafting.Ingredient.of;

@Mod.EventBusSubscriber(modid = Cookielicious.MOD_ID, bus = Bus.MOD)
public class CookieliciousItems {

    public static final ItemSubRegistryHelper HELPER = Cookielicious.REGISTRY_HELPER.getItemSubHelper();

    public static final Map<ResourceKey<CreativeModeTab>, List<RegistryObject<? extends Item>>> TABS_FOR_ITEMS = new HashMap<>();
    /**
     * All cookies added by Cookielicious
     */
    public static final List<RegistryObject<Item>> ALL_COOKIES = new ArrayList<>();


    public static final RegistryObject<Item> VANILLA_COOKIE = createCookie("vanilla_cookie", () -> new CookieItem(Properties.VANILLA.get()));
    public static final RegistryObject<Item> CHOCOLATE_COOKIE = createCookie("chocolate_cookie", () -> new CookieItem(Properties.CHOCOLATE.get()));
    public static final RegistryObject<Item> STRAWBERRY_COOKIE = createCookie("strawberry_cookie", () -> new HealingCookieItem(1.0F, Properties.createCookieProps().get()));
    public static final RegistryObject<Item> BANANA_COOKIE = createCookie("banana_cookie", () -> new CookieItem(Properties.BANANA.get()));
    public static final RegistryObject<Item> MINT_COOKIE = createCookie("mint_cookie", () -> new CookieItem(Properties.MINT.get()));
    public static final RegistryObject<Item> ADZUKI_COOKIE = createCookie("adzuki_cookie", () -> new CookieItem(Properties.ADZUKI.get()));
    public static final RegistryObject<Item> BEETROOT_COOKIE = createCompatCookie("beetroot_cookie", () -> new CookieItem(Properties.BEETROOT.get()), Set.of(CookieliciousCompat.SEASONALS));
    public static final RegistryObject<Item> PUMPKIN_COOKIE = createCompatCookie("pumpkin_cookie", () -> new CookieItem(Properties.PUMPKIN.get()), Set.of(CookieliciousCompat.SEASONALS));


    protected static RegistryObject<Item> createCookie(String name, Supplier<Item> itemSupplier) {
        RegistryObject<Item> regObj = createItem(name, itemSupplier, FOOD_AND_DRINKS);
        ALL_COOKIES.add(regObj);
        return regObj;
    }

    protected static RegistryObject<Item> createCompatCookie(String name, Supplier<Item> itemSupplier, Set<String> modIds) {
        RegistryObject<Item> regObj = createCompatItem(name, itemSupplier, modIds, FOOD_AND_DRINKS);
        ALL_COOKIES.add(regObj);
        return regObj;
    }

    @SafeVarargs
    protected static RegistryObject<Item> createItem(String name, Supplier<Item> itemSupplier, @Nullable ResourceKey<CreativeModeTab>... creativeModeTabs) {
        RegistryObject<Item> regObj = HELPER.createItem(name, itemSupplier);

        if (creativeModeTabs != null) {
            queueForCreativeTabs(regObj, creativeModeTabs);
        }
        return regObj;
    }

    @SuppressWarnings("SameParameterValue")
    @SafeVarargs
    protected static RegistryObject<Item> createCompatItem(String name, Supplier<Item> itemSupplier, Set<String> modids, @Nullable ResourceKey<CreativeModeTab>... creativeModeTabs) {
        RegistryObject<Item> regObj = HELPER.createItem(name, itemSupplier);
        boolean addToTabs = true;

        for (String modid : modids) {
            if (!ModList.get().isLoaded(modid)) {
                addToTabs = false;
                break;
            }
        }

        if (creativeModeTabs != null && addToTabs) {
            queueForCreativeTabs(regObj, creativeModeTabs);
        }
        return regObj;
    }

    @SafeVarargs
    protected static void queueForCreativeTabs(RegistryObject<? extends Item> item, ResourceKey<CreativeModeTab>... creativeTabs) {
        for (ResourceKey<CreativeModeTab> tab : creativeTabs) {
            if (!TABS_FOR_ITEMS.containsKey(tab)) {
                List<RegistryObject<? extends Item>> list = new ArrayList<>();
                list.add(item);
                TABS_FOR_ITEMS.put(tab, list);
            } else {
                TABS_FOR_ITEMS.get(tab).add(item);
            }
        }
    }

    public static void setupTabEditors() {
        CreativeModeTabContentsPopulator.Entry entry = CreativeModeTabContentsPopulator.mod(Neapolitan.MOD_ID);

        // Cookies
        entry.tab(FOOD_AND_DRINKS);
        ALL_COOKIES.forEach((regObj) -> {
            if (TABS_FOR_ITEMS.get(FOOD_AND_DRINKS).contains(regObj)) {
                entry.addItemsAfter(of(Items.COOKIE), regObj);
            }
        });

        // Cookie blocks
        entry.tab(BUILDING_BLOCKS);
        CookieliciousBlocks.ALL_COOKIE_BLOCKS.forEach((cookieTileSet) -> {
            if (TABS_FOR_ITEMS.get(BUILDING_BLOCKS).contains(cookieTileSet.tiles())) {
                entry.addItems(cookieTileSet.tiles());
            }
            if (TABS_FOR_ITEMS.get(BUILDING_BLOCKS).contains(cookieTileSet.stairs())) {
                entry.addItems(cookieTileSet.stairs());
            }
            if (TABS_FOR_ITEMS.get(BUILDING_BLOCKS).contains(cookieTileSet.slab())) {
                entry.addItems(cookieTileSet.slab());
            }
            if (TABS_FOR_ITEMS.get(BUILDING_BLOCKS).contains(cookieTileSet.wall())) {
                entry.addItems(cookieTileSet.wall());
            }
        });
    }

    public static class Properties {
        public static final Supplier<Item.Properties> VANILLA = createCookieProps(Effects.VANILLA_SCENT);
        public static final Supplier<Item.Properties> CHOCOLATE = createCookieProps(Effects.SUGAR_RUSH);
        public static final Supplier<Item.Properties> BANANA = createCookieProps(Effects.AGILITY);
        public static final Supplier<Item.Properties> MINT = createCookieProps(Effects.BERSERKING);
        public static final Supplier<Item.Properties> ADZUKI = createCookieProps(Effects.HARMONY);
        public static final Supplier<Item.Properties> BEETROOT = createCookieProps(Effects.ROOTED);
        public static final Supplier<Item.Properties> PUMPKIN = createCookieProps(Effects.STUFFED);

        protected static Supplier<Item.Properties> createCookieProps(Supplier<MobEffect> effect) {
            return () -> new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationMod(0.1F)
                    .fast()
                    .effect(() -> new MobEffectInstance(effect.get(), 120, 0), 1F)
                    .build());
        }

        protected static Supplier<Item.Properties> createCookieProps() {
            return () -> new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationMod(0.1F)
                    .fast()
                    .build());
        }
    }

    public static class Effects {
        public static final Supplier<MobEffect> VANILLA_SCENT = getEffect(Neapolitan.MOD_ID, "vanilla_scent");
        public static final Supplier<MobEffect> SUGAR_RUSH = getEffect(Neapolitan.MOD_ID, "sugar_rush");
        public static final Supplier<MobEffect> AGILITY = getEffect(Neapolitan.MOD_ID, "agility");
        public static final Supplier<MobEffect> BERSERKING = getEffect(Neapolitan.MOD_ID, "berserking");
        public static final Supplier<MobEffect> HARMONY = getEffect(Neapolitan.MOD_ID, "harmony");
        public static final Supplier<MobEffect> ROOTED = getEffect(CookieliciousCompat.SEASONALS, "rooted");
        public static final Supplier<MobEffect> STUFFED = getEffect(CookieliciousCompat.SEASONALS, "stuffed");

        protected static Supplier<MobEffect> getEffect(String modId, String id) {
            return () -> ForgeRegistries.MOB_EFFECTS.getValue(ResourceLocation.fromNamespaceAndPath(modId, id));
        }
    }
}
