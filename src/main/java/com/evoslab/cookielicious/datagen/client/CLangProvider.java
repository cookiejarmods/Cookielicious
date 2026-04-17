package com.evoslab.cookielicious.datagen.client;

import com.evoslab.cookielicious.common.core.Cookielicious;
import com.evoslab.cookielicious.common.core.registry.CookieliciousBlocks;
import com.evoslab.cookielicious.common.core.registry.CookieliciousItems;
import com.evoslab.cookielicious.datagen.server.CAdvancementProvider;
import com.google.common.collect.Lists;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class CLangProvider extends LanguageProvider {

    public CLangProvider(DataGenerator gen) {
        super(gen.getPackOutput(), Cookielicious.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        forItem(CookieliciousItems.ADZUKI_COOKIE);
        forItem(CookieliciousItems.BANANA_COOKIE);
        forItem(CookieliciousItems.CHOCOLATE_COOKIE);
        forItem(CookieliciousItems.STRAWBERRY_COOKIE);
        forItem(CookieliciousItems.VANILLA_COOKIE);
        forItem(CookieliciousItems.MINT_COOKIE);
        forItem(CookieliciousItems.PUMPKIN_COOKIE);
        forItem(CookieliciousItems.BEETROOT_COOKIE);

        CookieliciousBlocks.ALL_COOKIE_BLOCKS.forEach((cookieTileSet) -> {
            forBlock(cookieTileSet.tiles());
            forBlock(cookieTileSet.stairs());
            forBlock(cookieTileSet.slab());
            forBlock(cookieTileSet.wall());
        });

        add(CAdvancementProvider.BAKE_EM_ALL, "Bake 'Em All!");
        add(CAdvancementProvider.BAKE_EM_ALL_DESC, "Obtain one of every cookie");
        add(CAdvancementProvider.YOU_MONSTER, "You Monster");
        add(CAdvancementProvider.YOU_MONSTER_DESC, "Feed a cookie with chocolate to a Parrot and watch it perish horribly");
    }

    /**
     * The code below was taken from Sully's Mod, with permission
     */
    public void forItem(Supplier<? extends Item> item) {
        addItem(item, createTranslation(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.get())).getPath()));
    }

    public void forBlock(Supplier<? extends Block> block) {
        addBlock(block, createTranslation(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block.get())).getPath()));
    }

    public String createTranslation(String path) {
        var translation = "";
        List<String> translationParts = Lists.newArrayList();
        var splitList = path.split("_");
        for (String split : splitList) {
            var capitalized = firstToUpperCase(split);
            translationParts.add(capitalized);
        }
        translation = String.join(" ", translationParts);
        return translation;
    }

    public String firstToUpperCase(String string) {
        var firstLetter = string.charAt(0);
        return string.replaceFirst(String.valueOf(firstLetter), String.valueOf(firstLetter).toUpperCase());
    }
}
