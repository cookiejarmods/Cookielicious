package com.evoslab.cookielicious.common.core.registry;

import com.evoslab.cookielicious.common.core.Cookielicious;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.registries.DeferredRegister;

public class CookieliciousLootConditions {

public static final DeferredRegister<LootItemConditionType> LOOT_ITEM_CONDITION_TYPE = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, Cookielicious.MOD_ID);
}
