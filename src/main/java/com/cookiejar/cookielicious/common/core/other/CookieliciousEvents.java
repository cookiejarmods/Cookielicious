package com.cookiejar.cookielicious.common.core.other;

import com.cookiejar.cookielicious.common.core.Cookielicious;
import com.cookiejar.cookielicious.common.core.registry.CookieliciousItems;
import com.teamabnormals.blueprint.core.util.TradeUtil;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import static com.teamabnormals.blueprint.core.util.TradeUtil.BlueprintTrade;

@EventBusSubscriber(modid = Cookielicious.MOD_ID)
public class CookieliciousEvents {

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {
        TradeUtil.addVillagerTrades(event, VillagerProfession.FARMER, TradeUtil.JOURNEYMAN,
                new BlueprintTrade(3, CookieliciousItems.STRAWBERRY_COOKIE.get(), 18, 12, 10),
                new BlueprintTrade(3, CookieliciousItems.VANILLA_COOKIE.get(), 18, 12, 10),
                new BlueprintTrade(3, CookieliciousItems.CHOCOLATE_COOKIE.get(), 18, 12, 10),
                new BlueprintTrade(3, CookieliciousItems.BANANA_COOKIE.get(), 18, 12, 10),
                new BlueprintTrade(3, CookieliciousItems.MINT_COOKIE.get(), 18, 12, 10),
                new BlueprintTrade(3, CookieliciousItems.ADZUKI_COOKIE.get(), 18, 12, 10),
                new BlueprintTrade(3, CookieliciousItems.PUMPKIN_COOKIE.get(), 18, 12, 10),
                new BlueprintTrade(3, CookieliciousItems.BEETROOT_COOKIE.get(), 18, 12, 10));
    }

}
