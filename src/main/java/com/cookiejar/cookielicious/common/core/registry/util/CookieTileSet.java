package com.cookiejar.cookielicious.common.core.registry.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.neoforged.neoforge.registries.DeferredBlock;

/**
 * Convenience wrapper that holds each cookie block in a cookie block set.
 */
public record CookieTileSet(DeferredBlock<Block> tiles, DeferredBlock<StairBlock> stairs,
                            DeferredBlock<SlabBlock> slab, DeferredBlock<WallBlock> wall) {

}
