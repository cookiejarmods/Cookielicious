package com.evoslab.cookielicious.common.core.registry;

import com.evoslab.cookielicious.common.core.Cookielicious;
import com.evoslab.cookielicious.common.item.CookieItem;
import com.evoslab.cookielicious.common.item.HealingCookieItem;
import com.evoslab.cookielicious.common.core.other.CookieliciousCompat;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import com.teamabnormals.neapolitan.core.Neapolitan;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Cookielicious.MOD_ID, bus = Bus.MOD)
public class CookieliciousItems {

	public static final ItemSubRegistryHelper HELPER = Cookielicious.REGISTRY_HELPER.getItemSubHelper();

	public static final Map<ResourceKey<CreativeModeTab>, List<RegistryObject<? extends Item>>> TABS_FOR_ITEMS = new HashMap<>();
	public static final List<RegistryObject<Item>> ALL_COOKIES = new ArrayList<>();




	public static final RegistryObject<Item> VANILLA_COOKIE = createCookie("vanilla_cookie", () -> new CookieItem(Properties.VANILLA));
    public static final RegistryObject<Item> CHOCOLATE_COOKIE = createCookie("chocolate_cookie", () -> new CookieItem(Properties.CHOCOLATE));
    public static final RegistryObject<Item> STRAWBERRY_COOKIE = createCookie("strawberry_cookie", () -> new HealingCookieItem(1F, Properties.STRAWBERRY));
    public static final RegistryObject<Item> BANANA_COOKIE = createCookie("banana_cookie", () -> new CookieItem(Properties.BANANA));
    public static final RegistryObject<Item> MINT_COOKIE = createCookie("mint_cookie", () -> new CookieItem(Properties.MINT));
    public static final RegistryObject<Item> ADZUKI_COOKIE = createCookie("adzuki_cookie", () -> new CookieItem(Properties.ADZUKI));
    public static final RegistryObject<Item> BEETROOT_COOKIE = createCompatCookie("beetroot_cookie", () -> new CookieItem(Properties.BEETROOT), Set.of(CookieliciousCompat.SEASONALS));
    public static final RegistryObject<Item> PUMPKIN_COOKIE = createCompatCookie("pumpkin_cookie", () -> new CookieItem(Properties.PUMPKIN), Set.of(CookieliciousCompat.SEASONALS));




	protected static RegistryObject<Item> createCookie(String name, Supplier<Item> itemSupplier) {
		RegistryObject<Item> regObj = createItem(name, itemSupplier, CreativeModeTabs.FOOD_AND_DRINKS);
		ALL_COOKIES.add(regObj);
		return regObj;
	}

	protected static RegistryObject<Item> createCompatCookie(String name, Supplier<Item> itemSupplier, Set<String> modIds) {
		RegistryObject<Item> regObj = createCompatItem(name, itemSupplier, modIds, CreativeModeTabs.FOOD_AND_DRINKS);
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

	public static void onCreativeTabPopulate(BuildCreativeModeTabContentsEvent event) {
		if (TABS_FOR_ITEMS.containsKey(event.getTabKey())) {
			List<RegistryObject<? extends Item>> items = TABS_FOR_ITEMS.get(event.getTabKey());
			items.forEach((regObj) -> event.accept(regObj.get()));
		}
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

    	public static Item.Properties getCookieProps(MobEffect effect) {
    		return getCookieProps().food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(effect, 120, 0), 1F).build());
    	}
    	
    	public static Item.Properties getCookieProps() {
    		return new Item.Properties().food(new FoodProperties.Builder()
					.nutrition(2)
					.saturationMod(1F)
    				.fast()
    				.build());
    	}
    }
    
    public static class Effects {
    	public static final MobEffect VANILLA_SCENT = getEffect(Neapolitan.MOD_ID, "vanilla_scent");
    	public static final MobEffect SUGAR_RUSH = getEffect(Neapolitan.MOD_ID, "sugar_rush");
    	public static final MobEffect AGILITY = getEffect(Neapolitan.MOD_ID, "agility");
    	public static final MobEffect BERSERKING = getEffect(Neapolitan.MOD_ID, "berserking");
    	public static final MobEffect HARMONY = getEffect(Neapolitan.MOD_ID, "harmony");
    	public static final MobEffect ROOTED = getEffect(CookieliciousCompat.SEASONALS, "rooted");
    	public static final MobEffect STUFFED = getEffect(CookieliciousCompat.SEASONALS, "stuffed");

    	public static MobEffect getEffect(String modId, String id) {
    		return ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(modId, id));
    	}
    }
}
