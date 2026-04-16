package com.cookiejar.cookielicious.datagen.server;

import com.cookiejar.cookielicious.common.core.Cookielicious;
import com.cookiejar.cookielicious.common.core.registry.CookieliciousBlocks;
import com.cookiejar.cookielicious.common.core.registry.CookieliciousBlocks.ToolType;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class CBlockTagsProvider extends BlockTagsProvider {

    public CBlockTagsProvider(DataGenerator dataGen, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper fileHelper) {
        super(dataGen.getPackOutput(), lookupProvider, Cookielicious.MOD_ID, fileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        effectiveToolTags();

        CookieliciousBlocks.ALL_COOKIE_BLOCKS.forEach((cookieTileSet) -> {
            tag(BlockTags.SLABS).add(cookieTileSet.slab().get());
            tag(BlockTags.STAIRS).add(cookieTileSet.stairs().get());
            tag(BlockTags.WALLS).add(cookieTileSet.wall().get());
        });
    }

    private void effectiveToolTags() {
        for (RegistryObject<? extends Block> block : CookieliciousBlocks.EFFECTIVE_TOOL_MAP.keySet()) {
            ToolType toolType = CookieliciousBlocks.EFFECTIVE_TOOL_MAP.get(block);

            switch (toolType) {
                case AXE -> addVariantBlockRepoToTag(block, BlockTags.MINEABLE_WITH_AXE);
                case HOE -> addVariantBlockRepoToTag(block, BlockTags.MINEABLE_WITH_HOE);
                case SHOVEL -> addVariantBlockRepoToTag(block, BlockTags.MINEABLE_WITH_SHOVEL);
                case PICKAXE -> addVariantBlockRepoToTag(block, BlockTags.MINEABLE_WITH_PICKAXE);
            }
        }
    }

    private void addVariantBlockRepoToTag(Supplier<? extends Block> blocks, TagKey<Block> blockTag) {
        Objects.requireNonNull(blocks);
        Objects.requireNonNull(blockTag);

        tag(blockTag).add(blocks.get());
    }
}
