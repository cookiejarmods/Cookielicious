package com.cookiejar.cookielicious.common.core.registry;

import com.cookiejar.cookielicious.common.core.Cookielicious;
import com.cookiejar.cookielicious.common.triggers.SimpleTypeTrigger;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CookieliciousTriggers {

    public static final DeferredRegister<CriterionTrigger<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.TRIGGER_TYPES, Cookielicious.MOD_ID);

    public static final DeferredHolder<CriterionTrigger<?>, SimpleTypeTrigger> SIMPLE_TYPE = REGISTRY.register("simple_type", SimpleTypeTrigger::new);
}
