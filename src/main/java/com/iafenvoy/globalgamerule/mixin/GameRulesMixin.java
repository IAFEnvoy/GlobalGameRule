package com.iafenvoy.globalgamerule.mixin;

import com.google.gson.JsonPrimitive;
import com.iafenvoy.globalgamerule.GameRuleConfig;
import com.iafenvoy.globalgamerule.GlobalGameRule;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRules.class)
public class GameRulesMixin {
    @ModifyReturnValue(method = "get", at = @At("RETURN"))
    private <T extends GameRules.Rule<T>> T modifyGameRule(T original, @Local(name = "key") GameRules.Key<T> key) {
        JsonPrimitive primitive = GameRuleConfig.INSTANCE.getData(key.getName());
        if (primitive != null)
            try {
                original.deserialize(primitive.getAsString());
            } catch (Exception e) {
                GlobalGameRule.LOGGER.error("Fail to set game rule {}", key.getName(), e);
            }
        return original;
    }
}
