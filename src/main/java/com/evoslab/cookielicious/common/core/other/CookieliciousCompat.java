package com.evoslab.cookielicious.common.core.other;

import com.evoslab.cookielicious.common.item.CookieItem;
import com.evoslab.cookielicious.common.item.HealingCookieItem;
import com.teamabnormals.blueprint.core.util.DataUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

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

    public static void registerCompat() {
        registerCompostables();
    }

    public static void registerCompostables() {
        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            if (item instanceof CookieItem || item instanceof HealingCookieItem) {
                DataUtil.registerCompostable(item, 0.85F);
            }
        }
    }
}
