package com.iafenvoy.globalgamerule;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import org.slf4j.Logger;

public class GlobalGameRule implements ModInitializer {
    public static final String MOD_ID = "global_game_rule";
    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(GameRuleConfig.INSTANCE);
    }
}
