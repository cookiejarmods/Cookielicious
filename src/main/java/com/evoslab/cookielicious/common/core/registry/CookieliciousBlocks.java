package com.evoslab.cookielicious.common.core.registry;


import com.evoslab.cookielicious.common.core.Cookielicious;
import com.evoslab.cookielicious.common.core.other.CookieliciousCompat;
import com.evoslab.cookielicious.common.core.registry.util.CookieTileSet;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Cookielicious.MOD_ID, bus = Bus.MOD)
public class CookieliciousBlocks {

    public static final BlockSubRegistryHelper HELPER = Cookielicious.REGISTRY_HELPER.getBlockSubHelper();

    public static final Map<RegistryObject<? extends Block>, ToolType> EFFECTIVE_TOOL_MAP = new HashMap<>();
    public static final List<CookieTileSet> ALL_COOKIE_BLOCKS = new ArrayList<>();


    // Cookie tile sets
    public static final CookieTileSet NORMAL_COOKIE = cookieTileSet("cookie", CookieliciousCompat.MINECRAFT);
    public static final CookieTileSet STRAWBERRY_COOKIE = cookieTileSet("strawberry_cookie", CookieliciousCompat.NEAPOLITAN);
    public static final CookieTileSet CHOCOLATE_COOKIE = cookieTileSet("chocolate_cookie", CookieliciousCompat.NEAPOLITAN);
    public static final CookieTileSet VANILLA_COOKIE = cookieTileSet("vanilla_cookie", CookieliciousCompat.NEAPOLITAN);
    public static final CookieTileSet HONEY_COOKIE = cookieTileSet("honey_cookie", CookieliciousCompat.FARMERS_DELIGHT);
    public static final CookieTileSet SWEET_BERRY_COOKIE = cookieTileSet("sweet_berry_cookie", CookieliciousCompat.FARMERS_DELIGHT);
    public static final CookieTileSet BANANA_COOKIE = cookieTileSet("banana_cookie", CookieliciousCompat.NEAPOLITAN);
    public static final CookieTileSet MINT_COOKIE = cookieTileSet("mint_cookie", CookieliciousCompat.NEAPOLITAN);
    public static final CookieTileSet ADZUKI_COOKIE = cookieTileSet("adzuki_cookie", CookieliciousCompat.NEAPOLITAN);
    public static final CookieTileSet BEETROOT_COOKIE = cookieTileSet("beetroot_cookie", CookieliciousCompat.SEASONALS);
    public static final CookieTileSet PUMPKIN_COOKIE = cookieTileSet("pumpkin_cookie", CookieliciousCompat.SEASONALS);
    public static final CookieTileSet CHERRY_COOKIE = cookieTileSet("cherry_cookie", CookieliciousCompat.ABNORMALS_DELIGHT);
    public static final CookieTileSet MULBERRY_COOKIE = cookieTileSet("mulberry_cookie", CookieliciousCompat.ABNORMALS_DELIGHT);
    public static final CookieTileSet MAPLE_COOKIE = cookieTileSet("maple_cookie", CookieliciousCompat.ABNORMALS_DELIGHT);
    public static final CookieTileSet GOOSEBERRY_JAM_COOKIE = cookieTileSet("gooseberry_jam_cookie", CookieliciousCompat.BAYOU_BLUES);
    public static final CookieTileSet BAT_COOKIE = cookieTileSet("bat_cookie", CookieliciousCompat.MINERS_DELIGHT);
    public static final CookieTileSet GREEN_TEA_COOKIE = cookieTileSet("green_tea_cookie", CookieliciousCompat.FARMERS_RESPITE);
    public static final CookieTileSet LIME_COOKIE = cookieTileSet("lime_cookie", CookieliciousCompat.COLLECTORS_REAP);


    private static CookieTileSet cookieTileSet(String baseName, String... modids) {
        RegistryObject<Block> tiles = registerCookieTiles(baseName + "_tiles", ToolType.HOE, modids);
        RegistryObject<StairBlock> stairs = registerCookieStairs(baseName + "_tile_stairs", tiles, ToolType.HOE, modids);
        RegistryObject<SlabBlock> slab = registerCookieSlabs(baseName + "_tile_slab", ToolType.HOE, modids);
        RegistryObject<WallBlock> wall = registerCookieWalls(baseName + "_tile_wall", ToolType.HOE, modids);

        CookieTileSet set = new CookieTileSet(tiles, stairs, slab, wall);
        ALL_COOKIE_BLOCKS.add(set);
        return set;
    }

    @SuppressWarnings("SameParameterValue")
    private static RegistryObject<Block> registerCookieTiles(String name, @Nullable ToolType toolType, String... modIds) {
        RegistryObject<Block> blockObject = registerCompatBlock(name, () -> new Block(Properties.COOKIE), Set.of(modIds), CreativeModeTabs.BUILDING_BLOCKS);
        if (toolType != null) {
            EFFECTIVE_TOOL_MAP.put(blockObject, toolType);
        }
        return blockObject;
    }

    @SuppressWarnings("SameParameterValue")
    private static RegistryObject<StairBlock> registerCookieStairs(String name, Supplier<? extends Block> parentBlock, @Nullable ToolType toolType, String... modIds) {
        RegistryObject<StairBlock> blockObject = registerCompatBlock(name, () -> new StairBlock(parentBlock.get()::defaultBlockState, Properties.COOKIE), Set.of(modIds), CreativeModeTabs.BUILDING_BLOCKS);
        if (toolType != null) {
            EFFECTIVE_TOOL_MAP.put(blockObject, toolType);
        }
        return blockObject;
    }

    @SuppressWarnings("SameParameterValue")
    private static RegistryObject<SlabBlock> registerCookieSlabs(String name, @Nullable ToolType toolType, String... modIds) {
        RegistryObject<SlabBlock> blockObject = registerCompatBlock(name, () -> new SlabBlock(Properties.COOKIE), Set.of(modIds), CreativeModeTabs.BUILDING_BLOCKS);
        if (toolType != null) {
            EFFECTIVE_TOOL_MAP.put(blockObject, toolType);
        }
        return blockObject;
    }

    @SuppressWarnings("SameParameterValue")
    private static RegistryObject<WallBlock> registerCookieWalls(String name, @Nullable ToolType toolType, String... modIds) {
        RegistryObject<WallBlock> blockObject = registerCompatBlock(name, () -> new WallBlock(Properties.COOKIE), Set.of(modIds), CreativeModeTabs.BUILDING_BLOCKS);
        if (toolType != null) {
            EFFECTIVE_TOOL_MAP.put(blockObject, toolType);
        }
        return blockObject;
    }

    @SuppressWarnings("SameParameterValue")
    @SafeVarargs
    private static <T extends Block> RegistryObject<T> registerCompatBlock(String name, Supplier<T> blockSupplier, Set<String> modIds, ResourceKey<CreativeModeTab>... creativeModeTabs) {
        RegistryObject<T> regObj = HELPER.createBlockNoItem(name, blockSupplier);
        boolean addToTabs = true;

        if (!modIds.isEmpty()) {
            for (String modId : modIds) {
                if (!ModList.get().isLoaded(modId)) {
                    addToTabs = false;
                    break;
                }
            }
        }
        CookieliciousItems.createItem(name, () -> new BlockItem(regObj.get(), new Item.Properties()), addToTabs ? creativeModeTabs : null);
        return regObj;
    }


    public static class Properties {

        public static final Block.Properties COOKIE = Block.Properties.of()
                .strength(2.0F, 3.0F)
                .sound(SoundType.WOOD);
    }

    public enum ToolType {
        PICKAXE,
        AXE,
        HOE,
        SHOVEL
    }
}