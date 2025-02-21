package com.iafenvoy.globalgamerule;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public enum GameRuleConfig implements SimpleSynchronousResourceReloadListener {
    INSTANCE;
    private static final String PATH = "./config/global_game_rule.json";
    private JsonObject gameRuleMap = null;

    @Override
    public Identifier getFabricId() {
        return Identifier.of(GlobalGameRule.MOD_ID, GlobalGameRule.MOD_ID);
    }

    @Override
    public void reload(ResourceManager manager) {
        try {
            FileInputStream stream = new FileInputStream(PATH);
            InputStreamReader reader = new InputStreamReader(stream);
            gameRuleMap = JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException e) {
            GlobalGameRule.LOGGER.error("Failed to read file {}", PATH, e);
            try {
                FileUtils.write(new File(PATH), "{}", StandardCharsets.UTF_8);
            } catch (IOException ex) {
                GlobalGameRule.LOGGER.error("Failed to write file {}", PATH, ex);
            }
            gameRuleMap = new JsonObject();
        }
    }

    @Nullable
    public JsonPrimitive getData(String key) {
        if (gameRuleMap == null) this.reload(null);
        try {
            if (gameRuleMap.has(key)) return gameRuleMap.getAsJsonPrimitive(key);
        } catch (Exception e) {
            GlobalGameRule.LOGGER.error("Cannot read key {}", key, e);
        }
        return null;
    }
}
