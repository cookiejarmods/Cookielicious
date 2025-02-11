package com.evoslab.cookielicious.common.core.registry;

import com.evoslab.cookielicious.common.core.Cookielicious;
import com.evoslab.cookielicious.common.core.CookieliciousConfig;
import com.teamabnormals.blueprint.core.util.DataUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CookieliciousLootConditions {

public static final DeferredRegister<LootItemConditionType> LOOT_ITEM_CONDITION_TYPE = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, Cookielicious.MOD_ID);

public static final RegistryObject<LootItemConditionType> CONFIG = LOOT_ITEM_CONDITION_TYPE.register("config", () -> DataUtil.registerConfigCondition(Cookielicious.MOD_ID, CookieliciousConfig.COMMON));

}
