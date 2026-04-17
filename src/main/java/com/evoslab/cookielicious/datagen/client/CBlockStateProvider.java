package com.evoslab.cookielicious.datagen.client;

import com.evoslab.cookielicious.common.core.Cookielicious;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

import static com.evoslab.cookielicious.common.core.registry.CookieliciousBlocks.ALL_COOKIE_BLOCKS;
import static com.evoslab.cookielicious.datagen.CDatagenUtil.modBlockLocation;
import static com.evoslab.cookielicious.datagen.CDatagenUtil.name;

public class CBlockStateProvider extends BlockStateProvider {

    public CBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), Cookielicious.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ALL_COOKIE_BLOCKS.forEach((cookieTileSet) -> {
            cookieTileVariants(cookieTileSet.tiles(), cookieTileSet.stairs(), cookieTileSet.slab(), cookieTileSet.wall());
        });
    }

    private void cookieTileVariants(Supplier<Block> base, Supplier<StairBlock> stair, Supplier<SlabBlock> slab, Supplier<WallBlock> wall) {
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
