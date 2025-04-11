package com.evoslab.cookielicious.common.core.registry.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.registries.RegistryObject;

public record CookieTileSet(RegistryObject<Block> tiles, RegistryObject<StairBlock> stairs,
                            RegistryObject<SlabBlock> slab, RegistryObject<WallBlock> wall) {

}
