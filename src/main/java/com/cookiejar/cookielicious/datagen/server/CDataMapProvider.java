package com.cookiejar.cookielicious.datagen.server;

import com.cookiejar.cookielicious.common.tag.CItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class CDataMapProvider extends DataMapProvider {

    public CDataMapProvider(DataGenerator dataGen, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(dataGen.getPackOutput(), lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        final var compostables = builder(NeoForgeDataMaps.COMPOSTABLES);
        compostables.add(CItemTags.COOKIES, new Compostable(0.85F, false), true);
    }
}
