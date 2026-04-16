package com.cookiejar.cookielicious.common.core;

import com.cookiejar.cookielicious.common.core.other.CookieliciousCompat;
import com.cookiejar.cookielicious.common.core.registry.CookieliciousItems;
import com.cookiejar.cookielicious.common.core.registry.CookieliciousLootConditions;
import com.cookiejar.cookielicious.common.event.CEventsListener;
import com.cookiejar.cookielicious.common.triggers.CookieliciousTriggers;
import com.cookiejar.cookielicious.datagen.client.CBlockStateProvider;
import com.cookiejar.cookielicious.datagen.client.CItemModelProvider;
import com.cookiejar.cookielicious.datagen.client.CLangProvider;
import com.cookiejar.cookielicious.datagen.server.*;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.concurrent.CompletableFuture;

@Mod(Cookielicious.MOD_ID)
@Mod.EventBusSubscriber(modid = Cookielicious.MOD_ID)
public class Cookielicious {

    public static final String MOD_ID = "cookielicious";

    public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);


    public Cookielicious() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new CEventsListener());

        REGISTRY_HELPER.register(modEventBus);

        CookieliciousTriggers.init();

        CookieliciousLootConditions.LOOT_ITEM_CONDITION_TYPE.register(modEventBus);

        modEventBus.addListener(this::doCommonStuff);
        modEventBus.addListener(this::doClientStuff);
        modEventBus.addListener(this::gatherData);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            CookieliciousItems.setupTabEditors();
        });
    }

    private void doCommonStuff(final FMLCommonSetupEvent event) {
        event.enqueueWork(CookieliciousCompat::registerCompat);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }

    @SubscribeEvent
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
        generator.addProvider(server, new CRecipeProvider(generator));
        generator.addProvider(server, new CLootTableProvider(generator));
        generator.addProvider(server, new CAdvancementProvider(generator, lookupProvider, fileHelper));
    }

    public static ResourceLocation modPrefix(String path) {
        return new ResourceLocation(Cookielicious.MOD_ID, path);
    }
}