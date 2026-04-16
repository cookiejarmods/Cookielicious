package com.cookiejar.cookielicious.common.triggers;

import com.cookiejar.cookielicious.common.core.registry.CookieliciousTriggers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;

import javax.annotation.Nullable;
import java.util.Optional;

public class SimpleTypeTrigger extends SimpleCriterionTrigger<SimpleTypeTrigger.TriggerInstance> {

    @Override
    public Codec<SimpleTypeTrigger.TriggerInstance> codec() {
        return SimpleTypeTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, Type type) {
        trigger(player, (instance) -> instance.matches(player, type));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player,
                                  Type type) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<SimpleTypeTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
                inst -> inst.group(
                                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(SimpleTypeTrigger.TriggerInstance::player),
                                StringRepresentable.fromEnum(Type::values).fieldOf("type").forGetter(SimpleTypeTrigger.TriggerInstance::type)
                        )
                        .apply(inst, SimpleTypeTrigger.TriggerInstance::new)
        );

        public static Criterion<TriggerInstance> bakeEmAll() {
            ContextAwarePredicate predicate = ContextAwarePredicate.create();
            return CookieliciousTriggers.SIMPLE_TYPE.get().createCriterion(
                    new SimpleTypeTrigger.TriggerInstance(Optional.of(predicate), Type.BAKE_EM_ALL));
        }

        public static Criterion<SimpleTypeTrigger.TriggerInstance> poisonParrot() {
            ContextAwarePredicate predicate = ContextAwarePredicate.create();
            return CookieliciousTriggers.SIMPLE_TYPE.get().createCriterion(
                    new SimpleTypeTrigger.TriggerInstance(Optional.of(predicate), Type.POISON_PARROT));
        }

        public boolean matches(ServerPlayer serverPlayer, Type type) {
            return this.type == type;
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
