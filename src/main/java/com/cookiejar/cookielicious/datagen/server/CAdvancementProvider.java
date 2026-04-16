package com.cookiejar.cookielicious.datagen.server;

import com.cookiejar.cookielicious.common.core.Cookielicious;
import com.cookiejar.cookielicious.common.core.registry.CookieliciousItems;
import com.cookiejar.cookielicious.common.triggers.SimpleTypeTrigger;
import com.cookiejar.cookielicious.common.util.References;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class CAdvancementProvider extends ForgeAdvancementProvider {

    public static final String BAKE_EM_ALL = title("bake_em_all");
    public static final String BAKE_EM_ALL_DESC = desc("bake_em_all");
    public static final String YOU_MONSTER = title("you_monster");
    public static final String YOU_MONSTER_DESC = desc("you_monster");

    public CAdvancementProvider(DataGenerator dataGenerator, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper fileHelper) {
        super(dataGenerator.getPackOutput(), lookupProvider, fileHelper, List.of(new AdvancementGen()));
    }

    private static class AdvancementGen implements ForgeAdvancementProvider.AdvancementGenerator {

        @Override
        public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            ResourceLocation seedyPlaceId = new ResourceLocation("husbandry/plant_seed");
            ResourceLocation bestFriendsId = new ResourceLocation("husbandry/tame_an_animal");

            // Bake em all!
            Advancement.Builder.advancement()
                    .parent(Advancement.Builder.advancement().build(seedyPlaceId))
                    .display(CookieliciousItems.VANILLA_COOKIE.get(),
                            Component.translatable(BAKE_EM_ALL),
                            Component.translatable(BAKE_EM_ALL_DESC),
                            null,
                            FrameType.TASK,
                            false, true, false)
                    .addCriterion("has_all_cookies", SimpleTypeTrigger.TriggerInstance.bakeEmAll())
                    .save(saver, References.BAKE_EM_ALL_ADV.toString());

            // You monster :nomusic:
            Advancement.Builder.advancement()
                    .parent(Advancement.Builder.advancement().build(bestFriendsId))
                    .display(Items.BLAZE_POWDER,
                            Component.translatable(YOU_MONSTER),
                            Component.translatable(YOU_MONSTER_DESC),
                            null,
                            FrameType.TASK,
                            false, true, false)
                    .addCriterion("poison_parrot", SimpleTypeTrigger.TriggerInstance.poisonParrot())
                    .save(saver, References.YOU_MONSTER_ADV.toString());
        }
    }

    private static String title(String advancementName) {
        return Cookielicious.MOD_ID + ".advancements." + advancementName + ".title";
    }

    private static String desc(String advancementName) {
        return Cookielicious.MOD_ID + ".advancements." + advancementName + ".description";
    }

    private static String namespaced(String path) {
        return Cookielicious.MOD_ID + ":" + path;
    }
}
