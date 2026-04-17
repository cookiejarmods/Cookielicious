package com.cookiejar.cookielicious.common.core;

import com.cookiejar.cookielicious.common.core.registry.CookieliciousBlocks;
import com.cookiejar.cookielicious.common.core.registry.CookieliciousItems;
import com.cookiejar.cookielicious.common.core.registry.CookieliciousLootConditions;
import com.cookiejar.cookielicious.common.core.registry.CookieliciousTriggers;
import com.cookiejar.cookielicious.common.event.CEventsListener;
import com.cookiejar.cookielicious.datagen.client.CBlockStateProvider;
import com.cookiejar.cookielicious.datagen.client.CItemModelProvider;
import com.cookiejar.cookielicious.datagen.client.CLangProvider;
import com.cookiejar.cookielicious.datagen.server.*;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@Mod(Cookielicious.MOD_ID)
public class Cookielicious {

    public static final String MOD_ID = "cookielicious";

    public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);


    public Cookielicious(IEventBus modBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(new CEventsListener());

        REGISTRY_HELPER.register(modBus);

        CookieliciousBlocks.init();
        CookieliciousItems.init();
        CookieliciousLootConditions.REGISTRY.register(modBus);
        CookieliciousTriggers.REGISTRY.register(modBus);

        modBus.addListener(this::doCommonStuff);
        modBus.addListener(this::doClientStuff);
        modBus.addListener(this::gatherData);
    }

    private void doCommonStuff(final FMLCommonSetupEvent event) {
        //event.enqueueWork(CookieliciousCompat::registerCompat);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(CookieliciousItems::setupTabEditors);
    }

    public void gatherData(GatherDataEvent event) {
        boolean client = event.includeClient();
        boolean server = event.includeServer();
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(client, new CBlockStateProvider(generator, fileHelper));
        generator.addProvider(client, new CItemModelProvider(generator, fileHelper));
        generator.addProvider(client, new CLangProvider(generator));

        CBlockTagsProvider blockTagProvider = new CBlockTagsProvider(generator, lookupProvider, fileHelper);
        generator.addProvider(server, blockTagProvider);
        generator.addProvider(server, new CItemTagsProvider(generator, lookupProvider, blockTagProvider, fileHelper));
        generator.addProvider(server, new CRecipeProvider(generator, lookupProvider));
        generator.addProvider(server, new CLootTableProvider(generator, lookupProvider));
        generator.addProvider(server, new CAdvancementProvider(generator, lookupProvider, fileHelper));
        generator.addProvider(server, new CDataMapProvider(generator, lookupProvider));
    }

    public static ResourceLocation modPrefix(String path) {
        return ResourceLocation.fromNamespaceAndPath(Cookielicious.MOD_ID, path);
    }
}