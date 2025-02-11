package com.evoslab.cookielicious.datagen.client;

import com.evoslab.cookielicious.common.core.Cookielicious;
import com.evoslab.cookielicious.common.core.registry.CookieliciousItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class CItemModelProvider extends ItemModelProvider {

    public CItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator.getPackOutput(), Cookielicious.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(CookieliciousItems.VANILLA_COOKIE);
        basicItem(CookieliciousItems.CHOCOLATE_COOKIE);
        basicItem(CookieliciousItems.STRAWBERRY_COOKIE);
        basicItem(CookieliciousItems.BANANA_COOKIE);
        basicItem(CookieliciousItems.MINT_COOKIE);
        basicItem(CookieliciousItems.ADZUKI_COOKIE);
        basicItem(CookieliciousItems.BEETROOT_COOKIE);
        basicItem(CookieliciousItems.PUMPKIN_COOKIE);
    }

    private void basicItem(Supplier<? extends Item> item) {
        basicItem(item.get());
    }


}
