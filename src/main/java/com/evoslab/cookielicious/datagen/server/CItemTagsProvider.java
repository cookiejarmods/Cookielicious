package com.evoslab.cookielicious.datagen.server;

import com.evoslab.cookielicious.common.core.Cookielicious;
import com.evoslab.cookielicious.common.core.registry.CookieliciousBlocks;
import com.teamabnormals.blueprint.core.other.tags.BlueprintItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
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
    }
}
