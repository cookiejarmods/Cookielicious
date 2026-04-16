package com.cookiejar.cookielicious.common.core.registry;

import com.cookiejar.cookielicious.common.core.Cookielicious;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CookieliciousLootConditions {

    public static final DeferredRegister<LootItemConditionType> REGISTRY = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, Cookielicious.MOD_ID);
}
