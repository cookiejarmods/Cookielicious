package com.evoslab.cookielicious.common.triggers;

import com.evoslab.cookielicious.common.core.Cookielicious;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.StringRepresentable;

import javax.annotation.Nullable;

public class SimpleTypeTrigger extends SimpleCriterionTrigger<SimpleTypeTrigger.TriggerInstance> {

    private static final ResourceLocation ID = Cookielicious.rl("simple_type_trigger");

    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public SimpleTypeTrigger.TriggerInstance createInstance(JsonObject jsonObject, ContextAwarePredicate predicate, DeserializationContext context) {
        String typeName = GsonHelper.getAsString(jsonObject, "type");
        Type type = Type.getFromName(typeName);

        if (type == null)
            throw new IllegalArgumentException("Attempted to create SimpleTypeTrigger instance with non-existent Type: '" + typeName + "'");

        return new SimpleTypeTrigger.TriggerInstance(predicate, type);
    }

    public void trigger(ServerPlayer player, Type type) {
        this.trigger(player, (instance) -> instance.matches(player, type));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        private final Type type;

        public TriggerInstance(ContextAwarePredicate predicate, Type type) {
            super(SimpleTypeTrigger.ID, predicate);
            this.type = type;
        }

        public static SimpleTypeTrigger.TriggerInstance bakeEmAll() {
            return new SimpleTypeTrigger.TriggerInstance(ContextAwarePredicate.ANY, Type.BAKE_EM_ALL);
        }

        public static SimpleTypeTrigger.TriggerInstance poisonParrot() {
            return new SimpleTypeTrigger.TriggerInstance(ContextAwarePredicate.ANY, Type.POISON_PARROT);
        }

        public boolean matches(ServerPlayer serverPlayer, Type type) {
            return this.type == type;
        }

        public JsonObject serializeToJson(SerializationContext context) {
            JsonObject jsonObject = super.serializeToJson(context);
            jsonObject.addProperty("type", type.getSerializedName());
            return jsonObject;
        }
    }

    public enum Type implements StringRepresentable {
        BAKE_EM_ALL("bake_em_all"),
        POISON_PARROT("poison_parrot");

        Type(String name) {
            this.name = name;
        }

        final String name;

        @Override
        public String getSerializedName() {
            return name;
        }

        @Nullable
        public static Type getFromName(String name) {
            for (Type type : values()) {
                if (type.getSerializedName().equals(name))
                    return type;
            }
            return null;
        }
    }
}
