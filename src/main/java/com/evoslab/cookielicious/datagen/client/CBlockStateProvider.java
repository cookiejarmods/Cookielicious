package com.evoslab.cookielicious.datagen.client;

import com.evoslab.cookielicious.common.core.Cookielicious;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

import static com.evoslab.cookielicious.datagen.CDatagenUtil.*;
import static com.evoslab.cookielicious.common.core.registry.CookieliciousBlocks.*;

public class CBlockStateProvider extends BlockStateProvider {

    public CBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), Cookielicious.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        cookieTileVariants(COOKIE_TILES, COOKIE_TILE_STAIRS, COOKIE_TILE_SLAB, COOKIE_TILE_WALL);
        cookieTileVariants(STRAWBERRY_COOKIE_TILES, STRAWBERRY_COOKIE_TILE_STAIRS, STRAWBERRY_COOKIE_TILE_SLAB, STRAWBERRY_COOKIE_TILE_WALL);
        cookieTileVariants(CHOCOLATE_COOKIE_TILES, CHOCOLATE_COOKIE_TILE_STAIRS, CHOCOLATE_COOKIE_TILE_SLAB, CHOCOLATE_COOKIE_TILE_WALL);
        cookieTileVariants(VANILLA_COOKIE_TILES, VANILLA_COOKIE_TILE_STAIRS, VANILLA_COOKIE_TILE_SLAB, VANILLA_COOKIE_TILE_WALL);
        cookieTileVariants(HONEY_COOKIE_TILES, HONEY_COOKIE_TILE_STAIRS, HONEY_COOKIE_TILE_SLAB, HONEY_COOKIE_TILE_WALL);
        cookieTileVariants(SWEET_BERRY_COOKIE_TILES, SWEET_BERRY_COOKIE_TILE_STAIRS, SWEET_BERRY_COOKIE_TILE_SLAB, SWEET_BERRY_COOKIE_TILE_WALL);
        cookieTileVariants(BANANA_COOKIE_TILES, BANANA_COOKIE_TILE_STAIRS, BANANA_COOKIE_TILE_SLAB, BANANA_COOKIE_TILE_WALL);
        cookieTileVariants(MINT_COOKIE_TILES, MINT_COOKIE_TILE_STAIRS, MINT_COOKIE_TILE_SLAB, MINT_COOKIE_TILE_WALL);
        cookieTileVariants(ADZUKI_COOKIE_TILES, ADZUKI_COOKIE_TILE_STAIRS, ADZUKI_COOKIE_TILE_SLAB, ADZUKI_COOKIE_TILE_WALL);
        cookieTileVariants(CHERRY_COOKIE_TILES, CHERRY_COOKIE_TILE_STAIRS, CHERRY_COOKIE_TILE_SLAB, CHERRY_COOKIE_TILE_WALL);
        cookieTileVariants(MULBERRY_COOKIE_TILES, MULBERRY_COOKIE_TILE_STAIRS, MULBERRY_COOKIE_TILE_SLAB, MULBERRY_COOKIE_TILE_WALL);
        cookieTileVariants(MAPLE_COOKIE_TILES, MAPLE_COOKIE_TILE_STAIRS, MAPLE_COOKIE_TILE_SLAB, MAPLE_COOKIE_TILE_WALL);
        cookieTileVariants(GOOSEBERRY_JAM_COOKIE_TILES, GOOSEBERRY_JAM_COOKIE_TILE_STAIRS, GOOSEBERRY_JAM_COOKIE_TILE_SLAB, GOOSEBERRY_JAM_COOKIE_TILE_WALL);
    }

    private void cookieTileVariants(Supplier<? extends Block> base, Supplier<? extends Block> stair, Supplier<? extends Block> slab, Supplier<? extends Block> wall) {
        basicBlock(base);
        modStairsBlock(stair, base);
        modSlabBlock(slab, base);
        modWallBlock(wall, base);
    }

    private void basicBlock(Supplier<? extends Block> block) {
        simpleBlock(block.get());
        basicBlockItem(block);
    }

    private void basicBlockItem(Supplier<? extends Block> blockForItem) {
        simpleBlockItem(blockForItem.get(), new ModelFile.ExistingModelFile(modBlockLocation(name(blockForItem.get())), this.models().existingFileHelper));
    }

    private void modStairsBlock(Supplier<? extends Block> stair, Supplier<? extends Block> base) {
        stairsBlock((StairBlock) stair.get(), modBlockLocation(name(base.get())));
        basicBlockItem(stair);
    }

    private void modSlabBlock(Supplier<? extends Block> slab, Supplier<? extends Block> base) {
        slabBlock((SlabBlock) slab.get(), modBlockLocation(name(base.get())), modBlockLocation(name(base.get())));
        basicBlockItem(slab);
    }

    private void modWallBlock(Supplier<? extends Block> wall, Supplier<? extends Block> base) {
        wallBlock((WallBlock) wall.get(), modBlockLocation(name(base.get())));
        this.itemModels().getBuilder(name(wall.get())).parent(this.models().wallInventory(name(wall.get()) + "_inventory", modBlockLocation(name(base.get()))));
    }
}
