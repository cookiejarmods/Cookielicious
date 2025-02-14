package com.evoslab.cookielicious.datagen.server;

import com.evoslab.cookielicious.common.core.Cookielicious;
import com.evoslab.cookielicious.common.core.other.CookieliciousCompat;
import com.evoslab.cookielicious.common.core.registry.CookieliciousBlocks;
import com.evoslab.cookielicious.common.core.registry.CookieliciousItems;
import com.evoslab.cookielicious.common.tag.CItemTags;
import com.teamabnormals.blueprint.core.other.tags.BlueprintItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.http.cookie.Cookie;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class CItemTagsProvider extends ItemTagsProvider {

    public CItemTagsProvider(DataGenerator generator, CompletableFuture<HolderLookup.Provider> lookupProvider, BlockTagsProvider blockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator.getPackOutput(), lookupProvider, blockTagsProvider.contentsGetter(), Cookielicious.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        CookieliciousBlocks.ALL_TILE_SLABS.forEach(block -> tag(ItemTags.SLABS).add(block.get().asItem()));
        CookieliciousBlocks.ALL_TILE_STAIRS.forEach(block -> tag(ItemTags.STAIRS).add(block.get().asItem()));
        CookieliciousBlocks.ALL_TILE_WALLS.forEach(block -> tag(ItemTags.WALLS).add(block.get().asItem()));

        CookieliciousItems.ALL_COOKIES.forEach((regObj) -> tag(CItemTags.COOKIES).add(regObj.get()));
        tag(CItemTags.COOKIES)
                .add(Items.COOKIE)
                .addOptional(registryId(CookieliciousCompat.ABNORMALS_DELIGHT, "cherry_cookie"))
                .addOptional(registryId(CookieliciousCompat.ABNORMALS_DELIGHT, "mulberry_cookie"))
                .addOptional(registryId(CookieliciousCompat.ABNORMALS_DELIGHT, "maple_cookie"))
                .addOptional(registryId(CookieliciousCompat.FARMERS_DELIGHT, "honey_cookie"))
                .addOptional(registryId(CookieliciousCompat.FARMERS_DELIGHT, "sweet_berry_cookie")
        );

        tag(CItemTags.CHOCOLATE_COOKIES).add(
                Items.COOKIE,
                CookieliciousItems.CHOCOLATE_COOKIE.get()
        );
    }

    private ResourceLocation registryId(String namespace, String path) {
        ResourceLocation id = new ResourceLocation(namespace, path);

        if (!ForgeRegistries.ITEMS.containsKey(id))
            throw new IllegalArgumentException("Can't find Item with ID '" + id + "' as it does not exist in the registry!");

        return id;
    }
}
