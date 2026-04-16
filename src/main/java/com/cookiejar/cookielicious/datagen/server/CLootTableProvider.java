package com.cookiejar.cookielicious.datagen.server;

import com.cookiejar.cookielicious.common.core.registry.CookieliciousBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class CLootTableProvider extends LootTableProvider {

    public CLootTableProvider(DataGenerator generator, CompletableFuture<HolderLookup.Provider> provider) {
        super(
                generator.getPackOutput(),
                Set.of(),
                List.of(new SubProviderEntry(CBlockLoot::new, LootContextParamSets.BLOCK)),
                provider
        );
    }

    @Override
    protected void validate(WritableRegistry<LootTable> writableRegistry, ValidationContext context, ProblemReporter.Collector problemReporter) {
        // Nothing to validate
    }

    private static class CBlockLoot extends BlockLootSubProvider {

        protected CBlockLoot(HolderLookup.Provider provider) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
        }

        @Override
        protected void generate() {
            CookieliciousBlocks.ALL_COOKIE_BLOCKS.forEach((cookieTileSet) -> {
                dropSelf(cookieTileSet.tiles().get());
                dropSelf(cookieTileSet.stairs().get());
                add(cookieTileSet.slab().get(), this::createSlabItemTable);
                dropSelf(cookieTileSet.wall().get());
            });
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return CookieliciousBlocks.HELPER.getDeferredRegister().getEntries().stream().map((holder) -> (Block) holder.get())::iterator;
        }
    }
}
