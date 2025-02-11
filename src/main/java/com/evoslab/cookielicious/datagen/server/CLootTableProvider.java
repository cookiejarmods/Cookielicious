package com.evoslab.cookielicious.datagen.server;

import com.evoslab.cookielicious.common.core.registry.CookieliciousBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CLootTableProvider extends LootTableProvider {

    public CLootTableProvider(DataGenerator generator) {
        super(generator.getPackOutput(), Set.of(),
                List.of(new SubProviderEntry(() -> new CBlockLoot(Set.of()), LootContextParamSets.BLOCK))
        );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext context) {
    }

    private static class CBlockLoot extends BlockLootSubProvider {

        protected CBlockLoot(Set<Item> explosionResistantItems) {
            super(explosionResistantItems, FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected void generate() {
            CookieliciousBlocks.ALL_TILES.forEach(block -> dropSelf(block.get()));
            CookieliciousBlocks.ALL_TILE_STAIRS.forEach(block -> dropSelf(block.get()));
            CookieliciousBlocks.ALL_TILE_SLABS.forEach(block -> add(block.get(), this::createSlabItemTable));
            CookieliciousBlocks.ALL_TILE_WALLS.forEach(block -> dropSelf(block.get()));
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return CookieliciousBlocks.HELPER.getDeferredRegister().getEntries().stream().map(RegistryObject::get)::iterator;
        }
    }
}
