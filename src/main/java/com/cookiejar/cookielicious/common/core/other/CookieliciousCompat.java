package com.cookiejar.cookielicious.common.core.other;

import com.cookiejar.cookielicious.common.item.CookieItem;
import com.cookiejar.cookielicious.common.item.HealingCookieItem;
import com.teamabnormals.blueprint.core.util.DataUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public class CookieliciousCompat {

    public static final String MINECRAFT = "minecraft";
    public static final String NEAPOLITAN = "neapolitan";
    public static final String FARMERS_DELIGHT = "farmersdelight";
    public static final String ABNORMALS_DELIGHT = "abnormals_delight";
    public static final String ENVIRONMENTAL = "environmental";
    public static final String UPGRADE_AQUATIC = "upgrade_aquatic";
    public static final String AUTUMNITY = "autumnity";
    public static final String QUARK = "quark";
    public static final String BAYOU_BLUES = "bayou_blues";
    public static final String SEASONALS = "seasonals";
    public static final String MINERS_DELIGHT = "miners_delight";
    public static final String FARMERS_RESPITE = "farmersrespite";
    public static final String COLLECTORS_REAP = "collectorsreap";

    public static void registerCompat() {
        registerCompostables();
    }

    public static void registerCompostables() {
        for (Item item : BuiltInRegistries.ITEM.stream().toList()) {
            if (item instanceof CookieItem || item instanceof HealingCookieItem) {
                // TODO - Add via json instead
                DataUtil.registerCompostable(item, 0.85F);
            }
        }
    }
}
