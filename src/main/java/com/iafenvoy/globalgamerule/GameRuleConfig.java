package com.iafenvoy.globalgamerule;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class GameRuleConfig {
    private static JsonObject GAME_RULE_MAP = null;
    private static final String PATH = "./config/global_game_rule.json";

    @Nullable
    public static JsonPrimitive getData(String key) {
        if (GAME_RULE_MAP == null) {
            try {
                FileInputStream stream = new FileInputStream(PATH);
                InputStreamReader reader = new InputStreamReader(stream);
                GAME_RULE_MAP = JsonParser.parseReader(reader).getAsJsonObject();
            } catch (IOException e) {
                GlobalGameRule.LOGGER.error("Failed to read file {}", PATH, e);
                try {
                    FileUtils.write(new File(PATH), "{}", StandardCharsets.UTF_8);
                } catch (IOException ex) {
                    GlobalGameRule.LOGGER.error("Failed to write file {}", PATH, ex);
                }
                GAME_RULE_MAP = new JsonObject();
            }
        }
        try {
            if (GAME_RULE_MAP.has(key)) return GAME_RULE_MAP.getAsJsonPrimitive(key);
        } catch (Exception e) {
            GlobalGameRule.LOGGER.error("Cannot read key {}", key, e);
        }
        return null;
    }
}
