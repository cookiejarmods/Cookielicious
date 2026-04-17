package com.cookiejar.cookielicious.common.tag;

import com.cookiejar.cookielicious.common.core.Cookielicious;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.internal.versions.neoforge.NeoForgeVersion;

public class CItemTags {

    public static final TagKey<Item> COOKIES = neoforgeTag("cookies");
    public static final TagKey<Item> CHOCOLATE_COOKIES = neoforgeTag("cookies/chocolate");


    private static TagKey<Item> neoforgeTag(String path) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(NeoForgeVersion.MOD_ID, path));
    }

    private static TagKey<Item> modTag(String path) {
        return ItemTags.create(Cookielicious.rl(path));
    }
}
