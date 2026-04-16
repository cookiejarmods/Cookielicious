package com.cookiejar.cookielicious.common.tag;

import com.cookiejar.cookielicious.common.core.Cookielicious;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class CItemTags {

    public static final TagKey<Item> COOKIES = forgeTag("cookies");
    public static final TagKey<Item> CHOCOLATE_COOKIES = forgeTag("cookies/chocolate");


    private static TagKey<Item> forgeTag(String path) {
        return ItemTags.create(new ResourceLocation("forge", path));
    }

    private static TagKey<Item> modTag(String path) {
        return ItemTags.create(Cookielicious.modPrefix(path));
    }
}
